package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.relics.MachineToken;

public class DetonationAction extends AbstractGameAction {

    private int detonations;
    private final int damage;
    private final boolean damageAll;
    private final boolean selfDamage;
    private final boolean damageEnemies;
    private final boolean hpDamage;
    private final boolean superExploding;
    private AbstractCreature m;

    public DetonationAction(int detonations, boolean superExploding) {
        this(detonations, superExploding, false, false, true, AbstractDungeon.player);
    }

    public DetonationAction(int detonations, boolean superExploding, boolean damageAll, boolean damageEnemies, boolean hpDamage, AbstractCreature target) {
        this.m = AbstractDungeon.player.hasRelic(MachineToken.ID) && target != null && target.equals(AbstractDungeon.player) ? null : target;
        this.damageAll = damageAll;
        this.detonations = detonations;
        this.damageEnemies = AbstractDungeon.player.hasRelic(MachineToken.ID) || damageEnemies;
        this.selfDamage = this.m != null && this.m.equals(AbstractDungeon.player) && !this.damageEnemies && !this.damageAll;
        this.hpDamage = this.selfDamage && hpDamage;
        this.superExploding = superExploding;
        this.damage = Util.getExplodingTokenDamage(superExploding);
    }

    @Override
    public void update() {
        if (this.detonations < 1 || this.damage <= 0 || (!this.selfDamage && !this.damageEnemies && !this.damageAll)) {
            this.isDone = true;
            return;
        }

        if (this.damageAll) {
            DuelistCard.attackAllEnemiesFireThorns(this.damage);
            Util.log("Detonating for " + this.damage + "! MULTI_ENEMY_DAMAGE");
            this.detonate();
            return;
        }

        if (this.m == null && !this.selfDamage) {
            this.m = AbstractDungeon.getRandomMonster();
            if (this.m == null) {
                this.isDone = true;
                return;
            }
        }

        if (this.selfDamage) {
            Util.log("Detonating for " + this.damage + "! SELF_DAMAGE");
            AbstractDungeon.player.damage(new DamageInfo(AbstractDungeon.player, this.damage, this.hpDamage ? DamageInfo.DamageType.HP_LOSS : DamageInfo.DamageType.NORMAL));
            this.addToTop(new VFXAction(new ExplosionSmallEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.1f));
            if (this.superExploding) {
                this.addToTop(new VFXAction(new ExplosionSmallEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.1f));
            }
        } else {
            Util.log("Detonating for " + this.damage + "! SINGLE_ENEMY_DAMAGE");
            this.m.damage(new DamageInfo(AbstractDungeon.player, this.damage, DamageInfo.DamageType.THORNS));
            this.addToTop(new VFXAction(new ExplosionSmallEffect(this.m.hb.cX, this.m.hb.cY), 0.1f));
            if (this.superExploding) {
                this.addToTop(new VFXAction(new ExplosionSmallEffect(this.m.hb.cX, this.m.hb.cY), 0.1f));
            }
            this.m = null;
        }
        this.detonate();
    }

    private void detonate() {
        this.detonations--;
        DuelistCard.handleOnDetonateForAllAbstracts();
    }
}
