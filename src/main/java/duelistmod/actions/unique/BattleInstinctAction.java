package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Tags;

public class BattleInstinctAction extends AbstractGameAction
{
    private final AnyDuelist duelist;

    public BattleInstinctAction(AnyDuelist duelist) {
        this.duration = 0.0f;
        this.actionType = ActionType.WAIT;
        this.duelist = duelist;
    }

    @Override
    public void update() {
        for (final AbstractCard c : DrawCardAction.drawnCards) {
            if (c.hasTag(Tags.BEAST)) {
                this.duelist.gainEnergy(2);
                break;
            }
        }
        this.isDone = true;
    }
}
