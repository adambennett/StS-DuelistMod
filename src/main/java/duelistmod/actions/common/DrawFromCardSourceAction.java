package duelistmod.actions.common;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.abstracts.DuelistCard;
import duelistmod.enums.CardSource;
import duelistmod.helpers.BaseGameHelper;

import java.util.ArrayList;

public class DrawFromCardSourceAction extends AbstractGameAction {

	private boolean shuffleCheck = false;
	private CardSource sourceToDraw;

	public DrawFromCardSourceAction(AbstractCreature source, int amount, CardSource tag) {
		setValues(AbstractDungeon.player, source, amount);
		if (AbstractDungeon.player.hasPower("No Draw")) {
			AbstractDungeon.player.getPower("No Draw").flash();
			this.isDone = true;
			this.duration = 0.0F;
			this.actionType = ActionType.WAIT;
			return;
		}

		this.actionType = ActionType.DRAW;
		if (Settings.FAST_MODE) {
			this.duration = Settings.ACTION_DUR_XFAST;
		} else {
			this.duration = Settings.ACTION_DUR_FASTER;
		}
		this.sourceToDraw = tag;
	}

	public void drawTag(int numCards, CardSource tag) {
		ArrayList<AbstractCard> typedCardsFromDeck = new ArrayList<>();
		for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
			if (isMatchingCard(c, tag) && !c.type.equals(CardType.STATUS) && !c.type.equals(CardType.CURSE)) {
				typedCardsFromDeck.add(c);
			}
		}

		if (typedCardsFromDeck.size() > 0) {
			for (int i = 0; i < numCards; i++) {
				AbstractCard c = typedCardsFromDeck.size() == 1 ? typedCardsFromDeck.get(0) : typedCardsFromDeck.isEmpty() ? null : typedCardsFromDeck.remove(AbstractDungeon.cardRandomRng.random(typedCardsFromDeck.size() - 1));
				if (c == null) break;

				c.current_x = CardGroup.DRAW_PILE_X;
				c.current_y = CardGroup.DRAW_PILE_Y;
				c.setAngle(0.0F, true);
				c.lighten(false);
				c.drawScale = 0.12F;
				c.targetDrawScale = 0.75F;
				if ((AbstractDungeon.player.hasPower("Confusion")) && (c.cost >= 0)) {
					int newCost = AbstractDungeon.cardRandomRng.random(3);
					if (c.cost != newCost) {
						c.cost = newCost;
						c.costForTurn = c.cost;
						c.isCostModified = true;
					}
				}

				c.triggerWhenDrawn();
				BaseMod.publishPostDraw(c);
				AbstractDungeon.player.hand.addToHand(c);
				AbstractDungeon.player.drawPile.removeCard(c);

				if ((AbstractDungeon.player.hasPower("Corruption")) && (c.type == CardType.SKILL)) {
					c.setCostForTurn(-9);
				}

				for (AbstractRelic r : AbstractDungeon.player.relics) {
					r.onCardDraw(c);
				}

			}
		}
	}

	public void drawTag(CardSource tag)
	{
		if (AbstractDungeon.player.hand.size() == BaseMod.MAX_HAND_SIZE) {
			AbstractDungeon.player.createHandIsFullDialog();
			return;
		}

		CardCrawlGame.sound.playAV("CARD_DRAW_8", -0.12F, 0.25F);
		drawTag(1, tag);
		AbstractDungeon.player.onCardDrawOrDiscard();
	}

	public boolean isMatchingCard(AbstractCard c) {
		return isMatchingCard(c, null);
	}

	public boolean isMatchingCard(AbstractCard c, CardSource tag) {
		CardSource checkTag = tag == null ? sourceToDraw : tag;
		boolean isBaseGame = BaseGameHelper.isBaseGameCardId(c.cardID, true);
		return (isBaseGame && checkTag == CardSource.BASE_GAME) ||
			   (!isBaseGame && checkTag == CardSource.DUELIST && c instanceof DuelistCard) ||
			   (!isBaseGame && checkTag == CardSource.OTHER);
	}


	public void update()
	{
		if (this.amount <= 0) {
			this.isDone = true;
			return;
		}

		int deckSize = 0;
		for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
			if (isMatchingCard(c)) {
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
				for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
					if (isMatchingCard(c)) {
						taggedCardsInDiscard++;
					}
				}
				if (taggedCardsInDiscard > 0) {
					AbstractDungeon.actionManager.addToTop(new DrawFromCardSourceAction(AbstractDungeon.player, tmp, sourceToDraw));
					AbstractDungeon.actionManager.addToTop(new ShuffleOnlyCardSourceAction(this, sourceToDraw));
				}
				if (deckSize != 0) {
					AbstractDungeon.actionManager.addToTop(new DrawFromCardSourceAction(AbstractDungeon.player, deckSize, sourceToDraw));
				}
				this.amount = 0;
				this.isDone = true;
			}
			this.shuffleCheck = true;
		}

		this.duration -= com.badlogic.gdx.Gdx.graphics.getDeltaTime();

		if ((this.amount != 0) && (this.duration < 0.0F)) {
			if (Settings.FAST_MODE) {
				this.duration = Settings.ACTION_DUR_XFAST;
			} else {
				this.duration = Settings.ACTION_DUR_FASTER;
			}

			this.amount -= 1;

			if (!AbstractDungeon.player.drawPile.isEmpty()) {
				drawTag(sourceToDraw);
				AbstractDungeon.player.hand.refreshHandLayout();
			} else {
				this.isDone = true;
			}

			if (this.amount == 0) {
				this.isDone = true;
			}
		}
	}
}
