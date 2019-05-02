package duelistmod.actions.common;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.*;

import basemod.BaseMod;
import duelistmod.Tags;

@SuppressWarnings("unused")
public class MakeEtherealCopyInHandAction extends com.megacrit.cardcrawl.actions.AbstractGameAction
{

	private static final float DURATION_PER_CARD = 0.35F;
	private AbstractCard c;
	private static final float PADDING = 25.0F * Settings.scale;
	private boolean isOtherCardInCenter = true;

	public MakeEtherealCopyInHandAction(AbstractCard card, boolean isOtherCardInCenter, boolean upgrade) {
		UnlockTracker.markCardAsSeen(card.cardID);
		this.amount = 1;
		this.actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = 0.35F;
		this.c = card;
		this.isOtherCardInCenter = isOtherCardInCenter;
		if (upgrade) { this.c.upgrade(); }
		this.c.isEthereal = true;
		this.c.rawDescription = "Ethereal NL " + this.c.rawDescription;
	}

	public MakeEtherealCopyInHandAction(AbstractCard card, boolean upgrade) {
		this(card, 1, upgrade);
	}

	public MakeEtherealCopyInHandAction(AbstractCard card, int amount, boolean upgrade) {
		UnlockTracker.markCardAsSeen(card.cardID);
		//if (upgrade) { card.upgrade(); }
		//card.isEthereal = true;
		//card.rawDescription = "Ethereal. NL " + card.rawDescription;
		this.amount = amount;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = 0.35F;
		this.c = card;
		if (upgrade) { this.c.upgrade(); }
		this.c.isEthereal = true;
		this.c.rawDescription = "Ethereal NL " + this.c.rawDescription;
	}

	public MakeEtherealCopyInHandAction(AbstractCard card, int amount, boolean isOtherCardInCenter, boolean upgrade) {
		this(card, amount, upgrade);
	}


	public void update()
	{
		if (!c.isEthereal) {
            c.isEthereal = true;
            c.rawDescription = "Ethereal NL " + c.rawDescription;
            c.initializeDescription();
		}
		
		if (this.amount == 0) {
			this.isDone = true;
			return;
		}

		int discardAmount = 0;
		int handAmount = this.amount;


		if (this.amount + AbstractDungeon.player.hand.size() > BaseMod.MAX_HAND_SIZE) {
			AbstractDungeon.player.createHandIsFullDialog();
			discardAmount = this.amount + AbstractDungeon.player.hand.size() - BaseMod.MAX_HAND_SIZE;
			handAmount -= discardAmount;
		}

		addToHand(handAmount);
		addToDiscard(discardAmount);

		if (this.amount > 0) {
			AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.utility.WaitAction(0.8F));
		}

