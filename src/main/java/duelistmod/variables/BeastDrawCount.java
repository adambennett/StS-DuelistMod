package duelistmod.variables;

import basemod.ReflectionHacks;
import basemod.abstracts.DynamicVariable;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.DynamicTextBlocks;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import duelistmod.DuelistMod;
import duelistmod.cards.pools.beast.ThousandNeedles;
import duelistmod.characters.TheDuelist;
import duelistmod.dto.AnyDuelist;

import java.util.List;

public class BeastDrawCount extends DynamicVariable {

    @Override
    public String key() {
        return "theDuelist:BeastDrawCountV";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        return countBeasts(card);
    }

    @Override
    public int baseValue(AbstractCard card) {
        return countBeasts(card);
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return false;
    }

    @Override
    public Color getIncreasedValueColor() {
        return Settings.GREEN_TEXT_COLOR;
    }

    @Override
    public Color getDecreasedValueColor()
    {
        return Settings.RED_TEXT_COLOR;
    }

    @Override
    public Color getUpgradedColor() {
        return Settings.GREEN_TEXT_COLOR;
    }

    public static int customCheck(AbstractCard card) {
        AnyDuelist duelist = AnyDuelist.from(card);
        boolean inCombat = AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
        boolean inDeck = inCombat && duelist.masterDeck().contains(card);
        CardGroup singleCardViewPopup = null;
        try {
            singleCardViewPopup = ReflectionHacks.getPrivate(CardCrawlGame.cardPopup, SingleCardViewPopup.class, "group");
        } catch (Exception ignored) {}
        boolean inPopupView = CardCrawlGame.dungeon != null && AbstractDungeon.player != null && (singleCardViewPopup == null || !singleCardViewPopup.contains(card)) && !duelist.masterDeck().contains(card) && !duelist.hand().contains(card) && !duelist.drawPile().contains(card) && !duelist.discardPile().contains(card) && !TheDuelist.cardPool.contains(card) && !AbstractDungeon.colorlessCardPool.contains(card) && !duelist.exhaustPile().contains(card) && !duelist.resummonPile().contains(card) && !DynamicTextBlocks.ExhaustViewFixField.exhaustViewCopy.get(card);
        int magic = card instanceof ThousandNeedles ? ((ThousandNeedles) card).secondMagic : card.magicNumber;
        int errorCode = magic == 1 ? -1 : -2;
        return inCombat && !inDeck && !inPopupView
                ? countBeasts(card)
                : errorCode;
    }

    public static int countBeasts(AbstractCard card) {
        AnyDuelist duelist = AnyDuelist.from(card);
        List<Integer> beastDrawsByTurn = duelist.player() ? DuelistMod.beastsDrawnByTurn : duelist.getEnemy() != null ? DuelistMod.enemyBeastsDrawnByTurn : null;
        int sumOfBeasts = 0;
        int magic = card instanceof ThousandNeedles ? ((ThousandNeedles) card).secondMagic : card.magicNumber;
        if (beastDrawsByTurn != null) {
            int counter = 0;
            for (int i = beastDrawsByTurn.size() - 1; i > -1 && counter < magic; i--, counter++) {
                sumOfBeasts += beastDrawsByTurn.get(i);
            }
        }
        return sumOfBeasts;
    }
}
