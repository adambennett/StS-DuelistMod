package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.fourthWarriors.*;
import duelistmod.cards.incomplete.*;
import duelistmod.helpers.Util;

public class StandardPool 
{
	private static String deckName = "Standard Deck";
	
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
		pools.add(NaturePool.deck());
		pools.add(PlantPool.deck());
		//pools.add(PredaplantPool.deck());
		pools.add(SpellcasterPool.deck());
		pools.add(WarriorPool.deck());
		pools.add(ZombiePool.deck());
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
		pools.add(IncrementPool.deck());
		//pools.add(InsectPool.deck());
		pools.add(MachinePool.deck());
		pools.add(MegatypePool.deck());
		pools.add(NaturePool.deck());
		pools.add(PlantPool.deck());
		//pools.add(PredaplantPool.deck());
		pools.add(SpellcasterPool.deck());
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
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
	
		// Bonus
		cards.add(new LabyrinthWall());
		cards.add(new PreventRat());
		cards.add(new BigKoala());
		cards.add(new BattleFootballer());
		cards.add(new MillenniumShield());
		cards.add(new CharcoalInpachi());
		cards.add(new Mathematician());
		cards.add(new CastleWalls());
		cards.add(new GoldenApples());
		cards.add(new SilverApples());
		cards.add(new MiraculousDescent());
		cards.add(new Reinforcements());
		
		// Megatype
		cards.add(new HourglassLife());
		cards.add(new Eva());
		cards.add(new HappyLover());
		cards.add(new RainbowNeos());
		cards.add(new CrystalRaigeki());
		cards.add(new KamionTimelord());

		// Spells & Spell Support
		cards.add(new YamiForm());
		cards.add(new AncientGearFist());
		cards.add(new BerserkerCrush());
		cards.add(new CombinationAttack());
		cards.add(new CoreBlaster());
		cards.add(new FeatherShot());
		cards.add(new FuryFire());
		cards.add(new InfernoFireBlast());
		cards.add(new MeteorDestruction());
		cards.add(new SilentDoom());
		cards.add(new SparkBlaster());
		cards.add(new SpellShatteringArrow());
		cards.add(new SpiralSpearStrike());
		cards.add(new VenomShot());
		cards.add(new Wildfire());
		cards.add(new AmuletAmbition());
		cards.add(new MythicalBeast());
		cards.add(new Graverobber());
		cards.add(new WhiteMagicalHat());
		cards.add(new FrontierWiseman());
		cards.add(new SangaEarth());
		cards.add(new ImperialOrder());
		cards.add(new WormApocalypse());
		cards.add(new MagicalStone());
		cards.add(new Sangan());
		cards.add(new Raigeki());
		cards.add(new Wiretap());
		cards.add(new ElectromagneticShield());
		cards.add(new Electrowhip());
		cards.add(new StarBlast());
		cards.add(new PowerKaishin());
		cards.add(new BigFire());
		cards.add(new BlackPendant());
		cards.add(new CardDestruction());
		cards.add(new FairyBox());
		cards.add(new FeatherPho());
		cards.add(new FluteKuriboh());
		cards.add(new GiantTrunade());
		cards.add(new GravityBlaster());
		cards.add(new GravityAxe());
		cards.add(new HarpieFeather());
		cards.add(new Hinotama());
		cards.add(new Invigoration());
		cards.add(new LightningVortex());
		cards.add(new ScrapFactory());
		cards.add(new DarkFactory());
		cards.add(new PotDuality());
		cards.add(new PotGreed());
		cards.add(new Predaponics());
		cards.add(new RainMercy());
		cards.add(new SwordDeepSeated());
		cards.add(new SwordsRevealing());
		cards.add(new SwordsConcealing());
		cards.add(new ForbiddenLance());
		cards.add(new MiracleFertilizer());
		cards.add(new UnderworldCannon());
		cards.add(new SpiritHarp());
		cards.add(new ChaosSeed());
		cards.add(new CrossAttack());		
		cards.add(new GoldenSparks());		
		cards.add(new LegendarySword());
		cards.add(new LightningBlade());		
		
		if (!DuelistMod.ojamaBtnBool)
		{
			cards.add(new Ojamassimilation());
			cards.add(new OjamaCountry());
			cards.add(new OjamaDeltaHurricane());
			cards.add(new Ojamagic());
			cards.add(new Ojamuscle());
			cards.add(new Ojamatch());
		}
		
		if (DuelistMod.baseGameCards && DuelistMod.setIndex != 9)
		{
			//cards.add(new Token());
		}
		
		deck.fillPoolCards(cards);		
		deck.fillArchetypeCards(cards);
		return cards;
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
