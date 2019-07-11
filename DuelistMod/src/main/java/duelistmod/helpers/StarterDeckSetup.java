package duelistmod.helpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.patches.TheDuelistEnum;
import duelistmod.variables.Tags;

public class StarterDeckSetup {

	// STARTER DECK METHODS /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static void refreshPoolOptions()
	{
		DuelistMod.archetypeCards.clear();
		for (StarterDeck s : DuelistMod.starterDeckList)
		{
			s.getArchetypeCards().clear();
			s.getPoolCards().clear();
		}
		initStarterDeckPool();
	}
	
	public static void initStarterDeckPool()
	{
		// Non-archetype Set
		DuelistMod.basicCards = new ArrayList<AbstractCard>();	
		DuelistMod.basicCards.add(new AlphaMagnet());
		DuelistMod.basicCards.add(new BetaMagnet());		
		DuelistMod.basicCards.add(new CastleWalls());
		DuelistMod.basicCards.add(new CelticGuardian());
		DuelistMod.basicCards.add(new Cloning());		
		DuelistMod.basicCards.add(new DarkFactory());
		DuelistMod.basicCards.add(new DarkHole());
		DuelistMod.basicCards.add(new DarkMirrorForce());
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
		DuelistMod.basicCards.add(new MirrorForce());
		DuelistMod.basicCards.add(new MillenniumShield());
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
		DuelistMod.basicCards.add(new StormingMirrorForce());
		DuelistMod.basicCards.add(new StrayLambs());
		DuelistMod.basicCards.add(new Terraforming());
		DuelistMod.basicCards.add(new TokenVacuum());
		DuelistMod.basicCards.add(new BigFire());
		DuelistMod.basicCards.add(new UltimateOffering());
		DuelistMod.basicCards.add(new ValkMagnet());
		DuelistMod.basicCards.add(new Wiretap());
		DuelistMod.basicCards.add(new BeaverWarrior());
		DuelistMod.basicCards.add(new Mathematician());
		DuelistMod.basicCards.add(new BattleOx());
		DuelistMod.basicCards.add(new FluteKuriboh());
		DuelistMod.basicCards.add(new WingedKuriboh());
		DuelistMod.basicCards.add(new CastleDarkIllusions());
		DuelistMod.basicCards.add(new ChangeHeart());
		DuelistMod.basicCards.add(new JamBreeding());
		DuelistMod.basicCards.add(new Polymerization());
		DuelistMod.basicCards.add(new BigKoala());
		DuelistMod.basicCards.add(new BattleguardKing());		
		DuelistMod.basicCards.add(new BattleFootballer());
		DuelistMod.basicCards.add(new EarthquakeGiant());
		DuelistMod.basicCards.add(new EvilswarmHeliotrope());
		DuelistMod.basicCards.add(new WormApocalypse());
		DuelistMod.basicCards.add(new WormBarses());
		DuelistMod.basicCards.add(new WormWarlord());
		DuelistMod.basicCards.add(new WormKing());
		DuelistMod.basicCards.add(new GauntletWarrior());		
		DuelistMod.basicCards.add(new GaiaMidnight());		
		DuelistMod.basicCards.add(new GilfordLegend());
		DuelistMod.basicCards.add(new ReinforcementsArmy());
		DuelistMod.basicCards.add(new BlockGolem());
		//DuelistMod.basicCards.add(new DokiDoki());
		DuelistMod.basicCards.add(new GiantSoldierSteel());
		DuelistMod.basicCards.add(new RainbowJar());
		DuelistMod.basicCards.add(new WingedKuriboh9());
		DuelistMod.basicCards.add(new WingedKuriboh10());
		DuelistMod.basicCards.add(new MagicalStone());
		DuelistMod.basicCards.add(new Kuribohrn());
		DuelistMod.basicCards.add(new DropOff());
		DuelistMod.basicCards.add(new GiantRex());
		DuelistMod.basicCards.add(new PowerGiant());
		DuelistMod.basicCards.add(new RainbowBridge());
		DuelistMod.basicCards.add(new EarthGiant());
		DuelistMod.basicCards.add(new RainbowKuriboh());
		DuelistMod.basicCards.add(new ClearKuriboh());
		DuelistMod.basicCards.add(new Linkuriboh());
		DuelistMod.basicCards.add(new GiantTrapHole());
		DuelistMod.basicCards.add(new BigEye());
		DuelistMod.basicCards.add(new YamiForm());
		DuelistMod.basicCards.add(new RainbowOverdragon());
		DuelistMod.basicCards.add(new RainbowGravity());
		DuelistMod.basicCards.add(new RainbowLife());
		DuelistMod.basicCards.add(new SilverApples());
		DuelistMod.basicCards.add(new DarklordSuperbia());	
		DuelistMod.basicCards.add(new LightningVortex());	
		
		// Creators
		if (!DuelistMod.creatorBtnBool) { DuelistMod.basicCards.add(new TheCreator()); DuelistMod.basicCards.add(new DarkCreator()); }
		
		// Ascension-locked cards		
		if (AbstractDungeon.ascensionLevel < 10)
		{
			DuelistMod.basicCards.add(new DestructPotion());
			DuelistMod.basicCards.add(new Wiseman());
		} 
		
		if (AbstractDungeon.ascensionLevel < 15)
		{
			DuelistMod.basicCards.add(new DianKeto());
			DuelistMod.basicCards.add(new RedMedicine());
		}
		// END Ascension-locked cards
		
		if (DuelistMod.debug)
		{
			DuelistMod.logger.info("Printing all basic cards!!");
			int counter = 1;
			for (AbstractCard c : DuelistMod.basicCards)
			{
				DuelistMod.logger.info("[" + counter + "]: " + c.name);
			}
		}
		
		DuelistMod.archetypeCards.addAll(DuelistMod.basicCards);
		
		// Dragon Deck && Old Dragon
		StarterDeck dragonDeck = DuelistMod.starterDeckNamesMap.get("Dragon Deck");
		ArrayList<AbstractCard> dragonCards = new ArrayList<AbstractCard>();
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
		//dragonCards.add(new DragonCaptureJar());
		dragonCards.add(new DragonMaster());
		dragonCards.add(new DragonPiper());
		dragonCards.add(new ExploderDragon());
		dragonCards.add(new FiendSkull());
		dragonCards.add(new FiveHeaded());
		dragonCards.add(new FluteSummoning());
		dragonCards.add(new Gandora());
		dragonCards.add(new GaiaDragonChamp());
		dragonCards.add(new GravityAxe());
		dragonCards.add(new LesserDragon());
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
		dragonCards.add(new TyrantWing());
		dragonCards.add(new WhiteHornDragon());
		dragonCards.add(new WhiteNightDragon());
		dragonCards.add(new WingedDragonRa());
		dragonCards.add(new YamataDragon());
		dragonCards.add(new TwinHeadedFire());
		dragonCards.add(new DarkstormDragon());
		dragonCards.add(new AncientGearGadjiltron());
		dragonCards.add(new CheerfulCoffin());
		dragonCards.add(new SuperancientDinobeast());
		
		//dragonCards.add(new BeserkDragon());		
		//dragonCards.add(new DoomkaiserDragon());		
		dragonCards.add(new RedHeadedOni());	
		dragonCards.add(new BlueBloodedOni());	
		dragonCards.add(new DragonZombie());
		dragonCards.add(new YellowBelliedOni());

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
		
		if (DuelistMod.baseGameCards && DuelistMod.setIndex != 6)
		{
			dragonCards.add(new Reaper());
			dragonCards.add(new Havoc());
			dragonCards.add(new Shockwave());
			dragonCards.add(new Uppercut());
			dragonCards.add(new Bludgeon());
			dragonCards.add(new Pummel());
			dragonCards.add(new SpotWeakness());
			dragonCards.add(new InfernalBlade());
			dragonCards.add(new DemonForm());
			dragonCards.add(new Carnage());
			dragonCards.add(new Disarm());
			dragonCards.add(new Clothesline());
			dragonCards.add(new DualWield());
			dragonCards.add(new BodySlam());
			dragonCards.add(new Intimidate());
			dragonCards.add(new Rampage());
			dragonCards.add(new Whirlwind());
			dragonCards.add(new Dropkick());
			dragonCards.add(new Immolate());
			dragonCards.add(new PommelStrike());
			dragonCards.add(new SwordBoomerang());
			dragonCards.add(new RecklessCharge());
			dragonCards.add(new HeavyBlade());
			dragonCards.add(new Inflame());
			dragonCards.add(new DoubleTap());
			dragonCards.add(new LimitBreak());
			dragonCards.add(new SeverSoul());
			dragonCards.add(new TwinStrike());
			dragonCards.add(new FireBreathing());
			dragonCards.add(new SearingBlow());
			dragonCards.add(new ThunderClap());
			dragonCards.add(new Anger());
			dragonCards.add(new Cleave());
			dragonCards.add(new Hemokinesis());
			dragonCards.add(new WildStrike());
			dragonCards.add(new Rupture());
			dragonCards.add(new Clash());	
			dragonCards.add(new PiercingWail());	
			dragonCards.add(new DieDieDie());	
			dragonCards.add(new Malaise());	
			dragonCards.add(new Finisher());	
			dragonCards.add(new Burst());	
			dragonCards.add(new Predator());	
			dragonCards.add(new Terror());	
			dragonCards.add(new SuckerPunch());	
			dragonCards.add(new Backstab());	
			dragonCards.add(new DaggerThrow());	
			dragonCards.add(new DaggerSpray());	
			dragonCards.add(new MasterfulStab());	
			dragonCards.add(new QuickSlash());	
			dragonCards.add(new RiddleWithHoles());	
			dragonCards.add(new Choke());	
			dragonCards.add(new GlassKnife());	
			dragonCards.add(new FlyingKnee());	
			dragonCards.add(new HeelHook());	
			dragonCards.add(new AllOutAttack());	
			dragonCards.add(new PhantasmalKiller());	
			dragonCards.add(new Unload());	
			dragonCards.add(new Claw());	
			dragonCards.add(new Sunder());
			dragonCards.add(new Hyperbeam());
		}
		
		dragonDeck.fillPoolCards(DuelistMod.basicCards);
		dragonDeck.fillPoolCards(dragonCards); 
		
		dragonDeck.fillArchetypeCards(dragonCards);
		DuelistMod.archetypeCards.addAll(dragonCards);
		
		// Spellcaster Deck && Old Spellcaster
		StarterDeck spellcasterDeck = DuelistMod.starterDeckNamesMap.get("Spellcaster Deck");
		ArrayList<AbstractCard> spellcasterCards = new ArrayList<AbstractCard>();
		spellcasterCards.add(new AncientElf());
		spellcasterCards.add(new BadReactionRare());
		spellcasterCards.add(new BlizzardPrincess());
		spellcasterCards.add(new BookSecret());
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
		spellcasterCards.add(new LegendaryFlameLord());
		spellcasterCards.add(new BlizzardWarrior());
		//spellcasterCards.add(new DianKeto());
		spellcasterCards.add(new SwordsRevealing());
		spellcasterCards.add(new GrandSpellbookTower());
		if (!DuelistMod.exodiaBtnBool)
		{
			spellcasterCards.add(new ExodiaHead());
			spellcasterCards.add(new ExodiaLA());
			spellcasterCards.add(new ExodiaLL());
			spellcasterCards.add(new ExodiaRA());
			spellcasterCards.add(new ExodiaRL());
			//spellcasterCards.add(new ExxodMaster());
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
			
		}
		
		if (DuelistMod.baseGameCards && DuelistMod.setIndex != 6)
		{
			spellcasterCards.add(new Chill());
			spellcasterCards.add(new BiasedCognition());			
			spellcasterCards.add(new ColdSnap());
			spellcasterCards.add(new Loop());
			spellcasterCards.add(new Electrodynamics());
			spellcasterCards.add(new Coolheaded());
			spellcasterCards.add(new Capacitor());
			spellcasterCards.add(new Rainbow());
			spellcasterCards.add(new Barrage());
			spellcasterCards.add(new Storm());
			spellcasterCards.add(new CompileDriver());
			spellcasterCards.add(new Consume());
			spellcasterCards.add(new Blizzard());
			spellcasterCards.add(new Fission());
			spellcasterCards.add(new BallLightning());
			spellcasterCards.add(new MeteorStrike());
			spellcasterCards.add(new Darkness());
			spellcasterCards.add(new MultiCast());
			spellcasterCards.add(new DoomAndGloom());
			spellcasterCards.add(new Tempest());
			spellcasterCards.add(new Recursion());
			spellcasterCards.add(new Fusion());
			spellcasterCards.add(new StaticDischarge());
			spellcasterCards.add(new ThunderStrike());	
			spellcasterCards.add(new MachineLearning());
			spellcasterCards.add(new WhiteNoise());
			spellcasterCards.add(new SelfRepair());
			spellcasterCards.add(new Fission());
			spellcasterCards.add(new CreativeAI());
			spellcasterCards.add(new Leap());
			spellcasterCards.add(new Overclock());	
		}
		
		spellcasterDeck.fillPoolCards(DuelistMod.basicCards);
		spellcasterDeck.fillPoolCards(spellcasterCards);
		
		spellcasterDeck.fillArchetypeCards(spellcasterCards);
		DuelistMod.archetypeCards.addAll(spellcasterCards);
		
		
		// Nature Deck && Old Nature
		StarterDeck natureDeck = DuelistMod.starterDeckNamesMap.get("Nature Deck");
		
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
		//natureCards.add(new Predaplanet());
		
		if (DuelistMod.baseGameCards && DuelistMod.setIndex != 6)
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
		
		natureDeck.fillPoolCards(DuelistMod.basicCards);
		natureDeck.fillPoolCards(natureCards);
		
		natureDeck.fillArchetypeCards(natureCards);
		DuelistMod.archetypeCards.addAll(natureCards);
	
		
		// Toon Deck
		StarterDeck toonDeck = DuelistMod.starterDeckNamesMap.get("Toon Deck");
		ArrayList<AbstractCard> toonCards = new ArrayList<AbstractCard>();
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
		toonCards.add(new TributeDoomed());
		toonCards.add(new GiantTrunade());
		toonCards.add(new GracefulCharity());
		toonCards.add(new SwordsConcealing());
		toonCards.add(new TrapHole());
		toonCards.add(new CheerfulCoffin());
		toonCards.add(new ToonGoblinAttack());
		
		if (DuelistMod.baseGameCards && DuelistMod.setIndex != 6)
		{
			toonCards.add(new Entrench());
			toonCards.add(new Reaper());
			toonCards.add(new BurningPact());
			toonCards.add(new DemonForm());
			toonCards.add(new Uppercut());
			toonCards.add(new BattleTrance());
			toonCards.add(new Shockwave());
			toonCards.add(new SecondWind());
			toonCards.add(new Armaments());
			toonCards.add(new FlameBarrier());
			toonCards.add(new ShrugItOff());
			toonCards.add(new InfernalBlade());
			toonCards.add(new SpotWeakness());
			toonCards.add(new Disarm());
			toonCards.add(new Barricade());
			toonCards.add(new Juggernaut());
			toonCards.add(new Feed());
			toonCards.add(new Impervious());
			toonCards.add(new Metallicize());
			toonCards.add(new BodySlam());
			toonCards.add(new LimitBreak());
			toonCards.add(new DarkEmbrace());
			toonCards.add(new TrueGrit());
			toonCards.add(new Dropkick());
			toonCards.add(new DoubleTap());
			toonCards.add(new BloodForBlood());
			toonCards.add(new Anger());
			toonCards.add(new Rupture());
			toonCards.add(new ThunderClap());
			toonCards.add(new Backflip());
			toonCards.add(new Acrobatics());
			toonCards.add(new DodgeAndRoll());
			toonCards.add(new CalculatedGamble());
			toonCards.add(new WellLaidPlans());
			toonCards.add(new Blur());
			toonCards.add(new ToolsOfTheTrade());
			toonCards.add(new Adrenaline());
			toonCards.add(new Alchemize());
			toonCards.add(new BulletTime());
			toonCards.add(new Outmaneuver());
			toonCards.add(new AThousandCuts());
			toonCards.add(new Malaise());
			toonCards.add(new Burst());
			toonCards.add(new Predator());
			toonCards.add(new Terror());
			toonCards.add(new FlyingKnee());
			toonCards.add(new HeelHook());
			toonCards.add(new Distraction());
			toonCards.add(new Reboot());
			toonCards.add(new BeamCell());
			toonCards.add(new Amplify());
			toonCards.add(new Reprogram());
			toonCards.add(new Buffer());
			toonCards.add(new Recycle());
			toonCards.add(new HelloWorld());
			toonCards.add(new DoubleEnergy());
			toonCards.add(new MachineLearning());
			toonCards.add(new Storm());
			toonCards.add(new Equilibrium());
			toonCards.add(new ReinforcedBody());
			toonCards.add(new Heatsinks());			
		}

		toonDeck.fillPoolCards(DuelistMod.basicCards);
		toonDeck.fillPoolCards(toonCards);		
		toonDeck.fillArchetypeCards(toonCards);
		DuelistMod.archetypeCards.addAll(toonCards);
		
		// Zombie Deck
		StarterDeck zombieDeck = DuelistMod.starterDeckNamesMap.get("Zombie Deck");
		ArrayList<AbstractCard> zombieCards = new ArrayList<AbstractCard>();
		zombieCards.add(new ArmoredZombie());
		zombieCards.add(new ClownZombie());
		zombieCards.add(new DoubleCoston());
		zombieCards.add(new MoltenZombie());
		zombieCards.add(new RegenMummy());
		zombieCards.add(new PatricianDarkness());
		zombieCards.add(new RedEyesZombie());		
		zombieCards.add(new RyuKokki());
		zombieCards.add(new SoulAbsorbingBone());
		zombieCards.add(new VampireGenesis());
		zombieCards.add(new VampireLord());
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
		zombieCards.add(new SwordsBurning());
		zombieCards.add(new LegendaryFlameLord());
		zombieCards.add(new AcidTrapHole());
		zombieCards.add(new BottomlessTrapHole());
		zombieCards.add(new DrivenDaredevil());
		zombieCards.add(new MonsterEgg());
		zombieCards.add(new Zombyra());
		zombieCards.add(new ImperialTomb());
		zombieCards.add(new ZombieMaster());
		zombieCards.add(new VampireGrace());
		zombieCards.add(new VampireFraulein());
		zombieCards.add(new ShadowVampire());
		zombieCards.add(new IlBlud());
		zombieCards.add(new CallMummy());		
		zombieCards.add(new ArchfiendZombieSkull());
		zombieCards.add(new CorrodingShark());
		zombieCards.add(new FlameGhost());
		zombieCards.add(new Gernia());
		zombieCards.add(new GoblinZombie());
		zombieCards.add(new Gozuki());
		zombieCards.add(new Kasha());
		zombieCards.add(new OniTankT34());
		zombieCards.add(new RedHeadedOni());		
		zombieCards.add(new ZombieWarrior());
		zombieCards.add(new BlueBloodedOni());
		zombieCards.add(new DesLacooda());
		zombieCards.add(new EndlessDecay());		
		zombieCards.add(new HauntedShrine());
		zombieCards.add(new OniGamiCombo());
		zombieCards.add(new PlaguespreaderZombie());
		zombieCards.add(new YellowBelliedOni());
		zombieCards.add(new ZombieWorld());
		zombieCards.add(new DespairFromDark());
		
		if (DuelistMod.baseGameCards && DuelistMod.setIndex != 6)
		{
			zombieCards.add(new Corruption());
			zombieCards.add(new BurningPact());
			zombieCards.add(new Exhume());
			zombieCards.add(new Sentinel());
			zombieCards.add(new SeeingRed());
			zombieCards.add(new FeelNoPain());
			zombieCards.add(new GhostlyArmor());
			zombieCards.add(new SecondWind());
			zombieCards.add(new Offering());
			zombieCards.add(new InfernalBlade());
			zombieCards.add(new Bloodletting());
			zombieCards.add(new FiendFire());
			zombieCards.add(new DarkEmbrace());
			zombieCards.add(new BloodForBlood());
			zombieCards.add(new TrueGrit());
			zombieCards.add(new SeverSoul());
			zombieCards.add(new Brutality());
			zombieCards.add(new Hemokinesis());
			zombieCards.add(new Combust());
			zombieCards.add(new Rupture());
		}
		
		//zombieCards.add(new UnderworldCannon());
		//zombieCards.add(new WightLady());
		//zombieCards.add(new ZombieMammoth());
		//zombieCards.add(new PainPainter());
		//zombieCards.add(new FearFromDark());
		//zombieCards.add(new DragonZombie());
		//zombieCards.add(new DoomkaiserDragon());
		//zombieCards.add(new BeserkDragon());
		//zombieCards.add(new Relinkuriboh());
		
		zombieDeck.fillPoolCards(DuelistMod.basicCards);
		zombieDeck.fillPoolCards(zombieCards);		
		zombieDeck.fillArchetypeCards(zombieCards);
		DuelistMod.archetypeCards.addAll(zombieCards);
		
		// Aqua Deck
		StarterDeck aquaDeck = DuelistMod.starterDeckNamesMap.get("Aqua Deck");
		ArrayList<AbstractCard> aquaCards = new ArrayList<AbstractCard>();
		aquaCards.add(new AcidTrapHole());
		aquaCards.add(new BottomlessTrapHole());
		aquaCards.add(new CommandKnight());
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
		aquaCards.add(new SwordsConcealing());
		aquaCards.add(new PotDuality());
		//aquaCards.add(new HyperancientShark());
		//aquaCards.add(new KaiserSeaHorse());
		//aquaCards.add(new UnshavenAngler());
		aquaCards.add(new LostBlueBreaker());
		aquaCards.add(new Wingedtortoise());
		aquaCards.add(new GemKnightAmethyst());
		aquaCards.add(new ToadallyAwesome());
		aquaCards.add(new SpikedGillman());
		//aquaCards.add(new TripodFish());
		aquaCards.add(new BigWaveSmallWave());
		aquaCards.add(new GraydleSlimeJr());
		aquaCards.add(new FrillerRabca());	
		aquaCards.add(new WhitefishSalvage());	
		aquaCards.add(new SwampFrog());	
		aquaCards.add(new SharkStickers());	
		aquaCards.add(new RageDeepSea());
		aquaCards.add(new SpearfishSoldier());	
		aquaCards.add(new HydraViper());
		aquaCards.add(new AbyssWarrior());
		aquaCards.add(new AmphibiousBugroth());
		aquaCards.add(new BlizzardDefender());
		aquaCards.add(new Boneheimer());
		aquaCards.add(new CannonballSpearShellfish());
		aquaCards.add(new CrystalEmeraldTortoise());
		aquaCards.add(new DeepDiver());
		aquaCards.add(new CatShark());
		aquaCards.add(new BigWhale());
		aquaCards.add(new BlizzardThunderbird());
		aquaCards.add(new DiamondDust());
		aquaCards.add(new GoldenFlyingFish());
		aquaCards.add(new Monokeros());
		aquaCards.add(new FishSwaps());
		aquaCards.add(new FishKicks());
		aquaCards.add(new FishRain());
		aquaCards.add(new PoseidonWave());
		aquaCards.add(new DrillBarnacle());
		aquaCards.add(new CorrodingShark());
		aquaCards.add(new Uminotaurus());
		aquaCards.add(new BigDesFrog());
		aquaCards.add(new AtlanteanAttackSquad());
		aquaCards.add(new CatapultTurtle());
		aquaDeck.fillPoolCards(DuelistMod.basicCards);
		aquaDeck.fillPoolCards(aquaCards);		
		aquaDeck.fillArchetypeCards(aquaCards);
		DuelistMod.archetypeCards.addAll(aquaCards);
		
		// Fiend Deck
		StarterDeck fiendDeck = DuelistMod.starterDeckNamesMap.get("Fiend Deck");
		ArrayList<AbstractCard> fiendCards = new ArrayList<AbstractCard>();
		fiendCards.add(new SummonedSkull());
		fiendCards.add(new GatesDarkWorld());
		fiendCards.add(new FiendishChain());
		//fiendCards.add(new DarkMimicLv1());
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
		fiendCards.add(new CallGrave());
		fiendCards.add(new MiniPolymerization());
		fiendCards.add(new TributeDoomed());
		fiendCards.add(new Pumpking());
		fiendCards.add(new Pumprincess());
		fiendCards.add(new PotDuality());
		fiendCards.add(new Doomdog());
		fiendCards.add(new RedMirror());
		fiendCards.add(new DarknessNeosphere());
		fiendCards.add(new AcidTrapHole());
		fiendCards.add(new BottomlessTrapHole());
		fiendCards.add(new CheerfulCoffin());
		fiendCards.add(new DrivenDaredevil());
		fiendCards.add(new Zombyra());
		fiendCards.add(new GiantOrc());
		fiendCards.add(new DarkFusion());
		fiendCards.add(new DarklordMarie());
		fiendDeck.fillPoolCards(DuelistMod.basicCards);
		fiendDeck.fillPoolCards(fiendCards);		
		fiendDeck.fillArchetypeCards(fiendCards);
		DuelistMod.archetypeCards.addAll(fiendCards);

		
		// Machine Deck
		StarterDeck machineDeck = DuelistMod.starterDeckNamesMap.get("Machine Deck");
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
		if (!DuelistMod.toonBtnBool)
		{
			machineCards.add(new ToonWorld());
			machineCards.add(new ToonKingdom());
			machineCards.add(new ToonAncientGear());
			//machineCards.add(new ToonCyberDragon());		
		}
		
		machineDeck.fillPoolCards(DuelistMod.basicCards);
		machineDeck.fillPoolCards(machineCards);		
		machineDeck.fillArchetypeCards(machineCards);
		DuelistMod.archetypeCards.addAll(machineCards);
		
		// Magnet Deck
		StarterDeck magnetDeck = DuelistMod.starterDeckNamesMap.get("Superheavy Deck");
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
		magnetCards.add(new SuperheavySoulclaw());
		magnetCards.add(new SuperheavySoulhorns());
		magnetCards.add(new SuperheavySoulpiercer());
		magnetCards.add(new SuperheavySoulshield());
		magnetCards.add(new SwordsConcealing());
		magnetCards.add(new HeartUnderdog());
		magnetCards.add(new ReadyForIntercepting());
		magnetCards.add(new ArmorBreaker());
		magnetCards.add(new Hayate());
		for (int i = 0; i < DuelistMod.magnetSlider; i++)
		{
			magnetCards.add(new AlphaMagnet());
			magnetCards.add(new BetaMagnet());
			magnetCards.add(new GammaMagnet());
		}

		magnetDeck.fillPoolCards(DuelistMod.basicCards);
		magnetDeck.fillPoolCards(magnetCards);		
		magnetDeck.fillArchetypeCards(magnetCards);
		DuelistMod.archetypeCards.addAll(magnetCards);
		
		StarterDeck ojamaDeck = DuelistMod.starterDeckNamesMap.get("Ojama Deck");
		ArrayList<AbstractCard> ojamaCards = new ArrayList<AbstractCard>();
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
						ref.getDeck().add((DuelistCard) c.makeCopy()); 
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
		for (DuelistCard c : DuelistMod.myCards) { if (c.hasTag(Tags.STANDARD_DECK)) { for (int i = 0; i < c.standardDeckCopies; i++) { DuelistMod.standardDeck.add((DuelistCard) c.makeCopy()); }}}
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
	
	/*
	public static void newInitStarterDeckPool()
	{
		
		StarterDeck standardDeck = DuelistMod.starterDeckNamesMap.get("Standard Deck");
		ArrayList<DuelistCard> standardCards = new ArrayList<DuelistCard>();
		
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
		dragonCards.add(new Gandora());
		dragonCards.add(new GaiaDragonChamp());
		dragonCards.add(new GravityAxe());
		dragonCards.add(new LesserDragon());
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
		dragonCards.add(new TyrantWing());
		dragonCards.add(new WhiteHornDragon());
		dragonCards.add(new WhiteNightDragon());
		dragonCards.add(new WingedDragonRa());
		dragonCards.add(new YamataDragon());
		dragonCards.add(new TwinHeadedFire());
		dragonCards.add(new DarkstormDragon());
		dragonCards.add(new AncientGearGadjiltron());
		dragonCards.add(new CheerfulCoffin());
		dragonCards.add(new SuperancientDinobeast());
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
		dragonDeck.fillPoolCards(dragonCards); 		
		dragonDeck.fillArchetypeCards(dragonCards);
		DuelistMod.archetypeCards.addAll(dragonCards);
		
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
		natureCards.add(new AcidTrapHole());
		natureCards.add(new BottomlessTrapHole());
		natureCards.add(new CheerfulCoffin());
		natureCards.add(new WorldTree());
		//natureCards.add(new Predaplanet());
		natureDeck.fillPoolCards(natureCards);		
		natureDeck.fillArchetypeCards(natureCards);
		DuelistMod.archetypeCards.addAll(natureCards);
		
		StarterDeck spellcasterDeck = DuelistMod.starterDeckNamesMap.get("Spellcaster Deck");
		ArrayList<DuelistCard> spellcasterCards = new ArrayList<DuelistCard>();
		spellcasterCards.add(new AncientElf());
		spellcasterCards.add(new BadReactionRare());
		spellcasterCards.add(new BlizzardPrincess());
		spellcasterCards.add(new BookSecret());
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
		spellcasterCards.add(new LegendaryFlameLord());
		spellcasterCards.add(new BlizzardWarrior());
		spellcasterCards.add(new DianKeto());
		spellcasterCards.add(new SwordsRevealing());
		spellcasterCards.add(new GrandSpellbookTower());
		if (!DuelistMod.exodiaBtnBool)
		{
			spellcasterCards.add(new ExodiaHead());
			spellcasterCards.add(new ExodiaLA());
			spellcasterCards.add(new ExodiaLL());
			spellcasterCards.add(new ExodiaRA());
			spellcasterCards.add(new ExodiaRL());
			//spellcasterCards.add(new ExxodMaster());
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
		spellcasterDeck.fillPoolCards(spellcasterCards);		
		spellcasterDeck.fillArchetypeCards(spellcasterCards);
		DuelistMod.archetypeCards.addAll(spellcasterCards);
		
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
		toonCards.add(new CheerfulCoffin());
		toonCards.add(new DrivenDaredevil());
		toonDeck.fillPoolCards(toonCards);		
		toonDeck.fillArchetypeCards(toonCards);
		DuelistMod.archetypeCards.addAll(toonCards);
		
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
		zombieCards.add(new SwordsBurning());
		zombieCards.add(new LegendaryFlameLord());
		zombieCards.add(new AcidTrapHole());
		zombieCards.add(new BottomlessTrapHole());
		zombieCards.add(new DrivenDaredevil());
		zombieCards.add(new MonsterEgg());
		zombieCards.add(new Zombyra());
		zombieDeck.fillPoolCards(zombieCards);		
		zombieDeck.fillArchetypeCards(zombieCards);
		DuelistMod.archetypeCards.addAll(zombieCards);
		
		StarterDeck aquaDeck = DuelistMod.starterDeckNamesMap.get("Aqua Deck");
		ArrayList<DuelistCard> aquaCards = new ArrayList<DuelistCard>();
		aquaCards.add(new AcidTrapHole());
		aquaCards.add(new BottomlessTrapHole());
		aquaCards.add(new CommandKnight());
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
		aquaCards.add(new RedMedicine());
		aquaCards.add(new SwordsConcealing());
		aquaCards.add(new PotDuality());
		//aquaCards.add(new HyperancientShark());
		//aquaCards.add(new KaiserSeaHorse());
		//aquaCards.add(new UnshavenAngler());
		aquaCards.add(new LostBlueBreaker());
		aquaCards.add(new Wingedtortoise());
		aquaCards.add(new GemKnightAmethyst());
		aquaCards.add(new ToadallyAwesome());
		aquaCards.add(new SpikedGillman());
		aquaCards.add(new TripodFish());
		aquaCards.add(new BigWaveSmallWave());
		aquaCards.add(new GraydleSlimeJr());
		aquaCards.add(new FrillerRabca());	
		aquaCards.add(new WhitefishSalvage());	
		aquaCards.add(new SwampFrog());	
		aquaCards.add(new SharkStickers());	
		aquaCards.add(new RageDeepSea());
		aquaCards.add(new SpearfishSoldier());	
		aquaCards.add(new HydraViper());
		aquaCards.add(new AbyssWarrior());
		aquaCards.add(new AmphibiousBugroth());
		aquaCards.add(new BlizzardDefender());
		aquaCards.add(new Boneheimer());
		aquaCards.add(new CannonballSpearShellfish());
		aquaCards.add(new CrystalEmeraldTortoise());
		aquaCards.add(new DeepDiver());
		aquaCards.add(new CatShark());
		aquaCards.add(new BigWhale());
		aquaCards.add(new BlizzardThunderbird());
		aquaCards.add(new DiamondDust());
		aquaCards.add(new GoldenFlyingFish());
		aquaCards.add(new Monokeros());
		aquaCards.add(new FishSwaps());
		aquaCards.add(new FishKicks());
		aquaCards.add(new FishRain());
		aquaCards.add(new PoseidonWave());
		aquaCards.add(new DrillBarnacle());
		aquaDeck.fillPoolCards(aquaCards);		
		aquaDeck.fillArchetypeCards(aquaCards);
		DuelistMod.archetypeCards.addAll(aquaCards);
		
		StarterDeck fiendDeck = DuelistMod.starterDeckNamesMap.get("Fiend Deck");
		ArrayList<DuelistCard> fiendCards = new ArrayList<DuelistCard>();
		fiendCards.add(new SummonedSkull());
		fiendCards.add(new GatesDarkWorld());
		fiendCards.add(new FiendishChain());
		//fiendCards.add(new DarkMimicLv1());
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
		fiendCards.add(new CallGrave());
		fiendCards.add(new MiniPolymerization());
		fiendCards.add(new TributeDoomed());
		fiendCards.add(new Pumpking());
		fiendCards.add(new Pumprincess());
		fiendCards.add(new PotDuality());
		fiendCards.add(new Doomdog());
		fiendCards.add(new RedMirror());
		fiendCards.add(new DarknessNeosphere());
		fiendCards.add(new AcidTrapHole());
		fiendCards.add(new BottomlessTrapHole());
		fiendCards.add(new CheerfulCoffin());
		fiendCards.add(new DrivenDaredevil());
		fiendCards.add(new Zombyra());
		fiendCards.add(new GiantOrc());
		fiendCards.add(new DarkFusion());
		fiendCards.add(new DarklordMarie());
		fiendDeck.fillPoolCards(fiendCards);		
		fiendDeck.fillArchetypeCards(fiendCards);
		DuelistMod.archetypeCards.addAll(fiendCards);
		
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
		machineCards.add(new ExploderDragon());
		machineCards.add(new DianKeto());
		machineCards.add(new BlastJuggler());
		machineCards.add(new StimPack());
		machineCards.add(new IronhammerGiant());
		machineCards.add(new ChaosAncientGearGiant());
		machineCards.add(new AncientGearBox());
		machineCards.add(new Deskbot001());
		machineCards.add(new Deskbot002());
		machineCards.add(new Deskbot009());
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
		
		StarterDeck magnetDeck = DuelistMod.starterDeckNamesMap.get("Magnet Deck");
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
		magnetCards.add(new HeartUnderdog());
		for (int i = 0; i < DuelistMod.magnetSlider; i++)
		{
			magnetCards.add(new AlphaMagnet());
			magnetCards.add(new BetaMagnet());
			magnetCards.add(new GammaMagnet());
		}
		magnetDeck.fillPoolCards(magnetCards);		
		magnetDeck.fillArchetypeCards(magnetCards);
		DuelistMod.archetypeCards.addAll(magnetCards);
		
		StarterDeck creaDeck = DuelistMod.starterDeckNamesMap.get("Creator Deck");
		ArrayList<DuelistCard> creaCards = new ArrayList<DuelistCard>();
		
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
		ojamaDeck.fillPoolCards(ojamaCards);		
		ojamaDeck.fillArchetypeCards(ojamaCards);
		DuelistMod.archetypeCards.addAll(ojamaCards);
		
		StarterDeck genDeck = DuelistMod.starterDeckNamesMap.get("Generation Deck");
		ArrayList<DuelistCard> genCards = new ArrayList<DuelistCard>();
		
		StarterDeck orbDeck = DuelistMod.starterDeckNamesMap.get("Orb Deck");
		ArrayList<DuelistCard> orbCards = new ArrayList<DuelistCard>();
		
		StarterDeck resummonDeck = DuelistMod.starterDeckNamesMap.get("Resummon Deck");
		ArrayList<DuelistCard> resummonCards = new ArrayList<DuelistCard>();
		
		StarterDeck incDeck = DuelistMod.starterDeckNamesMap.get("Increment Deck");
		ArrayList<DuelistCard> incCards = new ArrayList<DuelistCard>();
		
		StarterDeck exodiaDeck = DuelistMod.starterDeckNamesMap.get("Exodia Deck");
		ArrayList<DuelistCard> exodiaCards = new ArrayList<DuelistCard>();
		
		StarterDeck hpDeck = DuelistMod.starterDeckNamesMap.get("Heal Deck");
		ArrayList<DuelistCard> hpCards = new ArrayList<DuelistCard>();
	}
	*/

}
