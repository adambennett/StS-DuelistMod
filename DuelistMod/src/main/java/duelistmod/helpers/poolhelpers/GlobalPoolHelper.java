package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.DuelistMod;
import duelistmod.helpers.Util;
import duelistmod.interfaces.RandomDeckInterface;

public class GlobalPoolHelper 
{
	private static ArrayList<AbstractCard> currentSelectionPool;
	private static ArrayList<AbstractCard> secondSelectionPool;
	
	public static ArrayList<AbstractCard> oneRandom()
	{
		return oneRandom(-1, -1, -1, -1);
	}
	
	public static ArrayList<AbstractCard> oneRandom(int exclude)
	{
		return oneRandom(exclude, -1, -1, -1);
	}
	
	public static ArrayList<AbstractCard> oneRandom(int exclude, int excludeB)
	{
		return oneRandom(exclude, excludeB, -1, -1);
	}
	
	public static ArrayList<AbstractCard> oneRandom(int exclude, int excludeB, int excludeC)
	{
		return oneRandom(exclude, excludeB, excludeC, -1);
	}
	
	public static ArrayList<AbstractCard> oneRandom(int exclude, int excludeB, int excludeC, int excludeD)
	{
		ArrayList<RandomDeckInterface> pools = new ArrayList<>();	
		DuelistMod.firstRandomDeck = "";
		DuelistMod.secondRandomDeck = "";
		RandomDeckInterface aqua = 			() -> { currentSelectionPool = AquaPool.deck(); 		Util.log("Selected Aquas as random");		DuelistMod.firstRandomDeck = "Aqua Deck"; };
		RandomDeckInterface dragon = 		() -> { currentSelectionPool = DragonPool.deck(); 		Util.log("Selected Dragons as random");		DuelistMod.firstRandomDeck = "Dragon Deck"; };
		RandomDeckInterface fiend = 		() -> { currentSelectionPool = FiendPool.deck(); 		Util.log("Selected Fiends as random"); 		DuelistMod.firstRandomDeck = "Fiend Deck"; };
		RandomDeckInterface increment = 	() -> { currentSelectionPool = IncrementPool.deck(); 	Util.log("Selected Increment as random");	DuelistMod.firstRandomDeck = "Increment Deck"; };
		RandomDeckInterface insect = 		() -> { currentSelectionPool = InsectPool.deck(); 		Util.log("Selected Insects as random"); 	DuelistMod.firstRandomDeck = "Insect Deck"; };
		RandomDeckInterface machine = 		() -> { currentSelectionPool = MachinePool.deck(); 		Util.log("Selected Machines as random");	DuelistMod.firstRandomDeck = "Machine Deck"; };
		RandomDeckInterface naturia = 		() -> { currentSelectionPool = NaturiaPool.deck(); 		Util.log("Selected Naturias as random");	DuelistMod.firstRandomDeck = "Naturia Deck"; };
		RandomDeckInterface plant = 		() -> { currentSelectionPool = PlantPool.deck(); 		Util.log("Selected Plants as random"); 		DuelistMod.firstRandomDeck = "Plant Deck"; };
		RandomDeckInterface spellcaster = 	() -> { currentSelectionPool = SpellcasterPool.deck(); 	Util.log("Selected Spellcasters as random");DuelistMod.firstRandomDeck = "Spellcaster Deck"; };
		RandomDeckInterface standard = 		() -> { currentSelectionPool = StandardPool.deck(); 	Util.log("Selected Standard as random");	DuelistMod.firstRandomDeck = "Standard Deck"; };
		RandomDeckInterface warrior = 		() -> { currentSelectionPool = WarriorPool.deck(); 		Util.log("Selected Warriors as random");	DuelistMod.firstRandomDeck = "Warrior Deck"; };
		RandomDeckInterface zombie = 		() -> { currentSelectionPool = ZombiePool.deck(); 		Util.log("Selected Zombies as random");		DuelistMod.firstRandomDeck = "Zombie Deck"; };
		RandomDeckInterface rock = 			() -> { currentSelectionPool = RockPool.deck(); 		Util.log("Selected Rocks as random");		DuelistMod.firstRandomDeck = "Rock Pool"; };
		RandomDeckInterface ojama = 		() -> { currentSelectionPool = OjamaPool.deck();  		Util.log("Selected Ojamas as random");		DuelistMod.firstRandomDeck = "Ojama Deck"; };
		RandomDeckInterface toon = 			() -> { currentSelectionPool = ToonPool.deck(); 		Util.log("Selected Toons as random"); 		DuelistMod.firstRandomDeck = "Toon Deck"; };
		RandomDeckInterface dino = 			() -> { currentSelectionPool = DinosaurPool.deck(); 	Util.log("Selected Dinos as random"); 		DuelistMod.firstRandomDeck = "Dinosaur Pool"; };
		RandomDeckInterface arc = 			() -> { currentSelectionPool = ArcanePool.deck(); 		Util.log("Selected Arcane as random"); 		DuelistMod.firstRandomDeck = "Arcane Pool"; };
		pools.add(aqua);		// 0
		pools.add(dragon);		// 1
		pools.add(fiend);		// 2
		pools.add(increment);	// 3
		pools.add(insect);		// 4
		pools.add(machine);		// 5
		pools.add(naturia);		// 6
		pools.add(plant);		// 7
		pools.add(spellcaster); // 8
		pools.add(standard);	// 9
		pools.add(warrior);		// 10
		pools.add(zombie);		// 11
		pools.add(rock);		// 12
		if (!DuelistMod.ojamaBtnBool) { pools.add(ojama); 	}	// 13
		if (!DuelistMod.toonBtnBool)  { pools.add(toon); 	}	// 14
		pools.add(dino);		// 15
		pools.add(arc);			// 16
		if (DuelistMod.archRoll1 == -1 || DuelistMod.archRoll2 == -1 || DuelistMod.archRoll1 > pools.size()) { DuelistMod.archRoll1 = ThreadLocalRandom.current().nextInt(pools.size()); }
		while (DuelistMod.archRoll1 == exclude || DuelistMod.archRoll1 == excludeB || DuelistMod.archRoll1 == excludeC || DuelistMod.archRoll1 == excludeD) { DuelistMod.archRoll1 = ThreadLocalRandom.current().nextInt(pools.size()); }
		pools.get(DuelistMod.archRoll1).getDeck();
		setGlobalDeckFlags();
		return currentSelectionPool;
	}
	
