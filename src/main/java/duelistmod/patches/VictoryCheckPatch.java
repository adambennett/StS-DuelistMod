package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.stats.CharStat;
import com.megacrit.cardcrawl.screens.stats.StatsScreen;
import duelistmod.helpers.BonusDeckUnlockHelper;

@SpirePatch(clz = StatsScreen.class, method = "incrementVictory")
public class VictoryCheckPatch {
	public static void Postfix(CharStat c) {
		if (AbstractDungeon.player.chosenClass.name().equals("THE_DUELIST")) {
			BonusDeckUnlockHelper.onWin();
		}
	}
}
