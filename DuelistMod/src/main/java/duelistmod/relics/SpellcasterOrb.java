package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnChannelRelic;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.StarterDeckSetup;
import duelistmod.variables.Strings;

public class SpellcasterOrb extends CustomRelic implements OnChannelRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("SpellcasterOrb");
	public static final String IMG = DuelistMod.makeRelicPath("SpellcasterRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicPath("SpellcasterRelic.png");

	
	public SpellcasterOrb() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.MAGICAL);
		setDescription();
	}
	
	@Override
	public void onChannel(AbstractOrb arg0) 
	{
		DuelistCard.damageAllEnemiesThornsFire(3);
	}
	
	@Override
	public boolean canSpawn()
	{
		String deck = StarterDeckSetup.getCurrentDeck().getSimpleName();
		if (deck.equals("Spellcaster Deck")) { return true; }
		else { return false; }
	}
	
	@Override
	public void onEquip()
	{
		setDescription();
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
		return new SpellcasterOrb();
	}
}
