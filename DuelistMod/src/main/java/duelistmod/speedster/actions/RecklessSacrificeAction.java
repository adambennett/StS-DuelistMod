package duelistmod.speedster.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.*;

import duelistmod.speedster.SpeedsterUtil.UC;

public class RecklessSacrificeAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private int energyOnUse;
    private int damage;
    private int hploss;

    public RecklessSacrificeAction(AbstractMonster m, int damage, int hploss, boolean freeToPlayOnce, int energyOnUse) {
        this.freeToPlayOnce = false;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.target = m;
        this.damage = damage;
        this.hploss = hploss;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1)
            effect = this.energyOnUse;
        if (UC.p().hasRelic(ChemicalX.ID)) {
            effect += 2;
            UC.p().getRelic(ChemicalX.ID).flash();
        }
        int dmg, hpl;
        if (effect > 0) {
            dmg = damage;
            hpl = hploss;
            for (int i = 0; i < effect; i++) {
                UC.doVfx(new InflameEffect(UC.p()));
                for (int j = 0; j < i; j++) {
                    float xOff = ((target.hb_w/2.0f) *MathUtils.random());
                    if(MathUtils.randomBoolean()) {
                        xOff = -xOff;
                    }
                    float yOff = ((target.hb_h/2.0f) *MathUtils.random());
                    if(MathUtils.randomBoolean()) {
                        yOff = -yOff;
                    }
                    UC.doVfx(new ExplosionSmallEffect(target.drawX + xOff, target.drawY+yOff));
                }
                UC.atb(new LoseHPIfMonsterNotDeadAction(UC.p(), UC.p(), hpl, (AbstractMonster)target));
                UC.doDmg(target, dmg, AttackEffect.FIRE);

                hpl*=2;
                dmg*=2;
            }
            if (!this.freeToPlayOnce)
                UC.p().energy.use(EnergyPanel.totalCount);
        }
        this.isDone = true;
    }
}
