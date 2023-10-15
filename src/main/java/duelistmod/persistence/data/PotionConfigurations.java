package duelistmod.persistence.data;

import com.megacrit.cardcrawl.potions.AbstractPotion;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPotion;
import duelistmod.dto.PotionConfigData;
import duelistmod.enums.DataCategoryType;
import duelistmod.persistence.DataDifferenceDTO;
import duelistmod.persistence.PersistentDuelistData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PotionConfigurations extends DataCategory {

    private HashMap<String, PotionConfigData> potionConfigurations = new HashMap<>();
    private Boolean disableAllCommonPotions = false;
    private Boolean disableAllUncommonPotions = false;
    private Boolean disableAllRarePotions = false;

    public PotionConfigurations() {
        this.category = "Potion Configuration Settings";
        this.type = DataCategoryType.Config_Setting;
    }

    public PotionConfigurations(PotionConfigurations from) {
        this(from.getPotionConfigurations(), from.getDisableAllCommonPotions(), from.getDisableAllUncommonPotions(), from.getDisableAllRarePotions());
    }

    public PotionConfigurations(PotionConfigurations base, Boolean disableAllCommonPotions, Boolean disableAllUncommonPotions, Boolean disableAllRarePotions) {
        this(base.getPotionConfigurations(), disableAllCommonPotions, disableAllUncommonPotions, disableAllRarePotions);
    }

    public PotionConfigurations(HashMap<String, PotionConfigData> potionConfigurations, Boolean disableAllCommonPotions, Boolean disableAllUncommonPotions, Boolean disableAllRarePotions) {
        this();
        this.potionConfigurations = potionConfigurations;
        this.disableAllCommonPotions = disableAllCommonPotions;
        this.disableAllUncommonPotions = disableAllUncommonPotions;
        this.disableAllRarePotions = disableAllRarePotions;
    }

    @Override
    public LinkedHashSet<DataDifferenceDTO<?>> generateMetricsDifferences(PersistentDuelistData defaultSettings, PersistentDuelistData playerSettings) {
        LinkedHashSet<DataDifferenceDTO<?>> output = new LinkedHashSet<>();
        if (!defaultSettings.PotionConfigurations.getDisableAllCommonPotions().equals(playerSettings.PotionConfigurations.getDisableAllCommonPotions())) {
            output.add(new DataDifferenceDTO<>(this, "Global Potion Config: Disabled Common Duelist Potions", defaultSettings.PotionConfigurations.getDisableAllCommonPotions(), playerSettings.PotionConfigurations.getDisableAllCommonPotions()));
        }
        if (!defaultSettings.PotionConfigurations.getDisableAllUncommonPotions().equals(playerSettings.PotionConfigurations.getDisableAllUncommonPotions())) {
            output.add(new DataDifferenceDTO<>(this, "Global Potion Config: Disabled Uncommon Duelist Potions", defaultSettings.PotionConfigurations.getDisableAllUncommonPotions(), playerSettings.PotionConfigurations.getDisableAllUncommonPotions()));
        }
        if (!defaultSettings.PotionConfigurations.getDisableAllRarePotions().equals(playerSettings.PotionConfigurations.getDisableAllRarePotions())) {
            output.add(new DataDifferenceDTO<>(this, "Global Potion Config: Disabled Rare Duelist Potions", defaultSettings.PotionConfigurations.getDisableAllRarePotions(), playerSettings.PotionConfigurations.getDisableAllRarePotions()));
        }
        for (Map.Entry<String, PotionConfigData> entry : this.potionConfigurations.entrySet()) {
            List<AbstractPotion> relicMatches = DuelistMod.allDuelistPotions.stream().filter(r -> r.ID.equals(entry.getKey())).collect(Collectors.toList());
            List<DuelistPotion> duelistPotionMatches = new ArrayList<>();
            for (AbstractPotion p : relicMatches) {
                if (p instanceof DuelistPotion) {
                    duelistPotionMatches.add((DuelistPotion)p);
                }
            }
            if (!duelistPotionMatches.isEmpty()) {
                PotionConfigData base = duelistPotionMatches.get(0).getDefaultConfig();
                PotionConfigData active = entry.getValue();
                if (!base.getIsDisabled().equals(active.getIsDisabled())) {
                    output.add(new DataDifferenceDTO<>(this, "Potion Disabled", base.getIsDisabled(), active.getIsDisabled()));
                }
                for (Map.Entry<String, Object> e : active.getProperties().entrySet()) {
                    if (base.getProperties().containsKey(e.getKey()) && !base.getProperties().get(e.getKey()).equals(e.getValue())) {
                        output.add(new DataDifferenceDTO<>(this, "Potion Setting: " + e.getKey(), base.getProperties().get(e.getKey()), e.getValue()));
                    }
                }
            }
        }
        return output;
    }

    public HashMap<String, PotionConfigData> getPotionConfigurations() {
        return potionConfigurations;
    }

    public void setPotionConfigurations(HashMap<String, PotionConfigData> potionConfigurations) {
        this.potionConfigurations = potionConfigurations;
    }

    public Boolean getDisableAllCommonPotions() {
        return disableAllCommonPotions;
    }

    public void setDisableAllCommonPotions(Boolean disableAllCommonPotions) {
        this.disableAllCommonPotions = disableAllCommonPotions;
    }

    public Boolean getDisableAllUncommonPotions() {
        return disableAllUncommonPotions;
    }

    public void setDisableAllUncommonPotions(Boolean disableAllUncommonPotions) {
        this.disableAllUncommonPotions = disableAllUncommonPotions;
    }

    public Boolean getDisableAllRarePotions() {
        return disableAllRarePotions;
    }

    public void setDisableAllRarePotions(Boolean disableAllRarePotions) {
        this.disableAllRarePotions = disableAllRarePotions;
    }
}
