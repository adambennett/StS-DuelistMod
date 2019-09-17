package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.abstracts.DuelistCard;
import duelistmod.stances.*;

public class GladiatorReturnAction extends AbstractGameAction
{
    public GladiatorReturnAction() {
        this.duration = Settings.ACTION_DUR_FAST;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.stance.ID.equals("theDuelist:Spectral")) 
            {
                DuelistCard.changeStance("theDuelist:Meditative");
            }
            else if (AbstractDungeon.player.stance.ID.equals("theDuelist:Meditative")) 
            {
            	 DuelistCard.changeStance("theDuelist:Spectral");
            }
            else
            {
            	DuelistCard.changeStance("theDuelist:Nimble");
            }
            if (Settings.FAST_MODE) {
                this.isDone = true;
                return;
            }
        }
        this.tickDuration();
    }
}
