package duelistmod.ui;

import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.MasterDeckViewScreen;
import com.megacrit.cardcrawl.screens.mainMenu.*;

public class DuelistMasterCardViewScreen extends MasterDeckViewScreen implements ScrollBarListener
{
    private static float drawStartX;
    private static float drawStartY;
    private static float padX;
    private static float padY;
    private static final float SCROLL_BAR_THRESHOLD;
    private boolean grabbedScreen;
    private float grabStartY;
    private float currentDiffY;
    private float scrollLowerBound;
    private float scrollUpperBound;
    private AbstractCard hoveredCard;
    private AbstractCard clickStartedCard;
    private int prevDeckSize;
    private ScrollBar scrollBar;
    private AbstractCard controllerCard;
    private DuelistMasterDeckSortHeader sortHeader;
    private int headerIndex;
    private Comparator<AbstractCard> sortOrder;
    private ArrayList<AbstractCard> tmpSortedDeck;
    private float tmpHeaderPosition;
    private int headerScrollLockRemainingFrames;
    private boolean justSorted;
    private String screenName;
    private CardGroup group;
    
    public DuelistMasterCardViewScreen() {
        this.grabbedScreen = false;
        this.grabStartY = 0.0f;
        this.currentDiffY = 0.0f;
        this.scrollLowerBound = -Settings.DEFAULT_SCROLL_LIMIT;
        this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
        this.hoveredCard = null;
        this.clickStartedCard = null;
        this.prevDeckSize = 0;
        this.controllerCard = null;
        this.headerIndex = -1;
        this.sortOrder = DuelistMasterDeckSortHeader.getSortOrder();
        this.tmpSortedDeck = null;
        this.tmpHeaderPosition = Float.NEGATIVE_INFINITY;
        this.headerScrollLockRemainingFrames = 0;
        this.justSorted = false;
        DuelistMasterCardViewScreen.drawStartX = (float)Settings.WIDTH;
        DuelistMasterCardViewScreen.drawStartX -= 5.0f * AbstractCard.IMG_WIDTH * 0.75f;
        DuelistMasterCardViewScreen.drawStartX -= 4.0f * Settings.CARD_VIEW_PAD_X;
        DuelistMasterCardViewScreen.drawStartX /= 2.0f;
        DuelistMasterCardViewScreen.drawStartX += AbstractCard.IMG_WIDTH * 0.75f / 2.0f;
        DuelistMasterCardViewScreen.padX = AbstractCard.IMG_WIDTH * 0.75f + Settings.CARD_VIEW_PAD_X;
        DuelistMasterCardViewScreen.padY = AbstractCard.IMG_HEIGHT * 0.75f + Settings.CARD_VIEW_PAD_Y;
        (this.scrollBar = new ScrollBar(this)).move(0.0f, -30.0f * Settings.scale);
        this.sortHeader = new DuelistMasterDeckSortHeader(this);
    }
    
    @Override
    public void update() {
        this.updateControllerInput();
        if (Settings.isControllerMode && this.controllerCard != null && !CardCrawlGame.isPopupOpen && !AbstractDungeon.topPanel.selectPotionMode) {
            if (Gdx.input.getY() > Settings.HEIGHT * 0.7f) {
                this.currentDiffY += Settings.SCROLL_SPEED;
            }
            else if (Gdx.input.getY() < Settings.HEIGHT * 0.3f) {
                this.currentDiffY -= Settings.SCROLL_SPEED;
            }
        }
        boolean isDraggingScrollBar = false;
        if (this.shouldShowScrollBar()) {
            isDraggingScrollBar = this.scrollBar.update();
        }
        if (!isDraggingScrollBar) {
            this.updateScrolling();
        }
        this.updatePositions();
        this.sortHeader.update();
        this.updateClicking();
        if (Settings.isControllerMode && this.controllerCard != null) {
            Gdx.input.setCursorPosition((int)this.controllerCard.hb.cX, (int)(Settings.HEIGHT - this.controllerCard.hb.cY));
        }
    }
    
