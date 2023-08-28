package duelistmod.ui;

import java.util.ArrayList;
import java.util.function.*;

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
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.mainMenu.*;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.ui.buttons.*;
import com.megacrit.cardcrawl.vfx.FastCardObtainEffect;

public class DuelistCardSelectScreen extends GridCardSelectScreen implements ScrollBarListener
{
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static float drawStartX;
    private static float drawStartY;
    private static float padX;
    private static float padY;
    private static final float SCROLL_BAR_THRESHOLD;
    private float grabStartY;
    private float currentDiffY;
    public ArrayList<AbstractCard> selectedCards;
    public CardGroup targetGroup;
    private AbstractCard hoveredCard;
    public AbstractCard upgradePreviewCard;
    private int numCards;
    private int cardSelectAmount;
    private final float scrollLowerBound;
    private float scrollUpperBound;
    private boolean grabbedScreen;
    private boolean canCancel;
    public boolean forUpgrade;
    public boolean forTransform;
    public boolean forPurge;
    public boolean confirmScreenUp;
    public boolean isJustForConfirming;
    public GridSelectConfirmButton confirmButton;
    public PeekButton peekButton;
    private String tipMsg;
    private String lastTip;
    private float ritualAnimTimer;
    private int prevDeckSize;
    public boolean cancelWasOn;
    public boolean anyNumber;
    public boolean forClarity;
    public String cancelText;
    private final ScrollBar scrollBar;
    private AbstractCard controllerCard;
    private float arrowScale1;
    private float arrowScale2;
    private float arrowScale3;
    private float arrowTimer;
    private boolean allowUpgrades;
    private boolean isAutoConfirm = true;

    private Consumer<ArrayList<AbstractCard>> onConfirmBehavior;
    
    public DuelistCardSelectScreen(boolean allowUpgrades) {
        this.grabStartY = 0.0f;
        this.currentDiffY = 0.0f;
        this.selectedCards = new ArrayList<>();
        this.hoveredCard = null;
        this.upgradePreviewCard = null;
        this.numCards = 0;
        this.cardSelectAmount = 0;
        this.scrollLowerBound = -Settings.DEFAULT_SCROLL_LIMIT;
        this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
        this.grabbedScreen = false;
        this.canCancel = true;
        this.forUpgrade = false;
        this.forTransform = false;
        this.forPurge = false;
        this.confirmScreenUp = false;
        this.isJustForConfirming = false;
        this.confirmButton = new GridSelectConfirmButton(DuelistCardSelectScreen.TEXT[0]);
        this.peekButton = new PeekButton();
        this.tipMsg = "";
        this.lastTip = "";
        this.ritualAnimTimer = 0.0f;
        this.prevDeckSize = 0;
        this.cancelWasOn = false;
        this.anyNumber = false;
        this.forClarity = false;
        this.controllerCard = null;
        this.arrowScale1 = 1.0f;
        this.arrowScale2 = 1.0f;
        this.arrowScale3 = 1.0f;
        this.arrowTimer = 0.0f;
        this.allowUpgrades = allowUpgrades;
        DuelistCardSelectScreen.drawStartX = (float)Settings.WIDTH;
        DuelistCardSelectScreen.drawStartX -= 5.0f * AbstractCard.IMG_WIDTH * 0.75f;
        DuelistCardSelectScreen.drawStartX -= 4.0f * Settings.CARD_VIEW_PAD_X;
        DuelistCardSelectScreen.drawStartX /= 2.0f;
        DuelistCardSelectScreen.drawStartX += AbstractCard.IMG_WIDTH * 0.75f / 2.0f;
        DuelistCardSelectScreen.padX = AbstractCard.IMG_WIDTH * 0.75f + Settings.CARD_VIEW_PAD_X;
        DuelistCardSelectScreen.padY = AbstractCard.IMG_HEIGHT * 0.75f + Settings.CARD_VIEW_PAD_Y;
        (this.scrollBar = new ScrollBar(this)).move(0.0f, -30.0f * Settings.scale);
    }

