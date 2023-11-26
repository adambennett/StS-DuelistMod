package duelistmod.persistence.data;

import com.megacrit.cardcrawl.orbs.AbstractOrb;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistOrb;
import duelistmod.dto.OrbConfigData;
import duelistmod.enums.DataCategoryType;
import duelistmod.helpers.Util;
import duelistmod.persistence.DataDifferenceDTO;
import duelistmod.persistence.PersistentDuelistData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrbConfigurations extends DataCategory {

    private HashMap<String, OrbConfigData> orbConfigurations = new HashMap<>();
    private Boolean disableAllOrbPassives = false;
    private Boolean disableAllOrbEvokes = false;

    public OrbConfigurations() {
        this.category = "Orb Configuration Settings";
        this.type = DataCategoryType.Config_Setting;
    }

    public OrbConfigurations(OrbConfigurations from) {
        this(from.getOrbConfigurations(), from.getDisableAllOrbPassives(), from.getDisableAllOrbEvokes());
    }

    public OrbConfigurations(OrbConfigurations base, Boolean disableAllOrbPassives, Boolean disableAllOrbEvokes) {
        this(base.getOrbConfigurations(), disableAllOrbPassives, disableAllOrbEvokes);
    }

    public OrbConfigurations(HashMap<String, OrbConfigData> orbConfigurations, Boolean disableAllOrbPassives, Boolean disableAllOrbEvokes) {
        this();
        this.orbConfigurations = orbConfigurations;
        this.disableAllOrbPassives = disableAllOrbPassives;
        this.disableAllOrbEvokes = disableAllOrbEvokes;

        if (this.orbConfigurations == null) this.orbConfigurations = new HashMap<>();
        HashMap<String, OrbConfigData> baseConfigs = Util.generateDefaultConfigurationsMap();
        for (Map.Entry<String, OrbConfigData> entry : baseConfigs.entrySet()) {
            OrbConfigData baseConfig = entry.getValue();
            OrbConfigData activeConfig = this.orbConfigurations.getOrDefault(entry.getKey(), null);
            OrbConfigData mergedConfig;
            if (activeConfig == null) {
                mergedConfig = baseConfig;
            } else {
                mergedConfig = activeConfig;
                for (Map.Entry<String, Object> e : baseConfig.getProperties().entrySet()) {
                    if (!mergedConfig.getProperties().containsKey(e.getKey())) {
                        mergedConfig.put(e.getKey(), e.getValue());
                    }
                }
            }
            this.orbConfigurations.put(entry.getKey(), mergedConfig);
        }
    }

    @Override
    public LinkedHashSet<DataDifferenceDTO<?>> generateMetricsDifferences(PersistentDuelistData defaultSettings, PersistentDuelistData playerSettings) {
        LinkedHashSet<DataDifferenceDTO<?>> output = new LinkedHashSet<>();
        if (!defaultSettings.OrbConfigurations.getDisableAllOrbPassives().equals(playerSettings.OrbConfigurations.getDisableAllOrbPassives())) {
            output.add(new DataDifferenceDTO<>(this, "Global Orb Config: Disabled Passive Effects", defaultSettings.OrbConfigurations.getDisableAllOrbPassives(), playerSettings.OrbConfigurations.getDisableAllOrbPassives()));
        }
        if (!defaultSettings.OrbConfigurations.getDisableAllOrbEvokes().equals(playerSettings.OrbConfigurations.getDisableAllOrbEvokes())) {
            output.add(new DataDifferenceDTO<>(this, "Global Orb Config: Disabled Evoke Effects", defaultSettings.OrbConfigurations.getDisableAllOrbEvokes(), playerSettings.OrbConfigurations.getDisableAllOrbEvokes()));
        }

        for (Map.Entry<String, OrbConfigData> entry : this.getOrbConfigurations().entrySet()) {
            List<AbstractOrb> orbMatches = DuelistCard.allOrbs.stream().filter(o -> o.ID.equals(entry.getKey())).collect(Collectors.toList());
            List<DuelistOrb> duelistOrbMatches = new ArrayList<>();
            for (AbstractOrb o : orbMatches) {
                if (o instanceof DuelistOrb) {
                    duelistOrbMatches.add((DuelistOrb)o);
                }
            }
            if (!duelistOrbMatches.isEmpty()) {
                OrbConfigData base = duelistOrbMatches.get(0).getDefaultConfig();
                OrbConfigData active = entry.getValue();
                if (!base.getPassiveDisabled().equals(active.getPassiveDisabled())) {
                    output.add(new DataDifferenceDTO<>(this, "Passive Effect Disabled", base.getPassiveDisabled(), active.getPassiveDisabled()));
                }
                if (!base.getEvokeDisabled().equals(active.getEvokeDisabled())) {
                    output.add(new DataDifferenceDTO<>(this, "Evoke Effect Disabled", base.getEvokeDisabled(), active.getEvokeDisabled()));
                }
                if (!base.getConfigPassive().equals(active.getConfigPassive())) {
                    output.add(new DataDifferenceDTO<>(this, "Passive Effect Modified", base.getConfigPassive(), active.getConfigPassive()));
                }
                if (!base.getConfigEvoke().equals(active.getConfigEvoke())) {
                    output.add(new DataDifferenceDTO<>(this, "Evoke Effect Modified", base.getConfigEvoke(), active.getConfigEvoke()));
                }
                for (Map.Entry<String, Object> e : active.getProperties().entrySet()) {
                    if (base.getProperties().containsKey(e.getKey()) && !base.getProperties().get(e.getKey()).equals(e.getValue())) {
                        output.add(new DataDifferenceDTO<>(this, "Orb Setting (" + duelistOrbMatches.get(0).name + "): " + e.getKey(), base.getProperties().get(e.getKey()), e.getValue()));
                    }
                }
            }
        }
        return output;
    }

    public HashMap<String, OrbConfigData> getOrbConfigurations() {
        if (orbConfigurations == null) {
            orbConfigurations = new HashMap<>();
        }
        if (orbConfigurations.isEmpty()) {
            orbConfigurations = Util.generateDefaultConfigurationsMap();
        }
        return orbConfigurations;
    }

    public void setOrbConfigurations(HashMap<String, OrbConfigData> orbConfigurations) {
        this.orbConfigurations = orbConfigurations;
    }

    public Boolean getDisableAllOrbPassives() {
        return disableAllOrbPassives;
    }

    public void setDisableAllOrbPassives(Boolean disableAllOrbPassives) {
        this.disableAllOrbPassives = disableAllOrbPassives;
    }

    public Boolean getDisableAllOrbEvokes() {
        return disableAllOrbEvokes;
    }

    public void setDisableAllOrbEvokes(Boolean disableAllOrbEvokes) {
        this.disableAllOrbEvokes = disableAllOrbEvokes;
    }
}
