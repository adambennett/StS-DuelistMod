package duelistmod.rewards.boosterPacks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.*;

import duelistmod.helpers.BoosterHelper;
import duelistmod.rewards.BoosterPack;
import duelistmod.variables.Tags;

public class ArcanePack extends BoosterPack
{

	public ArcanePack() {
		super("Arcane Pack", "UncommonBooster");
		rarity = PackRarity.UNCOMMON;
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
		packName = "Arcane Pack";
		checkRelics();
	}
	
	@Override
	public boolean canSpawn() { return true; }
	
	@Override
	public ArrayList<AbstractCard> getCards()
	{
		ArrayList<AbstractCard> toRet = new ArrayList<>();
		toRet.addAll(this.findAllCards(Tags.ARCANE, BoosterHelper.getPackSize(), toRet, CardRarity.RARE));
		return toRet;
	}

	@Override
	public BoosterPack makeCopy() {
		return new ArcanePack();
	}


}
