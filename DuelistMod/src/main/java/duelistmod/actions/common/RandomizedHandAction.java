package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import basemod.BaseMod;
import duelistmod.*;
import duelistmod.interfaces.DuelistCard;



public class RandomizedHandAction extends AbstractGameAction 
{
	private AbstractCard cardRef;
	private boolean exhaustCheck = false;
	private boolean etherealCheck = false;
	private boolean costChangeCheck = false;
	private boolean upgradeCheck = false;
	private boolean summonCheck = false;
	private boolean tributeCheck = false;
	private boolean summonChangeCombatCheck = false;
	private boolean tributeChangeCombatCheck = false;
	private int lowCostRoll = 1;
	private int highCostRoll = 4;
	private int lowSummonRoll = 1;
	private int highSummonRoll = 2;
	private int lowTributeRoll = 1;
	private int highTributeRoll = 3;

	public RandomizedHandAction(AbstractCard c)
	{
		this.actionType = ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_FAST;
		this.cardRef = c;
		this.lowCostRoll = 1;
		this.highCostRoll = 4;
		this.lowSummonRoll = 0;
		this.highSummonRoll = 2;
		this.lowTributeRoll = 0;
		this.highTributeRoll = 1;
		this.tributeChangeCombatCheck = false;
		this.summonChangeCombatCheck = false;
		if (DuelistMod.debug)
		{
			DuelistMod.logger.info("Stack trace indicating caller of this action [1]: " + Thread.currentThread().getStackTrace()[1].getMethodName());
			DuelistMod.logger.info("Stack trace indicating caller of this action [2]: " + Thread.currentThread().getStackTrace()[2].getMethodName());
			DuelistMod.logger.info("Stack trace indicating caller of this action [3]: " + Thread.currentThread().getStackTrace()[3].getMethodName());
			DuelistMod.logger.info("Stack trace indicating caller of this action [4]: " + Thread.currentThread().getStackTrace()[4].getMethodName());
		}
	}
	
	public RandomizedHandAction(AbstractCard c, boolean extras)
	{
		this.actionType = ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_FAST;
		this.cardRef = c;
		this.lowCostRoll = 1;
		this.highCostRoll = 4;
		this.lowSummonRoll = 0;
		this.highSummonRoll = 2;
		this.lowTributeRoll = 0;
		this.highTributeRoll = 1;
		this.tributeChangeCombatCheck = false;
		this.summonChangeCombatCheck = false; 
		if (extras)
		{
			this.upgradeCheck = true; 
			this.etherealCheck = true; 
			this.exhaustCheck = true; 
			if (!DuelistMod.noCostChanges) { this.costChangeCheck = true; }
			this.tributeCheck = true; 
			this.summonCheck = true; 
		}
		if (DuelistMod.debug)
		{
			DuelistMod.logger.info("Stack trace indicating caller of this action [1]: " + Thread.currentThread().getStackTrace()[1].getMethodName());
			DuelistMod.logger.info("Stack trace indicating caller of this action [2]: " + Thread.currentThread().getStackTrace()[2].getMethodName());
			DuelistMod.logger.info("Stack trace indicating caller of this action [3]: " + Thread.currentThread().getStackTrace()[3].getMethodName());
			DuelistMod.logger.info("Stack trace indicating caller of this action [4]: " + Thread.currentThread().getStackTrace()[4].getMethodName());
		}
	}
	
	public RandomizedHandAction(AbstractCard c, boolean upgrade, boolean ethereal)
	{
		this.actionType = ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_FAST;
		this.cardRef = c;
		this.tributeCheck = false;
		this.summonCheck = false;
		this.costChangeCheck = false;
		this.exhaustCheck = false; 
		if (upgrade) { this.upgradeCheck = true; }
		if (ethereal) { this.etherealCheck = true; }
		if (DuelistMod.debug)
		{
			DuelistMod.logger.info("Stack trace indicating caller of this action [1]: " + Thread.currentThread().getStackTrace()[1].getMethodName());
			DuelistMod.logger.info("Stack trace indicating caller of this action [2]: " + Thread.currentThread().getStackTrace()[2].getMethodName());
			DuelistMod.logger.info("Stack trace indicating caller of this action [3]: " + Thread.currentThread().getStackTrace()[3].getMethodName());
			DuelistMod.logger.info("Stack trace indicating caller of this action [4]: " + Thread.currentThread().getStackTrace()[4].getMethodName());
		}
	}
	
