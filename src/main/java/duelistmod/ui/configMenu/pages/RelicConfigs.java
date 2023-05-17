package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import duelistmod.DuelistMod;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.ui.configMenu.*;

import java.util.ArrayList;
import java.util.Collections;

public class RelicConfigs extends SpecificConfigMenuPageWithJson implements RefreshablePage {

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

    private void setPage(int index) {
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
        settingElements.add(new DuelistLabeledToggleButton("Enable Common Duelist Relics", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, !DuelistMod.disableAllCommonRelics, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.disableAllCommonRelics = !button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool("disableAllCommonRelics", DuelistMod.disableAllCommonRelics);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        LINEBREAK(25);

        // Uncommon
        tooltip = "When disabled, no Uncommon Duelist relics will spawn during runs. Enabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Enable Uncommon Duelist Relics", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, !DuelistMod.disableAllUncommonRelics, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.disableAllUncommonRelics = !button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool("disableAllUncommonRelics", DuelistMod.disableAllUncommonRelics);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        LINEBREAK(25);

        // Rare
        tooltip = "When disabled, no Rare Duelist relics will spawn during runs. Enabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Enable Rare Duelist Relics", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, !DuelistMod.disableAllRareRelics, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.disableAllRareRelics = !button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool("disableAllRareRelics", DuelistMod.disableAllRareRelics);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        LINEBREAK(25);

        // Boss
        tooltip = "When disabled, no Boss Duelist relics will spawn during runs. Enabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Enable Boss Duelist Relics", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, !DuelistMod.disableAllBossRelics, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.disableAllBossRelics = !button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool("disableAllBossRelics", DuelistMod.disableAllBossRelics);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        LINEBREAK(25);

        // Shop
        tooltip = "When disabled, no Shop Duelist relics will spawn during runs. Enabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Enable Shop Duelist Relics", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, !DuelistMod.disableAllShopRelics, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.disableAllShopRelics = !button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool("disableAllShopRelics", DuelistMod.disableAllShopRelics);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        return settingElements;
    }

    static {
        allCardsPage = new DuelistConfigurationData("All Duelist Relics", generateAllCardsPage());
    }
}