    @Override
    public void update() {
        this.updateControllerInput();
        this.updatePeekButton();
        if (PeekButton.isPeeking) {
            return;
        }
        if (Settings.isControllerMode && this.controllerCard != null && !CardCrawlGame.isPopupOpen && this.upgradePreviewCard == null) {
            if (Gdx.input.getY() > Settings.HEIGHT * 0.75f) {
                this.currentDiffY += Settings.SCROLL_SPEED;
            }
            else if (Gdx.input.getY() < Settings.HEIGHT * 0.25f) {
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
        if (this.forClarity) {
            this.confirmButton.isDisabled = this.selectedCards.size() <= 0;
        }
        this.confirmButton.update();
        if (this.isJustForConfirming) {
            this.updateCardPositionsAndHoverLogic();
            if (this.confirmButton.hb.clicked || CInputActionSet.topPanel.isJustPressed()) {
                CInputActionSet.select.unpress();
                this.confirmButton.hb.clicked = false;
                AbstractDungeon.overlayMenu.cancelButton.hide();
                AbstractDungeon.dynamicBanner.hide();
                this.confirmScreenUp = false;
                for (final AbstractCard c : this.targetGroup.group) {
                    AbstractDungeon.topLevelEffects.add(new FastCardObtainEffect(c, c.current_x, c.current_y));
                }
                AbstractDungeon.closeCurrentScreen();
            }
            return;
        }
        if ((this.anyNumber || this.forClarity) && (this.confirmButton.hb.clicked || (this.isAutoConfirm && this.selectedCards.size() == this.numCards))) {
            this.confirmButton.hb.clicked = false;
            if (this.onConfirmBehavior != null) {
                this.onConfirmBehavior.accept(this.selectedCards);
                this.selectedCards.forEach(AbstractCard::stopGlowing);
                this.onConfirmBehavior = null;
            }
            this.selectedCards.clear();
            CInputActionSet.select.unpress();
            this.confirmButton.hb.clicked = false;
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.dynamicBanner.hide();
            this.confirmScreenUp = false;
            AbstractDungeon.closeCurrentScreen();
            return;
        }
        if (this.numCards < 1) {
            this.confirmButton.hb.clicked = false;
            AbstractDungeon.closeCurrentScreen();
            return;
        }
        if (!this.confirmScreenUp) {
            this.updateCardPositionsAndHoverLogic();
            if (this.hoveredCard != null && InputHelper.justClickedRight && this.allowUpgrades) {
            	InputHelper.justClickedRight = false;
                CardCrawlGame.cardPopup.open(hoveredCard);
            }
            if (this.hoveredCard != null && InputHelper.justClickedLeft) {
                this.hoveredCard.hb.clickStarted = true;
            }
            if (this.hoveredCard != null && (this.hoveredCard.hb.clicked || CInputActionSet.select.isJustPressed())) {
                this.hoveredCard.hb.clicked = false;
                if (!this.selectedCards.contains(this.hoveredCard) && this.selectedCards.size() < this.numCards) {
                    if (this.forClarity && this.selectedCards.size() > 0) {
                        this.selectedCards.get(0).stopGlowing();
                        this.selectedCards.clear();
                        --this.cardSelectAmount;
                    }
                    this.selectedCards.add(this.hoveredCard);
                    this.hoveredCard.beginGlowing();
                    this.hoveredCard.targetDrawScale = 0.75f;
                    this.hoveredCard.drawScale = 0.875f;
                    ++this.cardSelectAmount;
                    CardCrawlGame.sound.play("CARD_SELECT");
                    if (this.numCards == this.cardSelectAmount) {
                        if (this.forUpgrade) {
                            this.hoveredCard.untip();
                            this.confirmScreenUp = true;
                            (this.upgradePreviewCard = this.hoveredCard.makeStatEquivalentCopy()).upgrade();
                            this.upgradePreviewCard.displayUpgrades();
                            this.upgradePreviewCard.drawScale = 0.875f;
                            this.hoveredCard.stopGlowing();
                            this.selectedCards.clear();
                            AbstractDungeon.overlayMenu.cancelButton.show(DuelistCardSelectScreen.TEXT[1]);
                            this.confirmButton.show();
                            this.confirmButton.isDisabled = false;
                            this.lastTip = this.tipMsg;
                            this.tipMsg = DuelistCardSelectScreen.TEXT[2];
                            return;
                        }
                        if (this.forTransform) {
                            this.hoveredCard.untip();
                            this.confirmScreenUp = true;
                            this.upgradePreviewCard = this.hoveredCard.makeStatEquivalentCopy();
                            this.upgradePreviewCard.drawScale = 0.875f;
                            this.hoveredCard.stopGlowing();
                            this.selectedCards.clear();
                            AbstractDungeon.overlayMenu.cancelButton.show(DuelistCardSelectScreen.TEXT[1]);
                            this.confirmButton.show();
                            this.confirmButton.isDisabled = false;
                            this.lastTip = this.tipMsg;
                            this.tipMsg = DuelistCardSelectScreen.TEXT[2];
                            return;
                        }
                        if (this.forPurge) {
                            if (this.numCards == 1) {
                                this.hoveredCard.untip();
                                this.hoveredCard.stopGlowing();
                                this.confirmScreenUp = true;
                                this.hoveredCard.current_x = Settings.WIDTH / 2.0f;
                                this.hoveredCard.target_x = Settings.WIDTH / 2.0f;
                                this.hoveredCard.current_y = Settings.HEIGHT / 2.0f;
                                this.hoveredCard.target_y = Settings.HEIGHT / 2.0f;
                                this.hoveredCard.update();
                                this.hoveredCard.targetDrawScale = 1.0f;
                                this.hoveredCard.drawScale = 1.0f;
                                this.selectedCards.clear();
                                this.confirmButton.show();
                                this.confirmButton.isDisabled = false;
                                this.lastTip = this.tipMsg;
                                this.tipMsg = DuelistCardSelectScreen.TEXT[2];
                                AbstractDungeon.overlayMenu.cancelButton.show(DuelistCardSelectScreen.TEXT[1]);
                            }
                            else {
                                AbstractDungeon.closeCurrentScreen();
                            }
                            for (final AbstractCard c : this.selectedCards) {
                                c.stopGlowing();
                            }
                            return;
                        }
                        if (!this.anyNumber) {
                            AbstractDungeon.closeCurrentScreen();
                            if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.SHOP) {
                                AbstractDungeon.overlayMenu.cancelButton.hide();
                            }
                            else {
                                AbstractDungeon.overlayMenu.cancelButton.show(DuelistCardSelectScreen.TEXT[3]);
                            }
                            for (final AbstractCard c : this.selectedCards) {
                                c.stopGlowing();
                            }
                            if (this.targetGroup.type == CardGroup.CardGroupType.DISCARD_PILE) {
                                for (final AbstractCard c : this.targetGroup.group) {
                                    c.drawScale = 0.12f;
                                    c.targetDrawScale = 0.12f;
                                    c.teleportToDiscardPile();
                                    c.lighten(true);
                                }
                            }
                        }
                    }
                }
                else if (this.selectedCards.contains(this.hoveredCard)) {
                    this.hoveredCard.stopGlowing();
                    this.selectedCards.remove(this.hoveredCard);
                    --this.cardSelectAmount;
                }
                return;
            }
        }
        else {
            if (this.forTransform) {
                this.ritualAnimTimer -= Gdx.graphics.getDeltaTime();
                if (this.ritualAnimTimer < 0.0f) {
                    this.upgradePreviewCard = AbstractDungeon.returnTrulyRandomCardFromAvailable(this.upgradePreviewCard).makeCopy();
                    this.ritualAnimTimer = 0.1f;
                }
            }
            if (this.forUpgrade) {
                this.upgradePreviewCard.update();
            }
            if (!this.forPurge) {
                this.upgradePreviewCard.drawScale = 1.0f;
                this.hoveredCard.update();
                this.hoveredCard.drawScale = 1.0f;
            }
            if (this.confirmButton.hb.clicked || CInputActionSet.topPanel.isJustPressed()) {
                CInputActionSet.select.unpress();
                this.confirmButton.hb.clicked = false;
                AbstractDungeon.overlayMenu.cancelButton.hide();
                this.confirmScreenUp = false;
                this.selectedCards.add(this.hoveredCard);
                if (this.onConfirmBehavior != null) {
                    this.onConfirmBehavior.accept(this.selectedCards);
                    this.onConfirmBehavior = null;
                }
                CInputActionSet.select.unpress();
                this.confirmButton.hb.clicked = false;
                AbstractDungeon.overlayMenu.cancelButton.hide();
                AbstractDungeon.dynamicBanner.hide();
                this.confirmScreenUp = false;
                AbstractDungeon.closeCurrentScreen();
            }
        }
        if (Settings.isControllerMode) {
            if (this.upgradePreviewCard != null) {
                CInputHelper.setCursor(this.upgradePreviewCard.hb);
            }
            else if (this.controllerCard != null) {
                CInputHelper.setCursor(this.controllerCard.hb);
            }
        }
    }
    
    private void updatePeekButton() {
        this.peekButton.update();
    }
    
    private void updateControllerInput() {
        if (!Settings.isControllerMode || this.upgradePreviewCard != null) {
            return;
        }
        boolean anyHovered = false;
        int index = 0;
        for (final AbstractCard c : this.targetGroup.group) {
            if (c.hb.hovered) {
                anyHovered = true;
                break;
            }
            ++index;
        }
        if (!anyHovered && this.controllerCard == null) {
            CInputHelper.setCursor(this.targetGroup.group.get(0).hb);
            this.controllerCard = this.targetGroup.group.get(0);
        }
        else if ((CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed()) && this.targetGroup.size() > 5) {
            if (index < 5) {
                index = this.targetGroup.size() + 2 - (4 - index);
                if (index > this.targetGroup.size() - 1) {
                    index -= 5;
                }
                if (index > this.targetGroup.size() - 1 || index < 0) {
                    index = 0;
                }
            }
            else {
                index -= 5;
            }
            CInputHelper.setCursor(this.targetGroup.group.get(index).hb);
            this.controllerCard = this.targetGroup.group.get(index);
        }
        else if ((CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed()) && this.targetGroup.size() > 5) {
            if (index < this.targetGroup.size() - 5) {
                index += 5;
            }
            else {
                index %= 5;
            }
            CInputHelper.setCursor(this.targetGroup.group.get(index).hb);
            this.controllerCard = this.targetGroup.group.get(index);
        }
        else if (CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed()) {
            if (index % 5 > 0) {
                --index;
            }
            else {
                index += 4;
                if (index > this.targetGroup.size() - 1) {
                    index = this.targetGroup.size() - 1;
                }
            }
            CInputHelper.setCursor(this.targetGroup.group.get(index).hb);
            this.controllerCard = this.targetGroup.group.get(index);
        }
        else if (CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed()) {
            if (index % 5 < 4) {
                if (++index > this.targetGroup.size() - 1) {
                    index -= this.targetGroup.size() % 5;
                }
            }
            else {
                index -= 4;
                if (index < 0) {
                    index = 0;
                }
            }
            if (index > this.targetGroup.group.size() - 1) {
                index = 0;
            }
            CInputHelper.setCursor(this.targetGroup.group.get(index).hb);
            this.controllerCard = this.targetGroup.group.get(index);
        }
    }
    
    private void updateCardPositionsAndHoverLogic() {
        if (this.isJustForConfirming && this.targetGroup.size() <= 4) {
            switch (this.targetGroup.size()) {
                case 1: {
                    this.targetGroup.getBottomCard().current_x = Settings.WIDTH / 2.0f;
                    this.targetGroup.getBottomCard().target_x = Settings.WIDTH / 2.0f;
                    break;
                }
                case 2: {
                    this.targetGroup.group.get(0).current_x = Settings.WIDTH / 2.0f - DuelistCardSelectScreen.padX / 2.0f;
                    this.targetGroup.group.get(0).target_x = Settings.WIDTH / 2.0f - DuelistCardSelectScreen.padX / 2.0f;
                    this.targetGroup.group.get(1).current_x = Settings.WIDTH / 2.0f + DuelistCardSelectScreen.padX / 2.0f;
                    this.targetGroup.group.get(1).target_x = Settings.WIDTH / 2.0f + DuelistCardSelectScreen.padX / 2.0f;
                    break;
                }
                case 3: {
                    this.targetGroup.group.get(0).current_x = DuelistCardSelectScreen.drawStartX + DuelistCardSelectScreen.padX;
                    this.targetGroup.group.get(1).current_x = DuelistCardSelectScreen.drawStartX + DuelistCardSelectScreen.padX * 2.0f;
                    this.targetGroup.group.get(2).current_x = DuelistCardSelectScreen.drawStartX + DuelistCardSelectScreen.padX * 3.0f;
                    this.targetGroup.group.get(0).target_x = DuelistCardSelectScreen.drawStartX + DuelistCardSelectScreen.padX;
                    this.targetGroup.group.get(1).target_x = DuelistCardSelectScreen.drawStartX + DuelistCardSelectScreen.padX * 2.0f;
                    this.targetGroup.group.get(2).target_x = DuelistCardSelectScreen.drawStartX + DuelistCardSelectScreen.padX * 3.0f;
                    break;
                }
                case 4: {
                    this.targetGroup.group.get(0).current_x = Settings.WIDTH / 2.0f - DuelistCardSelectScreen.padX / 2.0f - DuelistCardSelectScreen.padX;
                    this.targetGroup.group.get(0).target_x = Settings.WIDTH / 2.0f - DuelistCardSelectScreen.padX / 2.0f - DuelistCardSelectScreen.padX;
                    this.targetGroup.group.get(1).current_x = Settings.WIDTH / 2.0f - DuelistCardSelectScreen.padX / 2.0f;
                    this.targetGroup.group.get(1).target_x = Settings.WIDTH / 2.0f - DuelistCardSelectScreen.padX / 2.0f;
                    this.targetGroup.group.get(2).current_x = Settings.WIDTH / 2.0f + DuelistCardSelectScreen.padX / 2.0f;
                    this.targetGroup.group.get(2).target_x = Settings.WIDTH / 2.0f + DuelistCardSelectScreen.padX / 2.0f;
                    this.targetGroup.group.get(3).current_x = Settings.WIDTH / 2.0f + DuelistCardSelectScreen.padX / 2.0f + DuelistCardSelectScreen.padX;
                    this.targetGroup.group.get(3).target_x = Settings.WIDTH / 2.0f + DuelistCardSelectScreen.padX / 2.0f + DuelistCardSelectScreen.padX;
                    break;
                }
            }
            final ArrayList<AbstractCard> c2 = this.targetGroup.group;
            for (int i = 0; i < c2.size(); ++i) {
                c2.get(i).target_y = DuelistCardSelectScreen.drawStartY + this.currentDiffY;
                c2.get(i).fadingOut = false;
                c2.get(i).update();
                c2.get(i).updateHoverLogic();
                this.hoveredCard = null;
                for (final AbstractCard c3 : c2) {
                    if (c3.hb.hovered) {
                        this.hoveredCard = c3;
                    }
                }
            }
            return;
        }
        int lineNum = 0;
        final ArrayList<AbstractCard> cards = this.targetGroup.group;
        for (int j = 0; j < cards.size(); ++j) {
            final int mod = j % 5;
            if (mod == 0 && j != 0) {
                ++lineNum;
            }
            cards.get(j).target_x = DuelistCardSelectScreen.drawStartX + mod * DuelistCardSelectScreen.padX;
            cards.get(j).target_y = DuelistCardSelectScreen.drawStartY + this.currentDiffY - lineNum * DuelistCardSelectScreen.padY;
            cards.get(j).fadingOut = false;
            cards.get(j).update();
            cards.get(j).updateHoverLogic();
            this.hoveredCard = null;
            for (final AbstractCard c4 : cards) {
                if (c4.hb.hovered) {
                    this.hoveredCard = c4;
                }
            }
        }
    }

    public void open(final CardGroup group, final int numCards, final String msg) {
        this.open(group, numCards, msg, false, false, true, false);
        this.anyNumber = true;
        this.forClarity = false;
        this.confirmButton.hideInstantly();
        this.confirmButton.show();
        this.confirmButton.updateText(DuelistCardSelectScreen.TEXT[0]);
        this.confirmButton.isDisabled = false;
    }

    public void open(boolean allowUpgrades, final CardGroup group, final int numCards, final String msg, Consumer<ArrayList<AbstractCard>> onConfirmBehavior) {
        this.open(allowUpgrades, group, numCards, msg, onConfirmBehavior, false);
    }

    public void open(boolean allowUpgrades, final CardGroup group, final int numCards, final String msg, Consumer<ArrayList<AbstractCard>> onConfirmBehavior, boolean isAutoConfirm) {
        this.selectedCards.clear();
        this.allowUpgrades = allowUpgrades;
        this.onConfirmBehavior = onConfirmBehavior;
        this.isAutoConfirm = isAutoConfirm;
        this.open(group, numCards, msg);
    }

    @Override
    public void open(final CardGroup group, final int numCards, final String tipMsg, final boolean forUpgrade, final boolean forTransform, final boolean canCancel, final boolean forPurge) {
        this.targetGroup = group;
        this.callOnOpen();
        this.forUpgrade = forUpgrade;
        this.forTransform = forTransform;
        this.canCancel = canCancel;
        this.forPurge = forPurge;
        this.tipMsg = tipMsg;
        this.numCards = numCards;
        if ((forUpgrade || forTransform || forPurge || AbstractDungeon.previousScreen == AbstractDungeon.CurrentScreen.SHOP) || canCancel) {
            AbstractDungeon.overlayMenu.cancelButton.show(DuelistCardSelectScreen.TEXT[1]);
        }
        if (!canCancel) {
            AbstractDungeon.overlayMenu.cancelButton.hide();
        }
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.peekButton.hideInstantly();
            this.peekButton.show();
        }
        this.calculateScrollBounds();
    }