	public RandomizedHandAction(AbstractCard c, boolean upgrade, boolean ethereal, boolean exhaust)
	{
		this.actionType = ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_FAST;
		this.cardRef = c;
		this.tributeCheck = false;
		this.summonCheck = false;
		this.costChangeCheck = false;
		if (upgrade) { this.upgradeCheck = true; }
		if (ethereal) { this.etherealCheck = true; }
		if (exhaust) { this.exhaustCheck = true; }
		if (DuelistMod.debug)
		{
			DuelistMod.logger.info("Stack trace indicating caller of this action [1]: " + Thread.currentThread().getStackTrace()[1].getMethodName());
			DuelistMod.logger.info("Stack trace indicating caller of this action [2]: " + Thread.currentThread().getStackTrace()[2].getMethodName());
			DuelistMod.logger.info("Stack trace indicating caller of this action [3]: " + Thread.currentThread().getStackTrace()[3].getMethodName());
			DuelistMod.logger.info("Stack trace indicating caller of this action [4]: " + Thread.currentThread().getStackTrace()[4].getMethodName());
		}
	}
	
	public RandomizedHandAction(AbstractCard c, boolean upgrade, boolean ethereal, boolean exhaust, boolean costChange)
	{
		this.actionType = ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_FAST;
		this.cardRef = c;
		this.lowCostRoll = 1;
		this.highCostRoll = 4;
		this.tributeCheck = false;
		this.summonCheck = false;
		if (upgrade) { this.upgradeCheck = true; }
		if (ethereal) { this.etherealCheck = true; }
		if (exhaust) { this.exhaustCheck = true; }
		if (costChange && !DuelistMod.noCostChanges) { this.costChangeCheck = true; }
		if (DuelistMod.debug)
		{
			DuelistMod.logger.info("Stack trace indicating caller of this action [1]: " + Thread.currentThread().getStackTrace()[1].getMethodName());
			DuelistMod.logger.info("Stack trace indicating caller of this action [2]: " + Thread.currentThread().getStackTrace()[2].getMethodName());
			DuelistMod.logger.info("Stack trace indicating caller of this action [3]: " + Thread.currentThread().getStackTrace()[3].getMethodName());
			DuelistMod.logger.info("Stack trace indicating caller of this action [4]: " + Thread.currentThread().getStackTrace()[4].getMethodName());
		}
	}
	
	public RandomizedHandAction(AbstractCard c, boolean upgrade, boolean ethereal, boolean exhaust,	boolean costChange, boolean tributeChange, boolean summonChange)
	{
		this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.cardRef = c;
        this.lowCostRoll = 1;
        this.highCostRoll = 4;
        this.lowSummonRoll = 0;
        this.highSummonRoll = 2;
        this.lowTributeRoll = 0;
        this.highTributeRoll = 1;
        this.tributeChangeCombatCheck = false;
        this.summonChangeCombatCheck = false;
        if (upgrade) { this.upgradeCheck = true; }
		if (ethereal) { this.etherealCheck = true; }
		if (exhaust) { this.exhaustCheck = true; }
		if (costChange && !DuelistMod.noCostChanges)	{ this.costChangeCheck = true; }
		if (tributeChange) { this.tributeCheck = true; }
		if (summonChange) { this.summonCheck = true; }
		if (DuelistMod.debug)
		{
			DuelistMod.logger.info("Stack trace indicating caller of this action [1]: " + Thread.currentThread().getStackTrace()[1].getMethodName());
			DuelistMod.logger.info("Stack trace indicating caller of this action [2]: " + Thread.currentThread().getStackTrace()[2].getMethodName());
			DuelistMod.logger.info("Stack trace indicating caller of this action [3]: " + Thread.currentThread().getStackTrace()[3].getMethodName());
			DuelistMod.logger.info("Stack trace indicating caller of this action [4]: " + Thread.currentThread().getStackTrace()[4].getMethodName());
		}
	}
	
