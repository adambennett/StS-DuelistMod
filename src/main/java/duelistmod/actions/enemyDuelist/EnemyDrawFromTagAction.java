package duelistmod.actions.enemyDuelist;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.dto.AnyDuelist;

import java.util.ArrayList;

public class EnemyDrawFromTagAction extends AbstractGameAction
{
	private boolean shuffleCheck = false;
	private CardTags tagToDraw;
	private final AnyDuelist duelist;
	private final AbstractEnemyDuelist enemy;

	public EnemyDrawFromTagAction(AnyDuelist duelist, int amount, CardTags tag) {
		this.duelist = duelist;
		this.enemy = this.duelist.getEnemy();
		setValues(this.duelist.creature(), source, amount);
		if (this.duelist.hasPower("No Draw")) {
			this.duelist.getPower("No Draw").flash();
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
		tagToDraw = tag;
	}

	public void drawTag(int numCards, CardTags tag) {
		ArrayList<AbstractCard> typedCardsFromDeck = new ArrayList<>();
		for (AbstractCard c : this.duelist.drawPile()) {
			if (c.hasTag(tag)) {
				typedCardsFromDeck.add(c);
			}
		}

		if (typedCardsFromDeck.size() > 0) {
			for (int i = 0; i < numCards; i++) {
				AbstractCard c = typedCardsFromDeck.size() == 1 ? typedCardsFromDeck.get(0) : typedCardsFromDeck.isEmpty() ? null : typedCardsFromDeck.remove(AbstractDungeon.cardRandomRng.random(typedCardsFromDeck.size() - 1));
				if (c == null) break;

				c = typedCardsFromDeck.get(AbstractDungeon.cardRandomRng.random(typedCardsFromDeck.size() - 1));
				c.current_x = CardGroup.DRAW_PILE_X;
				c.current_y = CardGroup.DRAW_PILE_Y;
				c.setAngle(0.0F, true);
				c.lighten(false);
				c.drawScale = 0.12F;
				c.targetDrawScale = 0.75F;
				if ((this.duelist.hasPower("Confusion")) && (c.cost >= 0)) {
					int newCost = AbstractDungeon.cardRandomRng.random(3);
					if (c.cost != newCost) {
						c.cost = newCost;
						c.costForTurn = c.cost;
						c.isCostModified = true;
					}
				}

				c.triggerWhenDrawn();

				BaseMod.publishPostDraw(c);
				this.enemy.addCardToHand(AbstractEnemyDuelist.fromCard(c));
				this.enemy.drawPile.removeCard(c);

				if ((this.duelist.hasPower("Corruption")) && (c.type == AbstractCard.CardType.SKILL)) {
					c.setCostForTurn(-20);
				}

				for (AbstractRelic r : this.duelist.relics()) {
					r.onCardDraw(c);
				}
			}
		}
	}

	public void drawTag(CardTags tag) {
		if (this.duelist.hand().size() == BaseMod.MAX_HAND_SIZE) {
			return;
		}

		CardCrawlGame.sound.playAV("CARD_DRAW_8", -0.12F, 0.25F);
		drawTag(1, tag);
		this.enemy.onCardDrawOrDiscard();
	}


	public void update()
	{
		if (this.amount <= 0) {
			this.isDone = true;
			return;
		}

		int deckSize = 0;
		for (AbstractCard c : this.duelist.drawPile()) {
			if (c.hasTag(tagToDraw)) {
				deckSize++;
			}
		}
		int discardSize = this.duelist.discardPile().size();

		/*if (SoulGroup.isActive()) {
			return;
		}*/

		if (deckSize + discardSize == 0) {
			this.isDone = true;
			return;
		}

		if (this.duelist.hand().size() == BaseMod.MAX_HAND_SIZE) {
			this.isDone = true;
			return;
		}

		if (!this.shuffleCheck) {
			if (this.amount + this.duelist.hand().size() > BaseMod.MAX_HAND_SIZE) {
				int handSizeAndDraw = BaseMod.MAX_HAND_SIZE - (this.amount + this.duelist.hand().size());
				this.amount += handSizeAndDraw;
			}
			if (this.amount > deckSize) {
				int tmp = this.amount - deckSize;
				int taggedCardsInDiscard = 0;
				for (AbstractCard c : this.duelist.discardPile()) {
					if (c.hasTag(tagToDraw)) {
						taggedCardsInDiscard++;
					}
				}
				if (taggedCardsInDiscard > 0) {
					AbstractDungeon.actionManager.addToTop(new EnemyDrawFromTagAction(this.duelist, tmp, tagToDraw));
					AbstractDungeon.actionManager.addToTop(new EnemyShuffleOnlyTaggedAction(tagToDraw, this.duelist));
				}
				if (deckSize != 0) {
					AbstractDungeon.actionManager.addToTop(new EnemyDrawFromTagAction(this.duelist, deckSize, tagToDraw));
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

			if (!this.duelist.drawPile().isEmpty()) {
				drawTag(tagToDraw);
				this.duelist.handGroup().refreshHandLayout();
			} else {
				this.isDone = true;
			}

			if (this.amount == 0) {
				this.isDone = true;
			}
		}
	}
}
