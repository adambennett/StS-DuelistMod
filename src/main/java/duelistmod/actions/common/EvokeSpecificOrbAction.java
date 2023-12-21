package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class EvokeSpecificOrbAction extends AbstractGameAction {
    private final AbstractOrb orb;
    
    public EvokeSpecificOrbAction(final AbstractOrb orb, int amount) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.orb = orb;
        this.actionType = ActionType.DAMAGE;
        this.amount = amount;
    }
    
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
        	if (this.orb != null) {
        		int slot = AbstractDungeon.player.orbs.indexOf(orb);
                if (slot > -1 && slot < AbstractDungeon.player.orbs.size()) {
                    AbstractDungeon.player.orbs.remove(slot);
                    AbstractDungeon.player.orbs.add(0, orb);
                    if (this.amount > 1) { for (int i = 1; i < this.amount; i++) { this.orb.onEvoke(); }}
                    AbstractDungeon.player.evokeOrb();
                }
        	}
        }
        this.tickDuration();
    }
}
