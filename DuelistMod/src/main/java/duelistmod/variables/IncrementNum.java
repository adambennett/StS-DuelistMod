package duelistmod.variables;

import com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.abstracts.DynamicVariable;
import duelistmod.abstracts.DuelistCard;

public class IncrementNum extends DynamicVariable {

    //For in-depth comments, check the other variable(DefaultCustomVariable). It's nearly identical.

    @Override
    public String key() {
        return "duelist:I";
        // This is what you put between "!!" in your card strings to actually display the number.
        // You can name this anything (no spaces), but please pre-phase it with your mod name as otherwise mod conflicts can occur.
        // Remember, we're using makeID so it automatically puts "theDefault:" (or, your id) before the name.
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((DuelistCard)card).isIncModified;

    }

    @Override
    public int value(AbstractCard card) {
        return ((DuelistCard) card).incrementMagic;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((DuelistCard) card).baseIncrementMagic;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((DuelistCard) card).upgradedIncrement;
    }
}