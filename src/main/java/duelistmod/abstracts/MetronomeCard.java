package duelistmod.abstracts;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;

public abstract class MetronomeCard extends DuelistCard
{
	public boolean returnsMultiple = false;
	
	public MetronomeCard(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR,CardRarity RARITY, CardTarget TARGET) 
	{
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
	}
	
	public ArrayList<AbstractCard> returnCards()
	{
		ArrayList<AbstractCard> tmp = new ArrayList<>();
		return tmp;
	}
	
	public abstract AbstractCard returnCard();

}
