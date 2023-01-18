package duelistmod.helpers;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

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

}
