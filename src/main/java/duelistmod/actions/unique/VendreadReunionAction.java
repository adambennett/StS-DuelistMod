package duelistmod.actions.unique;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.abstracts.*;
import duelistmod.cards.other.tempCards.*;
import duelistmod.helpers.SelectScreenHelper;
import duelistmod.powers.duelistPowers.VendreadReunionPower;
import duelistmod.variables.Tags;

public class VendreadReunionAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private final int magic;
	private ArrayList<AbstractCard> cards;
	private Map<UUID, MapCardItem> mapp;

	public VendreadReunionAction(int amount)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.magic = amount;
		this.cards = AbstractDungeon.player.hand.group;
		this.mapp = new HashMap<>();
	}

	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard card : cards)
			{
				if (card.hasTag(Tags.VENDREAD) && card.hasTag(Tags.MONSTER))
				{
					AbstractCard gridCard = card.makeStatEquivalentCopy();	
					mapp.put(gridCard.uuid, new MapCardItem(card));
			        gridCard.initializeDescription();
					tmp.addToTop(gridCard);
				}				
			}
			
			if (tmp.size() > 0)
			{
				//Collections.sort(tmp.group, GridSort.getComparator());
				//tmp.addToTop(new CancelCard());
				SelectScreenHelper.open(tmp, 1, "Choose a card for Vendread Reunion");
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
					if (this.p.hasPower(VendreadReunionPower.POWER_ID))
			    	{
			    		AbstractPower pow = p.getPower(VendreadReunionPower.POWER_ID);
			    		pow.amount += this.magic;
			    		pow.updateDescription();
			    	}
					else
					{
						AbstractCard ref = mapp.get(c.uuid).card;
						DuelistCard.applyPowerToSelf(new VendreadReunionPower(this.magic, ref));	
					}					
				}
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
