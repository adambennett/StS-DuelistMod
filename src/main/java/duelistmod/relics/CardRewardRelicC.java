package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.helpers.Util;
import duelistmod.interfaces.CardRewardRelic;
import duelistmod.variables.Strings;

public class CardRewardRelicC extends DuelistRelic implements CardRewardRelic
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
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		return !Util.hasCardRewardRelic() && !DuelistMod.persistentDuelistData.CardPoolSettings.getRemoveCardRewards();
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
    		//this.usedUp = true;
    		this.grayscale = true;
    	}
    }
}
