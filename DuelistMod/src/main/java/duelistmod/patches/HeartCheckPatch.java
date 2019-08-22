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
    	if (AbstractDungeon.ascensionLevel > 19 && AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST)) { DuelistMod.bonusUnlockHelper.beatHeartA20(); }
    	else { DuelistMod.bonusUnlockHelper.beatHeart(); }
    }
}