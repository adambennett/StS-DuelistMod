package duelistmod.persistence.data;

import duelistmod.DuelistMod;
import duelistmod.enums.DataCategoryType;
import duelistmod.enums.SpecialSparksStrategy;
import duelistmod.persistence.DataDifferenceDTO;
import duelistmod.persistence.PersistentDuelistData;
import duelistmod.ui.configMenu.pages.Gameplay;

import java.util.LinkedHashSet;

public class GameplaySettings extends DataCategory {

    private Boolean enemyDuelists = true;
    private Boolean holidayCards = true;
    private Boolean duelistEvents = true;
    private Boolean unlockAllDecks = false;
    private Boolean duelistCurses = true;
    private Boolean unlockChallengeMode = false;
    private Boolean orbPotions = true;
    private Boolean cardPoolRelics = false;
    private Boolean quickTimeEvents = false;
    private Boolean restrictSummonZones = false;
    private Boolean allowSpecialSparks = true;
    private Boolean forceSpecialSparks = false;
    private String sparksStrategy = SpecialSparksStrategy.RANDOM.displayName();
    private Integer orbSlots = 0;

    private void callSetters() {
        this.setSparksStrategy(sparksStrategy);
    }

    public GameplaySettings() {
        this.category = "Gameplay Settings";
        this.type = DataCategoryType.Config_Setting;
        this.callSetters();
    }

    public GameplaySettings(GameplaySettings from) {
        this(from.enemyDuelists,
             from.holidayCards,
                from.duelistEvents,
                from.unlockAllDecks,
                from.duelistCurses,
                from.unlockChallengeMode,
                from.orbPotions,
                from.cardPoolRelics,
                from.quickTimeEvents,
                from.restrictSummonZones,
                from.allowSpecialSparks,
                from.forceSpecialSparks,
                from.sparksStrategy,
                from.orbSlots);
    }

    public GameplaySettings(Boolean enemyDuelists,
                            Boolean holidayCards,
                            Boolean duelistEvents,
                            Boolean unlockAllDecks,
                            Boolean duelistCurses,
                            Boolean unlockChallengeMode,
                            Boolean orbPotions,
                            Boolean cardPoolRelics,
                            Boolean quickTimeEvents,
                            Boolean restrictSummonZones,
                            Boolean allowSpecialSparks,
                            Boolean forceSpecialSparks,
                            String sparksStrategy,
                            Integer orbSlots) {
        this();
        this.enemyDuelists = PersistentDuelistData.validateBool(enemyDuelists);
        this.holidayCards = PersistentDuelistData.validateBool(holidayCards);
        this.duelistEvents = PersistentDuelistData.validateBool(duelistEvents);
        this.unlockAllDecks = PersistentDuelistData.validateBool(unlockAllDecks, false);
        this.duelistCurses = PersistentDuelistData.validateBool(duelistCurses);
        this.unlockChallengeMode = PersistentDuelistData.validateBool(unlockChallengeMode, false);
        this.orbPotions = PersistentDuelistData.validateBool(orbPotions);
        this.cardPoolRelics = PersistentDuelistData.validateBool(cardPoolRelics, false);
        this.quickTimeEvents = PersistentDuelistData.validateBool(quickTimeEvents, false);
        this.restrictSummonZones = PersistentDuelistData.validateBool(restrictSummonZones, false);
        this.allowSpecialSparks = PersistentDuelistData.validateBool(allowSpecialSparks);
        this.forceSpecialSparks = PersistentDuelistData.validateBool(forceSpecialSparks, false);
        this.sparksStrategy = PersistentDuelistData.validate(sparksStrategy, SpecialSparksStrategy.RANDOM.displayName(), SpecialSparksStrategy.displayNames);
        this.orbSlots = PersistentDuelistData.validate(orbSlots, 0, Gameplay.orbSlotOptions);
        this.callSetters();
    }

