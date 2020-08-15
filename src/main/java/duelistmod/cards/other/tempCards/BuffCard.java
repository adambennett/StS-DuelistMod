package duelistmod.cards.other.tempCards;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.abstracts.DuelistCard;

public class BuffCard extends DuelistCard
{
	public AbstractPower powerToApply;

	public BuffCard(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR,CardRarity RARITY, CardTarget TARGET) 
	{
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.dontTriggerOnUseCard = true;
	}

	@Override
	public BuffCard makeStatEquivalentCopy() {
		BuffCard out = (BuffCard) super.makeStatEquivalentCopy();
		out.rawDescription = this.rawDescription;
		out.powerToApply = this.powerToApply;
		out.dontTriggerOnUseCard = true;
		return out;
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onTribute(DuelistCard tributingCard) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		// TODO Auto-generated method stub
		
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
