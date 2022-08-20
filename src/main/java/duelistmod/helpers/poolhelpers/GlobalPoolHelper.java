package duelistmod.helpers.poolhelpers;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.pools.aqua.*;
import duelistmod.cards.pools.zombies.*;
import duelistmod.characters.TheDuelist;
import duelistmod.helpers.*;
import duelistmod.interfaces.*;
import duelistmod.relics.CardPoolRelic;
import duelistmod.variables.*;

public class GlobalPoolHelper 
{
	private static ArrayList<AbstractCard> currentSelectionPool;
	private static ArrayList<AbstractCard> secondSelectionPool;
	private static int index = -1;

	private static Map<String, AbstractCard> aquaPool = new HashMap<>();
	private static Map<String, AbstractCard> arcanePool = new HashMap<>();
	private static Map<String, AbstractCard> ascendedOnePool = new HashMap<>();
	private static Map<String, AbstractCard> ascendedTwoPool = new HashMap<>();
	private static Map<String, AbstractCard> ascendedThreePool = new HashMap<>();
	private static Map<String, AbstractCard> creatorPool = new HashMap<>();
	private static Map<String, AbstractCard> dinosaurPool = new HashMap<>();
	private static Map<String, AbstractCard> dragonPool = new HashMap<>();
	private static Map<String, AbstractCard> exodiaPool = new HashMap<>();
	private static Map<String, AbstractCard> fiendPool = new HashMap<>();
	private static Map<String, AbstractCard> incrementPool = new HashMap<>();
	private static Map<String, AbstractCard> insectPool = new HashMap<>();
	private static Map<String, AbstractCard> machinePool = new HashMap<>();
	private static Map<String, AbstractCard> megatypePool = new HashMap<>();
	private static Map<String, AbstractCard> naturiaPool = new HashMap<>();
	private static Map<String, AbstractCard> ojamaPool = new HashMap<>();
	private static Map<String, AbstractCard> pharaohOnePool = new HashMap<>();
	private static Map<String, AbstractCard> pharaohTwoPool = new HashMap<>();
	private static Map<String, AbstractCard> pharaohThreePool = new HashMap<>();
	private static Map<String, AbstractCard> pharaohFourPool = new HashMap<>();
	private static Map<String, AbstractCard> pharaohFivePool = new HashMap<>();
	private static Map<String, AbstractCard> plantPool = new HashMap<>();
	private static Map<String, AbstractCard> rockPool = new HashMap<>();
	private static Map<String, AbstractCard> spellcasterPool = new HashMap<>();
	private static Map<String, AbstractCard> standardPool = new HashMap<>();
	private static Map<String, AbstractCard> toonPool = new HashMap<>();
	private static Map<String, AbstractCard> warriorPool = new HashMap<>();
	private static Map<String, AbstractCard> zombiePool = new HashMap<>();
	private static Map<String, AbstractCard> namelessPool = new HashMap<>();
	
	
	private static Map<String, AbstractCard> aquaBasicPool = new HashMap<>();
	private static Map<String, AbstractCard> ascendedOneBasicPool = new HashMap<>();
	private static Map<String, AbstractCard> ascendedTwoBasicPool = new HashMap<>();
	private static Map<String, AbstractCard> ascendedThreeBasicPool = new HashMap<>();
	private static Map<String, AbstractCard> creatorBasicPool = new HashMap<>();
	private static Map<String, AbstractCard> dragonBasicPool = new HashMap<>();
	private static Map<String, AbstractCard> exodiaBasicPool = new HashMap<>();
	private static Map<String, AbstractCard> fiendBasicPool = new HashMap<>();
	private static Map<String, AbstractCard> incrementBasicPool = new HashMap<>();
	private static Map<String, AbstractCard> insectBasicPool = new HashMap<>();
	private static Map<String, AbstractCard> machineBasicPool = new HashMap<>();
	private static Map<String, AbstractCard> megatypeBasicPool = new HashMap<>();
	private static Map<String, AbstractCard> naturiaBasicPool = new HashMap<>();
	private static Map<String, AbstractCard> ojamaBasicPool = new HashMap<>();
	private static Map<String, AbstractCard> pharaohOneBasicPool = new HashMap<>();
	private static Map<String, AbstractCard> pharaohTwoBasicPool = new HashMap<>();
	private static Map<String, AbstractCard> pharaohThreeBasicPool = new HashMap<>();
	private static Map<String, AbstractCard> pharaohFourBasicPool = new HashMap<>();
	private static Map<String, AbstractCard> pharaohFiveBasicPool = new HashMap<>();
	private static Map<String, AbstractCard> plantBasicPool = new HashMap<>();
	private static Map<String, AbstractCard> spellcasterBasicPool = new HashMap<>();
	private static Map<String, AbstractCard> standardBasicPool = new HashMap<>();
	private static Map<String, AbstractCard> toonBasicPool = new HashMap<>();
	private static Map<String, AbstractCard> warriorBasicPool = new HashMap<>();
	private static Map<String, AbstractCard> zombieBasicPool = new HashMap<>();
	
