package duelistmod.ui.spireWithFriends;

import chronoMods.ui.lobby.NewGameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import duelistmod.DuelistMod;
import duelistmod.enums.StartingDeck;
import duelistmod.helpers.Util;
import duelistmod.relics.ChallengePuzzle;
import duelistmod.ui.CharacterSelectHelper;
import duelistmod.patches.crossmod.SpireWithFriendsPatches;

public class SpireWithFriendsUtils {

    public static Hitbox startingCardsSelectedHb;
    public static Hitbox startingCardsLeftHb;
    public static Hitbox startingCardsRightHb;
    public static Hitbox challengeModeHb;
    public static Hitbox challengeLevelHb;
    public static Hitbox challengeLeftHb;
    public static Hitbox challengeRightHb;

    public static void constructor() {
        float POS_X_CHALLENGE = 200f * Settings.scale;
        float challengeLeftTextWidth = FontHelper.getSmartWidth(FontHelper.cardTitleFont, "Challenge Mode", 9999.0F, 0.0F);
        float challengeRightTextWidth = FontHelper.getSmartWidth(FontHelper.cardTitleFont, "  Level 20  ", 9999.0F, 0.0F);

        startingCardsSelectedHb = new Hitbox(80.0f * Settings.scale, 80.0f * Settings.scale);
        startingCardsLeftHb = new Hitbox(70.0f * Settings.scale, 70.0f * Settings.scale);
        startingCardsRightHb = new Hitbox(70.0f * Settings.scale, 70.0f * Settings.scale);

        challengeModeHb = new Hitbox(challengeLeftTextWidth, 50.0F * Settings.scale);
        challengeLevelHb = new Hitbox(challengeRightTextWidth, 50f * Settings.scale);
        challengeLeftHb = new Hitbox(70.0F * Settings.scale, 50.0F * Settings.scale);
        challengeRightHb = new Hitbox(70.0F * Settings.scale, 50.0F * Settings.scale);

        startingCardsLeftHb.move((-122 * Settings.scale) + SpireWithFriendsPatches.TOGGLE_X_RIGHT + startingCardsSelectedHb.width * 1.5f, Settings.HEIGHT * 0.51025F);
        startingCardsSelectedHb.move((85 * Settings.scale) + SpireWithFriendsPatches.TOGGLE_X_RIGHT, Settings.HEIGHT * 0.51725F);
        startingCardsRightHb.move((168 * Settings.scale) + SpireWithFriendsPatches.TOGGLE_X_RIGHT + startingCardsSelectedHb.width * 1.5f, Settings.HEIGHT * 0.51025F);

        challengeModeHb.move(POS_X_CHALLENGE + (challengeLeftTextWidth / 2f) + 5, Settings.HEIGHT * 0.51725F);
        challengeLeftHb.move(challengeModeHb.x + challengeModeHb.width + (20 * Settings.scale), Settings.HEIGHT * 0.51025F);
        challengeLevelHb.move(challengeLeftHb.x + challengeLeftHb.width + (challengeRightTextWidth / 2f), Settings.HEIGHT * 0.51725F);
        challengeRightHb.move(challengeLevelHb.x + challengeLevelHb.width + (10 * Settings.scale), Settings.HEIGHT * 0.51025F);

        DuelistMod.challengeLevel = 0;
    }

    public static void update(NewGameScreen __instance) {
        startingCardsSelectedHb.update();
        startingCardsLeftHb.update();
        startingCardsRightHb.update();
        challengeModeHb.update();
        challengeLevelHb.update();
        challengeRightHb.update();
        challengeLeftHb.update();

        if (InputHelper.justClickedLeft) {
            if (startingCardsRightHb.hovered) {
                startingCardsRightHb.clickStarted = true;
            } else if (startingCardsLeftHb.hovered) {
                startingCardsLeftHb.clickStarted = true;
            } else if (challengeModeHb.hovered) {
                challengeModeHb.clickStarted = true;
            } else if (challengeRightHb.hovered) {
                challengeRightHb.clickStarted = true;
            } else if (challengeLeftHb.hovered) {
                challengeLeftHb.clickStarted = true;
            }
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
        } else if (startingCardsSelectedHb.hovered) {
            TipHelper.renderGenericTip(startingCardsSelectedHb.cX * 1.03f, startingCardsSelectedHb.cY + SpireWithFriendsPatches.TOOLTIP_Y_OFFSET, "DuelistMod Deck", "Select a starting deck to use for The Duelist.");
        }

        if (challengeModeHb.clicked) {
            challengeModeHb.clicked = false;
            DuelistMod.playingChallenge = !DuelistMod.playingChallenge;
            if (!DuelistMod.playingChallenge) {
                Util.setChallengeLevel(0);
            }

            int selectedPlayerIndex = __instance.characterSelectWidget.getChosenOption();
            if (selectedPlayerIndex < __instance.characterSelectWidget.options.size() && selectedPlayerIndex > -1) {
                AbstractPlayer selectedPlayer = __instance.characterSelectWidget.options.get(selectedPlayerIndex).c;
                if (selectedPlayer != null) {
                    if (!DuelistMod.playingChallenge) {
                        selectedPlayer.loseRelic(ChallengePuzzle.ID);
                    } else {
                        selectedPlayer.relics.add(1, new ChallengePuzzle());
                    }
                }
            }
        }

        if (challengeLeftHb.clicked) {
            challengeLeftHb.clicked = false;
            DuelistMod.playingChallenge = true;
            if (DuelistMod.challengeLevel > 0) {
                DuelistMod.challengeLevel--;
            } else {
                DuelistMod.challengeLevel = 20;
            }
        }

        if (challengeRightHb.clicked) {
            challengeRightHb.clicked = false;
            DuelistMod.playingChallenge = true;
            if (DuelistMod.challengeLevel < 20) {
                DuelistMod.challengeLevel++;
            } else {
                DuelistMod.challengeLevel = 0;
            }
        }
    }