	public RandomizedHandAction(AbstractCard c, boolean upgrade, boolean ethereal, boolean exhaust,	boolean costChange, boolean tributeChange, boolean summonChange, boolean tribChangeCombat, boolean summonChangeCombat)
	{
		this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.cardRef = c;
        this.lowCostRoll = 1;
        this.highCostRoll = 4;
        this.lowSummonRoll = 0;
        this.highSummonRoll = 2;
        this.lowTributeRoll = 0;
        this.highTributeRoll = 1;
        this.tributeChangeCombatCheck = tribChangeCombat;
        this.summonChangeCombatCheck = summonChangeCombat;
        if (upgrade) { this.upgradeCheck = true; }
		if (ethereal) { this.etherealCheck = true; }
		if (exhaust) { this.exhaustCheck = true; }
		if (costChange && !DuelistMod.noCostChanges)	{ this.costChangeCheck = true; }
		if (tributeChange) { this.tributeCheck = true; }
		if (summonChange) { this.summonCheck = true; }
		if (DuelistMod.debug)
		{
			DuelistMod.logger.info("Stack trace indicating caller of this action [1]: " + Thread.currentThread().getStackTrace()[1].getMethodName());
			DuelistMod.logger.info("Stack trace indicating caller of this action [2]: " + Thread.currentThread().getStackTrace()[2].getMethodName());
			DuelistMod.logger.info("Stack trace indicating caller of this action [3]: " + Thread.currentThread().getStackTrace()[3].getMethodName());
			DuelistMod.logger.info("Stack trace indicating caller of this action [4]: " + Thread.currentThread().getStackTrace()[4].getMethodName());
		}
	}
	
	public RandomizedHandAction(AbstractCard c, boolean upgrade, boolean costChange, int lowCost, int highCost) 
	{
		this.actionType = ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_FAST;
		this.cardRef = c;
		this.lowCostRoll = lowCost;
		this.highCostRoll = highCost;
		this.lowSummonRoll = 0;
		this.highSummonRoll = 0;
		this.lowTributeRoll = 0;
		this.highTributeRoll = 0;
		this.tributeChangeCombatCheck = false;
		this.summonChangeCombatCheck = false;
		this.upgradeCheck = false;
		this.etherealCheck = false;
		this.exhaustCheck = false;
		this.tributeCheck = false;
		this.summonCheck = false;
		if (costChange && !DuelistMod.noCostChanges)	
		{ 
			this.costChangeCheck = true; 
			this.lowCostRoll = lowCost;
			this.highCostRoll = highCost;
		}
		else { this.costChangeCheck = false; }
		if (DuelistMod.debug)
		{
			DuelistMod.logger.info("Stack trace indicating caller of this action [1]: " + Thread.currentThread().getStackTrace()[1].getMethodName());
			DuelistMod.logger.info("Stack trace indicating caller of this action [2]: " + Thread.currentThread().getStackTrace()[2].getMethodName());
			DuelistMod.logger.info("Stack trace indicating caller of this action [3]: " + Thread.currentThread().getStackTrace()[3].getMethodName());
			DuelistMod.logger.info("Stack trace indicating caller of this action [4]: " + Thread.currentThread().getStackTrace()[4].getMethodName());
		}
	}
	