	private static void setGlobalDeckFlags()
	{
		if (DuelistMod.archRoll1 > -1)
		{
			switch (DuelistMod.archRoll1)
			{
				case 0:
					DuelistMod.addedAquaSet = true;
					break;
				case 1:
					DuelistMod.addedDragonSet = true;
					break;
				case 2:
					DuelistMod.addedFiendSet = true;
					break;
				case 3:
					DuelistMod.addedIncrementSet = true;
					break;
				case 4:
					DuelistMod.addedInsectSet = true;
					break;
				case 5:
					DuelistMod.addedMachineSet = true;
					break;
				case 6:
					DuelistMod.addedNaturiaSet = true;
					break;
				case 7:
					DuelistMod.addedPlantSet = true;
					break;
				case 8:
					DuelistMod.addedSpellcasterSet = true;
					break;
				case 9:
					DuelistMod.addedStandardSet = true;
					break;
				case 10:
					DuelistMod.addedWarriorSet = true;
					break;
				case 11:
					DuelistMod.addedZombieSet = true;
					break;
				case 12:
					DuelistMod.addedRockSet = true;
					break;
				case 13:
					DuelistMod.addedOjamaSet = true;
					break;
				case 14:
					DuelistMod.addedToonSet = true;
					break;
				case 15:
					DuelistMod.addedDinoSet = true;
					break;
				case 16:
					DuelistMod.addedArcaneSet = true;
					break;	
				default:
					break;
			}
		}
		
		if (DuelistMod.archRoll2 > -1)
		{
			switch (DuelistMod.archRoll2)
			{
				case 0:
					DuelistMod.addedAquaSet = true;
					break;
				case 1:
					DuelistMod.addedDragonSet = true;
					break;
				case 2:
					DuelistMod.addedFiendSet = true;
					break;
				case 3:
					DuelistMod.addedIncrementSet = true;
					break;
				case 4:
					DuelistMod.addedInsectSet = true;
					break;
				case 5:
					DuelistMod.addedMachineSet = true;
					break;
				case 6:
					DuelistMod.addedNaturiaSet = true;
					break;
				case 7:
					DuelistMod.addedPlantSet = true;
					break;
				case 8:
					DuelistMod.addedSpellcasterSet = true;
					break;
				case 9:
					DuelistMod.addedStandardSet = true;
					break;
				case 10:
					DuelistMod.addedWarriorSet = true;
					break;
				case 11:
					DuelistMod.addedZombieSet = true;
					break;
				case 12:
					DuelistMod.addedRockSet = true;
					break;
				case 13:
					DuelistMod.addedOjamaSet = true;
					break;
				case 14:
					DuelistMod.addedToonSet = true;
					break;
				case 15:
					DuelistMod.addedDinoSet = true;
					break;
				case 16:
					DuelistMod.addedArcaneSet = true;
					break;	
				default:
					break;
			}
		}
	}
	
