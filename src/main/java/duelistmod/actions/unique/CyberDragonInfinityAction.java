package duelistmod.actions.unique;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;

public class CyberDragonInfinityAction extends AbstractGameAction
{
    private DamageInfo info;
    private static final float DURATION = 0.1f;
    private static final float POST_ATTACK_WAIT_DUR = 0.1f;
    private int mod = 0;
    
    public CyberDragonInfinityAction(final DamageInfo info, final AttackEffect effect, int magic) {
        this.info = info;
        this.setValues(AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng), info);
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
        this.duration = 0.5f;
        this.mod = magic;
        if (this.mod < 1) { this.mod = 1; }
    }
    
    @Override
    public void update() {
        if (this.shouldCancelAction()) {
            this.isDone = true;
            return;
        }
        if (monsHealthCheck()) { this.isDone = true; return; }
        else
        {
        	this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
	        if (this.duration == 0.5f) {
	            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
	        }
	        this.tickDuration();
            if (this.attackEffect == AttackEffect.POISON) {
                this.target.tint.color.set(Color.CHARTREUSE);
                this.target.tint.changeColor(Color.WHITE.cpy());
            }
            else if (this.attackEffect == AttackEffect.FIRE) {
                this.target.tint.color.set(Color.RED);
                this.target.tint.changeColor(Color.WHITE.cpy());
            }
            this.target.damage(this.info);
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            	this.isDone = true;
                AbstractDungeon.actionManager.clearPostCombatActions();	                
            }
            //this.addToTop(new WaitAction(0.1f));
        }
    }
    
    private boolean monsHealthCheck()
    {
    	if (this.info.base < 1) { Util.log("Cyber Dragon Infinity was going to deal 0 damage infinitely, so we are returning early"); return true; }
    	ArrayList<AbstractMonster> mons = DuelistCard.getAllMons();
    	for (AbstractMonster m : mons)
    	{
    		float mult = this.mod / 100.0f;
    		float checkVal = m.maxHealth * mult;
    		if (m.currentHealth >= checkVal) { return false; }
    	}
    	
    	return true;
    }
}
