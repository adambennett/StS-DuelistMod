package duelistmod.metrics.tierScoreDTO;

public class CardTierScores {

    private final PoolScore scores;

    public CardTierScores(PoolScore scores) {
        this.scores = scores;
    }

    public Score score(String pool, String cardId, int act) {
        return this.scores.score(pool, cardId, act);
    }
}
