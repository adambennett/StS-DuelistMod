package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import basemod.ModLabel;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.Settings;
import duelistmod.DuelistMod;
import duelistmod.dto.DropdownSelection;
import duelistmod.enums.MonsterTypes;
import duelistmod.enums.VinesLeavesMods;
import duelistmod.helpers.Util;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.GeneralPager;
import duelistmod.ui.configMenu.Pager;
import duelistmod.ui.configMenu.RefreshablePage;
import duelistmod.ui.configMenu.SpecificConfigMenuPage;

import java.util.ArrayList;

public class MonsterType extends SpecificConfigMenuPage implements RefreshablePage {
    
    private MonsterTypes type = MonsterTypes.AQUA;
    private int currentTypeIndex = 0;
    private int maxIndex = -1;
    private DuelistDropdown typeSelector;
    private boolean isRefreshing;

    public MonsterType() {
        super("Monster Type Settings");
    }

    public ArrayList<IUIElement> getElements() {
        this.maxIndex = MonsterTypes.values().length - 1;

        ArrayList<String> types = new ArrayList<>();
        for (MonsterTypes type : MonsterTypes.values()) { types.add(type.displayText()); }
        this.typeSelector = new DuelistDropdown(types, Settings.scale * (DuelistMod.xLabPos + 85), Settings.scale * (DuelistMod.yPos + 52), (s, i) -> {
            if (this.isRefreshing) {
                this.isRefreshing = false;
                return;
            }
            this.setPage(i);
        });
        if (!this.isRefreshing) {
            this.isRefreshing = true;
            this.setPage(this.currentTypeIndex);
        } else {
            this.typeSelector.setSelectedIndex(this.currentTypeIndex);
            this.type = MonsterTypes.menuMapping.get(this.currentTypeIndex);
        }

        int pagerRightX = (int)(DuelistMod.xLabPos + 285);
        int pagerLeftX = (int)DuelistMod.xLabPos;
        int pagerY = (int)DuelistMod.yPos;
        GeneralPager pager = new GeneralPager(() -> this.setPage(this.currentTypeIndex + 1), () -> this.setPage(this.currentTypeIndex - 1));
        Pager nextPageBtn = new Pager(DuelistMod.rightArrow, pagerRightX, pagerY, 75, 75, true, pager);
        Pager prevPageBtn = new Pager(DuelistMod.leftArrow, pagerLeftX, pagerY, 75, 75, false, pager);

        LINEBREAK();
        LINEBREAK();

        ArrayList<IUIElement> settingElements = new ArrayList<>();
        generateSubPages(settingElements);

        settingElements.add(this.typeSelector);
        settingElements.add(prevPageBtn);
        settingElements.add(nextPageBtn);

        this.isRefreshing = false;
        return settingElements;
    }

    @Override
    public void refresh() {
        this.setPage(0);
    }

    private void setPage(int index) {
        DropdownSelection selection = this.typeSelector.getSelectionBox();
        if (index > this.maxIndex) {
            index = 0;
        }
        if (index < 0) {
            index = this.maxIndex;
        }

        this.type = MonsterTypes.menuMapping.get(index);
        this.currentTypeIndex = index;
        if (!this.isRefreshing && DuelistMod.paginator != null) {
            this.isRefreshing = true;
            this.typeSelector.setSelectedIndex(index);
            this.isRefreshing = true;
            DuelistMod.paginator.refreshPage(this);
        }
    }

    private void aquaPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel(this.type.displayText() + ": No type-specific configurations available", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
    }

