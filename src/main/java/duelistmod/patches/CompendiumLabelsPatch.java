package duelistmod.patches;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.patches.com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.ColorTabBarFix.ModColorTab;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar;
import com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.CurrentTab;
import duelistmod.helpers.Util;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static duelistmod.patches.AbstractCardEnum.DUELIST_CRC;

public class CompendiumLabelsPatch {

    private static boolean initialized = false;

    private static final Map<String, String> NAME_BY_COLOR_ENUM_NAME = new HashMap<String, String>() {{
        put("duelist_monsters", "Monsters");
        put("duelist_spells", "Spells");
        put("duelist_traps", "Traps");
        put("duelist_special", "Nameless Tomb");
        put("duelist", "Tokens");
    }};

    @SpirePatch(
            clz = ColorTabBar.class,
            method = "update",
            paramtypez = { float.class }
    )
    public static class EnsureInit {
        @SpirePostfixPatch
        public static void Postfix(ColorTabBar __instance, float y) {
            if (!initialized) {
                tryInitialize();
            }
        }
    }

    @SpirePatch(
            clz = ColorTabBar.class,
            method = "render",
            paramtypez = { SpriteBatch.class, float.class }
    )
    public static class OverdrawLabels {
        @SpirePostfixPatch
        public static void Postfix(ColorTabBar __instance, SpriteBatch sb, float y) {
            if (!initialized) {
                tryInitialize();
                if (!initialized) {
                    return;
                }
            }

            List<ModColorTab> modTabs = getModTabs();
            if (modTabs == null) {
                return;
            }

            Object modEnum = getModCurrentTabEnum();
            Integer modTabIndex = getModTabIndex();
            if (modEnum == null || modTabIndex == null) return;

            Object curTab = ReflectionHacks.getPrivate(__instance, ColorTabBar.class, "curTab");

            final float SPACING = 64.0f;

            for (int i = 0; i < modTabs.size(); i++) {
                ModColorTab modTab = modTabs.get(i);
                AbstractCard.CardColor cardColor = modTab.color;
                if (cardColor == null) continue;

                String enumName = cardColor.name().toLowerCase();
                if (!NAME_BY_COLOR_ENUM_NAME.containsKey(enumName)) {
                    continue;
                }

                boolean isSelected = (curTab == modEnum) && (modTabIndex == i);

                // Redraw the tab background to cover the old label
                Color bg = BaseMod.getTrailVfxColor(cardColor).cpy();
                if (!isSelected) {
                    bg = bg.lerp(Color.GRAY, 0.5f);
                }
                sb.setColor(bg);

                sb.draw(
                        ImageMaster.COLOR_TAB_BAR,
                        40.0f * Settings.scale,
                        y - SPACING * (i + 1) * Settings.scale,
                        0, 0,
                        235.0f, 102.0f,
                        Settings.scale, Settings.scale,
                        0.0f,
                        0, 0,
                        1334, 102,
                        false, false
                );

                // Draw our label
                Color textColor = isSelected ? Settings.GOLD_COLOR : Color.GRAY;
                String tabName = NAME_BY_COLOR_ENUM_NAME.getOrDefault(enumName, enumName);
                if (tabName == null) continue;

                FontHelper.renderFontCentered(
                        sb,
                        FontHelper.buttonLabelFont,
                        tabName,
                        157.0f * Settings.scale,
                        y - (SPACING * (i + 1) * Settings.scale) + 50.0f * Settings.scale,
                        textColor,
                        0.85f
                );
            }

            // Restore color
            sb.setColor(Color.WHITE);

        }
    }

    private static void tryInitialize() {
        List<ModColorTab> modTabs = getModTabs();
        if (modTabs == null) return;

        modTabs.removeIf(tab -> tab.color == DUELIST_CRC);
        initialized = true;
    }

    private static List<ModColorTab> getModTabs() {
        try {
            Class<?> fieldsClz = Class.forName("basemod.patches.com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.ColorTabBarFix$Fields");
            Field f = fieldsClz.getDeclaredField("modTabs");
            f.setAccessible(true);
            return (List<ModColorTab>) f.get(null);
        } catch (Exception ex) {
            Util.log("Exception while getModTabs() " + ExceptionUtils.getStackTrace(ex));
        }
        return null;
    }

    private static Integer getModTabIndex() {
        try {
            Class<?> fieldsClz = Class.forName("basemod.patches.com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.ColorTabBarFix$Fields");
            Field f = fieldsClz.getDeclaredField("modTabIndex");
            f.setAccessible(true);
            return (int) f.get(null);
        } catch (Exception ex) {
            Util.log("Exception while getModTabIndex() " + ExceptionUtils.getStackTrace(ex));
        }
        return null;
    }

    private static CurrentTab getModCurrentTabEnum() {
        try {
            Class<?> enumsClz = Class.forName("basemod.patches.com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.ColorTabBarFix$Enums");
            Field f = enumsClz.getDeclaredField("MOD");
            f.setAccessible(true);
            return (CurrentTab) f.get(null);
        } catch (Exception ex) {
            Util.log("Exception while getModCurrentTabEnum() " + ExceptionUtils.getStackTrace(ex));
        }
        return null;
    }
}
