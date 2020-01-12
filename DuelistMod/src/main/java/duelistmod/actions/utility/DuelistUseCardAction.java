package duelistmod.actions.utility;

import java.util.ArrayList;

import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.actions.utility.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.abstracts.DuelistCard;
import duelistmod.characters.TheDuelist;

public class DuelistUseCardAction extends UseCardAction
{
    private AbstractCard targetCard;
    public AbstractCreature target;
    public boolean exhaustCard;
    public boolean returnToHand;
    public boolean reboundCard;
    private static final float DUR = 0.15f;
    
    public DuelistUseCardAction(final AbstractCard card, final AbstractCreature target) {
        super(card, target);
    	this.target = null;
        this.reboundCard = false;
        this.targetCard = card;
        this.target = target;
        if (card.exhaustOnUseOnce || card.exhaust) {
            this.exhaustCard = true;
        }
        this.setValues(AbstractDungeon.player, null, 1);
        this.duration = 0.15f;
        for (final AbstractPower p : AbstractDungeon.player.powers) {
            if (!card.dontTriggerOnUseCard) {
                p.onUseCard(card, this);
            }
        }
        for (final AbstractRelic r : AbstractDungeon.player.relics) {
            if (!card.dontTriggerOnUseCard) {
                r.onUseCard(card, this);
            }
        }
        for (final AbstractCard c : AbstractDungeon.player.hand.group) {
            if (!card.dontTriggerOnUseCard) {
                c.triggerOnCardPlayed(card);
            }
        }
        for (final AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (!card.dontTriggerOnUseCard) {
                c.triggerOnCardPlayed(card);
            }
        }
        for (final AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (!card.dontTriggerOnUseCard) {
                c.triggerOnCardPlayed(card);
            }
        }
        for (final AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            for (final AbstractPower p2 : m.powers) {
                if (!card.dontTriggerOnUseCard) {
                    p2.onUseCard(card, this);
                }
            }
        }
        if (this.exhaustCard) {
            this.actionType = ActionType.EXHAUST;
        }
        else {
            this.actionType = ActionType.USE;
        }
    }
    
    public DuelistUseCardAction(final AbstractCard targetCard) {
        this(targetCard, null);
    }
    
    @Override
    public void update() {
        if (this.duration == 0.15f) {
            for (final AbstractPower p : AbstractDungeon.player.powers) {
                if (!this.targetCard.dontTriggerOnUseCard) {
                    p.onAfterUseCard(this.targetCard, this);
                }
            }
            for (final AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                for (final AbstractPower p2 : m.powers) {
                    if (!this.targetCard.dontTriggerOnUseCard) {
                        p2.onAfterUseCard(this.targetCard, this);
                    }
                }
            }
            this.targetCard.freeToPlayOnce = false;
            this.targetCard.isInAutoplay = false;
            if (this.targetCard.purgeOnUse) {
                this.addToTop(new ShowCardAndPoofAction(this.targetCard));
                this.isDone = true;
                AbstractDungeon.player.cardInUse = null;
                return;
            }
            if (this.targetCard.type == AbstractCard.CardType.POWER) {
                this.addToTop(new ShowCardAction(this.targetCard));
                if (Settings.FAST_MODE) {
                    this.addToTop(new WaitAction(0.1f));
                }
                else {
                    this.addToTop(new WaitAction(0.7f));
                }
                AbstractDungeon.player.hand.empower(this.targetCard);
                this.isDone = true;
                AbstractDungeon.player.hand.applyPowers();
                AbstractDungeon.player.hand.glowCheck();
                AbstractDungeon.player.cardInUse = null;
                return;
            }
            AbstractDungeon.player.cardInUse = null;
            if (!this.exhaustCard) {
                if (this.reboundCard) {
                    AbstractDungeon.player.hand.moveToDeck(this.targetCard, false);
                }
                else if (this.targetCard.shuffleBackIntoDrawPile) {
                    AbstractDungeon.player.hand.moveToDeck(this.targetCard, true);
                }
                else if (this.targetCard.returnToHand) {
                    AbstractDungeon.player.hand.moveToHand(this.targetCard);
                    AbstractDungeon.player.onCardDrawOrDiscard();
                }
                else if (this.targetCard instanceof DuelistCard)
                {
                	DuelistCard dc = (DuelistCard)this.targetCard;
                	
                	if (dc.sendToGraveyard)
                	{
                		moveToGroup(this.targetCard, true);
                	}
                	else if (dc.sendToMasterDeck)
                	{
                		moveToGroup(this.targetCard, false);
                	}
                	else
                	{
                		 AbstractDungeon.player.hand.moveToDiscardPile(this.targetCard);
                	}
                }
                else {
                    AbstractDungeon.player.hand.moveToDiscardPile(this.targetCard);
                }
            }
            else if (this.exhaustCard) {
                this.targetCard.exhaustOnUseOnce = false;
                if (AbstractDungeon.player.hasRelic("Strange Spoon") && this.targetCard.type != AbstractCard.CardType.POWER) {
                    if (AbstractDungeon.cardRandomRng.randomBoolean()) {
                        AbstractDungeon.player.getRelic("Strange Spoon").flash();
                        AbstractDungeon.player.hand.moveToDiscardPile(this.targetCard);
                    }
                    else {
                        AbstractDungeon.player.hand.moveToExhaustPile(this.targetCard);
                        CardCrawlGame.dungeon.checkForPactAchievement();
                    }
                }
                else {
                    AbstractDungeon.player.hand.moveToExhaustPile(this.targetCard);
                    CardCrawlGame.dungeon.checkForPactAchievement();
                }
            }
            if (this.targetCard.dontTriggerOnUseCard) {
                this.targetCard.dontTriggerOnUseCard = false;
            }
            this.addToBot(new HandCheckAction());
        }
        this.tickDuration();
    }
    
    public void resetCardBeforeMoving(final AbstractCard c, final CardGroup group)
    {
    	 if (AbstractDungeon.player.hoveredCard == c) {
             AbstractDungeon.player.releaseCard();
         }
         AbstractDungeon.actionManager.removeFromQueue(c);
         c.unhover();
         c.untip();
         c.stopGlowing();
         group.removeCard(c);
    }
    
    public void moveToGroup(final AbstractCard c, boolean grave) {
    	resetCardBeforeMoving(c, AbstractDungeon.player.hand);
        c.shrink();
        c.darken(false);
        if (grave) { grave(c);}
        else { deck(c); }
    }

    public void grave(final AbstractCard card) {
        TheDuelist.resummonPile.addToTop(card);        
    }
    
    public void deck(final AbstractCard card) {
        AbstractDungeon.player.masterDeck.addToTop(card);        
    }
}