    private void dragonPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("Every", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));

        ArrayList<String> days = new ArrayList<>();
        for (int i = 0; i < 1000; i++) { days.add(i+""); }
        String tooltip = "";
        DuelistDropdown dragonScalesSelector = new DuelistDropdown(tooltip, days, Settings.scale * (DuelistMod.xLabPos + 120), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.dragonScalesSelectorIndex = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("dragonScalesSelectorIndex", DuelistMod.dragonScalesSelectorIndex);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        dragonScalesSelector.setSelectedIndex(DuelistMod.dragonScalesSelectorIndex);
        settingElements.add(new ModLabel("DragonScales", (DuelistMod.xLabPos + 120 + 150), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));

        LINEBREAK();

        settingElements.add(new ModLabel("increases the Block and damage of Dragons by", DuelistMod.xLabPos, DuelistMod.yPos, DuelistMod.settingsPanel, (me)->{}));

        ArrayList<String> mods = new ArrayList<>();
        for (int i = 0; i < 1000; i++) { mods.add(i+""); }
        tooltip = "";
        DuelistDropdown modSelector = new DuelistDropdown(tooltip, mods, Settings.scale * (DuelistMod.xLabPos + 710), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.dragonScalesModIndex = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("dragonScalesModIndex", DuelistMod.dragonScalesModIndex);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        modSelector.setSelectedIndex(DuelistMod.dragonScalesModIndex);

        settingElements.add(modSelector);
        settingElements.add(dragonScalesSelector);
    }

    private void fiendPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel(this.type.displayText() + ": No type-specific configurations available", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
    }

    private void insectPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel(this.type.displayText() + ": No type-specific configurations available", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
    }

    private void machinePage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel(this.type.displayText() + ": No type-specific configurations available", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
    }

    private void megatypePage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel(this.type.displayText() + ": No type-specific configurations available", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
    }

    private void naturiaPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("Whenever you gain Leaves", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));

        ArrayList<String> leavesOptions = new ArrayList<>();
        for (VinesLeavesMods mod : VinesLeavesMods.values()) {
            if (mod.forLeaves()) {
                leavesOptions.add(mod.displayText());
            }
        }
        String tooltip = "";
        DuelistDropdown leavesSelector = new DuelistDropdown(tooltip, leavesOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.leavesSelectorIndex = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("leavesSelectorIndex", DuelistMod.leavesSelectorIndex);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        leavesSelector.setSelectedIndex(DuelistMod.leavesSelectorIndex);

        LINEBREAK(25);

        settingElements.add(new ModLabel("Whenever you gain Vines", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));

        ArrayList<String> vinesOptions = new ArrayList<>();
        for (VinesLeavesMods mod : VinesLeavesMods.values()) {
            if (mod.forVines()) {
                vinesOptions.add(mod.displayText());
            }
        }
        tooltip = "";
        DuelistDropdown vinesSelector = new DuelistDropdown(tooltip, vinesOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.vinesSelectorIndex = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("vinesSelectorIndex", DuelistMod.vinesSelectorIndex);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        vinesSelector.setSelectedIndex(DuelistMod.vinesSelectorIndex);

        settingElements.add(vinesSelector);
        settingElements.add(leavesSelector);
    }

    private void plantPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel(this.type.displayText() + ": No type-specific configurations available", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
    }

    private void predaplantPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel(this.type.displayText() + ": No type-specific configurations available", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
    }

    private void spellcasterPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel(this.type.displayText() + ": No type-specific configurations available", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
    }

    private void superheavyPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel(this.type.displayText() + ": No type-specific configurations available", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
    }

    private void toonPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel(this.type.displayText() + ": No type-specific configurations available", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
    }

    private void zombiePage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel(this.type.displayText() + ": No type-specific configurations available", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
    }

    private void warriorPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel(this.type.displayText() + ": No type-specific configurations available", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
    }

    private void rockPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel(this.type.displayText() + ": No type-specific configurations available", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
    }

    private void wyrmPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel(this.type.displayText() + ": No type-specific configurations available", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
    }

    private void dinosaurPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel(this.type.displayText() + ": No type-specific configurations available", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
    }

    private void generateSubPages(ArrayList<IUIElement> settingElements) {
        switch (this.type) {
            case AQUA:
                aquaPage(settingElements);
                break;
            case DRAGON:
                dragonPage(settingElements);
                break;
            case FIEND:
                fiendPage(settingElements);
                break;
            case INSECT:
                insectPage(settingElements);
                break;
            case MACHINE:
                machinePage(settingElements);
                break;
            case MEGATYPE:
                megatypePage(settingElements);
                break;
            case NATURIA:
                naturiaPage(settingElements);
                break;
            case PLANT:
                plantPage(settingElements);
                break;
            case PREDAPLANT:
                predaplantPage(settingElements);
                break;
            case SPELLCASTER:
                spellcasterPage(settingElements);
                break;
            case SUPERHEAVY:
                superheavyPage(settingElements);
                break;
            case TOON_POOL:
                toonPage(settingElements);
                break;
            case ZOMBIE:
                zombiePage(settingElements);
                break;
            case WARRIOR:
                warriorPage(settingElements);
                break;
            case ROCK:
                rockPage(settingElements);
                break;
            case WYRM:
                wyrmPage(settingElements);
                break;
            case DINOSAUR:
                dinosaurPage(settingElements);
                break;
            default:
                Util.log("Monster type not implemented for config page!");
        }
    }
}
