package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.helpers.Util;

public class MegatypePool 
{
	private static String deckName = "Megatype Deck";
	
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
		pools.add(NaturePool.deck());
		pools.add(PlantPool.deck());
		//pools.add(PredaplantPool.deck());
		pools.add(SpellcasterPool.deck());
		pools.add(StandardPool.deck());
		pools.add(WarriorPool.deck());
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
		pools.add(NaturePool.deck());
		pools.add(PlantPool.deck());
		//pools.add(PredaplantPool.deck());
		pools.add(SpellcasterPool.deck());
		pools.add(StandardPool.deck());
		pools.add(WarriorPool.deck());
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
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();	
		
		// megatype specific cards
		cards.add(new RainbowOverdragon());
		cards.add(new RainbowKuriboh());
		cards.add(new RainbowLife());
		cards.add(new RainbowGravity());
		cards.add(new RainbowJar());
		cards.add(new WingedKuriboh9());
		cards.add(new WingedKuriboh10());
		cards.add(new ClearKuriboh());
		cards.add(new RainbowBridge());		
		cards.add(new KamionTimelord());
		cards.add(new IrisEarthMother());
		cards.add(new RainbowRefraction());
		cards.add(new CrystalRaigeki());
		cards.add(new RainbowRuins());
		cards.add(new RainbowMagician());
		cards.add(new RainbowDarkDragon());
		cards.add(new MaleficRainbowDragon());
		cards.add(new RainbowDragon());
		cards.add(new HourglassLife());
		cards.add(new Eva());
		cards.add(new HappyLover());
		cards.add(new DunamesDarkWitch());
		cards.add(new RainbowNeos());
		cards.add(new RainbowFlower());
		
