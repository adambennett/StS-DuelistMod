package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import duelistmod.dto.AnyDuelist;
import duelistmod.powers.duelistPowers.DarkBribePower;

@SpirePatch(clz= ArtifactPower.class,method="onSpecificTrigger")
public class ArtifactPatch {

    @SuppressWarnings("rawtypes")
	public static SpireReturn Prefix(ArtifactPower __instance) {
        AnyDuelist duelist = AnyDuelist.from(__instance);
        if (__instance.amount <= 0 && duelist.hasPower(DarkBribePower.POWER_ID)) {
            DarkBribePower power = (DarkBribePower) duelist.getPower(DarkBribePower.POWER_ID);
            power.zeroArtifactsTrigger();
        }
        return SpireReturn.Continue();
    }

}
