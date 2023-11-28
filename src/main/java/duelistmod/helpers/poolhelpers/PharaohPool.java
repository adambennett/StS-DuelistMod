package duelistmod.helpers.poolhelpers;

import java.util.*;

import com.megacrit.cardcrawl.cards.AbstractCard;

import com.megacrit.cardcrawl.cards.blue.Stack;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.cards.purple.*;
import duelistmod.DuelistMod;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.pools.aqua.*;
import duelistmod.cards.pools.beast.*;
import duelistmod.cards.pools.dragons.*;
import duelistmod.cards.pools.fiend.*;
import duelistmod.cards.pools.increment.*;
import duelistmod.cards.pools.insects.*;
import duelistmod.cards.pools.machine.*;
import duelistmod.cards.pools.naturia.*;
import duelistmod.cards.pools.warrior.*;
import duelistmod.cards.pools.zombies.*;

public class PharaohPool {
	
	public static ArrayList<AbstractCard> deck(int level) {
		Map<Integer, ArrayList<AbstractCard>> decks = new HashMap<>();
		decks.put(1, pharaohOne());
		decks.put(2, pharaohTwo());
		decks.put(3, pharaohThree());
		decks.put(4, pharaohFour());
		decks.put(5, pharaohFive());
		return decks.get(level);
	}
	
	public static ArrayList<AbstractCard> pharaohOne() {
		ArrayList<AbstractCard> cards = new ArrayList<>();

		// Duelist
		cards.add(new AllySalvo());
		cards.add(new AloofLupine());
		cards.add(new AncientCrimsonApe());
		cards.add(new AncientElf());
		cards.add(new AngelTrumpeter());
		cards.add(new Ankuriboh());
		cards.add(new Anthrosaurus());
		cards.add(new ApprenticeIllusionMagician());
		cards.add(new ArmoredBee());
		cards.add(new ArmoredRat());
		cards.add(new ArmoredStarfish());
		cards.add(new ArmoredWhiteBear());
		cards.add(new BarrierStatue());
		cards.add(new BasicInsect());
		cards.add(new BerserkGorilla());
		cards.add(new BlazewingButterfly());
		cards.add(new BloodSucker());
		cards.add(new CelticGuardian());
		cards.add(new ClearKuriboh());
		cards.add(new DarkBlade());
		cards.add(new DarkFactory());
		cards.add(new DrillBug());
		cards.add(new EvilMind());
		cards.add(new FiendSkull());
		cards.add(new FinalFusion());
		cards.add(new FrontierWiseman());
		cards.add(new FusionDevourer());
		cards.add(new FusionWeapon());
		cards.add(new GadgetSoldier());
		cards.add(new GenerationNext());
		cards.add(new GigastoneOmega());
		cards.add(new GlowUpBloom());
		cards.add(new GravityAxe());
		cards.add(new GuardianChimera());
		cards.add(new Hinotama());
		cards.add(new IrisEarthMother());
		cards.add(new JadeKnight());
		cards.add(new Kurivolt());
		cards.add(new LegendarySword());
		cards.add(new LesserDragon());
		cards.add(new Megalosmasher());
		cards.add(new MetalDragon());
		cards.add(new MoltenZombie());
		cards.add(new MorayGreed());
		cards.add(new NaturiaBambooShoot());
		cards.add(new NaturiaBeans());
		cards.add(new NaturiaRock());
		cards.add(new NeoSpacianDarkPanther());
		cards.add(new PharaohBlessing());
		cards.add(new PoisonousWinds());
		cards.add(new Raigeki());
		cards.add(new RainbowMedicine());
		cards.add(new RainbowRuins());
		cards.add(new RevivalJam());
		cards.add(new SangaEarth());
		cards.add(new SuperheavySoulbuster());
		cards.add(new TwinHeadedWolf());
		cards.add(new UnicornBeacon());
		cards.add(new VendreadCharge());
		cards.add(new Zombina());

		// Removed base game
		/*cards.add(new Anger());
		cards.add(new Berserk());
		cards.add(new Brutality());
		cards.add(new Clash());
		cards.add(new Cleave());
		cards.add(new Combust());
		cards.add(new DoubleTap());
		cards.add(new FireBreathing());
		cards.add(new Flex());
		cards.add(new Headbutt());
		cards.add(new Hemokinesis());
		cards.add(new Inflame());
		cards.add(new IronWave());
		cards.add(new Rage());
		cards.add(new RecklessCharge());
		cards.add(new Rupture());
		cards.add(new SearingBlow());
		cards.add(new SeverSoul());
		cards.add(new SwordBoomerang());
		cards.add(new ThunderClap());
		cards.add(new TwinStrike());
		cards.add(new WildStrike());
		cards.add(new Metallicize());
		cards.add(new Armaments());
		cards.add(new Clothesline());
		cards.add(new FlameBarrier());
		cards.add(new Intimidate());
		cards.add(new PerfectedStrike());
		cards.add(new PommelStrike());
		cards.add(new Rampage());
		cards.add(new ShrugItOff());
		cards.add(new Whirlwind());*/

		// Red
		cards.add(new Barricade());
		cards.add(new Bash());
		cards.add(new BattleTrance());
		cards.add(new BloodForBlood());
		cards.add(new Bloodletting());
		cards.add(new Bludgeon());
		cards.add(new BodySlam());
		cards.add(new BurningPact());
		cards.add(new Carnage());
		cards.add(new Corruption());
		cards.add(new DarkEmbrace());
		cards.add(new Defend_Red());
		cards.add(new DemonForm());
		cards.add(new Disarm());
		cards.add(new Dropkick());
		cards.add(new DualWield());
		cards.add(new Entrench());
		cards.add(new Evolve());
		cards.add(new Exhume());
		cards.add(new Feed());
		cards.add(new FeelNoPain());
		cards.add(new FiendFire());
		cards.add(new GhostlyArmor());
		cards.add(new Havoc());
		cards.add(new HeavyBlade());
		cards.add(new Immolate());
		cards.add(new Impervious());
		cards.add(new InfernalBlade());			
		cards.add(new Juggernaut());
		cards.add(new LimitBreak());
		cards.add(new Offering());			
		cards.add(new PowerThrough());
		cards.add(new Pummel());			
		cards.add(new Reaper());
		cards.add(new SecondWind());
		cards.add(new SeeingRed());
		cards.add(new Sentinel());
		cards.add(new Shockwave());
		cards.add(new SpotWeakness());
		cards.add(new Strike_Red());
		cards.add(new TrueGrit());
		cards.add(new Uppercut());
		cards.add(new Warcry());
		return cards;
	}

