package duelistmod;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.*;
import duelistmod.cards.curses.*;
import duelistmod.cards.fourthWarriors.*;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.orbCards.*;
import duelistmod.cards.tokens.*;
import duelistmod.helpers.*;
import duelistmod.helpers.poolhelpers.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class DuelistCardLibrary 
{

	// COMPENDIUM MANIPULATION FUNCTIONS /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static ArrayList<AbstractCard> getAllColoredCards()
	{
		return null;
	}
	
	public static void addCardsToGame()
	{
		for (DuelistCard c : DuelistMod.myCards) 
		{ 
			BaseMod.addCard(c); 		
			UnlockTracker.unlockCard(c.getID());
			DuelistMod.summonMap.put(c.originalName, c); 
		}

		for (DuelistCard c : DuelistMod.curses)
		{
			BaseMod.addCard(c); 
			UnlockTracker.unlockCard(c.getID()); 
		}

		DuelistMod.logger.info("theDuelist:DuelistMod:receiveEditCards() ---> done initializing cards");
		DuelistMod.logger.info("theDuelist:DuelistMod:receiveEditCards() ---> saving config options for card set");
		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
			config.setInt(DuelistMod.PROP_DECK, DuelistMod.deckIndex);
			config.setInt(DuelistMod.PROP_RESUMMON_DMG, DuelistMod.resummonDeckDamage);
			config.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setupMyCards()
	{
		DuelistMod.myCards.add(new CastleWalls());
		DuelistMod.myCards.add(new GiantSoldier());
		DuelistMod.myCards.add(new Ookazi());
		DuelistMod.myCards.add(new ScrapFactory());
		DuelistMod.myCards.add(new SevenColoredFish());
		DuelistMod.myCards.add(new SummonedSkull());
		DuelistMod.myCards.add(new ArmoredZombie());
		DuelistMod.myCards.add(new AxeDespair());
		DuelistMod.myCards.add(new BabyDragon());
		DuelistMod.myCards.add(new BadReaction());
		DuelistMod.myCards.add(new BigFire());
		DuelistMod.myCards.add(new BlizzardDragon());
		DuelistMod.myCards.add(new BlueEyes());
		DuelistMod.myCards.add(new BlueEyesUltimate());
		DuelistMod.myCards.add(new BusterBlader());
		DuelistMod.myCards.add(new CannonSoldier());
		DuelistMod.myCards.add(new CardDestruction());
		DuelistMod.myCards.add(new CastleDarkIllusions());
		DuelistMod.myCards.add(new CatapultTurtle());
		DuelistMod.myCards.add(new CaveDragon());
		DuelistMod.myCards.add(new CelticGuardian());
		DuelistMod.myCards.add(new ChangeHeart());
		DuelistMod.myCards.add(new DarkMagician());
		DuelistMod.myCards.add(new DarklordMarie());
		DuelistMod.myCards.add(new DianKeto());
		//DuelistMod.myCards.add(new DragonCaptureJar());
		DuelistMod.myCards.add(new FiendMegacyber());
		DuelistMod.myCards.add(new Fissure());
		DuelistMod.myCards.add(new FluteSummoning());
		DuelistMod.myCards.add(new FortressWarrior());
		DuelistMod.myCards.add(new GaiaDragonChamp());
		DuelistMod.myCards.add(new GaiaFierce());
		DuelistMod.myCards.add(new GeminiElf());
		DuelistMod.myCards.add(new GracefulCharity());
		DuelistMod.myCards.add(new GravityAxe());
		DuelistMod.myCards.add(new HaneHane());
		DuelistMod.myCards.add(new Hinotama());
		DuelistMod.myCards.add(new ImperialOrder());
		DuelistMod.myCards.add(new InjectionFairy());
		DuelistMod.myCards.add(new InsectQueen());
		DuelistMod.myCards.add(new IslandTurtle());
		DuelistMod.myCards.add(new JamBreeding());
		DuelistMod.myCards.add(new JudgeMan());
		DuelistMod.myCards.add(new Kuriboh());
		DuelistMod.myCards.add(new LabyrinthWall());		
		DuelistMod.myCards.add(new LesserDragon());
		DuelistMod.myCards.add(new LordD());
		DuelistMod.myCards.add(new MirrorForce());
		DuelistMod.myCards.add(new MonsterReborn());
		DuelistMod.myCards.add(new Mountain());
		DuelistMod.myCards.add(new MysticalElf());
		DuelistMod.myCards.add(new ObeliskTormentor());
		DuelistMod.myCards.add(new PotGenerosity());
		DuelistMod.myCards.add(new PotGreed());
		DuelistMod.myCards.add(new PreventRat());
		DuelistMod.myCards.add(new Pumpking());
		DuelistMod.myCards.add(new Raigeki());
		DuelistMod.myCards.add(new RainMercy());
		DuelistMod.myCards.add(new RedEyes());
		DuelistMod.myCards.add(new Relinquished());
		DuelistMod.myCards.add(new SangaEarth());
		DuelistMod.myCards.add(new SangaThunder());		
		DuelistMod.myCards.add(new Scapegoat());			
		DuelistMod.myCards.add(new SliferSky());
		DuelistMod.myCards.add(new SnowDragon());
		DuelistMod.myCards.add(new SnowdustDragon());
		DuelistMod.myCards.add(new SpiritHarp());		
		DuelistMod.myCards.add(new SuperheavyBenkei());
		DuelistMod.myCards.add(new SuperheavyScales());
		DuelistMod.myCards.add(new SuperheavySwordsman());
		DuelistMod.myCards.add(new SuperheavyWaraji());
		DuelistMod.myCards.add(new ThunderDragon());
		DuelistMod.myCards.add(new WingedDragonRa());
		DuelistMod.myCards.add(new Yami());
		DuelistMod.myCards.add(new NeoMagic());
		DuelistMod.myCards.add(new GoldenApples());
		DuelistMod.myCards.add(new SphereKuriboh());
		DuelistMod.myCards.add(new Wiseman());
		DuelistMod.myCards.add(new Sparks());
		DuelistMod.myCards.add(new CastleWallsBasic());
		DuelistMod.myCards.add(new Sangan());
		DuelistMod.myCards.add(new FlameSwordsman()); 
		DuelistMod.myCards.add(new BlastJuggler());
		DuelistMod.myCards.add(new MachineKing());
		DuelistMod.myCards.add(new BookSecret());
		DuelistMod.myCards.add(new HeavyStorm());
		DuelistMod.myCards.add(new FogKing());
		DuelistMod.myCards.add(new Lajinn());
		DuelistMod.myCards.add(new KingYami());
		DuelistMod.myCards.add(new BlacklandFireDragon());
		DuelistMod.myCards.add(new WhiteNightDragon());
		DuelistMod.myCards.add(new WhiteHornDragon());
		DuelistMod.myCards.add(new RevivalJam());
		DuelistMod.myCards.add(new StimPack());
		DuelistMod.myCards.add(new BottomlessTrapHole());
		DuelistMod.myCards.add(new SwordDeepSeated());
		DuelistMod.myCards.add(new MonsterEgg());
		DuelistMod.myCards.add(new SteamTrainKing());
		DuelistMod.myCards.add(new MachineFactory());
		DuelistMod.myCards.add(new TributeDoomed());
		DuelistMod.myCards.add(new PetitMoth());
		DuelistMod.myCards.add(new CocoonEvolution());
		DuelistMod.myCards.add(new CheerfulCoffin());
		DuelistMod.myCards.add(new TheCreator());
		DuelistMod.myCards.add(new Polymerization());
		DuelistMod.myCards.add(new VioletCrystal());
		DuelistMod.myCards.add(new Predaponics());
		DuelistMod.myCards.add(new MetalDragon());
		DuelistMod.myCards.add(new SuperSolarNutrient());
		DuelistMod.myCards.add(new Gigaplant());
		DuelistMod.myCards.add(new BasicInsect());		
		DuelistMod.myCards.add(new BSkullDragon());
		DuelistMod.myCards.add(new DarkfireDragon());
		DuelistMod.myCards.add(new EmpressMantis());
		DuelistMod.myCards.add(new Grasschopper());
		DuelistMod.myCards.add(new Jinzo());
		DuelistMod.myCards.add(new LeviaDragon());
		DuelistMod.myCards.add(new ManEaterBug());
		DuelistMod.myCards.add(new OceanDragonLord());
		DuelistMod.myCards.add(new PredaplantChimerafflesia());
		DuelistMod.myCards.add(new PredaplantChlamydosundew());
		DuelistMod.myCards.add(new PredaplantDrosophyllum());
		DuelistMod.myCards.add(new PredaplantFlytrap());
		DuelistMod.myCards.add(new PredaplantPterapenthes());
		DuelistMod.myCards.add(new PredaplantSarraceniant());
		DuelistMod.myCards.add(new PredaplantSpinodionaea());
		DuelistMod.myCards.add(new Predapruning());
		DuelistMod.myCards.add(new TrihornedDragon());
		DuelistMod.myCards.add(new Wiretap());
		DuelistMod.myCards.add(new Reinforcements());
		DuelistMod.myCards.add(new UltimateOffering());
		DuelistMod.myCards.add(new JerryBeansMan());
		DuelistMod.myCards.add(new Illusionist());
		DuelistMod.myCards.add(new ExploderDragon());
		DuelistMod.myCards.add(new Invigoration());
		DuelistMod.myCards.add(new InvitationDarkSleep());
		DuelistMod.myCards.add(new AcidTrapHole());
		DuelistMod.myCards.add(new AltarTribute());
		DuelistMod.myCards.add(new BlizzardPrincess());
		DuelistMod.myCards.add(new CardSafeReturn());
		DuelistMod.myCards.add(new Cloning());
		DuelistMod.myCards.add(new ComicHand());
		DuelistMod.myCards.add(new ContractExodia());
		DuelistMod.myCards.add(new DarkCreator());
		DuelistMod.myCards.add(new DoubleCoston());
		DuelistMod.myCards.add(new GatesDarkWorld());
		DuelistMod.myCards.add(new HammerShot());
		DuelistMod.myCards.add(new HeartUnderdog());
		DuelistMod.myCards.add(new InsectKnight());
		DuelistMod.myCards.add(new JiraiGumo());
		DuelistMod.myCards.add(new MythicalBeast());
		DuelistMod.myCards.add(new PotForbidden());
		DuelistMod.myCards.add(new SmashingGround());
		DuelistMod.myCards.add(new Terraforming());
		DuelistMod.myCards.add(new TerraTerrible());
		DuelistMod.myCards.add(new IcyCrevasse());
		DuelistMod.myCards.add(new StrayLambs());
		DuelistMod.myCards.add(new GuardianAngel());
		DuelistMod.myCards.add(new ShadowToon());
		DuelistMod.myCards.add(new ShallowGrave());
		DuelistMod.myCards.add(new MiniPolymerization());
		DuelistMod.myCards.add(new WorldCarrot());
		DuelistMod.myCards.add(new SoulAbsorbingBone());
		DuelistMod.myCards.add(new MsJudge());
		DuelistMod.myCards.add(new FiendishChain());
		DuelistMod.myCards.add(new Firegrass());
		DuelistMod.myCards.add(new PowerKaishin());
		DuelistMod.myCards.add(new AncientElf());
		DuelistMod.myCards.add(new FinalFlame());
		DuelistMod.myCards.add(new YamataDragon());
		DuelistMod.myCards.add(new TwinBarrelDragon());		
		DuelistMod.myCards.add(new HundredFootedHorror());
		DuelistMod.myCards.add(new SlateWarrior());
		DuelistMod.myCards.add(new MotherSpider());
		DuelistMod.myCards.add(new MangaRyuRan());
		DuelistMod.myCards.add(new ToonAncientGear());
		DuelistMod.myCards.add(new ClownZombie());
		DuelistMod.myCards.add(new RyuKokki());
		DuelistMod.myCards.add(new GoblinRemedy());
		DuelistMod.myCards.add(new CallGrave());
		DuelistMod.myCards.add(new AllyJustice());
		DuelistMod.myCards.add(new Graverobber());
		DuelistMod.myCards.add(new DragonPiper());
		DuelistMod.myCards.add(new SwordsRevealing());
		DuelistMod.myCards.add(new TimeWizard()); 
		DuelistMod.myCards.add(new TrapHole());
		DuelistMod.myCards.add(new BlueEyesToon());
		DuelistMod.myCards.add(new DragonMaster());
		DuelistMod.myCards.add(new Gandora());
		DuelistMod.myCards.add(new LegendaryExodia());
		DuelistMod.myCards.add(new RadiantMirrorForce());
		DuelistMod.myCards.add(new RedEyesToon());
		DuelistMod.myCards.add(new SuperancientDinobeast());
		DuelistMod.myCards.add(new TokenVacuum());
		DuelistMod.myCards.add(new ToonBarrelDragon());
		DuelistMod.myCards.add(new ToonBriefcase());
		DuelistMod.myCards.add(new ToonDarkMagician());
		DuelistMod.myCards.add(new ToonGeminiElf());
		DuelistMod.myCards.add(new ToonMagic());	   
		DuelistMod.myCards.add(new ToonMask());
		DuelistMod.myCards.add(new ToonMermaid());
		DuelistMod.myCards.add(new ToonRollback());
		DuelistMod.myCards.add(new ToonSummonedSkull());
		DuelistMod.myCards.add(new ToonWorld());
		DuelistMod.myCards.add(new ToonKingdom());
		DuelistMod.myCards.add(new CurseDragon());
		DuelistMod.myCards.add(new CyberDragon());
		DuelistMod.myCards.add(new DarkFactory());
		DuelistMod.myCards.add(new FiendSkull());
		DuelistMod.myCards.add(new FiveHeaded());
		DuelistMod.myCards.add(new GiantTrunade());
		DuelistMod.myCards.add(new HarpieFeather());
		DuelistMod.myCards.add(new MoltenZombie());
		DuelistMod.myCards.add(new OjamaGreen());
		DuelistMod.myCards.add(new OjamaYellow());
		DuelistMod.myCards.add(new Ojamagic());
		DuelistMod.myCards.add(new Ojamuscle());
		DuelistMod.myCards.add(new PotAvarice());
		DuelistMod.myCards.add(new PotDichotomy());
		DuelistMod.myCards.add(new PotDuality());
		DuelistMod.myCards.add(new Pumprincess());		
		DuelistMod.myCards.add(new RedEyesZombie());
		DuelistMod.myCards.add(new RedMedicine());
		DuelistMod.myCards.add(new ShardGreed());
		DuelistMod.myCards.add(new StormingMirrorForce());
		DuelistMod.myCards.add(new SuperheavyBlueBrawler());
		DuelistMod.myCards.add(new SuperheavyDaihachi());
		DuelistMod.myCards.add(new SuperheavyFlutist());	    
		DuelistMod.myCards.add(new SuperheavyGeneral());
		DuelistMod.myCards.add(new SuperheavyMagnet());	    
		DuelistMod.myCards.add(new SuperheavyOgre());
		DuelistMod.myCards.add(new SwordsBurning());
		DuelistMod.myCards.add(new SwordsConcealing());
		DuelistMod.myCards.add(new AlphaMagnet());
		DuelistMod.myCards.add(new AncientRules());
		DuelistMod.myCards.add(new BetaMagnet());
		DuelistMod.myCards.add(new DarkHole());
		DuelistMod.myCards.add(new DarkMagicianGirl());
		DuelistMod.myCards.add(new ExodiaHead());
		DuelistMod.myCards.add(new ExodiaLA());
		DuelistMod.myCards.add(new ExodiaLL());
		DuelistMod.myCards.add(new ExodiaRA());
		DuelistMod.myCards.add(new ExodiaRL());
		DuelistMod.myCards.add(new FeatherPho());
		DuelistMod.myCards.add(new GammaMagnet());		
		DuelistMod.myCards.add(new Mausoleum());
		DuelistMod.myCards.add(new MillenniumShield());
		DuelistMod.myCards.add(new ValkMagnet());
		DuelistMod.myCards.add(new BarrelDragon());
		DuelistMod.myCards.add(new DarkMirrorForce());
		DuelistMod.myCards.add(new MagicCylinder());
		DuelistMod.myCards.add(new NutrientZ());
		DuelistMod.myCards.add(new OjamaBlack());
		DuelistMod.myCards.add(new OjamaKing());
		DuelistMod.myCards.add(new OjamaKnight());
		DuelistMod.myCards.add(new Parasite());
		DuelistMod.myCards.add(new BeastFangs());
		DuelistMod.myCards.add(new BeaverWarrior());
		DuelistMod.myCards.add(new NaturiaBeast());	
		DuelistMod.myCards.add(new NaturiaCliff());
		DuelistMod.myCards.add(new NaturiaDragonfly());
		DuelistMod.myCards.add(new NaturiaGuardian());
		DuelistMod.myCards.add(new NaturiaHorneedle());
		DuelistMod.myCards.add(new NaturiaLandoise());
		DuelistMod.myCards.add(new NaturiaMantis());
		DuelistMod.myCards.add(new NaturiaPineapple());
		DuelistMod.myCards.add(new NaturiaPumpkin());
		DuelistMod.myCards.add(new NaturiaRosewhip());
		DuelistMod.myCards.add(new NaturiaSacredTree());
		DuelistMod.myCards.add(new NaturiaStrawberry());		
		DuelistMod.myCards.add(new IceQueen());
		DuelistMod.myCards.add(new ThousandEyesIdol());
		DuelistMod.myCards.add(new ThousandEyesRestrict());
		DuelistMod.myCards.add(new Zombyra());
		DuelistMod.myCards.add(new TwinHeadedFire());
		DuelistMod.myCards.add(new MindAir());
		DuelistMod.myCards.add(new FrontierWiseman());
		DuelistMod.myCards.add(new OjamaRed());
		DuelistMod.myCards.add(new OjamaBlue());
		DuelistMod.myCards.add(new OjamaDeltaHurricane());
		DuelistMod.myCards.add(new OjamaCountry());
		DuelistMod.myCards.add(new OjamaDuo());
		DuelistMod.myCards.add(new OjamaEmperor());
		DuelistMod.myCards.add(new OjamaPajama());
		DuelistMod.myCards.add(new Ojamassimilation());
		DuelistMod.myCards.add(new Ojamatch());
		DuelistMod.myCards.add(new OjamaTrio());
		DuelistMod.myCards.add(new Mathematician());
		DuelistMod.myCards.add(new ToonDarkMagicianGirl());
		DuelistMod.myCards.add(new GateGuardian());
		DuelistMod.myCards.add(new SangaWater());		
		DuelistMod.myCards.add(new LegendaryFisherman());
		//DuelistMod.myCards.add(new DarkMimicLv1());
		//DuelistMod.myCards.add(new DarkMimicLv3());
		DuelistMod.myCards.add(new FairyBox());
		DuelistMod.myCards.add(new ToonTable());
		DuelistMod.myCards.add(new ToonDefense());
		DuelistMod.myCards.add(new ToonCannonSoldier());
		DuelistMod.myCards.add(new AngelTrumpeter());
		DuelistMod.myCards.add(new ArmoredBee());
		DuelistMod.myCards.add(new DarkBlade());
		DuelistMod.myCards.add(new DarkMasterZorc());		
		DuelistMod.myCards.add(new SuperheavySoulbeads());
		DuelistMod.myCards.add(new SuperheavySoulbuster());
		//DuelistMod.myCards.add(new SuperheavySoulclaw());
		DuelistMod.myCards.add(new SuperheavySoulhorns());
		DuelistMod.myCards.add(new SuperheavySoulpiercer());
		DuelistMod.myCards.add(new SuperheavySoulshield());
		DuelistMod.myCards.add(new AquaSpirit());
		DuelistMod.myCards.add(new Umi());
		DuelistMod.myCards.add(new OhFish());
		DuelistMod.myCards.add(new DarkEnergy());
		DuelistMod.myCards.add(new SkullArchfiend());
		DuelistMod.myCards.add(new VanityFiend());
		DuelistMod.myCards.add(new RegenMummy());
		DuelistMod.myCards.add(new ArchfiendSoldier());
		DuelistMod.myCards.add(new FabledAshenveil());
		DuelistMod.myCards.add(new FabledGallabas());
		DuelistMod.myCards.add(new BattleOx());
		DuelistMod.myCards.add(new DarkstormDragon());
		DuelistMod.myCards.add(new GrossGhost());
		DuelistMod.myCards.add(new PortraitSecret());
		DuelistMod.myCards.add(new RedSprinter());
		DuelistMod.myCards.add(new Tierra());
		DuelistMod.myCards.add(new PatricianDarkness());
		DuelistMod.myCards.add(new WingedKuriboh());
		DuelistMod.myCards.add(new JunkKuriboh());
		DuelistMod.myCards.add(new FluteKuriboh());
		DuelistMod.myCards.add(new ApprenticeIllusionMagician());
		DuelistMod.myCards.add(new BlueDragonSummoner());
		DuelistMod.myCards.add(new DarkHorizon());
		DuelistMod.myCards.add(new Skelesaurus());
		DuelistMod.myCards.add(new RedGadget());
		DuelistMod.myCards.add(new GreenGadget());
		DuelistMod.myCards.add(new YellowGadget());
		DuelistMod.myCards.add(new BlizzardWarrior());
		DuelistMod.myCards.add(new DarkPaladin());
		DuelistMod.myCards.add(new BigKoala());
		DuelistMod.myCards.add(new AncientGearChimera());
		DuelistMod.myCards.add(new AncientGearGadjiltron());
		DuelistMod.myCards.add(new AncientGearGolem());
		DuelistMod.myCards.add(new WhiteMagicalHat());
		DuelistMod.myCards.add(new BattleguardKing());		
		DuelistMod.myCards.add(new BattleFootballer());
		DuelistMod.myCards.add(new EarthquakeGiant());
		DuelistMod.myCards.add(new EvilswarmHeliotrope());
		DuelistMod.myCards.add(new GadgetSoldier());
		DuelistMod.myCards.add(new LegendaryFlameLord());
		DuelistMod.myCards.add(new TurretWarrior());
		DuelistMod.myCards.add(new WormApocalypse());
		DuelistMod.myCards.add(new WormBarses());
		DuelistMod.myCards.add(new WormWarlord());
		DuelistMod.myCards.add(new WormKing());
		DuelistMod.myCards.add(new ArmorBreaker());
		DuelistMod.myCards.add(new GauntletWarrior());
		DuelistMod.myCards.add(new CommandKnight());
		DuelistMod.myCards.add(new GaiaMidnight());
		DuelistMod.myCards.add(new DrivenDaredevil());
		DuelistMod.myCards.add(new GilfordLegend());
		DuelistMod.myCards.add(new ReinforcementsArmy());
		DuelistMod.myCards.add(new BlockGolem());
		DuelistMod.myCards.add(new DokiDoki());
		DuelistMod.myCards.add(new GiantSoldierSteel());
		DuelistMod.myCards.add(new Doomdog());
		DuelistMod.myCards.add(new RedMirror());
		DuelistMod.myCards.add(new LostBlueBreaker());
		DuelistMod.myCards.add(new Wingedtortoise());
		DuelistMod.myCards.add(new GemKnightAmethyst());
		DuelistMod.myCards.add(new ToadallyAwesome());
		DuelistMod.myCards.add(new DarknessNeosphere());
		DuelistMod.myCards.add(new RainbowJar());
		DuelistMod.myCards.add(new WingedKuriboh9());
		DuelistMod.myCards.add(new WingedKuriboh10());
		DuelistMod.myCards.add(new SpikedGillman());
		//DuelistMod.myCards.add(new TripodFish());
		DuelistMod.myCards.add(new MagicalStone());
		DuelistMod.myCards.add(new Kuribohrn());
		DuelistMod.myCards.add(new BigWaveSmallWave());
		DuelistMod.myCards.add(new DropOff());
		DuelistMod.myCards.add(new GraydleSlimeJr());
		DuelistMod.myCards.add(new FrillerRabca());
		DuelistMod.myCards.add(new GiantRex());
		DuelistMod.myCards.add(new IronhammerGiant());
		DuelistMod.myCards.add(new GiantOrc());
		DuelistMod.myCards.add(new ChaosAncientGearGiant());
		DuelistMod.myCards.add(new PowerGiant());
		DuelistMod.myCards.add(new DarkFusion());
		DuelistMod.myCards.add(new WorldTree());
		DuelistMod.myCards.add(new TyrantWing());
		DuelistMod.myCards.add(new WhitefishSalvage());
		DuelistMod.myCards.add(new SwampFrog());
		DuelistMod.myCards.add(new SharkStickers());
		DuelistMod.myCards.add(new RageDeepSea());
		DuelistMod.myCards.add(new SpearfishSoldier());
		DuelistMod.myCards.add(new HydraViper());
		DuelistMod.myCards.add(new AbyssWarrior());
		DuelistMod.myCards.add(new AmphibiousBugroth());
		DuelistMod.myCards.add(new BlizzardDefender());
		DuelistMod.myCards.add(new Boneheimer());
		DuelistMod.myCards.add(new CannonballSpearShellfish());
		DuelistMod.myCards.add(new CrystalEmeraldTortoise());
		DuelistMod.myCards.add(new DeepDiver());
		DuelistMod.myCards.add(new CatShark());
		DuelistMod.myCards.add(new BigWhale());
		DuelistMod.myCards.add(new BlizzardThunderbird());
		DuelistMod.myCards.add(new DiamondDust());
		DuelistMod.myCards.add(new GoldenFlyingFish());
		DuelistMod.myCards.add(new RainbowBridge());
		DuelistMod.myCards.add(new Monokeros());
		DuelistMod.myCards.add(new EarthGiant());
		DuelistMod.myCards.add(new RainbowKuriboh());
		DuelistMod.myCards.add(new ClearKuriboh());
		DuelistMod.myCards.add(new Linkuriboh());
		DuelistMod.myCards.add(new FishSwaps());
		DuelistMod.myCards.add(new FishKicks());
		DuelistMod.myCards.add(new FishRain());
		DuelistMod.myCards.add(new PoseidonWave());
		DuelistMod.myCards.add(new AncientGearBox());
		DuelistMod.myCards.add(new Deskbot001());
		DuelistMod.myCards.add(new Deskbot002());
		DuelistMod.myCards.add(new Deskbot009());		
		DuelistMod.myCards.add(new GrandSpellbookTower());
		DuelistMod.myCards.add(new SpellbookPower());
		DuelistMod.myCards.add(new SpellbookLife());
		DuelistMod.myCards.add(new SpellbookMiracle());
		DuelistMod.myCards.add(new SpellbookKnowledge());
		DuelistMod.myCards.add(new DrillBarnacle());
		DuelistMod.myCards.add(new ImperialTomb());
		DuelistMod.myCards.add(new ZombieMaster());
		DuelistMod.myCards.add(new GiantTrapHole());
		DuelistMod.myCards.add(new GravityBlaster());
		DuelistMod.myCards.add(new FlyingPegasus());
		DuelistMod.myCards.add(new CyberneticFusion());
		DuelistMod.myCards.add(new ReadyForIntercepting());
		DuelistMod.myCards.add(new BigEye());
		DuelistMod.myCards.add(new IronCall());
		DuelistMod.myCards.add(new IronDraw());
		DuelistMod.myCards.add(new LimiterRemoval());
		DuelistMod.myCards.add(new MachineDuplication());		
		DuelistMod.myCards.add(new VampireLord());
		DuelistMod.myCards.add(new VampireGenesis());
		DuelistMod.myCards.add(new VampireGrace());
		DuelistMod.myCards.add(new VampireFraulein());
		DuelistMod.myCards.add(new ShadowVampire());
		DuelistMod.myCards.add(new Mixeroid());
		DuelistMod.myCards.add(new Oilman());
		DuelistMod.myCards.add(new IlBlud());
		DuelistMod.myCards.add(new OutriggerExtension());
		DuelistMod.myCards.add(new JumboDrill());
		DuelistMod.myCards.add(new YamiForm());
		DuelistMod.myCards.add(new DestructPotion());		
		DuelistMod.myCards.add(new CallMummy());
		DuelistMod.myCards.add(new Biofalcon());
		DuelistMod.myCards.add(new Deskbot004());
		DuelistMod.myCards.add(new Deskbot005());
		DuelistMod.myCards.add(new Hayate());
		DuelistMod.myCards.add(new Spore());
		DuelistMod.myCards.add(new RainbowOverdragon());
		DuelistMod.myCards.add(new RainbowGravity());
		DuelistMod.myCards.add(new RainbowLife());
		DuelistMod.myCards.add(new SilverApples());
		DuelistMod.myCards.add(new Uminotaurus());
		DuelistMod.myCards.add(new BigDesFrog());
		DuelistMod.myCards.add(new AtlanteanAttackSquad());
		DuelistMod.myCards.add(new DarklordSuperbia());
		DuelistMod.myCards.add(new ToonGoblinAttack());
		DuelistMod.myCards.add(new ToonMaskedSorcerer());
		DuelistMod.myCards.add(new MillenniumSpellbook());
		DuelistMod.myCards.add(new LightningVortex());
		DuelistMod.myCards.add(new ArmageddonDragonEmp());
		DuelistMod.myCards.add(new BackgroundDragon());
		DuelistMod.myCards.add(new BoosterDragon());
		DuelistMod.myCards.add(new Carboneddon());
		DuelistMod.myCards.add(new BusterBladerDDS());
		DuelistMod.myCards.add(new SilverDragon());
		DuelistMod.myCards.add(new AmuletDragon());
		DuelistMod.myCards.add(new EyeTimaeus());
		DuelistMod.myCards.add(new DragonShield());
		DuelistMod.myCards.add(new SpiritPharaoh());
		DuelistMod.myCards.add(new DarkAssailant());
		DuelistMod.myCards.add(new CallAtlanteans());
		DuelistMod.myCards.add(new DoomShaman());
		DuelistMod.myCards.add(new BlackPendant());
		DuelistMod.myCards.add(new LightningRodLord());
		DuelistMod.myCards.add(new MiracleFertilizer());
		DuelistMod.myCards.add(new MiraculousDescent());		
		DuelistMod.myCards.add(new StatueAnguishPattern());
		DuelistMod.myCards.add(new MudGolem());
		DuelistMod.myCards.add(new Mudora());
		DuelistMod.myCards.add(new MudragonSwamp());
		DuelistMod.myCards.add(new Mudballman());
		DuelistMod.myCards.add(new VoidExpansion());
		DuelistMod.myCards.add(new VoidVanishment());
		DuelistMod.myCards.add(new LavaDragon());
		DuelistMod.myCards.add(new FlameTiger());
		DuelistMod.myCards.add(new Reload());			
		DuelistMod.myCards.add(new ArchfiendCommander());		
		DuelistMod.myCards.add(new ArchfiendGeneral());		
		DuelistMod.myCards.add(new ArchfiendHeiress());		
		DuelistMod.myCards.add(new ArchfiendInterceptor());		
		DuelistMod.myCards.add(new BeastTalwar());		
		DuelistMod.myCards.add(new DarkSimorgh());		
		DuelistMod.myCards.add(new DarkTinker());		
		DuelistMod.myCards.add(new DoomcaliberKnight());		
		DuelistMod.myCards.add(new DoomsdayHorror());		
		DuelistMod.myCards.add(new EvilswarmNightmare());		
		DuelistMod.myCards.add(new ForbiddenLance());		
		DuelistMod.myCards.add(new GarbageLord());		
		DuelistMod.myCards.add(new MagicalBlast());		
		DuelistMod.myCards.add(new MaskedDragon());		
		DuelistMod.myCards.add(new MirageDragon());		
		DuelistMod.myCards.add(new StarBlast());		
		DuelistMod.myCards.add(new StardustDragon());		
		DuelistMod.myCards.add(new TwinHeadedBehemoth());		
		DuelistMod.myCards.add(new TwinHeadedWolf());		
		DuelistMod.myCards.add(new ViceDragon());		
		DuelistMod.myCards.add(new WanderingKing());		
		DuelistMod.myCards.add(new AmbitiousGofer());		
		DuelistMod.myCards.add(new CosmoBrain());		
		DuelistMod.myCards.add(new DiffusionWaveMotion());		
		DuelistMod.myCards.add(new ForbiddenChalice());		
		DuelistMod.myCards.add(new GoblinKing());		
		DuelistMod.myCards.add(new HorusServant());		
		DuelistMod.myCards.add(new PutridPudding());		
		DuelistMod.myCards.add(new QueenDragunDjinn());		
		DuelistMod.myCards.add(new FutureFusion());		
		DuelistMod.myCards.add(new DoomDonuts());		
		DuelistMod.myCards.add(new InariFire());		
		DuelistMod.myCards.add(new MagiciansRobe());		
		DuelistMod.myCards.add(new MagiciansRod());		
		DuelistMod.myCards.add(new TotemDragon());		
		DuelistMod.myCards.add(new WonderWand());		
		DuelistMod.myCards.add(new KamionTimelord());
		DuelistMod.myCards.add(new IrisEarthMother());
		DuelistMod.myCards.add(new RainbowRefraction());
		DuelistMod.myCards.add(new CrystalRaigeki());
		DuelistMod.myCards.add(new RainbowRuins());
		DuelistMod.myCards.add(new RainbowMagician());
		DuelistMod.myCards.add(new RainbowDarkDragon());
		DuelistMod.myCards.add(new MaleficRainbowDragon());
		DuelistMod.myCards.add(new RainbowDragon());
		DuelistMod.myCards.add(new HourglassLife());
		DuelistMod.myCards.add(new Eva());
		DuelistMod.myCards.add(new HappyLover());
		DuelistMod.myCards.add(new DunamesDarkWitch());
		DuelistMod.myCards.add(new RainbowNeos());
		DuelistMod.myCards.add(new RainbowFlower());
		DuelistMod.myCards.add(new RainbowKuribohBasic());
		DuelistMod.myCards.add(new ElectromagneticShield());
		DuelistMod.myCards.add(new Electrowhip());
		DuelistMod.myCards.add(new QueenAngelRoses());
		DuelistMod.myCards.add(new CyberArchfiend());
		DuelistMod.myCards.add(new BusterDrake());
		DuelistMod.myCards.add(new RedRisingDragon());
		DuelistMod.myCards.add(new AncientGearFist());
		DuelistMod.myCards.add(new BerserkerCrush());
		DuelistMod.myCards.add(new CombinationAttack());
		DuelistMod.myCards.add(new CoreBlaster());
		DuelistMod.myCards.add(new FeatherShot());
		DuelistMod.myCards.add(new FuryFire());
		DuelistMod.myCards.add(new InfernoFireBlast());
		DuelistMod.myCards.add(new MeteorDestruction());
		DuelistMod.myCards.add(new SilentDoom());
		DuelistMod.myCards.add(new SparkBlaster());
		DuelistMod.myCards.add(new SpellShatteringArrow());
		DuelistMod.myCards.add(new SpiralSpearStrike());
		DuelistMod.myCards.add(new VenomShot());
		DuelistMod.myCards.add(new Wildfire());
		DuelistMod.myCards.add(new CharcoalInpachi());
		DuelistMod.myCards.add(new WaterDragon());
		DuelistMod.myCards.add(new AmuletAmbition());
		DuelistMod.myCards.add(new ArchfiendZombieSkull());		
		DuelistMod.myCards.add(new CorrodingShark());		
		DuelistMod.myCards.add(new FlameGhost());
		DuelistMod.myCards.add(new Gernia());
		DuelistMod.myCards.add(new GoblinZombie());
		DuelistMod.myCards.add(new Gozuki());
		DuelistMod.myCards.add(new Kasha());
		DuelistMod.myCards.add(new OniTankT34());
		DuelistMod.myCards.add(new RedHeadedOni());		
		DuelistMod.myCards.add(new ZombieWarrior());
		DuelistMod.myCards.add(new BlueBloodedOni());
		DuelistMod.myCards.add(new DesLacooda());
		DuelistMod.myCards.add(new DespairFromDark());		
		DuelistMod.myCards.add(new EndlessDecay());		
		DuelistMod.myCards.add(new HauntedShrine());
		DuelistMod.myCards.add(new OniGamiCombo());		
		DuelistMod.myCards.add(new PlaguespreaderZombie());
		DuelistMod.myCards.add(new YellowBelliedOni());
		DuelistMod.myCards.add(new ZombieWorld());
		DuelistMod.myCards.add(new FearFromDark());		
		DuelistMod.myCards.add(new BeserkDragon());
		DuelistMod.myCards.add(new PainPainter());		
		DuelistMod.myCards.add(new DoomkaiserDragon());		
		DuelistMod.myCards.add(new UnderworldCannon());
		DuelistMod.myCards.add(new DragonZombie());	
		DuelistMod.myCards.add(new ZombieMammoth());	
		DuelistMod.myCards.add(new FlyingSaucer());
		DuelistMod.myCards.add(new Relinkuriboh());
		DuelistMod.myCards.add(new GoldenBlastJuggler());
		DuelistMod.myCards.add(new CosmicHorrorGangiel());
		DuelistMod.myCards.add(new AlienTelepath());
		DuelistMod.myCards.add(new BlastAsmodian());
		DuelistMod.myCards.add(new BlastHeldTribute());
		DuelistMod.myCards.add(new BlastMagician());
		DuelistMod.myCards.add(new BlastWithChain());
		DuelistMod.myCards.add(new ExploderDragonwing());
		DuelistMod.myCards.add(new OrbitalBombardment());
		DuelistMod.myCards.add(new AncientGearExplosive());
		DuelistMod.myCards.add(new BlasterDragonInfernos());
		DuelistMod.myCards.add(new BlastingRuins());
		DuelistMod.myCards.add(new BlossomBombardment());
		DuelistMod.myCards.add(new BlastingFuse());
		DuelistMod.myCards.add(new CemetaryBomb());
		DuelistMod.myCards.add(new RockBombardment());
		DuelistMod.myCards.add(new Obliterate());
		DuelistMod.myCards.add(new ExodiaNecross());		
		DuelistMod.myCards.add(new AlphaElectro());
		DuelistMod.myCards.add(new DeltaMagnet());
		DuelistMod.myCards.add(new Berserkion());
		DuelistMod.myCards.add(new BetaElectro());
		DuelistMod.myCards.add(new GammaElectro());
		DuelistMod.myCards.add(new MagneticField());
		DuelistMod.myCards.add(new UmbralHorrorGhoul());		
		DuelistMod.myCards.add(new RagingMadPlants());
		DuelistMod.myCards.add(new ThornMalice());
		DuelistMod.myCards.add(new ArsenalBug());
		DuelistMod.myCards.add(new CrossSwordBeetle());
		DuelistMod.myCards.add(new MultiplicationOfAnts());
		DuelistMod.myCards.add(new DarkSpider());
		DuelistMod.myCards.add(new CocoonUltraEvolution());
		DuelistMod.myCards.add(new PinchHopper());
		DuelistMod.myCards.add(new UndergroundArachnid());
		DuelistMod.myCards.add(new RockSunrise());		
		DuelistMod.myCards.add(new BlackRoseDragon());
		DuelistMod.myCards.add(new BlackRoseMoonlight());
		DuelistMod.myCards.add(new FallenAngelRoses());
		DuelistMod.myCards.add(new RedRoseDragon());
		DuelistMod.myCards.add(new RosePaladin());
		DuelistMod.myCards.add(new TwilightRoseKnight());
		DuelistMod.myCards.add(new BirdRoses());
		DuelistMod.myCards.add(new BlockSpider());
		DuelistMod.myCards.add(new BlueRoseDragon());
		DuelistMod.myCards.add(new ClearWingDragon());
		DuelistMod.myCards.add(new CopyPlant());
		DuelistMod.myCards.add(new CrystalWingDragon());
		DuelistMod.myCards.add(new FrozenRose());
		DuelistMod.myCards.add(new MarkRose());
		DuelistMod.myCards.add(new RevivalRose());
		DuelistMod.myCards.add(new RoseArcher());
		DuelistMod.myCards.add(new RoseLover());
		DuelistMod.myCards.add(new RoseWitch());
		DuelistMod.myCards.add(new UltraPolymerization());
		DuelistMod.myCards.add(new WhiteRoseDragon());
		DuelistMod.myCards.add(new WitchBlackRose());
		DuelistMod.myCards.add(new BloomingDarkestRose());
		DuelistMod.myCards.add(new SplendidRose());
		DuelistMod.myCards.add(new CactusBouncer());
		DuelistMod.myCards.add(new Inmato());
		DuelistMod.myCards.add(new PlantFoodChain());
		DuelistMod.myCards.add(new SeedCannon());
		DuelistMod.myCards.add(new BotanicalLion());
		DuelistMod.myCards.add(new BotanicalGirl());
		DuelistMod.myCards.add(new DarkworldThorns());
		DuelistMod.myCards.add(new LordPoison());
		DuelistMod.myCards.add(new MillenniumScorpion());				
		DuelistMod.myCards.add(new UmbralHorrorGhost());
		DuelistMod.myCards.add(new UmbralHorrorWilloWisp());
		DuelistMod.myCards.add(new SkilledDarkMagician());
		DuelistMod.myCards.add(new UmbralHorrorUniform());
		DuelistMod.myCards.add(new DarkHunter());
		DuelistMod.myCards.add(new Slushy());
		DuelistMod.myCards.add(new PharaohBlessing());
		DuelistMod.myCards.add(new Metronome());
		DuelistMod.myCards.add(new UncommonMetronome());
		DuelistMod.myCards.add(new UncommonAttackMetronome());
		DuelistMod.myCards.add(new AttackMetronome());
		DuelistMod.myCards.add(new SkillMetronome());
		DuelistMod.myCards.add(new RareAttackMetronome());
		DuelistMod.myCards.add(new RareSkillMetronome());
		DuelistMod.myCards.add(new PowerMetronome());
		DuelistMod.myCards.add(new RarePowerMetronome());
		DuelistMod.myCards.add(new AttackTrapMetronome());
		DuelistMod.myCards.add(new TrapMetronome());
		DuelistMod.myCards.add(new BlockMetronome());
		DuelistMod.myCards.add(new BlockSpellMetronome());
		DuelistMod.myCards.add(new RareBlockMetronome());
		DuelistMod.myCards.add(new SpellMetronome());
		DuelistMod.myCards.add(new OrbMetronome());
		DuelistMod.myCards.add(new OneForOne());
		DuelistMod.myCards.add(new MonsterEggSpecial());
		DuelistMod.myCards.add(new RainbowMedicine());
		DuelistMod.myCards.add(new MonsterEggSpecial());
		DuelistMod.myCards.add(new DarkCubicLord());
		DuelistMod.myCards.add(new Overworked());
		DuelistMod.myCards.add(new GoldenSparks());

		DuelistMod.myCards.add(new AssaultArmor());
		DuelistMod.myCards.add(new BambooSwordBroken());
		DuelistMod.myCards.add(new BambooSwordBurning());
		DuelistMod.myCards.add(new BambooSwordCursed());
		DuelistMod.myCards.add(new BambooSwordGolden());
		DuelistMod.myCards.add(new BambooSwordSoul());
		DuelistMod.myCards.add(new BattleWarrior());
		DuelistMod.myCards.add(new BladeArmorNinja());
		DuelistMod.myCards.add(new BladeKnight());
		DuelistMod.myCards.add(new BrushfireKnight());
		DuelistMod.myCards.add(new BullBlader());
		DuelistMod.myCards.add(new ColossalFighter());
		DuelistMod.myCards.add(new ComboFighter());
		DuelistMod.myCards.add(new ComboMaster());
		DuelistMod.myCards.add(new CrossAttack());
		DuelistMod.myCards.add(new DarkGrepher());
		DuelistMod.myCards.add(new DustKnight());
		DuelistMod.myCards.add(new FeedbackWarrior());
		DuelistMod.myCards.add(new GoyoChaser());
		DuelistMod.myCards.add(new GoyoKing());
		DuelistMod.myCards.add(new GravityWarrior());
		DuelistMod.myCards.add(new JunkWarrior());
		DuelistMod.myCards.add(new LegendarySword());
		DuelistMod.myCards.add(new LightningBlade());
		DuelistMod.myCards.add(new MightyWarrior());
		DuelistMod.myCards.add(new NinjaGrandmaster());
		DuelistMod.myCards.add(new NitroWarrior());
		DuelistMod.myCards.add(new SoldierLady());
		DuelistMod.myCards.add(new SwordDragonSoul());
		DuelistMod.myCards.add(new AdvanceForce());
		DuelistMod.myCards.add(new AfterGenocide());
		DuelistMod.myCards.add(new AfterTheStorm());
		DuelistMod.myCards.add(new AgainstTheWind());
		DuelistMod.myCards.add(new BattleguardHowling());
		DuelistMod.myCards.add(new BattleguardRage());
		DuelistMod.myCards.add(new ChaosSeed());
		DuelistMod.myCards.add(new CrystalBlessing());
		DuelistMod.myCards.add(new CrystalTree());
		DuelistMod.myCards.add(new CubicWave());
		DuelistMod.myCards.add(new DarkBurningAttack());
		DuelistMod.myCards.add(new DarkBurningMagic());
		DuelistMod.myCards.add(new DarkCrusader());
		DuelistMod.myCards.add(new DarkOccultism());
		DuelistMod.myCards.add(new DawnKnight());
		DuelistMod.myCards.add(new DefenseZone());
		DuelistMod.myCards.add(new DeltaAttacker());
		DuelistMod.myCards.add(new Downbeat());
		DuelistMod.myCards.add(new EgoBoost());
		DuelistMod.myCards.add(new ElfLight());
		DuelistMod.myCards.add(new EulerCircuit());
		DuelistMod.myCards.add(new FengshengMirror());
		DuelistMod.myCards.add(new GladiatorReturn());
		DuelistMod.myCards.add(new GlowingCrossbow());
		DuelistMod.myCards.add(new GoyoDefender());
		DuelistMod.myCards.add(new GoyoEmperor());
		DuelistMod.myCards.add(new GravityLash());
		DuelistMod.myCards.add(new GuardianOrder());
		DuelistMod.myCards.add(new HardArmor());
		DuelistMod.myCards.add(new HarmonicWaves());
		DuelistMod.myCards.add(new HerculeanPower());
		DuelistMod.myCards.add(new HeroRing());
		DuelistMod.myCards.add(new HiddenArmory());
		DuelistMod.myCards.add(new LegendHeart());
		DuelistMod.myCards.add(new LegendaryBlackBelt());
		DuelistMod.myCards.add(new LightLaser());
		DuelistMod.myCards.add(new MagnumShield());
		DuelistMod.myCards.add(new WarriorReturningAlive());
		DuelistMod.myCards.add(new CommanderSwords());
		//DuelistMod.myCards.add(new CubicKarma());
		DuelistMod.myCards.add(new FightingSpirit());
		DuelistMod.myCards.add(new Flint());
		DuelistMod.myCards.add(new GridRod());
		//DuelistMod.myCards.add(new ReinforceTruth());
		//DuelistMod.myCards.add(new SpiritForce());
		//DuelistMod.myCards.add(new WeaponChange());		
		
		//DuelistMod.myCards.add(new WightLady());			
		
		if (DuelistMod.duelistCurses)
		{
			DuelistMod.curses.add(new GravekeeperCurse());
			DuelistMod.curses.add(new CurseAnubis());
			DuelistMod.curses.add(new CurseArmaments());
			DuelistMod.curses.add(new CursedBill());
			DuelistMod.curses.add(new CurseAging());
			DuelistMod.curses.add(new SummoningCurse());
			DuelistMod.curses.add(new VampireCurse());
			DuelistMod.curses.add(new PsiCurse());
			//DuelistMod.curses.add(new CurseRoyal());
		}
		
		for (CardTags t : DuelistMod.monsterTypes)
		{
			String ID = DuelistMod.typeCardMap_ID.get(t);
			CardStrings localCardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
			DuelistMod.typeCardMap_NAME.put(t, localCardStrings.NAME);
			DuelistMod.typeCardMap_NameToString.put(localCardStrings.NAME, t);
			DuelistMod.typeCardMap_DESC.put(t, localCardStrings.DESCRIPTION);
		}
		
		ArrayList<CardTags> extraTags = new ArrayList<CardTags>();
		extraTags.add(Tags.ROSE);
		extraTags.add(Tags.ARCANE);
		extraTags.add(Tags.MEGATYPED);
		extraTags.add(Tags.OJAMA);
		extraTags.add(Tags.MONSTER);
		extraTags.add(Tags.SPELL);
		extraTags.add(Tags.TRAP);
		extraTags.add(Tags.GIANT);
		extraTags.add(Tags.MAGNET);
		
		for (CardTags t : extraTags)
		{
			String ID = DuelistMod.typeCardMap_ID.get(t);
			CardStrings localCardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
			DuelistMod.typeCardMap_NAME.put(t, localCardStrings.NAME);
			DuelistMod.typeCardMap_NameToString.put(localCardStrings.NAME, t);
			DuelistMod.typeCardMap_DESC.put(t, localCardStrings.DESCRIPTION);
		}

		DuelistMod.cardCount = 0;
		for (int i = 0; i < DuelistMod.myCards.size(); i++)
		{
			if (!DuelistMod.myCards.get(i).hasTag(Tags.METRONOME) || DuelistMod.myCards.get(i) instanceof Metronome)
			{
				if (!DuelistMod.myCards.get(i).color.equals(AbstractCardEnum.DUELIST_SPECIAL))
				{
					DuelistMod.cardCount++;
				}				
			}			
		}
		
		// Add tokens to 'The Duelist' section of compendium
		if (!DuelistMod.addTokens) { for (DuelistCard c : getAllDuelistTokens()) { if (c.rarity.equals(CardRarity.SPECIAL)) { DuelistMod.myCards.add(c); }}}
		
		
		// DEBUG CARD STUFF
		if (DuelistMod.debug)
		{
			Debug.printCardSetsForGithubReadme(DuelistMod.myCards);
			Debug.printTextForTranslation();
			for (DuelistCard c : DuelistMod.myCards)
			{
				if (c.tributes != c.baseTributes || c.summons != c.baseSummons)
				{
					if (c.hasTag(Tags.MONSTER))
					{
						DuelistMod.logger.info("something didn't match for " + c.originalName + " Base/Current Tributes: " + c.baseTributes + "/" + c.tributes + " :: Base/Current Summons: " + c.baseSummons + "/" + c.summons);
					}
					else
					{
						DuelistMod.logger.info("something didn't match for " + c.originalName + " but this card is a spell or trap");					
					}
				}
			}
		}
		
		if (DuelistMod.addTokens)
		{
			DuelistMod.myCards.addAll(getAllDuelistTokens());
			DuelistMod.myCards.add(new BadToken());			
			DuelistMod.myCards.add(new GreatMoth());
			DuelistMod.myCards.add(new HeartUnderspell());
			DuelistMod.myCards.add(new HeartUndertrap());
			DuelistMod.myCards.add(new HeartUndertribute());
			for (DuelistCard orbCard : DuelistMod.orbCards) { DuelistMod.myCards.add(orbCard); }
		}
		// END DEBUG CARD STUFF
		
		for (DuelistCard c : DuelistMod.myCards)
		{
			if (!c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL) && c.rarity.equals(CardRarity.RARE) && !c.hasTag(Tags.NEVER_GENERATE) && !c.hasTag(Tags.TOON) && !c.hasTag(Tags.EXODIA) && !c.hasTag(Tags.OJAMA))
			{
				DuelistMod.rareCards.add((DuelistCard) c.makeStatEquivalentCopy());
				if (!c.type.equals(CardType.POWER)) { DuelistMod.rareNonPowers.add((DuelistCard) c.makeStatEquivalentCopy()); }
			}
			
			else if (!c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL) && c.rarity.equals(CardRarity.UNCOMMON) && !c.hasTag(Tags.NEVER_GENERATE) && !c.hasTag(Tags.TOON) && !c.hasTag(Tags.EXODIA) && !c.hasTag(Tags.OJAMA))
			{
				DuelistMod.uncommonCards.add((DuelistCard) c.makeStatEquivalentCopy());
				DuelistMod.nonRareCards.add((DuelistCard) c.makeStatEquivalentCopy());
			}
			
			else if (!c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL) && c.rarity.equals(CardRarity.COMMON) && !c.hasTag(Tags.NEVER_GENERATE) && !c.hasTag(Tags.TOON) && !c.hasTag(Tags.EXODIA) && !c.hasTag(Tags.OJAMA))
			{
				DuelistMod.commonCards.add((DuelistCard) c.makeStatEquivalentCopy());
				DuelistMod.nonRareCards.add((DuelistCard) c.makeStatEquivalentCopy());
			}
			
			if (c.type.equals(CardType.POWER))
			{
				DuelistMod.allPowers.add((DuelistCard)c.makeStatEquivalentCopy());
				if (!c.rarity.equals(CardRarity.SPECIAL) && !c.hasTag(Tags.NO_MERCHANT_PENDANT) && !c.hasTag(Tags.NEVER_GENERATE))
				{
					DuelistMod.merchantPendantPowers.add((DuelistCard)c.makeStatEquivalentCopy());
				}
			}
			else if (!c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL) && !c.hasTag(Tags.NEVER_GENERATE) && !c.hasTag(Tags.TOON) && !c.hasTag(Tags.EXODIA) && !c.hasTag(Tags.OJAMA))
			{
				DuelistMod.nonPowers.add((DuelistCard)c.makeStatEquivalentCopy());
			}
			
			if (c.hasTag(Tags.METRONOME))
			{
				DuelistMod.metronomes.add(c.makeStatEquivalentCopy());
			}
		}
		
		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
			config.setInt(DuelistMod.PROP_CARDS, DuelistMod.cardCount);
			config.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<AbstractCard> orbCardsForGeneration()
	{
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		toReturn.add(new CrystalOrbCard());
		toReturn.add(new HellfireOrbCard());
		toReturn.add(new AirOrbCard());
		toReturn.add(new DarkOrbCard());
		toReturn.add(new DragonOrbCard());
		toReturn.add(new EarthOrbCard());
		toReturn.add(new FireOrbCard());
		toReturn.add(new FrostOrbCard());
		toReturn.add(new GlitchOrbCard());
		toReturn.add(new LightningOrbCard());
		toReturn.add(new MonsterOrbCard());
		toReturn.add(new PlasmaOrbCard());
		toReturn.add(new ReducerOrbCard());
		toReturn.add(new ShadowOrbCard());
		toReturn.add(new SplashOrbCard());
		toReturn.add(new SummonerOrbCard());
		toReturn.add(new BlackOrbCard());
		toReturn.add(new GadgetOrbCard());
		toReturn.add(new LavaOrbCard());
		toReturn.add(new MetalOrbCard());
		toReturn.add(new MistOrbCard());
		toReturn.add(new MudOrbCard());
		toReturn.add(new SandOrbCard());
		toReturn.add(new SmokeOrbCard());
		toReturn.add(new StormOrbCard());
		toReturn.add(new WaterOrbCard()); 
		toReturn.add(new TokenOrbCard()); 
		toReturn.add(new VoidOrbCard()); 
		toReturn.add(new WhiteOrbCard()); 
		toReturn.add(new SurgeOrbCard()); 
		toReturn.add(new AlienOrbCard()); 
		//toReturn.add(new BloodOrbCard()); 
		toReturn.add(new MoonOrbCard()); 
		toReturn.add(new SunOrbCard()); 
		return toReturn;
	}
	
	public static void setupOrbCards()
	{

		DuelistMod.orbCards.add(new CrystalOrbCard());
		DuelistMod.orbCards.add(new GlassOrbCard());
		DuelistMod.orbCards.add(new HellfireOrbCard());
		DuelistMod.orbCards.add(new LightOrbCard());	
		DuelistMod.orbCards.add(new AirOrbCard());
		DuelistMod.orbCards.add(new BufferOrbCard());
		DuelistMod.orbCards.add(new DarkOrbCard());
		DuelistMod.orbCards.add(new DragonOrbCard());
		DuelistMod.orbCards.add(new EarthOrbCard());
		DuelistMod.orbCards.add(new FireOrbCard());
		DuelistMod.orbCards.add(new FrostOrbCard());
		DuelistMod.orbCards.add(new GateOrbCard());
		DuelistMod.orbCards.add(new GlitchOrbCard());
		DuelistMod.orbCards.add(new LightningOrbCard());
		DuelistMod.orbCards.add(new MonsterOrbCard());
		DuelistMod.orbCards.add(new PlasmaOrbCard());
		DuelistMod.orbCards.add(new ReducerOrbCard());
		DuelistMod.orbCards.add(new ShadowOrbCard());
		DuelistMod.orbCards.add(new SplashOrbCard());
		DuelistMod.orbCards.add(new SummonerOrbCard());
		DuelistMod.orbCards.add(new BlackOrbCard());
		DuelistMod.orbCards.add(new BlazeOrbCard());
		DuelistMod.orbCards.add(new ConsumerOrbCard());
		DuelistMod.orbCards.add(new GadgetOrbCard());
		DuelistMod.orbCards.add(new LavaOrbCard());
		DuelistMod.orbCards.add(new MetalOrbCard());
		DuelistMod.orbCards.add(new MillenniumOrbCard());
		DuelistMod.orbCards.add(new MistOrbCard());
		DuelistMod.orbCards.add(new MudOrbCard());
		DuelistMod.orbCards.add(new SandOrbCard());
		DuelistMod.orbCards.add(new SmokeOrbCard());
		DuelistMod.orbCards.add(new StormOrbCard());
		DuelistMod.orbCards.add(new WaterOrbCard()); 
		DuelistMod.orbCards.add(new TokenOrbCard()); 
		DuelistMod.orbCards.add(new VoidOrbCard()); 
		DuelistMod.orbCards.add(new WhiteOrbCard()); 
		DuelistMod.orbCards.add(new SurgeOrbCard()); 
		DuelistMod.orbCards.add(new AlienOrbCard()); 
		//DuelistMod.orbCards.add(new BloodOrbCard()); 
		DuelistMod.orbCards.add(new MoonOrbCard()); 
		DuelistMod.orbCards.add(new SunOrbCard()); 
		DuelistMod.orbCards.add(new LightMillenniumOrbCard()); 
		DuelistMod.orbCards.add(new DarkMillenniumOrbCard()); 
		DuelistMod.orbCards.add(new AnticrystalOrbCard()); 
		
		for (DuelistCard o : DuelistMod.orbCards) 
		{ 
			DuelistMod.orbCardMap.put(o.name, o); 
			DuelistMod.invertableOrbNames.add(o.name); 
			Util.log("Added " + o.name + " to invertableOrbNames and the OrbCardMap");
		}
		//DuelistCard.resetInvertMap();
	}

	public static void fillSummonMap(ArrayList<DuelistCard> cards)
	{
		for (DuelistCard c : cards) 
		{ 
			DuelistMod.summonMap.put(c.originalName, c);	
		}
		
		DuelistMod.summonMap.put("Great Moth", new GreatMoth());
			
		DuelistMod.summonMap.put("Summoner Token", new Token());		
		DuelistMod.summonMap.put("Buffer Token", new Token());		
		DuelistMod.summonMap.put("Tribute Token", new Token());
		DuelistMod.summonMap.put("Summon Token", new Token());		
		DuelistMod.summonMap.put("Warrior Token", new WarriorToken());		
		DuelistMod.summonMap.put("Stance Token", new StanceToken());		
		DuelistMod.summonMap.put("Forsaken Token", new ForsakenToken());
		DuelistMod.summonMap.put("Token", new Token());
		DuelistMod.summonMap.put("Jam Token", new JamToken());
		DuelistMod.summonMap.put("Castle Token", new CastleToken());
		DuelistMod.summonMap.put("Storm Token", new StormToken());
		DuelistMod.summonMap.put("Puzzle Token", new PuzzleToken());
		DuelistMod.summonMap.put("Relic Token", new RelicToken());
		DuelistMod.summonMap.put("Bonanza Token", new BonanzaToken());
		DuelistMod.summonMap.put("Spellcaster Token", new SpellcasterToken());
		DuelistMod.summonMap.put("Predaplant Token", new PredaplantToken());
		DuelistMod.summonMap.put("Kuriboh Token", new KuribohToken());
		DuelistMod.summonMap.put("Exploding Token", new ExplosiveToken());
		DuelistMod.summonMap.put("Explosive Token", new ExplosiveToken());
		DuelistMod.summonMap.put("Shadow Token", new ShadowToken());
		DuelistMod.summonMap.put("Insect Token", new InsectToken());
		DuelistMod.summonMap.put("Plant Token", new PlantToken());
		DuelistMod.summonMap.put("Dragon Token", new DragonToken());
		DuelistMod.summonMap.put("Fiend Token", new FiendToken());
		DuelistMod.summonMap.put("Machine Token", new MachineToken());
		DuelistMod.summonMap.put("Superheavy Token", new SuperheavyToken());
		DuelistMod.summonMap.put("Toon Token", new ToonToken());
		DuelistMod.summonMap.put("Zombie Token", new ZombieToken());
		DuelistMod.summonMap.put("Aqua Token", new AquaToken());
		DuelistMod.summonMap.put("Exodia Token", new ExodiaToken());
		DuelistMod.summonMap.put("Damage Token", new DamageToken());
		DuelistMod.summonMap.put("Gold Token", new GoldToken());
		DuelistMod.summonMap.put("Orb Token", new OrbToken());
		DuelistMod.summonMap.put("Underdog Token", new UnderdogToken());
		DuelistMod.summonMap.put("Magnet Token", new MagnetToken());
		DuelistMod.summonMap.put("Cocoon Token", new CocoonToken());
		DuelistMod.summonMap.put("Potion Token", new PotionToken());
		DuelistMod.summonMap.put("Pot Token", new PotionToken());
		DuelistMod.summonMap.put("God Token", new GodToken());
		DuelistMod.summonMap.put("Glitch Token", new GlitchToken());
		DuelistMod.summonMap.put("Anubis Token", new AnubisToken());
		DuelistMod.summonMap.put("Blood Token", new BloodToken());
		DuelistMod.summonMap.put("Hane Token", new HaneToken());
		DuelistMod.summonMap.put("Stim Token", new StimToken());
		DuelistMod.summonMap.put("Megatype Token", new MegatypeToken());
		DuelistMod.summonMap.put("S. Exploding Token", new SuperExplodingToken());
	}
	
	public static ArrayList<DuelistCard> getAllDuelistTokens()
	{
		ArrayList<DuelistCard> tokens = new ArrayList<DuelistCard>();
		tokens.add(new AquaToken());
		tokens.add(new DragonToken());
		tokens.add(new ExodiaToken());
		tokens.add(new SpellcasterToken());
		tokens.add(new PredaplantToken());
		tokens.add(new KuribohToken());
		tokens.add(new ExplosiveToken());
		tokens.add(new ShadowToken());
		tokens.add(new InsectToken());
		tokens.add(new PlantToken());
		tokens.add(new FiendToken());
		tokens.add(new MachineToken());
		tokens.add(new SuperheavyToken());
		tokens.add(new ToonToken());
		tokens.add(new ZombieToken());
		tokens.add(new JamToken());
		tokens.add(new Token());
		tokens.add(new DamageToken());
		tokens.add(new CastleToken());
		tokens.add(new StormToken());
		tokens.add(new RelicToken());
		tokens.add(new PuzzleToken());
		tokens.add(new BonanzaToken());		
		tokens.add(new OrbToken());
		tokens.add(new UnderdogToken());
		tokens.add(new GoldToken());
		tokens.add(new MagnetToken());
		tokens.add(new CocoonToken());
		tokens.add(new GodToken());
		tokens.add(new PotionToken());
		tokens.add(new GlitchToken());
		tokens.add(new AnubisToken());
		tokens.add(new BloodToken());
		tokens.add(new HaneToken());
		tokens.add(new StimToken());
		tokens.add(new PlagueToken());
		tokens.add(new SummonToken());
		tokens.add(new TributeToken());
		tokens.add(new SuperExplodingToken());
		tokens.add(new MegatypeToken());
		tokens.add(new ForsakenToken());
		tokens.add(new WarriorToken());
		tokens.add(new StanceToken());
		return tokens;
	}
	
	public static ArrayList<DuelistCard> getTokensForCombat()
	{
		ArrayList<DuelistCard> tokens = new ArrayList<DuelistCard>();
		tokens.add(new AquaToken());
		tokens.add(new DragonToken());
		if (!DuelistMod.exodiaBtnBool) { tokens.add(new ExodiaToken()); }
		tokens.add(new SpellcasterToken());
		tokens.add(new PredaplantToken());
		tokens.add(new KuribohToken());
		tokens.add(new ExplosiveToken());
		tokens.add(new ShadowToken());
		tokens.add(new InsectToken());
		tokens.add(new PlantToken());
		tokens.add(new FiendToken());
		tokens.add(new MachineToken());
		tokens.add(new SuperheavyToken());
		if (!DuelistMod.toonBtnBool) { tokens.add(new ToonToken()); }
		tokens.add(new ZombieToken());
		tokens.add(new JamToken());
		tokens.add(new Token());
		tokens.add(new DamageToken());
		tokens.add(new CastleToken());
		tokens.add(new StormToken());
		//tokens.add(new RelicToken());
		tokens.add(new PuzzleToken());
		tokens.add(new BonanzaToken());		
		tokens.add(new OrbToken());
		tokens.add(new UnderdogToken());
		tokens.add(new MagnetToken());
		tokens.add(new CocoonToken());
		tokens.add(new GodToken());
		tokens.add(new PotionToken());
		tokens.add(new GlitchToken());
		tokens.add(new AnubisToken());
		tokens.add(new BloodToken());
		tokens.add(new HaneToken());
		tokens.add(new StimToken());
		tokens.add(new PlagueToken());
		tokens.add(new SummonToken());
		tokens.add(new TributeToken());
		tokens.add(new SuperExplodingToken());
		tokens.add(new MegatypeToken());
		tokens.add(new ForsakenToken());
		tokens.add(new WarriorToken());
		tokens.add(new StanceToken());
		return tokens;
	}
	
	public static DuelistCard getRandomTokenForCombat()
	{
		ArrayList<DuelistCard> tokens = getTokensForCombat();
		return tokens.get(AbstractDungeon.cardRandomRng.random(tokens.size() - 1));
	}
	
	public static AbstractCard getRandomDuelistCurseUnseeded()
	{
		if (DuelistMod.curses.size() > 0)
		{
			return DuelistMod.curses.get(ThreadLocalRandom.current().nextInt(0, DuelistMod.curses.size())).makeCopy();
		}
		else
		{
			return CardLibrary.getCurse();
		}
	}
	
	public static AbstractCard getRandomDuelistCurse()
	{
		if (DuelistMod.curses.size() > 0)
		{
			return DuelistMod.curses.get(AbstractDungeon.cardRandomRng.random(DuelistMod.curses.size() - 1)).makeCopy();
		}
		else
		{
			return CardLibrary.getCurse();
		}
	}
	
	public static AbstractCard getRandomMetronome()
	{
		Util.log("Calling getRandomMetronome()");
		ArrayList<AbstractCard> mets = new ArrayList<AbstractCard>();
		mets.add(new Metronome());
		mets.add(new AttackMetronome());
		mets.add(new RareAttackMetronome());
		mets.add(new SkillMetronome());
		mets.add(new RareSkillMetronome());
		mets.add(new PowerMetronome());
		mets.add(new RarePowerMetronome());
		mets.add(new UncommonMetronome());
		return mets.get(AbstractDungeon.cardRandomRng.random(mets.size() - 1));
	}
	
	public static void setupMyCardsDebug(String poolName)
	{
		switch (poolName)
		{
			case "Standard":
				for (AbstractCard c : StandardPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
				break;
			case "Dragon":
				for (AbstractCard c : DragonPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
				break;
			case "Spellcaster":
				for (AbstractCard c : SpellcasterPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
				break;
			case "Nature":
				for (AbstractCard c : NaturePool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
				break;
			case "Aqua":
				for (AbstractCard c : AquaPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
				break;
			case "Ascended One":
				for (AbstractCard c : AscendedOnePool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
				break;
			case "Ascended Two":
				for (AbstractCard c : AscendedTwoPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
				break;
			case "Ascended Three":
				for (AbstractCard c : AscendedThreePool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
				break;
			case "Pharaoh":
				for (AbstractCard c : PharaohPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
				break;
			case "Basic":
				for (AbstractCard c : BasicPool.fullBasic()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
				break;
			case "Creator":
				for (AbstractCard c : CreatorPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
				break;
			case "Exodia":
				for (AbstractCard c : ExodiaPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
				break;
			case "Fiend":
				for (AbstractCard c : FiendPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
				break;
			case "Giant":
				for (AbstractCard c : GiantPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
				break;
			case "Increment":
				for (AbstractCard c : IncrementPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
				break;
			case "Insect":
				for (AbstractCard c : InsectPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
				break;
			case "Machine":
				for (AbstractCard c : MachinePool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
				break;
			case "Megatype":
				for (AbstractCard c : MegatypePool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
				break;
			case "Ojama":
				for (AbstractCard c : OjamaPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
				break;
			case "Plant":
				for (AbstractCard c : PlantPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
				break;
			case "Predaplant":
				for (AbstractCard c : PredaplantPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
				break;
			case "Toon":
				for (AbstractCard c : ToonPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
				break;
			case "Warrior":
				for (AbstractCard c : WarriorPool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
				break;
			case "Zombie":
				for (AbstractCard c : ZombiePool.deck()) { if (c instanceof DuelistCard) { DuelistMod.myCards.add((DuelistCard) c); }}
				break;
		}
		DuelistMod.curses.add(new GravekeeperCurse());
		DuelistMod.curses.add(new CurseAnubis());
		DuelistMod.curses.add(new CurseArmaments());
		DuelistMod.curses.add(new CursedBill());
		DuelistMod.curses.add(new CurseAging());
		DuelistMod.curses.add(new SummoningCurse());
		DuelistMod.curses.add(new VampireCurse());
		DuelistMod.curses.add(new PsiCurse());
		for (CardTags t : DuelistMod.monsterTypes)
		{
			String ID = DuelistMod.typeCardMap_ID.get(t);
			CardStrings localCardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
			DuelistMod.typeCardMap_NAME.put(t, localCardStrings.NAME);
			DuelistMod.typeCardMap_NameToString.put(localCardStrings.NAME, t);
			DuelistMod.typeCardMap_DESC.put(t, localCardStrings.DESCRIPTION);
		}
		
		DuelistMod.typeCardMap_NAME.put(Tags.MEGATYPED, "Megatyped");
		DuelistMod.typeCardMap_NameToString.put("Megatyped", Tags.MEGATYPED);

		DuelistMod.cardCount = 0;
		for (int i = 0; i < DuelistMod.myCards.size(); i++)
		{
			DuelistMod.cardCount++;
		}
		
		// Add tokens to 'The Duelist' section of compendium
		if (!DuelistMod.addTokens) { for (DuelistCard c : getAllDuelistTokens()) { if (c.rarity.equals(CardRarity.SPECIAL)) { DuelistMod.myCards.add(c); }}}
		
		
		// DEBUG CARD STUFF
		if (DuelistMod.debug)
		{
			Debug.printCardSetsForGithubReadme(DuelistMod.myCards);
			Debug.printTextForTranslation();
			for (DuelistCard c : DuelistMod.myCards)
			{
				if (c.tributes != c.baseTributes || c.summons != c.baseSummons)
				{
					if (c.hasTag(Tags.MONSTER))
					{
						DuelistMod.logger.info("something didn't match for " + c.originalName + " Base/Current Tributes: " + c.baseTributes + "/" + c.tributes + " :: Base/Current Summons: " + c.baseSummons + "/" + c.summons);
					}
					else
					{
						DuelistMod.logger.info("something didn't match for " + c.originalName + " but this card is a spell or trap");					
					}
				}
			}
		}
		
		if (DuelistMod.addTokens)
		{
			DuelistMod.myCards.addAll(getAllDuelistTokens());
			DuelistMod.myCards.add(new BadToken());			
			DuelistMod.myCards.add(new GreatMoth());
			DuelistMod.myCards.add(new HeartUnderspell());
			DuelistMod.myCards.add(new HeartUndertrap());
			DuelistMod.myCards.add(new HeartUndertribute());
			for (DuelistCard orbCard : DuelistMod.orbCards) { DuelistMod.myCards.add(orbCard); }
		}
		// END DEBUG CARD STUFF
		
		for (DuelistCard c : DuelistMod.myCards)
		{
			if (!c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL) && c.rarity.equals(CardRarity.RARE) && !c.hasTag(Tags.NEVER_GENERATE) && !c.hasTag(Tags.TOON) && !c.hasTag(Tags.EXODIA) && !c.hasTag(Tags.OJAMA))
			{
				DuelistMod.rareCards.add((DuelistCard) c.makeStatEquivalentCopy());
			}
			
			else if (!c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL) && c.rarity.equals(CardRarity.UNCOMMON) && !c.hasTag(Tags.NEVER_GENERATE) && !c.hasTag(Tags.TOON) && !c.hasTag(Tags.EXODIA) && !c.hasTag(Tags.OJAMA))
			{
				DuelistMod.uncommonCards.add((DuelistCard) c.makeStatEquivalentCopy());
				DuelistMod.nonRareCards.add((DuelistCard) c.makeStatEquivalentCopy());
			}
			
			else if (!c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.SPECIAL) && c.rarity.equals(CardRarity.COMMON) && !c.hasTag(Tags.NEVER_GENERATE) && !c.hasTag(Tags.TOON) && !c.hasTag(Tags.EXODIA) && !c.hasTag(Tags.OJAMA))
			{
				DuelistMod.commonCards.add((DuelistCard) c.makeStatEquivalentCopy());
				DuelistMod.nonRareCards.add((DuelistCard) c.makeStatEquivalentCopy());
			}
		}
		
		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
			config.setInt(DuelistMod.PROP_CARDS, DuelistMod.cardCount);
			config.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
