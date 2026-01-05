package duelistmod.helpers.poolhelpers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Recycle;
import com.megacrit.cardcrawl.cards.purple.*;
import com.megacrit.cardcrawl.cards.red.*;
import duelistmod.DuelistMod;
import duelistmod.cards.pools.machine.*;
import duelistmod.cards.pools.toon.RoseWarriorOfRevenge;
import duelistmod.cards.pools.warrior.*;

import java.util.ArrayList;

public class WarriorPool {

	public static ArrayList<AbstractCard> oneRandom() {
        return new ArrayList<>(GlobalPoolHelper.oneRandom(9));
	}
	
	public static ArrayList<AbstractCard> twoRandom() {
        return new ArrayList<>(GlobalPoolHelper.twoRandom(9));
	}
	
	public static ArrayList<AbstractCard> deck() {
		ArrayList<AbstractCard> warriorCards = new ArrayList<>();

		warriorCards.add(new AdvanceForce());
		warriorCards.add(new AfterGenocide());
		warriorCards.add(new AfterTheStorm());
		warriorCards.add(new AgainstTheWind());
		warriorCards.add(new AlphaMagnet());
		warriorCards.add(new ArmorBreaker());
		warriorCards.add(new AssaultArmor());
		warriorCards.add(new BattleWarrior());
		warriorCards.add(new BattleguardHowling());
		warriorCards.add(new BattleguardRage());
		warriorCards.add(new BetaMagnet());
		warriorCards.add(new BladeArmorNinja());
		warriorCards.add(new BrilliantSpark());
		warriorCards.add(new BullBlader());
		warriorCards.add(new CelticGuardian());
		warriorCards.add(new ColossalFighter());
		warriorCards.add(new CommanderSwords());
		warriorCards.add(new CrystalBlessing());
		warriorCards.add(new CrystalTree());
		warriorCards.add(new CubicKarma());
		warriorCards.add(new CubicWave());
		warriorCards.add(new CyberRaider());
		warriorCards.add(new DarkGrepher());
		warriorCards.add(new DarkOccultism());
		warriorCards.add(new DawnKnight());
		warriorCards.add(new DeltaAttacker());
		warriorCards.add(new Downbeat());
		warriorCards.add(new DropOff());
		warriorCards.add(new EgoBoost());
		warriorCards.add(new FeedbackWarrior());
		warriorCards.add(new FortressWarrior());
		warriorCards.add(new GaiaFierce());
		warriorCards.add(new GammaMagnet());
		warriorCards.add(new GauntletWarrior());
		warriorCards.add(new GlowingCrossbow());
		warriorCards.add(new GoyoChaser());
		warriorCards.add(new GoyoDefender());
		warriorCards.add(new GoyoEmperor());
		warriorCards.add(new GravityWarrior());
		warriorCards.add(new GuardianOrder());
		warriorCards.add(new HarmonicWaves());
		warriorCards.add(new HayateTheEarthStar());
		warriorCards.add(new HerculeanPower());
		warriorCards.add(new HeroRing());
		warriorCards.add(new LegendHeart());
		warriorCards.add(new LegendaryBlackBelt());
		warriorCards.add(new LegendarySword());
		warriorCards.add(new LightLaser());
		warriorCards.add(new MaxWarrior());
		warriorCards.add(new MagnetForce());
		warriorCards.add(new MillenniumShield());
		warriorCards.add(new NinjaGrandmaster());
		warriorCards.add(new NitroWarrior());
		warriorCards.add(new ReinforcementsArmy());
		warriorCards.add(new RockstoneWarrior());
		warriorCards.add(new Sogen());
		warriorCards.add(new SoldierLady());
		warriorCards.add(new SpiritForce());
		warriorCards.add(new SuperheavyBenkei());
		warriorCards.add(new SuperheavyBigWaraji());
		warriorCards.add(new SuperheavyFlutist());
		warriorCards.add(new SuperheavyMagnet());
		warriorCards.add(new SuperheavyOgre());
		warriorCards.add(new SuperheavyScales());
		warriorCards.add(new SuperheavySoulbeads());
		warriorCards.add(new SuperheavySoulhorns());
		warriorCards.add(new SuperheavySoulpiercer());
		warriorCards.add(new SuperheavySoulshield());
		warriorCards.add(new SuperheavySwordsman());
		warriorCards.add(new SwordsConcealing());
		warriorCards.add(new Valkyrion());
		warriorCards.add(new WarriorReturningAlive());
		warriorCards.add(new WeaponChange());
		warriorCards.add(new WhiteNinja());
		warriorCards.add(new Zombyra());

		if (DuelistMod.persistentDuelistData.CardPoolSettings.getBaseGameCards() && DuelistMod.isNotAllCardsPoolType()) {
			warriorCards.add(new Rushdown());
			warriorCards.add(new Blasphemy());
			warriorCards.add(new BowlingBash());
			warriorCards.add(new CarveReality());
			//magnetCards.add(new Clarity());
			warriorCards.add(new ConjureBlade());
			warriorCards.add(new Consecrate());
			warriorCards.add(new CutThroughFate());
			warriorCards.add(new DeceiveReality());
			warriorCards.add(new DeusExMachina());
			warriorCards.add(new DevaForm());
			warriorCards.add(new Devotion());
			warriorCards.add(new EmptyBody());
			warriorCards.add(new EmptyFist());
			warriorCards.add(new EmptyMind());
			warriorCards.add(new Evaluate());
			warriorCards.add(new Fasting());
			warriorCards.add(new FearNoEvil());
			warriorCards.add(new FlurryOfBlows());
			warriorCards.add(new ForeignInfluence());
			warriorCards.add(new Halt());
			warriorCards.add(new InnerPeace());
			warriorCards.add(new JustLucky());
			warriorCards.add(new LessonLearned());
			warriorCards.add(new MasterReality());
			warriorCards.add(new Meditate());
			warriorCards.add(new MentalFortress());
			warriorCards.add(new Indignation());
			warriorCards.add(new Nirvana());
			warriorCards.add(new Omniscience());
			warriorCards.add(new Pray());
			warriorCards.add(new Prostrate());
			warriorCards.add(new Protect());
			warriorCards.add(new Ragnarok());
			warriorCards.add(new ReachHeaven());
			warriorCards.add(new Sanctity());
			warriorCards.add(new SandsOfTime());
			//magnetCards.add(new SoothingAura());
			warriorCards.add(new SpiritShield());
			warriorCards.add(new WheelKick());
			warriorCards.add(new Swivel());
			warriorCards.add(new TalkToTheHand());
			warriorCards.add(new ThirdEye());
			warriorCards.add(new Vault());
			//magnetCards.add(new Vengeance());
			warriorCards.add(new Vigilance());
			warriorCards.add(new Wallop());
			warriorCards.add(new Weave());
			warriorCards.add(new WindmillStrike());
			//magnetCards.add(new Wireheading());
			warriorCards.add(new Wish());
			warriorCards.add(new Worship());
			warriorCards.add(new WreathOfFlame());
			warriorCards.add(new BurningPact());
			warriorCards.add(new Corruption());
			warriorCards.add(new FeelNoPain());
			warriorCards.add(new SecondWind());
			warriorCards.add(new Sentinel());
			warriorCards.add(new SeverSoul());
			warriorCards.add(new DarkEmbrace());
			warriorCards.add(new Exhume());
			warriorCards.add(new Feed());
			warriorCards.add(new FiendFire());
			warriorCards.add(new Recycle());
		}
		return warriorCards;
	}
	
	public static  ArrayList<AbstractCard> basic() {
		ArrayList<AbstractCard> pool = new ArrayList<>();
		if (DuelistMod.persistentDuelistData.CardPoolSettings.getSmallBasicSet()) {
			pool.addAll(BasicPool.smallBasic("Warrior Deck"));
		} else {
			pool.addAll(BasicPool.fullBasic("Warrior Deck"));
		}
		return pool;
	}
}
