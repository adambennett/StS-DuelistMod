package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;

public class FabledDeathCheckAction extends AbstractGameAction
{
    private static final float DURATION = 0.1f;
    private final DuelistCard card;

    public FabledDeathCheckAction(DuelistCard card, AbstractMonster monster)
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
               DuelistCard.runTributeLogicWithoutRemovingSummons(AnyDuelist.from(this.card), card.tributes, card, true);
            }
            else
            {
            	card.tribute();
            }
        }
        tickDuration();
    }
}
