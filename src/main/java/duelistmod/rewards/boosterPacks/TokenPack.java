package duelistmod.rewards.boosterPacks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;

import duelistmod.DuelistCardLibrary;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.*;
import duelistmod.rewards.BoosterPack;

public class TokenPack extends BoosterPack
{

	public TokenPack() {
		super("Token Pack", "UncommonBooster");
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
        return Util.deckIs("Machine Deck");
    }
	
	@Override
	public ArrayList<AbstractCard> getCards()
	{
		ArrayList<AbstractCard> toRet = new ArrayList<>();
		ArrayList<DuelistCard> tokens = DuelistCardLibrary.getTokensForCombat();
		ArrayList<AbstractCard> abTok = new ArrayList<>();
		abTok.addAll(tokens);
		toRet.addAll(this.findAllCards(abTok, BoosterHelper.getPackSize(), toRet));
		return toRet;
	}

	@Override
	public BoosterPack makeCopy() {
		return new TokenPack();
	}


}
