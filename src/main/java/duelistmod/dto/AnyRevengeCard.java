package duelistmod.dto;

import com.megacrit.cardcrawl.cards.AbstractCard;
import duelistmod.abstracts.DynamicDamageRevengeCard;
import duelistmod.abstracts.RevengeDuelistCard;
import duelistmod.interfaces.RevengeCard;

public class AnyRevengeCard {

    private final RevengeDuelistCard duelistCard;
    private final DynamicDamageRevengeCard dynamicDamageRevengeCard;

    public AnyRevengeCard(RevengeDuelistCard duelistCard) {
        this.duelistCard = duelistCard;
        this.dynamicDamageRevengeCard = null;
    }

    public AnyRevengeCard(DynamicDamageRevengeCard dynamicDamageRevengeCard) {
        this.duelistCard = null;
        this.dynamicDamageRevengeCard = dynamicDamageRevengeCard;
    }

    public Object getCard() {
        return this.duelistCard != null ? this.duelistCard : this.dynamicDamageRevengeCard;
    }

    public AbstractCard get() {
        Object c = this.getCard();
        if (c instanceof AbstractCard) {
            return (AbstractCard) c;
        }
        return null;
    }

    public RevengeCard getRevengeCard() {
        Object c = this.getCard();
        if (c instanceof RevengeCard) {
            return (RevengeCard) c;
        }
        return null;
    }

    public static AnyRevengeCard from(RevengeDuelistCard card) {
        return new AnyRevengeCard(card);
    }

    public static AnyRevengeCard from(DynamicDamageRevengeCard dynamicDamageRevengeCard) {
        return new AnyRevengeCard(dynamicDamageRevengeCard);
    }
}
