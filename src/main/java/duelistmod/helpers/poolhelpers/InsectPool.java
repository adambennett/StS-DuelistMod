package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.incomplete.MillenniumScorpion;
import duelistmod.cards.pools.insects.*;
import duelistmod.cards.pools.naturia.*;
import duelistmod.cards.pools.zombies.PoisonousMayakashi;

public class InsectPool 
{
	private static String deckName = "Insect Deck";
	
	public static ArrayList<AbstractCard> oneRandom()
	{
		ArrayList<AbstractCard> pool = new ArrayList<>();		
		pool.addAll(GlobalPoolHelper.oneRandom(3));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		//deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> twoRandom()
	{
		ArrayList<AbstractCard> pool = new ArrayList<>();		
		pool.addAll(GlobalPoolHelper.twoRandom(3));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		//deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> deck()
	{
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
		cards.add(new Anteater());
		cards.add(new ArmoredBee());
		cards.add(new ArsenalBug());
		cards.add(new AtomicFirefly());
		cards.add(new Aztekipede());
		cards.add(new BasicInsect());
		cards.add(new BeakedSnake());
		cards.add(new BeeListSoldier());
		cards.add(new BigInsect());
		cards.add(new BirdParadise());
		cards.add(new BiteBug());
		cards.add(new BlazewingButterfly());
		cards.add(new BlockSpider());
		cards.add(new BrainCrusher());
		cards.add(new BugEmergency());
		cards.add(new BugMatrix());
		cards.add(new BugSignal());
		cards.add(new Chiwen());
		cards.add(new CobraJar());
		cards.add(new CocoonEvolution());
		cards.add(new CocoonUltraEvolution());
		cards.add(new CorrosiveScales());
		cards.add(new CrossSwordBeetle());
		cards.add(new DarkBug());
		cards.add(new DarkSpider());
		cards.add(new DarkSpider());
		cards.add(new Denglong());
		cards.add(new DestructionCyclone());
		cards.add(new DigitalBug());
		cards.add(new DragonDowser());
		cards.add(new DrillBug());
		cards.add(new EmpressMantis());
		//cards.add(new FirestormProminence());
		cards.add(new Forest());
		cards.add(new Gagagigo());
		cards.add(new GigaCricket());
		cards.add(new GigaMantis());
		cards.add(new Grasschopper());
		cards.add(new Greatfly());
		cards.add(new GroundSpider());
		cards.add(new HerculesBeetle());
		cards.add(new HowlingInsect());
		cards.add(new HundredFootedHorror());
		cards.add(new HunterSpider());
		cards.add(new IgnisHeat());
		cards.add(new InsectKing());
		cards.add(new InsectKnight());
		cards.add(new InsectPrincess());
		cards.add(new InsectQueen());
		cards.add(new Invigoration());
		cards.add(new Inzektron());
		cards.add(new JiraiGumo());
		cards.add(new KarakuriSpider());
		cards.add(new Lightserpent());
		cards.add(new LinkSpider());
		cards.add(new ManEaterBug());
		cards.add(new MareMare());
		cards.add(new MetalArmoredBug());
		cards.add(new MetamorphInsectQueen());
		cards.add(new MillenniumScorpion());
		cards.add(new MirrorLadybug());
		cards.add(new MotherSpider());
		//cards.add(new MultiplicationOfAnts());
		cards.add(new NeoBug());
		cards.add(new Parasite());
		cards.add(new PetitMoth());	
		cards.add(new PinchHopper());
		cards.add(new PoisonChain());
		cards.add(new PoisonFangs());
		cards.add(new PoisonMummy());
		cards.add(new PoisonOldMan());
		cards.add(new PoisonousMayakashi());
		cards.add(new PoisonousWinds());
		cards.add(new PoseidonBeetle());
		cards.add(new RazorLizard());
		cards.add(new RelinquishedSpider());
		cards.add(new ReptiliannePoison());
		cards.add(new ResonanceInsect());
		cards.add(new SkullMarkLadybug());
		cards.add(new SpiderEgg());
		cards.add(new SpiderWeb());
		cards.add(new SpiritualForest());
		cards.add(new Suanni());
		cards.add(new Taotie());
		cards.add(new TornadoDragon());
		cards.add(new UndergroundArachnid());	
		cards.add(new WallThorns());
		cards.add(new Yazi());
		cards.add(new ZefraMetaltron());
		cards.add(new Zektahawk());
		cards.add(new Zektarrow());
		cards.add(new Zektkaliber());
		if (DuelistMod.persistentDuelistData.CardPoolSettings.getBaseGameCards() && DuelistMod.isNotAllCardsPoolType())
		{
			cards.add(new NoxiousFumes());
			cards.add(new BouncingFlask());
			cards.add(new Catalyst());
			cards.add(new CorpseExplosion());
			cards.add(new CripplingPoison());
			cards.add(new Envenom());
			cards.add(new DeadlyPoison());
			cards.add(new PoisonedStab());
			cards.add(new Bane());			
		}

		//deck.fillPoolCards(cards);
		return cards;
	}
	
	public static  ArrayList<AbstractCard> basic()
	{
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> pool = new ArrayList<AbstractCard>();
		if (DuelistMod.persistentDuelistData.CardPoolSettings.getSmallBasicSet()) { pool.addAll(BasicPool.smallBasic("Insect Deck")); }
		else { pool.addAll(BasicPool.fullBasic("Insect Deck")); }
		//deck.fillPoolCards(pool); 
		return pool;
	}
}
