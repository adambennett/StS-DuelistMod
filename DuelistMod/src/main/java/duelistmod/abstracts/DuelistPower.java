package duelistmod.abstracts;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.cards.AbstractCard;

public abstract class DuelistPower extends TwoAmountPower 
{
	/*public DuelistPower(AbstractCreature owner, AbstractCreature source, int amt)
	{
		
	}*/
	
	public void onTribute(DuelistCard tributedMon, DuelistCard tributingMon) { }
	
	public void onSummon(DuelistCard summoned, int amountSummoned) { }
	
	public void onIncrement(int amount, int newMaxSummons) { }
	
	public void onResummon(DuelistCard resummoned) { }
	
	public void onSynergyTribute() { }
	
	public void onGainVines() { }
	
	public void onLoseArtifact() { }
	
	public float modifyBlock(float blockAmount, AbstractCard card) { return blockAmount; }
	
	public void onEnemyUseCard(final AbstractCard card) { }
	
	public void onDexChange() { }
	
	public void onDetonate() { }
	
	public void onSolder() { }
	
	public void onPassRoulette() { }
	
	public float modifyMagicNumber(float magicAmt) { return magicAmt; }
	
	public float modifyMagicNumber(float magicAmt, AbstractCard card) { return this.modifyMagicNumber(magicAmt); }
}
