package duelistmod.dto;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;

public class CardForHashSets {

    private final AbstractCard card;
    private final String id;

    public CardForHashSets(AbstractCard card) {
        this.card = card;
        this.id = card.cardID;
    }

    public AbstractCard card() { return this.card; }

    public static ArrayList<AbstractCard> setToList(HashSet<CardForHashSets> set) {
        return set.stream().map(item -> item.card).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CardForHashSets)) return false;
        CardForHashSets that = (CardForHashSets) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


