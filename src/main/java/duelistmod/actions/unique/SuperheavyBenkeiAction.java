package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import duelistmod.abstracts.GuardedDuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.dto.AnyGuardedCard;

import java.util.ArrayList;
import java.util.List;

public class SuperheavyBenkeiAction extends AbstractGameAction {
    private final DamageInfo info;
    private final boolean isGuarded;
    private final AnyDuelist duelist;
    private final GuardedDuelistCard card;
    private final List<AbstractCreature> targets;

    public SuperheavyBenkeiAction(GuardedDuelistCard caller, final boolean isGuarded, final AnyDuelist duelist, final AbstractCreature target, final DamageInfo info) {
        this.setValues(target, this.info = info);
        this.actionType = ActionType.DAMAGE;
        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
        this.isGuarded = isGuarded;
        this.duelist = duelist;
        this.card = caller;
        this.targets = new ArrayList<>();
        if (target != null) {
            this.targets.add(target);
        }
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
            if (this.target.lastDamageTaken > 0 && this.isGuarded) {
                this.duelist.applyPowerToSelf(new VigorPower(this.duelist.creature(), this.target.lastDamageTaken));
                this.card.triggerGuarded(AnyGuardedCard.from(this.card), this.duelist, this.targets);
            }
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            } else {
                this.addToTop(new WaitAction(0.1f));
            }
        }
    }
}
