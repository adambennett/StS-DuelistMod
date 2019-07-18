package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.*;
import duelistmod.helpers.StarterDeckSetup;
import duelistmod.variables.Strings;

public class DragonRelicB extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("DragonRelicB");
	public static final String IMG = DuelistMod.makeRelicPath("DragonRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicPath("DragonRelic.png");
	
	public DragonRelicB() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.RARE, LandingSound.MAGICAL);
		//this.counter = 0;
	}
	
	@Override
	public boolean canSpawn()
	{
		String deck = StarterDeckSetup.getCurrentDeck().getSimpleName();
		if (deck.equals("Dragon Deck")) { return true; }
		else { return false; }
	}
	
	@Override
	public void onTrigger() 
	{
		//if (DuelistMod.dragonRelicBFlipper) { setCounter(1); }
		//else { setCounter(0); }
	}
	
	
	@Override
	public void onEquip()
	{
		setDescription();
		//this.counter = 0;
	}
	
	@Override
	public void onUnequip()
	{
		
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
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
		return new DragonRelicB();
	}
}
