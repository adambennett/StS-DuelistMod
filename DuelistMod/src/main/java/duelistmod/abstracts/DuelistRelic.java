package duelistmod.abstracts;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;

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
}
