package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import duelistmod.abstracts.DuelistCard;

public class SuperheavyBrawlerAction extends AbstractGameAction
{
    private DamageInfo info;
    
    public SuperheavyBrawlerAction(final AbstractCreature target, final DamageInfo info) {
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
            DuelistCard.gainTempHP(this.target.lastDamageTaken);
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
            else {
                this.addToTop(new WaitAction(0.1f));
            }
        }
    }
}
