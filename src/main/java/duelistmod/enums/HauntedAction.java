package duelistmod.enums;

public enum HauntedAction {
    DISCARD_SMALL("Discard #b1 card"),
    DICARD_BIG("Discard #b2 cards"),
    DISCARD_SMALL_RANDOM("Discard #b1 card randomly"),
    LOSE_RANDOM_GOLD("Lose a random amount of gold (1-50)"),
    LOSE_HP_SMALL("Lose #b1 HP"),
    LOSE_HP_BIG("Lose #b2 HP"),
    LOSE_HP_CARD("Discard #b1 card"),
    TRIBUTE_ALL("Discard #b1 card"),
    TRIBUTE_SMALL("Discard #b1 card"),
    TRIBUTE_BIG("Discard #b1 card"),
    TRIBUTE_INCREASE_SMALL("Discard #b1 card"),
    TRIBUTE_INCREASE_MEDIUM("Discard #b1 card"),
    TRIBUTE_INCREASE_BIG("Discard #b1 card"),
    SUMMONS_REDUCE_SMALL("Discard #b1 card"),
    SUMMONS_REDUCE_BIG("Discard #b1 card"),
    LOSE_ENERGY_SMALL("Discard #b1 card"),
    MAX_SUMMONS_REDUCE_SMALL("Discard #b1 card"),
    GAIN_DEBUFF_SMALL("Discard #b1 card"),
    GAIN_DEBUFF_BIG("Discard #b1 card"),
    APPLY_STRENGTH_SMALL("Discard #b1 card"),
    APPLY_THORNS_SMALL("Discard #b1 card"),
    APPLY_THORNS_MEDIUM("Discard #b1 card"),
    APPLY_THORNS_BIG("Discard #b1 card"),
    APPLY_THORNS_GIANT("Discard #b1 card"),
    APPLY_THORNS_ALL("Discard #b1 card"),
    APPLY_STRENGTH_ALL_SMALL("Discard #b1 card"),
    APPLY_STRENGTH_ALL_BIG("Discard #b1 card"),
    INCREASE_HAUNTED_SMALL("Discard #b1 card"),
    INCREASE_HAUNTED_BIG("Discard #b1 card"),
    RANDOM_CURSE_DRAW_PILE("Discard #b1 card"),
    BLOCK_ENEMIES_SMALL("Discard #b1 card"),
    BLOCK_ENEMIES_BIG("Discard #b1 card"),
    STATUS_WOUND_DISCARD_SMALL("Discard #b1 card"),
    STATUS_SWARM_DISCARD_SMALL("Discard #b1 card"),
    STATUS_COLD_BLOODED_DISCARD_SMALL("Discard #b1 card"),
    STATUS_DAZED_DISCARD_SMALL("Discard #b1 card"),
    STATUS_SLIMED_DISCARD_SMALL("Discard #b1 card"),
    STATUS_BURN_DISCARD_SMALL("Discard #b1 card"),
    STATUS_WOUND_DRAW_SMALL("Discard #b1 card"),
    STATUS_SWARM_DRAW_SMALL("Discard #b1 card"),
    STATUS_COLD_BLOODED_DRAW_SMALL("Discard #b1 card"),
    STATUS_DAZED_DRAW_SMALL("Discard #b1 card"),
    STATUS_SLIMED_DRAW_SMALL("Discard #b1 card"),
    STATUS_BURN_DRAW_SMALL("Discard #b1 card"),
    STATUS_WOUND_DISCARD_BIG("Discard #b1 card"),
    STATUS_SWARM_DISCARD_BIG("Discard #b1 card"),
    STATUS_COLD_BLOODED_DISCARD_BIG("Discard #b1 card"),
    STATUS_DAZED_DISCARD_BIG("Discard #b1 card"),
    STATUS_SLIMED_DISCARD_BIG("Discard #b1 card"),
    STATUS_BURN_DISCARD_BIG("Discard #b1 card"),
    STATUS_WOUND_DRAW_BIG("Discard #b1 card"),
    STATUS_SWARM_DRAW_BIG("Discard #b1 card"),
    STATUS_COLD_BLOODED_DRAW_BIG("Discard #b1 card"),
    STATUS_DAZED_DRAW_BIG("Discard #b1 card"),
    STATUS_SLIMED_DRAW_BIG("Discard #b1 card"),
    STATUS_BURN_DRAW_BIG("Discard #b1 card"),
    MAX_HP_LOSS_SMALL("Discard #b1 card"),
    SUMMON_PLAGUE_SMALL("Discard #b1 card"),
    SUMMON_PLAGUE_MEDIUM("Discard #b1 card"),
    SUMMON_PLAGUE_BIG("Discard #b1 card"),
    RESTRICT_RESUMMONS("Discard #b1 card"),
    CAN_USE_NO_SPELLS("Discard #b1 card"),
    CAN_USE_NO_TRAPS("Discard #b1 card"),
    CAN_USE_MONSTER_RESTRICT("Discard #b1 card");

    private final String actionText;

    HauntedAction(String actionText) {
        this.actionText = actionText;
    }

    @Override
    public String toString() {
        return actionText;
    }
}