	public static void resetGlobalDeckFlags()
	{
		DuelistMod.addedAquaSet = false;
		DuelistMod.addedDragonSet = false;
		DuelistMod.addedFiendSet = false;
		DuelistMod.addedIncrementSet = false;
		DuelistMod.addedInsectSet = false;
		DuelistMod.addedMachineSet = false;
		DuelistMod.addedNaturiaSet = false;
		DuelistMod.addedPlantSet = false;
		DuelistMod.addedSpellcasterSet = false;
		DuelistMod.addedStandardSet = false;
		DuelistMod.addedWarriorSet = false;
		DuelistMod.addedZombieSet = false;
		DuelistMod.addedRockSet = false;
		DuelistMod.addedOjamaSet = false;
		DuelistMod.addedToonSet = false;
		DuelistMod.addedDinoSet = false;
		DuelistMod.addedArcaneSet = false;
	}
	
	public static boolean addedAnyDecks()
	{
		ArrayList<Boolean> flags = new ArrayList<>();
		flags.add(DuelistMod.addedAquaSet);
		flags.add(DuelistMod.addedDragonSet);
		flags.add(DuelistMod.addedFiendSet);
		flags.add(DuelistMod.addedIncrementSet);
		flags.add(DuelistMod.addedInsectSet);
		flags.add(DuelistMod.addedMachineSet);
		flags.add(DuelistMod.addedNaturiaSet);
		flags.add(DuelistMod.addedPlantSet);
		flags.add(DuelistMod.addedSpellcasterSet);
		flags.add(DuelistMod.addedStandardSet);
		flags.add(DuelistMod.addedWarriorSet);
		flags.add(DuelistMod.addedZombieSet);
		flags.add(DuelistMod.addedRockSet);
		flags.add(DuelistMod.addedOjamaSet);
		flags.add(DuelistMod.addedToonSet);
		flags.add(DuelistMod.addedDinoSet);
		flags.add(DuelistMod.addedArcaneSet);
		int counter = 0;
		for (Boolean b : flags)
		{
			if (b == true) { counter++; }
			if (counter > 1) { break; }
		}
		
		if (counter > 1) { return true; }
		else if (counter > 0 && (DuelistMod.setIndex == 2 || DuelistMod.setIndex == 5 || DuelistMod.setIndex == 7)) { return true; }
		else { return false; }
	}
	
