package duelistmod.enums;

public enum ToonPageFlipUpgradeStates {
    MONSTER__DRAW_PILE__RANDOM_ENEMY,
    MONSTER__BOTH_PILE__RANDOM_ENEMY,
    CARD__BOTH_PILE__RANDOM_ENEMY,
    CARD__BOTH_PILE__CHOOSE_ENEMY,
    CARD__BOTH_PILE__ALL_ENEMY;

    public static ToonPageFlipUpgradeStates next(ToonPageFlipUpgradeStates current) {
        switch (current) {
            case MONSTER__DRAW_PILE__RANDOM_ENEMY:
                return MONSTER__BOTH_PILE__RANDOM_ENEMY;
            case MONSTER__BOTH_PILE__RANDOM_ENEMY:
                return CARD__BOTH_PILE__RANDOM_ENEMY;
            case CARD__BOTH_PILE__RANDOM_ENEMY:
                return CARD__BOTH_PILE__CHOOSE_ENEMY;
            case CARD__BOTH_PILE__CHOOSE_ENEMY:
                return CARD__BOTH_PILE__ALL_ENEMY;
            default:
                return null;
        }
    }
}
