package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.BeamCell;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.DuelistMod;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.incomplete.KaiserSeaHorse;
import duelistmod.cards.pools.aqua.*;
import duelistmod.cards.pools.dragons.*;
import duelistmod.cards.pools.insects.*;
import duelistmod.cards.pools.machine.*;
import duelistmod.cards.pools.naturia.*;
import duelistmod.cards.pools.warrior.ElectromagneticShield;
import duelistmod.cards.pools.zombies.*;

public class BasicPool 
{

	public static ArrayList<AbstractCard> pharaohBasics()
	{
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		
		return toReturn;
	}
	
	public static ArrayList<AbstractCard> ascendedBasics()
	{
		ArrayList<AbstractCard> toReturn = new ArrayList<AbstractCard>();
		toReturn.add(new FlyingSaucer());
		toReturn.add(new DarkMirrorForce());
		toReturn.add(new Fissure());
		toReturn.add(new FlameSwordsman());
		toReturn.add(new FortressWarrior());
		toReturn.add(new GaiaFierce());
		toReturn.add(new GoldenApples());
		toReturn.add(new HammerShot());
		toReturn.add(new HaneHane());
		toReturn.add(new Hinotama());
		toReturn.add(new ImperialOrder());
		toReturn.add(new JudgeMan());
		toReturn.add(new LabyrinthWall());
		toReturn.add(new MillenniumShield());
		toReturn.add(new ObeliskTormentor());
		toReturn.add(new FeatherPho());
		toReturn.add(new PotDichotomy());
		toReturn.add(new PotGenerosity());
		toReturn.add(new PotGreed());
		toReturn.add(new PreventRat());
		toReturn.add(new RadiantMirrorForce());
		toReturn.add(new Raigeki());
		toReturn.add(new Sangan());
		toReturn.add(new ScrapFactory());
		toReturn.add(new ShardGreed());
		toReturn.add(new SmashingGround());
		toReturn.add(new SphereKuriboh());
		toReturn.add(new StormingMirrorForce());
		toReturn.add(new Terraforming());
		toReturn.add(new BigFire());
		toReturn.add(new UltimateOffering());
		toReturn.add(new ValkMagnet());
		toReturn.add(new AlphaMagnet());
		toReturn.add(new BetaMagnet());
		toReturn.add(new GammaMagnet());
		toReturn.add(new Mathematician());
		toReturn.add(new BattleOx());
		toReturn.add(new FluteKuribohBasic());
		toReturn.add(new WingedKuriboh());
		toReturn.add(new CastleDarkIllusions());
		toReturn.add(new ChangeHeart());
		toReturn.add(new JamBreeding());
		toReturn.add(new BigKoala());
		toReturn.add(new BattleguardKing());		
		toReturn.add(new BattleFootballer());
		toReturn.add(new EvilswarmHeliotrope());
		toReturn.add(new WormApocalypse());
		toReturn.add(new WormBarses());
		toReturn.add(new WormKing());
		toReturn.add(new GauntletWarrior());		
		toReturn.add(new GaiaMidnight());		
		toReturn.add(new GilfordLegend());
		toReturn.add(new ReinforcementsArmy());
		toReturn.add(new BlockGolem());
		toReturn.add(new GiantSoldierSteel());
		toReturn.add(new WingedKuriboh9());
		toReturn.add(new WingedKuriboh10());
		toReturn.add(new Kuribohrn());
		toReturn.add(new PowerGiant());
		toReturn.add(new RainbowBridge());
		toReturn.add(new EarthquakeGiant());
		toReturn.add(new RainbowKuriboh());
		toReturn.add(new ClearKuriboh());
		toReturn.add(new BigEye());
		toReturn.add(new YamiForm());
		toReturn.add(new RainbowOverdragon());
		toReturn.add(new SilverApples());
		toReturn.add(new DarklordSuperbia());	
		toReturn.add(new LightningVortex());
		toReturn.add(new BlackPendant());	
		toReturn.add(new LightningRodLord());		
		toReturn.add(new MudGolem());	
		toReturn.add(new Mudora());	
		toReturn.add(new MudragonSwamp());	
		toReturn.add(new FlameTiger());
		toReturn.add(new ForbiddenLance());
		toReturn.add(new FutureFusion());
		toReturn.add(new ElectromagneticShield());
		toReturn.add(new Electrowhip());
		toReturn.add(new FuryFire());
		toReturn.add(new CharcoalInpachi());
		toReturn.add(new MagicCylinder());
		toReturn.add(new RockSunrise());
		toReturn.add(new UltraPolymerization());
		toReturn.add(new MillenniumScorpion());
		toReturn.add(new Metronome());
		toReturn.add(new OrbMetronome());
		toReturn.add(new Jinzo());
		toReturn.add(new OneForOne());
		toReturn.add(new DigitalBug());
		toReturn.add(new Solidarity());
		toReturn.add(new RedHeadedOni());
		toReturn.add(new BlueBloodedOni());
		toReturn.add(new GreenGraveOni());
		toReturn.add(new PurplePainOni());
		
		// megatype pool cards
		toReturn.add(new KamionTimelord());
		toReturn.add(new RainbowRefraction());
		toReturn.add(new CrystalRaigeki());
		toReturn.add(new RainbowRuins());
		//toReturn.add(new HourglassLife());
		toReturn.add(new Eva());
		toReturn.add(new HappyLover());
		toReturn.add(new DunamesDarkWitch());
		toReturn.add(new RainbowNeos());
		toReturn.add(new RainbowFlower());
		//DuelistMod.basicCards = new ArrayList<AbstractCard>();		
		//DuelistMod.basicCards.addAll(toReturn);
		return toReturn;
	}
	
