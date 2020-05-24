package duelistmod.metrics;

import com.fasterxml.jackson.annotation.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.helpers.*;
import duelistmod.abstracts.*;
import duelistmod.helpers.poolhelpers.*;
import duelistmod.metrics.builders.*;

import java.util.*;
import java.util.concurrent.*;

public class CardExportData implements Comparable<CardExportData> {

    @JsonIgnore
    public ModExportData mod;

    public AbstractCard card;
    public CardExportData upgrade;
    public String name;
    public String color;
    public String rarity;
    public String type;
    public String duelistType = "none";
    public Boolean isDuelistCard = false;
    public List<String> pools = new ArrayList<>();
    public String cost, costAndUpgrade;
    public String text, textAndUpgrade, textWikiData, textWikiFormat;
    public int block, damage, magicNumber, secondMag, thirdMag, tributes, summons, entomb;

    public CardExportData(Exporter export, AbstractCard card) {
        this(export, card, true);
    }

    public CardExportData(Exporter export, AbstractCard card, boolean exportUpgrade) {
        cardSetup(export, card, exportUpgrade, card instanceof DuelistCard);
    }

    private void cardSetup(Exporter export, AbstractCard card, boolean exportUpgrade, boolean duelist) {
        card.initializeDescription();
        this.card = card;
        this.name = card.name;
        this.rarity = ExportUploader.rarityName(card.rarity);
        this.color = ExportUploader.colorName(card.color);
        this.type = ExportUploader.typeString(card.type);
        this.mod = export.findMod(card.getClass());
        if (duelist && card instanceof DuelistCard) {
            DuelistCard dCard = (DuelistCard)card;
            this.duelistType = ExportUploader.duelistType(dCard);
            this.secondMag = dCard.isSecondMagicModified ? dCard.secondMagic : dCard.baseSecondMagic;
            this.thirdMag = dCard.isThirdMagicModified ? dCard.thirdMagic : dCard.baseThirdMagic;
            this.entomb = dCard.isEntombModified ? dCard.entomb : dCard.baseEntomb;
            this.tributes = dCard.isTributesModified ? dCard.tributes : dCard.baseTributes;
            this.summons = dCard.isSummonsModified ? dCard.summons : dCard.baseSummons;
            this.pools = GlobalPoolHelper.getAppearancePools(dCard);
            this.isDuelistCard = true;
        }
        if (!card.upgraded) {
            this.mod.cards.add(this);
        }
        if (exportUpgrade && !card.upgraded && card.canUpgrade()) {
            AbstractCard copy = card.makeCopy();
            copy.upgrade();
            copy.displayUpgrades();
            this.upgrade = new CardExportData(export, copy, false);
        }
        // cost
        if (card.cost == -1) {
            this.cost = "X";
        } else if (card.cost == -2) {
            this.cost = ""; // unplayable
        } else {
            this.cost = String.valueOf(card.cost);
        }
        this.costAndUpgrade = combineUpgrade(cost, upgrade == null ? null : upgrade.cost, TextMode.NORMAL_MODE);
        // description
        this.block = card.isBlockModified ? card.block : card.baseBlock;
        this.damage = card.isDamageModified ? card.damage : card.baseDamage;
        this.magicNumber = card.isMagicNumberModified ? card.magicNumber : card.baseMagicNumber;
        this.text = card.rawDescription
                .replace("!duelist:E!", String.valueOf(entomb))
                .replace("!duelist:M!", String.valueOf(secondMag))
                .replace("!duelist:SUMM!", String.valueOf(summons))
                .replace("!duelist:O!", String.valueOf(thirdMag))
                .replace("!duelist:TRIB!", String.valueOf(tributes))
                .replace("!B!", String.valueOf(block))
                .replace("!D!", String.valueOf(damage))
                .replace("!M!", String.valueOf(magicNumber))
                //.replace(" NL ", "\n");
                .replace(" NL ", " ");
        if (upgrade == null) {
            this.textAndUpgrade = this.text;
        } else {
            this.textAndUpgrade = combineDescriptions(card.rawDescription, upgrade.card.rawDescription, TextMode.NORMAL_MODE)
                    .replace("!duelist:E!", combineUpgrade(String.valueOf(entomb), String.valueOf(upgrade.entomb), TextMode.NORMAL_MODE))
                    .replace("!duelist:M!", combineUpgrade(String.valueOf(secondMag), String.valueOf(upgrade.secondMag), TextMode.NORMAL_MODE))
                    .replace("!duelist:SUMM!", combineUpgrade(String.valueOf(summons), String.valueOf(upgrade.summons), TextMode.NORMAL_MODE))
                    .replace("!duelist:O!", combineUpgrade(String.valueOf(thirdMag), String.valueOf(upgrade.thirdMag), TextMode.NORMAL_MODE))
                    .replace("!duelist:TRIB!", combineUpgrade(String.valueOf(tributes), String.valueOf(upgrade.tributes), TextMode.NORMAL_MODE))
                    .replace("!B!", combineUpgrade(String.valueOf(block), String.valueOf(upgrade.block), TextMode.NORMAL_MODE))
                    .replace("!D!", combineUpgrade(String.valueOf(damage), String.valueOf(upgrade.damage), TextMode.NORMAL_MODE))
                    .replace("!M!", combineUpgrade(String.valueOf(magicNumber), String.valueOf(upgrade.magicNumber), TextMode.NORMAL_MODE))
                    //.replace(" NL ", "\n");
                    .replace(" NL ", " ");
            this.textWikiData = combineDescriptions(card.rawDescription, upgrade.card.rawDescription, TextMode.WIKI_DATA)
                    .replace("!duelist:E!", combineUpgrade(String.valueOf(entomb), String.valueOf(upgrade.entomb), TextMode.WIKI_DATA))
                    .replace("!duelist:M!", combineUpgrade(String.valueOf(secondMag), String.valueOf(upgrade.secondMag), TextMode.WIKI_DATA))
                    .replace("!duelist:SUMM!", combineUpgrade(String.valueOf(summons), String.valueOf(upgrade.summons), TextMode.WIKI_DATA))
                    .replace("!duelist:O!", combineUpgrade(String.valueOf(thirdMag), String.valueOf(upgrade.thirdMag), TextMode.WIKI_DATA))
                    .replace("!duelist:TRIB!", combineUpgrade(String.valueOf(tributes), String.valueOf(upgrade.tributes), TextMode.WIKI_DATA))
                    .replace("!B!", combineUpgrade(String.valueOf(block), String.valueOf(upgrade.block), TextMode.WIKI_DATA))
                    .replace("!D!", combineUpgrade(String.valueOf(damage), String.valueOf(upgrade.damage), TextMode.WIKI_DATA))
                    .replace("!M!", combineUpgrade(String.valueOf(magicNumber), String.valueOf(upgrade.magicNumber), TextMode.WIKI_DATA))
                    //.replace(" NL ", "\n");
                    .replace(" NL ", " ");
            this.textWikiFormat = combineDescriptions(card.rawDescription, upgrade.card.rawDescription, TextMode.WIKI_FORMAT)
                    .replace("!duelist:E!", combineUpgrade(String.valueOf(entomb), String.valueOf(upgrade.entomb), TextMode.WIKI_FORMAT))
                    .replace("!duelist:M!", combineUpgrade(String.valueOf(secondMag), String.valueOf(upgrade.secondMag), TextMode.WIKI_FORMAT))
                    .replace("!duelist:SUMM!", combineUpgrade(String.valueOf(summons), String.valueOf(upgrade.summons), TextMode.WIKI_FORMAT))
                    .replace("!duelist:O!", combineUpgrade(String.valueOf(thirdMag), String.valueOf(upgrade.thirdMag), TextMode.WIKI_FORMAT))
                    .replace("!duelist:TRIB!", combineUpgrade(String.valueOf(tributes), String.valueOf(upgrade.tributes), TextMode.WIKI_FORMAT))
                    .replace("!B!", combineUpgrade(String.valueOf(block), String.valueOf(upgrade.block), TextMode.WIKI_FORMAT))
                    .replace("!D!", combineUpgrade(String.valueOf(damage), String.valueOf(upgrade.damage), TextMode.WIKI_FORMAT))
                    .replace("!M!", combineUpgrade(String.valueOf(magicNumber), String.valueOf(upgrade.magicNumber), TextMode.WIKI_FORMAT))
                    //.replace(" NL ", "\n");
                    .replace(" NL ", " ");
        }
    }

