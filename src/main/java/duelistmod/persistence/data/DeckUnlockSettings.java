package duelistmod.persistence.data;

import duelistmod.DuelistMod;
import duelistmod.enums.DataCategoryType;
import duelistmod.enums.DeckUnlockRate;
import duelistmod.persistence.DataDifferenceDTO;
import duelistmod.persistence.PersistentDuelistData;

import java.util.LinkedHashSet;

public class DeckUnlockSettings extends DataCategory {

    private String deckUnlockRate = DeckUnlockRate.NORMAL.displayText();
    private Boolean hideUnlockAllDecksButton = false;

    public DeckUnlockSettings() {
        this.category = "Deck Unlock Settings";
        this.type = DataCategoryType.Config_Setting;
    }

    public DeckUnlockSettings(DeckUnlockSettings from) {
        this(from.deckUnlockRate, from.hideUnlockAllDecksButton);
    }

    public DeckUnlockSettings(String deckUnlockRate, Boolean hideUnlockAllDecksButton) {
        this();
        this.hideUnlockAllDecksButton = PersistentDuelistData.validateBool(hideUnlockAllDecksButton, false);
        this.deckUnlockRate = PersistentDuelistData.validate(deckUnlockRate, DeckUnlockRate.NORMAL.displayText(), DeckUnlockRate.displayNames);
    }

    @Override
    public LinkedHashSet<DataDifferenceDTO<?>> generateMetricsDifferences(PersistentDuelistData defaultSettings, PersistentDuelistData playerSettings) {
        return new LinkedHashSet<>();
    }

    public String getDeckUnlockRate() {
        return deckUnlockRate;
    }

    public void setDeckUnlockRate(String deckUnlockRate) {
        this.deckUnlockRate = deckUnlockRate;
        DuelistMod.currentUnlockRate = DeckUnlockRate.displayNameMapping.getOrDefault(deckUnlockRate, DeckUnlockRate.NORMAL);
    }

    public Boolean getHideUnlockAllDecksButton() {
        return hideUnlockAllDecksButton || !DuelistMod.isUnlockAllDecksButtonNeeded();
    }

    public void setHideUnlockAllDecksButton(Boolean hideUnlockAllDecksButton) {
        this.hideUnlockAllDecksButton = hideUnlockAllDecksButton;
    }
}
