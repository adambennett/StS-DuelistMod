package duelistmod.rewards.boosterPacks;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;

import duelistmod.rewards.BoosterPack;
import duelistmod.variables.Tags;

public class MetronomeBoosterU extends BoosterPack
{

	public MetronomeBoosterU() {
		super("Metronome Pack", "UncommonBooster");
		this.rarity = PackRarity.UNCOMMON;
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
	public boolean canSpawn() { return true; }
	
	@Override
	public ArrayList<AbstractCard> getCards()
	{
		ArrayList<AbstractCard> toRet = new ArrayList<>();
		toRet.addAll(this.findAllCards(Tags.METRONOME, CardRarity.COMMON, 3, toRet));
		toRet.addAll(this.findAllCards(Tags.METRONOME, CardRarity.UNCOMMON, 2, toRet));
		return toRet;
	}

	@Override
	public BoosterPack makeCopy() {
		return new MetronomeBoosterU();
	}


}
