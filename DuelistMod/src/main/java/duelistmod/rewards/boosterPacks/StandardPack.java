package duelistmod.rewards.boosterPacks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;

import duelistmod.rewards.BoosterPack;
import duelistmod.variables.Tags;

public class StandardPack extends BoosterPack
{

	public StandardPack() {
		super("Standard Pack", "CommonBooster");
		this.rarity = PackRarity.COMMON;
		this.textColor = Color.GRAY;
		this.obeyPackSize = true;
		this.allowBasics = false;
		this.onlyBasics = false;
		this.alwaysUpgrade = false;
		this.alwaysUpgradeAtk = false;
		this.alwaysUpgradeSkill = false;
		this.alwaysUpgradePower = false;
		this.alwaysUpgradeMon = false;
		this.alwaysUpgradeSpell = false;
		this.alwaysUpgradeTrap = false;
		checkRelics();
	}
	
	@Override
	public boolean canSpawn() { return true; }
	
	@Override
	public ArrayList<AbstractCard> getCards()
	{
		ArrayList<AbstractCard> toRet = new ArrayList<>();
		toRet.addAll(this.findAllCards(CardType.ATTACK, 1, toRet, CardRarity.RARE, CardRarity.UNCOMMON));
		toRet.addAll(this.findAllCards(CardType.SKILL, 1, toRet, CardRarity.RARE, CardRarity.UNCOMMON));
		toRet.addAll(this.findAllCards(Tags.MONSTER, 1, toRet, CardRarity.RARE, CardRarity.UNCOMMON));
		toRet.addAll(this.findAllCards(CardRarity.COMMON, 1, toRet));
		toRet.addAll(this.findAllCards(CardRarity.UNCOMMON, 1, toRet));
		return toRet;
	}

	@Override
	public BoosterPack makeCopy() {
		return new StandardPack();
	}


}
