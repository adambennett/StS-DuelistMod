package defaultmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class AbstractCardEnum {

    @SpireEnum
    public static AbstractCard.CardColor DUELIST_MONSTERS;
    
    @SpireEnum
    public static AbstractCard.CardColor DUELIST_SPELLS;
    
    @SpireEnum
    public static AbstractCard.CardColor DUELIST_TRAPS;
}