package duelistmod.metrics;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import com.megacrit.cardcrawl.core.*;
import duelistmod.*;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.pools.aqua.*;
import duelistmod.cards.pools.zombies.*;
import duelistmod.enums.MetricsMode;
import duelistmod.helpers.*;
import duelistmod.metrics.builders.*;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

public class Exporter {

    public boolean include_basegame;
    public boolean include_duelist;
    public boolean skipAll;
    public Map<String, List<String>> moduleVersions;
    public ArrayList<ModExportData> mods = new ArrayList<>();
    public ArrayList<CardExportData> cards = new ArrayList<>();
    public ArrayList<RelicExportData> relics = new ArrayList<>();
    public ArrayList<CreatureExportData> creatures = new ArrayList<>();
    public ArrayList<PotionExportData> potions = new ArrayList<>();
    public ArrayList<ColorExportData> colors = new ArrayList<>();
    public ArrayList<KeywordExportData> keywords = new ArrayList<>();

    public Exporter() {
        Map<String, List<String>> currentTrackedModules = MetricsHelper.getAllModuleVersions();
        if (currentTrackedModules.containsKey("SERVER IS DOWN")) {
            this.skipAll = true;
        } else {
            this.moduleVersions = currentTrackedModules;
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
                if (!this.include_duelist && DuelistMod.metricsMode == MetricsMode.LOCAL) {
                    List<String> forceCheckedVersions = MetricsHelper.getAllTrackedDuelistVersions();
                    if (!forceCheckedVersions.contains(DuelistMod.trueVersion)) {
                        this.include_duelist = true;
                    }
                }
            } else {
                this.include_basegame = true;
                this.include_duelist = true;
            }
        }
    }

    private Integer initModList() {
        if (include_basegame) {
            Util.log("Adding basegame to export data.");
            mods.add(new ModExportData());
        }
        int numMods = 0;
        for (ModInfo modInfo : Loader.MODINFOS) {
            if (modInfo.Name.equals("Duelist Mod") && include_duelist) {
                ModExportData data = new ModExportData(modInfo);
                Util.log("Adding DuelistMod to export data.");
                mods.add(data);
                numMods++;
                try {
                    data.cards.add(new CardExportData(this, new LightningDarts()));
                    data.cards.add(new CardExportData(this, new Mispolymerization()));
                    data.cards.add(new CardExportData(this, new CrystalEmeraldTortoise()));
                    for (AbstractCard c : DuelistMod.orbCards) {
                        data.cards.add(new CardExportData(this, c));
                    }
                } catch (Exception ex) {
                    Util.log("Could not add extra Duelist Cards to DuelistMod export!");
                    Util.log(ex.toString());
                }
            } else if (!modInfo.Name.equals("Duelist Mod")) {
                boolean add = true;
                if (this.moduleVersions.containsKey(modInfo.ID)) {
                    List<String> trackedVersions = new ArrayList<>(this.moduleVersions.get(modInfo.ID));
                    add = (trackedVersions.size() < 1 || !trackedVersions.contains(modInfo.ModVersion.getValue()));
                }
                if (add) {
                    Util.log("Adding mod " + modInfo.ID + "[" + modInfo.Name + "] to export data.");
                    mods.add(new ModExportData(modInfo));
                    numMods++;
                }
            }
        }
        return numMods;
    }

    public Integer collectAll() {
        if (skipAll) {
            return 0;
        }
        int sum = initModList();
        if (this.include_basegame) { sum++; }
        if (sum < 1) {
            return 0;
        }
        Util.log("Collecting items to export mods:\n" + this.mods);
        CardExportData.exportAllCards(this);
        Util.log("All export cards collected");
        RelicExportData.exportAllRelics(this);
        Util.log("All export relics collected");
        CreatureExportData.exportAllCreatures(this);
        Util.log("All export creatures collected");
        PotionExportData.exportAllPotions(this);
        Util.log("All export potions collected");
        this.colors = ColorExportData.exportAllColors();
        Util.log("All export colors collected");
        this.keywords = KeywordExportData.exportAllKeywords(this);
        Util.log("All export keywords collected");
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
        Util.log("Export nearly prepared");
        // per color items
        for (CardExportData c : this.cards) {
            ColorExportData colorData = findColor(c.card.color);
            if (colorData == null || colorData.cards == null) continue;
            colorData.cards.add(c);
        }
        Util.log("All card colors for export found");
        for (RelicExportData r : this.relics) {
            if (r.poolColor != null) {
                ColorExportData colorData = findColor(r.poolColor);
                if (colorData == null || colorData.relics == null) continue;
                colorData.relics.add(r);
            }
        }
        Util.log("All relic colors for export found");
        Util.log("Export sum=" + sum);
        return sum;
    }

    public ModExportData findMod(Class<?> cls) {
        // Inspired by BaseMod.patches.whatmod.WhatMod
        if (cls == null) {
            Util.log("Could not determine mod by card class. AAA Mods(0)=" + this.mods.get(0));
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
            Util.log("Could not determine mod by card class name. BBB Mods(0)=" + this.mods.get(0));
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
            Util.log("Could not determine mod by card class. Exception. CCC Mods(0)=" + this.mods.get(0));
            e.printStackTrace();
            return mods.get(0);
        }
    }
    public ModExportData findMod(URL locationURL) {
        if (locationURL == null) {
            Util.log("Could not determine mod by card class URL. DDD Mods(0)=" + this.mods.get(0));
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
