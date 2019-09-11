package duelistmod.helpers.poolhelpers;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.DuelistMod;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.*;

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
		toReturn.add(new FluteKuriboh());
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
		toReturn.add(new GiantRex());
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
		
		// megatype pool cards
		toReturn.add(new KamionTimelord());
		toReturn.add(new RainbowRefraction());
		toReturn.add(new CrystalRaigeki());
		toReturn.add(new RainbowRuins());
		toReturn.add(new HourglassLife());
		toReturn.add(new Eva());
		toReturn.add(new HappyLover());
		toReturn.add(new DunamesDarkWitch());
		toReturn.add(new RainbowNeos());
		toReturn.add(new RainbowFlower());
		DuelistMod.basicCards = new ArrayList<AbstractCard>();		
		DuelistMod.basicCards.addAll(toReturn);
		return toReturn;
	}
	
	public static ArrayList<AbstractCard> fullBasic()
	{
		DuelistMod.basicCards = new ArrayList<AbstractCard>();		
		DuelistMod.basicCards.add(new CastleWalls());
		DuelistMod.basicCards.add(new CelticGuardian());
		DuelistMod.basicCards.add(new Cloning());		
		DuelistMod.basicCards.add(new DarkFactory());
		DuelistMod.basicCards.add(new DarkHole());
		DuelistMod.basicCards.add(new DarkMirrorForce());
		DuelistMod.basicCards.add(new Fissure());
		DuelistMod.basicCards.add(new FlameSwordsman());
		DuelistMod.basicCards.add(new FortressWarrior());
		DuelistMod.basicCards.add(new GaiaFierce());
		DuelistMod.basicCards.add(new GoldenApples());
		DuelistMod.basicCards.add(new HammerShot());
		DuelistMod.basicCards.add(new HaneHane());
		DuelistMod.basicCards.add(new HarpieFeather());
		DuelistMod.basicCards.add(new HeavyStorm());
		DuelistMod.basicCards.add(new Hinotama());
		DuelistMod.basicCards.add(new ImperialOrder());
		DuelistMod.basicCards.add(new JudgeMan());
		DuelistMod.basicCards.add(new Kuriboh());
		DuelistMod.basicCards.add(new LabyrinthWall());
		DuelistMod.basicCards.add(new Mausoleum());
		DuelistMod.basicCards.add(new MirrorForce());
		DuelistMod.basicCards.add(new MillenniumShield());
		DuelistMod.basicCards.add(new ObeliskTormentor());
		DuelistMod.basicCards.add(new FeatherPho());
		DuelistMod.basicCards.add(new PotAvarice());
		DuelistMod.basicCards.add(new PotForbidden());
		DuelistMod.basicCards.add(new PotDichotomy());
		DuelistMod.basicCards.add(new PotGenerosity());
		DuelistMod.basicCards.add(new PotGreed());
		DuelistMod.basicCards.add(new PreventRat());
		DuelistMod.basicCards.add(new RadiantMirrorForce());
		DuelistMod.basicCards.add(new Raigeki());
		DuelistMod.basicCards.add(new Sangan());
		DuelistMod.basicCards.add(new Scapegoat());
		DuelistMod.basicCards.add(new ScrapFactory());
		DuelistMod.basicCards.add(new ShardGreed());
		DuelistMod.basicCards.add(new SmashingGround());
		DuelistMod.basicCards.add(new SphereKuriboh());
		DuelistMod.basicCards.add(new StormingMirrorForce());
		DuelistMod.basicCards.add(new StrayLambs());
		DuelistMod.basicCards.add(new Terraforming());
		DuelistMod.basicCards.add(new BigFire());
		DuelistMod.basicCards.add(new UltimateOffering());
		//DuelistMod.basicCards.add(new ValkMagnet());
		DuelistMod.basicCards.add(new Wiretap());
		DuelistMod.basicCards.add(new Mathematician());
		DuelistMod.basicCards.add(new BattleOx());
		DuelistMod.basicCards.add(new FluteKuriboh());
		DuelistMod.basicCards.add(new WingedKuriboh());
		DuelistMod.basicCards.add(new CastleDarkIllusions());
		DuelistMod.basicCards.add(new ChangeHeart());
		DuelistMod.basicCards.add(new JamBreeding());
		DuelistMod.basicCards.add(new Polymerization());
		DuelistMod.basicCards.add(new BigKoala());
		DuelistMod.basicCards.add(new BattleguardKing());		
		DuelistMod.basicCards.add(new BattleFootballer());
		DuelistMod.basicCards.add(new EarthquakeGiant());
		DuelistMod.basicCards.add(new EvilswarmHeliotrope());
		DuelistMod.basicCards.add(new WormApocalypse());
		DuelistMod.basicCards.add(new WormBarses());
		DuelistMod.basicCards.add(new WormWarlord());
		DuelistMod.basicCards.add(new WormKing());
		DuelistMod.basicCards.add(new GauntletWarrior());		
		DuelistMod.basicCards.add(new GaiaMidnight());		
		DuelistMod.basicCards.add(new GilfordLegend());
		DuelistMod.basicCards.add(new ReinforcementsArmy());
		DuelistMod.basicCards.add(new BlockGolem());
		DuelistMod.basicCards.add(new DokiDoki());
		DuelistMod.basicCards.add(new GiantSoldierSteel());
		DuelistMod.basicCards.add(new RainbowJar());
		DuelistMod.basicCards.add(new WingedKuriboh9());
		DuelistMod.basicCards.add(new WingedKuriboh10());
		DuelistMod.basicCards.add(new MagicalStone());
		DuelistMod.basicCards.add(new Kuribohrn());
		DuelistMod.basicCards.add(new DropOff());
		DuelistMod.basicCards.add(new GiantRex());
		DuelistMod.basicCards.add(new PowerGiant());
		DuelistMod.basicCards.add(new RainbowBridge());
		DuelistMod.basicCards.add(new EarthGiant());
		DuelistMod.basicCards.add(new RainbowKuriboh());
		DuelistMod.basicCards.add(new ClearKuriboh());
		DuelistMod.basicCards.add(new Linkuriboh());
		DuelistMod.basicCards.add(new GiantTrapHole());
		DuelistMod.basicCards.add(new BigEye());
		DuelistMod.basicCards.add(new YamiForm());
		DuelistMod.basicCards.add(new RainbowOverdragon());
		DuelistMod.basicCards.add(new RainbowGravity());
		DuelistMod.basicCards.add(new RainbowLife());
		DuelistMod.basicCards.add(new SilverApples());
		DuelistMod.basicCards.add(new DarklordSuperbia());	
		DuelistMod.basicCards.add(new LightningVortex());
		DuelistMod.basicCards.add(new BlackPendant());	
		DuelistMod.basicCards.add(new LightningRodLord());		
		DuelistMod.basicCards.add(new MudGolem());	
		DuelistMod.basicCards.add(new Mudora());	
		DuelistMod.basicCards.add(new MudragonSwamp());	
		DuelistMod.basicCards.add(new FlameTiger());
		DuelistMod.basicCards.add(new MiraculousDescent());
		DuelistMod.basicCards.add(new ForbiddenLance());
		DuelistMod.basicCards.add(new FutureFusion());
		DuelistMod.basicCards.add(new ElectromagneticShield());
		DuelistMod.basicCards.add(new Electrowhip());
		DuelistMod.basicCards.add(new FuryFire());
		DuelistMod.basicCards.add(new MagicCylinder());
		DuelistMod.basicCards.add(new RockSunrise());
		DuelistMod.basicCards.add(new UltraPolymerization());
		DuelistMod.basicCards.add(new MillenniumScorpion());
		DuelistMod.basicCards.add(new Metronome());
		DuelistMod.basicCards.add(new OneForOne());
		DuelistMod.basicCards.add(new OrbMetronome());
		DuelistMod.basicCards.add(new Overworked());
		
		// Creators
		if (!DuelistMod.creatorBtnBool) { DuelistMod.basicCards.add(new TheCreator()); DuelistMod.basicCards.add(new DarkCreator()); }
		
		// Ascension-locked cards		
		if (AbstractDungeon.ascensionLevel < 10)
		{
			DuelistMod.basicCards.add(new DestructPotion());
			DuelistMod.basicCards.add(new Wiseman());			
		} 
		
		if (AbstractDungeon.ascensionLevel < 15)
		{
			DuelistMod.basicCards.add(new DianKeto());
			DuelistMod.basicCards.add(new RedMedicine());
			DuelistMod.basicCards.add(new Reload());
			DuelistMod.basicCards.add(new ForbiddenChalice());
		}
		// END Ascension-locked cards
		
		if (DuelistMod.debug)
		{
			DuelistMod.logger.info("Printing all basic cards!!");
			int counter = 1;
			for (AbstractCard c : DuelistMod.basicCards)
			{
				DuelistMod.logger.info("[" + counter + "]: " + c.name);
			}
		}
		
		DuelistMod.archetypeCards.addAll(DuelistMod.basicCards);
		return DuelistMod.basicCards;
	}

	public static ArrayList<AbstractCard> smallBasic()
	{
		DuelistMod.basicCards = new ArrayList<AbstractCard>();		
		//DuelistMod.basicCards.add(new CastleWalls());
		//DuelistMod.basicCards.add(new CelticGuardian());
		DuelistMod.basicCards.add(new Cloning());		
		DuelistMod.basicCards.add(new DarkFactory());
		//DuelistMod.basicCards.add(new DarkHole());
		//DuelistMod.basicCards.add(new DarkMirrorForce());
		//DuelistMod.basicCards.add(new Fissure());
		//DuelistMod.basicCards.add(new FlameSwordsman());
		DuelistMod.basicCards.add(new FortressWarrior());
		DuelistMod.basicCards.add(new GaiaFierce());
		DuelistMod.basicCards.add(new GoldenApples());
		DuelistMod.basicCards.add(new HammerShot());
		DuelistMod.basicCards.add(new HaneHane());
		//DuelistMod.basicCards.add(new HarpieFeather());
		//DuelistMod.basicCards.add(new HeavyStorm());
		DuelistMod.basicCards.add(new Hinotama());
		DuelistMod.basicCards.add(new ImperialOrder());
		//DuelistMod.basicCards.add(new JudgeMan());
		DuelistMod.basicCards.add(new Kuriboh());
		DuelistMod.basicCards.add(new LabyrinthWall());
		DuelistMod.basicCards.add(new Mausoleum());
		//DuelistMod.basicCards.add(new MirrorForce());
		DuelistMod.basicCards.add(new MillenniumShield());
		DuelistMod.basicCards.add(new ObeliskTormentor());
		//DuelistMod.basicCards.add(new FeatherPho());
		//DuelistMod.basicCards.add(new PotAvarice());
		//DuelistMod.basicCards.add(new PotForbidden());
		DuelistMod.basicCards.add(new PotDichotomy());
		DuelistMod.basicCards.add(new PotGenerosity());
		DuelistMod.basicCards.add(new PotGreed());
		DuelistMod.basicCards.add(new PreventRat());
		//DuelistMod.basicCards.add(new RadiantMirrorForce());
		DuelistMod.basicCards.add(new Raigeki());
		DuelistMod.basicCards.add(new Sangan());
		//DuelistMod.basicCards.add(new Scapegoat());
		DuelistMod.basicCards.add(new ScrapFactory());
		DuelistMod.basicCards.add(new ShardGreed());
		DuelistMod.basicCards.add(new SmashingGround());
		//DuelistMod.basicCards.add(new SphereKuriboh());
		//DuelistMod.basicCards.add(new StormingMirrorForce());
		//DuelistMod.basicCards.add(new StrayLambs());
		//DuelistMod.basicCards.add(new Terraforming());
		//DuelistMod.basicCards.add(new BigFire());
		//DuelistMod.basicCards.add(new UltimateOffering());
		//DuelistMod.basicCards.add(new ValkMagnet());
		DuelistMod.basicCards.add(new Wiretap());
		DuelistMod.basicCards.add(new Mathematician());
		DuelistMod.basicCards.add(new BattleOx());
		DuelistMod.basicCards.add(new FluteKuriboh());
		//DuelistMod.basicCards.add(new WingedKuriboh());
		//DuelistMod.basicCards.add(new CastleDarkIllusions());
		DuelistMod.basicCards.add(new ChangeHeart());
		//DuelistMod.basicCards.add(new JamBreeding());
		DuelistMod.basicCards.add(new Polymerization());
		DuelistMod.basicCards.add(new BigKoala());
		//DuelistMod.basicCards.add(new BattleguardKing());		
		DuelistMod.basicCards.add(new BattleFootballer());
		//DuelistMod.basicCards.add(new EarthquakeGiant());
		//DuelistMod.basicCards.add(new EvilswarmHeliotrope());
		DuelistMod.basicCards.add(new WormApocalypse());
		//DuelistMod.basicCards.add(new WormBarses());
		//DuelistMod.basicCards.add(new WormWarlord());
		//DuelistMod.basicCards.add(new WormKing());
		//DuelistMod.basicCards.add(new GauntletWarrior());		
		DuelistMod.basicCards.add(new GaiaMidnight());		
		//DuelistMod.basicCards.add(new GilfordLegend());
		//DuelistMod.basicCards.add(new ReinforcementsArmy());
		DuelistMod.basicCards.add(new BlockGolem());
		DuelistMod.basicCards.add(new DokiDoki());
		DuelistMod.basicCards.add(new GiantSoldierSteel());
		DuelistMod.basicCards.add(new RainbowJar());
		DuelistMod.basicCards.add(new WingedKuriboh9());
		//DuelistMod.basicCards.add(new WingedKuriboh10());
		DuelistMod.basicCards.add(new MagicalStone());
		//DuelistMod.basicCards.add(new Kuribohrn());
		//DuelistMod.basicCards.add(new DropOff());
		DuelistMod.basicCards.add(new GiantRex());
		DuelistMod.basicCards.add(new PowerGiant());
		//DuelistMod.basicCards.add(new RainbowBridge());
		DuelistMod.basicCards.add(new EarthGiant());
		DuelistMod.basicCards.add(new RainbowKuriboh());
		DuelistMod.basicCards.add(new ClearKuriboh());
		//DuelistMod.basicCards.add(new Linkuriboh());
		//DuelistMod.basicCards.add(new GiantTrapHole());
		DuelistMod.basicCards.add(new BigEye());
		DuelistMod.basicCards.add(new YamiForm());
		DuelistMod.basicCards.add(new RainbowOverdragon());
		//DuelistMod.basicCards.add(new RainbowGravity());
		//DuelistMod.basicCards.add(new RainbowLife());
		DuelistMod.basicCards.add(new SilverApples());
		DuelistMod.basicCards.add(new DarklordSuperbia());	
		DuelistMod.basicCards.add(new LightningVortex());
		DuelistMod.basicCards.add(new BlackPendant());	
		//DuelistMod.basicCards.add(new LightningRodLord());	
		DuelistMod.basicCards.add(new ForbiddenLance());
		//DuelistMod.basicCards.add(new FutureFusion());
		//DuelistMod.basicCards.add(new ElectromagneticShield());
		//DuelistMod.basicCards.add(new Electrowhip());
		DuelistMod.basicCards.add(new FuryFire());
		//DuelistMod.basicCards.add(new MudGolem());	
		//DuelistMod.basicCards.add(new Mudora());	
		//DuelistMod.basicCards.add(new MudragonSwamp());	
		//DuelistMod.basicCards.add(new FlameTiger());
		DuelistMod.basicCards.add(new MiraculousDescent());
		DuelistMod.basicCards.add(new RockSunrise());
		DuelistMod.basicCards.add(new UltraPolymerization());
		DuelistMod.basicCards.add(new OrbMetronome());
		
		// Creators
		if (!DuelistMod.creatorBtnBool) { DuelistMod.basicCards.add(new TheCreator()); DuelistMod.basicCards.add(new DarkCreator()); }
		
		// Ascension-locked cards		
		if (AbstractDungeon.ascensionLevel < 10)
		{
			DuelistMod.basicCards.add(new DestructPotion());
			DuelistMod.basicCards.add(new Wiseman());
		} 
		
		if (AbstractDungeon.ascensionLevel < 15)
		{
			DuelistMod.basicCards.add(new DianKeto());
			DuelistMod.basicCards.add(new RedMedicine());
			DuelistMod.basicCards.add(new Reload());
			DuelistMod.basicCards.add(new ForbiddenChalice());
		}
		// END Ascension-locked cards
		
		if (DuelistMod.debug)
		{
			DuelistMod.logger.info("Printing all basic cards!!");
			int counter = 1;
			for (AbstractCard c : DuelistMod.basicCards)
			{
				DuelistMod.logger.info("[" + counter + "]: " + c.name);
			}
		}
		
		DuelistMod.archetypeCards.addAll(DuelistMod.basicCards);
		return DuelistMod.basicCards;
	}

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
		pools.add(StandardPool.deck());
		pools.add(WarriorPool.deck());
		pools.add(ZombiePool.deck());
		if (!DuelistMod.ojamaBtnBool) { pools.add(OjamaPool.deck()); }
		if (!DuelistMod.toonBtnBool) { pools.add(ToonPool.deck()); }
		if (DuelistMod.archRoll1 == -1 || DuelistMod.archRoll2 == -1 || DuelistMod.archRoll1 > pools.size()) { DuelistMod.archRoll1 = ThreadLocalRandom.current().nextInt(pools.size()); }
		ArrayList<AbstractCard> random = pools.get(DuelistMod.archRoll1);
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
		return random;
	}
}
