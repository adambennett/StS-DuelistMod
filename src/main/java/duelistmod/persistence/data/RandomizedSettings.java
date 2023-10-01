package duelistmod.persistence.data;

import duelistmod.enums.DataCategoryType;
import duelistmod.persistence.DataDifferenceDTO;
import duelistmod.persistence.PersistentDuelistData;

import java.util.LinkedHashSet;

public class RandomizedSettings extends DataCategory {

    private Boolean noCostChanges = false;
    private Boolean onlyCostDecreases = false;
    private Boolean noTributeChanges = false;
    private Boolean onlyTributeDecreases = false;
    private Boolean noSummonChanges = false;
    private Boolean onlySummonIncreases = false;
    private Boolean alwaysUpgrade = false;
    private Boolean neverUpgrade = false;
    private Boolean allowEthereal = true;
    private Boolean allowExhaust = true;

    public RandomizedSettings() {
        this.category = "Randomized Settings";
        this.type = DataCategoryType.Config_Setting;
    }

    public RandomizedSettings(RandomizedSettings from) {
        this(from.noCostChanges, from.onlyCostDecreases, from.noTributeChanges, from.onlyTributeDecreases, from.noSummonChanges, from.onlySummonIncreases, from.alwaysUpgrade, from.neverUpgrade, from.allowEthereal, from.allowExhaust);
    }

    public RandomizedSettings(Boolean noCostChanges, Boolean onlyCostDecreases, Boolean noTributeChanges, Boolean onlyTributeDecreases, Boolean noSummonChanges, Boolean onlySummonIncreases, Boolean alwaysUpgrade, Boolean neverUpgrade, Boolean allowEthereal, Boolean allowExhaust) {
        this();
        this.noCostChanges = PersistentDuelistData.validateBool(noCostChanges, false);
        this.onlyCostDecreases = PersistentDuelistData.validateBool(onlyCostDecreases, false);
        this.noTributeChanges = PersistentDuelistData.validateBool(noTributeChanges, false);
        this.onlyTributeDecreases = PersistentDuelistData.validateBool(onlyTributeDecreases, false);
        this.noSummonChanges = PersistentDuelistData.validateBool(noSummonChanges, false);
        this.onlySummonIncreases = PersistentDuelistData.validateBool(onlySummonIncreases, false);
        this.alwaysUpgrade = PersistentDuelistData.validateBool(alwaysUpgrade, false);
        this.neverUpgrade = PersistentDuelistData.validateBool(neverUpgrade, false);
        this.allowEthereal = PersistentDuelistData.validateBool(allowEthereal, true);
        this.allowExhaust = PersistentDuelistData.validateBool(allowExhaust, true);
    }

