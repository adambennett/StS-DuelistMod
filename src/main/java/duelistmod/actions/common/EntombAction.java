package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tempCards.*;
import duelistmod.helpers.SelectScreenHelper;
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
				if (Util.canEntomb(card) && card.hasTag(Tags.ZOMBIE) && !card.hasTag(Tags.NO_ENTOMB) && !Util.isExempt(card) && card instanceof DuelistCard)
				{
					tmp.addToTop(card);
				}
			}
			
			if (tmp.group.size() > 0)
			{
				if (this.amount == 1) { SelectScreenHelper.open(tmp, this.amount, "Choose " + this.amount + " a Zombie to Entomb"); }
				else { SelectScreenHelper.open(tmp, this.amount, "Choose " + this.amount + " Zombies to Entomb"); }
				tickDuration();
				return;
			}
		}
		
		if ((AbstractDungeon.gridSelectScreen.selectedCards.size() != 0))
		{
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
			{
				c.unhover();
				c.stopGlowing();
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
