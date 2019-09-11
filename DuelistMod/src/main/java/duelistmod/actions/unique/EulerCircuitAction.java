package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.abstracts.DuelistCard;
import duelistmod.stances.*;

public class EulerCircuitAction extends AbstractGameAction
{
    public EulerCircuitAction() {
        this.duration = Settings.ACTION_DUR_FAST;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.stance.ID.equals("theDuelist:Samurai")) 
            {
                DuelistCard.changeStance(new Guarded());
            }
            else if (AbstractDungeon.player.stance.ID.equals("theDuelist:Guarded")) 
            {
            	 DuelistCard.changeStance(new Samurai());
            }
            if (Settings.FAST_MODE) {
                this.isDone = true;
                return;
            }
        }
        this.tickDuration();
    }
}
