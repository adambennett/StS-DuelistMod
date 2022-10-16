package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import basemod.ModLabeledToggleButton;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import duelistmod.DuelistMod;
import duelistmod.ui.configMenu.SpecificConfigMenuPage;

import java.util.ArrayList;

public class CardPool extends SpecificConfigMenuPage {

    public CardPool() {
        super("Card Pool Settings");
    }

    public ArrayList<IUIElement> getElements() {
        String toonString = DuelistMod.Config_UI_String.TEXT[0];
        String creatorString = DuelistMod.Config_UI_String.TEXT[11];
        ArrayList<IUIElement> settingElements = new ArrayList<>();
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
        settingElements.add(new ModLabeledToggleButton(creatorString, DuelistMod.xLabPos + DuelistMod.xSecondCol, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.creatorBtnBool, DuelistMod.settingsPanel, (label) -> {}, (button) ->
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
        return settingElements;
    }
}
