package duelistmod.actions.unique;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.abstracts.DuelistCard;
import duelistmod.powers.duelistPowers.FrozenDebuff;

public class ColdWaveAction extends AbstractGameAction
{
    private static final float DURATION = 0.1f;

    public ColdWaveAction()
    {
        actionType = ActionType.SPECIAL;
        duration = DURATION;
    }

    @Override
    public void update()
    {
        if (duration == DURATION && target != null) 
        {
        	ArrayList<AbstractMonster> mons = DuelistCard.getAllMons();
        	ArrayList<AbstractMonster> monsters = new ArrayList<>();    	
        	for (AbstractMonster monst : mons) { if (!monst.hasPower(FrozenDebuff.POWER_ID)) { monsters.add(monst); }}
        	if (monsters.size() > 0)
        	{
        		int index = AbstractDungeon.cardRandomRng.random(monsters.size() - 1);
        		AbstractMonster mon = monsters.get(index);
        		if (mon != null) { DuelistCard.applyPower(new FrozenDebuff(mon, AbstractDungeon.player), mon); }
        		monsters.remove(index);
        		if (monsters.size() > 0)
        		{
        			index = AbstractDungeon.cardRandomRng.random(monsters.size() - 1);
            		mon = monsters.get(index);
            		if (mon != null) { DuelistCard.applyPower(new FrozenDebuff(mon, AbstractDungeon.player), mon); }
        		}
        	}
        }
        tickDuration();
    }
}
