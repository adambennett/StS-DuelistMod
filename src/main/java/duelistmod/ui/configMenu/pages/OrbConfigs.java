package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import duelistmod.DuelistMod;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.helpers.Util;
import duelistmod.persistence.data.OrbConfigurations;
import duelistmod.ui.configMenu.*;

import java.util.ArrayList;
import java.util.Collections;

public class OrbConfigs extends SpecificConfigMenuPageWithJson implements RefreshablePage, SubMenuPage {

    private int currentCardIndex = 0;
    private int maxIndex = -1;
    private DuelistDropdown cardSelector;
    private static final DuelistConfigurationData allCardsPage;
    private boolean isRefreshing;

    public OrbConfigs() {
        super("Orb Settings", "Orbs");
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
        if (this.config != null && this.config.orb() != null && this.config.orb().getImage() != null) {
            this.config.orb().fixFocusOnConfigChanges();
            this.config.orb().updateDescription();
            this.image = new ModHoverImage(DuelistMod.xLabPos + DuelistMod.xSecondCol + DuelistMod.xThirdCol, pagerY, this.config.orb().getImage(), this.config.orb().description);
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
            this.configs.addAll(DuelistMod.orbConfigurations);
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
        if (this.config.orb() != null) {
            return this.config.orb().getConfigurations().settingElements();
        }
        return this.config.settingElements();
    }

    private static ArrayList<IUIElement> generateAllCardsPage() {
        RESET_Y(); LINEBREAK(); LINEBREAK(); LINEBREAK(); LINEBREAK();
        ArrayList<IUIElement> settingElements = new ArrayList<>();

        // Disable all Passive Effects
        String tooltip = "When the toggle is enabled, ALL Duelist orbs will not trigger their passive effects. Disabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Disable all Orb passive effects", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getDisableAllOrbPassives(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setDisableAllOrbPassives(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));

        LINEBREAK(25);

        // Disable all Evoke Effects
        tooltip = "When the toggle is enabled, ALL Duelist orbs will not trigger their #yEvoke effects. Disabled by default.";
        settingElements.add(new DuelistLabeledToggleButton("Disable all Orb Evoke effects", tooltip,DuelistMod.xLabPos, DuelistMod.yPos, Settings.CREAM_COLOR, FontHelper.charDescFont, settings().getDisableAllOrbEvokes(), DuelistMod.settingsPanel, (label) -> {}, (button) ->
        {
            settings().setDisableAllOrbEvokes(button.enabled);
            DuelistMod.configSettingsLoader.save();
        }));
        return settingElements;
    }

    @Override
    public void resetToDefault() {
        DuelistMod.persistentDuelistData.OrbConfigurations = new OrbConfigurations();
        Util.setupOrbConfigSettingsMap();
    }

    @Override
    public void resetSubPageToDefault() {
        if (this.config.orb() != null) {
            DuelistMod.persistentDuelistData.OrbConfigurations.getOrbConfigurations().put(this.config.orb().ID, this.config.orb().getDefaultConfig());
        } else {
            DuelistMod.persistentDuelistData.OrbConfigurations.setDisableAllOrbEvokes(false);
            DuelistMod.persistentDuelistData.OrbConfigurations.setDisableAllOrbPassives(false);
        }
    }

    @Override
    public String getSubMenuPageName() {
        return this.config.orb() == null ? "Global Duelist Orb" : this.config.orb().name;
    }

    @Override
    public boolean hasSubMenuPageSettings() {
        return true;
    }

    private static OrbConfigurations settings() { return DuelistMod.persistentDuelistData.OrbConfigurations; }

    static {
        allCardsPage = new DuelistConfigurationData("All Duelist Orbs", generateAllCardsPage());
    }
}
