package duelistmod.variables;

import com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.abstracts.DynamicVariable;
import duelistmod.abstracts.DuelistCard;

public class EntombNum extends DynamicVariable {

    //For in-depth comments, check the other variable(DefaultCustomVariable). It's nearly identical.

    @Override
    public String key() {
        return "duelist:E";
        // This is what you put between "!!" in your card strings to actually display the number.
        // You can name this anything (no spaces), but please pre-phase it with your mod name as otherwise mod conflicts can occur.
        // Remember, we're using makeID so it automatically puts "theDefault:" (or, your id) before the name.
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return card instanceof DuelistCard && ((DuelistCard)card).isEntombModified;

    }

    @Override
    public int value(AbstractCard card) {
        return card instanceof DuelistCard ? ((DuelistCard) card).entomb : -1;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return card instanceof DuelistCard ? ((DuelistCard) card).baseEntomb : -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return card instanceof DuelistCard && ((DuelistCard) card).upgradedEntomb;
    }
}
