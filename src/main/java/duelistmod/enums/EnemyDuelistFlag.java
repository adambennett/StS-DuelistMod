package duelistmod.enums;

import com.megacrit.cardcrawl.cards.AbstractCard;
import duelistmod.variables.Tags;

@SuppressWarnings("rawtypes")
public enum EnemyDuelistFlag {

    LAST_CARD_PLAYED(AbstractCard.class),
    SECOND_LAST_CARD_PLAYED(AbstractCard.class),
    LAST_GHOSTRICK_PLAYED(AbstractCard.class),
    SECOND_LAST_GHOSTRICK_PLAYED(AbstractCard.class),
    LAST_PLANT_PLAYED(AbstractCard.class),
    SECOND_LAST_PLANT_PLAYED(AbstractCard.class),
    BATTLE_FUSION_MONSTER(AbstractCard.class),
    LAST_TYPE_SUMMONED(AbstractCard.CardTags.class, false, Tags.ALL),
    PLAYED_SPELL_THIS_TURN(Boolean.class, true, false),
    PLAYED_CARD_THIS_COMBAT(Boolean.class, false, false),
    PLAYED_VAMPIRE_THIS_TURN(Boolean.class, true, false);

    private final Class type;
    private final boolean resetAtTurnStart;
    private final Object defaultValue;

    EnemyDuelistFlag(Class type) {
        this(type, false, null);
    }

    EnemyDuelistFlag(Class type, boolean resetAtTurnStart) {
        this(type, false, null);
    }

    EnemyDuelistFlag(Class type, Object defaultValue) {
        this(type, false, defaultValue);
    }

    EnemyDuelistFlag(Class type, boolean resetAtTurnStart, Object defaultValue) {
        this.type = type;
        this.resetAtTurnStart = resetAtTurnStart;
        this.defaultValue = defaultValue;
    }

    public Class type() { return this.type; }

    public boolean isResetAtTurnStart() { return this.resetAtTurnStart; }

    public Object defaultValue() { return this.defaultValue; }
}
