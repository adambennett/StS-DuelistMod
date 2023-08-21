package duelistmod.rewards.boosterPacks;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import duelistmod.helpers.Util;
import duelistmod.rewards.BoosterPack;
import duelistmod.variables.Tags;

import java.util.ArrayList;

public class BeastPackR extends BoosterPack {

	public BeastPackR() {
		super("Beast Pack", "RareBooster");
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
	public boolean canSpawn() {
		return Util.deckIs("Beast Deck");
	}
	
	@Override
	public ArrayList<AbstractCard> getCards() {
		ArrayList<AbstractCard> toRet = new ArrayList<>();
		toRet.addAll(this.findAllCards(Tags.BEAST, 1, toRet));
		toRet.addAll(this.findAllCards(Tags.BEAST, 3, toRet, CardRarity.RARE));
		toRet.addAll(this.findAllCards(Tags.BEAST, CardRarity.RARE, 1, toRet));
		return toRet;
	}

	@Override
	public BoosterPack makeCopy() {
		return new BeastPackR();
	}
}
