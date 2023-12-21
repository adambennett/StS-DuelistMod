package duelistmod.actions.common;
import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.PlayerTurnEffect;

import basemod.BaseMod;

public class DrawFromBothTagsAction extends com.megacrit.cardcrawl.actions.AbstractGameAction
{
	private boolean shuffleCheck = false;
	private CardTags tagToDraw;
	private CardTags tagToDrawB;

	public DrawFromBothTagsAction(AbstractCreature source, int amount, boolean endTurnDraw, CardTags tag, CardTags tagB) {
		if (endTurnDraw) {
			AbstractDungeon.topLevelEffects.add(new PlayerTurnEffect());
		} else if (AbstractDungeon.player.hasPower("No Draw")) {
			AbstractDungeon.player.getPower("No Draw").flash();
			setValues(AbstractDungeon.player, source, amount);
			this.isDone = true;
			this.duration = 0.0F;
			this.actionType = AbstractGameAction.ActionType.WAIT;
			return;
		}

		setValues(AbstractDungeon.player, source, amount);
		this.actionType = AbstractGameAction.ActionType.DRAW;
		if (Settings.FAST_MODE) {
			this.duration = Settings.ACTION_DUR_XFAST;
		} else {
			this.duration = Settings.ACTION_DUR_FASTER;
		}
		tagToDraw = tag;
		tagToDrawB = tagB;
	}

	public DrawFromBothTagsAction(AbstractCreature source, int amount, CardTags tag, CardTags tagB) {
		this(source, amount, false, tag, tagB);
	}

	public void drawTag(int numCards, CardTags tag, CardTags tagB)
	{
		ArrayList<AbstractCard> typedCardsFromDeck = new ArrayList<AbstractCard>();
		for (AbstractCard c : AbstractDungeon.player.drawPile.group)
		{
			if (c.hasTag(tag) && c.hasTag(tagB))
			{
				typedCardsFromDeck.add(c);
			}
		}

		if (typedCardsFromDeck.size() > 0)
		{
			for (int i = 0; i < numCards; i++) 
			{ 
				AbstractCard c;
				int newCost; 
				c = typedCardsFromDeck.get(AbstractDungeon.cardRandomRng.random(typedCardsFromDeck.size() - 1));
				c.current_x = CardGroup.DRAW_PILE_X;
				c.current_y = CardGroup.DRAW_PILE_Y;
				c.setAngle(0.0F, true);
				c.lighten(false);
				c.drawScale = 0.12F;
				c.targetDrawScale = 0.75F;
				if ((AbstractDungeon.player.hasPower("Confusion")) && (c.cost >= 0)) 
				{
					newCost = AbstractDungeon.cardRandomRng.random(3);
					if (c.cost != newCost) 
					{
						c.cost = newCost;
						c.costForTurn = c.cost;
						c.isCostModified = true;
					}
				}

				c.triggerWhenDrawn();

				BaseMod.publishPostDraw(c);
				AbstractDungeon.player.hand.addToHand(c);
				AbstractDungeon.player.drawPile.removeCard(c);

				if ((AbstractDungeon.player.hasPower("Corruption")) && (c.type == AbstractCard.CardType.SKILL)) 
				{
					c.setCostForTurn(-9);
				}

				for (AbstractRelic r : AbstractDungeon.player.relics) 
				{
					r.onCardDraw(c);
				}

			}
		}
	}

	public void drawTag(CardTags tag, CardTags tagB)
	{
		if (AbstractDungeon.player.hand.size() == BaseMod.MAX_HAND_SIZE) {
			AbstractDungeon.player.createHandIsFullDialog();
			return;
		}

		CardCrawlGame.sound.playAV("CARD_DRAW_8", -0.12F, 0.25F);
		drawTag(1, tag, tagB);
		AbstractDungeon.player.onCardDrawOrDiscard();
	}


	public void update()
	{
		if (this.amount <= 0) {
			this.isDone = true;
			return;
		}

		int deckSize = 0;
		for (AbstractCard c : AbstractDungeon.player.drawPile.group)
		{
			if (c.hasTag(tagToDraw) && c.hasTag(tagToDrawB))
			{
				deckSize++;
			}
		}
		int discardSize = AbstractDungeon.player.discardPile.size();


		if (SoulGroup.isActive()) {
			return;
		}


		if (deckSize + discardSize == 0) {
			this.isDone = true;
			return;
		}

		if (AbstractDungeon.player.hand.size() == BaseMod.MAX_HAND_SIZE) {
			AbstractDungeon.player.createHandIsFullDialog();
			this.isDone = true;
			return;
		}

		if (!this.shuffleCheck) {
			if (this.amount + AbstractDungeon.player.hand.size() > BaseMod.MAX_HAND_SIZE) {
				int handSizeAndDraw = 10 - (this.amount + AbstractDungeon.player.hand.size());
				this.amount += handSizeAndDraw;
				AbstractDungeon.player.createHandIsFullDialog();
			}
			if (this.amount > deckSize) {
				int tmp = this.amount - deckSize;
				int taggedCardsInDiscard = 0;
				for (AbstractCard c : AbstractDungeon.player.discardPile.group)
				{
					if (c.hasTag(tagToDraw) && c.hasTag(tagToDrawB))
					{
						taggedCardsInDiscard++;
					}
				}
				if (taggedCardsInDiscard > 0)
				{
					AbstractDungeon.actionManager.addToTop(new DrawFromBothTagsAction(AbstractDungeon.player, tmp, tagToDraw, tagToDrawB));
					AbstractDungeon.actionManager.addToTop(new ShuffleOnlyTaggedAction(tagToDraw));
					//AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
				}
				if (deckSize != 0) {
					AbstractDungeon.actionManager.addToTop(new DrawFromBothTagsAction(AbstractDungeon.player, deckSize, tagToDraw, tagToDrawB));
				}
				this.amount = 0;
				this.isDone = true;
			}
			this.shuffleCheck = true;
		}

		this.duration -= com.badlogic.gdx.Gdx.graphics.getDeltaTime();

		if ((this.amount != 0) && (this.duration < 0.0F)) 
		{
			if (Settings.FAST_MODE) 
			{
				this.duration = Settings.ACTION_DUR_XFAST;
			} 
			else 
			{
				this.duration = Settings.ACTION_DUR_FASTER;
			}

			this.amount -= 1;

			if (!AbstractDungeon.player.drawPile.isEmpty()) 
			{
				drawTag(tagToDraw, tagToDrawB);
				AbstractDungeon.player.hand.refreshHandLayout();
			} 
			else 
			{
				this.isDone = true;
			}

			if (this.amount == 0) 
			{
				this.isDone = true;
			}
		}
	}
}
