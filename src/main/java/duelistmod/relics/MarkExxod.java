package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.enums.StartingDecks;
import duelistmod.helpers.StarterDeckSetup;

public class MarkExxod extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("MarkExxod");
    public static final String IMG = DuelistMod.makeRelicPath("MarkExxod.png");
    public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("MarkExxodOutline.png");

	public MarkExxod() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.MAGICAL);
	}
	
	@Override
	public boolean canSpawn()
	{
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
		if (StartingDecks.currentDeck == StartingDecks.EXODIA) { return false; }
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
