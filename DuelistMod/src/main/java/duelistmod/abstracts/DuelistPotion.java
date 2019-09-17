package duelistmod.abstracts;

import com.megacrit.cardcrawl.potions.AbstractPotion;

public abstract class DuelistPotion extends AbstractPotion
{
	public DuelistPotion(String name, String id, PotionRarity rarity, PotionSize size, PotionColor color) 
	{
		super(name, id, rarity, size, color);
	}
	
	public void onChangeStance() { }
}
