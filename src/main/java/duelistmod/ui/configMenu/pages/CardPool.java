package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import basemod.ModLabel;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import duelistmod.DuelistMod;
import duelistmod.enums.CardPoolType;
import duelistmod.helpers.Util;
import duelistmod.persistence.data.CardPoolSettings;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.DuelistLabeledToggleButton;
import duelistmod.ui.configMenu.RefreshablePage;
import duelistmod.ui.configMenu.SpecificConfigMenuPage;
import duelistmod.variables.Strings;

import java.util.ArrayList;

public class CardPool extends SpecificConfigMenuPage implements RefreshablePage {

    private boolean isRefreshing;

    public CardPool() {
        super("Card Pool Settings", "Card Pool");
    }

    public ArrayList<IUIElement> getElements() {
        ArrayList<IUIElement> settingElements = new ArrayList<>();

        String tooltip = "Allows booster pack rewards to appear as combat rewards.";
        settingElements.add(new DuelistLabeledToggleButton(Strings.configAllowBoosters,tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.persistentDuelistData.CardPoolSettings.getAllowBoosters(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setAllowBoosters(button.enabled);
            DuelistMod.configSettingsLoader.save();
            Util.updateSelectScreenRelicList();
        }));

        tooltip = "Forces booster pack rewards to appear in all combat rewards.";
        settingElements.add(new DuelistLabeledToggleButton(Strings.configAlwaysBoosters,tooltip,(DuelistMod.xLabPos + DuelistMod.xSecondCol), DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.persistentDuelistData.CardPoolSettings.getAlwaysBoosters(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setAlwaysBoosters(button.enabled);
            DuelistMod.configSettingsLoader.save();
            Util.updateSelectScreenRelicList();
        }));

        tooltip = "Removes normal card rewards from all combat rewards. NL NL Ideally this would be used alongside the option 'Always Boosters' but this is not required.";
        settingElements.add(new DuelistLabeledToggleButton(Strings.configRemoveCards,tooltip,(DuelistMod.xLabPos + DuelistMod.xSecondCol + DuelistMod.xThirdCol - 85), DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.persistentDuelistData.CardPoolSettings.getRemoveCardRewards(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setRemoveCardRewards(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        LINEBREAK();

        tooltip = "Adds a curated set of base game cards to the reward pool for each deck.";
        settingElements.add(new DuelistLabeledToggleButton(Strings.allowBaseGameCards,tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getBaseGameCards(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setBaseGameCards(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        tooltip = "Reduces the amount of cards that appear in the 'Basic' reward pool. NL NL Basic rewards appear in lieu of colorless rewards in places where they would normally appear, i.e. the bottom two cards for sale in the shop.";
        settingElements.add(new DuelistLabeledToggleButton("Reduced Basic Set",tooltip, (DuelistMod.xLabPos + DuelistMod.xSecondCol), DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getSmallBasicSet(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setSmallBasicSet(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        LINEBREAK();

        tooltip = "When enabled, cards that appear in your starting deck are allowed to appear in the reward pool. Disabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Allow cards from starting decks in pool",tooltip, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getAllowStartingDeckCardsInPool(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setAllowStartingDeckCardsInPool(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        LINEBREAK();

        tooltip = "When enabled, all Dinosaur-related cards will be removed from the Dragon Deck card pool. Disabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Remove Dinosaurs from Dragon Pool",tooltip, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getRemoveDinosaurs(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setRemoveDinosaurs(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        LINEBREAK();

        tooltip = "When enabled, all Toon-related cards will be removed from the Spellcaster Deck card pool. Disabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Remove Toons from Spellcaster Pool",tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getRemoveToons(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setRemoveToons(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        tooltip = "Removes both #yThe #yCreator and #yDark #yCreator cards from the reward pool for each deck where they appear.";
        settingElements.add(new DuelistLabeledToggleButton("Remove Creator cards from all pools", tooltip,(DuelistMod.xLabPos + DuelistMod.xSecondCol + 100), DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont,  settings().getRemoveCreator(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setRemoveCreator(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        LINEBREAK();

        tooltip = "When enabled, all Exodia-related cards will be removed from the Spellcaster Deck card pool. Disabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Remove Exodia cards from Spellcaster Pool",tooltip, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont,  settings().getRemoveExodia(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setRemoveExodia(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        tooltip = "When enabled, all Ojama-related cards will be removed from the Beast Deck card pool. Disabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Remove Ojamas from Beast Pool", tooltip,(DuelistMod.xLabPos + DuelistMod.xSecondCol + 100), DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont,  settings().getRemoveOjama(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setRemoveOjama(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        LINEBREAK();
        LINEBREAK();

        settingElements.add(new ModLabel("Reward/Shop Pool Fill Type", DuelistMod.xLabPos, DuelistMod.yPos,DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> sets = new ArrayList<>();
        for (CardPoolType t : CardPoolType.values()) {
            sets.add(t.getDisplay());
        }
        tooltip = "Modifies the logic used to fill the card reward pool. Using the default option is recommended for the most 'balanced' experience. Some of the other options may prevent tier scores from loading properly.";
        DuelistDropdown setSelector = new DuelistDropdown(tooltip, sets, Settings.scale * (DuelistMod.xLabPos + DuelistMod.xSecondCol), Settings.scale * (DuelistMod.yPos + 24), (s, i) -> {
            settings().setCardPoolType(s);
            DuelistMod.configSettingsLoader.save();
        });
        setSelector.setSelected(settings().getCardPoolType());
        settingElements.add(setSelector);

        this.isRefreshing = false;
        return settingElements;
    }

    @Override
    public void refresh() {
        if (!this.isRefreshing && DuelistMod.paginator != null) {
            this.isRefreshing = true;
            DuelistMod.paginator.refreshPage(this);
        }
    }

    @Override
    public void resetToDefault() {
        DuelistMod.persistentDuelistData.CardPoolSettings = new CardPoolSettings();
    }

    private CardPoolSettings settings() {
        return DuelistMod.persistentDuelistData.CardPoolSettings;
    }
}