    @Override
    public void open(final CardGroup group, final int numCards, final String tipMsg, final boolean forUpgrade, final boolean forRitual) {
        this.open(group, numCards, tipMsg, forUpgrade, forRitual, true, false);
    }

    @Override
    public void open(final CardGroup group, final int numCards, final String tipMsg, final boolean forUpgrade) {
        this.open(group, numCards, tipMsg, forUpgrade, false);
    }
    
    @Override
    public void openConfirmationGrid(final CardGroup group, final String tipMsg) {
        this.targetGroup = group;
        this.callOnOpen();
        this.isJustForConfirming = true;
        this.tipMsg = tipMsg;
        AbstractDungeon.overlayMenu.cancelButton.hideInstantly();
        this.confirmButton.show();
        this.confirmButton.updateText(DuelistCardSelectScreen.TEXT[0]);
        this.confirmButton.isDisabled = false;
        this.canCancel = false;
        if (group.size() <= 5) {
            AbstractDungeon.dynamicBanner.appear(tipMsg);
        }
    }
    
    private void callOnOpen() {
        if (Settings.isControllerMode) {
            Gdx.input.setCursorPosition(10, Settings.HEIGHT / 2);
            this.controllerCard = null;
        }
        this.anyNumber = false;
        this.forClarity = false;
        this.canCancel = false;
        this.forUpgrade = false;
        this.forTransform = false;
        this.forPurge = false;
        this.confirmScreenUp = false;
        this.isJustForConfirming = false;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        this.controllerCard = null;
        this.hoveredCard = null;
        this.selectedCards.clear();
        AbstractDungeon.topPanel.unhoverHitboxes();
        this.cardSelectAmount = 0;
        this.currentDiffY = 0.0f;
        this.grabStartY = 0.0f;
        this.grabbedScreen = false;
        this.hideCards();
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = Enum.DUELIST_SELECTION_SCREEN;
        AbstractDungeon.overlayMenu.showBlackScreen(0.75f);
        this.confirmButton.hideInstantly();
        this.peekButton.hideInstantly();
        if (this.targetGroup.group.size() <= 5) {
            DuelistCardSelectScreen.drawStartY = Settings.HEIGHT * 0.5f;
        }
        else {
            DuelistCardSelectScreen.drawStartY = Settings.HEIGHT * 0.66f;
        }
    }
    