    public RandomizedHandAction(AbstractCard c, boolean upgrade, boolean ethereal, boolean exhaust,	boolean costChange, boolean tributeChange, boolean summonChange, boolean tribChangeCombat, boolean summonChangeCombat, int lowCost, int highCost, int lowTrib, int highTrib, int lowSummon, int highSummon) 
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.cardRef = c;
        this.lowCostRoll = lowCost;
        this.highCostRoll = highCost;
        this.lowSummonRoll = lowSummon;
        this.highSummonRoll = highSummon;
        this.lowTributeRoll = lowTrib;
        this.highTributeRoll = highTrib;
        this.tributeChangeCombatCheck = tribChangeCombat;
        this.summonChangeCombatCheck = summonChangeCombat;
        if (upgrade) { this.upgradeCheck = true; }
		if (ethereal) { this.etherealCheck = true; }
		if (exhaust) { this.exhaustCheck = true; }
		if (costChange && !DuelistMod.noCostChanges)	{ this.costChangeCheck = true; }
		if (tributeChange) { this.tributeCheck = true; }
		if (summonChange) { this.summonCheck = true; }
		if (DuelistMod.debug)
		{
			DuelistMod.logger.info("Stack trace indicating caller of this action [1]: " + Thread.currentThread().getStackTrace()[1].getMethodName());
			DuelistMod.logger.info("Stack trace indicating caller of this action [2]: " + Thread.currentThread().getStackTrace()[2].getMethodName());
		}
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) 
        {
            AbstractCard c = cardRef.makeStatEquivalentCopy();
            if (!c.upgraded && upgradeCheck)
    		{
    			c.upgrade();
    		}
            
            if (!c.isEthereal && etherealCheck && !c.hasTag(Tags.NEVER_ETHEREAL)) {
                c.isEthereal = true;
                c.rawDescription = "Ethereal NL " + c.rawDescription;
    		}
    		
    		if (!c.exhaust && exhaustCheck && !c.hasTag(Tags.NEVER_EXHAUST)) {
                c.exhaust = true;
                c.rawDescription = c.rawDescription + " NL Exhaust.";
    		}
    		
    		if (costChangeCheck)
    		{
    			int randomNum = AbstractDungeon.cardRandomRng.random(lowCostRoll, highCostRoll);
    			if (DuelistMod.onlyCostDecreases)
    			{
    				if (randomNum < c.cost)
    				{
    					c.costForTurn = randomNum;
    	    			c.isCostModifiedForTurn = true;
    				}
    			}
    			else
    			{
	    			c.costForTurn = randomNum;
	    			c.isCostModifiedForTurn = true;
    			}
    		}       
    		
    		if (summonCheck && c instanceof DuelistCard)
    		{
    			int randomNum = AbstractDungeon.cardRandomRng.random(lowSummonRoll, highSummonRoll);
    			DuelistCard dC = (DuelistCard)c;
    			if (summonChangeCombatCheck && dC.baseSummons > 0)
    			{
    				dC.modifySummons(randomNum);
    			}
    			else if (dC.baseSummons > 0)
    			{
    				dC.modifySummonsForTurn(randomNum);
    			}
    		}
    		
    		if (tributeCheck && c instanceof DuelistCard)
    		{
    			int randomNum = AbstractDungeon.cardRandomRng.random(lowTributeRoll, highTributeRoll);
    			DuelistCard dC = (DuelistCard)c;
    			if (tributeChangeCombatCheck && dC.baseTributes > 0)
    			{
    				dC.modifyTributes(-randomNum);
    			}
    			else if (dC.baseTributes > 0)
    			{
    				dC.modifyTributesForTurn(-randomNum);
    			}
    		}
    		
            c.initializeDescription();
            
            if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE)
            {
            	AbstractDungeon.actionManager.addToBottom(new MakeStatEquivalentLocal(c));
            }
            else
            {
            	if (DuelistMod.debug)
            	{
            		System.out.println("theDuelist:RandomizedHandAction:update() ---> got a hand size bigger than allowed, so skipped adding card to hand");
            	}
            }
            this.tickDuration();
        }
        this.isDone = true;
    }

    public class MakeStatEquivalentLocal extends AbstractGameAction {
        private AbstractCard c;

        public MakeStatEquivalentLocal(AbstractCard c) {
            this.actionType = ActionType.CARD_MANIPULATION;
            this.duration = Settings.ACTION_DUR_FAST;
            this.c = c;

        }

        public void update() {
            if (this.duration == Settings.ACTION_DUR_FAST) {
            	if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE)
            	{
            		AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c));
            		if (DuelistMod.debug) 
            		{
            			DuelistMod.logger.info("Added " + c.originalName + " to hand from RandomizedAction"); 
            		}
            	}
            	else
            	{
            		AbstractDungeon.player.createHandIsFullDialog();
            	}
                tickDuration();
                this.isDone = true;
            }
        }
    }

}
