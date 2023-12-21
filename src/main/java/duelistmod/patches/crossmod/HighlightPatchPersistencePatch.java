package duelistmod.patches.crossmod;

import HighlightPath.HighlightPathInitializer;
import HighlightPath.patches.RightClickMapNodePatch;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;
import duelistmod.helpers.crossover.HighlightPathHelper;

public class HighlightPatchPersistencePatch {

    @SuppressWarnings("unused")
    @SpirePatch(clz = MapRoomNode.class, method = "update", optional = true, requiredModId = "HighlightPath")
    public static class ClickNode {
        public static void Postfix(final MapRoomNode __instance) {
            boolean isHighlighted = RightClickMapNodePatch.HighlightedField.isHighlighted.get(__instance);
            HighlightPathHelper.updateInfo(__instance, isHighlighted);
        }
    }

    @SuppressWarnings("unused")
    @SpirePatch(clz = MapRoomNode.class, method = "render", optional = true, requiredModId = "HighlightPath")
    public static class PersistOnRender {
        public static void Postfix(MapRoomNode __instance, SpriteBatch sb) {
            Boolean highlightPathModCheck = RightClickMapNodePatch.HighlightedField.isHighlighted.get(__instance);
            boolean highlightModCheck = highlightPathModCheck == null || !highlightPathModCheck;
            boolean duelistHighlightCheck = HighlightPathHelper.isHighlighted(__instance) || HighlightPathHelper.isHighlighted(__instance.toString());
            if ((highlightModCheck) && (duelistHighlightCheck)) {
                float offsetX = ReflectionHacks.getPrivateStatic(MapRoomNode.class, "OFFSET_X");
                float offsetY = ReflectionHacks.getPrivateStatic(MapRoomNode.class, "OFFSET_Y");
                float spacingX = ReflectionHacks.getPrivateStatic(MapRoomNode.class, "SPACING_X");
                float scale = ReflectionHacks.getPrivate(__instance, MapRoomNode.class, "scale");
                float angle = ReflectionHacks.getPrivate(__instance, MapRoomNode.class, "angle");
                sb.setColor(HighlightPathInitializer.highlightColorObject);
                sb.draw(ImageMaster.MAP_CIRCLE_5, (float)__instance.x * spacingX + offsetX - 96.0F + __instance.offsetX, (float)__instance.y * Settings.MAP_DST_Y + offsetY + DungeonMapScreen.offsetY - 96.0F + __instance.offsetY, 96.0F, 96.0F, 192.0F, 192.0F, (scale * 0.95F + 0.2F) * Settings.scale, (scale * 0.95F + 0.2F) * Settings.scale, angle, 0, 0, 192, 192, false, false);
            }
        }
    }
}
