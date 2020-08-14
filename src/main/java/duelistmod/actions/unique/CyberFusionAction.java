package duelistmod.actions.unique;
import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.PlayerTurnEffect;

import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.*;
import duelistmod.variables.Tags;

public class CyberFusionAction extends com.megacrit.cardcrawl.actions.AbstractGameAction
{
	private boolean shuffleCheck = false;
	private CardTags tagToDraw;
	private boolean draw = true;
	private AbstractMonster targ;
	private boolean upgraded;
	private boolean ranResummon = false;

	public CyberFusionAction(AbstractCreature source, int amount, boolean endTurnDraw, CardTags tag, AbstractMonster target, boolean upgraded) {
		if (endTurnDraw) 
		{
			AbstractDungeon.topLevelEffects.add(new PlayerTurnEffect());
		} 
		else if (AbstractDungeon.player.hasPower("No Draw")) 
		{
			AbstractDungeon.player.getPower("No Draw").flash();
			setValues(AbstractDungeon.player, source, amount);
			this.isDone = false;
			this.draw = false;
		}

		setValues(AbstractDungeon.player, source, amount);
		this.actionType = AbstractGameAction.ActionType.SPECIAL;
		if (Settings.FAST_MODE) 
		{
			this.duration = Settings.ACTION_DUR_XFAST;
		} 
		else 
		{
			this.duration = Settings.ACTION_DUR_FASTER;
		}
		tagToDraw = tag;
		this.targ = target;
		this.upgraded = upgraded;
	}

	public CyberFusionAction(AbstractCreature source, int amount, CardTags tag, AbstractMonster m, boolean upgraded) {
		this(source, amount, false, tag, m, upgraded);
	}


	public void drawTag(int numCards, CardTags tag)
	{
		ArrayList<AbstractCard> typedCardsFromDeck = new ArrayList<AbstractCard>();
		for (AbstractCard c : AbstractDungeon.player.drawPile.group)
		{
			if (c.hasTag(tag))
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

	public void drawTag(CardTags tag)
	{
		if (AbstractDungeon.player.hand.size() == 10) {
			AbstractDungeon.player.createHandIsFullDialog();
			return;
		}

		CardCrawlGame.sound.playAV("CARD_DRAW_8", -0.12F, 0.25F);
		drawTag(1, tag);
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
			if (c.hasTag(tagToDraw))
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

		if (AbstractDungeon.player.hand.size() == 10) {
			AbstractDungeon.player.createHandIsFullDialog();
			this.isDone = true;
			return;
		}

		if (!this.shuffleCheck) {
			if (this.amount + AbstractDungeon.player.hand.size() > 10) {
				int handSizeAndDraw = 10 - (this.amount + AbstractDungeon.player.hand.size());
				this.amount += handSizeAndDraw;
				AbstractDungeon.player.createHandIsFullDialog();
			}
			if (this.amount > deckSize) {
				int tmp = this.amount - deckSize;
				int taggedCardsInDiscard = 0;
				for (AbstractCard c : AbstractDungeon.player.discardPile.group)
				{
					if (c.hasTag(tagToDraw))
					{
						taggedCardsInDiscard++;
					}
				}
				if (taggedCardsInDiscard > 0 && this.draw)
				{
					AbstractDungeon.actionManager.addToTop(new CyberFusionAction(AbstractDungeon.player, tmp, tagToDraw, this.targ, this.upgraded));
					AbstractDungeon.actionManager.addToTop(new ShuffleOnlyTaggedAction(tagToDraw));
					//AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
				}
				if (deckSize != 0 && this.draw) {
					AbstractDungeon.actionManager.addToTop(new CyberFusionAction(AbstractDungeon.player, deckSize, tagToDraw, this.targ, this.upgraded));
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

			if (!AbstractDungeon.player.drawPile.isEmpty() && this.draw) 
			{
				drawTag(tagToDraw);
				AbstractDungeon.player.hand.refreshHandLayout();
			} 
			else 
			{
				if (!this.ranResummon) {
					ArrayList<AbstractCard> handCards = new ArrayList<AbstractCard>();
					for (AbstractCard a : AbstractDungeon.player.hand.group) { if (a.hasTag(Tags.MACHINE) && DuelistCard.allowResummonsWithExtraChecks(a)) { handCards.add(a); }}
					if (handCards.size() > 0)
					{
						if (!upgraded)
						{
							AbstractCard summon = DuelistCard.returnRandomFromArrayAbstract(handCards);
							DuelistCard cardCopy = (DuelistCard)summon;
							if (cardCopy != null)
							{
								AbstractMonster m = AbstractDungeon.getRandomMonster();
								if (m != null) { DuelistCard.resummon(cardCopy, m, false, summon.upgraded); }
							}
						}
						else
						{
							ArrayList<AbstractCard> choices = new ArrayList<>(); for (AbstractCard c : handCards) { if (c instanceof DuelistCard) { choices.add((DuelistCard)c); }}
							AbstractMonster m = AbstractDungeon.getRandomMonster();
							if (m != null) { this.addToBot(new CardSelectScreenResummonAction(choices, 1, m)); }
						}
					}
					this.ranResummon = true;
				}
				this.isDone = true;
			}

			if (this.amount == 0) 
			{
				if (!this.ranResummon) {
					ArrayList<AbstractCard> handCards = new ArrayList<AbstractCard>();
					for (AbstractCard a : AbstractDungeon.player.hand.group) { if (a.hasTag(Tags.MACHINE) && DuelistCard.allowResummonsWithExtraChecks(a)) { handCards.add(a); }}
					if (handCards.size() > 0)
					{
						if (!upgraded)
						{
							AbstractCard summon = DuelistCard.returnRandomFromArrayAbstract(handCards);
							DuelistCard cardCopy = (DuelistCard)summon;
							if (cardCopy != null)
							{
								if (this.targ != null && !this.targ.isDead && !this.targ.isDying && !this.targ.isDeadOrEscaped() && !this.targ.halfDead)
								{
									DuelistCard.resummon(cardCopy, this.targ, false, summon.upgraded);
								}
								else
								{
									AbstractMonster m = AbstractDungeon.getRandomMonster();
									if (m != null) { DuelistCard.resummon(cardCopy, m, false, summon.upgraded); }
								}
							}
						}
						else
						{
							ArrayList<AbstractCard> choices = new ArrayList<>(); for (AbstractCard c : handCards) { if (c instanceof DuelistCard) { choices.add((DuelistCard)c); }}
							if (this.targ != null && !this.targ.isDead && !this.targ.isDying && !this.targ.isDeadOrEscaped() && !this.targ.halfDead)
							{
								this.addToBot(new CardSelectScreenResummonAction(choices, 1, this.targ));
							}
							else
							{
								AbstractMonster m = AbstractDungeon.getRandomMonster();
								if (m != null) { this.addToBot(new CardSelectScreenResummonAction(choices, 1, m)); }
							}
						}
					}
					this.ranResummon = true;
				}
				this.isDone = true;
			}
		}
	}
}