	public static ArrayList<AbstractCard> pharaohTwo() {
		ArrayList<AbstractCard> cards = new ArrayList<>();

		// Duelist
		cards.add(new SwordsConcealing());
		cards.add(new SpikedGillman());
		cards.add(new ThornMalice());
		cards.add(new Gigaplant());
		cards.add(new GigaCricket());
		cards.add(new GlowUpBloom());
		cards.add(new CatnippedKitty());
		cards.add(new SuperheavyGeneral());
		cards.add(new MillenniumShield());
		cards.add(new SuperheavyBenkei());
		cards.add(new ReadyForIntercepting());
		cards.add(new RoseArcher());
		cards.add(new GravityWarrior());
		cards.add(new NaturiaWhiteOak());
		cards.add(new ObsidianDragon());
		cards.add(new MetalArmoredBug());
		cards.add(new ShiranuiSamurai());
		cards.add(new ShiranuiSamuraisaga());
		cards.add(new ShiranuiSkillsagaSupremacy());
		cards.add(new ShiranuiSolitaire());
		cards.add(new ShiranuiSquire());
		cards.add(new ShiranuiSwordmaster());
		cards.add(new NobleKnightsShieldBearer());
		cards.add(new InsectQueen());
		cards.add(new PredaplantChimerafflesia());
		cards.add(new PredaplantFlytrap());
		cards.add(new PredaplantPterapenthes());
		cards.add(new PredaplantSarraceniant());
		cards.add(new PredaplantSpinodionaea());
		cards.add(new SuperSolarNutrient());
		cards.add(new PinchHopper());
		cards.add(new NaturiaStagBeetle());
		cards.add(new CorrosiveScales());
		cards.add(new PoisonChain());
		cards.add(new AtomicFirefly());
		cards.add(new Aztekipede());
		cards.add(new PoisonMummy());
		cards.add(new PoisonFangs());
		cards.add(new Tsukahagi());
		cards.add(new PoisonousMayakashi());
		cards.add(new Predaponics());
		cards.add(new PharaohBlessing());
		cards.add(new RainbowMedicine());

		// 10 new predas
		// new predaplant
		// new predaplant
		// new predaplant
		// new predaplant
		// new predaplant
		// new predaplant
		// new predaplant
		// new predaplant
		// new predaplant
		// new predaplant

		// Removed base game
		/*cards.add(new Accuracy());
		cards.add(new AfterImage());
		cards.add(new Alchemize());
		cards.add(new BladeDance());
		cards.add(new BouncingFlask());
		cards.add(new Caltrops());
		cards.add(new Catalyst());
		cards.add(new DaggerSpray());
		cards.add(new Dash());
		cards.add(new DeadlyPoison());
		cards.add(new Envenom());
		cards.add(new Footwork());
		cards.add(new GlassKnife());
		cards.add(new GrandFinale());
		cards.add(new InfiniteBlades());
		cards.add(new MasterfulStab());
		cards.add(new NoxiousFumes());
		cards.add(new Outmaneuver());
		cards.add(new PhantasmalKiller());
		cards.add(new PoisonedStab());
		cards.add(new Skewer());
		cards.add(new Slice());
		cards.add(new StormOfSteel());
		cards.add(new WellLaidPlans());*/

		// Green
		cards.add(new Acrobatics());
		cards.add(new Adrenaline());
		cards.add(new AllOutAttack());
		cards.add(new AThousandCuts());
		cards.add(new Backflip());
		cards.add(new Backstab());
		cards.add(new Bane());
		cards.add(new Blur());
		cards.add(new BulletTime());
		cards.add(new Burst());
		cards.add(new CalculatedGamble());
		cards.add(new Choke());
		cards.add(new CloakAndDagger());
		cards.add(new Concentrate());
		cards.add(new CorpseExplosion());
		cards.add(new CripplingPoison());
		cards.add(new DaggerThrow());
		cards.add(new Defend_Green());
		cards.add(new Deflect());
		cards.add(new DieDieDie());
		cards.add(new Distraction());
		cards.add(new DodgeAndRoll());
		cards.add(new Doppelganger());
		cards.add(new EndlessAgony());
		cards.add(new EscapePlan());
		cards.add(new Eviscerate());
		cards.add(new Expertise());
		cards.add(new Finisher());
		cards.add(new Flechettes());
		cards.add(new FlyingKnee());
		cards.add(new HeelHook());
		cards.add(new LegSweep());
		cards.add(new Malaise());
		cards.add(new Neutralize());
		cards.add(new Nightmare());
		cards.add(new PiercingWail());
		cards.add(new Predator());
		cards.add(new Prepared());
		cards.add(new QuickSlash());
		cards.add(new Reflex());
		cards.add(new RiddleWithHoles());
		cards.add(new Setup());
		cards.add(new Strike_Green());
		cards.add(new SuckerPunch());
		cards.add(new Survivor());
		cards.add(new Tactician());
		cards.add(new Terror());
		cards.add(new ToolsOfTheTrade());
		cards.add(new SneakyStrike());
		cards.add(new Unload());
		cards.add(new WraithForm());
		return cards;
	}

