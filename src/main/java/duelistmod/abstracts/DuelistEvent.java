package duelistmod.abstracts;

import basemod.IUIElement;
import basemod.ModLabel;
import basemod.eventUtil.EventUtils;
import basemod.eventUtil.util.Condition;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import duelistmod.DuelistMod;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.dto.EventConfigData;
import duelistmod.helpers.Util;
import duelistmod.ui.configMenu.DuelistDropdown;

import java.util.*;
import java.util.List;

public abstract class DuelistEvent extends AbstractImageEvent
{
    public final String duelistEventId;
    public Condition spawnCondition;
    public Condition bonusCondition;
    public boolean duelistOnly;
    public String dungeonId;
    public EventUtils.EventType type;
    public String duelistTitle;

    public DuelistEvent(String duelistEventId, String title, String body, String imgUrl) {
        super(title, body, imgUrl);
        this.duelistEventId = duelistEventId;
        this.duelistTitle = title;
        this.type = EventUtils.EventType.ONE_TIME;
        this.duelistOnly = true;
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

    public EventConfigData getActiveConfig() { return DuelistMod.eventConfigSettingsMap.getOrDefault(this.duelistEventId, this.getDefaultConfig()); }

    public DuelistConfigurationData getConfigurations() {
        RESET_Y(); LINEBREAK(); LINEBREAK(); LINEBREAK(); LINEBREAK();
        ArrayList<IUIElement> settingElements = new ArrayList<>();
        settingElements.add(new ModLabel("No configurations are currently setup for " + this.title, (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        return new DuelistConfigurationData(this.title, settingElements, this);
    }

    public void logDuelistMetric(String eventName, String playerChoice) {
        logDuelistMetric(eventName, playerChoice, null, null, null, null, null, null, null, 0, 0, 0, 0, 0, 0);
    }

    public void logDuelistMetric(String eventName, String playerChoice, List<String> cardsObtained, List<String> cardsRemoved, List<String> cardsTransformed, List<String> cardsUpgraded, List<String> relicsObtained, List<String> potionsObtained, List<String> relicsLost, int damageTaken, int damageHealed, int hpLoss, int hpGain, int goldGain, int goldLoss) {
        Util.logMetricsFromBattleCity(eventName, playerChoice, cardsObtained, cardsRemoved, cardsTransformed, cardsUpgraded, relicsObtained, potionsObtained, relicsLost, damageTaken, damageHealed, hpLoss, hpGain, goldGain, goldLoss);
    }

}
