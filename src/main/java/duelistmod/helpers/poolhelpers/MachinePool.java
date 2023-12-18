package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.red.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.pools.aqua.Submarineroid;
import duelistmod.cards.pools.dragons.*;
import duelistmod.cards.pools.machine.*;
import duelistmod.cards.pools.naturia.DigitalBug;
import duelistmod.cards.pools.warrior.*;

public class MachinePool 
{
	private static String deckName = "Machine Deck";
	
	public static ArrayList<AbstractCard> oneRandom()
	{
		ArrayList<AbstractCard> pool = new ArrayList<>();		
		pool.addAll(GlobalPoolHelper.oneRandom(4));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		//deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> twoRandom()
	{
		ArrayList<AbstractCard> pool = new ArrayList<>();		
		pool.addAll(GlobalPoolHelper.twoRandom(4));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		//deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> deck()
	{
		StarterDeck machineDeck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> machineCards = new ArrayList<AbstractCard>();
		machineCards.add(new AirCrackingStorm());
		machineCards.add(new AllyJustice());
		machineCards.add(new AllySalvo());
		machineCards.add(new AncientGearBox());
		machineCards.add(new AncientGearChimera());
		machineCards.add(new AncientGearExplosive());
		machineCards.add(new AncientGearFist());
		machineCards.add(new AncientGearGadjiltron());
		machineCards.add(new AncientGearGolem());
		machineCards.add(new AncientGearReactorDragon());
		machineCards.add(new AncientGearWorkshop());
		machineCards.add(new ArtifactIgnition());
		machineCards.add(new ArtifactSanctum());
		machineCards.add(new BarrelDragon());
		machineCards.add(new Biofalcon());
		machineCards.add(new BlackSalvo());
		machineCards.add(new BlastAsmodian());
		machineCards.add(new BlastHeldTribute());
		machineCards.add(new BlastJuggler());
		machineCards.add(new BlastWithChain());
		machineCards.add(new BlastingFuse());	
		machineCards.add(new BlindDestruction());
		machineCards.add(new BreakDraw());
		machineCards.add(new CannonSoldier());
		machineCards.add(new CardsFromTheSky());		
		machineCards.add(new CatapultTurtle());
		machineCards.add(new CemetaryBomb());
		machineCards.add(new ChaosAncientGearGiant());
		machineCards.add(new CoreBlaster());
		machineCards.add(new CyberArchfiend());
		machineCards.add(new CyberRaider());
		machineCards.add(new CyberneticFusion());
		machineCards.add(new CyberneticOverflow());
		machineCards.add(new DarkFactory());
		machineCards.add(new Deskbot001());
		machineCards.add(new Deskbot002());
		machineCards.add(new Deskbot003());
		machineCards.add(new Deskbot004());
		machineCards.add(new Deskbot005());
		machineCards.add(new Deskbot006());
		machineCards.add(new Deskbot007());
		machineCards.add(new Deskbot008());
		machineCards.add(new Deskbot009());
		machineCards.add(new DoubleTool());
		machineCards.add(new ElectromagneticTurtle());
		machineCards.add(new Factory100Machines());
		machineCards.add(new Flashbang());
		machineCards.add(new FlyingPegasus());
		machineCards.add(new FrontlineObserver());
		machineCards.add(new GadgetSoldier());
		machineCards.add(new GearGigant());
		machineCards.add(new Geargiauger());
		machineCards.add(new Geartown());
		machineCards.add(new GenexAllyBirdman());
		machineCards.add(new GenexNeutron());
		machineCards.add(new GoldGadget());
		machineCards.add(new GoldenBlastJuggler());
		machineCards.add(new GravityAxe());
		machineCards.add(new GravityBlaster());
		machineCards.add(new GravityLash());
		machineCards.add(new GreenGadget());
		machineCards.add(new HeavyFreightTrainDerricane());
		machineCards.add(new HeavyMechSupportArmor());
		machineCards.add(new HeavyMechSupportPlatform());
		machineCards.add(new HeavyMetalRaiders());
		machineCards.add(new ImperialOrder());		
		machineCards.add(new IronDraw());
		machineCards.add(new IronhammerGiant());
		machineCards.add(new JadeKnight());
		machineCards.add(new Jinzo());
		machineCards.add(new JinzoLord());
		machineCards.add(new JumboDrill());
		machineCards.add(new JunkKuriboh());
		machineCards.add(new JunkWarrior());
		machineCards.add(new LimiterRemoval());		
		machineCards.add(new MachineDuplication());
		machineCards.add(new MachineFactory());		
		machineCards.add(new MachineKing());
		machineCards.add(new MachineKingPrototype());
		machineCards.add(new MetalDetector());
		machineCards.add(new MetalDragon());
		machineCards.add(new MetalholdMovingBlockade());
		machineCards.add(new Mixeroid());
		machineCards.add(new NightmareWheel());
		machineCards.add(new Oilman());
		machineCards.add(new OrbitalBombardment());
		machineCards.add(new OrcustCrescendo());
		machineCards.add(new OutriggerExtension());
		machineCards.add(new Overworked());
		machineCards.add(new ParallelPortArmor());
		machineCards.add(new PineappleBlast());
		machineCards.add(new PlatinumGadget());
		machineCards.add(new PortableBatteryPack());
		machineCards.add(new PotDuality());
		//machineCards.add(new PsychicShockwave());
		machineCards.add(new RevolvingSwitchyard());
		machineCards.add(new QuillboltHedgehog());
		machineCards.add(new RainbowBridge());
		machineCards.add(new RedGadget());
		machineCards.add(new RoboticKnight());
		machineCards.add(new SatelliteCannon());
		machineCards.add(new ScrapBeast());
		machineCards.add(new ScrapIronScarecrow());
		machineCards.add(new SevenCompleted());	
		machineCards.add(new ShortCircuit());		
		machineCards.add(new SlotMachine());
		machineCards.add(new SolarWindJammer());
		machineCards.add(new SolemnStrike());
		machineCards.add(new SolemnWarning());
		machineCards.add(new SparkBlaster());
		machineCards.add(new SteamTrainKing());
		machineCards.add(new StimPack());
		machineCards.add(new Submarineroid());
		machineCards.add(new SwordsConcealing());
		machineCards.add(new TokenVacuum());
		machineCards.add(new Tuningware());
		machineCards.add(new TurretWarrior());
		machineCards.add(new UnionHangar());
		machineCards.add(new WonderGarage());
		machineCards.add(new YellowGadget());
		machineCards.add(new ZONE());
		machineCards.add(new Apoqliphort());
		machineCards.add(new Shekhinaga());
		machineCards.add(new Quariongandrax());
		machineCards.add(new BarricadeborgBlocker());
		//machineDeck.fillPoolCards(machineCards);

		if (DuelistMod.persistentDuelistData.CardPoolSettings.getBaseGameCards() && DuelistMod.isNotAllCardsPoolType())
		{
			machineCards.add(new Anger());
			machineCards.add(new BattleTrance());
			machineCards.add(new DemonForm());
			machineCards.add(new Flex());
			machineCards.add(new Inflame());
			machineCards.add(new Intimidate());
			machineCards.add(new LimitBreak());
			machineCards.add(new Rage());
			machineCards.add(new Rampage());
			machineCards.add(new Reaper());
			machineCards.add(new SpotWeakness());
			machineCards.add(new AThousandCuts());
			machineCards.add(new AfterImage());
			machineCards.add(new BladeDance());
			machineCards.add(new CloakAndDagger());
			machineCards.add(new InfiniteBlades());
			machineCards.add(new Amplify());
			machineCards.add(new BeamCell());
			machineCards.add(new BiasedCognition());
			machineCards.add(new Capacitor());
			machineCards.add(new CoreSurge());
			machineCards.add(new CreativeAI());
			machineCards.add(new Electrodynamics());
			machineCards.add(new FTL());
			machineCards.add(new MachineLearning());
			machineCards.add(new Overclock());
			machineCards.add(new Reboot());
			machineCards.add(new Recursion());
			machineCards.add(new Reprogram());
			machineCards.add(new SelfRepair());
		}
		
		//DuelistMod.archetypeCards.addAll(machineCards);
		return machineCards;
	}
	
	public static  ArrayList<AbstractCard> basic()
	{
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> pool = new ArrayList<AbstractCard>();
		if (DuelistMod.persistentDuelistData.CardPoolSettings.getSmallBasicSet()) { pool.addAll(BasicPool.smallBasic("Machine Deck")); }
		else { pool.addAll(BasicPool.fullBasic("Machine Deck")); }
		//deck.fillPoolCards(pool); 
		return pool;
	}
}
