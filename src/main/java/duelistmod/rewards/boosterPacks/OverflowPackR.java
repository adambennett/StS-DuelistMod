package duelistmod.rewards.boosterPacks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;

import duelistmod.rewards.BoosterPack;
import duelistmod.variables.Tags;

public class OverflowPackR extends BoosterPack
{

	public OverflowPackR() {
		super("Overflow Pack", "RareBooster");
		this.rarity = PackRarity.RARE;
		this.textColor = Color.YELLOW;
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
		toRet.addAll(this.findAllCards(Tags.IS_OVERFLOW, 1, toRet));
		toRet.addAll(this.findAllCards(Tags.IS_OVERFLOW, 3, toRet, CardRarity.RARE));
		toRet.addAll(this.findAllCards(Tags.IS_OVERFLOW, CardRarity.RARE, 1, toRet));
		return toRet;
	}

	@Override
	public BoosterPack makeCopy() {
		return new OverflowPackR();
	}


}
