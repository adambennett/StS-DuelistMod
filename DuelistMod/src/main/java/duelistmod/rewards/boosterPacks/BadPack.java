package duelistmod.rewards.boosterPacks;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.cards.GiantSoldier;
import duelistmod.rewards.BoosterPack;

public class BadPack extends BoosterPack
{

	public BadPack() {
		super("Bad Pack", "SillyPack");
		rarity = PackRarity.COMMON;
		obeyPackSize = true;
		allowBasics = true;
		onlyBasics = false;
		alwaysUpgrade = false;
		alwaysUpgradeAtk = false;
		alwaysUpgradeSkill = false;
		alwaysUpgradePower = false;
		alwaysUpgradeMon = false;
		alwaysUpgradeSpell = false;
		alwaysUpgradeTrap = false;
		checkRelics();
	}
	
	@Override
	public boolean canSpawn() { return true; }
	
	@Override
	public ArrayList<AbstractCard> getCards()
	{
		ArrayList<AbstractCard> toRet = new ArrayList<>();
		toRet.add(new GiantSoldier());
		toRet.add(new GiantSoldier());
		toRet.add(new GiantSoldier());
		toRet.add(new GiantSoldier());
		toRet.add(new GiantSoldier());
		return toRet;
	}

	@Override
	public BoosterPack makeCopy() {
		return new BadPack();
	}


}
