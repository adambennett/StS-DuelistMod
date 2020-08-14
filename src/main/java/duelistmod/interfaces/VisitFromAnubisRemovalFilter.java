package duelistmod.interfaces;

public interface VisitFromAnubisRemovalFilter {
    default boolean canRemove() { return false; }
}
