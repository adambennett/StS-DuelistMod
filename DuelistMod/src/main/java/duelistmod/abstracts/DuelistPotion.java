package duelistmod.abstracts;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.potions.AbstractPotion;

public abstract class DuelistPotion extends AbstractPotion
{
	public DuelistPotion(String name, String id, PotionRarity rarity, PotionSize size, PotionEffect pfx, Color liquid) 
	{
		super(name, id, rarity, size, pfx, liquid, null, null);
	}
	
	public DuelistPotion(String name, String id, PotionRarity rarity, PotionSize size, PotionEffect pfx, Color liquid, Color hybrid) 
	{
		super(name, id, rarity, size, pfx, liquid, hybrid, null);
	}
	
	public DuelistPotion(String name, String id, PotionRarity rarity, PotionSize size, PotionEffect pfx, Color liquid, Color hybrid, Color spots) 
	{
		super(name, id, rarity, size, pfx, liquid, hybrid, spots);
	}
	
	public void onLoseArtifact() { }
	
	public void onChangeStance() { }
	
	public void onTribute(DuelistCard tributedMon, DuelistCard tributingMon) { }
	
	public void onSummon(DuelistCard summoned, int amountSummoned) { }
	
	public void onIncrement(int amount, int newMaxSummons) { }
	
	public void onResummon(DuelistCard resummoned) { }
	
	public void onSynergyTribute() { }
	
	public float modifyBlock(float blockAmount, AbstractCard card) { return blockAmount; }
	
	public void onEnemyUseCard(final AbstractCard card) { }
	
	public void onDetonate() { }
	
	public void onSolder() { }
	
	public void onPassRoulette() { }
	
	public boolean canSpawn() { return true; }
}
