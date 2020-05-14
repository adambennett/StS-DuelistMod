package duelistmod.rewards.boosterPacks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;

import duelistmod.helpers.BoosterHelper;
import duelistmod.rewards.BoosterPack;
import duelistmod.variables.Tags;

public class ArcanePackR extends BoosterPack
{

	public ArcanePackR() {
		super("Arcane Pack", "RareBooster");
		rarity = PackRarity.RARE;
		textColor = Color.GRAY;
		obeyPackSize = true;
		allowBasics = true;
		onlyBasics = false;
		alwaysUpgrade = false;
		alwaysUpgradeAtk = false;
		alwaysUpgradeSkill = false;
		alwaysUpgradePower = false;
		alwaysUpgradeMon = false;
		alwaysUpgradeSpell = true;
		alwaysUpgradeTrap = false;
		checkRelics();
	}
	
	@Override
	public boolean canSpawn() { return true; }
	
	@Override
	public ArrayList<AbstractCard> getCards()
	{
		ArrayList<AbstractCard> toRet = new ArrayList<>();
		toRet.addAll(this.findAllCards(Tags.ARCANE, BoosterHelper.getPackSize() - 1, toRet));
		toRet.addAll(this.findAllCards(Tags.ARCANE, 1, toRet, CardRarity.COMMON));
		return toRet;
	}

	@Override
	public BoosterPack makeCopy() {
		return new ArcanePackR();
	}


}
