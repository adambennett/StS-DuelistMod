package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import basemod.ModLabel;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import duelistmod.DuelistMod;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.ui.configMenu.*;

import java.util.ArrayList;
import java.util.Collections;

public class CardConfigs extends SpecificConfigMenuPageWithJson implements RefreshablePage, SubMenuPage {

    private int currentCardIndex = 0;
    private int maxIndex = -1;
    private DuelistDropdown cardSelector;
    private static final DuelistConfigurationData allCardsPage;
    private static final DuelistConfigurationData allTokensPage;
    private boolean isRefreshing;

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
        settingElements.add(this.cardSelector);
        settingElements.add(prevPageBtn);
        settingElements.add(nextPageBtn);

        this.isRefreshing = false;
        return settingElements;
    }

    @Override
    public void resetToDefault() {

    }

    private void setupCardConfigurations() {
        if (this.configs == null) {
            this.configs = new ArrayList<>();
            this.configs.addAll(DuelistMod.cardConfigurations);
            Collections.sort(this.configs);
            this.configs.add(0, allCardsPage);
            this.configs.add(1, allTokensPage);
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
        if (this.config.card() != null) {
            return this.config.card().getConfigurations().settingElements();
        }
        return this.config.settingElements();
    }

    private static void resetForSubPage() {
        RESET_Y(); LINEBREAK(); LINEBREAK(); LINEBREAK(); LINEBREAK();
    }

    private static ArrayList<IUIElement> generateAllCardsPage() {
        resetForSubPage();
        ArrayList<IUIElement> settingElements = new ArrayList<>();
        settingElements.add(new ModLabel("No global DuelistCard configurations are currently available.", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        return settingElements;
    }

    private static ArrayList<IUIElement> generateTokensPage() {
        resetForSubPage();
        ArrayList<IUIElement> settingElements = new ArrayList<>();

        String tooltip = "When enabled, #yTokens in your hand are removed from play at the end of turn. Enabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Tokens Purge at end of turn", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, DuelistMod.tokensPurgeAtEndOfTurn, DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            DuelistMod.tokensPurgeAtEndOfTurn = button.enabled;
            try
            {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setBool("tokensPurgeAtEndOfTurn", DuelistMod.tokensPurgeAtEndOfTurn);
                config.save();
            } catch (Exception e) { e.printStackTrace(); }

        }));

        LINEBREAK(35);

        settingElements.add(new ModLabel("Explosive Token Damage Range", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));

        ArrayList<String> explosiveRangeOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { explosiveRangeOptions.add(i+""); }
        tooltip = "Modify the low end of the damage range for #yExplosive #yTokens. Set to #b2 by default.";
        DuelistDropdown explosiveRangeSelectorLow = new DuelistDropdown(tooltip, explosiveRangeOptions, Settings.scale * (DuelistMod.xLabPos + 650), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.explosiveDmgLow = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("explosiveDmgLow", DuelistMod.explosiveDmgLow);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        explosiveRangeSelectorLow.setSelectedIndex(DuelistMod.explosiveDmgLow);

        ArrayList<String> explosiveRangeOptions2 = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { explosiveRangeOptions2.add(i+""); }
        tooltip = "Modify the high end of the damage range for #yExplosive #yTokens. Set to #b6 by default.";
        DuelistDropdown explosiveRangeSelectorHigh = new DuelistDropdown(tooltip, explosiveRangeOptions2, Settings.scale * (DuelistMod.xLabPos + 650 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.explosiveDmgHigh = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("explosiveDmgHigh", DuelistMod.explosiveDmgHigh);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        explosiveRangeSelectorHigh.setSelectedIndex(DuelistMod.explosiveDmgHigh);

        LINEBREAK(25);

        settingElements.add(new ModLabel("Super Explosive Token Damage Multipliers", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));

        ArrayList<String> superExplosiveRangeOptions = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { superExplosiveRangeOptions.add(i+""); }
        tooltip = "Modify the low damage multiplier for #ySuper #yExplosive #yTokens. Set to #b3 by default.";
        DuelistDropdown superExplosiveRangeSelectorLow = new DuelistDropdown(tooltip, superExplosiveRangeOptions, Settings.scale * (DuelistMod.xLabPos + 650), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.superExplodeMultiplierLow = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("superExplodeMultiplierLow", DuelistMod.superExplodeMultiplierLow);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        superExplosiveRangeSelectorLow.setSelectedIndex(DuelistMod.superExplodeMultiplierLow);

        ArrayList<String> superExplosiveRangeOptions2 = new ArrayList<>();
        for (int i = 0; i < 1001; i++) { superExplosiveRangeOptions2.add(i+""); }
        tooltip = "Modify the high damage multiplier for #ySuper #yExplosive #yTokens. Set to #b4 by default.";
        DuelistDropdown superExplosiveRangeSelectorHigh = new DuelistDropdown(tooltip, superExplosiveRangeOptions2, Settings.scale * (DuelistMod.xLabPos + 650 + 150), Settings.scale * (DuelistMod.yPos + 22), (s, i) -> {
            DuelistMod.superExplodeMultiplierHigh = i;
            try {
                SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
                config.setInt("superExplodeMultiplierHigh", DuelistMod.superExplodeMultiplierHigh);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        superExplosiveRangeSelectorHigh.setSelectedIndex(DuelistMod.superExplodeMultiplierHigh);

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
