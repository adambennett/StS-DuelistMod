package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import basemod.ModLabel;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.CardConfigData;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.persistence.data.CardConfigurations;
import duelistmod.ui.configMenu.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class CardConfigs extends SpecificConfigMenuPageWithJson implements RefreshablePage, SubMenuPage {

    private int currentCardIndex = 0;
    private int maxIndex = -1;
    private DuelistDropdown cardSelector;
    private static final DuelistConfigurationData allCardsPage;
    private static final DuelistConfigurationData allTokensPage;
    private boolean isRefreshing;
    private boolean noSettingsImplemented = false;

    public CardConfigs() {
        super("Card Settings", "Cards");
        this.config = allCardsPage;
    }

    public ArrayList<IUIElement> getElements() {
        this.setupCardConfigurations();
        this.maxIndex = this.configs.size() - 1;

        ArrayList<String> cards = new ArrayList<>();
        for (DuelistConfigurationData cardConfig : this.configs) { cards.add(cardConfig.displayName()); }
        this.cardSelector = new DuelistDropdown(cards, Settings.scale * (DuelistMod.xLabPos + 87), Settings.scale * (DuelistMod.yPos + 50), (s, i) -> {
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

        int pagerRightX = (int)(DuelistMod.xLabPos + 400);
        int pagerLeftX = (int)DuelistMod.xLabPos;
        int pagerY = (int)DuelistMod.yPos;
        GeneralPager pager = new GeneralPager(() -> this.setPage(this.currentCardIndex + 1), () -> this.setPage(this.currentCardIndex - 1));
        Pager nextPageBtn = new Pager(DuelistMod.rightArrow, pagerRightX, pagerY, 75, 75, true, pager);
        Pager prevPageBtn = new Pager(DuelistMod.leftArrow, pagerLeftX, pagerY, 75, 75, false, pager);

        LINEBREAK();
        LINEBREAK();

        ArrayList<IUIElement> settingElements = new ArrayList<>(generateSubPages());
        this.noSettingsImplemented = false;
        if (!settingElements.isEmpty() && settingElements.get(0) instanceof ModLabel) {
            ModLabel label = (ModLabel) settingElements.get(0);
            if (label.text.contains("Configurations for") && label.text.contains("not setup yet.")) {
                this.noSettingsImplemented = true;
            }
        }
        settingElements.add(this.cardSelector);
        settingElements.add(prevPageBtn);
        settingElements.add(nextPageBtn);

        this.isRefreshing = false;
        return settingElements;
    }

    @Override
    public void resetToDefault() {
        DuelistMod.persistentDuelistData.CardConfigurations = new CardConfigurations();
        for (DuelistCard card : DuelistMod.myCards) {
            CardConfigData baseConfig = card.getDefaultConfig();
            if (baseConfig != null) {
                DuelistMod.persistentDuelistData.CardConfigurations.getCardConfigurations().put(card.cardID, baseConfig);
            }
        }
    }

    @Override
    public void resetSubPageToDefault() {
        if (this.config.card() != null) {
            DuelistMod.persistentDuelistData.CardConfigurations.getCardConfigurations().put(this.config.card().cardID, this.config.card().getDefaultConfig());
        } else if (this.currentCardIndex == 1) {
            CardConfigurations baseConfig = new CardConfigurations();
            DuelistMod.persistentDuelistData.CardConfigurations.setExplosiveDamageHigh(baseConfig.getExplosiveDamageHigh());
            DuelistMod.persistentDuelistData.CardConfigurations.setExplosiveDamageLow(baseConfig.getExplosiveDamageLow());
            DuelistMod.persistentDuelistData.CardConfigurations.setSuperExplosiveHighMultiplier(baseConfig.getSuperExplosiveHighMultiplier());
            DuelistMod.persistentDuelistData.CardConfigurations.setSuperExplosiveLowMultiplier(baseConfig.getSuperExplosiveLowMultiplier());
            DuelistMod.persistentDuelistData.CardConfigurations.setTokensPurgeAtEndOfTurn(baseConfig.getTokensPurgeAtEndOfTurn());
        }
    }

    @Override
    public String getSubMenuPageName() {
        return this.hasSubMenuPageSettings() ? this.currentCardIndex == 1 ? "Global Token" : this.config.card().name : "";
    }

    @Override
    public boolean hasSubMenuPageSettings() {
        return (this.currentCardIndex == 1 || this.config.card() != null) && !this.noSettingsImplemented;
    }

    private static CardConfigurations settings() {
        return DuelistMod.persistentDuelistData.CardConfigurations;
    }

    private void setupCardConfigurations() {
        if (this.configs == null) {
            this.configs = new ArrayList<>();
            //this.configs.addAll(DuelistMod.cardConfigurations);
            this.configs.addAll(DuelistMod.cardConfigurations.stream().filter(c -> c.settingElements().size() > 1).collect(Collectors.toList()));
            Collections.sort(this.configs);
            this.configs.add(0, allCardsPage);
            this.configs.add(1, allTokensPage);
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
        if (this.config.card() != null) {
            return this.config.card().getConfigurations().settingElements();
        }
        return this.config.settingElements();
    }

    private static void resetAlignmentForSubPage() {
        RESET_Y(); LINEBREAK(); LINEBREAK(); LINEBREAK(); LINEBREAK();
    }

    private static ArrayList<IUIElement> generateAllCardsPage() {
        resetAlignmentForSubPage();
        ArrayList<IUIElement> settingElements = new ArrayList<>();
        settingElements.add(new ModLabel("No global DuelistCard configurations are currently available.", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        return settingElements;
    }

    private static ArrayList<IUIElement> generateTokensPage() {
        resetAlignmentForSubPage();
        ArrayList<IUIElement> settingElements = new ArrayList<>();

        String tooltip = "When enabled, #yTokens in your hand are removed from play at the end of turn. Enabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Tokens Purge at end of turn", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getTokensPurgeAtEndOfTurn(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setTokensPurgeAtEndOfTurn(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        LINEBREAK(35);

        settingElements.add(new ModLabel("Explosive Token Damage Range", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));

        ArrayList<String> explosiveRangeOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { explosiveRangeOptions.add(i+""); }
        tooltip = "Modify the low end of the damage range for #yExplosive #yTokens. Set to #b2 by default.";
        DuelistDropdown explosiveRangeSelectorLow = new DuelistDropdown(tooltip, explosiveRangeOptions, Settings.scale * (DuelistMod.xLabPos + 650), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            settings().setExplosiveDamageLow(i);
            DuelistMod.configSettingsLoader.save();
        });
        explosiveRangeSelectorLow.setSelectedIndex(settings().getExplosiveDamageLow());

        ArrayList<String> explosiveRangeOptions2 = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { explosiveRangeOptions2.add(i+""); }
        tooltip = "Modify the high end of the damage range for #yExplosive #yTokens. Set to #b6 by default.";
        DuelistDropdown explosiveRangeSelectorHigh = new DuelistDropdown(tooltip, explosiveRangeOptions2, Settings.scale * (DuelistMod.xLabPos + 650 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            settings().setExplosiveDamageHigh(i);
            DuelistMod.configSettingsLoader.save();
        });
        explosiveRangeSelectorHigh.setSelectedIndex(settings().getExplosiveDamageHigh());

        LINEBREAK(25);

        settingElements.add(new ModLabel("Super Explosive Token Damage Multipliers", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));

        ArrayList<String> superExplosiveRangeOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { superExplosiveRangeOptions.add(i+""); }
        tooltip = "Modify the low damage multiplier for #ySuper #yExplosive #yTokens. Set to #b3 by default.";
        DuelistDropdown superExplosiveRangeSelectorLow = new DuelistDropdown(tooltip, superExplosiveRangeOptions, Settings.scale * (DuelistMod.xLabPos + 650), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            settings().setSuperExplosiveLowMultiplier(i);
            DuelistMod.configSettingsLoader.save();
        });
        superExplosiveRangeSelectorLow.setSelectedIndex(settings().getSuperExplosiveLowMultiplier());

        ArrayList<String> superExplosiveRangeOptions2 = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { superExplosiveRangeOptions2.add(i+""); }
        tooltip = "Modify the high damage multiplier for #ySuper #yExplosive #yTokens. Set to #b4 by default.";
        DuelistDropdown superExplosiveRangeSelectorHigh = new DuelistDropdown(tooltip, superExplosiveRangeOptions2, Settings.scale * (DuelistMod.xLabPos + 650 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            settings().setSuperExplosiveHighMultiplier(i);
            DuelistMod.configSettingsLoader.save();
        });
        superExplosiveRangeSelectorHigh.setSelectedIndex(settings().getSuperExplosiveHighMultiplier());

        settingElements.add(superExplosiveRangeSelectorHigh);
        settingElements.add(superExplosiveRangeSelectorLow);
        settingElements.add(explosiveRangeSelectorHigh);
        settingElements.add(explosiveRangeSelectorLow);
        return settingElements;
    }

    static {
        allCardsPage = new DuelistConfigurationData("All Duelist Cards", generateAllCardsPage());
        allTokensPage = new DuelistConfigurationData("All Tokens", generateTokensPage());
    }
}
