package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Recycle;
import com.megacrit.cardcrawl.cards.purple.*;
import com.megacrit.cardcrawl.cards.red.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.fourthWarriors.*;
import duelistmod.cards.incomplete.*;
import duelistmod.helpers.Util;

public class WarriorPool 
{
	private static String deckName = "Warrior Deck";
	
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
		pools.add(NaturePool.deck());
		pools.add(PlantPool.deck());
		//pools.add(PredaplantPool.deck());
		pools.add(SpellcasterPool.deck());
		pools.add(StandardPool.deck());
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
		pools.add(NaturePool.deck());
		pools.add(PlantPool.deck());
		//pools.add(PredaplantPool.deck());
		pools.add(SpellcasterPool.deck());
		pools.add(StandardPool.deck());
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
		StarterDeck magnetDeck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> magnetCards = new ArrayList<AbstractCard>();
		magnetCards.add(new SuperheavyBenkei());
		magnetCards.add(new SuperheavyBlueBrawler());
		magnetCards.add(new SuperheavyDaihachi());
		magnetCards.add(new SuperheavyFlutist());
		magnetCards.add(new SuperheavyGeneral());
		magnetCards.add(new SuperheavyMagnet());
		magnetCards.add(new SuperheavyOgre());
		magnetCards.add(new SuperheavyScales());
		magnetCards.add(new SuperheavySwordsman());
		magnetCards.add(new SuperheavyWaraji());
		magnetCards.add(new SteamTrainKing());
		magnetCards.add(new SuperheavySoulbeads());
		magnetCards.add(new SuperheavySoulbuster());
		//magnetCards.add(new SuperheavySoulclaw());
		magnetCards.add(new SuperheavySoulhorns());
		magnetCards.add(new SuperheavySoulpiercer());
		magnetCards.add(new SuperheavySoulshield());
		magnetCards.add(new SwordsConcealing());
		magnetCards.add(new HeartUnderdog());
		magnetCards.add(new ReadyForIntercepting());
		magnetCards.add(new Hayate());
		magnetCards.add(new AlphaMagnet());
		magnetCards.add(new BetaMagnet());
		magnetCards.add(new GammaMagnet());
		magnetCards.add(new ValkMagnet());
		magnetCards.add(new Mudballman());
		magnetCards.add(new CombinationAttack());
		magnetCards.add(new MagneticField());		
		magnetCards.add(new CelticGuardian());
		magnetCards.add(new FlameSwordsman());
		magnetCards.add(new FortressWarrior());
		magnetCards.add(new GaiaFierce());
		magnetCards.add(new GauntletWarrior());
		magnetCards.add(new JudgeMan());
		magnetCards.add(new Zombyra());		
		magnetCards.add(new AssaultArmor());
		magnetCards.add(new BattleWarrior());
		magnetCards.add(new BladeArmorNinja());
		magnetCards.add(new BladeKnight());
		magnetCards.add(new BrushfireKnight());
		magnetCards.add(new BullBlader());
		magnetCards.add(new ColossalFighter());
		magnetCards.add(new ComboFighter());
		magnetCards.add(new ComboMaster());
		magnetCards.add(new CrossAttack());
		magnetCards.add(new DarkGrepher());
		magnetCards.add(new DustKnight());
		magnetCards.add(new FeedbackWarrior());
		magnetCards.add(new GoyoChaser());
		magnetCards.add(new GoyoKing());
		magnetCards.add(new GravityWarrior());
		magnetCards.add(new JunkWarrior());
		magnetCards.add(new LegendarySword());
		magnetCards.add(new LightningBlade());
		magnetCards.add(new MightyWarrior());
		magnetCards.add(new NinjaGrandmaster());
		magnetCards.add(new NitroWarrior());
		magnetCards.add(new SoldierLady());
		magnetCards.add(new SwordDragonSoul());
		magnetCards.add(new AdvanceForce());
		magnetCards.add(new AfterGenocide());
		magnetCards.add(new AfterTheStorm());
		magnetCards.add(new AgainstTheWind());
		magnetCards.add(new BattleguardHowling());
		magnetCards.add(new BattleguardRage());
		magnetCards.add(new ChaosSeed());
		magnetCards.add(new CrystalBlessing());
		magnetCards.add(new CrystalTree());
		magnetCards.add(new CubicWave());
		magnetCards.add(new DarkBurningAttack());
		magnetCards.add(new DarkBurningMagic());
		magnetCards.add(new DarkCrusader());
		magnetCards.add(new DarkOccultism());
		magnetCards.add(new DawnKnight());
		magnetCards.add(new DefenseZone());
		magnetCards.add(new DeltaAttacker());
		magnetCards.add(new Downbeat());
		magnetCards.add(new EgoBoost());
		magnetCards.add(new ElfLight());
		magnetCards.add(new EulerCircuit());
		magnetCards.add(new FengshengMirror());
		magnetCards.add(new GladiatorReturn());
		magnetCards.add(new GlowingCrossbow());
		magnetCards.add(new GoyoDefender());
		magnetCards.add(new GoyoEmperor());
		magnetCards.add(new GravityLash());
		magnetCards.add(new GuardianOrder());
		magnetCards.add(new HardArmor());
		magnetCards.add(new HarmonicWaves());
		magnetCards.add(new HerculeanPower());
		magnetCards.add(new HeroRing());
		magnetCards.add(new HiddenArmory());
		magnetCards.add(new LegendHeart());
		magnetCards.add(new LegendaryBlackBelt());
		magnetCards.add(new LightLaser());
		magnetCards.add(new MagnumShield());
		magnetCards.add(new WarriorReturningAlive());
		magnetCards.add(new CommanderSwords());
		magnetCards.add(new CubicKarma());
		magnetCards.add(new FightingSpirit());
		magnetCards.add(new Flint());
		magnetCards.add(new GridRod());
		magnetCards.add(new ReinforceTruth());
		magnetCards.add(new SpiritForce());
		magnetCards.add(new WeaponChange());
		magnetCards.add(new Sogen());
		magnetCards.add(new PurplePainOni());
		magnetCards.add(new ImperialOrder());
		magnetCards.add(new MeteorDestruction());
		magnetCards.add(new ScrapFactory());
		magnetCards.add(new BrilliantSpark());
		magnetCards.add(new ElectromagneticShield());
		magnetCards.add(new MillenniumShield());
		magnetCards.add(new DokiDoki());
		magnetCards.add(new ReinforcementsArmy());
		magnetCards.add(new WhiteNinja());
		magnetCards.add(new CyberRaider());
		magnetCards.add(new MaxWarrior());
		magnetCards.add(new CircleFireKings());
		magnetCards.add(new OnslaughtFireKings());
		if (DuelistMod.baseGameCards && DuelistMod.setIndex != 9)
		{
			magnetCards.add(new Adaptation());
			magnetCards.add(new Blasphemy());
			magnetCards.add(new BowlingBash());
			magnetCards.add(new CarveReality());
			magnetCards.add(new Clarity());
			magnetCards.add(new CleanseEvil());
			magnetCards.add(new Consecrate());
			magnetCards.add(new CutThroughFate());
			magnetCards.add(new DeceiveReality());
			magnetCards.add(new DeusExMachina());
			magnetCards.add(new DevaForm());
			magnetCards.add(new Devotion());
			magnetCards.add(new EmptyBody());
			magnetCards.add(new EmptyFist());
			magnetCards.add(new EmptyMind());
			magnetCards.add(new Evaluate());
			magnetCards.add(new Fasting());
			magnetCards.add(new FearNoEvil());
			magnetCards.add(new FlurryOfBlows());
			magnetCards.add(new ForeignInfluence());
			magnetCards.add(new Halt());
			magnetCards.add(new InnerPeace());
			magnetCards.add(new JustLucky());
			magnetCards.add(new LessonLearned());
			magnetCards.add(new MasterReality());
			magnetCards.add(new Meditate());
			magnetCards.add(new MentalFortress());
			magnetCards.add(new Metaphysics());
			magnetCards.add(new Nirvana());
			magnetCards.add(new Omniscience());
			magnetCards.add(new PathToVictory());
			magnetCards.add(new Pray());
			magnetCards.add(new Prostrate());
			magnetCards.add(new Protect());
			magnetCards.add(new Ragnarok());
			magnetCards.add(new ReachHeaven());
			magnetCards.add(new Sanctity());
			magnetCards.add(new SandsOfTime());
			//magnetCards.add(new SoothingAura());
			magnetCards.add(new SpiritShield());
			magnetCards.add(new StepAndStrike());
			magnetCards.add(new Swivel());
			magnetCards.add(new TalkToTheHand());
			magnetCards.add(new ThirdEye());
			magnetCards.add(new Unraveling());
			magnetCards.add(new Vault());
			//magnetCards.add(new Vengeance());
			magnetCards.add(new Vigilance());
			magnetCards.add(new Wallop());
			magnetCards.add(new Weave());
			magnetCards.add(new WindmillStrike());
			magnetCards.add(new Wireheading());
			magnetCards.add(new Wish());
			magnetCards.add(new Worship());
			magnetCards.add(new WreathOfFlame());
			magnetCards.add(new BurningPact());
			magnetCards.add(new Corruption());
			magnetCards.add(new FeelNoPain());
			magnetCards.add(new SecondWind());
			magnetCards.add(new Sentinel());
			magnetCards.add(new SeverSoul());
			magnetCards.add(new DarkEmbrace());
			magnetCards.add(new Exhume());
			magnetCards.add(new Feed());
			magnetCards.add(new FiendFire());
			magnetCards.add(new Recycle());			
		}
		
		magnetDeck.fillPoolCards(magnetCards);		
		magnetDeck.fillArchetypeCards(magnetCards);
		DuelistMod.archetypeCards.addAll(magnetCards);
		return magnetCards;
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