	public static ArrayList<AbstractCard> fullBasic(String deckName)
	{
		ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
		if (deckName.equals("Dragon Deck")) { cards.addAll(dragonBasics(true)); }
		else if (deckName.equals("Naturia Deck")) { cards.addAll(naturiaBasics(true)); }
		else if (deckName.equals("Insect Deck")) { cards.addAll(insectBasics(true)); }
		else if (deckName.equals("Machine Deck")) { cards.addAll(machineBasics(true)); }
		else if (deckName.equals("Aqua Deck")) { cards.addAll(aquaBasics(true)); }
		else if (deckName.equals("Zombie Deck")) { cards.addAll(zombBasics(true)); }
		else
		{
			cards.add(new AttackTheMoon());
			cards.add(new BattleFootballer());
			cards.add(new BattleOx());
			cards.add(new BattleguardKing());		
			cards.add(new BigEye());	
			cards.add(new BigFire());
			cards.add(new BigKoala());
			cards.add(new BlackPendant());	
			cards.add(new BlockGolem());
			cards.add(new Blockman());
			cards.add(new BlueBloodedOni());
			cards.add(new CastleDarkIllusions());
			cards.add(new CatapultZone());
			cards.add(new ChangeHeart());
			cards.add(new ClearKuriboh());
			cards.add(new Cloning());		
			cards.add(new DarkFactory());
			cards.add(new DarkHole());
			cards.add(new DarkMirrorForce());
			cards.add(new DarklordSuperbia());	
			cards.add(new DestroyerGolem());
			cards.add(new DokiDoki());
			cards.add(new DropOff());
			cards.add(new DummyGolem());
			cards.add(new EarthEffigy());
			cards.add(new EarthGiant());
			cards.add(new EarthquakeGiant());
			cards.add(new ElectromagneticShield());
			cards.add(new Electrowhip());
			cards.add(new EvilswarmHeliotrope());
			cards.add(new FeatherPho());
			cards.add(new Fissure());
			cards.add(new FlameTiger());
			cards.add(new FluteKuribohBasic());
			cards.add(new ForbiddenLance());
			cards.add(new FuryFire());
			cards.add(new FutureFusion());
			cards.add(new GaiaMidnight());		
			cards.add(new GauntletWarrior());		
			cards.add(new GemArmadillo());
			cards.add(new GemElephant());
			cards.add(new GiantSoldierSteel());
			cards.add(new GiantTrapHole());
			cards.add(new GilfordLegend());
			cards.add(new GoldenApples());
			cards.add(new GolemSentry());
			cards.add(new GraniteLoyalist());		
			cards.add(new GreenGraveOni());
			cards.add(new GreyGreedOni());
			cards.add(new HammerShot());
			cards.add(new HaneHane());
			cards.add(new HarpieFeather());
			cards.add(new HeavyStorm());
			cards.add(new Hinotama());
			cards.add(new ImperialOrder());
			cards.add(new JamBreeding());
			cards.add(new Jinzo());
			cards.add(new Kuriboh());
			cards.add(new Kuribohrn());
			cards.add(new LabyrinthWall());
			cards.add(new LightningDarts());
			cards.add(new LightningRodLord());		
			cards.add(new LightningVortex());
			cards.add(new MagicCylinder());
			cards.add(new MagicHoleGolem());
			cards.add(new MagicalStone());
			cards.add(new Mathematician());
			cards.add(new Mausoleum());
			cards.add(new Metronome());
			cards.add(new MillenniumGolem());
			cards.add(new MillenniumScorpion());
			cards.add(new MillenniumShield());
			cards.add(new MirrorForce());
			cards.add(new MudGolem());	
			cards.add(new Mudora());	
			cards.add(new MudragonSwamp());	
			cards.add(new ObeliskTormentor());
			cards.add(new OneForOne());
			cards.add(new OrbMetronome());
			cards.add(new Polymerization());
			cards.add(new PotAvarice());
			cards.add(new PotDichotomy());
			cards.add(new PotForbidden());
			cards.add(new PotGenerosity());
			cards.add(new PotGreed());
			cards.add(new PowerGiant());
			cards.add(new PowerWall());
			cards.add(new PreventRat());
			cards.add(new PurplePainOni());
			cards.add(new RadiantMirrorForce());
			cards.add(new Raigeki());
			cards.add(new RainbowBridge());
			cards.add(new RainbowGravity());
			cards.add(new RainbowJar());
			cards.add(new RainbowKuriboh());
			cards.add(new RainbowLife());
			cards.add(new RainbowOverdragon());
			cards.add(new RedHeadedOni());
			cards.add(new ReinforcementsArmy());
			cards.add(new ReleaseFromStone());
			cards.add(new RockSunrise());
			cards.add(new Sangan());
			cards.add(new ScrapFactory());
			cards.add(new ShardGreed());
			cards.add(new SilverApples());
			cards.add(new SlotMachine());
			cards.add(new SmashingGround());
			cards.add(new SolemnStrike());
			cards.add(new Solidarity());
			cards.add(new SphereKuriboh());
			cards.add(new StormingMirrorForce());
			cards.add(new StrayLambs());
			cards.add(new Terraforming());
			cards.add(new TimeSeal());
			cards.add(new UltimateOffering());
			cards.add(new UltraPolymerization());
			cards.add(new WeepingIdol());
			cards.add(new WingedKuriboh());
			cards.add(new WingedKuriboh10());
			cards.add(new WingedKuriboh9());
			cards.add(new Wiretap());
			cards.add(new WormApocalypse());
			cards.add(new WormBarses());
			cards.add(new WormKing());
			cards.add(new WormWarlord());
			cards.add(new YamiForm());
			cards.add(new YellowBelliedOni());		

			// Creators
			if (!DuelistMod.creatorBtnBool) { cards.add(new TheCreator()); cards.add(new DarkCreator()); }
			
			// Ascension-locked cards		
			if (AbstractDungeon.ascensionLevel < 10)
			{
				cards.add(new DestructPotion());
				cards.add(new Wiseman());			
			} 
			
			if (AbstractDungeon.ascensionLevel < 15)
			{
				cards.add(new DianKeto());
				cards.add(new RedMedicine());
				cards.add(new Reload());
				cards.add(new ForbiddenChalice());
			}
			// END Ascension-locked cards
		}
		
		if (DuelistMod.debug)
		{
			DuelistMod.logger.info("Printing all basic cards!!");
			int counter = 1;
			for (AbstractCard c : cards)
			{
				DuelistMod.logger.info("[" + counter + "]: " + c.name);
				counter++;
			}
		}
		
		//DuelistMod.archetypeCards.addAll(cards);
		return cards;
	}

