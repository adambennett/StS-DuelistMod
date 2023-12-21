package duelistmod.dto;

public class StartingDeckProgressStat {

    private SingleDifficultyStatMap ascension;
    private SingleDifficultyStatMap challenge;
    private CombinedDifficultyStatMap both;
    private Integer neither = 0;
    private Integer allRuns = 0;

    public StartingDeckProgressStat() {}

    public StartingDeckProgressStat(SingleDifficultyStatMap ascension, SingleDifficultyStatMap challenge, CombinedDifficultyStatMap both, Integer neither, Integer allRuns) {
        this.ascension = ascension;
        this.challenge = challenge;
        this.both = both;
        this.neither = neither;
        this.allRuns = allRuns;
    }

    public SingleDifficultyStatMap getAscension() {
        return ascension;
    }

    public void setAscension(SingleDifficultyStatMap ascension) {
        this.ascension = ascension;
    }

    public SingleDifficultyStatMap getChallenge() {
        return challenge;
    }

    public void setChallenge(SingleDifficultyStatMap challenge) {
        this.challenge = challenge;
    }

    public CombinedDifficultyStatMap getBoth() {
        return both;
    }

    public void setBoth(CombinedDifficultyStatMap both) {
        this.both = both;
    }

    public Integer getNeither() {
        return neither;
    }

    public void setNeither(Integer neither) {
        this.neither = neither;
    }

    public Integer getAllRuns() {
        return allRuns;
    }

    public void setAllRuns(Integer allRuns) {
        this.allRuns = allRuns;
    }

    private void incAllRuns() {
        this.allRuns++;
    }

    private void incNeither() {
        this.neither++;
    }

    private void incAscension(int level) {
        for (int i = level; i > -1; i--) {
            this.ascension.compute(i, (k, v) -> v == null ? 1 : v + 1);
        }
    }

    private void incChallenge(int level) {
        for (int i = level; i > -2; i--) {
            this.challenge.compute(i, (k, v) -> v == null ? 1 : v + 1);
        }
    }

    public void increment(int ascLevel, int challengeLevel) {
        this.incAllRuns();
        if (ascLevel == 0 && challengeLevel == -1) {
            this.incNeither();
        }
        this.incAscension(ascLevel);
        this.incChallenge(challengeLevel);
        for (int a = ascLevel; a > -1; a--) {
            for (int c = challengeLevel; c > -2; c--) {
                String key = "a"+a+"c"+c;
                this.both.compute(key, (k,v) -> v == null ? 1 : v + 1);
            }
        }
    }
}
