package duelistmod.abstracts;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;

import duelistmod.actions.unique.PurgeSpecificCard;
import duelistmod.relics.*;

public class TokenCard extends DuelistCard
{
	public TokenCard(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR,CardRarity RARITY, CardTarget TARGET) 
	{
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
	}

	@Override
	public void triggerOnEndOfPlayerTurn()
	{
		AbstractDungeon.effectList.add(new ExhaustCardEffect(this));
		AbstractDungeon.actionManager.addToTop(new PurgeSpecificCard(this, AbstractDungeon.player.hand));
	}
	
	@Override
	public boolean canUpgrade()
	{
		if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(TokenUpgradeRelic.ID)) { return true; }
		else { return super.canUpgrade(); }
	}
	
	@Override
	public void update()
	{
		super.update();
		if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(MillenniumToken.ID) && this.cost > 0)
		{
			this.setCostForTurn(-this.cost);
			this.isCostModifiedForTurn = true;
			AbstractDungeon.player.hand.glowCheck();
		}
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
