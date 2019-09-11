package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class SuperheavyBenkeiAction extends AbstractGameAction
{
    private DamageInfo info;
    
    public SuperheavyBenkeiAction(final AbstractCreature target, final DamageInfo info) {
        this.setValues(target, this.info = info);
        this.actionType = ActionType.DAMAGE;
        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
    }
    
    @Override
    public void update() {
        if (this.shouldCancelAction()) {
            this.isDone = true;
            return;
        }
        this.tickDuration();
        if (this.isDone) {
            this.target.damage(this.info);
            this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player,  this.target.lastDamageTaken)));
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
            else {
                this.addToTop(new WaitAction(0.1f));
            }
        }
    }
}
