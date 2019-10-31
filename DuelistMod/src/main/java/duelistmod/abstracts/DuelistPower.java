package duelistmod.abstracts;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

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
}
