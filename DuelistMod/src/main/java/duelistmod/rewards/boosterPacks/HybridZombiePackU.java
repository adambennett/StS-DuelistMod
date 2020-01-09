package duelistmod.rewards.boosterPacks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.rewards.BoosterPack;
import duelistmod.variables.Tags;

public class HybridZombiePackU extends BoosterPack
{

	public HybridZombiePackU() {
		super("Hybrid Zombie Pack", "UncommonBooster");
		rarity = PackRarity.UNCOMMON;
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
		toRet.addAll(this.findAllCards(Tags.VAMPIRE, 3, toRet));
		toRet.addAll(this.findAllCards(Tags.MAYAKASHI, 2, toRet));
		return toRet;
	}

	@Override
	public BoosterPack makeCopy() {
		return new HybridZombiePackU();
	}


}
