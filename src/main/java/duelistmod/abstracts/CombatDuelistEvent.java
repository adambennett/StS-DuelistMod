package duelistmod.abstracts;

import basemod.IUIElement;
import basemod.ModLabel;
import basemod.eventUtil.EventUtils;
import basemod.eventUtil.util.Condition;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.events.AbstractEvent;
import duelistmod.DuelistMod;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.dto.EventConfigData;
import duelistmod.helpers.Util;
import duelistmod.ui.configMenu.DuelistDropdown;

import java.util.*;

public abstract class CombatDuelistEvent extends AbstractEvent
{
    public final String duelistEventId;
    public final String title;
    public Condition spawnCondition;
    public Condition bonusCondition;
    public boolean duelistOnly;
    public String dungeonId;
    public EventUtils.EventType type;
    public String duelistTitle;

    public CombatDuelistEvent(String duelistEventId, String title) {
        this.duelistEventId = duelistEventId;
        this.type = EventUtils.EventType.ONE_TIME;
        this.duelistTitle = title;
        this.duelistOnly = true;
        this.title = title;
    }

    protected void addToBot(final AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    protected void addToTop(final AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }

    public void LINEBREAK() {
        DuelistMod.linebreak();
    }

    public void LINEBREAK(int extra) {
        DuelistMod.linebreak(extra);
    }

    public void RESET_Y() {
        DuelistMod.yPos = DuelistMod.startingYPos;
    }

    protected List<DuelistDropdown> configAddAfterDisabledBox(ArrayList<IUIElement> settingElements) { return new ArrayList<>(); }

    protected List<DuelistDropdown> configAddAfterDescription(ArrayList<IUIElement> settingElements) { return new ArrayList<>(); }

    public EventConfigData getDefaultConfig() { return new EventConfigData(); }

    public Object getDefaultConfig(String key) {
        return this.getDefaultConfig().getProperties().getOrDefault(key, null);
    }

    public EventConfigData getActiveConfig() { return DuelistMod.persistentDuelistData.EventConfigurations.getEventConfigurations().getOrDefault(this.duelistEventId, this.getDefaultConfig()); }

    public Object getConfig(String key, Object defaultVal) {
        return this.getActiveConfig().getProperties().getOrDefault(key, this.getDefaultConfig().getProperties().getOrDefault(key, defaultVal));
    }

    public DuelistConfigurationData getConfigurations() {
        RESET_Y(); LINEBREAK(); LINEBREAK(); LINEBREAK(); LINEBREAK();
        ArrayList<IUIElement> settingElements = new ArrayList<>();
        settingElements.add(new ModLabel("No configurations are currently setup for " + this.title, (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        return new DuelistConfigurationData(this.title, settingElements, this);
    }

    public void updateConfigSettings(EventConfigData data) {
        DuelistMod.persistentDuelistData.EventConfigurations.getEventConfigurations().put(this.duelistEventId, data);
        DuelistMod.configSettingsLoader.save();
    }

    public void logDuelistMetric(String eventName, String playerChoice) {
        logDuelistMetric(eventName, playerChoice, null, null, null, null, null, null, null, 0, 0, 0, 0, 0, 0);
    }

    public void logDuelistMetric(String eventName, String playerChoice, List<String> cardsObtained, List<String> cardsRemoved, List<String> cardsTransformed, List<String> cardsUpgraded, List<String> relicsObtained, List<String> potionsObtained, List<String> relicsLost, int damageTaken, int damageHealed, int hpLoss, int hpGain, int goldGain, int goldLoss) {
        Util.logMetricsFromBattleCity(eventName, playerChoice, cardsObtained, cardsRemoved, cardsTransformed, cardsUpgraded, relicsObtained, potionsObtained, relicsLost, damageTaken, damageHealed, hpLoss, hpGain, goldGain, goldLoss);
    }
}
