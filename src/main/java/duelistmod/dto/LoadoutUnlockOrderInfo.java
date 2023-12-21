package duelistmod.dto;

public class LoadoutUnlockOrderInfo {

    private final String deck;
    private final int cost;
    private Integer nextCost;

    public LoadoutUnlockOrderInfo(String deck, int cost) {
        this(deck, cost, null);
    }

    public LoadoutUnlockOrderInfo(String deck, int cost, Integer nextCost) {
        this.deck = deck;
        this.cost = cost;
        this.nextCost = nextCost;
    }

    public String deck() { return this.deck; }

    public int cost() { return this.cost; }

    public Integer nextCost() { return this.nextCost; }

    public void setNextCost(int nextCost) { this.nextCost = nextCost; }

    @Override
    public String toString() {
        return ", info={ deck: " + deck + ", cost: " + cost + ", nextCost: " + nextCost + " }";
    }
}
