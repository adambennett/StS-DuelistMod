package defaultmod.actions.common;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.*;

@SuppressWarnings("unused")
public class Make0CostHandCardAction extends AbstractGameAction
{
	private static final float DURATION_PER_CARD = 0.35F;
	private AbstractCard c;
	private static final float PADDING = 25.0F * Settings.scale;
	private boolean isOtherCardInCenter = true;

	public Make0CostHandCardAction(AbstractCard card, boolean isOtherCardInCenter) {
		UnlockTracker.markCardAsSeen(card.cardID);
		card.costForTurn = 0;
		card.isCostModifiedForTurn = true;
		this.amount = 1;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = 0.35F;
		this.c = card;
		this.isOtherCardInCenter = isOtherCardInCenter;
		
	}

	public Make0CostHandCardAction(AbstractCard card) {
		this(card, 1);
	}

	public Make0CostHandCardAction(AbstractCard card, int amount) {
		UnlockTracker.markCardAsSeen(card.cardID);
		card.costForTurn = 0;
		card.isCostModifiedForTurn = true;
		this.amount = amount;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = 0.35F;
		this.c = card;
	}
	
	public Make0CostHandCardAction(AbstractCard card, boolean isOtherCardInCenter, String justUseThisFunctionToUpgrade) {
		UnlockTracker.markCardAsSeen(card.cardID);
		card.costForTurn = 0;
		card.isCostModifiedForTurn = true;
		card.upgrade();
		this.amount = 1;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = 0.35F;
		this.c = card;
		this.isOtherCardInCenter = isOtherCardInCenter;
		
	}

	public Make0CostHandCardAction(AbstractCard card, int amount, boolean isOtherCardInCenter) {
		this(card, amount);
	}

	@Override
	public void update() 
	{
		if (this.amount == 0) {
			this.isDone = true;
			return;
		}

		int discardAmount = 0;
		int handAmount = this.amount;


		if (this.amount + AbstractDungeon.player.hand.size() > 10) {
			AbstractDungeon.player.createHandIsFullDialog();
			discardAmount = this.amount + AbstractDungeon.player.hand.size() - 10;
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
		switch (this.amount) {
		case 0: 
			break;
		case 1: 
			if (handAmt == 1) {
				if (this.isOtherCardInCenter) {
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
			if (handAmt == 1) {
				AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.c

						.makeStatEquivalentCopy(), Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH * 0.5F), Settings.HEIGHT / 2.0F));

			}
			else if (handAmt == 2) {
				AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.c

						.makeStatEquivalentCopy(), Settings.WIDTH / 2.0F + (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0F));


				AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.c

						.makeStatEquivalentCopy(), Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0F));
			}


			break;
		case 3: 
			if (handAmt == 1) {
				AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.c

						.makeStatEquivalentCopy(), Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0F));

			}
			else if (handAmt == 2) {
				AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.c

						.makeStatEquivalentCopy(), Settings.WIDTH / 2.0F + (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0F));


				AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.c

						.makeStatEquivalentCopy(), Settings.WIDTH / 2.0F - (PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0F));

			}
			else if (handAmt == 3)
			{

				for (int i = 0; i < this.amount; i++) {
					AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.c.makeStatEquivalentCopy()));
				}
			}
			break;
		default: 
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