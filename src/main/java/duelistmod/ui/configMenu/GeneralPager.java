package duelistmod.ui.configMenu;

public class GeneralPager {

    private final GeneralPaginationLogic next;
    private final GeneralPaginationLogic prev;

    public GeneralPager(GeneralPaginationLogic next, GeneralPaginationLogic prev) {
        this.next = next;
        this.prev = prev;
    }

    public void nextPage() {
        if (this.next != null) {
            this.next.run();
        }
    }

    public void prevPage() {
        if (this.prev != null) {
            this.prev.run();
        }
    }
}
