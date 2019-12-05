package duelistmod.helpers;

import java.util.*;
import java.util.Map.Entry;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import duelistmod.DuelistMod;
import duelistmod.powers.*;
import duelistmod.powers.duelistPowers.*;
import duelistmod.powers.enemyPowers.*;
import duelistmod.powers.incomplete.FlameTigerPower;

public class BuffHelper {

	
	// RANDOM BUFF HELPERS ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static AbstractPower randomBuffEnemy(AbstractMonster mon, int roll, boolean naturia)
	{
		if (roll < 1) { roll = AbstractDungeon.cardRandomRng.random(1, 6); }
		int intangibleRoll = AbstractDungeon.cardRandomRng.random(1, 3);
		int timeWarpCardsRoll = AbstractDungeon.cardRandomRng.random(10, 16);
		int timeWarpStrRoll = AbstractDungeon.cardRandomRng.random(1, 3);
		ArrayList<AbstractPower> pows = new ArrayList<>();
		pows.add(new StrengthPower(mon, roll));
		pows.add(new RegenPower(mon, roll));
		pows.add(new IntangiblePower(mon, intangibleRoll));
		pows.add(new ThornsPower(mon, roll));
		pows.add(new MetallicizePower(mon, roll));
		pows.add(new AngerPower(mon, roll));
		pows.add(new AngryPower(mon, roll));
		pows.add(new ArtifactPower(mon, roll));
		pows.add(new MalleablePower(mon, roll));
		pows.add(new DemonFormPower(mon, roll));
		pows.add(new RitualPower(mon, roll, false));
		pows.add(new ThieveryPower(mon, roll));
		pows.add(new DuelistTimeWarpPower(mon, timeWarpCardsRoll, timeWarpStrRoll));
		if (naturia) {	pows.add(new ResistNatureEnemyPower(mon, AbstractDungeon.player, roll)); }
		AbstractPower powReturn = pows.get(AbstractDungeon.cardRandomRng.random(pows.size() - 1));
		if (powReturn instanceof DuelistTimeWarpPower) { powReturn = pows.get(AbstractDungeon.cardRandomRng.random(pows.size() - 1)); }
		return powReturn;
	}
	
	public static AbstractPower createRandomBuff()
	{
		BuffHelper.initBuffMap(AbstractDungeon.player);
		Set<Entry<String, AbstractPower>> set = DuelistMod.buffMap.entrySet();
		ArrayList<AbstractPower> localBuffs = new ArrayList<AbstractPower>();
		for (Entry<String, AbstractPower> e : set) { localBuffs.add(e.getValue()); }
		int roll = AbstractDungeon.cardRandomRng.random(localBuffs.size() - 1);
		return localBuffs.get(roll);
	}

	public static void resetBuffPool()
	{
		int noBuffs = AbstractDungeon.cardRandomRng.random(DuelistMod.lowNoBuffs, DuelistMod.highNoBuffs);
		DuelistMod.randomBuffs = new ArrayList<AbstractPower>();
		DuelistMod.randomBuffStrings = new ArrayList<String>();
		for (int i = 0; i < noBuffs; i++)
		{
			AbstractPower randomBuff = createRandomBuff();
			while (DuelistMod.randomBuffStrings.contains(randomBuff.name)) { randomBuff = createRandomBuff(); }
			DuelistMod.randomBuffs.add(randomBuff);
			DuelistMod.randomBuffStrings.add(randomBuff.name);
		}
	}

	public static void resetRandomBuffs(int turnNum)
	{
		BuffHelper.initBuffMap(AbstractDungeon.player, turnNum);
		for (int i = 0; i < DuelistMod.randomBuffs.size(); i++) { DuelistMod.randomBuffs.set(i, DuelistMod.buffMap.get(DuelistMod.randomBuffs.get(i).name)); }
	}
	
	public static String trapVortexBuffName(int powerID)
	{
		if (powerID == 1)
		{
			return "#yStrength";
		}
		else if (powerID == 2)
		{
			return "#yThorns";
		}
		else if (powerID == 3)
		{
			return "#yArtifacts";
		}
		else if (powerID == 4)
		{
			return "#yBlur";
		}
		else if (powerID == 5)
		{
			return "#yElectricity";
		}
		else if (powerID == 6)
		{
			return "#yTomb #yLooter";
		}
		else
		{
			return "#yBlur";
		}
	}

	public static AbstractPower trapVortex(int powerID, int traps)
	{
		AbstractPlayer p = AbstractDungeon.player;
		if (powerID > 7 || powerID < 1) { return new BlurPower(p, traps); }
		else
		{
			if (powerID == 1)
			{
				return new StrengthPower(p, traps);
			}
			else if (powerID == 2)
			{
				return new ThornsPower(p, traps);
			}
			else if (powerID == 3)
			{
				return new ArtifactPower(p, traps);
			}
			else if (powerID == 4)
			{
				return new BlurPower(p, traps);
			}
			else if (powerID == 5)
			{
				return new ElectricityPower(traps);
			}
			else if (powerID == 6)
			{
				return new TombLooterPower(p, traps);
			}
			else
			{
				return new BlurPower(p, traps);
			}
		}
	}
	
