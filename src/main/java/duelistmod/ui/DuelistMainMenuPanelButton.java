package duelistmod.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuPanelButton;
import duelistmod.DuelistMod;

public class DuelistMainMenuPanelButton extends MainMenuPanelButton {

    private static final float START_Y;

    private final DuelistPanelClickResult result;
    public final DuelistPanelColor panelColor;
    private final PanelSubScreen subScreen;
    private final Runnable clickHandler;
    private float uiScale;
    private final Color gColor;
    private final Color cColor;
    private final Color wColor;
    private final Color grColor;
    private Texture portraitImg;
    private Texture panelImg;
    private String header;
    private String description;
    private float yMod;
    private float animTimer;
    private final float animTime;

    public DuelistMainMenuPanelButton(DuelistPanelClickResult setResult, DuelistPanelColor setColor, float x, float y, Runnable clickHandler) {
        this(setResult, setColor, x, y, null, clickHandler);
    }

    public DuelistMainMenuPanelButton(DuelistPanelClickResult setResult, DuelistPanelColor setColor, float x, float y, PanelSubScreen subScreen) {
        this(setResult, setColor, x, y, subScreen, null);
    }

    private DuelistMainMenuPanelButton(DuelistPanelClickResult setResult, DuelistPanelColor setColor, float x, float y, PanelSubScreen subScreen, Runnable clickHandler) {
        super(null, null, x, y);
        this.gColor = Settings.GOLD_COLOR.cpy();
        this.cColor = Settings.CREAM_COLOR.cpy();
        this.wColor = Color.WHITE.cpy();
        this.grColor = Color.GRAY.cpy();
        this.result = setResult;
        this.panelColor = setColor;
        this.setLabel();
        this.animTime = MathUtils.random(0.2f, 0.35f);
        this.animTimer = this.animTime;
        this.subScreen = subScreen;
        this.clickHandler = clickHandler;
        this.uiScale = 1.0f;
    }

    public void setLabel() {
        this.setPanelImg(ImageMaster.MENU_PANEL_BG_BEIGE);
        switch (this.result) {
            case SINGLE_PLAYER:
                this.setHeader("Single Player");
                this.setDescription("Duel against standard enemies locally.");
                this.setPortraitImg(ImageMaster.P_STANDARD);
                this.setPanelImg(ImageMaster.MENU_PANEL_BG_RED);
                break;
            case MULTI_PLAYER:
                this.setHeader("Multiplayer");
                this.setDescription("Duel against other players online.");
                this.setPortraitImg(ImageMaster.P_SETTING_GAME);
                break;
            case SLAY_THE_SPIRE:
                this.setHeader("Slay the Spire");
                this.setDescription("Embark on a quest to Slay the Spire.");
                this.setPortraitImg(ImageMaster.P_STANDARD);
                this.setPanelImg(ImageMaster.MENU_PANEL_BG_BLUE);
                break;
            case DUELIST_KINGDOM:
                this.setHeader("Duelist Kingdom");
                this.setDescription("Compete to become the King of Games.");
                this.setPortraitImg(ImageMaster.loadImage(DuelistMod.makePanelPath("duelist_kingdom.jpg")));
                this.setPanelImg(ImageMaster.MENU_PANEL_BG_RED);
                break;
            case DUEL:
                this.setHeader("Duel");
                this.setDescription("Duel against other players with constructed decks.");
                this.setPortraitImg(ImageMaster.P_INFO_RELIC);
                this.setPanelImg(ImageMaster.MENU_PANEL_BG_BLUE);
                break;
            case GAUNTLET:
                this.setHeader("Gauntlet");
                this.setDescription("Duel against other players in draft format.");
                this.setPortraitImg(ImageMaster.P_STAT_LEADERBOARD);
                this.setPanelImg(ImageMaster.MENU_PANEL_BG_RED);
                break;
            case RPG:
                this.setHeader("RPG");
                this.setDescription("Play a persistent RPG version of DuelistMod.");
                this.setPortraitImg(ImageMaster.loadImage(DuelistMod.makePanelPath("duelist_kingdom_rpg.png")));
                this.setPanelImg(ImageMaster.MENU_PANEL_BG_RED);
                break;
            case DUELIST_SETTINGS:
                this.setHeader("Duelist Settings");
                this.setDescription("Configure DuelistMod settings.");
                this.setPortraitImg(ImageMaster.loadImage(DuelistMod.makePanelPath("puzzle_settings.png")));
                this.setPanelImg(ImageMaster.MENU_PANEL_BG_BLUE);
                break;
            case STANDARD_DUELIST_KINGDOM:
                this.setHeader("Standard");
                this.setDescription("Standard run but all enemies are Duelists.");
                this.setPortraitImg(ImageMaster.loadImage(DuelistMod.makePanelPath("duelist_kingdom.jpg")));
                this.setPanelImg(ImageMaster.MENU_PANEL_BG_BLUE);
                break;
        }
    }

    @Override
    public void update() {}

