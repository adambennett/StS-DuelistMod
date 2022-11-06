package duelistmod.metrics;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import duelistmod.DuelistMod;
import duelistmod.helpers.Util;

import java.util.Arrays;
import java.util.Map;

public class TierScoreCacheService {

    static void flushTierScoreCache() {
        try {
            SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig", DuelistMod.duelistDefaults);
            config.remove(DuelistMod.PROP_TIER_SCORE_CACHE);
            config.save();
        } catch (Exception e) {
            Util.log("Error flushing tier score cache!\n" + Arrays.toString(e.getStackTrace()));
        }
    }

    static String serializeTierScores(Map<String, Map<String, Map<Integer, Integer>>> scores) {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(scores);
        } catch (Exception ignored) {}
        return null;
    }

    static Map<String, Map<String, Map<Integer, Integer>>> deserializeTierScoresFromCache() {
        try {
            SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
            String cache = config.getString(DuelistMod.PROP_TIER_SCORE_CACHE);
            if (cache == null || cache.equals("")) {
                return null;
            }
            return new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .readValue(cache, new TypeReference<Map<String, Map<String, Map<Integer, Integer>>>>(){});
        } catch (Exception ex) {
            Util.log("Error while deserializing tier scores from the local cache, flushing...\n"
                    + Arrays.toString(ex.getStackTrace()));
            return null;
        }
    }

    static boolean needToCheckServerForScores() {
        if (DuelistMod.lastTimeTierScoreChecked == null || DuelistMod.lastTimeTierScoreChecked.equals("NEVER")) {
            return true;
        }

        long currentSum = calculateTierScoreCheckTimeSum();
        try {
            long lastSum = Long.parseLong(DuelistMod.lastTimeTierScoreChecked);
            if (currentSum >= (lastSum + 24 * 60 * 60 * 1000)) return true;
        } catch (NumberFormatException ignored) {}

        return false;
    }

    static boolean updateTierScoreCacheData(String scores) {
        long sum = calculateTierScoreCheckTimeSum();
        try {
            SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
            config.setString(DuelistMod.PROP_LAST_TIME_TIER_SCORES_CHECKED, sum+"");
            config.setString(DuelistMod.PROP_TIER_SCORE_CACHE, scores);
            config.save();
            return true;
        } catch (Exception e) {
            Util.log("Error updating last tier score check time!\n" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    private static long calculateTierScoreCheckTimeSum() {
        return System.currentTimeMillis();
    }
}
