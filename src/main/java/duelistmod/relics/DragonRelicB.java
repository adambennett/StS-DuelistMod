package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.helpers.*;

public class DragonRelicB extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("DragonRelicB");
	public static final String IMG = DuelistMod.makeRelicPath("DragonPendantRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("DragonPendantRelic_Outline.png");
	
	public DragonRelicB() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
		//this.counter = 0;
	}
	
	@Override
	public boolean canSpawn()
	{
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		if (Util.deckIs("Dragon Deck")) { return true; }
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
