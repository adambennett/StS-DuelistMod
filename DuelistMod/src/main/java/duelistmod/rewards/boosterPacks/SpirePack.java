package duelistmod.rewards.boosterPacks;

import java.util.*;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.DuelistMod;
import duelistmod.helpers.*;
import duelistmod.rewards.BoosterPack;

public class SpirePack extends BoosterPack
{

	public SpirePack() {
		super("Spire Pack", "RareBooster");
		this.rarity = PackRarity.RARE;
		this.textColor = Color.YELLOW;
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
	public boolean canSpawn() { if (DuelistMod.baseGameCards) { return true; } return false; }
	
	@Override
	public ArrayList<AbstractCard> getCards()
	{
		ArrayList<AbstractCard> toRet = new ArrayList<>();
		ArrayList<AbstractCard> orbCards = new ArrayList<>();
		for (AbstractCard c : BaseGameHelper.getAllBaseGameCards(false)) { orbCards.add(c.makeCopy()); }
		Map<String, String> map = new HashMap<>();
		while (toRet.size() < BoosterHelper.getPackSize())
		{
			AbstractCard c = orbCards.get(AbstractDungeon.cardRandomRng.random(orbCards.size() - 1));
			while (map.containsKey(c.cardID)) { c = orbCards.get(AbstractDungeon.cardRandomRng.random(orbCards.size() - 1)); }
			toRet.add(c.makeCopy());
			map.put(c.cardID, c.cardID);
		}
		return toRet;
	}

	@Override
	public BoosterPack makeCopy() {
		return new SpirePack();
	}


}
