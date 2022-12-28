package duelistmod.dto;

import com.megacrit.cardcrawl.screens.GameOverStat;

import java.util.ArrayList;

public class StatsData {

    private final int points;
    private final ArrayList<GameOverStat> stats;

    public StatsData(int points, ArrayList<GameOverStat> stats) {
        this.points = points;
        this.stats = stats;
    }

    public int points() { return this.points; }

    public ArrayList<GameOverStat> stats() { return this.stats; }
}
