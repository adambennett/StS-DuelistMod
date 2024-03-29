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
import duelistmod.helpers.SelectScreenHelper;
import duelistmod.helpers.Util;

public class EyeTimaeusAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private boolean upgrade;
	private ArrayList<AbstractCard> cards;
	private AbstractMonster target;
	private boolean canCancel = false;
	private int blockMagicNumber = 0;

	public EyeTimaeusAction(ArrayList<AbstractCard> cardsToChooseFrom, int amount, int blockMagic, AbstractMonster m)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = false;
		this.amount = amount;
		this.cards = cardsToChooseFrom;
		this.canCancel = true;
		this.target = m;
		this.blockMagicNumber = blockMagic;
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
			//if (this.canCancel) { for (int i = 0; i < this.amount; i++) { tmp.addToTop(new CancelCard()); }}
			if (this.amount == 1) { SelectScreenHelper.open(tmp, this.amount, "Choose any Dragon to Special Summon (Exempt monsters will give Block instead)"); }
			else { SelectScreenHelper.open(tmp, this.amount, "Choose " + this.amount + " Dragons to Special Summon (Exempt monsters will give Block instead)"); }
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
					if (!Util.isExempt(c))
					{
						DuelistCard.resummon(c, this.target);
					}
					else if (Util.isExempt(c))
					{
						DuelistCard.staticBlock(this.blockMagicNumber);
					}
				}
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
