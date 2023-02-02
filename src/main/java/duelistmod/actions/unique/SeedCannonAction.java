package duelistmod.actions.unique;

import java.util.Collections;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tempCards.*;
import duelistmod.helpers.GridSort;
import duelistmod.powers.incomplete.SeedCannonPower;

public class SeedCannonAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private SeedCannonConfirm card;

	public SeedCannonAction(SeedCannonConfirm card)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.amount = 1;
		this.card = card;
	}

	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			tmp.addToTop(this.card);
			tmp.addToTop(new SplendidCancel());
			Collections.sort(tmp.group, GridSort.getComparator());
			AbstractDungeon.gridSelectScreen.open(tmp, this.amount, "Seed Cannon", false);
			tickDuration();
			return;
			
		}
		
		if ((AbstractDungeon.gridSelectScreen.selectedCards.size() != 0))
		{
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
			{
				c.unhover();
				c.stopGlowing();
				if (!(c instanceof CancelCard) && !(c instanceof SplendidCancel))
				{
					if (p.hasPower(SeedCannonPower.POWER_ID))
			    	{
			    		DuelistCard.removePower(p.getPower(SeedCannonPower.POWER_ID), p);
			    		DuelistCard.damageAllEnemiesThornsPoison(c.magicNumber);
			    	}    	 
				}
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
