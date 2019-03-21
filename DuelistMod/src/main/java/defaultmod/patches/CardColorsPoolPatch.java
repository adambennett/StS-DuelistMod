package defaultmod.patches;
import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.abstracts.CustomPlayer;
import defaultmod.DefaultMod;


@SpirePatch(
        clz = CustomPlayer.class,
        method = "getCardPool"
)

public class CardColorsPoolPatch 
{
    @SpireInsertPatch(rloc=0)
    public static void insert(CustomPlayer __instance, @ByRef ArrayList<AbstractCard> tmpPool) 
    {
    	for (AbstractCard c : DefaultMod.coloredCards)
    	{
    		tmpPool.add(c);
    	}
    }
}