	public static void setupAppearanceMaps() {
		aquaPool.clear();
		arcanePool.clear();
		ascendedOnePool.clear();
		ascendedTwoPool.clear();
		ascendedThreePool.clear();
		creatorPool.clear();
		dinosaurPool.clear();
		dragonPool.clear();
		exodiaPool.clear();
		fiendPool.clear();
		incrementPool.clear();
		insectPool.clear();
		machinePool.clear();
		megatypePool.clear();
		naturiaPool.clear();
		ojamaPool.clear();
		pharaohOnePool.clear();
		pharaohTwoPool.clear();
		pharaohThreePool.clear();
		pharaohFourPool.clear();
		pharaohFivePool.clear();
		plantPool.clear();
		rockPool.clear();
		spellcasterPool.clear();
		standardPool.clear();
		toonPool.clear();
		warriorPool.clear();
		zombiePool.clear();
		namelessPool.clear();
		
		aquaBasicPool.clear();
		ascendedOneBasicPool.clear();
		ascendedTwoBasicPool.clear();
		ascendedThreeBasicPool.clear();
		creatorBasicPool.clear();
		dragonBasicPool.clear();
		exodiaBasicPool.clear();
		fiendBasicPool.clear();
		incrementBasicPool.clear();
		insectBasicPool.clear();
		machineBasicPool.clear();
		megatypeBasicPool.clear();
		naturiaBasicPool.clear();
		ojamaBasicPool.clear();
		pharaohOneBasicPool.clear();
		pharaohTwoBasicPool.clear();
		pharaohThreeBasicPool.clear();
		pharaohFourBasicPool.clear();
		pharaohFiveBasicPool.clear();
		plantBasicPool.clear();
		spellcasterBasicPool.clear();
		standardBasicPool.clear();
		toonBasicPool.clear();
		warriorBasicPool.clear();
		zombieBasicPool.clear();
		
		for (AbstractCard c : AquaPool.deck()) { aquaPool.put(c.cardID, c); }
		for (AbstractCard c : ArcanePool.deck()) { arcanePool.put(c.cardID, c); }
		for (AbstractCard c : AscendedOnePool.deck()) { ascendedOnePool.put(c.cardID, c); }
		for (AbstractCard c : AscendedTwoPool.deck()) { ascendedTwoPool.put(c.cardID, c); }
		for (AbstractCard c : AscendedThreePool.deck()) { ascendedThreePool.put(c.cardID, c); }
		for (AbstractCard c : CreatorPool.deck()) { creatorPool.put(c.cardID, c); }
		for (AbstractCard c : DinosaurPool.deck()) { dinosaurPool.put(c.cardID, c); }
		for (AbstractCard c : DragonPool.deck()) { dragonPool.put(c.cardID, c); }
		for (AbstractCard c : ExodiaPool.deck()) { exodiaPool.put(c.cardID, c); }
		for (AbstractCard c : FiendPool.deck()) { fiendPool.put(c.cardID, c); }
		for (AbstractCard c : IncrementPool.deck()) { incrementPool.put(c.cardID, c); }
		for (AbstractCard c : InsectPool.deck()) { insectPool.put(c.cardID, c); }
		for (AbstractCard c : MachinePool.deck()) { machinePool.put(c.cardID, c); }
		for (AbstractCard c : MegatypePool.deck()) { megatypePool.put(c.cardID, c); }
		for (AbstractCard c : NaturiaPool.deck()) { naturiaPool.put(c.cardID, c); }
		for (AbstractCard c : OjamaPool.deck()) { ojamaPool.put(c.cardID, c); }
		for (AbstractCard c : PharaohPool.deck(1)) { pharaohOnePool.put(c.cardID, c); }
		for (AbstractCard c : PharaohPool.deck(2)) { pharaohTwoPool.put(c.cardID, c); }
		for (AbstractCard c : PharaohPool.deck(3)) { pharaohThreePool.put(c.cardID, c); }
		for (AbstractCard c : PharaohPool.deck(4)) { pharaohFourPool.put(c.cardID, c); }
		for (AbstractCard c : PharaohPool.deck(5)) { pharaohFivePool.put(c.cardID, c); }
		for (AbstractCard c : PlantPool.deck()) { plantPool.put(c.cardID, c); }
		for (AbstractCard c : RockPool.deck()) { rockPool.put(c.cardID, c); }
		for (AbstractCard c : SpellcasterPool.deck()) { spellcasterPool.put(c.cardID, c); }
		for (AbstractCard c : StandardPool.deck()) { standardPool.put(c.cardID, c); }
		for (AbstractCard c : ToonPool.deck()) { toonPool.put(c.cardID, c); }
		for (AbstractCard c : WarriorPool.deck()) { warriorPool.put(c.cardID, c); }
		for (AbstractCard c : ZombiePool.deck()) { zombiePool.put(c.cardID, c); }
		for (AbstractCard c : DuelistMod.myNamelessCards) { namelessPool.put(c.cardID, c); }
		
		for (AbstractCard c : AquaPool.basic()) { aquaBasicPool.put(c.cardID, c); }
		for (AbstractCard c : AscendedOnePool.basic()) { ascendedOneBasicPool.put(c.cardID, c); }
		for (AbstractCard c : AscendedTwoPool.basic()) { ascendedTwoBasicPool.put(c.cardID, c); }
		for (AbstractCard c : AscendedThreePool.basic()) { ascendedThreeBasicPool.put(c.cardID, c); }
		for (AbstractCard c : CreatorPool.basic()) { creatorBasicPool.put(c.cardID, c); }
		for (AbstractCard c : DragonPool.basic()) { dragonBasicPool.put(c.cardID, c); }
		for (AbstractCard c : ExodiaPool.basic()) { exodiaBasicPool.put(c.cardID, c); }
		for (AbstractCard c : FiendPool.basic()) { fiendBasicPool.put(c.cardID, c); }
		for (AbstractCard c : IncrementPool.basic()) { incrementBasicPool.put(c.cardID, c); }
		for (AbstractCard c : InsectPool.basic()) { insectBasicPool.put(c.cardID, c); }
		for (AbstractCard c : MachinePool.basic()) { machineBasicPool.put(c.cardID, c); }
		for (AbstractCard c : MegatypePool.basic()) { megatypeBasicPool.put(c.cardID, c); }
		for (AbstractCard c : NaturiaPool.basic()) { naturiaBasicPool.put(c.cardID, c); }
		for (AbstractCard c : OjamaPool.basic()) { ojamaBasicPool.put(c.cardID, c); }
		for (AbstractCard c : PharaohPool.basic()) { pharaohOneBasicPool.put(c.cardID, c); }
		for (AbstractCard c : PharaohPool.basic()) { pharaohTwoBasicPool.put(c.cardID, c); }
		for (AbstractCard c : PharaohPool.basic()) { pharaohThreeBasicPool.put(c.cardID, c); }
		for (AbstractCard c : PharaohPool.basic()) { pharaohFourBasicPool.put(c.cardID, c); }
		for (AbstractCard c : PharaohPool.basic()) { pharaohFiveBasicPool.put(c.cardID, c); }
		for (AbstractCard c : PlantPool.basic()) { plantBasicPool.put(c.cardID, c); }
		for (AbstractCard c : SpellcasterPool.basic()) { spellcasterBasicPool.put(c.cardID, c); }
		for (AbstractCard c : StandardPool.basic()) { standardBasicPool.put(c.cardID, c); }
		for (AbstractCard c : ToonPool.basic()) { toonBasicPool.put(c.cardID, c); }
		for (AbstractCard c : WarriorPool.basic()) { warriorBasicPool.put(c.cardID, c); }
		for (AbstractCard c : ZombiePool.basic()) { zombieBasicPool.put(c.cardID, c); }
	}

