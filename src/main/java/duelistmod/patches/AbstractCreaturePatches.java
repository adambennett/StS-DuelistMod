package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import duelistmod.DuelistMod;

public class AbstractCreaturePatches {

    @SpirePatch(clz = AbstractCreature.class, method = "applyEndOfTurnTriggers")
    public static class AbstractCreatureEndTurnPatch {
        public static void Postfix(AbstractCreature __instance) {
            if (!(__instance instanceof AbstractPlayer)) {
                DuelistMod.unblockedDamageTakenLastTurn = DuelistMod.unblockedDamageTakenThisTurn;
                DuelistMod.unblockedDamageTakenThisTurn = false;
            }
        }
    }
}
