package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import basemod.ModLabel;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import duelistmod.DuelistMod;
import duelistmod.enums.SpecialSparksStrategy;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.DuelistLabeledToggleButton;
import duelistmod.ui.configMenu.SpecificConfigMenuPage;
import java.util.ArrayList;

public class Gameplay extends SpecificConfigMenuPage {

    public Gameplay() {
        super("Gameplay Settings", "Gameplay");
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

        tooltip = "Allow Holiday-specific cards to spawn in reward pools and in your starting deck. NL NL Includes birthday cards.";
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

        String unlockDecksTooltip = "Unlock all decks. NL Does not reset your progress!";
        settingElements.add(new DuelistLabeledToggleButton("Unlock All Decks", unlockDecksTooltip, DuelistMod.xLabPos + DuelistMod.xSecondCol, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.unlockAllDecks, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.unlockAllDecks = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_UNLOCK, DuelistMod.unlockAllDecks);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        LINEBREAK();

        tooltip = "Allow Duelist curses to appear during runs. NL NL When turned off, if a Duelist curse is generated, it will be replaced with a random base game curse.";
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

        tooltip = "When enabled, #yChallenge #yMode will be fully available for all decks. NL Enabling this could modify your #yChallenge #yMode progress if you win on a higher level than you normally have available.";
        settingElements.add(new DuelistLabeledToggleButton("Unlock Challenge Mode",tooltip, DuelistMod.xLabPos + DuelistMod.xSecondCol, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.isChallengeForceUnlocked, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.isChallengeForceUnlocked = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool("isChallengeForceUnlocked", DuelistMod.isChallengeForceUnlocked);
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

        tooltip = "When enabled, start each run with a set of relics that allow you to make various modifications to the card reward pool. NL NL Game must be restarted when changing this option.";
        settingElements.add(new DuelistLabeledToggleButton("Card Pool Relics",tooltip, DuelistMod.xLabPos + DuelistMod.xSecondCol, (DuelistMod.yPos), Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.allowCardPoolRelics, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.allowCardPoolRelics = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_ALLOW_CARD_POOL_RELICS, DuelistMod.allowCardPoolRelics);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }
        }));

        LINEBREAK();

        tooltip = "When enabled, #yTributing a #yMegatype card for another #yMegatype card will launch a Quick-Time Event minigame during combat that determines which random #yTribute synergy effects to run. NL NL When disabled, #yMegatype synergy #yTributes will simply trigger one random synergy #yTribute effect.";
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

        tooltip = "When enabled, any cards that #ySummon will be unplayable unless you have enough summoning zones available. NL NL This should make the game much harder.";
        settingElements.add(new DuelistLabeledToggleButton("Restrict Summoning to Available Zones",tooltip, DuelistMod.xLabPos + DuelistMod.xSecondCol, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.restrictSummonZones, DuelistMod.settingsPanel, (label) -> {}, (button) ->
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

        tooltip = "When enabled, allows the chance for each copy of #ySparks in your starting deck to be replaced with a special modified copy.";
        settingElements.add(new DuelistLabeledToggleButton("Allow Special Sparks",tooltip, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.allowSpecialSparks, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.allowSpecialSparks = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_ALLOW_SPECIAL_SPARKS, DuelistMod.allowSpecialSparks);
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
                config.setBool(DuelistMod.PROP_FORCE_SPECIAL_SPARKS, DuelistMod.forceSpecialSparks);
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

        LINEBREAK(15);

        settingElements.add(new ModLabel("Extra Orb Slots", DuelistMod.xLabPos, DuelistMod.yPos,DuelistMod.settingsPanel,(me)->{}));
        tooltip = "Extra orb slots to start with above #b3. Set to #b0 by default.";
        ArrayList<String> orbSlotsOptions = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            orbSlotsOptions.add(String.valueOf(i));
        }
        DuelistDropdown bonusOrbsSelector = new DuelistDropdown(tooltip, orbSlotsOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 24), (s, i) -> {
            DuelistMod.bonusStartingOrbSlots = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("bonusStartingOrbSlots", i);
                config.save();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        bonusOrbsSelector.setSelectedIndex(DuelistMod.bonusStartingOrbSlots);

        settingElements.add(bonusOrbsSelector);
        settingElements.add(sparksStrategy);

        return settingElements;
    }
}
