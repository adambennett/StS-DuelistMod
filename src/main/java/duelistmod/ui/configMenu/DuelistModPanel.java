package duelistmod.ui.configMenu;

import basemod.ModPanel;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import com.megacrit.cardcrawl.screens.options.DropdownMenuListener;
import duelistmod.DuelistMod;
import duelistmod.helpers.Util;

public class DuelistModPanel extends ModPanel implements DropdownMenuListener {

    @Override
    public void changedSelectionTo(DropdownMenu dropdownMenu, int i, String s) {
        if (dropdownMenu instanceof DuelistDropdown) {
            DuelistDropdown menu = ((DuelistDropdown)dropdownMenu);
            switch (menu.type) {
                case PAGE_SELECTOR:
                    DuelistMod.paginator.setPage(s);
                    break;
                case BIRTHDAY_MONTH:
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
                    break;
                case BIRTHDAY_DAY:
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
                    break;
                case POOL_TYPE:
                    DuelistMod.setIndex = i;
                    try {
                        SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                        config.setInt(DuelistMod.PROP_SET, DuelistMod.setIndex);
                        config.save();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case TAG_SELECTOR:
                    DuelistMod.flipCardTags = i == 1;
                    try
                    {
                        SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                        config.setBool(DuelistMod.PROP_FLIP, DuelistMod.flipCardTags);
                        config.save();
                    } catch (Exception e) { e.printStackTrace(); }
                    break;
                case CHARACTER_MODEL:
                    switch (i) {
                        case 0:
                            DuelistMod.oldCharacter = false;
                            DuelistMod.playAsKaiba = false;
                            break;
                        case 1:
                            DuelistMod.oldCharacter = true;
                            DuelistMod.playAsKaiba = false;
                            break;
                        case 2:
                            DuelistMod.playAsKaiba = true;
                            break;
                        default:
                            Util.log("Character model option not supported by DuelistModPanel! Character selected at index " + i + ": " + s);
                            break;
                    }
                    break;
                default:
                    Util.log("Unimplemented dropdown menu changed to option " + i + " (" + s + ")");
                    break;
            }
        }
    }

    private void onClose() {
        Util.log("Closed DuelistModPanel");
        DuelistMod.paginator.resetToPageOne();
    }

    @Override
    public void update() {
        super.update();
        if (!this.isUp) {
            this.onClose();
        }
    }
}
