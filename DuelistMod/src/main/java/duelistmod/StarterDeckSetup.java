package duelistmod;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import duelistmod.cards.*;
import duelistmod.interfaces.*;
import duelistmod.patches.*;

public class StarterDeckSetup {

	// STARTER DECK METHODS /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void initStarterDeckPool()
	{
		// Non-archetype Set
		DuelistMod.basicCards = new ArrayList<DuelistCard>();
		DuelistMod.basicCards.add(new AcidTrapHole());
		DuelistMod.basicCards.add(new AlphaMagnet());
		DuelistMod.basicCards.add(new BetaMagnet());
		DuelistMod.basicCards.add(new BlastJuggler());
		DuelistMod.basicCards.add(new BlizzardWarrior());
		DuelistMod.basicCards.add(new BottomlessTrapHole());
		DuelistMod.basicCards.add(new CardDestruction());
		DuelistMod.basicCards.add(new CannonSoldier());
		DuelistMod.basicCards.add(new CastleWalls());
		DuelistMod.basicCards.add(new CatapultTurtle());
		DuelistMod.basicCards.add(new CelticGuardian());
		DuelistMod.basicCards.add(new CheerfulCoffin());
		DuelistMod.basicCards.add(new Cloning());
		if (!DuelistMod.creatorBtnBool) {DuelistMod.basicCards.add(new DarkCreator()); }
		DuelistMod.basicCards.add(new DarkFactory());
		DuelistMod.basicCards.add(new DarkHole());
		DuelistMod.basicCards.add(new DarkMirrorForce());
		DuelistMod.basicCards.add(new DianKeto());
		DuelistMod.basicCards.add(new FinalFlame());
		DuelistMod.basicCards.add(new Fissure());
		DuelistMod.basicCards.add(new FlameSwordsman());
		DuelistMod.basicCards.add(new FortressWarrior());
		DuelistMod.basicCards.add(new GaiaFierce());
		DuelistMod.basicCards.add(new GammaMagnet());
		DuelistMod.basicCards.add(new GoldenApples());
		DuelistMod.basicCards.add(new HammerShot());
		DuelistMod.basicCards.add(new HaneHane());
		DuelistMod.basicCards.add(new HarpieFeather());
		DuelistMod.basicCards.add(new HeavyStorm());
		DuelistMod.basicCards.add(new Hinotama());
		DuelistMod.basicCards.add(new ImperialOrder());
		DuelistMod.basicCards.add(new JudgeMan());
		DuelistMod.basicCards.add(new Kuriboh());
		DuelistMod.basicCards.add(new LabyrinthWall());
		DuelistMod.basicCards.add(new Mausoleum());
		DuelistMod.basicCards.add(new MillenniumShield());
		DuelistMod.basicCards.add(new MirrorForce());
		DuelistMod.basicCards.add(new MonsterEgg());
		DuelistMod.basicCards.add(new ObeliskTormentor());
		DuelistMod.basicCards.add(new FeatherPho());
		DuelistMod.basicCards.add(new PotAvarice());
		DuelistMod.basicCards.add(new PotForbidden());
		DuelistMod.basicCards.add(new PotDichotomy());
		DuelistMod.basicCards.add(new PotGenerosity());
		DuelistMod.basicCards.add(new PotGreed());
		DuelistMod.basicCards.add(new PreventRat());
		DuelistMod.basicCards.add(new RadiantMirrorForce());
		DuelistMod.basicCards.add(new Raigeki());
		DuelistMod.basicCards.add(new Sangan());
		DuelistMod.basicCards.add(new Scapegoat());
		DuelistMod.basicCards.add(new ScrapFactory());
		DuelistMod.basicCards.add(new ShardGreed());
		DuelistMod.basicCards.add(new SmashingGround());
		DuelistMod.basicCards.add(new SphereKuriboh());
		DuelistMod.basicCards.add(new StimPack());
		DuelistMod.basicCards.add(new StormingMirrorForce());
		DuelistMod.basicCards.add(new StrayLambs());
		DuelistMod.basicCards.add(new SuperancientDinobeast());
		DuelistMod.basicCards.add(new SwordsRevealing());
		DuelistMod.basicCards.add(new Terraforming());
		if (!DuelistMod.creatorBtnBool) { DuelistMod.basicCards.add(new TheCreator()); }
		DuelistMod.basicCards.add(new TokenVacuum());
		DuelistMod.basicCards.add(new BigFire());
		DuelistMod.basicCards.add(new UltimateOffering());
		DuelistMod.basicCards.add(new ValkMagnet());
		DuelistMod.basicCards.add(new Wiretap());
		DuelistMod.basicCards.add(new BeaverWarrior());
		DuelistMod.basicCards.add(new Zombyra());
		DuelistMod.basicCards.add(new Mathematician());
		DuelistMod.basicCards.add(new BattleOx());
		DuelistMod.basicCards.add(new FluteKuriboh());
		DuelistMod.basicCards.add(new WingedKuriboh());
		DuelistMod.basicCards.add(new CastleDarkIllusions());
		DuelistMod.basicCards.add(new ChangeHeart());
		DuelistMod.basicCards.add(new JamBreeding());
		DuelistMod.basicCards.add(new SmallLabyrinthWall());
		DuelistMod.basicCards.add(new Polymerization());
		DuelistMod.basicCards.add(new BigKoala());
		DuelistMod.basicCards.add(new BattleguardKing());		
		DuelistMod.basicCards.add(new BattleFootballer());
		DuelistMod.basicCards.add(new EarthquakeGiant());
		DuelistMod.basicCards.add(new EvilswarmHeliotrope());
		DuelistMod.basicCards.add(new LegendaryFlameLord());
		DuelistMod.basicCards.add(new WormApocalypse());
		DuelistMod.basicCards.add(new WormBarses());
		DuelistMod.basicCards.add(new WormWarlord());
		DuelistMod.basicCards.add(new WormKing());
		DuelistMod.basicCards.add(new ArmorBreaker());
		DuelistMod.basicCards.add(new GauntletWarrior());
		DuelistMod.basicCards.add(new CommandKnight());
		DuelistMod.basicCards.add(new GaiaMidnight());
		DuelistMod.basicCards.add(new DrivenDaredevil());
		DuelistMod.basicCards.add(new GilfordLegend());
		DuelistMod.basicCards.add(new ReinforcementsArmy());
		DuelistMod.basicCards.add(new BlockGolem());
		DuelistMod.basicCards.add(new DokiDoki());
		DuelistMod.basicCards.add(new GiantSoldierSteel());

		DuelistMod.archetypeCards.addAll(DuelistMod.basicCards);
		
		// Dragon Deck && Old Dragon
		StarterDeck dragonDeck = DuelistMod.starterDeckNamesMap.get("Dragon Deck");
		ArrayList<DuelistCard> dragonCards = new ArrayList<DuelistCard>();
		dragonCards.add(new AncientRules());
		dragonCards.add(new AxeDespair());
		dragonCards.add(new BabyDragon());
		dragonCards.add(new BarrelDragon());
		dragonCards.add(new BlacklandFireDragon());
		dragonCards.add(new BlizzardDragon());
		dragonCards.add(new BlueEyes());
		dragonCards.add(new BlueEyesUltimate());
		dragonCards.add(new BSkullDragon());
		dragonCards.add(new BusterBlader());
		dragonCards.add(new CaveDragon());
		dragonCards.add(new CurseDragon());
		dragonCards.add(new CyberDragon());
		dragonCards.add(new DarkfireDragon());
		dragonCards.add(new DragonCaptureJar());
		dragonCards.add(new DragonMaster());
		dragonCards.add(new DragonPiper());
		dragonCards.add(new ExploderDragon());
		dragonCards.add(new FiendSkull());
		dragonCards.add(new FiveHeaded());
		dragonCards.add(new FluteSummoning());
		dragonCards.add(new GaiaDragonChamp());
		dragonCards.add(new GravityAxe());
		dragonCards.add(new LesserDragon());
		dragonCards.add(new LeviaDragon());
		dragonCards.add(new LordD());
		dragonCards.add(new MetalDragon());
		dragonCards.add(new Mountain());
		dragonCards.add(new OceanDragonLord());
		dragonCards.add(new RedEyes());
		dragonCards.add(new RedEyesZombie());
		dragonCards.add(new Reinforcements());
		dragonCards.add(new SangaThunder());
		dragonCards.add(new SliferSky());
		dragonCards.add(new SnowDragon());
		dragonCards.add(new SnowdustDragon());
		dragonCards.add(new SwordDeepSeated());
		dragonCards.add(new ThunderDragon());
		dragonCards.add(new TrihornedDragon());
		dragonCards.add(new TwinBarrelDragon());
		//dragonCards.add(new TyrantWing());
		dragonCards.add(new WhiteHornDragon());
		dragonCards.add(new WhiteNightDragon());
		dragonCards.add(new WingedDragonRa());
		dragonCards.add(new YamataDragon());
		dragonCards.add(new TwinHeadedFire());
		dragonCards.add(new DarkstormDragon());
		dragonCards.add(new AncientGearGadjiltron());
		if (!DuelistMod.toonBtnBool)
		{
			dragonCards.add(new ToonWorld());
			dragonCards.add(new ToonKingdom());
			dragonCards.add(new ToonBarrelDragon());
			//dragonCards.add(new ToonCyberDragon());
			dragonCards.add(new RedEyesToon());
			dragonCards.add(new BlueEyesToon());
			dragonCards.add(new MangaRyuRan());
		}
		
		dragonDeck.fillPoolCards(DuelistMod.basicCards);
		dragonDeck.fillPoolCards(dragonCards); 
		
		dragonDeck.fillArchetypeCards(dragonCards);
		DuelistMod.archetypeCards.addAll(dragonCards);
		
		// Spellcaster Deck && Old Spellcaster
		StarterDeck spellcasterDeck = DuelistMod.starterDeckNamesMap.get("Spellcaster Deck");
		ArrayList<DuelistCard> spellcasterCards = new ArrayList<DuelistCard>();
		spellcasterCards.add(new AncientElf());
		spellcasterCards.add(new BadReactionRare());
		spellcasterCards.add(new BlizzardPrincess());
		spellcasterCards.add(new BookSecret());
		spellcasterCards.add(new DarklordMarie());
		spellcasterCards.add(new DarkMagicianGirl());
		spellcasterCards.add(new DarkMagician());
		spellcasterCards.add(new FogKing());
		spellcasterCards.add(new GeminiElf());
		spellcasterCards.add(new GiantTrunade());
		spellcasterCards.add(new Graverobber());
		spellcasterCards.add(new GuardianAngel());
		spellcasterCards.add(new IcyCrevasse());
		spellcasterCards.add(new Illusionist());
		spellcasterCards.add(new InjectionFairy());
		spellcasterCards.add(new InvitationDarkSleep());
		spellcasterCards.add(new MysticalElf());
		spellcasterCards.add(new MythicalBeast());
		spellcasterCards.add(new NeoMagic());
		spellcasterCards.add(new NutrientZ());
		spellcasterCards.add(new PowerKaishin());
		spellcasterCards.add(new RainMercy());
		spellcasterCards.add(new Relinquished());
		spellcasterCards.add(new SangaEarth());
		spellcasterCards.add(new SangaThunder());
		spellcasterCards.add(new SangaWater());
		spellcasterCards.add(new GateGuardian());
		spellcasterCards.add(new FairyBox());
		spellcasterCards.add(new SliferSky());
		spellcasterCards.add(new SpiritHarp());
		spellcasterCards.add(new SwordsBurning());
		spellcasterCards.add(new Yami());
		spellcasterCards.add(new TimeWizard());
		spellcasterCards.add(new IceQueen());
		spellcasterCards.add(new ThousandEyesRestrict());
		spellcasterCards.add(new ThousandEyesIdol());
		spellcasterCards.add(new MindAir());
		spellcasterCards.add(new FrontierWiseman());		
		spellcasterCards.add(new GoblinRemedy());
		spellcasterCards.add(new AltarTribute());
		spellcasterCards.add(new LordD());
		spellcasterCards.add(new BlueDragonSummoner());
		spellcasterCards.add(new ApprenticeIllusionMagician());
		spellcasterCards.add(new DarkHorizon());
		spellcasterCards.add(new MachineFactory());
		spellcasterCards.add(new DarkPaladin());
		spellcasterCards.add(new WhiteMagicalHat());
		if (!DuelistMod.exodiaBtnBool)
		{
			spellcasterCards.add(new ExodiaHead());
			spellcasterCards.add(new ExodiaLA());
			spellcasterCards.add(new ExodiaLL());
			spellcasterCards.add(new ExodiaRA());
			spellcasterCards.add(new ExodiaRL());
			spellcasterCards.add(new ExxodMaster());
			spellcasterCards.add(new LegendaryExodia());
			spellcasterCards.add(new ContractExodia());
			//spellcasterCards.add(new LegendExodia());
			//spellcasterCards.add(new ExodiaNecross());
		}
		if (!DuelistMod.ojamaBtnBool)
		{
			spellcasterCards.add(new OjamaRed());
			spellcasterCards.add(new OjamaBlue());
			spellcasterCards.add(new OjamaBlack());
			spellcasterCards.add(new OjamaGreen());
			spellcasterCards.add(new OjamaKnight());
			spellcasterCards.add(new OjamaDeltaHurricane());
			spellcasterCards.add(new Ojamatch());
			spellcasterCards.add(new OjamaYellow());
			spellcasterCards.add(new OjamaDuo());
			spellcasterCards.add(new OjamaCountry());
			spellcasterCards.add(new OjamaKing());
			spellcasterCards.add(new OjamaTrio());
			spellcasterCards.add(new Ojamuscle());
			spellcasterCards.add(new RedMedicine());
			
		}
		
		spellcasterDeck.fillPoolCards(DuelistMod.basicCards);
		spellcasterDeck.fillPoolCards(spellcasterCards);
		
		spellcasterDeck.fillArchetypeCards(spellcasterCards);
		DuelistMod.archetypeCards.addAll(spellcasterCards);
		
		
		// Nature Deck && Old Nature
		StarterDeck natureDeck = DuelistMod.starterDeckNamesMap.get("Nature Deck");
		
		ArrayList<DuelistCard> natureCards = new ArrayList<DuelistCard>();
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
		natureCards.add(new RedMedicine());
		natureCards.add(new PotDuality());
		//natureCards.add(new WorldTree());
		//natureCards.add(new Predaplanet());
		
		natureDeck.fillPoolCards(DuelistMod.basicCards);
		natureDeck.fillPoolCards(natureCards);
		
		natureDeck.fillArchetypeCards(natureCards);
		DuelistMod.archetypeCards.addAll(natureCards);
	
		
		// Toon Deck
		StarterDeck toonDeck = DuelistMod.starterDeckNamesMap.get("Toon Deck");
		ArrayList<DuelistCard> toonCards = new ArrayList<DuelistCard>();
		toonCards.add(new ToonWorld());
		toonCards.add(new ToonSummonedSkull());
		toonCards.add(new ToonRollback());
		toonCards.add(new ToonMermaid());
		toonCards.add(new ToonMask());
		toonCards.add(new ToonMagic());
		toonCards.add(new ToonKingdom());
		toonCards.add(new ToonGoblinAttack());
		toonCards.add(new ToonGeminiElf());
		toonCards.add(new ToonDarkMagicianGirl());
		toonCards.add(new ToonDarkMagician());
		//toonCards.add(new ToonCyberDragon());
		toonCards.add(new ToonAncientGear());
		toonCards.add(new RedEyesToon());
		toonCards.add(new MangaRyuRan());
		toonCards.add(new BlueEyesToon());
		toonCards.add(new ToonTable());
		toonCards.add(new ToonCannonSoldier());
		toonCards.add(new ToonDefense());
		toonCards.add(new ComicHand());
		toonCards.add(new ShadowToon());
		toonCards.add(new ToonBriefcase());
		toonCards.add(new HeartUnderdog());
		//toonCards.add(new MagicCylinder());
		toonCards.add(new MiniPolymerization());
		toonCards.add(new RedMedicine());
		toonCards.add(new TributeDoomed());
		toonCards.add(new GiantTrunade());
		toonCards.add(new GracefulCharity());
		toonCards.add(new SwordsConcealing());
		toonCards.add(new TrapHole());
		toonCards.add(new CardSafeReturn());
		if (!DuelistMod.ojamaBtnBool)
		{
			toonCards.add(new OjamaRed());
			toonCards.add(new OjamaBlue());
			toonCards.add(new OjamaBlack());
			toonCards.add(new OjamaGreen());
			toonCards.add(new OjamaKnight());
			toonCards.add(new OjamaDeltaHurricane());
			toonCards.add(new Ojamatch());
			toonCards.add(new OjamaYellow());
			toonCards.add(new OjamaDuo());
			toonCards.add(new OjamaCountry());
			toonCards.add(new OjamaKing());
			toonCards.add(new OjamaTrio());
			toonCards.add(new Ojamuscle());
			toonCards.add(new OjamaPajama());
			toonCards.add(new Ojamassimilation());
			toonCards.add(new OjamaEmperor());
			toonCards.add(new Ojamagic());			
		}
		
		toonDeck.fillPoolCards(DuelistMod.basicCards);
		toonDeck.fillPoolCards(toonCards);		
		toonDeck.fillArchetypeCards(toonCards);
		DuelistMod.archetypeCards.addAll(toonCards);
		
		// Zombie Deck
		StarterDeck zombieDeck = DuelistMod.starterDeckNamesMap.get("Zombie Deck");
		ArrayList<DuelistCard> zombieCards = new ArrayList<DuelistCard>();
		zombieCards.add(new ArmoredZombie());
		zombieCards.add(new ClownZombie());
		zombieCards.add(new DoubleCoston());
		zombieCards.add(new MoltenZombie());
		zombieCards.add(new RegenMummy());
		zombieCards.add(new PatricianDarkness());
		zombieCards.add(new RedEyesZombie());
		//zombieCards.add(new Relinkuriboh());
		zombieCards.add(new RyuKokki());
		zombieCards.add(new SoulAbsorbingBone());
		//zombieCards.add(new VampireGenesis());
		//zombieCards.add(new VampireLord());
		zombieCards.add(new Skelesaurus());
		zombieCards.add(new GatesDarkWorld());
		zombieCards.add(new CallGrave());
		zombieCards.add(new CardSafeReturn());
		zombieCards.add(new MonsterReborn());
		zombieCards.add(new ShallowGrave());
		zombieCards.add(new MiniPolymerization());
		zombieCards.add(new Pumpking());
		zombieCards.add(new Pumprincess());
		zombieCards.add(new TrapHole());
		zombieCards.add(new TributeDoomed());
		zombieCards.add(new PotDuality());
		
		zombieDeck.fillPoolCards(DuelistMod.basicCards);
		zombieDeck.fillPoolCards(zombieCards);		
		zombieDeck.fillArchetypeCards(zombieCards);
		DuelistMod.archetypeCards.addAll(zombieCards);
		
		// Aqua Deck
		StarterDeck aquaDeck = DuelistMod.starterDeckNamesMap.get("Aqua Deck");
		ArrayList<DuelistCard> aquaCards = new ArrayList<DuelistCard>();
		aquaCards.add(new SevenColoredFish());
		aquaCards.add(new IslandTurtle());
		aquaCards.add(new Umi());
		aquaCards.add(new AquaSpirit());
		aquaCards.add(new LegendaryFisherman());
		aquaCards.add(new OhFish());
		aquaCards.add(new RevivalJam());
		aquaCards.add(new LeviaDragon());
		aquaCards.add(new OceanDragonLord());
		aquaCards.add(new SangaWater());
		aquaCards.add(new OhFish());
		aquaCards.add(new GracefulCharity());
		aquaCards.add(new HeartUnderdog());
		//aquaCards.add(new MagicCylinder());
		aquaCards.add(new RedMedicine());
		aquaCards.add(new SwordsConcealing());
		//aquaCards.add(new DarkMimicLv1());
		aquaCards.add(new PotDuality());
		//aquaCards.add(new HyperancientShark());
		//aquaCards.add(new KaiserSeaHorse());
		//aquaCards.add(new UnshavenAngler());
		aquaDeck.fillPoolCards(DuelistMod.basicCards);
		aquaDeck.fillPoolCards(aquaCards);		
		aquaDeck.fillArchetypeCards(aquaCards);
		DuelistMod.archetypeCards.addAll(aquaCards);
		
		// Fiend Deck
		StarterDeck fiendDeck = DuelistMod.starterDeckNamesMap.get("Fiend Deck");
		ArrayList<DuelistCard> fiendCards = new ArrayList<DuelistCard>();
		fiendCards.add(new SummonedSkull());
		fiendCards.add(new GatesDarkWorld());
		fiendCards.add(new FiendishChain());
		fiendCards.add(new DarkMimicLv1());
		fiendCards.add(new DarkBlade());
		fiendCards.add(new DarkMasterZorc());
		fiendCards.add(new FiendMegacyber());
		fiendCards.add(new KingYami());
		fiendCards.add(new Lajinn());
		fiendCards.add(new MsJudge());
		fiendCards.add(new SkullArchfiend());
		fiendCards.add(new SlateWarrior());
		fiendCards.add(new TerraTerrible());
		fiendCards.add(new DarkEnergy());
		fiendCards.add(new VanityFiend());
		fiendCards.add(new ArchfiendSoldier());
		fiendCards.add(new FabledAshenveil());
		fiendCards.add(new FabledGallabas());
		fiendCards.add(new GrossGhost());
		fiendCards.add(new PortraitSecret());
		fiendCards.add(new FiendSkull());
		fiendCards.add(new RedSprinter());
		fiendCards.add(new Tierra());
		fiendCards.add(new GracefulCharity());
		fiendCards.add(new MonsterReborn());
		fiendCards.add(new CardSafeReturn());
		fiendCards.add(new CallGrave());
		fiendCards.add(new MiniPolymerization());
		fiendCards.add(new TributeDoomed());
		fiendCards.add(new Pumpking());
		fiendCards.add(new Pumprincess());
		fiendCards.add(new PotDuality());
		fiendCards.add(new Doomdog());
		fiendCards.add(new RedMirror());
		fiendDeck.fillPoolCards(DuelistMod.basicCards);
		fiendDeck.fillPoolCards(fiendCards);		
		fiendDeck.fillArchetypeCards(fiendCards);
		DuelistMod.archetypeCards.addAll(fiendCards);
		
		// TEMPORARY UNTIL FIEND & ZOMBIE HAVE ENOUGH CARDS
		//fiendDeck.fillArchetypeCards(zombieCards);
		//fiendDeck.fillPoolCards(zombieCards);
		//zombieDeck.fillArchetypeCards(fiendCards);
		//zombieDeck.fillPoolCards(fiendCards);
		
		// Machine Deck
		StarterDeck machineDeck = DuelistMod.starterDeckNamesMap.get("Machine Deck");
		ArrayList<DuelistCard> machineCards = new ArrayList<DuelistCard>();
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
		//machineCards.add(new MagicCylinder());
		machineCards.add(new RedMedicine());
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
		if (!DuelistMod.toonBtnBool)
		{
			machineCards.add(new ToonWorld());
			machineCards.add(new ToonKingdom());
			machineCards.add(new ToonAncientGear());
			//machineCards.add(new ToonCyberDragon());		
		}
		
		if (!DuelistMod.ojamaBtnBool)
		{
			machineCards.add(new OjamaRed());
			machineCards.add(new OjamaBlue());
			machineCards.add(new OjamaBlack());
			machineCards.add(new OjamaGreen());
			machineCards.add(new OjamaKnight());
			machineCards.add(new OjamaDeltaHurricane());
			machineCards.add(new Ojamatch());
			machineCards.add(new OjamaYellow());
			machineCards.add(new OjamaDuo());
			machineCards.add(new OjamaCountry());
			machineCards.add(new OjamaKing());
			machineCards.add(new OjamaTrio());
			machineCards.add(new Ojamuscle());
			machineCards.add(new OjamaPajama());
			machineCards.add(new Ojamassimilation());
			machineCards.add(new OjamaEmperor());
		}

		machineDeck.fillPoolCards(DuelistMod.basicCards);
		machineDeck.fillPoolCards(machineCards);		
		machineDeck.fillArchetypeCards(machineCards);
		DuelistMod.archetypeCards.addAll(machineCards);
		
		// Magnet Deck
		StarterDeck magnetDeck = DuelistMod.starterDeckNamesMap.get("Superheavy Deck");
		ArrayList<DuelistCard> magnetCards = new ArrayList<DuelistCard>();
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
		magnetCards.add(new SuperheavySoulclaw());
		magnetCards.add(new SuperheavySoulhorns());
		magnetCards.add(new SuperheavySoulpiercer());
		magnetCards.add(new SuperheavySoulshield());
		magnetCards.add(new SwordsConcealing());
		//magnetCards.add(new MagicCylinder());
		magnetCards.add(new HeartUnderdog());
		for (int i = 0; i < DuelistMod.magnetSlider; i++)
		{
			magnetCards.add(new AlphaMagnet());
			magnetCards.add(new BetaMagnet());
			magnetCards.add(new GammaMagnet());
		}
		
		if (!DuelistMod.ojamaBtnBool)
		{
			magnetCards.add(new OjamaRed());
			magnetCards.add(new OjamaBlue());
			magnetCards.add(new OjamaBlack());
			magnetCards.add(new OjamaGreen());
			magnetCards.add(new OjamaKnight());
			magnetCards.add(new OjamaDeltaHurricane());
			magnetCards.add(new Ojamatch());
			magnetCards.add(new OjamaYellow());
			magnetCards.add(new OjamaDuo());
			magnetCards.add(new OjamaCountry());
			magnetCards.add(new OjamaKing());
			magnetCards.add(new OjamaTrio());
			magnetCards.add(new Ojamuscle());
			magnetCards.add(new OjamaPajama());
			magnetCards.add(new Ojamassimilation());
			magnetCards.add(new OjamaEmperor());
		}

		magnetDeck.fillPoolCards(DuelistMod.basicCards);
		magnetDeck.fillPoolCards(magnetCards);		
		magnetDeck.fillArchetypeCards(magnetCards);
		DuelistMod.archetypeCards.addAll(magnetCards);
		
		StarterDeck ojamaDeck = DuelistMod.starterDeckNamesMap.get("Ojama Deck");
		ArrayList<DuelistCard> ojamaCards = new ArrayList<DuelistCard>();
		ojamaCards.add(new OjamaRed());
		ojamaCards.add(new OjamaBlue());
		ojamaCards.add(new OjamaBlack());
		ojamaCards.add(new OjamaGreen());
		ojamaCards.add(new OjamaKnight());
		ojamaCards.add(new OjamaDeltaHurricane());
		ojamaCards.add(new Ojamatch());
		ojamaCards.add(new OjamaYellow());
		ojamaCards.add(new OjamaDuo());
		ojamaCards.add(new OjamaCountry());
		ojamaCards.add(new OjamaKing());
		ojamaCards.add(new OjamaTrio());
		ojamaCards.add(new Ojamuscle());
		ojamaCards.add(new OjamaPajama());
		ojamaCards.add(new Ojamassimilation());
		ojamaCards.add(new OjamaEmperor());
		ojamaCards.add(new Ojamagic());
		ojamaDeck.fillPoolCards(DuelistMod.basicCards);
		ojamaDeck.fillPoolCards(ojamaCards);		
		ojamaDeck.fillArchetypeCards(ojamaCards);
		DuelistMod.archetypeCards.addAll(ojamaCards);
	
	}
	
