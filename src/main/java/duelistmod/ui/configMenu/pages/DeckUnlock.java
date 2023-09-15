package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import basemod.ModLabel;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import duelistmod.DuelistMod;
import duelistmod.enums.DeckUnlockRate;
import duelistmod.persistence.data.DeckUnlockSettings;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.DuelistLabeledToggleButton;
import duelistmod.ui.configMenu.RefreshablePage;
import duelistmod.ui.configMenu.SpecificConfigMenuPage;

import java.util.ArrayList;

public class DeckUnlock extends SpecificConfigMenuPage implements RefreshablePage {

    private boolean isRefreshing;

    public DeckUnlock() {
        super("Deck Unlock Settings", "Deck Unlock");
    }

    public ArrayList<IUIElement> getElements() {
        ArrayList<IUIElement> settingElements = new ArrayList<>();

        settingElements.add(new ModLabel("Deck Unlock Rate", DuelistMod.xLabPos, DuelistMod.yPos,DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> rates = new ArrayList<>();
        for (DeckUnlockRate val : DeckUnlockRate.values()) {
            rates.add(val.displayText());
        }
        String tooltip = "Allows you to modify the amount of score you receive after each run to allow for slower or faster deck unlocks. NL NL Two types of options - either modify ALL points received after the run, or only modify the points you receive from Duelist-specific scoring events (such as the points you receive for number of summons during the run).";
        DuelistDropdown unlockRateSelector = new DuelistDropdown(tooltip, rates, Settings.scale * (DuelistMod.xLabPos + 300), Settings.scale * (DuelistMod.yPos + 24), (s, i) -> {
            settings().setDeckUnlockRate(s);
            DuelistMod.configSettingsLoader.save();
        });
        unlockRateSelector.setSelected(settings().getDeckUnlockRate());

        LINEBREAK();
        LINEBREAK();

        settingElements.add(new DuelistLabeledToggleButton("Hide 'Unlock All Decks' button in character select screen", DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getHideUnlockAllDecksButton(), DuelistMod.settingsPanel,  (label) -> {}, (button) ->
        {
            settings().setHideUnlockAllDecksButton(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));
        settingElements.add(unlockRateSelector);

        this.isRefreshing = false;
        return settingElements;
    }

    @Override
    public void resetToDefault() {
        DuelistMod.persistentDuelistData.DeckUnlockSettings = new DeckUnlockSettings();
    }

    @Override
    public void refresh() {
        if (!this.isRefreshing && DuelistMod.paginator != null) {
            this.isRefreshing = true;
            DuelistMod.paginator.refreshPage(this);
        }
    }

    private DeckUnlockSettings settings() {
        return DuelistMod.persistentDuelistData.DeckUnlockSettings;
    }
}
