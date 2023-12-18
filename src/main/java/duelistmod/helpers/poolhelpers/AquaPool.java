package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.red.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.pools.aqua.*;
import duelistmod.cards.pools.dragons.LeviaDragon;
import duelistmod.cards.pools.dragons.Megalosmasher;
import duelistmod.cards.pools.machine.CatapultTurtle;
import duelistmod.cards.pools.plant.MobiusFrostMonarch;

public class AquaPool 
{
	private static String deckName = "Aqua Deck";
	
	public static ArrayList<AbstractCard> oneRandom()
	{
		ArrayList<AbstractCard> pool = new ArrayList<>();		
		pool.addAll(GlobalPoolHelper.oneRandom(0));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		//deck.fillPoolCards(pool);
		return pool;
	}
	
	public static ArrayList<AbstractCard> twoRandom()
	{
		ArrayList<AbstractCard> pool = new ArrayList<>();		
		pool.addAll(GlobalPoolHelper.twoRandom(0));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		//deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> deck()
	{
		StarterDeck aquaDeck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> aquaCards = new ArrayList<>();	
		aquaCards.add(new AbyssDweller());
		aquaCards.add(new AbyssSoldier());
		aquaCards.add(new AbyssWarrior());
		aquaCards.add(new AbyssalKingshark());
		aquaCards.add(new AegisOceanDragon());
		aquaCards.add(new Akihiron());
		aquaCards.add(new AmphibiousBugroth());
		aquaCards.add(new AquaSnake());
		aquaCards.add(new AquaactressArowana());
		aquaCards.add(new AquaactressGuppy());
		aquaCards.add(new AquaactressTetra());
		aquaCards.add(new ArmoredStarfish());
		aquaCards.add(new AtlanteanAttackSquad());		
		aquaCards.add(new AtlanteanHeavyInfantry());				
		aquaCards.add(new BigDesFrog());		
		aquaCards.add(new BigWaveSmallWave());		
		aquaCards.add(new BigWhale());			
		aquaCards.add(new BlizzardThunderbird());
		aquaCards.add(new Boneheimer());
		aquaCards.add(new BoulderTortoise());		
		aquaCards.add(new BubbleBringer());		
		aquaCards.add(new CallAtlanteans());			
		aquaCards.add(new CannonballSpearShellfish());		
		aquaCards.add(new CatShark());		
		aquaCards.add(new CatapultTurtle());		
		aquaCards.add(new CitadelWhale());		
		aquaCards.add(new ColdEnchanter());		
		aquaCards.add(new ColdWave());		
		aquaCards.add(new CorrodingShark());			
		aquaCards.add(new Cryomancer());
		aquaCards.add(new CyberShark());			
		aquaCards.add(new DeepSweeper());		
		aquaCards.add(new DeepseaShark());		
		aquaCards.add(new DepthShark());		
		aquaCards.add(new Dewdark());		
		aquaCards.add(new DewlorenTigerKing());		
		aquaCards.add(new DiamondDust());		
		aquaCards.add(new Dorover());		
		aquaCards.add(new DrillBarnacle());		
		aquaCards.add(new EagleShark());			
		aquaCards.add(new EnchantingMermaid());		
		aquaCards.add(new EvigishkiGustkraken());
		aquaCards.add(new EvigishkiLevianima());
		aquaCards.add(new EvigishkiMerrowgeist());		
		aquaCards.add(new EvigishkiMindAugus());
		aquaCards.add(new EvigishkiSoulOgre());		
		aquaCards.add(new EvigishkiTetrogre());			
		aquaCards.add(new FishDepthCharge());		
		aquaCards.add(new FishKicks());		
		aquaCards.add(new FishRain());		
		aquaCards.add(new FishSwaps());		
		aquaCards.add(new FishborgArcher());		
		aquaCards.add(new FishborgBlaster());
		aquaCards.add(new FishborgDoctor());		
		aquaCards.add(new FishborgLauncher());		
		aquaCards.add(new FishborgPlanter());		
		aquaCards.add(new FrillerRabca());		
		aquaCards.add(new FuriousSeaKing());		
		aquaCards.add(new GamecieltheSeaTurtleKaiju());		
		aquaCards.add(new GemKnightAmethyst());			
		aquaCards.add(new GiantPairfish());		
		aquaCards.add(new GiantRedSeasnake());		
		aquaCards.add(new GiantTurtle());		
		aquaCards.add(new GishkiAriel());
		aquaCards.add(new GishkiAvance());
		aquaCards.add(new GishkiBeast());
		aquaCards.add(new GishkiEmilia());
		aquaCards.add(new GishkiMollusk());
		aquaCards.add(new GishkiPsychelone());
		aquaCards.add(new GishkiReliever());		
		aquaCards.add(new GishkiShadow());
		aquaCards.add(new GishkiZielgigas());		
		aquaCards.add(new GoldenFlyingFish());		
		aquaCards.add(new GoraTurtle());
		aquaCards.add(new GraydleAlligator());		
		aquaCards.add(new GraydleCobra());
		aquaCards.add(new GraydleCombat());
		aquaCards.add(new GraydleDragon());
		aquaCards.add(new GraydleEagle());
		aquaCards.add(new GraydleParasite());
		aquaCards.add(new GraydleSlime());
		aquaCards.add(new GraydleSlimeJr());
		aquaCards.add(new HighTideGyojin());
		aquaCards.add(new HumanoidSlime());		
		aquaCards.add(new HydraViper());		
		aquaCards.add(new Hyosube());		
		aquaCards.add(new HyperancientShark());		
		aquaCards.add(new IceHand());		
		aquaCards.add(new ImperialCustom());		
		aquaCards.add(new IslandTurtle());				
		aquaCards.add(new KaiserSeaSnake());		
		aquaCards.add(new KoakiMeiruIce());				
		aquaCards.add(new LegendaryOcean());		
		aquaCards.add(new LeviaDragon());				
		aquaCards.add(new LeviairSeaDragon());		
		aquaCards.add(new LiquidBeast());
		aquaCards.add(new LostBlueBreaker());
		aquaCards.add(new Megalosmasher());
		aquaCards.add(new MermaidKnight());		
		aquaCards.add(new MermailAbyssalacia());		
		aquaCards.add(new Monokeros());		
		aquaCards.add(new MorayGreed());		
		aquaCards.add(new NightmarePenguin());		
		aquaCards.add(new NimbleAngler());
		aquaCards.add(new NimbleManta());
		aquaCards.add(new NimbleSunfish());			
		aquaCards.add(new OhFish());		
		aquaCards.add(new OldWhiteTurtle());			
		aquaCards.add(new RageDeepSea());
		aquaCards.add(new RevivalJam());			
		aquaCards.add(new SeaLordAmulet());
		aquaCards.add(new SevenColoredFish());
		aquaCards.add(new SharkStickers());			
		aquaCards.add(new Skystarray());		
		aquaCards.add(new Slushy());		
		aquaCards.add(new SpearfishSoldier());		
		aquaCards.add(new SpikedGillman());				
		aquaCards.add(new SplashCapture());		
		aquaCards.add(new StarBoy());		
		aquaCards.add(new SwampFrog());			
		aquaCards.add(new Submarineroid());			
		aquaCards.add(new TerrorkingSalmon());		
		aquaCards.add(new ThunderSeaHorse());		
		aquaCards.add(new PaleozoicAnomalocaris());
		aquaCards.add(new TorrentialReborn());		
		aquaCards.add(new TorrentialTribute());		
		aquaCards.add(new TripodFish());
		aquaCards.add(new Umi());		
		aquaCards.add(new Uminotaurus());		
		aquaCards.add(new Unifrog());		
		aquaCards.add(new WaterHazard());		
		aquaCards.add(new WaterSpirit());		
		aquaCards.add(new Wetlands());		
		aquaCards.add(new WhiteAuraWhale());		
		aquaCards.add(new Wingedtortoise());
		aquaCards.add(new UnshavenAngler());
		aquaCards.add(new MobiusFrostMonarch());
		//aquaCards.add(new AquariumLighting());		
		//aquaCards.add(new CrystalEmeraldTortoise());			
		//aquaCards.add(new FieldBarrier());		
		//aquaCards.add(new ForgottenCity());		
		//aquaCards.add(new LadyOfTheLake());			
		//aquaCards.add(new Umiiruka());						
		//aquaCards.add(new WhitefishSalvage());	
		// 145 cards with this extra spaces
		
		if (DuelistMod.persistentDuelistData.CardPoolSettings.getBaseGameCards() && DuelistMod.isNotAllCardsPoolType())
		{
			aquaCards.add(new Anger());
			aquaCards.add(new Headbutt());
			aquaCards.add(new PommelStrike());
			aquaCards.add(new ShrugItOff());
			aquaCards.add(new BattleTrance());
			aquaCards.add(new Immolate());
			aquaCards.add(new Backflip());
			aquaCards.add(new CalculatedGamble());
			aquaCards.add(new Distraction());
			aquaCards.add(new EndlessAgony());
			aquaCards.add(new Expertise());
			aquaCards.add(new Predator());
			aquaCards.add(new Nightmare());
			aquaCards.add(new ToolsOfTheTrade());
			aquaCards.add(new Hologram());
			aquaCards.add(new Stack());
			aquaCards.add(new Aggregate());
			aquaCards.add(new Consume());
			aquaCards.add(new HelloWorld());
			aquaCards.add(new Overclock());
			aquaCards.add(new Skim());
			aquaCards.add(new MachineLearning());
			aquaCards.add(new Reboot());
		}
		
		//aquaDeck.fillPoolCards(aquaCards);
		//DuelistMod.archetypeCards.addAll(aquaCards);
		return aquaCards;
	}
	
	public static  ArrayList<AbstractCard> basic()
	{
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> pool = new ArrayList<AbstractCard>();
		if (DuelistMod.persistentDuelistData.CardPoolSettings.getSmallBasicSet()) { pool.addAll(BasicPool.smallBasic("Aqua Deck")); }
		else { pool.addAll(BasicPool.fullBasic("Aqua Deck")); }
		//deck.fillPoolCards(pool); 
		return pool;
	}
}
