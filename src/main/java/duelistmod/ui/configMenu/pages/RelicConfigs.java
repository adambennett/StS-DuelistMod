package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.persistence.data.RelicConfigurations;
import duelistmod.ui.configMenu.*;

import java.util.ArrayList;
import java.util.Collections;

public class RelicConfigs extends SpecificConfigMenuPageWithJson implements RefreshablePage, SubMenuPage {

    private int currentCardIndex = 0;
    private int maxIndex = -1;
    private DuelistDropdown cardSelector;
    private static final DuelistConfigurationData allCardsPage;
    private boolean isRefreshing;

    public RelicConfigs() {
        super("Relic Settings", "Relics");
        this.config = allCardsPage;
    }

    public ArrayList<IUIElement> getElements() {
        this.setupCardConfigurations();
        this.maxIndex = this.configs.size() - 1;

        ArrayList<String> cards = new ArrayList<>();
        for (DuelistConfigurationData cardConfig : this.configs) { cards.add(cardConfig.displayName()); }
        this.cardSelector = new DuelistDropdown(cards, Settings.scale * (DuelistMod.xLabPos + 100), Settings.scale * (DuelistMod.yPos + 50), (s, i) -> {
            if (this.isRefreshing) {
                this.isRefreshing = false;
                return;
            }
            this.setPage(i);
        });
        if (!this.isRefreshing) {
            this.isRefreshing = true;
            this.setPage(this.currentCardIndex);
        } else {
            this.cardSelector.setSelectedIndex(this.currentCardIndex);
            this.config = this.configs.get(this.currentCardIndex);
        }

        int pagerRightX = (int)(DuelistMod.xLabPos + 420);
        int pagerLeftX = (int)DuelistMod.xLabPos;
        int pagerY = (int)DuelistMod.yPos;
        GeneralPager pager = new GeneralPager(() -> this.setPage(this.currentCardIndex + 1), () -> this.setPage(this.currentCardIndex - 1));
        Pager nextPageBtn = new Pager(DuelistMod.rightArrow, pagerRightX, pagerY, 75, 75, true, pager);
        Pager prevPageBtn = new Pager(DuelistMod.leftArrow, pagerLeftX, pagerY, 75, 75, false, pager);

        LINEBREAK();
        LINEBREAK();

        ArrayList<IUIElement> settingElements = new ArrayList<>(generateSubPages());
        if (this.config != null && this.config.relic() != null && this.config.relic().img != null) {
            this.config.relic().callUpdateDesc();
            this.image = new ModHoverImage(DuelistMod.xLabPos + DuelistMod.xSecondCol + DuelistMod.xThirdCol, pagerY - 15, this.config.relic().img, this.config.relic().getHoverConfigIconTooltip());
            settingElements.add(this.image);
        }
        settingElements.add(this.cardSelector);
        settingElements.add(prevPageBtn);
        settingElements.add(nextPageBtn);

        this.isRefreshing = false;
        return settingElements;
    }

    private void setupCardConfigurations() {
        if (this.configs == null) {
            this.configs = new ArrayList<>();
            this.configs.addAll(DuelistMod.relicConfigurations);
            Collections.sort(this.configs);
            this.configs.add(0, allCardsPage);
        }
    }

    @Override
    public void refresh() {
        this.setPage(0);
    }

    @Override
    public int getCurrentSubPageIndex() {
        return this.currentCardIndex;
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

        this.config = this.configs.get(index);
        this.currentCardIndex = index;
        if (!this.isRefreshing && DuelistMod.paginator != null) {
            this.isRefreshing = true;
            this.cardSelector.setSelectedIndex(index);
            this.isRefreshing = true;
            DuelistMod.paginator.refreshPage(this);

        }
    }

    private ArrayList<IUIElement> generateSubPages() {
        if (this.config.relic() != null) {
            return this.config.relic().getConfigurations().settingElements();
        }
        return this.config.settingElements();
    }

    private static ArrayList<IUIElement> generateAllCardsPage() {
        RESET_Y(); LINEBREAK(); LINEBREAK(); LINEBREAK(); LINEBREAK();
        ArrayList<IUIElement> settingElements = new ArrayList<>();

        // Common
        String tooltip = "When disabled, no Common Duelist relics will spawn during runs. Enabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Enable Common Duelist Relics", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, !settings().getDisableAllCommonRelics(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setDisableAllCommonRelics(!button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        LINEBREAK(25);

        // Uncommon
        tooltip = "When disabled, no Uncommon Duelist relics will spawn during runs. Enabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Enable Uncommon Duelist Relics", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, !settings().getDisableAllUncommonRelics(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setDisableAllUncommonRelics(!button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        LINEBREAK(25);

        // Rare
        tooltip = "When disabled, no Rare Duelist relics will spawn during runs. Enabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Enable Rare Duelist Relics", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, !settings().getDisableAllRareRelics(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setDisableAllRareRelics(!button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        LINEBREAK(25);

        // Boss
        tooltip = "When disabled, no Boss Duelist relics will spawn during runs. Enabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Enable Boss Duelist Relics", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, !settings().getDisableAllBossRelics(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setDisableAllBossRelics(!button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        LINEBREAK(25);

        // Shop
        tooltip = "When disabled, no Shop Duelist relics will spawn during runs. Enabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Enable Shop Duelist Relics", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, !settings().getDisableAllShopRelics(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setDisableAllShopRelics(!button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        return settingElements;
    }

    @Override
    public void resetToDefault() {
        DuelistMod.persistentDuelistData.RelicConfigurations = new RelicConfigurations();
        for (DuelistRelic relic : DuelistMod.allDuelistRelics) {
            DuelistMod.persistentDuelistData.RelicConfigurations.getRelicConfigurations().put(relic.relicId, relic.getDefaultConfig());
        }
    }

    @Override
    public void resetSubPageToDefault() {
        if (this.config.relic() != null) {
            DuelistMod.persistentDuelistData.RelicConfigurations.getRelicConfigurations().put(this.config.relic().relicId, this.config.relic().getDefaultConfig());
        } else {
            DuelistMod.persistentDuelistData.RelicConfigurations.setDisableAllCommonRelics(false);
            DuelistMod.persistentDuelistData.RelicConfigurations.setDisableAllUncommonRelics(false);
            DuelistMod.persistentDuelistData.RelicConfigurations.setDisableAllRareRelics(false);
            DuelistMod.persistentDuelistData.RelicConfigurations.setDisableAllBossRelics(false);
            DuelistMod.persistentDuelistData.RelicConfigurations.setDisableAllShopRelics(false);
        }
    }

    @Override
    public String getSubMenuPageName() {
        return this.config.relic() == null ? "Global Duelist Relic" : this.config.relic().name;
    }

    @Override
    public boolean hasSubMenuPageSettings() {
        return true;
    }

    private static RelicConfigurations settings() { return DuelistMod.persistentDuelistData.RelicConfigurations; }

    static {
        allCardsPage = new DuelistConfigurationData("All Duelist Relics", generateAllCardsPage());
    }
}