    private static String combineUpgrade(String a, String b, TextMode mode) {
        return (b == null || b.equals(a)) ? a : (mode == TextMode.WIKI_DATA) ? "[" + a + "|" + b + "]" : a + "(" + b + ")";
    }

    private static String combineDescriptions(String a, String b, TextMode mode) {
        // Combine description with upgrade description
        if (a.equals(b) && mode == TextMode.NORMAL_MODE) return a;
        // prepare punctuation, so we count it as separate words
        a = preprocessText(a, mode);
        b = preprocessText(b, mode);
        // Split input into words
        ArrayList<String> awords = words(a);
        ArrayList<String> bwords = words(b);
        // Use the standard sequence alignment algorithm (Needleman–Wunsch)
        final int INSERT_A = 10;
        final int INSERT_B = 10;
        int[][] score = new int[awords.size()+1][bwords.size()+1];
        for (int ai=0 ; ai <= awords.size() ; ai++) {
            score[ai][0] = ai * INSERT_A;
        }
        for (int bi=0 ; bi <= bwords.size() ; bi++) {
            score[0][bi] = bi * INSERT_B;
        }
        for (int ai=1 ; ai <= awords.size() ; ai++) {
            for (int bi=1 ; bi <= bwords.size() ; bi++) {
                score[ai][bi] = Math.min(score[ai-1][bi] + INSERT_A,
                        Math.min(score[ai][bi-1] + INSERT_B,
                                score[ai-1][bi-1] + wordCost(awords.get(ai-1),bwords.get(bi-1),mode)));
            }
        }
        // Now return the optimal alignment, first in reverse order
        ArrayList<String>    words  = new ArrayList<>();
        ArrayList<Character> source = new ArrayList<>();
        int ai=awords.size(), bi=bwords.size();
        while (ai > 0 && bi > 0) {
            int acost       = score[ai-1][bi] + INSERT_A;
            int bcost       = score[ai][bi-1] + INSERT_B;
            int replacecost = score[ai-1][bi-1] + wordCost(awords.get(ai-1),bwords.get(bi-1),mode);
            if (bcost <= acost && bcost <= replacecost) {
                words.add(bwords.get(bi-1));
                source.add('b');
                bi--;
            } else if (acost <= replacecost) {
                words.add(awords.get(ai-1));
                source.add('a');
                ai--;
            } else {
                words.add(wordReplacement(awords.get(ai-1),bwords.get(bi-1),mode));
                source.add('c');
                ai--; bi--;
            }
        }
        while (bi > 0) {
            words.add(bwords.get(bi-1));
            source.add('b');
            bi--;
        }
        while (ai > 0) {
            words.add(awords.get(ai-1));
            source.add('a');
            ai--;
        }
        // Now reverse
        Collections.reverse(words);
        Collections.reverse(source);
        // Add parentheses to destinguish the sources
        // We keep track of which source we are taking words from ('a', 'b', or a combination 'c')
        char prev = 'c';
        int astart = 0;
        StringBuilder out = new StringBuilder();
        if (mode == TextMode.NORMAL_MODE || mode == TextMode.WIKI_FORMAT) {
            for (int i = 0 ; i < words.size() ; i++) {
                if (i > 0) out.append(" ");
                if (source.get(i) == 'a' && prev != 'a') astart = i;
                if (source.get(i) != 'b' && prev == 'b') out.append(") ");
                if (source.get(i) == 'b' && prev != 'b') out.append("(");
                if (source.get(i) == 'c' && prev == 'a') {
                    // a deletion not followed by an insertion. For example "Exhaust. (not Exhaust.)".
                    out.append("(not");
                    for (int j = astart ; j < i ; j++) {
                        out.append(" ");
                        out.append(words.get(j));
                    }
                    out.append(")");
                }
                prev = source.get(i);
                // is this a keyword?
                out.append(formatKeyword(words.get(i), mode));
            }
            if (prev == 'b') out.append(")");
            if (prev == 'a') {
                out.append(" (not");
                for (int j = astart ; j < words.size() ; j++) {
                    out.append(" ");
                    out.append(words.get(j));
                }
                out.append(")");
            }
        } else {
            for (int i = 0 ; i < words.size() ; i++) {
                if      (source.get(i) == 'c' && prev == 'a') out.append("|] ");
                else if (source.get(i) == 'c' && prev == 'b') out.append("] ");
                else if (source.get(i) == 'b' && prev == 'a') out.append("|");
                else if (source.get(i) == 'b' && prev == 'c') { if (i > 0) out.append("| "); else out.append("|"); }
                else if (source.get(i) == 'a' && prev == 'c') { if (i > 0) out.append(" ["); else out.append("["); }
                else if (source.get(i) == 'a' && prev == 'b') out.append("] [");
                else if (i > 0) out.append(" ");
                prev = source.get(i);
                // is this a keyword?
                out.append(formatKeyword(words.get(i), mode));
            }
            if (prev == 'b') out.append("]");
            if (prev == 'a') out.append("|");
        }
        // Join and remove unnecesary spaces
        if (mode == TextMode.WIKI_DATA) {
            return out.toString().replace(" .",".").replace(" ,",",").replace(" ]","]").replace("[ ","[").replace(" NL]"," NL ]").replace("[NL ","[ NL ").replace(" NL|"," NL |").replace("|NL ","| NL ");
        } else {
            return out.toString().replace(" .",".").replace(" ,",",").replace(" )",")").replace("( ","(").replace(" NL)",") NL ").replace("(NL "," NL (");
        }
    }

