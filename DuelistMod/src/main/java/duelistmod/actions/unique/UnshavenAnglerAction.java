package duelistmod.actions.unique;

import java.util.ArrayList;

import org.apache.logging.log4j.*;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.PlayerTurnEffect;

import duelistmod.DuelistMod;
import duelistmod.helpers.Util;

public class UnshavenAnglerAction extends AbstractGameAction
{
    private boolean shuffleCheck;
    private static final Logger logger;
    public static ArrayList<AbstractCard> drawnCards;
    private boolean clearDrawHistory;
    private AbstractGameAction followUpAction;
    
    public UnshavenAnglerAction(final AbstractCreature source, final int amount, final boolean endTurnDraw) {
        this.shuffleCheck = false;
        this.clearDrawHistory = true;
        this.followUpAction = null;
        if (endTurnDraw) {
            AbstractDungeon.topLevelEffects.add(new PlayerTurnEffect());
        }
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.DRAW;
        if (Settings.FAST_MODE) {
            this.duration = Settings.ACTION_DUR_XFAST;
        }
        else {
            this.duration = Settings.ACTION_DUR_FASTER;
        }
    }
    
    public UnshavenAnglerAction(final AbstractCreature source, final int amount) {
        this(source, amount, false);
    }
    
    public UnshavenAnglerAction(final int amount, final boolean clearDrawHistory) {
        this(amount);
        this.clearDrawHistory = clearDrawHistory;
    }
    
    public UnshavenAnglerAction(final int amount) {
        this(null, amount, false);
    }
    
    public UnshavenAnglerAction(final int amount, final AbstractGameAction action) {
        this(amount, action, true);
    }
    
    public UnshavenAnglerAction(final int amount, final AbstractGameAction action, final boolean clearDrawHistory) {
        this(amount, clearDrawHistory);
        this.followUpAction = action;
    }
    
    @Override
    public void update() {
        if (this.clearDrawHistory) {
            this.clearDrawHistory = false;
            UnshavenAnglerAction.drawnCards.clear();
        }
        if (AbstractDungeon.player.hasPower("No Draw")) {
            AbstractDungeon.player.getPower("No Draw").flash();
            this.endActionWithFollowUp();
            return;
        }
        if (this.amount <= 0) {
            this.endActionWithFollowUp();
            return;
        }
        final int deckSize = AbstractDungeon.player.drawPile.size();
        final int discardSize = AbstractDungeon.player.discardPile.size();
        if (SoulGroup.isActive()) {
            return;
        }
        if (deckSize + discardSize == 0) {
            this.endActionWithFollowUp();
            return;
        }
        if (AbstractDungeon.player.hand.size() == 10) {
            AbstractDungeon.player.createHandIsFullDialog();
            this.endActionWithFollowUp();
            return;
        }
        if (!this.shuffleCheck) {
            if (this.amount + AbstractDungeon.player.hand.size() > 10) {
                final int handSizeAndDraw = 10 - (this.amount + AbstractDungeon.player.hand.size());
                this.amount += handSizeAndDraw;
                AbstractDungeon.player.createHandIsFullDialog();
            }
            if (this.amount > deckSize) {
                final int tmp = this.amount - deckSize;
                this.addToTop(new UnshavenAnglerAction(tmp, this.followUpAction, false));
                this.addToTop(new EmptyDeckShuffleAction());
                if (deckSize != 0) {
                    this.addToTop(new UnshavenAnglerAction(deckSize, false));
                }
                this.amount = 0;
                this.isDone = true;
                return;
            }
            this.shuffleCheck = true;
        }
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.amount != 0 && this.duration < 0.0f) {
            if (Settings.FAST_MODE) {
                this.duration = Settings.ACTION_DUR_XFAST;
            }
            else {
                this.duration = Settings.ACTION_DUR_FASTER;
            }
            --this.amount;
            if (!AbstractDungeon.player.drawPile.isEmpty()) {
            	AbstractCard ref = AbstractDungeon.player.drawPile.getTopCard();
                UnshavenAnglerAction.drawnCards.add(ref);
                AbstractDungeon.player.draw();         
                AbstractDungeon.player.hand.refreshHandLayout();
                if (DuelistMod.overflowedLastTurn) { 
                	this.addToBot(new MakeTempCardInHandAction(ref.makeStatEquivalentCopy(), false)); 
                	Util.log("Unshaven Angler is duplicating "  + ref.cardID);
                }
                else
                {
                	Util.log("Unshaven Angler failed Overflow last turn check");
                }

            }
            else {
                UnshavenAnglerAction.logger.warn("Player attempted to draw from an empty drawpile mid-DrawAction?MASTER DECK: " + AbstractDungeon.player.masterDeck.getCardNames());
                this.endActionWithFollowUp();
            }
            if (this.amount == 0) {
                this.endActionWithFollowUp();
            }
            

        }
    }
    
    private void endActionWithFollowUp() {
        this.isDone = true;
        if (this.followUpAction != null) {
            this.addToTop(this.followUpAction);
        }
    }
    
    static {
        logger = LogManager.getLogger(UnshavenAnglerAction.class.getName());
        UnshavenAnglerAction.drawnCards = new ArrayList<AbstractCard>();
    }
}
