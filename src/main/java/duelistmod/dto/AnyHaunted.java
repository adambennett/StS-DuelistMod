package duelistmod.dto;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.AbstractPower;
import duelistmod.powers.incomplete.HauntedDebuff;
import duelistmod.powers.incomplete.HauntedPower;
import duelistmod.variables.Tags;

import java.util.List;

import static com.megacrit.cardcrawl.cards.AbstractCard.*;

public class AnyHaunted {

    private final HauntedPower buff;
    private final HauntedDebuff debuff;

    public AnyHaunted(HauntedPower buff, HauntedDebuff debuff) {
        this.buff = buff;
        this.debuff = debuff;
    }

    public static AnyHaunted from(AbstractPower power) {
        HauntedDebuff debuff = power instanceof HauntedDebuff ? (HauntedDebuff)power : null;
        HauntedPower buff = power instanceof HauntedPower ? (HauntedPower)power : null;
        return new AnyHaunted(buff, debuff);
    }

    public static AnyHaunted from(List<AbstractPower> powers) {
        HauntedDebuff debuff = null;
        HauntedPower buff = null;
        for (AbstractPower pow : powers) {
            if (pow instanceof HauntedDebuff) {
                debuff = ((HauntedDebuff)pow);
            } else if (pow instanceof HauntedPower) {
                buff = ((HauntedPower)pow);
            }
        }
        return new AnyHaunted(buff, debuff);
    }

    public void triggerHaunt(AbstractCard card) {
        if (this.buff != null && ((!this.buff.hauntedCardBaseType.equals(CardType.CURSE) && card.type.equals(this.buff.hauntedCardBaseType)) || (!this.buff.hauntedCardType.equals(Tags.DRAGON) && card.hasTag(this.buff.hauntedCardType)))) {
            this.buff.triggerHaunt(card);
        }
        if (this.debuff != null && ((!this.debuff.hauntedCardBaseType.equals(CardType.CURSE) && card.type.equals(this.debuff.hauntedCardBaseType) || (!this.debuff.hauntedCardType.equals(Tags.DRAGON) && card.hasTag(this.debuff.hauntedCardType))))) {
            this.debuff.triggerHaunt(card);
        }
    }

    public HauntedPower getBuff() {
        return buff;
    }

    public HauntedDebuff getDebuff() {
        return debuff;
    }
}
