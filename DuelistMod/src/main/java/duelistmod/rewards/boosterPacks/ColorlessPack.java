package duelistmod.rewards.boosterPacks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;

import duelistmod.helpers.*;
import duelistmod.rewards.BoosterPack;

public class ColorlessPack extends BoosterPack
{

	public ColorlessPack() {
		super("Colorless Pack", "UncommonBooster");
		this.rarity = PackRarity.UNCOMMON;
		this.textColor = Color.GRAY;
		this.obeyPackSize = false;
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
		int ps = BoosterHelper.getPackSize() - 1;
		if (ps > 0)
		{
			toRet.addAll(this.findAllCards(BaseGameHelper.getAllColorlessCards(), ps, toRet, CardRarity.SPECIAL));
		}
		return toRet;
	}

	@Override
	public BoosterPack makeCopy() {
		return new ColorlessPack();
	}


}
