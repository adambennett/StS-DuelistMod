package duelistmod.helpers;

public class CommonKeywordIconHelper {


    public static String parseReplaceKeywords(String unmodified) {
        unmodified = unmodified.replaceAll("Exhaust. NL ", "");
        unmodified = unmodified.replaceAll(" NL Exhaust\\.", "");
        unmodified = unmodified.replaceAll(" NL Ethereal", "");
        unmodified = unmodified.replaceAll("Ethereal NL ", "");
        unmodified = unmodified.replaceAll("Innate NL ", "");
        unmodified = unmodified.replaceAll("Purge NL ", "");
        unmodified = unmodified.replaceAll("NL Purge", "");
        unmodified = unmodified.replaceAll("Retain NL ", "NL ");
        return unmodified;
    }

}
