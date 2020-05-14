package duelistmod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import duelistmod.DuelistMod;
import javassist.CtBehavior;

public class SpeedTimePatches {
    @SpirePatch(clz = AbstractRoom.class, method = "render")
    public static class BehindMonsterRenderCall {
        @SpireInsertPatch(locator = Locator.class)
        public static void patch(AbstractRoom __instance, SpriteBatch sb) {
            if(DuelistMod.speedScreen != null) {
            	DuelistMod.speedScreen.renderController(sb, duelistmod.speedster.mechanics.AbstractSpeedTime.Location.BEHIND_MONSTER);
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractRoom.class, "monsters");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }

    @SpirePatch(clz = AbstractDungeon.class, method = "render")
    public static class BlackscreenRenderCall {
        @SpireInsertPatch(locator = Locator.class)
        public static void patch(AbstractDungeon __instance, SpriteBatch sb) {
            if(DuelistMod.speedScreen != null) {
            	DuelistMod.speedScreen.renderController(sb, duelistmod.speedster.mechanics.AbstractSpeedTime.Location.COMPLETE);
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(OverlayMenu.class, "renderBlackScreen");
                return LineFinder.findInOrder(ctBehavior, finalMatcher);
            }
        }
    }

    @SpirePatch(clz = AbstractDungeon.class, method = "update")
    public static class UpdateCaller {
        @SpirePostfixPatch
        public static void patch(AbstractDungeon __instance) {
            if(DuelistMod.speedScreen != null) {
            	DuelistMod.speedScreen.update();
            }
        }
    }
}
