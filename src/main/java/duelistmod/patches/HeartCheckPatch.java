package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.ending.CorruptHeart;

import duelistmod.DuelistMod;

@SpirePatch(
		clz=CorruptHeart.class,
        method="die"
)
public class HeartCheckPatch 
{
    public static void Prefix() 
    {
    	if (AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST)) 
    	{ 
    		DuelistMod.bonusUnlockHelper.beatHeart(); 
    		if (AbstractDungeon.ascensionLevel > 19) { DuelistMod.bonusUnlockHelper.beatHeartA20(); }
    	}
    }
}