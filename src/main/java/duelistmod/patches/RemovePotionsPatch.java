package duelistmod.patches;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPotion;
import duelistmod.helpers.*;

public class RemovePotionsPatch 
{
	@SpirePatch(clz = PotionHelper.class, method = "initialize")
	public static class PotionPatch 
	{
		@SpirePostfixPatch
		public static void Postfix(AbstractPlayer.PlayerClass chosenClass) 
		{
			if (chosenClass == TheDuelistEnum.THE_DUELIST && !DuelistMod.persistentDuelistData.GameplaySettings.getOrbPotions())
			{
				for (String s : DuelistMod.orbPotionIDs) { PotionHelper.potions.remove(s); Util.log("Duelist config settings: removing " + s + " from potion pool"); }
				for (AbstractPotion pot : DuelistMod.allDuelistPotions)
				{
					if (pot instanceof DuelistPotion)
					{
						DuelistPotion dp = (DuelistPotion)pot;
						if (!dp.canSpawn()) { String s = dp.ID; PotionHelper.potions.remove(s); Util.log("Duelist config settings: removing " + s + " from potion pool"); }
					}
				}
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
				ArrayList<String> newList = new ArrayList<>();
				for (String s : __result)
				{
					if (DuelistMod.duelistPotionMap.get(s) instanceof DuelistPotion)
					{
						DuelistPotion pot = (DuelistPotion) DuelistMod.duelistPotionMap.get(s);
						if (pot.canSpawn()) { newList.add(s); }
						else { Util.log("Duelist config settings: removing " + s + " from potion pool"); } 
					}
					else
					{
						newList.add(s);
					}
				}
				__result = newList;
				
				// Naturia/Plant/Insect - Poison Potion
				if (Util.deckIs("Naturia Deck") || Util.deckIs("Plant Deck") || Util.deckIs("Insect Deck")) { __result.add("Poison_Potion"); }
				
				// Spellcaster/Standard - Focus Potion
				if (Util.deckIs("Spellcaster Deck") || Util.deckIs("Standard Deck")|| Util.deckIs("Dragon Deck")|| Util.deckIs("Plant Deck")|| Util.deckIs("Fiend Deck")|| Util.deckIs("Zombie Deck") || Util.deckIs("Machine Deck")) { __result.add("FocusPotion"); }
				
				// Toon/Beast/Zombie/Increment - Blood Potion
				if (Util.deckIs("Toon Deck") || Util.deckIs("Increment Deck") || Util.deckIs("Beast Deck") || Util.deckIs("Zombie Deck")) { __result.add("BloodPotion"); }
				
				// Machine/Dragon/Aqua - Heart of Iron
				if (Util.deckIs("Machine Deck") || Util.deckIs("Dragon Deck") || Util.deckIs("Aqua Deck")) { __result.add("HeartOfIron"); }
				
				// Standard/Beast/Creator/Exodia/Increment - Ghost in a Jar
				if (Util.deckIs("Standard Deck") || Util.deckIs("Increment Deck") || Util.deckIs("Beast Deck") || Util.deckIs("Creator Deck") || Util.deckIs("Exodia Deck")) { __result.add("GhostInAJar");	}
				
				// Machine/Beast/Standard/Increment - Cunning Potion
				if (Util.deckIs("Machine Deck") || Util.deckIs("Increment Deck") || Util.deckIs("Beast Deck") || Util.deckIs("Standard Deck")) { __result.add("CunningPotion"); }
				
				// Spellcaster/Standard/Fiend/Zombie - Essence of Darkness
				if (Util.deckIs("Spellcaster Deck") || Util.deckIs("Standard Deck") || Util.deckIs("Fiend Deck") || Util.deckIs("Zombie Deck")) { __result.add("EssenceOfDarkness"); }
				
				// Warrior - Stance Potion
				if (Util.deckIs("Warrior Deck")) { __result.add("StancePotion"); }

				// Warrior/Standard - Ambrosia
				if (Util.deckIs("Warrior Deck") || Util.deckIs("Standard Deck")) { __result.add("Ambrosia"); }

				// Megatype/Ascended/Pharaoh - All Unique Potions, except Stance Potion & Ambrosia
				if (Util.deckIs("Megatype Deck") || Util.deckIs("Pharaoh I") || Util.deckIs("Pharaoh II") || Util.deckIs("Pharaoh III") || Util.deckIs("Pharaoh IV") || Util.deckIs("Pharaoh V") || Util.deckIs("Ascended I") || Util.deckIs("Ascended II") || Util.deckIs("Ascended III"))
				{
					__result.add("Poison_Potion");
					__result.add("FocusPotion");
					__result.add("BloodPotion");
					__result.add("HeartOfIron"); 
					__result.add("GhostInAJar");
					__result.add("CunningPotion");
					__result.add("EssenceOfDarkness");
				}
			}
			return __result;
		}
	}
}
