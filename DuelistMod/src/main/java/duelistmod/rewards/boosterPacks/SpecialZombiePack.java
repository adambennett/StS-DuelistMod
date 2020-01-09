package duelistmod.rewards.boosterPacks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.rewards.BoosterPack;
import duelistmod.variables.Tags;

public class SpecialZombiePack extends BoosterPack
{

	public SpecialZombiePack() {
		super("Special Zombie Pack", "UncommonBooster");
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
		packName = "Special Zombie Pack";
		checkRelics();
	}
	
	@Override
	public boolean canSpawn() { return true; }
	
	@Override
	public ArrayList<AbstractCard> getCards()
	{
		ArrayList<AbstractCard> toRet = new ArrayList<>();
		toRet.addAll(this.findAllCards(Tags.VAMPIRE, 1, toRet));
		toRet.addAll(this.findAllCards(Tags.SHIRANUI, 1, toRet));
		toRet.addAll(this.findAllCards(Tags.GHOSTRICK, 1, toRet));
		toRet.addAll(this.findAllCards(Tags.VENDREAD, 1, toRet));
		toRet.addAll(this.findAllCards(Tags.MAYAKASHI, 1, toRet));
		return toRet;
	}

	@Override
	public BoosterPack makeCopy() {
		return new SpecialZombiePack();
	}


}