		// other pool cards
		cards.add(new AncientRules());
		cards.add(new DragonPiper());
		cards.add(new Mountain());
		cards.add(new Yami());
		cards.add(new VioletCrystal());
		cards.add(new Umi());
		cards.add(new GatesDarkWorld());
		cards.add(new SuperancientDinobeast());
		cards.add(new Carboneddon());
		cards.add(new BlueBloodedOni());
		cards.add(new RedHeadedOni());
		cards.add(new StarBlast());
		cards.add(new StardustDragon());
		cards.add(new HorusServant());
		cards.add(new CaveDragon());
		cards.add(new CurseDragon());
		cards.add(new BabyDragon());
		cards.add(new BlacklandFireDragon());
		cards.add(new TyrantWing());
		cards.add(new WhiteHornDragon());
		cards.add(new YamataDragon());
		cards.add(new TwinHeadedFire());
		cards.add(new MaskedDragon());
		cards.add(new BookSecret());
		cards.add(new FluteSummoning());
		cards.add(new Illusionist());
		cards.add(new NeoMagic());
		cards.add(new DarkMagician());
		cards.add(new WhiteMagicalHat());
		cards.add(new BlizzardWarrior());
		cards.add(new SwordsRevealing());
		cards.add(new StatueAnguishPattern());
		cards.add(new QueenDragunDjinn());
		cards.add(new IceQueen());
		cards.add(new SpiritHarp());
		cards.add(new SangaEarth());
		cards.add(new SangaThunder());
		cards.add(new SangaWater());
		cards.add(new GateGuardian());
		cards.add(new MysticalElf());
		cards.add(new FogKing());
		cards.add(new CommandKnight());
		cards.add(new LegendaryFisherman());
		cards.add(new SevenColoredFish());
		cards.add(new IslandTurtle());
		cards.add(new RevivalJam());
		cards.add(new HeartUnderdog());
		cards.add(new Wingedtortoise());
		cards.add(new GemKnightAmethyst());
		cards.add(new BigWaveSmallWave());
		cards.add(new GraydleSlimeJr());
		cards.add(new FrillerRabca());	
		cards.add(new AmphibiousBugroth());
		cards.add(new BlizzardDefender());
		cards.add(new Boneheimer());
		cards.add(new CrystalEmeraldTortoise());
		cards.add(new DeepDiver());
		cards.add(new CatShark());
		cards.add(new BigWhale());
		cards.add(new BlizzardThunderbird());
		cards.add(new SummonedSkull());
		cards.add(new DarkMasterZorc());
		cards.add(new FiendMegacyber());
		cards.add(new KingYami());
		cards.add(new Lajinn());
		cards.add(new VanityFiend());
		cards.add(new DarkEnergy());
		cards.add(new OhFish());
		cards.add(new SuperSolarNutrient());
		cards.add(new ArchfiendSoldier());
		cards.add(new FabledAshenveil());
		cards.add(new MonsterReborn());
		cards.add(new CallGrave());
		cards.add(new MiniPolymerization());
		cards.add(new TributeDoomed());
		cards.add(new Pumpking());
		cards.add(new PotDuality());
		cards.add(new Zombyra());
		cards.add(new BeastTalwar());
		cards.add(new DoomcaliberKnight());
		cards.add(new GarbageLord());
		cards.add(new WanderingKing());
		cards.add(new GoblinKing());
		cards.add(new Jinzo());
		cards.add(new JunkKuriboh());
		cards.add(new RedGadget());
		cards.add(new GreenGadget());
		cards.add(new GadgetSoldier());
		cards.add(new BlastJuggler());
		cards.add(new StimPack());
		cards.add(new IronhammerGiant());
		cards.add(new GravityBlaster());
		cards.add(new IronCall());
		cards.add(new IronDraw());
		cards.add(new Biofalcon());
		cards.add(new OniTankT34());
		cards.add(new AlphaMagnet());
		cards.add(new BetaMagnet());
		cards.add(new GammaMagnet());
		cards.add(new ValkMagnet());
		cards.add(new SuperheavyBenkei());
		cards.add(new SuperheavyFlutist());
		cards.add(new SuperheavyGeneral());
		cards.add(new SuperheavyOgre());
		cards.add(new SuperheavyScales());
		cards.add(new SuperheavySwordsman());
		cards.add(new PatricianDarkness());
		cards.add(new VampireGenesis());
		cards.add(new VampireLord());
		cards.add(new CardSafeReturn());
		cards.add(new ShallowGrave());
		cards.add(new VampireGrace());
		cards.add(new VampireFraulein());
		cards.add(new ShadowVampire());
		cards.add(new CallMummy());	
		cards.add(new Gernia());
		cards.add(new GoblinZombie());
		cards.add(new Gozuki());
		cards.add(new Kasha());
		cards.add(new ZombieWarrior());
		cards.add(new DesLacooda());
		cards.add(new OniGamiCombo());
		cards.add(new SpiritPharaoh());
		cards.add(new FearFromDark());
		cards.add(new ZombieMammoth());
		cards.add(new DragonZombie());
		cards.add(new SpiralSpearStrike());
		cards.add(new MeteorDestruction());
		cards.add(new CharcoalInpachi());
		cards.add(new FlyingSaucer());
		cards.add(new CosmicHorrorGangiel());
		cards.add(new GoldenBlastJuggler());
		cards.add(new BlastAsmodian());
		cards.add(new BlastHeldTribute());
		cards.add(new BlastMagician());
		cards.add(new BlastWithChain());
		cards.add(new ExploderDragonwing());
		cards.add(new OrbitalBombardment());
		cards.add(new AncientGearExplosive());
		cards.add(new BlasterDragonInfernos());
		cards.add(new BlastingRuins());
		cards.add(new BlossomBombardment());
		cards.add(new BlastingFuse());
		cards.add(new CemetaryBomb());
		cards.add(new RockBombardment());
		cards.add(new Metronome());
		cards.add(new OrbMetronome());
		
		if (DuelistMod.baseGameCards && DuelistMod.setIndex != 9)
		{
			//cards.add(new Token());
		}

		deck.fillPoolCards(cards);		
		deck.fillArchetypeCards(cards);	
		return cards;
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