		this.isDone = true;
	}

	private void addToHand(int handAmt) {
		
		if (!c.isEthereal && !c.hasTag(Tags.NEVER_ETHEREAL)) {
            c.isEthereal = true;
            c.rawDescription = "Ethereal NL " + c.rawDescription;
            c.initializeDescription();
		}
		
		switch (this.amount) {
		case 0: 
			break;
		case 1: 
			if (handAmt == 1) {
				if (this.isOtherCardInCenter) 
				{
					if (!c.isEthereal && !c.hasTag(Tags.NEVER_ETHEREAL)) {
			            c.isEthereal = true;
			            c.rawDescription = "Ethereal NL " + c.rawDescription;
			            c.initializeDescription();
					}
					AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.c

							.makeStatEquivalentCopy(), Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0F));
				}
				else
				{
					AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.c.makeStatEquivalentCopy()));
				}
			}
			break;
		case 2: 
			if (handAmt == 1) 
			{
				if (!c.isEthereal && !c.hasTag(Tags.NEVER_ETHEREAL)) {
		            c.isEthereal = true;
		            c.rawDescription = "Ethereal NL " + c.rawDescription;
		            c.initializeDescription();
				}
				AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.c

						.makeStatEquivalentCopy(), Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH * 0.5F), Settings.HEIGHT / 2.0F));

			}
			else if (handAmt == 2) 
			{
				if (!c.isEthereal && !c.hasTag(Tags.NEVER_ETHEREAL)) {
		            c.isEthereal = true;
		            c.rawDescription = "Ethereal NL " + c.rawDescription;
		            c.initializeDescription();
				}
				AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.c

						.makeStatEquivalentCopy(), Settings.WIDTH / 2.0F + (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0F));


				AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.c

						.makeStatEquivalentCopy(), Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0F));
			}


			break;
		case 3: 
			if (handAmt == 1) 
			{
				if (!c.isEthereal && !c.hasTag(Tags.NEVER_ETHEREAL)) {
		            c.isEthereal = true;
		            c.rawDescription = "Ethereal NL " + c.rawDescription;
		            c.initializeDescription();
				}
				AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.c

						.makeStatEquivalentCopy(), Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0F));

			}
			else if (handAmt == 2) 
			{
				if (!c.isEthereal && !c.hasTag(Tags.NEVER_ETHEREAL)) {
		            c.isEthereal = true;
		            c.rawDescription = "Ethereal NL " + c.rawDescription;
		            c.initializeDescription();
				}
				AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.c

						.makeStatEquivalentCopy(), Settings.WIDTH / 2.0F + (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0F));


				AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.c

						.makeStatEquivalentCopy(), Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0F));

			}
			else if (handAmt == 3)
			{
				if (!c.isEthereal && !c.hasTag(Tags.NEVER_ETHEREAL)) {
		            c.isEthereal = true;
		            c.rawDescription = "Ethereal NL " + c.rawDescription;
		            c.initializeDescription();
				}
				for (int i = 0; i < this.amount; i++) {
					AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.c.makeStatEquivalentCopy()));
				}
			}
			break;
		default: 
			if (!c.isEthereal && !c.hasTag(Tags.NEVER_ETHEREAL)) {
	            c.isEthereal = true;
	            c.rawDescription = "Ethereal NL " + c.rawDescription;
	            c.initializeDescription();
			}
			for (int i = 0; i < handAmt; i++) {
				AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.c

						.makeStatEquivalentCopy(), 
						MathUtils.random(Settings.WIDTH * 0.2F, Settings.WIDTH * 0.8F), 
						MathUtils.random(Settings.HEIGHT * 0.3F, Settings.HEIGHT * 0.7F)));
			}
		}
	}

	private void addToDiscard(int discardAmt)
	{
		switch (this.amount) {
		case 0: 
			break;
		case 1: 
			if (discardAmt == 1) {
				AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.c

						.makeStatEquivalentCopy(), Settings.WIDTH / 2.0F + (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0F));
			}


			break;
		case 2: 
			if (discardAmt == 1) {
				AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.c

						.makeStatEquivalentCopy(), Settings.WIDTH * 0.5F - (PADDING + AbstractCard.IMG_WIDTH * 0.5F), Settings.HEIGHT * 0.5F));

			}
			else if (discardAmt == 2) {
				AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.c

						.makeStatEquivalentCopy(), Settings.WIDTH * 0.5F - (PADDING + AbstractCard.IMG_WIDTH * 0.5F), Settings.HEIGHT * 0.5F));


				AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.c

						.makeStatEquivalentCopy(), Settings.WIDTH * 0.5F + (PADDING + AbstractCard.IMG_WIDTH * 0.5F), Settings.HEIGHT * 0.5F));
			}


			break;
		case 3: 
			if (discardAmt == 1) {
				AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.c

						.makeStatEquivalentCopy(), Settings.WIDTH * 0.5F + (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT * 0.5F));

			}
			else if (discardAmt == 2) {
				AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.c

						.makeStatEquivalentCopy(), Settings.WIDTH * 0.5F, Settings.HEIGHT * 0.5F));


				AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.c

						.makeStatEquivalentCopy(), Settings.WIDTH * 0.5F + (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT * 0.5F));

			}
			else if (discardAmt == 3) {
				AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.c

						.makeStatEquivalentCopy(), Settings.WIDTH * 0.5F, Settings.HEIGHT * 0.5F));


				AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.c

						.makeStatEquivalentCopy(), Settings.WIDTH * 0.5F - (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT * 0.5F));


				AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.c

						.makeStatEquivalentCopy(), Settings.WIDTH * 0.5F + (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT * 0.5F));
			}


			break;
		default: 
			for (int i = 0; i < discardAmt; i++) {
				AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.c

						.makeStatEquivalentCopy(), 
						MathUtils.random(Settings.WIDTH * 0.2F, Settings.WIDTH * 0.8F), 
						MathUtils.random(Settings.HEIGHT * 0.3F, Settings.HEIGHT * 0.7F)));
			}
		}
	}
}

