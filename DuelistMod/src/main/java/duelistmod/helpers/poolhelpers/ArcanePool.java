package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;

import duelistmod.DuelistMod;

public class ArcanePool 
{
	public static ArrayList<AbstractCard> deck()
	{
		ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
		for (AbstractCard c : DuelistMod.arcaneCards)
		{
			if (!c.rarity.equals(CardRarity.SPECIAL) && !c.rarity.equals(CardRarity.BASIC))
			{
				cards.add(c.makeStatEquivalentCopy());
			}
		}
		return cards;
	}
}
