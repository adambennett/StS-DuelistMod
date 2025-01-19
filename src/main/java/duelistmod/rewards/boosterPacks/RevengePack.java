package duelistmod.rewards.boosterPacks;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import duelistmod.interfaces.RevengeCard;
import duelistmod.rewards.BoosterPack;

import java.util.ArrayList;

public class RevengePack extends BoosterPack {

	public RevengePack() {
		super("Revenge Pack", "UncommonBooster");
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
	public ArrayList<AbstractCard> getCards() {
		ArrayList<AbstractCard> toRet = new ArrayList<>();
		toRet.addAll(this.findAllCards(c -> c instanceof RevengeCard, 3, toRet, CardRarity.RARE));
		toRet.addAll(this.findAllCards(c -> c instanceof RevengeCard, 2, toRet));
		return toRet;
	}

	@Override
	public BoosterPack makeCopy() {
		return new RevengePack();
	}

}
