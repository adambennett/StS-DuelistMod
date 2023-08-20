package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import basemod.ModLabel;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import duelistmod.DuelistMod;
import duelistmod.enums.SpecialSparksStrategy;
import duelistmod.persistence.data.GameplaySettings;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.DuelistLabeledToggleButton;
import duelistmod.ui.configMenu.RefreshablePage;
import duelistmod.ui.configMenu.SpecificConfigMenuPage;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Gameplay extends SpecificConfigMenuPage implements RefreshablePage {

    private boolean isRefreshing;
    public static LinkedHashSet<String> orbSlotOptions;

    public Gameplay() {
        super("Gameplay Settings", "Gameplay");
    }

    public ArrayList<IUIElement> getElements() {
        ArrayList<IUIElement> settingElements = new ArrayList<>();
        String tooltip = "Allow enemy duelists to spawn in Elite encounters.";
        settingElements.add(new DuelistLabeledToggleButton("Enemy Duelists", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getEnemyDuelists(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setEnemyDuelists(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        tooltip = "Allow Holiday-specific cards to spawn in reward pools and in your starting deck. NL NL Includes birthday cards.";
        settingElements.add(new DuelistLabeledToggleButton("Celebrate Holidays",tooltip, DuelistMod.xLabPos + DuelistMod.xSecondCol, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getHolidayCards(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setHolidayCards(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        LINEBREAK();

        tooltip = "Allow custom Duelist events to appear during runs.";
        settingElements.add(new DuelistLabeledToggleButton("Duelist Events",tooltip, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getDuelistEvents(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setDuelistEvents(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        String unlockDecksTooltip = "Unlock all decks. NL Does not reset your progress!";
        settingElements.add(new DuelistLabeledToggleButton("Unlock All Decks", unlockDecksTooltip, DuelistMod.xLabPos + DuelistMod.xSecondCol, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getUnlockAllDecks(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setUnlockAllDecks(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        LINEBREAK();

        tooltip = "Allow Duelist curses to appear during runs. NL NL When turned off, if a Duelist curse is generated, it will be replaced with a random base game curse.";
        settingElements.add(new DuelistLabeledToggleButton("Duelist Curses",tooltip, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getDuelistCurses(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setDuelistCurses(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        tooltip = "When enabled, #yChallenge #yMode will be fully available for all decks. NL Enabling this could modify your #yChallenge #yMode progress if you win on a higher level than you normally have available.";
        settingElements.add(new DuelistLabeledToggleButton("Unlock Challenge Mode",tooltip, DuelistMod.xLabPos + DuelistMod.xSecondCol, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getUnlockChallengeMode(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setUnlockChallengeMode(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        LINEBREAK();

        tooltip = "Allow potions that channel orbs to spawn as rewards and in shops.";
        settingElements.add(new DuelistLabeledToggleButton("Orb Potions",tooltip, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getOrbPotions(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setOrbPotions(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        tooltip = "When enabled, start each run with a set of relics that allow you to make various modifications to the card reward pool. NL NL Changes to this option are applied at the start of a new run.";
        settingElements.add(new DuelistLabeledToggleButton("Card Pool Relics",tooltip, DuelistMod.xLabPos + DuelistMod.xSecondCol, (DuelistMod.yPos), Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getCardPoolRelics(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setCardPoolRelics(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        LINEBREAK();

        tooltip = "When enabled, #yTributing a #yMegatype card for another #yMegatype card will launch a Quick-Time Event mini-game during combat that determines which random #yTribute synergy effects to run. NL NL When disabled, #yMegatype synergy #yTributes will simply trigger one random synergy #yTribute effect.";
        settingElements.add(new DuelistLabeledToggleButton("Quick-Time Events",tooltip, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getQuickTimeEvents(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setQuickTimeEvents(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        tooltip = "When enabled, any cards that #ySummon will be unplayable unless you have enough summoning zones available. NL NL This should make the game much harder.";
        settingElements.add(new DuelistLabeledToggleButton("Restrict Summoning to Available Zones",tooltip, DuelistMod.xLabPos + DuelistMod.xSecondCol, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getRestrictSummonZones(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setRestrictSummonZones(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        LINEBREAK();

        tooltip = "When enabled, allows the chance for each copy of #ySparks in your starting deck to be replaced with a special modified copy.";
        settingElements.add(new DuelistLabeledToggleButton("Allow Special Sparks",tooltip, DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getAllowSpecialSparks(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setAllowSpecialSparks(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        tooltip = "When enabled, forces at least one copy of #ySparks in your starting deck to be replaced with a special modified copy.";
        settingElements.add(new DuelistLabeledToggleButton("Force Special Sparks",tooltip, DuelistMod.xLabPos + DuelistMod.xSecondCol, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getForceSpecialSparks(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setForceSpecialSparks(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        ArrayList<String> sparksChoices = new ArrayList<>(SpecialSparksStrategy.displayNames);
        tooltip = "Determines which special version of #ySparks will replace the copies in your deck.";
        DuelistDropdown sparksStrategy = new DuelistDropdown(tooltip, sparksChoices, Settings.scale * (DuelistMod.xLabPos + DuelistMod.xSecondCol + DuelistMod.xThirdCol - 52), Settings.scale * (DuelistMod.yPos + 24), (s, i) -> {
            settings().setSparksStrategy(s);
            DuelistMod.selectedSparksStrategy = SpecialSparksStrategy.displayNameMapping.getOrDefault(s, SpecialSparksStrategy.RANDOM);
            DuelistMod.configSettingsLoader.save();
        });
        sparksStrategy.setSelected(settings().getSparksStrategy());

        LINEBREAK(15);

        settingElements.add(new ModLabel("Extra Orb Slots", DuelistMod.xLabPos, DuelistMod.yPos,DuelistMod.settingsPanel,(me)->{}));
        tooltip = "Extra orb slots to start with above #b3. Set to #b0 by default.";
        DuelistDropdown bonusOrbsSelector = new DuelistDropdown(tooltip, orbSlotOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 24), (s, i) -> {
            settings().setOrbSlots(Integer.parseInt(s));
            DuelistMod.configSettingsLoader.save();
        });
        bonusOrbsSelector.setSelected(settings().getOrbSlots().toString());

        settingElements.add(bonusOrbsSelector);
        settingElements.add(sparksStrategy);

        this.isRefreshing = false;
        return settingElements;
    }

    @Override
    public void refresh() {
        if (!this.isRefreshing && DuelistMod.paginator != null) {
            this.isRefreshing = true;
            DuelistMod.paginator.refreshPage(this);
        }
    }

    @Override
    public void resetToDefault() {
        DuelistMod.persistentDuelistData.GameplaySettings = new GameplaySettings();
    }

    private GameplaySettings settings() {
        return DuelistMod.persistentDuelistData.GameplaySettings;
    }

    static {
        orbSlotOptions = new LinkedHashSet<>();
        for (int i = 0; i < 8; i++) {
            orbSlotOptions.add(String.valueOf(i));
        }
    }
}
