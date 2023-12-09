package duelistmod.interfaces;

import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tokens.Token;

public interface NamelessTombCard {

    default DuelistCard getStandardVersion() {
        return new Token();
    }
}
