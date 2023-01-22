package duelistmod.actions.unique;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.SummonAction;
import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.helpers.*;
import duelistmod.interfaces.ImmutableList;
import duelistmod.ui.DuelistCardSelectScreen;

public class AquamirrorCycleAction extends AbstractGameAction
{
	private final ImmutableList<DuelistCard> cards;
	private final boolean canCancel;
	private final int summonAmt;

	public AquamirrorCycleAction(ImmutableList<DuelistCard> cardsToChooseFrom, int amount, int summonAmount)
	{
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.amount = amount;
		this.cards = cardsToChooseFrom;
		this.canCancel = true;
		this.summonAmt = summonAmount;
		this.setValues(AbstractDungeon.player, AbstractDungeon.player, amount);
	}

	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard gridCard : cards)
			{
				tmp.addToBottom(gridCard);
			}
	
			tmp.group.sort(GridSort.getComparator());
			if (this.amount >= tmp.group.size())
			{
				this.confirmLogic(tmp.group);
			}
			else
			{
				if (this.canCancel) { for (int i = 0; i < this.amount; i++) { tmp.addToTop(new CancelCard()); }}
				String btmScreenTxt = "Choose " + this.amount + " Card to Summon";
				if (this.amount != 1 ) { btmScreenTxt = "Choose " + this.amount + " Cards to Summon"; }
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
					DuelistCard dc = (DuelistCard)c;
					this.addToBot(new SummonAction(this.summonAmt, dc));
				}
			}
		}
	}

}
