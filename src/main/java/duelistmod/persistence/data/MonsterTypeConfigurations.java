package duelistmod.persistence.data;

import duelistmod.dto.MonsterTypeConfigData;
import duelistmod.enums.DataCategoryType;
import duelistmod.enums.MonsterType;
import duelistmod.persistence.DataDifferenceDTO;
import duelistmod.persistence.PersistentDuelistData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MonsterTypeConfigurations extends DataCategory {

    private HashMap<MonsterType, MonsterTypeConfigData> typeConfigurations = new HashMap<>();

    public MonsterTypeConfigurations() {
        this.category = "Monster Type Configuration Settings";
        this.type = DataCategoryType.Config_Setting;
    }

    public MonsterTypeConfigurations(MonsterTypeConfigurations from) {
        this(from.getTypeConfigurations());
    }

    public MonsterTypeConfigurations(HashMap<MonsterType, MonsterTypeConfigData> typeConfigurations) {
        this();
        this.typeConfigurations = typeConfigurations;
        if (this.typeConfigurations == null) this.typeConfigurations = new HashMap<>();

        for (MonsterType type : MonsterType.values()) {
            MonsterTypeConfigData baseConfig = type.getDefaultConfig();
            MonsterTypeConfigData activeConfig = this.typeConfigurations.getOrDefault(type, null);
            MonsterTypeConfigData mergedConfig;
            if (activeConfig == null) {
                mergedConfig = baseConfig;
            } else {
                mergedConfig = activeConfig;
                for (Map.Entry<String, Object> entry : baseConfig.getProperties().entrySet()) {
                    if (!mergedConfig.getProperties().containsKey(entry.getKey())) {
                        mergedConfig.put(entry.getKey(), entry.getValue());
                    }
                }
            }
            this.typeConfigurations.put(type, mergedConfig);
        }
    }

    @Override
    public LinkedHashSet<DataDifferenceDTO<?>> generateMetricsDifferences(PersistentDuelistData defaultSettings, PersistentDuelistData playerSettings) {
        LinkedHashSet<DataDifferenceDTO<?>> output = new LinkedHashSet<>();
        for (Map.Entry<MonsterType, MonsterTypeConfigData> entry : this.typeConfigurations.entrySet()) {
            List<MonsterType> typeMatches = Arrays.stream(MonsterType.values()).filter(t -> t == entry.getKey()).collect(Collectors.toList());
            if (!typeMatches.isEmpty()) {
                MonsterTypeConfigData base = typeMatches.get(0).getDefaultConfig();
                MonsterTypeConfigData active = entry.getValue();
                for (Map.Entry<String, Object> e : active.getProperties().entrySet()) {
                    if (base.getProperties().containsKey(e.getKey()) && !base.getProperties().get(e.getKey()).equals(e.getValue())) {
                        output.add(new DataDifferenceDTO<>(this, "Monster Type Setting (" + typeMatches.get(0).displayText() + "): " + e.getKey(), base.getProperties().get(e.getKey()), e.getValue()));
                    }
                }
            }
        }
        return output;
    }

    public HashMap<MonsterType, MonsterTypeConfigData> getTypeConfigurations() {
        if (typeConfigurations == null) {
            typeConfigurations = new HashMap<>();
        }
        if (typeConfigurations.isEmpty()) {
            for (MonsterType type : MonsterType.values()) {
                MonsterTypeConfigData baseConfig = type.getDefaultConfig();
                if (baseConfig != null) {
                    typeConfigurations.put(type, baseConfig);
                }
            }
        }
        return typeConfigurations;
    }

    public void setTypeConfigurations(HashMap<MonsterType, MonsterTypeConfigData> typeConfigurations) {
        this.typeConfigurations = typeConfigurations;
    }
}