	public static ArrayList<AbstractCard> smallBasic(String deckName)
	{
		ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
		if (deckName.equals("Dragon Deck")) { cards.addAll(dragonBasics(false)); }
		else if (deckName.equals("Naturia Deck")) { cards.addAll(naturiaBasics(false)); }
		else if (deckName.equals("Insect Deck")) { cards.addAll(insectBasics(false)); }
		else if (deckName.equals("Machine Deck")) { cards.addAll(machineBasics(false)); }
		else if (deckName.equals("Aqua Deck")) { cards.addAll(aquaBasics(false)); }
		else if (deckName.equals("Zombie Deck")) { cards.addAll(zombBasics(false)); }
		else
		{
			//cards.add(new CastleWalls());
			//cards.add(new CelticGuardian());
			//cards.add(new Cloning());		
			//cards.add(new DarkFactory());
			//cards.add(new DarkHole());
			//cards.add(new DarkMirrorForce());
			//cards.add(new Fissure());
			//cards.add(new FlameSwordsman());
			cards.add(new FortressWarrior());
			cards.add(new GaiaFierce());
			cards.add(new GoldenApples());
			//cards.add(new HammerShot());
			cards.add(new HaneHane());
			//cards.add(new HarpieFeather());
			//cards.add(new HeavyStorm());
			//cards.add(new Hinotama());
			cards.add(new ImperialOrder());
			//cards.add(new JudgeMan());
			//cards.add(new Kuriboh());
			cards.add(new LabyrinthWall());
			cards.add(new Mausoleum());
			//cards.add(new MirrorForce());
			//cards.add(new MillenniumShield());
			cards.add(new ObeliskTormentor());
			//cards.add(new FeatherPho());
			//cards.add(new PotAvarice());
			//cards.add(new PotForbidden());
			//cards.add(new PotDichotomy());
			cards.add(new PotGenerosity());
			cards.add(new PotGreed());
			cards.add(new PreventRat());
			//cards.add(new RadiantMirrorForce());
			cards.add(new Raigeki());
			//cards.add(new Sangan());
			//cards.add(new Scapegoat());
			cards.add(new ScrapFactory());
			cards.add(new ShardGreed());
			cards.add(new SmashingGround());
			//cards.add(new SphereKuriboh());
			//cards.add(new StormingMirrorForce());
			//cards.add(new StrayLambs());
			//cards.add(new Terraforming());
			//cards.add(new BigFire());
			//cards.add(new UltimateOffering());
			cards.add(new Wiretap());
			//cards.add(new Mathematician());
			//cards.add(new BattleOx());
			cards.add(new FluteKuribohBasic());
			cards.add(new WingedKuriboh());
			//cards.add(new CastleDarkIllusions());
			cards.add(new ChangeHeart());
			//cards.add(new JamBreeding());
			cards.add(new Polymerization());
			cards.add(new BigKoala());
			//cards.add(new BattleguardKing());		
			cards.add(new BattleFootballer());
			//cards.add(new EarthquakeGiant());
			//cards.add(new EvilswarmHeliotrope());
			//cards.add(new WormApocalypse());
			//cards.add(new WormBarses());
			//cards.add(new WormWarlord());
			//cards.add(new WormKing());
			//cards.add(new GauntletWarrior());		
			//cards.add(new GaiaMidnight());		
			//cards.add(new GilfordLegend());
			cards.add(new ReinforcementsArmy());
			//cards.add(new BlockGolem());
			//cards.add(new DokiDoki());
			//cards.add(new GiantSoldierSteel());
			//cards.add(new RainbowJar());
			cards.add(new WingedKuriboh9());
			//cards.add(new WingedKuriboh10());
			//cards.add(new MagicalStone());
			//cards.add(new Kuribohrn());
			//cards.add(new DropOff());
			cards.add(new PowerGiant());
			cards.add(new RainbowBridge());
			cards.add(new EarthGiant());
			//cards.add(new RainbowKuriboh());
			//cards.add(new ClearKuriboh());
			//cards.add(new Linkuriboh());
			//cards.add(new GiantTrapHole());
			//cards.add(new BigEye());
			cards.add(new YamiForm());
			cards.add(new RainbowOverdragon());
			//cards.add(new RainbowGravity());
			//cards.add(new RainbowLife());
			cards.add(new SilverApples());
			//cards.add(new DarklordSuperbia());	
			cards.add(new LightningVortex());
			//cards.add(new BlackPendant());	
			//cards.add(new LightningRodLord());	
			cards.add(new ForbiddenLance());
			//cards.add(new FutureFusion());
			//cards.add(new ElectromagneticShield());
			//cards.add(new Electrowhip());
			//cards.add(new FuryFire());
			//cards.add(new MudGolem());	
			//cards.add(new Mudora());	
			//cards.add(new MudragonSwamp());	
			//cards.add(new FlameTiger());
			//cards.add(new RockSunrise());
			//cards.add(new UltraPolymerization());
			cards.add(new MillenniumScorpion());
			//cards.add(new Metronome());
			cards.add(new OneForOne());
			//cards.add(new OrbMetronome());
			cards.add(new Solidarity());
			//cards.add(new Overworked());
			cards.add(new Jinzo());
			cards.add(new GolemSentry());
			cards.add(new Blockman());
			//cards.add(new AttackTheMoon());
			cards.add(new EarthEffigy());
			//cards.add(new DestroyerGolem());
			//cards.add(new DummyGolem());
			cards.add(new GemElephant());
			cards.add(new GemArmadillo());
			
			// Creators
			if (!DuelistMod.creatorBtnBool) { cards.add(new TheCreator()); cards.add(new DarkCreator()); }
			
			// Ascension-locked cards		
			if (AbstractDungeon.ascensionLevel < 10)
			{
				cards.add(new DestructPotion());
				cards.add(new Wiseman());
			} 
			
			if (AbstractDungeon.ascensionLevel < 15)
			{
				cards.add(new DianKeto());
				cards.add(new RedMedicine());
				cards.add(new Reload());
				cards.add(new ForbiddenChalice());
			}
			// END Ascension-locked cards
		}
		
		if (DuelistMod.debug)
		{
			DuelistMod.logger.info("Printing all basic cards!! (reduced basic set)");
			int counter = 1;
			for (AbstractCard c : cards)
			{
				DuelistMod.logger.info("[" + counter + "]: " + c.name);
				counter++;
			}
		}
		
		//DuelistMod.archetypeCards.addAll(cards);
		return cards;
	}
	
