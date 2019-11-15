package duelistmod.abstracts;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.abstracts.CustomRelic;

public abstract class DuelistRelic extends CustomRelic implements ClickableRelic
{
	
	public DuelistRelic(String id, Texture texture, Texture outline, RelicTier tier, LandingSound sfx) 
	{
		super(id, texture, outline, tier, sfx);
	}
	
	public DuelistRelic(String id, Texture texture, Texture outline, RelicTier tier, LandingSound sfx, int counter) 
	{
		super(id, texture, outline, tier, sfx);
		this.setCounter(counter);
	}
	
	@Override
    public void onRightClick() 
    {
    	if (this.counter > 0)
    	{
    		flash();
    		this.counter--;
    	}
    }
	
	public void onGainVines() { }
	
	public void onTribute(DuelistCard tributedMon, DuelistCard tributingMon) { }
	
	public void onSummon(DuelistCard summoned, int amountSummoned) { }
	
	public void onIncrement(int amount, int newMaxSummons) { }
	
	public void onResummon(DuelistCard resummoned) { }
	
	public void onSynergyTribute() { }
	
	public void onLoseArtifact() { }
	
	public void onOverflow(int amt) { }
	
	public float modifyBlock(float blockAmount, AbstractCard card) { return blockAmount; }
	
	public void onEnemyUseCard(final AbstractCard card) { }
	
	public void onDetonate() { }
	
	public void onSolder() { }
	
	public void onPassRoulette() { }
	
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
