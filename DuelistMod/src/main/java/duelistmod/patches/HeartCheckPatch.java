package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.ending.CorruptHeart;

import duelistmod.DuelistMod;
import duelistmod.helpers.Util;

@SpirePatch(
		clz=CorruptHeart.class,
        method="die"
)
public class HeartCheckPatch 
{
    public static void Prefix() 
    {
    	if (AbstractDungeon.ascensionLevel > 19 && AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST)) { DuelistMod.bonusUnlockHelper.beatHeart(); }
    	else { Util.log("Beat the Heart! Ascension was below 20, or you're playing as a character that is not the Duelist (how could you)"); }
    }
}