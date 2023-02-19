package duelistmod.actions.utility;

import basemod.BaseMod;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.*;

import duelistmod.abstracts.MutateCard;

public class AddCardToHandAndMutate extends AbstractGameAction
{
    private static final float DURATION_PER_CARD = 0.35f;
    private AbstractCard c;
    private MutateCard m;
    private static final float PADDING;
    private boolean isOtherCardInCenter;
    private boolean sameUUID;
    
    public AddCardToHandAndMutate(final AbstractCard card, final boolean isOtherCardInCenter, MutateCard mutation) {
        this.isOtherCardInCenter = true;
        this.sameUUID = false;
        UnlockTracker.markCardAsSeen(card.cardID);
        this.amount = 1;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = 0.35f;
        this.c = card;
        this.m = mutation;
        if (this.c.type != AbstractCard.CardType.CURSE && this.c.type != AbstractCard.CardType.STATUS && AbstractDungeon.player.hasPower("MasterRealityPower")) {
            this.c.upgrade();
        }
        this.isOtherCardInCenter = isOtherCardInCenter;
    }
    
    public AddCardToHandAndMutate(final AbstractCard card, MutateCard mutation) {
        this(card, 1, mutation);
    }
    
    public AddCardToHandAndMutate(final AbstractCard card, final int amount, MutateCard mutation) {
        this.isOtherCardInCenter = true;
        this.sameUUID = false;
        UnlockTracker.markCardAsSeen(card.cardID);
        this.amount = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = 0.35f;
        this.c = card;
        if (this.c.type != AbstractCard.CardType.CURSE && this.c.type != AbstractCard.CardType.STATUS && AbstractDungeon.player.hasPower("MasterRealityPower")) {
            this.c.upgrade();
        }
    }
    
    public AddCardToHandAndMutate(final AbstractCard card, final int amount, final boolean isOtherCardInCenter, MutateCard mutation) {
        this(card, amount, mutation);
        this.isOtherCardInCenter = isOtherCardInCenter;
    }
    
    public AddCardToHandAndMutate(final AbstractCard card, final boolean isOtherCardInCenter, final boolean sameUUID, MutateCard mutation) {
        this(card, 1, mutation);
        this.isOtherCardInCenter = isOtherCardInCenter;
        this.sameUUID = sameUUID;
    }
    
    @Override
    public void update() {
        if (this.amount == 0) {
            this.isDone = true;
            return;
        }
        int discardAmount = 0;
        int handAmount = this.amount;
        if (this.amount + AbstractDungeon.player.hand.size() > BaseMod.MAX_HAND_SIZE) {
            AbstractDungeon.player.createHandIsFullDialog();
            discardAmount = this.amount + AbstractDungeon.player.hand.size() - 10;
            handAmount -= discardAmount;
        }
        this.addToHand(handAmount);
        this.addToDiscard(discardAmount);
        if (this.amount > 0) {
            this.addToTop(new WaitAction(0.8f));
        }
        this.isDone = true;
    }
    
    private void addToHand(final int handAmt) {
        switch (this.amount) {
            case 0: {
                break;
            }
            case 1: {
                if (handAmt != 1) {
                    break;
                }
                if (this.isOtherCardInCenter) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.makeNewCard(), Settings.WIDTH / 2.0f - (AddCardToHandAndMutate.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0f));
                    break;
                }
                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.makeNewCard()));
                break;
            }
            case 2: {
                if (handAmt == 1) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.makeNewCard(), Settings.WIDTH / 2.0f - (AddCardToHandAndMutate.PADDING + AbstractCard.IMG_WIDTH * 0.5f), Settings.HEIGHT / 2.0f));
                    break;
                }
                if (handAmt == 2) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.makeNewCard(), Settings.WIDTH / 2.0f + (AddCardToHandAndMutate.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0f));
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.makeNewCard(), Settings.WIDTH / 2.0f - (AddCardToHandAndMutate.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0f));
                    break;
                }
                break;
            }
            case 3: {
                if (handAmt == 1) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.makeNewCard(), Settings.WIDTH / 2.0f - (AddCardToHandAndMutate.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0f));
                    break;
                }
                if (handAmt == 2) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.makeNewCard(), Settings.WIDTH / 2.0f + (AddCardToHandAndMutate.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0f));
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.makeNewCard(), Settings.WIDTH / 2.0f - (AddCardToHandAndMutate.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0f));
                    break;
                }
                if (handAmt == 3) {
                    for (int i = 0; i < this.amount; ++i) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.makeNewCard()));
                    }
                    break;
                }
                break;
            }
            default: {
                for (int i = 0; i < handAmt; ++i) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(this.makeNewCard(), MathUtils.random(Settings.WIDTH * 0.2f, Settings.WIDTH * 0.8f), MathUtils.random(Settings.HEIGHT * 0.3f, Settings.HEIGHT * 0.7f)));
                }
                break;
            }
        }
    }
    
    private void addToDiscard(final int discardAmt) {
        switch (this.amount) {
            case 0: {
                break;
            }
            case 1: {
                if (discardAmt == 1) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH / 2.0f + (AddCardToHandAndMutate.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT / 2.0f));
                    break;
                }
                break;
            }
            case 2: {
                if (discardAmt == 1) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH * 0.5f - (AddCardToHandAndMutate.PADDING + AbstractCard.IMG_WIDTH * 0.5f), Settings.HEIGHT * 0.5f));
                    break;
                }
                if (discardAmt == 2) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH * 0.5f - (AddCardToHandAndMutate.PADDING + AbstractCard.IMG_WIDTH * 0.5f), Settings.HEIGHT * 0.5f));
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH * 0.5f + (AddCardToHandAndMutate.PADDING + AbstractCard.IMG_WIDTH * 0.5f), Settings.HEIGHT * 0.5f));
                    break;
                }
                break;
            }
            case 3: {
                if (discardAmt == 1) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH * 0.5f + (AddCardToHandAndMutate.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT * 0.5f));
                    break;
                }
                if (discardAmt == 2) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH * 0.5f, Settings.HEIGHT * 0.5f));
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH * 0.5f + (AddCardToHandAndMutate.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT * 0.5f));
                    break;
                }
                if (discardAmt == 3) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH * 0.5f, Settings.HEIGHT * 0.5f));
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH * 0.5f - (AddCardToHandAndMutate.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT * 0.5f));
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.makeNewCard(), Settings.WIDTH * 0.5f + (AddCardToHandAndMutate.PADDING + AbstractCard.IMG_WIDTH), Settings.HEIGHT * 0.5f));
                    break;
                }
                break;
            }
            default: {
                for (int i = 0; i < discardAmt; ++i) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(this.makeNewCard(), MathUtils.random(Settings.WIDTH * 0.2f, Settings.WIDTH * 0.8f), MathUtils.random(Settings.HEIGHT * 0.3f, Settings.HEIGHT * 0.7f)));
                }
                break;
            }
        }
    }
    
    private AbstractCard makeNewCard() 
    {
    	if (this.sameUUID)
    	{
    		AbstractCard toRet = this.c.makeSameInstanceOf();
        	this.m.runMutation(toRet);
        	return toRet;
    	}
    	else
    	{
    		AbstractCard toRet = this.c.makeStatEquivalentCopy();
        	this.m.runMutation(toRet);
        	return toRet;
    	}    	
    }
    
    static {
        PADDING = 25.0f * Settings.scale;
    }
}
