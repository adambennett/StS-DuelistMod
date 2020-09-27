package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.pools.aqua.*;
import duelistmod.cards.pools.dragons.*;
import duelistmod.cards.pools.machine.*;
import duelistmod.cards.pools.warrior.*;
import duelistmod.cards.pools.zombies.*;

public class AscendedThreePool 
{
	private static final String deckName = "Ascended III";
	
	public static ArrayList<AbstractCard> oneRandom()
	{
		ArrayList<AbstractCard> pool = new ArrayList<>(GlobalPoolHelper.oneRandom());
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> twoRandom()
	{
		ArrayList<AbstractCard> pool = new ArrayList<>(GlobalPoolHelper.twoRandom());
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> deck()
	{
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> cards = new ArrayList<>();
		
		// Special megatype pool
		// Megatype cards
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
		//cards.add(new HourglassLife());
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
		cards.add(new SuperancientDinobeast());
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
		cards.add(new Illusionist());
		cards.add(new NeoMagic());
		cards.add(new DarkMagician());
		cards.add(new BlizzardWarrior());
		cards.add(new QueenDragunDjinn());
		cards.add(new IceQueen());
		cards.add(new SpiritHarp());
		cards.add(new MysticalElf());
		cards.add(new FogKing());
		cards.add(new CommandKnight());
		cards.add(new LegendaryFisherman());
		cards.add(new SevenColoredFish());
		cards.add(new IslandTurtle());
		cards.add(new RevivalJam());
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
		cards.add(new ArchfiendSoldier());
		cards.add(new FabledAshenveil());
		cards.add(new MonsterReborn());
		cards.add(new CallGrave());
		cards.add(new TributeDoomed());
		cards.add(new Pumpking());
		cards.add(new PotDuality());
		cards.add(new Zombyra());
		cards.add(new BeastTalwar());
		cards.add(new DoomcaliberKnight());
		cards.add(new WanderingKing());
		cards.add(new GoblinKing());
		cards.add(new Jinzo());
		cards.add(new RedGadget());
		cards.add(new GreenGadget());
		cards.add(new GadgetSoldier());
		//cards.add(new IronhammerGiant());
		cards.add(new Biofalcon());
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
		cards.add(new FearFromDark());
		cards.add(new ZombieMammoth());
		cards.add(new DragonZombie());
		cards.add(new GoldenBlastJuggler());
		cards.add(new BlastAsmodian());
		cards.add(new ExploderDragonwing());
		cards.add(new BlasterDragonInfernos());
		
		if (DuelistMod.baseGameCards && DuelistMod.setIndex != 9)
		{
			//cards.add(new Token());
		}
		
		deck.fillPoolCards(cards);
		return cards;
	}

	public static  ArrayList<AbstractCard> basic()
	{
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> pool = new ArrayList<>(BasicPool.ascendedBasics());
		deck.fillPoolCards(pool); 
		return pool;
	}
}
