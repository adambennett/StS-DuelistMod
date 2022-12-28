package duelistmod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.cutscenes.Cutscene;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.beyond.SpireHeart;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.EventRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.rooms.VictoryRoom;
import com.megacrit.cardcrawl.screens.options.ConfirmPopup;
import com.megacrit.cardcrawl.ui.buttons.UnlockConfirmButton;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import duelistmod.DuelistMod;
import duelistmod.enums.DeathType;
import duelistmod.helpers.Util;
import duelistmod.ui.gameOver.DuelistDeathScreen;
import duelistmod.ui.gameOver.DuelistVictoryScreen;
import duelistmod.variables.VictoryDeathScreens;
import com.badlogic.gdx.graphics.Color;
import javassist.CtBehavior;

import java.util.ArrayList;

public class VictoryDeathScreenPatches {

    @SpirePatch(clz = Cutscene.class, method = "openVictoryScreen")
    public static class VictoryScreenPatch {
        public static SpireReturn<Void> Prefix() {
            GameCursor.hidden = false;
            DuelistMod.victoryScreen = new DuelistVictoryScreen(null);
            return SpireReturn.Return();
        }
    }

    @SpirePatch(clz = SpireHeart.class, method = "buttonEffect")
    public static class SpireHeartPatch {
        public static SpireReturn<Void> Prefix(SpireHeart __instance) {
            try {
                Class<?> enumElement = Class.forName("com.megacrit.cardcrawl.events.beyond.SpireHeart.CUR_SCREEN");
                Object[] enumElements = enumElement.getEnumConstants();
                Object deathEnum = enumElements.length > 3 ? enumElements[3] : null;
                if (deathEnum != null) {
                    Object currentScreen = ReflectionHacks.getPrivate(__instance, SpireHeart.class, "screen");
                    if (currentScreen == deathEnum) {
                        AbstractDungeon.player.isDying = true;
                        __instance.hasFocus = false;
                        __instance.roomEventText.hide();
                        AbstractDungeon.player.isDead = true;
                        DuelistMod.deathScreen = new DuelistDeathScreen(null, DeathType.SPIRE_HEART);
                        return SpireReturn.Return();
                    } else {
                        return SpireReturn.Continue();
                    }
                }
            } catch (Exception ex) {
                Util.logError("Exception during SpireHeartPatch on method 'buttonEffect()'", ex);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = ConfirmPopup.class, method = "yesButtonEffect")
    public static class ConfirmPopupPatch {
        public static SpireReturn<Void> Prefix(ConfirmPopup __instance) {
            if (__instance.type == ConfirmPopup.ConfirmType.ABANDON_MID_RUN) {
                __instance.hide();
                AbstractDungeon.closeCurrentScreen();
                AbstractDungeon.player.isDead = true;
                DuelistMod.deathScreen = new DuelistDeathScreen(AbstractDungeon.getMonsters(), DeathType.ABANDON_MID_RUN);
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = AbstractRoom.class, method = "render")
    public static class RoomRenderPatch {
        public static SpireReturn<Void> Prefix(AbstractRoom __instance, SpriteBatch sb) {
            if (__instance instanceof EventRoom || __instance instanceof VictoryRoom) {
                if (__instance.event != null && (!(__instance.event instanceof AbstractImageEvent) || __instance.event.combatTime)) {
                    __instance.event.renderRoomEventPanel(sb);
                    if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.VICTORY && AbstractDungeon.screen != VictoryDeathScreens.DUELIST_VICTORY) {
                        AbstractDungeon.player.render(sb);
                    }
                }
            }
            else if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.BOSS_REWARD) {
                AbstractDungeon.player.render(sb);
            }
            if (!(AbstractDungeon.getCurrRoom() instanceof RestRoom)) {
                if (__instance.monsters != null && AbstractDungeon.screen != AbstractDungeon.CurrentScreen.DEATH && AbstractDungeon.screen != VictoryDeathScreens.DUELIST_DEATH) {
                    __instance.monsters.render(sb);
                }
                if (__instance.phase == AbstractRoom.RoomPhase.COMBAT) {
                    AbstractDungeon.player.renderPlayerBattleUi(sb);
                }
                for (final AbstractPotion i : __instance.potions) {
                    if (!i.isObtained) {
                        i.render(sb);
                    }
                }
            }
            for (final AbstractRelic r : __instance.relics) {
                r.render(sb);
            }
            __instance.renderTips(sb);
            return SpireReturn.Return();
        }
    }

    @SpirePatch(clz = UnlockConfirmButton.class, method = "update")
    public static class UpdatePatch {
        public static SpireReturn<Void> Prefix(UnlockConfirmButton __instance) {
            if (!AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST)) {
                return SpireReturn.Continue();
            }
            ReflectionHacks.privateMethod(UnlockConfirmButton.class, "animateIn").invoke(__instance);
            boolean done = ReflectionHacks.getPrivate(__instance, UnlockConfirmButton.class, "done");
            float animTimer = ReflectionHacks.getPrivate(__instance, UnlockConfirmButton.class, "animTimer");
            float targetA = ReflectionHacks.getPrivate(__instance, UnlockConfirmButton.class, "target_a");
            Color textColor = ReflectionHacks.getPrivate(__instance, UnlockConfirmButton.class, "textColor");
            Color btnColor = ReflectionHacks.getPrivate(__instance, UnlockConfirmButton.class, "btnColor");
            Color hoverColor = ReflectionHacks.getPrivate(__instance, UnlockConfirmButton.class, "hoverColor");
            if (!done && animTimer < 0.2f) {
                __instance.hb.update();
            }
            if (__instance.hb.hovered && !done) {
                hoverColor.a = 0.33f;
                ReflectionHacks.setPrivate(__instance, UnlockConfirmButton.class, "hoverColor", hoverColor);
            }
            else {
                hoverColor.a = MathHelper.fadeLerpSnap(hoverColor.a, 0.0f);
                ReflectionHacks.setPrivate(__instance, UnlockConfirmButton.class, "hoverColor", hoverColor);
            }
            if (__instance.hb.justHovered) {
                CardCrawlGame.sound.play("UI_HOVER");
            }
            if (__instance.hb.hovered && InputHelper.justClickedLeft) {
                __instance.hb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
            }
            if (__instance.hb.clicked || CInputActionSet.select.isJustPressed()) {
                CInputActionSet.select.unpress();
                __instance.hb.clicked = false;
                __instance.hb.hovered = false;
                if (AbstractDungeon.unlockScreen.unlock != null) {
                    UnlockTracker.hardUnlock(AbstractDungeon.unlockScreen.unlock.key);
                    CardCrawlGame.sound.stop("UNLOCK_SCREEN", AbstractDungeon.unlockScreen.id);
                }
                else if (AbstractDungeon.unlocks != null) {
                    for (final AbstractUnlock u : AbstractDungeon.unlocks) {
                        UnlockTracker.hardUnlock(u.key);
                    }
                }
                InputHelper.justClickedLeft = false;
                __instance.hide();
                if (!AbstractDungeon.is_victory) {
                    AbstractDungeon.previousScreen = VictoryDeathScreens.DUELIST_DEATH;
                }
                else {
                    AbstractDungeon.previousScreen = VictoryDeathScreens.DUELIST_VICTORY;
                }
                AbstractDungeon.closeCurrentScreen();
            }
            textColor.a = MathHelper.fadeLerpSnap(textColor.a, targetA);
            btnColor.a = textColor.a;
            ReflectionHacks.setPrivate(__instance, UnlockConfirmButton.class, "textColor", textColor);
            ReflectionHacks.setPrivate(__instance, UnlockConfirmButton.class, "btnColor", btnColor);
            return SpireReturn.Return();
        }
    }

    @SpirePatch(clz = MonsterGroup.class, method = "update")
    public static class MGUpdatePatch
    {
        @SpireInsertPatch(locator= Locator.class)
        public static SpireReturn<Void> Insert(MonsterGroup __instance)
        {
            if (AbstractDungeon.screen == VictoryDeathScreens.DUELIST_DEATH) {
                __instance.hoveredMonster = null;
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
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
}