    @Override
    public LinkedHashSet<DataDifferenceDTO<?>> generateMetricsDifferences(PersistentDuelistData defaultSettings, PersistentDuelistData playerSettings) {
        LinkedHashSet<DataDifferenceDTO<?>> output = new LinkedHashSet<>();
        if (!defaultSettings.GameplaySettings.enemyDuelists.equals(playerSettings.GameplaySettings.enemyDuelists)) {
            output.add(new DataDifferenceDTO<>(this, "Enemy Duelists", defaultSettings.GameplaySettings.enemyDuelists, playerSettings.GameplaySettings.enemyDuelists));
        }
        if (!defaultSettings.GameplaySettings.duelistEvents.equals(playerSettings.GameplaySettings.duelistEvents)) {
            output.add(new DataDifferenceDTO<>(this, "Duelist Events", defaultSettings.GameplaySettings.duelistEvents, playerSettings.GameplaySettings.duelistEvents));
        }
        if (!defaultSettings.GameplaySettings.duelistCurses.equals(playerSettings.GameplaySettings.duelistCurses)) {
            output.add(new DataDifferenceDTO<>(this, "Duelist Curses", defaultSettings.GameplaySettings.duelistCurses, playerSettings.GameplaySettings.duelistCurses));
        }
        if (!defaultSettings.GameplaySettings.orbPotions.equals(playerSettings.GameplaySettings.orbPotions)) {
            output.add(new DataDifferenceDTO<>(this, "Orb Potions", defaultSettings.GameplaySettings.orbPotions, playerSettings.GameplaySettings.orbPotions));
        }
        if (!defaultSettings.GameplaySettings.restrictSummonZones.equals(playerSettings.GameplaySettings.restrictSummonZones)) {
            output.add(new DataDifferenceDTO<>(this, "Restrict Summoning to Available Zones", defaultSettings.GameplaySettings.restrictSummonZones, playerSettings.GameplaySettings.restrictSummonZones));
        }
        if (!defaultSettings.GameplaySettings.allowSpecialSparks.equals(playerSettings.GameplaySettings.allowSpecialSparks)) {
            output.add(new DataDifferenceDTO<>(this, "Allow Special Sparks", defaultSettings.GameplaySettings.allowSpecialSparks, playerSettings.GameplaySettings.allowSpecialSparks));
        }
        if (!defaultSettings.GameplaySettings.forceSpecialSparks.equals(playerSettings.GameplaySettings.forceSpecialSparks)) {
            output.add(new DataDifferenceDTO<>(this, "Force Special Sparks", defaultSettings.GameplaySettings.forceSpecialSparks, playerSettings.GameplaySettings.forceSpecialSparks));
        }
        if (!defaultSettings.GameplaySettings.sparksStrategy.equals(playerSettings.GameplaySettings.sparksStrategy)) {
            output.add(new DataDifferenceDTO<>(this, "Special Sparks Strategy", defaultSettings.GameplaySettings.sparksStrategy, playerSettings.GameplaySettings.sparksStrategy));
        }
        if (!defaultSettings.GameplaySettings.orbSlots.equals(playerSettings.GameplaySettings.orbSlots)) {
            output.add(new DataDifferenceDTO<>(this, "Extra Orb Slots", defaultSettings.GameplaySettings.orbSlots, playerSettings.GameplaySettings.orbSlots));
        }
        return output;
    }

    public Boolean getEnemyDuelists() {
        return enemyDuelists;
    }

    public void setEnemyDuelists(Boolean enemyDuelists) {
        this.enemyDuelists = enemyDuelists;
    }

    public Boolean getHolidayCards() {
        return holidayCards;
    }

    public void setHolidayCards(Boolean holidayCards) {
        this.holidayCards = holidayCards;
    }

    public Boolean getDuelistEvents() {
        return duelistEvents;
    }

    public void setDuelistEvents(Boolean duelistEvents) {
        this.duelistEvents = duelistEvents;
    }

    public Boolean getUnlockAllDecks() {
        return unlockAllDecks;
    }

    public void setUnlockAllDecks(Boolean unlockAllDecks) {
        this.unlockAllDecks = unlockAllDecks;
    }

    public Boolean getDuelistCurses() {
        return duelistCurses;
    }

    public void setDuelistCurses(Boolean duelistCurses) {
        this.duelistCurses = duelistCurses;
    }

    public Boolean getUnlockChallengeMode() {
        return unlockChallengeMode;
    }

    public void setUnlockChallengeMode(Boolean unlockChallengeMode) {
        this.unlockChallengeMode = unlockChallengeMode;
    }

    public Boolean getOrbPotions() {
        return orbPotions;
    }

    public void setOrbPotions(Boolean orbPotions) {
        this.orbPotions = orbPotions;
    }

    public Boolean getCardPoolRelics() {
        return cardPoolRelics;
    }

    public void setCardPoolRelics(Boolean cardPoolRelics) {
        this.cardPoolRelics = cardPoolRelics;
    }

    public Boolean getQuickTimeEvents() {
        return quickTimeEvents;
    }

    public void setQuickTimeEvents(Boolean quickTimeEvents) {
        this.quickTimeEvents = quickTimeEvents;
    }

    public Boolean getRestrictSummonZones() {
        return restrictSummonZones;
    }

    public void setRestrictSummonZones(Boolean restrictSummonZones) {
        this.restrictSummonZones = restrictSummonZones;
    }

    public Boolean getAllowSpecialSparks() {
        return allowSpecialSparks;
    }

    public void setAllowSpecialSparks(Boolean allowSpecialSparks) {
        this.allowSpecialSparks = allowSpecialSparks;
    }

    public Boolean getForceSpecialSparks() {
        return forceSpecialSparks;
    }

    public void setForceSpecialSparks(Boolean forceSpecialSparks) {
        this.forceSpecialSparks = forceSpecialSparks;
    }

    public String getSparksStrategy() {
        return sparksStrategy;
    }

    public void setSparksStrategy(String sparksStrategy) {
        this.sparksStrategy = sparksStrategy;
        DuelistMod.selectedSparksStrategy = SpecialSparksStrategy.displayNameMapping.getOrDefault(sparksStrategy, SpecialSparksStrategy.RANDOM);
    }

    public Integer getOrbSlots() {
        return orbSlots != null ? orbSlots : 0;
    }

    public void setOrbSlots(Integer orbSlots) {
        this.orbSlots = orbSlots;
    }
}
