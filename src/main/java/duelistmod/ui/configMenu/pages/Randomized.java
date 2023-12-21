package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import duelistmod.DuelistMod;
import duelistmod.persistence.data.RandomizedSettings;
import duelistmod.ui.configMenu.DuelistLabeledToggleButton;
import duelistmod.ui.configMenu.RefreshablePage;
import duelistmod.ui.configMenu.SpecificConfigMenuPage;

import java.util.ArrayList;

public class Randomized extends SpecificConfigMenuPage implements RefreshablePage {

    private boolean isRefreshing;

    public Randomized() {
        super("Randomized Settings", "Randomized");
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

        String tooltip = "When enabled, #yRandomized cards will not experience any changes to their base energy cost.";
        settingElements.add(new DuelistLabeledToggleButton(noCostChange, tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getNoCostChanges(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setNoCostChanges(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        tooltip = "When enabled, the energy cost of all #yRandomized cards will be equal or lower to the card's base energy cost.";
        settingElements.add(new DuelistLabeledToggleButton(onlyCostDecrease, tooltip,(DuelistMod.xLabPos + DuelistMod.xSecondCol), DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getOnlyCostDecreases(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setOnlyCostDecreases(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));
        
        LINEBREAK();
        LINEBREAK();

        tooltip = "When enabled, #yRandomized cards will not experience any changes to their base #yTribute cost.";
        settingElements.add(new DuelistLabeledToggleButton(noTributeChange, tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getNoTributeChanges(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setNoTributeChanges(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));
        tooltip = "When enabled, the #yTribute cost of all #yRandomized cards will be equal or lower to the card's base #yTribute cost.";
        settingElements.add(new DuelistLabeledToggleButton(onlyTributeDecrease, tooltip,(DuelistMod.xLabPos + DuelistMod.xSecondCol), DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getOnlyTributeDecreases(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setOnlyTributeDecreases(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));
        
        LINEBREAK();
        LINEBREAK();

        tooltip = "When enabled, #yRandomized cards will not experience any changes to their base #ySummons.";
        settingElements.add(new DuelistLabeledToggleButton(noSummonChange, tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getNoSummonChanges(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setNoSummonChanges(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        tooltip = "When enabled, the #ySummons of all #yRandomized cards will be equal or higher than the card's base #ySummons.";
        settingElements.add(new DuelistLabeledToggleButton(onlySummonIncrease, tooltip,(DuelistMod.xLabPos + DuelistMod.xSecondCol), DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getOnlySummonIncreases(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setOnlySummonIncreases(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));
        
        LINEBREAK();
        LINEBREAK();

        tooltip = "When enabled, #yRandomized cards will always be upgraded.";
        settingElements.add(new DuelistLabeledToggleButton(alwaysUpgrade, tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getAlwaysUpgrade(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setAlwaysUpgrade(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        tooltip = "When enabled, #yRandomized cards will never be upgraded.";
        settingElements.add(new DuelistLabeledToggleButton(neverUpgrade, tooltip,(DuelistMod.xLabPos + DuelistMod.xSecondCol), DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getNeverUpgrade(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setNeverUpgrade(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));
        
        LINEBREAK();
        LINEBREAK();

        tooltip = "When enabled, #yRandomized cards will occasionally become #yEthereal.";
        settingElements.add(new DuelistLabeledToggleButton(allowEthereal, tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getAllowEthereal(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setAllowEthereal(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        tooltip = "When enabled, #yRandomized cards will occasionally gain #yExhaust.";
        settingElements.add(new DuelistLabeledToggleButton(allowExhaust, tooltip,(DuelistMod.xLabPos + DuelistMod.xSecondCol), DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getAllowExhaust(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setAllowExhaust(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        DuelistMod.xLabPos = originalXPos;

        this.isRefreshing = false;
        return settingElements;
    }

    private RandomizedSettings settings() { return DuelistMod.persistentDuelistData.RandomizedSettings; }

    @Override
    public void resetToDefault() {
        DuelistMod.persistentDuelistData.RandomizedSettings = new RandomizedSettings();
    }

    @Override
    public void refresh() {
        if (!this.isRefreshing && DuelistMod.paginator != null) {
            this.isRefreshing = true;
            DuelistMod.paginator.refreshPage(this);
        }
    }
}
