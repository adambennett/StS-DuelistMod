package duelistmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class UseCardPatch {

    @SpirePatch(clz= UseCardAction.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {
            AbstractCard.class, AbstractCreature.class
    })
    public static class ConstructorPatch {
        public static SpireReturn<Void> Prefix(UseCardAction __instance, AbstractCard card, AbstractCreature target) {
            if (card == null && target == null) {
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

}
