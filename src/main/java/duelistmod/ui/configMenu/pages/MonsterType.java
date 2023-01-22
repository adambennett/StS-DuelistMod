package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import basemod.ModImage;
import basemod.ModLabel;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import duelistmod.DuelistMod;
import duelistmod.dto.DropdownSelection;
import duelistmod.enums.MonsterTypes;
import duelistmod.enums.VinesLeavesMods;
import duelistmod.helpers.Util;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.DuelistLabeledToggleButton;
import duelistmod.ui.configMenu.GeneralPager;
import duelistmod.ui.configMenu.Pager;
import duelistmod.ui.configMenu.RefreshablePage;
import duelistmod.ui.configMenu.SpecificConfigMenuPage;

import java.util.ArrayList;
import java.util.HashMap;

public class MonsterType extends SpecificConfigMenuPage implements RefreshablePage {
    
    private MonsterTypes type = MonsterTypes.AQUA;
    private int currentTypeIndex = 0;
    private int maxIndex = -1;
    private DuelistDropdown typeSelector;
    private boolean isRefreshing;

    public static final HashMap<Integer, VinesLeavesMods> leavesMenuMapping;
    public static final HashMap<Integer, VinesLeavesMods> vinesMenuMapping;
    public static final HashMap<VinesLeavesMods, Integer> leavesMenuMappingReverse;
    public static final HashMap<VinesLeavesMods, Integer> vinesMenuMappingReverse;