	public static boolean isDeckArchetype()
	{
		if (getCurrentDeck().getIndex() > 0 && getCurrentDeck().getIndex() < 10)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static StarterDeck getCurrentDeck()
	{
		for (StarterDeck d : DuelistMod.starterDeckList) { if (d.getIndex() == DuelistMod.deckIndex) { return d; }}
		return DuelistMod.starterDeckList.get(0);
	}

	public static CardTags findDeckTag(int deckIndex) 
	{
		for (StarterDeck d : DuelistMod.starterDeckList) { if (d.getIndex() == deckIndex) { return d.getDeckTag(); }}
		return null;
	}

	public static StarterDeck findDeck(int deckIndex) 
	{
		for (StarterDeck d : DuelistMod.starterDeckList) { if (d.getIndex() == deckIndex) { return d; }}
		return null;
	}

	public static void initStartDeckArrays()
	{
		ArrayList<CardTags> deckTagList = StarterDeckSetup.getAllDeckTags();
		for (DuelistCard c : DuelistMod.myCards)
		{
			for (CardTags t : deckTagList)
			{
				if (c.hasTag(t))
				{
					StarterDeck ref = DuelistMod.deckTagMap.get(t);
					int copyIndex = StarterDeck.getDeckCopiesMap().get(ref.getDeckTag());
					for (int i = 0; i < c.startCopies.get(copyIndex); i++) 
					{ 
						if (DuelistMod.debug)
						{
							DuelistMod.logger.info("theDuelist:DuelistMod:initStartDeckArrays() ---> added " + c.originalName + " to " + ref.getSimpleName()); 
						}
						ref.getDeck().add(c); 
					}
				}
			}
		}
	}

	public static ArrayList<CardTags> getAllDeckTags()
	{
		ArrayList<CardTags> deckTagList = new ArrayList<CardTags>();
		for (StarterDeck d : DuelistMod.starterDeckList) { deckTagList.add(d.getDeckTag()); }
		return deckTagList;
	}

	public static boolean hasTags(AbstractCard c, ArrayList<CardTags> tags)
	{
		boolean hasAnyTag = false;
		for (CardTags t : tags) { if (c.hasTag(t)) { hasAnyTag = true; }}
		return hasAnyTag;
	}

	public static void setupStartDecksB()
	{
		DuelistMod.chosenDeckTag = findDeckTag(DuelistMod.deckIndex);
		StarterDeck refDeck = null;
		for (StarterDeck d : DuelistMod.starterDeckList) { if (d.getDeckTag().equals(DuelistMod.chosenDeckTag)) { refDeck = d; }}
		if (refDeck != null)
		{
			if (DuelistMod.chosenDeckTag.equals(Tags.RANDOM_DECK_SMALL))
			{
				DuelistMod.deckToStartWith = new ArrayList<DuelistCard>();
				for (int i = 0; i < DuelistMod.randomDeckSmallSize; i++) { DuelistMod.deckToStartWith.add((DuelistCard)DuelistCard.returnTrulyRandomDuelistCard());}
			}
			
			else if (DuelistMod.chosenDeckTag.equals(Tags.RANDOM_DECK_BIG))
			{
				DuelistMod.deckToStartWith = new ArrayList<DuelistCard>();
				for (int i = 0; i < DuelistMod.randomDeckBigSize; i++) { DuelistMod.deckToStartWith.add((DuelistCard)DuelistCard.returnTrulyRandomDuelistCard()); }
			}
	
			else 
			{
				if (DuelistMod.debug) { DuelistMod.logger.info("theDuelist:DuelistMod:setupStartDecksB() ---> " + refDeck.getSimpleName() + " size: " + refDeck.getDeck().size());  }
				DuelistMod.deckToStartWith = new ArrayList<DuelistCard>();
				DuelistMod.deckToStartWith.addAll(refDeck.getDeck());
			}
		}
		
		else
		{
			StarterDeckSetup.initStandardDeck();
			DuelistMod.deckToStartWith = new ArrayList<DuelistCard>();
			DuelistMod.deckToStartWith.addAll(DuelistMod.standardDeck);
		}
		
	}

	public static void initStandardDeck()
	{
		DuelistMod.standardDeck = new ArrayList<DuelistCard>();
		for (DuelistCard c : DuelistMod.myCards) { if (c.hasTag(Tags.STANDARD_DECK)) { for (int i = 0; i < c.standardDeckCopies; i++) { DuelistMod.standardDeck.add(c); }}}
	}

	public static void resetStarterDeck()
	{		
		setupStartDecksB();
		if (DuelistMod.deckToStartWith.size() > 0)
		{
			CardGroup newStartGroup = new CardGroup(CardGroup.CardGroupType.MASTER_DECK);
			for (AbstractCard c : DuelistMod.deckToStartWith) { newStartGroup.addToRandomSpot(c);}
			CardCrawlGame.characterManager.getCharacter(TheDuelistEnum.THE_DUELIST).masterDeck.initializeDeck(newStartGroup);
			CardCrawlGame.characterManager.getCharacter(TheDuelistEnum.THE_DUELIST).masterDeck.sortAlphabetically(true);
		}
	}

}
