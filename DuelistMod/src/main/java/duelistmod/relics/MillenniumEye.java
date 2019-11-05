package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.AbstractRelic.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.helpers.StarterDeckSetup;
import duelistmod.powers.ToonWorldPower;

public class MillenniumEye extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("MillenniumEye");
	public static final String IMG = DuelistMod.makeRelicPath("MillenniumEyeRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("MillenniumEye_Outline.png");

	public MillenniumEye() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}

	@Override
	public boolean canSpawn()
	{
		String deck = StarterDeckSetup.getCurrentDeck().getSimpleName();
		boolean allowSpawn = false;
		if (DuelistMod.toonBtnBool) 
		{ 
			if (deck.equals("Toon Deck")) { allowSpawn = false; }
			if (DuelistMod.setIndex == 6) { allowSpawn = true; }
		}
		else
		{
			if (deck.equals("Machine Deck")) { allowSpawn = true; }
			if (deck.equals("Dragon Deck")) { allowSpawn = true; }
			if (deck.equals("Toon Deck")) { allowSpawn = false; }
			if (DuelistMod.setIndex == 6) { allowSpawn = true; }
		}
		return allowSpawn;
	}

	// Summon 1 on turn start
	@Override
	public void atBattleStart() 
	{
		String deck = StarterDeckSetup.getCurrentDeck().getSimpleName();
		if (!(AbstractDungeon.player.hasRelic(MillenniumSymbol.ID) && deck.equals("Toon Deck"))) 
		{
			this.flash();
			AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ToonWorldPower(AbstractDungeon.player, AbstractDungeon.player, 0)));
		}
		this.grayscale = true;
	}
	
	@Override
    public void onVictory() 
    {
		this.grayscale = false;
    }

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new MillenniumEye();
	}

}
