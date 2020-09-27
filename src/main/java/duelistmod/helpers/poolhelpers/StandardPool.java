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
import duelistmod.cards.pools.zombies.*;

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
		
		// Megatype
		cards.add(new CrystalRaigeki());
		cards.add(new Eva());
		cards.add(new HappyLover());
		cards.add(new KamionTimelord());
		cards.add(new RainbowNeos());
		
		// Bonus
		cards.add(new BattleFootballer());
		cards.add(new BigKoala());
		cards.add(new CharcoalInpachi());
		cards.add(new Mathematician());
		cards.add(new MillenniumShield());
		cards.add(new MiraculousDescent());
		cards.add(new PowerWall());
		cards.add(new PreventRat());
		cards.add(new Reinforcements());
		cards.add(new SilverApples());
		cards.add(new SolarWindJammer());
		cards.add(new Tuningware());		

		// Spells & Spell Support
		cards.add(new AfterGenocide());
		cards.add(new AfterTheStorm());
		cards.add(new AmuletAmbition());
		cards.add(new AncientGearFist());
		cards.add(new AssaultArmor());
		cards.add(new BerserkerCrush());
		cards.add(new BigFire());
		cards.add(new BlackPendant());
		cards.add(new BreakDraw());
		cards.add(new ChaosSeed());
		cards.add(new CircleFireKings());
		cards.add(new CombinationAttack());
		cards.add(new CoreBlaster());
		cards.add(new CrossAttack());
		cards.add(new CrystalBlessing());
		cards.add(new DarkBurningAttack());
		cards.add(new DarkBurningMagic());
		cards.add(new DarkFactory());
		cards.add(new DeltaAttacker());
		cards.add(new DoubleTool());
		cards.add(new Downbeat());
		cards.add(new EgoBoost());
		cards.add(new ElectromagneticShield());
		cards.add(new Electrowhip());
		cards.add(new FairyBox());
		cards.add(new FeatherShot());
		cards.add(new FluteKuriboh());
		cards.add(new ForbiddenLance());
		cards.add(new FrontierWiseman());
		cards.add(new FuryFire());
		cards.add(new FusionFire());		
		cards.add(new FusionWeapon());
		cards.add(new GiantTrunade());
		cards.add(new Graverobber());
		cards.add(new GravityAxe());
		cards.add(new GravityBlaster());
		cards.add(new GravityLash());
		cards.add(new GridRod());
		cards.add(new Hinotama());
		cards.add(new ImperialOrder());
		cards.add(new InfernoFireBlast());
		cards.add(new InstantFusion());
		cards.add(new Invigoration());
		cards.add(new LegendHeart());
		cards.add(new LegendarySword());
		cards.add(new LightLaser());
		cards.add(new LightningBlade());
		cards.add(new LightningVortex());
		cards.add(new MagicalStone());
		cards.add(new MagnumShield());
		cards.add(new MeteorDestruction());
		cards.add(new MiracleFertilizer());
		cards.add(new MythicalBeast());
		cards.add(new OnslaughtFireKings());
		cards.add(new PineappleBlast());
		cards.add(new PotDuality());
		cards.add(new PowerKaishin());
		cards.add(new Predaponics());
		cards.add(new PsychicShockwave());
		cards.add(new Raigeki());
		cards.add(new SangaEarth());
		cards.add(new Sangan());
		cards.add(new ScrapFactory());
		cards.add(new SevenCompleted());
		cards.add(new SilentDoom());
		cards.add(new SparkBlaster());
		cards.add(new SpellShatteringArrow());
		cards.add(new SpiralSpearStrike());
		cards.add(new SpiritHarp());
		cards.add(new StarBlast());
		cards.add(new SwordDeepSeated());
		cards.add(new SwordsConcealing());
		cards.add(new SwordsRevealing());
		cards.add(new UnderworldCannon());
		cards.add(new VendreadCharge());
		cards.add(new VenomShot());
		cards.add(new WhiteHowling());		
		cards.add(new WhiteMagicalHat());
		cards.add(new WhiteNinja());
		cards.add(new Wildfire());
		cards.add(new Wiretap());
		cards.add(new WormApocalypse());
		cards.add(new YamiForm());
		
		if (!DuelistMod.ojamaBtnBool)
		{
			cards.add(new Ojamassimilation());
			cards.add(new OjamaCountry());
			cards.add(new OjamaDeltaHurricane());
			cards.add(new Ojamagic());
			cards.add(new Ojamatch());
		}
		
		if (DuelistMod.baseGameCards && DuelistMod.setIndex != 9)
		{
			//cards.add(new Token());
		}
		
		deck.fillPoolCards(cards);
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
