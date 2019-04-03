package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;

import basemod.helpers.CardTags;
import duelistmod.Tags;

public class DiscardFromDeckAction extends AbstractGameAction {

    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString("strawberrySpire:DataDumpAction").TEXT;
    private AbstractPlayer player;
    private int numberOfCards;

    public DiscardFromDeckAction(int magicNumber, CardTags tagToDiscardFrom) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FASTER;
        this.player = AbstractDungeon.player;
        this.numberOfCards = magicNumber;
    }

    public void update() {
        if (AbstractDungeon.getCurrRoom().isBattleEnding()) {
            this.isDone = true;
            return;
        }
        if (this.duration == Settings.ACTION_DUR_FASTER) {
            if (this.player.drawPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            else if (this.player.drawPile.size() <= this.numberOfCards) {
                CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                for (AbstractCard c: this.player.drawPile.group) {
                    if (c.hasTag(Tags.MONSTER)) { temp.addToTop(c); }
                }
                for (AbstractCard c : temp.group) {
                    AbstractDungeon.player.drawPile.moveToDiscardPile(c);
                }
            }
            else {
                CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                for (AbstractCard c : this.player.drawPile.group) {
                	if (c.hasTag(Tags.MONSTER)) { temp.addToTop(c); }
                }
                temp.sortAlphabetically(true);
                temp.sortByRarityPlusStatusCardType(false);
                if (numberOfCards == 1) {
                    AbstractDungeon.gridSelectScreen.open(temp, numberOfCards, false, TEXT[0]);
                }
                else {
                    AbstractDungeon.gridSelectScreen.open(temp, numberOfCards, false, TEXT[1] + numberOfCards + TEXT[2]);
                }
                tickDuration();
                return;
            }
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                this.player.drawPile.moveToDiscardPile(c);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();
        }
        tickDuration();
    }
}