	public static void setGlobalDeckFlags(String deckName)
	{
		resetGlobalDeckFlags();
		switch (deckName)
		{
			case "Aqua Deck":
				DuelistMod.addedAquaSet = true;
				break;
			case "Dragon Deck":
				DuelistMod.addedDragonSet = true;
				break;
			case "Fiend Deck":
				DuelistMod.addedFiendSet = true;
				break;
			case "Increment Deck":
				DuelistMod.addedIncrementSet = true;
				break;
			case "Insect Deck":
				DuelistMod.addedInsectSet = true;
				break;
			case "Machine Deck":
				DuelistMod.addedMachineSet = true;
				break;
			case "Naturia Deck":
				DuelistMod.addedNaturiaSet = true;
				break;
			case "Plant Deck":
				DuelistMod.addedPlantSet = true;
				break;
			case "Spellcaster Deck":
				DuelistMod.addedSpellcasterSet = true;
				break;
			case "Standard Deck":
				DuelistMod.addedStandardSet = true;
				break;
			case "Warrior Deck":
				DuelistMod.addedWarriorSet = true;
				break;
			case "Zombie Deck":
				DuelistMod.addedZombieSet = true;
				break;
			case "Ojama Deck":
				DuelistMod.addedOjamaSet = true;
				break;
			case "Toon Deck":
				DuelistMod.addedToonSet = true;
				break;		
			default:
				break;
		}
	}
	
	public static ArrayList<AbstractCard> twoRandom()
	{
		return twoRandom(-1, -1, -1, -1);
	}
	
	public static ArrayList<AbstractCard> twoRandom(int exclude)
	{
		return twoRandom(exclude, -1, -1, -1);
	}
	
	public static ArrayList<AbstractCard> twoRandom(int exclude, int excludeB)
	{
		return twoRandom(exclude, excludeB, -1, -1);
	}
	
	public static ArrayList<AbstractCard> twoRandom(int exclude, int excludeB, int excludeC)
	{
		return twoRandom(exclude, excludeB, excludeC, -1);
	}
	
	private static void setDeckString(String set)
	{
		if (DuelistMod.firstRandomDeck.equals("")) { DuelistMod.firstRandomDeck = set; }
		else { DuelistMod.secondRandomDeck = set; }
	}
	
	private static ArrayList<AbstractCard> getListLoc()
	{
		if (DuelistMod.firstRandomDeck.equals("")) { return currentSelectionPool; }
		else { return secondSelectionPool; }
	}

