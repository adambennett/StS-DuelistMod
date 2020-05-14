package duelistmod.rewards.boosterPacks;

import java.util.*;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.DuelistMod;
import duelistmod.characters.TheDuelist;
import duelistmod.helpers.BoosterHelper;
import duelistmod.rewards.BoosterPack;

public class FallbackPack extends BoosterPack
{

	public FallbackPack() {
		super("Fallback Pack", "CommonBooster");
		rarity = PackRarity.RARE;
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
		packName = "Fallback Pack";
		checkRelics();
	}
	
	@Override
	public boolean canSpawn() { if (DuelistMod.badBoosterSituation) { return true; } return false; }
	
	@Override
	public ArrayList<AbstractCard> getCards()
	{
		ArrayList<AbstractCard> toRet = new ArrayList<>();
		if (TheDuelist.cardPool.group.size() > 0)
		{
			ArrayList<AbstractCard> tempPool = new ArrayList<>();
			for (AbstractCard c : TheDuelist.cardPool.group) {
				tempPool.add(c.makeStatEquivalentCopy());
			}
			
			while (tempPool.size() > 0 && toRet.size() < BoosterHelper.getPackSize()) {
				toRet.add(tempPool.remove(AbstractDungeon.cardRandomRng.random(tempPool.size() - 1)));
			}
		}
		
		if (toRet.size() < BoosterHelper.getPackSize()) {
			ArrayList<AbstractCard> tempPool = new ArrayList<>();
			for (AbstractCard c : DuelistMod.myCards) {
				tempPool.add(c.makeStatEquivalentCopy());
			}
			
			while (tempPool.size() > 0 && toRet.size() < BoosterHelper.getPackSize()) {
				toRet.add(tempPool.remove(AbstractDungeon.cardRandomRng.random(tempPool.size() - 1)));
			}
		}
		
		while (toRet.size() > BoosterHelper.getPackSize() && toRet.size() > 0) {
			toRet.remove(AbstractDungeon.cardRandomRng.random(toRet.size() - 1));
		}
		return toRet;
	}

	@Override
	public BoosterPack makeCopy() {
		return new FallbackPack();
	}


}
