package duelistmod.rewards.boosterPacks;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;

import duelistmod.rewards.BoosterPack;
import duelistmod.variables.Tags;

public class MetronomeBoosterR extends BoosterPack
{

	public MetronomeBoosterR() {
		super("Metronome Pack", "RareBooster");
		this.rarity = PackRarity.RARE;
		this.obeyPackSize = true;
		this.allowBasics = false;
		this.onlyBasics = false;
		this.alwaysUpgrade = false;
		this.alwaysUpgradeAtk = false;
		this.alwaysUpgradeSkill = false;
		this.alwaysUpgradePower = false;
		this.alwaysUpgradeMon = false;
		this.alwaysUpgradeSpell = true;
		this.alwaysUpgradeTrap = true;
		checkRelics();
	}
	
	@Override
	public boolean canSpawn() { return true; }
	
	@Override
	public ArrayList<AbstractCard> getCards()
	{
		ArrayList<AbstractCard> toRet = new ArrayList<>();
		toRet.addAll(this.findAllCards(Tags.METRONOME, CardRarity.COMMON, 2, toRet));
		toRet.addAll(this.findAllCards(Tags.METRONOME, CardRarity.UNCOMMON, 1, toRet));
		toRet.addAll(this.findAllCards(Tags.METRONOME, CardRarity.RARE, 1, toRet));
		return toRet;
	}

	@Override
	public BoosterPack makeCopy() {
		return new MetronomeBoosterR();
	}


}
