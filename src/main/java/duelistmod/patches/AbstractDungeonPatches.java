package duelistmod.patches;

import com.badlogic.gdx.graphics.g2d.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.*;
import duelistmod.DuelistMod;
import duelistmod.characters.TheDuelist;
import duelistmod.events.*;
import javassist.*;

import java.lang.reflect.*;
import java.util.*;

import static duelistmod.ui.DuelistCardSelectScreen.Enum.DUELIST_SELECTION_SCREEN;
import static duelistmod.ui.DuelistCardViewScreen.Enum.DUELIST_CARD_VIEW_SCREEN;
import static duelistmod.ui.DuelistMasterCardViewScreen.Enum.DUELIST_MASTER_CARD_VIEW;

public class AbstractDungeonPatches
{
    @SpirePatch(clz = AbstractDungeon.class, method = "initializeCardPools")
    public static class EventRemoval {
        @SpirePrefixPatch
        public static void Prefix(AbstractDungeon dungeon_instance)
        {
            if (!(AbstractDungeon.player instanceof TheDuelist) || !DuelistMod.allowDuelistEvents)
            {
                AbstractDungeon.eventList.remove(AknamkanonTomb.ID);
                AbstractDungeon.eventList.remove(MillenniumItems.ID);
                AbstractDungeon.eventList.remove(EgyptVillage.ID);
                AbstractDungeon.eventList.remove(TombNameless.ID);
                AbstractDungeon.eventList.remove(TombNamelessPuzzle.ID);
                AbstractDungeon.eventList.remove(BattleCity.ID);
                AbstractDungeon.eventList.remove(CardTrader.ID);
                AbstractDungeon.eventList.remove(RelicDuplicator.ID);
                AbstractDungeon.eventList.remove(VisitFromAnubis.ID);
            }
        }
    }

    @SpirePatch(clz = AbstractDungeon.class, method = "closeCurrentScreen")
    public static class CloseCurrentScreen {
        public static void Prefix() {
            if (AbstractDungeon.screen == DUELIST_SELECTION_SCREEN
                    || AbstractDungeon.screen == DUELIST_CARD_VIEW_SCREEN
                    || AbstractDungeon.screen == DUELIST_MASTER_CARD_VIEW) {
                try {
                    Method overlayReset = AbstractDungeon.class.getDeclaredMethod("genericScreenOverlayReset");
                    overlayReset.setAccessible(true);
                    overlayReset.invoke(AbstractDungeon.class);
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                AbstractDungeon.overlayMenu.hideBlackScreen();
            }
        }
    }

    @SpirePatch(clz = AbstractDungeon.class, method = "update")
    public static class Update
    {
        @SpireInsertPatch(locator= Locator.class)
        public static void Insert(AbstractDungeon __instance)
        {
            if (AbstractDungeon.screen == DUELIST_SELECTION_SCREEN) {
                DuelistMod.duelistCardSelectScreen.update();
            } else if (AbstractDungeon.screen == DUELIST_CARD_VIEW_SCREEN) {
                DuelistMod.duelistCardViewScreen.update();
            } else if (AbstractDungeon.screen == DUELIST_MASTER_CARD_VIEW) {
                DuelistMod.duelistMasterCardViewScreen.update();
            }
        }

        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception
            {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher("com.megacrit.cardcrawl.dungeons.AbstractDungeon", "screen");
                return LineFinder.findInOrder(ctBehavior, new ArrayList<>(), finalMatcher);
            }
        }
    }

    @SpirePatch(clz = AbstractDungeon.class, method = "render")
    public static class Render
    {
        @SpireInsertPatch(locator= Locator.class)
        public static void Insert(AbstractDungeon __instance, SpriteBatch sb)
        {
            if (AbstractDungeon.screen == DUELIST_SELECTION_SCREEN) {
                DuelistMod.duelistCardSelectScreen.render(sb);
            } else if (AbstractDungeon.screen == DUELIST_CARD_VIEW_SCREEN) {
                DuelistMod.duelistCardViewScreen.render(sb);
            } else if (AbstractDungeon.screen == DUELIST_MASTER_CARD_VIEW) {
                DuelistMod.duelistMasterCardViewScreen.render(sb);
            }
        }

        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception
            {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher("com.megacrit.cardcrawl.dungeons.AbstractDungeon", "screen");
                return LineFinder.findInOrder(ctBehavior, new ArrayList<>(), finalMatcher);
            }
        }
    }

    @SpirePatch(clz = AbstractDungeon.class, method = "openPreviousScreen")
    public static class OpenPreviousScreen
    {
        public static void Postfix(AbstractDungeon.CurrentScreen s)
        {
            if (s == DUELIST_SELECTION_SCREEN) {
                DuelistMod.duelistCardSelectScreen.reopen();
            } else if (AbstractDungeon.screen == DUELIST_CARD_VIEW_SCREEN) {
                DuelistMod.duelistCardViewScreen.reopen();
            }
        }
    }
}
