package duelistmod.abstracts;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public abstract class DuelistPower extends TwoAmountPower 
{
	/*public DuelistPower(AbstractCreature owner, AbstractCreature source, int amt)
	{
		
	}*/
	
	protected void addToBot(final AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }
    
    protected void addToTop(final AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }
	
	public void onTribute(DuelistCard tributedMon, DuelistCard tributingMon) { }
	
	public void onSummon(DuelistCard summoned, int amountSummoned) { }
	
	public void onIncrement(int amount, int newMaxSummons) { }
	
	public void onResummon(DuelistCard resummoned) { }
	
	public void onSynergyTribute() { }
	
	public void onGainVines() { }
	
	public void onLoseArtifact() { }
	
	public void onOverflow(int amt) { }
	
	public void onFish(ArrayList<AbstractCard> discarded, ArrayList<AbstractCard> aquasDiscarded) { }

	public float modifyBlock(float blockAmount, AbstractCard card) { return blockAmount; }
	
	public void onEnemyUseCard(final AbstractCard card) { }
	
	public void onDexChange() { }
	
	public void onDetonate() { }
	
	public void onSolder() { }
	
	public void onPassRoulette() { }
	
	public float modifyMagicNumber(float magicAmt) { return magicAmt; }
	
	public float modifyMagicNumber(float magicAmt, AbstractCard card) { return this.modifyMagicNumber(magicAmt); }
	
	public float modifySecondMagicNumber(float magicAmt) { return magicAmt; }
	
	public float modifySecondMagicNumber(float magicAmt, AbstractCard card) { return this.modifySecondMagicNumber(magicAmt); }
	
	public float modifyThirdMagicNumber(float magicAmt) { return magicAmt; }
	
	public float modifyThirdMagicNumber(float magicAmt, AbstractCard card) { return this.modifyThirdMagicNumber(magicAmt); }
	
	public int modifySummons(int magicAmt) { return magicAmt; }
	
	public int modifyTributes(int magicAmt) { return magicAmt; }
	
	public int modifySummons(int magicAmt, AbstractCard card) { return this.modifySummons(magicAmt); }
	
	public int modifyTributes(int magicAmt, AbstractCard card) { return this.modifyTributes(magicAmt); }
}
