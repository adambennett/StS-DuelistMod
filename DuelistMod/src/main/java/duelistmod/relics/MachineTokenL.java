package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.other.tokens.*;
import duelistmod.helpers.*;

public class MachineTokenL extends DuelistRelic 
{
	// ID, images, text.
	public static final String ID = duelistmod.DuelistMod.makeID("MachineTokenL");
	public static final String IMG = DuelistMod.makeRelicPath("MachineRelic.png");
	public static final String OUTLINE = DuelistMod.makeRelicPath("MachineRelic.png");

	public MachineTokenL() {
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
		if (AbstractDungeon.cardRandomRng.random(1,2) == 1)
		{
			DuelistCard.addCardToHand(new ExplosiveToken());
		}
		else
		{
			DuelistCard.addCardToHand(new SuperExplodingToken());
		}
		this.flash();
	}
	
	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new MachineTokenL();
	}
}
