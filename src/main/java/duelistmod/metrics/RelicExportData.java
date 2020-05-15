package duelistmod.metrics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.badlogic.gdx.graphics.Colors;
import com.fasterxml.jackson.annotation.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.*;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import duelistmod.metrics.builders.*;

public class RelicExportData implements Comparable<RelicExportData> {

    @JsonIgnore
    public ModExportData mod;

    @JsonIgnoreProperties("card")
    public AbstractRelic relic;
    public String tier;
    public String pool;
    public AbstractCard.CardColor poolColor;
    public String name;
    public String description, descriptionHTML, descriptionPlain;
    public String flavorText, flavorTextHTML, flavorTextPlain;

    @Override
    public String toString() {
        JsonToStringBuilder builder = new JsonToStringBuilder(this);
        builder.append("relic_id", relic.relicId);
        builder.append("tier", tier);
        builder.append("pool", pool);
        builder.append("name", name);
        builder.append("flavorText", flavorText);
        builder.append("description", description);
        return builder.build();
    }

    public RelicExportData(Exporter export, AbstractRelic relic, AbstractCard.CardColor pool) {
        this.relic = relic;
        this.mod = export.findMod(relic.getClass());
        this.mod.relics.add(this);
        this.name = relic.name;
        this.description = relic.description;
        this.descriptionHTML = smartTextToHTML(relic.description,true,true);
        this.descriptionPlain = smartTextToPlain(relic.description,true,true);
        this.flavorText = relic.flavorText;
        this.flavorTextHTML = smartTextToHTML(relic.flavorText,false,true);;
        this.flavorTextPlain = smartTextToPlain(relic.flavorText,false,true);;
        this.tier = ExportUploader.tierName(relic.tier);
        this.poolColor = pool;
        this.pool = pool == null ? "" : ExportUploader.colorName(pool);
    }

    public static String smartTextToHTML(String string, boolean smart, boolean markup) {
        return parseSmartText(string,smart,markup,true);
    }

    public static String smartTextToPlain(String string, boolean smart, boolean markup) {
        return parseSmartText(string,smart,markup,false);
    }

    // Parse "smart text" from FontHelper into plain text or html
    // @param smart FontHelper.renderSmartText escapes
    // @param markup GlyphLayout color markup
    // @param html HTML or plain text output
    public static String parseSmartText(String string, boolean smart, boolean markup, boolean html) {
        if (string == null) return "";
        StringBuilder out = new StringBuilder();
        boolean space = false; // should we insert a space?
        boolean wordStart = true;
        int wordTags = 0; // number of tags that close at the end of the word
        int openTags = 0;
        for (int pos = 0; pos < string.length();) {
            char c = string.charAt(pos);
            if (c == ' ') {
                while (wordTags > 0) {
                    wordTags--; openTags--;
                    if (html) out.append("</span>");
                }
                pos++;
                wordStart = true;
                space = true;
            } else if (c == '\n' || c == '\t') {
                while (wordTags > 0) {
                    wordTags--; openTags--;
                    if (html) out.append("</span>");
                }
                pos++;
                wordStart = true;
                space = false;
            } else if (smart && wordStart && string.startsWith("NL ",pos)) {
                //out.append('\n');
                out.append(' ');
                pos += 3;
                space = false;
            } else if (smart && wordStart && string.startsWith("TAB ",pos)) {
                out.append('\t');
                pos += 4;
                space = false;
            } else if (smart && wordStart && c == '#' && pos+1 < string.length()) {
                if (space) {
                    out.append(' ');
                    space = false;
                }
                if (html) {
                    out.append("<span class=\"color-");
                    out.append(string.charAt(pos+1));
                    out.append("\">");
                }
                pos += 2;
                openTags++;
                wordTags++;
            } else if (markup && c == '[' && pos+1 < string.length() && string.charAt(pos+1) == '[') {
                // escaped [
                if (space) {
                    out.append(' ');
                    space = false;
                }
                wordStart = false;
                out.append(c);
                pos += 2;
            } else if (markup && c == '[' && pos + 1 < string.length()) {
                if (space) {
                    out.append(' ');
                    space = false;
                }
                int end = string.indexOf(']',pos);
                if (end == -1 || (end == pos+1 && openTags == 0) || (end == pos+2)) {
                    // no closing bracket, or an energy orb like [R]
                    wordStart = false;
                    out.append(c);
                    pos++;
                } else if (end == pos+1) {
                    if (openTags > 0) {
                        if (wordTags > 0) wordTags--;
                        openTags--;
                        if (html) out.append("</span>");
                    }
                    pos = end + 1;
                } else {
                    String colorName = string.substring(pos+1,end);
                    if (colorName.charAt(0) != '#' && Colors.get(colorName) == null) {
                        // not a valid color, ignore
                        wordStart = false;
                        out.append(c);
                        pos++;
                    } else {
                        if (html) {
                            out.append("<span style=\"color:");
                            out.append(colorName);
                            out.append("\">");
                        }
                        openTags++;
                        if (smart || wordTags > 0) {
                            // note: FontHelper uses separate calls to GlyphLayout.setText, so each word is rendered independently, as a result all tags end at word boundaries
                            // note2: If we are already using wordTags, then also close this tag at the end of the word
                            wordTags++;
                        }
                        pos = end + 1;
                    }
                }
            } else {
                if (space) {
                    out.append(' ');
                    space = false;
                }
                wordStart = false;
                if (html && c == '<') {
                    out.append("&lt;");
                } else if (html && c == '>') {
                    out.append("&gt;");
                } else if (html && c == '&') {
                    out.append("&amp;");
                } else {
                    out.append(c);
                }
                pos++;
            }
        }
        while (openTags > 0) {
            openTags--;
            if (html) out.append("</span>");
        }
        return out.toString();
    }


    public static ArrayList<RelicExportData> exportAllRelics(Exporter export) {
        ArrayList<RelicExportData> relics = new ArrayList<>();
        @SuppressWarnings("unchecked")
        HashMap<String, AbstractRelic> sharedRelics = (HashMap<String,AbstractRelic>) ReflectionHacks.getPrivateStatic(RelicLibrary.class, "sharedRelics");
        for (AbstractRelic relic : sharedRelics.values()) {
            relics.add(new RelicExportData(export, relic, null));
        }
        for (AbstractRelic relic : RelicLibrary.redList) {
            relics.add(new RelicExportData(export, relic, AbstractCard.CardColor.RED));
        }
        for (AbstractRelic relic : RelicLibrary.greenList) {
            relics.add(new RelicExportData(export, relic, AbstractCard.CardColor.GREEN));
        }
        for (AbstractRelic relic : RelicLibrary.blueList) {
            relics.add(new RelicExportData(export, relic, AbstractCard.CardColor.BLUE));
        }
        for (AbstractRelic relic : RelicLibrary.whiteList) {
            relics.add(new RelicExportData(export, relic, AbstractCard.CardColor.PURPLE));
        }
        for (HashMap.Entry<AbstractCard.CardColor,HashMap<String,AbstractRelic>> entry : BaseMod.getAllCustomRelics().entrySet()) {
            for (AbstractRelic relic : entry.getValue().values()) {
                relics.add(new RelicExportData(export, relic, entry.getKey()));
            }
        }
        Collections.sort(relics);
        return relics;
    }

    @Override
    public int compareTo(RelicExportData that) {
        if (relic.tier != that.relic.tier) return relic.tier.compareTo(that.relic.tier);
        return name.compareTo(that.name);
    }
}
