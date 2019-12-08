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
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.helpers.*;
import duelistmod.ui.DuelistCardSelectScreen;
import duelistmod.variables.Strings;

public class SplashCaptureAction extends AbstractGameAction
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
	private boolean dontTrig = false;
	private boolean canCancel = false;
	private boolean anyNumber = false;
	private int cardCopies = 1;
	private DuelistCardSelectScreen dcss;
	
	public SplashCaptureAction(ArrayList<AbstractCard> cardsToChooseFrom, int amount, boolean anyNumber, boolean allowShowUpgrades, int cardCopies)
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
		this.cardCopies = cardCopies;
		this.anyNumber = anyNumber;
		this.dcss = new DuelistCardSelectScreen(allowShowUpgrades);
		checkFlags();
	}
	
	// DragonOrbs
	public SplashCaptureAction(ArrayList<AbstractCard> cardsToChooseFrom, int amount)
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
	public SplashCaptureAction(boolean upgraded, boolean sendExtraToDiscard, int amount, ArrayList<AbstractCard> cardsToChooseFrom)
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
	public SplashCaptureAction(boolean upgraded, boolean sendExtraToDiscard, int amount, ArrayList<AbstractCard> cardsToChooseFrom, int newCost)
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
	public SplashCaptureAction(ArrayList<AbstractCard> cardsToChooseFrom, boolean sendExtraToDiscard, int amount, boolean upgraded, boolean exhaust, boolean ethereal, boolean costChange, boolean summonCheck, boolean tributeCheck, boolean combat, int lowCost, int highCost, int lowTrib, int highTrib, int lowSummon, int highSummon)
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
	public SplashCaptureAction(ArrayList<AbstractCard> cardsToChooseFrom, boolean sendExtraToDiscard, int amount, boolean upgraded, boolean exhaust, boolean ethereal, boolean costChange, boolean summonCheck, boolean tributeCheck, boolean combat, int lowCost, int highCost, int lowTrib, int highTrib, int lowSummon, int highSummon, boolean dontTrig)
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
	public SplashCaptureAction(boolean randomizeDamageAndBlock, ArrayList<AbstractCard> cardsToChooseFrom, boolean sendExtraToDiscard, int amount, boolean upgraded, boolean exhaust, boolean ethereal, boolean costChange, boolean summonCheck, boolean tributeCheck, boolean combat, int lowCost, int highCost, int lowTrib, int highTrib, int lowSummon, int highSummon)
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
					if (!gridCard.isEthereal && etherealCheck) 
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
		    		
		            gridCard.initializeDescription();
				}
				tmp.addToBottom(gridCard);
				if (DuelistMod.debug)
				{
					System.out.println("theDuelist:CreatorIncarnateAction:update() ---> added " + gridCard.originalName + " into grid selection pool");
				}
			}
	
			Collections.sort(tmp.group, GridSort.getComparator());
			if (this.canCancel) { for (int i = 0; i < this.amount; i++) { tmp.addToTop(new CancelCard()); }}
			if (this.amount >= tmp.group.size())
			{
				if (anyNumber)
				{
					AbstractDungeon.gridSelectScreen = this.dcss;
					DuelistMod.wasViewingSelectScreen = true;
					String btmScreenTxt = Strings.configChooseString + this.amount + Strings.configAddCardHandString;
					if (this.amount != 1 ) { btmScreenTxt = Strings.configChooseString + this.amount + Strings.configAddCardHandPluralString; }
					((DuelistCardSelectScreen)AbstractDungeon.gridSelectScreen).open(tmp, this.amount, btmScreenTxt);
				}
				else
				{
					for (AbstractCard c : tmp.group)
					{
						if (!(c instanceof CancelCard))
						{
							if (this.p.hand.size() == BaseMod.MAX_HAND_SIZE)
							{
								this.p.createHandIsFullDialog();
								if (sendExtraToDiscard) 
								{
									for (int i = 0; i < this.cardCopies; i++) {
										this.p.discardPile.addToTop(c.makeStatEquivalentCopy()); 
									}
								}
							}
							else
							{
								for (int i = 0; i < this.cardCopies; i++) {
									AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c.makeStatEquivalentCopy()));
								}
							}
							this.p.hand.refreshHandLayout();
							this.p.hand.applyPowers();
						}					
					}
				}
				
				AbstractDungeon.gridSelectScreen.selectedCards.clear();
				this.p.hand.refreshHandLayout();
			}
			
			else
			{				
				if (this.anyNumber)
				{		
					AbstractDungeon.gridSelectScreen = this.dcss;
					DuelistMod.wasViewingSelectScreen = true;
					String btmScreenTxt = Strings.configChooseString + this.amount + Strings.configAddCardHandString;
					if (this.amount != 1 ) { btmScreenTxt = Strings.configChooseString + this.amount + Strings.configAddCardHandPluralString; }
					((DuelistCardSelectScreen)AbstractDungeon.gridSelectScreen).open(tmp, this.amount, btmScreenTxt);
				}
				else
				{
					if (this.amount == 1) { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, Strings.configChooseString + this.amount + Strings.configAddCardHandString, false); }
					else { AbstractDungeon.gridSelectScreen.open(tmp, this.amount, Strings.configChooseString + this.amount + Strings.configAddCardHandPluralString, false); }
				}
				
			}
			tickDuration();
			return;
		}

		if (!anyNumber)
		{
			// If there are more cards to add to hand still and player hand has space, or if there are still cards to hand and we intend to discard them if the players hand has no space
			if ((AbstractDungeon.gridSelectScreen.selectedCards.size() != 0 && this.p.hand.size() < BaseMod.MAX_HAND_SIZE) || (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0 && sendExtraToDiscard))
			{
				for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
				{
					Util.log("CardSelectScreenIntoHandAction found " + c.name + " in selection");
					c.unhover();
					if (!(c instanceof CancelCard))
					{
						if (this.p.hand.size() == BaseMod.MAX_HAND_SIZE)
						{
							this.p.createHandIsFullDialog();
							if (sendExtraToDiscard) 
							{ 
								for (int i = 0; i < this.cardCopies; i++) {
									this.p.discardPile.addToTop(c.makeStatEquivalentCopy()); 
								}
							}
						}
						else
						{
							for (int i = 0; i < this.cardCopies; i++) {
								AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c.makeStatEquivalentCopy()));
							}
							Util.log("CardSelectScreenIntoHandAction tried to add " + c.name + " to hand");
						}
						this.p.hand.refreshHandLayout();
						this.p.hand.applyPowers();
					}				
				}
				AbstractDungeon.gridSelectScreen.selectedCards.clear();			
				this.p.hand.refreshHandLayout();
			}
		}
		else if (this.dcss != null && (this.dcss.selectedCards.size() != 0 && this.p.hand.size() < BaseMod.MAX_HAND_SIZE) || (this.dcss.selectedCards.size() != 0 && sendExtraToDiscard))
		{
			for (AbstractCard c : this.dcss.selectedCards)
			{
				Util.log("CardSelectScreenIntoHandAction found " + c.name + " in this.dcss");
				c.unhover();
				if (!(c instanceof CancelCard))
				{
					if (this.p.hand.size() == BaseMod.MAX_HAND_SIZE)
					{
						this.p.createHandIsFullDialog();
						if (sendExtraToDiscard) 
						{ 
							for (int i = 0; i < this.cardCopies; i++) {
								this.p.discardPile.addToTop(c.makeStatEquivalentCopy()); 
							}
						}
					}
					else
					{
						for (int i = 0; i < this.cardCopies; i++) {
							AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c.makeStatEquivalentCopy()));
						}
						Util.log("CardSelectScreenIntoHandAction tried to add " + c.name + " to hand from this.dcss");
					}
					this.p.hand.refreshHandLayout();
					this.p.hand.applyPowers();
				}				
			}
			this.dcss.selectedCards.clear();
			this.p.hand.refreshHandLayout();
		}
		
		//if (this.anyNumber) { AbstractDungeon.gridSelectScreen = new GridCardSelectScreen(); }
		tickDuration();
	}
	
    private void checkFlags()
    {
    	if (DuelistMod.noCostChanges) { this.costChangeCheck = false; }
    	if (DuelistMod.noTributeChanges) { this.tributeCheck = false; }
    	if (DuelistMod.noSummonChanges) { this.summonCheck = false; }
    	if (DuelistMod.alwaysUpgrade) { this.upgrade = true; }
    	if (DuelistMod.neverUpgrade) { this.upgrade = false; }
    	if (!DuelistMod.randomizeEthereal) { this.etherealCheck = false; }
    	if (!DuelistMod.randomizeExhaust) { this.exhaustCheck = false; }
    }
}
