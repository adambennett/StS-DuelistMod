package duelistmod.ui;

import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.controller.CInputHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuPanelButton;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MenuPanelScreen;
import duelistmod.enums.ConfigOpenSource;
import duelistmod.helpers.Util;

import java.util.Stack;

import static duelistmod.enums.MainMenuPanelEnums.*;
import static duelistmod.enums.MainMenuPatchEnums.DUELIST_PANEL_MENU;

public class DuelistMenuPanelScreen extends MenuPanelScreen {

    private final Stack<PanelSubScreen> previousScreens = new Stack<>();
    private static final float PANEL_Y = Settings.HEIGHT / 2.0f;
    private PanelSubScreen screen;
    public boolean isShowing;

    public PanelSubScreen getScreen() {
        return this.screen;
    }

    @SpireOverride
    public void initializePanels() {
        this.panels.clear();
        if (this.getScreen().subScreen().equals(MAIN_HUB)) {
            this.panels.add(new DuelistMainMenuPanelButton(DuelistMainMenuPanelButton.DuelistPanelClickResult.SINGLE_PLAYER, DuelistMainMenuPanelButton.DuelistPanelColor.RED, Settings.WIDTH / 2.0f - 450.0f * Settings.scale, PANEL_Y, new PanelSubScreen(SINGLE_PLAYER, "Return")));
            this.panels.add(new DuelistMainMenuPanelButton(DuelistMainMenuPanelButton.DuelistPanelClickResult.MULTI_PLAYER, DuelistMainMenuPanelButton.DuelistPanelColor.BEIGE, Settings.WIDTH / 2.0f, PANEL_Y, new PanelSubScreen(MULTI_PLAYER, "Return")));
            this.panels.add(new DuelistMainMenuPanelButton(DuelistMainMenuPanelButton.DuelistPanelClickResult.DUELIST_SETTINGS, DuelistMainMenuPanelButton.DuelistPanelColor.BLUE, Settings.WIDTH / 2.0f + 450.0f * Settings.scale, PANEL_Y, () -> Util.openModSettings(ConfigOpenSource.MAIN_MENU)));
        }else if (this.getScreen().subScreen().equals(SINGLE_PLAYER)) {
            this.panels.add(new DuelistMainMenuPanelButton(DuelistMainMenuPanelButton.DuelistPanelClickResult.SLAY_THE_SPIRE, DuelistMainMenuPanelButton.DuelistPanelColor.BLUE, Settings.WIDTH / 2.0f - 250.0f * Settings.scale, PANEL_Y, new PanelSubScreen(SLAY_THE_SPIRE, "Return")));
            this.panels.add(new DuelistMainMenuPanelButton(DuelistMainMenuPanelButton.DuelistPanelClickResult.DUELIST_KINGDOM, DuelistMainMenuPanelButton.DuelistPanelColor.RED, Settings.WIDTH / 2.0f + 200.0f * Settings.scale, PANEL_Y, new PanelSubScreen(DUELIST_KINGDOM, "Return")));
        } else if (this.getScreen().subScreen().equals(MULTI_PLAYER)) {
            this.panels.add(new DuelistMainMenuPanelButton(DuelistMainMenuPanelButton.DuelistPanelClickResult.DUEL, DuelistMainMenuPanelButton.DuelistPanelColor.BLUE, Settings.WIDTH / 2.0f - 250.0f * Settings.scale, PANEL_Y, () -> {}));
            this.panels.add(new DuelistMainMenuPanelButton(DuelistMainMenuPanelButton.DuelistPanelClickResult.GAUNTLET, DuelistMainMenuPanelButton.DuelistPanelColor.RED, Settings.WIDTH / 2.0f + 200.0f * Settings.scale, PANEL_Y, () -> {}));
        } else if (this.getScreen().subScreen().equals(SLAY_THE_SPIRE)) {
            this.panels.add(new MainMenuPanelButton(MainMenuPanelButton.PanelClickResult.PLAY_NORMAL, MainMenuPanelButton.PanelColor.BLUE, Settings.WIDTH / 2.0f - 250.0f * Settings.scale, PANEL_Y));
            this.panels.add(new MainMenuPanelButton(MainMenuPanelButton.PanelClickResult.PLAY_CUSTOM, MainMenuPanelButton.PanelColor.RED, Settings.WIDTH / 2.0f + 200.0f * Settings.scale, PANEL_Y));
        } else if (this.getScreen().subScreen().equals(DUELIST_KINGDOM)) {
            this.panels.add(new DuelistMainMenuPanelButton(DuelistMainMenuPanelButton.DuelistPanelClickResult.STANDARD_DUELIST_KINGDOM, DuelistMainMenuPanelButton.DuelistPanelColor.BLUE, Settings.WIDTH / 2.0f - 250.0f * Settings.scale, PANEL_Y, () -> {}));
            this.panels.add(new DuelistMainMenuPanelButton(DuelistMainMenuPanelButton.DuelistPanelClickResult.RPG, DuelistMainMenuPanelButton.DuelistPanelColor.RED, Settings.WIDTH / 2.0f + 200.0f * Settings.scale, PANEL_Y, () -> {}));
        }
    }

