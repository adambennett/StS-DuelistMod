package duelistmod.ui.configMenu;

import basemod.IUIElement;
import duelistmod.DuelistMod;

import java.util.ArrayList;

public abstract class SpecificConfigMenuPage {

    private String header;

    public SpecificConfigMenuPage(String header) {
        this.header = header;
    }

    public ArrayList<IUIElement> getHeaders() {
        ArrayList<IUIElement> headers = new ArrayList<>();
        headers.add(new HeaderFooterLabel(this.header));
        return headers;
    }

    public abstract ArrayList<IUIElement> getElements();

    public ConfigMenuPage generatePage() {
        DuelistMod.yPos = DuelistMod.startingYPos;
        ArrayList<IUIElement> allPageElements = new ArrayList<>();
        allPageElements.addAll(this.getHeaders());
        allPageElements.addAll(this.getElements());
        return new ConfigMenuPage(500.0f, 550.0f, 100, 100, allPageElements);
    }

    public void lineBreak() {
        DuelistMod.linebreak();
    }

    public String getPageName() {
        return this.header;
    }

}
