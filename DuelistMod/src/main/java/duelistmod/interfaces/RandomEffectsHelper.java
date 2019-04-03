package duelistmod.interfaces;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.mod.replay.powers.NecroticPoisonPower;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import duelistmod.*;
import duelistmod.patches.DuelistCard;
import duelistmod.powers.*;

public class RandomEffectsHelper 
{
	public static AbstractPower getRandomDebuff(AbstractPlayer p, AbstractMonster targetMonster, int turnNum)
	{
		// Setup powers array for random debuff selection
		AbstractPower slow = new SlowPower(targetMonster, turnNum);
		AbstractPower vulnerable = new VulnerablePower(targetMonster, turnNum, true);
		AbstractPower poison = new PoisonPower(targetMonster, p, turnNum);
		AbstractPower weak = new WeakPower(targetMonster, turnNum, false);
		AbstractPower constricted = new ConstrictedPower(targetMonster, p, turnNum);
		ArrayList<AbstractPower> debuffs = new ArrayList<AbstractPower>();
		if (Loader.isModLoaded("ReplayTheSpireMod"))
    	{
			AbstractPower nPoison = new NecroticPoisonPower(targetMonster, p, turnNum);
			debuffs.add(slow);
			debuffs.add(vulnerable);
			debuffs.add(poison);
			debuffs.add(nPoison);
			debuffs.add(weak);
			debuffs.add(constricted);
    	}
		else
		{
			debuffs.add(slow);
			debuffs.add(vulnerable);
			debuffs.add(poison);
			debuffs.add(weak);
			debuffs.add(constricted);
		}

		// Get randomized debuff
		int randomDebuffNum = AbstractDungeon.cardRandomRng.random(debuffs.size() - 1);
		AbstractPower randomDebuff = debuffs.get(randomDebuffNum);
		if (DuelistMod.debug) { System.out.println("theDuelist:RandomEffectsHelper:getRandomDebuff() ---> grabbed debuff: " + randomDebuff.name); }
		return randomDebuff;
	}
	
	public static AbstractPower getRandomDebuffPotion(AbstractPlayer p, AbstractMonster targetMonster, int turnNum)
	{
		// Setup powers array for random debuff selection
		//AbstractPower slow = new SlowPower(targetMonster, turnNum );
		AbstractPower vulnerable = new VulnerablePower(targetMonster, turnNum, true);
		AbstractPower poison = new PoisonPower(targetMonster, p, turnNum);
		AbstractPower weak = new WeakPower(targetMonster, turnNum, true);
		AbstractPower constricted = new ConstrictedPower(targetMonster, p, turnNum);
		ArrayList<AbstractPower> debuffs = new ArrayList<AbstractPower>();
		if (Loader.isModLoaded("ReplayTheSpireMod"))
    	{
			AbstractPower nPoison = new NecroticPoisonPower(targetMonster, p, turnNum);
			debuffs.add(vulnerable);
			debuffs.add(poison);
			debuffs.add(nPoison);
			debuffs.add(weak);
			debuffs.add(constricted);
			
    	}
		else
		{
			debuffs.add(vulnerable);
			debuffs.add(poison);
			debuffs.add(weak);
			debuffs.add(constricted);
		}
		// Get randomized debuff
		int randomDebuffNum = AbstractDungeon.cardRandomRng.random(debuffs.size() - 1);
		AbstractPower randomDebuff = debuffs.get(randomDebuffNum);

		return randomDebuff;

	}

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
		AbstractPower corruption = new CorruptionPower(p);
		ArrayList<AbstractPower> debuffs = new ArrayList<AbstractPower>();
		if (Loader.isModLoaded("ReplayTheSpireMod"))
    	{
			AbstractPower nPoison = new NecroticPoisonPower(p, p, turnNum);
			debuffs.add(slow);
			debuffs.add(vulnerable);
			debuffs.add(poison);
			debuffs.add(nPoison);
			debuffs.add(weak);
			debuffs.add(entangled);
			debuffs.add(hexed);
			debuffs.add(summonSick);
			debuffs.add(tributeSick);
			debuffs.add(evokeSick);
			debuffs.add(attackBurn);
			//debuffs.add(beatOfDeath);
			debuffs.add(confusion);
			debuffs.add(corruption);
    	}
		else
		{
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
			debuffs.add(corruption);
		}
		// Get randomized debuff
		int randomDebuffNum = AbstractDungeon.cardRandomRng.random(debuffs.size() - 1);
		AbstractPower randomDebuff = debuffs.get(randomDebuffNum);

		return randomDebuff;

	}
	
	public static void addFromRandomSetToHand()
	{
		AbstractCard randomSetCard = DuelistCard.returnTrulyRandomFromSet(Tags.RANDOMONLY);
		DuelistCard.addCardToHand(randomSetCard);
	}

}
