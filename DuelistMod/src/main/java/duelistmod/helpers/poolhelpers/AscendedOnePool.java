package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;

public class AscendedOnePool 
{
	private static String deckName = "Ascended I";
	
	public static ArrayList<AbstractCard> oneRandom()
	{
		ArrayList<ArrayList<AbstractCard>> pools = new ArrayList<ArrayList<AbstractCard>>();
		pools.add(AquaPool.deck());
		//pools.add(CreatorPool.deck());
		pools.add(FiendPool.deck());		
		pools.add(IncrementPool.deck());
		pools.add(InsectPool.deck());
		pools.add(MachinePool.deck());
		pools.add(MegatypePool.deck());
		pools.add(NaturiaPool.deck());
		pools.add(PlantPool.deck());
		pools.add(StandardPool.deck());
		pools.add(WarriorPool.deck());
		pools.add(ZombiePool.deck());
		pools.add(RockPool.deck());
		if (!DuelistMod.ojamaBtnBool) { pools.add(OjamaPool.deck()); }
		if (!DuelistMod.toonBtnBool) { pools.add(ToonPool.deck()); }
		if (DuelistMod.archRoll1 == -1 || DuelistMod.archRoll2 == -1 || DuelistMod.archRoll1 > pools.size()) { DuelistMod.archRoll1 = ThreadLocalRandom.current().nextInt(pools.size()); }
		ArrayList<AbstractCard> random = pools.get(DuelistMod.archRoll1);
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(random);	
		return random;
	}
	
	public static ArrayList<AbstractCard> twoRandom()
	{
		ArrayList<ArrayList<AbstractCard>> pools = new ArrayList<ArrayList<AbstractCard>>();
		pools.add(AquaPool.deck());
		//pools.add(CreatorPool.deck());
		pools.add(FiendPool.deck());
		//pools.add(GiantPool.deck());
		pools.add(IncrementPool.deck());
		//pools.add(InsectPool.deck());
		pools.add(MachinePool.deck());
		pools.add(MegatypePool.deck());
		pools.add(NaturiaPool.deck());
		pools.add(PlantPool.deck());
		//pools.add(PredaplantPool.deck());
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
		return random;
	}
	
	public static ArrayList<AbstractCard> deck()
	{
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
		cards.addAll(DragonPool.deck());
		cards.addAll(SpellcasterPool.deck());			
		deck.fillPoolCards(cards);		
		deck.fillArchetypeCards(cards);
		return cards;
	}

	public static  ArrayList<AbstractCard> basic()
	{
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> pool = new ArrayList<AbstractCard>();
		pool.addAll(BasicPool.ascendedBasics());
		deck.fillPoolCards(pool); 
		return pool;
	}
}
