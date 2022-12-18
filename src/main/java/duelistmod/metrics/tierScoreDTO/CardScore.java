package duelistmod.metrics.tierScoreDTO;

import java.util.Map;

public class CardScore {

    private final Map<String, ActScore> cardScores;

    public CardScore(Map<String, ActScore> cardScores) {
        this.cardScores = cardScores;
    }

    public Score score(String cardId, int act) {
        if (this.cardScores.containsKey(cardId)) {
            ActScore score = this.cardScores.get(cardId);
            return score != null ? score.score(act) : null;
        }
        return null;
    }

}
