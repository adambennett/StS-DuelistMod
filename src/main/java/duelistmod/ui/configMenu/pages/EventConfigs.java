package duelistmod.ui.configMenu.pages;

import basemod.IUIElement;
import basemod.ModLabel;
import basemod.eventUtil.EventUtils;
import basemod.eventUtil.util.Condition;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.events.AbstractEvent;
import duelistmod.DuelistMod;
import duelistmod.abstracts.CombatDuelistEvent;
import duelistmod.abstracts.DuelistEvent;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.persistence.data.EventConfigurations;
import duelistmod.ui.configMenu.*;

import java.util.ArrayList;
import java.util.Collections;

public class EventConfigs extends SpecificConfigMenuPageWithJson implements RefreshablePage, SubMenuPage {

    private int currentCardIndex = 0;
    private int maxIndex = -1;
    private DuelistDropdown cardSelector;
    private static final DuelistConfigurationData allCardsPage;
    private boolean isRefreshing;

    public EventConfigs() {
        super("Event Settings", "Events");
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
        settingElements.add(this.cardSelector);
        settingElements.add(prevPageBtn);
        settingElements.add(nextPageBtn);

        this.isRefreshing = false;
        return settingElements;
    }

    private void setupCardConfigurations() {
        if (this.configs == null) {
            this.configs = new ArrayList<>();
            this.configs.addAll(DuelistMod.eventConfigurations);
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
        if (this.config.event() != null) {
            return this.config.event().getConfigurations().settingElements();
        } else if (this.config.combatEvent() != null) {
            return this.config.combatEvent().getConfigurations().settingElements();
        }
        return this.config.settingElements();
    }

    private static ArrayList<IUIElement> generateAllCardsPage() {
        RESET_Y(); LINEBREAK(); LINEBREAK(); LINEBREAK(); LINEBREAK();
        ArrayList<IUIElement> settingElements = new ArrayList<>();

        settingElements.add(new ModLabel("No global Duelist Event configurations available", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        return settingElements;
    }

    @Override
    public void resetToDefault() {
        DuelistMod.persistentDuelistData.EventConfigurations = new EventConfigurations();
        for (AbstractEvent event : DuelistMod.allDuelistEvents) {
            if (event instanceof DuelistEvent) {
                DuelistEvent de = (DuelistEvent) event;
                DuelistMod.persistentDuelistData.EventConfigurations.getEventConfigurations().put(de.duelistEventId, de.getDefaultConfig());
            } else if (event instanceof CombatDuelistEvent) {
                CombatDuelistEvent ce = (CombatDuelistEvent) event;
                DuelistMod.persistentDuelistData.EventConfigurations.getEventConfigurations().put(ce.duelistEventId, ce.getDefaultConfig());
            }
        }
    }

    @Override
    public void resetSubPageToDefault() {
        if (this.config.event() != null) {
            DuelistMod.persistentDuelistData.EventConfigurations.getEventConfigurations().put(this.config.event().duelistEventId, this.config.event().getDefaultConfig());
        } else if (this.config.combatEvent() != null) {
            DuelistMod.persistentDuelistData.EventConfigurations.getEventConfigurations().put(this.config.combatEvent().duelistEventId, this.config.combatEvent().getDefaultConfig());
        }
    }

    @Override
    public int getCurrentSubPageIndex() {
        return this.currentCardIndex;
    }

    @Override
    public String getSubMenuPageName() {
        return this.config.event() != null ? this.config.event().duelistTitle : this.config.combatEvent() != null ? this.config.combatEvent().duelistTitle : "";
    }

    @Override
    public boolean hasSubMenuPageSettings() {
        return this.config.event() != null || this.config.combatEvent() != null;
    }

    private static EventConfigurations settings() {
        return DuelistMod.persistentDuelistData.EventConfigurations;
    }

    static {
        allCardsPage = new DuelistConfigurationData("All Duelist Events", generateAllCardsPage());
    }
}
