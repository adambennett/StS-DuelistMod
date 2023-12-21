package duelistmod.actions.unique;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.combat.FlameBarrierEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class FiresOfDoomsdayAction extends AttackDamageRandomEnemyAction {

    public FiresOfDoomsdayAction(AbstractCard card) {
        super(card);
    }

    public void update() {
        if (!Settings.FAST_MODE) {
            this.addToTop(new WaitAction(0.1F));
        }

        super.update();
        if (this.target != null) {
            this.addToTop(new VFXAction(new FlameBarrierEffect(this.target.drawX, this.target.drawY)));
            this.addToTop(new VFXAction(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect)));
            this.addToTop(new SFXAction("ATTACK_FLAME_BARRIER", 0.1F));
        }

    }
}