    private void updateControllerInput() {
        if (!Settings.isControllerMode || AbstractDungeon.topPanel.selectPotionMode) {
            return;
        }
        final CardGroup deck = this.group;
        boolean anyHovered = false;
        int index = 0;
        if (this.tmpSortedDeck == null) {
            this.tmpSortedDeck = deck.group;
        }
        for (final AbstractCard c : this.tmpSortedDeck) {
            if (c.hb.hovered) {
                anyHovered = true;
                break;
            }
            ++index;
        }
        anyHovered = (anyHovered || this.headerIndex >= 0);
        if (!anyHovered) {
            if (this.tmpSortedDeck.size() > 0) {
                Gdx.input.setCursorPosition((int)this.tmpSortedDeck.get(0).hb.cX, (int)this.tmpSortedDeck.get(0).hb.cY);
                this.controllerCard = this.tmpSortedDeck.get(0);
            }
        }
        else if (this.headerIndex >= 0) {
            if (CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed()) {
                final DuelistMasterDeckSortHeader sortHeader = this.sortHeader;
                final int n = -1;
                this.headerIndex = n;
                sortHeader.selectionIndex = n;
                this.controllerCard = this.tmpSortedDeck.get(0);
                Gdx.input.setCursorPosition((int)this.tmpSortedDeck.get(0).hb.cX, Settings.HEIGHT - (int)this.tmpSortedDeck.get(0).hb.cY);
            }
            else if (CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed()) {
                if (this.headerIndex > 0) {
                    this.selectSortButton(--this.headerIndex);
                }
            }
            else if ((CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed()) && this.headerIndex < this.sortHeader.buttons.length - 1) {
                this.selectSortButton(++this.headerIndex);
            }
        }
        else if ((CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed()) && deck.size() > 5) {
            index -= 5;
            if (index < 0) {
                this.selectSortButton(this.headerIndex = 0);
                return;
            }
            Gdx.input.setCursorPosition((int)this.tmpSortedDeck.get(index).hb.cX, Settings.HEIGHT - (int)this.tmpSortedDeck.get(index).hb.cY);
            this.controllerCard = this.tmpSortedDeck.get(index);
        }
        else if ((CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed()) && deck.size() > 5) {
            if (index < deck.size() - 5) {
                index += 5;
            }
            else {
                index %= 5;
            }
            Gdx.input.setCursorPosition((int)this.tmpSortedDeck.get(index).hb.cX, Settings.HEIGHT - (int)this.tmpSortedDeck.get(index).hb.cY);
            this.controllerCard = this.tmpSortedDeck.get(index);
        }
        else if (CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed()) {
            if (index % 5 > 0) {
                --index;
            }
            else {
                index += 4;
                if (index > deck.size() - 1) {
                    index = deck.size() - 1;
                }
            }
            Gdx.input.setCursorPosition((int)this.tmpSortedDeck.get(index).hb.cX, Settings.HEIGHT - (int)this.tmpSortedDeck.get(index).hb.cY);
            this.controllerCard = this.tmpSortedDeck.get(index);
        }
        else if (CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed()) {
            if (index % 5 < 4) {
                if (++index > deck.size() - 1) {
                    index -= deck.size() % 5;
                }
            }
            else {
                index -= 4;
                if (index < 0) {
                    index = 0;
                }
            }
            Gdx.input.setCursorPosition((int)this.tmpSortedDeck.get(index).hb.cX, Settings.HEIGHT - (int)this.tmpSortedDeck.get(index).hb.cY);
            this.controllerCard = this.tmpSortedDeck.get(index);
        }
    }

    public void open(String nameOfScreen, CardGroup cardsInGroup) {
        this.screenName = nameOfScreen;
        this.group = cardsInGroup;
        this.open();
    }
    
    @Override
    public void open() {
        if (Settings.isControllerMode) {
            Gdx.input.setCursorPosition(10, Settings.HEIGHT / 2);
            this.controllerCard = null;
        }
        AbstractDungeon.player.releaseCard();
        CardCrawlGame.sound.play("DECK_OPEN");
        this.currentDiffY = this.scrollLowerBound;
        this.grabStartY = this.scrollLowerBound;
        this.grabbedScreen = false;
        this.hideCards();
        AbstractDungeon.dynamicBanner.hide();
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = Enum.DUELIST_MASTER_CARD_VIEW;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        AbstractDungeon.overlayMenu.hideCombatPanels();
        AbstractDungeon.overlayMenu.showBlackScreen();
        AbstractDungeon.overlayMenu.cancelButton.show("Return");
        this.calculateScrollBounds();
    }
    