	public static List<String> getRelicAppearancePool(String relic) {
		Map<String, List<String>> relics = DuelistMod.relicAndPotionByDeckData.get("Relics");
		Set<String> normalPools = new HashSet<>();
		for (Map.Entry<String, List<String>> entry : relics.entrySet()) {
			if (entry.getValue().contains(relic)) {
				normalPools.add(entry.getKey());
			}
		}
		return new ArrayList<>(normalPools);
	}

	public static List<String> getPotionAppearancePool(String relic) {
		Map<String, List<String>> potions = DuelistMod.relicAndPotionByDeckData.get("Potions");
		Set<String> normalPools = new HashSet<>();
		for (Map.Entry<String, List<String>> entry : potions.entrySet()) {
			if (entry.getValue().contains(relic)) {
				normalPools.add(entry.getKey());
			}
		}
		return new ArrayList<>(normalPools);
	}
	
	public static List<String> getAppearancePools(DuelistCard checkCard) {
		List<String> output = new ArrayList<>();
		boolean inOnePool = false;

		if (checkCard.hasTag(Tags.STANDARD_DECK)) {
			output.add("Standard Deck"); inOnePool = true;
		}
		if (checkCard.hasTag(Tags.DRAGON_DECK)) {
			output.add("Dragon Deck"); inOnePool = true;
		}
		if (checkCard.hasTag(Tags.NATURIA_DECK)) {
			output.add("Naturia Deck"); inOnePool = true;
		}
		if (checkCard.hasTag(Tags.SPELLCASTER_DECK)) {
			output.add("Spellcaster Deck"); inOnePool = true;
		}
		if (checkCard.hasTag(Tags.TOON_DECK)) {
			output.add("Toon Deck"); inOnePool = true;
		}
		if (checkCard.hasTag(Tags.ZOMBIE_DECK)) {
			output.add("Zombie Deck"); inOnePool = true;
		}
		if (checkCard.hasTag(Tags.AQUA_DECK)) {
			output.add("Aqua Deck"); inOnePool = true;
		}
		if (checkCard.hasTag(Tags.FIEND_DECK)) {
			output.add("Fiend Deck"); inOnePool = true;
		}
		if (checkCard.hasTag(Tags.MACHINE_DECK)) {
			output.add("Machine Deck"); inOnePool = true;
		}
		if (checkCard.hasTag(Tags.WARRIOR_DECK)) {
			output.add("Warrior Deck"); inOnePool = true;
		}
		if (checkCard.hasTag(Tags.INSECT_DECK)) {
			output.add("Insect Deck"); inOnePool = true;
		}
		if (checkCard.hasTag(Tags.PLANT_DECK)) {
			output.add("Plant Deck"); inOnePool = true;
		}
		if (checkCard.hasTag(Tags.MEGATYPE_DECK)) {
			output.add("Megatype Deck"); inOnePool = true;
		}
		if (checkCard.hasTag(Tags.INCREMENT_DECK)) {
			output.add("Increment Deck"); inOnePool = true;
		}
		if (checkCard.hasTag(Tags.CREATOR_DECK)) {
			output.add("Creator Deck"); inOnePool = true;
		}
		if (checkCard.hasTag(Tags.OJAMA_DECK)) {
			output.add("Ojama Deck"); inOnePool = true;
		}
		if (checkCard.hasTag(Tags.EXODIA_DECK)) {
			output.add("Exodia Deck"); inOnePool = true;
		}
		if (checkCard.hasTag(Tags.ASCENDED_ONE_DECK)) {
			output.add("Ascended I Deck"); inOnePool = true;
		}
		if (checkCard.hasTag(Tags.ASCENDED_TWO_DECK)) {
			output.add("Ascended II Deck"); inOnePool = true;
		}
		if (checkCard.hasTag(Tags.ASCENDED_THREE_DECK)) {
			output.add("Ascended III Deck"); inOnePool = true;
		}
		if (checkCard.hasTag(Tags.PHARAOH_ONE_DECK)) {
			output.add("Pharaoh I Deck"); inOnePool = true;
		}
		if (checkCard.hasTag(Tags.PHARAOH_TWO_DECK)) {
			output.add("Pharaoh II Deck"); inOnePool = true;
		}
		if (checkCard.hasTag(Tags.PHARAOH_THREE_DECK)) {
			output.add("Pharaoh III Deck"); inOnePool = true;
		}
		if (checkCard.hasTag(Tags.PHARAOH_FOUR_DECK)) {
			output.add("Pharaoh IV Deck"); inOnePool = true;
		}
		if (checkCard.hasTag(Tags.PHARAOH_FIVE_DECK)) {
			output.add("Pharaoh V Deck"); inOnePool = true;
		}
		if (checkCard.hasTag(Tags.METRONOME_DECK)) {
			output.add("Metronome Deck"); inOnePool = true;
		}
		if (aquaPool.containsKey(checkCard.cardID)) { output.add("Aqua Pool"); inOnePool = true; }
		if (arcanePool.containsKey(checkCard.cardID)) { output.add("Arcane Pool"); inOnePool = true; }
		if (ascendedOnePool.containsKey(checkCard.cardID)) { output.add("Ascended I Pool"); inOnePool = true; }
		if (ascendedTwoPool.containsKey(checkCard.cardID)) { output.add("Ascended II Pool"); inOnePool = true; }
		if (ascendedThreePool.containsKey(checkCard.cardID)) { output.add("Ascended III Pool"); inOnePool = true; }
		if (creatorPool.containsKey(checkCard.cardID)) { output.add("Creator Pool"); inOnePool = true; }
		if (dinosaurPool.containsKey(checkCard.cardID)) { output.add("Dinosaur Pool"); inOnePool = true; }
		if (dragonPool.containsKey(checkCard.cardID)) { output.add("Dragon Pool"); inOnePool = true; }
		if (exodiaPool.containsKey(checkCard.cardID)) { output.add("Exodia Pool"); inOnePool = true; }
		if (fiendPool.containsKey(checkCard.cardID)) { output.add("Fiend Pool"); inOnePool = true; }
		if (incrementPool.containsKey(checkCard.cardID)) { output.add("Increment Pool"); inOnePool = true; }
		if (insectPool.containsKey(checkCard.cardID)) { output.add("Insect Pool"); inOnePool = true; }
		if (machinePool.containsKey(checkCard.cardID)) { output.add("Machine Pool"); inOnePool = true; }
		if (megatypePool.containsKey(checkCard.cardID)) { output.add("Megatype Pool"); inOnePool = true; }
		if (naturiaPool.containsKey(checkCard.cardID)) { output.add("Naturia Pool"); inOnePool = true; }
		if (ojamaPool.containsKey(checkCard.cardID)) { output.add("Ojama Pool"); inOnePool = true; }
		if (pharaohOnePool.containsKey(checkCard.cardID)) { output.add("Pharaoh I Pool"); inOnePool = true; }
		if (pharaohTwoPool.containsKey(checkCard.cardID)) { output.add("Pharaoh II Pool"); inOnePool = true; }
		if (pharaohThreePool.containsKey(checkCard.cardID)) { output.add("Pharaoh III Pool"); inOnePool = true; }
		if (pharaohFourPool.containsKey(checkCard.cardID)) { output.add("Pharaoh IV Pool"); inOnePool = true; }
		if (pharaohFivePool.containsKey(checkCard.cardID)) { output.add("Pharaoh V Pool"); inOnePool = true; }
		if (plantPool.containsKey(checkCard.cardID)) { output.add("Plant Pool"); inOnePool = true; }
		if (rockPool.containsKey(checkCard.cardID)) { output.add("Rock Pool"); inOnePool = true; }
		if (spellcasterPool.containsKey(checkCard.cardID)) { output.add("Spellcaster Pool"); inOnePool = true; }
		if (standardPool.containsKey(checkCard.cardID)) { output.add("Standard Pool"); inOnePool = true; }
		if (toonPool.containsKey(checkCard.cardID)) { output.add("Toon Pool"); inOnePool = true; }
		if (warriorPool.containsKey(checkCard.cardID)) { output.add("Warrior Pool"); inOnePool = true; }
		if (zombiePool.containsKey(checkCard.cardID)) { output.add("Zombie Pool"); inOnePool = true; }
		if (namelessPool.containsKey(checkCard.cardID)) { output.add("Nameless Tomb Rewards"); inOnePool = true; }
		
		if (aquaBasicPool.containsKey(checkCard.cardID)) { output.add("Aqua Pool [Basic/Colorless]"); inOnePool = true; }
		if (ascendedOneBasicPool.containsKey(checkCard.cardID)) { output.add("Ascended I Pool [Basic/Colorless]"); inOnePool = true; }
		if (ascendedTwoBasicPool.containsKey(checkCard.cardID)) { output.add("Ascended II Pool [Basic/Colorless]"); inOnePool = true; }
		if (ascendedThreeBasicPool.containsKey(checkCard.cardID)) { output.add("Ascended III Pool [Basic/Colorless]"); inOnePool = true; }
		if (creatorBasicPool.containsKey(checkCard.cardID)) { output.add("Creator Pool [Basic/Colorless]"); inOnePool = true; }
		if (dragonBasicPool.containsKey(checkCard.cardID)) { output.add("Dragon Pool [Basic/Colorless]"); inOnePool = true; }
		if (exodiaBasicPool.containsKey(checkCard.cardID)) { output.add("Exodia Pool [Basic/Colorless]"); inOnePool = true; }
		if (fiendBasicPool.containsKey(checkCard.cardID)) { output.add("Fiend Pool [Basic/Colorless]"); inOnePool = true; }
		if (incrementBasicPool.containsKey(checkCard.cardID)) { output.add("Increment Pool [Basic/Colorless]"); inOnePool = true; }
		if (insectBasicPool.containsKey(checkCard.cardID)) { output.add("Insect Pool [Basic/Colorless]"); inOnePool = true; }
		if (machineBasicPool.containsKey(checkCard.cardID)) { output.add("Machine Pool [Basic/Colorless]"); inOnePool = true; }
		if (megatypeBasicPool.containsKey(checkCard.cardID)) { output.add("Megatype Pool [Basic/Colorless]"); inOnePool = true; }
		if (naturiaBasicPool.containsKey(checkCard.cardID)) { output.add("Naturia Pool [Basic/Colorless]"); inOnePool = true; }
		if (ojamaBasicPool.containsKey(checkCard.cardID)) { output.add("Ojama Pool [Basic/Colorless]"); inOnePool = true; }
		if (pharaohOneBasicPool.containsKey(checkCard.cardID)) { output.add("Pharaoh I Pool [Basic/Colorless]"); inOnePool = true; }
		if (pharaohTwoBasicPool.containsKey(checkCard.cardID)) { output.add("Pharaoh II Pool [Basic/Colorless]"); inOnePool = true; }
		if (pharaohThreeBasicPool.containsKey(checkCard.cardID)) { output.add("Pharaoh III Pool [Basic/Colorless]"); inOnePool = true; }
		if (pharaohFourBasicPool.containsKey(checkCard.cardID)) { output.add("Pharaoh IV Pool [Basic/Colorless]"); inOnePool = true; }
		if (pharaohFiveBasicPool.containsKey(checkCard.cardID)) { output.add("Pharaoh V Pool [Basic/Colorless]"); inOnePool = true; }
		if (plantBasicPool.containsKey(checkCard.cardID)) { output.add("Plant Pool [Basic/Colorless]"); inOnePool = true; }
		if (spellcasterBasicPool.containsKey(checkCard.cardID)) { output.add("Spellcaster Pool [Basic/Colorless]"); inOnePool = true; }
		if (standardBasicPool.containsKey(checkCard.cardID)) { output.add("Standard Pool [Basic/Colorless]"); inOnePool = true; }
		if (toonBasicPool.containsKey(checkCard.cardID)) { output.add("Toon Pool [Basic/Colorless]"); inOnePool = true; }
		if (warriorBasicPool.containsKey(checkCard.cardID)) { output.add("Warrior Pool [Basic/Colorless]"); inOnePool = true; }
		if (zombieBasicPool.containsKey(checkCard.cardID)) { output.add("Zombie Pool [Basic/Colorless]"); inOnePool = true; }

		if (checkCard.name.contains("Metronome")) {
			output.add("Metronome Pool");
			inOnePool = true;
		}

		if (checkCard instanceof CrystalEmeraldTortoise || checkCard instanceof Mispolymerization) {
			output.add("Deprecated");
			inOnePool = true;
		}

		if (!inOnePool) {
			if (checkCard instanceof OrbCard) {
				output.add("Orb Card Booster Pack");
			} else if (checkCard.hasTag(Tags.GEM_KNIGHT)) {
				output.add("Generated from Crystal Rose, or Gem Knight Metronome");
			} else {
				output.add("Unknown");
			}
		}

		return output;
	}
	
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
	
