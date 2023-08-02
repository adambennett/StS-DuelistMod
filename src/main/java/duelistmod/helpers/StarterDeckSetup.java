package duelistmod.helpers;

import java.util.ArrayList;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.StarterDeck;
import duelistmod.enums.CardPoolType;
import duelistmod.enums.StartingDeck;
import duelistmod.helpers.poolhelpers.AquaPool;
import duelistmod.helpers.poolhelpers.AscendedOnePool;
import duelistmod.helpers.poolhelpers.AscendedThreePool;
import duelistmod.helpers.poolhelpers.AscendedTwoPool;
import duelistmod.helpers.poolhelpers.BasicPool;
import duelistmod.helpers.poolhelpers.CreatorPool;
import duelistmod.helpers.poolhelpers.DragonPool;
import duelistmod.helpers.poolhelpers.ExodiaPool;
import duelistmod.helpers.poolhelpers.FiendPool;
import duelistmod.helpers.poolhelpers.GiantPool;
import duelistmod.helpers.poolhelpers.GlobalPoolHelper;
import duelistmod.helpers.poolhelpers.IncrementPool;
import duelistmod.helpers.poolhelpers.InsectPool;
import duelistmod.helpers.poolhelpers.MachinePool;
import duelistmod.helpers.poolhelpers.MegatypePool;
import duelistmod.helpers.poolhelpers.NaturiaPool;
import duelistmod.helpers.poolhelpers.OjamaPool;
import duelistmod.helpers.poolhelpers.PharaohPool;
import duelistmod.helpers.poolhelpers.PlantPool;
import duelistmod.helpers.poolhelpers.PredaplantPool;
import duelistmod.helpers.poolhelpers.RandomBigPool;
import duelistmod.helpers.poolhelpers.RandomMetronomePool;
import duelistmod.helpers.poolhelpers.RandomSmallPool;
import duelistmod.helpers.poolhelpers.RandomUpgradePool;
import duelistmod.helpers.poolhelpers.SpellcasterPool;
import duelistmod.helpers.poolhelpers.StandardPool;
import duelistmod.helpers.poolhelpers.ToonPool;
import duelistmod.helpers.poolhelpers.WarriorPool;
import duelistmod.helpers.poolhelpers.ZombiePool;
import duelistmod.patches.AbstractCardEnum;

public class StarterDeckSetup {