    private void updatePositions() {
        this.hoveredCard = null;
        int lineNum = 0;
        ArrayList<AbstractCard> cards = this.group.group;
        if (this.sortOrder != null) {
            cards = new ArrayList<>(cards);
            cards.sort(this.sortOrder);
            this.tmpSortedDeck = cards;
        }
        else {
            this.tmpSortedDeck = null;
        }
        if (this.justSorted && this.headerScrollLockRemainingFrames <= 0) {
            final AbstractCard c = this.highestYPosition(cards);
            if (c != null) {
                this.tmpHeaderPosition = c.current_y;
            }
        }
        for (int i = 0; i < cards.size(); ++i) {
            final int mod = i % 5;
            if (mod == 0 && i != 0) {
                ++lineNum;
            }
            cards.get(i).target_x = DuelistMasterCardViewScreen.drawStartX + mod * DuelistMasterCardViewScreen.padX;
            cards.get(i).target_y = DuelistMasterCardViewScreen.drawStartY + this.currentDiffY - lineNum * DuelistMasterCardViewScreen.padY;
            cards.get(i).update();
            cards.get(i).updateHoverLogic();
            if (cards.get(i).hb.hovered) {
                this.hoveredCard = cards.get(i);
            }
        }
        final AbstractCard c = this.highestYPosition(cards);
        if (this.justSorted && c != null) {
            int lerps = 0;
            for (float lerpY = c.current_y, lerpTarget = c.target_y; lerpY != lerpTarget; lerpY = MathHelper.cardLerpSnap(lerpY, lerpTarget), ++lerps) {}
            this.headerScrollLockRemainingFrames = lerps;
        }
        this.headerScrollLockRemainingFrames -= (Settings.FAST_MODE ? 2 : 1);
        if (cards.size() > 0 && this.sortHeader != null && c != null) {
            this.sortHeader.updateScrollPosition((this.headerScrollLockRemainingFrames <= 0) ? c.current_y : this.tmpHeaderPosition);
            this.justSorted = false;
        }
    }
    
    private AbstractCard highestYPosition(final List<AbstractCard> cards) {
        if (cards == null) {
            return null;
        }
        float highestY = 0.0f;
        AbstractCard retVal = null;
        for (final AbstractCard card : cards) {
            if (card.current_y > highestY) {
                highestY = card.current_y;
                retVal = card;
            }
        }
        return retVal;
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
        if (this.prevDeckSize != this.group.size()) {
            this.calculateScrollBounds();
        }
        this.resetScrolling();
        this.updateBarPosition();
    }
    
    private void updateClicking() {
        if (this.hoveredCard != null) {
            CardCrawlGame.cursor.changeType(GameCursor.CursorType.INSPECT);
            if (InputHelper.justClickedLeft) {
                this.clickStartedCard = this.hoveredCard;
            }
            if (((InputHelper.justReleasedClickLeft && this.hoveredCard == this.clickStartedCard) || CInputActionSet.select.isJustPressed()) && this.headerIndex < 0) {
                InputHelper.justReleasedClickLeft = false;
                CardCrawlGame.cardPopup.open(this.hoveredCard, this.group);
                this.clickStartedCard = null;
            }
        }
        else {
            this.clickStartedCard = null;
        }
    }
    
    private void calculateScrollBounds() {
        if (this.group.size() > 10) {
            int scrollTmp = this.group.size() / 5 - 2;
            if (this.group.size() % 5 != 0) {
                ++scrollTmp;
            }
            this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT + scrollTmp * DuelistMasterCardViewScreen.padY;
        }
        else {
            this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
        }
        this.prevDeckSize = this.group.size();
    }
    
    private void resetScrolling() {
        if (this.currentDiffY < this.scrollLowerBound) {
            this.currentDiffY = MathHelper.scrollSnapLerpSpeed(this.currentDiffY, this.scrollLowerBound);
        }
        else if (this.currentDiffY > this.scrollUpperBound) {
            this.currentDiffY = MathHelper.scrollSnapLerpSpeed(this.currentDiffY, this.scrollUpperBound);
        }
    }
    
