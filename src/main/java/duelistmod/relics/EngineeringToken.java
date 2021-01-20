package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.actions.common.SolderAction;
import duelistmod.helpers.*;

public class EngineeringToken extends DuelistRelic 
{
	// ID, images, text.
	public static final String ID = duelistmod.DuelistMod.makeID("EngineeringToken");
	public static final String IMG = DuelistMod.makeRelicPath("EngineeringToken.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("EngineeringToken_Outline.png");

	public EngineeringToken() {
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
		this.addToBot(new SolderAction(AbstractDungeon.player.hand.group, 2, true));
		this.flash();
	}
	
	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new EngineeringToken();
	}
}
