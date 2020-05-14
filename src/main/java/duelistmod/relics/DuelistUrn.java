package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.variables.Tags;

public class DuelistUrn extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("DuelistUrn");
	public static final String IMG = DuelistMod.makeRelicPath("DuelistUrn.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("DuelistUrn_Outline.png");

	public DuelistUrn() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.RARE, LandingSound.SOLID);
	}

	@Override
	public void onUseCard(final AbstractCard card, final UseCardAction action) 
	{
		if (card.hasTag(Tags.TRAP)) 
		{
			this.flash();
			this.addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 2));
			this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
		}
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new DuelistUrn();
	}
}
