package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import duelistmod.abstracts.DuelistCard;

@SpirePatch(clz = ArtifactPower.class, method = "onSpecificTrigger")
public class LoseArtifactsPatch
{
    public static void Postfix(ArtifactPower __instance)
    {
    	if (__instance.owner.equals(AbstractDungeon.player)) { DuelistCard.handleOnLoseArtifactForAllAbstracts(); }
    }
}