	public static ArrayList<AbstractCard> zombBasics(boolean full)
	{
		ArrayList<AbstractCard> cards = new ArrayList<>();
		if (full)
		{
			cards.add(new BigEye());
			cards.add(new BlackPendant());
			cards.add(new BlueBloodedOni());
			cards.add(new ChangeHeart());
			cards.add(new DarkAssailant());
			cards.add(new DarkHole());
			cards.add(new DarkMirrorForce());	
			cards.add(new EarthGiant());
			cards.add(new EnmaJudgement());
			cards.add(new FluteKuribohBasic());
			cards.add(new ForbiddenLance());
			cards.add(new FuryFire());
			cards.add(new GhostrickAngel());
			cards.add(new GhostrickDoll());		
			cards.add(new GhostrickDullahan());
			cards.add(new GhostrickFairy());
			cards.add(new GhostrickGoRound());
			cards.add(new GhostrickLantern());
			cards.add(new GhostrickMary());
			cards.add(new GhostrickNekomusume());
			cards.add(new GhostrickSocuteboss());
			cards.add(new GhostrickWitch());
			cards.add(new GhostrickYukiOnna());
			cards.add(new GigastoneOmega());
			cards.add(new GlowUpBloom());
			cards.add(new GreenGraveOni());
			cards.add(new GreyGreedOni());
			cards.add(new ImperialTomb());
			cards.add(new MagicalGhost());			
			cards.add(new MonsterEgg());
			cards.add(new OneForOne());			
			cards.add(new PlaguespreaderZombie());			
			cards.add(new PotGenerosity());
			cards.add(new PotGreed());
			cards.add(new PurplePainOni());
			cards.add(new RedHeadedOni());
			cards.add(new Relinkuriboh());
			cards.add(new SeaMonsterTheseus());	
			cards.add(new TrapHole());
			cards.add(new YamiForm());	
		}

		cards.add(new BaconSaver());
		cards.add(new BeastPharaoh());
		cards.add(new BloodSucker());
		cards.add(new BookTaiyou());
		cards.add(new BurningSkullHead());
		cards.add(new CallGrave());
		cards.add(new CallHaunted());
		cards.add(new CalledByGrave());
		cards.add(new CorrodingShark());
		cards.add(new FinalFusion());
		cards.add(new FlameGhost());
		cards.add(new FlashFusion());
		cards.add(new FossilDragon());		
		cards.add(new FossilKnight());
		cards.add(new FossilSkullConvoy());
		cards.add(new FossilSkullbuggy());
		cards.add(new FusionDevourer());
		cards.add(new FusionFire());
		cards.add(new FutureFusion());			
		cards.add(new GatesDarkWorld());
		cards.add(new HardSellinZombie());
		cards.add(new HumptyGrumpty());
		cards.add(new MagicalizedFusion());
		cards.add(new MayakashiMetamorphosis());
		cards.add(new MiracleFusion());
		cards.add(new PyramidLight());
		cards.add(new RebornZombie());
		cards.add(new ReturnToDoomed());
		cards.add(new ShiranuiSamurai());
		cards.add(new ShiranuiSamuraisaga());
		cards.add(new ShiranuiShogunsaga());
		cards.add(new ShiranuiSkillsagaSupremacy());
		cards.add(new ShiranuiSmith());
		cards.add(new ShiranuiSolitaire());
		cards.add(new ShiranuiSpectralsword());
		cards.add(new ShiranuiSpectralswordShade());
		cards.add(new ShiranuiSpiritmaster());
		cards.add(new ShiranuiSquire());
		cards.add(new ShiranuiSquiresaga());
		cards.add(new ShiranuiSunsaga());
		cards.add(new ShiranuiSwordmaster());
		cards.add(new ShiranuiSwordsaga());
		cards.add(new SkullFlame());
		cards.add(new Skullgios());
		cards.add(new SupersonicSkullFlame());
		cards.add(new SynchroFusionist());
		cards.add(new TimeFusion());
		cards.add(new TutanMask());
		cards.add(new YellowBelliedOni());
		cards.add(new Zombina());
		
		// Base Game Cards
		if (DuelistMod.baseGameCards && DuelistMod.setIndex != 9)
		{
			cards.add(new Footwork()); 			 
			cards.add(new CorpseExplosion()); 
			cards.add(new Malaise()); 
			cards.add(new BeamCell()); 
			cards.add(new WellLaidPlans()); 
		}

		// Creators
		if (!DuelistMod.creatorBtnBool) { cards.add(new TheCreator()); cards.add(new DarkCreator()); }
		
		// Ascension-locked cards		
		if (AbstractDungeon.ascensionLevel < 10)
		{
			cards.add(new DestructPotion());
			cards.add(new Wiseman());			
		} 
		
		if (AbstractDungeon.ascensionLevel < 15)
		{
			cards.add(new RedMedicine());
		}
		
		return cards;
	}
	
