package duelistmod.patches;
import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.abstracts.CustomPlayer;
import duelistmod.DuelistMod;


@SpirePatch(
		clz = CustomPlayer.class,
		method = "getCardPool"
		)

public class CardColorsPoolPatch 
{
	@SpireInsertPatch(rloc=0)
	public static void insert(CustomPlayer __instance, @ByRef ArrayList<AbstractCard> tmpPool) 
	{
		if (__instance.name.equals("the Duelist"))
		{
			DuelistMod.fillColoredCards();
			for (AbstractCard c : DuelistMod.coloredCards)
			{
				tmpPool.add(c);
			}
		}
	}
}