    private static String preprocessText(String a, TextMode mode) {
        a = a.replace("."," .").replace(","," ,");
        if (mode == TextMode.WIKI_DATA) {
            a = a.replace("[R]","<R>").replace("[G]","<G>").replace("[B]","<B>");
        } else if(mode == TextMode.WIKI_FORMAT) {
            final String[] energySymbols = {"[R] [R] [R]","[R] [R]","[R]", "[G] [G] [G]","[G] [G]","[G]", "[B] [B] [B]","[B] [B]","[B]"};
            for (String e : energySymbols) {
                a = a.replace(e, (e.length()+1)/4 + " Energy");
            }
        }
        return a;
    }

    private static int wordCost(String aw, String bw, TextMode mode) {
        if (aw.equals(bw)) return 0;
        if (mode != TextMode.WIKI_DATA && bw.equals(aw + "s")) return 10;
        return 21;
    }
    private static String wordReplacement(String aw, String bw, TextMode mode) {
        if (aw.equals(bw)) return aw;
        if (mode != TextMode.WIKI_DATA && bw.equals(aw + "s")) return aw + "(s)";
        return aw + " (" + bw + ")";
    }

    private static String formatKeyword(String w, TextMode mode) {
        if (GameDictionary.keywords.containsKey(w.toLowerCase()) || w.toLowerCase().equals("energy")) {
            // keyword
            if (mode == TextMode.WIKI_FORMAT) {
                return "[[" + w + "]]";
            } else if (mode == TextMode.WIKI_DATA) {
                return "#" + w;
            }
        }
        return w;
    }