	public static ArrayList<AbstractCard> aquaBasics(boolean full)
	{
		ArrayList<AbstractCard> cards = new ArrayList<>();
		if (full)
		{
			cards.add(new FluteKuribohBasic());
			cards.add(new LightningVortex());
			cards.add(new MadLobster());	
			cards.add(new MillenniumShield());
			cards.add(new ObeliskTormentor());
			cards.add(new OneForOne());
			cards.add(new PotDichotomy());
			cards.add(new PotGreed());
			cards.add(new PreventRat());
			cards.add(new ReinforcementsArmy());
		}
	
		cards.add(new Ameba());
		cards.add(new AquamirrorCycle());
		cards.add(new BarrierStatue());
		cards.add(new BattleOx());		
		cards.add(new BigEye());			
		cards.add(new BlizzardDefender());	
		cards.add(new BlueBloodedOni());
		cards.add(new ChangeHeart());
		cards.add(new ChrysalisDolphin());
		cards.add(new ColdFeet());
		cards.add(new CraniumFish());	
		cards.add(new DeepDiver());	
		cards.add(new DupeFrog());
		cards.add(new ElementalBurst());	
		cards.add(new FeatherPho());
		cards.add(new Fissure());		
		cards.add(new FreezingBeast());
		cards.add(new FutureFusion());	
		cards.add(new GeneralGantal());	
		cards.add(new GilfordLegend());
		cards.add(new GishkiAquamirror());
		cards.add(new GishkiMarker());
		cards.add(new GishkiNoellia());
		cards.add(new GoldenApples());
		cards.add(new GraydleImpact());
		cards.add(new HammerShot());
		cards.add(new ImperialOrder());
		cards.add(new JamBreeding());	
		cards.add(new KaiserSeaHorse());
		cards.add(new LegendaryFisherman());
		cards.add(new Polymerization());
		cards.add(new PoseidonWave());	
		cards.add(new PotAvarice());		
		cards.add(new Salvage());		
		cards.add(new SangaWater());	
		cards.add(new ShardGreed());
		cards.add(new SmashingGround());
		cards.add(new SolemnStrike());
		cards.add(new SphereKuriboh());
		cards.add(new YamiForm());
		
		// Creators
		if (!DuelistMod.creatorBtnBool) { cards.add(new TheCreator()); cards.add(new DarkCreator()); }
		
		// Ascension-locked cards		
		if (AbstractDungeon.ascensionLevel < 10)
		{
			cards.add(new DestructPotion());
			cards.add(new Wiseman());			
		} 
		
		if (AbstractDungeon.ascensionLevel < 15)
		{
			cards.add(new DianKeto());
			cards.add(new RedMedicine());
			cards.add(new Reload());
			cards.add(new ForbiddenChalice());
		}
		
		return cards;
	}
	
