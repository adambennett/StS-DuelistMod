package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import basemod.ModLabel;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.Settings;
import duelistmod.DuelistMod;
import duelistmod.enums.Mode;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.SpecificConfigMenuPage;
import java.util.ArrayList;

public class General extends SpecificConfigMenuPage {

    public General() {
        super("General Settings", "General");
    }

    public ArrayList<IUIElement> getElements() {

        String cardsString = DuelistMod.Config_UI_String.TEXT[5];
        ArrayList<IUIElement> settingElements = new ArrayList<>();

        settingElements.add(new ModLabel(cardsString + DuelistMod.cardCount, (DuelistMod.xLabPos), (DuelistMod.yPos + 15),DuelistMod.settingsPanel,(me)->{}));

        LINEBREAK(25);

        settingElements.add(new ModLabel("Birthday", DuelistMod.xLabPos, DuelistMod.yPos, DuelistMod.settingsPanel, (me)->{}));

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
        String tooltip = "On your birthday, special birthday cards will appear in card reward pools and sometimes in your starting deck.";
        DuelistDropdown monthSelector = new DuelistDropdown(tooltip, months, Settings.scale * (DuelistMod.xLabPos + 195),Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
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

        ArrayList<String> days = new ArrayList<>();
        days.add("---"); for (int i = 1; i < 32; i++) { days.add(i+""); }
        tooltip = "On your birthday, special birthday cards will appear in card reward pools and sometimes in your starting deck.";
        DuelistMod.daySelector = new DuelistDropdown(tooltip, days, Settings.scale * (DuelistMod.xLabPos + DuelistMod.xSecondCol + DuelistMod.xThirdCol - 135 - 360), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
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

        if (DuelistMod.modMode == Mode.NIGHTLY) {
            LINEBREAK(45);
            settingElements.add(new ModLabel("Nightly Build " + DuelistMod.nightlyBuildNum, (DuelistMod.xLabPos), (DuelistMod.yPos + 15),DuelistMod.settingsPanel,(me)->{}));
        }

        settingElements.add(DuelistMod.daySelector);
        settingElements.add(monthSelector);

        return settingElements;
    }
}
