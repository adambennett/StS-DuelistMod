package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.helpers.EventHelper;

import duelistmod.events.*;

public class DuelistOnlyEventPatch {
	@SpirePatch(clz = EventHelper.class, method = "getEvent")
	public static class GetEvent {
		public static AbstractEvent Postfix(AbstractEvent __result, String key)
		{
			//if (key.equals(BottleCollector.ID)) 
			//{
			//	return new BottleCollector();
			//} 
			//else 
			//{
				return __result;
			//}
		}
	}

	@SpirePatch(clz = AbstractDungeon.class, method = "initializeSpecialOneTimeEventList")
	public static class AddToSpecialOneTimeEventList {
		public static void Postfix(AbstractDungeon dungeon) 
		{
			if (AbstractDungeon.player.chosenClass == TheDuelistEnum.THE_DUELIST) 
			{
				AbstractDungeon.specialOneTimeEventList.add(MillenniumItems.ID);
				AbstractDungeon.specialOneTimeEventList.add(AknamkanonTomb.ID);
				
			}
		}
	}
}
