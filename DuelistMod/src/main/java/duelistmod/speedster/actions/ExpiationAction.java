package duelistmod.speedster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.speedster.SpeedsterUtil.UC;

public class ExpiationAction extends AbstractGameAction {
    private int blockAmt;
    private int healAmt;

    public ExpiationAction(int blockAmt, int healAmt) {
        this.blockAmt = blockAmt;
        this.healAmt = healAmt;
    }

    public void update() {
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if(c.type == AbstractCard.CardType.ATTACK) {
                UC.doDef(blockAmt);
                UC.atb(new HealAction(UC.p(), UC.p(), healAmt));
            }
        }
        isDone = true;
    }
}
