package duelistmod.rewards.boosterPacks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.helpers.Util;
import duelistmod.rewards.BoosterPack;

public class HolidayPack extends BoosterPack
{

	public HolidayPack() {
		super("Holiday Pack", "AllRaresBooster");
		this.rarity = PackRarity.SUPER_RARE;
		this.textColor = Color.GRAY;
		this.obeyPackSize = false;
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
	public boolean canSpawn() { if (Util.getChallengeLevel() < 0) { return true; } else { return false; }}
	
	@Override
	public ArrayList<AbstractCard> getCards()
	{
		ArrayList<AbstractCard> toRet = new ArrayList<>();
		toRet.addAll(this.findAllCards(Util.allHolidayCardsNoDateCheck(), 3, toRet));
		return toRet;
	}

	@Override
	public BoosterPack makeCopy() {
		return new HolidayPack();
	}


}
