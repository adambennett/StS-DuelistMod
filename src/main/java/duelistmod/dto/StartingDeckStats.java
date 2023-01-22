package duelistmod.dto;

public class StartingDeckStats {

    private StartingDeckProgressStat victories;
    private StartingDeckProgressStat heartKills;
    private Integer challengeLevel = -1;
    private Integer runs = 0;
    private Integer score = 0;

    public StartingDeckStats() {}

    public StartingDeckStats(StartingDeckProgressStat victories, StartingDeckProgressStat heartKills, Integer challengeLevel, Integer runs, Integer score) {
        this.victories = victories;
        this.heartKills = heartKills;
        this.challengeLevel = challengeLevel;
        this.runs = runs;
        this.score = score;
    }

    public StartingDeckProgressStat getVictories() {
        return victories;
    }

    public void setVictories(StartingDeckProgressStat victories) {
        this.victories = victories;
    }

    public StartingDeckProgressStat getHeartKills() {
        return heartKills;
    }

    public void setHeartKills(StartingDeckProgressStat heartKills) {
        this.heartKills = heartKills;
    }

    public Integer getChallengeLevel() {
        return challengeLevel;
    }

    public void setChallengeLevel(Integer challengeLevel) {
        this.challengeLevel = challengeLevel;
    }

    public Integer getRuns() {
        return runs;
    }

    public void setRuns(Integer runs) {
        this.runs = runs;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
