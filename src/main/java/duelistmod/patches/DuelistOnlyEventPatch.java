package duelistmod.patches;

/*
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.helpers.EventHelper;
import com.megacrit.cardcrawl.random.Random;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.events.*;
import duelistmod.helpers.*;

public class DuelistOnlyEventPatch {
	@SpirePatch(clz = EventHelper.class, method = "getEvent")
	public static class GetEvent 
	{
		public static AbstractEvent Postfix(AbstractEvent __result, String key)
		{
			if (!AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST) || !DuelistMod.allowDuelistEvents)
			{
				if (__result instanceof DuelistEvent || __result instanceof CombatDuelistEvent) 
				{
					AbstractEvent newEv = AbstractDungeon.getEvent(new Random()); 
					while (newEv instanceof DuelistEvent) { newEv = AbstractDungeon.getEvent(new Random()); }
					Util.log("DuelistOnlyEventPatch --- not playing as The Duelist or you have Duelist events turned off, but you rolled a Duelist Event (" + key + "), so we replaced it with another random event.");
					return newEv;  
				}
			}
			return __result;
		}
	}

	@SpirePatch(clz = AbstractDungeon.class, method = "initializeSpecialOneTimeEventList")
	public static class AddToSpecialOneTimeEventList 
	{
		public static void Postfix(AbstractDungeon dungeon) 
		{
			if (AbstractDungeon.player.chosenClass == TheDuelistEnum.THE_DUELIST && DuelistMod.allowDuelistEvents) 
			{
				AbstractDungeon.specialOneTimeEventList.add(MillenniumItems.ID);
				AbstractDungeon.specialOneTimeEventList.add(AknamkanonTomb.ID);				
				AbstractDungeon.specialOneTimeEventList.add(TombNameless.ID);
				AbstractDungeon.specialOneTimeEventList.add(TombNamelessPuzzle.ID);
				AbstractDungeon.specialOneTimeEventList.add(CardTrader.ID);
				AbstractDungeon.specialOneTimeEventList.add(RelicDuplicator.ID);
				AbstractDungeon.specialOneTimeEventList.add(BattleCity.ID);
				AbstractDungeon.specialOneTimeEventList.add(VisitFromAnubis.ID);
				if (Util.deckIs("Warrior Deck")) { AbstractDungeon.specialOneTimeEventList.add(EgyptVillage.ID); Util.log("Added Egypt Village to events list");}
				else { Util.log("Egypt Village event was not added to special events list, you are not playing with the Warrior Deck"); }
			}
		}
	}
}
*/
