package duelistmod.actions.unique;

import basemod.BaseMod;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import duelistmod.cards.pools.beast.FirewingPegasus;
import duelistmod.dto.AnyDuelist;
import duelistmod.variables.Tags;

import java.util.ArrayList;

public class FireWingPegasusDrawAction extends AbstractGameAction {

    private boolean shuffleCheck;
    public static ArrayList<AbstractCard> drawnCards = new ArrayList<>();
    private boolean clearDrawHistory;
    private final AnyDuelist duelist;
    private final FirewingPegasus caller;

    public FireWingPegasusDrawAction(int amount, FirewingPegasus caller) {
        this.shuffleCheck = false;
        this.clearDrawHistory = true;
        this.duelist = AnyDuelist.from(caller);
        this.caller = caller;
        this.setValues(duelist.creature(), duelist.creature(), amount);
        this.actionType = ActionType.DRAW;
        if (Settings.FAST_MODE) {
            this.duration = Settings.ACTION_DUR_XFAST;
        } else {
            this.duration = Settings.ACTION_DUR_FASTER;
        }
    }

    public void update() {
        if (this.clearDrawHistory) {
            this.clearDrawHistory = false;
            drawnCards.clear();
        }

        if (this.duelist.hasPower("No Draw")) {
            this.duelist.getPower("No Draw").flash();
        } else if (this.amount > 0) {
            int deckSize = this.duelist.drawPile().size();
            int discardSize = this.duelist.discardPile().size();
            if (!SoulGroup.isActive()) {
                if (!(deckSize + discardSize == 0)) {
                    if (this.duelist.hand().size() == BaseMod.MAX_HAND_SIZE) {
                        if (duelist.player()) {
                            AbstractDungeon.player.createHandIsFullDialog();
                        }
                    } else {
                        if (!this.shuffleCheck) {
                            int tmp;
                            if (this.amount + this.duelist.hand().size() > 10) {
                                tmp = 10 - (this.amount + this.duelist.hand().size());
                                this.amount += tmp;
                                if (duelist.player()) {
                                    AbstractDungeon.player.createHandIsFullDialog();
                                }
                            }

                            if (this.amount > deckSize) {
                                tmp = this.amount - deckSize;
                                this.addToTop(new DrawCardAction(tmp, false));
                                this.addToTop(new EmptyDeckShuffleAction());
                                if (deckSize != 0) {
                                    this.addToTop(new DrawCardAction(deckSize, false));
                                }

                                this.amount = 0;
                                this.isDone = true;
                                return;
                            }

                            this.shuffleCheck = true;
                        }

                        this.duration -= Gdx.graphics.getDeltaTime();
                        if (this.amount != 0 && this.duration < 0.0F) {
                            if (Settings.FAST_MODE) {
                                this.duration = Settings.ACTION_DUR_XFAST;
                            } else {
                                this.duration = Settings.ACTION_DUR_FASTER;
                            }

                            --this.amount;
                            if (!this.duelist.drawPile().isEmpty()) {
                                drawnCards.add(this.duelist.drawPileGroup().getTopCard());
                                if (this.duelist.player()) {
                                    AbstractDungeon.player.draw();
                                    AbstractDungeon.player.hand.refreshHandLayout();
                                } else if (this.duelist.getEnemy() != null) {
                                    this.duelist.getEnemy().draw();
                                    this.duelist.getEnemy().hand.refreshHandLayout();
                                }
                            }
                        } else {
                            checkForBeasts();
                            this.isDone = true;
                        }
                    }
                }
            }
        }
    }

    private void checkForBeasts() {
        for (AbstractCard card : drawnCards) {
            if (card.hasTag(Tags.BEAST)) {
                this.caller.passBeastCheckEffect();
                return;
            }
        }
    }
}