	public static ArrayList<AbstractCard> pharaohThree() {
		ArrayList<AbstractCard> cards = new ArrayList<>();

		// Duelist
		cards.add(new AirCrackingStorm());
		cards.add(new AmuletAmbition());
		cards.add(new AncientGearBox());
		cards.add(new ApprenticeIllusionMagician());
		cards.add(new AutorokketDragon());
		cards.add(new BirdParadise());
		cards.add(new BlackRoseMoonlight());
		cards.add(new BlizzardPrincess());
		cards.add(new BlizzardWarrior());
		cards.add(new BlueDragonSummoner());
		cards.add(new BookSecret());
		cards.add(new CharcoalInpachi());
		cards.add(new Chiwen());
		cards.add(new CosmicHorrorGangiel());
		cards.add(new Cryomancer());
		cards.add(new CrystalWingDragon());
		cards.add(new CyberArchfiend());
		cards.add(new CyberDragonVier());
		cards.add(new CyberneticRevolution());
		cards.add(new DarkBlade());
		cards.add(new DarkBurningAttack());
		cards.add(new DarkDustSpirit());
		cards.add(new DarkHorizon());
		cards.add(new DarkHunter());
		cards.add(new DarkMagician());
		cards.add(new DarkMagicianGirl());
		cards.add(new DarkMasterZorc());
		cards.add(new DarkSpider());
		cards.add(new DarkfireDragon());
		cards.add(new DawnKnight());
		cards.add(new DefensiveTactics());
		cards.add(new DiamondDust());
		cards.add(new DiffusionWaveMotion());
		cards.add(new DoomShaman());
		cards.add(new FairyBox());
		cards.add(new Freezadon());
		cards.add(new Frostosaurus());
		cards.add(new FrozenFitzgerald());
		cards.add(new Galactikuriboh());
		cards.add(new GalaxyTyranno());
		cards.add(new GateGuardian());
		cards.add(new GeminiElf());
		cards.add(new GeneralGantal());
		cards.add(new GenexAllyBirdman());
		cards.add(new GhostrickDoll());
		cards.add(new GhostrickFairy());
		cards.add(new GhostrickYukiOnna());
		cards.add(new GiantTrunade());
		cards.add(new GishkiNoellia());
		cards.add(new HazyFlameHydra());
		cards.add(new HeartUnderdog());
		cards.add(new HeavyMechSupportPlatform());
		cards.add(new IceQueen());
		cards.add(new IcyCrevasse());
		cards.add(new InfernoFireBlast());
		cards.add(new InsectPrincess());
		cards.add(new JunkWarrior());
		cards.add(new Kuribot());
		cards.add(new LegendaryFisherman());
		cards.add(new LightningBlade());
		cards.add(new Lightserpent());
		cards.add(new MagicalizedFusion());
		cards.add(new MareMare());
		cards.add(new MasterMagmaBlacksmith());
		cards.add(new MetalDetector());
		cards.add(new MindAir());
		cards.add(new MoltenDestruction());
		cards.add(new Mudballman());
		cards.add(new NitroWarrior());
		cards.add(new Oilman());
		cards.add(new OjamaDeltaHurricane());
		cards.add(new ParallelPortArmor());
		cards.add(new PharaohBlessing());
		cards.add(new QueenAngelRoses());
		cards.add(new QueenDragunDjinn());
		cards.add(new RainbowDarkDragon());
		cards.add(new RainbowMedicine());
		cards.add(new SangaEarth());
		cards.add(new SangaThunder());
		cards.add(new SangaWater());
		cards.add(new SilentDoom());
		cards.add(new Slushy());
		cards.add(new SnowdustDragon());
		cards.add(new SolarWindJammer());
		cards.add(new SwordsBurning());
		cards.add(new Tuningware());
		cards.add(new VoidVanishment());
		cards.add(new WhiteHowling());
		cards.add(new WhiteNightDragon());
		cards.add(new WitchBlackRose());
		cards.add(new YukiMusume());
		cards.add(new YukiOnnaAbsolute());
		cards.add(new YukiOnnaIce());
		cards.add(new ZefraMetaltron());

		// Removed base game


		// Blue
		cards.add(new Aggregate());
		cards.add(new AllForOne());
		cards.add(new Amplify());
		cards.add(new AutoShields());
		cards.add(new BallLightning());
		cards.add(new Barrage());
		cards.add(new BeamCell());
		cards.add(new BiasedCognition());
		cards.add(new Blizzard());
		cards.add(new BootSequence());
		cards.add(new Buffer());
		cards.add(new Capacitor());
		cards.add(new Chaos());
		cards.add(new Chill());
		cards.add(new Claw());
		cards.add(new ColdSnap());
		cards.add(new CompileDriver());
		cards.add(new ConserveBattery());
		cards.add(new Consume());
		cards.add(new Coolheaded());
		cards.add(new CoreSurge());
		cards.add(new CreativeAI());
		cards.add(new Darkness());
		cards.add(new Defend_Blue());
		cards.add(new Defragment());
		cards.add(new DoomAndGloom());
		cards.add(new DoubleEnergy());
		cards.add(new Dualcast());
		cards.add(new EchoForm());
		cards.add(new Electrodynamics());
		cards.add(new Fission());
		cards.add(new ForceField());
		cards.add(new FTL());
		cards.add(new Fusion());
		cards.add(new GeneticAlgorithm());
		cards.add(new Glacier());
		cards.add(new GoForTheEyes());
		cards.add(new Heatsinks());
		cards.add(new HelloWorld());
		cards.add(new Hologram());
		cards.add(new Hyperbeam());
		cards.add(new Leap());
		cards.add(new LockOn());
		cards.add(new Loop());
		cards.add(new MachineLearning());
		cards.add(new Melter());
		cards.add(new MeteorStrike());
		cards.add(new MultiCast());
		cards.add(new Overclock());
		cards.add(new Rainbow());
		cards.add(new Reboot());
		cards.add(new Rebound());
		cards.add(new Recursion());
		cards.add(new Recycle());
		cards.add(new ReinforcedBody());
		cards.add(new Reprogram());
		cards.add(new RipAndTear());
		cards.add(new Scrape());
		cards.add(new Seek());
		cards.add(new SelfRepair());
		cards.add(new Skim());
		cards.add(new Stack());
		cards.add(new StaticDischarge());
		cards.add(new SteamBarrier());
		cards.add(new Storm());
		cards.add(new Streamline());
		cards.add(new Strike_Blue());
		cards.add(new Sunder());
		cards.add(new SweepingBeam());
		cards.add(new Tempest());
		cards.add(new ThunderStrike());
		cards.add(new Turbo());
		cards.add(new Equilibrium());
		cards.add(new WhiteNoise());
		cards.add(new Zap());

		return cards;
	}

