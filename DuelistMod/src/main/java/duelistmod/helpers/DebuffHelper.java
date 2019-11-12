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

		// Get randomized debuff
		int randomDebuffNum = AbstractDungeon.cardRandomRng.random(debuffs.size() - 1);
		AbstractPower randomDebuff = debuffs.get(randomDebuffNum);
		if (DuelistMod.debug) { System.out.println("theDuelist:RandomEffectsHelper:getRandomDebuff() ---> grabbed debuff: " + randomDebuff.name); }
		return randomDebuff;
	}

	// For player
	public static AbstractPower getRandomPlayerDebuff(AbstractPlayer p, int turnNum)
	{
		// Setup powers array for random debuff selection
		AbstractPower slow = new SlowPower(p, turnNum);
		AbstractPower vulnerable = new VulnerablePower(p, turnNum, true);
		AbstractPower poison = new PoisonPower(p, p, turnNum);
		AbstractPower weak = new WeakPower(p, turnNum, true);
		AbstractPower entangled = new EntanglePower(p);
		AbstractPower hexed = new HexPower(p, 1);
		AbstractPower summonSick = new SummonSicknessPower(p, turnNum);
		AbstractPower tributeSick = new TributeSicknessPower(p, turnNum);
		AbstractPower evokeSick = new EvokeSicknessPower(p, turnNum);
		AbstractPower attackBurn = new AttackBurnPower(p, turnNum);
		AbstractPower beatOfDeath = new BeatOfDeathPower(p, 1);
		//AbstractPower choked = new ChokePower(p, turnNum);
		AbstractPower confusion = new ConfusionPower(p);
		AbstractPower strDown = new LoseStrengthPower(p, turnNum);
		AbstractPower strDownB = new StrengthDownPower(p, p, turnNum, 2);
		AbstractPower strDownC = new StrengthDownPower(p, p, turnNum, 4);
		AbstractPower dexDown = new LoseDexterityPower(p, turnNum);
		AbstractPower haunted = new HauntedDebuff(p, p, 1);
		AbstractPower mortal = new MortalityPower(p, p, turnNum);
		AbstractPower depower = new DepoweredPower(p, p, turnNum);
		//AbstractPower bias = new BiasPower(p, turnNum);
		AbstractPower str = new StrengthPower(p, -turnNum);
		AbstractPower dex = new DexterityPower(p, -turnNum);
		AbstractPower foc = new FocusPower(p, -turnNum);
		AbstractPower frail = new FrailPower(p, turnNum, true);
		AbstractPower drawDown = new DrawReductionPower(p, turnNum);
		AbstractPower noBlock = new NoBlockPower(p, turnNum, true);
		//AbstractPower noDraw = new NoDrawPower(p);
		//AbstractPower wraith = new WraithFormPower(p, -turnNum);
		AbstractPower focDown = new FocusDownPower(p, p, turnNum, 2);
		AbstractPower focLoss = new FocusLossPower(p, p, turnNum);
		AbstractPower deElec = new DeElectrifiedPower(p, p, 1, 1);
		AbstractPower megaCon = new MegaconfusionPower(turnNum);
		ArrayList<AbstractPower> debuffs = new ArrayList<AbstractPower>();
		
		debuffs.add(slow);
		debuffs.add(vulnerable);
		debuffs.add(poison);
		debuffs.add(weak);
		debuffs.add(entangled);
		debuffs.add(hexed);
		debuffs.add(summonSick);
		debuffs.add(tributeSick);
		debuffs.add(evokeSick);
		debuffs.add(attackBurn);
		debuffs.add(beatOfDeath);
		debuffs.add(confusion);
		debuffs.add(strDown);
		debuffs.add(dexDown);
		debuffs.add(haunted);
		debuffs.add(mortal);
		debuffs.add(depower);
		debuffs.add(strDownB);
		debuffs.add(strDownC);
		//debuffs.add(bias);
		debuffs.add(str);
		debuffs.add(dex);
		debuffs.add(foc);
		debuffs.add(frail);
		debuffs.add(drawDown);
		debuffs.add(noBlock);
		//debuffs.add(noDraw);
		//debuffs.add(wraith);
		debuffs.add(focDown);
		debuffs.add(focLoss);
		debuffs.add(deElec);
		debuffs.add(megaCon);
	
		// Get randomized debuff
		int randomDebuffNum = AbstractDungeon.cardRandomRng.random(debuffs.size() - 1); 
		AbstractPower randomDebuff = debuffs.get(randomDebuffNum);
		return randomDebuff;

	}
	
	// For player - used only for Haunted effects (so you dont pull another Haunted debuff)
	public static AbstractPower getRandomPlayerDebuffForHaunt(AbstractPlayer p, int turnNum)
	{
		AbstractPower randomDebuff = getRandomPlayerDebuff(p, turnNum);
		while (randomDebuff instanceof HauntedDebuff) { randomDebuff = getRandomPlayerDebuff(p, turnNum); }
		return randomDebuff;
	}
}
