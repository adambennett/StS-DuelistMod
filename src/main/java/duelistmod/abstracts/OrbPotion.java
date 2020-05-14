package duelistmod.abstracts;

import com.badlogic.gdx.graphics.Color;

public abstract class OrbPotion extends DuelistPotion
{
	public OrbPotion(String name, String id, PotionRarity rarity, PotionSize size, PotionEffect pfx, Color liquid) 
	{
		super(name, id, rarity, size, pfx, liquid, null, null);
	}
	
	public OrbPotion(String name, String id, PotionRarity rarity, PotionSize size, PotionEffect pfx, Color liquid, Color hybrid) 
	{
		super(name, id, rarity, size, pfx, liquid, hybrid, null);
	}
	
	public OrbPotion(String name, String id, PotionRarity rarity, PotionSize size, PotionEffect pfx, Color liquid, Color hybrid, Color spots) 
	{
		super(name, id, rarity, size, pfx, liquid, hybrid, spots);
	}
}
