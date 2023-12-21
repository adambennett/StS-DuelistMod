package duelistmod.abstracts.enemyDuelist;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EnemyEnergyManager extends EnergyManager {

    public EnemyEnergyManager(final int e) {
        super(e);
    }

    public void prep() {
        this.energy = this.energyMaster;
        EnemyEnergyPanel.totalCount = 0;
    }

    public void recharge() {
        if (AbstractEnemyDuelist.enemyDuelist != null) {
            if (AbstractEnemyDuelist.enemyDuelist.hasRelic("Art of War")) {
                AbstractEnemyDuelist.enemyDuelist.getRelic("Art of War").onTrigger();
            }
            if (AbstractEnemyDuelist.enemyDuelist.hasRelic("Ice Cream")) {
                if (EnemyEnergyPanel.totalCount > 0) {
                    AbstractEnemyDuelist.enemyDuelist.getRelic("Ice Cream").flash();
                    AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractEnemyDuelist.enemyDuelist, AbstractEnemyDuelist.enemyDuelist.getRelic("Ice Cream")));
                }
                EnemyEnergyPanel.addEnergy(this.energy);
            }
            else if (AbstractEnemyDuelist.enemyDuelist.hasPower("Conserve")) {
                if (EnemyEnergyPanel.totalCount > 0) {
                    AbstractDungeon.actionManager.addToTop(new ReducePowerAction(AbstractEnemyDuelist.enemyDuelist, AbstractEnemyDuelist.enemyDuelist, "Conserve", 1));
                }
                EnemyEnergyPanel.addEnergy(this.energy);
            }
            else {
                EnemyEnergyPanel.setEnergy(this.energy);
            }
        }
    }

    public void use(final int e) {
        EnemyEnergyPanel.useEnergy(e);
    }
}
