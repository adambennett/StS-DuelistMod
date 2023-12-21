package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import basemod.ModImage;
import basemod.ModLabel;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import duelistmod.DuelistMod;
import duelistmod.dto.MonsterTypeConfigData;
import duelistmod.enums.MonsterType;
import duelistmod.enums.VinesLeavesMod;
import duelistmod.helpers.Util;
import duelistmod.persistence.data.MonsterTypeConfigurations;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.DuelistLabeledToggleButton;
import duelistmod.ui.configMenu.GeneralPager;
import duelistmod.ui.configMenu.Pager;
import duelistmod.ui.configMenu.RefreshablePage;
import duelistmod.ui.configMenu.SpecificConfigMenuPage;
import duelistmod.ui.configMenu.SubMenuPage;

import java.util.ArrayList;
import java.util.HashMap;

public class MonsterTypeConfigs extends SpecificConfigMenuPage implements RefreshablePage, SubMenuPage {
    
    private MonsterType type = MonsterType.AQUA;
    private int currentTypeIndex = 0;
    private int maxIndex = -1;
    private DuelistDropdown typeSelector;
    private boolean isRefreshing;
    private boolean noSettingsImplemented = false;

    public static final HashMap<Integer, VinesLeavesMod> leavesMenuMapping;
    public static final HashMap<Integer, VinesLeavesMod> vinesMenuMapping;
    public static final HashMap<VinesLeavesMod, Integer> leavesMenuMappingReverse;
    public static final HashMap<VinesLeavesMod, Integer> vinesMenuMappingReverse;

    public MonsterTypeConfigs() {
        super("Monster Type Settings", "Monster Type");
    }

    public ArrayList<IUIElement> getElements() {
        this.maxIndex = MonsterType.values().length - 1;

        ArrayList<String> types = new ArrayList<>();
        for (MonsterType type : MonsterType.values()) { types.add(type.displayText()); }
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
            this.type = MonsterType.menuMapping.get(this.currentTypeIndex);
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
        this.noSettingsImplemented = false;
        if (!settingElements.isEmpty() && settingElements.get(0) instanceof ModLabel) {
            ModLabel label = (ModLabel) settingElements.get(0);
            if (label.text.equals("No type-specific configurations available")) {
                this.noSettingsImplemented = true;
            }
        }

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

    @Override
    public int getCurrentSubPageIndex() {
        return this.currentTypeIndex;
    }

    @Override
    public void setPage(int index) {
        if (DuelistMod.openDropdown != null) {
            DuelistMod.openDropdown.close();
        }
        if (index > this.maxIndex) {
            index = 0;
        }
        if (index < 0) {
            index = this.maxIndex;
        }

        this.type = MonsterType.menuMapping.get(index);
        this.currentTypeIndex = index;
        if (!this.isRefreshing && DuelistMod.paginator != null) {
            this.isRefreshing = true;
            this.typeSelector.setSelectedIndex(index);
            this.isRefreshing = true;
            DuelistMod.paginator.refreshPage(this);
        }
    }

    private void put(String key, Object value) {
        settings().getTypeConfigurations().get(this.type).put(key, value);
    }

    private String getSelected(String key, Object defaultValue) {
        return this.type.getConfig(key, defaultValue).toString();
    }

    private void aquaPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("Summon Increase per Tribute", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> summonTributeOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { summonTributeOptions.add(i+""); }
        String tooltip = "Modify the amount of #ySummons gained each time you #yTribute one #yAqua for another. Set to #b1 by default.";
        DuelistDropdown summonTributeSelector = new DuelistDropdown(tooltip, summonTributeOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            put(MonsterType.aquaSummonKey, i);
            DuelistMod.configSettingsLoader.save();
        });
        summonTributeSelector.setSelected(getSelected(MonsterType.aquaSummonKey, MonsterType.aquaDefaultSummon));
        settingElements.add(summonTributeSelector);
    }

    private void beastPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("Increment Bonus on Summon", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> summonTributeOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { summonTributeOptions.add(i+""); }
        String tooltip = "Modify the amount of #yMax #ySummons gained each time you #ySummon a #yBeast with all your zones filled. Set to #b" + this.type.getDefValue(MonsterType.beastIncKey) + " by default.";
        DuelistDropdown summonTributeSelector = new DuelistDropdown(tooltip, summonTributeOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            put(MonsterType.beastIncKey, i);
            DuelistMod.configSettingsLoader.save();
        });
        summonTributeSelector.setSelected(getSelected(MonsterType.beastIncKey, MonsterType.beastDefaultInc));

