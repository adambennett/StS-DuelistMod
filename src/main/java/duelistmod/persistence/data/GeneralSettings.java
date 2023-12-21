package duelistmod.persistence.data;

import duelistmod.enums.DataCategoryType;
import duelistmod.persistence.DataDifferenceDTO;
import duelistmod.persistence.PersistentDuelistData;
import duelistmod.ui.configMenu.pages.General;

import java.util.LinkedHashSet;

public class GeneralSettings extends DataCategory {
    public String birthdayMonth = "---";
    public String birthdayDay = "0";
    public Boolean neverChangedBirthday = true;

    public GeneralSettings() {
        this.category = "General Settings";
        this.type = DataCategoryType.Config_Setting;
    }

    public GeneralSettings(GeneralSettings from) {
        this(from.birthdayMonth, from.birthdayDay, from.neverChangedBirthday);
    }

    public GeneralSettings(String month, String day, Boolean neverChangedBirthday) {
        this();
        this.birthdayMonth = PersistentDuelistData.validate(month, this.birthdayMonth, General.months);
        this.birthdayDay = PersistentDuelistData.validate(day, this.birthdayDay, General.days);
        this.neverChangedBirthday = this.birthdayMonth.equals("---") || this.birthdayDay.equals("0") || PersistentDuelistData.<Boolean>nullCheck(neverChangedBirthday, this.neverChangedBirthday);
        if (this.neverChangedBirthday) {
            this.birthdayDay = "0";
            this.birthdayMonth = "---";
        }
    }

    @Override
    public LinkedHashSet<DataDifferenceDTO<?>> generateMetricsDifferences(PersistentDuelistData defaultSettings, PersistentDuelistData playerSettings) {
        return new LinkedHashSet<>();
    }

    public Integer getBirthdayMonthInt() {
        try {
            return Integer.parseInt(this.birthdayMonth);
        } catch (Exception ignored) {}
        return null;
    }

    public String getBirthdayMonth() {
        return birthdayMonth;
    }

    public void setBirthdayMonth(String birthdayMonth) {
        this.birthdayMonth = birthdayMonth;
    }

    public Integer getBirthdayDayInt() {
        try {
            return Integer.parseInt(this.birthdayDay);
        } catch (Exception ignored) {}
        return null;
    }

    public String getBirthdayDay() {
        return birthdayDay;
    }

    public void setBirthdayDay(String birthdayDay) {
        this.birthdayDay = birthdayDay;
    }

    public Boolean getNeverChangedBirthday() {
        return neverChangedBirthday;
    }

    public void setNeverChangedBirthday(Boolean neverChangedBirthday) {
        this.neverChangedBirthday = neverChangedBirthday;
    }
}
