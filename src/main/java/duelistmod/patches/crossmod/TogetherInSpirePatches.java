package duelistmod.patches.crossmod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import duelistmod.DuelistMod;
import duelistmod.characters.TheDuelist;
import duelistmod.enums.StartingDeck;
import duelistmod.helpers.BonusDeckUnlockHelper;
import duelistmod.helpers.Util;
import duelistmod.relics.ChallengePuzzle;
import duelistmod.ui.CharacterSelectHelper;
import spireTogether.screens.lobby.MPLobbyScreen;

@SuppressWarnings("unused")
public class TogetherInSpirePatches {

    public static boolean isDuelistSelected = false;
    private static AbstractPlayer selectedPlayer;

    private static Hitbox startingCardsSelectedHb;
    private static Hitbox startingCardsLeftHb;
    private static Hitbox startingCardsRightHb;

    private static Hitbox challengeModeHb;
    private static Hitbox challengeLevelHb;
    private static Hitbox challengeLeftHb;
    private static Hitbox challengeRightHb;

    private static boolean isInitialized = false;

    private static void init() {
        float POS_X_CHALLENGE = 1200f * Settings.scale;
        float POS_Y_CHALLENGE = 1450;//((float) Settings.HEIGHT / 3.25F);

        float deckRightTextWidth = FontHelper.getSmartWidth(FontHelper.cardTitleFont, "################", 9999.0F, 0.0F);
        float challengeLeftTextWidth = FontHelper.getSmartWidth(FontHelper.cardTitleFont, "Challenge Mode", 9999.0F, 0.0F);
        float challengeRightTextWidth = FontHelper.getSmartWidth(FontHelper.cardTitleFont, "  Level 20  ", 9999.0F, 0.0F);

        challengeModeHb = new Hitbox(challengeLeftTextWidth, 50.0F * Settings.scale);
        challengeLevelHb = new Hitbox(challengeRightTextWidth, 50f * Settings.scale);
        challengeLeftHb = new Hitbox(70.0F * Settings.scale, 50.0F * Settings.scale);
        challengeRightHb = new Hitbox(70.0F * Settings.scale, 50.0F * Settings.scale);
        challengeModeHb.move(POS_X_CHALLENGE + (challengeLeftTextWidth / 2f) + 5, POS_Y_CHALLENGE - (10 * Settings.scale));
        challengeLeftHb.move(challengeModeHb.x + challengeModeHb.width + (20 * Settings.scale), POS_Y_CHALLENGE - (10 * Settings.scale));
        challengeLevelHb.move(challengeLeftHb.x + challengeLeftHb.width + (challengeRightTextWidth / 2f), POS_Y_CHALLENGE);
        challengeRightHb.move(challengeLevelHb.x + challengeLevelHb.width + (10 * Settings.scale), POS_Y_CHALLENGE - (10 * Settings.scale));

        startingCardsSelectedHb = new Hitbox(deckRightTextWidth, 50f * Settings.scale);
        startingCardsLeftHb = new Hitbox(80, 80);//70.0F * Settings.scale, 50.0F * Settings.scale);
        startingCardsRightHb = new Hitbox(70.0F * Settings.scale, 50.0F * Settings.scale);
        startingCardsLeftHb.move(2500, 1530);//startingCardsSelectedHb.x + startingCardsSelectedHb.width + (20 * Settings.scale), POS_Y_DECK - (10 * Settings.scale));
        startingCardsSelectedHb.move(2940, 1550);
        startingCardsRightHb.move(challengeLevelHb.x + challengeLevelHb.width + (10 * Settings.scale), 1530);

        isInitialized = true;
    }

    @SpirePatch(clz = MPLobbyScreen.class, method = "init", optional = true, requiredModId = "spireTogether")
    public static class ResetOnInitPatch {
        public static void Postfix() {
            isDuelistSelected = false;
        }
    }

    @SpirePatch(clz = MPLobbyScreen.class, method = "SetRandomCharacter", optional = true, requiredModId = "spireTogether")
    public static class ResetOnRandomCharPatch {
        public static void Postfix() {
            isDuelistSelected = false;
        }
    }

    @SpirePatch(clz = MPLobbyScreen.class, method = "LoadCharacterModel", optional = true, requiredModId = "spireTogether")
    public static class CheckCharacterPatch {
        public static void Postfix(MPLobbyScreen __instance, AbstractPlayer p) {
            isDuelistSelected = p instanceof TheDuelist;
            selectedPlayer = p;
            if (!isInitialized) {
                init();
            }
        }
    }

