package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import basemod.ModLabel;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import duelistmod.DuelistMod;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.ui.configMenu.DuelistDropdown;
import duelistmod.ui.configMenu.DuelistLabeledToggleButton;
import duelistmod.ui.configMenu.GeneralPager;
import duelistmod.ui.configMenu.Pager;
import duelistmod.ui.configMenu.RefreshablePage;
import duelistmod.ui.configMenu.SpecificConfigMenuPage;

import java.util.ArrayList;
import java.util.Collections;

public class CardConfigs extends SpecificConfigMenuPage implements RefreshablePage {

    private DuelistConfigurationData config = allCardsPage;
    private int currentCardIndex = 0;
    private int maxIndex = -1;
    private DuelistDropdown cardSelector;
    private ArrayList<DuelistConfigurationData> configs;
    private static final DuelistConfigurationData allCardsPage;
    private static final DuelistConfigurationData allTokensPage;
    private static final DuelistConfigurationData allNamelessPage;
    private boolean isRefreshing;

    public CardConfigs() {
        super("Card Settings", "Cards");
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

    private void setupCardConfigurations() {
        if (this.configs == null) {
            this.configs = new ArrayList<>();
            this.configs.addAll(DuelistMod.cardConfigurations);
            Collections.sort(this.configs);
            this.configs.add(0, allCardsPage);
            this.configs.add(1, allTokensPage);
            this.configs.add(2, allNamelessPage);
        }
    }

    @Override
    public void refresh() {
        this.setPage(0);
    }

    private void setPage(int index) {
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

        return settingElements;
    }

    private static ArrayList<IUIElement> generateNamelessPage() {
        resetForSubPage();
        ArrayList<IUIElement> settingElements = new ArrayList<>();
        settingElements.add(new ModLabel("No global Nameless Tomb card configurations are currently available.", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        return settingElements;
    }

    static {
        allCardsPage = new DuelistConfigurationData("All Duelist Cards", generateAllCardsPage());
        allTokensPage = new DuelistConfigurationData("All Tokens", generateTokensPage());
        allNamelessPage = new DuelistConfigurationData("All Nameless Tomb Cards", generateNamelessPage());
    }
}
