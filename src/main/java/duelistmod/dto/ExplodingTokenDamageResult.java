package duelistmod.dto;

public class ExplodingTokenDamageResult {

    private final int low;
    private final int high;
    private final int damage;

    public ExplodingTokenDamageResult() {
        this(0, 0, 0);
    }

    public ExplodingTokenDamageResult(int low, int high, int damage) {
        this.low = low;
        this.high = high;
        this.damage = damage;
    }

    public int low() { return this.low; }

    public int high() { return this.high; }

    public int damage() { return this.damage; }
}
