package duelistmod.helpers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import duelistmod.DuelistMod;
import duelistmod.powers.*;
import duelistmod.powers.incomplete.HauntedDebuff;

public class RandomEffectsHelper 
{
	// For enemies
	public static AbstractPower getRandomDebuff(AbstractPlayer p, AbstractMonster targetMonster, int turnNum)
	{
		// Setup powers array for random debuff selection
		AbstractPower slow = new SlowPower(targetMonster, turnNum);
		AbstractPower vulnerable = new VulnerablePower(targetMonster, turnNum, true);
		AbstractPower poison = new PoisonPower(targetMonster, p, turnNum);
		AbstractPower weak = new WeakPower(targetMonster, turnNum, false);
		AbstractPower constricted = new ConstrictedPower(targetMonster, p, turnNum);
		AbstractPower strDown = new LoseStrengthPower(targetMonster, turnNum);
		ArrayList<AbstractPower> debuffs = new ArrayList<AbstractPower>();
		debuffs.add(slow);
		debuffs.add(vulnerable);
		debuffs.add(poison);
		debuffs.add(weak);
		debuffs.add(constricted);
		debuffs.add(strDown);
		

		// Get randomized debuff
		int randomDebuffNum = AbstractDungeon.cardRandomRng.random(debuffs.size() - 1);
		AbstractPower randomDebuff = debuffs.get(randomDebuffNum);
		if (DuelistMod.debug) { System.out.println("theDuelist:RandomEffectsHelper:getRandomDebuff() ---> grabbed debuff: " + randomDebuff.name); }
		return randomDebuff;
	}
	
	// Old Joey Juice code - didnt work properly
	public static AbstractPower getRandomDebuffPotion(AbstractPlayer p, AbstractMonster targetMonster, int turnNum)
	{
		// Setup powers array for random debuff selection
		//AbstractPower slow = new SlowPower(targetMonster, turnNum );
		AbstractPower vulnerable = new VulnerablePower(targetMonster, turnNum, true);
		AbstractPower poison = new PoisonPower(targetMonster, p, turnNum);
		AbstractPower weak = new WeakPower(targetMonster, turnNum, true);
		AbstractPower constricted = new ConstrictedPower(targetMonster, p, turnNum);
		ArrayList<AbstractPower> debuffs = new ArrayList<AbstractPower>();
		debuffs.add(vulnerable);
		debuffs.add(poison);
		debuffs.add(weak);
		debuffs.add(constricted);
		// Get randomized debuff
		int randomDebuffNum = AbstractDungeon.cardRandomRng.random(debuffs.size() - 1);
		AbstractPower randomDebuff = debuffs.get(randomDebuffNum);

		return randomDebuff;

	}

	// For player
	public static AbstractPower getRandomPlayerDebuff(AbstractPlayer p, int turnNum)
	{
		// Setup powers array for random debuff selection
		AbstractPower slow = new SlowPower(p, turnNum);
		AbstractPower vulnerable = new VulnerablePower(p, turnNum, true);
		AbstractPower poison = new PoisonPower(p, p, turnNum);
		AbstractPower weak = new WeakPower(p, turnNum, false);
		AbstractPower entangled = new EntanglePower(p);
		AbstractPower hexed = new HexPower(p, 1);
		AbstractPower summonSick = new SummonSicknessPower(p, turnNum);
		AbstractPower tributeSick = new TributeSicknessPower(p, turnNum);
		AbstractPower evokeSick = new EvokeSicknessPower(p, turnNum);
		AbstractPower attackBurn = new AttackBurnPower(p, turnNum);
		//AbstractPower beatOfDeath = new BeatOfDeathPower(p, turnNum);
		//AbstractPower choked = new ChokePower(p, turnNum);
		AbstractPower confusion = new ConfusionPower(p);
		//AbstractPower corruption = new CorruptionPower(p);
		AbstractPower strDown = new LoseStrengthPower(p, turnNum);
		AbstractPower dexDown = new LoseDexterityPower(p, turnNum);
		AbstractPower haunted = new HauntedDebuff(p, p, 1);
		AbstractPower mortal = new MortalityPower(p, p, turnNum);
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
		//debuffs.add(beatOfDeath);
		debuffs.add(confusion);
		//debuffs.add(corruption);
		debuffs.add(strDown);
		debuffs.add(dexDown);
		debuffs.add(haunted);
		debuffs.add(mortal);
	
		// Get randomized debuff
		int randomDebuffNum = AbstractDungeon.cardRandomRng.random(debuffs.size() - 1);
		AbstractPower randomDebuff = debuffs.get(randomDebuffNum);

		return randomDebuff;

	}
	
	// For player - used only for Haunted effects (so you dont pull another Haunted debuff)
	public static AbstractPower getRandomPlayerDebuffForHaunt(AbstractPlayer p, int turnNum)
	{
		// Setup powers array for random debuff selection
		AbstractPower slow = new SlowPower(p, turnNum);
		AbstractPower vulnerable = new VulnerablePower(p, turnNum, true);
		AbstractPower poison = new PoisonPower(p, p, turnNum);
		AbstractPower weak = new WeakPower(p, turnNum, false);
		AbstractPower entangled = new EntanglePower(p);
		AbstractPower hexed = new HexPower(p, 1);
		AbstractPower summonSick = new SummonSicknessPower(p, turnNum);
		AbstractPower tributeSick = new TributeSicknessPower(p, turnNum);
		AbstractPower evokeSick = new EvokeSicknessPower(p, turnNum);
		AbstractPower attackBurn = new AttackBurnPower(p, turnNum);
		//AbstractPower beatOfDeath = new BeatOfDeathPower(p, turnNum);
		//AbstractPower choked = new ChokePower(p, turnNum);
		AbstractPower confusion = new ConfusionPower(p);
		//AbstractPower corruption = new CorruptionPower(p);
		AbstractPower strDown = new LoseStrengthPower(p, turnNum);
		AbstractPower dexDown = new LoseDexterityPower(p, turnNum);
		AbstractPower mortal = new MortalityPower(p, p, turnNum);
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
		//debuffs.add(beatOfDeath);
		debuffs.add(confusion);
		//debuffs.add(corruption);
		debuffs.add(strDown);
		debuffs.add(dexDown);
		debuffs.add(mortal);
	
		// Get randomized debuff
		int randomDebuffNum = AbstractDungeon.cardRandomRng.random(debuffs.size() - 1);
		AbstractPower randomDebuff = debuffs.get(randomDebuffNum);

		return randomDebuff;

	}
}
