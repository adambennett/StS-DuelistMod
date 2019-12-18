package duelistmod.actions.common;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tempCards.*;
import duelistmod.helpers.Util;
import duelistmod.variables.Tags;

public class EntombAction extends AbstractGameAction
{
	private AbstractPlayer p;

	public EntombAction(int amount)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.amount = amount;
	}

	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard card : this.p.masterDeck.group)
			{
				if (card.hasTag(Tags.ZOMBIE) && !card.hasTag(Tags.NO_ENTOMB) && !card.hasTag(Tags.EXEMPT) && card instanceof DuelistCard)
				{
					tmp.addToTop(card);
				}
			}
			
			//Collections.sort(tmp.group, GridSort.getComparator());
			for (int i = 0; i < this.amount; i++) { tmp.addToTop(new CancelCard()); }
			if (this.amount == 1) { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, "Choose " + this.amount + " a Zombie to Entomb", false, false, false, false); }
			else { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, "Choose " + this.amount + " Zombies to Entomb", false, false, false, false); }
			tickDuration();
			return;
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
				if (!(c instanceof CancelCard) && !(c instanceof SplendidCancel))
				{
					Util.log("Entombing from selection screen: " + c.name);
					this.addToBot(new InstantEntombAction(c));
				}
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
