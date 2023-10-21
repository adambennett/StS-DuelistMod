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
    }

    @Override
    public LinkedHashSet<DataDifferenceDTO<?>> generateMetricsDifferences(PersistentDuelistData defaultSettings, PersistentDuelistData playerSettings) {
        LinkedHashSet<DataDifferenceDTO<?>> output = new LinkedHashSet<>();
        if (!defaultSettings.RelicConfigurations.getDisableAllCommonRelics().equals(playerSettings.RelicConfigurations.getDisableAllCommonRelics())) {
            output.add(new DataDifferenceDTO<>(this, "Global Relic Config: Disabled Common Duelist Relics", defaultSettings.RelicConfigurations.getDisableAllCommonRelics(), playerSettings.RelicConfigurations.getDisableAllCommonRelics()));
        }
        if (!defaultSettings.RelicConfigurations.getDisableAllUncommonRelics().equals(playerSettings.RelicConfigurations.getDisableAllUncommonRelics())) {
            output.add(new DataDifferenceDTO<>(this, "Global Relic Config: Disabled Uncommon Duelist Relics", defaultSettings.RelicConfigurations.getDisableAllUncommonRelics(), playerSettings.RelicConfigurations.getDisableAllUncommonRelics()));
        }
        if (!defaultSettings.RelicConfigurations.getDisableAllRareRelics().equals(playerSettings.RelicConfigurations.getDisableAllRareRelics())) {
            output.add(new DataDifferenceDTO<>(this, "Global Relic Config: Disabled Rare Duelist Relics", defaultSettings.RelicConfigurations.getDisableAllRareRelics(), playerSettings.RelicConfigurations.getDisableAllRareRelics()));
        }
        if (!defaultSettings.RelicConfigurations.getDisableAllBossRelics().equals(playerSettings.RelicConfigurations.getDisableAllBossRelics())) {
            output.add(new DataDifferenceDTO<>(this, "Global Relic Config: Disabled Boss Duelist Relics", defaultSettings.RelicConfigurations.getDisableAllBossRelics(), playerSettings.RelicConfigurations.getDisableAllBossRelics()));
        }
        if (!defaultSettings.RelicConfigurations.getDisableAllShopRelics().equals(playerSettings.RelicConfigurations.getDisableAllShopRelics())) {
            output.add(new DataDifferenceDTO<>(this, "Global Relic Config: Disabled Shop Duelist Relics", defaultSettings.RelicConfigurations.getDisableAllShopRelics(), playerSettings.RelicConfigurations.getDisableAllShopRelics()));
        }
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
                        output.add(new DataDifferenceDTO<>(this, "Event Disabled", base.getIsDisabled(), active.getIsDisabled()));
                    }
                    if (!base.getMultipleChoices().equals(active.getMultipleChoices())) {
                        output.add(new DataDifferenceDTO<>(this, "Multiple Rewards", base.getMultipleChoices(), active.getMultipleChoices()));
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
        return eventConfigurations;
    }

    public void setEventConfigurations(HashMap<String, EventConfigData> eventConfigurations) {
        this.eventConfigurations = eventConfigurations;
    }
}
