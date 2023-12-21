package duelistmod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.actions.common.DrawFromCardSourceAction;
import duelistmod.enums.CardSource;
import duelistmod.helpers.Util;
import duelistmod.variables.Strings;

import static duelistmod.enums.StartingDeck.*;

public class SphinxInsight extends DuelistRelic {

	public static final String ID = DuelistMod.makeID("SphinxInsight");
	public static final String IMG = DuelistMod.makePath(Strings.TEMP_RELIC);
	public static final String OUTLINE = DuelistMod.makePath(Strings.TEMP_RELIC_OUTLINE);

	public SphinxInsight() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SHOP, LandingSound.FLAT);
	}
	
	@Override
	public boolean canSpawn() {
        return super.canSpawn() && Util.deckIs(PHARAOH_I.getDeckName(), PHARAOH_II.getDeckName(), PHARAOH_III.getDeckName(), PHARAOH_IV.getDeckName(), PHARAOH_V.getDeckName());
	}

	@Override
	public void onUseCard(final AbstractCard card, final UseCardAction action) {
		int roll = AbstractDungeon.cardRandomRng.random(0, 100);
		if (roll <= 10) {
			this.flash();
			CardSource drawFrom = card instanceof DuelistCard ? CardSource.BASE_GAME : CardSource.DUELIST;
			this.addToBot(new DrawFromCardSourceAction(AbstractDungeon.player, 1, drawFrom));
		}
	}

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	@Override
	public AbstractRelic makeCopy() {
		return new SphinxInsight();
	}
}
