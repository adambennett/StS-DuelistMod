package duelistmod.helpers;

import duelistmod.DuelistMod;
import duelistmod.dto.DuelistKeyword;

import java.util.Map;

public class CardDescriptionModificationHelper {


    public static String parseReplaceKeywords(String unmodified) {
        unmodified = unmodified.replaceAll("Exhaust\\. NL ", "");
        unmodified = unmodified.replaceAll(" NL Exhaust\\.", "");
        unmodified = unmodified.replaceAll("\\. Exhaust\\.", ".");
        unmodified = unmodified.replaceAll(" NL Ethereal", "");
        unmodified = unmodified.replaceAll("Ethereal NL ", "");
        unmodified = unmodified.replaceAll("Innate NL ", "");
        unmodified = unmodified.replaceAll("Purge NL ", "");
        unmodified = unmodified.replaceAll("NL Purge NL", "NL");
        unmodified = unmodified.replaceAll("Retain NL ", "");
        return unmodified;
    }

    public static String parseReplaceMultiWordKeywords(String unmodified) {
        for (Map.Entry<String, DuelistKeyword> entry : DuelistMod.duelistKeywordMultiwordKeyMap.entrySet()) {
            if (unmodified.contains(entry.getKey())) {
                unmodified = unmodified.replaceAll(entry.getKey(), entry.getValue().FORMATTED_DISPLAY);
            }
        }
        return unmodified;
    }

}