	public static ArrayList<AbstractCard> returnTotallyRandomCardSet(boolean allowBaseGame)
	{
		ArrayList<RandomDeckInterface> pools = new ArrayList<>();
		pools.add(AquaPool::deck);
		pools.add(DragonPool::deck);
		pools.add(FiendPool::deck);
		pools.add(IncrementPool::deck);
		pools.add(InsectPool::deck);
		pools.add(MachinePool::deck);
		pools.add(NaturiaPool::deck);
		pools.add(PlantPool::deck);
		pools.add(SpellcasterPool::deck);
		pools.add(StandardPool::deck);
		pools.add(WarriorPool::deck);
		pools.add(ZombiePool::deck);
		pools.add(RockPool::deck);
		pools.add(DinosaurPool::deck);
		pools.add(ArcanePool::deck);
		if (!DuelistMod.ojamaBtnBool) { pools.add(OjamaPool::deck); }
		if (!DuelistMod.toonBtnBool) { pools.add(ToonPool::deck); }
		if (DuelistMod.baseGameCards || allowBaseGame) 
		{ 
			pools.add(BaseGameHelper::getAllIroncladCards);
			pools.add(BaseGameHelper::getAllDefectCards);
			pools.add(BaseGameHelper::getAllSilentCards);
			pools.add(BaseGameHelper::getAllWatcherCards);
		}
		int roll = AbstractDungeon.cardRandomRng.random(pools.size() - 1);
		return pools.get(roll).getDeck();
	}
	
