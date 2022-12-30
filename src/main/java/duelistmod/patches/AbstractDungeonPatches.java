package duelistmod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.SaveHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.FadeWipeParticle;
import duelistmod.DuelistMod;
import duelistmod.characters.TheDuelist;
import duelistmod.events.*;
import duelistmod.ui.DuelistCardSelectScreen;
import duelistmod.ui.DuelistMasterCardViewScreen;
import duelistmod.variables.VictoryDeathScreens;
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

    @SpirePatch(clz = AbstractDungeon.class, method = "generateSeeds")
    public static class GenerateSeedsCheck {
        public static void Postfix() {
            DuelistMod.receiveStartRun();
        }
    }

    @SpirePatch(clz = SaveHelper.class, method = "saveIfAppropriate")
    public static class SaveHelperPatch {
        public static void Postfix() {
            DuelistMod.onSave();
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
            } else if (AbstractDungeon.screen == VictoryDeathScreens.DUELIST_VICTORY) {
                DuelistMod.victoryScreen.update();
            } else if (AbstractDungeon.screen == VictoryDeathScreens.DUELIST_DEATH) {
                DuelistMod.deathScreen.update();
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
            } else if (AbstractDungeon.screen == VictoryDeathScreens.DUELIST_VICTORY) {
                DuelistMod.victoryScreen.render(sb);
            } else if (AbstractDungeon.screen == VictoryDeathScreens.DUELIST_DEATH) {
                DuelistMod.deathScreen.render(sb);
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
            } else if (AbstractDungeon.screen == VictoryDeathScreens.DUELIST_VICTORY) {
                DuelistMod.victoryScreen.reopen();
            } else if (AbstractDungeon.screen == VictoryDeathScreens.DUELIST_DEATH) {
                DuelistMod.deathScreen.reopen();
            }
        }
    }

    @SpirePatch(clz = TopPanel.class, method = "updateDeckViewButtonLogic")
    public static class UpdateDeckView {
        public static SpireReturn<Void> Prefix(TopPanel __instance) {
            if (!AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST)) {
                return SpireReturn.Continue();
            }
            if (AbstractDungeon.screen != VictoryDeathScreens.DUELIST_VICTORY && AbstractDungeon.screen != VictoryDeathScreens.DUELIST_DEATH && AbstractDungeon.screen != DUELIST_SELECTION_SCREEN && AbstractDungeon.screen != DUELIST_MASTER_CARD_VIEW) {
                return SpireReturn.Continue();
            }

            if (AbstractDungeon.screen == DUELIST_MASTER_CARD_VIEW) {
                float timer = ReflectionHacks.getPrivate(__instance, TopPanel.class, "rotateTimer");
                ReflectionHacks.setPrivate(__instance, TopPanel.class, "rotateTimer", timer + Gdx.graphics.getDeltaTime() * 4.0f);
                float angle = ReflectionHacks.getPrivate(__instance, TopPanel.class, "deckAngle");
                timer = ReflectionHacks.getPrivate(__instance, TopPanel.class, "rotateTimer");
                ReflectionHacks.setPrivate(__instance, TopPanel.class, "deckAngle", MathHelper.angleLerpSnap(angle, MathUtils.sin(timer) * 15.0f));
            }
            else if (__instance.deckHb.hovered) {
                float angle = ReflectionHacks.getPrivate(__instance, TopPanel.class, "deckAngle");
                ReflectionHacks.setPrivate(__instance, TopPanel.class, "deckAngle", MathHelper.angleLerpSnap(angle, 15.0f));
            }
            else {
                float angle = ReflectionHacks.getPrivate(__instance, TopPanel.class, "deckAngle");
                ReflectionHacks.setPrivate(__instance, TopPanel.class, "deckAngle", MathHelper.angleLerpSnap(angle, 0.0f));
            }
            if (AbstractDungeon.screen == DUELIST_MASTER_CARD_VIEW || AbstractDungeon.screen ==  VictoryDeathScreens.DUELIST_DEATH || AbstractDungeon.screen == VictoryDeathScreens.DUELIST_VICTORY) {
                ReflectionHacks.setPrivate(__instance, TopPanel.class, "deckButtonDisabled", false);
                __instance.deckHb.update();
            }
            else {
                ReflectionHacks.setPrivate(__instance, TopPanel.class, "deckButtonDisabled", true);
                __instance.deckHb.hovered = false;
            }
            final boolean clickedDeckButton = __instance.deckHb.hovered && InputHelper.justClickedLeft;
            if ((clickedDeckButton || InputActionSet.masterDeck.isJustPressed() || CInputActionSet.pageLeftViewDeck.isJustPressed()) && !CardCrawlGame.isPopupOpen) {
                if (AbstractDungeon.screen == DUELIST_MASTER_CARD_VIEW) {
                    AbstractDungeon.screenSwap = false;
                    if (AbstractDungeon.previousScreen == DUELIST_MASTER_CARD_VIEW) {
                        AbstractDungeon.previousScreen = null;
                    }
                    AbstractDungeon.closeCurrentScreen();
                    CardCrawlGame.sound.play("DECK_CLOSE", 0.05f);
                }
                else if (AbstractDungeon.screen == VictoryDeathScreens.DUELIST_DEATH ) {
                    AbstractDungeon.previousScreen = VictoryDeathScreens.DUELIST_DEATH ;
                    DuelistMod.deathScreen.hide();
                    AbstractDungeon.deckViewScreen.open();
                }
                else if (AbstractDungeon.screen == DUELIST_SELECTION_SCREEN) {
                    AbstractDungeon.previousScreen = DUELIST_SELECTION_SCREEN;
                    DuelistMod.duelistCardSelectScreen.hide();
                    AbstractDungeon.deckViewScreen.open();
                }
                InputHelper.justClickedLeft = false;
            }
            return SpireReturn.Return();
        }
    }

    @SpirePatch(clz = TopPanel.class, method = "updateSettingsButtonLogic")
    public static class UpdateSettingsButton {
        public static SpireReturn<Void> Prefix(TopPanel __instance) {
            if (!AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST)) {
                return SpireReturn.Continue();
            }
            ReflectionHacks.setPrivate(__instance, TopPanel.class, "settingsButtonDisabled", false);
            if (AbstractDungeon.screen != VictoryDeathScreens.DUELIST_VICTORY && AbstractDungeon.screen != VictoryDeathScreens.DUELIST_DEATH && AbstractDungeon.screen != DUELIST_SELECTION_SCREEN && AbstractDungeon.screen != DUELIST_MASTER_CARD_VIEW) {
                return SpireReturn.Continue();
            }

            if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.FTUE) {
                __instance.settingsHb.update();
            }

            float curSettingsAngle = ReflectionHacks.getPrivate(__instance, TopPanel.class, "settingsAngle");
            if (__instance.settingsHb.hovered) {
                ReflectionHacks.setPrivate(__instance, TopPanel.class, "settingsAngle", MathHelper.angleLerpSnap(curSettingsAngle, -90.0f));
            }
            else {
                ReflectionHacks.setPrivate(__instance, TopPanel.class, "settingsAngle", MathHelper.angleLerpSnap(curSettingsAngle, 0.0f));
            }

            if (AbstractDungeon.gridSelectScreen.isJustForConfirming) {
                AbstractDungeon.dynamicBanner.hide();
            }
            if (AbstractDungeon.screen != DUELIST_SELECTION_SCREEN && AbstractDungeon.screen != DUELIST_MASTER_CARD_VIEW) {
                if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.MAP) {
                    InputHelper.pressedEscape = false;
                }
            }

            CInputActionSet.settings.unpress();
            if ((__instance.settingsHb.hovered && InputHelper.justClickedLeft) || InputHelper.pressedEscape || CInputActionSet.settings.isJustPressed()) {
                if (AbstractDungeon.screen == VictoryDeathScreens.DUELIST_VICTORY) {
                    AbstractDungeon.previousScreen = VictoryDeathScreens.DUELIST_VICTORY;
                    AbstractDungeon.settingsScreen.open();
                    DuelistMod.victoryScreen.hide();
                }

                if (AbstractDungeon.screen == VictoryDeathScreens.DUELIST_DEATH) {
                    AbstractDungeon.previousScreen = VictoryDeathScreens.DUELIST_DEATH;
                    AbstractDungeon.settingsScreen.open();
                    DuelistMod.deathScreen.hide();
                }

                if (AbstractDungeon.screen == DUELIST_SELECTION_SCREEN) {
                    if (!InputHelper.pressedEscape) {
                        AbstractDungeon.previousScreen = DuelistCardSelectScreen.Enum.DUELIST_SELECTION_SCREEN;
                        AbstractDungeon.gridSelectScreen.hide();
                        AbstractDungeon.settingsScreen.open();
                    }
                }

                if (AbstractDungeon.screen == DUELIST_MASTER_CARD_VIEW) {
                    if (!InputHelper.pressedEscape) {
                        if (AbstractDungeon.previousScreen == null) {
                            AbstractDungeon.previousScreen = DuelistMasterCardViewScreen.Enum.DUELIST_MASTER_CARD_VIEW;;
                        }
                        else {
                            AbstractDungeon.screenSwap = true;
                        }
                        AbstractDungeon.settingsScreen.open();
                    }
                    else {
                        AbstractDungeon.closeCurrentScreen();
                    }
                }
                InputHelper.justClickedLeft = false;
            }
            return SpireReturn.Return();
        }
    }

    @SpirePatch(clz = TopPanel.class, method = "updateMapButtonLogic")
    public static class UpdateMapView {
        public static SpireReturn<Void> Prefix(TopPanel __instance) {
            if (!AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST)) {
                return SpireReturn.Continue();
            }
            if (AbstractDungeon.screen != VictoryDeathScreens.DUELIST_VICTORY && AbstractDungeon.screen != VictoryDeathScreens.DUELIST_DEATH && AbstractDungeon.screen != DUELIST_SELECTION_SCREEN && AbstractDungeon.screen != DUELIST_MASTER_CARD_VIEW) {
                return SpireReturn.Continue();
            }
            float angle = ReflectionHacks.getPrivate(__instance, TopPanel.class, "mapAngle");
            if (__instance.mapHb.hovered) {
                ReflectionHacks.setPrivate(__instance, TopPanel.class, "mapAngle", MathHelper.angleLerpSnap(angle, 10.0f));
            }
            else {
                ReflectionHacks.setPrivate(__instance, TopPanel.class, "mapAngle", MathHelper.angleLerpSnap(angle, -5.0f));
            }
            if (AbstractDungeon.screen == DUELIST_SELECTION_SCREEN || AbstractDungeon.screen == VictoryDeathScreens.DUELIST_DEATH || AbstractDungeon.screen == VictoryDeathScreens.DUELIST_VICTORY || AbstractDungeon.screen == DUELIST_MASTER_CARD_VIEW) {
                ReflectionHacks.setPrivate(__instance, TopPanel.class, "mapButtonDisabled", false);
                __instance.mapHb.update();
            }
            else {
                ReflectionHacks.setPrivate(__instance, TopPanel.class, "mapButtonDisabled", true);
                __instance.mapHb.hovered = false;
            }
            final boolean clickedMapButton = __instance.mapHb.hovered && InputHelper.justClickedLeft;
            if (!CardCrawlGame.cardPopup.isOpen && (clickedMapButton || InputActionSet.map.isJustPressed() || CInputActionSet.map.isJustPressed())) {
                for (final AbstractGameEffect e : AbstractDungeon.topLevelEffects) {
                    if (e instanceof FadeWipeParticle) {
                        return SpireReturn.Return();
                    }
                }
                if (!AbstractDungeon.isScreenUp) {
                    AbstractDungeon.dungeonMapScreen.open(false);
                }
                if (AbstractDungeon.screen == VictoryDeathScreens.DUELIST_DEATH) {
                    AbstractDungeon.previousScreen = VictoryDeathScreens.DUELIST_DEATH;
                    DuelistMod.deathScreen.hide();
                    AbstractDungeon.dungeonMapScreen.open(false);
                }
                else if (AbstractDungeon.screen == DUELIST_MASTER_CARD_VIEW) {
                    if (AbstractDungeon.dungeonMapScreen.dismissable) {
                        if (AbstractDungeon.previousScreen != null) {
                            AbstractDungeon.screenSwap = true;
                        }
                        AbstractDungeon.closeCurrentScreen();
                        AbstractDungeon.dungeonMapScreen.open(false);
                    }
                    else {
                        AbstractDungeon.closeCurrentScreen();
                    }
                }
                else if (AbstractDungeon.screen == DUELIST_SELECTION_SCREEN) {
                    AbstractDungeon.previousScreen = DUELIST_SELECTION_SCREEN;
                    DuelistMod.duelistCardSelectScreen.hide();
                    AbstractDungeon.dungeonMapScreen.open(false);
                }
                InputHelper.justClickedLeft = false;
            }
            return SpireReturn.Return();
        }
    }
}
