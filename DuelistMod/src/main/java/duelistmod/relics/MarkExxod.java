package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import duelistmod.DuelistMod;
import duelistmod.helpers.StarterDeckSetup;
import duelistmod.variables.Strings;

public class MarkExxod extends CustomRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("MarkExxod");
    public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
    public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);

	public MarkExxod() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL);
	}
	
	@Override
	public boolean canSpawn()
	{
		String deck = StarterDeckSetup.getCurrentDeck().getSimpleName();
		if (deck.equals("Exodia Deck")) { return false; }
		else { return true; }
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	@Override
	public void onEquip()
	{
		AbstractDungeon.player.increaseMaxHp(10, true);
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new MarkExxod();
	}
}
