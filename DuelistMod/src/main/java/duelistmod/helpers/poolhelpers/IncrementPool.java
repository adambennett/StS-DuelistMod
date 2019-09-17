package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.fourthWarriors.WhiteHowling;
import duelistmod.cards.incomplete.*;
import duelistmod.helpers.Util;

public class IncrementPool 
{
	private static String deckName = "Increment Deck";
	
	public static ArrayList<AbstractCard> oneRandom()
	{
		ArrayList<ArrayList<AbstractCard>> pools = new ArrayList<ArrayList<AbstractCard>>();
		pools.add(AquaPool.deck());
		//pools.add(CreatorPool.deck());
		pools.add(DragonPool.deck());
		pools.add(FiendPool.deck());
		//pools.add(GiantPool.deck());
		//pools.add(InsectPool.deck());
		pools.add(MachinePool.deck());
		pools.add(MegatypePool.deck());
		pools.add(NaturiaPool.deck());
		pools.add(PlantPool.deck());
		//pools.add(PredaplantPool.deck());
		pools.add(SpellcasterPool.deck());
		pools.add(StandardPool.deck());
		pools.add(WarriorPool.deck());
		pools.add(ZombiePool.deck());
		pools.add(RockPool.deck());
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
		//pools.add(InsectPool.deck());
		pools.add(MachinePool.deck());
		pools.add(MegatypePool.deck());
		pools.add(NaturiaPool.deck());
		pools.add(PlantPool.deck());
		//pools.add(PredaplantPool.deck());
		pools.add(SpellcasterPool.deck());
		pools.add(StandardPool.deck());
		pools.add(WarriorPool.deck());
		pools.add(ZombiePool.deck());
		pools.add(RockPool.deck());
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
		StarterDeck incrementDeck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> incCards = new ArrayList<AbstractCard>();
		incCards.add(new Fissure());
		incCards.add(new Kuriboh());
		incCards.add(new PotGenerosity());				
		incCards.add(new Scapegoat());		
		incCards.add(new SphereKuriboh());
		incCards.add(new UltimateOffering());
		incCards.add(new HammerShot());
		incCards.add(new SmashingGround());
		incCards.add(new StrayLambs());
		incCards.add(new GoblinRemedy());
		incCards.add(new RadiantMirrorForce());
		incCards.add(new PotAvarice());
		incCards.add(new PotDichotomy());
		incCards.add(new WingedKuriboh());
		incCards.add(new JunkKuriboh());
		incCards.add(new FluteKuriboh());
		incCards.add(new LostBlueBreaker());
		incCards.add(new Wingedtortoise());
		incCards.add(new GemKnightAmethyst());
		incCards.add(new WingedKuriboh9());
		incCards.add(new WingedKuriboh10());
		incCards.add(new Kuribohrn());
		incCards.add(new DarkFusion());
		incCards.add(new CrystalEmeraldTortoise());
		incCards.add(new RainbowKuriboh());
		incCards.add(new ClearKuriboh());
		incCards.add(new Linkuriboh());
		incCards.add(new Spore());
		incCards.add(new SilverApples());
		incCards.add(new GoldenApples());
		incCards.add(new BeastFangs());
		incCards.add(new AmuletDragon());
		incCards.add(new DarklordSuperbia());
		incCards.add(new LightningVortex());		
		incCards.add(new DarkSimorgh());	
		incCards.add(new CosmoBrain());	
		incCards.add(new InariFire());		
		incCards.add(new AmuletAmbition());		
		incCards.add(new Relinkuriboh());		
		incCards.add(new LegendaryFlameLord());		
		incCards.add(new LavaDragon());		
		incCards.add(new FlameTiger());		
		incCards.add(new GeminiElf());		
		incCards.add(new SwordsBurning());		
		incCards.add(new ArsenalBug());		
		incCards.add(new CrossSwordBeetle());		
		incCards.add(new MultiplicationOfAnts());		
		incCards.add(new DarkSpider());		
		incCards.add(new UndergroundArachnid());
		incCards.add(new WhiteHowling());
		
		
		if (DuelistMod.baseGameCards && DuelistMod.setIndex != 9)
		{
			//incCards.add(new Token());
		}
		incrementDeck.fillPoolCards(incCards);		
		incrementDeck.fillArchetypeCards(incCards);
		return incCards;
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
