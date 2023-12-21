package duelistmod.actions.common;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.helpers.GridSort;
import duelistmod.helpers.SelectScreenHelper;

public class DiamondDustAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private ArrayList<AbstractCard> cards;
	private boolean canCancel = false;
	private int costAdd = 0;
	private HashMap<UUID, AbstractCard> originalMap = new HashMap<>();
	
	public DiamondDustAction(ArrayList<AbstractCard> cardsToChooseFrom, int amountToChoose, int costAdd)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;	
		this.amount = amountToChoose;
		this.cards = cardsToChooseFrom;
		this.canCancel = true;
		this.costAdd = costAdd;
	}
	
	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard c : cards) 
			{ 				
				boolean disallow = false;
				if (c.type.equals(CardType.STATUS) || c.type.equals(CardType.CURSE)) { disallow = true; }
				if (!disallow)
				{
					if (c.costForTurn > 0 || c.cost > 0) 
					{
						AbstractCard copy = c.makeStatEquivalentCopy();
						tmp.addToTop(copy);
						originalMap.put(copy.uuid, c);
					}
				}
			}
			Collections.sort(tmp.group, GridSort.getComparator());
			//if (this.canCancel && tmp.group.size() > 0) { for (int i = 0; i < this.amount; i++) { tmp.addToTop(new CancelCard()); }}
			if (this.amount == 1 && tmp.group.size() > 0) { SelectScreenHelper.open(tmp, this.amount, "Choose " + this.amount + " Card to Modify"); }
			else if (tmp.group.size() > 0) { SelectScreenHelper.open(tmp, this.amount,  "Choose " + this.amount + " Cards to Modify"); }
			tickDuration();
			return;
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
					modify(original, costAdd);
				}
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
			this.p.hand.glowCheck();
		}
		tickDuration();
	}
	
	private void modify(AbstractCard original, int costAdd)
	{
		original.modifyCostForCombat(costAdd);
	}
}
