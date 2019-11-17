package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.BaseMod;
import duelistmod.*;
import duelistmod.abstracts.*;
import duelistmod.cards.other.tokens.*;
import duelistmod.helpers.*;
import duelistmod.powers.duelistPowers.WonderGaragePower;

public class Bombchain extends DuelistRelic 
{
	// ID, images, text.
	public static final String ID = DuelistMod.makeID("Bombchain");
	public static final String IMG = DuelistMod.makeRelicPath("MachineRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicPath("MachineRelic.png");

	public Bombchain() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}

	@Override
	public boolean canSpawn()
	{
		if (Util.deckIs("Machine Deck")) { return true; }
		else { return false; }
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	@Override
	public void onDetonate()
	{
		boolean hasWG = AbstractDungeon.player.hasPower(WonderGaragePower.POWER_ID);
		int maxRoll = 3;
		int handSpace = BaseMod.DEFAULT_MAX_HAND_SIZE - AbstractDungeon.player.hand.group.size();
		if (maxRoll > handSpace) { maxRoll = handSpace; }
		int tokenRoll = AbstractDungeon.cardRandomRng.random(1, maxRoll);	
		if (AbstractDungeon.cardRandomRng.random(1,4) == 1)
		{
			for (int i = 0; i < tokenRoll; i++)
			{
				DuelistCard tok = DuelistCardLibrary.getTokenInCombat(new SuperExplodingToken());
				if (!hasWG) { if (AbstractDungeon.cardRandomRng.random(1, 4) == 1) { tok.upgrade(); }}
				DuelistCard.addCardToHand(tok);
			}
		}
		else
		{
			for (int i = 0; i < tokenRoll; i++)
			{				
				DuelistCard tok = DuelistCardLibrary.getTokenInCombat(new ExplosiveToken());
				if (!hasWG) { if (AbstractDungeon.cardRandomRng.random(1, 3) == 1) { tok.upgrade(); }}
				DuelistCard.addCardToHand(tok);
			}
		}
		this.flash();
	}
	
	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new Bombchain();
	}
}
