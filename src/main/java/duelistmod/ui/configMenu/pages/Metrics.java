package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import basemod.ModLabel;
import basemod.ModLabeledButton;
import basemod.ModLabeledToggleButton;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import duelistmod.DuelistMod;
import duelistmod.ui.configMenu.SpecificConfigMenuPage;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;

public class Metrics extends SpecificConfigMenuPage {

    public Metrics() {
        super("Metrics Settings");
    }

    public ArrayList<IUIElement> getElements() {
        ArrayList<IUIElement> settingElements = new ArrayList<>();

        settingElements.add(new ModLabeledToggleButton("Show tier scores", DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.tierScoresEnabled, DuelistMod.settingsPanel,  (label) -> {}, (button) ->
        {
            DuelistMod.tierScoresEnabled = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_TIER_SCORES_ENABLED, DuelistMod.tierScoresEnabled);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        lineBreak();

        settingElements.add(new ModLabeledToggleButton("Score buttons open metrics site", DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.webButtonsEnabled, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.webButtonsEnabled = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_WEB_BUTTONS, DuelistMod.webButtonsEnabled);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }
        }));

        lineBreak();

        settingElements.add(new ModLabeledToggleButton("Upload country & language with metric data", DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.allowLocaleUpload, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.allowLocaleUpload = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool("allowLocaleUpload", DuelistMod.allowLocaleUpload);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        lineBreak();
        lineBreak();
        lineBreak();

        settingElements.add(new ModLabel("Metrics UUID: " + DuelistMod.metricsUUID, DuelistMod.xLabPos, DuelistMod.yPos,DuelistMod.settingsPanel,(me)->{}));
        settingElements.add(new ModLabeledButton("Copy", DuelistMod.xLabPos + DuelistMod.xSecondCol + DuelistMod.xThirdCol + 50, DuelistMod.yPos, DuelistMod.settingsPanel, (element)->{
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(DuelistMod.metricsUUID), null);
        }));

        return settingElements;
    }
}