	public static void addRandomSetToPool()
	{
		ArrayList<RandomDeckInterface> pools = new ArrayList<>();	
		RandomDeckInterface aqua = 			() -> { index = 0; return AquaPool.deck(); };
		RandomDeckInterface dragon = 		() -> { index = 1; return DragonPool.deck(); };
		RandomDeckInterface fiend = 		() -> { index = 2; return FiendPool.deck(); };
		RandomDeckInterface increment = 	() -> { index = 3; return IncrementPool.deck(); };
		RandomDeckInterface insect = 		() -> { index = 4; return InsectPool.deck(); };
		RandomDeckInterface machine = 		() -> { index = 5; return MachinePool.deck();  };
		RandomDeckInterface naturia = 		() -> { index = 6; return NaturiaPool.deck();  };
		RandomDeckInterface plant = 		() -> { index = 7; return PlantPool.deck();  };
		RandomDeckInterface spellcaster = 	() -> { index = 8; return SpellcasterPool.deck();  };
		RandomDeckInterface standard = 		() -> { index = 9; return StandardPool.deck(); };
		RandomDeckInterface warrior = 		() -> { index = 10; return WarriorPool.deck();  };
		RandomDeckInterface zombie = 		() -> { index = 11; return ZombiePool.deck(); };
		RandomDeckInterface rock = 			() -> { index = 12; return RockPool.deck();  };
		RandomDeckInterface ojama = 		() -> { index = 13; return OjamaPool.deck();   };
		RandomDeckInterface toon = 			() -> { index = 14; return ToonPool.deck(); 	 };
		RandomDeckInterface dino = 			() -> { index = 15; return DinosaurPool.deck();  };
		RandomDeckInterface arc = 			() -> { index = 16; return ArcanePool.deck();  };
		RandomDeckInterface red = 			() -> { index = 17; return BaseGameHelper.getAllIroncladCards();  };
		RandomDeckInterface blue = 			() -> { index = 18; return BaseGameHelper.getAllDefectCards(); 	 };
		RandomDeckInterface green = 		() -> { index = 19; return BaseGameHelper.getAllSilentCards();  };
		RandomDeckInterface purple = 		() -> { index = 20; return BaseGameHelper.getAllWatcherCards();  };
		if (!DuelistMod.addedAquaSet) { pools.add(aqua); }
		if (!DuelistMod.addedDragonSet) { pools.add(dragon); }
		if (!DuelistMod.addedFiendSet) { pools.add(fiend); }
		if (!DuelistMod.addedIncrementSet) { pools.add(increment); }
		if (!DuelistMod.addedInsectSet) { pools.add(insect); }
		if (!DuelistMod.addedMachineSet) { pools.add(machine); }
		if (!DuelistMod.addedNaturiaSet) { pools.add(naturia); }
		if (!DuelistMod.addedPlantSet) { pools.add(plant); }
		if (!DuelistMod.addedSpellcasterSet) { pools.add(spellcaster); }
		if (!DuelistMod.addedStandardSet) { pools.add(standard); }
		if (!DuelistMod.addedWarriorSet) { pools.add(warrior);}
		if (!DuelistMod.addedZombieSet) { pools.add(zombie); }
		if (!DuelistMod.addedRockSet) { pools.add(rock); }
		if (!DuelistMod.addedOjamaSet && !DuelistMod.ojamaBtnBool) { pools.add(ojama); }
		if (!DuelistMod.addedToonSet && !DuelistMod.toonBtnBool) { pools.add(toon); }
		if (!DuelistMod.addedDinoSet) { pools.add(dino); }
		if (!DuelistMod.addedArcaneSet) { pools.add(arc); }
		if (!DuelistMod.baseGameCards) 
		{ 
			pools.add(red);
			pools.add(blue);
			pools.add(green);
			pools.add(purple);
		}
		if (pools.size() > 0)
		{
			int roll = AbstractDungeon.cardRandomRng.random(pools.size() - 1); 
			ArrayList<AbstractCard> pool = pools.get(roll).getDeck();
			setPrismDeckFlags(index);
			TheDuelist.cardPool.group.addAll(pool);
			if (AbstractDungeon.player.hasRelic(CardPoolRelic.ID)) 
			{ 
				CardPoolRelic rel = (CardPoolRelic)AbstractDungeon.player.getRelic(CardPoolRelic.ID); 
				rel.refreshPool(); 
			}
		}
	}
	
