package duelistmod.rewards.boosterPacks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;

import duelistmod.rewards.BoosterPack;

public class AttackPack extends BoosterPack
{

	public AttackPack() {
		super("Attack Pack", "CommonBooster");
		rarity = PackRarity.COMMON;
		textColor = Color.GRAY;
		obeyPackSize = true;
		allowBasics = false;
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
		toRet.addAll(this.findAllCards(CardType.ATTACK, 4, toRet, CardRarity.RARE, CardRarity.UNCOMMON));
		toRet.addAll(this.findAllCards(CardType.ATTACK, 1, toRet, CardRarity.RARE));
		return toRet;
	}

	@Override
	public BoosterPack makeCopy() {
		return new AttackPack();
	}


}
