package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Tags;

public class WildNatureActionA extends AbstractGameAction
{
    private float startingDuration;
    private int shuffle;
    
    public WildNatureActionA(int shuffling) 
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.shuffle = shuffling;
    }
    
    @Override
    public void update() {
        if (this.duration == this.startingDuration) 
        {
        	for (int i = 0; i < this.shuffle; i++)
        	{
        		AbstractCard rand = DuelistCard.returnTrulyRandomFromSet(Tags.NATURIA);
        		AbstractDungeon.actionManager.addToTop(new MakeTempCardInDrawPileAction(rand, 1, true, true));
        	}
        }
        this.tickDuration();
    }
}
