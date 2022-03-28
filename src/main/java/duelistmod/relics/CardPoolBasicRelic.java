package duelistmod.relics;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistRelic;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.pools.aqua.*;
import duelistmod.cards.pools.aqua.KaiserSeaHorse;
import duelistmod.cards.pools.dragons.*;
import duelistmod.cards.pools.insects.*;
import duelistmod.cards.pools.machine.*;
import duelistmod.cards.pools.naturia.*;
import duelistmod.cards.pools.warrior.*;
import duelistmod.cards.pools.zombies.*;
import duelistmod.characters.TheDuelist;
import duelistmod.helpers.Util;
import duelistmod.interfaces.*;

public class CardPoolBasicRelic extends DuelistRelic implements ClickableRelic, VisitFromAnubisRemovalFilter
{
	// ID, images, text.
	public static final String ID = DuelistMod.makeID("CardPoolBasicRelic");
	public static final String IMG =  DuelistMod.makeRelicPath("CardPoolBasicRelic.png");
	public static final String OUTLINE =  DuelistMod.makeRelicOutlinePath("CardPoolRelic_Outline.png");
	public CardGroup pool;

	public CardPoolBasicRelic() {
		super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);
		pool = new CardGroup(CardGroupType.MASTER_DECK);
		setDescription();
	}
	
	public void refreshPool()
	{
		if (DuelistMod.duelistChar != null)
		{
			pool.clear();
			if (AbstractDungeon.colorlessCardPool.group != null)
			{
				pool.group.addAll(AbstractDungeon.colorlessCardPool.group);
			}
			//pool.group.addAll(getTempDebugSet());
			setDescription();
		}
	}

	@SuppressWarnings("unused")
	private ArrayList<AbstractCard> getTempDebugSet() {
		ArrayList<AbstractCard> temp = new ArrayList<>();
		temp.add(new CyberRaider());	
		temp.add(new SatelliteCannon());	
		temp.add(new MaxWarrior());	
		temp.add(new CircleFireKings());	
		temp.add(new OnslaughtFireKings());	
		temp.add(new WhiteHowling());	
		temp.add(new Alpacaribou());	
		temp.add(new Anteater());	
		temp.add(new AttackTheMoon());	
		temp.add(new BarkionBark());	
		temp.add(new Blockman());	
		temp.add(new BrainCrusher());	
		temp.add(new Canyon());	
		temp.add(new CastleGate());	
		temp.add(new GolemSentry());	
		temp.add(new ClosedForest());	
		temp.add(new CrystalRose());	
		temp.add(new DestroyerGolem());	
		temp.add(new DigitalBug());	
		temp.add(new DummyGolem());	
		temp.add(new EarthEffigy());	
		temp.add(new ElephantStatueBlessing());	
		temp.add(new ElephantStatueDisaster());	
		temp.add(new EvilswarmGolem());	
		temp.add(new ExterioFang());	
		temp.add(new FossilDig());	
		temp.add(new FossilExcavation());	
		temp.add(new FossilTusker());	
		temp.add(new GateBlocker());	
		temp.add(new GemArmadillo());	
		temp.add(new GemElephant());	
		temp.add(new GemKnightAlexandrite());	
		temp.add(new GemKnightCrystal());	
		temp.add(new GemKnightEmerald());	
		temp.add(new GemKnightLapis());	
		temp.add(new GemKnightLazuli());	
		temp.add(new GemKnightMasterDiamond());	
		temp.add(new GemKnightObsidian());	
		temp.add(new GemKnightPearl());	
		temp.add(new GemKnightQuartz());	
		temp.add(new GemKnightZirconia());	
		temp.add(new HuntingInstinct());
		temp.add(new LairWire());
		temp.add(new LeodrakeMane());
		temp.add(new LonefireBlossom());
		temp.add(new LuminousMoss());
		temp.add(new NaturalDisaster());
		temp.add(new NatureReflection());
		temp.add(new NaturiaAntjaw());
		temp.add(new NaturiaBambooShoot());
		temp.add(new NaturiaBarkion());
		temp.add(new NaturiaBeans());
		temp.add(new NaturiaBeetle());
		temp.add(new NaturiaBrambi());
		temp.add(new NaturiaButterfly());
		temp.add(new NaturiaCherries());
		temp.add(new NaturiaCosmobeet());
		temp.add(new NaturiaEggplant());
		temp.add(new NaturiaExterio());
		temp.add(new NaturiaGaiastrio());
		temp.add(new NaturiaHydrangea());
		temp.add(new NaturiaLadybug());
		temp.add(new NaturiaLeodrake());
		temp.add(new NaturiaMarron());
		temp.add(new NaturiaMosquito());
		temp.add(new NaturiaParadizo());
		temp.add(new NaturiaRagweed());
		temp.add(new NaturiaRock());
		temp.add(new NaturiaSpiderfang());
		temp.add(new NaturiaStagBeetle());
		temp.add(new NaturiaStinkbug());
		temp.add(new NaturiaStrawberry());
		temp.add(new NaturiaSunflower());
		temp.add(new NaturiaTulip());
		temp.add(new NaturiaVein());
		temp.add(new NaturiaWhiteOak());
		temp.add(new SeedSacredTree());
		temp.add(new SeismicShockwave());
		temp.add(new SpacetimeTranscendence());
		temp.add(new SummoningSwarm());
		temp.add(new WildNatureRelease());
		temp.add(new WormBait());
		temp.add(new CorrosiveScales());
		temp.add(new Pollinosis());
		temp.add(new DemiseLand());
		temp.add(new SurvivalInstinct());
		temp.add(new ConvulsionNature());
		temp.add(new NaturiaForest());
		temp.add(new CatapultZone());
		temp.add(new GolemSentry());
		temp.add(new GraniteLoyalist());
		temp.add(new LostGuardian());
		temp.add(new MagicHoleGolem());
		temp.add(new MasterMagmaBlacksmith());
		temp.add(new MegarockDragon());
		temp.add(new MillenniumGolem());
		temp.add(new ObsidianDragon());
		temp.add(new ReleaseFromStone());
		temp.add(new RockstoneWarrior());
		temp.add(new StoneDragon());
		temp.add(new WeepingIdol());
		temp.add(new Solidarity());
		temp.add(new PoisonChain());
		temp.add(new SlotMachine());
		temp.add(new BeeListSoldier());
		temp.add(new BiteBug());
		temp.add(new DragonDowser());
		temp.add(new HunterSpider());
		temp.add(new NeoBug());
		temp.add(new RazorLizard());
		temp.add(new TornadoDragon());
		temp.add(new AtomicFirefly());
		temp.add(new CobraJar());
		temp.add(new DarkSpider());
		temp.add(new FirestormProminence());
		temp.add(new Gagagigo());
		temp.add(new GroundSpider());
		temp.add(new KarakuriSpider());
		temp.add(new Lightserpent());
		temp.add(new MetalArmoredBug());
		temp.add(new RelinquishedSpider());
		temp.add(new ZefraMetaltron());
		temp.add(new ReptiliannePoison());
		temp.add(new SpiderWeb());
		temp.add(new BugEmergency());
		temp.add(new BeakedSnake());
		temp.add(new BigInsect());
		temp.add(new BlazewingButterfly());
		temp.add(new DrillBug());
		temp.add(new GiantPairfish());
		temp.add(new HerculesBeetle());
		temp.add(new Suanni());
		temp.add(new Yazi());
		temp.add(new Aztekipede());
		temp.add(new BirdParadise());
		temp.add(new Chiwen());
		temp.add(new PoseidonBeetle());
		temp.add(new Greatfly());
		temp.add(new InsectPrincess());
		temp.add(new Inzektron());
		temp.add(new MareMare());
		temp.add(new PoisonMummy());
		temp.add(new ResonanceInsect());
		temp.add(new PoisonOldMan());
		temp.add(new BugSignal());
		temp.add(new BugMatrix());
		temp.add(new Forest());
		temp.add(new Denglong());
		temp.add(new DestructionCyclone());
		temp.add(new GigaCricket());
		temp.add(new GigaMantis());
		temp.add(new HowlingInsect());
		temp.add(new LinkSpider());
		temp.add(new Taotie());
		temp.add(new Zektahawk());
		temp.add(new Zektarrow());
		temp.add(new Zektkaliber());
		temp.add(new DarkBug());
		temp.add(new InsectKing());
		temp.add(new MetamorphInsectQueen());
		temp.add(new MirrorLadybug());
		temp.add(new PoisonousMayakashi());
		temp.add(new SkullMarkLadybug());
		temp.add(new PoisonousWinds());
		temp.add(new SpiderEgg());
		temp.add(new IgnisHeat());
		temp.add(new PoisonFangs());
		temp.add(new SpiritualForest());
		temp.add(new WallThorns());
		temp.add(new Bixi());
		temp.add(new ArmedDragon10());
		temp.add(new ArmedDragon7());
		temp.add(new AtomicScrapDragon());
		temp.add(new Beatraptor());
		temp.add(new BlackBrachios());
		temp.add(new BlackBrutdrago());
		temp.add(new BlackPtera());
		temp.add(new BlackStego());
		temp.add(new BlackTyranno());
		temp.add(new BlackVeloci());
		temp.add(new BurstBreath());
		temp.add(new ChimeratechOverdragon());
		temp.add(new CyberDinosaur());
		temp.add(new CyberDragonInfinity());
		temp.add(new CyberEndDragon());
		temp.add(new CyberTwinDragon());
		temp.add(new CyberValley());
		temp.add(new DarkDriceratops());
		temp.add(new DarkHorus());
		temp.add(new Destroyersaurus());
		temp.add(new Earthquake());
		temp.add(new EvolutionBurst());
		temp.add(new FireDarts());
		temp.add(new GenesisDragon());
		temp.add(new Hydrogeddon());
		temp.add(new InfernityDoomDragon());
		temp.add(new Kabazauls());
		temp.add(new Lancephorhynchus());
		temp.add(new MadFlameKaiju());
		temp.add(new MadSwordBeast());
		temp.add(new MythicWaterDragon());
		temp.add(new Pyrorex());
		temp.add(new Sabersaurus());
		temp.add(new SpiralFlameStrike());
		temp.add(new StampingDestruction());
		temp.add(new TailSwipe());
		temp.add(new VoidOgreDragon());
		temp.add(new AncientDragon());
		temp.add(new AncientPixieDragon());
		temp.add(new Anthrosaurus());
		temp.add(new AquaDolphin());
		temp.add(new ArmedDragon3());
		temp.add(new ArmedDragon5());
		temp.add(new ArmedProtectorDragon());
		temp.add(new ArtifactIgnition());
		temp.add(new Babycerasaurus());
		temp.add(new BackupSoldier());
		temp.add(new BerserkerSoul());
		temp.add(new BlueDuston());
		temp.add(new ClawHermos());
		temp.add(new CloudianGhost());
		temp.add(new CoralDragon());
		temp.add(new CyberDragonCore());
		temp.add(new CyberDragonDrei());
		temp.add(new CyberDragonNachster());
		temp.add(new CyberDragonVier());
		temp.add(new CyberEltanin());
		temp.add(new CyberRevsystem());
		temp.add(new CyberneticOverflow());
		temp.add(new CyberneticRevolution());
		temp.add(new DefenseDraw());
		temp.add(new DefenseZone());
		temp.add(new DefensiveTactics());
		temp.add(new Dracocension());
		temp.add(new DreadnoughtDreadnoid());
		temp.add(new Duoterion());
		temp.add(new EvilMind());
		temp.add(new FangCritias());
		temp.add(new Freezadon());
		temp.add(new FrostflameDragon());
		temp.add(new Frostosaurus());
		temp.add(new FrozenFitzgerald());
		temp.add(new GalaxyTyranno());
		temp.add(new GoldSarcophagus());
		temp.add(new GuardragonJusticia());
		temp.add(new HazyFlameHydra());
		temp.add(new HymnOfLight());
		temp.add(new MegafleetDragon());
		temp.add(new MoltenDestruction());
		temp.add(new OneDayPeace());
		temp.add(new RedDragonArchfiend());
		temp.add(new SafeZone());
		temp.add(new SauropodBrachion());
		temp.add(new ScrapIronScarecrow());
		temp.add(new SoulCharge());
		temp.add(new SphereChaos());
		temp.add(new StarlightRoad());
		temp.add(new SuperStridentBlaze());
		temp.add(new SurvivalFittest());
		temp.add(new ThreateningRoar());
		temp.add(new UltraEvolutionPill());
		temp.add(new VeilDarkness());
		temp.add(new VolcanicEruption());
		temp.add(new WaterDragonCluster());
		temp.add(new ArtifactSanctum());
		temp.add(new AutorokketDragon());
		temp.add(new BerserkScales());
		temp.add(new Berserking());
		temp.add(new CastleDragonSouls());
		temp.add(new CyberDragonSieger());
		temp.add(new CyberEmergency());
		temp.add(new CyberLaserDragon());
		temp.add(new DragonMastery());
		temp.add(new DragonMirror());
		temp.add(new DragonRavine());
		temp.add(new DragonTreasure());
		temp.add(new GalaxySoldier());
		temp.add(new JurassicImpact());
		temp.add(new LostWorld());
		temp.add(new SilverWing());
		temp.add(new SystemDown());
		temp.add(new Vandalgyon());
		temp.add(new BreakDraw());
		temp.add(new ElectromagneticTurtle());
		temp.add(new Geargiauger());
		temp.add(new GoldGadget());
		temp.add(new PlatinumGadget());
		temp.add(new PsychicShockwave());
		temp.add(new JinzoLord());
		temp.add(new JadeKnight());
		temp.add(new HeavyMetalRaiders());
		temp.add(new HeavyMechSupportArmor());
		temp.add(new HeavyMechSupportPlatform());
		temp.add(new AllySalvo());
		temp.add(new DoubleTool());
		temp.add(new FrontlineObserver());
		temp.add(new HeavyFreightTrainDerricane());
		temp.add(new CardsFromTheSky());
		temp.add(new SevenCompleted());
		temp.add(new AirCrackingStorm());
		temp.add(new Submarineroid());
		temp.add(new GearGigant());
		temp.add(new SolarWindJammer());
		temp.add(new AbyssDweller());
		temp.add(new Deskbot003());
		temp.add(new Deskbot006());
		temp.add(new Deskbot007());
		temp.add(new Deskbot008());
		temp.add(new GenexAllyBirdman());
		temp.add(new GenexNeutron());
		temp.add(new MachineKingPrototype());
		temp.add(new PerfectMachineKing());
		temp.add(new Tuningware());		
		temp.add(new MessengerPeace());
		temp.add(new WonderGarage());
		temp.add(new RoboticKnight());
		temp.add(new Factory100Machines());
		temp.add(new Geartown());
		temp.add(new UnionHangar());
		temp.add(new AncientGearWorkshop());
		temp.add(new RevolvingSwitchyard());
		temp.add(new ScrapBeast());
		temp.add(new AncientGearReactorDragon());
		temp.add(new MetalholdMovingBlockade());
		temp.add(new MachinaCannon());
		temp.add(new TimeSeal());
		temp.add(new OrcustCrescendo());
		temp.add(new ParallelPortArmor());
		temp.add(new PineappleBlast());
		temp.add(new BlackSalvo());
		temp.add(new Flashbang());
		temp.add(new BlindDestruction());
		temp.add(new NightmareWheel());
		temp.add(new PortableBatteryPack());
		temp.add(new QuillboltHedgehog());
		temp.add(new MetalDetector());
		temp.add(new SolemnStrike());
		temp.add(new SolemnWarning());
		temp.add(new QuickCharger());
		temp.add(new ZONE());
		temp.add(new Apoqliphort());
		temp.add(new Shekhinaga());
		temp.add(new Quariongandrax());
		temp.add(new JunkSpeeder());
		temp.add(new AbyssDweller());
		temp.add(new AbyssSoldier());
		temp.add(new AbyssalKingshark());
		temp.add(new AegisOceanDragon());
		temp.add(new Akihiron());
		temp.add(new Ameba());
		temp.add(new AquaSnake());
		temp.add(new AquaactressArowana());
		temp.add(new AquaactressGuppy());
		temp.add(new AquaactressTetra());
		temp.add(new AquamirrorCycle());
		temp.add(new ArmoredStarfish());
		temp.add(new AtlanteanHeavyInfantry());
		temp.add(new BarrierStatue());
		temp.add(new BoulderTortoise());
		temp.add(new BubbleBringer());
		temp.add(new ChrysalisDolphin());
		temp.add(new CitadelWhale());
		temp.add(new ColdEnchanter());
		temp.add(new ColdFeet());
		temp.add(new ColdWave());
		temp.add(new CraniumFish());
		temp.add(new Cryomancer());
		temp.add(new CyberShark());
		temp.add(new DeepSweeper());
		temp.add(new DeepseaShark());
		temp.add(new DepthShark());
		temp.add(new Dewdark());
		temp.add(new DewlorenTigerKing());
		temp.add(new Dorover());
		temp.add(new DupeFrog());
		temp.add(new ElementalBurst());
		temp.add(new EnchantingMermaid());
		temp.add(new EvigishkiGustkraken());
		temp.add(new EvigishkiLevianima());
		temp.add(new EvigishkiMerrowgeist());
		temp.add(new EvigishkiMindAugus());
		temp.add(new EvigishkiSoulOgre());
		temp.add(new EvigishkiTetrogre());
		temp.add(new FishDepthCharge());
		temp.add(new FishborgArcher());
		temp.add(new FishborgBlaster());
		temp.add(new FishborgDoctor());
		temp.add(new FishborgLauncher());
		temp.add(new FishborgPlanter());
		temp.add(new FreezingBeast());
		temp.add(new GamecieltheSeaTurtleKaiju());
		temp.add(new GeneralGantal());
		temp.add(new GiantPairfish());
		temp.add(new GiantRedSeasnake());
		temp.add(new GiantTurtle());
		temp.add(new GishkiAquamirror());
		temp.add(new GishkiAriel());
		temp.add(new GishkiAvance());
		temp.add(new GishkiBeast());
		temp.add(new GishkiEmilia());
		temp.add(new GishkiMarker());
		temp.add(new GishkiMollusk());
		temp.add(new GishkiNoellia());
		temp.add(new GishkiPsychelone());
		temp.add(new GishkiReliever());
		temp.add(new GishkiShadow());
		temp.add(new GishkiZielgigas());
		temp.add(new GoraTurtle());
		temp.add(new GraydleAlligator());
		temp.add(new GraydleCobra());
		temp.add(new GraydleCombat());
		temp.add(new GraydleDragon());
		temp.add(new GraydleEagle());
		temp.add(new GraydleImpact());
		temp.add(new GraydleParasite());
		temp.add(new GraydleSlime());
		temp.add(new HighTideGyojin());
		temp.add(new HumanoidSlime());
		temp.add(new Hyosube());
		temp.add(new IceHand());
		temp.add(new ImperialCustom());
		temp.add(new KaiserSeaSnake());
		temp.add(new KoakiMeiruIce());
		temp.add(new LeviairSeaDragon());
		temp.add(new LiquidBeast());
		temp.add(new MadLobster());
		temp.add(new MermaidKnight());
		temp.add(new MermailAbyssalacia());
		temp.add(new MorayGreed());
		temp.add(new NightmarePenguin());
		temp.add(new NimbleAngler());
		temp.add(new NimbleManta());
		temp.add(new NimbleSunfish());
		temp.add(new OldWhiteTurtle());
		temp.add(new Salvage());
		temp.add(new SeaLordAmulet());
		temp.add(new Skystarray());
		temp.add(new StarBoy());
		temp.add(new TerrorkingSalmon());
		temp.add(new ThunderSeaHorse());
		temp.add(new TorrentialReborn());
		temp.add(new TorrentialTribute());
		temp.add(new Unifrog());
		temp.add(new WaterSpirit());
		temp.add(new FuriousSeaKing());
		temp.add(new LegendaryOcean());
		temp.add(new SplashCapture());
		temp.add(new Wetlands());
		temp.add(new WhiteAuraWhale());
		temp.add(new HyperancientShark());
		temp.add(new KaiserSeaHorse());
		temp.add(new TripodFish());
		temp.add(new UnshavenAngler());
		temp.add(new WaterHazard());
		temp.add(new EagleShark());
		temp.add(new BookLife());
		temp.add(new CrimsonKnightVampire());
		temp.add(new VampireBaby());
		temp.add(new VampireDesire());
		temp.add(new VampireDomain());
		temp.add(new VampireDomination());
		temp.add(new VampireDragon());
		temp.add(new VampireDuke());
		temp.add(new VampireFamiliar());
		temp.add(new VampireGrimson());
		temp.add(new VampireHunter());
		temp.add(new VampireKingdom());
		temp.add(new VampireLady());
		temp.add(new VampireRedBaron());
		temp.add(new VampireRetainer());
		temp.add(new VampireScarletScourge());
		temp.add(new VampireSorcerer());
		temp.add(new VampireSucker());
		temp.add(new VampireTakeover());
		temp.add(new VampireVamp());
		temp.add(new SuperPolymerization());
		temp.add(new VampireAwakening());
		temp.add(new AbsorbFusion());
		temp.add(new AntiFusionDevice());
		temp.add(new AshBlossom());
		temp.add(new BattleFusion());
		temp.add(new BloodSucker());
		temp.add(new BoneMouse());
		temp.add(new Bonecrusher());
		temp.add(new BookEclipse());
		temp.add(new BookMoon());
		temp.add(new BookTaiyou());
		temp.add(new BrilliantFusion());
		temp.add(new BurningSkullHead());
		temp.add(new CallHaunted());
		temp.add(new CalledByGrave());
		temp.add(new Chewbone());
		temp.add(new Chopman());
		temp.add(new Dakki());
		temp.add(new DarkDustSpirit());
		temp.add(new DecayedCommander());
		temp.add(new DimensionBurial());
		temp.add(new DimensionFusion());
		temp.add(new DoomkingBalerdroch());
		temp.add(new EnmaJudgement());
		temp.add(new FinalFusion());
		temp.add(new FireReaper());
		temp.add(new FlashFusion());
		temp.add(new FossilSkullConvoy());
		temp.add(new FossilSkullKing());
		temp.add(new FossilSkullbuggy());
		temp.add(new FossilSkullknight());
		temp.add(new FragmentFusion());
		temp.add(new FusionDevourer());
		temp.add(new FusionGuard());
		temp.add(new FusionTag());
		temp.add(new FusionWeapon());
		temp.add(new FusionFire());
		temp.add(new Gashadokuro());
		temp.add(new GhostBelle());
		temp.add(new GhostOgre());
		temp.add(new GhostReaper());
		temp.add(new GhostSister());
		temp.add(new GhostrickAlucard());
		temp.add(new GhostrickAngel());
		temp.add(new GhostrickBreak());
		temp.add(new GhostrickDoll());
		temp.add(new GhostrickDullahan());
		temp.add(new GhostrickFairy());
		temp.add(new GhostrickGhoul());
		temp.add(new GhostrickJackfrost());
		temp.add(new GhostrickGoRound());
		temp.add(new GhostrickJiangshi());
		temp.add(new GhostrickLantern());
		temp.add(new GhostrickMansion());
		temp.add(new GhostrickMary());
		temp.add(new GhostrickMummy());
		temp.add(new GhostrickMuseum());
		temp.add(new GhostrickNight());
		temp.add(new GhostrickParade());
		temp.add(new GhostrickRenovation());
		temp.add(new GhostrickScare());
		temp.add(new GhostrickSkeleton());
		temp.add(new GhostrickStein());
		temp.add(new GhostrickVanish());
		temp.add(new GhostrickWarwolf());
		temp.add(new GhostrickYeti());
		temp.add(new GhostrickNekomusume());
		temp.add(new GhostrickSocuteboss());
		temp.add(new GhostrickSpecter());
		temp.add(new GhostrickWitch());
		temp.add(new GhostrickYukiOnna());
		temp.add(new GigastoneOmega());
		temp.add(new Hajun());
		temp.add(new ImmortalRuler());
		temp.add(new InstantFusion());
		temp.add(new LichLord());
		temp.add(new MagicalGhost());
		temp.add(new MagicalizedFusion());
		temp.add(new MammothGraveyard());
		temp.add(new MayakashiReturn());
		temp.add(new MayakashiWinter());
		temp.add(new MechMoleZombie());
		temp.add(new Mezuki());
		temp.add(new MiracleFusion());
		temp.add(new Miscellaneousaurus());
		temp.add(new Mispolymerization());
		temp.add(new NecroFusion());
		temp.add(new Necroface());
		temp.add(new NecroworldBanshee());
		temp.add(new NightmareHorse());
		temp.add(new OboroGuruma());
		temp.add(new OverpoweringEye());
		temp.add(new OvertexQoatlus());
		temp.add(new PMCaptor());
		temp.add(new ParallelWorldFusion());
		temp.add(new PhantomGhost());
		temp.add(new PrematureBurial());
		temp.add(new PyramidTurtle());
		temp.add(new RelinquishedFusion());
		temp.add(new RobbinZombie());
		temp.add(new ShiftingShadows());
		temp.add(new SkullFlame());
		temp.add(new SkullServant());
		temp.add(new SoulRelease());
		temp.add(new SouleatingOviraptor());
		temp.add(new SpiritReaper());
		temp.add(new SupersonicSkullFlame());
		temp.add(new SynchroFusionist());
		temp.add(new Tengu());
		temp.add(new TimeFusion());
		temp.add(new TriWight());
		temp.add(new Tsukahagi());
		temp.add(new TutanMask());
		temp.add(new TyrantDinoFusion());
		temp.add(new UniZombie());
		temp.add(new VisionFusion());
		temp.add(new Wasteland());
		temp.add(new WightLady());
		temp.add(new Wightmare());
		temp.add(new Wightprince());
		temp.add(new Wightprincess());
		temp.add(new Yasha());
		temp.add(new Yoko());
		temp.add(new YukiMusume());
		temp.add(new YukiOnnaAbsolute());
		temp.add(new YukiOnnaIce());
		temp.add(new ZombieNecronize());
		temp.add(new ZombiePowerStruggle());
		temp.add(new ZombieTiger());		
		temp.add(new GiantAxeMummy());
		temp.add(new PyramidWonders());
		temp.add(new PyramidLight());
		temp.add(new FossilKnight());
		temp.add(new Scapeghost());
		temp.add(new AvendreadSavior());
		temp.add(new BaconSaver());
		temp.add(new BeastPharaoh());
		temp.add(new FossilDragon());
		temp.add(new FossilKnight());
		temp.add(new GlowUpBloom());
		temp.add(new HardSellinZombie());
		temp.add(new HumptyGrumpty());
		temp.add(new RebornZombie());
		temp.add(new ReturnToDoomed());
		temp.add(new RevendreadEvolution());
		temp.add(new RevendreadExecutor());
		temp.add(new RevendreadOrigin());
		temp.add(new RevendreadSlayer());
		temp.add(new SeaMonsterTheseus());
		temp.add(new ShiranuiSamurai());
		temp.add(new ShiranuiSamuraisaga());
		temp.add(new ShiranuiShogunsaga());
		temp.add(new ShiranuiSkillsagaSupremacy());
		temp.add(new ShiranuiSmith());
		temp.add(new ShiranuiSolitaire());
		temp.add(new ShiranuiSpectralsword());
		temp.add(new ShiranuiSpectralswordShade());
		temp.add(new ShiranuiSpiritmaster());
		temp.add(new ShiranuiSquire());
		temp.add(new ShiranuiSquiresaga());
		temp.add(new ShiranuiSunsaga());
		temp.add(new ShiranuiSwordmaster());
		temp.add(new ShiranuiSwordsaga());
		temp.add(new Skullgios());
		temp.add(new VendreadAnima());
		temp.add(new VendreadBattlelord());
		temp.add(new VendreadCharge());
		temp.add(new VendreadChimera());
		temp.add(new VendreadCore());
		temp.add(new VendreadDaybreak());
		temp.add(new VendreadHoundhorde());
		temp.add(new VendreadNightmare());
		temp.add(new VendreadNights());
		temp.add(new VendreadReorigin());
		temp.add(new VendreadReunion());
		temp.add(new VendreadRevenants());
		temp.add(new VendreadRevolution());
		temp.add(new VendreadStriges());
		temp.add(new Zombina());
		temp.add(new SkullConductor());
		return temp;
	}

	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	// Which relic to return on making a copy of this relic.
	@Override
	public AbstractRelic makeCopy() {
		return new CardPoolBasicRelic();
	}

	@Override
	public void onRightClick() 
	{
		if (pool.size() > 0) {
			DuelistMod.duelistMasterCardViewScreen.open("Basic Card Pool", pool);
		}
	}
	
	public void setDescription()
	{
		this.description = getUpdatedDescription();
		/* Relic Description			*/
		String poolDesc = "";

		/* Colorless Cards	*/
		// Basic Cards
		boolean holiday = DuelistMod.holidayNonDeckCards.size() > 0;
		boolean basic = AbstractDungeon.colorlessCardPool.group.size() > 0;
		if (holiday && basic)
		{
			poolDesc += " NL NL #yColorless #b(" + (AbstractDungeon.colorlessCardPool.group.size() + DuelistMod.holidayNonDeckCards.size()) + "): NL Basic Cards NL ";
			if (DuelistMod.addedBirthdayCards)  
			{ 
				if (Util.whichBirthday() == 1) { poolDesc += "Birthday Cards NL (Nyoxide's Birthday)";  }
				else if (Util.whichBirthday() == 2) 
				{
					String playerName = CardCrawlGame.playerName;
					poolDesc += "Birthday Cards NL (" + playerName + "'s Birthday)";
				}
				else if (Util.whichBirthday() == 3) { poolDesc += "Birthday Cards NL (DuelistMod's Birthday)"; }
			}
			if (DuelistMod.addedHalloweenCards) 
			{ 
				if (DuelistMod.addedBirthdayCards) { poolDesc += " NL Halloween Cards"; }
				else { poolDesc += "Halloween Cards"; }
			}
			
			else if (DuelistMod.addedXmasCards)
			{
				if (DuelistMod.addedBirthdayCards) { poolDesc += " NL Christmas Cards"; }
				else { poolDesc += "Christmas Cards"; }
			}
			
			else if (DuelistMod.addedWeedCards)
			{
				if (DuelistMod.addedBirthdayCards) { poolDesc += " NL 420 Cards"; }
				else { poolDesc += "420 Cards"; }
			}
		}
		else if (basic)
		{
			poolDesc += " NL NL #yColorless #b(" + AbstractDungeon.colorlessCardPool.group.size() + "): NL Basic Cards";
		}
		else if (holiday)
		{
			poolDesc += " NL NL #yColorless #b(" + DuelistMod.holidayNonDeckCards.size() + "): NL ";
			if (DuelistMod.addedBirthdayCards)  
			{ 
				if (Util.whichBirthday() == 1) { poolDesc += "Birthday Cards NL (Nyoxide's Birthday)";  }
				else if (Util.whichBirthday() == 2) 
				{
					String playerName = CardCrawlGame.playerName;
					poolDesc += "Birthday Cards NL (" + playerName + "'s Birthday)";
				}
				else if (Util.whichBirthday() == 3) { poolDesc += "Birthday Cards NL (DuelistMod's Birthday)"; }
			}
			if (DuelistMod.addedHalloweenCards) 
			{ 
				if (DuelistMod.addedBirthdayCards) { poolDesc += " NL Halloween Cards"; }
				else { poolDesc += "Halloween Cards"; }
			}
			
			else if (DuelistMod.addedXmasCards)
			{
				if (DuelistMod.addedBirthdayCards) { poolDesc += " NL Christmas Cards"; }
				else { poolDesc += "Christmas Cards"; }
			}
			
			else if (DuelistMod.addedWeedCards)
			{
				if (DuelistMod.addedBirthdayCards) { poolDesc += " NL 420 Cards"; }
				else { poolDesc += "420 Cards"; }
			}
		}
		if (!poolDesc.equals("") && TheDuelist.cardPool.size() > 0) { description += poolDesc; }
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
	}
}
