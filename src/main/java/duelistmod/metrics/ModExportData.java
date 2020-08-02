package duelistmod.metrics;

import com.evacipated.cardcrawl.modthespire.*;
import com.megacrit.cardcrawl.core.*;
import duelistmod.metrics.builders.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class ModExportData {
    public ModInfo info;
    public String id;
    public String name;
    public String modName; // same as name, but empty for the base game
    public String version;
    public String displayName;
    public URL url;
    public Boolean isBaseGame;
    public Boolean isDuelist;
    public ArrayList<CardExportData> cards = new ArrayList<>();
    public ArrayList<RelicExportData> relics = new ArrayList<>();
    public ArrayList<CreatureExportData> creatures = new ArrayList<>();
    public ArrayList<PotionExportData> potions = new ArrayList<>();
    public ArrayList<KeywordExportData> keywords = new ArrayList<>();
    public ArrayList<String> authors = new ArrayList<>();
    public static final String BASE_GAME_ID = "slay-the-spire";

    public ModExportData(ModInfo info) {
        this.info = info;
        this.id = info.ID;
        this.name = info.Name;
        this.modName = info.Name;
        this.displayName = info.Name;
        this.url = info.jarURL;
        this.version = info.ModVersion.getValue();
        this.isBaseGame = false;
        this.isDuelist = info.Name.equals("Duelist Mod");
        this.authors.addAll(Arrays.asList(info.Authors));
    }

    public ModExportData() {
        this.info = null;
        this.id = BASE_GAME_ID;
        this.name = "Slay the Spire";
        this.displayName = "Slay the Spire";
        this.modName = "";
        this.version = CardCrawlGame.TRUE_VERSION_NUM;
        this.isBaseGame = true;
        this.isDuelist = false;
        this.authors.add("MegaCrit");
        try {
            this.url = new File(Loader.STS_JAR).toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        JsonToStringBuilder builder = new JsonToStringBuilder(this);
        builder.append("modID", id);
        builder.append("version", version);
        builder.append("isDuelist", isDuelist);
        builder.append("isBaseGame", isBaseGame);
        builder.append("name", name);
        builder.append("modName", modName);
        builder.append("displayName", displayName);
        builder.appendObj("cards", cards);
        builder.appendObj("relics", relics);
        builder.appendObj("creatures", creatures);
        builder.appendObj("potions", potions);
        builder.appendObj("keywords", keywords);
        builder.append("authors", authors);
        return builder.build();
    }
}
