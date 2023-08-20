package duelistmod.persistence.data;

import duelistmod.enums.CardPoolType;
import duelistmod.enums.DataCategoryType;
import duelistmod.persistence.DataDifferenceDTO;
import duelistmod.persistence.PersistentDuelistData;

import java.util.LinkedHashSet;

public class CardPoolSettings extends DataCategory {

    private Boolean allowBoosters = false;
    private Boolean alwaysBoosters = false;
    private Boolean removeCardRewards = false;
    private Boolean baseGameCards = false;
    private Boolean smallBasicSet = false;
    private Boolean removeToons = false;
    private Boolean removeCreator = false;
    private Boolean removeExodia = false;
    private Boolean removeOjama = false;
    private Boolean allowStartingDeckCardsInPool = false;
    private Boolean removeDinosaurs = false;
    private String cardPoolType = CardPoolType.DECK_BASIC_DEFAULT.getDisplay();

    public CardPoolSettings() {
        this.category = "Card Pool Settings";
        this.type = DataCategoryType.Config_Setting;
    }

    public CardPoolSettings(CardPoolSettings from) {
        this(from.allowBoosters,
                from.alwaysBoosters,
                from.removeCardRewards,
                from.baseGameCards,
                from.smallBasicSet,
                from.removeToons,
                from.removeCreator,
                from.removeExodia,
                from.removeOjama,
                from.allowStartingDeckCardsInPool,
                from.removeDinosaurs,
                from.cardPoolType);
    }

    public CardPoolSettings(Boolean allowBoosters,
                            Boolean alwaysBoosters,
                            Boolean removeCardRewards,
                            Boolean baseGameCards,
                            Boolean smallBasicSet,
                            Boolean removeToons,
                            Boolean removeCreator,
                            Boolean removeExodia,
                            Boolean removeOjama,
                            Boolean allowStartingDeckCardsInPool,
                            Boolean removeDinosaurs,
                            String cardPoolType) {
        this();
        this.allowBoosters = PersistentDuelistData.validateBool(allowBoosters, false);
        this.alwaysBoosters = PersistentDuelistData.validateBool(alwaysBoosters, false);
        this.removeCardRewards = PersistentDuelistData.validateBool(removeCardRewards, false);
        this.baseGameCards = PersistentDuelistData.validateBool(baseGameCards, false);
        this.smallBasicSet = PersistentDuelistData.validateBool(smallBasicSet, false);
        this.removeToons = PersistentDuelistData.validateBool(removeToons, false);
        this.removeCreator = PersistentDuelistData.validateBool(removeCreator, false);
        this.removeExodia = PersistentDuelistData.validateBool(removeExodia, false);
        this.removeOjama = PersistentDuelistData.validateBool(removeOjama, false);
        this.allowStartingDeckCardsInPool = PersistentDuelistData.validateBool(allowStartingDeckCardsInPool, false);
        this.removeDinosaurs = PersistentDuelistData.validateBool(removeDinosaurs, false);
        this.cardPoolType = PersistentDuelistData.validate(cardPoolType, CardPoolType.DECK_BASIC_DEFAULT.getDisplay(), CardPoolType.displayNames);
    }

