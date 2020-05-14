package duelistmod.rewards.boosterPacks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;

import duelistmod.rewards.BoosterPack;
import duelistmod.variables.Tags;

public class StandardPackR extends BoosterPack
{

	public StandardPackR() {
		super("Standard Pack", "RareBooster");
		rarity = PackRarity.RARE;
		this.textColor = Color.YELLOW;
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
		toRet.addAll(this.findAllCards(CardType.ATTACK, 1, toRet, CardRarity.COMMON));
		toRet.addAll(this.findAllCards(CardType.SKILL, 1, toRet, CardRarity.COMMON));
		toRet.addAll(this.findAllCards(Tags.MONSTER, 1, toRet, CardRarity.COMMON));
		toRet.addAll(this.findAllCards(1, toRet, CardRarity.COMMON));
		toRet.addAll(this.findAllCards(CardRarity.RARE, 1, toRet));
		return toRet;
	}

	@Override
	public BoosterPack makeCopy() {
		return new StandardPackR();
	}


}
