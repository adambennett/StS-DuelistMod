package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.screens.stats.*;
import duelistmod.*;
import duelistmod.abstracts.*;
import duelistmod.helpers.*;
import duelistmod.relics.*;


@SpirePatch(
		clz = StatsScreen.class,
		method = "incrementVictory"
		)

public class VictoryCheckPatch
{
	public static void Postfix(CharStat c)
	{
		if (AbstractDungeon.player.chosenClass.name().equals("THE_DUELIST")) {
			DuelistMod.bonusUnlockHelper.onWin();
		}
	}
}

