package duelistmod;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import duelistmod.cards.*;
import duelistmod.interfaces.StarterDeck;
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
		DuelistMod.basicCards.add(new BottomlessTrapHole());
		DuelistMod.basicCards.add(new CardDestruction());
		DuelistMod.basicCards.add(new CannonSoldier());
		DuelistMod.basicCards.add(new CastleWalls());
		DuelistMod.basicCards.add(new CatapultTurtle());
		DuelistMod.basicCards.add(new CelticGuardian());
		DuelistMod.basicCards.add(new CheerfulCoffin());
		DuelistMod.basicCards.add(new Cloning());
		DuelistMod.basicCards.add(new DarkCreator());
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
		DuelistMod.basicCards.add(new PotDuality());
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
		DuelistMod.basicCards.add(new TheCreator());
		DuelistMod.basicCards.add(new TokenVacuum());
		DuelistMod.basicCards.add(new BigFire());
		DuelistMod.basicCards.add(new UltimateOffering());
		DuelistMod.basicCards.add(new ValkMagnet());
		DuelistMod.basicCards.add(new Wiretap());
		DuelistMod.basicCards.add(new BeaverWarrior());
		DuelistMod.basicCards.add(new Zombyra());
		DuelistMod.basicCards.add(new Mathematician());
		DuelistMod.basicCards.add(new BattleOx());
		
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
		if (!DuelistMod.toonBtnBool)
		{
			dragonCards.add(new ToonWorld());
			dragonCards.add(new ToonKingdom());
			dragonCards.add(new ToonBarrelDragon());
			dragonCards.add(new ToonCyberDragon());
			dragonCards.add(new RedEyesToon());
			dragonCards.add(new BlueEyesToon());
			dragonCards.add(new MangaRyuRan());
		}
		
		dragonDeck.fillPoolCards(dragonCards); 
		dragonDeck.fillPoolCards(DuelistMod.basicCards);
		
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
		spellcasterCards.add(new SliferSky());
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
		}
		
		spellcasterDeck.fillPoolCards(spellcasterCards);
		spellcasterDeck.fillPoolCards(DuelistMod.basicCards);
		
		
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
		//natureCards.add(new WorldTree());
		//natureCards.add(new Predaplanet());
		
		natureDeck.fillPoolCards(natureCards);
		natureDeck.fillPoolCards(DuelistMod.basicCards);
	
		
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
		toonCards.add(new ToonCyberDragon());
		toonCards.add(new ToonAncientGear());
		toonCards.add(new RedEyesToon());
		toonCards.add(new MangaRyuRan());
		toonCards.add(new BlueEyesToon());
		toonCards.add(new ToonTable());
		toonCards.add(new ToonCannonSoldier());
		toonCards.add(new ToonDefense());
		
		toonDeck.fillPoolCards(toonCards);
		toonDeck.fillPoolCards(DuelistMod.basicCards);
		
		// Zombie Deck
		StarterDeck zombieDeck = DuelistMod.starterDeckNamesMap.get("Zombie Deck");
		ArrayList<DuelistCard> zombieCards = new ArrayList<DuelistCard>();
		zombieCards.add(new ArmoredZombie());
		zombieCards.add(new ClownZombie());
		zombieCards.add(new DoubleCoston());
		zombieCards.add(new MoltenZombie());
		zombieCards.add(new RegenMummy());
		//zombieCards.add(new PatricianDarkness());
		zombieCards.add(new RedEyesZombie());
		//zombieCards.add(new Relinkuriboh());
		zombieCards.add(new RyuKokki());
		zombieCards.add(new SoulAbsorbingBone());
		//zombieCards.add(new VampireGenesis());
		//zombieCards.add(new VampireLord());
		zombieCards.add(new GatesDarkWorld());
		
		zombieDeck.fillPoolCards(zombieCards);
		zombieDeck.fillPoolCards(DuelistMod.basicCards);
		
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
		//aquaCards.add(new HyperancientShark());
		//aquaCards.add(new KaiserSeaHorse());
		//aquaCards.add(new UnshavenAngler());
		aquaDeck.fillPoolCards(aquaCards);
		aquaDeck.fillPoolCards(DuelistMod.basicCards);
		
		// Fiend Deck
		StarterDeck fiendDeck = DuelistMod.starterDeckNamesMap.get("Fiend Deck");
		ArrayList<DuelistCard> fiendCards = new ArrayList<DuelistCard>();
		fiendCards.add(new SummonedSkull());
		fiendCards.add(new GatesDarkWorld());
		fiendCards.add(new FiendishChain());
		fiendCards.add(new DarkMimicLv1());
		fiendCards.add(new DarkMimicLv3());
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
		if (!DuelistMod.toonBtnBool)
		{
			fiendCards.add(new ToonWorld());
			fiendCards.add(new ToonKingdom());
			fiendCards.add(new ToonSummonedSkull());
			fiendCards.add(new ToonMermaid());
		}
		fiendDeck.fillPoolCards(fiendCards);
		fiendDeck.fillPoolCards(DuelistMod.basicCards);
		
		// Machine Deck
		StarterDeck machineDeck = DuelistMod.starterDeckNamesMap.get("Machine Deck");
		ArrayList<DuelistCard> machineCards = new ArrayList<DuelistCard>();
		machineCards.add(new AllyJustice());
		machineCards.add(new BarrelDragon());
		machineCards.add(new BlastJuggler());
		machineCards.add(new CannonSoldier());
		machineCards.add(new CyberDragon());
		machineCards.add(new Jinzo());
		machineCards.add(new MachineKing());
		machineCards.add(new MetalDragon());
		machineCards.add(new SteamTrainKing());
		if (!DuelistMod.toonBtnBool)
		{
			machineCards.add(new ToonWorld());
			machineCards.add(new ToonKingdom());
			machineCards.add(new ToonAncientGear());
			machineCards.add(new ToonCyberDragon());
			DuelistCard randomToon = (DuelistCard) DuelistCard.returnTrulyRandomFromSetUnseeded(Tags.TOON);
			while (machineCards.contains(randomToon)) { randomToon = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(Tags.TOON); }
			machineCards.add(randomToon);			
		}

		machineDeck.fillPoolCards(machineCards);
		machineDeck.fillPoolCards(DuelistMod.basicCards);
		
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
		for (int i = 0; i < DuelistMod.magnetSlider; i++)
		{
			magnetCards.add(new AlphaMagnet());
			magnetCards.add(new BetaMagnet());
			magnetCards.add(new GammaMagnet());
		}

		magnetDeck.fillPoolCards(magnetCards);
		magnetDeck.fillPoolCards(DuelistMod.basicCards);
	
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
