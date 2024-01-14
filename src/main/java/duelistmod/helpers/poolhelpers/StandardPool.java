package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.DuelistMod;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.pools.beast.NeoSpacianDarkPanther;
import duelistmod.cards.pools.beast.NimbleMomonga;
import duelistmod.cards.pools.beast.UnicornBeacon;
import duelistmod.cards.pools.dragons.*;
import duelistmod.cards.pools.increment.GenerationNext;
import duelistmod.cards.pools.increment.Magikuriboh;
import duelistmod.cards.pools.increment.Multiply;
import duelistmod.cards.pools.increment.TranscendentWings;
import duelistmod.cards.pools.insects.*;
import duelistmod.cards.pools.machine.*;
import duelistmod.cards.pools.naturia.AttackTheMoon;
import duelistmod.cards.pools.plant.Predaponics;
import duelistmod.cards.pools.spellcaster.MaskedSorcerer;
import duelistmod.cards.pools.warrior.*;
import duelistmod.cards.pools.zombies.*;

public class StandardPool {
	public static ArrayList<AbstractCard> oneRandom() {
        return new ArrayList<>(GlobalPoolHelper.oneRandom(8, 15));
	}
	
	public static ArrayList<AbstractCard> twoRandom() {
        return new ArrayList<>(GlobalPoolHelper.twoRandom(8, 15));
	}
	
	public static ArrayList<AbstractCard> deck() {
		ArrayList<AbstractCard> cards = new ArrayList<>();
		
		// Megatype
		cards.add(new CrystalRaigeki());
		cards.add(new HappyLover());
		cards.add(new KamionTimelord());
		cards.add(new RainbowNeos());
		
		// Bonus
		cards.add(new BattleFootballer());
		cards.add(new BigKoala());
		cards.add(new BarricadeborgBlocker());
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
		cards.add(new NimbleMomonga());

		// Spells & Spell Support
		cards.add(new AfterGenocide());
		cards.add(new AfterTheStorm());
		cards.add(new AttackTheMoon());
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
		cards.add(new FusionWeapon());
		cards.add(new GenerationNext());
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
		cards.add(new LegendarySword());
		cards.add(new LightningBlade());
		cards.add(new LightningVortex());
		cards.add(new MagicalStone());
		cards.add(new MagnumShield());
		cards.add(new Magikuriboh());
		cards.add(new MaskedSorcerer());
		cards.add(new MeteorDestruction());
		cards.add(new Multiply());
		cards.add(new MythicalBeast());
		cards.add(new NeoSpacianDarkPanther());
		cards.add(new OnslaughtFireKings());
		cards.add(new PineappleBlast());
		cards.add(new PotDuality());
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
		cards.add(new TranscendentWings());
		cards.add(new UnderworldCannon());
		cards.add(new UnicornBeacon());
		cards.add(new VendreadCharge());
		cards.add(new WhiteHowling());
		cards.add(new WhiteNinja());
		cards.add(new Wildfire());
		cards.add(new Wiretap());
		cards.add(new WormApocalypse());
		cards.add(new YamiForm());

		if (DuelistMod.persistentDuelistData.CardPoolSettings.getBaseGameCards() && DuelistMod.isNotAllCardsPoolType()) {
			//cards.add(new Token());
		}
		return cards;
	}
	
	public static  ArrayList<AbstractCard> basic() {
		ArrayList<AbstractCard> pool = new ArrayList<>();
		if (DuelistMod.persistentDuelistData.CardPoolSettings.getSmallBasicSet()) {
			pool.addAll(BasicPool.smallBasic(""));
		} else {
			pool.addAll(BasicPool.fullBasic(""));
		}
		return pool;
	}
}
