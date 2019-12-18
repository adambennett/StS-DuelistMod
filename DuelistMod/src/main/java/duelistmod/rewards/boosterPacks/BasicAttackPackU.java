package duelistmod.rewards.boosterPacks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;

import duelistmod.rewards.BoosterPack;

public class BasicAttackPackU extends BoosterPack
{

	public BasicAttackPackU() {
		super("Basic Attack Pack", "UncommonBooster");
		rarity = PackRarity.UNCOMMON;
		textColor = Color.BLUE;
		obeyPackSize = true;
		allowBasics = true;
		onlyBasics = true;
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
		toRet.addAll(this.findAllCards(CardType.ATTACK, 3, toRet, CardRarity.RARE));
		toRet.addAll(this.findAllCards(CardType.ATTACK, 2, toRet));
		return toRet;
	}

	@Override
	public BoosterPack makeCopy() {
		return new BasicAttackPackU();
	}


}
