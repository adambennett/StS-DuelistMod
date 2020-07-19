package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.monsters.*;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.cardManip.*;
import duelistmod.helpers.*;

import java.util.*;

public class GoldenSparksAction extends AbstractGameAction
{
    private static final float DURATION = 0.1f;
    private static int magicNum = 0;

    public GoldenSparksAction(AbstractMonster monster, int magic)
    {
        target = monster;
        actionType = ActionType.DAMAGE;
        duration = DURATION;
        magicNum = magic;
    }

    @Override
    public void update()
    {
        if (duration == DURATION && target != null) 
        {
        	if ((((AbstractMonster)this.target).isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion"))
            {
				AbstractDungeon.player.gainGold(magicNum);
            }
            
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
        tickDuration();
    }
}
