package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.variables.Strings;

public class CardRewardRelicC extends CustomRelic
{
	// FIELDS
	public static final String ID = DuelistMod.makeID("CardRewardRelicC");
    public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);
    // /FIELDS

    public CardRewardRelicC() { super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL); }
    @Override public String getUpdatedDescription() { return this.DESCRIPTIONS[0]; }

    @Override
	public boolean canSpawn()
	{
		// Only spawn for non-Duelist characters
		if (DuelistMod.hasCardRewardRelic) { return false; }
		else { return true; }
	}
 
    @Override
    public void onEquip()
    {
    	this.counter = 5;
    	DuelistMod.hasCardRewardRelic = true;
    }
    
    @Override
    public void onVictory() 
    {
    	if (this.counter > 0)
    	{
    		flash();
	    	AbstractDungeon.getCurrRoom().addCardToRewards();
	    	setCounter(counter - 1);
    	}
    	else
    	{
    		this.usedUp = true;
    	}
    }
}
