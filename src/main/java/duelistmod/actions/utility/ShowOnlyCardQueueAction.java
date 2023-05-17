package duelistmod.actions.utility;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import duelistmod.dto.AnyDuelist;
import java.util.Collections;
import java.util.List;

public class ShowOnlyCardQueueAction extends AbstractGameAction {

    private final List<AbstractCard> queue;
    private final AnyDuelist duelist;

    public ShowOnlyCardQueueAction(AbstractCard c, AnyDuelist duelist) {
        this(Collections.singletonList(c), duelist);
    }

    public ShowOnlyCardQueueAction(List<AbstractCard> queue, AnyDuelist duelist) {
        this.queue = queue;
        this.duelist = duelist;
    }

    @Override
    public void update() {
        if (this.queue == null) {
            this.isDone = true;
            return;
        }
        for (AbstractCard c : this.queue) {
            if (this.duelist.limboGroup().contains(c)) {
                AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
                this.duelist.limbo().remove(c);
            }
        }
        this.isDone = true;
    }
}
