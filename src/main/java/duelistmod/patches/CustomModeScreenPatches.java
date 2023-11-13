package duelistmod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.custom.CustomMod;
import com.megacrit.cardcrawl.screens.custom.CustomModeCharacterButton;
import com.megacrit.cardcrawl.screens.custom.CustomModeScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.ui.panels.SeedPanel;
import duelistmod.DuelistMod;
import duelistmod.characters.TheDuelist;
import duelistmod.enums.StartingDeck;
import duelistmod.helpers.Util;
import duelistmod.relics.ChallengePuzzle;
import duelistmod.ui.CharacterSelectHelper;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class CustomModeScreenPatches {

    private static AbstractPlayer currentSelection;
    public static Hitbox startingCardsLeftHb;
    public static Hitbox startingCardsRightHb;
    public static Hitbox challengeModeHb;
    public static Hitbox challengeLeftHb;
    public static Hitbox challengeRightHb;
    public static Hitbox draftCardsAmtHb;
    public static Hitbox draftCardsLeftHb;
    public static Hitbox draftCardsRightHb;

    public static int draftLimit = 15;
    public static boolean resizedDraftHb = false;

    private static float DUELIST_Y_MOD() {
        return 170.0f * 2;
    }

    private static float CHA_Y_TEXT() {
        return 155.0f * 2;
    }

    private static float SD_Y_TEXT() {
        return 70.0f * 2;
    }

    private static float DRAFT_CARDS_LEFT() { return 370.0f * Settings.scale; }

    private static float DRAFT_CARDS_MIDDLE() { return 435.0f * Settings.scale; }

    private static float DRAFT_CARDS_RIGHT() { return 470.0f * Settings.scale; }

    @SpirePatch(clz = CustomModeScreen.class, method = "updateMods")
    public static class UpdateModsPatch {
        public static SpireReturn<Void> Prefix(CustomModeScreen __instance) {
            if (!(currentSelection instanceof TheDuelist)) {
                return SpireReturn.Continue();
            }

            ArrayList<CustomMod> modList = ReflectionHacks.getPrivate(__instance, CustomModeScreen.class, "modList");
            float scrollY = ReflectionHacks.getPrivate(__instance, CustomModeScreen.class, "scrollY");
            float offset = -(DUELIST_Y_MOD() * Settings.scale);
            for (CustomMod customMod : modList) {
                customMod.update(scrollY + offset);
                if (customMod.ID.equals("Draft")) {
                    if (!resizedDraftHb) {
                        resizedDraftHb = true;
                        customMod.hb.resize((customMod.hb.width / 2) + (120.0f * Settings.scale), customMod.hb.height);
                    }
                    customMod.hb.move(customMod.hb.cX -(205.0f * Settings.scale), customMod.hb.cY);
                    draftCardsLeftHb.move(customMod.hb.cX + DRAFT_CARDS_LEFT(), customMod.hb.cY);
                    draftCardsAmtHb.move(customMod.hb.cX + DRAFT_CARDS_MIDDLE(), customMod.hb.cY);
                    draftCardsRightHb.move(customMod.hb.cX + DRAFT_CARDS_RIGHT(), customMod.hb.cY);
                }
                offset -= customMod.height;
            }
            return SpireReturn.Return();
        }
    }

    @SpirePatch(clz = CustomModeScreen.class, method = "updateSeed")
    public static class UpdateSeedPatch {
        public static SpireReturn<Void> Prefix(CustomModeScreen __instance) {
            if (!(currentSelection instanceof TheDuelist)) {
                return SpireReturn.Continue();
            }

            Hitbox seedHb = ReflectionHacks.getPrivate(__instance, CustomModeScreen.class, "seedHb");
            SeedPanel seedPanel = ReflectionHacks.getPrivate(__instance, CustomModeScreen.class, "seedPanel");
            float scrollY = ReflectionHacks.getPrivate(__instance, CustomModeScreen.class, "scrollY");
            scrollY -= (DUELIST_Y_MOD() * Settings.scale);

            seedHb.move(CustomModeScreen.screenX + 280.0f * Settings.xScale, (scrollY + 320.0f * Settings.scale));
            seedHb.update();
            if (seedHb.justHovered) {
                playHoverSound();
            }
            if (seedHb.hovered && InputHelper.justClickedLeft) {
                seedHb.clickStarted = true;
            }
            if (seedHb.clicked || (CInputActionSet.select.isJustPressed() && seedHb.hovered)) {
                seedHb.clicked = false;
                if (Settings.seed == null) {
                    Settings.seed = 0L;
                }
                seedPanel.show(MainMenuScreen.CurScreen.CUSTOM);
            }

            return SpireReturn.Return();
        }
    }

    @SpirePatch(clz = CustomModeScreen.class, method = "update")
    public static class UpdatePatch {
        public static void Postfix(CustomModeScreen __instance) {
            for (CustomModeCharacterButton b : __instance.options) {
                if (b.selected) {
                    currentSelection = b.c;
                }
            }
            if (!(currentSelection instanceof TheDuelist)) return;

            SeedPanel seedPanel = ReflectionHacks.getPrivate(__instance, CustomModeScreen.class, "seedPanel");
            if (!seedPanel.shown) {
                updateChallengeMode(__instance);
                updateStartingDeck(__instance);
                updateDraftCards(__instance);
            }
        }
    }

    @SpirePatch(clz = CustomModeScreen.class, method = "renderScreen")
    public static class RenderScreenPatch {
        public static SpireReturn<Void> Prefix(CustomModeScreen __instance, SpriteBatch sb) {
            if (!(currentSelection instanceof TheDuelist)) {
                return SpireReturn.Continue();
            }

            float scrollY = ReflectionHacks.getPrivate(__instance, CustomModeScreen.class, "scrollY");
            renderTitle(sb, CustomModeScreen.TEXT[0], scrollY - 50.0f * Settings.scale);
            renderHeader(sb, CustomModeScreen.TEXT[2], scrollY - 120.0f * Settings.scale);
            renderHeader(sb, CustomModeScreen.TEXT[3], scrollY - 290.0f * Settings.scale);
            renderHeader(sb, "Challenge Mode", scrollY - 460.0f * Settings.scale);
            renderHeader(sb, "Starting Deck", scrollY - 630.0f * Settings.scale);
            renderHeader(sb, CustomModeScreen.TEXT[7], scrollY - (460.0f + DUELIST_Y_MOD()) * Settings.scale);
            renderHeader(sb, CustomModeScreen.TEXT[6], scrollY - (630.0f + DUELIST_Y_MOD()) * Settings.scale);
            return SpireReturn.Return();
        }
    }
    
    @SpirePatch(clz = CustomModeScreen.class, method = "render")
    public static class RenderPatch {
        public static void Postfix(CustomModeScreen __instance, SpriteBatch sb) {
            if (currentSelection instanceof TheDuelist) {
                renderChallengeMode(__instance, sb);
                renderStartingDecks(__instance, sb);

                ArrayList<CustomMod> modList = ReflectionHacks.getPrivate(__instance, CustomModeScreen.class, "modList");
                for (CustomMod mod : modList) {
                    if (mod.ID.equals("Draft")) {
                        renderDraftAmount(__instance, sb, mod.hb);
                        break;
                    }
                }
            }
        }
    }

    @SpirePatch(clz = CustomModeScreen.class, method = "open")
    public static class OpenPatch {
        public static void Postfix(CustomModeScreen __instance) {

            float scrollY = ReflectionHacks.getPrivate(__instance, CustomModeScreen.class, "scrollY");
            float ascRightW = FontHelper.getSmartWidth(FontHelper.charDescFont, CustomModeScreen.TEXT[4] + "22", 9999.0f, 0.0f) * Settings.xScale;

            challengeModeHb = new Hitbox(80.0f * Settings.scale, 80.0f * Settings.scale);
            challengeLeftHb = new Hitbox(95.0f * Settings.scale, 95.0f * Settings.scale);
            challengeRightHb = new Hitbox(95.0f * Settings.scale, 95.0f * Settings.scale);
            challengeModeHb.move(CustomModeScreen.screenX + 130.0f * Settings.xScale, scrollY + CHA_Y_TEXT() * Settings.scale);
            challengeLeftHb.move(CustomModeScreen.screenX + ascRightW * 1.1f + 250.0f * Settings.xScale, scrollY + CHA_Y_TEXT() * Settings.scale);
            challengeRightHb.move(CustomModeScreen.screenX + ascRightW * 1.1f + 350.0f * Settings.xScale, scrollY + CHA_Y_TEXT() * Settings.scale);

            startingCardsLeftHb = new Hitbox(95.0f * Settings.scale, 95.0f * Settings.scale);
            startingCardsRightHb = new Hitbox(95.0f * Settings.scale, 95.0f * Settings.scale);
            startingCardsLeftHb.move(CustomModeScreen.screenX + ascRightW * 1.1f + 250.0f * Settings.xScale, scrollY + SD_Y_TEXT() * Settings.scale);
            startingCardsRightHb.move(CustomModeScreen.screenX + ascRightW * 1.1f + 350.0f * Settings.xScale, scrollY + SD_Y_TEXT() * Settings.scale);

            draftCardsLeftHb = new Hitbox(95.0f * Settings.scale, 70.0f * Settings.scale);
            draftCardsAmtHb = new Hitbox(120.0f * Settings.scale, 70.0f * Settings.scale);
            draftCardsRightHb = new Hitbox(95.0f * Settings.scale, 70.0f * Settings.scale);

            DuelistMod.challengeLevel = 0;
            resizedDraftHb = false;

        }
    }

    private static void renderTitle(final SpriteBatch sb, final String text, final float y) {
        FontHelper.renderSmartText(sb, FontHelper.charTitleFont, text, CustomModeScreen.screenX, y + 900.0f * Settings.scale, 9999.0f, 32.0f * Settings.scale, Settings.GOLD_COLOR);
        if (!Settings.usesTrophies) {
            FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, CustomModeScreen.TEXT[1], CustomModeScreen.screenX + FontHelper.getSmartWidth(FontHelper.charTitleFont, text, 9999.0f, 9999.0f) + 18.0f * Settings.scale, y + 888.0f * Settings.scale, 9999.0f, 32.0f * Settings.scale, Settings.RED_TEXT_COLOR);
        }
        else {
            FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, CustomModeScreen.TEXT[9], CustomModeScreen.screenX + FontHelper.getSmartWidth(FontHelper.charTitleFont, text, 9999.0f, 9999.0f) + 18.0f * Settings.scale, y + 888.0f * Settings.scale, 9999.0f, 32.0f * Settings.scale, Settings.RED_TEXT_COLOR);
        }
    }

    private static void renderHeader(final SpriteBatch sb, final String text, final float y) {
        if (Settings.isMobile) {
            FontHelper.renderSmartText(sb, FontHelper.panelNameFont, text, CustomModeScreen.screenX + 50.0f * Settings.scale, y + 850.0f * Settings.scale, 9999.0f, 32.0f * Settings.scale, Settings.GOLD_COLOR, 1.2f);
        }
        else {
            FontHelper.renderSmartText(sb, FontHelper.panelNameFont, text, CustomModeScreen.screenX + 50.0f * Settings.scale, y + 850.0f * Settings.scale, 9999.0f, 32.0f * Settings.scale, Settings.GOLD_COLOR);
        }
    }

    private static void updateStartingDeck(CustomModeScreen __instance) {
        float scrollY = ReflectionHacks.getPrivate(__instance, CustomModeScreen.class, "scrollY");
        startingCardsLeftHb.moveY(scrollY + SD_Y_TEXT() * Settings.scale);
        startingCardsRightHb.moveY(scrollY + SD_Y_TEXT() * Settings.scale);
        startingCardsLeftHb.update();
        startingCardsRightHb.update();

        if (startingCardsRightHb.justHovered || startingCardsLeftHb.justHovered) {
            playHoverSound();
        }

        if (startingCardsLeftHb.hovered && InputHelper.justClickedLeft) {
            playClickStartSound();
            startingCardsLeftHb.clickStarted = true;
        } else if (startingCardsRightHb.hovered && InputHelper.justClickedLeft) {
            playClickStartSound();
            startingCardsRightHb.clickStarted = true;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            CharacterSelectHelper.rightClickStartingDeck(startingCardsRightHb);
            return;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            CharacterSelectHelper.leftClickStartingDeck(startingCardsLeftHb);
            return;
        }

        if (startingCardsLeftHb.clicked) {
            CharacterSelectHelper.leftClickStartingDeck(startingCardsLeftHb);
        } else if (startingCardsRightHb.clicked) {
            CharacterSelectHelper.rightClickStartingDeck(startingCardsRightHb);
        }
    }

    private static void updateDraftCards(CustomModeScreen __instance) {
        float scrollY = ReflectionHacks.getPrivate(__instance, CustomModeScreen.class, "scrollY");
        draftCardsLeftHb.update();
        draftCardsRightHb.update();
        draftCardsAmtHb.update();

        if (draftCardsLeftHb.justHovered || draftCardsRightHb.justHovered || draftCardsAmtHb.justHovered) {
            playHoverSound();
        }

        if (draftCardsLeftHb.hovered && InputHelper.justClickedLeft) {
            playClickStartSound();
            draftCardsLeftHb.clickStarted = true;
        }
        else if (draftCardsRightHb.hovered && InputHelper.justClickedLeft) {
            playClickStartSound();
            draftCardsRightHb.clickStarted = true;
        }

        if (draftCardsLeftHb.clicked) {
            playClickFinishSound();
            draftCardsLeftHb.clicked = false;
            draftLimit--;
            if (draftLimit < 1) {
                draftLimit = 1;
            }
        } else if (draftCardsRightHb.clicked) {
            playClickFinishSound();
            draftCardsRightHb.clicked = false;
            draftLimit++;
        }
    }
    
    private static void updateChallengeMode(CustomModeScreen __instance) {
        float scrollY = ReflectionHacks.getPrivate(__instance, CustomModeScreen.class, "scrollY");
        challengeLeftHb.moveY(scrollY + CHA_Y_TEXT() * Settings.scale);
        challengeRightHb.moveY(scrollY + CHA_Y_TEXT() * Settings.scale);
        challengeModeHb.moveY(scrollY + CHA_Y_TEXT() * Settings.scale);
        challengeModeHb.update();
        challengeLeftHb.update();
        challengeRightHb.update();
        if (challengeModeHb.justHovered || challengeRightHb.justHovered || challengeLeftHb.justHovered) {
            playHoverSound();
        }
        if (challengeModeHb.hovered && InputHelper.justClickedLeft) {
            playClickStartSound();
            challengeModeHb.clickStarted = true;
        }
        else if (challengeLeftHb.hovered && InputHelper.justClickedLeft) {
            playClickStartSound();
            challengeLeftHb.clickStarted = true;
        }
        else if (challengeRightHb.hovered && InputHelper.justClickedLeft) {
            playClickStartSound();
            challengeRightHb.clickStarted = true;
        }
        if (challengeModeHb.clicked || CInputActionSet.topPanel.isJustPressed()) {
            CInputActionSet.topPanel.unpress();
            playClickFinishSound();
            challengeModeHb.clicked = false;
            DuelistMod.playingChallenge = !DuelistMod.playingChallenge;
            if (!DuelistMod.playingChallenge) {
                Util.setChallengeLevel(0);
            }

            if (currentSelection != null) {
                if (!DuelistMod.playingChallenge) {
                    currentSelection.loseRelic(ChallengePuzzle.ID);
                } else {
                    currentSelection.relics.add(1, new ChallengePuzzle());
                }
            }
        }
        else if (challengeLeftHb.clicked) {
            playClickFinishSound();
            challengeLeftHb.clicked = false;
            DuelistMod.playingChallenge = true;
            if (DuelistMod.challengeLevel > 0) {
                DuelistMod.challengeLevel--;
            } else {
                DuelistMod.challengeLevel = 20;
            }
        }
        else if (challengeRightHb.clicked) {
            playClickFinishSound();
            challengeRightHb.clicked = false;
            DuelistMod.playingChallenge = true;
            if (DuelistMod.challengeLevel < 20) {
                DuelistMod.challengeLevel++;
            } else {
                DuelistMod.challengeLevel = 0;
            }
        }
    }

    private static void renderStartingDecks(CustomModeScreen __instance, final SpriteBatch sb) {
        StartingDeck info = StartingDeck.currentDeck;
        float scrollY = ReflectionHacks.getPrivate(__instance, CustomModeScreen.class, "scrollY");
        float imageScale = (Settings.isMobile ? (Settings.scale * 1.2f) : Settings.scale);
        sb.setColor(Color.WHITE);
        FontHelper.renderFontCentered(sb, FontHelper.charDescFont, info.getDeckName(), CustomModeScreen.screenX + 170.0f * Settings.scale, scrollY + SD_Y_TEXT() * Settings.scale, Settings.BLUE_TEXT_COLOR);
        if (startingCardsLeftHb.hovered || Settings.isControllerMode) {
            sb.setColor(Color.WHITE);
        }
        else {
            sb.setColor(Color.LIGHT_GRAY);
        }
        sb.draw(ImageMaster.CF_LEFT_ARROW, startingCardsLeftHb.cX - 24.0f, startingCardsLeftHb.cY - 24.0f, 24.0f, 24.0f, 48.0f, 48.0f, imageScale, imageScale, 0.0f, 0, 0, 48, 48, false, false);
        if (startingCardsRightHb.hovered || Settings.isControllerMode) {
            sb.setColor(Color.WHITE);
        }
        else {
            sb.setColor(Color.LIGHT_GRAY);
        }
        sb.draw(ImageMaster.CF_RIGHT_ARROW, startingCardsRightHb.cX - 24.0f, startingCardsRightHb.cY - 24.0f, 24.0f, 24.0f, 48.0f, 48.0f, imageScale, imageScale, 0.0f, 0, 0, 48, 48, false, false);
        startingCardsLeftHb.render(sb);
        startingCardsRightHb.render(sb);
    }

    private static void renderDraftAmount(CustomModeScreen __instance, final SpriteBatch sb, Hitbox draftModHb) {
        float scrollY = ReflectionHacks.getPrivate(__instance, CustomModeScreen.class, "scrollY");
        float imageScale = (Settings.isMobile ? (Settings.scale * 1.2f) : Settings.scale);
        float ascRightW = FontHelper.getSmartWidth(FontHelper.charDescFont, CustomModeScreen.TEXT[4] + "22", 9999.0f, 0.0f) * Settings.xScale;

        sb.draw(ImageMaster.CF_LEFT_ARROW, draftModHb.cX + DRAFT_CARDS_LEFT(), draftModHb.cY - (5.0f * Settings.scale), 24.0f, 24.0f, 48.0f, 48.0f, imageScale, imageScale, 0.0f, 0, 0, 48, 48, false, false);
        FontHelper.renderFontCentered(sb, FontHelper.charDescFont, draftLimit + "", draftModHb.cX + DRAFT_CARDS_MIDDLE(), draftModHb.cY + (2.5f * Settings.scale), Color.CYAN);
        sb.draw(ImageMaster.CF_RIGHT_ARROW, draftModHb.cX + DRAFT_CARDS_RIGHT(), draftModHb.cY - (5.0f * Settings.scale), 24.0f, 24.0f, 48.0f, 48.0f, imageScale, imageScale, 0.0f, 0, 0, 48, 48, false, false);

        if (draftCardsAmtHb.hovered) {
            TipHelper.renderGenericTip(InputHelper.mX - 150.0f * Settings.scale, InputHelper.mY + 150.0f * Settings.scale, "Draft Amount", "Set the number of cards to draft before the run begins.");
        }

        draftCardsLeftHb.render(sb);
        draftCardsRightHb.render(sb);
        draftCardsAmtHb.render(sb);
    }

    private static void renderChallengeMode(CustomModeScreen __instance, final SpriteBatch sb) {
        float scrollY = ReflectionHacks.getPrivate(__instance, CustomModeScreen.class, "scrollY");
        float imageScale = (Settings.isMobile ? (Settings.scale * 1.2f) : Settings.scale);
        float ascRightW = FontHelper.getSmartWidth(FontHelper.charDescFont, CustomModeScreen.TEXT[4] + "22", 9999.0f, 0.0f) * Settings.xScale;
        sb.setColor(Color.WHITE);
        if (challengeModeHb.hovered) {
            sb.draw(ImageMaster.CHECKBOX, challengeModeHb.cX - 32.0f, challengeModeHb.cY - 32.0f, 32.0f, 32.0f, 64.0f, 64.0f, imageScale * 1.2f, imageScale * 1.2f, 0.0f, 0, 0, 64, 64, false, false);
            sb.setColor(Color.GOLD);
            sb.setBlendFunction(770, 1);
            sb.draw(ImageMaster.CHECKBOX, challengeModeHb.cX - 32.0f, challengeModeHb.cY - 32.0f, 32.0f, 32.0f, 64.0f, 64.0f, imageScale * 1.2f, imageScale * 1.2f, 0.0f, 0, 0, 64, 64, false, false);
            sb.setBlendFunction(770, 771);
        }
        else {
            sb.draw(ImageMaster.CHECKBOX, challengeModeHb.cX - 32.0f, challengeModeHb.cY - 32.0f, 32.0f, 32.0f, 64.0f, 64.0f, imageScale, imageScale, 0.0f, 0, 0, 64, 64, false, false);
        }
        if (challengeModeHb.hovered) {
            FontHelper.renderFontCentered(sb, FontHelper.charDescFont, CustomModeScreen.TEXT[4] + DuelistMod.challengeLevel, CustomModeScreen.screenX + 240.0f * Settings.scale, scrollY + CHA_Y_TEXT() * Settings.scale, Color.CYAN);
        }
        else {
            FontHelper.renderFontCentered(sb, FontHelper.charDescFont, CustomModeScreen.TEXT[4] + DuelistMod.challengeLevel, CustomModeScreen.screenX + 240.0f * Settings.scale, scrollY + CHA_Y_TEXT() * Settings.scale, Settings.BLUE_TEXT_COLOR);
        }
        if (DuelistMod.playingChallenge) {
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.TICK, challengeModeHb.cX - 32.0f, challengeModeHb.cY - 32.0f, 32.0f, 32.0f, 64.0f, 64.0f, imageScale, imageScale, 0.0f, 0, 0, 64, 64, false, false);
            FontHelper.renderSmartText(sb, FontHelper.charDescFont, DuelistMod.challengeLevel + ". " + Util.getChallengeDifficultyDesc(false), CustomModeScreen.screenX + ascRightW * 1.1f + 400.0f * Settings.scale, challengeModeHb.cY + 10.0f * Settings.scale, 9999.0f, 32.0f * Settings.scale, Settings.CREAM_COLOR);
        }

        if (challengeModeHb.hovered && Util.getChallengeLevel() > -1) {
            TipHelper.renderGenericTip(InputHelper.mX - 140.0f * Settings.scale, InputHelper.mY + 250.0f * Settings.scale, "Challenge #b" + DuelistMod.challengeLevel, Util.getChallengeDifficultyDesc(false));
        }

        if (challengeLeftHb.hovered || Settings.isControllerMode) {
            sb.setColor(Color.WHITE);
        }
        else {
            sb.setColor(Color.LIGHT_GRAY);
        }
        sb.draw(ImageMaster.CF_LEFT_ARROW, challengeLeftHb.cX - 24.0f, challengeLeftHb.cY - 24.0f, 24.0f, 24.0f, 48.0f, 48.0f, imageScale, imageScale, 0.0f, 0, 0, 48, 48, false, false);
        if (challengeRightHb.hovered || Settings.isControllerMode) {
            sb.setColor(Color.WHITE);
        }
        else {
            sb.setColor(Color.LIGHT_GRAY);
        }
        sb.draw(ImageMaster.CF_RIGHT_ARROW, challengeRightHb.cX - 24.0f, challengeRightHb.cY - 24.0f, 24.0f, 24.0f, 48.0f, 48.0f, imageScale, imageScale, 0.0f, 0, 0, 48, 48, false, false);
        challengeModeHb.render(sb);
        challengeLeftHb.render(sb);
        challengeRightHb.render(sb);
    }

    private static void playClickStartSound() {
        CardCrawlGame.sound.playA("UI_CLICK_1", -0.1f);
    }

    private static void playClickFinishSound() {
        CardCrawlGame.sound.playA("UI_CLICK_1", -0.1f);
    }

    private static void playHoverSound() {
        CardCrawlGame.sound.playV("UI_HOVER", 0.75f);
    }
}
