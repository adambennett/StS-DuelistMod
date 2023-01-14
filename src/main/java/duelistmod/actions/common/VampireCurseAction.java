package duelistmod.actions.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Strings;

public class VampireCurseAction extends AbstractGameAction
{
	ArrayList<AbstractCard> cardsToMod;
	HashSet<UUID> cardsChecked = new HashSet<>();
	
	public VampireCurseAction(ArrayList<AbstractCard> card, int amount) {
		this.setValues(this.target, this.source, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.cardsToMod = card;
		this.amount = amount;
	}
	
	@Override
	public void update() 
	{
		if (this.cardsToMod.size() < 1 || this.amount < 1 || this.cardsChecked.size() == this.cardsToMod.size()) {
			this.isDone = true;
			return;
		}
		if (this.cardsToMod.size() == 1) {
			this.modify(this.cardsToMod.get(0));
			this.amount--;
			this.isDone = true;
			return;
		}

		int roll = AbstractDungeon.cardRandomRng.random(0, this.cardsToMod.size() - 1);
		if (this.modify(this.cardsToMod.get(roll))) {
			this.amount--;
		}
	}

	private boolean modify(AbstractCard cardToModify) {
		cardsChecked.add(cardToModify.uuid);
		if (!cardToModify.isEthereal && !cardToModify.type.equals(AbstractCard.CardType.CURSE)) {
			cardToModify.isEthereal = true;
			cardToModify.rawDescription = Strings.etherealForCardText + cardToModify.rawDescription;
			if (cardToModify instanceof DuelistCard) {
				((DuelistCard)cardToModify).fixUpgradeDesc();
			}
			cardToModify.initializeDescription();
			return true;
		}
		return false;
	}
	
}
