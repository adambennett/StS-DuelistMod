package duelistmod.patches;
import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;

import basemod.abstracts.CustomPlayer;
import duelistmod.*;
import duelistmod.helpers.*;


@SpirePatch(
		clz = CustomPlayer.class,
		method = "getCardPool"
		)

public class CardColorsPoolPatch 
{
	@SpireInsertPatch(rloc=0)
	public static void insert(CustomPlayer __instance, @ByRef ArrayList<AbstractCard> tmpPool) 
	{
		if (__instance.name.equals(DuelistMod.duelistChar.name))
		{
			if (DuelistMod.shouldFill)
			{ 
				PoolHelpers.newFillColored();
				BoosterPackHelper.setupPoolsForPacks();
				DuelistMod.shouldFill = false;
			}
			else { PoolHelpers.coloredCardsHadCards(); }
			for (AbstractCard c : DuelistMod.coloredCards)
			{
				if (!c.rarity.equals(CardRarity.SPECIAL))
				{
					tmpPool.add(c);
				}				
			}
		}
	}
}

