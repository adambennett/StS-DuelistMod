package duelistmod.metrics;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import duelistmod.DuelistMod;
import duelistmod.helpers.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleVersionCacheService {

    public static void flushModuleCache() {
        try {
            SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig", DuelistMod.duelistDefaults);
            config.remove(DuelistMod.PROP_MODULE_CACHE);
            config.save();
        } catch (Exception e) {
            Util.log("Error flushing module cache!\n" + Arrays.toString(e.getStackTrace()));
        }
    }

    public static String serializeModules(Map<String, List<String>> modules) {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(modules);
        } catch (Exception ignored) {}
        return null;
    }

    public static Map<String, List<String>> deserializeModulesFromCache() {
        try {
            SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
            String cache = config.getString(DuelistMod.PROP_MODULE_CACHE);
            if (cache == null || cache.equals("")) {
                return null;
            }
            return new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .readValue(cache, new TypeReference<Map<String, List<String>> >(){});
        } catch (Exception ex) {
            Util.log("Error while deserializing modules from the local cache, flushing...\n"
                    + Arrays.toString(ex.getStackTrace()));
            return null;
        }
    }

    public static boolean needToCheckServerForModules() {
        Map<String, List<String>> deserial = deserializeModulesFromCache();
        if (deserial == null) return true;

        List<ModuleCacheRecord> modulesInCache = convertToCacheFormat(deserial);
        if (modulesInCache.size() < 1) return true;

        for (ModInfo activeModule : Loader.MODINFOS) {
            boolean matchedOne = false;
            for (ModuleCacheRecord cachedModule : modulesInCache) {
                if (activeModule.ModVersion.getValue().equals(cachedModule.getVersion()) && activeModule.ID.equals(cachedModule.getId())) {
                    matchedOne = true;
                    break;
                }
            }
            if (!matchedOne) return true;
        }
        return false;
    }

    public static boolean updateModuleCacheData(String modules) {
        try {
            SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
            config.setString(DuelistMod.PROP_MODULE_CACHE, modules);
            config.save();
            return true;
        } catch (Exception e) {
            Util.log("Error updating modules cache!\n" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    private static List<ModuleCacheRecord> convertToCacheFormat(Map<String, List<String>> serverRecords) {
        List<ModuleCacheRecord> cache = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : serverRecords.entrySet()) {
            for (String version : entry.getValue()) {
                ModuleCacheRecord mcr = new ModuleCacheRecord(entry.getKey(), version);
                cache.add(mcr);
            }
        }
        return cache;
    }

}
