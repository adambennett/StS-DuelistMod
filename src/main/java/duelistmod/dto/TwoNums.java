package duelistmod.dto;

public class TwoNums {
    private final int low;
    private final int high;

    public TwoNums(int low, int high) {
        this.low = low;
        this.high = high;
    }

    public int low() { return this.low; }
    public int high() { return this.high; }
}
