package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import basemod.ModLabeledToggleButton;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import duelistmod.DuelistMod;
import duelistmod.enums.SpecialSparksStrategy;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.DuelistLabeledToggleButton;
import duelistmod.ui.configMenu.SpecificConfigMenuPage;
import duelistmod.variables.Strings;
import java.util.ArrayList;

public class Gameplay extends SpecificConfigMenuPage {

    public Gameplay() {
        super("Gameplay Settings");
    }

    public ArrayList<IUIElement> getElements() {
        ArrayList<IUIElement> settingElements = new ArrayList<>();
        String tooltip = "Allow enemy duelists to spawn in Elite encounters.";
        settingElements.add(new DuelistLabeledToggleButton("Enemy Duelists", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.duelistMonsters, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.duelistMonsters = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_DUELIST_MONSTERS, DuelistMod.duelistMonsters);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        tooltip = "Allow Holiday-specific cards to spawn in reward pools and in your starting deck. Includes birthday cards.";
        settingElements.add(new DuelistLabeledToggleButton("Celebrate Holidays",tooltip, DuelistMod.xLabPos + DuelistMod.xSecondCol, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.holidayCardsEnabled, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.holidayCardsEnabled = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_CELEBRATE_HOLIDAYS, DuelistMod.holidayCardsEnabled);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        LINEBREAK();

        tooltip = "Allow custom Duelist events to appear during runs.";
        settingElements.add(new DuelistLabeledToggleButton("Duelist Events",tooltip, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.allowDuelistEvents, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.allowDuelistEvents = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool("allowDuelistEvents", DuelistMod.allowDuelistEvents);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        LINEBREAK();

        tooltip = "Allow Duelist curses to appear during runs. When turned off, if a Duelist curse is generated, it will be replaced with a random base game curse.";
        settingElements.add(new DuelistLabeledToggleButton("Duelist Curses",tooltip, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.duelistCurses, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.duelistCurses = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_DUELIST_CURSES, DuelistMod.duelistCurses);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        LINEBREAK();

        tooltip = "Allow potions that channel orbs to spawn as rewards and in shops.";
        settingElements.add(new DuelistLabeledToggleButton("Orb Potions",tooltip, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.addOrbPotions, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.addOrbPotions = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_ADD_ORB_POTIONS, DuelistMod.addOrbPotions);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        LINEBREAK();

        tooltip = "When enabled, any cards that #ySummon will be unplayable unless you have enough summoning zones available. This should make the game much harder.";
        settingElements.add(new DuelistLabeledToggleButton("Restrict Summoning to Available Zones",tooltip, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.restrictSummonZones, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.restrictSummonZones = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_RESTRICT_SUMMONING, DuelistMod.restrictSummonZones);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        LINEBREAK();

        tooltip = "When enabled, #yTributing a #yMegatype card for another #yMegatype card will launch a Quick-Time Event minigame during combat that determines which random #yTribute synergy effects to run. NL NL Mostly for fun, recommended to not enable this for serious runs. NL NL When disabled, #yMegatype synergy #yTributes will simply trigger one random synergy #yTribute effect.";
        settingElements.add(new DuelistLabeledToggleButton("Quick-Time Events",tooltip, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.quicktimeEventsAllowed, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.quicktimeEventsAllowed = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool("quicktimeEventsAllowed", DuelistMod.quicktimeEventsAllowed);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        LINEBREAK();

        tooltip = "Adds one extra #ySummon to the Millennium Puzzle's start of combat effect.";
        settingElements.add(new DuelistLabeledToggleButton(Strings.forcePuzzleText,tooltip, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.forcePuzzleSummons, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.forcePuzzleSummons = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_FORCE_PUZZLE, DuelistMod.forcePuzzleSummons);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        LINEBREAK();

        tooltip = "When enabled, allows the chance for each copy of #ySparks in your starting deck to be replaced with a special modified copy.";
        settingElements.add(new DuelistLabeledToggleButton("Allow Special Sparks",tooltip, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.allowSpecialSparks, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.allowSpecialSparks = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_ALLOW_SPECIAL_SPARKS, DuelistMod.forcePuzzleSummons);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        tooltip = "When enabled, forces at least one copy of #ySparks in your starting deck to be replaced with a special modified copy.";
        settingElements.add(new DuelistLabeledToggleButton("Force Special Sparks",tooltip, DuelistMod.xLabPos + DuelistMod.xSecondCol, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.forceSpecialSparks, DuelistMod.settingsPanel, (label) -> {}, (button) ->
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
        tooltip = "Determines which special version of #ySparks will replace the copies in your deck.";
        DuelistDropdown sparksStrategy = new DuelistDropdown(tooltip, sparksChoices, Settings.scale * (DuelistMod.xLabPos + DuelistMod.xSecondCol + DuelistMod.xThirdCol - 52), Settings.scale * (DuelistMod.yPos + 24), (s, i) -> {
            DuelistMod.selectedSparksStrategy = SpecialSparksStrategy.menuMappingReverse.get(i);
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt(DuelistMod.PROP_SELECTED_SPARKS_STRATEGY, i);
                config.save();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        sparksStrategy.setSelectedIndex(SpecialSparksStrategy.menuMapping.get(DuelistMod.selectedSparksStrategy));
        settingElements.add(sparksStrategy);

        return settingElements;
    }
}