    public MonsterType() {
        super("Monster Type Settings", "Monster Type");
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

        if (this.type != null && this.type.configImg() != null) {
            settingElements.add(new ModImage(DuelistMod.xLabPos + DuelistMod.xSecondCol + DuelistMod.xThirdCol, pagerY - 30, this.type.configImg()));
        }
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
        settingElements.add(new ModLabel("Summon Increase per Tribute", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> summonTributeOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { summonTributeOptions.add(i+""); }
        String tooltip = "Modify the amount of #ySummons gained each time you #yTribute one #yAqua for another. Set to #b1 by default.";
        DuelistDropdown summonTributeSelector = new DuelistDropdown(tooltip, summonTributeOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.aquaInc = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("aquaInc", DuelistMod.aquaInc);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        summonTributeSelector.setSelectedIndex(DuelistMod.aquaInc);
        settingElements.add(summonTributeSelector);
    }

    private void bugPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("Number of Bugs to trigger", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));

        ArrayList<String> numberOfBugsOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { numberOfBugsOptions.add(i+""); }
        String tooltip = "The number of #yBugs you need to play each combat to trigger the #yTemporary #yHP gain effect. Set to #b2 by default.";
        DuelistDropdown numberOfBugsSelector = new DuelistDropdown(tooltip, numberOfBugsOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.bugsToPlayForTempHp = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("bugsToPlayForTempHp", DuelistMod.bugsToPlayForTempHp);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        numberOfBugsSelector.setSelectedIndex(DuelistMod.bugsToPlayForTempHp);

        LINEBREAK(25);

        settingElements.add(new ModLabel("Temporary HP Gain Amount", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> tempHpAmountOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { tempHpAmountOptions.add(i+""); }
        tooltip = "Amount of #yTemporary #yHP to gain whenever you play enough #yBugs in combat. Set to #b5 by default.";
        DuelistDropdown tempHpAmountSelector = new DuelistDropdown(tooltip, tempHpAmountOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.bugTempHP = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("bugTempHP", DuelistMod.bugTempHP);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        tempHpAmountSelector.setSelectedIndex(DuelistMod.bugTempHP);

        LINEBREAK(45);

        tooltip = "When enabled, after gaining #yTemporary #yHP after playing enough #yBugs, the counter is reset so you can trigger the effect again. Disabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Temp HP effect resets after triggering", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.bugEffectResets, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.bugEffectResets = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool("bugEffectResets", DuelistMod.bugEffectResets);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        settingElements.add(numberOfBugsSelector);
        settingElements.add(tempHpAmountSelector);
    }

    private void dinosaurPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("No type-specific configurations available", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
    }

    private void dragonPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("Every", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));

        ArrayList<String> days = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { days.add(String.valueOf(i)); }
        String tooltip = "Set to #b6 by default.";
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
        for (int i = 0; i < 1001; i++) { mods.add(String.valueOf(i)); }
        tooltip = "Set to #b1 by default.";
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

        LINEBREAK(65);

        settingElements.add(new ModLabel("DragonScales per Tribute", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));

        ArrayList<String> dragonScalesAmountOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { dragonScalesAmountOptions.add(i+""); }
        tooltip = "Modify the amount of #yDragonScales gained each time you #yTribute one #yDragon for another. Set to #b2 by default.";
        DuelistDropdown dragonScalesAmountSelector = new DuelistDropdown(tooltip, dragonScalesAmountOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.dragonStr = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("dragonStr", DuelistMod.dragonStr);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        dragonScalesAmountSelector.setSelectedIndex(DuelistMod.dragonStr);

        settingElements.add(dragonScalesAmountSelector);
        settingElements.add(modSelector);
        settingElements.add(dragonScalesSelector);
    }

    private void fiendPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("Cards to Fetch per Tribute", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> summonTributeOptions = new ArrayList<>();
        for (int i = 0; i < 11; i++) { summonTributeOptions.add(i+""); }
        String tooltip = "Modify the amount of cards you move from your discard pile into your hand each time you #yTribute one #yFiend for another. Set to #b1 by default.";
        DuelistDropdown summonTributeSelector = new DuelistDropdown(tooltip, summonTributeOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.fiendDraw = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("fiendDraw", DuelistMod.fiendDraw);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        summonTributeSelector.setSelectedIndex(DuelistMod.fiendDraw);
        settingElements.add(summonTributeSelector);
    }

    private void giantPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("No type-specific configurations available", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
    }

    private void insectPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("Poison per Tribute", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> poisonOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { poisonOptions.add(i+""); }
        String tooltip = "Modify the amount of #yPoison you apply each time you #yTribute one #yInsect for another. Set to #b1 by default.";
        DuelistDropdown poisonSelector = new DuelistDropdown(tooltip, poisonOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.insectPoisonDmg = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("insectPoisonDmg", DuelistMod.insectPoisonDmg);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        poisonSelector.setSelectedIndex(DuelistMod.insectPoisonDmg);
        settingElements.add(poisonSelector);
    }

    private void machinePage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("Artifacts per Tribute", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> artifactOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { artifactOptions.add(i+""); }
        String tooltip = "Modify the amount of #yArtifacts you gain every other time you #yTribute one #yMachine for another. Set to #b1 by default.";
        DuelistDropdown artifactSelector = new DuelistDropdown(tooltip, artifactOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.machineArt = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("machineArt", DuelistMod.machineArt);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        artifactSelector.setSelectedIndex(DuelistMod.machineArt);
        settingElements.add(artifactSelector);
    }

    private void magnetPage(ArrayList<IUIElement> settingElements) {
        String tooltip = "When enabled, a random #yMagnet card will be added to your starting deck each run. Disabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Add random Magnet to starting deck", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.randomMagnetAddedToDeck, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.randomMagnetAddedToDeck = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool("randomMagnetAddedToDeck", DuelistMod.randomMagnetAddedToDeck);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        LINEBREAK(25);

        tooltip = "When enabled alongside 'Add random Magnet to starting deck', various powerful #yMagnet cards such as the electrified versions will be able to be added to your deck. If this is disabled, only the standard #b4 #yMagnet cards can be added. Disabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Allow adding random Super Magnet", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.allowRandomSuperMagnets, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.allowRandomSuperMagnets = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool("allowRandomSuperMagnets", DuelistMod.allowRandomSuperMagnets);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));
    }

    private void megatypePage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("No type-specific configurations available", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
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
            DuelistMod.leavesOption = leavesMenuMapping.get(i);
            Util.log("Setting leaves option to: " + DuelistMod.leavesOption + " based on idex of " + i + "\nLeaves Menu Mapping: \n" + leavesMenuMapping);
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
            DuelistMod.vinesOption = vinesMenuMapping.get(i);
            Util.log("Setting vines option to: " + DuelistMod.vinesOption + " based on idex of " + i + "\nVines Menu Mapping: \n" + vinesMenuMapping);
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("vinesSelectorIndex", DuelistMod.vinesSelectorIndex);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        vinesSelector.setSelectedIndex(DuelistMod.vinesSelectorIndex);

        LINEBREAK(25);

        settingElements.add(new ModLabel("Bonus Vines Damage", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));

        ArrayList<String> baseVinesDamageOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { baseVinesDamageOptions.add(i+""); }
        tooltip = "Deal extra damage with #yVines. Set to #b0 by default.";
        DuelistDropdown vinesDmgSelector = new DuelistDropdown(tooltip, baseVinesDamageOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.naturiaVinesDmgMod = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("naturiaVinesDmgMod", DuelistMod.naturiaVinesDmgMod);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        vinesDmgSelector.setSelectedIndex(DuelistMod.naturiaVinesDmgMod);

        LINEBREAK(25);

        settingElements.add(new ModLabel("Leaves to Trigger", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));

        ArrayList<String> leavesNeededOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { leavesNeededOptions.add(i+""); }
        tooltip = "Number of #yLeaves needed to allow you to trigger the #yBlock effect on your turn. Set to #b5 by default.";
        DuelistDropdown leavesNeededSelector = new DuelistDropdown(tooltip, leavesNeededOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.naturiaLeavesNeeded = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("naturiaLeavesNeeded", DuelistMod.naturiaLeavesNeeded);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        leavesNeededSelector.setSelectedIndex(DuelistMod.naturiaLeavesNeeded);

        settingElements.add(leavesNeededSelector);
        settingElements.add(vinesDmgSelector);
        settingElements.add(vinesSelector);
        settingElements.add(leavesSelector);
    }

    private void ojamaPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("No type-specific configurations available", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
    }

    private void plantPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("Constricted per Tribute", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> constrictedOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { constrictedOptions.add(i+""); }
        String tooltip = "Modify the amount of #yConstricted you apply each time you #yTribute one #yPlant for another. Set to #b1 by default.";
        DuelistDropdown constrictedSelctor = new DuelistDropdown(tooltip, constrictedOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.plantConstricted = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("plantConstricted", DuelistMod.plantConstricted);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        constrictedSelctor.setSelectedIndex(DuelistMod.plantConstricted);
        settingElements.add(constrictedSelctor);
    }

    private void predaplantPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("Thorns per Tribute", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> thornsOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { thornsOptions.add(i+""); }
        String tooltip = "Modify the amount of #yThorns you gain each time you #yTribute one #yPredaplant for another. Set to #b1 by default.";
        DuelistDropdown thornsSelector = new DuelistDropdown(tooltip, thornsOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.predaplantThorns = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("predaplantThorns", DuelistMod.predaplantThorns);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thornsSelector.setSelectedIndex(DuelistMod.predaplantThorns);
        settingElements.add(thornsSelector);
    }

    private void rockPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("Rock Block", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));

        ArrayList<String> blockOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { blockOptions.add(i+""); }
        String tooltip = "Modify the amount of #yBlock you gain for each #yRock you have #ySummoned at the end of turn. Set to #b2 by default.";
        DuelistDropdown blockSelector = new DuelistDropdown(tooltip, blockOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.rockBlock = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("rockBlock", DuelistMod.rockBlock);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        blockSelector.setSelectedIndex(DuelistMod.rockBlock);

        settingElements.add(blockSelector);
    }

    private void rosePage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("No type-specific configurations available", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
    }

    private void spiderPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("Number of Spiders to trigger", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));

        ArrayList<String> numberOfSpidersOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { numberOfSpidersOptions.add(i+""); }
        String tooltip = "The number of #ySpiders you need to play each combat to trigger the #yTemporary #yHP gain effect. Set to #b3 by default.";
        DuelistDropdown numberOfSpidersSelector = new DuelistDropdown(tooltip, numberOfSpidersOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.spidersToPlayForTempHp = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("spidersToPlayForTempHp", DuelistMod.spidersToPlayForTempHp);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        numberOfSpidersSelector.setSelectedIndex(DuelistMod.spidersToPlayForTempHp);

        LINEBREAK(25);

        settingElements.add(new ModLabel("Temporary HP Gain Amount", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> tempHpAmountOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { tempHpAmountOptions.add(i+""); }
        tooltip = "Amount of #yTemporary #yHP to gain whenever you play enough #ySpiders in combat. Set to #b7 by default.";
        DuelistDropdown tempHpAmountSelector = new DuelistDropdown(tooltip, tempHpAmountOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.spiderTempHP = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("spiderTempHP", DuelistMod.spiderTempHP);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        tempHpAmountSelector.setSelectedIndex(DuelistMod.spiderTempHP);

        LINEBREAK(45);

        tooltip = "When enabled, after gaining #yTemporary #yHP after playing enough #ySpiders, the counter is reset so you can trigger the effect again. Disabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Temp HP effect resets after triggering", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.spiderEffectResets, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.spiderEffectResets = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool("bugEffectResets", DuelistMod.spiderEffectResets);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        settingElements.add(numberOfSpidersSelector);
        settingElements.add(tempHpAmountSelector);
    }

    private void spellcasterPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("Block on Attack", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> blockOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { blockOptions.add(i+""); }
        String tooltip = "Modify the amount of #yBlock you gain each time you attack with only #ySpellcasters #ysummoned. Set to #b4 by default.";
        DuelistDropdown blockSelector = new DuelistDropdown(tooltip, blockOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.spellcasterBlockOnAttack = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("spellcasterBlockOnAttack", DuelistMod.spellcasterBlockOnAttack);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        blockSelector.setSelectedIndex(DuelistMod.spellcasterBlockOnAttack);
        settingElements.add(blockSelector);
    }

    private void superheavyPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("Dexterity per Tribute", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> dexOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { dexOptions.add(i+""); }
        String tooltip = "Modify the amount of #yDexterity you gain each time you #yTribute one #ySuperheavy for another. Set to #b1 by default.";
        DuelistDropdown dexSelector = new DuelistDropdown(tooltip, dexOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.superheavyDex = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("superheavyDex", DuelistMod.superheavyDex);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        dexSelector.setSelectedIndex(DuelistMod.superheavyDex);
        settingElements.add(dexSelector);
    }

    private void toonPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("Vulnerable per Tribute", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> thornsOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { thornsOptions.add(i+""); }
        String tooltip = "Modify the amount of #yVulnerable you apply each time you #yTribute one #yToon for another. Set to #b1 by default.";
        DuelistDropdown thornsSelector = new DuelistDropdown(tooltip, thornsOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.toonVuln = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("toonVuln", DuelistMod.toonVuln);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thornsSelector.setSelectedIndex(DuelistMod.toonVuln);
        settingElements.add(thornsSelector);
    }

    private void warriorPage(ArrayList<IUIElement> settingElements) {
        String sMod = DuelistMod.warriorTributeEffectTriggersPerCombat != 1 ? "s" : "";
        String tooltip = "When enabled, #yTributing a #yWarrior for another will allow you to choose any #yStance to enter. Limited to #b" + DuelistMod.warriorTributeEffectTriggersPerCombat + " trigger" + sMod + " per combat. Enabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Enable Warrior Tribute Effect", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.enableWarriorTributeEffect, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.enableWarriorTributeEffect = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool("enableWarriorTributeEffect", DuelistMod.enableWarriorTributeEffect);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        LINEBREAK(25);

        // warriorSynergyTributeNeededToTrigger - how many times you need to tribute to trigger the stance change effect
        settingElements.add(new ModLabel("Number of Tributes to trigger effect", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> tributesNeededOptions = new ArrayList<>();
        for (int i = 1; i < 1001; i++) { tributesNeededOptions.add(i+""); }
        tooltip = "Modify the number of #yWarriors you need to #yTribute each combat in order to trigger than #yStance change effect. Set to #b1 by default.";
        DuelistDropdown tributesNeededSelector = new DuelistDropdown(tooltip, tributesNeededOptions, Settings.scale * (DuelistMod.xLabPos + 590), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.warriorSynergyTributeNeededToTrigger = i + 1;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("warriorSynergyTributeNeededToTrigger", DuelistMod.warriorSynergyTributeNeededToTrigger);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        tributesNeededSelector.setSelectedIndex(DuelistMod.warriorSynergyTributeNeededToTrigger - 1);


        LINEBREAK(25);

        // warriorTributeEffectTriggersPerCombat - how many times you can trigger the Stance-choose effect
        settingElements.add(new ModLabel("Number of effect triggers per combat", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> effectTriggersOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { effectTriggersOptions.add(i+""); }
        tooltip = "Modify the number of times the #yWarrrior #yTribute effect can be triggered each combat. Set to #b1 by default.";
        DuelistDropdown effectTriggersSelector = new DuelistDropdown(tooltip, effectTriggersOptions, Settings.scale * (DuelistMod.xLabPos + 590), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.warriorTributeEffectTriggersPerCombat = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("warriorTributeEffectTriggersPerCombat", DuelistMod.warriorTributeEffectTriggersPerCombat);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        effectTriggersSelector.setSelectedIndex(DuelistMod.warriorTributeEffectTriggersPerCombat);

        settingElements.add(effectTriggersSelector);
        settingElements.add(tributesNeededSelector);
    }

    private void wyrmPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("No type-specific configurations available", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
    }

    private void zombiePage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("Souls per Tribute", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> soulsOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { soulsOptions.add(i+""); }
        String tooltip = "Modify the amount of #ySouls you gain each time you #yTribute one #yZombie for another. Set to #b1 by default.";
        DuelistDropdown soulsSelector = new DuelistDropdown(tooltip, soulsOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.zombieSouls = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("zombieSouls", DuelistMod.zombieSouls);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        soulsSelector.setSelectedIndex(DuelistMod.zombieSouls);

        LINEBREAK(35);

        // Vampires
        tooltip = "When enabled, each time you play #b" + (DuelistMod.vampiresNeedPlayed + 1) + " #yVampires, #ySiphon #b5 #yTemporary #yHP from all enemies. Enabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Vampire Play Effect", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.vampiresPlayEffect, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.vampiresPlayEffect = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool("vampiresPlayEffect", DuelistMod.vampiresPlayEffect);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));
        ArrayList<String> vampireOptions = new ArrayList<>();
        for (int i = 1; i < 1001; i++) { vampireOptions.add(i+""); }
        tooltip = "Modify the amount of #yVampires you must play before triggering the #ySiphon effect. Set to #b10 by default.";
        DuelistDropdown vampireSelector = new DuelistDropdown(tooltip, vampireOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.vampiresNeedPlayed = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("vampiresNeedPlayed", DuelistMod.vampiresNeedPlayed);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        vampireSelector.setSelectedIndex(DuelistMod.vampiresNeedPlayed);
        LINEBREAK();

        // Mayakashi
        tooltip = "When enabled, each time you play #b" + (DuelistMod.mayakashiNeedPlayed + 1) + " #yMayakashi, apply a random #yDebuff to a random enemy. Enabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Mayakashi Play Effect", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.mayakashiPlayEffect, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.mayakashiPlayEffect = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool("mayakashiPlayEffect", DuelistMod.mayakashiPlayEffect);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));
        ArrayList<String> mayakashiOptions = new ArrayList<>();
        for (int i = 1; i < 1001; i++) { mayakashiOptions.add(i+""); }
        tooltip = "Modify the amount of #yMayakashi you must play before triggering the #yDebuff effect. Set to #b3 by default.";
        DuelistDropdown mayakashiSelector = new DuelistDropdown(tooltip, mayakashiOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.mayakashiNeedPlayed = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("mayakashiNeedPlayed", DuelistMod.mayakashiNeedPlayed);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        mayakashiSelector.setSelectedIndex(DuelistMod.mayakashiNeedPlayed);
        LINEBREAK();

        // Vendread
        tooltip = "When enabled, each time you play #b" + (DuelistMod.vendreadNeedPlayed + 1) + " #yVendread, gain #b1 #yStrength. Enabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Vendread Play Effect", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.vendreadPlayEffect, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.vendreadPlayEffect = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool("vendreadPlayEffect", DuelistMod.vendreadPlayEffect);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));
        ArrayList<String> vendreadOptions = new ArrayList<>();
        for (int i = 1; i < 1001; i++) { vendreadOptions.add(i+""); }
        tooltip = "Modify the amount of #yVendread you must play before triggering the #yStrength gain effect. Set to #b5 by default.";
        DuelistDropdown vendreadSelector = new DuelistDropdown(tooltip, vendreadOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.vendreadNeedPlayed = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("vendreadNeedPlayed", DuelistMod.vendreadNeedPlayed);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        vendreadSelector.setSelectedIndex(DuelistMod.vendreadNeedPlayed);
        LINEBREAK();

        // Shiranui
        tooltip = "When enabled, each time you play #b" + (DuelistMod.shiranuiNeedPlayed + 1) + " #yShiranui, gain #b1 #yDexterity. Enabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Shiranui Play Effect", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.shiranuiPlayEffect, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.shiranuiPlayEffect = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool("shiranuiPlayEffect", DuelistMod.shiranuiPlayEffect);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));
        ArrayList<String> shiranuiOptions = new ArrayList<>();
        for (int i = 1; i < 1001; i++) { shiranuiOptions.add(i+""); }
        tooltip = "Modify the amount of #yShiranui you must play before triggering the #yDexterity gain effect. Set to #b5 by default.";
        DuelistDropdown shirSelector = new DuelistDropdown(tooltip, shiranuiOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.shiranuiNeedPlayed = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("shiranuiNeedPlayed", DuelistMod.shiranuiNeedPlayed);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        shirSelector.setSelectedIndex(DuelistMod.shiranuiNeedPlayed);
        LINEBREAK();

        // Ghostrick
        tooltip = "When enabled, each time you play #b" + (DuelistMod.ghostrickNeedPlayed + 1) + " #yGhostrick, #yResummon a random monster from your #yGraveyard on a random enemy. Enabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Ghostrick Play Effect", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.ghostrickPlayEffect, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.ghostrickPlayEffect = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool("ghostrickPlayEffect", DuelistMod.ghostrickPlayEffect);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));
        ArrayList<String> ghostrickOptions = new ArrayList<>();
        for (int i = 1; i < 1001; i++) { ghostrickOptions.add(i+""); }
        tooltip = "Modify the amount of #yGhostrick you must play before triggering the #yResummon effect. Set to #b10 by default.";
        DuelistDropdown ghostSelector = new DuelistDropdown(tooltip, ghostrickOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.ghostrickNeedPlayed = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("ghostrickNeedPlayed", DuelistMod.ghostrickNeedPlayed);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ghostSelector.setSelectedIndex(DuelistMod.ghostrickNeedPlayed);

        settingElements.add(ghostSelector);
        settingElements.add(shirSelector);
        settingElements.add(vendreadSelector);
        settingElements.add(mayakashiSelector);
        settingElements.add(vampireSelector);
        settingElements.add(soulsSelector);
    }

    private void generateSubPages(ArrayList<IUIElement> settingElements) {
        switch (this.type) {
            case AQUA:
                aquaPage(settingElements);
                break;
            case BUG:
                bugPage(settingElements);
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
            case SPIDER:
                spiderPage(settingElements);
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
            case ROSE:
                rosePage(settingElements);
                break;
            case GIANT:
                giantPage(settingElements);
                break;
            case OJAMA:
                ojamaPage(settingElements);
                break;
            case MAGNET:
                magnetPage(settingElements);
                break;
            default:
                Util.log("Monster type not implemented for config page!");
        }
    }

    static {
        leavesMenuMapping = new HashMap<>();
        vinesMenuMapping = new HashMap<>();
        leavesMenuMappingReverse = new HashMap<>();
        vinesMenuMappingReverse = new HashMap<>();

        int counter = 0;
        for (VinesLeavesMods mod : VinesLeavesMods.values()) {
            if (mod.forLeaves()) {
                leavesMenuMapping.put(counter, mod);
                leavesMenuMappingReverse.put(mod, counter);
                counter++;
            }

        }

        counter = 0;
        for (VinesLeavesMods mod : VinesLeavesMods.values()) {
            if (mod.forVines()) {
                vinesMenuMapping.put(counter, mod);
                vinesMenuMappingReverse.put(mod, counter);
                counter++;
            }
        }
    }
}
