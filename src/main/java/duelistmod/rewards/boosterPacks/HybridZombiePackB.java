package duelistmod.rewards.boosterPacks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.rewards.BoosterPack;
import duelistmod.variables.Tags;

public class HybridZombiePackB extends BoosterPack
{

	public HybridZombiePackB() {
		super("Hybrid Zombie Pack", "CommonBooster");
		rarity = PackRarity.COMMON;
		textColor = Color.GRAY;
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
		packName = "Hybrid Zombie Pack";
		checkRelics();
	}
	
	@Override
	public boolean canSpawn() { return true; }
	
	@Override
	public ArrayList<AbstractCard> getCards()
	{
		ArrayList<AbstractCard> toRet = new ArrayList<>();
		toRet.addAll(this.findAllCards(Tags.VAMPIRE, 1, toRet));
		toRet.addAll(this.findAllCards(Tags.VENDREAD, 2, toRet));
		toRet.addAll(this.findAllCards(Tags.GHOSTRICK, 2, toRet));
		return toRet;
	}

	@Override
	public BoosterPack makeCopy() {
		return new HybridZombiePackB();
	}


}
