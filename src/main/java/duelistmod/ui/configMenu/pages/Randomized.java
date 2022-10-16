package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import basemod.ModLabeledToggleButton;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import duelistmod.DuelistMod;
import duelistmod.ui.configMenu.SpecificConfigMenuPage;

import java.util.ArrayList;

public class Randomized extends SpecificConfigMenuPage {

    public Randomized() {
        super("Randomized Settings");
    }

    public ArrayList<IUIElement> getElements() {

        ArrayList<IUIElement> settingElements = new ArrayList<>();
        String noCostChange = DuelistMod.Config_UI_String.TEXT[14];
        String onlyCostDecrease = DuelistMod.Config_UI_String.TEXT[15];
        String noTributeChange = DuelistMod.Config_UI_String.TEXT[16];
        String onlyTributeDecrease = DuelistMod.Config_UI_String.TEXT[17];
        String noSummonChange = DuelistMod.Config_UI_String.TEXT[18];
        String onlySummonIncrease = DuelistMod.Config_UI_String.TEXT[19];
        String alwaysUpgrade = DuelistMod.Config_UI_String.TEXT[20];
        String neverUpgrade = DuelistMod.Config_UI_String.TEXT[21];
        String allowEthereal = DuelistMod.Config_UI_String.TEXT[22];
        String allowExhaust = DuelistMod.Config_UI_String.TEXT[23];

        float originalXPos = DuelistMod.xLabPos;
        DuelistMod.xLabPos += 180;

        settingElements.add(new ModLabeledToggleButton(noCostChange, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.noCostChanges, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.noCostChanges =  button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_NO_CHANGE_COST, DuelistMod.noCostChanges);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));
        settingElements.add(new ModLabeledToggleButton(onlyCostDecrease, DuelistMod.xLabPos + DuelistMod.xSecondCol, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.onlyCostDecreases, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.onlyCostDecreases = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_ONLY_DEC_COST, DuelistMod.onlyCostDecreases);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));
        
        lineBreak();
        lineBreak();

        settingElements.add(new ModLabeledToggleButton(noTributeChange, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.noTributeChanges, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.noTributeChanges = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_NO_CHANGE_TRIB, DuelistMod.noTributeChanges);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));
        settingElements.add(new ModLabeledToggleButton(onlyTributeDecrease, DuelistMod.xLabPos + DuelistMod.xSecondCol, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.onlyTributeDecreases, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.onlyTributeDecreases = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_ONLY_DEC_TRIB, DuelistMod.onlyTributeDecreases);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));
        
        lineBreak();
        lineBreak();

        settingElements.add(new ModLabeledToggleButton(noSummonChange, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.noSummonChanges, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.noSummonChanges = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_NO_CHANGE_SUMM, DuelistMod.noSummonChanges);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));
        settingElements.add(new ModLabeledToggleButton(onlySummonIncrease, DuelistMod.xLabPos + DuelistMod.xSecondCol, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.onlySummonIncreases, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.onlySummonIncreases = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_ONLY_INC_SUMM, DuelistMod.onlySummonIncreases);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));
        
        lineBreak();
        lineBreak();

        settingElements.add(new ModLabeledToggleButton(alwaysUpgrade, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.alwaysUpgrade, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.alwaysUpgrade = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_ALWAYS_UPGRADE, DuelistMod.alwaysUpgrade);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));
        settingElements.add(new ModLabeledToggleButton(neverUpgrade, DuelistMod.xLabPos + DuelistMod.xSecondCol, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.neverUpgrade, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.neverUpgrade = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_NEVER_UPGRADE, DuelistMod.neverUpgrade);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));
        
        lineBreak();
        lineBreak();

        settingElements.add(new ModLabeledToggleButton(allowEthereal, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.randomizeEthereal, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.randomizeEthereal = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_R_ETHEREAL, DuelistMod.randomizeEthereal);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));
        settingElements.add(new ModLabeledToggleButton(allowExhaust, DuelistMod.xLabPos + DuelistMod.xSecondCol, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.randomizeExhaust, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.randomizeExhaust = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_R_EXHAUST, DuelistMod.randomizeExhaust);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        DuelistMod.xLabPos = originalXPos;

        return settingElements;
    }
}
