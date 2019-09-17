package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;

public class PharaohPool 
{
	public static ArrayList<AbstractCard> deck()
	{
		String deckName = "Pharaoh ";
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName + "I");
		StarterDeck deckB = DuelistMod.starterDeckNamesMap.get(deckName + "II");
		StarterDeck deckC = DuelistMod.starterDeckNamesMap.get(deckName + "III");
		StarterDeck deckD = DuelistMod.starterDeckNamesMap.get(deckName + "IV");
		StarterDeck deckE = DuelistMod.starterDeckNamesMap.get(deckName + "IV");
		ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
	
		// Pharaoh Cards
		cards.add(new PharaohBlessing());
		cards.add(new RainbowMedicine());
		
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
		cards.add(new HourglassLife());
		cards.add(new Eva());
		cards.add(new HappyLover());
		cards.add(new DunamesDarkWitch());
		cards.add(new RainbowNeos());
		cards.add(new RainbowFlower());
		
		if (DuelistMod.baseGameCards && DuelistMod.setIndex != 9)
		{
			//cards.add(new Token());
		}

		deck.fillPoolCards(cards);		
		deck.fillArchetypeCards(cards);		
		
		deckB.fillPoolCards(cards);		
		deckB.fillArchetypeCards(cards);		
		
		deckC.fillPoolCards(cards);		
		deckC.fillArchetypeCards(cards);		
		
		deckD.fillPoolCards(cards);		
		deckD.fillArchetypeCards(cards);		
		
		deckE.fillPoolCards(cards);		
		deckE.fillArchetypeCards(cards);
		
		return cards;
	}
	
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
		String deckName = "Pharaoh ";
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName + "I");
		StarterDeck deckB = DuelistMod.starterDeckNamesMap.get(deckName + "II");
		StarterDeck deckC = DuelistMod.starterDeckNamesMap.get(deckName + "III");
		StarterDeck deckD = DuelistMod.starterDeckNamesMap.get(deckName + "IV");
		StarterDeck deckE = DuelistMod.starterDeckNamesMap.get(deckName + "IV");
		deck.fillPoolCards(random);		
		deckB.fillPoolCards(random);		
		deckC.fillPoolCards(random);		
		deckD.fillPoolCards(random);		
		deckE.fillPoolCards(random);		
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
		String deckName = "Pharaoh ";
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName + "I");
		StarterDeck deckB = DuelistMod.starterDeckNamesMap.get(deckName + "II");
		StarterDeck deckC = DuelistMod.starterDeckNamesMap.get(deckName + "III");
		StarterDeck deckD = DuelistMod.starterDeckNamesMap.get(deckName + "IV");
		StarterDeck deckE = DuelistMod.starterDeckNamesMap.get(deckName + "IV");
		deck.fillPoolCards(random);		
		deckB.fillPoolCards(random);		
		deckC.fillPoolCards(random);		
		deckD.fillPoolCards(random);		
		deckE.fillPoolCards(random);		
		return random;
	}
	
	public static ArrayList<AbstractCard> basic()
	{
		String deckName = "Pharaoh ";
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName + "I");
		StarterDeck deckB = DuelistMod.starterDeckNamesMap.get(deckName + "II");
		StarterDeck deckC = DuelistMod.starterDeckNamesMap.get(deckName + "III");
		StarterDeck deckD = DuelistMod.starterDeckNamesMap.get(deckName + "IV");
		StarterDeck deckE = DuelistMod.starterDeckNamesMap.get(deckName + "IV");
		deck.fillPoolCards(DuelistMod.basicCards);
		deckB.fillPoolCards(DuelistMod.basicCards);
		deckC.fillPoolCards(DuelistMod.basicCards);
		deckD.fillPoolCards(DuelistMod.basicCards);
		deckE.fillPoolCards(DuelistMod.basicCards);	
		return DuelistMod.basicCards;
	}
}
