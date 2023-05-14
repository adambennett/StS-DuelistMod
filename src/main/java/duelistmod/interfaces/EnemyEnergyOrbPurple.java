package duelistmod.interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbPurple;
import duelistmod.events.BattleCity;

public class EnemyEnergyOrbPurple extends EnergyOrbPurple {

    private final BattleCity battleCity;

    public EnemyEnergyOrbPurple(BattleCity event) {
        this.battleCity = event;
    }

    private boolean isHiddenPreBattle() {
        return this.battleCity != null && (!this.battleCity.isInCombat());
    }

    @Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {
        if (this.isHiddenPreBattle()) return;

        super.renderOrb(sb, enabled, current_x, current_y);
    }
}
