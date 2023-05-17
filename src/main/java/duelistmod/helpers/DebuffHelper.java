package duelistmod.helpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import duelistmod.DuelistMod;
import duelistmod.powers.*;
import duelistmod.powers.duelistPowers.*;
import duelistmod.powers.incomplete.*;

public class DebuffHelper 
{
	// For enemies
	public static AbstractPower getRandomDebuff(AbstractPlayer p, AbstractMonster targetMonster, int turnNum)
	{
		// Setup powers array for random debuff selection
		AbstractPower slow = new SlowPower(targetMonster, turnNum);
		AbstractPower vulnerable = new VulnerablePower(targetMonster, turnNum, false);
		AbstractPower poison = new PoisonPower(targetMonster, p, turnNum);
		AbstractPower weak = new WeakPower(targetMonster, turnNum, false);
		AbstractPower constricted = new ConstrictedPower(targetMonster, p, turnNum);
		AbstractPower strDown = new LoseStrengthPower(targetMonster, turnNum);
		AbstractPower strDownB = new StrengthDownPower(targetMonster, targetMonster, turnNum, 2);
		AbstractPower burning = new BurningDebuff(targetMonster, p, turnNum);
		//AbstractPower frozen = new FrozenDebuff(targetMonster, p);
		AbstractPower grease = new GreasedDebuff(targetMonster, AbstractDungeon.player, turnNum);
		AbstractPower permafrost = new PermafrostDebuff(targetMonster, AbstractDungeon.player, turnNum);
		
		ArrayList<AbstractPower> debuffs = new ArrayList<AbstractPower>();
		debuffs.add(slow);
		debuffs.add(vulnerable);
		debuffs.add(poison);
		debuffs.add(weak);
		debuffs.add(constricted);
		debuffs.add(strDown);
		debuffs.add(strDownB);
		debuffs.add(burning);
		//debuffs.add(frozen);
		debuffs.add(grease);
		debuffs.add(permafrost);

		// Get randomized debuff
		int randomDebuffNum = AbstractDungeon.cardRandomRng.random(debuffs.size() - 1);
		AbstractPower randomDebuff = debuffs.get(randomDebuffNum);
		if (DuelistMod.debug) { System.out.println("theDuelist:RandomEffectsHelper:getRandomDebuff() ---> grabbed debuff: " + randomDebuff.name); }
		return randomDebuff;
	}
	
	// For Spiral Spear Strike
	public static AbstractPower getRandomDebuffSpiral(AbstractMonster targetMonster, int turnNum)
	{
		// Setup powers array for random debuff selection
		int strRoll = AbstractDungeon.cardRandomRng.random(1, turnNum + 3);
		AbstractPower slow = new SlowPower(targetMonster, turnNum);
		AbstractPower vulnerable = new VulnerablePower(targetMonster, turnNum, false);
		AbstractPower poison = new PoisonPower(targetMonster, AbstractDungeon.player, turnNum);
		AbstractPower weak = new WeakPower(targetMonster, turnNum, false);
		AbstractPower constricted = new ConstrictedPower(targetMonster, AbstractDungeon.player, turnNum);
		AbstractPower strDown = new LoseStrengthPower(targetMonster, turnNum);
		AbstractPower strDownB = new StrengthDownPower(targetMonster, targetMonster, turnNum, strRoll);
		AbstractPower burning = new BurningDebuff(targetMonster, AbstractDungeon.player, turnNum);
		AbstractPower frozen = new FrozenDebuff(targetMonster, AbstractDungeon.player);
		AbstractPower grease = new GreasedDebuff(targetMonster, AbstractDungeon.player, turnNum);
		AbstractPower permafrost = new PermafrostDebuff(targetMonster, AbstractDungeon.player, turnNum);
		
		ArrayList<AbstractPower> debuffs = new ArrayList<AbstractPower>();
		debuffs.add(slow);
		debuffs.add(vulnerable);
		debuffs.add(poison);
		debuffs.add(weak);
		debuffs.add(constricted);
		debuffs.add(strDown);
		debuffs.add(strDownB);
		debuffs.add(burning);
		debuffs.add(frozen);
		debuffs.add(grease);
		debuffs.add(permafrost);

		// Get randomized debuff
		int randomDebuffNum = AbstractDungeon.cardRandomRng.random(debuffs.size() - 1);
		AbstractPower randomDebuff = debuffs.get(randomDebuffNum);
		if (DuelistMod.debug) { System.out.println("theDuelist:RandomEffectsHelper:getRandomDebuff() ---> grabbed debuff: " + randomDebuff.name); }
		return randomDebuff;
	}

