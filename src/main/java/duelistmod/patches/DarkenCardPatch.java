package duelistmod.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import duelistmod.abstracts.enemyDuelist.AbstractEnemyDuelist;
import duelistmod.abstracts.enemyDuelist.EnemyDuelistCard;

@SpirePatch(clz = AbstractCard.class, method = "renderTint")
public class DarkenCardPatch {
    private static Color lightenColor;
    private static Color darkenColor;

    @SpirePrefixPatch
    public static SpireReturn<Void> Prefix(final AbstractCard instance, final SpriteBatch sb) {
        EnemyDuelistCard ab = AbstractEnemyDuelist.fromCardOrNull(instance);
        if (ab != null) {
            if (!Settings.hideCards) {
                Color tintColor;
                if (ab.bossDarkened && !ab.hov2) {
                    tintColor = DarkenCardPatch.darkenColor;
                }
                else {
                    tintColor = DarkenCardPatch.lightenColor;
                }
                final TextureAtlas.AtlasRegion cardBgImg = instance.getCardBgAtlas();
                if (cardBgImg != null) {
                    ab.renderHelperB(sb, tintColor, cardBgImg, instance.current_x, instance.current_y);
                }
                else {
                    ab.renderHelperB(sb, tintColor, instance.getCardBg(), instance.current_x - 256.0f, instance.current_y - 256.0f);
                }
            }
            return SpireReturn.Return();
        }
        return SpireReturn.Continue();
    }

    static {
        DarkenCardPatch.lightenColor = new Color(109.65f, 94.35f, 165.75f, 0.0f);
        DarkenCardPatch.darkenColor = new Color(0.0f, 0.0f, 0.0f, 0.75f);
    }
}
