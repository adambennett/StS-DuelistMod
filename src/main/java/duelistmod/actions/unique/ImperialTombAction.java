package duelistmod.actions.unique;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

public class ImperialTombAction extends AbstractGameAction 
{
	private ArrayList<AbstractCard> cardRefs = new ArrayList<AbstractCard>();

	public ImperialTombAction(ArrayList<AbstractCard> cards)
	{
		this.actionType = ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_FAST;
		this.cardRefs = cards;
	}

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) 
        {
            AbstractDungeon.actionManager.addToBottom(new MakeStatEquivalentLocal(cardRefs));            
            this.tickDuration();
        }
        this.isDone = true;
    }

    public class MakeStatEquivalentLocal extends AbstractGameAction {
    	private ArrayList<AbstractCard> cardRefs = new ArrayList<AbstractCard>();

        public MakeStatEquivalentLocal(ArrayList<AbstractCard> cards) {
            this.actionType = ActionType.CARD_MANIPULATION;
            this.duration = Settings.ACTION_DUR_FAST;
            this.cardRefs = cards;
        }

        public void update() {
            if (this.duration == Settings.ACTION_DUR_FAST) 
            {
            	for (AbstractCard c : cardRefs)
            	{
            		AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, true, false));
            	}
            	tickDuration();
            	this.isDone = true;
            }
        }
    }

}
