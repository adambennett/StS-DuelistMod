package duelistmod.rewards.boosterPacks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;

import duelistmod.rewards.BoosterPack;
import duelistmod.variables.Tags;

public class TrapPackR extends BoosterPack
{

	public TrapPackR() {
		super("Trap Pack", "RareBooster");
		this.rarity = PackRarity.RARE;
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
		toRet.addAll(this.findAllCards(Tags.TRAP, 3, toRet));
		toRet.addAll(this.findAllCards(Tags.TRAP, 2, toRet, CardRarity.COMMON));
		toRet.addAll(this.findAllCards(Tags.TRAP, CardRarity.RARE, 1, toRet));
		return toRet;
	}

	@Override
	public BoosterPack makeCopy() {
		return new TrapPackR();
	}


}
