package duelistmod.actions.unique;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import basemod.BaseMod;
import duelistmod.*;
import duelistmod.helpers.GridSort;
import duelistmod.variables.Strings;

public class GraverobberAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private boolean upgrade;
	private ArrayList<AbstractCard> cards;
	private boolean sendExtraToDiscard = false;
	private int costToChangeTo = 1;

	
	public GraverobberAction(int cardCost, ArrayList<AbstractCard> cardsToChooseFrom)
	{
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, 1);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = false;
		this.amount = 1;
		this.cards = cardsToChooseFrom;
		this.sendExtraToDiscard = false;
		this.costToChangeTo = cardCost;
	}

	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard gridCard : cards)
			{
				if (this.upgrade) { gridCard.upgrade(); }
				if (gridCard.costForTurn != costToChangeTo && gridCard.costForTurn > -1)
				{
					gridCard.costForTurn += (-gridCard.cost + costToChangeTo);
					if (gridCard.costForTurn != gridCard.cost) { gridCard.isCostModifiedForTurn = true; }
				}				
				tmp.addToTop(gridCard);
				if (DuelistMod.debug)
				{
					System.out.println("theDuelist:GraverobberAction:update() ---> added " + gridCard.originalName + " into grid selection pool");
				}
			}
			
			Collections.sort(tmp.group, GridSort.getComparator());
			if (this.amount >= tmp.group.size())
			{
				for (AbstractCard c : tmp.group)
				{
					
					if (this.p.hand.size() >= BaseMod.MAX_HAND_SIZE)
					{
						this.p.createHandIsFullDialog();
						if (sendExtraToDiscard) { this.p.discardPile.addToTop(c); }
					}
					else
					{
						AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c));
					}
					this.p.hand.refreshHandLayout();
					this.p.hand.applyPowers();
				}
				
				AbstractDungeon.gridSelectScreen.selectedCards.clear();
				this.p.hand.refreshHandLayout();
			}
			
			else
			{
				if (this.amount == 1) { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, Strings.configChooseString + this.amount + Strings.configAddCardHandString, false); }
				else { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, Strings.configChooseString + this.amount + Strings.configAddCardHandPluralString, false); }
			}
			tickDuration();
			return;
		}
		
		// If there are more cards to add to hand still and player hand has space, or if there are still cards to hand and we intend to discard them if the players hand has no space
		if ((AbstractDungeon.gridSelectScreen.selectedCards.size() != 0 && this.p.hand.size() < BaseMod.MAX_HAND_SIZE) || (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0 && sendExtraToDiscard))
		{
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
			{
				c.unhover();
				//c.costForTurn += (-c.cost + costToChangeTo);
				//c.isCostModifiedForTurn = true;
				if (this.p.hand.size() == BaseMod.MAX_HAND_SIZE)
				{
					this.p.createHandIsFullDialog();
					if (sendExtraToDiscard) { this.p.discardPile.addToTop(c); }
				}
				else
				{
					AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c));
				}
				this.p.hand.refreshHandLayout();
				this.p.hand.applyPowers();
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
