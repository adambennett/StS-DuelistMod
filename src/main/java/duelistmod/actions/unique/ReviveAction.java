package duelistmod.actions.unique;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tempCards.*;
import duelistmod.helpers.Util;

public class ReviveAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private boolean noSoulCost = false;
	private AbstractMonster target;
	
	public ReviveAction(int amount, boolean noSoulCost)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.noSoulCost = noSoulCost;
		this.amount = amount;
		this.target = AbstractDungeon.getRandomMonster();
	}
	
	public void update()
	{
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			int amtAllowed = this.amount;
			while (!Util.canRevive(amtAllowed, this.noSoulCost) && amtAllowed > 0) { amtAllowed--; }
			if (Util.canRevive(amtAllowed, this.noSoulCost))
			{
				CardGroup tmp;
				int maxSize = DuelistCard.getCurrentReviveListSize();				
				ArrayList<AbstractCard> tmpList = new ArrayList<>();
				for (AbstractCard c : DuelistMod.entombedCardsCombat) { 
					if (DuelistCard.allowResummonsRevive(c)) { tmpList.add(c); }
				}
				tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
				if (tmpList.size() > maxSize)
				{
					while (tmpList.size() > 0 && tmp.group.size() < maxSize) {
						tmp.group.add(tmpList.remove(AbstractDungeon.cardRandomRng.random(tmpList.size() - 1)));
					}
				}
				else { tmp.group.addAll(tmpList); }
				if (tmp.group.size() > 0)
				{
					int pickAmt = this.amount;
					while ((pickAmt > tmp.group.size() || pickAmt > amtAllowed) && pickAmt > 0) {
						pickAmt--;
					}
					for (int i = 0; i < pickAmt; i++) { tmp.addToTop(new CancelCard()); }
					if (pickAmt == 1) { AbstractDungeon.gridSelectScreen.open(tmp, pickAmt, "Choose " + pickAmt + " Card to Revive", false, false, false, false); }
					else if (pickAmt > 0) { AbstractDungeon.gridSelectScreen.open(tmp, pickAmt, "Choose " + pickAmt + " Cards to Revive", false, false, false, false); }	
					else if (tmp.group.size() > 0) { this.addToBot(new TalkAction(true, "Not enough #rSouls", 1.0F, 2.0F)); }
					tickDuration();
					return;				
				}
			}
			else
			{
				this.addToBot(new TalkAction(true, "Not enough #rSouls", 1.0F, 2.0F));
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
				AbstractCard orig = c;
				c = c.makeStatEquivalentCopy();
				boolean resummoned = false;
				if (!(c instanceof CancelCard) && !(c instanceof SplendidCancel))
				{
					if (this.target != null)
					{
						DuelistCard.runRevive(c, this.target, this.noSoulCost);
						Util.log("ReviveAction :: fullResummon triggered with " + c.name);
						resummoned = true;												
					}
					else
					{
						Util.log("Null target, generating new random monster");
						AbstractMonster mon = AbstractDungeon.getRandomMonster();
						if (mon != null) { DuelistCard.runRevive(c,  mon, this.noSoulCost); resummoned = true; }
						else { Util.log("ReviveAction is still finding a null target, so we are skipping this.");}						
					}
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
				else { 
					Util.log("For some reason, ReviveAction did not resummon " + c.cardID + " so we are refuding your Souls"); 
				}
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