        LINEBREAK(25);

        settingElements.add(new ModLabel("Fang Loss at Turn Start", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> fangLossOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { fangLossOptions.add(i+""); }
        tooltip = "Amount of #yFangs to lose at the start of turn. Set to #b" + this.type.getDefValue(MonsterType.beastFangLossKey) + " by default.";
        DuelistDropdown fangLossSelector = new DuelistDropdown(tooltip, fangLossOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            put(MonsterType.beastFangLossKey, i);
            DuelistMod.configSettingsLoader.save();
        });
        fangLossSelector.setSelected(getSelected(MonsterType.beastFangLossKey, MonsterType.beastDefaultFangLoss));

        settingElements.add(fangLossSelector);
        settingElements.add(summonTributeSelector);
    }

    private void bugPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("Number of Bugs to trigger", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));

        ArrayList<String> numberOfBugsOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { numberOfBugsOptions.add(i+""); }
        String tooltip = "The number of #yBugs you need to play each combat to trigger the #yTemporary #yHP gain effect. Set to #b2 by default.";
        DuelistDropdown numberOfBugsSelector = new DuelistDropdown(tooltip, numberOfBugsOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            put(MonsterType.bugNumberKey, i);
            DuelistMod.configSettingsLoader.save();
        });
        numberOfBugsSelector.setSelected(getSelected(MonsterType.bugNumberKey, MonsterType.bugDefaultNumber));

        LINEBREAK(25);

        settingElements.add(new ModLabel("Temporary HP Gain Amount", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> tempHpAmountOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { tempHpAmountOptions.add(i+""); }
        tooltip = "Amount of #yTemporary #yHP to gain whenever you play enough #yBugs in combat. Set to #b5 by default.";
        DuelistDropdown tempHpAmountSelector = new DuelistDropdown(tooltip, tempHpAmountOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            put(MonsterType.bugTempHpKey, i);
            DuelistMod.configSettingsLoader.save();
        });
        tempHpAmountSelector.setSelected(getSelected(MonsterType.bugTempHpKey, MonsterType.bugDefaultTempHp));

        LINEBREAK(45);

        tooltip = "When enabled, after gaining #yTemporary #yHP after playing enough #yBugs, the counter is reset so you can trigger the effect again. Disabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Temp HP effect resets after triggering", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.getMonsterSetting(MonsterType.BUG, MonsterType.bugResetKey, MonsterType.bugDefaultReset), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            put(MonsterType.bugResetKey, button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        settingElements.add(tempHpAmountSelector);
        settingElements.add(numberOfBugsSelector);
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
            put(MonsterType.dragonScalesKey, i);
            DuelistMod.configSettingsLoader.save();
        });
        dragonScalesSelector.setSelected(getSelected(MonsterType.dragonScalesKey, MonsterType.dragonDefaultScales));
        settingElements.add(new ModLabel("DragonScales", (DuelistMod.xLabPos + 120 + 150), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));

        LINEBREAK();

        settingElements.add(new ModLabel("increases the Block and damage of Dragons by", DuelistMod.xLabPos, DuelistMod.yPos, DuelistMod.settingsPanel, (me)->{}));

        ArrayList<String> mods = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { mods.add(String.valueOf(i)); }
        tooltip = "Set to #b1 by default.";
        DuelistDropdown modSelector = new DuelistDropdown(tooltip, mods, Settings.scale * (DuelistMod.xLabPos + 710), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            put(MonsterType.dragonBlkDmgKey, i);
            DuelistMod.configSettingsLoader.save();
        });
        modSelector.setSelected(getSelected(MonsterType.dragonBlkDmgKey, MonsterType.dragonDefaultBlkDmg));

        LINEBREAK(65);

        settingElements.add(new ModLabel("DragonScales per Tribute", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));

        ArrayList<String> dragonScalesAmountOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { dragonScalesAmountOptions.add(i+""); }
        tooltip = "Modify the amount of #yDragonScales gained each time you #yTribute one #yDragon for another. Set to #b2 by default.";
        DuelistDropdown dragonScalesAmountSelector = new DuelistDropdown(tooltip, dragonScalesAmountOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            put(MonsterType.dragonTributeScalesKey, i);
            DuelistMod.configSettingsLoader.save();
        });
        dragonScalesAmountSelector.setSelected(getSelected(MonsterType.dragonTributeScalesKey, MonsterType.dragonDefaultTributeScales));

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
            put(MonsterType.fiendCardsKey, i);
            DuelistMod.configSettingsLoader.save();
        });
        summonTributeSelector.setSelected(getSelected(MonsterType.fiendCardsKey, MonsterType.fiendDefaultCards));
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
            put(MonsterType.insectPosionKey, i);
            DuelistMod.configSettingsLoader.save();
        });
        poisonSelector.setSelected(getSelected(MonsterType.insectPosionKey, MonsterType.insectDefaultPoison));
        settingElements.add(poisonSelector);
    }

    private void machinePage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("Artifacts per Tribute", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> artifactOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { artifactOptions.add(i+""); }
        String tooltip = "Modify the amount of #yArtifacts you gain every other time you #yTribute one #yMachine for another. Set to #b1 by default.";
        DuelistDropdown artifactSelector = new DuelistDropdown(tooltip, artifactOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            put(MonsterType.machineArtifactsKey, i);
            DuelistMod.configSettingsLoader.save();
        });
        artifactSelector.setSelected(getSelected(MonsterType.machineArtifactsKey, MonsterType.machineDefaultArtifacts));
        settingElements.add(artifactSelector);
    }

    private void magnetPage(ArrayList<IUIElement> settingElements) {
        String tooltip = "When enabled, a random #yMagnet card will be added to your starting deck each run. Disabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Add random Magnet to starting deck", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.getMonsterSetting(MonsterType.MAGNET, MonsterType.magnetDeckKey, MonsterType.magnetDefaultDeck), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            put(MonsterType.magnetDeckKey, button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        LINEBREAK(25);

        tooltip = "When enabled alongside 'Add random Magnet to starting deck', various powerful #yMagnet cards such as the electrified versions will be able to be added to your deck. If this is disabled, only the standard #b4 #yMagnet cards can be added. Disabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Allow adding random Super Magnet", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.getMonsterSetting(MonsterType.MAGNET, MonsterType.magnetSuperKey, MonsterType.magnetDefaultSuper), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            put(MonsterType.magnetSuperKey, button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));
    }

    private void megatypePage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("No type-specific configurations available", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
    }

    private void naturiaPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("Whenever you gain Leaves", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));

        ArrayList<String> leavesOptions = new ArrayList<>();
        for (VinesLeavesMod mod : VinesLeavesMod.values()) {
            if (mod.forLeaves()) {
                leavesOptions.add(mod.displayText());
            }
        }
        String tooltip = "";
        DuelistDropdown leavesSelector = new DuelistDropdown(tooltip, leavesOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            put(MonsterType.naturiaLeavesActionKey, leavesMenuMapping.get(i).displayText());
            DuelistMod.configSettingsLoader.save();
        });
        leavesSelector.setSelected(getSelected(MonsterType.naturiaLeavesActionKey, MonsterType.naturiaDefaultLeavesAction));

        LINEBREAK(25);

        settingElements.add(new ModLabel("Whenever you gain Vines", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));

        ArrayList<String> vinesOptions = new ArrayList<>();
        for (VinesLeavesMod mod : VinesLeavesMod.values()) {
            if (mod.forVines()) {
                vinesOptions.add(mod.displayText());
            }
        }
        tooltip = "";
        DuelistDropdown vinesSelector = new DuelistDropdown(tooltip, vinesOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            put(MonsterType.naturiaVinesActionKey, vinesMenuMapping.get(i).displayText());
            DuelistMod.configSettingsLoader.save();
        });
        vinesSelector.setSelected(getSelected(MonsterType.naturiaVinesActionKey, MonsterType.naturiaDefaultVinesAction));

        LINEBREAK(25);

        settingElements.add(new ModLabel("Bonus Vines Damage", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));

        ArrayList<String> baseVinesDamageOptions = new ArrayList<>();
        for (int i = 0; i < 5001; i++) { baseVinesDamageOptions.add(i+""); }
        tooltip = "Deal extra damage with #yVines. Set to #b0 by default.";
        DuelistDropdown vinesDmgSelector = new DuelistDropdown(tooltip, baseVinesDamageOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            put(MonsterType.naturiaVinesDmgKey, i);
            DuelistMod.configSettingsLoader.save();
        });
        vinesDmgSelector.setSelected(getSelected(MonsterType.naturiaVinesDmgKey, MonsterType.naturiaDefaultVinesDmg));

        LINEBREAK(25);

        settingElements.add(new ModLabel("Leaves to Trigger", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));

        ArrayList<String> leavesNeededOptions = new ArrayList<>();
        for (int i = 0; i < 5001; i++) { leavesNeededOptions.add(i+""); }
        tooltip = "Number of #yLeaves needed to allow you to trigger the #yBlock effect on your turn. Set to #b5 by default.";
        DuelistDropdown leavesNeededSelector = new DuelistDropdown(tooltip, leavesNeededOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            put(MonsterType.naturiaLeavesAmtKey, i);
            DuelistMod.configSettingsLoader.save();
        });
        leavesNeededSelector.setSelected(getSelected(MonsterType.naturiaLeavesAmtKey, MonsterType.naturiaDefaultLeavesAmt));

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
            put(MonsterType.plantConstrictedKey, i);
            DuelistMod.configSettingsLoader.save();
        });
        constrictedSelctor.setSelected(getSelected(MonsterType.plantConstrictedKey, MonsterType.plantDefaultConstricted));
        settingElements.add(constrictedSelctor);
    }

    private void predaplantPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("Thorns per Tribute", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> thornsOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { thornsOptions.add(i+""); }
        String tooltip = "Modify the amount of #yThorns you gain each time you #yTribute one #yPredaplant for another. Set to #b1 by default.";
        DuelistDropdown thornsSelector = new DuelistDropdown(tooltip, thornsOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            put(MonsterType.predaplantThornsKey, i);
            DuelistMod.configSettingsLoader.save();
        });
        thornsSelector.setSelected(getSelected(MonsterType.predaplantThornsKey, MonsterType.predaplantDefaultThorns));
        settingElements.add(thornsSelector);
    }

    private void rockPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("Rock Block", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));

        ArrayList<String> blockOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { blockOptions.add(i+""); }
        String tooltip = "Modify the amount of #yBlock you gain for each #yRock you have #ySummoned at the end of turn. Set to #b2 by default.";
        DuelistDropdown blockSelector = new DuelistDropdown(tooltip, blockOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            put(MonsterType.rockBlockKey, i);
            DuelistMod.configSettingsLoader.save();
        });
        blockSelector.setSelected(getSelected(MonsterType.rockBlockKey, MonsterType.rockDefaultBlock));

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
            put(MonsterType.spiderNumberKey, i);
            DuelistMod.configSettingsLoader.save();
        });
        numberOfSpidersSelector.setSelected(getSelected(MonsterType.spiderNumberKey, MonsterType.spiderDefaultNumber));

        LINEBREAK(25);

        settingElements.add(new ModLabel("Temporary HP Gain Amount", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> tempHpAmountOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { tempHpAmountOptions.add(i+""); }
        tooltip = "Amount of #yTemporary #yHP to gain whenever you play enough #ySpiders in combat. Set to #b7 by default.";
        DuelistDropdown tempHpAmountSelector = new DuelistDropdown(tooltip, tempHpAmountOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            put(MonsterType.spiderTempHpKey, i);
            DuelistMod.configSettingsLoader.save();
        });
        tempHpAmountSelector.setSelected(getSelected(MonsterType.spiderTempHpKey, MonsterType.spiderDefaultTempHp));

        LINEBREAK(45);

        tooltip = "When enabled, after gaining #yTemporary #yHP after playing enough #ySpiders, the counter is reset so you can trigger the effect again. Disabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Temp HP effect resets after triggering", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.getMonsterSetting(MonsterType.SPIDER, MonsterType.spiderResetKey, MonsterType.spiderDefaultReset), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            put(MonsterType.spiderResetKey, button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        settingElements.add(tempHpAmountSelector);
        settingElements.add(numberOfSpidersSelector);
    }

    private void spellcasterPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("Block on Attack", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> blockOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { blockOptions.add(i+""); }
        String tooltip = "Modify the amount of #yBlock you gain each time you attack with only #ySpellcasters #ysummoned. Set to #b4 by default.";
        DuelistDropdown blockSelector = new DuelistDropdown(tooltip, blockOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            put(MonsterType.spellcasterBlockKey, i);
            DuelistMod.configSettingsLoader.save();
        });
        blockSelector.setSelected(getSelected(MonsterType.spellcasterBlockKey, MonsterType.spellcasterDefaultBlock));
        settingElements.add(blockSelector);
    }

    private void superheavyPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("Dexterity per Tribute", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> dexOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { dexOptions.add(i+""); }
        String tooltip = "Modify the amount of #yDexterity you gain each time you #yTribute one #ySuperheavy for another. Set to #b1 by default.";
        DuelistDropdown dexSelector = new DuelistDropdown(tooltip, dexOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            put(MonsterType.superheavyDexKey, i);
            DuelistMod.configSettingsLoader.save();
        });
        dexSelector.setSelected(getSelected(MonsterType.superheavyDexKey, MonsterType.superheavyDefaultDex));
        settingElements.add(dexSelector);
    }

    private void toonPage(ArrayList<IUIElement> settingElements) {
        settingElements.add(new ModLabel("Vulnerable per Tribute", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> thornsOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { thornsOptions.add(i+""); }
        String tooltip = "Modify the amount of #yVulnerable you apply each time you #yTribute one #yToon for another. Set to #b1 by default.";
        DuelistDropdown thornsSelector = new DuelistDropdown(tooltip, thornsOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            put(MonsterType.toonVulnKey, i);
            DuelistMod.configSettingsLoader.save();
        });
        thornsSelector.setSelected(getSelected(MonsterType.toonVulnKey, MonsterType.toonDefaultVuln));
        settingElements.add(thornsSelector);
    }

    private void warriorPage(ArrayList<IUIElement> settingElements) {
        int amt = DuelistMod.getMonsterSetting(MonsterType.WARRIOR, MonsterType.warriorTriggersPerCombatKey, MonsterType.warriorDefaultTriggersPerCombat);
        String sMod = amt != 1 ? "s" : "";
        String tooltip = "When enabled, #yTributing a #yWarrior for another will allow you to choose any #yStance to enter. Limited to #b" + amt + " trigger" + sMod + " per combat. Enabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Enable Warrior Tribute Effect", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.getMonsterSetting(MonsterType.WARRIOR, MonsterType.warriorEnableKey, MonsterType.warriorDefaultEnable), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            put(MonsterType.warriorEnableKey, button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        LINEBREAK(25);

        // warriorSynergyTributeNeededToTrigger - how many times you need to tribute to trigger the stance change effect
        settingElements.add(new ModLabel("Number of Tributes to trigger effect", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> tributesNeededOptions = new ArrayList<>();
        for (int i = 1; i < 1001; i++) { tributesNeededOptions.add(i+""); }
        tooltip = "Modify the number of #yWarriors you need to #yTribute each combat in order to trigger than #yStance change effect. Set to #b1 by default.";
        DuelistDropdown tributesNeededSelector = new DuelistDropdown(tooltip, tributesNeededOptions, Settings.scale * (DuelistMod.xLabPos + 590), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            put(MonsterType.warriorNumTributesKey, i + 1);
            DuelistMod.configSettingsLoader.save();
        });
        tributesNeededSelector.setSelected(getSelected(MonsterType.warriorNumTributesKey, MonsterType.warriorDefaultNumTributes));

        LINEBREAK(25);

        // warriorTributeEffectTriggersPerCombat - how many times you can trigger the Stance-choose effect
        settingElements.add(new ModLabel("Number of effect triggers per combat", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        ArrayList<String> effectTriggersOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { effectTriggersOptions.add(i+""); }
        tooltip = "Modify the number of times the #yWarrrior #yTribute effect can be triggered each combat. Set to #b1 by default.";
        DuelistDropdown effectTriggersSelector = new DuelistDropdown(tooltip, effectTriggersOptions, Settings.scale * (DuelistMod.xLabPos + 590), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            put(MonsterType.warriorTriggersPerCombatKey, i);
            DuelistMod.configSettingsLoader.save();
        });
        effectTriggersSelector.setSelected(getSelected(MonsterType.warriorTriggersPerCombatKey, MonsterType.warriorDefaultTriggersPerCombat));

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
        String tooltip = "Modify the amount of #ySouls you gain each time you #yTribute one #yZombie for another. Set to #b" + MonsterType.zombieDefaultSouls + " by default.";
        DuelistDropdown soulsSelector = new DuelistDropdown(tooltip, soulsOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            put(MonsterType.zombieSoulsKey, i);
            DuelistMod.configSettingsLoader.save();
        });
        soulsSelector.setSelected(getSelected(MonsterType.zombieSoulsKey, MonsterType.zombieDefaultSouls));

        LINEBREAK(35);

        // Vampires
        int amt = DuelistMod.getMonsterSetting(MonsterType.ZOMBIE, MonsterType.zombieVampireNumKey, MonsterType.zombieVampireDefaultNum);
        String vs = amt == 1 ? "" : "s";
        tooltip = "When enabled, each time you play #b" + DuelistMod.getMonsterSetting(MonsterType.ZOMBIE, MonsterType.zombieVampireNumKey, MonsterType.zombieVampireDefaultNum) + " #yVampire" + vs + ", #ySiphon #b5 #yTemporary #yHP from all enemies. Enabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Vampire Play Effect", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.getMonsterSetting(MonsterType.ZOMBIE, MonsterType.zombieVampireEffectKey, MonsterType.zombieVampireDefaultEffect), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            put(MonsterType.zombieVampireEffectKey, button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));
        ArrayList<String> vampireOptions = new ArrayList<>();
        for (int i = 1; i < 1001; i++) { vampireOptions.add(i+""); }
        tooltip = "Modify the amount of #yVampires you must play before triggering the #ySiphon effect. Set to #b" + MonsterType.zombieVampireDefaultNum + " by default.";
        DuelistDropdown vampireSelector = new DuelistDropdown(tooltip, vampireOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), 10, (s, i) -> {
            put(MonsterType.zombieVampireNumKey, i + 1);
            DuelistMod.configSettingsLoader.save();
        });
        vampireSelector.setSelected(getSelected(MonsterType.zombieVampireNumKey, MonsterType.zombieVampireDefaultNum));
        LINEBREAK();

        // Mayakashi
        tooltip = "When enabled, each time you play #b" + DuelistMod.getMonsterSetting(MonsterType.ZOMBIE, MonsterType.zombieMayakashiNumKey, MonsterType.zombieMayakashiDefaultNum) + " #yMayakashi, apply a random #yDebuff to a random enemy. Enabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Mayakashi Play Effect", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.getMonsterSetting(MonsterType.ZOMBIE, MonsterType.zombieMayakashiEffectKey, MonsterType.zombieMayakashiDefaultEffect), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            put(MonsterType.zombieMayakashiEffectKey, button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));
        ArrayList<String> mayakashiOptions = new ArrayList<>();
        for (int i = 1; i < 1001; i++) { mayakashiOptions.add(i+""); }
        tooltip = "Modify the amount of #yMayakashi you must play before triggering the #yDebuff effect. Set to #b" + MonsterType.zombieMayakashiDefaultNum + " by default.";
        DuelistDropdown mayakashiSelector = new DuelistDropdown(tooltip, mayakashiOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), 10, (s, i) -> {
            put(MonsterType.zombieMayakashiNumKey, i + 1);
            DuelistMod.configSettingsLoader.save();
        });
        mayakashiSelector.setSelected(getSelected(MonsterType.zombieMayakashiNumKey, MonsterType.zombieMayakashiDefaultNum));
        LINEBREAK();

        // Vendread
        tooltip = "When enabled, each time you play #b" + DuelistMod.getMonsterSetting(MonsterType.ZOMBIE, MonsterType.zombieVendreadNumKey, MonsterType.zombieVendreadDefaultNum) + " #yVendread, gain #b1 #yStrength. Enabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Vendread Play Effect", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.getMonsterSetting(MonsterType.ZOMBIE, MonsterType.zombieVendreadEffectKey, MonsterType.zombieVendreadDefaultEffect), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            put(MonsterType.zombieVendreadEffectKey, button.enabled);
            DuelistMod.configSettingsLoader.save();

        }));
        ArrayList<String> vendreadOptions = new ArrayList<>();
        for (int i = 1; i < 1001; i++) { vendreadOptions.add(i+""); }
        tooltip = "Modify the amount of #yVendread you must play before triggering the #yStrength gain effect. Set to #b" + MonsterType.zombieVendreadDefaultNum + " by default.";
        DuelistDropdown vendreadSelector = new DuelistDropdown(tooltip, vendreadOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), 10, (s, i) -> {
            put(MonsterType.zombieVendreadNumKey, i + 1);
            DuelistMod.configSettingsLoader.save();
        });
        vendreadSelector.setSelected(getSelected(MonsterType.zombieVendreadNumKey, MonsterType.zombieVendreadDefaultNum));
        LINEBREAK();

        // Shiranui
        tooltip = "When enabled, each time you play #b" + DuelistMod.getMonsterSetting(MonsterType.ZOMBIE, MonsterType.zombieShiranuiNumKey, MonsterType.zombieShiranuiDefaultNum) + " #yShiranui, gain #b1 #yDexterity. Enabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Shiranui Play Effect", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.getMonsterSetting(MonsterType.ZOMBIE, MonsterType.zombieShiranuiEffectKey, MonsterType.zombieShiranuiDefaultEffect), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            put(MonsterType.zombieShiranuiEffectKey, button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));
        ArrayList<String> shiranuiOptions = new ArrayList<>();
        for (int i = 1; i < 1001; i++) { shiranuiOptions.add(i+""); }
        tooltip = "Modify the amount of #yShiranui you must play before triggering the #yDexterity gain effect. Set to #b" + MonsterType.zombieShiranuiDefaultNum + " by default.";
        DuelistDropdown shirSelector = new DuelistDropdown(tooltip, shiranuiOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), 10, (s, i) -> {
            put(MonsterType.zombieShiranuiNumKey, i + 1);
            DuelistMod.configSettingsLoader.save();
        });
        shirSelector.setSelected(getSelected(MonsterType.zombieShiranuiNumKey, MonsterType.zombieShiranuiDefaultNum));
        LINEBREAK();

        // Ghostrick
        tooltip = "When enabled, each time you play #b" + DuelistMod.getMonsterSetting(MonsterType.ZOMBIE, MonsterType.zombieGhostrickNumKey, MonsterType.zombieGhostrickDefaultNum) + " #yGhostrick, #ySpecial #ySummon a random monster from your #yGraveyard on a random enemy. Enabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Ghostrick Play Effect", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.getMonsterSetting(MonsterType.ZOMBIE, MonsterType.zombieGhostrickEffectKey, MonsterType.zombieGhostrickDefaultEffect), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            put(MonsterType.zombieGhostrickEffectKey, button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));
        ArrayList<String> ghostrickOptions = new ArrayList<>();
        for (int i = 1; i < 1001; i++) { ghostrickOptions.add(i+""); }
        tooltip = "Modify the amount of #yGhostrick you must play before triggering the #ySpecial #ySummon effect. Set to #b" + MonsterType.zombieGhostrickDefaultNum + " by default.";
        DuelistDropdown ghostSelector = new DuelistDropdown(tooltip, ghostrickOptions, Settings.scale * (DuelistMod.xLabPos + 490), Settings.scale * (DuelistMod.yPos + 22), 10, (s, i) -> {
            put(MonsterType.zombieGhostrickNumKey, i + 1);
            DuelistMod.configSettingsLoader.save();
        });
        ghostSelector.setSelected(getSelected(MonsterType.zombieGhostrickNumKey, MonsterType.zombieGhostrickDefaultNum));

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
            case BEAST:
                beastPage(settingElements);
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
        for (VinesLeavesMod mod : VinesLeavesMod.values()) {
            if (mod.forLeaves()) {
                leavesMenuMapping.put(counter, mod);
                leavesMenuMappingReverse.put(mod, counter);
                counter++;
            }

        }

        counter = 0;
        for (VinesLeavesMod mod : VinesLeavesMod.values()) {
            if (mod.forVines()) {
                vinesMenuMapping.put(counter, mod);
                vinesMenuMappingReverse.put(mod, counter);
                counter++;
            }
        }
    }

    @Override
    public void resetToDefault() {
        DuelistMod.persistentDuelistData.MonsterTypeConfigurations = new MonsterTypeConfigurations();
        for (MonsterType type : MonsterType.values()) {
            MonsterTypeConfigData baseConfig = type.getDefaultConfig();
            if (baseConfig != null) {
                settings().getTypeConfigurations().put(type, baseConfig);
            }
        }
    }

    @Override
    public void resetSubPageToDefault() {
        settings().getTypeConfigurations().put(this.type, this.type.getDefaultConfig());
    }

    @Override
    public String getSubMenuPageName() {
        return this.hasSubMenuPageSettings() ? this.type.displayText() : "";
    }

    @Override
    public boolean hasSubMenuPageSettings() {
        return this.type != null && !this.noSettingsImplemented;
    }

    private static MonsterTypeConfigurations settings() {
        return DuelistMod.persistentDuelistData.MonsterTypeConfigurations;
    }

}
