package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

/* This action is an optimization to be used in place of EvokeSpecificOrbAction in cases
   where an orb is not already in an AbstractPlayer's orb list, such as when inverting orbs.
 */
public class ActivateOrbEvokeEffectAction extends AbstractGameAction {
    private AbstractOrb orb;

    public ActivateOrbEvokeEffectAction(AbstractOrb orb, int amt) {
        duration = startDuration = Settings.FAST_MODE ? Settings.ACTION_DUR_XFAST : Settings.ACTION_DUR_FAST;
        amount = amt;
        actionType = ActionType.DAMAGE;
        this.orb = orb;
    }

    @Override
    public void update() {
        if (duration == startDuration && orb != null) {
            for (int i=0; i < amount; i++) {
                orb.onEvoke();
            }
        }
        tickDuration();
    }
}
