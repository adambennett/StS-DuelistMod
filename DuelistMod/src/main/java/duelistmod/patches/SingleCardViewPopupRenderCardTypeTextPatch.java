package duelistmod.patches;

import basemod.ReflectionHacks;
import duelistmod.DuelistMod;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import java.util.ArrayList;

@SpirePatch( clz = SingleCardViewPopup.class, method = "renderCardTypeText")
public class SingleCardViewPopupRenderCardTypeTextPatch 
{
    @SpireInsertPatch(localvars = {"label"},locator = Locator.class)
    public static void Insert(SingleCardViewPopup __instance, SpriteBatch sb, @ByRef String[] label) 
    {
    	if (!DuelistMod.flipCardTags)
    	{
	        AbstractCard reflectedCard = (AbstractCard) ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "card");
	        boolean isSpell = DuelistMod.isSpell(reflectedCard);
	        boolean isTrap = DuelistMod.isTrap(reflectedCard);
	        boolean isMonster = DuelistMod.isMonster(reflectedCard);
	        boolean isToken = DuelistMod.isToken(reflectedCard);
	        boolean isArchetype = DuelistMod.isArchetype(reflectedCard);
	        boolean isOrbCard = DuelistMod.isOrbCard(reflectedCard);
	        
	        if (isMonster) 			{ label[0] = "Monster"; 	} 
	        else if (isTrap) 		{ label[0] = "Trap";  		} 
	        else if (isSpell) 		{ label[0] = "Spell";   	}
	        else if (isToken)		{ label[0] = "Token"; 		}
	        else if (isArchetype)	{ label[0] = "Set"; 		}
	        else if (isOrbCard)		{ label[0] = "Orb";			}
    	}
    }

    public static class Locator extends SpireInsertLocator 
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException 
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(FontHelper.class, "renderFontCentered");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}
