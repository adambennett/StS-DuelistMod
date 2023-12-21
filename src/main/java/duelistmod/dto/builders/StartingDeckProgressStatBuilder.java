package duelistmod.dto.builders;

import duelistmod.dto.CombinedDifficultyStatMap;
import duelistmod.dto.SingleDifficultyStatMap;
import duelistmod.dto.StartingDeckProgressStat;

public class StartingDeckProgressStatBuilder {
    private SingleDifficultyStatMap ascension;
    private SingleDifficultyStatMap challenge;
    private CombinedDifficultyStatMap both;
    private Integer neither = 0;
    private Integer allRuns = 0;

    public StartingDeckProgressStatBuilder setAscension(SingleDifficultyStatMap ascension) {
        this.ascension = ascension;
        return this;
    }

    public StartingDeckProgressStatBuilder setChallenge(SingleDifficultyStatMap challenge) {
        this.challenge = challenge;
        return this;
    }

    public StartingDeckProgressStatBuilder setBoth(CombinedDifficultyStatMap both) {
        this.both = both;
        return this;
    }

    public StartingDeckProgressStatBuilder setNeither(Integer neither) {
        this.neither = neither;
        return this;
    }

    public StartingDeckProgressStatBuilder setAllRuns(Integer allRuns) {
        this.allRuns = allRuns;
        return this;
    }

    public StartingDeckProgressStat createStartingDeckProgressStat() {
        if (ascension == null) {
            ascension = new SingleDifficultyStatMap();
            for (int i = 0; i < 21; i++) {
                ascension.put(i, 0);
            }
        }
        if (challenge == null) {
            challenge = new SingleDifficultyStatMap();
            for (int i = -1; i < 21; i++) {
                challenge.put(i, 0);
            }
        }
        if (both == null) {
            both = new CombinedDifficultyStatMap();
            for (int a = 0; a < 21; a++) {
                for (int c = -1; c < 21; c++) {
                    both.put("a"+a+"c"+c, 0);
                }
            }
        }
        return new StartingDeckProgressStat(ascension, challenge, both, neither, allRuns);
    }
}
