package duelistmod.actions.common;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.helpers.*;
import duelistmod.powers.duelistPowers.SeaDwellerPower;
import duelistmod.variables.Tags;

public class CardSelectScreenTriggerOverflowAction extends AbstractGameAction
{
	private final ArrayList<AbstractCard> cards;
	private final boolean canCancel;
	private final int overflowsToTrigger;
	private final boolean decMagic;

	public CardSelectScreenTriggerOverflowAction(ArrayList<AbstractCard> cardsToChooseFrom, int cardsToChoose, int overflows, boolean decrementMagic)
	{
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.amount = cardsToChoose;
		this.cards = cardsToChooseFrom;
		this.canCancel = true;
		this.overflowsToTrigger = overflows;
		this.decMagic = decrementMagic;
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
				if (gridCard.hasTag(Tags.IS_OVERFLOW) && gridCard instanceof DuelistCard)
				{ 
					tmp.addToBottom(gridCard); 
				}				
			}
	
			tmp.group.sort(GridSort.getComparator());
			if (this.amount >= tmp.group.size())
			{
				this.confirmLogic(tmp.group);
			}
			else
			{
				String btmScreenTxt;
				if (this.decMagic)
				{
					btmScreenTxt = "Choose " + this.amount + " Card to Overflow";
					if (this.amount != 1 ) { btmScreenTxt = "Choose " + this.amount + " Cards to Overflow"; }
				}
				else
				{
					btmScreenTxt = "Choose " + this.amount + " Overflow Card to Trigger";
					if (this.amount != 1 ) { btmScreenTxt = "Choose " + this.amount + " Overflow Cards to Trigger"; }
				}
				DuelistMod.duelistCardSelectScreen.open(false, tmp, this.amount, btmScreenTxt, this::confirmLogic);
			}
			tickDuration();
			return;
		}
		tickDuration();
	}

	private void confirmLogic(List<AbstractCard> selectedCards) {
		for (AbstractCard c : selectedCards) {
			c.unhover();
			if (!(c instanceof CancelCard)) {
				if (c instanceof DuelistCard) {
					for (int i = 0; i < this.overflowsToTrigger; i++) {
						if (c.magicNumber > 0 || !this.decMagic) {
							((DuelistCard)c).triggerOverflowEffect();
							if (!AbstractDungeon.player.hasPower(SeaDwellerPower.POWER_ID) && this.decMagic) {
								c.baseMagicNumber--;
								if (c.baseMagicNumber < 0) { c.baseMagicNumber = 0; }
								c.magicNumber = c.baseMagicNumber;
								c.unhover();
							}
						}
					}
				}
			}
		}
	}

}
