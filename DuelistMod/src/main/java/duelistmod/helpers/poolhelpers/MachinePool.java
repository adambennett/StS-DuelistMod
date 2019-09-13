package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.fourthWarriors.*;
import duelistmod.cards.incomplete.*;
import duelistmod.helpers.Util;

public class MachinePool 
{
	private static String deckName = "Machine Deck";
	
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
		pools.add(MegatypePool.deck());
		pools.add(NaturePool.deck());
		pools.add(PlantPool.deck());
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
		deck.fillPoolCards(random);	
		Util.log(deckName + " was filled with random cards from the pool with index of " + DuelistMod.archRoll1);
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
		pools.add(MegatypePool.deck());
		pools.add(NaturePool.deck());
		pools.add(PlantPool.deck());
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
		StarterDeck machineDeck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> machineCards = new ArrayList<AbstractCard>();
		machineCards.add(new AllyJustice());
		machineCards.add(new BarrelDragon());
		machineCards.add(new CannonSoldier());
		machineCards.add(new CyberDragon());
		machineCards.add(new Jinzo());
		machineCards.add(new MachineKing());
		machineCards.add(new MetalDragon());
		machineCards.add(new SteamTrainKing());
		machineCards.add(new JunkKuriboh());
		machineCards.add(new MachineFactory());		
		machineCards.add(new GracefulCharity());
		machineCards.add(new HeartUnderdog());
		machineCards.add(new PotDuality());
		machineCards.add(new SwordsConcealing());
		machineCards.add(new RedGadget());
		machineCards.add(new YellowGadget());
		machineCards.add(new GreenGadget());
		machineCards.add(new AncientGearChimera());
		machineCards.add(new AncientGearGadjiltron());
		machineCards.add(new AncientGearGolem());
		machineCards.add(new TurretWarrior());
		machineCards.add(new GadgetSoldier());
		machineCards.add(new ExploderDragon());
		machineCards.add(new BlastJuggler());
		machineCards.add(new StimPack());
		machineCards.add(new IronhammerGiant());
		machineCards.add(new ChaosAncientGearGiant());
		machineCards.add(new AncientGearBox());
		machineCards.add(new Deskbot001());
		machineCards.add(new Deskbot002());
		machineCards.add(new Deskbot009());
		machineCards.add(new GravityBlaster());
		machineCards.add(new FlyingPegasus());
		machineCards.add(new CyberneticFusion());
		machineCards.add(new IronCall());
		machineCards.add(new IronDraw());
		machineCards.add(new LimiterRemoval());
		machineCards.add(new MachineDuplication());
		machineCards.add(new Oilman());
		machineCards.add(new Mixeroid());
		machineCards.add(new OutriggerExtension());
		machineCards.add(new JumboDrill());
		machineCards.add(new Biofalcon());
		machineCards.add(new Deskbot004());
		machineCards.add(new Deskbot005());
		machineCards.add(new OniTankT34());
		machineCards.add(new CatapultTurtle());
		machineCards.add(new TokenVacuum());
		machineCards.add(new CyberArchfiend());
		machineCards.add(new BusterDrake());
		machineCards.add(new AncientGearFist());
		machineCards.add(new CoreBlaster());
		machineCards.add(new SparkBlaster());
		machineCards.add(new FlyingSaucer());
		machineCards.add(new CosmicHorrorGangiel());
		machineCards.add(new GoldenBlastJuggler());
		machineCards.add(new AlienTelepath());
		machineCards.add(new BlastAsmodian());
		machineCards.add(new BlastHeldTribute());
		machineCards.add(new BlastMagician());
		machineCards.add(new BlastWithChain());
		machineCards.add(new ExploderDragonwing());
		machineCards.add(new OrbitalBombardment());
		machineCards.add(new AncientGearExplosive());
		machineCards.add(new BlasterDragonInfernos());
		machineCards.add(new BlastingRuins());
		machineCards.add(new BlossomBombardment());
		machineCards.add(new BlastingFuse());
		machineCards.add(new CemetaryBomb());
		machineCards.add(new RockBombardment());
		machineCards.add(new JunkWarrior());
		machineCards.add(new CyberRaider());
		machineCards.add(new SatelliteCannon());
		
		if (!DuelistMod.toonBtnBool)
		{
			machineCards.add(new ToonWorld());
			machineCards.add(new ToonKingdom());
			machineCards.add(new ToonAncientGear());
			//machineCards.add(new ToonCyberDragon());		
		}
		machineDeck.fillPoolCards(machineCards);		
		machineDeck.fillArchetypeCards(machineCards);
		DuelistMod.archetypeCards.addAll(machineCards);
		return machineCards;
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
