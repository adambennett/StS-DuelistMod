package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.*;

import duelistmod.powers.duelistPowers.*;

import com.megacrit.cardcrawl.actions.common.*;

public class HuntingInstinctAction extends AbstractGameAction
{
    private DamageInfo info;
    private float startingDuration;
    private boolean up = false;
    
    public HuntingInstinctAction(final AbstractCreature target, final DamageInfo info, boolean upgraded) {
        this.setValues(target, this.info = info);
        this.actionType = ActionType.WAIT;
        this.attackEffect = AttackEffect.FIRE;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.up = upgraded;
    }
    
    @Override
    public void update() {
        if (this.duration == this.startingDuration) 
        {
        	AbstractPlayer p = AbstractDungeon.player;
        	//int leaf = 0;
        	int vine = 0;
        	if (p.hasPower(VinesPower.POWER_ID)) { vine = p.getPower(VinesPower.POWER_ID).amount; }
        	//if (p.hasPower(LeavesPower.POWER_ID)) { leaf = p.getPower(LeavesPower.POWER_ID).amount; }
            /*if (this.up)
            {
            	for (int i = 0; i < leaf + vine; ++i) {
                    this.addToTop(new DamageAction(this.target, this.info, AttackEffect.FIRE));
                }
            }
            else
            {*/
            	for (int i = 0; i < vine; ++i) {
                    this.addToTop(new DamageAction(this.target, this.info, AttackEffect.FIRE));
                }
           // }
        	
        }
        this.tickDuration();
    }
}
