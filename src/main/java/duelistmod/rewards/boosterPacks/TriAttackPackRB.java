package duelistmod.rewards.boosterPacks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;

import duelistmod.rewards.BoosterPack;

public class TriAttackPackRB extends BoosterPack
{

	public TriAttackPackRB() {
		super("Tri-Attack Pack", "RareBooster");
		this.rarity = PackRarity.RARE;
		this.textColor = Color.GRAY;
		this.obeyPackSize = false;
		this.allowBasics = true;
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
		toRet.addAll(this.findAllCards(CardType.ATTACK, 1, toRet, CardRarity.COMMON));
		toRet.addAll(this.findAllCards(CardType.ATTACK, 2, toRet, CardRarity.COMMON, CardRarity.UNCOMMON));
		return toRet;
	}

	@Override
	public BoosterPack makeCopy() {
		return new TriAttackPackRB();
	}


}