	public static ArrayList<AbstractCard> oneRandom(int exclude, int excludeB, int excludeC, int excludeD)
	{
		ArrayList<RandomDeckInterface> pools = new ArrayList<>();	
		DuelistMod.firstRandomDeck = "";
		DuelistMod.secondRandomDeck = "";
		RandomDeckInterface aqua = 			() -> { currentSelectionPool = AquaPool.deck(); 		Util.log("Selected Aquas as random");		DuelistMod.firstRandomDeck = "Aqua Deck"; DuelistMod.secondaryTierScorePools.add("Aqua Pool"); return currentSelectionPool; };
		RandomDeckInterface dragon = 		() -> { currentSelectionPool = DragonPool.deck(); 		Util.log("Selected Dragons as random");		DuelistMod.firstRandomDeck = "Dragon Deck"; DuelistMod.secondaryTierScorePools.add("Dragon Pool"); return currentSelectionPool; };
		RandomDeckInterface fiend = 		() -> { currentSelectionPool = FiendPool.deck(); 		Util.log("Selected Fiends as random"); 		DuelistMod.firstRandomDeck = "Fiend Deck"; DuelistMod.secondaryTierScorePools.add("Fiend Pool"); return currentSelectionPool; };
		RandomDeckInterface increment = 	() -> { currentSelectionPool = IncrementPool.deck(); 	Util.log("Selected Increment as random");	DuelistMod.firstRandomDeck = "Increment Deck"; DuelistMod.secondaryTierScorePools.add("Increment Pool"); return currentSelectionPool; };
		RandomDeckInterface insect = 		() -> { currentSelectionPool = InsectPool.deck(); 		Util.log("Selected Insects as random"); 	DuelistMod.firstRandomDeck = "Insect Deck"; DuelistMod.secondaryTierScorePools.add("Insect Pool"); return currentSelectionPool; };
		RandomDeckInterface machine = 		() -> { currentSelectionPool = MachinePool.deck(); 		Util.log("Selected Machines as random");	DuelistMod.firstRandomDeck = "Machine Deck"; DuelistMod.secondaryTierScorePools.add("Machine Pool"); return currentSelectionPool; };
		RandomDeckInterface naturia = 		() -> { currentSelectionPool = NaturiaPool.deck(); 		Util.log("Selected Naturias as random");	DuelistMod.firstRandomDeck = "Naturia Deck"; DuelistMod.secondaryTierScorePools.add("Naturia Pool"); return currentSelectionPool; };
		RandomDeckInterface plant = 		() -> { currentSelectionPool = PlantPool.deck(); 		Util.log("Selected Plants as random"); 		DuelistMod.firstRandomDeck = "Plant Deck"; DuelistMod.secondaryTierScorePools.add("Plant Pool"); return currentSelectionPool; };
		RandomDeckInterface spellcaster = 	() -> { currentSelectionPool = SpellcasterPool.deck(); 	Util.log("Selected Spellcasters as random");DuelistMod.firstRandomDeck = "Spellcaster Deck"; DuelistMod.secondaryTierScorePools.add("Spellcaster Pool"); return currentSelectionPool; };
		RandomDeckInterface standard = 		() -> { currentSelectionPool = StandardPool.deck(); 	Util.log("Selected Standard as random");	DuelistMod.firstRandomDeck = "Standard Deck"; DuelistMod.secondaryTierScorePools.add("Standard Pool"); return currentSelectionPool; };
		RandomDeckInterface warrior = 		() -> { currentSelectionPool = WarriorPool.deck(); 		Util.log("Selected Warriors as random");	DuelistMod.firstRandomDeck = "Warrior Deck"; DuelistMod.secondaryTierScorePools.add("Warrior Pool"); return currentSelectionPool; };
		RandomDeckInterface zombie = 		() -> { currentSelectionPool = ZombiePool.deck(); 		Util.log("Selected Zombies as random");		DuelistMod.firstRandomDeck = "Zombie Deck"; DuelistMod.secondaryTierScorePools.add("Zombie Pool"); return currentSelectionPool; };
		RandomDeckInterface rock = 			() -> { currentSelectionPool = RockPool.deck(); 		Util.log("Selected Rocks as random");		DuelistMod.firstRandomDeck = "Rock Pool"; return currentSelectionPool; };
		RandomDeckInterface ojama = 		() -> { currentSelectionPool = OjamaPool.deck();  		Util.log("Selected Ojamas as random");		DuelistMod.firstRandomDeck = "Ojama Deck"; DuelistMod.secondaryTierScorePools.add("Ojama Pool"); return currentSelectionPool; };
		RandomDeckInterface toon = 			() -> { currentSelectionPool = ToonPool.deck(); 		Util.log("Selected Toons as random"); 		DuelistMod.firstRandomDeck = "Toon Deck"; DuelistMod.secondaryTierScorePools.add("Toon Pool"); return currentSelectionPool; };
		RandomDeckInterface dino = 			() -> { currentSelectionPool = DinosaurPool.deck(); 	Util.log("Selected Dinos as random"); 		DuelistMod.firstRandomDeck = "Dinosaur Pool"; return currentSelectionPool; };
		RandomDeckInterface arc = 			() -> { currentSelectionPool = ArcanePool.deck(); 		Util.log("Selected Arcane as random"); 		DuelistMod.firstRandomDeck = "Arcane Pool"; return currentSelectionPool; };
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
		Util.log("archRoll1 was " + DuelistMod.archRoll1 + " when attempting to roll a random archetype. If this value is -1, a new one will be rolled");
		if (DuelistMod.archRoll1 == -1 || DuelistMod.archRoll2 == -1 || DuelistMod.archRoll1 > pools.size()) { DuelistMod.archRoll1 = ThreadLocalRandom.current().nextInt(pools.size()); }
		while (DuelistMod.archRoll1 == exclude || DuelistMod.archRoll1 == excludeB || DuelistMod.archRoll1 == excludeC || DuelistMod.archRoll1 == excludeD) { DuelistMod.archRoll1 = ThreadLocalRandom.current().nextInt(pools.size()); }
		pools.get(DuelistMod.archRoll1).getDeck();
		setGlobalDeckFlags();
		return currentSelectionPool;
	}
	
