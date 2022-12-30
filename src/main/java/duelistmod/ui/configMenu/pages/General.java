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
import java.util.ArrayList;

public class General extends SpecificConfigMenuPage {

    public General() {
        super("General Settings");
    }

    public ArrayList<IUIElement> getElements() {
        String unlockString = DuelistMod.Config_UI_String.TEXT[8];
        String cardsString = DuelistMod.Config_UI_String.TEXT[5];
        ArrayList<IUIElement> settingElements = new ArrayList<>();

        settingElements.add(new ModLabel(cardsString + DuelistMod.cardCount, (DuelistMod.xLabPos + DuelistMod.xSecondCol - 75), (DuelistMod.yPos + 15),DuelistMod.settingsPanel,(me)->{}));

        lineBreak();

        String unlockDecksTooltip = "Unlock all decks. Does not reset your progress!";
        settingElements.add(new ModLabeledToggleButton(unlockString, unlockDecksTooltip, (DuelistMod.xLabPos + 175), (DuelistMod.yPos - 10), Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.unlockAllDecks, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.unlockAllDecks = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_UNLOCK, DuelistMod.unlockAllDecks);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        String tooltip = "When enabled, start each run with a set of relics that allow you to make various modifications to the card reward pool.";
        settingElements.add(new ModLabeledToggleButton("Card Pool Relics",tooltip, (DuelistMod.xLabPos + DuelistMod.xSecondCol + 200), (DuelistMod.yPos - 10), Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.allowCardPoolRelics, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.allowCardPoolRelics = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool(DuelistMod.PROP_ALLOW_CARD_POOL_RELICS, DuelistMod.allowCardPoolRelics);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }
        }));

        lineBreak();
        lineBreak();

        settingElements.add(new ModLabel("Birthday", (DuelistMod.xLabPos + DuelistMod.xSecondCol - 10), DuelistMod.yPos, DuelistMod.settingsPanel, (me)->{}));
        lineBreak();

        settingElements.add(new ModLabel("Month", (DuelistMod.xLabPos + DuelistMod.xSecondCol), DuelistMod.yPos,DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> months = new ArrayList<>();
        months.add("---");
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
        DuelistDropdown monthSelector = new DuelistDropdown(months, Settings.scale * (DuelistMod.xLabPos + 150 + DuelistMod.xSecondCol),Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            if (i > 0) {
                DuelistMod.birthdayMonth = i;
                if (DuelistMod.neverChangedBirthday) {
                    DuelistMod.neverChangedBirthday = false;
                }
            }
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                if (DuelistMod.birthdayMonth > 0 && DuelistMod.birthdayMonth < 13) {
                    config.setInt("birthdayMonth", DuelistMod.birthdayMonth);
                }
                config.setBool("neverChangedBirthday", DuelistMod.neverChangedBirthday);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        monthSelector.setSelectedIndex(DuelistMod.birthdayMonth > 0 && DuelistMod.birthdayMonth < 13 ? DuelistMod.birthdayMonth : 0);

        lineBreak();

        settingElements.add(new ModLabel("Day", (DuelistMod.xLabPos + DuelistMod.xSecondCol), (DuelistMod.yPos - 10),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> days = new ArrayList<>();
        days.add("---");
        for (int i = 1; i < 32; i++) {
            days.add(i+"");
        }
        DuelistMod.daySelector = new DuelistDropdown(days, Settings.scale * (DuelistMod.xLabPos + 150 + DuelistMod.xSecondCol), Settings.scale * (DuelistMod.yPos + 22 - 10), (s, i) -> {
            if (i > 0) {
                DuelistMod.birthdayDay = i;
                if (DuelistMod.neverChangedBirthday) {
                    DuelistMod.neverChangedBirthday = false;
                }
            }
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                if (DuelistMod.birthdayDay > 0 && DuelistMod.birthdayDay < 32) {
                    config.setInt("birthdayDay", DuelistMod.birthdayDay);
                }
                config.setBool("neverChangedBirthday", DuelistMod.neverChangedBirthday);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        DuelistMod.daySelector.setSelectedIndex(DuelistMod.birthdayDay > 0 && DuelistMod.birthdayDay < 32 ? DuelistMod.birthdayDay : 0);

        lineBreak();
        lineBreak();

/*
        settingElements.add(new ModLabel("Unlocking all decks does not reset your progress and can be toggled off ", (DuelistMod.xLabPos + 25), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        settingElements.add(new ModLabel("", (DuelistMod.xLabPos + 25), (DuelistMod.yPos - 35), DuelistMod.settingsPanel, (me)->{}));
*/

        settingElements.add(DuelistMod.daySelector);
        settingElements.add(monthSelector);


        return settingElements;
    }
}
