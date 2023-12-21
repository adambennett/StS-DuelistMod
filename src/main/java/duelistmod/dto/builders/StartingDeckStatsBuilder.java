package duelistmod.dto.builders;

import duelistmod.dto.StartingDeckProgressStat;
import duelistmod.dto.StartingDeckStats;

public class StartingDeckStatsBuilder {
    private StartingDeckProgressStat victories = new StartingDeckProgressStatBuilder().createStartingDeckProgressStat();
    private StartingDeckProgressStat heartKills = new StartingDeckProgressStatBuilder().createStartingDeckProgressStat();
    private Integer challengeLevel = -1;
    private Integer runs = 0;
    private Integer score = 0;

    public StartingDeckStatsBuilder setVictories(StartingDeckProgressStat victories) {
        this.victories = victories;
        return this;
    }

    public StartingDeckStatsBuilder setHeartKills(StartingDeckProgressStat heartKills) {
        this.heartKills = heartKills;
        return this;
    }

    public StartingDeckStatsBuilder setChallengeLevel(Integer challengeLevel) {
        this.challengeLevel = challengeLevel;
        return this;
    }

    public StartingDeckStatsBuilder setRuns(Integer runs) {
        this.runs = runs;
        return this;
    }

    public StartingDeckStatsBuilder setScore(Integer score) {
        this.score = score;
        return this;
    }

    public StartingDeckStats createStartingDeckStats() {
        return new StartingDeckStats(victories, heartKills, challengeLevel, runs, score);
    }
}
