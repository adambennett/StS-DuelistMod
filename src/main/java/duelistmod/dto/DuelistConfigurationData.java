package duelistmod.dto;

import basemod.IUIElement;
import duelistmod.abstracts.CombatDuelistEvent;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistEvent;
import duelistmod.abstracts.DuelistOrb;
import duelistmod.abstracts.DuelistPotion;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.abstracts.DuelistStance;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DuelistConfigurationData implements Comparable<DuelistConfigurationData> {

    private final String displayName;
    private final ArrayList<IUIElement> settingElements;
    private DuelistOrb orb;
    private DuelistCard card;
    private DuelistRelic relic;
    private DuelistPotion potion;
    private DuelistStance stance;
    private DuelistEvent event;
    private CombatDuelistEvent combatEvent;

    public DuelistConfigurationData(String displayName, ArrayList<IUIElement> settingElements, DuelistOrb orb) {
        this(displayName, settingElements);
        this.orb = orb;
    }

    public DuelistConfigurationData(String displayName, ArrayList<IUIElement> settingElements, DuelistCard card) {
        this(displayName, settingElements);
        this.card = card;
    }

    public DuelistConfigurationData(String displayName, ArrayList<IUIElement> settingElements, DuelistRelic relic) {
        this(displayName, settingElements);
        this.relic = relic;
    }

    public DuelistConfigurationData(String displayName, ArrayList<IUIElement> settingElements, DuelistPotion potion) {
        this(displayName, settingElements);
        this.potion = potion;
    }

    public DuelistConfigurationData(String displayName, ArrayList<IUIElement> settingElements, DuelistStance stance) {
        this(displayName, settingElements);
        this.stance = stance;
    }

    public DuelistConfigurationData(String displayName, ArrayList<IUIElement> settingElements, DuelistEvent event) {
        this(displayName, settingElements);
        this.event = event;
    }

    public DuelistConfigurationData(String displayName, ArrayList<IUIElement> settingElements, CombatDuelistEvent event) {
        this(displayName, settingElements);
        this.combatEvent = event;
    }

    public DuelistConfigurationData(String displayName, ArrayList<IUIElement> settingElements) {
        this.displayName = displayName;
        this.settingElements = settingElements;
    }

    public String displayName() { return this.displayName; }

    public ArrayList<IUIElement> settingElements() { return this.settingElements; }

    public DuelistOrb orb() { return this.orb; }

    public DuelistCard card() { return this.card; }

    public DuelistPotion potion() { return this.potion; }

    public DuelistRelic relic() { return this.relic; }

    public DuelistStance stance() { return this.stance; }

    public DuelistEvent event() { return this.event; }

    public CombatDuelistEvent combatEvent() { return this.combatEvent; }

    @Override
    public int compareTo(@NotNull DuelistConfigurationData o) {
        return this.displayName().compareTo(o.displayName());
    }
}
