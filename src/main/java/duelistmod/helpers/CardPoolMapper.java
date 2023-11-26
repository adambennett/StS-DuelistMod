package duelistmod.helpers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import duelistmod.interfaces.CardPoolLoader;

import java.util.ArrayList;
import java.util.HashMap;

public class CardPoolMapper {

    private final HashMap<String, Integer> numberOfCopiesByID;
    private final HashMap<AbstractCard, Integer> numberOfCopiesByCard;
    private int totalCards;

    public CardPoolMapper(ArrayList<AbstractCard> preloaded) {
        this.numberOfCopiesByID = new HashMap<>();
        this.numberOfCopiesByCard = new HashMap<>();
        this.totalCards = 0;
        for (AbstractCard card : preloaded) {
            this.numberOfCopiesByID.compute(card.cardID, (k, v) -> v == null ? 1 : v + 1);
            this.numberOfCopiesByCard.compute(card, (k, v) -> v == null ? 1 : v + 1);
            totalCards++;
        }
    }

    public CardPoolMapper(CardPoolLoader loader) {
        this(loader.pool());
    }

    public int getNumberOfCopies(String cardId) {
        return this.numberOfCopiesByID.getOrDefault(cardId, 0);
    }

    public int getNumberOfCopies(AbstractCard card) {
        return this.numberOfCopiesByCard.getOrDefault(card, 0);
    }

    public int size() { return this.totalCards; }
}