	public static ArrayList<AbstractCard> dragonBasics(boolean full)
	{
		ArrayList<AbstractCard> cards = new ArrayList<>();
		if (full)
		{
			cards.add(new BattleguardKing());	
			cards.add(new BigFire());
			cards.add(new HaneHane());
			cards.add(new Hinotama());
			cards.add(new MagicalStone());
			cards.add(new OrbMetronome());
			cards.add(new PotGreed());
			cards.add(new RainbowBridge());
			cards.add(new StrayLambs());
			cards.add(new Terraforming());
			cards.add(new UltimateOffering());
		}
		cards.add(new BigEye());	
		cards.add(new BigKoala());
		cards.add(new BlackPendant());	
		cards.add(new BlockGolem());
		cards.add(new BlueBloodedOni());
		cards.add(new DarkFactory());
		cards.add(new FeatherPho());
		cards.add(new ForbiddenLance());
		cards.add(new GoldenApples());
		cards.add(new GreenGraveOni());
		cards.add(new GreyGreedOni());		
		cards.add(new HarpieFeather());		
		cards.add(new ImperialOrder());
		cards.add(new JamBreeding());
		cards.add(new Jinzo());
		cards.add(new LabyrinthWall());
		cards.add(new LightningDarts());		
		cards.add(new Mausoleum());
		cards.add(new ObeliskTormentor());		
		cards.add(new PotGenerosity());		
		cards.add(new PreventRat());
		cards.add(new PurplePainOni());
		cards.add(new Raigeki());		
		cards.add(new RainbowOverdragon());
		cards.add(new ReinforcementsArmy());
		cards.add(new ScrapFactory());
		cards.add(new ShardGreed());
		cards.add(new SlotMachine());
		cards.add(new SmashingGround());
		cards.add(new Solidarity());		
		cards.add(new YamiForm());
		cards.add(new YellowBelliedOni());
		
		// Creators
		if (!DuelistMod.creatorBtnBool) { cards.add(new TheCreator()); cards.add(new DarkCreator()); }
		
		// Ascension-locked cards		
		if (AbstractDungeon.ascensionLevel < 10)
		{
			cards.add(new DestructPotion());
			cards.add(new Wiseman());			
		} 
		
		if (AbstractDungeon.ascensionLevel < 15)
		{
			cards.add(new DianKeto());
			cards.add(new RedMedicine());
			cards.add(new Reload());
			cards.add(new ForbiddenChalice());
		}
		
		return cards;
	}
	
