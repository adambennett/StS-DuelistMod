package duelistmod.actions.unique;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.helpers.*;
import duelistmod.ui.DuelistCardSelectScreen;
import duelistmod.variables.Tags;

public class HumanoidSlimeAction extends AbstractGameAction
{
	private final ArrayList<AbstractCard> cards;
	private final boolean canCancel;
	private int overflowsToTrigger = 0;

	public HumanoidSlimeAction(ArrayList<AbstractCard> cardsToChooseFrom, int cardsToChoose)
	{
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.amount = cardsToChoose;
		this.cards = cardsToChooseFrom;
		this.canCancel = true;
		this.overflowsToTrigger = 2;
		this.setValues(AbstractDungeon.player, AbstractDungeon.player, cardsToChoose);
	}

	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard gridCard : cards)
			{
				if (gridCard.hasTag(Tags.IS_OVERFLOW) || gridCard instanceof DuelistCard && gridCard.canUpgrade())
				{ 
					tmp.addToBottom(gridCard); 
				}				
			}
			
			if (tmp.group.size() == 0)
			{
				for (AbstractCard gridCard : cards)
				{
					if (gridCard.hasTag(Tags.IS_OVERFLOW) || gridCard instanceof DuelistCard && gridCard.canUpgrade())
					{ 
						tmp.addToBottom(gridCard); 
					}				
				}
			}
	
			tmp.group.sort(GridSort.getComparator());
			if (this.canCancel) { for (int i = 0; i < this.amount; i++) { tmp.addToTop(new CancelCard()); }}
			if (this.amount >= tmp.group.size())
			{
				this.confirmLogic(tmp.group);
			}
			else
			{
				String btmScreenTxt = "Choose " + this.amount + " Card to Upgrade and Overflow (twice)";
				if (this.amount != 1 ) { btmScreenTxt = "Choose " + this.amount + " Cards to Upgrade and Overflow (twice)"; }
				DuelistMod.duelistCardSelectScreen.open(false, tmp, this.amount, btmScreenTxt, this::confirmLogic);
			}
			tickDuration();
			return;
		}
		tickDuration();
	}

	private void confirmLogic(List<AbstractCard> selectedCards) {
		for (AbstractCard c : selectedCards)
		{
			if (!(c instanceof CancelCard))
			{
				if (c instanceof DuelistCard)
				{
					for (int i = 0; i < this.overflowsToTrigger; i++)
					{
						if (c.canUpgrade()) { c.upgrade(); }
						if (c.hasTag(Tags.IS_OVERFLOW)) { ((DuelistCard)c).triggerOverflowEffect(); }
					}
				}
			}
		}
	}

}
