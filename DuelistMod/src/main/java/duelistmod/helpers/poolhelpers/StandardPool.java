package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.pools.dragons.*;
import duelistmod.cards.pools.insects.*;
import duelistmod.cards.pools.machine.*;
import duelistmod.cards.pools.warrior.*;
import duelistmod.cards.pools.zombies.UnderworldCannon;

public class StandardPool 
{
	private static String deckName = "Standard Deck";
	
	public static ArrayList<AbstractCard> oneRandom()
	{
		ArrayList<AbstractCard> pool = new ArrayList<>();		
		pool.addAll(GlobalPoolHelper.oneRandom(9, 16));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> twoRandom()
	{
		ArrayList<AbstractCard> pool = new ArrayList<>();		
		pool.addAll(GlobalPoolHelper.twoRandom(9, 16));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> deck()
	{
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
	
		// Bonus
		cards.add(new PreventRat());
		cards.add(new BigKoala());
		cards.add(new BattleFootballer());
		cards.add(new MillenniumShield());
		cards.add(new CharcoalInpachi());
		cards.add(new Mathematician());
		cards.add(new PowerWall());
		cards.add(new SilverApples());
		cards.add(new MiraculousDescent());
		cards.add(new Reinforcements());
		cards.add(new SolarWindJammer());
		cards.add(new Tuningware());
		
		// Megatype
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
		cards.add(new FluteKuriboh());
		cards.add(new GiantTrunade());
		cards.add(new GravityBlaster());
		cards.add(new GravityAxe());
		//cards.add(new HarpieFeather());
		cards.add(new Hinotama());
		cards.add(new Invigoration());
		cards.add(new LightningVortex());
		cards.add(new ScrapFactory());
		cards.add(new DarkFactory());
		cards.add(new PotDuality());
		cards.add(new Predaponics());
		//cards.add(new RainMercy());
		cards.add(new SwordDeepSeated());
		cards.add(new SwordsRevealing());
		cards.add(new SwordsConcealing());
		cards.add(new ForbiddenLance());
		cards.add(new MiracleFertilizer());
		cards.add(new UnderworldCannon());
		cards.add(new SpiritHarp());
		cards.add(new CircleFireKings());
		cards.add(new OnslaughtFireKings());
		cards.add(new WhiteHowling());
		
		cards.add(new Downbeat());
		cards.add(new EgoBoost());
		cards.add(new AfterGenocide());
		cards.add(new AdvanceForce());
		cards.add(new AfterTheStorm());
		cards.add(new AssaultArmor());
		cards.add(new ChaosSeed());
		cards.add(new CrossAttack());
		cards.add(new CrystalBlessing());
		cards.add(new DarkBurningAttack());
		cards.add(new DarkBurningMagic());
		cards.add(new DeltaAttacker());
		cards.add(new PineappleBlast());
		cards.add(new GridRod());
		cards.add(new HarmonicWaves());
		cards.add(new LegendHeart());
		cards.add(new LightLaser());
		cards.add(new LegendarySword());
		cards.add(new LightningBlade());
		cards.add(new MagnumShield());
		cards.add(new SwordDragonSoul());
		cards.add(new WhiteNinja());
		cards.add(new PsychicShockwave());
		cards.add(new BreakDraw());
		cards.add(new DoubleTool());
		cards.add(new SevenCompleted());
		cards.add(new GravityLash());
		
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
		if (DuelistMod.smallBasicSet) { pool.addAll(BasicPool.smallBasic("")); }
		else { pool.addAll(BasicPool.fullBasic("")); }
		deck.fillPoolCards(pool); 
		return pool;
	}
}
