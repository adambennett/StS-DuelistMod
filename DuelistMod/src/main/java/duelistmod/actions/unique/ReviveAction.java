package duelistmod.actions.unique;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tempCards.*;
import duelistmod.helpers.Util;
import duelistmod.variables.Tags;

public class ReviveAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private boolean upgrade;
	private AbstractMonster target;
	
	
	public ReviveAction(int amount, boolean upgraded)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = upgraded;
		this.amount = amount;
		this.target = AbstractDungeon.getRandomMonster();
	}
	
	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED && DuelistMod.entombedCardsCombat.size() > 0)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard card : DuelistMod.entombedCardsCombat)
			{
				if (!card.hasTag(Tags.EXEMPT))
				{
					AbstractCard gridCard = card;
					if (this.upgrade) { gridCard.upgrade(); }					
					tmp.addToTop(gridCard);					
				}
			}
			
			//Collections.sort(tmp.group, GridSort.getComparator());
			if (tmp.group.size() > 0)
			{
				for (int i = 0; i < this.amount; i++) { tmp.addToTop(new CancelCard()); }
				if (this.amount == 1) { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, "Choose " + this.amount + " Zombie to Revive", false, false, false, false); }
				else { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, "Choose " + this.amount + " Zombies to Revive", false, false, false, false); }	
				tickDuration();
				return;				
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
				AbstractCard orig = c;
				c = c.makeStatEquivalentCopy();
				boolean resummoned = false;
				if (!(c instanceof CancelCard) && !(c instanceof SplendidCancel))
				{
					if (c instanceof DuelistCard && this.target != null)
					{
						DuelistCard.fullResummon((DuelistCard)c, false, this.target, false);
						Util.log("ReviveAction :: fullResummon triggered with " + c.name);
						resummoned = true;
					}
					else if (c instanceof DuelistCard)
					{
						Util.log("Null target, generating new random monster");
						AbstractMonster mon = AbstractDungeon.getRandomMonster();
						if (mon != null) { DuelistCard.fullResummon((DuelistCard)c, false, mon, false); resummoned = true; }
						else { Util.log("ReviveAction is still finding a null target, so we are skipping this.");}
					}
					
					if (resummoned)
					{
						if (DuelistMod.entombedCardsCombat.contains(orig))
						{
							DuelistMod.entombedCardsCombat.remove(orig);
							Util.log("Found selected Revive card in entombed list, removing for combat...");
						}
						else
						{
							Util.log("Did NOT find selected Revive card in entombed list.");
						}
					}
					else { Util.log("For some reason, ReviveAction did not resummon " + c.cardID); }
				}
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