    private static ArrayList<String> words(String str) {
        Scanner scanner = new Scanner(str);
        ArrayList<String> out = new ArrayList<>();
        while (scanner.hasNext()) {
            out.add(scanner.next());
        }
        scanner.close();
        return out;
    }

    public static ArrayList<CardExportData> exportAllCards(Exporter export) {
        ArrayList<CardExportData> cards = new ArrayList<>();
        if (export.include_duelist) { GlobalPoolHelper.setupAppearanceMaps(); }
        for (AbstractCard.CardColor color : AbstractCard.CardColor.values()) {
            ArrayList<AbstractCard> cardLibrary = CardLibrary.getCardList(CardLibrary.LibraryType.valueOf(color.name()));
            for (AbstractCard c : cardLibrary) {
                    cards.add(new CardExportData(export, c.makeCopy()));
            }
        }
        // Collections.sort(cards);     // was causing issues, not needed anyway
        return cards;
    }

    enum TextMode {
        NORMAL_MODE,
        WIKI_DATA,
        WIKI_FORMAT,
    }

    @Override
    public int compareTo(CardExportData that) {
        int result = card.rarity.compareTo(that.card.rarity);
        if (result == 0) result = name.compareTo(that.name);
        return result;
    }

    @Override
    public String toString() {
        JsonToStringBuilder builder = new JsonToStringBuilder(this);
        builder.append("card_id", card.cardID);
        builder.append("isDuelistCard", isDuelistCard);
        builder.append("name", name);
        builder.append("color", color);
        builder.append("rarity", rarity);
        builder.append("type", type);
        builder.append("duelistType", duelistType);
        builder.append("cost", cost);
        builder.append("text", text);
        builder.append("block", block);
        builder.append("damage", damage);
        builder.append("magicNumber", magicNumber);
        builder.append("secondMag", secondMag);
        builder.append("thirdMag", thirdMag);
        builder.append("tributes", tributes);
        builder.append("summons", summons);
        builder.append("entomb", entomb);
        builder.append("pools", pools);
        return builder.build();
    }
}
