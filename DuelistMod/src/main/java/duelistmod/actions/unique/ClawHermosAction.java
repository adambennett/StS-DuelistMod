package duelistmod.actions.unique;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import basemod.BaseMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.helpers.GridSort;
import duelistmod.variables.*;

public class ClawHermosAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private boolean upgrade;
	private ArrayList<AbstractCard> cards;
	private boolean sendExtraToDiscard = false;
	private boolean canCancel = false;
	private int defend = 0;
	
	public ClawHermosAction(ArrayList<AbstractCard> cardsToChooseFrom, int amount, int defend)
	{
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = false;
		this.amount = amount;
		this.cards = cardsToChooseFrom;
		this.sendExtraToDiscard = true;
		this.canCancel = true;
		this.defend = defend;
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
				tmp.addToBottom(gridCard);				
			}
			Collections.sort(tmp.group, GridSort.getComparator());
			if (this.canCancel) { for (int i = 0; i < this.amount; i++) { tmp.addToTop(new CancelCard()); }}
			if (this.amount >= tmp.group.size())
			{
				for (AbstractCard c : tmp.group)
				{
					if (!(c instanceof CancelCard))
					{
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
				if (!(c instanceof CancelCard))
				{
					if (this.p.hand.size() == BaseMod.MAX_HAND_SIZE)
					{
						this.p.createHandIsFullDialog();
						if (sendExtraToDiscard) { this.p.discardPile.addToTop(c); }
						if (c.hasTag(Tags.EXEMPT)) { DuelistCard.staticBlock(this.defend); }
					}
					else
					{
						AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c));
						if (c.hasTag(Tags.EXEMPT)) { DuelistCard.staticBlock(this.defend); }
					}
					this.p.hand.refreshHandLayout();
					this.p.hand.applyPowers();
				}				
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
