package duelistmod.metrics.tierScoreDTO;

import java.util.Map;

public class ActScore {

    private final Map<Integer, Integer> actScoreMap;

    public ActScore(Map<Integer, Integer> actScoreMap) {
        this.actScoreMap = actScoreMap;
    }

    public Score score(int act) {
        if (this.actScoreMap.containsKey(act)) {
            return new Score(this.actScoreMap.get(act), false);
        } else if (this.actScoreMap.containsKey(-1)) {
            return new Score(this.actScoreMap.get(-1), true);
        }
        return null;
    }
}