    @Override
    public LinkedHashSet<DataDifferenceDTO<?>> generateMetricsDifferences(PersistentDuelistData defaultSettings, PersistentDuelistData playerSettings) {
        LinkedHashSet<DataDifferenceDTO<?>> output = new LinkedHashSet<>();
        if (!defaultSettings.RandomizedSettings.noCostChanges.equals(playerSettings.RandomizedSettings.noCostChanges)) {
            output.add(new DataDifferenceDTO<>(this, "Randomized: No Cost Changes", defaultSettings.RandomizedSettings.noCostChanges, playerSettings.RandomizedSettings.noCostChanges));
        }
        if (!defaultSettings.RandomizedSettings.onlyCostDecreases.equals(playerSettings.RandomizedSettings.onlyCostDecreases)) {
            output.add(new DataDifferenceDTO<>(this, "Randomized: Costs Only Decrease", defaultSettings.RandomizedSettings.onlyCostDecreases, playerSettings.RandomizedSettings.onlyCostDecreases));
        }
        if (!defaultSettings.RandomizedSettings.noTributeChanges.equals(playerSettings.RandomizedSettings.noTributeChanges)) {
            output.add(new DataDifferenceDTO<>(this, "Randomized: No Tribute Changes", defaultSettings.RandomizedSettings.noTributeChanges, playerSettings.RandomizedSettings.noTributeChanges));
        }
        if (!defaultSettings.RandomizedSettings.onlyTributeDecreases.equals(playerSettings.RandomizedSettings.onlyTributeDecreases)) {
            output.add(new DataDifferenceDTO<>(this, "Randomized: Tributes Only Decrease", defaultSettings.RandomizedSettings.onlyTributeDecreases, playerSettings.RandomizedSettings.onlyTributeDecreases));
        }
        if (!defaultSettings.RandomizedSettings.noSummonChanges.equals(playerSettings.RandomizedSettings.noSummonChanges)) {
            output.add(new DataDifferenceDTO<>(this, "Randomized: No Summon Changes", defaultSettings.RandomizedSettings.noSummonChanges, playerSettings.RandomizedSettings.noSummonChanges));
        }
        if (!defaultSettings.RandomizedSettings.onlySummonIncreases.equals(playerSettings.RandomizedSettings.onlySummonIncreases)) {
            output.add(new DataDifferenceDTO<>(this, "Randomized: Summons Only Increase", defaultSettings.RandomizedSettings.onlySummonIncreases, playerSettings.RandomizedSettings.onlySummonIncreases));
        }
        if (!defaultSettings.RandomizedSettings.alwaysUpgrade.equals(playerSettings.RandomizedSettings.alwaysUpgrade)) {
            output.add(new DataDifferenceDTO<>(this, "Randomized: Always Upgrade", defaultSettings.RandomizedSettings.alwaysUpgrade, playerSettings.RandomizedSettings.alwaysUpgrade));
        }
        if (!defaultSettings.RandomizedSettings.neverUpgrade.equals(playerSettings.RandomizedSettings.neverUpgrade)) {
            output.add(new DataDifferenceDTO<>(this, "Randomized: Never Upgrade", defaultSettings.RandomizedSettings.neverUpgrade, playerSettings.RandomizedSettings.neverUpgrade));
        }
        if (!defaultSettings.RandomizedSettings.allowEthereal.equals(playerSettings.RandomizedSettings.allowEthereal)) {
            output.add(new DataDifferenceDTO<>(this, "Randomized: Allow Ethereal", defaultSettings.RandomizedSettings.allowEthereal, playerSettings.RandomizedSettings.allowEthereal));
        }
        if (!defaultSettings.RandomizedSettings.allowExhaust.equals(playerSettings.RandomizedSettings.allowExhaust)) {
            output.add(new DataDifferenceDTO<>(this, "Randomized: Allow Exhaust", defaultSettings.RandomizedSettings.allowExhaust, playerSettings.RandomizedSettings.allowExhaust));
        }
        return output;
    }

    public Boolean getNoCostChanges() {
        return noCostChanges;
    }

    public void setNoCostChanges(Boolean noCostChanges) {
        this.noCostChanges = noCostChanges;
    }

    public Boolean getOnlyCostDecreases() {
        return onlyCostDecreases;
    }

    public void setOnlyCostDecreases(Boolean onlyCostDecreases) {
        this.onlyCostDecreases = onlyCostDecreases;
    }

    public Boolean getNoTributeChanges() {
        return noTributeChanges;
    }

    public void setNoTributeChanges(Boolean noTributeChanges) {
        this.noTributeChanges = noTributeChanges;
    }

    public Boolean getOnlyTributeDecreases() {
        return onlyTributeDecreases;
    }

    public void setOnlyTributeDecreases(Boolean onlyTributeDecreases) {
        this.onlyTributeDecreases = onlyTributeDecreases;
    }

    public Boolean getNoSummonChanges() {
        return noSummonChanges;
    }

    public void setNoSummonChanges(Boolean noSummonChanges) {
        this.noSummonChanges = noSummonChanges;
    }

    public Boolean getOnlySummonIncreases() {
        return onlySummonIncreases;
    }

    public void setOnlySummonIncreases(Boolean onlySummonIncreases) {
        this.onlySummonIncreases = onlySummonIncreases;
    }

    public Boolean getAlwaysUpgrade() {
        return alwaysUpgrade;
    }

    public void setAlwaysUpgrade(Boolean alwaysUpgrade) {
        this.alwaysUpgrade = alwaysUpgrade;
    }

    public Boolean getNeverUpgrade() {
        return neverUpgrade;
    }

    public void setNeverUpgrade(Boolean neverUpgrade) {
        this.neverUpgrade = neverUpgrade;
    }

    public Boolean getAllowEthereal() {
        return allowEthereal;
    }

    public void setAllowEthereal(Boolean allowEthereal) {
        this.allowEthereal = allowEthereal;
    }

    public Boolean getAllowExhaust() {
        return allowExhaust;
    }

    public void setAllowExhaust(Boolean allowExhaust) {
        this.allowExhaust = allowExhaust;
    }
}
