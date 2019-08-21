package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.helpers.Util;

public class AquaPool 
{
	private static String deckName = "Aqua Deck";
	
	public static ArrayList<AbstractCard> oneRandom()
	{
		ArrayList<ArrayList<AbstractCard>> pools = new ArrayList<ArrayList<AbstractCard>>();
		//pools.add(CreatorPool.deck());
		pools.add(DragonPool.deck());
		pools.add(FiendPool.deck());
		//pools.add(GiantPool.deck());
		pools.add(IncrementPool.deck());
		//pools.add(InsectPool.deck());
		pools.add(MachinePool.deck());
		pools.add(MegatypePool.deck());
		pools.add(NaturePool.deck());
		//pools.add(PlantPool.deck());
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
		deck.fillPoolCards(random);	
		Util.log(deckName + " was filled with random cards from the pool with index of " + DuelistMod.archRoll1);
		return random;
	}
	
	public static ArrayList<AbstractCard> twoRandom()
	{
		ArrayList<ArrayList<AbstractCard>> pools = new ArrayList<ArrayList<AbstractCard>>();
		//pools.add(CreatorPool.deck());
		pools.add(DragonPool.deck());
		pools.add(FiendPool.deck());
		//pools.add(GiantPool.deck());
		pools.add(IncrementPool.deck());
		//pools.add(InsectPool.deck());
		pools.add(MachinePool.deck());
		pools.add(MegatypePool.deck());
		pools.add(NaturePool.deck());
		//pools.add(PlantPool.deck());
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
		StarterDeck aquaDeck = DuelistMod.starterDeckNamesMap.get(deckName);
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
		aquaCards.add(new CallAtlanteans());
		aquaCards.add(new DarkSimorgh());
		aquaCards.add(new AmuletAmbition());
		aquaCards.add(new FlyingSaucer());
		aquaCards.add(new CosmicHorrorGangiel());
		aquaCards.add(new AlienTelepath());
		aquaCards.add(new Slushy());
		//aquaDeck.fillPoolCards(DuelistMod.basicCards);
		aquaDeck.fillPoolCards(aquaCards);		
		aquaDeck.fillArchetypeCards(aquaCards);
		DuelistMod.archetypeCards.addAll(aquaCards);
		return aquaCards;
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
