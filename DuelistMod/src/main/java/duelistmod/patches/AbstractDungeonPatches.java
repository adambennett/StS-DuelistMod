package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.DuelistMod;
import duelistmod.characters.TheDuelist;
import duelistmod.events.*;

@SpirePatch(clz= AbstractDungeon.class,method="initializeCardPools")
public class AbstractDungeonPatches
{
    @SpirePrefixPatch
    public static void Prefix(AbstractDungeon dungeon_instance)
    {
        if (!(AbstractDungeon.player instanceof TheDuelist) || !DuelistMod.allowDuelistEvents)
        {
            AbstractDungeon.eventList.remove(AknamkanonTomb.ID);
            AbstractDungeon.eventList.remove(MillenniumItems.ID);
            AbstractDungeon.eventList.remove(EgyptVillage.ID);
            AbstractDungeon.eventList.remove(TombNameless.ID);
            AbstractDungeon.eventList.remove(TombNamelessPuzzle.ID);
            AbstractDungeon.eventList.remove(BattleCity.ID);
            AbstractDungeon.eventList.remove(CardTrader.ID);
        }
    }
}
