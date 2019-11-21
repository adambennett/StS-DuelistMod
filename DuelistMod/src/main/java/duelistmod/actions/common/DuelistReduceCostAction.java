package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.*;
import java.util.*;

public class DuelistReduceCostAction extends AbstractGameAction
{
    UUID uuid;
    private AbstractCard card;
    
    public DuelistReduceCostAction(final AbstractCard card) {
        this.card = null;
        this.card = card;
    }
    
    public DuelistReduceCostAction(final UUID targetUUID, final int amount) {
        this.card = null;
        this.uuid = targetUUID;
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_XFAST;
    }
    
    @Override
    public void update() {
        if (this.card == null) {
            for (final AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
                c.modifyCostForCombat(-this.amount);
            }
        }
        else {
            this.card.modifyCostForCombat(-this.amount);
        }
        this.isDone = true;
    }
}
