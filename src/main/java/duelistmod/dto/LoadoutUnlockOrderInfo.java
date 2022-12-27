package duelistmod.dto;

public class LoadoutUnlockOrderInfo {

    private final String deck;
    private final int cost;

    public LoadoutUnlockOrderInfo(String deck, int cost) {
        this.deck = deck;
        this.cost = cost;
    }

    public String deck() { return this.deck; }

    public int cost() { return this.cost; }
}
