package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import basemod.ModLabeledToggleButton;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import duelistmod.DuelistMod;
import duelistmod.enums.SpecialSparksStrategy;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.SpecificConfigMenuPage;
import duelistmod.variables.Strings;
import java.util.ArrayList;

public class Gameplay extends SpecificConfigMenuPage {

    public Gameplay() {
        super("Gameplay Settings");
    }

    public ArrayList<IUIElement> getElements() {
        ArrayList<IUIElement> settingElements = new ArrayList<>();
        settingElements.add(new ModLabeledToggleButton("Enemy Duelists", DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.duelistMonsters, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.duelistMonsters = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_DUELIST_MONSTERS, DuelistMod.duelistMonsters);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        settingElements.add(new ModLabeledToggleButton("Celebrate Holidays", DuelistMod.xLabPos + DuelistMod.xSecondCol, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.holidayCardsEnabled, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.holidayCardsEnabled = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_CELEBRATE_HOLIDAYS, DuelistMod.holidayCardsEnabled);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        lineBreak();

        settingElements.add(new ModLabeledToggleButton("Duelist Events", DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.allowDuelistEvents, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.allowDuelistEvents = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool("allowDuelistEvents", DuelistMod.allowDuelistEvents);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        lineBreak();

        settingElements.add(new ModLabeledToggleButton("Duelist Curses", DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.duelistCurses, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.duelistCurses = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_DUELIST_CURSES, DuelistMod.duelistCurses);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        lineBreak();

        settingElements.add(new ModLabeledToggleButton("Orb Potions", DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.addOrbPotions, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.addOrbPotions = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_ADD_ORB_POTIONS, DuelistMod.addOrbPotions);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        lineBreak();

        settingElements.add(new ModLabeledToggleButton("Restrict Summoning to Available Zones", DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.restrictSummonZones, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.restrictSummonZones = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_RESTRICT_SUMMONING, DuelistMod.restrictSummonZones);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        lineBreak();

        settingElements.add(new ModLabeledToggleButton("Quick-Time Events", DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.quicktimeEventsAllowed, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.quicktimeEventsAllowed = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool("quicktimeEventsAllowed", DuelistMod.quicktimeEventsAllowed);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        lineBreak();

        settingElements.add(new ModLabeledToggleButton(Strings.forcePuzzleText, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.forcePuzzleSummons, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.forcePuzzleSummons = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_FORCE_PUZZLE, DuelistMod.forcePuzzleSummons);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        lineBreak();

        settingElements.add(new ModLabeledToggleButton("Allow Special Sparks", DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.allowSpecialSparks, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.allowSpecialSparks = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_ALLOW_SPECIAL_SPARKS, DuelistMod.forcePuzzleSummons);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        settingElements.add(new ModLabeledToggleButton("Force Special Sparks", DuelistMod.xLabPos + DuelistMod.xSecondCol, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.forceSpecialSparks, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.forceSpecialSparks = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_FORCE_SPECIAL_SPARKS, DuelistMod.forcePuzzleSummons);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        ArrayList<String> sparksChoices = new ArrayList<>();
        sparksChoices.add("Random (Default)");
        sparksChoices.add("Random - Weighted");
        sparksChoices.add("Golden Sparks");
        sparksChoices.add("Blood Sparks");
        sparksChoices.add("Magic Sparks");
        sparksChoices.add("Storm Sparks");
        sparksChoices.add("Dark Sparks");
        DuelistDropdown sparksStrategy = new DuelistDropdown(sparksChoices, Settings.scale * (DuelistMod.xLabPos + DuelistMod.xSecondCol + DuelistMod.xThirdCol), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.selectedSparksStrategy = SpecialSparksStrategy.menuMappingReverse.get(i);
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt(DuelistMod.PROP_SELECTED_SPARKS_STRATEGY, i);
                config.save();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        settingElements.add(sparksStrategy);

        return settingElements;
    }
}
