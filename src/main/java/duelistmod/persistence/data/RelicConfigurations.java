package duelistmod.persistence.data;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.dto.RelicConfigData;
import duelistmod.enums.DataCategoryType;
import duelistmod.persistence.DataDifferenceDTO;
import duelistmod.persistence.PersistentDuelistData;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RelicConfigurations extends DataCategory {

    private HashMap<String, RelicConfigData> relicConfigurations = new HashMap<>();
    private Boolean disableAllCommonRelics = false;
    private Boolean disableAllUncommonRelics = false;
    private Boolean disableAllRareRelics = false;
    private Boolean disableAllBossRelics = false;
    private Boolean disableAllShopRelics = false;

    public RelicConfigurations() {
        this.category = "Relic Configuration Settings";
        this.type = DataCategoryType.Config_Setting;
    }

    public RelicConfigurations(RelicConfigurations from) {
        this(from.getRelicConfigurations(), from.getDisableAllCommonRelics(), from.getDisableAllUncommonRelics(), from.getDisableAllRareRelics(), from.getDisableAllBossRelics(), from.getDisableAllShopRelics());
    }

    public RelicConfigurations(RelicConfigurations base, Boolean disableAllCommonRelics, Boolean disableAllUncommonRelics, Boolean disableAllRareRelics, Boolean disableAllBossRelics, Boolean disableAllShopRelics) {
        this(base.getRelicConfigurations(), disableAllCommonRelics, disableAllUncommonRelics, disableAllRareRelics, disableAllBossRelics, disableAllShopRelics);
    }

    public RelicConfigurations(HashMap<String, RelicConfigData> relicConfigurations, Boolean disableAllCommonRelics, Boolean disableAllUncommonRelics, Boolean disableAllRareRelics, Boolean disableAllBossRelics, Boolean disableAllShopRelics) {
        this();
        this.relicConfigurations = relicConfigurations;
        this.disableAllCommonRelics = disableAllCommonRelics;
        this.disableAllUncommonRelics = disableAllUncommonRelics;
        this.disableAllRareRelics = disableAllRareRelics;
        this.disableAllBossRelics = disableAllBossRelics;
        this.disableAllShopRelics = disableAllShopRelics;

        if (this.relicConfigurations == null) this.relicConfigurations = new HashMap<>();
        for (DuelistRelic relic : DuelistMod.allDuelistRelics) {
            RelicConfigData baseConfig = relic.getDefaultConfig();
            RelicConfigData activeConfig = this.relicConfigurations.getOrDefault(relic.relicId, null);
            RelicConfigData mergedConfig;
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
            this.relicConfigurations.put(relic.relicId, mergedConfig);
        }
    }

    @Override
    public LinkedHashSet<DataDifferenceDTO<?>> generateMetricsDifferences(PersistentDuelistData defaultSettings, PersistentDuelistData playerSettings) {
        LinkedHashSet<DataDifferenceDTO<?>> output = new LinkedHashSet<>();
        if (!defaultSettings.RelicConfigurations.getDisableAllCommonRelics().equals(playerSettings.RelicConfigurations.getDisableAllCommonRelics())) {
            output.add(new DataDifferenceDTO<>(this, "Global Relic Config: Disabled Common Duelist Relics", defaultSettings.RelicConfigurations.getDisableAllCommonRelics(), playerSettings.RelicConfigurations.getDisableAllCommonRelics()));
        }
        if (!defaultSettings.RelicConfigurations.getDisableAllUncommonRelics().equals(playerSettings.RelicConfigurations.getDisableAllUncommonRelics())) {
            output.add(new DataDifferenceDTO<>(this, "Global Relic Config: Disabled Uncommon Duelist Relics", defaultSettings.RelicConfigurations.getDisableAllUncommonRelics(), playerSettings.RelicConfigurations.getDisableAllUncommonRelics()));
        }
        if (!defaultSettings.RelicConfigurations.getDisableAllRareRelics().equals(playerSettings.RelicConfigurations.getDisableAllRareRelics())) {
            output.add(new DataDifferenceDTO<>(this, "Global Relic Config: Disabled Rare Duelist Relics", defaultSettings.RelicConfigurations.getDisableAllRareRelics(), playerSettings.RelicConfigurations.getDisableAllRareRelics()));
        }
        if (!defaultSettings.RelicConfigurations.getDisableAllBossRelics().equals(playerSettings.RelicConfigurations.getDisableAllBossRelics())) {
            output.add(new DataDifferenceDTO<>(this, "Global Relic Config: Disabled Boss Duelist Relics", defaultSettings.RelicConfigurations.getDisableAllBossRelics(), playerSettings.RelicConfigurations.getDisableAllBossRelics()));
        }
        if (!defaultSettings.RelicConfigurations.getDisableAllShopRelics().equals(playerSettings.RelicConfigurations.getDisableAllShopRelics())) {
            output.add(new DataDifferenceDTO<>(this, "Global Relic Config: Disabled Shop Duelist Relics", defaultSettings.RelicConfigurations.getDisableAllShopRelics(), playerSettings.RelicConfigurations.getDisableAllShopRelics()));
        }
        for (Map.Entry<String, RelicConfigData> entry : this.relicConfigurations.entrySet()) {
            List<DuelistRelic> relicMatches = DuelistMod.allDuelistRelics.stream().filter(r -> r.relicId.equals(entry.getKey())).collect(Collectors.toList());
            if (!relicMatches.isEmpty()) {
                RelicConfigData base = relicMatches.get(0).getDefaultConfig();
                RelicConfigData active = entry.getValue();
                if (!base.getIsDisabled().equals(active.getIsDisabled())) {
                    output.add(new DataDifferenceDTO<>(this, "Relic Disabled", base.getIsDisabled(), active.getIsDisabled()));
                }
                for (Map.Entry<String, Object> e : active.getProperties().entrySet()) {
                    if (base.getProperties().containsKey(e.getKey()) && !base.getProperties().get(e.getKey()).equals(e.getValue())) {
                        output.add(new DataDifferenceDTO<>(this, "Relic Setting (" + relicMatches.get(0).name + "): " + e.getKey(), base.getProperties().get(e.getKey()), e.getValue()));
                    }
                }
            }
        }
        return output;
    }

    public HashMap<String, RelicConfigData> getRelicConfigurations() {
        if (relicConfigurations == null) {
            relicConfigurations = new HashMap<>();
        }
        if (relicConfigurations.isEmpty()) {
            for (DuelistRelic relic : DuelistMod.allDuelistRelics) {
                relicConfigurations.put(relic.relicId, relic.getDefaultConfig());
            }
        }
        return relicConfigurations;
    }

    public void setRelicConfigurations(HashMap<String, RelicConfigData> relicConfigurations) {
        this.relicConfigurations = relicConfigurations;
    }

    public Boolean getDisableAllCommonRelics() {
        return disableAllCommonRelics;
    }

    public void setDisableAllCommonRelics(Boolean disableAllCommonRelics) {
        this.disableAllCommonRelics = disableAllCommonRelics;
    }

    public Boolean getDisableAllUncommonRelics() {
        return disableAllUncommonRelics;
    }

    public void setDisableAllUncommonRelics(Boolean disableAllUncommonRelics) {
        this.disableAllUncommonRelics = disableAllUncommonRelics;
    }

    public Boolean getDisableAllRareRelics() {
        return disableAllRareRelics;
    }

    public void setDisableAllRareRelics(Boolean disableAllRareRelics) {
        this.disableAllRareRelics = disableAllRareRelics;
    }

    public Boolean getDisableAllBossRelics() {
        return disableAllBossRelics;
    }

    public void setDisableAllBossRelics(Boolean disableAllBossRelics) {
        this.disableAllBossRelics = disableAllBossRelics;
    }

    public Boolean getDisableAllShopRelics() {
        return disableAllShopRelics;
    }

    public void setDisableAllShopRelics(Boolean disableAllShopRelics) {
        this.disableAllShopRelics = disableAllShopRelics;
    }
}
