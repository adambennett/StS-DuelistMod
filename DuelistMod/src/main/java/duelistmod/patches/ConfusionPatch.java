package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ConfusionPower;

import duelistmod.abstracts.DuelistCard;
import duelistmod.relics.ConfusionGoldRelic;

@SpirePatch(clz= ConfusionPower.class,method="onCardDraw")
public class ConfusionPatch
{
    @SuppressWarnings("rawtypes")
	public static SpireReturn Prefix(ConfusionPower __instance, AbstractCard card)
    {
    	if (card.cost > 4 && card instanceof DuelistCard)
    	{
    		int lowBound = 0;
    		if (card.cost > 5) { lowBound = 1; }
    		int costCapRoll = AbstractDungeon.cardRandomRng.random(3, card.cost);
    		int newCost = AbstractDungeon.cardRandomRng.random(lowBound, costCapRoll);
            if (newCost > 3) { newCost = AbstractDungeon.cardRandomRng.random(lowBound, costCapRoll); }
            //if (newCost > 6) { int ref = newCost; newCost = AbstractDungeon.cardRandomRng.random(0, ref); }
            if (newCost > 3 && AbstractDungeon.player.hasRelic(ConfusionGoldRelic.ID)) { ConfusionGoldRelic rel = (ConfusionGoldRelic)AbstractDungeon.player.getRelic(ConfusionGoldRelic.ID); rel.goldGainEffect(); }
            if (card.cost != newCost) 
            {
                card.cost = newCost;
                card.costForTurn = card.cost;
                card.isCostModified = true;
            }
            return SpireReturn.Return(null);
    	}
    	else if (card.cost >= 0 && card instanceof DuelistCard) 
    	{
            int newCost = AbstractDungeon.cardRandomRng.random(0, 4);
            if (newCost == 4) { newCost = AbstractDungeon.cardRandomRng.random(0, 4); }
            if (newCost == 4 && AbstractDungeon.player.hasRelic(ConfusionGoldRelic.ID)) { ConfusionGoldRelic rel = (ConfusionGoldRelic)AbstractDungeon.player.getRelic(ConfusionGoldRelic.ID); rel.goldGainEffect(); }
            if (card.cost != newCost) 
            {
                card.cost = newCost;
                card.costForTurn = card.cost;
                card.isCostModified = true;
            }
            return SpireReturn.Return(null);
        }
    	else
    	{
    		return SpireReturn.Continue();
    	}
    }
}
