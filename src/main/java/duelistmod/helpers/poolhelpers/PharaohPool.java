package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;

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
		//cards.add(new HourglassLife());
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
		
		deckB.fillPoolCards(cards);
		
		deckC.fillPoolCards(cards);
		
		deckD.fillPoolCards(cards);
		
		deckE.fillPoolCards(cards);
		
		return cards;
	}
	
	public static ArrayList<AbstractCard> oneRandom()
	{
		ArrayList<AbstractCard> pools = new ArrayList<>();		
		pools.addAll(GlobalPoolHelper.oneRandom());
		String deckName = "Pharaoh ";
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName + "I");
		StarterDeck deckB = DuelistMod.starterDeckNamesMap.get(deckName + "II");
		StarterDeck deckC = DuelistMod.starterDeckNamesMap.get(deckName + "III");
		StarterDeck deckD = DuelistMod.starterDeckNamesMap.get(deckName + "IV");
		StarterDeck deckE = DuelistMod.starterDeckNamesMap.get(deckName + "IV");
		deck.fillPoolCards(pools);
		
		deckB.fillPoolCards(pools);
		
		deckC.fillPoolCards(pools);
		
		deckD.fillPoolCards(pools);
		
		deckE.fillPoolCards(pools);
		return pools;
	}
	
	public static ArrayList<AbstractCard> twoRandom()
	{
		ArrayList<AbstractCard> pools = new ArrayList<>();		
		pools.addAll(GlobalPoolHelper.twoRandom());
		String deckName = "Pharaoh ";
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName + "I");
		StarterDeck deckB = DuelistMod.starterDeckNamesMap.get(deckName + "II");
		StarterDeck deckC = DuelistMod.starterDeckNamesMap.get(deckName + "III");
		StarterDeck deckD = DuelistMod.starterDeckNamesMap.get(deckName + "IV");
		StarterDeck deckE = DuelistMod.starterDeckNamesMap.get(deckName + "IV");
		deck.fillPoolCards(pools);
		
		deckB.fillPoolCards(pools);
		
		deckC.fillPoolCards(pools);
		
		deckD.fillPoolCards(pools);
		
		deckE.fillPoolCards(pools);
		return pools;
	}
	
	public static ArrayList<AbstractCard> basic()
	{
		String deckName = "Pharaoh ";
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName + "I");
		StarterDeck deckB = DuelistMod.starterDeckNamesMap.get(deckName + "II");
		StarterDeck deckC = DuelistMod.starterDeckNamesMap.get(deckName + "III");
		StarterDeck deckD = DuelistMod.starterDeckNamesMap.get(deckName + "IV");
		StarterDeck deckE = DuelistMod.starterDeckNamesMap.get(deckName + "IV");
		
		if (DuelistMod.smallBasicSet) { deck.fillPoolCards(BasicPool.smallBasic(deckName)); }
		else { deck.fillPoolCards(BasicPool.fullBasic(deckName)); }
		
		if (DuelistMod.smallBasicSet) { deckB.fillPoolCards(BasicPool.smallBasic(deckName)); }
		else { deckB.fillPoolCards(BasicPool.fullBasic(deckName)); }
		
		if (DuelistMod.smallBasicSet) { deckC.fillPoolCards(BasicPool.smallBasic(deckName)); }
		else { deckC.fillPoolCards(BasicPool.fullBasic(deckName)); }
		
		if (DuelistMod.smallBasicSet) { deckD.fillPoolCards(BasicPool.smallBasic(deckName)); }
		else { deckD.fillPoolCards(BasicPool.fullBasic(deckName)); }
		
		if (DuelistMod.smallBasicSet) { deckE.fillPoolCards(BasicPool.smallBasic(deckName)); }
		else { deckE.fillPoolCards(BasicPool.fullBasic(deckName)); }
		
		if (DuelistMod.smallBasicSet) { return BasicPool.smallBasic(deckName); }
		else { return BasicPool.fullBasic(deckName); }
	}
}
