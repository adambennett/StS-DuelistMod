package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.*;
import duelistmod.helpers.StarterDeckSetup;
import duelistmod.variables.Strings;

public class ToonRelic extends CustomRelic 
{
	// ID, images, text.
	public static final String ID = DuelistMod.makeID("ToonRelic");
	public static final String IMG = DuelistMod.makeRelicPath("ToonRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicPath("ToonRelic.png");
	
	public ToonRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}
	
	@Override
	public boolean canSpawn()
	{
		String deck = StarterDeckSetup.getCurrentDeck().getSimpleName();
    	boolean allowSpawn = false;
    	if (DuelistMod.toonBtnBool) 
    	{ 
    		if (deck.equals("Toon Deck")) { allowSpawn = true; }
    		if (DuelistMod.setIndex == 6) { allowSpawn = true; }
    	}
    	else
    	{
    		if (deck.equals("Machine Deck")) { allowSpawn = true; }
    		if (deck.equals("Dragon Deck")) { allowSpawn = true; }
    		if (deck.equals("Toon Deck")) { allowSpawn = true; }
    		if (DuelistMod.setIndex == 6) { allowSpawn = true; }
    	}
		return allowSpawn;
	}
	
	@Override
	public void onEquip()
	{
		setDescription();
		DuelistMod.toonVuln = 2;
	}
	
	@Override
	public void onUnequip()
	{
		DuelistMod.toonVuln = 2;
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
		return new ToonRelic();
	}
}
