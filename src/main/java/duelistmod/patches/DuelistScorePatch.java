package duelistmod.patches;

import basemod.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.screens.*;
import duelistmod.*;
import duelistmod.helpers.*;

import java.io.*;

public class DuelistScorePatch
{
    @SpirePatch(clz = VictoryScreen.class, method = "calculateUnlockProgress")
    public static class DuelistScore_Score_Victory_Screen
    {
        @SpirePostfixPatch
        public static void GetScoreAndUpdateDuelist(VictoryScreen __instance)
        {
            int score = (int)ReflectionHacks.getPrivate(__instance, VictoryScreen.class, "score");
            updateDuelistScore(score, "Score on victory: ", "Duelist Score updated on victory! Old Score: ");
        }

    }

    @SpirePatch(clz = DeathScreen.class, method = "calculateUnlockProgress")
    public static class DuelistScore_Score_Death_Screen
    {
        @SpirePostfixPatch
        public static void GetScoreAndUpdateDuelist(DeathScreen __instance)
        {
            int score = (int)ReflectionHacks.getPrivate(__instance, DeathScreen.class, "score");
            updateDuelistScore(score, "Score on death: ", "Duelist Score updated on death! Old Score: ");
        }
    }

    private static void updateDuelistScore(int score, String scoreLog, String updateLog) {
        Util.log(scoreLog + score);
        try {
            SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig", DuelistMod.duelistDefaults);
            config.load();
            int duelistScore = config.getInt("duelistScore");
            int newScore = duelistScore + score;
            if (newScore > duelistScore) {
                config.setInt("duelistScore", newScore);
                Util.log(updateLog + duelistScore + " / New Score: " + newScore);
            }
        } catch(IOException ignored) {
            Util.log("Did not update duelistScore due to IOException");
        }
    }
}
