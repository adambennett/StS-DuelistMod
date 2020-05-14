package duelistmod.abstracts;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;

public abstract class MutateCard extends DuelistCard
{
	public MutateCard(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR,CardRarity RARITY, CardTarget TARGET) 
	{
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
	}
	
	public void runMutation(AbstractCard c) { }
	
	public boolean canSpawnInOptions(ArrayList<AbstractCard> mutatePool) { return true; }

}
