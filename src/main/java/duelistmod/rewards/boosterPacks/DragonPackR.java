package duelistmod.rewards.boosterPacks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;

import duelistmod.helpers.Util;
import duelistmod.rewards.BoosterPack;
import duelistmod.variables.Tags;

public class DragonPackR extends BoosterPack
{

	public DragonPackR() {
		super("Dragon Pack", "RareBooster");
		this.rarity = PackRarity.RARE;
		this.textColor = Color.GRAY;
		this.obeyPackSize = true;
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
	public boolean canSpawn() { if (Util.deckIs("Zombie Deck")) { return false; } return true; }
	
	@Override
	public ArrayList<AbstractCard> getCards()
	{
		ArrayList<AbstractCard> toRet = new ArrayList<>();
		toRet.addAll(this.findAllCards(Tags.DRAGON, 1, toRet));
		toRet.addAll(this.findAllCards(Tags.DRAGON, 3, toRet, CardRarity.RARE));
		toRet.addAll(this.findAllCards(Tags.DRAGON, CardRarity.RARE, 1, toRet));
		return toRet;
	}

	@Override
	public BoosterPack makeCopy() {
		return new DragonPackR();
	}


}
