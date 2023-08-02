package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import basemod.ModLabel;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import duelistmod.DuelistMod;
import duelistmod.enums.CardPoolType;
import duelistmod.helpers.Util;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.DuelistLabeledToggleButton;
import duelistmod.ui.configMenu.SpecificConfigMenuPage;
import duelistmod.variables.Strings;

import java.util.ArrayList;

public class CardPool extends SpecificConfigMenuPage {

    public CardPool() {
        super("Card Pool Settings", "Card Pool");
    }

    public ArrayList<IUIElement> getElements() {
        String toonString = DuelistMod.Config_UI_String.TEXT[0];
        String creatorString = DuelistMod.Config_UI_String.TEXT[11];
        String exodiaString = DuelistMod.Config_UI_String.TEXT[1];
        String ojamaString = DuelistMod.Config_UI_String.TEXT[2];
        String setString = DuelistMod.Config_UI_String.TEXT[4];
        ArrayList<IUIElement> settingElements = new ArrayList<>();

        String tooltip = "Allows booster pack rewards to appear as combat rewards.";
        settingElements.add(new DuelistLabeledToggleButton(Strings.configAllowBoosters,tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.allowBoosters, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.allowBoosters = button.enabled;
            Util.updateSelectScreenRelicList();
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_ALLOW_BOOSTERS, DuelistMod.allowBoosters);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        tooltip = "Forces booster pack rewards to appear in all combat rewards.";
        settingElements.add(new DuelistLabeledToggleButton(Strings.configAlwaysBoosters,tooltip,(DuelistMod.xLabPos + DuelistMod.xSecondCol), DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.alwaysBoosters, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.alwaysBoosters = button.enabled;
            Util.updateSelectScreenRelicList();
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_ALWAYS_BOOSTERS, DuelistMod.alwaysBoosters);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        tooltip = "Removes normal card rewards from all combat rewards. NL NL Ideally this would be used alongside the option 'Always Boosters' but this is not required.";
        settingElements.add(new DuelistLabeledToggleButton(Strings.configRemoveCards,tooltip,(DuelistMod.xLabPos + DuelistMod.xSecondCol + DuelistMod.xThirdCol - 85), DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.removeCardRewards, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.removeCardRewards = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_REMOVE_CARD_REWARDS, DuelistMod.removeCardRewards);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        LINEBREAK();

        tooltip = "Adds a curated set of base game cards to the reward pool for each deck.";
        settingElements.add(new DuelistLabeledToggleButton(Strings.allowBaseGameCards,tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.baseGameCards, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.baseGameCards = button.enabled;
            //shouldFill = true;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_BASE_GAME_CARDS, DuelistMod.baseGameCards);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        tooltip = "Reduces the amount of cards that appear in the 'Basic' reward pool. NL NL Basic rewards appear in lieu of colorless rewards in places where they would normally appear, i.e. the bottom two cards for sale in the shop.";
        settingElements.add(new DuelistLabeledToggleButton("Reduced Basic Set",tooltip, (DuelistMod.xLabPos + DuelistMod.xSecondCol), DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.smallBasicSet, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.smallBasicSet = button.enabled;
            //shouldFill = true;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_SMALL_BASIC, DuelistMod.smallBasicSet);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        LINEBREAK();

        tooltip = "Removes all #yToon cards from all reward pools, except when playing with the #yToon Deck.";
        settingElements.add(new DuelistLabeledToggleButton(toonString,tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.toonBtnBool, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.toonBtnBool = button.enabled;
            //shouldFill = true;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_TOON_BTN, DuelistMod.toonBtnBool);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        tooltip = "Removes both #yThe #yCreator and #yDark #yCreator cards from the reward pool for each deck where they appear.";
        settingElements.add(new DuelistLabeledToggleButton(creatorString, tooltip,(DuelistMod.xLabPos + DuelistMod.xSecondCol), DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.creatorBtnBool, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.creatorBtnBool = button.enabled;
            //shouldFill = true;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_CREATOR_BTN, DuelistMod.creatorBtnBool);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        LINEBREAK();

        tooltip = "Removes all #yExodia cards from all reward pools.";
        settingElements.add(new DuelistLabeledToggleButton(exodiaString,tooltip, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.exodiaBtnBool, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.exodiaBtnBool = button.enabled;
            //shouldFill = true;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_EXODIA_BTN, DuelistMod.exodiaBtnBool);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        tooltip = "Removes all #yOjama cards from all reward pools.";
        settingElements.add(new DuelistLabeledToggleButton(ojamaString, tooltip,(DuelistMod.xLabPos + DuelistMod.xSecondCol), DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.ojamaBtnBool, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.ojamaBtnBool = button.enabled;
            //shouldFill = true;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_OJAMA_BTN, DuelistMod.ojamaBtnBool);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        LINEBREAK();

        tooltip = "When enabled, cards that appear in your starting deck are allowed to appear in the reward pool. Disabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Allow cards from starting decks in pool",tooltip, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.isAllowStartingDeckCardsInPool, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.isAllowStartingDeckCardsInPool = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool("isAllowStartingDeckCardsInPool", DuelistMod.isAllowStartingDeckCardsInPool);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        LINEBREAK();

        tooltip = "When enabled, all Dinosaur-related cards will be removed from the Dragon Deck card pool. Disabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Remove Dinosaurs from Dragon Pool",tooltip, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.isRemoveDinosaursFromDragonPool, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.isRemoveDinosaursFromDragonPool = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool("isRemoveDinosaursFromDragonPool", DuelistMod.isRemoveDinosaursFromDragonPool);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        LINEBREAK();
        LINEBREAK();

        settingElements.add(new ModLabel(setString, DuelistMod.xLabPos, DuelistMod.yPos,DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> sets = new ArrayList<>();
        for (CardPoolType t : CardPoolType.values()) {
            sets.add(t.getDisplay());
        }
        tooltip = "Modifies the logic used to fill the card reward pool. Using the default option is recommended for the most 'balanced' experience. Some of the other options may prevent tier scores from loading properly.";
        DuelistDropdown setSelector = new DuelistDropdown(tooltip, sets, Settings.scale * (DuelistMod.xLabPos + DuelistMod.xSecondCol), Settings.scale * (DuelistMod.yPos + 24), (s, i) -> {
            DuelistMod.cardPoolType = CardPoolType.menuMappingReverse.get(i);
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt(DuelistMod.PROP_CARD_POOL_TYPE, i);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        setSelector.setSelectedIndex(CardPoolType.menuMapping.get(DuelistMod.cardPoolType));
        settingElements.add(setSelector);

        return settingElements;
    }
}
