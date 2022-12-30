package duelistmod.dto;

import com.megacrit.cardcrawl.screens.GameOverStat;

import java.util.ArrayList;

public class StatsData {

    private final int points;
    private final int truePoints;
    private final ArrayList<GameOverStat> stats;
    private final GameOverStat stat;

    public StatsData(int points, int truePoints) {
        this.points = points;
        this.truePoints = truePoints;
        this.stat = null;
        this.stats = new ArrayList<>();
    }

    public StatsData(int points, int truePoints, ArrayList<GameOverStat> stats) {
        this.points = points;
        this.truePoints = truePoints;
        this.stats = stats;
        this.stat = null;
    }

    public StatsData(int points, int truePoints, GameOverStat stat) {
        this.points = points;
        this.truePoints = truePoints;
        this.stat = stat;
        this.stats = new ArrayList<>();
    }

    public int points() { return this.points; }

    public int truePoints() { return this.truePoints; }

    public ArrayList<GameOverStat> stats() { return this.stats; }

    public GameOverStat stat() { return this.stat; }
}