    public boolean duelistUpdate() {
        boolean openedSubScreen = false;
        if (this.panelColor != DuelistPanelColor.GRAY) {
            this.hb.update();
        }
        if (this.hb.justHovered) {
            CardCrawlGame.sound.playV("UI_HOVER", 0.5f);
        }
        if (this.hb.hovered) {
            this.uiScale = MathHelper.fadeLerpSnap( this.uiScale, 1.025f);
            if (InputHelper.justClickedLeft) {
                this.hb.clickStarted = true;
            }
        }
        else {
            this.uiScale = MathHelper.cardScaleLerpSnap(this.uiScale, 1.0f);
        }
        if (this.hb.hovered && CInputActionSet.select.isJustPressed()) {
            this.hb.clicked = true;
        }
        if (this.hb.clicked) {
            this.hb.clicked = false;
            if (this.subScreen != null) {
                DuelistMod.mainMenuPanelScreen.openWithText(this.subScreen);
                openedSubScreen = true;
            } else {
                CardCrawlGame.sound.play("DECK_OPEN");
                CardCrawlGame.mainMenuScreen.panelScreen.hide();
                if (this.clickHandler != null) {
                    this.clickHandler.run();
                }
            }
        }
        this.animatePanelIn();
        return openedSubScreen;
    }

    private void animatePanelIn() {
        this.animTimer -= Gdx.graphics.getDeltaTime();
        if (this.animTimer < 0.0f) {
            this.animTimer = 0.0f;
        }
        this.yMod = Interpolation.swingIn.apply(0.0f, START_Y, this.animTimer / this.animTime);
        this.wColor.a = 1.0f - this.animTimer / this.animTime;
        this.cColor.a = this.wColor.a;
        this.gColor.a = this.wColor.a;
        this.grColor.a = this.wColor.a;
    }

    @Override
    public void render(final SpriteBatch sb) {
        sb.setColor(this.wColor);
        sb.draw(this.panelImg, this.hb.cX - 256.0f, this.hb.cY + this.yMod - 400.0f, 256.0f, 400.0f, 512.0f, 800.0f, this.uiScale * Settings.scale, this.uiScale * Settings.scale, 0.0f, 0, 0, 512, 800, false, false);
        if (this.hb.hovered) {
            sb.setColor(new Color(1.0f, 1.0f, 1.0f, (this.uiScale - 1.0f) * 16.0f));
            sb.setBlendFunction(770, 1);
            sb.draw(ImageMaster.MENU_PANEL_BG_BLUE, this.hb.cX - 256.0f, this.hb.cY + this.yMod - 400.0f, 256.0f, 400.0f, 512.0f, 800.0f, this.uiScale * Settings.scale, this.uiScale * Settings.scale, 0.0f, 0, 0, 512, 800, false, false);
            sb.setBlendFunction(770, 771);
        }
        if (this.panelColor == DuelistPanelColor.GRAY) {
            sb.setColor(this.grColor);
        }
        else {
            sb.setColor(this.wColor);
        }
        sb.draw(this.portraitImg, this.hb.cX - 158.5f, this.hb.cY + this.yMod - 103.0f + 140.0f * Settings.scale, 158.5f, 103.0f, 317.0f, 206.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 317, 206, false, false);
        if (this.panelColor == DuelistPanelColor.GRAY) {
            sb.setColor(this.wColor);
            sb.draw(ImageMaster.P_LOCK, this.hb.cX - 158.5f, this.hb.cY + this.yMod - 103.0f + 140.0f * Settings.scale, 158.5f, 103.0f, 317.0f, 206.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 317, 206, false, false);
        }
        sb.draw(ImageMaster.MENU_PANEL_FRAME, this.hb.cX - 256.0f, this.hb.cY + this.yMod - 400.0f, 256.0f, 400.0f, 512.0f, 800.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 512, 800, false, false);
        if (FontHelper.getWidth(FontHelper.damageNumberFont, this.header, 0.8f) > 310.0f * Settings.scale) {
            FontHelper.renderFontCenteredHeight(sb, FontHelper.damageNumberFont, this.header, this.hb.cX - 138.0f * Settings.scale, this.hb.cY + this.yMod + 294.0f * Settings.scale, 280.0f * Settings.scale, this.gColor, 0.7f);
        }
        else {
            FontHelper.renderFontCenteredHeight(sb, FontHelper.damageNumberFont, this.header, this.hb.cX - 153.0f * Settings.scale, this.hb.cY + this.yMod + 294.0f * Settings.scale, 310.0f * Settings.scale, this.gColor, 0.8f);
        }
        FontHelper.renderFontCenteredHeight(sb, FontHelper.charDescFont, this.description, this.hb.cX - 153.0f * Settings.scale, this.hb.cY + this.yMod - 130.0f * Settings.scale, 330.0f * Settings.scale, this.cColor);
        this.hb.render(sb);
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPortraitImg(Texture portraitImg) {
        this.portraitImg = portraitImg;
    }

    public void setPanelImg(Texture panelImg) {
        this.panelImg = panelImg;
    }

    static {
        START_Y = -100.0f * Settings.scale;
    }

    public enum DuelistPanelClickResult {
        SINGLE_PLAYER,
        MULTI_PLAYER,
        SLAY_THE_SPIRE,
        DUELIST_KINGDOM,
        DUEL,
        GAUNTLET,
        RPG,
        DUELIST_SETTINGS,
        STANDARD_DUELIST_KINGDOM
    }

    public enum DuelistPanelColor {
        RED,
        BLUE,
        BEIGE,
        GRAY
    }
}
