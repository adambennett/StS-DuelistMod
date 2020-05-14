package duelistmod.patches;

import java.util.*;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.helpers.GameTips;

import basemod.ReflectionHacks;
import duelistmod.helpers.DuelistTipHelper;

@SpirePatch(clz = GameTips.class, method = "initialize")
public class CustomTipsPatch
{
    @SuppressWarnings("unchecked")
	public static void Postfix(GameTips __instance)
    {
    	ArrayList<String> privTips = (ArrayList<String>) ReflectionHacks.getPrivate(__instance, GameTips.class, "tips");
    	//privTips.clear();
    	Collections.addAll(privTips, DuelistTipHelper.tutorialStrings.TEXT);
    	Collections.shuffle(privTips);
    }
}