	private static void setPrismDeckFlags(int roll)
	{
		if (roll > -1)
		{
			switch (roll)
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
				case 17:
					DuelistMod.addedRedSet = true;
					break;
				case 18:
					DuelistMod.addedBlueSet = true;
					break;
				case 19:
					DuelistMod.addedGreenSet = true;
					break;
				case 20:
					DuelistMod.addedPurpleSet = true;
					break;
				default:
					break;
			}
		}
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
		DuelistMod.addedRedSet = false;
		DuelistMod.addedBlueSet = false;
		DuelistMod.addedGreenSet = false;
		DuelistMod.addedPurpleSet = false;
		//DuelistMod.addedHalloweenCards = false;
		//DuelistMod.addedBirthdayCards = false;
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
		RandomDeckInterface aqua = 			() -> {  getListLoc().addAll(AquaPool.deck()); 			Util.log("Selected Aquas as random");		setDeckString("Aqua Deck"); 	return null; };
		RandomDeckInterface dragon = 		() -> {  getListLoc().addAll(DragonPool.deck()); 		Util.log("Selected Dragons as random");		setDeckString("Dragon Deck"); 	return null; };
		RandomDeckInterface fiend = 		() -> {  getListLoc().addAll(FiendPool.deck()); 		Util.log("Selected Fiends as random");		setDeckString("Fiend Deck"); 	return null; };
		RandomDeckInterface increment = 	() -> {  getListLoc().addAll(IncrementPool.deck()); 	Util.log("Selected Increment as random");	setDeckString("Increment Deck");return null; };
		RandomDeckInterface insect = 		() -> {  getListLoc().addAll(InsectPool.deck()); 		Util.log("Selected Insects as random"); 	setDeckString("Insect Deck"); 	return null; };
		RandomDeckInterface machine = 		() -> {  getListLoc().addAll(MachinePool.deck()); 		Util.log("Selected Machines as random");	setDeckString("Machine Deck"); 	return null; };
		RandomDeckInterface naturia = 		() -> {  getListLoc().addAll(NaturiaPool.deck()); 		Util.log("Selected Naturias as random"); 	setDeckString("Naturia Deck"); 	return null; };
		RandomDeckInterface plant = 		() -> {  getListLoc().addAll(PlantPool.deck()); 		Util.log("Selected Plants as random");		setDeckString("Plant Deck"); 	return null; };
		RandomDeckInterface spellcaster = 	() -> {  getListLoc().addAll(SpellcasterPool.deck()); 	Util.log("Selected Spellcasters as random");setDeckString("Spellcaster Deck"); return null; };
		RandomDeckInterface standard = 		() -> {  getListLoc().addAll(StandardPool.deck()); 		Util.log("Selected Standard as random");	setDeckString("Standard Deck"); return null; };
		RandomDeckInterface warrior = 		() -> {  getListLoc().addAll(WarriorPool.deck()); 		Util.log("Selected Warriors as random");	setDeckString("Warrior Deck"); 	return null; };
		RandomDeckInterface zombie = 		() -> {  getListLoc().addAll(ZombiePool.deck()); 		Util.log("Selected Zombies as random");		setDeckString("Zombie Deck"); 	return null; };
		RandomDeckInterface rock = 			() -> {  getListLoc().addAll(RockPool.deck()); 			Util.log("Selected Rocks as random");		setDeckString("Rock Pool"); 	return null; };
		RandomDeckInterface ojama = 		() -> {  getListLoc().addAll(OjamaPool.deck());  		Util.log("Selected Ojamas as random");		setDeckString("Ojama Deck"); 	return null; }; 
		RandomDeckInterface toon = 			() -> {  getListLoc().addAll(ToonPool.deck()); 			Util.log("Selected Toons as random");		setDeckString("Toon Deck"); 	return null; };
		RandomDeckInterface dino = 			() -> {  getListLoc().addAll(DinosaurPool.deck()); 		Util.log("Selected Dinos as random");		setDeckString("Dinosaur Pool"); return null; };
		RandomDeckInterface arc = 			() -> { currentSelectionPool = ArcanePool.deck(); 		Util.log("Selected Arcane as random"); 		DuelistMod.firstRandomDeck = "Arcane Pool"; return null; };
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
