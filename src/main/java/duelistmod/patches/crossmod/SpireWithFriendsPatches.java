package duelistmod.patches.crossmod;

import basemod.ReflectionHacks;
import chronoMods.TogetherManager;
import chronoMods.network.NetworkHelper;
import chronoMods.network.RemotePlayer;
import chronoMods.ui.lobby.NewGameScreen;
import chronoMods.ui.mainMenu.NewMenuButtons;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.SeedHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import duelistmod.enums.StartingDeck;
import duelistmod.patches.TheDuelistEnum;
import duelistmod.ui.spireWithFriends.SpireWithFriendsUtils;
import com.badlogic.gdx.graphics.Color;

public class SpireWithFriendsPatches {

    public static final float TOOLTIP_Y_OFFSET = 50.0f * Settings.scale;
    public static final float TOGGLE_X_RIGHT = 1400.0f * Settings.xScale;

    @SuppressWarnings("unused")
    @SpirePatch(clz = NewGameScreen.class, method = SpirePatch.CONSTRUCTOR, optional = true, requiredModId = "chronoMods")
    public static class LobbyConstructorPatch {
        public static void Postfix() {
            SpireWithFriendsUtils.constructor();
        }
    }

    @SuppressWarnings("unused")
    @SpirePatch(clz = NewGameScreen.class, method = "update", optional = true, requiredModId = "chronoMods")
    public static class UpdatePatch {
        public static SpireReturn<Void> Prefix(NewGameScreen __instance) {
            if (!__instance.characterSelectWidget.getChosenClass().equals(TheDuelistEnum.THE_DUELIST)) {
                return SpireReturn.Continue();
            }
            __instance.button.update();
            if (__instance.button.hb.clicked || InputHelper.pressedEscape) {
                __instance.button.hb.clicked = false;
                InputHelper.pressedEscape = false;
                __instance.backToMenu();
            }
            __instance.playerList.update();
            if (TogetherManager.currentLobby != null && TogetherManager.currentLobby.isOwner()) {
                if (TogetherManager.gameMode == TogetherManager.mode.Bingo && __instance.bingoDifficulty.isOpen) {
                    __instance.bingoDifficulty.update();
                    return SpireReturn.Return();
                }
                SpireWithFriendsUtils.update();
                __instance.confirmButton.show();
                __instance.confirmButton.isDisabled = false;
                __instance.characterSelectWidget.update();
                __instance.ascensionSelectWidget.update();
                if (TogetherManager.gameMode != TogetherManager.mode.Bingo) {
                    __instance.seedSelectWidget.update();
                }
                if (Settings.isTrial) {
                    __instance.neowToggle.setTicked(false);
                    __instance.lamentToggle.setTicked(false);
                }
                if (TogetherManager.gameMode != TogetherManager.mode.Bingo) {
                    if (__instance.heartToggle.update()) {
                        NetworkHelper.sendData(NetworkHelper.dataType.Rules);
                    }
                    if (__instance.neowToggle.update()) {
                        NetworkHelper.sendData(NetworkHelper.dataType.Rules);
                    }
                    if (Loader.isModLoaded("downfall") && __instance.downfallToggle.update()) {
                        NetworkHelper.sendData(NetworkHelper.dataType.Rules);
                    }
                }
                if (TogetherManager.gameMode == TogetherManager.mode.Coop && __instance.hardToggle.update()) {
                    NetworkHelper.sendData(NetworkHelper.dataType.Rules);
                }
                if (__instance.privateToggle.update()) {
                    NetworkHelper.setLobbyPrivate(__instance.privateToggle.isTicked());
                }
                if (TogetherManager.gameMode == TogetherManager.mode.Bingo) {
                    __instance.bingoDifficulty.update();
                    if (__instance.teamsToggle.update()) {
                        NetworkHelper.sendData(NetworkHelper.dataType.BingoRules);
                    }
                    if (__instance.uniqueBoardToggle.update()) {
                        NetworkHelper.sendData(NetworkHelper.dataType.BingoRules);
                    }
                    if (__instance.blackoutToggle.update()) {
                        NetworkHelper.sendData(NetworkHelper.dataType.BingoRules);
                    }
                }
                if (__instance.lamentToggle.isTicked()) {
                    __instance.neowToggle.setTicked(true);
                }
                if (!__instance.neowToggle.isTicked()) {
                    __instance.lamentToggle.setTicked(false);
                }
                if (__instance.heartToggle.hb.hovered) {
                    TipHelper.renderGenericTip(__instance.heartToggle.hb.cX * 1.03f, __instance.heartToggle.hb.cY + SpireWithFriendsPatches.TOOLTIP_Y_OFFSET, NewGameScreen.LOBBY[5], NewGameScreen.LOBBY[6]);
                }
                if (__instance.neowToggle.hb.hovered) {
                    TipHelper.renderGenericTip(__instance.neowToggle.hb.cX * 1.03f, __instance.neowToggle.hb.cY + SpireWithFriendsPatches.TOOLTIP_Y_OFFSET, NewGameScreen.LOBBY[7], NewGameScreen.LOBBY[8]);
                }
                if (__instance.privateToggle.hb.hovered) {
                    TipHelper.renderGenericTip(__instance.privateToggle.hb.cX * 0.85f, __instance.privateToggle.hb.cY + SpireWithFriendsPatches.TOOLTIP_Y_OFFSET + 48.0f, NewGameScreen.LOBBY[19], NewGameScreen.LOBBY[20]);
                }
                if (__instance.hardToggle.hb.hovered) {
                    TipHelper.renderGenericTip(__instance.hardToggle.hb.cX * 1.03f, __instance.hardToggle.hb.cY + SpireWithFriendsPatches.TOOLTIP_Y_OFFSET + 48.0f, NewGameScreen.LOBBY[33], NewGameScreen.LOBBY[34]);
                }
                if (TogetherManager.gameMode == TogetherManager.mode.Versus) {
                    if (__instance.lamentToggle.update()) {
                        NetworkHelper.sendData(NetworkHelper.dataType.Rules);
                    }
                    if (__instance.lamentToggle.hb.hovered) {
                        TipHelper.renderGenericTip(__instance.lamentToggle.hb.cX * 1.03f, __instance.lamentToggle.hb.cY + SpireWithFriendsPatches.TOOLTIP_Y_OFFSET, NewGameScreen.LOBBY[21], NewGameScreen.LOBBY[22]);
                    }
                    if (__instance.ironmanToggle.update()) {
                        NetworkHelper.sendData(NetworkHelper.dataType.Rules);
                    }
                    if (__instance.ironmanToggle.hb.hovered) {
                        TipHelper.renderGenericTip(__instance.ironmanToggle.hb.cX * 1.03f, __instance.ironmanToggle.hb.cY + SpireWithFriendsPatches.TOOLTIP_Y_OFFSET, NewGameScreen.LOBBY[9], NewGameScreen.LOBBY[10]);
                    }
                }
                if (TogetherManager.gameMode == TogetherManager.mode.Bingo) {
                    if (__instance.teamsToggle.hb.hovered) {
                        TipHelper.renderGenericTip(__instance.teamsToggle.hb.cX * 1.03f, __instance.teamsToggle.hb.cY + SpireWithFriendsPatches.TOOLTIP_Y_OFFSET, NewGameScreen.LOBBY[26], NewGameScreen.LOBBY[27]);
                    }
                    if (__instance.uniqueBoardToggle.hb.hovered) {
                        TipHelper.renderGenericTip(__instance.uniqueBoardToggle.hb.cX * 1.03f, __instance.uniqueBoardToggle.hb.cY + SpireWithFriendsPatches.TOOLTIP_Y_OFFSET, NewGameScreen.LOBBY[28], NewGameScreen.LOBBY[29]);
                    }
                    if (__instance.bingoDifficulty.getHitbox().hovered) {
                        TipHelper.renderGenericTip(__instance.bingoDifficulty.getHitbox().cX * 1.03f, __instance.bingoDifficulty.getHitbox().cY + SpireWithFriendsPatches.TOOLTIP_Y_OFFSET, NewGameScreen.LOBBY[30], NewGameScreen.LOBBY[31]);
                    }
                    if (__instance.blackoutToggle.hb.hovered) {
                        TipHelper.renderGenericTip(__instance.blackoutToggle.hb.cX * 1.03f, __instance.blackoutToggle.hb.cY + SpireWithFriendsPatches.TOOLTIP_Y_OFFSET, NewGameScreen.LOBBY[35], NewGameScreen.LOBBY[36]);
                    }
                }
                __instance.customModeButton.update();
                if (__instance.customModeButton.hb.clicked || CInputActionSet.proceed.isJustPressed()) {
                    __instance.customModeButton.hb.clicked = false;
                    NewMenuButtons.customScreen.open();
                }
                if (__instance.customModeButton.hb.hovered) {
                    TipHelper.renderGenericTip(__instance.customModeButton.hb.cX + 320.0f * Settings.scale / 2.0f, __instance.customModeButton.hb.cY + SpireWithFriendsPatches.TOOLTIP_Y_OFFSET, NewGameScreen.LOBBY[24], NewGameScreen.LOBBY[25]);
                }
            }
            else if (TogetherManager.currentLobby != null && TogetherManager.gameMode != TogetherManager.mode.Versus) {
                __instance.characterSelectWidget.update();
            }
            __instance.seedSelectWidget.currentSeed = SeedHelper.getUserFacingSeedString();
            if (TogetherManager.gameMode == TogetherManager.mode.Bingo && __instance.teamsToggle.isTicked()) {
                __instance.renameHb.update();
                if (__instance.renameHb.justHovered) {
                    CardCrawlGame.sound.play("UI_HOVER");
                }
                else if (__instance.renameHb.hovered && InputHelper.justClickedLeft) {
                    __instance.renameHb.clickStarted = true;
                    CardCrawlGame.sound.play("UI_CLICK_1");
                }
                else if (__instance.renameHb.clicked) {
                    __instance.renameHb.clicked = false;
                    String teamName = TogetherManager.getCurrentUser().teamName;
                    if (teamName.equals("")) {
                        teamName = "Team " + TogetherManager.getCurrentUser().team;
                    }
                    __instance.renamePopup.open(teamName);
                }
                __instance.renamePopup.update();
            }
            __instance.confirmButton.isDisabled = false;
            for (final RemotePlayer player : TogetherManager.players) {
                if (!player.ready) {
                    __instance.confirmButton.isDisabled = true;
                    break;
                }
            }
            if (TogetherManager.players.size() == 0 || TogetherManager.currentLobby == null) {
                __instance.confirmButton.isDisabled = true;
            }

            ReflectionHacks.privateMethod(NewGameScreen.class, "updateEmbarkButton").invoke(__instance);
            if (__instance.playerList.clicked) {
                __instance.playerList.toggleReadyState();
                if (__instance.playerList.joinButton.buttonText.equals(NewGameScreen.LOBBY[17])) {
                    __instance.playerList.joinButton.updateText(NewGameScreen.LOBBY[18]);
                }
                else {
                    __instance.playerList.joinButton.updateText(NewGameScreen.LOBBY[17]);
                }
                NetworkHelper.sendData(NetworkHelper.dataType.Ready);
            }
            InputHelper.justClickedLeft = false;
            return SpireReturn.Return();
        }
    }

