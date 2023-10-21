package duelistmod.dto;

import java.util.List;

public class CardPoolSaveSlotData {

    private List<String> cardPool;
    private int slot;

    public CardPoolSaveSlotData() {}

    public CardPoolSaveSlotData(List<String> cardPool, int slot) {
        this.cardPool = cardPool;
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public List<String> getCardPool() {
        return cardPool;
    }

    public void setCardPool(List<String> cardPool) {
        this.cardPool = cardPool;
    }
}
