package duelistmod.cards.other.tempCards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.abstracts.DuelistCard;

public class CreatorCard extends DuelistCard 
{

	public String fxText = "";
	public boolean applyPoison = false;
	
	public CreatorCard(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR,CardRarity RARITY, CardTarget TARGET) 
	{
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.dontTriggerOnUseCard = true;
	}



	

	





	@Override
	public void upgrade() {
		// TODO Auto-generated method stub

	}

	@Override
	public void use(AbstractPlayer arg0, AbstractMonster arg1) {
		// TODO Auto-generated method stub

	}

}
