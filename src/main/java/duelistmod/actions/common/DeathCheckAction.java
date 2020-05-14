package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.abstracts.DuelistCard;

public class DeathCheckAction extends AbstractGameAction
{
    private static final float DURATION = 0.1f;
    private DuelistCard card;

    public DeathCheckAction(DuelistCard card, AbstractMonster monster)
    {
        this.card = card;
        target = monster;
        actionType = ActionType.SPECIAL;
        duration = DURATION;
    }

    @Override
    public void update()
    {
        if (duration == DURATION && target != null) {
            if (target.isDying || target.currentHealth <= 0) 
            {
               DuelistCard.tributeChecker(AbstractDungeon.player, card.tributes, card, true);
            }
            else
            {
            	card.tribute();
            }
        }
        tickDuration();
    }
}
