package duelistmod.actions.utility;

import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField;
import com.megacrit.cardcrawl.actions.utility.HandCheckAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAndPoofAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import duelistmod.abstracts.DuelistCard;
import duelistmod.characters.TheDuelist;
import duelistmod.helpers.Util;

public class DuelistUseCardAction extends UseCardAction {
    private final AbstractCard targetCard;

    public DuelistUseCardAction(final AbstractCard card, final AbstractCreature target) {
        super(card, target);
        if (FleetingField.fleeting.get(card)) {
            PurgeField.purge.set(card, true);
            AbstractCard c = StSLib.getMasterDeckEquivalent(card);
            if (c != null) {
                AbstractDungeon.player.masterDeck.removeCard(c);
            }
        }
        this.targetCard = card;
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
            	if (this.target != null)
            	{
	            	if (this.targetCard.misc == 52 && this.target instanceof AbstractMonster)
	            	{
	            		AbstractMonster tar = (AbstractMonster)target;
	            		Util.empowerResummon(this.targetCard, tar);
	            	}
	            	else if (this.targetCard.misc == 52) {
	            		AbstractDungeon.player.hand.empower(this.targetCard);
	            	}
            	}
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
                if (this.targetCard instanceof DuelistCard)
                {
                	DuelistCard dc = (DuelistCard)this.targetCard;
                	if (!dc.retainPowerAfterUse) { this.isDone = true; }
                }
                else { this.isDone = true; }               
                AbstractDungeon.player.hand.applyPowers();
                AbstractDungeon.player.hand.glowCheck();
                AbstractDungeon.player.cardInUse = null;
                return;
            }
            AbstractDungeon.player.cardInUse = null;
            if (PurgeField.purge.get(this.targetCard)) {
                AbstractDungeon.effectList.add(new ExhaustCardEffect(this.targetCard));
                AbstractDungeon.actionManager.addToBottom(new HandCheckAction());
                tickDuration();
                return;
            }
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
            } else {
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
         c.stopGlowing();
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
