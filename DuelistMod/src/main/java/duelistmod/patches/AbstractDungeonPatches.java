package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.characters.TheDuelist;
import duelistmod.events.*;

@SpirePatch(clz= AbstractDungeon.class,method="initializeCardPools")
public class AbstractDungeonPatches
{
    @SpirePrefixPatch
    public static void Prefix(AbstractDungeon dungeon_instance)
    {
        if (!(AbstractDungeon.player instanceof TheDuelist))
        {
            AbstractDungeon.eventList.remove(AknamkanonTomb.ID);
            AbstractDungeon.eventList.remove(MillenniumItems.ID);
        }
    }
}