	public static String trapVortexBuffNameB(int powerID)
	{
		if (powerID == 1)
		{
			return "#yStrength";
		}
		else if (powerID == 2)
		{
			return "#yThorns";
		}
		else if (powerID == 3)
		{
			return "#yArtifacts";
		}
		else if (powerID == 4)
		{
			return "#Electricity";
		}
		else if (powerID == 5)
		{
			return "#yJuggernaut";
		}
		else if (powerID == 6)
		{
			return "#yBlur";
		}
		else
		{
			return "#yBlur";
		}
	}

	public static AbstractPower trapVortexB(int powerID, int traps)
	{
		AbstractPlayer p = AbstractDungeon.player;
		if (powerID > 7 || powerID < 1) { return new BlurPower(p, traps); }
		else
		{
			if (powerID == 1)
			{
				return new StrengthPower(p, traps);
			}
			else if (powerID == 2)
			{
				return new ThornsPower(p, traps);
			}
			else if (powerID == 3)
			{
				return new ArtifactPower(p, traps);
			}
			else if (powerID == 4)
			{
				return new ElectricityPower(traps);
			}
			else if (powerID == 5)
			{
				return new JuggernautPower(p, traps);
			}
			else if (powerID == 6)
			{
				return new BlurPower(p, traps);
			}
			else
			{
				return new BlurPower(p, traps);
			}
		}
	}
	
	public static void initBuffMap(AbstractPlayer p)
	{
		int turnNum = AbstractDungeon.cardRandomRng.random(1, 4);
		initBuffMap(p, turnNum);
	}
	
	public static void initBuffMap(AbstractPlayer p, int turnNum)
	{
		int secondRoll = AbstractDungeon.cardRandomRng.random(1, 3);
		initBuffMap(p, turnNum, secondRoll);
	}
	
	public static void initBuffMap(AbstractPlayer p, int turnNum, int secondRoll)
	{
		DuelistMod.buffMap = new HashMap<String, AbstractPower>();
		DuelistMod.logger.info("random buff map turn num roll: " + turnNum);		
		AbstractPower aerodynamics = new AerodynamicsPower(p, p);
		AbstractPower artifact = new ArtifactPower(p, turnNum);
		AbstractPower barricade = new BarricadePower(p);
		AbstractPower blood = new BloodPower(1);
		AbstractPower blur = new BlurPower(p, turnNum);
		AbstractPower burst = new BurstPower(p, turnNum);
		AbstractPower dexterity = new DexterityPower(p, turnNum);
		AbstractPower doublePlay = new DoublePlayFirstCardPower(p, p, turnNum);
		AbstractPower doubleTap = new DoubleTapPower(p, turnNum);
		AbstractPower electric = new ElectricityPower(1);
		AbstractPower energized = new EnergizedPower(p, turnNum);
		AbstractPower envenom = new EnvenomPower(p, turnNum);
		AbstractPower equilibrium = new EquilibriumPower(p, turnNum);
		AbstractPower exodia = new ExodiaPower();
		AbstractPower feelNoPain = new FeelNoPainPower(p, turnNum);
		AbstractPower fireBreathing = new FireBreathingPower(p, turnNum);
		AbstractPower fishscale = new FishscalesPower(1);
		AbstractPower flux = new FluxPower(turnNum);
		AbstractPower focusUp = new FocusUpPower(p, p, turnNum, secondRoll);
		AbstractPower helloWorld = new HelloPower(p, turnNum);
		AbstractPower jamBreedingMachine = new TwoJamPower(p, 1, turnNum, secondRoll);
		AbstractPower juggernaut = new JuggernautPower(p, turnNum);
		AbstractPower mayhem = new MayhemPower(p, turnNum);
		AbstractPower metallicize = new MetallicizePower(p, turnNum);
		AbstractPower oniPower = new OniPower(p, p, 1);
		AbstractPower orbEvoker = new OrbEvokerPower(p, turnNum);
		AbstractPower orbHealer = new OrbHealerPower(p, turnNum);
		AbstractPower platedArmor = new PlatedArmorPower(p, turnNum);
		AbstractPower potGenerosity = new PotGenerosityPower(secondRoll);
		AbstractPower reductionist = new ReducerPower(p, turnNum);
		AbstractPower sadistic = new SadisticPower(p, turnNum);
		AbstractPower seaDweller = new SeaDwellerPower();
		AbstractPower storm = new StormPower(p, turnNum);
		AbstractPower strength = new StrengthPower(p, turnNum);
		AbstractPower thermodynamics = new FlameTigerPower(p, p);
		AbstractPower thorns = new ThornsPower(p, turnNum);
		AbstractPower tombLooter = new TombLooterPower(p, turnNum);
		AbstractPower tombPilfer = new HealGoldPower(p, turnNum);
		AbstractPower zombieLord = new ResummonBonusPower(p, p, turnNum);
		AbstractPower iceHand = new IceHandPower(turnNum);
		AbstractPower[] buffs = new AbstractPower[] 
		{
				strength, dexterity, artifact, platedArmor, energized, thorns, barricade, blur, 
				burst, doubleTap, equilibrium, feelNoPain, fireBreathing, juggernaut, metallicize, sadistic, storm, 
				orbEvoker, tombPilfer, fishscale, blood, iceHand,
				potGenerosity, reductionist, mayhem, envenom, seaDweller,
				aerodynamics, flux, electric, orbHealer, tombLooter, doublePlay,
				jamBreedingMachine, helloWorld, thermodynamics, zombieLord, exodia, oniPower, focusUp
		};
		for (AbstractPower a : buffs)
		{
			DuelistMod.buffMap.put(a.name, a);
		}
	}

}
