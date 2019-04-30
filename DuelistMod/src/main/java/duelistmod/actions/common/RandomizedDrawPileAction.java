package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.*;

import basemod.BaseMod;
import duelistmod.DuelistMod;
import duelistmod.interfaces.DuelistCard;

public class RandomizedDrawPileAction extends AbstractGameAction {

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

	public RandomizedDrawPileAction(AbstractCard c)
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
	}
	
	public RandomizedDrawPileAction(AbstractCard c, boolean extras)
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
			this.costChangeCheck = true; 
			this.tributeCheck = true; 
			this.summonCheck = true; 
		}
	}
	
	public RandomizedDrawPileAction(AbstractCard c, boolean upgrade, boolean ethereal)
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
	}
	
	public RandomizedDrawPileAction(AbstractCard c, boolean upgrade, boolean ethereal, boolean exhaust)
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
	}
	
	public RandomizedDrawPileAction(AbstractCard c, boolean upgrade, boolean ethereal, boolean exhaust, boolean costChange)
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
		if (costChange) { this.costChangeCheck = true; }
	}
	
	public RandomizedDrawPileAction(AbstractCard c, boolean upgrade, boolean ethereal, boolean exhaust, boolean costChange, int lowCost, int highCost)
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
		if (costChange) { this.costChangeCheck = true; }
	}
	
	public RandomizedDrawPileAction(AbstractCard c, boolean upgrade, boolean ethereal, boolean exhaust,	boolean costChange, boolean tributeChange, boolean summonChange)
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
		if (costChange)	{ this.costChangeCheck = true; }
		if (tributeChange) { this.tributeCheck = true; }
		if (summonChange) { this.summonCheck = true; }
	}
	
	public RandomizedDrawPileAction(AbstractCard c, boolean upgrade, boolean ethereal, boolean exhaust,	boolean costChange, boolean tributeChange, boolean summonChange, boolean tribChangeCombat, boolean summonChangeCombat)
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
		if (costChange)	{ this.costChangeCheck = true; }
		if (tributeChange) { this.tributeCheck = true; }
		if (summonChange) { this.summonCheck = true; }
	}
	
    public RandomizedDrawPileAction(AbstractCard c, boolean upgrade, boolean ethereal, boolean exhaust,	boolean costChange, boolean tributeChange, boolean summonChange, boolean tribChangeCombat, boolean summonChangeCombat, int lowCost, int highCost, int lowTrib, int highTrib, int lowSummon, int highSummon) 
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
		if (costChange)	{ this.costChangeCheck = true; }
		if (tributeChange) { this.tributeCheck = true; }
		if (summonChange) { this.summonCheck = true; }
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) 
        {
            AbstractCard c = cardRef.makeStatEquivalentCopy();
            if (!c.upgraded && upgradeCheck)
    		{
    			c.upgrade();
    		}
            
            if (!c.isEthereal && etherealCheck) {
                c.isEthereal = true;
                c.rawDescription = "Ethereal NL " + c.rawDescription;
               // c.initializeDescription();
    		}
    		
    		if (!c.exhaust && exhaustCheck) {
                c.exhaust = true;
                c.rawDescription = c.rawDescription + " NL Exhaust.";
               // c.initializeDescription();
    		}
    		
    		if (costChangeCheck)
    		{
    			int randomNum = AbstractDungeon.cardRandomRng.random(lowCostRoll, highCostRoll);
    			c.costForTurn = randomNum;
    			c.isCostModifiedForTurn = true;
    			//c.initializeDescription();
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
            if (this.duration == Settings.ACTION_DUR_FAST) 
            {
            	AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, true, false));
                tickDuration();
                this.isDone = true;
            }
        }
    }

}
