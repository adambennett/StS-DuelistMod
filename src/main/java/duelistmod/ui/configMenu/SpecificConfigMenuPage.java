package duelistmod.ui.configMenu;

import basemod.IUIElement;
import duelistmod.DuelistMod;

import java.util.ArrayList;

public abstract class SpecificConfigMenuPage {

    private final String header;
    private final String pageName;

    public SpecificConfigMenuPage(String header, String pageName) {
        this.header = header;
        this.pageName = pageName;
    }

    public ArrayList<IUIElement> getHeaders() {
        ArrayList<IUIElement> headers = new ArrayList<>();
        headers.add(new HeaderLabel(this.header));
        return headers;
    }

    public abstract ArrayList<IUIElement> getElements();

    public ConfigMenuPage generatePage() {
        DuelistMod.yPos = DuelistMod.startingYPos;
        ArrayList<IUIElement> allPageElements = new ArrayList<>();
        allPageElements.addAll(this.getHeaders());
        allPageElements.addAll(this.getElements());
        return new ConfigMenuPage(this.header, 500.0f, 550.0f, 100, 100, allPageElements);
    }

    public static void LINEBREAK() {
        DuelistMod.linebreak();
    }

    public static void LINEBREAK(int extra) {
        DuelistMod.linebreak(extra);
    }

    public static void RESET_Y() {
        DuelistMod.yPos = DuelistMod.startingYPos;
    }

    public String getHeader() {
        return this.header;
    }

    public String getPageName() { return this.pageName; }

}
