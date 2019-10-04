package duelistmod.patches;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.PotionHelper;

import duelistmod.DuelistMod;
import duelistmod.helpers.*;

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
				String deck = StarterDeckSetup.getCurrentDeck().getSimpleName();
				
				// Naturia/Plant/Insect - Poison Potion
				if (deck.equals("Naturia Deck") || deck.equals("Plant Deck") || deck.equals("Insect Deck")) { __result.add("Poison_Potion"); }
				
				// Spellcaster/Standard - Focus Potion
				if (deck.equals("Spellcaster Deck") || deck.equals("Standard Deck")|| deck.equals("Dragon Deck")|| deck.equals("Plant Deck")|| deck.equals("Fiend Deck")|| deck.equals("Zombie Deck")) { __result.add("FocusPotion"); }
				
				// Toon/Increment/Zombie - Blood Potion
				if (deck.equals("Toon Deck") || deck.equals("Increment Deck") || deck.equals("Zombie Deck")) { __result.add("BloodPotion"); }
				
				// Machine/Dragon/Aqua - Heart of Iron
				//if (deck.equals("Machine Deck") || deck.equals("Dragon Deck") || deck.equals("Aqua Deck")) { __result.add("HeartOfIron"); }
				
				// Standard/Increment/Creator/Exodia/Ojama - Ghost in a Jar
				if (deck.equals("Standard Deck") || deck.equals("Increment Deck") || deck.equals("Creator Deck") || deck.equals("Exodia Deck") || deck.equals("Ojama Deck")) { __result.add("GhostInAJar");	}
				
				// Machine/Dragon/Increment/Standard - Cunning Potion
				//if (deck.equals("Machine Deck") || deck.equals("Dragon Deck") || deck.equals("Increment Deck") || deck.equals("Standard Deck")) { __result.add("CunningPotion"); }	
				
				// Spellcaster/Standard/Fiend/Zombie - Essence of Darkness
				//if (deck.equals("Spellcaster Deck") || deck.equals("Standard Deck") || deck.equals("Fiend Deck") || deck.equals("Zombie Deck")) { __result.add("EssenceOfDarkness"); }
				
				// Warrior - Stance Potion
				//if (deck.equals("Warrior Deck")) { __result.add("StancePotion"); }

				// Warrior/Standard - Ambrosia
				//if (deck.equals("Warrior Deck") || deck.equals("Standard Deck")) { __result.add("Ambrosia"); }

				// Megatype/Ascended/Pharaoh - All Unique Potions, except Stance Potion & Ambrosia
				if (deck.equals("Megatype Deck") || deck.equals("Pharaoh I") || deck.equals("Pharaoh II") || deck.equals("Pharaoh III") || deck.equals("Pharaoh IV") || deck.equals("Pharaoh V") || deck.equals("Ascended I") || deck.equals("Ascended II") || deck.equals("Ascended III"))
				{
					__result.add("Poison_Potion");
					__result.add("FocusPotion");
					__result.add("BloodPotion");
					//__result.add("HeartOfIron"); 
					__result.add("GhostInAJar");
					//__result.add("CunningPotion");
					//__result.add("EssenceOfDarkness");
				}
			}
			return __result;
		}
	}

}
