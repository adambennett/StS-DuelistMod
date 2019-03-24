package defaultmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

public class RandomizedAction extends AbstractGameAction {

	private AbstractCard cardRef;
	private boolean exhaustCheck = false;
	private boolean etherealCheck = false;
	private boolean costChangeCheck = false;
	private boolean upgradeCheck = false;
	private int lowCostRoll = 1;
	private int highCostRoll = 4;
	
    public RandomizedAction(AbstractCard c, boolean upgrade, boolean ethereal, boolean exhaust, boolean costChange, int lowCost, int highCost) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.cardRef = c;
        this.lowCostRoll = lowCost;
        this.highCostRoll = highCost;
        if (upgrade)
        {
        	this.upgradeCheck = true;
        }
		if (ethereal)
		{
			this.etherealCheck = true;
		}
		if (exhaust)
		{			
			this.exhaustCheck = true;
		}
		if (costChange)
		{
			this.costChangeCheck = true;
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
            c.initializeDescription();
            AbstractDungeon.actionManager.addToBottom(new MakeStatEquivalentLocal(c));
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
                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c));
                tickDuration();
                this.isDone = true;
            }
        }
    }

}