    @SuppressWarnings("unused")
    @SpirePatch(clz = NewGameScreen.class, method = "render", optional = true, requiredModId = "chronoMods")
    public static class RenderPatch {
        public static void Postfix(NewGameScreen __instance, SpriteBatch sb) {
            if (__instance.characterSelectWidget.getChosenClass().equals(TheDuelistEnum.THE_DUELIST)) {
                StartingDeck info = StartingDeck.currentDeck;

                //FontHelper.renderFont(sb, FontHelper.cardTitleFont, "Starting Deck: ", SpireWithFriendsUtils.startingCardsLabelHb.x, SpireWithFriendsUtils.startingCardsLabelHb.cY, Settings.CREAM_COLOR);
                FontHelper.renderFont(sb, FontHelper.cardTitleFont, SpireWithFriendsUtils.getDeckDisplayName(info.getDeckName()), SpireWithFriendsUtils.startingCardsSelectedHb.x, SpireWithFriendsUtils.startingCardsSelectedHb.cY, Settings.CREAM_COLOR);

                sb.setColor(!SpireWithFriendsUtils.startingCardsLeftHb.hovered ? Color.LIGHT_GRAY : Color.WHITE);
                sb.draw(ImageMaster.CF_LEFT_ARROW, SpireWithFriendsUtils.startingCardsLeftHb.cX - 24.0F, SpireWithFriendsUtils.startingCardsLeftHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);

                sb.setColor(!SpireWithFriendsUtils.startingCardsRightHb.hovered ? Color.LIGHT_GRAY : Color.WHITE);
                sb.draw(ImageMaster.CF_RIGHT_ARROW, SpireWithFriendsUtils.startingCardsRightHb.cX - 24.0F, SpireWithFriendsUtils.startingCardsRightHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);

                SpireWithFriendsUtils.render(sb);
            }
        }
    }

}
