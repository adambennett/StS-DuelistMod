package duelistmod.rewards.boosterPacks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.rewards.BoosterPack;
import duelistmod.variables.Tags;

public class GhostrickPack extends BoosterPack
{

	public GhostrickPack() {
		super("Ghostrick Pack", "UncommonBooster");
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
		alwaysUpgradeSpell = false;
		alwaysUpgradeTrap = false;
		packName = "Ghostrick Pack";
		checkRelics();
	}
	
	@Override
	public boolean canSpawn() { return true; }
	
	@Override
	public ArrayList<AbstractCard> getCards()
	{
		ArrayList<AbstractCard> toRet = new ArrayList<>();
		toRet.addAll(this.findAllCards(Tags.GHOSTRICK, 5, toRet));
		return toRet;
	}

	@Override
	public BoosterPack makeCopy() {
		return new GhostrickPack();
	}


}
