package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Tags;

public class ToadallyAwesomePowerAction extends AbstractGameAction {

    private final AnyDuelist duelist;

    public ToadallyAwesomePowerAction(AnyDuelist duelist, int amount) {
        this.duelist = duelist;
        this.amount = amount;
    }

    public void update() {
        if (this.duelist.hand().stream().anyMatch(c -> c.hasTag(Tags.TOON))) {
            this.isDone = true;
            return;
        }

        if (this.duelist.player()) {
            DuelistCard.gainTempHP(this.amount);
        } else {
            DuelistCard.gainTempHP(this.duelist.getEnemy(), this.duelist.getEnemy(), this.amount);
        }

        this.isDone = true;
    }
}
