package duelistmod;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import duelistmod.cards.*;
import duelistmod.cards.tokens.*;
import duelistmod.interfaces.DuelistCard;
import duelistmod.orbCards.*;

public class CardLibrary {

	// COMPENDIUM MANIPULATION FUNCTIONS /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static ArrayList<AbstractCard> getAllColoredCards()
	{
		return null;
	}

	public static void removeCardsFromSet()
	{
		int tempCardCount = 0;
		for (DuelistCard c : DuelistMod.myCards) 
		{ 
			if ((!c.hasTag(Tags.RANDOMONLY) && (!c.hasTag(Tags.RANDOMONLY_NOCREATOR))))
			{
				BaseMod.addCard(c); UnlockTracker.unlockCard(c.getID()); DuelistMod.summonMap.put(c.originalName, c); 
				if (!c.hasTag(Tags.TOKEN)) { tempCardCount++; }
			}
			else { if (!c.rarity.equals(CardRarity.SPECIAL)) { UnlockTracker.unlockCard(c.getID()); }}
		}
		
		if (!DuelistMod.fullDebug)
		{
			if (DuelistMod.exodiaBtnBool)
			{
				for (int i = 0; i < DuelistMod.myCards.size(); i++)
				{
					if (DuelistMod.myCards.get(i).hasTag(Tags.EXODIA))
					{
						DuelistMod.myCards.remove(i);
						i = 0;
					}
				}
			}
	
			if (DuelistMod.toonBtnBool)
			{
				for (int i = 0; i < DuelistMod.myCards.size(); i++)
				{
					if ((DuelistMod.myCards.get(i).hasTag(Tags.TOON)))
					{
						DuelistMod.myCards.remove(i);
						i = 0;
					}
				}
			}
			
			if (DuelistMod.ojamaBtnBool)
			{
				for (int i = 0; i < DuelistMod.myCards.size(); i++)
				{
					if ((DuelistMod.myCards.get(i).hasTag(Tags.OJAMA)))
					{
						DuelistMod.myCards.remove(i);
						i = 0;
					}
				}
			}
		}
		
		DuelistMod.cardCount = tempCardCount;
		
		DuelistMod.logger.info("theDuelist:DuelistMod:receiveEditCards() ---> done initializing cards");
		DuelistMod.logger.info("theDuelist:DuelistMod:receiveEditCards() ---> saving config options for card set");
		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
			config.setInt(DuelistMod.PROP_CARDS, DuelistMod.cardCount);
			config.setInt(DuelistMod.PROP_DECK, DuelistMod.deckIndex);
			//config.setInt(DuelistMod.PROP_MAX_SUMMONS, DuelistMod.lastMaxSummons);
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
		DuelistMod.myCards.add(new DragonCaptureJar());
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
		DuelistMod.myCards.add(new SmallLabyrinthWall());
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
		DuelistMod.myCards.add(new BadReactionRare());
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
		//DuelistMod.myCards.add(new MagicCylinder());
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
		DuelistMod.myCards.add(new DarkMimicLv1());
		DuelistMod.myCards.add(new DarkMimicLv3());
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
		DuelistMod.myCards.add(new SuperheavySoulclaw());
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
		
		
		// Add tokens to 'The Duelist' section of compendium
		if (!DuelistMod.addTokens) { for (DuelistCard c : getTokens()) { if (c.rarity.equals(CardRarity.SPECIAL)) { DuelistMod.myCards.add(c); }}}
		
		
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
			DuelistMod.myCards.addAll(getTokens());
			DuelistMod.myCards.add(new BadToken());			
			DuelistMod.myCards.add(new GreatMoth());
			DuelistMod.myCards.add(new HeartUnderspell());
			DuelistMod.myCards.add(new HeartUndertrap());
			DuelistMod.myCards.add(new HeartUndertribute());
			for (DuelistCard orbCard : DuelistMod.orbCards) { DuelistMod.myCards.add(orbCard); }
		}
		// END DEBUG CARD STUFF
	}

	public static void setupOrbCards()
	{
		if (DuelistMod.isReplay)
		{
			DuelistMod.orbCards.add(new CrystalOrbCard());
			DuelistMod.orbCards.add(new GlassOrbCard());
			DuelistMod.orbCards.add(new HellfireOrbCard());
			DuelistMod.orbCards.add(new LightOrbCard());
		}
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
		
		if (DuelistMod.isConspire) { DuelistMod.orbCards.add(new WaterOrbCard()); }
		for (DuelistCard o : DuelistMod.orbCards) { DuelistMod.orbCardMap.put(o.name, o); }
		//DuelistCard.resetInvertMap();
	}

	public static void fillSummonMap(ArrayList<DuelistCard> cards)
	{
		for (DuelistCard c : cards) 
		{ 
			DuelistMod.summonMap.put(c.originalName, c);	
		}
		
		//summonMap.put("R. Stone Soldier", new RandomSoldier());
		DuelistMod.summonMap.put("Token", new Token());
		DuelistMod.summonMap.put("Great Moth", new GreatMoth());
		DuelistMod.summonMap.put("Puzzle Token", new Token());
		DuelistMod.summonMap.put("Ancient Token", new Token());
		DuelistMod.summonMap.put("Anubis Token", new Token());
		DuelistMod.summonMap.put("Glitch Token", new Token());
		DuelistMod.summonMap.put("Summoner Token", new Token());
		DuelistMod.summonMap.put("Gate Token", new Token());
		DuelistMod.summonMap.put("Jam Token", new JamToken());
		DuelistMod.summonMap.put("Castle Token", new Token());
		DuelistMod.summonMap.put("Storm Token", new Token());
		DuelistMod.summonMap.put("Random Token", new Token());
		DuelistMod.summonMap.put("Pot Token", new Token());
		DuelistMod.summonMap.put("Buffer Token", new Token());
		DuelistMod.summonMap.put("Blood Token", new Token());
		DuelistMod.summonMap.put("Hane Token", new Token());
		DuelistMod.summonMap.put("Bonanza Token", new Token());
		DuelistMod.summonMap.put("Orb Token", new Token());
		DuelistMod.summonMap.put("Resummon Token", new Token());
		DuelistMod.summonMap.put("Resummoned Token", new Token());
		DuelistMod.summonMap.put("Stim Token", new Token());
		DuelistMod.summonMap.put("Underdog Token", new Token());
		DuelistMod.summonMap.put("Judge Token", new Token());
		DuelistMod.summonMap.put("Exxod Token", new Token());
		DuelistMod.summonMap.put("Relic Token", new Token());
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
	}
	
	public static ArrayList<DuelistCard> getTokens()
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
		return tokens;
	}

}
