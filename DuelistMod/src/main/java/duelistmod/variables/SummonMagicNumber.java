package duelistmod.variables;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

import basemod.abstracts.DynamicVariable;
import duelistmod.patches.DuelistCard;

public class SummonMagicNumber extends DynamicVariable {

    //For in-depth comments, check the other variable(DefaultCustomVariable). It's nearly identical.

    @Override
    public String key() {
        return "duelist:SUMM";
        // This is what you put between "!!" in your card strings to actually display the number.
        // You can name this anything (no spaces), but please pre-phase it with your mod name as otherwise mod conflicts can occur.
        // Remember, we're using makeID so it automatically puts "theDefault:" (or, your id) before the name.
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((DuelistCard) card).isSummonsModified;

    }

    @Override
    public int value(AbstractCard card) {
        return ((DuelistCard) card).summons;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((DuelistCard) card).baseSummons;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((DuelistCard) card).upgradedSummons;
    }
    
    @Override
    public Color getIncreasedValueColor()
    {
    	 return Settings.GREEN_TEXT_COLOR;
    }

    @Override
    public Color getDecreasedValueColor()
    {
        return Settings.RED_TEXT_COLOR;
    }
}