    public void setScreen(PanelSubScreen screen) {
        PanelSubScreen current = this.getScreen();
        if (current != null && !this.previousScreens.contains(current)) {
            this.previousScreens.push(current);
        }
        this.screen = screen;
    }

    public void openWithText(PanelSubScreen screen) {
        String buttonText = screen.buttonText() == null ? CharacterSelectScreen.TEXT[5] : screen.buttonText();
        CardCrawlGame.mainMenuScreen.screen = DUELIST_PANEL_MENU;
        this.setScreen(screen);
        this.initializePanels();
        this.isShowing = true;
        this.button.show(buttonText);
        CardCrawlGame.mainMenuScreen.darken();
    }

    @Override
    public void open(final PanelScreen screenType) {
        this.openWithText(new PanelSubScreen(screenType, null));
    }

    @Override
    public void update() {
        this.button.update();
        if (this.button.hb.clicked || InputHelper.pressedEscape) {
            InputHelper.pressedEscape = false;
            if (this.previousScreens.isEmpty()) {
                CardCrawlGame.mainMenuScreen.screen = MainMenuScreen.CurScreen.MAIN_MENU;
                this.isShowing = false;
                this.button.hide();
                CardCrawlGame.mainMenuScreen.lighten();
            } else {
                this.button.hb.clicked = false;
                this.goBackToScreen(this.previousScreens.pop());
            }
        }
        for (MainMenuPanelButton panel : this.panels) {
            if (panel instanceof DuelistMainMenuPanelButton) {
                if (((DuelistMainMenuPanelButton)panel).duelistUpdate()) break;
            } else {
                panel.update();
            }
        }
    }

    public void updateMenuPanelController() {
        if (!Settings.isControllerMode) {
            return;
        }
        boolean anyHovered = false;
        int index = 0;
        for (MainMenuPanelButton b : this.panels) {
            if (b.hb.hovered) {
                anyHovered = true;
                break;
            }
            ++index;
        }
        if (anyHovered) {
            if (CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed()) {
                if (--index < 0) {
                    index = this.panels.size() - 1;
                }
                if (this.panels.get(index).pColor == MainMenuPanelButton.PanelColor.GRAY) {
                    --index;
                }
                CInputHelper.setCursor(this.panels.get(index).hb);
            }
            else if (CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed()) {
                if (++index > this.panels.size() - 1) {
                    index = 0;
                }
                MainMenuPanelButton button = this.panels.get(index);
                if (button instanceof DuelistMainMenuPanelButton) {
                    if (((DuelistMainMenuPanelButton)button).panelColor == DuelistMainMenuPanelButton.DuelistPanelColor.GRAY) {
                        index = 0;
                    }
                } else if (this.panels.get(index).pColor == MainMenuPanelButton.PanelColor.GRAY) {
                    index = 0;
                }
                CInputHelper.setCursor(this.panels.get(index).hb);
            }
        }
        else {
            index = 0;
            CInputHelper.setCursor(this.panels.get(index).hb);
        }
    }

    private void goBackToScreen(PanelSubScreen screen) {
        this.screen = null;
        this.openWithText(screen);
    }

    @Override
    public void refresh() {
        this.button.hideInstantly();
        this.button.show(CharacterSelectScreen.TEXT[5]);
        if (CardCrawlGame.mainMenuScreen.screen == MainMenuScreen.CurScreen.CHAR_SELECT || CardCrawlGame.mainMenuScreen.screen == MainMenuScreen.CurScreen.CUSTOM) {
            if (CardCrawlGame.mainMenuScreen.screen == MainMenuScreen.CurScreen.CHAR_SELECT) {
                CharacterSelectScreen characterSelectScreen = CardCrawlGame.mainMenuScreen.charSelectScreen;
                characterSelectScreen.confirmButton.isDisabled = true;
                characterSelectScreen.confirmButton.hide();
            }
            this.goBackToScreen(new PanelSubScreen(SLAY_THE_SPIRE, "Return"));
        } else if (this.previousScreens.isEmpty()) {
            CardCrawlGame.mainMenuScreen.screen = MainMenuScreen.CurScreen.MAIN_MENU;
            this.isShowing = false;
            this.button.hide();
            CardCrawlGame.mainMenuScreen.lighten();
        } else {
            this.goBackToScreen(this.previousScreens.pop());
        }
    }

}