	// For player
	public static AbstractPower getRandomPlayerDebuff(AbstractPlayer p, int turnNum)
	{
		return getRandomPlayerDebuff(p, turnNum, false);
	}
	
	public static AbstractPower getRandomPlayerDebuff(AbstractPlayer p, int turnNum, boolean haunted)
	{
		return getRandomPlayerDebuff(p, turnNum, haunted, false);
	}
	
	public static AbstractPower getRandomPlayerDebuff(AbstractPlayer p, int turnNum, boolean haunted, boolean challengePuzzle)
	{
		ArrayList<AbstractPower> pows = new ArrayList<>();
		pows.add(new ArcanaPower(p, p, -turnNum));
		pows.add(new AttackBurnPower(p, turnNum));		
		pows.add(new BloodPower(p, p, -turnNum));
		pows.add(new BloodDownPower(p, p, turnNum, 2));
		pows.add(new BurningDebuff(p, p, turnNum));
		pows.add(new DepoweredPower(p, p, turnNum));
		pows.add(new DexterityPower(p, -turnNum));
		pows.add(new DrawReductionPower(p, turnNum));
		pows.add(new EntanglePower(p));
		pows.add(new EvokeSicknessPower(p, turnNum));
		pows.add(new FishscalesPower(p, p, -turnNum));
		pows.add(new FocusDownPower(p, p, turnNum, 2));
		pows.add(new FocusLossPower(p, p, turnNum));
		pows.add(new FocusPower(p, -turnNum));
		pows.add(new FrailPower(p, turnNum, true));		
		pows.add(new LoseDexterityPower(p, turnNum));
		pows.add(new LoseStrengthPower(p, turnNum));		
		pows.add(new NoBlockPower(p, turnNum, true));
		pows.add(new SlowPower(p, turnNum));
		pows.add(new StrengthDownPower(p, p, turnNum, 2));
		pows.add(new StrengthPower(p, -turnNum));
		pows.add(new SummonSicknessPower(p, turnNum));
		pows.add(new TributeSicknessPower(p, turnNum));
		pows.add(new TricksPower(p, p, -turnNum));
		pows.add(new VulnerablePower(p, turnNum, true));
		pows.add(new WeakPower(p, turnNum, true));
		pows.add(new MonsterRestrictionsPower(p, p, turnNum));
		if (!haunted)
		{
			pows.add(new HauntedDebuff(p, p, 1));
			pows.add(new MortalityPower(p, p, turnNum));
		}
		if (!challengePuzzle)
		{
			//pows.add(new ConfusionPower(p));
			pows.add(new DeElectrifiedPower(p, p, 1, 1));
			pows.add(new FrozenDebuff(p, p));
			pows.add(new HexPower(p, 1));
			//pows.add(new MegaconfusionPower(turnNum));
			pows.add(new BeatOfDeathPower(p, 1));
		}
		if (Util.deckIs("Zombie Deck")) { pows.add(new NoSoulGainPower(p, p)); }
		return pows.get(AbstractDungeon.cardRandomRng.random(pows.size() - 1));

	}

	public static AbstractPower getRandomResummoningDebuff(AbstractPlayer p, int turnNum, boolean combatDurationDebuffs)
	{
		ArrayList<AbstractPower> pows = new ArrayList<>();
		pows.add(new MortalityPower(p, p, turnNum));
		pows.add(new NoResummoningPower(p, p, turnNum));
		pows.add(new NoResummoningAttacksPower(p, p, turnNum));
		pows.add(new NoResummoningSkillsPower(p, p, turnNum));
		pows.add(new NoResummoningPowersPower(p, p, turnNum));
		pows.add(new NoResummoningMonstersPower(p, p, turnNum));
		pows.add(new NoResummoningSpellsPower(p, p, turnNum));
		pows.add(new NoResummoningTrapsPower(p, p, turnNum));
		if (combatDurationDebuffs)
		{
			pows.add(new NoResummoningAttacksCombatPower(p, p));
			pows.add(new NoResummoningSkillsCombatPower(p, p));
			pows.add(new NoResummoningPowersCombatPower(p, p));
			pows.add(new NoResummoningMonstersCombatPower(p, p));
			pows.add(new NoResummoningSpellsCombatPower(p, p));
			pows.add(new NoResummoningTrapsCombatPower(p, p));
		}
		return pows.get(AbstractDungeon.cardRandomRng.random(pows.size() - 1));
	}
}