	public static ArrayList<AbstractCard> machineBasics(boolean full)
	{
		ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
		if (DuelistMod.quicktimeEventsAllowed) { cards.add(new MachinaCannon()); }
		else { cards.add(new FlyingSaucer()); }
		if (full)
		{
			cards.add(new BigEye());	
			cards.add(new BigFire());
			cards.add(new BlockGolem());
			cards.add(new FutureFusion());
			cards.add(new GemElephant());
			cards.add(new Hinotama());
			cards.add(new Kuribohrn());
			cards.add(new LightningDarts());
			cards.add(new LightningVortex());
			cards.add(new MagicalStone());
			cards.add(new PerfectMachineKing());
			cards.add(new Sangan());
			cards.add(new Solidarity());
			cards.add(new Terraforming());
			cards.add(new UltimateOffering());
		}
		cards.add(new BattleFootballer());
		cards.add(new BlackPendant());	
		cards.add(new BlastingRuins());	
		cards.add(new ChangeHeart());			
		cards.add(new DreadnoughtDreadnoid());
		cards.add(new FluteKuribohBasic());		
		cards.add(new GoldenApples());		
		cards.add(new GracefulCharity());
		cards.add(new IronCall());
		cards.add(new Kuriboh());		
		cards.add(new LabyrinthWall());		
		cards.add(new Linkuriboh());		
		cards.add(new LostGuardian());
		cards.add(new MegafleetDragon());
		cards.add(new MillenniumShield());
		cards.add(new Polymerization());
		cards.add(new JunkSpeeder());
		cards.add(new PotGenerosity());
		cards.add(new PotGreed());
		cards.add(new PowerGiant());
		cards.add(new PowerWall());
		cards.add(new Raigeki());		
		cards.add(new Reinforcements());
		cards.add(new QuickCharger());
		cards.add(new ScrapFactory());
		cards.add(new SeismicShockwave());
		cards.add(new ShardGreed());	
		cards.add(new StrayLambs());
		cards.add(new SystemDown());		
		cards.add(new TimeSeal());
		cards.add(new WingedKuriboh());
		cards.add(new Wiretap());
		cards.add(new WormWarlord());
		cards.add(new YamiForm());
		
		// Creators
		if (!DuelistMod.creatorBtnBool) { cards.add(new TheCreator()); cards.add(new DarkCreator()); }
		
		// Ascension-locked cards		
		if (AbstractDungeon.ascensionLevel < 10)
		{
			cards.add(new DestructPotion());
			cards.add(new Wiseman());			
		} 
		
		if (AbstractDungeon.ascensionLevel < 15)
		{
			cards.add(new DianKeto());
			cards.add(new RedMedicine());
			cards.add(new Reload());
			cards.add(new ForbiddenChalice());
		}
		
		return cards;
	}
	
