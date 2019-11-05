package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.variables.Tags;

public class DuelistLetterOpener extends DuelistRelic {

	/*
	 * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
	 * 
	 * Summon 1 on combat start
	 */

	// ID, images, text.
	public static final String ID = DuelistMod.makeID("DuelistLetterOpener");
	public static final String IMG = DuelistMod.makeRelicPath("DuelistLetterOpener.png");
	public static final String OUTLINE = DuelistMod.makeRelicOutlinePath("DuelistLetterOpener_Outline.png");

	public DuelistLetterOpener() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.UNCOMMON, LandingSound.CLINK);
	}

	@Override
	public void atTurnStart() {
		this.counter = 0;
	}

	@Override
	public void onUseCard(final AbstractCard card, final UseCardAction action) 
	{
		if (card.hasTag(Tags.SPELL)) {
			++this.counter;
			if (this.counter % 3 == 0) 
			{
				this.flash();
				this.counter = 0;
				this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
				this.addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(8, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_HEAVY));
			}
		}
	}

	@Override
	public void onVictory() {
		this.counter = -1;
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new DuelistLetterOpener();
	}
}
