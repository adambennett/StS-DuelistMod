package duelistmod.abstracts;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.events.AbstractEvent;

import java.util.*;

public abstract class CombatDuelistEvent extends AbstractEvent
{
    public void logDuelistMetric(String eventName, String playerChoice) {
        logDuelistMetric(eventName, playerChoice, null, null, null, null, null, null, null, 0, 0, 0, 0, 0, 0);
    }

    public void logDuelistMetric(String eventName, String playerChoice, List<String> cardsObtained, List<String> cardsRemoved, List<String> cardsTransformed, List<String> cardsUpgraded, List<String> relicsObtained, List<String> potionsObtained, List<String> relicsLost, int damageTaken, int damageHealed, int hpLoss, int hpGain, int goldGain, int goldLoss) {
        HashMap<String, Object> choice = new HashMap<>();
        choice.put("event_name", eventName);
        choice.put("player_choice", playerChoice);
        choice.put("floor", AbstractDungeon.floorNum);
        choice.put("cards_obtained", cardsObtained);
        choice.put("cards_removed", cardsRemoved);
        choice.put("cards_transformed", cardsTransformed);
        choice.put("cards_upgraded", cardsUpgraded);
        choice.put("relics_obtained", relicsObtained);
        choice.put("potions_obtained", potionsObtained);
        choice.put("relics_lost", relicsLost);
        choice.put("damage_taken", damageTaken);
        choice.put("damage_healed", damageHealed);
        choice.put("max_hp_loss", hpLoss);
        choice.put("max_hp_gain", hpGain);
        choice.put("gold_gain", goldGain);
        choice.put("gold_loss", goldLoss);
        choice.put("duelist", true);
        CardCrawlGame.metricData.event_choices.add(choice);
    }
}
