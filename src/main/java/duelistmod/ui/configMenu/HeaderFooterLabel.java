package duelistmod.ui.configMenu;

import basemod.ModLabel;
import duelistmod.DuelistMod;

public class HeaderFooterLabel extends ModLabel {

    private boolean isFooter;

    public HeaderFooterLabel(String text) {
        super(text, DuelistMod.xLabPos + DuelistMod.xSecondCol - 25, DuelistMod.yPos, DuelistMod.settingsPanel, x->{});
        DuelistMod.linebreak();
        DuelistMod.linebreak();
    }

    public HeaderFooterLabel(float yPos) {
        super("Page " + (DuelistMod.paginator.page() + 1) + " / " + DuelistMod.paginator.lastPage(), DuelistMod.xLabPos + DuelistMod.xSecondCol, yPos, DuelistMod.settingsPanel, x->{});
        this.isFooter = true;
    }

    public void updateText() {
        if (this.isFooter) {
            this.text = generateText();
        }
    }

    private int currentPage() {
        return DuelistMod.paginator == null ? 1 : DuelistMod.paginator.page() + 1;
    }

    private int lastPage() {
        return DuelistMod.paginator.lastPage();
    }

    private String generateText() {
        return "Page " + currentPage() + " / " + lastPage();
    }
}
