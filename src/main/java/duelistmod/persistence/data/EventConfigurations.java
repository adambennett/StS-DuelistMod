package duelistmod.persistence.data;

import com.megacrit.cardcrawl.events.AbstractEvent;
import duelistmod.DuelistMod;
import duelistmod.abstracts.CombatDuelistEvent;
import duelistmod.abstracts.DuelistEvent;
import duelistmod.dto.EventConfigData;
import duelistmod.enums.DataCategoryType;
import duelistmod.persistence.DataDifferenceDTO;
import duelistmod.persistence.PersistentDuelistData;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EventConfigurations extends DataCategory {

    private HashMap<String, EventConfigData> eventConfigurations = new HashMap<>();

    public EventConfigurations() {
        this.category = "Event Configuration Settings";
        this.type = DataCategoryType.Config_Setting;
    }

    public EventConfigurations(EventConfigurations from) {
        this(from.getEventConfigurations());
    }

    public EventConfigurations(HashMap<String, EventConfigData> eventConfigurations) {
        this();
        this.eventConfigurations = eventConfigurations;

        if (this.eventConfigurations == null) this.eventConfigurations = new HashMap<>();

        for (AbstractEvent event : DuelistMod.allDuelistEvents) {
            EventConfigData mergedConfig;
            if (event instanceof DuelistEvent) {
                DuelistEvent de = (DuelistEvent) event;
                EventConfigData baseConfig = de.getDefaultConfig();
                EventConfigData activeConfig = this.eventConfigurations.getOrDefault(de.duelistEventId, null);
                if (activeConfig == null) {
                    mergedConfig = baseConfig;
                } else {
                    mergedConfig = activeConfig;
                    for (Map.Entry<String, Object> entry : baseConfig.getProperties().entrySet()) {
                        if (!mergedConfig.getProperties().containsKey(entry.getKey())) {
                            mergedConfig.put(entry.getKey(), entry.getValue());
                        }
                    }
                }
                this.eventConfigurations.put(de.duelistEventId, mergedConfig);
            } else if (event instanceof CombatDuelistEvent) {
                CombatDuelistEvent ce = (CombatDuelistEvent) event;
                EventConfigData baseConfig = ce.getDefaultConfig();
                EventConfigData activeConfig = this.eventConfigurations.getOrDefault(ce.duelistEventId, null);
                if (activeConfig == null) {
                    mergedConfig = baseConfig;
                } else {
                    mergedConfig = activeConfig;
                    for (Map.Entry<String, Object> entry : baseConfig.getProperties().entrySet()) {
                        if (!mergedConfig.getProperties().containsKey(entry.getKey())) {
                            mergedConfig.put(entry.getKey(), entry.getValue());
                        }
                    }
                }
                this.eventConfigurations.put(ce.duelistEventId, mergedConfig);
            }
        }
    }

    @Override
    public LinkedHashSet<DataDifferenceDTO<?>> generateMetricsDifferences(PersistentDuelistData defaultSettings, PersistentDuelistData playerSettings) {
        LinkedHashSet<DataDifferenceDTO<?>> output = new LinkedHashSet<>();
        for (Map.Entry<String, EventConfigData> entry : this.eventConfigurations.entrySet()) {
            List<AbstractEvent> eventMatches = DuelistMod.allDuelistEvents.stream().filter(r -> (r instanceof CombatDuelistEvent && ((CombatDuelistEvent)r).duelistEventId.equals(entry.getKey())) || (r instanceof DuelistEvent && ((DuelistEvent)r).duelistEventId.equals(entry.getKey()))).collect(Collectors.toList());
            if (!eventMatches.isEmpty()) {
                AbstractEvent baseEvent = eventMatches.get(0);
                EventConfigData base = null;
                if (baseEvent instanceof DuelistEvent) {
                    base = ((DuelistEvent)baseEvent).getDefaultConfig();
                } else if (baseEvent instanceof CombatDuelistEvent) {
                    base = ((CombatDuelistEvent)baseEvent).getDefaultConfig();
                }
                if (base != null) {
                    EventConfigData active = entry.getValue();
                    if (!base.getIsDisabled().equals(active.getIsDisabled())) {
                        output.add(new DataDifferenceDTO<>(this, "Event Disabled (" + entry.getKey() + ")", base.getIsDisabled(), active.getIsDisabled()));
                    }
                    if (!base.getMultipleChoices().equals(active.getMultipleChoices())) {
                        output.add(new DataDifferenceDTO<>(this, "Multiple Rewards (" + entry.getKey() + ")", base.getMultipleChoices(), active.getMultipleChoices()));
                    }
                    for (Map.Entry<String, Object> e : active.getProperties().entrySet()) {
                        if (base.getProperties().containsKey(e.getKey()) && !base.getProperties().get(e.getKey()).equals(e.getValue())) {
                            output.add(new DataDifferenceDTO<>(this, "Event Setting: " + e.getKey(), base.getProperties().get(e.getKey()), e.getValue()));
                        }
                    }
                }
            }
        }
        return output;
    }

    public HashMap<String, EventConfigData> getEventConfigurations() {
        if (eventConfigurations == null) {
            eventConfigurations = new HashMap<>();
        }
        if (eventConfigurations.isEmpty()) {
            for (AbstractEvent event : DuelistMod.allDuelistEvents) {
                if (event instanceof DuelistEvent) {
                    DuelistEvent de = (DuelistEvent) event;
                    eventConfigurations.put(de.duelistEventId, de.getDefaultConfig());
                } else if (event instanceof CombatDuelistEvent) {
                    CombatDuelistEvent ce = (CombatDuelistEvent) event;
                    eventConfigurations.put(ce.duelistEventId, ce.getDefaultConfig());
                }
            }
        }
        return eventConfigurations;
    }

    public void setEventConfigurations(HashMap<String, EventConfigData> eventConfigurations) {
        this.eventConfigurations = eventConfigurations;
    }
}
