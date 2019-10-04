package duelistmod.abstracts;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;

public abstract class DuelistPower extends TwoAmountPower 
{
	public void onTribute(DuelistCard tributedMon, DuelistCard tributingMon) { }
	
	public void onSummon(DuelistCard summoned, int amountSummoned) { }
	
	public void onIncrement(int amount, int newMaxSummons) { }
	
	public void onResummon(DuelistCard resummoned) { }
	
	public void onSynergyTribute() { }
	
	public void onGainVines() { }
}
