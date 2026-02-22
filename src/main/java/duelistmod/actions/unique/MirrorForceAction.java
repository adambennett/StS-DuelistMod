package duelistmod.actions.unique;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.Util;

public class MirrorForceAction extends AbstractGameAction {

    private final AbstractCreature m;
    private final AnyDuelist duelist;

    public MirrorForceAction(int intangibleAmount, AbstractCreature m, AnyDuelist duelist) {
        this.actionType = ActionType.SPECIAL;
        this.amount = intangibleAmount;
        this.m = m;
        this.duelist = duelist;
    }

    @Override
    public void update() {
        if (!(this.m instanceof AbstractMonster) || !this.duelist.player()) {
            this.isDone = true;
            return;
        }

        AbstractMonster monsterTarget = (AbstractMonster) this.m;
        if (monsterTarget.isDeadOrEscaped()) {
            this.isDone = true;
            return;
        }

        if (!isAttackIntent(monsterTarget.intent) || monsterTarget.getIntentBaseDmg() < 0) {
            this.isDone = true;
            return;
        }

        int damage = getTotalIntentDamage(monsterTarget);
        if (damage <= 0) {
            this.isDone = true;
            return;
        }

        if (this.amount > 0) {
            this.duelist.applyPowerToSelf(new IntangiblePlayerPower(this.duelist.creature(), this.amount));
        }

        for (AbstractMonster currentTarget : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!currentTarget.isDeadOrEscaped()) {
                DamageInfo info = new DamageInfo(
                        this.duelist.creature(),
                        damage,
                        DamageInfo.DamageType.NORMAL
                );
                this.addToBot(new DamageAction(currentTarget, info, AttackEffect.SHIELD));
            }
        }

        this.isDone = true;
    }

    private static boolean isAttackIntent(AbstractMonster.Intent intent) {
        return intent == AbstractMonster.Intent.ATTACK
                || intent == AbstractMonster.Intent.ATTACK_BUFF
                || intent == AbstractMonster.Intent.ATTACK_DEBUFF
                || intent == AbstractMonster.Intent.ATTACK_DEFEND;
    }

    private static int getTotalIntentDamage(AbstractMonster m) {
        // This is the per-hit damage shown on the intent UI (already after modifiers).
        int perHit = m.getIntentDmg();
        if (perHit <= 0) return 0;

        boolean isMulti = false;
        int multiAmt = 1;

        try {
            isMulti = ReflectionHacks.getPrivate(m, AbstractMonster.class, "isMultiDmg");
            if (isMulti) {
                multiAmt = ReflectionHacks.getPrivate(m, AbstractMonster.class, "intentMultiAmt");
                if (multiAmt < 1) multiAmt = 1;
            }
        } catch (Exception ex) {
            Util.logError("Relfection failure", ex);
        }

        long total = (long) perHit * (long) multiAmt;
        if (total > Integer.MAX_VALUE) return Integer.MAX_VALUE;
        return (int) total;
    }
}
