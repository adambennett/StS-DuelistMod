package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.dto.AnyDuelist;
import duelistmod.powers.duelistPowers.FangsPower;

public class HiddenFangsOfRevengeAction extends AbstractGameAction {

    private final AnyDuelist duelist;
    private final int damage;
    private final int fangGain;

    public HiddenFangsOfRevengeAction(AnyDuelist duelist, int damage, int fangGain) {
        this.duelist = duelist;
        this.damage = damage;
        this.fangGain = fangGain;
    }

    public void update() {
        // Deal damage to random enemy
        AbstractCreature target = null;
        if (this.duelist.player()) {
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                AbstractMonster random = AbstractDungeon.getMonsters().getRandomMonster(true);
                if (random != null) {
                    target = random;

                }
            }
        } else if (this.duelist.getEnemy() != null) {
            target = AbstractDungeon.player;
        }
        if (target != null) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(duelist.creature(), this.damage, DamageInfo.DamageType.NORMAL), AttackEffect.SLASH_VERTICAL));
        }

        // Gain Fangs
        if (this.fangGain > 0) {
            this.duelist.applyPowerToSelf(new FangsPower(duelist.creature(), duelist.creature(), this.fangGain));
        }
        this.isDone = true;
    }

}
