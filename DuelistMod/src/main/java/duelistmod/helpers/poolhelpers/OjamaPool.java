package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.red.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.fourthWarriors.WhiteHowling;
import duelistmod.cards.incomplete.*;
import duelistmod.helpers.Util;

public class OjamaPool 
{
	private static String deckName = "Ojama Deck";
	
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
		ojamaCards.add(new HourglassLife());
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
		DuelistMod.archetypeCards.addAll(ojamaCards);
		return ojamaCards;
	}
	
	public static  ArrayList<AbstractCard> basic()
	{
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> pool = new ArrayList<AbstractCard>();
		if (DuelistMod.smallBasicSet) { pool.addAll(BasicPool.smallBasic()); }
		else { pool.addAll(BasicPool.fullBasic()); }
		deck.fillPoolCards(pool); 
		return pool;
	}
}