    private void hideCards() {
        int lineNum = 0;
        final ArrayList<AbstractCard> cards = this.group.group;
        for (int i = 0; i < cards.size(); ++i) {
            final int mod = i % 5;
            if (mod == 0 && i != 0) {
                ++lineNum;
            }
            cards.get(i).current_x = DuelistMasterCardViewScreen.drawStartX + mod * DuelistMasterCardViewScreen.padX;
            cards.get(i).current_y = DuelistMasterCardViewScreen.drawStartY + this.currentDiffY - lineNum * DuelistMasterCardViewScreen.padY - MathUtils.random(100.0f * Settings.scale, 200.0f * Settings.scale);
            cards.get(i).targetDrawScale = 0.75f;
            cards.get(i).drawScale = 0.75f;
            cards.get(i).setAngle(0.0f, true);
        }
    }
    
    @Override
    public void render(final SpriteBatch sb) {
        if (this.shouldShowScrollBar()) {
            this.scrollBar.render(sb);
        }
        if (this.hoveredCard == null) {
            this.group.renderMasterDeck(sb); 
        }
        else {
            this.group.renderMasterDeckExceptOneCard(sb, this.hoveredCard);
            this.hoveredCard.renderHoverShadow(sb);
            this.hoveredCard.render(sb);
            if (this.hoveredCard.inBottleFlame) {
                AbstractRelic tmp = RelicLibrary.getRelic("Bottled Flame");
                final float prevX = tmp.currentX;
                final float prevY = tmp.currentY;
                tmp.currentX = this.hoveredCard.current_x + 130.0f * Settings.scale;
                tmp.currentY = this.hoveredCard.current_y + 182.0f * Settings.scale;
                tmp.scale = this.hoveredCard.drawScale * Settings.scale * 1.5f;
                tmp.render(sb);
                tmp.currentX = prevX;
                tmp.currentY = prevY;
                tmp = null;
            }
            else if (this.hoveredCard.inBottleLightning) {
                AbstractRelic tmp = RelicLibrary.getRelic("Bottled Lightning");
                final float prevX = tmp.currentX;
                final float prevY = tmp.currentY;
                tmp.currentX = this.hoveredCard.current_x + 130.0f * Settings.scale;
                tmp.currentY = this.hoveredCard.current_y + 182.0f * Settings.scale;
                tmp.scale = this.hoveredCard.drawScale * Settings.scale * 1.5f;
                tmp.render(sb);
                tmp.currentX = prevX;
                tmp.currentY = prevY;
                tmp = null;
            }
            else if (this.hoveredCard.inBottleTornado) {
                AbstractRelic tmp = RelicLibrary.getRelic("Bottled Tornado");
                final float prevX = tmp.currentX;
                final float prevY = tmp.currentY;
                tmp.currentX = this.hoveredCard.current_x + 130.0f * Settings.scale;
                tmp.currentY = this.hoveredCard.current_y + 182.0f * Settings.scale;
                tmp.scale = this.hoveredCard.drawScale * Settings.scale * 1.5f;
                tmp.render(sb);
                tmp.currentX = prevX;
                tmp.currentY = prevY;
                tmp = null;
            }
        }
        this.group.renderTip(sb);
        FontHelper.renderDeckViewTip(sb, this.screenName, 96.0f * Settings.scale, Settings.CREAM_COLOR);
        this.sortHeader.render(sb);
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
        return this.scrollUpperBound > DuelistMasterCardViewScreen.SCROLL_BAR_THRESHOLD;
    }
    
    @Override
    public void setSortOrder(final Comparator<AbstractCard> sortOrder) {
        if (this.sortOrder != sortOrder) {
            this.justSorted = true;
        }
        this.sortOrder = sortOrder;
    }
    
    private void selectSortButton(final int index) {
        final Hitbox hb = this.sortHeader.buttons[this.headerIndex].hb;
        Gdx.input.setCursorPosition((int)hb.cX, Settings.HEIGHT - (int)hb.cY);
        this.controllerCard = null;
        this.sortHeader.selectionIndex = this.headerIndex;
    }
    
    static {
        DuelistMasterCardViewScreen.drawStartY = (Settings.HEIGHT * 0.66f);
        SCROLL_BAR_THRESHOLD = 500.0f * Settings.scale;
    }

    public static class Enum {
        @SpireEnum
        public static AbstractDungeon.CurrentScreen DUELIST_MASTER_CARD_VIEW;

        public Enum() {}
    }
}
