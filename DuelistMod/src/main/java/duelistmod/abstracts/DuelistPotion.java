package duelistmod.abstracts;

import com.megacrit.cardcrawl.potions.AbstractPotion;

public abstract class DuelistPotion extends AbstractPotion
{
	public DuelistPotion(String name, String id, PotionRarity rarity, PotionSize size, PotionColor color) 
	{
		super(name, id, rarity, size, color);
	}
	
	public void onChangeStance() { }
	
	public void onTribute(DuelistCard tributedMon, DuelistCard tributingMon) { }
	
	public void onSummon(DuelistCard summoned, int amountSummoned) { }
	
	public void onIncrement(int amount, int newMaxSummons) { }
	
	public void onResummon(DuelistCard resummoned) { }
	
	public void onSynergyTribute() { }
}
