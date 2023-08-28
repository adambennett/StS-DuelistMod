package duelistmod.actions.unique;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.helpers.GridSort;
import duelistmod.helpers.Util;
import duelistmod.variables.Tags;

public class FangCritiasAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private boolean upgrade;
	private ArrayList<DuelistCard> cards;
	private AbstractMonster target;
	private boolean canCancel = false;

	public FangCritiasAction(ArrayList<DuelistCard> cardsToChooseFrom, int amount, AbstractMonster m)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = false;
		this.amount = amount;
		this.cards = cardsToChooseFrom;
		this.canCancel = true;
		this.target = m;
	}
	
	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard card : cards)
			{
				AbstractCard gridCard = card.makeStatEquivalentCopy();
				if (this.upgrade) { gridCard.upgrade(); }
		        gridCard.initializeDescription();
				tmp.addToTop(gridCard);
				if (DuelistMod.debug) { System.out.println("theDuelist:EyeTimaeusAction:update() ---> added " + gridCard.originalName + " into grid selection pool"); }
			}
			
			Collections.sort(tmp.group, GridSort.getComparator());
			if (this.canCancel) { for (int i = 0; i < this.amount; i++) { tmp.addToTop(new CancelCard()); }}
			if (this.amount == 1) { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, "Choose any Dragon to Special Summon (Exempt monsters will give Block instead)", false); }
			else { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, "Choose " + this.amount + " Dragons to Special Summon (Exempt monsters will give Block instead)", false); }
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
					if (c instanceof DuelistCard && !Util.isExempt(c))
					{
						DuelistCard.fullResummon((DuelistCard)c, false, this.target, false);
					}
					else if (c instanceof DuelistCard && Util.isExempt(c))
					{
						DuelistCard.addCardToHand(c);
					}
				}
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
