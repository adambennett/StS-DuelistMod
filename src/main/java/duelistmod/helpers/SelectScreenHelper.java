package duelistmod.helpers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import duelistmod.DuelistMod;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

public class SelectScreenHelper {

    public static void open(CardGroup cards) {
        open(cards, true);
    }

    public static void open(CardGroup cards, boolean autoConfirm) {
        open(cards, 1, autoConfirm);
    }

    public static void open(CardGroup cards, String msg) {
        open(cards, msg, true);
    }

    public static void open(CardGroup cards, String msg, boolean autoConfirm) {
        open(cards, 1, msg, autoConfirm, null);
    }

    public static void open(CardGroup cards, int amount) {
        open(cards, amount, true);
    }

    public static void open(CardGroup cards, int amount, boolean autoConfirm) {
        open(cards, amount, amount == 1 ? "Select a card" : "Select " + amount + " cards", autoConfirm, null);
    }

    public static void open(CardGroup cards, int amount, String msg) {
        open(cards, amount, msg, true, null);
    }

    public static void open(CardGroup cards, int amount, String msg, boolean autoConfirm, Consumer<ArrayList<AbstractCard>> onConfirmBehavior) {
        open(cards, amount, msg, autoConfirm, onConfirmBehavior, null);
    }

    public static void open(CardGroup cards, int amount, String msg, boolean autoConfirm, Consumer<ArrayList<AbstractCard>> onConfirmBehavior, Function<ArrayList<AbstractCard>, ArrayList<AbstractCard>> preFilterConfirmedCardsBeforeOnConfirmBehavior) {
        DuelistMod.duelistCardSelectScreen.open(true, cards, amount,  msg, onConfirmBehavior, preFilterConfirmedCardsBeforeOnConfirmBehavior, autoConfirm);
        AbstractDungeon.overlayMenu.cancelButton.show("Cancel");
    }

    public static void openWithNoConfirmButton(CardGroup cards, int amount, String msg, Consumer<ArrayList<AbstractCard>> onConfirmBehavior, Function<ArrayList<AbstractCard>, ArrayList<AbstractCard>> preFilterConfirmedCardsBeforeOnConfirmBehavior) {
        DuelistMod.duelistCardSelectScreen.openWithNoConfirmButton(true, cards, amount,  msg, onConfirmBehavior, preFilterConfirmedCardsBeforeOnConfirmBehavior, true);
        AbstractDungeon.overlayMenu.cancelButton.show("Cancel");
    }

}
