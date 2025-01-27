package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.helpers.GridSort;
import duelistmod.helpers.SelectScreenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class CardSelectScreenModifyStatAction extends AbstractGameAction {
	private final AbstractPlayer p;
	private final ArrayList<AbstractCard> cards;
	private final int newCost;
	private final int newTributeCost;
	private final HashMap<UUID, AbstractCard> originalMap = new HashMap<>();
	private final boolean modifyCost;
	private final boolean modifyTributes;
	private final boolean costForTurn;
	private final boolean tributesForTurn;

	public CardSelectScreenModifyStatAction(ArrayList<AbstractCard> cardsToChooseFrom, int amountToChoose, int newCost, int newTributeCost) {
		this(cardsToChooseFrom, amountToChoose, newCost, newTributeCost, true, true, true, true);
	}

	public CardSelectScreenModifyStatAction(ArrayList<AbstractCard> cardsToChooseFrom, int amountToChoose, int newCost, int newTributeCost, boolean modifyCost, boolean modifyTributes, boolean costForTurn, boolean tributeForTurn) {
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;	
		this.amount = amountToChoose;
		this.cards = cardsToChooseFrom;
		this.newCost = newCost;
		this.newTributeCost = newTributeCost;
		this.modifyCost = modifyCost;
		this.modifyTributes = modifyTributes;
		this.costForTurn = costForTurn;
		this.tributesForTurn = tributeForTurn;
	}

	public void update() {
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED) {
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard c : cards) {
				DuelistCard ref = null;
				boolean refIsLive = false;
				boolean disallow = false;
				if (c instanceof DuelistCard) {
					ref = (DuelistCard)c;
				}
				if (ref != null) {
					if (ref.isTributeCard() && ref.tributes != this.newCost) {
						refIsLive = true;
					}
				}
				if (c.type.equals(CardType.STATUS) || c.type.equals(CardType.CURSE)) {
					disallow = true;
				}
				if (!disallow) {
					if (c.costForTurn != this.newCost || c.cost != this.newCost || refIsLive) {
						AbstractCard copy = c.makeStatEquivalentCopy();
						tmp.addToTop(copy);
						originalMap.put(copy.uuid, c);
					}
				}
			}
			
			if (tmp.group.size() > 0) {
				tmp.group.sort(GridSort.getComparator());
				//if (this.canCancel && tmp.group.size() > 0) { for (int i = 0; i < this.amount; i++) { tmp.addToTop(new CancelCard()); }}
				if (this.amount == 1 && tmp.group.size() > 0) {
					SelectScreenHelper.open(tmp, this.amount, "Choose " + this.amount + " Card to Modify");
				} else if (tmp.group.size() > 0) {
					SelectScreenHelper.open(tmp, this.amount,  "Choose " + this.amount + " Cards to Modify");
				}
				tickDuration();
				return;
			}			
		}
		
		if ((AbstractDungeon.gridSelectScreen.selectedCards.size() != 0)) {
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
				c.unhover();
				c.stopGlowing();
				if (!(c instanceof CancelCard)) {
					AbstractCard original = originalMap.get(c.uuid);
					modify(original, this.newCost, this.newTributeCost);
				}
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
			DuelistCard.glowCheck();
		}
		tickDuration();
	}
	
	private void modify(AbstractCard original, int newCost, int newTrib) {
		if (this.modifyCost && original.cost >= 0) {
			if (this.costForTurn) {
				original.setCostForTurn(-original.costForTurn + newCost);
			} else {
				original.modifyCostForCombat(-original.cost + newCost);
			}
		}
		
		if (this.modifyTributes && original instanceof DuelistCard) {
			DuelistCard dc = (DuelistCard)original;
			if (this.tributesForTurn) {
				dc.setTributesForTurn(newTrib);
			} else {
				dc.setTributesForCombat(newTrib);
			}
		}
	}
}
