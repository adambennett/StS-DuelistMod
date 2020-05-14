package duelistmod.actions.unique;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.abstracts.DuelistCard;
import duelistmod.powers.duelistPowers.FrozenDebuff;

public class CryomancerTribAction extends AbstractGameAction
{
    private static final float DURATION = 0.1f;
    private int magicNumber = 0;

    public CryomancerTribAction(int amt)
    {
        actionType = ActionType.SPECIAL;
        duration = DURATION;
        magicNumber = amt;
    }

    @Override
    public void update()
    {
    	if (duration == DURATION)  
        {
        	ArrayList<AbstractMonster> mons = DuelistCard.getAllMons();
        	ArrayList<AbstractMonster> monst = new ArrayList<AbstractMonster>();
        	for (AbstractMonster m : mons)
        	{
        		if (!m.hasPower(FrozenDebuff.POWER_ID)) { monst.add(m); }
        	}

    		while (monst.size() > this.magicNumber)
    		{
    			monst.remove(AbstractDungeon.cardRandomRng.random(monst.size() - 1));
    		}
        	
        	if (monst.size() > 0)
        	{
        		for (AbstractMonster mon : monst)
        		{
        			DuelistCard.applyPower(new FrozenDebuff(mon, AbstractDungeon.player), mon);
        		}
        	}
        }
        tickDuration();
    }
}
