package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.variables.Tags;

public class WildNatureActionB extends AbstractGameAction
{
    private DamageInfo info;
    private float startingDuration;
    
    public WildNatureActionB() 
    {
        this.actionType = ActionType.WAIT;
        this.attackEffect = AttackEffect.FIRE;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }
    
    @Override
    public void update() {
        if (this.duration == this.startingDuration) 
        {
        	AbstractPlayer p = AbstractDungeon.player;
        	int nats = 0;
        	for (AbstractCard c : p.drawPile.group) { if (c.hasTag(Tags.NATURIA)) { nats++; }}
        	for (AbstractMonster mon : AbstractDungeon.getCurrRoom().monsters.monsters)
        	{
        		if (!mon.isDead && !mon.isDying && !mon.isDeadOrEscaped())
        		{
        			this.info = new DamageInfo(mon, nats);
                    this.addToTop(new DamageAction(this.target, this.info, AttackEffect.FIRE));                    
        		}
        	}
        }
        this.tickDuration();
    }
}
