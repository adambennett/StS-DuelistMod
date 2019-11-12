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

	public static void resetRandomBuffs()
	{
		BuffHelper.initBuffMap(AbstractDungeon.player);
		for (int i = 0; i < DuelistMod.randomBuffs.size(); i++) { DuelistMod.randomBuffs.set(i, DuelistMod.buffMap.get(DuelistMod.randomBuffs.get(i).name));	}
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
		AbstractPower str = new StrengthPower(p, turnNum);
		AbstractPower dex = new DexterityPower(p, 1);
		AbstractPower art = new ArtifactPower(p, turnNum);
		AbstractPower plate = new PlatedArmorPower(p, turnNum);
		AbstractPower intan = new IntangiblePlayerPower(p, 1);
		AbstractPower regen = new RegenPower(p, turnNum);
		AbstractPower energy = new EnergizedPower(p, 1);
		AbstractPower thorns = new ThornsPower(p, turnNum);
		AbstractPower barricade = new BarricadePower(p);
		AbstractPower blur = new BlurPower(p, turnNum);
		AbstractPower burst = new BurstPower(p, turnNum);
		AbstractPower creative = new CreativeAIPower(p, 1);
		AbstractPower doubleTap = new DoubleTapPower(p, turnNum);
		AbstractPower equal = new EquilibriumPower(p, 2);
		AbstractPower noPain = new FeelNoPainPower(p, turnNum);
		AbstractPower fire = new FireBreathingPower(p, 3);
		AbstractPower jugger = new JuggernautPower(p, turnNum);
		AbstractPower metal = new MetallicizePower(p, turnNum);
		AbstractPower penNib = new PenNibPower(p, 1);
		AbstractPower sadistic = new SadisticPower(p, turnNum);
		AbstractPower storm = new StormPower(p, 1);
		AbstractPower orbHeal = new OrbHealerPower(p, turnNum);
		AbstractPower tombLoot = new TombLooterPower(p, turnNum);
		AbstractPower orbEvoker = new OrbEvokerPower(p, turnNum);
		AbstractPower tombPilfer = new HealGoldPower(p, turnNum);
		AbstractPower retainCards = new RetainCardPower(p, 1);
		AbstractPower generosity = new PotGenerosityPower(secondRoll);
		AbstractPower focus = new FocusPower(p, 1);
		AbstractPower focusB = new FocusPower(p, 2);
		AbstractPower reductionist = new ReducerPower(p, turnNum);
		AbstractPower timeWizard = new TimeWizardPower(p, p, 1);
		AbstractPower mayhem = new MayhemPower(p, 1);
		AbstractPower envenom = new EnvenomPower(p, turnNum);
		AbstractPower amplify = new AmplifyPower(p, 1);
		//AbstractPower angry = new AngryPower(p, 1);
		//AbstractPower anger = new AngerPower(p, 1);
		AbstractPower buffer = new BufferPower(p, 1);
		AbstractPower conserve = new ConservePower(p, 1);
		AbstractPower curiosity = new CuriosityPower(p, 1);
		AbstractPower aero = new AerodynamicsPower(p, p);
		AbstractPower jambreed = new TwoJamPower(p, 1, turnNum, secondRoll);
		AbstractPower hello = new HelloPower(p, turnNum);
		AbstractPower flameTiger = new FlameTigerPower(p, p);
		AbstractPower zombieLord = new ResummonBonusPower(p, p, turnNum);
		AbstractPower exodia = new ExodiaPower();
		AbstractPower oniPower = new OniPower(p, p, 1);
		AbstractPower focusUp = new FocusUpPower(p, p, turnNum, secondRoll);
		AbstractPower doublePlay = new DoublePlayFirstCardPower(p, p, turnNum);
		AbstractPower flux = new FluxPower(turnNum);
		AbstractPower electric = new ElectricityPower(turnNum);
		AbstractPower fishscale = new FishscalesPower(turnNum);
		AbstractPower blood = new BloodPower(turnNum);
		AbstractPower seaDweller = new SeaDwellerPower();
		AbstractPower[] buffs = new AbstractPower[] 
		{
				str, dex, art, plate, intan, regen, energy, thorns, barricade, blur, 
				burst, doubleTap, equal, noPain, fire, jugger, metal, penNib, sadistic, storm, orbHeal, tombLoot,
				orbEvoker, tombPilfer, retainCards, timeWizard, fishscale, blood,
				generosity, focus, reductionist, creative, mayhem, envenom, seaDweller,
				amplify, buffer, conserve, curiosity, aero, flux, electric,
				jambreed, focusB, hello, flameTiger, zombieLord, exodia, oniPower, focusUp, doublePlay
		};
		for (AbstractPower a : buffs)
		{
			DuelistMod.buffMap.put(a.name, a);
		}
	}

}
