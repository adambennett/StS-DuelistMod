package duelistmod.actions.common;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Tags;

public class FishAction extends AbstractGameAction
{
    private float startingDuration;
    
    public FishAction(final int numCards) {
        this.amount = numCards;        
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }
    
    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
            return;
        }
        if (this.duration == this.startingDuration) {
            /*for (final AbstractPower p : AbstractDungeon.player.powers) {
                p.onScry();
            }*/
            if (AbstractDungeon.player.drawPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            final CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            if (this.amount != -1) {
                for (int i = 0; i < Math.min(this.amount, AbstractDungeon.player.drawPile.size()); ++i) {
                    tmpGroup.addToTop(AbstractDungeon.player.drawPile.group.get(AbstractDungeon.player.drawPile.size() - i - 1));
                }
            }
            else {
                for (final AbstractCard c : AbstractDungeon.player.drawPile.group) {
                    tmpGroup.addToBottom(c);
                }
            }
            AbstractDungeon.gridSelectScreen.open(tmpGroup, this.amount, true, "Choose any number of cards to discard. Discard Aqua cards to gain Temp HP.");
        }
        else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) 
        {       	
           	ArrayList<AbstractCard> fished = new ArrayList<>();
           	ArrayList<AbstractCard> aquaFished = new ArrayList<>();
            for (final AbstractCard c2 : AbstractDungeon.gridSelectScreen.selectedCards) 
            {
                AbstractDungeon.player.drawPile.moveToDiscardPile(c2);
                fished.add(c2.makeStatEquivalentCopy());
                if (c2.hasTag(Tags.AQUA)) 
                { 
                	DuelistCard.gainTempHP(2); 
                	aquaFished.add(c2.makeStatEquivalentCopy());
                }
            }
            DuelistCard.handleOnFishForAllAbstracts(fished, aquaFished);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
        /*for (final AbstractCard c2 : AbstractDungeon.player.discardPile.group) {
            c2.triggerOnScry();
        }*/
        this.tickDuration();
    }
}
