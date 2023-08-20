package duelistmod.persistence;

import com.evacipated.cardcrawl.modthespire.lib.ConfigUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import duelistmod.DuelistMod;
import duelistmod.dto.EventConfigData;
import duelistmod.helpers.Util;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;

public class DuelistConfig {

    private final String filePath;
    private final String oldFilePath;
    private boolean loadFromOldFile;

    public DuelistConfig(String modName, String fileName) throws IOException {
        this.filePath = makeFilePath(modName, fileName);
        this.oldFilePath = makeOldFilePath();
        File file = new File(this.filePath);
        if (!file.exists()) {
            this.loadFromOldFile = true;
        }
        //noinspection ResultOfMethodCallIgnored
        new File(this.filePath).createNewFile();
        DuelistMod.persistentDuelistData = this.load();
        this.save();
        Util.log("Checking Metrics Differences:\n" + DuelistMod.persistentDuelistData.generateMetricsDifferences());
    }

    public static String makeFilePath(String modName, String fileName) {
        return makeFilePath(modName, fileName, "json");
    }

    public static String makeFilePath(String modName, String fileName, String ext) {
        String dirPath;
        if (modName == null) {
            dirPath = ConfigUtils.CONFIG_DIR + File.separator;
        } else {
            dirPath = ConfigUtils.CONFIG_DIR + File.separator + modName + File.separator;
        }

        String filePath = dirPath + fileName + "." + ext;
        File dir = new File(dirPath);
        //noinspection ResultOfMethodCallIgnored
        dir.mkdirs();
        return filePath;
    }

    public static String makeOldFilePath() {
        String dirPath = ConfigUtils.CONFIG_DIR + File.separator + "TheDuelist" + File.separator;
        String filePath = dirPath + "DuelistConfig.properties";
        File dir = new File(dirPath);
        //noinspection ResultOfMethodCallIgnored
        dir.mkdirs();
        return filePath;
    }

    public void save() {
        try (Writer writer = new FileWriter(this.filePath)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(DuelistMod.persistentDuelistData, writer);
        } catch (IOException e) {
            Util.logError("JSON Config Save Failed", e, true);
        }
    }

    public PersistentDuelistData load() {
        File file = new File(this.filePath);
        if (this.loadFromOldFile || !file.exists()) {
            this.loadFromOldFile = false;
            return fallbackLoad();
        }

        try (Reader reader = new FileReader(file)) {
            //Gson gson = new Gson();
            PersistentDuelistData loadedData = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .readValue(reader, PersistentDuelistData.class);
            //PersistentDuelistData loadedData = gson.fromJson(reader, new TypeToken<PersistentDuelistData>(){}.getType());
            if (loadedData != null) {
                return new PersistentDuelistData(loadedData);
            }
            Util.log("Error parsing loaded PersistentDuelistData JSON");
            return fallbackLoad();
        } catch (IOException e) {
            Util.logError("JSON Config Load Failed", e, true);
            return fallbackLoad();
        }
    }

    private PersistentDuelistData fallbackLoad() {
        File oldFile = new File(this.oldFilePath);
        PersistentDuelistData output = null;
        if (oldFile.exists()) {
            output = PersistentDuelistData.generateFromOldPropertiesFile();
        }
        if (output != null) {
            return output;
        }
        return new PersistentDuelistData();
    }
}
