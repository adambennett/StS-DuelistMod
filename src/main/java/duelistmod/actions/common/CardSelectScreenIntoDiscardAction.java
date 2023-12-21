package duelistmod.actions.common;

import java.util.*;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.helpers.*;
import duelistmod.variables.Strings;

public class CardSelectScreenIntoDiscardAction extends AbstractGameAction
{
	private final AbstractPlayer p;
	private boolean upgrade;
	private final ArrayList<AbstractCard> cards;
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
	private boolean dontTrig = false;
	private boolean canCancel = false;
	private boolean anyNumber = false;

	private boolean cardSelectScreenAllowUpgrades;
	
	public CardSelectScreenIntoDiscardAction(ArrayList<AbstractCard> cardsToChooseFrom, int amount, boolean anyNumber, boolean allowShowUpgrades)
	{
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = false;
		this.amount = amount;
		this.cards = cardsToChooseFrom;
		this.randomize = false;		
		this.sendExtraToDiscard = true;
		this.canCancel = false;
		this.anyNumber = anyNumber;
		this.cardSelectScreenAllowUpgrades = allowShowUpgrades;
		checkFlags();
	}

	// DragonOrbs
	public CardSelectScreenIntoDiscardAction(ArrayList<AbstractCard> cardsToChooseFrom, int amount)
	{
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_MED;
		this.upgrade = false;
		this.amount = amount;
		this.cards = cardsToChooseFrom;
		this.randomize = true;
		this.exhaustCheck = true;
		this.etherealCheck = true;
		this.costChangeCheck = true;
		this.summonCheck = true;
		this.tributeCheck = true;
		this.lowCostRoll = 0;
		this.highCostRoll = 4;
		this.lowSummonRoll = 0;
		this.highSummonRoll = 3;
		this.lowTributeRoll = 0;
		this.highTributeRoll = 2;
		this.summonChangeCombatCheck = false;
		this.tributeChangeCombatCheck = false;
		this.costChangeCombatCheck = false;
		this.sendExtraToDiscard = true;
		this.canCancel = true;
		checkFlags();
	}

