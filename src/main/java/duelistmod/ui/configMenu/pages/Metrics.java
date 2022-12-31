package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import basemod.ModLabel;
import basemod.ModLabeledButton;
import basemod.ModLabeledToggleButton;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import duelistmod.DuelistMod;
import duelistmod.ui.configMenu.DuelistLabeledToggleButton;
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

        String tooltip = "When enabled, tier scores appear under cards in card reward screens. NL NL Tier scores should be considered a guide for how well a card performs on average when picked during the current act with the current deck.";
        settingElements.add(new DuelistLabeledToggleButton("Show tier scores",tooltip, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.tierScoresEnabled, DuelistMod.settingsPanel,  (label) -> {}, (button) ->
        {
            DuelistMod.tierScoresEnabled = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_TIER_SCORES_ENABLED, DuelistMod.tierScoresEnabled);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        LINEBREAK();

        tooltip = "When enabled, clicking on the tier score buttons will open the metrics site directly to the full list of cards (and scores) for your current deck.";
        settingElements.add(new DuelistLabeledToggleButton("Score buttons open metrics site",tooltip, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.webButtonsEnabled, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.webButtonsEnabled = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_WEB_BUTTONS, DuelistMod.webButtonsEnabled);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }
        }));

        LINEBREAK();

        tooltip = "When enabled, your run data will be submitted to the metrics server with your country and system language.";
        settingElements.add(new DuelistLabeledToggleButton("Upload country & language with metric data",tooltip, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.allowLocaleUpload, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.allowLocaleUpload = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool("allowLocaleUpload", DuelistMod.allowLocaleUpload);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        LINEBREAK();
        LINEBREAK();
        LINEBREAK();

        settingElements.add(new ModLabel("Metrics UUID: " + DuelistMod.metricsUUID, DuelistMod.xLabPos, DuelistMod.yPos,DuelistMod.settingsPanel,(me)->{}));
        settingElements.add(new ModLabeledButton("Copy", DuelistMod.xLabPos + DuelistMod.xSecondCol + DuelistMod.xThirdCol + 50, DuelistMod.yPos - 25, DuelistMod.settingsPanel, (element)->{
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(DuelistMod.metricsUUID), null);
        }));

        return settingElements;
    }
}
