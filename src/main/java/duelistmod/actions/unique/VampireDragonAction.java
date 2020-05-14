package duelistmod.actions.unique;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.*;

import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tokens.ShadowToken;

public class VampireDragonAction extends AbstractGameAction
{
    public int[] damage;
    
    public VampireDragonAction(final AbstractCreature source, final int[] amount, final DamageInfo.DamageType type, final AttackEffect effect) {
        this.setValues(null, source, amount[0]);
        this.damage = amount;
        this.actionType = ActionType.DAMAGE;
        this.damageType = type;
        this.attackEffect = effect;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            boolean playedMusic = false;
            for (int temp = AbstractDungeon.getCurrRoom().monsters.monsters.size(), i = 0; i < temp; ++i) {
                if (!AbstractDungeon.getCurrRoom().monsters.monsters.get(i).isDying && AbstractDungeon.getCurrRoom().monsters.monsters.get(i).currentHealth > 0 && !AbstractDungeon.getCurrRoom().monsters.monsters.get(i).isEscaping) {
                    if (playedMusic) {
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(AbstractDungeon.getCurrRoom().monsters.monsters.get(i).hb.cX, AbstractDungeon.getCurrRoom().monsters.monsters.get(i).hb.cY, this.attackEffect, true));
                    }
                    else {
                        playedMusic = true;
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(AbstractDungeon.getCurrRoom().monsters.monsters.get(i).hb.cX, AbstractDungeon.getCurrRoom().monsters.monsters.get(i).hb.cY, this.attackEffect));
                    }
                }
            }
        }
        this.tickDuration();
        if (this.isDone) {
            for (final AbstractPower p : AbstractDungeon.player.powers) {
                p.onDamageAllEnemies(this.damage);
            }
           
            ArrayList<AbstractMonster> hits = new ArrayList<>();
            for (int j = 0; j < AbstractDungeon.getCurrRoom().monsters.monsters.size(); ++j) {
                final AbstractMonster target = AbstractDungeon.getCurrRoom().monsters.monsters.get(j);
                if (!target.isDying && target.currentHealth > 0 && !target.isEscaping) {
                    target.damage(new DamageInfo(this.source, this.damage[j], this.damageType));
                    if (target.lastDamageTaken > 0) {                        
                    	hits.add(target);
                        for (int k = 0; k < target.lastDamageTaken / 2 && k < 10; ++k) {
                            this.addToBot(new VFXAction(new FlyingOrbEffect(target.hb.cX, target.hb.cY)));
                        }
                    }
                }
            }            
            if (hits.size() > 0) { DuelistCard.summon(AbstractDungeon.player, hits.size(), new ShadowToken()); }
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
            this.addToTop(new WaitAction(0.1f));
        }
    }
}
