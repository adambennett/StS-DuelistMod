package duelistmod.metrics.tierScoreDTO;

public class Score {

    private final int score;
    private final boolean isOverall;

    public Score(int score, boolean isOverall) {
        this.score = score;
        this.isOverall = isOverall;
    }

    public int score() { return this.score; }

    public boolean isOverall() { return this.isOverall; }
}
