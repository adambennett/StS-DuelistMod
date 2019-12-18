package duelistmod.rewards.boosterPacks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;

import duelistmod.rewards.BoosterPack;
import duelistmod.variables.Tags;

public class MonsterPackR extends BoosterPack
{

	public MonsterPackR() {
		super("Monster Pack", "RareBooster");
		this.rarity = PackRarity.RARE;
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
		toRet.addAll(this.findAllCards(Tags.MONSTER, CardType.ATTACK, 2, toRet, CardRarity.COMMON));
		toRet.addAll(this.findAllCards(Tags.MONSTER, CardType.SKILL, 2, toRet, CardRarity.COMMON));
		toRet.addAll(this.findAllCards(Tags.MONSTER, CardRarity.RARE, 1, toRet));
		return toRet;
	}

	@Override
	public BoosterPack makeCopy() {
		return new MonsterPackR();
	}


}
