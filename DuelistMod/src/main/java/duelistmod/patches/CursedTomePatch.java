package duelistmod.patches;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.events.city.CursedTome;
import com.megacrit.cardcrawl.relics.AbstractRelic;

@SpirePatch(clz = CursedTome.class, method = "randomBook")
public class CursedTomePatch
{
    @SpireInsertPatch(rloc = 1, localvars = {"possibleBooks"})
    public static void Insert(CursedTome __instance, @ByRef ArrayList<AbstractRelic>[] possibleBooks)
    {
        //if (!AbstractDungeon.player.hasRelic(Zontanonomicon.ID)) {
        //    possibleBooks[0].add(RelicLibrary.getRelic(Zontanonomicon.ID).makeCopy());
       // }
    }
}