	public static ArrayList<AbstractCard> pharaohFour() {
		ArrayList<AbstractCard> cards = new ArrayList<>();

		cards.add(new HerculeanPower());

		cards.add(new Alpha());
		cards.add(new BattleHymn());
		cards.add(new Blasphemy());
		cards.add(new BowlingBash());
		cards.add(new Brilliance());
		cards.add(new CarveReality());
		cards.add(new Collect());
		cards.add(new Conclude());
		cards.add(new ConjureBlade());
		cards.add(new Consecrate());
		cards.add(new Crescendo());
		cards.add(new CrushJoints());
		cards.add(new CutThroughFate());
		cards.add(new DeceiveReality());
		cards.add(new Defend_Watcher());
		cards.add(new DeusExMachina());
		cards.add(new DevaForm());
		cards.add(new Devotion());
		cards.add(new EmptyBody());
		cards.add(new EmptyFist());
		cards.add(new EmptyMind());
		cards.add(new Eruption());
		cards.add(new Establishment());
		cards.add(new Evaluate());
		cards.add(new Fasting());
		cards.add(new FearNoEvil());
		cards.add(new FlurryOfBlows());
		cards.add(new FlyingSleeves());
		cards.add(new FollowUp());
		cards.add(new ForeignInfluence());
		cards.add(new Halt());
		cards.add(new Indignation());
		cards.add(new InnerPeace());
		cards.add(new Judgement());
		cards.add(new JustLucky());
		cards.add(new LessonLearned());
		cards.add(new LikeWater());
		cards.add(new MasterReality());
		cards.add(new Meditate());
		cards.add(new MentalFortress());
		cards.add(new Nirvana());
		cards.add(new Omniscience());
		cards.add(new Perseverance());
		cards.add(new Pray());
		cards.add(new PressurePoints());
		cards.add(new Prostrate());
		cards.add(new Protect());
		cards.add(new Ragnarok());
		cards.add(new ReachHeaven());
		cards.add(new Rushdown());
		cards.add(new Sanctity());
		cards.add(new SandsOfTime());
		cards.add(new SashWhip());
		cards.add(new Scrawl());
		cards.add(new SignatureMove());
		cards.add(new SimmeringFury());
		cards.add(new SpiritShield());
		cards.add(new Strike_Purple());
		cards.add(new Swivel());
		cards.add(new TalkToTheHand());
		cards.add(new Tantrum());
		cards.add(new ThirdEye());
		cards.add(new Tranquility());
		cards.add(new Vault());
		cards.add(new Vigilance());
		cards.add(new Wallop());
		cards.add(new WaveOfTheHand());
		cards.add(new Weave());
		cards.add(new WheelKick());
		cards.add(new WindmillStrike());
		cards.add(new Foresight());
		cards.add(new Wish());
		cards.add(new Worship());
		cards.add(new WreathOfFlame());
		cards.add(new Study());

		return cards;
	}

