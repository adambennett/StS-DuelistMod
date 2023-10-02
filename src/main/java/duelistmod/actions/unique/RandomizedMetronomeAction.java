package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import basemod.BaseMod;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.variables.*;



public class RandomizedMetronomeAction extends AbstractGameAction 
{
	private AbstractCard cardRef;
	private boolean exhaustCheck = false;
	private boolean etherealCheck = false;
	private boolean costChangeCheck = false;
	private boolean upgradeCheck = false;
	private boolean dontTrigFromFairyBox = false;
	private int lowCostRoll = 1;
	private int highCostRoll = 3;

	public RandomizedMetronomeAction(AbstractCard c, boolean upgraded)
	{
		this.actionType = ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_FAST;
		this.cardRef = c;
		this.lowCostRoll = 0;
		this.highCostRoll = 3;
		this.exhaustCheck = true;
		this.etherealCheck = true;
		this.upgradeCheck = upgraded;
		if (DuelistMod.debug)
		{
			DuelistMod.logger.info("Stack trace indicating caller of this action [1]: " + Thread.currentThread().getStackTrace()[1].getMethodName());
			DuelistMod.logger.info("Stack trace indicating caller of this action [2]: " + Thread.currentThread().getStackTrace()[2].getMethodName());
			DuelistMod.logger.info("Stack trace indicating caller of this action [3]: " + Thread.currentThread().getStackTrace()[3].getMethodName());
			DuelistMod.logger.info("Stack trace indicating caller of this action [4]: " + Thread.currentThread().getStackTrace()[4].getMethodName());
		}
		checkFlags();
	}
	
    private void checkFlags()
    {
    	if (DuelistMod.persistentDuelistData.RandomizedSettings.getNoCostChanges()) { this.costChangeCheck = false; }
    	if (DuelistMod.persistentDuelistData.RandomizedSettings.getAlwaysUpgrade()) { this.upgradeCheck = true; }
    	if (DuelistMod.persistentDuelistData.RandomizedSettings.getNeverUpgrade()) { this.upgradeCheck = false; }
    	if (!DuelistMod.persistentDuelistData.RandomizedSettings.getAllowEthereal()) { this.etherealCheck = false; }
    	if (!DuelistMod.persistentDuelistData.RandomizedSettings.getAllowExhaust()) { this.exhaustCheck = false; }
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) 
        {
            AbstractCard c = cardRef.makeStatEquivalentCopy();
            if (c.canUpgrade() && upgradeCheck)
    		{
    			c.upgrade();
    		}
            
            if (!c.isEthereal && etherealCheck && !c.hasTag(Tags.NEVER_ETHEREAL)) {
                c.isEthereal = true;
                c.rawDescription = Strings.etherealForCardText + c.rawDescription;
    		}
    		
    		if (!c.exhaust && exhaustCheck && !c.hasTag(Tags.NEVER_EXHAUST) && !c.type.equals(CardType.POWER)) {
                c.exhaust = true;
                c.rawDescription = c.rawDescription + DuelistMod.exhaustForCardText;
    		}
    		
    		if (costChangeCheck && c.cost >= 0 && c.costForTurn >= 0)
    		{
    			int randomNum = AbstractDungeon.cardRandomRng.random(lowCostRoll, highCostRoll);
    			if (DuelistMod.persistentDuelistData.RandomizedSettings.getOnlyCostDecreases())
    			{
    				if (randomNum < c.cost)
    				{
    					c.costForTurn = randomNum;
    	    			c.isCostModifiedForTurn = true;
    	    			if (DuelistMod.debug) { DuelistMod.logger.info("Only cost decreases allowed for randomized cards"); }
    				}
    			}
    			else
    			{
	    			c.costForTurn = randomNum;
	    			c.isCostModifiedForTurn = true;
    			}
    		}
    		
    		
    		if (c.magicNumber > 0)
    		{
    			int roll = AbstractDungeon.cardRandomRng.random(1, 5);
    			if (roll == 1)
    			{
    				int randomNum = AbstractDungeon.cardRandomRng.random(0, 2);
    				c.magicNumber = c.baseMagicNumber += randomNum;  
    				Util.log("MetronomeRelicD modified the magic number of " + c.name);
    			}
    		}
    		
    		if (dontTrigFromFairyBox)
    		{
    			c.dontTriggerOnUseCard = false;
    		}
			if (c instanceof DuelistCard) {
				((DuelistCard)c).fixUpgradeDesc();
			}
            c.initializeDescription();
            
            if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE)
            {
            	AbstractDungeon.actionManager.addToBottom(new MakeStatEquivalentLocal(c, dontTrigFromFairyBox));
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

        public MakeStatEquivalentLocal(AbstractCard c, boolean dontTrig) {
            this.actionType = ActionType.CARD_MANIPULATION;
            this.duration = Settings.ACTION_DUR_FAST;
            this.c = c;
            if (dontTrig) { this.c.dontTriggerOnUseCard = false; } 
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
