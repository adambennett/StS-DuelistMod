package duelistmod.helpers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import duelistmod.DuelistMod;

import java.util.ArrayList;
import java.util.function.Consumer;

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
        DuelistMod.duelistCardSelectScreen.open(true, cards, amount,  msg, onConfirmBehavior, autoConfirm);
        AbstractDungeon.overlayMenu.cancelButton.show("Cancel");
    }

}