    @Override
    public void reopen() {
        AbstractDungeon.overlayMenu.showBlackScreen(0.75f);
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = Enum.DUELIST_SELECTION_SCREEN;
        AbstractDungeon.topPanel.unhoverHitboxes();
        if (this.cancelWasOn && !this.isJustForConfirming && this.canCancel) {
            AbstractDungeon.overlayMenu.cancelButton.show(this.cancelText);
        }
        for (final AbstractCard c : this.targetGroup.group) {
            c.targetDrawScale = 0.75f;
            c.drawScale = 0.75f;
            c.lighten(false);
        }
        this.scrollBar.reset();
    }
    
    @Override
    public void hide() {
        if (!AbstractDungeon.overlayMenu.cancelButton.isHidden) {
            this.cancelWasOn = true;
            this.cancelText = AbstractDungeon.overlayMenu.cancelButton.buttonText;
        }
    }
    
    private void updateScrolling() {
        if (PeekButton.isPeeking) {
            return;
        }
        if (this.isJustForConfirming && this.targetGroup.size() <= 5) {
            this.currentDiffY = -64.0f * Settings.scale;
            return;
        }
        final int y = InputHelper.mY;
        final boolean isDraggingScrollBar = this.scrollBar.update();
        if (!isDraggingScrollBar) {
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
        }
        if (this.prevDeckSize != this.targetGroup.size()) {
            this.calculateScrollBounds();
        }
        this.resetScrolling();
        this.updateBarPosition();
    }
    