    public static void render(SpriteBatch sb) {
        StartingDeck info = StartingDeck.currentDeck;

        FontHelper.renderFont(sb, FontHelper.cardTitleFont, SpireWithFriendsUtils.getDeckDisplayName(info.getDeckName()), SpireWithFriendsUtils.startingCardsSelectedHb.x, SpireWithFriendsUtils.startingCardsSelectedHb.cY, Settings.CREAM_COLOR);

        sb.setColor(!SpireWithFriendsUtils.startingCardsLeftHb.hovered ? Color.LIGHT_GRAY : Color.WHITE);
        sb.draw(ImageMaster.CF_LEFT_ARROW, SpireWithFriendsUtils.startingCardsLeftHb.cX - 24.0F, SpireWithFriendsUtils.startingCardsLeftHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);

        sb.setColor(!SpireWithFriendsUtils.startingCardsRightHb.hovered ? Color.LIGHT_GRAY : Color.WHITE);
        sb.draw(ImageMaster.CF_RIGHT_ARROW, SpireWithFriendsUtils.startingCardsRightHb.cX - 24.0F, SpireWithFriendsUtils.startingCardsRightHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);

        Color challengeLevelColor = Settings.BLUE_TEXT_COLOR;
        if (!DuelistMod.playingChallenge) { challengeLevelColor = Settings.RED_TEXT_COLOR; }
        FontHelper.renderFont(sb, FontHelper.cardTitleFont, "Level " + DuelistMod.challengeLevel, challengeLevelHb.x, challengeLevelHb.cY, challengeLevelColor);
        if (!challengeLeftHb.hovered || !DuelistMod.playingChallenge) { sb.setColor(Color.LIGHT_GRAY); }
        else { sb.setColor(Color.WHITE); }
        sb.draw(ImageMaster.CF_LEFT_ARROW, challengeLeftHb.cX - 24.0F, challengeLeftHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);

        if (!challengeRightHb.hovered || !DuelistMod.playingChallenge) { sb.setColor(Color.LIGHT_GRAY); }
        else { sb.setColor(Color.WHITE); }
        sb.draw(ImageMaster.CF_RIGHT_ARROW, challengeRightHb.cX - 24.0F, challengeRightHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);

        // Render tip on hover over Challenge Level
        if (challengeLevelHb.hovered && Util.getChallengeLevel() > -1)
        {
            TipHelper.renderGenericTip(InputHelper.mX - 140.0f * Settings.scale, InputHelper.mY + 250.0f * Settings.scale, "Challenge #b" + DuelistMod.challengeLevel, Util.getChallengeDifficultyDesc(true));
        }

        // Challenge Mode toggle
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.OPTION_TOGGLE, challengeModeHb.cX + 25.0F, challengeModeHb.cY - 45.0f, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
        if (DuelistMod.playingChallenge) { sb.draw(ImageMaster.OPTION_TOGGLE_ON, challengeModeHb.cX + 22.0F, challengeModeHb.cY - 38.0f, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false); }
        if (challengeModeHb.hovered) {
            FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, "Challenge Mode", challengeModeHb.x, challengeModeHb.cY, Settings.GREEN_TEXT_COLOR);
        } else {
            FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, "Challenge Mode", challengeModeHb.x, challengeModeHb.cY, Settings.CREAM_COLOR);
        }

        startingCardsSelectedHb.render(sb);
        startingCardsLeftHb.render(sb);
        startingCardsRightHb.render(sb);
        challengeModeHb.render(sb);
        challengeLevelHb.render(sb);
        challengeLeftHb.render(sb);
        challengeRightHb.render(sb);
    }

    public static String getDeckDisplayName(String name) {
        if (name.contains("Random Deck")) {
            if (name.contains("Small")) {
                return "Random Deck S";
            } else {
                return "Random Deck B";
            }
        }
        return name;
    }

}
