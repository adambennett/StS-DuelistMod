package duelistmod.abstracts;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import duelistmod.rewards.BoosterPack;

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
	
	protected void addToBot(final AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }
    
    protected void addToTop(final AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }
    
    public void onReceiveBoosterPack(BoosterPack pack) { }
    
	public void onSoulChange(int newSouls, int change) { }
	
	public void onEndOfBattle() { }
	
    public int modifyShadowDamage() { return 0; }
	
	public void onLoseArtifact() { }
	
	public void onChangeStance() { }
	
	public void onOverflow(int amt) { }
	
	public void onFish(ArrayList<AbstractCard> discarded, ArrayList<AbstractCard> aquasDiscarded) { }
	
	public void onTribute(DuelistCard tributedMon, DuelistCard tributingMon) { }
	
	public void onSummon(DuelistCard summoned, int amountSummoned) { }
	
	public void onIncrement(int amount, int newMaxSummons) { }
	
	public void onResummon(DuelistCard resummoned) { }
	
	public int modifyResummonAmt(AbstractCard resummoningCard) { return 0; }
	
	public boolean allowResummon(AbstractCard resummoningCard) { return true; }
	
	public boolean upgradeResummon(AbstractCard resummoningCard) { return false; }
	
	public void onSynergyTribute() { }
	
	public float modifyBlock(float blockAmount, AbstractCard card) { return blockAmount; }
	
	public void onEnemyUseCard(final AbstractCard card) { }
	
	public void onDetonate() { }
	
	public void onSolder() { }
	
	public void onPassRoulette() { }
	
	public boolean canSpawn() { return true; }
	
	public int modifySummons(int magicAmt) { return magicAmt; }
	
	public int modifyTributes(int magicAmt) { return magicAmt; }
	
	public int modifySummons(int magicAmt, AbstractCard card) { return this.modifySummons(magicAmt); }
	
	public int modifyTributes(int magicAmt, AbstractCard card) { return this.modifyTributes(magicAmt); }

	public float modifyMagicNumber(float magicAmt) { return magicAmt; }
	
	public float modifyMagicNumber(float magicAmt, AbstractCard card) { return this.modifyMagicNumber(magicAmt); }

	public float modifySecondMagicNumber(float magicAmt) { return magicAmt; }
	
	public float modifySecondMagicNumber(float magicAmt, AbstractCard card) { return this.modifySecondMagicNumber(magicAmt); }
	
	public float modifyThirdMagicNumber(float magicAmt) { return magicAmt; }
	
	public float modifyThirdMagicNumber(float magicAmt, AbstractCard card) { return this.modifyThirdMagicNumber(magicAmt); }
	
}
