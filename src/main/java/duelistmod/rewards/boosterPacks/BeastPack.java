package duelistmod.rewards.boosterPacks;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import duelistmod.helpers.Util;
import duelistmod.rewards.BoosterPack;
import duelistmod.variables.Tags;

import java.util.ArrayList;

public class BeastPack extends BoosterPack {

	public BeastPack() {
		super("Beast Pack", "UncommonBooster");
		this.rarity = PackRarity.UNCOMMON;
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
	public ArrayList<AbstractCard> getCards()
	{
        return new ArrayList<>(this.findAllCards(Tags.BEAST, 5, new ArrayList<>(), CardRarity.RARE));
	}

	@Override
	public BoosterPack makeCopy() {
		return new BeastPack();
	}
}
