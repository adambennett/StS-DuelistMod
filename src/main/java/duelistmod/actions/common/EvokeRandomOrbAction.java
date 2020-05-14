package duelistmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import duelistmod.helpers.Util;

import java.util.ArrayList;

public class EvokeRandomOrbAction extends AbstractGameAction {
    public EvokeRandomOrbAction() {
        target = AbstractDungeon.player;
        actionType = ActionType.DAMAGE;
        duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update()
    {
        ArrayList<AbstractOrb> orbs = new ArrayList<AbstractOrb>();
        for (AbstractOrb o : AbstractDungeon.player.orbs) { if (!(o instanceof EmptyOrbSlot)) { orbs.add(o); }}
        if (orbs.size() > 0) {
            int index = AbstractDungeon.cardRandomRng.random(0, orbs.size() - 1);
            Util.log("EvokeRandomOrbAction is evoking at index " + index);
            AbstractOrb toEvoke = orbs.get(index);
            Util.log("EvokeRandomOrbAction is evoking orb " + toEvoke.name);
            addToTop(new EvokeSpecificOrbAction(toEvoke, 1));
        }
        this.isDone = true;
    }
}
