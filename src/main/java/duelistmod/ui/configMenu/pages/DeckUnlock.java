package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import basemod.ModLabel;
import basemod.ModLabeledToggleButton;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import duelistmod.DuelistMod;
import duelistmod.enums.DeckUnlockRate;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.SpecificConfigMenuPage;

import java.util.ArrayList;

public class DeckUnlock extends SpecificConfigMenuPage {

    public DeckUnlock() {
        super("Deck Unlock Settings");
    }

    public ArrayList<IUIElement> getElements() {
        ArrayList<IUIElement> settingElements = new ArrayList<>();

        settingElements.add(new ModLabel("Deck Unlock Rate", DuelistMod.xLabPos, DuelistMod.yPos,DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> rates = new ArrayList<>();
        for (DeckUnlockRate val : DeckUnlockRate.values()) {
            rates.add(val.displayText());
        }
        DuelistDropdown unlockRateSelector = new DuelistDropdown("Allows you to modify the amount of score you receive after each run to allow for slower or faster deck unlocks. Two types of options - either modify ALL points received after the run, or only modify the points you receive from Duelist-specific scoring events (such as the points you receive for number of summons during the run).", rates, Settings.scale * (DuelistMod.xLabPos + DuelistMod.xSecondCol), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.deckUnlockRateIndex = i;
            DuelistMod.currentUnlockRate = DeckUnlockRate.menuMapping.get(i);
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt(DuelistMod.PROP_DECK_UNLOCK_RATE, DuelistMod.deckUnlockRateIndex);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }
        });
        unlockRateSelector.setSelectedIndex(DuelistMod.deckUnlockRateIndex);

        lineBreak();
        lineBreak();

        settingElements.add(new ModLabeledToggleButton("Hide 'Unlock All Decks' button in character select screen", DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.hideUnlockAllDecksButtonInCharacterSelect, DuelistMod.settingsPanel,  (label) -> {}, (button) ->
        {
            DuelistMod.hideUnlockAllDecksButtonInCharacterSelect = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_HIDE_UNLOCK_ALL_DECKS_BTN, DuelistMod.hideUnlockAllDecksButtonInCharacterSelect);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        settingElements.add(unlockRateSelector);

        return settingElements;
    }
}
