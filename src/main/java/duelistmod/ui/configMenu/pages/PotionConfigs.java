package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import duelistmod.DuelistMod;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.persistence.data.PotionConfigurations;
import duelistmod.ui.configMenu.*;

import java.util.ArrayList;
import java.util.Collections;

public class PotionConfigs extends SpecificConfigMenuPageWithJson implements RefreshablePage, SubMenuPage {

    private int currentCardIndex = 0;
    private int maxIndex = -1;
    private DuelistDropdown cardSelector;
    private static final DuelistConfigurationData allCardsPage;
    private boolean isRefreshing;

    public PotionConfigs() {
        super("Potion Settings", "Potions");
        this.config = allCardsPage;
    }

    public ArrayList<IUIElement> getElements() {
        this.setupCardConfigurations();
        this.maxIndex = this.configs.size() - 1;

        ArrayList<String> cards = new ArrayList<>();
        for (DuelistConfigurationData cardConfig : this.configs) { cards.add(cardConfig.displayName()); }
        this.cardSelector = new DuelistDropdown(cards, Settings.scale * (DuelistMod.xLabPos + 95), Settings.scale * (DuelistMod.yPos + 50), (s, i) -> {
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

        int pagerRightX = (int)(DuelistMod.xLabPos + 385);
        int pagerLeftX = (int)DuelistMod.xLabPos;
        int pagerY = (int)DuelistMod.yPos;
        GeneralPager pager = new GeneralPager(() -> this.setPage(this.currentCardIndex + 1), () -> this.setPage(this.currentCardIndex - 1));
        Pager nextPageBtn = new Pager(DuelistMod.rightArrow, pagerRightX, pagerY, 75, 75, true, pager);
        Pager prevPageBtn = new Pager(DuelistMod.leftArrow, pagerLeftX, pagerY, 75, 75, false, pager);

        LINEBREAK();
        LINEBREAK();

        ArrayList<IUIElement> settingElements = new ArrayList<>(generateSubPages());
        if (this.config != null && this.config.potion() != null) {
            this.config.potion().callUpdateDesc();
            this.image = new ModHoverImage(DuelistMod.xLabPos + DuelistMod.xSecondCol + DuelistMod.xThirdCol, pagerY + 10, this.config.potion(), this.config.potion().getHoverConfigIconTooltip());
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
            this.configs.addAll(DuelistMod.potionConfigurations);
            Collections.sort(this.configs);
            this.configs.add(0, allCardsPage);
        }
    }

    @Override
    public void refresh() {
        this.setPage(0);
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
        if (this.config.potion() != null) {
            return this.config.potion().getConfigurations().settingElements();
        }
        return this.config.settingElements();
    }

    private static ArrayList<IUIElement> generateAllCardsPage() {
        RESET_Y(); LINEBREAK(); LINEBREAK(); LINEBREAK(); LINEBREAK();
        ArrayList<IUIElement> settingElements = new ArrayList<>();

        // Common
        String tooltip = "When disabled, no Common Duelist potions will spawn during runs. Enabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Enable Common Duelist Potions", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, !settings().getDisableAllCommonPotions(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setDisableAllCommonPotions(!button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        LINEBREAK(25);

        // Uncommon
        tooltip = "When disabled, no Uncommon Duelist potions will spawn during runs. Enabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Enable Uncommon Duelist Potions", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, !settings().getDisableAllUncommonPotions(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setDisableAllUncommonPotions(!button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        LINEBREAK(25);

        // Rare
        tooltip = "When disabled, no Rare Duelist potions will spawn during runs. Enabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Enable Rare Duelist Potions", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, !settings().getDisableAllRarePotions(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setDisableAllRarePotions(!button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        return settingElements;
    }

    @Override
    public void resetToDefault() {
        DuelistMod.persistentDuelistData.PotionConfigurations = new PotionConfigurations();
    }

    @Override
    public void resetSubPageToDefault() {
        if (this.config.potion() != null) {
            DuelistMod.persistentDuelistData.PotionConfigurations.getPotionConfigurations().put(this.config.potion().ID, this.config.potion().getDefaultConfig());
        } else {
            DuelistMod.persistentDuelistData.PotionConfigurations.setDisableAllCommonPotions(false);
            DuelistMod.persistentDuelistData.PotionConfigurations.setDisableAllUncommonPotions(false);
            DuelistMod.persistentDuelistData.PotionConfigurations.setDisableAllRarePotions(false);
        }
    }

    @Override
    public int getCurrentSubPageIndex() {
        return this.currentCardIndex;
    }

    @Override
    public String getSubMenuPageName() {
        return this.config.potion() == null ? "Global Duelist Potion" : this.config.potion().name;
    }

    @Override
    public boolean hasSubMenuPageSettings() {
        return true;
    }

    private static PotionConfigurations settings() { return DuelistMod.persistentDuelistData.PotionConfigurations; }

    static {
        allCardsPage = new DuelistConfigurationData("All Duelist Potions", generateAllCardsPage());
    }
}
