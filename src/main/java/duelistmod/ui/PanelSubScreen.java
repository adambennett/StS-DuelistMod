package duelistmod.ui;

import com.megacrit.cardcrawl.screens.mainMenu.MenuPanelScreen.PanelScreen;

import java.util.Objects;

public final class PanelSubScreen {

    private final PanelScreen subScreen;
    private final String buttonText;

    public PanelSubScreen(PanelScreen subScreen, String buttonText) {
        this.subScreen = subScreen;
        this.buttonText = buttonText;
    }

    public PanelScreen subScreen() {
        return subScreen;
    }

    public String buttonText() {
        return buttonText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PanelSubScreen)) return false;
        PanelSubScreen that = (PanelSubScreen) o;
        return subScreen == that.subScreen;
    }

    @Override
    public int hashCode() {
        return Objects.hash(subScreen);
    }
}
