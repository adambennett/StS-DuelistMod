package duelistmod.actions.unique;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class BurningTakeDamageAction extends AbstractGameAction
{
    public BurningTakeDamageAction(final AbstractCreature target, final AbstractCreature source, final int amount, final AttackEffect effect) {
        this.setValues(target, source, amount);
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
        this.duration = 0.33f;
    }

    @Override
    public void update() 
    {
    	if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT)
    	{
    		this.isDone = true;
    		return;
    	}
    	if (this.duration == 0.33f && this.target.currentHealth > 0) 
    	{
    		AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
    	}
    	this.tickDuration();
    	if (this.isDone) 
    	{
    		if (this.target.currentHealth > 0) 
    		{
    			this.target.tint.color = Color.RED.cpy();
    			this.target.tint.changeColor(Color.WHITE.cpy());
    			this.target.damage(new DamageInfo(this.source, this.amount, DamageInfo.DamageType.THORNS));
    		}
    	}
    	if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) { AbstractDungeon.actionManager.clearPostCombatActions(); }
    	this.addToTop(new WaitAction(0.1f));
    }
}
