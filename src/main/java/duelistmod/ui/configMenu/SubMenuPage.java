package duelistmod.ui.configMenu;

public interface SubMenuPage {
    int getCurrentSubPageIndex();
    boolean hasSubMenuPageSettings();
    String getSubMenuPageName();

    void resetSubPageToDefault();
    void setPage(int index);

    default void refreshAfterReset() {
        this.setPage(this.getCurrentSubPageIndex());
    }
}
