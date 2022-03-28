package duelistmod.ui;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.DrawPileViewScreen;
import com.megacrit.cardcrawl.screens.mainMenu.*;

public class DuelistCardViewScreen extends DrawPileViewScreen implements ScrollBarListener
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private CardGroup drawPileCopy;
    public boolean isHovered;
    private static final int CARDS_PER_LINE = 5;
    private static final float SCROLL_BAR_THRESHOLD;
    private boolean grabbedScreen;
    private static float drawStartX;
    private static float drawStartY;
    private static float padX;
    private static float padY;
    private float scrollLowerBound;
    private float scrollUpperBound;
    private float grabStartY;
    private float currentDiffY;
    private static final String BODY_INFO;
    private AbstractCard hoveredCard;
    private int prevDeckSize;
    private ScrollBar scrollBar;
    private AbstractCard controllerCard;
    private CardGroup tmp;
    private String header;
    
    public DuelistCardViewScreen() {
        this.drawPileCopy = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        this.isHovered = false;
        this.grabbedScreen = false;
        this.scrollLowerBound = -Settings.DEFAULT_SCROLL_LIMIT;
        this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
        this.grabStartY = this.scrollLowerBound;
        this.currentDiffY = this.scrollLowerBound;
        this.hoveredCard = null;
        this.prevDeckSize = 0;
        this.controllerCard = null;
        DuelistCardViewScreen.drawStartX = (float)Settings.WIDTH;
        DuelistCardViewScreen.drawStartX -= 5.0f * AbstractCard.IMG_WIDTH * 0.75f;
        DuelistCardViewScreen.drawStartX -= 4.0f * Settings.CARD_VIEW_PAD_X;
        DuelistCardViewScreen.drawStartX /= 2.0f;
        DuelistCardViewScreen.drawStartX += AbstractCard.IMG_WIDTH * 0.75f / 2.0f;
        DuelistCardViewScreen.padX = AbstractCard.IMG_WIDTH * 0.75f + Settings.CARD_VIEW_PAD_X;
        DuelistCardViewScreen.padY = AbstractCard.IMG_HEIGHT * 0.75f + Settings.CARD_VIEW_PAD_Y;
        (this.scrollBar = new ScrollBar(this)).move(0.0f, -30.0f * Settings.scale);
    }

    public void update() {
        boolean isDraggingScrollBar = false;
        if (this.shouldShowScrollBar()) {
            isDraggingScrollBar = this.scrollBar.update();
        }
        if (!isDraggingScrollBar) {
            this.updateScrolling();
        }
        this.updateControllerInput();
        if (Settings.isControllerMode && this.controllerCard != null && !CardCrawlGame.isPopupOpen && !CInputHelper.isTopPanelActive()) {
            if (Gdx.input.getY() > Settings.HEIGHT * 0.7f) {
                this.currentDiffY += Settings.SCROLL_SPEED;
            }
            else if (Gdx.input.getY() < Settings.HEIGHT * 0.3f) {
                this.currentDiffY -= Settings.SCROLL_SPEED;
            }
        }
        this.updatePositions();
        if (Settings.isControllerMode && this.controllerCard != null && !CInputHelper.isTopPanelActive()) {
            CInputHelper.setCursor(this.controllerCard.hb);
        }
    }
    
    private void updateControllerInput() {
        if (!Settings.isControllerMode || CInputHelper.isTopPanelActive()) {
            return;
        }
        boolean anyHovered = false;
        int index = 0;
        for (final AbstractCard c : this.drawPileCopy.group) {
            if (c.hb.hovered) {
                anyHovered = true;
                break;
            }
            ++index;
        }
        if (!anyHovered) {
            Gdx.input.setCursorPosition((int)this.drawPileCopy.group.get(0).hb.cX, Settings.HEIGHT - (int)this.drawPileCopy.group.get(0).hb.cY);
            this.controllerCard = this.drawPileCopy.group.get(0);
        }
        else if ((CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed()) && this.drawPileCopy.size() > 5) {
            index -= 5;
            if (index < 0) {
                final int wrap = this.drawPileCopy.size() / 5;
                index += wrap * 5;
                if (index + 5 < this.drawPileCopy.size()) {
                    index += 5;
                }
            }
            Gdx.input.setCursorPosition((int)this.drawPileCopy.group.get(index).hb.cX, Settings.HEIGHT - (int)this.drawPileCopy.group.get(index).hb.cY);
            this.controllerCard = this.drawPileCopy.group.get(index);
        }
        else if ((CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed()) && this.drawPileCopy.size() > 5) {
            if (index < this.drawPileCopy.size() - 5) {
                index += 5;
            }
            else {
                index %= 5;
            }
            Gdx.input.setCursorPosition((int)this.drawPileCopy.group.get(index).hb.cX, Settings.HEIGHT - (int)this.drawPileCopy.group.get(index).hb.cY);
            this.controllerCard = this.drawPileCopy.group.get(index);
        }
        else if (CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed()) {
            if (index % 5 > 0) {
                --index;
            }
            else {
                index += 4;
                if (index > this.drawPileCopy.size() - 1) {
                    index = this.drawPileCopy.size() - 1;
                }
            }
            Gdx.input.setCursorPosition((int)this.drawPileCopy.group.get(index).hb.cX, Settings.HEIGHT - (int)this.drawPileCopy.group.get(index).hb.cY);
            this.controllerCard = this.drawPileCopy.group.get(index);
        }
        else if (CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed()) {
            if (index % 5 < 4) {
                if (++index > this.drawPileCopy.size() - 1) {
                    index -= this.drawPileCopy.size() % 5;
                }
            }
            else {
                index -= 4;
                if (index < 0) {
                    index = 0;
                }
            }
            Gdx.input.setCursorPosition((int)this.drawPileCopy.group.get(index).hb.cX, Settings.HEIGHT - (int)this.drawPileCopy.group.get(index).hb.cY);
            this.controllerCard = this.drawPileCopy.group.get(index);
        }
    }
    
    private void updateScrolling() {
        final int y = InputHelper.mY;
        if (!this.grabbedScreen) {
            if (InputHelper.scrolledDown) {
                this.currentDiffY += Settings.SCROLL_SPEED;
            }
            else if (InputHelper.scrolledUp) {
                this.currentDiffY -= Settings.SCROLL_SPEED;
            }
            if (InputHelper.justClickedLeft) {
                this.grabbedScreen = true;
                this.grabStartY = y - this.currentDiffY;
            }
        }
        else if (InputHelper.isMouseDown) {
            this.currentDiffY = y - this.grabStartY;
        }
        else {
            this.grabbedScreen = false;
        }
        if (this.prevDeckSize != this.drawPileCopy.size()) {
            this.calculateScrollBounds();
        }
        this.resetScrolling();
        this.updateBarPosition();
    }
    
    private void calculateScrollBounds() {
        if (this.drawPileCopy.size() > 10) {
            int scrollTmp = this.drawPileCopy.size() / 5 - 2;
            if (this.drawPileCopy.size() % 5 != 0) {
                ++scrollTmp;
            }
            this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT + scrollTmp * DuelistCardViewScreen.padY;
        }
        else {
            this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
        }
        this.prevDeckSize = this.drawPileCopy.size();
    }
    
    private void resetScrolling() {
        if (this.currentDiffY < this.scrollLowerBound) {
            this.currentDiffY = MathHelper.scrollSnapLerpSpeed(this.currentDiffY, this.scrollLowerBound);
        }
        else if (this.currentDiffY > this.scrollUpperBound) {
            this.currentDiffY = MathHelper.scrollSnapLerpSpeed(this.currentDiffY, this.scrollUpperBound);
        }
    }
    
    private void updatePositions() {
        this.hoveredCard = null;
        int lineNum = 0;
        final ArrayList<AbstractCard> cards = this.drawPileCopy.group;
        for (int i = 0; i < cards.size(); ++i) {
            final int mod = i % 5;
            if (mod == 0 && i != 0) {
                ++lineNum;
            }
            cards.get(i).target_x = DuelistCardViewScreen.drawStartX + mod * DuelistCardViewScreen.padX;
            cards.get(i).target_y = DuelistCardViewScreen.drawStartY + this.currentDiffY - lineNum * DuelistCardViewScreen.padY;
            cards.get(i).update();
            if (AbstractDungeon.topPanel.potionUi.isHidden) {
                cards.get(i).updateHoverLogic();
                if (cards.get(i).hb.hovered) {
                    this.hoveredCard = cards.get(i);
                }
            }
        }
    }
    
    public void reopen() {
        if (Settings.isControllerMode) {
            Gdx.input.setCursorPosition(10, Settings.HEIGHT / 2);
            this.controllerCard = null;
        }
        AbstractDungeon.overlayMenu.cancelButton.show(DuelistCardViewScreen.TEXT[2]);
    }

    public void open(CardGroup group, String header) {
        this.tmp = group;
        this.header = header;
        this.open();
    }
    
    public void open() {
        if (Settings.isControllerMode) {
            Gdx.input.setCursorPosition(10, Settings.HEIGHT / 2);
            this.controllerCard = null;
        }
        CardCrawlGame.sound.play("DECK_OPEN");
        AbstractDungeon.overlayMenu.showBlackScreen();
        AbstractDungeon.overlayMenu.cancelButton.show(DuelistCardViewScreen.TEXT[2]);
        this.currentDiffY = this.scrollLowerBound;
        this.grabStartY = this.scrollLowerBound;
        this.grabbedScreen = false;
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = Enum.DUELIST_CARD_VIEW_SCREEN;
        this.drawPileCopy.clear();
        for (final AbstractCard c : this.tmp.group) {
            c.setAngle(0.0f, true);
            c.targetDrawScale = 0.75f;
            c.targetDrawScale = 0.75f;
            c.drawScale = 0.75f;
            c.lighten(true);
            this.drawPileCopy.addToBottom(c);
        }
        this.hideCards();
        if (this.drawPileCopy.group.size() <= 5) {
            DuelistCardViewScreen.drawStartY = Settings.HEIGHT * 0.5f;
        }
        else {
            DuelistCardViewScreen.drawStartY = Settings.HEIGHT * 0.66f;
        }
        this.calculateScrollBounds();
    }
    
    private void hideCards() {
        int lineNum = 0;
        final ArrayList<AbstractCard> cards = this.drawPileCopy.group;
        for (int i = 0; i < cards.size(); ++i) {
            final int mod = i % 5;
            if (mod == 0 && i != 0) {
                ++lineNum;
            }
            cards.get(i).current_x = DuelistCardViewScreen.drawStartX + mod * DuelistCardViewScreen.padX;
            cards.get(i).current_y = DuelistCardViewScreen.drawStartY + this.currentDiffY - lineNum * DuelistCardViewScreen.padY - MathUtils.random(100.0f * Settings.scale, 200.0f * Settings.scale);
            cards.get(i).targetDrawScale = 0.75f;
            cards.get(i).drawScale = 0.75f;
        }
    }
    
    public void render(final SpriteBatch sb) {
        if (this.shouldShowScrollBar()) {
            this.scrollBar.render(sb);
        }
        if (this.hoveredCard == null) {
            this.drawPileCopy.render(sb);
        }
        else {
            this.drawPileCopy.renderExceptOneCard(sb, this.hoveredCard);
            this.hoveredCard.renderHoverShadow(sb);
            this.hoveredCard.render(sb);
            this.hoveredCard.renderCardTip(sb);
        }
        sb.setColor(Color.WHITE);
       // sb.draw(ImageMaster.DRAW_PILE_BANNER, 0.0f, 0.0f, 630.0f * Settings.scale, 128.0f * Settings.scale);
       /* FontHelper.renderFontLeftTopAligned(sb, FontHelper.panelNameFont, "Summons", 166.0f * Settings.scale, 82.0f * Settings.scale, Settings.LIGHT_YELLOW_COLOR);
        if (!AbstractDungeon.player.hasRelic("Frozen Eye")) {
            FontHelper.renderDeckViewTip(sb, DuelistCardViewScreen.BODY_INFO, 48.0f * Settings.scale, Settings.GOLD_COLOR);
        }*/
        FontHelper.renderDeckViewTip(sb, this.header, 96.0f * Settings.scale, Settings.CREAM_COLOR);
        AbstractDungeon.overlayMenu.combatDeckPanel.render(sb);
    }
    
    @Override
    public void scrolledUsingBar(final float newPercent) {
        this.currentDiffY = MathHelper.valueFromPercentBetween(this.scrollLowerBound, this.scrollUpperBound, newPercent);
        this.updateBarPosition();
    }
    
    private void updateBarPosition() {
        final float percent = MathHelper.percentFromValueBetween(this.scrollLowerBound, this.scrollUpperBound, this.currentDiffY);
        this.scrollBar.parentScrolledToPercent(percent);
    }
    
    private boolean shouldShowScrollBar() {
        return this.scrollUpperBound > DuelistCardViewScreen.SCROLL_BAR_THRESHOLD;
    }
    
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("DrawPileViewScreen");
        TEXT = DuelistCardViewScreen.uiStrings.TEXT;
        SCROLL_BAR_THRESHOLD = 500.0f * Settings.scale;
        BODY_INFO = "";
    }

    public static class Enum {
        @SpireEnum
        public static AbstractDungeon.CurrentScreen DUELIST_CARD_VIEW_SCREEN;

        public Enum() {}
    }
}
