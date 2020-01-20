package duelistmod.rewards.boosterPacks;

import java.util.*;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.DuelistCardLibrary;
import duelistmod.helpers.*;
import duelistmod.rewards.BoosterPack;

public class OrbPack extends BoosterPack
{

	public OrbPack() {
		super("Orb Pack", "OrbBooster");
		this.rarity = PackRarity.UNCOMMON;
		this.textColor = Color.BLUE;
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
	public boolean canSpawn() 
	{
		if (Util.deckIs("Spellcaster Deck")) { return true; }
		return false; 
	}
	
	@Override
	public ArrayList<AbstractCard> getCards()
	{
		ArrayList<AbstractCard> toRet = new ArrayList<>();
		ArrayList<AbstractCard> orbCards = new ArrayList<>();
		for (AbstractCard c : DuelistCardLibrary.orbCardsForGeneration()) { orbCards.add(c.makeCopy()); }
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
		return new OrbPack();
	}


}