	public static ArrayList<AbstractCard> pharaohFive() {
		ArrayList<AbstractCard> cards = new ArrayList<>();

		// Red
		cards.add(new Anger());
		cards.add(new Armaments());
		cards.add(new Barricade());
		cards.add(new Bash());
		cards.add(new BattleTrance());
		cards.add(new Berserk());
		cards.add(new BloodForBlood());
		cards.add(new Bloodletting());
		cards.add(new Bludgeon());
		cards.add(new BodySlam());
		cards.add(new Brutality());
		cards.add(new BurningPact());
		cards.add(new Carnage());
		cards.add(new Clash());
		cards.add(new Cleave());
		cards.add(new Clothesline());
		cards.add(new Combust());
		cards.add(new Corruption());
		cards.add(new DarkEmbrace());
		cards.add(new Defend_Red());
		cards.add(new DemonForm());
		cards.add(new Disarm());
		cards.add(new DoubleTap());
		cards.add(new Dropkick());
		cards.add(new DualWield());
		cards.add(new Entrench());
		cards.add(new Evolve());
		cards.add(new Exhume());
		cards.add(new Feed());
		cards.add(new FeelNoPain());
		cards.add(new FiendFire());
		cards.add(new FireBreathing());
		cards.add(new FlameBarrier());
		cards.add(new Flex());
		cards.add(new GhostlyArmor());
		cards.add(new Havoc());
		cards.add(new Headbutt());
		cards.add(new HeavyBlade());
		cards.add(new Hemokinesis());
		cards.add(new Immolate());
		cards.add(new Impervious());
		cards.add(new InfernalBlade());
		cards.add(new Inflame());
		cards.add(new Intimidate());
		cards.add(new IronWave());
		cards.add(new Juggernaut());
		cards.add(new LimitBreak());
		cards.add(new Metallicize());
		cards.add(new Offering());
		cards.add(new PerfectedStrike());
		cards.add(new PommelStrike());
		cards.add(new PowerThrough());
		cards.add(new Pummel());
		cards.add(new Rage());
		cards.add(new Rampage());
		cards.add(new Reaper());
		cards.add(new RecklessCharge());
		cards.add(new Rupture());
		cards.add(new SearingBlow());
		cards.add(new SecondWind());
		cards.add(new SeeingRed());
		cards.add(new Sentinel());
		cards.add(new SeverSoul());
		cards.add(new Shockwave());
		cards.add(new ShrugItOff());
		cards.add(new SpotWeakness());
		cards.add(new Strike_Red());
		cards.add(new SwordBoomerang());
		cards.add(new ThunderClap());
		cards.add(new TrueGrit());
		cards.add(new TwinStrike());
		cards.add(new Uppercut());
		cards.add(new Warcry());
		cards.add(new Whirlwind());
		cards.add(new WildStrike());

		// Green
		cards.add(new Accuracy());
		cards.add(new Acrobatics());
		cards.add(new Adrenaline());
		cards.add(new AfterImage());
		cards.add(new Alchemize());
		cards.add(new AllOutAttack());
		cards.add(new AThousandCuts());
		cards.add(new Backflip());
		cards.add(new Backstab());
		cards.add(new Bane());
		cards.add(new BladeDance());
		cards.add(new Blur());
		cards.add(new BouncingFlask());
		cards.add(new BulletTime());
		cards.add(new Burst());
		cards.add(new CalculatedGamble());
		cards.add(new Caltrops());
		cards.add(new Catalyst());
		cards.add(new Choke());
		cards.add(new CloakAndDagger());
		cards.add(new Concentrate());
		cards.add(new CorpseExplosion());
		cards.add(new CripplingPoison());
		cards.add(new DaggerSpray());
		cards.add(new DaggerThrow());
		cards.add(new Dash());
		cards.add(new DeadlyPoison());
		cards.add(new Defend_Green());
		cards.add(new Deflect());
		cards.add(new DieDieDie());
		cards.add(new Distraction());
		cards.add(new DodgeAndRoll());
		cards.add(new Doppelganger());
		cards.add(new EndlessAgony());
		cards.add(new Envenom());
		cards.add(new EscapePlan());
		cards.add(new Eviscerate());
		cards.add(new Expertise());
		cards.add(new Finisher());
		cards.add(new Flechettes());
		cards.add(new FlyingKnee());
		cards.add(new Footwork());
		cards.add(new GlassKnife());
		cards.add(new GrandFinale());
		cards.add(new HeelHook());
		cards.add(new InfiniteBlades());
		cards.add(new LegSweep());
		cards.add(new Malaise());
		cards.add(new MasterfulStab());
		cards.add(new Neutralize());
		cards.add(new Nightmare());
		cards.add(new NoxiousFumes());
		cards.add(new Outmaneuver());
		cards.add(new PhantasmalKiller());
		cards.add(new PiercingWail());
		cards.add(new PoisonedStab());
		cards.add(new Predator());
		cards.add(new Prepared());
		cards.add(new QuickSlash());
		cards.add(new Reflex());
		cards.add(new RiddleWithHoles());
		cards.add(new Setup());
		cards.add(new Skewer());
		cards.add(new Slice());
		cards.add(new StormOfSteel());
		cards.add(new Strike_Green());
		cards.add(new SuckerPunch());
		cards.add(new Survivor());
		cards.add(new Tactician());
		cards.add(new Terror());
		cards.add(new ToolsOfTheTrade());
		cards.add(new SneakyStrike());
		cards.add(new Unload());
		cards.add(new WellLaidPlans());
		cards.add(new WraithForm());

		// Blue
		cards.add(new Aggregate());
		cards.add(new AllForOne());
		cards.add(new Amplify());
		cards.add(new AutoShields());
		cards.add(new BallLightning());
		cards.add(new Barrage());
		cards.add(new BeamCell());
		cards.add(new BiasedCognition());
		cards.add(new Blizzard());
		cards.add(new BootSequence());
		cards.add(new Buffer());
		cards.add(new Capacitor());
		cards.add(new Chaos());
		cards.add(new Chill());
		cards.add(new Claw());
		cards.add(new ColdSnap());
		cards.add(new CompileDriver());
		cards.add(new ConserveBattery());
		cards.add(new Consume());
		cards.add(new Coolheaded());
		cards.add(new CoreSurge());
		cards.add(new CreativeAI());
		cards.add(new Darkness());
		cards.add(new Defend_Blue());
		cards.add(new Defragment());
		cards.add(new DoomAndGloom());
		cards.add(new DoubleEnergy());
		cards.add(new Dualcast());
		cards.add(new EchoForm());
		cards.add(new Electrodynamics());
		cards.add(new Fission());
		cards.add(new ForceField());
		cards.add(new FTL());
		cards.add(new Fusion());
		cards.add(new GeneticAlgorithm());
		cards.add(new Glacier());
		cards.add(new GoForTheEyes());
		cards.add(new Heatsinks());
		cards.add(new HelloWorld());
		cards.add(new Hologram());
		cards.add(new Hyperbeam());
		cards.add(new Leap());
		cards.add(new LockOn());
		cards.add(new Loop());
		cards.add(new MachineLearning());
		cards.add(new Melter());
		cards.add(new MeteorStrike());
		cards.add(new MultiCast());
		cards.add(new Overclock());
		cards.add(new Rainbow());
		cards.add(new Reboot());
		cards.add(new Rebound());
		cards.add(new Recursion());
		cards.add(new Recycle());
		cards.add(new ReinforcedBody());
		cards.add(new Reprogram());
		cards.add(new RipAndTear());
		cards.add(new Scrape());
		cards.add(new Seek());
		cards.add(new SelfRepair());
		cards.add(new Skim());
		cards.add(new Stack());
		cards.add(new StaticDischarge());
		cards.add(new SteamBarrier());
		cards.add(new Storm());
		cards.add(new Streamline());
		cards.add(new Strike_Blue());
		cards.add(new Sunder());
		cards.add(new SweepingBeam());
		cards.add(new Tempest());
		cards.add(new ThunderStrike());
		cards.add(new Turbo());
		cards.add(new Equilibrium());
		cards.add(new WhiteNoise());
		cards.add(new Zap());

		// Purple
		cards.add(new Alpha());
		cards.add(new BattleHymn());
		cards.add(new Blasphemy());
		cards.add(new BowlingBash());
		cards.add(new Brilliance());
		cards.add(new CarveReality());
		cards.add(new Collect());
		cards.add(new Conclude());
		cards.add(new ConjureBlade());
		cards.add(new Consecrate());
		cards.add(new Crescendo());
		cards.add(new CrushJoints());
		cards.add(new CutThroughFate());
		cards.add(new DeceiveReality());
		cards.add(new Defend_Watcher());
		cards.add(new DeusExMachina());
		cards.add(new DevaForm());
		cards.add(new Devotion());
		cards.add(new EmptyBody());
		cards.add(new EmptyFist());
		cards.add(new EmptyMind());
		cards.add(new Eruption());
		cards.add(new Establishment());
		cards.add(new Evaluate());
		cards.add(new Fasting());
		cards.add(new FearNoEvil());
		cards.add(new FlurryOfBlows());
		cards.add(new FlyingSleeves());
		cards.add(new FollowUp());
		cards.add(new ForeignInfluence());
		cards.add(new Halt());
		cards.add(new Indignation());
		cards.add(new InnerPeace());
		cards.add(new Judgement());
		cards.add(new JustLucky());
		cards.add(new LessonLearned());
		cards.add(new LikeWater());
		cards.add(new MasterReality());
		cards.add(new Meditate());
		cards.add(new MentalFortress());
		cards.add(new Nirvana());
		cards.add(new Omniscience());
		cards.add(new Perseverance());
		cards.add(new Pray());
		cards.add(new PressurePoints());
		cards.add(new Prostrate());
		cards.add(new Protect());
		cards.add(new Ragnarok());
		cards.add(new ReachHeaven());
		cards.add(new Rushdown());
		cards.add(new Sanctity());
		cards.add(new SandsOfTime());
		cards.add(new SashWhip());
		cards.add(new Scrawl());
		cards.add(new SignatureMove());
		cards.add(new SimmeringFury());
		cards.add(new SpiritShield());
		cards.add(new Strike_Purple());
		cards.add(new Swivel());
		cards.add(new TalkToTheHand());
		cards.add(new Tantrum());
		cards.add(new ThirdEye());
		cards.add(new Tranquility());
		cards.add(new Vault());
		cards.add(new Vigilance());
		cards.add(new Wallop());
		cards.add(new WaveOfTheHand());
		cards.add(new Weave());
		cards.add(new WheelKick());
		cards.add(new WindmillStrike());
		cards.add(new Foresight());
		cards.add(new Wish());
		cards.add(new Worship());
		cards.add(new WreathOfFlame());
		cards.add(new Study());

		// Duelist


		return cards;
	}
	
