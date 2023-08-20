package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.cards.red.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.StarterDeck;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.HorusServant;
import duelistmod.cards.pools.dragons.*;
import duelistmod.cards.pools.insects.TornadoDragon;
import duelistmod.cards.pools.machine.*;
import duelistmod.cards.pools.naturia.SeismicShockwave;
import duelistmod.cards.pools.warrior.*;
import duelistmod.cards.pools.zombies.RedEyesZombie;

public class DragonPool 
{
	private static String deckName = "Dragon Deck";
	
	public static ArrayList<AbstractCard> oneRandom()
	{
        ArrayList<AbstractCard> pool = new ArrayList<>(GlobalPoolHelper.oneRandom(1, 14));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> twoRandom()
	{
        ArrayList<AbstractCard> pool = new ArrayList<>(GlobalPoolHelper.twoRandom(1, 14));
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		deck.fillPoolCards(pool);	
		return pool;
	}
	
	public static ArrayList<AbstractCard> deck()
	{
		StarterDeck dragonDeck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> dragonCards = new ArrayList<AbstractCard>();
		
		dragonCards.add(new GravityLash());
		dragonCards.add(new AmuletDragon());
		dragonCards.add(new AncientDragon());
		dragonCards.add(new AncientGearGadjiltron());
		dragonCards.add(new AncientPixieDragon());
		dragonCards.add(new AncientRules());
		dragonCards.add(new ArmageddonDragonEmp());
		dragonCards.add(new ArmedDragon3());
		dragonCards.add(new ArmedDragon5());
		dragonCards.add(new ArmedProtectorDragon());
		//dragonCards.add(new ArtifactIgnition());
		//dragonCards.add(new ArtifactSanctum());
		dragonCards.add(new AtomicScrapDragon());
		dragonCards.add(new AutorokketDragon());
		//dragonCards.add(new AxeDespair());
		dragonCards.add(new BSkullDragon());
		dragonCards.add(new BabyDragon());
		dragonCards.add(new BackgroundDragon());
		dragonCards.add(new BackupSoldier());
		dragonCards.add(new BarrelDragon());
		dragonCards.add(new BerserkScales());
		dragonCards.add(new BerserkerSoul());
		dragonCards.add(new Berserking());
		dragonCards.add(new BeserkDragon());
		dragonCards.add(new BlackBrutdrago());
		dragonCards.add(new BlacklandFireDragon());
		dragonCards.add(new BlasterDragonInfernos());
		dragonCards.add(new BlizzardDragon());
		dragonCards.add(new BlueDuston());
		dragonCards.add(new BlueEyes());
		dragonCards.add(new BlueEyesUltimate());
		dragonCards.add(new BoosterDragon());
		dragonCards.add(new BurstBreath());
		dragonCards.add(new BusterBlader());
		dragonCards.add(new BusterBladerDDS());
		dragonCards.add(new BusterDrake());
		dragonCards.add(new CastleDragonSouls());
		dragonCards.add(new CaveDragon());		
		dragonCards.add(new ChimeratechOverdragon());
		dragonCards.add(new ClawHermos());
		//dragonCards.add(new CloudianGhost());
		dragonCards.add(new CoralDragon());
		dragonCards.add(new CurseDragon());
		dragonCards.add(new CyberDragon());
		dragonCards.add(new CyberDragonCore());
		dragonCards.add(new CyberDragonDrei());
		//dragonCards.add(new CyberDragonHerz());
		dragonCards.add(new CyberDragonInfinity());
		dragonCards.add(new CyberDragonNachster());
		//dragonCards.add(new CyberDragonNova());
		dragonCards.add(new CyberDragonSieger());
		dragonCards.add(new CyberDragonVier());
		dragonCards.add(new CyberEltanin());
		dragonCards.add(new CyberEmergency());
		dragonCards.add(new CyberEndDragon());
		dragonCards.add(new CyberLaserDragon());
		//dragonCards.add(new CyberPharos());
		//dragonCards.add(new CyberPhoenix());
		//dragonCards.add(new CyberRepairPlant());
		dragonCards.add(new CyberRevsystem());
		dragonCards.add(new CyberTwinDragon());
		dragonCards.add(new CyberValley());
		//dragonCards.add(new CyberloadFusion());
		//dragonCards.add(new CyberneticOverflow());
		dragonCards.add(new CyberneticRevolution());
		dragonCards.add(new DarkHorus());
		dragonCards.add(new DarkfireDragon());
		dragonCards.add(new DarkstormDragon());
		dragonCards.add(new DefenseDraw());
		dragonCards.add(new DefenseZone());
		dragonCards.add(new DoomkaiserDragon());
		dragonCards.add(new DragonCaptureJar());
		dragonCards.add(new DragonMastery());
		dragonCards.add(new DragonMirror());
		dragonCards.add(new DragonPiper());
		dragonCards.add(new DragonRavine());
		dragonCards.add(new DragonShield());
		//dragonCards.add(new DragonShrine());
		dragonCards.add(new DragonTreasure());
		dragonCards.add(new DragonZombie());
		dragonCards.add(new DreadnoughtDreadnoid());
		dragonCards.add(new Earthquake());
		dragonCards.add(new EvilMind());
		dragonCards.add(new EvolutionBurst());
		dragonCards.add(new ExploderDragon());
		dragonCards.add(new ExploderDragonwing());
		dragonCards.add(new EyeTimaeus());
		dragonCards.add(new FangCritias());
		dragonCards.add(new FiendSkull());
		dragonCards.add(new FireDarts());
		dragonCards.add(new FiveHeaded());
		dragonCards.add(new FluteSummoning());
		dragonCards.add(new FrostflameDragon());
		dragonCards.add(new FrozenFitzgerald());
		dragonCards.add(new GaiaDragonChamp());
		dragonCards.add(new GenesisDragon());
		dragonCards.add(new GoldSarcophagus());
		dragonCards.add(new GuardragonJusticia());
		dragonCards.add(new HorusServant());
		dragonCards.add(new HymnOfLight());
		dragonCards.add(new InfernityDoomDragon());
		dragonCards.add(new IronChainDragon());
		dragonCards.add(new Lancephorhynchus());
		dragonCards.add(new LavaDragon());	
		dragonCards.add(new LesserDragon());
		dragonCards.add(new LeviaDragon());	
		//dragonCards.add(new LivingFossil());
		dragonCards.add(new LordD());
		dragonCards.add(new MaskedDragon());
		dragonCards.add(new MegafleetDragon());
		dragonCards.add(new Megalosmasher());
		dragonCards.add(new MetalDragon());
		//dragonCards.add(new MetalReflectSlime());
		dragonCards.add(new MiraculousDescent());
		dragonCards.add(new MirageDragon());
		dragonCards.add(new MoltenDestruction());
		dragonCards.add(new Mountain());
		dragonCards.add(new MythicWaterDragon());
		dragonCards.add(new OceanDragonLord());
		dragonCards.add(new OneDayPeace());
		//dragonCards.add(new PowerBond());
		dragonCards.add(new QueenDragunDjinn());
		dragonCards.add(new RedDragonArchfiend());
		dragonCards.add(new RedEyes());
		dragonCards.add(new RedEyesZombie());
		dragonCards.add(new RedHeadedOni());
		dragonCards.add(new RedRisingDragon());
		//dragonCards.add(new RisingEnergy());
		dragonCards.add(new SafeZone());
		//dragonCards.add(new Scapegoat());
		//dragonCards.add(new ScrapIronScarecrow());
		dragonCards.add(new SilverDragon());
		dragonCards.add(new SilverWing());
		dragonCards.add(new SliferSky());
		dragonCards.add(new SnowDragon());
		dragonCards.add(new SnowdustDragon());
		dragonCards.add(new SoulCharge());
		//dragonCards.add(new SphereChaos());
		dragonCards.add(new SpiralFlameStrike());
		dragonCards.add(new SpiralSpearStrike());
		dragonCards.add(new StampingDestruction());
		dragonCards.add(new StardustDragon());
		dragonCards.add(new StarlightRoad());
		dragonCards.add(new SuperStridentBlaze());

		dragonCards.add(new SurvivalFittest());
		//dragonCards.add(new SystemDown());
		dragonCards.add(new TailSwipe());
		dragonCards.add(new ThreateningRoar());
		dragonCards.add(new ThunderDragon());
		dragonCards.add(new TwinHeadedThunderDragon());
		//dragonCards.add(new TidalWaterfall());
		dragonCards.add(new TornadoDragon());
		dragonCards.add(new TotemDragon());
		dragonCards.add(new TrihornedDragon());
		dragonCards.add(new TwinBarrelDragon());
		dragonCards.add(new TwinHeadedBehemoth());
		dragonCards.add(new TwinHeadedFire());
		dragonCards.add(new TyrantWing());
		//dragonCards.add(new UltimateTyranno());
		dragonCards.add(new UltraEvolutionPill());
		dragonCards.add(new Vandalgyon());
		dragonCards.add(new VeilDarkness());
		dragonCards.add(new ViceDragon());
		dragonCards.add(new VoidOgreDragon());
		dragonCards.add(new VolcanicEruption());
		dragonCards.add(new WaterDragon());
		dragonCards.add(new WaterDragonCluster());
		dragonCards.add(new WhiteHornDragon());
		dragonCards.add(new WhiteHowling());
		dragonCards.add(new WhiteNightDragon());
		dragonCards.add(new WingedDragonRa());
		dragonCards.add(new YamataDragon());

		if (!DuelistMod.isRemoveDinosaursFromDragonPool) {
			dragonCards.add(new SuperancientDinobeast());
			dragonCards.add(new Anthrosaurus());
			dragonCards.add(new Babycerasaurus());
			dragonCards.add(new Beatraptor());
			dragonCards.add(new BlackBrachios());
			dragonCards.add(new BlackPtera());
			dragonCards.add(new BlackStego());
			dragonCards.add(new BlackTyranno());
			dragonCards.add(new BlackVeloci());
			dragonCards.add(new Carboneddon());
			dragonCards.add(new CyberDinosaur());
			dragonCards.add(new DarkDriceratops());
			dragonCards.add(new Destroyersaurus());
			dragonCards.add(new Duoterion());
			dragonCards.add(new Freezadon());
			dragonCards.add(new Frostosaurus());
			dragonCards.add(new GalaxyTyranno());
			dragonCards.add(new GiantRex());
			dragonCards.add(new HazyFlameHydra());
			dragonCards.add(new Hydrogeddon());
			dragonCards.add(new JurassicImpact());
			dragonCards.add(new Kabazauls());
			dragonCards.add(new LostWorld());
			dragonCards.add(new MadSwordBeast());
			dragonCards.add(new Pyrorex());
			dragonCards.add(new Sabersaurus());
			dragonCards.add(new SauropodBrachion());
			dragonCards.add(new Dracocension());
			dragonCards.add(new MadFlameKaiju());
			dragonCards.add(new SeismicShockwave());
		}

		if (DuelistMod.persistentDuelistData.CardPoolSettings.getBaseGameCards() && DuelistMod.isNotAllCardsPoolType())
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
		return dragonCards;
	}
	
	public static  ArrayList<AbstractCard> basic()
	{
		StarterDeck deck = DuelistMod.starterDeckNamesMap.get(deckName);
		ArrayList<AbstractCard> pool = new ArrayList<AbstractCard>();
		if (DuelistMod.smallBasicSet) { pool.addAll(BasicPool.smallBasic("Dragon Deck")); }
		else { pool.addAll(BasicPool.fullBasic("Dragon Deck")); }
		deck.fillPoolCards(pool); 
		return pool;
	}
}