	public static ArrayList<AbstractCard> twoRandom(int exclude, int excludeB, int excludeC, int excludeD)
	{
		ArrayList<RandomDeckInterface> pools = new ArrayList<>();	
		DuelistMod.firstRandomDeck = "";
		DuelistMod.secondRandomDeck = "";
		currentSelectionPool = new ArrayList<>();
		secondSelectionPool = new ArrayList<>();
		RandomDeckInterface aqua = 			() -> {  getListLoc().addAll(AquaPool.deck()); 			Util.log("Selected Aquas as random");		setDeckString("Aqua Deck"); 	};
		RandomDeckInterface dragon = 		() -> {  getListLoc().addAll(DragonPool.deck()); 		Util.log("Selected Dragons as random");		setDeckString("Dragon Deck"); 	};
		RandomDeckInterface fiend = 		() -> {  getListLoc().addAll(FiendPool.deck()); 		Util.log("Selected Fiends as random");		setDeckString("Fiend Deck"); 	};
		RandomDeckInterface increment = 	() -> {  getListLoc().addAll(IncrementPool.deck()); 	Util.log("Selected Increment as random");	setDeckString("Increment Deck");};
		RandomDeckInterface insect = 		() -> {  getListLoc().addAll(InsectPool.deck()); 		Util.log("Selected Insects as random"); 	setDeckString("Insect Deck"); 	};
		RandomDeckInterface machine = 		() -> {  getListLoc().addAll(MachinePool.deck()); 		Util.log("Selected Machines as random");	setDeckString("Machine Deck"); 	};
		RandomDeckInterface naturia = 		() -> {  getListLoc().addAll(NaturiaPool.deck()); 		Util.log("Selected Naturias as random"); 	setDeckString("Naturia Deck"); 	};
		RandomDeckInterface plant = 		() -> {  getListLoc().addAll(PlantPool.deck()); 		Util.log("Selected Plants as random");		setDeckString("Plant Deck"); 	};
		RandomDeckInterface spellcaster = 	() -> {  getListLoc().addAll(SpellcasterPool.deck()); 	Util.log("Selected Spellcasters as random");setDeckString("Spellcaster Deck"); };
		RandomDeckInterface standard = 		() -> {  getListLoc().addAll(StandardPool.deck()); 		Util.log("Selected Standard as random");	setDeckString("Standard Deck"); };
		RandomDeckInterface warrior = 		() -> {  getListLoc().addAll(WarriorPool.deck()); 		Util.log("Selected Warriors as random");	setDeckString("Warrior Deck"); 	};
		RandomDeckInterface zombie = 		() -> {  getListLoc().addAll(ZombiePool.deck()); 		Util.log("Selected Zombies as random");		setDeckString("Zombie Deck"); 	};
		RandomDeckInterface rock = 			() -> {  getListLoc().addAll(RockPool.deck()); 			Util.log("Selected Rocks as random");		setDeckString("Rock Pool"); 	};
		RandomDeckInterface ojama = 		() -> {  getListLoc().addAll(OjamaPool.deck());  		Util.log("Selected Ojamas as random");		setDeckString("Ojama Deck"); 	}; 
		RandomDeckInterface toon = 			() -> {  getListLoc().addAll(ToonPool.deck()); 			Util.log("Selected Toons as random");		setDeckString("Toon Deck"); 	};
		RandomDeckInterface dino = 			() -> {  getListLoc().addAll(DinosaurPool.deck()); 		Util.log("Selected Dinos as random");		setDeckString("Dinosaur Pool"); };
		RandomDeckInterface arc = 			() -> { currentSelectionPool = ArcanePool.deck(); 		Util.log("Selected Arcane as random"); 		DuelistMod.firstRandomDeck = "Arcane Pool"; };
		pools.add(aqua);
		pools.add(dragon);
		pools.add(fiend);
		pools.add(increment);
		pools.add(insect);
		pools.add(machine);
		pools.add(naturia);
		pools.add(plant);
		pools.add(spellcaster);
		pools.add(standard);
		pools.add(warrior);
		pools.add(zombie);
		pools.add(rock);		
		if (!DuelistMod.ojamaBtnBool) { pools.add(ojama); 	}
		if (!DuelistMod.toonBtnBool)  { pools.add(toon); 	}
		pools.add(dino);
		pools.add(arc);
		if (DuelistMod.archRoll1 == -1 || DuelistMod.archRoll2 == -1 || DuelistMod.archRoll1 > pools.size() || DuelistMod.archRoll2 > pools.size())
		{
			DuelistMod.archRoll1 = ThreadLocalRandom.current().nextInt(pools.size());
			DuelistMod.archRoll2 = ThreadLocalRandom.current().nextInt(pools.size());
		}
		while (DuelistMod.archRoll1 == exclude || DuelistMod.archRoll1 == excludeB || DuelistMod.archRoll1 == excludeC || DuelistMod.archRoll1 == excludeD) { DuelistMod.archRoll1 = ThreadLocalRandom.current().nextInt(pools.size()); }
		while (DuelistMod.archRoll1 == DuelistMod.archRoll2 || DuelistMod.archRoll2 == exclude || DuelistMod.archRoll2 == excludeB || DuelistMod.archRoll2 == excludeC || DuelistMod.archRoll2 == excludeD) { DuelistMod.archRoll2 = ThreadLocalRandom.current().nextInt(pools.size()); }
		pools.get(DuelistMod.archRoll1).getDeck();
		pools.get(DuelistMod.archRoll2).getDeck();
		ArrayList<AbstractCard> finalPool = new ArrayList<>();
		finalPool.addAll(currentSelectionPool);
		finalPool.addAll(secondSelectionPool);
		setGlobalDeckFlags();
		return finalPool;
	}
}
