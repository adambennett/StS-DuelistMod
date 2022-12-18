package duelistmod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.FontHelper;

import duelistmod.DuelistMod;
import duelistmod.patches.utils.PatchHelper;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import java.util.ArrayList;

@SpirePatch(clz=AbstractCard.class,method="renderType")
public class AbstractCardRenderTypePatch 
{
    @SpireInsertPatch(localvars={"text"},locator = Locator.class)
    public static void Insert(AbstractCard __instance, SpriteBatch sb, @ByRef String[] text) 
    {
    	if (!DuelistMod.flipCardTags)
    	{
	        boolean isSpell = PatchHelper.isSpell(__instance);
	        boolean isTrap = PatchHelper.isTrap(__instance);
	        boolean isMonster = PatchHelper.isMonster(__instance);
	        boolean isToken = PatchHelper.isToken(__instance);
	        boolean isArchetype = PatchHelper.isArchetype(__instance);
	        boolean isOrbCard = PatchHelper.isOrbCard(__instance);
	        boolean isBooster = PatchHelper.isBooster(__instance);
  
	        if (DuelistMod.monsterTagString.equals(""))
	        {
		        if (isMonster) 			{ text[0] = "Monster"; 	} 
		        else if (isTrap) 		{ text[0] = "Trap";  	} 
		        else if (isSpell) 		{ text[0] = "Spell";    }
		        else if (isToken)		{ text[0] = "Token"; 	}
		        else if (isArchetype)	{ text[0] = "Type";		}
		        else if (isOrbCard) 	{ text[0] = "Orb"; 		}
		        else if (isBooster)		{ text[0] = "Pack";		}
	        }
	        else
	        {
	        	if (isMonster) 			{ text[0] = DuelistMod.monsterTagString; 	} 
		        else if (isTrap) 		{ text[0] = DuelistMod.trapTagString;  		} 
		        else if (isSpell) 		{ text[0] = DuelistMod.spellTagString;    	}
		        else if (isToken)		{ text[0] = DuelistMod.tokenTagString;		}
		        else if (isArchetype)	{ text[0] = DuelistMod.typeTagString;		}
		        else if (isOrbCard) 	{ text[0] = DuelistMod.orbTagString; 		}
		        else if (isBooster)		{ text[0] = "Pack";		}
	        }
    	}
    }

    public static class Locator extends SpireInsertLocator 
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException 
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(FontHelper.class, "renderRotatedText");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}
