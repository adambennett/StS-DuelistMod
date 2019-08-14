package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.helpers.Util;

public class NaturePool 
{
	private static String deckName = "Nature Deck";
	
	public static ArrayList<AbstractCard> oneRandom()
	{
		ArrayList<ArrayList<AbstractCard>> pools = new ArrayList<ArrayList<AbstractCard>>();
		pools.add(AquaPool.deck());
		//pools.add(CreatorPool.deck());
		pools.add(DragonPool.deck());
		pools.add(FiendPool.deck());
		//pools.add(GiantPool.deck());
		pools.add(IncrementPool.deck());
		//pools.add(InsectPool.deck());
		pools.add(MachinePool.deck());
		pools.add(MegatypePool.deck());
		//pools.add(PlantPool.deck());
		//pools.add(PredaplantPool.deck());
		pools.add(SpellcasterPool.deck());
		pools.add(StandardPool.deck());
		pools.add(WarriorPool.deck());
		pools.add(ZombiePool.deck());
		if (!DuelistMod.ojamaBtnBool) { pools.add(OjamaPool.deck()); }
		if (!DuelistMod.toonBtnBool) { pools.add(ToonPool.deck()); }
		if (DuelistMod.archRoll1 == -1 || DuelistMod.archRoll2 == -1 || DuelistMod.archRoll1 > pools.size()) { DuelistMod.archRoll1 = ThreadLocalRandom.current().nextInt(pools.size()); }
		ArrayList<AbstractCard> random = pools.get(DuelistMod.archRoll1);
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		Util.log(deckName + " was filled with random cards from the pool with index of " + DuelistMod.archRoll1);
		deck.fillPoolCards(random);	
		return random;
	}
	
	public static ArrayList<AbstractCard> twoRandom()
	{
		ArrayList<ArrayList<AbstractCard>> pools = new ArrayList<ArrayList<AbstractCard>>();
		pools.add(AquaPool.deck());
		//pools.add(CreatorPool.deck());
		pools.add(DragonPool.deck());
		pools.add(FiendPool.deck());
		//pools.add(GiantPool.deck());
		pools.add(IncrementPool.deck());
		//pools.add(InsectPool.deck());
		pools.add(MachinePool.deck());
		pools.add(MegatypePool.deck());
		//pools.add(PlantPool.deck());
		//pools.add(PredaplantPool.deck());
		pools.add(SpellcasterPool.deck());
		pools.add(StandardPool.deck());
		pools.add(WarriorPool.deck());
		pools.add(ZombiePool.deck());
		if (!DuelistMod.ojamaBtnBool) { pools.add(OjamaPool.deck()); }
		if (!DuelistMod.toonBtnBool) { pools.add(ToonPool.deck()); }	
		ArrayList<AbstractCard> random = new ArrayList<AbstractCard>();
		if (DuelistMod.archRoll1 == -1 || DuelistMod.archRoll2 == -1 || DuelistMod.archRoll1 > pools.size() || DuelistMod.archRoll2 > pools.size())
		{
			DuelistMod.archRoll1 = ThreadLocalRandom.current().nextInt(pools.size());
			DuelistMod.archRoll2 = ThreadLocalRandom.current().nextInt(pools.size());
			while (DuelistMod.archRoll1 == DuelistMod.archRoll2) { DuelistMod.archRoll2 = ThreadLocalRandom.current().nextInt(pools.size()); }
		}
		ArrayList<AbstractCard> randomA = pools.get(DuelistMod.archRoll1);
		ArrayList<AbstractCard> randomB = pools.get(DuelistMod.archRoll2);
		random.addAll(randomA); random.addAll(randomB);
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(random);	
		Util.log(deckName + " was filled with random cards from the pool with index of " + DuelistMod.archRoll1 + " and " + DuelistMod.archRoll2);
		return random;
	}
	
