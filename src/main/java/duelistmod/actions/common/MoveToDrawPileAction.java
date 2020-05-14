package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

public class MoveToDrawPileAction extends AbstractGameAction {

	private AbstractCard cardRef;
	

	public MoveToDrawPileAction(AbstractCard c)
	{
		this.actionType = ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_FAST;
		this.cardRef = c;
	}
	
	

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) 
        {
            AbstractCard c = cardRef.makeStatEquivalentCopy();
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
            if (this.duration == Settings.ACTION_DUR_FAST) 
            {
            	AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, true, false));
                tickDuration();
                this.isDone = true;
            }
        }
    }

}
