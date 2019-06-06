package duelistmod.patches;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import duelistmod.DuelistMod;


@SpirePatch(
		clz = CardLibrary.class,
		method = "getEachRare"
		)

public class CardLibPatch 
{
	public static SpireReturn<CardGroup> Prefix(AbstractPlayer p)
	{
		if (p.chosenClass.equals(TheDuelistEnum.THE_DUELIST))
		{
			CardGroup everyRareCard = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard c : DuelistMod.rareCardInPool) 
			{
				{
					everyRareCard.addToBottom(c.makeCopy());
				}
			}
			return SpireReturn.Return(everyRareCard);
		}
		else
		{
			return SpireReturn.Continue();
		}
	}
}

