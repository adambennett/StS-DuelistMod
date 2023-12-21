package duelistmod.enums;

import com.megacrit.cardcrawl.cards.AbstractCard;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;

public enum DuelistCardType {
    ATTACK,
    SKILL,
    POWER,
    STATUS,
    CURSE,
    MONSTER,
    TRAP,
    SPELL,
    TOKEN;

    public static List<DuelistCardType> of(AbstractCard card) {
        ArrayList<DuelistCardType> output = new ArrayList<>();
        switch (card.type) {
            case ATTACK:
                output.add(DuelistCardType.ATTACK);
                break;
            case SKILL:
                output.add(DuelistCardType.SKILL);
                break;
            case POWER:
                output.add(DuelistCardType.POWER);
                break;
            case STATUS:
                output.add(DuelistCardType.STATUS);
                break;
            case CURSE:
                output.add(DuelistCardType.CURSE);
                break;
        }
        if (card.hasTag(Tags.MONSTER)) {
            output.add(DuelistCardType.MONSTER);
        }
        if (card.hasTag(Tags.SPELL)) {
            output.add(DuelistCardType.SPELL);
        }
        if (card.hasTag(Tags.TRAP)) {
            output.add(DuelistCardType.TRAP);
        }
        if (card.hasTag(Tags.TOKEN)) {
            output.add(DuelistCardType.TOKEN);
        }
        return output;
    }
}
