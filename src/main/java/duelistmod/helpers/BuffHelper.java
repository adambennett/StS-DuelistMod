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
import duelistmod.powers.incomplete.*;

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

	public static AbstractPower randomBuffEnemyChallenge(AbstractMonster mon, int roll, boolean naturia)
	{
		if (roll < 1) { roll = AbstractDungeon.cardRandomRng.random(1, 2); }
		int intangibleRoll = AbstractDungeon.cardRandomRng.random(1, 2);
		int timeWarpCardsRoll = AbstractDungeon.cardRandomRng.random(18, 24);
		int timeWarpStrRoll = AbstractDungeon.cardRandomRng.random(1, 2);
		ArrayList<AbstractPower> pows = new ArrayList<>();
		pows.add(new StrengthPower(mon, roll));
		pows.add(new RegenPower(mon, roll));
		pows.add(new IntangiblePower(mon, intangibleRoll));
		pows.add(new ThornsPower(mon, roll));
		pows.add(new MetallicizePower(mon, roll));
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
		ArrayList<AbstractPower> localBuffs = new ArrayList<>();
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
			return "#yJuggernaut";
		}
		else if (powerID == 5)
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
				return new JuggernautPower(p, traps);
			}
			else if (powerID == 5)
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
	
	public static ArrayList<AbstractPower> returnBuffList(AbstractPlayer p, int turnNum, int secondRoll)
	{
		ArrayList<AbstractPower> pows = new ArrayList<>();
		pows.add(new ArcanaPower(turnNum));
		pows.add(new ArtifactPower(p, turnNum));
		pows.add(new AmplifyPower(p, 1));
		pows.add(new BarricadePower(p));
		pows.add(new BloodPower(1));
		pows.add(new BlurPower(p, turnNum));
		pows.add(new BurstPower(p, turnNum));
		pows.add(new DexterityPower(p, turnNum));
		pows.add(new DoublePlayFirstCardPower(p, p, turnNum));
		pows.add(new DoubleTapPower(p, turnNum));
		pows.add(new DragonTreasurePower(turnNum));
		pows.add(new ElectricityPower(1));
		pows.add(new ElectricityUpPower(p, p, turnNum, secondRoll));
		pows.add(new EnergizedPower(p, turnNum));
		pows.add(new EnvenomPower(p, turnNum));
		pows.add(new EquilibriumPower(p, turnNum));
		pows.add(new ExodiaPower());
		pows.add(new FeelNoPainPower(p, turnNum));
		pows.add(new FireBreathingPower(p, turnNum));
		pows.add(new FishscalesPower(1));
		pows.add(new FutureFusionPower(p, p, turnNum));
		pows.add(new HelloPower(p, turnNum));
		pows.add(new ImperialPower(p, turnNum));
		pows.add(new JuggernautPower(p, turnNum));
		pows.add(new MayhemPower(p, turnNum));
		pows.add(new MetallicizePower(p, turnNum));
		pows.add(new ObeliskPower(p, p, turnNum));
		pows.add(new OniPower(p, p, 1));
		pows.add(new PlatedArmorPower(p, turnNum));
		pows.add(new PotGenerosityPower(secondRoll));
		pows.add(new ReducerPower(p, turnNum));
		pows.add(new RegenPower(p, turnNum));		
		pows.add(new SeafaringPower(p, p, turnNum));	
		pows.add(new SadisticPower(p, turnNum));	
		pows.add(new StrengthPower(p, turnNum));
		pows.add(new StrengthUpPower(p, p, turnNum, secondRoll));
		pows.add(new ThornsPower(p, turnNum));
		pows.add(new TombLooterPower(p, turnNum));
		pows.add(new TricksPower(turnNum));
		pows.add(new TwoJamPower(p, 1, turnNum, secondRoll));
		pows.add(new RetainCardPower(p, 1));
		
		if (Util.deckIs("Machine Deck")) { 
			pows.add(new TurretWarriorPower(p, p, turnNum)); 
			pows.add(new FluxPower(turnNum));
		}
		if (Util.deckIs("Zombie Deck")) { 
			pows.add(new GhostrickMansionPower()); 
			pows.add(new OverpoweringEyePower()); 
			pows.add(new VampireRetainerPower()); 
			pows.add(new ResummonBonusPower(p, p, turnNum));
			pows.add(new GravediggerPower(turnNum));
		}
		if (Util.deckIs("Aqua Deck")) { 
			pows.add(new SeaDwellerPower());
			pows.add(new MakoBlessingPower(p, p, turnNum));
		}
		
		// Orb-ish Decks
		if (Util.deckIs("Spellcaster Deck") || Util.deckIs("Standard Deck")|| Util.deckIs("Dragon Deck")|| Util.deckIs("Plant Deck")|| Util.deckIs("Fiend Deck")|| Util.deckIs("Zombie Deck") || Util.deckIs("Machine Deck")) { 
			pows.add(new OrbEvokerPower(p, turnNum));
			pows.add(new FocusUpPower(p, p, turnNum, secondRoll));
			pows.add(new FocusPower(p, secondRoll)); 
			pows.add(new FlameTigerPower(p, p));
			pows.add(new OrbHealerPower(p, turnNum));
			pows.add(new AerodynamicsPower(p, p));
			if (Util.deckIs("Spellcaster Deck")){
				pows.add(new MagickaPower(p, p, turnNum));
			}
		}
		if (Util.deckIs("Naturia Deck")) { 
			/*pows.add(Util.vinesPower(turnNum));
			pows.add(Util.leavesPower(turnNum));*/
			// TODO: Rewrite BuffHelper class to allow these to be uncommented and a AnyDuelist object passed in
			pows.add(new StormPower(p, turnNum));
		}
		if (Util.getChallengeLevel() < 0) { 
			pows.add(new HealGoldPower(p, turnNum)); 
			pows.add(new IntangiblePlayerPower(p, 1));
		}
		
		return pows;
	}
	
	public static void initBuffMap(AbstractPlayer p, int turnNum, int secondRoll)
	{
		DuelistMod.buffMap = new HashMap<>();
		ArrayList<AbstractPower> pows = returnBuffList(p, turnNum, secondRoll);
		for (AbstractPower a : pows)
		{
			DuelistMod.buffMap.put(a.name, a);
		}
	}

}
