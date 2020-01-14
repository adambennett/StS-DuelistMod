package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.*;

public class DarkCreatorAction extends AbstractGameAction
{
    private static final float DURATION = 0.1f;
    private AbstractPlayer p;

    public DarkCreatorAction()
    {
        actionType = ActionType.SPECIAL;
        duration = DURATION;
        this.p = AbstractDungeon.player;
    }

    @Override
    public void update()
    {
        if (duration == DURATION) 
        {
        	int counter = 0;
        	for (AbstractCard c : p.drawPile.group) { counter++; this.addToBot(new ExhaustSpecificCardSuperFastAction(c, p.drawPile, true)); }
        	if (counter > 0) 
        	{
        		Util.log("Dark Creator found cards to exhaust, # of cards: " + counter);
        		for (AbstractMonster mon : AbstractDungeon.getCurrRoom().monsters.monsters)
        		{
        			if (!mon.isDead && !mon.isDying && !mon.isDeadOrEscaped() && !mon.halfDead)
        			{
        				DuelistCard.applyPower(DebuffHelper.getRandomDebuff(p, mon, counter), mon);
        				Util.log("Dark Creator debuffing: " + mon.name);
        			}
        		}
        	}
        	else
        	{
        		Util.log("Dark Creator found no cards to exhaust. Counter: " + counter);
        	}
        }
        tickDuration();
    }
}
