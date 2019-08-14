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
import duelistmod.cards.incomplete.*;
import duelistmod.helpers.Util;

public class DragonPool 
{
	private static String deckName = "Dragon Deck";
	
	public static ArrayList<AbstractCard> oneRandom()
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
		pools.add(NaturePool.deck());
		//pools.add(PlantPool.deck());
		//pools.add(PredaplantPool.deck());
		pools.add(SpellcasterPool.deck());
		pools.add(StandardPool.deck());
		pools.add(WarriorPool.deck());
		pools.add(ZombiePool.deck());
		if (!DuelistMod.ojamaBtnBool) { pools.add(OjamaPool.deck()); }
		if (!DuelistMod.toonBtnBool) { pools.add(ToonPool.deck()); }
		if (DuelistMod.archRoll1 == -1 || DuelistMod.archRoll2 == -1 || DuelistMod.archRoll1 > pools.size()) { DuelistMod.archRoll1 = ThreadLocalRandom.current().nextInt(pools.size()); }
		ArrayList<AbstractCard> random = pools.get(DuelistMod.archRoll1);
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(random);	
		Util.log(deckName + " was filled with random cards from the pool with index of " + DuelistMod.archRoll1);
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
		pools.add(NaturePool.deck());
		//pools.add(PlantPool.deck());
		//pools.add(PredaplantPool.deck());
		pools.add(SpellcasterPool.deck());
		pools.add(StandardPool.deck());
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
		Util.log(deckName + " was filled with random cards from the pool with index of " + DuelistMod.archRoll1 + " and " + DuelistMod.archRoll2);
		deck.fillPoolCards(random);	
		return random;
	}
	
	public static ArrayList<AbstractCard> deck()
	{
		StarterDeck dragonDeck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> dragonCards = new ArrayList<AbstractCard>();
		dragonCards.add(new AncientRules());
		dragonCards.add(new AxeDespair());
		dragonCards.add(new BabyDragon());
		dragonCards.add(new BarrelDragon());
		dragonCards.add(new BlacklandFireDragon());
		dragonCards.add(new BlizzardDragon());
		dragonCards.add(new BlueEyes());
		dragonCards.add(new BlueEyesUltimate());
		dragonCards.add(new BSkullDragon());
		dragonCards.add(new BusterBlader());
		dragonCards.add(new CaveDragon());
		dragonCards.add(new CurseDragon());
		dragonCards.add(new CyberDragon());
		dragonCards.add(new DarkfireDragon());
		//dragonCards.add(new DragonCaptureJar());
		dragonCards.add(new DragonMaster());
		dragonCards.add(new DragonPiper());
		dragonCards.add(new ExploderDragon());
		dragonCards.add(new FiendSkull());
		dragonCards.add(new FiveHeaded());
		dragonCards.add(new FluteSummoning());
		dragonCards.add(new Gandora());
		dragonCards.add(new GaiaDragonChamp());
		dragonCards.add(new GravityAxe());
		dragonCards.add(new LesserDragon());
		dragonCards.add(new LordD());
		dragonCards.add(new MetalDragon());
		dragonCards.add(new Mountain());
		dragonCards.add(new OceanDragonLord());
		dragonCards.add(new RedEyes());
		dragonCards.add(new RedEyesZombie());
		dragonCards.add(new Reinforcements());
		dragonCards.add(new SangaThunder());
		dragonCards.add(new SliferSky());
		dragonCards.add(new SnowDragon());
		dragonCards.add(new SnowdustDragon());
		dragonCards.add(new SwordDeepSeated());
		dragonCards.add(new ThunderDragon());
		dragonCards.add(new TrihornedDragon());
		dragonCards.add(new TwinBarrelDragon());
		dragonCards.add(new TyrantWing());
		dragonCards.add(new WhiteHornDragon());
		dragonCards.add(new WhiteNightDragon());
		dragonCards.add(new WingedDragonRa());
		dragonCards.add(new YamataDragon());
		dragonCards.add(new TwinHeadedFire());
		dragonCards.add(new DarkstormDragon());
		dragonCards.add(new AncientGearGadjiltron());
		dragonCards.add(new CheerfulCoffin());
		dragonCards.add(new SuperancientDinobeast());
		dragonCards.add(new ArmageddonDragonEmp());
		dragonCards.add(new BackgroundDragon());
		dragonCards.add(new BoosterDragon());
		dragonCards.add(new Carboneddon());
		dragonCards.add(new BusterBladerDDS());
		dragonCards.add(new SilverDragon());
		dragonCards.add(new AmuletDragon());
		dragonCards.add(new EyeTimaeus());
		dragonCards.add(new DragonShield());
		dragonCards.add(new BeserkDragon());		
		dragonCards.add(new DoomkaiserDragon());		
		dragonCards.add(new RedHeadedOni());	
		dragonCards.add(new BlueBloodedOni());	
		dragonCards.add(new DragonZombie());
		dragonCards.add(new YellowBelliedOni());
		dragonCards.add(new LavaDragon());		
		dragonCards.add(new DarkSimorgh());
		dragonCards.add(new MaskedDragon());
		dragonCards.add(new MirageDragon());
		dragonCards.add(new StarBlast());
		dragonCards.add(new StardustDragon());
		dragonCards.add(new TwinHeadedBehemoth());
		dragonCards.add(new ViceDragon());
		dragonCards.add(new HorusServant());
		dragonCards.add(new QueenDragunDjinn());
		dragonCards.add(new RainbowDragon());
		dragonCards.add(new MaleficRainbowDragon());
		dragonCards.add(new RainbowDarkDragon());
		dragonCards.add(new RedRisingDragon());
		dragonCards.add(new BusterDrake());
		dragonCards.add(new BerserkerCrush());
		dragonCards.add(new FeatherShot());
		dragonCards.add(new ExploderDragonwing());
		dragonCards.add(new BlastHeldTribute());
		dragonCards.add(new BlastWithChain());
		dragonCards.add(new OrbitalBombardment());
		dragonCards.add(new BlasterDragonInfernos());
		dragonCards.add(new ClearWingDragon());
		dragonCards.add(new CrystalWingDragon());		
		dragonCards.add(new BlackRoseDragon());
		dragonCards.add(new BlackRoseMoonlight());
		dragonCards.add(new RedRoseDragon());
		dragonCards.add(new BlueRoseDragon());
		dragonCards.add(new WhiteRoseDragon());

		if (!DuelistMod.toonBtnBool)
		{
			dragonCards.add(new ToonWorld());
			dragonCards.add(new ToonKingdom());
			dragonCards.add(new ToonBarrelDragon());
			//dragonCards.add(new ToonCyberDragon());
			dragonCards.add(new RedEyesToon());
			dragonCards.add(new BlueEyesToon());
			dragonCards.add(new MangaRyuRan());
		}
		
		if (DuelistMod.baseGameCards && DuelistMod.setIndex != 9)
		{
			dragonCards.add(new Reaper());
			dragonCards.add(new Havoc());
			dragonCards.add(new Shockwave());
			dragonCards.add(new Uppercut());
			dragonCards.add(new Bludgeon());
			dragonCards.add(new Pummel());
			dragonCards.add(new SpotWeakness());
			dragonCards.add(new InfernalBlade());
			dragonCards.add(new DemonForm());
			dragonCards.add(new Carnage());
			dragonCards.add(new Disarm());
			dragonCards.add(new Clothesline());
			dragonCards.add(new DualWield());
			dragonCards.add(new BodySlam());
			dragonCards.add(new Intimidate());
			dragonCards.add(new Rampage());
			dragonCards.add(new Whirlwind());
			dragonCards.add(new Dropkick());
			dragonCards.add(new Immolate());
			dragonCards.add(new PommelStrike());
			dragonCards.add(new SwordBoomerang());
			dragonCards.add(new RecklessCharge());
			dragonCards.add(new HeavyBlade());
			dragonCards.add(new Inflame());
			dragonCards.add(new DoubleTap());
			dragonCards.add(new LimitBreak());
			dragonCards.add(new SeverSoul());
			dragonCards.add(new TwinStrike());
			dragonCards.add(new FireBreathing());
			dragonCards.add(new SearingBlow());
			dragonCards.add(new ThunderClap());
			dragonCards.add(new Anger());
			dragonCards.add(new Cleave());
			dragonCards.add(new Hemokinesis());
			dragonCards.add(new WildStrike());
			dragonCards.add(new Rupture());
			dragonCards.add(new Clash());	
			dragonCards.add(new PiercingWail());	
			dragonCards.add(new DieDieDie());	
			dragonCards.add(new Malaise());	
			dragonCards.add(new Finisher());	
			dragonCards.add(new Burst());	
			dragonCards.add(new Predator());	
			dragonCards.add(new Terror());	
			dragonCards.add(new SuckerPunch());	
			dragonCards.add(new Backstab());	
			dragonCards.add(new DaggerThrow());	
			dragonCards.add(new DaggerSpray());	
			dragonCards.add(new MasterfulStab());	
			dragonCards.add(new QuickSlash());	
			dragonCards.add(new RiddleWithHoles());	
			dragonCards.add(new Choke());	
			dragonCards.add(new GlassKnife());	
			dragonCards.add(new FlyingKnee());	
			dragonCards.add(new HeelHook());	
			dragonCards.add(new AllOutAttack());	
			dragonCards.add(new PhantasmalKiller());	
			dragonCards.add(new Unload());	
			dragonCards.add(new Claw());	
			dragonCards.add(new Sunder());
			dragonCards.add(new Hyperbeam());
		}
		
		dragonDeck.fillPoolCards(dragonCards); 		
		dragonDeck.fillArchetypeCards(dragonCards);
		DuelistMod.archetypeCards.addAll(dragonCards);
		return dragonCards;
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
