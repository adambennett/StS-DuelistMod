package duelistmod.abstracts;

import com.megacrit.cardcrawl.core.AbstractCreature;
import duelistmod.dto.AnyDuelist;
import duelistmod.dto.AnyRevengeCard;
import duelistmod.interfaces.RevengeCard;

import java.util.List;

public abstract class RevengeDuelistCard extends DuelistCard implements RevengeCard {

    public RevengeDuelistCard(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET) {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    public abstract void onRevengeTriggered(AnyDuelist duelist);

    public final void triggerRevenge(AnyDuelist duelist) {
        onRevengeTriggered(duelist);
        RevengeCard.super.trigger(AnyRevengeCard.from(this), duelist);
    }

    @Override
    public void postDuelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        super.postDuelistUseCard(owner, targets);
        if (isRevengeActive(this)) {
            triggerRevenge(AnyDuelist.from(this));
        }
    }

}
