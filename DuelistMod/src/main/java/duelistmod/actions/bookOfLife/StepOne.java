package duelistmod.actions.bookOfLife;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import duelistmod.cards.other.bookOfLifeOptions.CustomCardOption;
import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.helpers.Util;

public class StepOne extends AbstractGameAction
{
	private AbstractPlayer p;
	private boolean canCancel = true;
	private boolean upgrade = false;

	public StepOne(boolean upGrade)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = upGrade;
	}
	
	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			
			if (EnergyPanel.getCurrentEnergy() >= 1) { tmp.addToTop(new CustomCardOption(0, 0, 1)); }
			if (EnergyPanel.getCurrentEnergy() >= 2) { tmp.addToTop(new CustomCardOption(0, 1, 2)); }
			if (EnergyPanel.getCurrentEnergy() >= 3) { tmp.addToTop(new CustomCardOption(0, 2, 3)); }
			if (EnergyPanel.getCurrentEnergy() >= 5) { tmp.addToTop(new CustomCardOption(0, 38, 5)); }
			Util.log("Current energy detected by Book of Life=" + EnergyPanel.getCurrentEnergy());
			
			//Collections.sort(tmp.group, GridSort.getComparator());
			if (tmp.group.size() > 0)
			{
				if (this.canCancel) { for (int i = 0; i < 1; i++) { tmp.addToTop(new CancelCard()); }}
				AbstractDungeon.gridSelectScreen.open(tmp, 1, "Book of Life - Step 1 - Choose where to send the custom card", false, false, false, false); 	
			}
			else
			{
				this.addToBot(new TalkAction(true, "I need #rEnergy to activate Book of Life!", 1.0F, 2.0F));
			}

		}
		
		if ((AbstractDungeon.gridSelectScreen.selectedCards.size() != 0))
		{
			ArrayList<AbstractCard> reverseList = new ArrayList<>();
			for (int i = AbstractDungeon.gridSelectScreen.selectedCards.size() - 1; i > -1; i--)
			{
				reverseList.add(AbstractDungeon.gridSelectScreen.selectedCards.get(i));
			}
			for (AbstractCard c : reverseList)
			{
				c.unhover();
				if (c instanceof CustomCardOption)
				{
					CustomCardOption ref = (CustomCardOption)c;
					if (ref.descInd == 0) { this.addToBot(new StepTwo(false, true, false, this.upgrade)); AbstractDungeon.player.energy.use(ref.magicNumber); }
					else if (ref.descInd == 1) { this.addToBot(new StepTwo(false, false, true, this.upgrade)); AbstractDungeon.player.energy.use(ref.magicNumber); }
					else if (ref.descInd == 2) { this.addToBot(new StepTwo(false, true, true, this.upgrade)); AbstractDungeon.player.energy.use(ref.magicNumber); }
					else if (ref.descInd == 38) { this.addToBot(new StepTwo(true, false, false, this.upgrade)); AbstractDungeon.player.energy.use(ref.magicNumber); }
					else { Util.log("Step 1: Bad option index found from selection screen. CustomCardOption.descInd=" + ref.descInd); }
				}				
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
