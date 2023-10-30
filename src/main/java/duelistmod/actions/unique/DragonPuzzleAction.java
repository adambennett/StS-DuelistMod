package duelistmod.actions.unique;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tempCards.*;
import duelistmod.helpers.*;

public class DragonPuzzleAction extends AbstractGameAction
{
	private final AbstractPlayer p;
	private final boolean upgrade;
	private final ArrayList<DuelistCard> cards;
	private final boolean damageBlockRandomize;
	private AbstractMonster target;
	private final boolean canCancel;

	public DragonPuzzleAction(ArrayList<DuelistCard> cardsToChooseFrom, int amount)
	{
		this.p = AbstractDungeon.player;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = false;
		this.amount = amount;
		this.cards = cardsToChooseFrom;
		this.damageBlockRandomize = false;
		this.canCancel = false;
	}

	public void update()
	{
		CardGroup tmp;
		if (this.amount == this.cards.size()) {
			if (this.duration == Settings.ACTION_DUR_MED) {
				tickDuration();
				return;
			}
			for (AbstractCard c : this.cards) {
				playCard(c);
			}
			this.isDone = true;
			return;
		}
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard card : cards)
			{
				AbstractCard gridCard = card.makeStatEquivalentCopy();
				if (this.upgrade) { gridCard.upgrade(); }
				this.target = AbstractDungeon.getRandomMonster();
				if (damageBlockRandomize)
	    		{
	    			if (gridCard.damage > 0)
	    			{
	    				int low = gridCard.damage * -1;
	    				int high = gridCard.damage + 6;
	    				int roll = AbstractDungeon.cardRandomRng.random(low, high);
	    				AbstractDungeon.actionManager.addToTop(new ModifyDamageAction(gridCard.uuid, roll));
	    				gridCard.isDamageModified = true;
	    			}
	    			
	    			if (gridCard.block > 0)
	    			{
	    				int low = gridCard.block * -1;
	    				int high = gridCard.block + 6;
	    				int roll = AbstractDungeon.cardRandomRng.random(low, high);
	    				AbstractDungeon.actionManager.addToTop(new ModifyBlockAction(gridCard.uuid, roll));
	    				gridCard.isBlockModified = true;
	    			}
	    		}	
		        gridCard.initializeDescription();
				tmp.addToTop(gridCard);
				if (DuelistMod.debug) { System.out.println("theDuelist:CardSelectScreenResummonAction:update() ---> added " + gridCard.originalName + " into grid selection pool"); }
			}
			
			tmp.group.sort(GridSort.getComparator());
			//if (this.canCancel) { for (int i = 0; i < this.amount; i++) { tmp.addToTop(new CancelCard()); }}
			if (this.amount == 1) { SelectScreenHelper.open(tmp, this.amount, "Choose " + this.amount + " Special Puzzle Effect"); }
			else { SelectScreenHelper.open(tmp, this.amount, "Choose " + this.amount + " Special Puzzle Effects"); }
			tickDuration();
			return;
			
		}
		
		if ((AbstractDungeon.gridSelectScreen.selectedCards.size() != 0))
		{
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
				playCard(c);
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}

	private void playCard(AbstractCard c) {
		c.unhover();
		c.stopGlowing();
		if (!(c instanceof CancelCard) && !(c instanceof SplendidCancel)) {
			AbstractMonster targ = this.target != null ? this.target : AbstractDungeon.getRandomMonster();
			DuelistCard.resummon(c, targ);
		}
	}
}
