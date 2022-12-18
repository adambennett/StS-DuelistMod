package duelistmod.patches;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import basemod.ReflectionHacks;
import duelistmod.DuelistMod;
import duelistmod.patches.utils.PatchHelper;
import javassist.*;

@SpirePatch( clz = SingleCardViewPopup.class, method = "renderCardTypeText")
public class SingleCardViewPopupRenderCardTypeTextPatch 
{
    @SpireInsertPatch(localvars = {"label"},locator = Locator.class)
    public static void Insert(SingleCardViewPopup __instance, SpriteBatch sb, @ByRef String[] label) 
    {
    	if (!DuelistMod.flipCardTags)
    	{
	        AbstractCard reflectedCard = (AbstractCard) ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "card");
	        boolean isSpell = PatchHelper.isSpell(reflectedCard);
	        boolean isTrap = PatchHelper.isTrap(reflectedCard);
	        boolean isMonster = PatchHelper.isMonster(reflectedCard);
	        boolean isToken = PatchHelper.isToken(reflectedCard);
	        boolean isArchetype = PatchHelper.isArchetype(reflectedCard);
	        boolean isOrbCard = PatchHelper.isOrbCard(reflectedCard);
	        boolean isBooster = PatchHelper.isBooster(reflectedCard);
	        
	        if (DuelistMod.monsterTagString.equals(""))
	        {
		        if (isMonster) 			{ label[0] = "Monster"; 	} 
		        else if (isTrap) 		{ label[0] = "Trap";  		} 
		        else if (isSpell) 		{ label[0] = "Spell";   	}
		        else if (isToken)		{ label[0] = "Token"; 		}
		        else if (isArchetype)	{ label[0] = "Type"; 		}
		        else if (isOrbCard)		{ label[0] = "Orb";			}
		        else if (isBooster)		{ label[0] = "Pack";		}
	        }
	        else
	        {
	        	if (isMonster) 			{ label[0] = DuelistMod.monsterTagString; 		} 
		        else if (isTrap) 		{ label[0] = DuelistMod.trapTagString;  		} 
		        else if (isSpell) 		{ label[0] = DuelistMod.spellTagString;   		}
		        else if (isToken)		{ label[0] = DuelistMod.tokenTagString; 		}
		        else if (isArchetype)	{ label[0] = DuelistMod.typeTagString; 			}
		        else if (isOrbCard)		{ label[0] = DuelistMod.orbTagString;			}
		        else if (isBooster)		{ label[0] = "Pack";		}
	        }
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
