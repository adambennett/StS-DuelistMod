package duelistmod.metrics.tierScoreDTO;

import java.util.Map;

public class PoolScore {

    private final Map<String, CardScore> poolScores;

    public PoolScore(Map<String, CardScore> poolScores) {
        this.poolScores = poolScores;
    }

    public Score score(String pool, String cardId, int act) {
        if (this.poolScores.containsKey(pool)) {
            CardScore score = this.poolScores.get(pool);
            return score != null ? score.score(cardId, act) : null;
        }
        return null;
    }
}