	public static ArrayList<AbstractCard> oneRandom() {
        return new ArrayList<>(GlobalPoolHelper.oneRandom());
	}
	
	public static ArrayList<AbstractCard> twoRandom() {
        return new ArrayList<>(GlobalPoolHelper.twoRandom());
	}

	public static ArrayList<AbstractCard> basicOne() {
		String deckName = "Pharaoh I";
		boolean smallBasicSet = DuelistMod.persistentDuelistData.CardPoolSettings.getSmallBasicSet();
		if (smallBasicSet) {
			return BasicPool.smallBasic(deckName);
		} else {
			return BasicPool.fullBasic(deckName);
		}
	}

	public static ArrayList<AbstractCard> basicTwo() {
		String deckName = "Pharaoh II";
		boolean smallBasicSet = DuelistMod.persistentDuelistData.CardPoolSettings.getSmallBasicSet();
		if (smallBasicSet) {
			return BasicPool.smallBasic(deckName);
		} else {
			return BasicPool.fullBasic(deckName);
		}
	}

	public static ArrayList<AbstractCard> basicThree() {
		String deckName = "Pharaoh III";
		boolean smallBasicSet = DuelistMod.persistentDuelistData.CardPoolSettings.getSmallBasicSet();
		if (smallBasicSet) {
			return BasicPool.smallBasic(deckName);
		} else {
			return BasicPool.fullBasic(deckName);
		}
	}

	public static ArrayList<AbstractCard> basicFour() {
		String deckName = "Pharaoh IV";
		boolean smallBasicSet = DuelistMod.persistentDuelistData.CardPoolSettings.getSmallBasicSet();
		if (smallBasicSet) {
			return BasicPool.smallBasic(deckName);
		} else {
			return BasicPool.fullBasic(deckName);
		}
	}

	public static ArrayList<AbstractCard> basicFive() {
		String deckName = "Pharaoh V";
		boolean smallBasicSet = DuelistMod.persistentDuelistData.CardPoolSettings.getSmallBasicSet();
		if (smallBasicSet) {
			return BasicPool.smallBasic(deckName);
		} else {
			return BasicPool.fullBasic(deckName);
		}
	}
}
