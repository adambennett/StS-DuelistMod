package duelistmod.variables;

import basemod.abstracts.DynamicVariable;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import duelistmod.abstracts.DynamicDamageCard;

public class OriginalDamageNum extends DynamicVariable {

    @Override
    public String key() {
        return "duelist:OD";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return false;

    }

    @Override
    public int value(AbstractCard card) {
        return card instanceof DynamicDamageCard ? ((DynamicDamageCard) card).originalDamage : 0;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return card instanceof DynamicDamageCard ? ((DynamicDamageCard) card).originalDamage : 0;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return false;
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
