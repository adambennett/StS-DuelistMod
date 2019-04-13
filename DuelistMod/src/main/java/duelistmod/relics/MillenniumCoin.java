package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.*;

import basemod.abstracts.CustomRelic;
import duelistmod.*;
import duelistmod.interfaces.DuelistCard;

public class MillenniumCoin extends CustomRelic 
{
	// ID, images, text.
	public static final String ID = duelistmod.DuelistMod.makeID("MillenniumCoin");
	public static final String IMG = DuelistMod.makePath(Strings.M_COIN_RELIC);
	public static final String OUTLINE = DuelistMod.makePath(Strings.M_COIN_RELIC_OUTLINE);
	private static int rollCheck = 3;

	public MillenniumCoin() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.RARE, LandingSound.MAGICAL);
		this.counter = 10;
	}
	
	@Override
	public void onEquip()
	{
		this.counter = 10;
	}

	@Override
	public void onEvokeOrb(AbstractOrb ammo) 
	{
		int roll = AbstractDungeon.cardRandomRng.random(1, 10);
		if (roll <= rollCheck)
		{
			flash();
			DuelistCard.gainGold(this.counter, AbstractDungeon.player, true);
		}
	}
	
	 @Override
	    public void onVictory()
	    {
	        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite|| AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) 
	        {
	        	flash();
	            int roll = AbstractDungeon.cardRandomRng.random(1, 10);
	            this.counter += 10;
	    		if (roll <= 3)
	    		{
		            if (rollCheck < 10) { rollCheck++; }
	    		}
	    		setDescription();
	        }
	    }

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + this.counter + DESCRIPTIONS[1];
	}
	
	public void setDescription()
	{
		description = getUpdatedDescription();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new MillenniumCoin();
	}
}
