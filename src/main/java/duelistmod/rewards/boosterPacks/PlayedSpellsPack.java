package duelistmod.rewards.boosterPacks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.DuelistMod;
import duelistmod.helpers.BoosterHelper;
import duelistmod.rewards.BoosterPack;

public class PlayedSpellsPack extends BoosterPack
{

	public PlayedSpellsPack() {
		super("Played Spells Pack", "UncommonBooster");
		this.rarity = PackRarity.UNCOMMON;
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
		ArrayList<AbstractCard> ss = new ArrayList<>();
		ss.addAll(DuelistMod.uniqueSpellsThisRun);
		toRet.addAll(this.findAllCards(ss, BoosterHelper.getPackSize(), toRet));
		return toRet;
	}

	@Override
	public BoosterPack makeCopy() {
		return new PlayedSpellsPack();
	}


}