    private void calculateScrollBounds() {
        int scrollTmp;
        if (this.targetGroup.size() > 10) {
            scrollTmp = this.targetGroup.size() / 5 - 2;
            if (this.targetGroup.size() % 5 != 0) {
                ++scrollTmp;
            }
            this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT + scrollTmp * DuelistCardSelectScreen.padY;
        }
        else {
            this.scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
        }
        this.prevDeckSize = this.targetGroup.size();
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
        final ArrayList<AbstractCard> cards = this.targetGroup.group;
        for (int i = 0; i < cards.size(); ++i) {
            cards.get(i).setAngle(0.0f, true);
            final int mod = i % 5;
            if (mod == 0 && i != 0) {
                ++lineNum;
            }
            cards.get(i).lighten(true);
            cards.get(i).current_x = DuelistCardSelectScreen.drawStartX + mod * DuelistCardSelectScreen.padX;
            cards.get(i).current_y = DuelistCardSelectScreen.drawStartY + this.currentDiffY - lineNum * DuelistCardSelectScreen.padY - MathUtils.random(100.0f * Settings.scale, 200.0f * Settings.scale);
            cards.get(i).targetDrawScale = 0.75f;
            cards.get(i).drawScale = 0.75f;
        }
    }
    
    @Override
    public void cancelUpgrade() {
        this.cardSelectAmount = 0;
        this.confirmScreenUp = false;
        this.confirmButton.hide();
        this.confirmButton.isDisabled = true;
        this.hoveredCard = null;
        this.upgradePreviewCard = null;
        if (Settings.isControllerMode && this.controllerCard != null) {
            this.hoveredCard = this.controllerCard;
            CInputHelper.setCursor(this.hoveredCard.hb);
        }
        if ((this.forUpgrade || this.forTransform || this.forPurge || AbstractDungeon.previousScreen == AbstractDungeon.CurrentScreen.SHOP) && this.canCancel) {
            AbstractDungeon.overlayMenu.cancelButton.show(DuelistCardSelectScreen.TEXT[1]);
        }
        int lineNum = 0;
        final ArrayList<AbstractCard> cards = this.targetGroup.group;
        for (int i = 0; i < cards.size(); ++i) {
            final int mod = i % 5;
            if (mod == 0 && i != 0) {
                ++lineNum;
            }
            cards.get(i).current_x = DuelistCardSelectScreen.drawStartX + mod * DuelistCardSelectScreen.padX;
            cards.get(i).current_y = DuelistCardSelectScreen.drawStartY + this.currentDiffY - lineNum * DuelistCardSelectScreen.padY;
        }
        this.tipMsg = this.lastTip;
    }
    