	public static ArrayList<AbstractCard> naturiaBasics(boolean full)
	{
		ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
		if (full)
		{
			cards.add(new BigFire());
			cards.add(new DarkHole());
			cards.add(new HaneHane());
			cards.add(new MagicCylinder());
			cards.add(new PotDichotomy());
			cards.add(new PotForbidden());
			cards.add(new PowerWall());
			cards.add(new Sangan());
		}
		cards.add(new AttackTheMoon());
		cards.add(new BigEye());	
		cards.add(new BigKoala());
		cards.add(new BlockGolem());
		cards.add(new Blockman());
		cards.add(new CatapultZone());
		cards.add(new ChangeHeart());
		cards.add(new Cloning());		
		cards.add(new DestroyerGolem());
		cards.add(new DummyGolem());
		cards.add(new EarthEffigy());
		cards.add(new Fissure());
		cards.add(new FluteKuribohBasic());
		cards.add(new FutureFusion());		
		cards.add(new GemArmadillo());
		cards.add(new GemElephant());
		cards.add(new GoldenApples());
		cards.add(new GolemSentry());
		cards.add(new GraniteLoyalist());		
		cards.add(new HammerShot());		
		cards.add(new HeavyStorm());
		cards.add(new ImperialOrder());
		cards.add(new MagicHoleGolem());
		cards.add(new MillenniumGolem());
		cards.add(new MillenniumScorpion());
		cards.add(new MillenniumShield());
		cards.add(new MudGolem());	
		cards.add(new Mudora());	
		cards.add(new MudragonSwamp());	
		cards.add(new ObeliskTormentor());
		cards.add(new Polymerization());
		cards.add(new PotAvarice());		
		cards.add(new PotGenerosity());		
		cards.add(new ReleaseFromStone());
		cards.add(new RockSunrise());
		cards.add(new ShardGreed());
		cards.add(new SliferSky());
		cards.add(new SphereKuriboh());
		cards.add(new Terraforming());		
		cards.add(new UltimateOffering());
		cards.add(new WeepingIdol());
		cards.add(new WingedKuriboh());
		cards.add(new Wiretap());
		cards.add(new YamiForm());
		
		// Creators
		if (!DuelistMod.creatorBtnBool) { cards.add(new TheCreator()); cards.add(new DarkCreator()); }
		
		// Ascension-locked cards		
		if (AbstractDungeon.ascensionLevel < 10)
		{
			cards.add(new DestructPotion());
			cards.add(new Wiseman());			
		} 
		
		if (AbstractDungeon.ascensionLevel < 15)
		{
			cards.add(new DianKeto());
			cards.add(new RedMedicine());
			cards.add(new Reload());
			cards.add(new ForbiddenChalice());
		}
		
		return cards;
	}
	
	public static ArrayList<AbstractCard> insectBasics(boolean full)
	{
		ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
		if (full)
		{
			cards.add(new BattleFootballer());
			cards.add(new BlackPendant());	
			cards.add(new Cloning());
			cards.add(new EarthquakeGiant());
			cards.add(new ForbiddenLance());
			cards.add(new FuryFire());
			cards.add(new HammerShot());
			cards.add(new HaneHane());
			cards.add(new HeavyStorm());
			cards.add(new FluteKuribohBasic());	
			cards.add(new OrbMetronome());
			cards.add(new PowerGiant());
			cards.add(new Solidarity());
		}
		
		cards.add(new BigEye());	
		cards.add(new BigKoala());		
		cards.add(new ChangeHeart());				
		cards.add(new DarklordSuperbia());	
		cards.add(new EarthGiant());		
		cards.add(new ElectromagneticShield());
		cards.add(new Electrowhip());
		cards.add(new Fissure());	
		cards.add(new FutureFusion());
		cards.add(new GoldenApples());		
		cards.add(new HarpieFeather());	
		cards.add(new ImperialOrder());
		cards.add(new Kuriboh());
		cards.add(new LabyrinthWall());
		cards.add(new LightningDarts());
		cards.add(new LightningVortex());
		cards.add(new Mausoleum());
		cards.add(new MillenniumScorpion());
		cards.add(new MudGolem());	
		cards.add(new Mudora());	
		cards.add(new MudragonSwamp());			
		cards.add(new Polymerization());
		cards.add(new PotGenerosity());
		cards.add(new PotGreed());		
		cards.add(new PowerWall());
		cards.add(new PreventRat());
		cards.add(new Raigeki());
		cards.add(new RainbowBridge());
		cards.add(new ReinforcementsArmy());
		cards.add(new Sangan());
		cards.add(new ShardGreed());
		cards.add(new SilverApples());	
		cards.add(new SphereKuriboh());
		cards.add(new Terraforming());
		cards.add(new UltimateOffering());
		cards.add(new UltraPolymerization());
		cards.add(new WingedKuriboh());
		cards.add(new WormApocalypse());
		cards.add(new WormBarses());
		cards.add(new WormKing());
		cards.add(new WormWarlord());
		cards.add(new YamiForm());
		cards.add(new YellowBelliedOni());
		
		// Creators
		if (!DuelistMod.creatorBtnBool) { cards.add(new TheCreator()); cards.add(new DarkCreator()); }
		
		// Ascension-locked cards		
		if (AbstractDungeon.ascensionLevel < 10)
		{
			cards.add(new DestructPotion());
			cards.add(new Wiseman());			
		} 
		
		if (AbstractDungeon.ascensionLevel < 15)
		{
			cards.add(new DianKeto());
			cards.add(new RedMedicine());
			cards.add(new Reload());
			cards.add(new ForbiddenChalice());
		}
		
		return cards;
	}
}
