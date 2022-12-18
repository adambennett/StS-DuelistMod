package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import basemod.ModLabel;
import basemod.ModLabeledToggleButton;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import duelistmod.DuelistMod;
import duelistmod.enums.DropdownMenuType;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.SpecificConfigMenuPage;
import duelistmod.variables.Strings;

import java.util.ArrayList;

public class CardPool extends SpecificConfigMenuPage {

    public CardPool() {
        super("Card Pool Settings");
    }

    public ArrayList<IUIElement> getElements() {
        String toonString = DuelistMod.Config_UI_String.TEXT[0];
        String creatorString = DuelistMod.Config_UI_String.TEXT[11];
        String exodiaString = DuelistMod.Config_UI_String.TEXT[1];
        String ojamaString = DuelistMod.Config_UI_String.TEXT[2];
        String setString = DuelistMod.Config_UI_String.TEXT[4];
        ArrayList<IUIElement> settingElements = new ArrayList<>();

        settingElements.add(new ModLabeledToggleButton(Strings.configAllowBoosters,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.allowBoosters, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.allowBoosters = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_ALLOW_BOOSTERS, DuelistMod.allowBoosters);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        settingElements.add(new ModLabeledToggleButton(Strings.configAlwaysBoosters,(DuelistMod.xLabPos + DuelistMod.xSecondCol), DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.alwaysBoosters, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.alwaysBoosters = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_ALWAYS_BOOSTERS, DuelistMod.alwaysBoosters);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        lineBreak();

        settingElements.add(new ModLabeledToggleButton(Strings.allowBaseGameCards,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.baseGameCards, DuelistMod.settingsPanel, (label) -> {}, (button) ->
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

        settingElements.add(new ModLabeledToggleButton("Reduced Basic Set", (DuelistMod.xLabPos + DuelistMod.xSecondCol), DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.smallBasicSet, DuelistMod.settingsPanel, (label) -> {}, (button) ->
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

        lineBreak();

        settingElements.add(new ModLabeledToggleButton(toonString,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.toonBtnBool, DuelistMod.settingsPanel, (label) -> {}, (button) ->
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

        settingElements.add(new ModLabeledToggleButton(creatorString, (DuelistMod.xLabPos + DuelistMod.xSecondCol), DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.creatorBtnBool, DuelistMod.settingsPanel, (label) -> {}, (button) ->
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

        lineBreak();

        settingElements.add(new ModLabeledToggleButton(exodiaString, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.exodiaBtnBool, DuelistMod.settingsPanel, (label) -> {}, (button) ->
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

        settingElements.add(new ModLabeledToggleButton(ojamaString, (DuelistMod.xLabPos + DuelistMod.xSecondCol), DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.ojamaBtnBool, DuelistMod.settingsPanel, (label) -> {}, (button) ->
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

        lineBreak();
        lineBreak();
        lineBreak();

        settingElements.add(new ModLabel(setString, DuelistMod.xLabPos, DuelistMod.yPos,DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> sets = new ArrayList<>();
        for (String set : DuelistMod.cardSets) {
            sets.add(set);
        }
        DuelistDropdown setSelector = new DuelistDropdown(sets, Settings.scale * (DuelistMod.xLabPos + DuelistMod.xSecondCol), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.setIndex = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt(DuelistMod.PROP_SET, DuelistMod.setIndex);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        setSelector.setSelectedIndex(DuelistMod.setIndex);
        settingElements.add(setSelector);

        return settingElements;
    }
}
