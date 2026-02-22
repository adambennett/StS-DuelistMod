package duelistmod.patches.crossmod;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import extendedui.EUIGameUtils;
import extendedui.ui.controls.EUIButton;
import extendedui.ui.controls.EUIButtonList;
import extendedui.ui.screens.CustomCardLibraryScreen;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.*;

import static duelistmod.variables.Colors.*;

public class CompendiumLabelsEUIPatch {

    @SpirePatch(clz = EUIGameUtils.class, method = "getColorName", optional = true, requiredModId = "extendedui")
    public static class EUIGameUtilsColorNamePatch {

        public static String Postfix(String __result, AbstractCard.CardColor color) {
            if (color == AbstractCardEnum.DUELIST_MONSTERS) return "Monsters";
            if (color == AbstractCardEnum.DUELIST_SPELLS)   return "Spells";
            if (color == AbstractCardEnum.DUELIST_TRAPS)    return "Traps";
            if (color == AbstractCardEnum.DUELIST)          return "Tokens";
            if (color == AbstractCardEnum.DUELIST_SPECIAL)  return "Nameless Tomb";
            return __result;
        }
    }

    @SpirePatch(clz = CustomCardLibraryScreen.class, method = "makeColorButton", optional = true, requiredModId = "extendedui")
    public static class EUICustomCompendiumHideCRCPatch {

        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(CustomCardLibraryScreen __instance, AbstractCard.CardColor co) {
            if (co == AbstractCardEnum.DUELIST_CRC) {
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = CustomCardLibraryScreen.class, method = "initialize", optional = true, requiredModId = "extendedui")
    public static class EUICustomCompendiumReorderPatch {

        private static final List<String> DUELIST_ORDER = new ArrayList<>();

        static {
            DUELIST_ORDER.add("Monsters");
            DUELIST_ORDER.add("Spells");
            DUELIST_ORDER.add("Traps");
            DUELIST_ORDER.add("Tokens");
            DUELIST_ORDER.add("Nameless Tomb");
        }

        @SpirePostfixPatch
        public static void Postfix(CustomCardLibraryScreen __instance, CardLibraryScreen screen) {
            try {
                EUIButtonList colorButtons = ReflectionHacks.getPrivate(__instance, CustomCardLibraryScreen.class, "colorButtons");
                if (colorButtons == null || colorButtons.buttons == null || colorButtons.buttons.isEmpty()) {
                    return;
                }

                ArrayList<EUIButton> buttons = colorButtons.buttons;
                Map<String, EUIButton> byLabel = new HashMap<>();
                for (EUIButton b : buttons) {
                    if (b != null && b.label != null && b.label.text != null) {
                        byLabel.put(b.label.text, b);
                    }
                }

                List<EUIButton> duelistButtons = new ArrayList<>();
                for (String name : DUELIST_ORDER) {
                    EUIButton b = byLabel.get(name);
                    if (b != null) {
                        duelistButtons.add(b);
                    }
                }
                if (duelistButtons.isEmpty()) {
                    return;
                }

                buttons.removeAll(duelistButtons);

                Integer insertIndex = tryFindInsertIndexAfterVanilla(buttons);
                if (insertIndex == null) {
                    buttons.addAll(duelistButtons); // fallback: end of list
                } else {
                    buttons.addAll(Math.min(insertIndex, buttons.size()), duelistButtons);
                }
                colorButtons.setTopButtonIndex(0);
            } catch (Throwable t) {
                Util.log("EUICustomCompendiumReorderPatch failed: " + ExceptionUtils.getStackTrace(t));
            }
        }

        private static String getLabelText(EUIButton b) {
            if (b == null || b.label == null) return null;
            return b.label.text;
        }

        private static Integer tryFindInsertIndexAfterVanilla(List<EUIButton> buttons) {
            Set<String> vanillaEnglish = new HashSet<>(Arrays.asList(
                    "Colorless", "Curses", "Red", "Green", "Blue", "Purple"
            ));
            int lastVanillaIndex = -1;
            for (int i = 0; i < buttons.size(); i++) {
                String txt = getLabelText(buttons.get(i));
                if (txt == null) continue;

                if (vanillaEnglish.contains(txt)) {
                    lastVanillaIndex = i;
                }
            }
            if (lastVanillaIndex >= 0) {
                return lastVanillaIndex + 1;
            }
            return null;
        }
    }

    @SpirePatch(clz = EUIGameUtils.class, method = "getColorColor", optional = true, requiredModId = "extendedui")
    public static class EUIGameUtilsColorColorPatch {

        public static Color Postfix(Color __result, AbstractCard.CardColor color) {
            if (color == AbstractCardEnum.DUELIST_TRAPS) {
                return TAB_PURPLE;
            }
            if (color == AbstractCardEnum.DUELIST_SPECIAL) {
                return TAB_RED;
            }
            return __result;
        }
    }

}
