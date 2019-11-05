package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
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
		pool.addAll(GlobalPoolHelper.oneRandom(5));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> twoRandom()
	{
		ArrayList<AbstractCard> pool = new ArrayList<>();		
		pool.addAll(GlobalPoolHelper.twoRandom(5));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(pool);	
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
		machineCards.add(new ArtifactIgnition());
		machineCards.add(new ArtifactSanctum());
		machineCards.add(new AxeDespair());
		machineCards.add(new BarrelDragon());
		machineCards.add(new Biofalcon());
		machineCards.add(new BlackPendant());
		machineCards.add(new BlastAsmodian());
		machineCards.add(new BlastHeldTribute());
		machineCards.add(new BlastJuggler());
		machineCards.add(new BlastWithChain());
		machineCards.add(new BlastingFuse());
		machineCards.add(new BlastingRuins());
		machineCards.add(new BlossomBombardment());
		machineCards.add(new BreakDraw());
		//machineCards.add(new BusterDrake());
		machineCards.add(new CannonSoldier());
		machineCards.add(new CardsFromTheSky());		
		machineCards.add(new CatapultTurtle());
		machineCards.add(new CemetaryBomb());
		machineCards.add(new ChaosAncientGearGiant());
		machineCards.add(new CoreBlaster());
		machineCards.add(new CyberArchfiend());
		//machineCards.add(new CyberDragon());
		machineCards.add(new CyberRaider());
		machineCards.add(new CyberneticFusion());
		machineCards.add(new CyberneticOverflow());
		machineCards.add(new Deskbot001());
		machineCards.add(new Deskbot002());
		machineCards.add(new Deskbot004());
		machineCards.add(new Deskbot005());
		machineCards.add(new Deskbot009());
		machineCards.add(new DigitalBug());
		machineCards.add(new DoubleTool());
		machineCards.add(new ElectromagneticTurtle());
		machineCards.add(new FlyingPegasus());
		machineCards.add(new FrontlineObserver());
		machineCards.add(new GadgetSoldier());
		machineCards.add(new Geargiauger());
		machineCards.add(new GearGigant());
		machineCards.add(new GoldGadget());
		machineCards.add(new GoldenBlastJuggler());
		machineCards.add(new GracefulCharity());
		machineCards.add(new GravityAxe());
		machineCards.add(new GravityBlaster());
		machineCards.add(new GreenGadget());
		machineCards.add(new HeartUnderdog());
		machineCards.add(new HeavyFreightTrainDerricane());
		machineCards.add(new HeavyMechSupportArmor());
		machineCards.add(new HeavyMechSupportPlatform());
		machineCards.add(new HeavyMetalRaiders());
		machineCards.add(new IronCall());
		machineCards.add(new IronDraw());
		machineCards.add(new IronhammerGiant());
		machineCards.add(new JadeKnight());
		machineCards.add(new Jinzo());
		machineCards.add(new JinzoLord());
		machineCards.add(new JumboDrill());
		machineCards.add(new JunkKuriboh());
		machineCards.add(new JunkWarrior());
		machineCards.add(new LimiterRemoval());
		machineCards.add(new LostGuardian());
		machineCards.add(new MachineDuplication());
		machineCards.add(new MachineFactory());		
		machineCards.add(new MachineKing());
		machineCards.add(new MetalDragon());
		machineCards.add(new Mixeroid());
		machineCards.add(new Oilman());
		machineCards.add(new OniTankT34());
		machineCards.add(new OrbitalBombardment());
		machineCards.add(new OutriggerExtension());
		machineCards.add(new PlatinumGadget());
		machineCards.add(new PotDuality());
		machineCards.add(new PsychicShockwave());
		machineCards.add(new RedGadget());
		machineCards.add(new Reinforcements());
		machineCards.add(new RockBombardment());
		machineCards.add(new SatelliteCannon());
		machineCards.add(new ScrapIronScarecrow());
		machineCards.add(new SevenCompleted());	
		machineCards.add(new SparkBlaster());
		machineCards.add(new StarBlast());
		machineCards.add(new SteamTrainKing());
		machineCards.add(new StimPack());
		machineCards.add(new StoneDragon());
		machineCards.add(new Submarineroid());
		machineCards.add(new SwordDeepSeated());
		machineCards.add(new SwordsConcealing());
		machineCards.add(new SystemDown());
		machineCards.add(new TokenVacuum());
		machineCards.add(new TurretWarrior());
		machineCards.add(new YellowGadget());
		machineCards.add(new RainbowBridge());
		machineCards.add(new Overworked());
		machineCards.add(new SolarWindJammer());
		machineCards.add(new ShortCircuit());
		machineDeck.fillPoolCards(machineCards);		
		machineDeck.fillArchetypeCards(machineCards);
		//DuelistMod.archetypeCards.addAll(machineCards);
		return machineCards;
	}
	
	public static  ArrayList<AbstractCard> basic()
	{
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> pool = new ArrayList<AbstractCard>();
		if (DuelistMod.smallBasicSet) { pool.addAll(BasicPool.smallBasic("")); }
		else { pool.addAll(BasicPool.fullBasic("")); }
		deck.fillPoolCards(pool); 
		return pool;
	}
}
