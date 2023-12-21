package duelistmod.actions.common;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.cards.other.tokens.Token;
import duelistmod.helpers.GridSort;
import duelistmod.helpers.SelectScreenHelper;

public class CardSelectScreenModifyStatAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private ArrayList<AbstractCard> cards;
	private boolean canCancel = true;
	private int newCost = 0;
	private int newTribCost = 0;
	private HashMap<UUID, AbstractCard> originalMap = new HashMap<>();
	private boolean modifyCost;
	private boolean modifyTribs;
	private boolean costForTurn;
	private boolean tribForTurn;
	
	public CardSelectScreenModifyStatAction(ArrayList<AbstractCard> cardsToChooseFrom, int amountToChoose, int newCost, int newTribCost, boolean forTurnCost, boolean forTurnTrib)
	{
		this(cardsToChooseFrom, amountToChoose, newCost, newTribCost, true, true, true, forTurnCost, forTurnTrib);
	}
	
	public CardSelectScreenModifyStatAction(ArrayList<AbstractCard> cardsToChooseFrom, int amountToChoose, int newCost, int newTribCost, boolean forTurn)
	{
		this(cardsToChooseFrom, amountToChoose, newCost, newTribCost, true, true, true, forTurn, forTurn);
	}
	
	public CardSelectScreenModifyStatAction(ArrayList<AbstractCard> cardsToChooseFrom, int amountToChoose, int newCost, int newTribCost)
	{
		this(cardsToChooseFrom, amountToChoose, newCost, newTribCost, true, true, true, true, true);
	}

	public CardSelectScreenModifyStatAction(ArrayList<AbstractCard> cardsToChooseFrom, int amountToChoose, int newCost, int newTribCost, boolean canCancel, boolean modifyCost, boolean modifyTributes, boolean costForTurn, boolean tributeForTurn)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;	
		this.amount = amountToChoose;
		this.cards = cardsToChooseFrom;
		this.canCancel = canCancel;
		this.newCost = newCost;
		this.newTribCost = newTribCost;
		this.modifyCost = modifyCost;
		this.modifyTribs = modifyTributes;
		this.costForTurn = costForTurn;
		this.tribForTurn = tributeForTurn;
	}

	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard c : cards) 
			{ 
				DuelistCard ref = null;
				boolean refIsLive = false;
				boolean disallow = false;
				if (c instanceof DuelistCard) { ref = (DuelistCard)c; }
				if (ref != null) { if (ref.isTributeCard() && ref.tributes != this.newCost) { refIsLive = true; }}
				if (c.type.equals(CardType.STATUS) || c.type.equals(CardType.CURSE)) { disallow = true; }
				if (!disallow)
				{
					if (c.costForTurn != this.newCost || c.cost != this.newCost || refIsLive) 
					{
						AbstractCard copy = c.makeStatEquivalentCopy();
						tmp.addToTop(copy);
						originalMap.put(copy.uuid, c);
					}
				}
			}
			
			if (tmp.group.size() > 0)
			{
				Collections.sort(tmp.group, GridSort.getComparator());
				//if (this.canCancel && tmp.group.size() > 0) { for (int i = 0; i < this.amount; i++) { tmp.addToTop(new CancelCard()); }}
				if (this.amount == 1 && tmp.group.size() > 0) { SelectScreenHelper.open(tmp, this.amount, "Choose " + this.amount + " Card to Modify"); }
				else if (tmp.group.size() > 0) { SelectScreenHelper.open(tmp, this.amount,  "Choose " + this.amount + " Cards to Modify"); }
				tickDuration();
				return;
			}			
		}
		
		if ((AbstractDungeon.gridSelectScreen.selectedCards.size() != 0))
		{
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
			{
				c.unhover();
				c.stopGlowing();
				if (!(c instanceof CancelCard))
				{
					AbstractCard original = originalMap.get(c.uuid);
					modify(original, this.newCost, this.newTribCost);
				}
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
			this.p.hand.glowCheck();
		}
		tickDuration();
	}
	
	private void modify(AbstractCard original, int newCost, int newTrib)
	{
		if (this.modifyCost && original.cost >= 0)
		{
			if (this.costForTurn)
			{
				original.setCostForTurn(-original.costForTurn + newCost);
			}
			else
			{
				original.modifyCostForCombat(-original.cost + newCost);
			}
		}
		
		if (this.modifyTribs && original instanceof DuelistCard)
		{
			DuelistCard dc = (DuelistCard)original;
			if (this.tribForTurn)
			{
				dc.modifyTributesForTurn(newTrib);
			}
			else
			{
				dc.modifyTributes(newTrib);
			}
		}
	}
}
