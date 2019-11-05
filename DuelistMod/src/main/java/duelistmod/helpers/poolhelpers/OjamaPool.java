package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.pools.dragons.*;
import duelistmod.cards.pools.warrior.WhiteHowling;

public class OjamaPool 
{
	private static String deckName = "Ojama Deck";
	
	public static ArrayList<AbstractCard> oneRandom()
	{
		ArrayList<AbstractCard> pool = new ArrayList<>();		
		pool.addAll(GlobalPoolHelper.oneRandom(13));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> twoRandom()
	{
		ArrayList<AbstractCard> pool = new ArrayList<>();		
		pool.addAll(GlobalPoolHelper.twoRandom(13));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> deck()
	{
		StarterDeck ojamaDeck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> ojamaCards = new ArrayList<AbstractCard>();
		
		// Ojama Cards
		ojamaCards.add(new OjamaRed());
		ojamaCards.add(new OjamaBlue());
		ojamaCards.add(new OjamaBlack());
		ojamaCards.add(new OjamaGreen());
		ojamaCards.add(new OjamaKnight());
		ojamaCards.add(new OjamaDeltaHurricane());
		ojamaCards.add(new Ojamatch());
		ojamaCards.add(new OjamaYellow());
		ojamaCards.add(new OjamaDuo());
		ojamaCards.add(new OjamaCountry());
		ojamaCards.add(new OjamaKing());
		ojamaCards.add(new OjamaTrio());
		ojamaCards.add(new Ojamuscle());
		ojamaCards.add(new OjamaPajama());
		ojamaCards.add(new Ojamassimilation());
		ojamaCards.add(new OjamaEmperor());
		ojamaCards.add(new Ojamagic());
		
		// Spellcaster & Fiend
		ojamaCards.add(new StatueAnguishPattern());		
		ojamaCards.add(new DarkTinker());
		ojamaCards.add(new GarbageLord());
		ojamaCards.add(new StarBlast());
		ojamaCards.add(new QueenDragunDjinn());
		ojamaCards.add(new IceQueen());
		ojamaCards.add(new DiffusionWaveMotion());
		ojamaCards.add(new CosmoBrain());
		ojamaCards.add(new NeoMagic());
		ojamaCards.add(new MysticalElf());
		ojamaCards.add(new DarkMagician());	
		ojamaCards.add(new SwordsConcealing());		
		ojamaCards.add(new Yami());		
		ojamaCards.add(new Illusionist());
		ojamaCards.add(new DarkMagicianGirl());
		ojamaCards.add(new IcyCrevasse());
		ojamaCards.add(new Relinquished());
		ojamaCards.add(new SangaWater());
		ojamaCards.add(new MindAir());
		ojamaCards.add(new DarkHorizon());
		ojamaCards.add(new WhiteMagicalHat());
		ojamaCards.add(new SwordsRevealing());
		ojamaCards.add(new Mudballman());
		ojamaCards.add(new TributeDoomed());
		ojamaCards.add(new Pumpking());
		ojamaCards.add(new Pumprincess());
		ojamaCards.add(new PotDuality());
		ojamaCards.add(new Zombyra());
		ojamaCards.add(new GiantOrc());
		ojamaCards.add(new FabledAshenveil());
		ojamaCards.add(new KingYami());
		ojamaCards.add(new DarkMasterZorc());
		ojamaCards.add(new FiendMegacyber());
		ojamaCards.add(new DarkBlade());
		ojamaCards.add(new SummonedSkull());
		ojamaCards.add(new GatesDarkWorld());
		ojamaCards.add(new Tierra());
		ojamaCards.add(new EvilswarmNightmare());
		ojamaCards.add(new KamionTimelord());
		ojamaCards.add(new IrisEarthMother());
		ojamaCards.add(new CrystalRaigeki());
		ojamaCards.add(new RainbowRuins());
		ojamaCards.add(new RainbowDragon());
		//ojamaCards.add(new HourglassLife());
		ojamaCards.add(new Eva());
		ojamaCards.add(new HappyLover());
		ojamaCards.add(new DunamesDarkWitch());
		ojamaCards.add(new RainbowNeos());
		ojamaCards.add(new RainbowFlower());
		ojamaCards.add(new SpiralSpearStrike());
		ojamaCards.add(new FlyingSaucer());
		ojamaCards.add(new CircleFireKings());
		ojamaCards.add(new OnslaughtFireKings());
		ojamaCards.add(new WhiteHowling());
		ojamaDeck.fillPoolCards(ojamaCards);		
		ojamaDeck.fillArchetypeCards(ojamaCards);
		//DuelistMod.archetypeCards.addAll(ojamaCards);
		return ojamaCards;
	}
	
	public static  ArrayList<AbstractCard> basic()
	{
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> pool = new ArrayList<AbstractCard>();
		if (DuelistMod.smallBasicSet) { pool.addAll(BasicPool.smallBasic("")); }
		else { pool.addAll(BasicPool.fullBasic("")); }
		deck.fillPoolCards(pool); 
		return pool;
	}
}