	public static ArrayList<AbstractCard> deck()
	{
		StarterDeck natureDeck = DuelistMod.starterDeckNamesMap.get(deckName);		
		ArrayList<AbstractCard> natureCards = new ArrayList<AbstractCard>();
		natureCards.add(new BasicInsect());
		natureCards.add(new CocoonEvolution());
		natureCards.add(new EmpressMantis());
		natureCards.add(new Firegrass());
		natureCards.add(new Gigaplant());
		natureCards.add(new Grasschopper());
		natureCards.add(new HundredFootedHorror());
		natureCards.add(new InsectKnight());
		natureCards.add(new InsectQueen());
		natureCards.add(new Invigoration());
		natureCards.add(new JerryBeansMan());
		natureCards.add(new JiraiGumo());
		natureCards.add(new ManEaterBug());
		natureCards.add(new MotherSpider());
		natureCards.add(new Parasite());
		natureCards.add(new PetitMoth());
		natureCards.add(new PredaplantChimerafflesia());
		natureCards.add(new PredaplantChlamydosundew());
		natureCards.add(new PredaplantDrosophyllum());
		natureCards.add(new PredaplantFlytrap());
		natureCards.add(new PredaplantPterapenthes());
		natureCards.add(new PredaplantSarraceniant());
		natureCards.add(new PredaplantSpinodionaea());
		natureCards.add(new Predaponics());
		natureCards.add(new Predapruning());
		natureCards.add(new VioletCrystal());
		natureCards.add(new WorldCarrot());
		natureCards.add(new BeastFangs());
		natureCards.add(new NaturiaBeast());
		natureCards.add(new NaturiaCliff());
		natureCards.add(new NaturiaDragonfly());
		natureCards.add(new NaturiaGuardian());
		natureCards.add(new NaturiaHorneedle());
		natureCards.add(new NaturiaLandoise());
		natureCards.add(new NaturiaMantis());
		natureCards.add(new NaturiaPineapple());
		natureCards.add(new NaturiaPumpkin());
		natureCards.add(new NaturiaRosewhip());
		natureCards.add(new NaturiaSacredTree());
		natureCards.add(new NaturiaStrawberry());
		natureCards.add(new AngelTrumpeter());
		natureCards.add(new ArmoredBee());
		natureCards.add(new SuperSolarNutrient());
		natureCards.add(new SangaEarth());
		natureCards.add(new GracefulCharity());
		natureCards.add(new HeartUnderdog());
		natureCards.add(new PotDuality());
		natureCards.add(new AcidTrapHole());
		natureCards.add(new BottomlessTrapHole());
		natureCards.add(new CheerfulCoffin());
		natureCards.add(new WorldTree());
		natureCards.add(new Spore());	
		natureCards.add(new MiracleFertilizer());
		natureCards.add(new QueenAngelRoses());
		natureCards.add(new VenomShot());
		natureCards.add(new OrbitalBombardment());
		natureCards.add(new BlossomBombardment());
		natureCards.add(new RagingMadPlants());
		natureCards.add(new ThornMalice());
		natureCards.add(new ArsenalBug());
		natureCards.add(new CrossSwordBeetle());
		natureCards.add(new MultiplicationOfAnts());
		natureCards.add(new DarkSpider());
		natureCards.add(new CocoonUltraEvolution());
		natureCards.add(new PinchHopper());
		natureCards.add(new UndergroundArachnid());		
		natureCards.add(new BlackRoseDragon());	
		natureCards.add(new BlackRoseMoonlight());
		natureCards.add(new FallenAngelRoses());
		natureCards.add(new RedRoseDragon());
		natureCards.add(new RosePaladin());
		natureCards.add(new TwilightRoseKnight());
		natureCards.add(new BirdRoses());
		natureCards.add(new BlockSpider());
		natureCards.add(new BlueRoseDragon());
		natureCards.add(new CopyPlant());
		natureCards.add(new FrozenRose());
		natureCards.add(new MarkRose());
		natureCards.add(new RevivalRose());
		natureCards.add(new RoseArcher());
		natureCards.add(new RoseLover());
		natureCards.add(new RoseWitch());
		natureCards.add(new WhiteRoseDragon());
		natureCards.add(new WitchBlackRose());
		natureCards.add(new BloomingDarkestRose());
		natureCards.add(new SplendidRose());
		//natureCards.add(new Predaplanet());
		
		if (DuelistMod.baseGameCards && DuelistMod.setIndex != 9)
		{
			natureCards.add(new NoxiousFumes());
			natureCards.add(new BouncingFlask());
			natureCards.add(new Catalyst());
			natureCards.add(new CorpseExplosion());
			natureCards.add(new CripplingPoison());
			natureCards.add(new Envenom());
			natureCards.add(new DeadlyPoison());
			natureCards.add(new PoisonedStab());
			natureCards.add(new Bane());			
		}
		natureDeck.fillPoolCards(natureCards);		
		natureDeck.fillArchetypeCards(natureCards);
		DuelistMod.archetypeCards.addAll(natureCards);
		return natureCards;
	}
	
	public static  ArrayList<AbstractCard> basic()
	{
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> pool = new ArrayList<AbstractCard>();
		pool.addAll(DuelistMod.basicCards);
		deck.fillPoolCards(pool); 
		return pool;
	}
}
