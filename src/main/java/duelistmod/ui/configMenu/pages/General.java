package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import basemod.ModLabel;
import basemod.ModLabeledButton;
import com.megacrit.cardcrawl.core.Settings;
import duelistmod.DuelistMod;
import duelistmod.enums.Mode;
import duelistmod.persistence.data.GeneralSettings;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.DuelistLabeledButton;
import duelistmod.ui.configMenu.RefreshablePage;
import duelistmod.ui.configMenu.SpecificConfigMenuPage;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class General extends SpecificConfigMenuPage implements RefreshablePage {

    private boolean isRefreshing;
    public static LinkedHashSet<String> months;
    public static LinkedHashSet<String> days;

    public General() {
        super("General Settings", "General");
    }

    public ArrayList<IUIElement> getElements() {

        String cardsString = DuelistMod.Config_UI_String.TEXT[5];
        ArrayList<IUIElement> settingElements = new ArrayList<>();

        settingElements.add(new ModLabel(cardsString + DuelistMod.cardCount, (DuelistMod.xLabPos), (DuelistMod.yPos + 15),DuelistMod.settingsPanel,(me)->{}));

        LINEBREAK(25);

        settingElements.add(new ModLabel("Birthday", DuelistMod.xLabPos, DuelistMod.yPos, DuelistMod.settingsPanel, (me)->{}));

        String tooltip = "On your birthday, special birthday cards will appear in card reward pools and sometimes in your starting deck.";
        DuelistDropdown monthSelector = new DuelistDropdown(tooltip, months, Settings.scale * (DuelistMod.xLabPos + 195),Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            if (s != null && !s.equals("---")) {
                settings().setBirthdayMonth(s);
                if (settings().getNeverChangedBirthday()) {
                    settings().setNeverChangedBirthday(false);
                }
                DuelistMod.configSettingsLoader.save();
            }
        });
        monthSelector.setSelected(settings().getBirthdayMonth());


        tooltip = "On your birthday, special birthday cards will appear in card reward pools and sometimes in your starting deck.";
        DuelistDropdown daySelector = new DuelistDropdown(tooltip, days, Settings.scale * (DuelistMod.xLabPos + DuelistMod.xSecondCol + DuelistMod.xThirdCol - 135 - 360), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            if (s != null && !s.equals("---")) {
                settings().setBirthdayDay(s);
                if (settings().getNeverChangedBirthday()) {
                    settings().setNeverChangedBirthday(false);
                }
                DuelistMod.configSettingsLoader.save();
            }

        });
        daySelector.setSelected(settings().getBirthdayDay());

        LINEBREAK(65);

        tooltip = "Reset #rALL DuelistMod configuration settings to default. NL Impacts all pages in this menu, including all sub-menus.";
        ModLabeledButton resetButton = new DuelistLabeledButton("Reset ALL Settings to Default", tooltip, DuelistMod.xLabPos + DuelistMod.xSecondCol + 200, DuelistMod.yPos - 25, DuelistMod.settingsPanel, (element)->{
            if (this.isRefreshing) {
                this.isRefreshing = false;
                return;
            }
            DuelistMod.paginator.resetAllPagesToDefault();
        });

        if (DuelistMod.modMode == Mode.NIGHTLY) {
            LINEBREAK(45);
            settingElements.add(new ModLabel("Nightly Build " + DuelistMod.nightlyBuildNum, (DuelistMod.xLabPos), (DuelistMod.yPos + 15),DuelistMod.settingsPanel,(me)->{}));
        }

        settingElements.add(resetButton);
        settingElements.add(daySelector);
        settingElements.add(monthSelector);

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
        DuelistMod.persistentDuelistData.GeneralSettings = new GeneralSettings();
    }

    private GeneralSettings settings() {
        return DuelistMod.persistentDuelistData.GeneralSettings;
    }

    static {
        months = new LinkedHashSet<>();
        days = new LinkedHashSet<>();
        days.add("---");
        for (int i = 1; i < 32; i++) {
            days.add(addSuffix(i));
        }
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
    }

    private static String addSuffix(int number) {
        if (number >= 1 && number <= 31) {
            if (number >= 11 && number <= 13) {
                return number + "th";
            } else {
                int lastDigit = number % 10;
                switch (lastDigit) {
                    case 1:
                        return number + "st";
                    case 2:
                        return number + "nd";
                    case 3:
                        return number + "rd";
                    default:
                        return number + "th";
                }
            }
        } else {
            return "Invalid number";
        }
    }
}
