package duelistmod.helpers;

import java.util.*;
import java.util.Map.Entry;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;

import duelistmod.DuelistMod;
import duelistmod.powers.*;
import duelistmod.powers.incomplete.FlameTigerPower;

public class BuffHelper {

	// RANDOM BUFF HELPERS ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

	public static void initBuffMap(AbstractPlayer p)
	{
		DuelistMod.buffMap = new HashMap<String, AbstractPower>();
		int turnNum = AbstractDungeon.cardRandomRng.random(1, 4);
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
		AbstractPower creative = new CreativeAIPower(p, 1); //probably too good
		//AbstractPower darkEmb = new DarkEmbracePower(p, turnNum);
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
		AbstractPower generosity = new PotGenerosityPower(p, p, 2);
		AbstractPower focus = new FocusPower(p, 1);
		AbstractPower focusB = new FocusPower(p, 2);
		AbstractPower reductionist = new ReducerPower(p, turnNum);
		AbstractPower timeWizard = new TimeWizardPower(p, p, 1);
		AbstractPower mayhem = new MayhemPower(p, 1);
		AbstractPower envenom = new EnvenomPower(p, turnNum);
		AbstractPower amplify = new AmplifyPower(p, 1);
		AbstractPower angry = new AngryPower(p, 1);
		AbstractPower anger = new AngerPower(p, 1);
		AbstractPower buffer = new BufferPower(p, 1);
		AbstractPower conserve = new ConservePower(p, 1);
		AbstractPower curiosity = new CuriosityPower(p, 1);
		AbstractPower aero = new AerodynamicsPower(p, p);
		AbstractPower naturia = new NaturiaPower(p, p, turnNum);
		AbstractPower jambreed = new TwoJamPower(p, 1, turnNum, 3);
		AbstractPower jambreedC = new TwoJamPower(p, 1, turnNum, 2);
		AbstractPower hello = new HelloPower(p, turnNum);
		AbstractPower flameTiger = new FlameTigerPower(p, p);
		AbstractPower zombieLord = new ResummonBonusPower(p, p, turnNum);
		AbstractPower exodia = new ExodiaPower();
		
		AbstractPower[] buffs = new AbstractPower[] { str };
		if (DuelistMod.challengeMode)
		{
			buffs = new AbstractPower[] 
			{
					str, dex, art, plate, thorns, blur, 
					orbHeal, tombLoot, orbEvoker, tombPilfer,
					focus, reductionist, envenom,
					anger, angry, conserve, curiosity, aero,
					naturia, jambreedC, hello
			};
		}
		else
		{
			buffs = new AbstractPower[] 
			{
					str, dex, art, plate, intan, regen, energy, thorns, barricade, blur, 
					burst, doubleTap, equal, noPain, fire, jugger, metal, penNib, sadistic, storm, orbHeal, tombLoot,
					orbEvoker, tombPilfer, retainCards, timeWizard,
					generosity, focus, reductionist, creative, mayhem, envenom,
					amplify, anger, angry, buffer, conserve, curiosity, aero,
					naturia, jambreed, focusB, hello, flameTiger, zombieLord, exodia
			};
		}
		for (AbstractPower a : buffs)
		{
			DuelistMod.buffMap.put(a.name, a);
		}
	}

	public static void initBuffMap(AbstractPlayer p, int turnNum)
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
		AbstractPower creative = new CreativeAIPower(p, 1); //probably too good
		//AbstractPower darkEmb = new DarkEmbracePower(p, turnNum);
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
		AbstractPower generosity = new PotGenerosityPower(p, p, 2);
		AbstractPower focus = new FocusPower(p, 1);
		AbstractPower focusB = new FocusPower(p, 2);
		AbstractPower reductionist = new ReducerPower(p, turnNum);
		AbstractPower timeWizard = new TimeWizardPower(p, p, 1);
		AbstractPower mayhem = new MayhemPower(p, 1);
		AbstractPower envenom = new EnvenomPower(p, turnNum);
		AbstractPower amplify = new AmplifyPower(p, 1);
		AbstractPower angry = new AngryPower(p, 1);
		AbstractPower anger = new AngerPower(p, 1);
		AbstractPower buffer = new BufferPower(p, 1);
		AbstractPower conserve = new ConservePower(p, 1);
		AbstractPower curiosity = new CuriosityPower(p, 1);
		AbstractPower aero = new AerodynamicsPower(p, p);
		AbstractPower naturia = new NaturiaPower(p, p, turnNum);
		AbstractPower jambreed = new TwoJamPower(p, 1, turnNum, 3);
		AbstractPower jambreedC = new TwoJamPower(p, 1, turnNum, 2);
		AbstractPower hello = new HelloPower(p, turnNum);
		AbstractPower flameTiger = new FlameTigerPower(p, p);
		AbstractPower zombieLord = new ResummonBonusPower(p, p, turnNum);
		AbstractPower exodia = new ExodiaPower();
		
		AbstractPower[] buffs = new AbstractPower[] { str };
		if (DuelistMod.challengeMode)
		{
			buffs = new AbstractPower[] 
			{
					str, dex, art, plate, thorns, blur, 
					orbHeal, tombLoot, orbEvoker, tombPilfer,
					focus, reductionist, envenom,
					anger, angry, conserve, curiosity, aero,
					naturia, jambreedC, hello
			};
		}
		else
		{
			buffs = new AbstractPower[] 
			{
					str, dex, art, plate, intan, regen, energy, thorns, barricade, blur, 
					burst, doubleTap, equal, noPain, fire, jugger, metal, penNib, sadistic, storm, orbHeal, tombLoot,
					orbEvoker, tombPilfer, retainCards, timeWizard,
					generosity, focus, reductionist, creative, mayhem, envenom,
					amplify, anger, angry, buffer, conserve, curiosity, aero,
					naturia, jambreed, focusB, hello, flameTiger, zombieLord, exodia
			};
		}
		for (AbstractPower a : buffs)
		{
			DuelistMod.buffMap.put(a.name, a);
		}
	}

}
