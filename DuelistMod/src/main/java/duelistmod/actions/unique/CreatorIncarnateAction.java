package duelistmod.actions.unique;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import basemod.BaseMod;
import duelistmod.*;
import duelistmod.interfaces.DuelistCard;

public class CreatorIncarnateAction extends AbstractGameAction
{
	private AbstractPlayer p;
	private boolean upgrade;
	private ArrayList<AbstractCard> cards;
	private boolean randomize = false;
	private boolean etherealCheck = false;
	private boolean exhaustCheck = false;
	private boolean costChangeCheck = false;
	private boolean summonCheck = false;
	private boolean tributeCheck = false;
	private boolean summonChangeCombatCheck = false;
	private boolean tributeChangeCombatCheck = false;
	private boolean costChangeCombatCheck = false;
	private int lowCostRoll = 1;
	private int highCostRoll = 4;
	private int lowSummonRoll = 0;
	private int highSummonRoll = 0;
	private int lowTributeRoll = 0;
	private int highTributeRoll = 0;
	private boolean sendExtraToDiscard = false;
	private boolean damageBlockRandomize = false;
  
	public CreatorIncarnateAction(boolean upgraded, boolean sendExtraToDiscard, int amount, ArrayList<AbstractCard> cardsToChooseFrom)
	{
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, 1);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = upgraded;
		this.amount = amount;
		this.cards = cardsToChooseFrom;
		this.randomize = false;
		this.sendExtraToDiscard = sendExtraToDiscard;
	}
	
	public CreatorIncarnateAction(boolean upgraded, boolean sendExtraToDiscard, int amount, ArrayList<AbstractCard> cardsToChooseFrom, boolean randomize, boolean combat)
	{
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, 1);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = upgraded;
		this.amount = amount;
		this.cards = cardsToChooseFrom;
		this.randomize = randomize;
		this.exhaustCheck = true;
		this.etherealCheck = true;
		this.costChangeCheck = true;
		this.summonCheck = true;
		this.tributeCheck = true;
		this.lowCostRoll = 1;
		this.highCostRoll = 5;
		this.lowSummonRoll = 0;
		this.highSummonRoll = 2;
		this.lowTributeRoll = 0;
		this.highTributeRoll = 1;
		this.summonChangeCombatCheck = combat;
		this.tributeChangeCombatCheck = combat;
		this.sendExtraToDiscard = sendExtraToDiscard;
	}
	
	public CreatorIncarnateAction(ArrayList<AbstractCard> cardsToChooseFrom, boolean sendExtraToDiscard, int amount, boolean upgraded, boolean exhaust, boolean ethereal, boolean costChange)
	{
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, 1);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = upgraded;
		this.amount = amount;
		this.cards = cardsToChooseFrom;
		this.randomize = true;
		this.exhaustCheck = exhaust;
		this.etherealCheck = ethereal;
		this.costChangeCheck = costChange;
		this.sendExtraToDiscard = sendExtraToDiscard;
	}
	
	public CreatorIncarnateAction(ArrayList<AbstractCard> cardsToChooseFrom, boolean sendExtraToDiscard, int amount, boolean upgraded, boolean exhaust, boolean ethereal, boolean costChange, boolean summonCheck, boolean tributeCheck)
	{
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, 1);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = upgraded;
		this.amount = amount;
		this.cards = cardsToChooseFrom;
		this.randomize = true;
		this.exhaustCheck = exhaust;
		this.etherealCheck = ethereal;
		this.costChangeCheck = costChange;
		this.summonCheck = summonCheck;
		this.tributeCheck = tributeCheck;
		this.sendExtraToDiscard = sendExtraToDiscard;
	}
	
	public CreatorIncarnateAction(ArrayList<AbstractCard> cardsToChooseFrom, boolean sendExtraToDiscard, int amount, boolean upgraded, boolean exhaust, boolean ethereal, boolean costChange, boolean summonCheck, boolean tributeCheck, boolean combat, int lowCost, int highCost, int lowTrib, int highTrib, int lowSummon, int highSummon)
	{
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, 1);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = upgraded;
		this.amount = amount;
		this.cards = cardsToChooseFrom;
		this.randomize = true;
		this.exhaustCheck = exhaust;
		this.etherealCheck = ethereal;
		this.costChangeCheck = costChange;
		this.summonCheck = summonCheck;
		this.tributeCheck = tributeCheck;
		this.lowCostRoll = lowCost;
		this.highCostRoll = highCost;
		this.lowSummonRoll = lowSummon;
		this.highSummonRoll = highSummon;
		this.lowTributeRoll = lowTrib;
		this.highTributeRoll = highTrib;
		this.summonChangeCombatCheck = combat;
		this.tributeChangeCombatCheck = combat;
		this.costChangeCombatCheck = combat;
		this.sendExtraToDiscard = sendExtraToDiscard;
	}
	
	public CreatorIncarnateAction(boolean randomizeDamageAndBlock, ArrayList<AbstractCard> cardsToChooseFrom, boolean sendExtraToDiscard, int amount, boolean upgraded, boolean exhaust, boolean ethereal, boolean costChange, boolean summonCheck, boolean tributeCheck, boolean combat, int lowCost, int highCost, int lowTrib, int highTrib, int lowSummon, int highSummon)
	{
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, 1);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = upgraded;
		this.amount = amount;
		this.cards = cardsToChooseFrom;
		this.randomize = true;
		this.exhaustCheck = exhaust;
		this.etherealCheck = ethereal;
		this.costChangeCheck = costChange;
		this.summonCheck = summonCheck;
		this.tributeCheck = tributeCheck;
		this.lowCostRoll = lowCost;
		this.highCostRoll = highCost;
		this.lowSummonRoll = lowSummon;
		this.highSummonRoll = highSummon;
		this.lowTributeRoll = lowTrib;
		this.highTributeRoll = highTrib;
		this.summonChangeCombatCheck = combat;
		this.tributeChangeCombatCheck = combat;
		this.costChangeCombatCheck = combat;
		this.sendExtraToDiscard = sendExtraToDiscard;
		this.damageBlockRandomize = randomizeDamageAndBlock;
	}
  
	public void update()
	{
		CardGroup tmp;
		if (this.duration == Settings.ACTION_DUR_MED)
		{
			tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard gridCard : cards)
			{
				if (this.upgrade) { gridCard.upgrade(); }
				if (this.randomize)
				{
					if (!gridCard.isEthereal && etherealCheck) 
					{
		                gridCard.isEthereal = true;
		                gridCard.rawDescription = "Ethereal NL " + gridCard.rawDescription;
		    		}
		    		
		    		if (!gridCard.exhaust && exhaustCheck) 
		    		{
		                gridCard.exhaust = true;
		                gridCard.rawDescription = gridCard.rawDescription + " NL Exhaust.";
		    		}
		    		
		    		if (costChangeCheck && gridCard.cost > -1)
		    		{
		    			int randomNum = AbstractDungeon.cardRandomRng.random(lowCostRoll, highCostRoll);
		    			System.out.println("randomNum: " + randomNum);
		    			if (costChangeCombatCheck)
		    			{
		    				gridCard.modifyCostForCombat(-gridCard.cost + randomNum);
			    			gridCard.isCostModified = true;
		    			}
		    			else
		    			{
		    				gridCard.modifyCostForTurn(-gridCard.cost + randomNum);
			    			gridCard.isCostModifiedForTurn = true;
		    			}
		    		}       
		    		
		    		if (summonCheck && gridCard instanceof DuelistCard)
		    		{
		    			int randomNum = AbstractDungeon.cardRandomRng.random(lowSummonRoll, highSummonRoll);
		    			DuelistCard dC = (DuelistCard)gridCard;
		    			if (dC.summons > 0)
		    			{
			    			if (summonChangeCombatCheck)
			    			{
			    				dC.modifySummons(randomNum);
			    			}
			    			else
			    			{
			    				dC.modifySummonsForTurn(randomNum);
			    			}
		    			}
		    		}
		    		
		    		if (tributeCheck && gridCard instanceof DuelistCard)
		    		{
		    			int randomNum = AbstractDungeon.cardRandomRng.random(lowTributeRoll, highTributeRoll);
		    			DuelistCard dC = (DuelistCard)gridCard;
		    			if (dC.tributes > 0)
		    			{
			    			if (tributeChangeCombatCheck)
			    			{
			    				dC.modifyTributes(-randomNum);
			    			}
			    			else
			    			{
			    				dC.modifyTributesForTurn(-randomNum);
			    			}
		    			}
		    		}
		    		
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
				}
				tmp.addToTop(gridCard);
				if (DuelistMod.debug)
				{
					System.out.println("theDuelist:CreatorIncarnateAction:update() ---> added " + gridCard.originalName + " into grid selection pool");
				}
			}
			
			Collections.sort(tmp.group, GridSort.getComparator());
			if (this.amount == 1) { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, "Choose " + this.amount + " card to add to your hand", false); }
			else { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, "Choose " + this.amount + " cards to add to your hand", false); }
			tickDuration();
			return;
		}
		
		// If there are more cards to add to hand still and player hand has space, or if there are still cards to hand and we intend to discard them if the players hand has no space
		if ((AbstractDungeon.gridSelectScreen.selectedCards.size() != 0 && this.p.hand.size() < BaseMod.MAX_HAND_SIZE) || (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0 && sendExtraToDiscard))
		{
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
			{
				c.unhover();
				if (this.p.hand.size() == BaseMod.MAX_HAND_SIZE)
				{
					this.p.createHandIsFullDialog();
					if (sendExtraToDiscard) { this.p.discardPile.addToTop(c); }
				}
				else
				{
					AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c));
				}
				this.p.hand.refreshHandLayout();
				this.p.hand.applyPowers();
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		tickDuration();
	}
}
