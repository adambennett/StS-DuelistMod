package duelistmod.metrics;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import com.megacrit.cardcrawl.core.*;
import duelistmod.*;
import duelistmod.helpers.*;
import duelistmod.metrics.builders.*;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

public class Exporter {

    public boolean include_basegame;
    public boolean include_duelist;
    public Map<String, List<String>> moduleVersions;
    public ArrayList<ModExportData> mods = new ArrayList<>();
    public ArrayList<CardExportData> cards = new ArrayList<>();
    public ArrayList<RelicExportData> relics = new ArrayList<>();
    public ArrayList<CreatureExportData> creatures = new ArrayList<>();
    public ArrayList<PotionExportData> potions = new ArrayList<>();
    public ArrayList<ColorExportData> colors = new ArrayList<>();
    public ArrayList<KeywordExportData> keywords = new ArrayList<>();

    public Exporter() {
        this.moduleVersions = MetricsHelper.getAllModuleVersions();
        if (this.moduleVersions.size() > 0) {
            if (this.moduleVersions.containsKey("slay-the-spire")) {
                List<String> trackedVersions = new ArrayList<>(this.moduleVersions.get("slay-the-spire"));
                if (trackedVersions.size() < 1 || !trackedVersions.contains(CardCrawlGame.TRUE_VERSION_NUM)) {
                    this.include_basegame = true;
                }
            } else {
                this.include_basegame = true;
            }

            if (this.moduleVersions.containsKey("duelistmod")) {
                List<String> trackedDuelVersions = new ArrayList<>(this.moduleVersions.get("duelistmod"));
                if (trackedDuelVersions.size() < 1 || !trackedDuelVersions.contains(DuelistMod.trueVersion)) {
                    this.include_duelist = true;
                }
            } else {
                this.include_duelist = true;
            }
        } else {
            this.include_basegame = true;
            this.include_duelist = true;
        }
    }

    private Integer initModList() {
        if (include_basegame) {
            mods.add(new ModExportData());
        }
        int numMods = 0;
        for (ModInfo modInfo : Loader.MODINFOS) {
            if (modInfo.Name.equals("Duelist Mod") && include_duelist) {
                mods.add(new ModExportData(modInfo));
                numMods++;
            } else if (!modInfo.Name.equals("Duelist Mod")) {
                boolean add = true;
                if (this.moduleVersions.containsKey(modInfo.ID)) {
                    List<String> trackedVersions = new ArrayList<>(this.moduleVersions.get(modInfo.ID));
                    add = (trackedVersions.size() < 1 || !trackedVersions.contains(modInfo.ModVersion.getValue()));
                }
                if (add) {
                    mods.add(new ModExportData(modInfo));
                    numMods++;
                }
            }
        }
        return numMods;
    }

    public Integer collectAll() {
        int sum = initModList();
        if (this.include_basegame) { sum++; }
        Util.log("Collecting items");
        CardExportData.exportAllCards(this);
        RelicExportData.exportAllRelics(this);
        CreatureExportData.exportAllCreatures(this);
        PotionExportData.exportAllPotions(this);
        this.colors = ColorExportData.exportAllColors();
        this.keywords = KeywordExportData.exportAllKeywords(this);
        // collect only from included mods
        for (ModExportData mod : this.mods) {
            if (modIncludedInExport(mod)) {
                cards.addAll(mod.cards);
                relics.addAll(mod.relics);
                creatures.addAll(mod.creatures);
                potions.addAll(mod.potions);
                // sort
                Collections.sort(mod.cards);
                Collections.sort(mod.relics);
                Collections.sort(mod.creatures);
                Collections.sort(mod.potions);
            }
        }
        Collections.sort(this.cards);
        Collections.sort(this.relics);
        Collections.sort(this.creatures);
        Collections.sort(this.potions);
        // per color items
        for (CardExportData c : this.cards) {
            findColor(c.card.color).cards.add(c);
        }
        for (RelicExportData r : this.relics) {
            if (r.poolColor != null) findColor(r.poolColor).relics.add(r);
        }
        return sum;
    }

    public ModExportData findMod(Class<?> cls) {
        // Inspired by BaseMod.patches.whatmod.WhatMod
        if (cls == null) {
            return mods.get(0);
        }
        URL locationURL = cls.getProtectionDomain().getCodeSource().getLocation();
        if (locationURL == null) {
            return findMod(cls.getName());
        } else {
            return findMod(locationURL);
        }
    }
    public ModExportData findMod(String clsName) {
        if (clsName == null) {
            return mods.get(0);
        }
        try {
            ClassPool pool = Loader.getClassPool();
            CtClass ctCls = pool.get(clsName);
            String url = ctCls.getURL().getFile();
            int i = url.lastIndexOf('!');
            url = url.substring(0, i);
            URL locationURL = new URL(url);
            return findMod(locationURL);
        } catch (NotFoundException | MalformedURLException e) {
            e.printStackTrace();
            return mods.get(0);
        }
    }
    public ModExportData findMod(URL locationURL) {
        if (locationURL == null) {
            return mods.get(0);
        }
        for (ModExportData mod : mods) {
            if (locationURL.equals(mod.url)) {
                return mod;
            }
        }
        return mods.get(0);
    }

    public boolean modIncludedInExport(ModExportData mod) {
        if (mod.id.equals(ModExportData.BASE_GAME_ID)) {
            return include_basegame;
        } else {
            return true;
        }
    }

    public ColorExportData findColor(CardColor color) {
        for (ColorExportData c : this.colors) {
            if (c.color == color) return c;
        }
        return null;
    }

    @Override
    public String toString() {
        JsonToStringBuilder builder = new JsonToStringBuilder(this);
        builder.append("mods", mods);
        builder.append("cards", cards);
        builder.append("relics", relics);
        builder.append("creatures", creatures);
        builder.append("potions", potions);
        builder.append("colors", colors);
        builder.append("keywords", keywords);
        return builder.build();
    }
}
