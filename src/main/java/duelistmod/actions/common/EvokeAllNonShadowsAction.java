package duelistmod.actions.common;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.*;

import duelistmod.orbs.Shadow;

public class EvokeAllNonShadowsAction extends AbstractGameAction {
    public EvokeAllNonShadowsAction() {
        target = AbstractDungeon.player;
        actionType = ActionType.SPECIAL;
        duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update()
    {
        ArrayList<AbstractOrb> orbs = new ArrayList<AbstractOrb>();
        for (AbstractOrb o : AbstractDungeon.player.orbs) { if (!(o instanceof EmptyOrbSlot) && !(o instanceof Shadow)) { orbs.add(o); }}
        if (orbs.size() > 0)
        {
        	addToTop(new EvokeSpecificOrbAction(orbs.get(0), 1));
        	if (orbs.size() > 1) 
        	{
        		addToBot(new EvokeAllNonShadowsAction());
        	}
        }
        this.isDone = true;
    }
}
