package duelistmod.helpers;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;

public class PowHelper {

    @SuppressWarnings("unchecked")
    public static <T> T getPower(String powerId) {
        if (AbstractDungeon.player != null) {
            if (AbstractDungeon.player.hasPower(powerId)) {
                try {
                    return (T)AbstractDungeon.player.getPower(powerId);
                } catch (Exception ex) {
                    return null;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getPowerFromEnemyDuelist(String powerId) {
        if (AbstractEnemyDuelist.enemyDuelist != null) {
            if (AbstractEnemyDuelist.enemyDuelist.hasPower(powerId)) {
                try {
                    return (T)AbstractEnemyDuelist.enemyDuelist.getPower(powerId);
                } catch (Exception ex) {
                    return null;
                }
            }
        }
        return null;
    }

}