	// STARTER DECK METHODS /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void refreshPoolOptions(String deckName) {
		//DuelistMod.archetypeCards.clear();
		DuelistMod.coloredCards.clear();
		DuelistMod.duelColorlessCards.clear();
		initStarterDeckPool(deckName);
	}

	public static void initStarterDeckPool(String deckName) {
		
		// Fill Colorless with Basic Set
		if (DuelistMod.cardPoolType.includesBasic() && DuelistMod.cardPoolType != CardPoolType.BASIC_ONLY) {
			setupColorlessCards(deckName);
		}
		
		// Fill Colored
		switch (DuelistMod.cardPoolType) {
			case BASIC_ONLY:
				StartingDeck.currentDeck.forceSetColoredPool(basicFill(deckName));
				break;
			case DECK_BASIC_1_RANDOM:
			case DECK_1_RANDOM:
				StartingDeck.currentDeck.addToColoredPool(oneRandomDeckFill(deckName));
				break;
			case BASIC_1_RANDOM:
				StartingDeck.currentDeck.forceSetColoredPool(oneRandomDeckFill(deckName));
				break;
			case DECK_BASIC_2_RANDOM:
			case DECK_2_RANDOM:
				StartingDeck.currentDeck.addToColoredPool(twoRandomDeckFill(deckName));
				break;
			case TWO_RANDOM:
				StartingDeck.currentDeck.forceSetColoredPool(twoRandomFill());
				break;
		}
	}

	public static StarterDeck getCurrentDeck() {
		for (StarterDeck d : DuelistMod.starterDeckList) {
			if (d.getIndex() == DuelistMod.deckIndex) {
				return d;
			}
		}
		return DuelistMod.starterDeckList.get(0);
	}

	public static void initStartDeckArrays() {
		ArrayList<CardTags> deckTagList = StarterDeckSetup.getAllDeckTags();
		for (DuelistCard c : DuelistMod.myCards) {
			for (CardTags t : deckTagList) {
				if (c.hasTag(t) && !c.color.equals(AbstractCardEnum.DUELIST_SPECIAL)) {
					StarterDeck ref = DuelistMod.deckTagMap.get(t);
					int copyIndex = StarterDeck.getDeckCopiesMap().get(ref.getDeckTag());
					for (int i = 0; i < c.startCopies.get(copyIndex); i++) {
						ref.getDeck().add((DuelistCard) c.makeCopy());
					}
				}
			}
		}
	}

	private static ArrayList<CardTags> getAllDeckTags() {
		ArrayList<CardTags> deckTagList = new ArrayList<>();
		for (StarterDeck d : DuelistMod.starterDeckList) { deckTagList.add(d.getDeckTag()); }
		return deckTagList;
	}

	public static void setupColorlessCards(String deckName) {
		DuelistMod.duelColorlessCards.clear();
		if (DuelistMod.smallBasicSet) {
			DuelistMod.duelColorlessCards.addAll(BasicPool.smallBasic(deckName));
		} else {
			DuelistMod.duelColorlessCards.addAll(BasicPool.fullBasic(deckName));
		}
	}

	public static ArrayList<AbstractCard> basicFill(String deckName) {
        switch (deckName) {
            case "Aqua Deck":
                return AquaPool.basic();
            case "Ascended I":
				return AscendedOnePool.basic();
            case "Ascended II":
				return AscendedTwoPool.basic();
            case "Ascended III":
				return AscendedThreePool.basic();
            case "Pharaoh I":
            case "Pharaoh II":
            case "Pharaoh III":
            case "Pharaoh IV":
            case "Pharaoh V":
				return PharaohPool.basic();
            case "Creator Deck":
				return CreatorPool.basic();
            case "Dragon Deck":
				return DragonPool.basic();
            case "Exodia Deck":
				return ExodiaPool.basic();
            case "Fiend Deck":
				return FiendPool.basic();
            case "Giant Deck":
				return GiantPool.basic();
            case "Increment Deck":
				return IncrementPool.basic();
            case "Insect Deck":
				return InsectPool.basic();
            case "Machine Deck":
				return MachinePool.basic();
            case "Megatype Deck":
				return MegatypePool.basic();
            case "Naturia Deck":
				return NaturiaPool.basic();
            case "Ojama Deck":
				return OjamaPool.basic();
            case "Plant Deck":
				return PlantPool.basic();
            case "Predaplant Deck":
				return PredaplantPool.basic();
            case "Spellcaster Deck":
				return SpellcasterPool.basic();
            case "Standard Deck":
				return StandardPool.basic();
            case "Toon Deck":
				return ToonPool.basic();
            case "Warrior Deck":
				return WarriorPool.basic();
            case "Zombie Deck":
				return ZombiePool.basic();
            case "Random Deck (Small)":
				return RandomSmallPool.basic();
            case "Random Deck (Big)":
				return RandomBigPool.basic();
            case "Upgrade Deck":
				return RandomUpgradePool.basic();
            case "Metronome Deck":
				return RandomMetronomePool.basic();
        }
		return new ArrayList<>();
	}

	public static ArrayList<AbstractCard> oneRandomFill() {
		return GlobalPoolHelper.oneRandom();
	}

	public static ArrayList<AbstractCard> twoRandomFill() {
        return GlobalPoolHelper.twoRandom();
	}

	public static ArrayList<AbstractCard> oneRandomDeckFill(String deckName) {
		switch (deckName) {
			case "Aqua Deck":
				return AquaPool.oneRandom();
			case "Ascended I":
				return AscendedOnePool.oneRandom();
			case "Ascended II":
				return AscendedTwoPool.oneRandom();
			case "Ascended III":
				return  AscendedThreePool.oneRandom();
			case "Pharaoh I":
			case "Pharaoh II":
			case "Pharaoh III":
			case "Pharaoh IV":
			case "Pharaoh V":
				return PharaohPool.oneRandom();
			case "Creator Deck":
				return CreatorPool.oneRandom();
			case "Dragon Deck":
				return DragonPool.oneRandom();
			case "Exodia Deck":
				return ExodiaPool.oneRandom();
			case "Fiend Deck":
				return FiendPool.oneRandom();
			case "Giant Deck":
				return GiantPool.oneRandom();
			case "Increment Deck":
				return IncrementPool.oneRandom();
			case "Insect Deck":
				return InsectPool.oneRandom();
			case "Machine Deck":
				return MachinePool.oneRandom();
			case "Megatype Deck":
				return MegatypePool.oneRandom();
			case "Naturia Deck":
				return NaturiaPool.oneRandom();
			case "Ojama Deck":
				return OjamaPool.oneRandom();
			case "Plant Deck":
				return PlantPool.oneRandom();
			case "Predaplant Deck":
				return PredaplantPool.oneRandom();
			case "Spellcaster Deck":
				return SpellcasterPool.oneRandom();
			case "Standard Deck":
				return StandardPool.oneRandom();
			case "Toon Deck":
				return ToonPool.oneRandom();
			case "Warrior Deck":
				return WarriorPool.oneRandom();
			case "Zombie Deck":
				return ZombiePool.oneRandom();
			case "Random Deck (Small)":
				return RandomSmallPool.oneRandom();
			case "Random Deck (Big)":
				return RandomBigPool.oneRandom();
			case "Upgrade Deck":
				return RandomUpgradePool.oneRandom();
			case "Metronome Deck":
				return RandomMetronomePool.oneRandom();
		}
		return new ArrayList<>();
	}

	public static ArrayList<AbstractCard> twoRandomDeckFill(String deckName) {
        switch (deckName) {
            case "Aqua Deck":
                return AquaPool.twoRandom();                
            case "Ascended I":
				return AscendedOnePool.twoRandom();                
            case "Ascended II":
				return AscendedTwoPool.twoRandom();                
            case "Ascended III":
				return  AscendedThreePool.twoRandom();                
            case "Pharaoh I":
            case "Pharaoh II":
            case "Pharaoh III":
            case "Pharaoh IV":
            case "Pharaoh V":
				return PharaohPool.twoRandom();                
            case "Creator Deck":
				return CreatorPool.twoRandom();                
            case "Dragon Deck":
				return DragonPool.twoRandom();                
            case "Exodia Deck":
				return ExodiaPool.twoRandom();                
            case "Fiend Deck":
				return FiendPool.twoRandom();                
            case "Giant Deck":
				return GiantPool.twoRandom();                
            case "Increment Deck":
				return IncrementPool.twoRandom();                
            case "Insect Deck":
				return InsectPool.twoRandom();                
            case "Machine Deck":
				return MachinePool.twoRandom();                
            case "Megatype Deck":
				return MegatypePool.twoRandom();                
            case "Naturia Deck":
				return NaturiaPool.twoRandom();                
            case "Ojama Deck":
				return OjamaPool.twoRandom();                
            case "Plant Deck":
				return PlantPool.twoRandom();                
            case "Predaplant Deck":
				return PredaplantPool.twoRandom();                
            case "Spellcaster Deck":
				return SpellcasterPool.twoRandom();                
            case "Standard Deck":
				return StandardPool.twoRandom();                
            case "Toon Deck":
				return ToonPool.twoRandom();                
            case "Warrior Deck":
				return WarriorPool.twoRandom();                
            case "Zombie Deck":
				return ZombiePool.twoRandom();                
            case "Random Deck (Small)":
				return RandomSmallPool.twoRandom();                
            case "Random Deck (Big)":
				return RandomBigPool.twoRandom();                
            case "Upgrade Deck":
				return RandomUpgradePool.twoRandom();                
            case "Metronome Deck":
				return RandomMetronomePool.twoRandom();                
        }
		return new ArrayList<>();
	}

}