    @Override
    public void render(final SpriteBatch sb) {
        if (this.shouldShowScrollBar()) {
            this.scrollBar.render(sb);
        }
        if (!PeekButton.isPeeking) {
            if (this.hoveredCard != null) {
                if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                    this.targetGroup.renderExceptOneCard(sb, this.hoveredCard);
                }
                else {
                    this.targetGroup.renderExceptOneCardShowBottled(sb, this.hoveredCard);
                }
                this.hoveredCard.renderHoverShadow(sb);
                this.hoveredCard.render(sb);
                if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
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
                this.hoveredCard.renderCardTip(sb);
            }
            else if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                this.targetGroup.render(sb);
            }
            else {
                this.targetGroup.renderShowBottled(sb);
            }
        }
        if (this.confirmScreenUp) {
            sb.setColor(new Color(0.0f, 0.0f, 0.0f, 0.8f));
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0f, 0.0f, (float)Settings.WIDTH, Settings.HEIGHT - 64.0f * Settings.scale);
            if (this.forTransform || this.forUpgrade) {
                this.renderArrows(sb);
                this.hoveredCard.current_x = Settings.WIDTH * 0.36f;
                this.hoveredCard.current_y = Settings.HEIGHT / 2.0f;
                this.hoveredCard.target_x = Settings.WIDTH * 0.36f;
                this.hoveredCard.target_y = Settings.HEIGHT / 2.0f;
                this.hoveredCard.render(sb);
                this.hoveredCard.updateHoverLogic();
                this.upgradePreviewCard.current_x = Settings.WIDTH * 0.63f;
                this.upgradePreviewCard.current_y = Settings.HEIGHT / 2.0f;
                this.upgradePreviewCard.target_x = Settings.WIDTH * 0.63f;
                this.upgradePreviewCard.target_y = Settings.HEIGHT / 2.0f;
                this.upgradePreviewCard.render(sb);
                this.upgradePreviewCard.updateHoverLogic();
                this.upgradePreviewCard.renderCardTip(sb);
            }
            else {
                this.hoveredCard.current_x = Settings.WIDTH / 2.0f;
                this.hoveredCard.current_y = Settings.HEIGHT / 2.0f;
                this.hoveredCard.render(sb);
                this.hoveredCard.updateHoverLogic();
            }
        }
        if (!PeekButton.isPeeking && (this.forUpgrade || this.forTransform || this.forPurge || this.isJustForConfirming || this.anyNumber || this.forClarity)) {
            this.confirmButton.render(sb);
        }
        this.peekButton.render(sb);
        if ((!this.isJustForConfirming || this.targetGroup.size() > 5) && !PeekButton.isPeeking) {
            FontHelper.renderDeckViewTip(sb, this.tipMsg, 96.0f * Settings.scale, Settings.CREAM_COLOR);
        }
    }
    
    private void renderArrows(final SpriteBatch sb) {
        float x = Settings.WIDTH / 2.0f - 73.0f * Settings.scale - 32.0f;
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.UPGRADE_ARROW, x, Settings.HEIGHT / 2.0f - 32.0f, 32.0f, 32.0f, 64.0f, 64.0f, this.arrowScale1 * Settings.scale, this.arrowScale1 * Settings.scale, 0.0f, 0, 0, 64, 64, false, false);
        x += 64.0f * Settings.scale;
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.UPGRADE_ARROW, x, Settings.HEIGHT / 2.0f - 32.0f, 32.0f, 32.0f, 64.0f, 64.0f, this.arrowScale2 * Settings.scale, this.arrowScale2 * Settings.scale, 0.0f, 0, 0, 64, 64, false, false);
        x += 64.0f * Settings.scale;
        sb.draw(ImageMaster.UPGRADE_ARROW, x, Settings.HEIGHT / 2.0f - 32.0f, 32.0f, 32.0f, 64.0f, 64.0f, this.arrowScale3 * Settings.scale, this.arrowScale3 * Settings.scale, 0.0f, 0, 0, 64, 64, false, false);
        this.arrowTimer += Gdx.graphics.getDeltaTime() * 2.0f;
        this.arrowScale1 = 0.8f + (MathUtils.cos(this.arrowTimer) + 1.0f) / 8.0f;
        this.arrowScale2 = 0.8f + (MathUtils.cos(this.arrowTimer - 0.8f) + 1.0f) / 8.0f;
        this.arrowScale3 = 0.8f + (MathUtils.cos(this.arrowTimer - 1.6f) + 1.0f) / 8.0f;
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
        return !this.confirmScreenUp && this.scrollUpperBound > DuelistCardSelectScreen.SCROLL_BAR_THRESHOLD && !PeekButton.isPeeking;
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("GridCardSelectScreen");
        TEXT = DuelistCardSelectScreen.uiStrings.TEXT;
        SCROLL_BAR_THRESHOLD = 500.0f * Settings.scale;
    }

    public static class Enum {
        @SpireEnum
        public static AbstractDungeon.CurrentScreen DUELIST_SELECTION_SCREEN;

        public Enum() {}
    }
}
