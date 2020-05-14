package duelistmod.rewards.boosterPacks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;

import duelistmod.helpers.BoosterHelper;
import duelistmod.rewards.BoosterPack;

public class PowerPackC extends BoosterPack
{

	public PowerPackC() {
		super("Power Pack", "CommonBooster");
		this.rarity = PackRarity.COMMON;
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
		int ps = BoosterHelper.getPackSize() - 1;
		if (ps > 0)
		{
			toRet.addAll(this.findAllCards(CardType.POWER, 4, toRet, CardRarity.RARE));
		}
		return toRet;
	}

	@Override
	public BoosterPack makeCopy() {
		return new PowerPackC();
	}


}
