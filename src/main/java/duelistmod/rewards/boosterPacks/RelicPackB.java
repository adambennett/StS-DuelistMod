package duelistmod.rewards.boosterPacks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;

import duelistmod.rewards.BoosterPack;
import duelistmod.variables.Tags;

public class RelicPackB extends BoosterPack
{

	public RelicPackB() {
		super("Relic Pack", "DeckBooster");
		this.rarity = PackRarity.SPECIAL;
		this.textColor = Color.GRAY;
		this.obeyPackSize = false;
		this.allowBasics = false;
		this.onlyBasics = false;
		this.alwaysUpgrade = false;
		this.alwaysUpgradeAtk = false;
		this.alwaysUpgradeSkill = false;
		this.alwaysUpgradePower = false;
		this.alwaysUpgradeMon = true;
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
		toRet.addAll(this.findAllCards(Tags.MONSTER, 3, toRet, CardRarity.COMMON));
		toRet.addAll(this.findAllCards(CardRarity.RARE, 2, toRet));
		return toRet;
	}

	@Override
	public BoosterPack makeCopy() {
		return new RelicPackB();
	}


}