	// Cloning, Wiretap, Red/Yellow/Green Gadgets, Grand Spellbook Tower
	public CardSelectScreenIntoDiscardAction(boolean upgraded, boolean sendExtraToDiscard, int amount, ArrayList<AbstractCard> cardsToChooseFrom)
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
		this.canCancel = true;
	}

	// Megatype Deck w/ Millennium Symbol relic
	public CardSelectScreenIntoDiscardAction(boolean upgraded, boolean sendExtraToDiscard, int amount, ArrayList<AbstractCard> cardsToChooseFrom, int newCost)
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
		this.canCancel = true;
		this.costChangeCheck = true;
		this.lowCostRoll = newCost;
		this.highCostRoll = newCost;
		this.randomize = true;
		this.upgrade = false;
		this.exhaustCheck = false;
		this.etherealCheck = false;
		this.summonCheck = false;
		this.tributeCheck = false;
		this.damageBlockRandomize = false;
	}

	// Pot of the Forbidden
	public CardSelectScreenIntoDiscardAction(ArrayList<AbstractCard> cardsToChooseFrom, boolean sendExtraToDiscard, int amount, boolean upgraded, boolean exhaust, boolean ethereal, boolean costChange, boolean summonCheck, boolean tributeCheck, boolean combat, int lowCost, int highCost, int lowTrib, int highTrib, int lowSummon, int highSummon)
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
		this.canCancel = true;
	}

	// Fairy Box
	public CardSelectScreenIntoDiscardAction(ArrayList<AbstractCard> cardsToChooseFrom, boolean sendExtraToDiscard, int amount, boolean upgraded, boolean exhaust, boolean ethereal, boolean costChange, boolean summonCheck, boolean tributeCheck, boolean combat, int lowCost, int highCost, int lowTrib, int highTrib, int lowSummon, int highSummon, boolean dontTrig)
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
		this.dontTrig = dontTrig;
		this.canCancel = true;
	}

	// Millennium orb evoke
	public CardSelectScreenIntoDiscardAction(boolean randomizeDamageAndBlock, ArrayList<AbstractCard> cardsToChooseFrom, boolean sendExtraToDiscard, int amount, boolean upgraded, boolean exhaust, boolean ethereal, boolean costChange, boolean summonCheck, boolean tributeCheck, boolean combat, int lowCost, int highCost, int lowTrib, int highTrib, int lowSummon, int highSummon)
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
		this.canCancel = false;
		checkFlags();
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
					if (!gridCard.isEthereal && etherealCheck && !gridCard.selfRetain) 
					{
		                gridCard.isEthereal = true;
		                gridCard.rawDescription = Strings.etherealForCardText + gridCard.rawDescription;
		    		}
		    		
		    		if (!gridCard.exhaust && exhaustCheck) 
		    		{
		                gridCard.exhaust = true;
		                gridCard.rawDescription = gridCard.rawDescription + DuelistMod.exhaustForCardText;
		    		}
		    		
		    		if (costChangeCheck && gridCard.cost > -1)
		    		{
		    			int randomNum = AbstractDungeon.cardRandomRng.random(lowCostRoll, highCostRoll);
		    			System.out.println("randomNum: " + randomNum);
		    			if (costChangeCombatCheck)
		    			{
		    				int gridCardCost = gridCard.cost;
		    				gridCard.modifyCostForCombat(-gridCard.cost + randomNum);
			    			if (randomNum != gridCardCost) { gridCard.isCostModified = true; }
		    			}
		    			else
		    			{
		    				int gridCardCost = gridCard.cost;
		    				gridCard.setCostForTurn(-gridCard.cost + randomNum);
		    				if (randomNum != gridCardCost) { gridCard.isCostModifiedForTurn = true; }
		    			}
		    		}       
		    		
		    		if (summonCheck && gridCard instanceof DuelistCard)
		    		{
		    			int randomNum = AbstractDungeon.cardRandomRng.random(lowSummonRoll, highSummonRoll);
		    			DuelistCard dC = (DuelistCard)gridCard;
		    			if (dC.isSummonCard())
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
		    			if (dC.isTributeCard())
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

		    		if (dontTrig)
		    		{
		    			gridCard.dontTriggerOnUseCard = false;
		    		}
					if (gridCard instanceof DuelistCard) {
						((DuelistCard)gridCard).fixUpgradeDesc();
					}
		            gridCard.initializeDescription();
				}
				tmp.addToBottom(gridCard);
			}
	
			tmp.group.sort(GridSort.getComparator());
			//if (this.canCancel) { for (int i = 0; i < this.amount; i++) { tmp.addToTop(new CancelCard()); }}
			if (this.amount >= tmp.group.size())
			{
				if (anyNumber)
				{

					String btmScreenTxt = "Choose " + this.amount + " Card to Shuffle into your Discard Pile";
					if (this.amount != 1 ) { btmScreenTxt = "Choose " + this.amount + " Cards to Shuffle into your Discard Pile"; }
					DuelistMod.duelistCardSelectScreen.open(this.cardSelectScreenAllowUpgrades, tmp, this.amount, btmScreenTxt, (selectedCards) -> {
						for (AbstractCard c : selectedCards)
						{
							c.unhover();
							c.stopGlowing();
							c.isSelected = false;
							if (!(c instanceof CancelCard))
							{
								AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c));
							}
						}
					});
					AbstractDungeon.overlayMenu.cancelButton.show("Cancel");
				}
				else
				{
					for (AbstractCard c : tmp.group)
					{
						if (!(c instanceof CancelCard))
						{
							AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c));
						}					
					}
				}
				
				AbstractDungeon.gridSelectScreen.selectedCards.clear();
			}
			
			else
			{				
				if (this.anyNumber)
				{
					String btmScreenTxt = "Choose " + this.amount + " Card to Shuffle into your Discard Pile";
					if (this.amount != 1 ) { btmScreenTxt = "Choose " + this.amount + " Cards to Shuffle into your Discard Pile"; }
					DuelistMod.duelistCardSelectScreen.open(this.cardSelectScreenAllowUpgrades, tmp, this.amount, btmScreenTxt, (selectedCards) -> {
						for (AbstractCard c : selectedCards)
						{
							c.unhover();
							c.stopGlowing();
							c.isSelected = false;
							if (!(c instanceof CancelCard))
							{
								AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c));
							}
						}
					});
				}
				else
				{
					if (this.amount == 1) { SelectScreenHelper.open(tmp, this.amount, "Choose " + this.amount + " Card to Shuffle into your Discard Pile"); }
					else { SelectScreenHelper.open(tmp, this.amount, "Choose " + this.amount + " Cards to Shuffle into your Discard Pile"); }
				}
				
			}
			tickDuration();
			return;
		}

		if (!anyNumber)
		{
			// If there are more cards to add to hand still and player hand has space, or if there are still cards to hand and we intend to discard them if the players hand has no space
			if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0)
			{
				for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
				{
					Util.log("CardSelectScreenIntoHandAction found " + c.name + " in selection");
					c.unhover();
					c.stopGlowing();
					c.isSelected = false;
					if (!(c instanceof CancelCard))
					{
						AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c));
					}				
				}
				AbstractDungeon.gridSelectScreen.selectedCards.clear();	
			}
		}
		
		//if (this.anyNumber) { AbstractDungeon.gridSelectScreen = new GridCardSelectScreen(); }
		tickDuration();
	}
	
    private void checkFlags()
    {
    	if (DuelistMod.persistentDuelistData.RandomizedSettings.getNoCostChanges()) { this.costChangeCheck = false; }
    	if (DuelistMod.persistentDuelistData.RandomizedSettings.getNoTributeChanges()) { this.tributeCheck = false; }
    	if (DuelistMod.persistentDuelistData.RandomizedSettings.getNoSummonChanges()) { this.summonCheck = false; }
    	if (DuelistMod.persistentDuelistData.RandomizedSettings.getAlwaysUpgrade()) { this.upgrade = true; }
    	if (DuelistMod.persistentDuelistData.RandomizedSettings.getNeverUpgrade()) { this.upgrade = false; }
    	if (!DuelistMod.persistentDuelistData.RandomizedSettings.getAllowEthereal()) { this.etherealCheck = false; }
    	if (!DuelistMod.persistentDuelistData.RandomizedSettings.getAllowExhaust()) { this.exhaustCheck = false; }
    }
}
