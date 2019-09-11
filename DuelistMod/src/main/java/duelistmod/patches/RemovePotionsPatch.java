package duelistmod.patches;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.PotionHelper;

import duelistmod.DuelistMod;
import duelistmod.helpers.Util;

public class RemovePotionsPatch 
{
	@SpirePatch(clz = PotionHelper.class, method = "initialize")
	public static class PotionPatch 
	{
		@SpirePostfixPatch
		public static void Postfix(AbstractPlayer.PlayerClass chosenClass) 
		{
			if (chosenClass == TheDuelistEnum.THE_DUELIST && !DuelistMod.addOrbPotions) 
			{
				for (String s : DuelistMod.orbPotionIDs) { PotionHelper.potions.remove(s); Util.log("Duelist config settings: removing " + s + " from potion pool"); }
			}
		}
	}
	
	@SpirePatch(clz = PotionHelper.class, method = "getPotions")
	public static class PotionAddPatch
	{
		@SpirePostfixPatch
		public static ArrayList<String> Postfix(ArrayList<String> __result, final AbstractPlayer.PlayerClass c, final boolean getAll) 
		{
			if (c == TheDuelistEnum.THE_DUELIST && !getAll) 
			{
				__result.add("BloodPotion");	
				__result.add("GhostInAJar");	
				__result.add("FocusPotion");	
				//__result.add("StancePotion");	
			}
			return __result;
		}
	}
}
