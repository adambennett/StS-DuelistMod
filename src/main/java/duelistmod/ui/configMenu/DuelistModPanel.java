package duelistmod.ui.configMenu;

import basemod.BaseMod;
import basemod.IUIElement;
import basemod.ModPanel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import com.megacrit.cardcrawl.screens.options.DropdownMenuListener;
import duelistmod.DuelistMod;
import duelistmod.enums.ConfigOpenSource;
import duelistmod.helpers.Util;

import java.util.ArrayList;
import java.util.Comparator;

public class DuelistModPanel extends ModPanel implements DropdownMenuListener {

    public ConfigOpenSource currentSource = ConfigOpenSource.BASE_MOD;
    private final ArrayList<IUIElement> uiElementsUpdateOuter;
    public boolean combatPanelsHidden;
    public boolean proceedButtonHidden;
    public boolean blackScreenShown;
    public boolean isSomethingSelectedRestRoom;
    public AbstractDungeon.CurrentScreen lastScreen;

    public DuelistModPanel() {
        super();
        this.uiElementsUpdateOuter = new ArrayList<>();
    }

    @Override
    public void changedSelectionTo(DropdownMenu dropdownMenu, int i, String s) {
        if (dropdownMenu instanceof DuelistDropdown) {
            DuelistDropdown menu = ((DuelistDropdown)dropdownMenu);
            if (menu.hasOnChange()) {
                menu.change(s, i);
            } else {
                Util.log("Unimplemented dropdown menu changed to option " + i + " (" + s + ")");
            }
        }
    }

    @Override
    public void addUIElement(IUIElement element) {
        super.addUIElement(element);
        this.uiElementsUpdateOuter.add(element);
        this.uiElementsUpdateOuter.sort(Comparator.comparingInt(IUIElement::updateOrder));
    }

    public void renderFrom(SpriteBatch sb, ConfigOpenSource source) {
        super.render(sb);
        this.currentSource = source;
    }

    private void onClose() {
        if (this.currentSource != ConfigOpenSource.MID_RUN) {
            DuelistMod.paginator.resetToPageOne();
        }
        DuelistMod.openedModSettings = false;
        this.currentSource = ConfigOpenSource.BASE_MOD;
        if (DuelistMod.openDropdown != null) {
            DuelistMod.openDropdown.close();
        }
    }

    @Override
    public void update() {
        for (IUIElement elem : uiElementsUpdateOuter) {
            elem.update();
        }

        if (InputHelper.pressedEscape) {
            InputHelper.pressedEscape = false;
            BaseMod.modSettingsUp = false;
            DuelistMod.openedModSettings = false;
        }

        if (!BaseMod.modSettingsUp && !DuelistMod.openedModSettings) {
            waitingOnEvent = false;
            Gdx.input.setInputProcessor(oldInputProcessor);
            if (this.currentSource == ConfigOpenSource.BASE_MOD || this.currentSource == ConfigOpenSource.MAIN_MENU) {
                CardCrawlGame.mainMenuScreen.lighten();
                CardCrawlGame.mainMenuScreen.screen = MainMenuScreen.CurScreen.MAIN_MENU;
                CardCrawlGame.cancelButton.hideInstantly();
            } else if (this.currentSource == ConfigOpenSource.CHARACTER_SELECT) {
                CardCrawlGame.mainMenuScreen.lighten();
                CardCrawlGame.mainMenuScreen.screen = MainMenuScreen.CurScreen.CHAR_SELECT;
            }
            isUp = false;
            this.onClose();
        }
    }
}
