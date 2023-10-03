package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.Util;
import duelistmod.powers.duelistPowers.FangsPower;
import duelistmod.variables.Tags;

public class ClawedCodex extends DuelistRelic {

	public static final String ID = DuelistMod.makeID("ClawedCodex");
	public static final String IMG =  DuelistMod.makeRelicPath("ClawedCodex.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("ClawedCodex_Outline.png");

	private static final int FANG_GAIN = 3;

	public ClawedCodex() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.FLAT);
		this.counter = 0;
	}
	
	@Override
	public boolean canSpawn() {
        return super.canSpawn() && Util.deckIs("Beast Deck");
	}

	@Override
	public void onUseCard(final AbstractCard card, final UseCardAction action) {
		if (card.hasTag(Tags.BEAST)) {
			++this.counter;
			if (this.counter % 10 == 0) {
				this.counter = 0;
				this.flash();
				AnyDuelist duelist = AnyDuelist.from(this);
				this.addToBot(new RelicAboveCreatureAction(duelist.creature(), this));
				duelist.applyPowerToSelf(new FangsPower(duelist.creature(), duelist.creature(), FANG_GAIN));
			}
		}
	}

	@SuppressWarnings("ConstantValue")
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + FANG_GAIN + (FANG_GAIN == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2]);
	}

	@Override
	public AbstractRelic makeCopy() {
		return new ClawedCodex();
	}
}
