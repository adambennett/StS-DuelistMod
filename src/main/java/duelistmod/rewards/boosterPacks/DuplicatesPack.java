package duelistmod.rewards.boosterPacks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.enums.StartingDeck;
import duelistmod.helpers.BoosterHelper;
import duelistmod.helpers.CardFinderHelper;
import duelistmod.rewards.BoosterPack;

public class DuplicatesPack extends BoosterPack
{

	public DuplicatesPack() {
		super("Duplicates Pack", "UncommonBooster");
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
	public boolean canSpawn() {
		return AbstractDungeon.floorNum > 10;
	}
	
	@Override
	public ArrayList<AbstractCard> getCards() {
		return CardFinderHelper.find(BoosterHelper.getPackSize(), AbstractDungeon.player.masterDeck.group, null, (card) -> !StartingDeck.currentDeck.isCardInStartingDeck(card.cardID) && card.rarity != CardRarity.SPECIAL);
	}

	@Override
	public BoosterPack makeCopy() {
		return new DuplicatesPack();
	}


}
