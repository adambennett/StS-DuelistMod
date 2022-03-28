package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.monsters.*;
import duelistmod.*;

public class MonsterGroupPatches {

    @SpirePatch(clz = MonsterGroup.class, method = "applyEndOfTurnPowers")
    public static class MonsterRoundEnd
    {
        public static void Postfix(MonsterGroup __instance) {
            for (AbstractMonster m : __instance.monsters) {
                if ((!m.isDying) && (!m.isEscaping)) {
                    DuelistMod.onVeryEndOfMonsterTurn(m);
                }
            }
        }
    }
}