    @SpirePatch(clz = MPLobbyScreen.class, method = "render", optional = true, requiredModId = "spireTogether")
    public static class RenderDeckSelectArrows {
        public static void Postfix(MPLobbyScreen __instance, SpriteBatch sb) {
            if (isDuelistSelected) {
                StartingDeck info = StartingDeck.currentDeck;
                boolean allowChallenge = BonusDeckUnlockHelper.challengeUnlocked(StartingDeck.currentDeck);
                FontHelper.renderFont(sb, FontHelper.cardTitleFont, info.getDeckName(), startingCardsSelectedHb.x, startingCardsSelectedHb.cY, Settings.CREAM_COLOR);

                if (!startingCardsLeftHb.hovered) { sb.setColor(Color.LIGHT_GRAY); }
                else { sb.setColor(Color.WHITE); }
                sb.draw(ImageMaster.CF_LEFT_ARROW, startingCardsLeftHb.cX - 24.0F, startingCardsLeftHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);

                if (!startingCardsRightHb.hovered) { sb.setColor(Color.LIGHT_GRAY); }
                else { sb.setColor(Color.WHITE); }
                sb.draw(ImageMaster.CF_RIGHT_ARROW, startingCardsRightHb.cX - 24.0F, startingCardsRightHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);

                if (allowChallenge) {
                    Color challengeLevelColor = Settings.BLUE_TEXT_COLOR;
                    if (!DuelistMod.playingChallenge) { challengeLevelColor = Settings.RED_TEXT_COLOR; }
                    FontHelper.renderFont(sb, FontHelper.cardTitleFont, "Level " + DuelistMod.challengeLevel, challengeLevelHb.x, challengeLevelHb.cY, challengeLevelColor);
                    if (!challengeLeftHb.hovered || !DuelistMod.playingChallenge) { sb.setColor(Color.LIGHT_GRAY); }
                    else { sb.setColor(Color.WHITE); }
                    sb.draw(ImageMaster.CF_LEFT_ARROW, challengeLeftHb.cX - 24.0F, challengeLeftHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);

                    if (Util.getChallengeLevel() < 20) {
                        if (!challengeRightHb.hovered || !DuelistMod.playingChallenge || BonusDeckUnlockHelper.challengeLevel(info) <= Util.getChallengeLevel() + 1) { sb.setColor(Color.LIGHT_GRAY); }
                        else { sb.setColor(Color.WHITE); }
                        sb.draw(ImageMaster.CF_RIGHT_ARROW, challengeRightHb.cX - 24.0F, challengeRightHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
                    }

                    // Render tip on hover over Challenge Level
                    if (challengeLevelHb.hovered && Util.getChallengeLevel() > -1)
                    {
                        TipHelper.renderGenericTip(InputHelper.mX - 140.0f * Settings.scale, InputHelper.mY + 250.0f * Settings.scale, "Challenge #b" + DuelistMod.challengeLevel, Util.getChallengeDifficultyDesc());
                    }

                    // Challenge Mode toggle
                    sb.setColor(Color.WHITE);
                    sb.draw(ImageMaster.OPTION_TOGGLE, challengeModeHb.cX + 25.0F, challengeModeHb.cY - 45.0f, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
                    if (DuelistMod.playingChallenge) { sb.draw(ImageMaster.OPTION_TOGGLE_ON, challengeModeHb.cX + 22.0F, challengeModeHb.cY - 38.0f, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false); }
                    if (challengeModeHb.hovered)
                    {
                        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, "Challenge Mode", challengeModeHb.x, challengeModeHb.cY, Settings.GREEN_TEXT_COLOR);
                        TipHelper.renderGenericTip(InputHelper.mX - 140.0f * Settings.scale, InputHelper.mY + 250.0f * Settings.scale, "Challenge Mode", "Unlock higher Challenge levels by completing Act 3 on Ascension 20 at the highest Challenge level available.");
                    }
                    else
                    {
                        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, "Challenge Mode", challengeModeHb.x, challengeModeHb.cY, Settings.CREAM_COLOR);
                    }
                } else {
                    DuelistMod.playingChallenge = false;
                    Util.setChallengeLevel(0);
                    Color challengeLevelColor = Settings.RED_TEXT_COLOR;
                    FontHelper.renderFont(sb, FontHelper.cardTitleFont, "Level " + DuelistMod.challengeLevel, challengeLevelHb.x, challengeLevelHb.cY, challengeLevelColor);
                    sb.setColor(Color.LIGHT_GRAY);
                    sb.draw(ImageMaster.CF_LEFT_ARROW, challengeLeftHb.cX - 24.0F, challengeLeftHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
                    sb.setColor(Color.LIGHT_GRAY);
                    sb.draw(ImageMaster.CF_RIGHT_ARROW, challengeRightHb.cX - 24.0F, challengeRightHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);

                    // Challenge Mode toggle
                    sb.setColor(Color.WHITE);
                    if (!DuelistMod.playingChallenge) { sb.draw(ImageMaster.OPTION_TOGGLE, challengeModeHb.cX + 25.0F, challengeModeHb.cY - 45.0f, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false); }
                    if (challengeModeHb.hovered)
                    {
                        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, "Challenge Mode", challengeModeHb.x, challengeModeHb.cY, Settings.RED_TEXT_COLOR);
                        TipHelper.renderGenericTip(InputHelper.mX - 140.0f * Settings.scale, InputHelper.mY + 250.0f * Settings.scale, "Challenge Mode", "Unlock Challenge Mode by defeating the Heart at Ascension 20.");
                    }
                    else
                    {
                        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, "Challenge Mode", challengeModeHb.x, challengeModeHb.cY, Settings.RED_TEXT_COLOR);
                    }
                }
                startingCardsLeftHb.render(sb);
                startingCardsRightHb.render(sb);
                startingCardsSelectedHb.render(sb);
                challengeModeHb.render(sb);
                challengeLevelHb.render(sb);
                challengeLeftHb.render(sb);
                challengeRightHb.render(sb);
            }
        }
    }

    @SpirePatch(clz = MPLobbyScreen.class, method = "update", optional = true, requiredModId = "spireTogether")
    public static class UpdateDeckSelectArrows {
        public static void Postfix() {
            if (isDuelistSelected) {
                startingCardsRightHb.update();
                startingCardsLeftHb.update();
                startingCardsSelectedHb.update();
                challengeModeHb.update();
                challengeLevelHb.update();
                challengeRightHb.update();
                challengeLeftHb.update();

                if (InputHelper.justClickedLeft) {
                    if (startingCardsRightHb.hovered)
                    {
                        startingCardsRightHb.clickStarted = true;
                    }
                    else if (startingCardsLeftHb.hovered)
                    {
                        startingCardsLeftHb.clickStarted = true;
                    }

                    else if (challengeModeHb.hovered)
                    {
                        challengeModeHb.clickStarted = true;
                    }
                    else if (challengeRightHb.hovered)
                    {
                        challengeRightHb.clickStarted = true;
                    }
                    else if (challengeLeftHb.hovered)
                    {
                        challengeLeftHb.clickStarted = true;
                    }
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                    CharacterSelectHelper.rightClickStartingDeck(startingCardsRightHb, null);
                    return;
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                    CharacterSelectHelper.leftClickStartingDeck(startingCardsLeftHb, null);
                    return;
                }

                if (startingCardsLeftHb.clicked) {
                    CharacterSelectHelper.leftClickStartingDeck(startingCardsLeftHb, null);
                }

                if (startingCardsRightHb.clicked) {
                    CharacterSelectHelper.rightClickStartingDeck(startingCardsRightHb, null);
                }

                if (challengeModeHb.clicked) {
                    challengeModeHb.clicked = false;
                    boolean allowChallenge = BonusDeckUnlockHelper.challengeUnlocked(StartingDeck.currentDeck);
                    if (allowChallenge) {
                        DuelistMod.playingChallenge = !DuelistMod.playingChallenge;
                        if (selectedPlayer != null) {
                            if (!DuelistMod.playingChallenge) {
                                Util.setChallengeLevel(0);
                                selectedPlayer.loseRelic(ChallengePuzzle.ID);
                            }
                            else {
                                selectedPlayer.relics.add(1, new ChallengePuzzle());
                                //Util.updateCharacterSelectScreenPuzzleDescription();
                            }
                        }
                    }
                }

                if (challengeLeftHb.clicked)
                {
                    challengeLeftHb.clicked = false;
                    if (DuelistMod.playingChallenge)
                    {
                        if (DuelistMod.challengeLevel > 0)
                        {
                            DuelistMod.challengeLevel--;
                            //Util.updateCharacterSelectScreenPuzzleDescription();
                        }
                    }
                }

                if (challengeRightHb.clicked)
                {
                    challengeRightHb.clicked = false;
                    if (DuelistMod.playingChallenge) {
                        if (DuelistMod.challengeLevel < 20 && BonusDeckUnlockHelper.challengeLevel(StartingDeck.currentDeck) >= DuelistMod.challengeLevel + 1) {
                            DuelistMod.challengeLevel++;
                            //Util.updateCharacterSelectScreenPuzzleDescription();
                        }
                    }
                }
            }
        }
    }

}