    @Override
    public LinkedHashSet<DataDifferenceDTO<?>> generateMetricsDifferences(PersistentDuelistData defaultSettings, PersistentDuelistData playerSettings) {
        LinkedHashSet<DataDifferenceDTO<?>> output = new LinkedHashSet<>();
        if (!defaultSettings.CardPoolSettings.allowBoosters.equals(playerSettings.CardPoolSettings.allowBoosters)) {
            output.add(new DataDifferenceDTO<>(this, "Allow Booster Packs", defaultSettings.CardPoolSettings.allowBoosters, playerSettings.CardPoolSettings.allowBoosters));
        }
        if (!defaultSettings.CardPoolSettings.alwaysBoosters.equals(playerSettings.CardPoolSettings.alwaysBoosters)) {
            output.add(new DataDifferenceDTO<>(this, "Always Booster Packs", defaultSettings.CardPoolSettings.alwaysBoosters, playerSettings.CardPoolSettings.alwaysBoosters));
        }
        if (!defaultSettings.CardPoolSettings.removeCardRewards.equals(playerSettings.CardPoolSettings.removeCardRewards)) {
            output.add(new DataDifferenceDTO<>(this, "Remove Card Rewards", defaultSettings.CardPoolSettings.removeCardRewards, playerSettings.CardPoolSettings.removeCardRewards));
        }
        if (!defaultSettings.CardPoolSettings.smallBasicSet.equals(playerSettings.CardPoolSettings.smallBasicSet)) {
            output.add(new DataDifferenceDTO<>(this, "Reduced Basic Set", defaultSettings.CardPoolSettings.smallBasicSet, playerSettings.CardPoolSettings.smallBasicSet));
        }
        if (!defaultSettings.CardPoolSettings.removeToons.equals(playerSettings.CardPoolSettings.removeToons)) {
            output.add(new DataDifferenceDTO<>(this, "Remove Toons", defaultSettings.CardPoolSettings.removeToons, playerSettings.CardPoolSettings.removeToons));
        }
        if (!defaultSettings.CardPoolSettings.removeCreator.equals(playerSettings.CardPoolSettings.removeCreator)) {
            output.add(new DataDifferenceDTO<>(this, "Remove Creator", defaultSettings.CardPoolSettings.removeCreator, playerSettings.CardPoolSettings.removeCreator));
        }
        if (!defaultSettings.CardPoolSettings.removeExodia.equals(playerSettings.CardPoolSettings.removeExodia)) {
            output.add(new DataDifferenceDTO<>(this, "Remove Exodia", defaultSettings.CardPoolSettings.removeExodia, playerSettings.CardPoolSettings.removeExodia));
        }
        if (!defaultSettings.CardPoolSettings.removeOjama.equals(playerSettings.CardPoolSettings.removeOjama)) {
            output.add(new DataDifferenceDTO<>(this, "Remove Ojama", defaultSettings.CardPoolSettings.removeOjama, playerSettings.CardPoolSettings.removeOjama));
        }
        if (!defaultSettings.CardPoolSettings.allowStartingDeckCardsInPool.equals(playerSettings.CardPoolSettings.allowStartingDeckCardsInPool)) {
            output.add(new DataDifferenceDTO<>(this, "Allow cards from starting decks in pool", defaultSettings.CardPoolSettings.allowStartingDeckCardsInPool, playerSettings.CardPoolSettings.allowStartingDeckCardsInPool));
        }
        if (!defaultSettings.CardPoolSettings.removeDinosaurs.equals(playerSettings.CardPoolSettings.removeDinosaurs)) {
            output.add(new DataDifferenceDTO<>(this, "Remove Dinosaurs from Dragon Pool", defaultSettings.CardPoolSettings.removeDinosaurs, playerSettings.CardPoolSettings.removeDinosaurs));
        }
        if (!defaultSettings.CardPoolSettings.cardPoolType.equals(playerSettings.CardPoolSettings.cardPoolType)) {
            output.add(new DataDifferenceDTO<>(this, "Reward/Shop Pool Fill Type", defaultSettings.CardPoolSettings.cardPoolType, playerSettings.CardPoolSettings.cardPoolType));
        }
        return output;
    }

    public Boolean getAllowBoosters() {
        return allowBoosters;
    }

    public void setAllowBoosters(Boolean allowBoosters) {
        this.allowBoosters = allowBoosters;
    }

    public Boolean getAlwaysBoosters() {
        return alwaysBoosters;
    }

    public void setAlwaysBoosters(Boolean alwaysBoosters) {
        this.alwaysBoosters = alwaysBoosters;
    }

    public Boolean getRemoveCardRewards() {
        return removeCardRewards;
    }

    public void setRemoveCardRewards(Boolean removeCardRewards) {
        this.removeCardRewards = removeCardRewards;
    }

    public Boolean getAnyBoosterOption() {
        return this.allowBoosters || this.alwaysBoosters || this.removeCardRewards;
    }

    public Boolean getBaseGameCards() {
        return baseGameCards;
    }

    public void setBaseGameCards(Boolean baseGameCards) {
        this.baseGameCards = baseGameCards;
    }

    public Boolean getSmallBasicSet() {
        return smallBasicSet;
    }

    public void setSmallBasicSet(Boolean smallBasicSet) {
        this.smallBasicSet = smallBasicSet;
    }

    public Boolean getRemoveToons() {
        return removeToons;
    }

    public void setRemoveToons(Boolean removeToons) {
        this.removeToons = removeToons;
    }

    public Boolean getRemoveCreator() {
        return removeCreator;
    }

    public void setRemoveCreator(Boolean removeCreator) {
        this.removeCreator = removeCreator;
    }

    public Boolean getRemoveExodia() {
        return removeExodia;
    }

    public void setRemoveExodia(Boolean removeExodia) {
        this.removeExodia = removeExodia;
    }

    public Boolean getRemoveOjama() {
        return removeOjama;
    }

    public void setRemoveOjama(Boolean removeOjama) {
        this.removeOjama = removeOjama;
    }

    public Boolean getAllowStartingDeckCardsInPool() {
        return allowStartingDeckCardsInPool;
    }

    public void setAllowStartingDeckCardsInPool(Boolean allowStartingDeckCardsInPool) {
        this.allowStartingDeckCardsInPool = allowStartingDeckCardsInPool;
    }

    public Boolean getRemoveDinosaurs() {
        return removeDinosaurs;
    }

    public void setRemoveDinosaurs(Boolean removeDinosaurs) {
        this.removeDinosaurs = removeDinosaurs;
    }

    public String getCardPoolType() {
        return cardPoolType;
    }

    public void setCardPoolType(String cardPoolType) {
        this.cardPoolType = cardPoolType;
    }
}
