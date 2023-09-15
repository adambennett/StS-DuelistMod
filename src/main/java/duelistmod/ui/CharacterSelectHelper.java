package duelistmod.ui;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import duelistmod.DuelistMod;
import duelistmod.enums.ConfigOpenSource;
import duelistmod.enums.StartingDeck;
import duelistmod.helpers.*;
import duelistmod.patches.TheDuelistEnum;
import duelistmod.relics.ChallengePuzzle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.text.DecimalFormat;

//Copied from The Animator, then modified
public class CharacterSelectHelper
{
	protected static final Logger logger = LogManager.getLogger(CharacterSelectHelper.class.getName());

	public static final UIStrings UIStrings = CardCrawlGame.languagePack.getUIString("theDuelist:ConfigMenuText");

	public static Hitbox startingCardsLabelHb;
	public static Hitbox startingCardsSelectedHb;
	public static Hitbox startingCardsLeftHb;
	public static Hitbox startingCardsRightHb;

	public static Hitbox challengeModeHb;
	public static Hitbox challengeLevelHb;
	public static Hitbox challengeLeftHb;
	public static Hitbox challengeRightHb;

	public static Hitbox unlockAllDecksHb;
	public static Hitbox trueScoreLabelHb;
	public static Hitbox deckScoreLabelHb;
	public static Hitbox duelistConfigsHb;

	private static CharacterOption deckOption;

	public static float POS_Y_DECK;
	public static float POS_X_DECK;
	public static float POS_Y_CHALLENGE;
	public static float POS_X_CHALLENGE;

	public static void Initialize(CharacterSelectScreen selectScreen)
	{
		float deckLeftTextWidth = FontHelper.getSmartWidth(FontHelper.cardTitleFont, "Starting Deck: ", 9999.0F, 0.0F); 
		float deckRightTextWidth = FontHelper.getSmartWidth(FontHelper.cardTitleFont, "################", 9999.0F, 0.0F); 
		float challengeLeftTextWidth = FontHelper.getSmartWidth(FontHelper.cardTitleFont, "Challenge Mode", 9999.0F, 0.0F); 
		float challengeRightTextWidth = FontHelper.getSmartWidth(FontHelper.cardTitleFont, "  Level 20  ", 9999.0F, 0.0F);
		float unlockAllDecksTextWidth = FontHelper.getSmartWidth(FontHelper.cardTitleFont, "Unlock All Decks", 9999.0F, 0.0F);
		float trueScoreWidth = FontHelper.getSmartWidth(FontHelper.cardTitleFont, "Duelist Leaderboard Score: ############", 9999.0F, 0.0F);
		float duelistConfigsWidth = FontHelper.getSmartWidth(FontHelper.cardTitleFont, "Duelist Configuration Settings", 9999.0F, 0.0F);
		float deckScoreWidth = FontHelper.getSmartWidth(FontHelper.cardTitleFont, "Deck Leaderboard Score: ############", 9999.0F, 0.0F);

		POS_X_DECK = 180f * Settings.scale;
		POS_X_CHALLENGE = 1200f * Settings.scale;
		POS_Y_DECK = ((float) Settings.HEIGHT / 3.25F);
		POS_Y_CHALLENGE = ((float) Settings.HEIGHT / 3.25F);

		startingCardsLabelHb = new Hitbox(deckLeftTextWidth, 50.0F * Settings.scale);
		startingCardsSelectedHb = new Hitbox(deckRightTextWidth, 50f * Settings.scale);
		startingCardsLeftHb = new Hitbox(70.0F * Settings.scale, 50.0F * Settings.scale);
		startingCardsRightHb = new Hitbox(70.0F * Settings.scale, 50.0F * Settings.scale);

		if (!DuelistMod.persistentDuelistData.DeckUnlockSettings.getHideUnlockAllDecksButton()) {
			unlockAllDecksHb = new Hitbox(unlockAllDecksTextWidth, 50.0F * Settings.scale);
			unlockAllDecksHb.move(POS_X_CHALLENGE + (unlockAllDecksTextWidth / 2f) + 10, POS_Y_CHALLENGE + (int)(60 * Settings.scale));
		}

		duelistConfigsHb = new Hitbox(duelistConfigsWidth, 50.0F * Settings.scale);
		int duelistConfigYMod = DuelistMod.persistentDuelistData.DeckUnlockSettings.getHideUnlockAllDecksButton() ? (int)(60 * Settings.scale) : (int)(120 * Settings.scale);
		duelistConfigsHb.move(POS_X_CHALLENGE + (duelistConfigsWidth / 2f) - ((int)102.5 * Settings.scale), POS_Y_CHALLENGE + duelistConfigYMod);

		trueScoreLabelHb = new Hitbox(trueScoreWidth, 50.0F * Settings.scale);
		int trueScoreYMod = DuelistMod.persistentDuelistData.DeckUnlockSettings.getHideUnlockAllDecksButton() ? (int)(180 * Settings.scale) : (int)(240 * Settings.scale);
		trueScoreLabelHb.move(POS_X_CHALLENGE + (trueScoreWidth / 2f) - ((int)102.5 * Settings.scale), POS_Y_CHALLENGE + trueScoreYMod);

		deckScoreLabelHb = new Hitbox(deckScoreWidth,50.0F * Settings.scale);
		int deckScoreYMod = DuelistMod.persistentDuelistData.DeckUnlockSettings.getHideUnlockAllDecksButton() ? (int)(120 * Settings.scale) : (int)(180 * Settings.scale);
		deckScoreLabelHb.move(POS_X_CHALLENGE + (deckScoreWidth / 2f) - ((int)102.5 * Settings.scale), POS_Y_CHALLENGE + deckScoreYMod);

		challengeModeHb = new Hitbox(challengeLeftTextWidth, 50.0F * Settings.scale);
		challengeLevelHb = new Hitbox(challengeRightTextWidth, 50f * Settings.scale);
		challengeLeftHb = new Hitbox(70.0F * Settings.scale, 50.0F * Settings.scale);
		challengeRightHb = new Hitbox(70.0F * Settings.scale, 50.0F * Settings.scale);

		startingCardsLabelHb.move(POS_X_DECK + (deckLeftTextWidth / 2f), POS_Y_DECK);
		startingCardsLeftHb.move(startingCardsLabelHb.x + startingCardsLabelHb.width + (20 * Settings.scale), POS_Y_DECK - (10 * Settings.scale));
		startingCardsSelectedHb.move(startingCardsLeftHb.x + startingCardsLeftHb.width + (deckRightTextWidth / 2f), POS_Y_DECK);
		startingCardsRightHb.move(startingCardsSelectedHb.x + startingCardsSelectedHb.width + (10 * Settings.scale), POS_Y_DECK - (10 * Settings.scale));

		challengeModeHb.move(POS_X_CHALLENGE + (challengeLeftTextWidth / 2f) + 5, POS_Y_CHALLENGE - 5);
		challengeLeftHb.move(challengeModeHb.x + challengeModeHb.width + (20 * Settings.scale), POS_Y_CHALLENGE - (10 * Settings.scale));
		challengeLevelHb.move(challengeLeftHb.x + challengeLeftHb.width + (challengeRightTextWidth / 2f), POS_Y_CHALLENGE);
		challengeRightHb.move(challengeLevelHb.x + challengeLevelHb.width + (10 * Settings.scale), POS_Y_CHALLENGE - (10 * Settings.scale));
	}

	public static void Update(CharacterSelectScreen selectScreen)
	{
		UpdateSelectedCharacter(selectScreen);
		if (deckOption == null || DuelistMod.seedPanelOpen) return;

		startingCardsLabelHb.update();
		startingCardsRightHb.update();
		startingCardsLeftHb.update();
		startingCardsSelectedHb.update();

		challengeModeHb.update();
		challengeLevelHb.update();
		challengeRightHb.update();
		challengeLeftHb.update();

		trueScoreLabelHb.update();
		duelistConfigsHb.update();
		deckScoreLabelHb.update();

		if (!DuelistMod.persistentDuelistData.DeckUnlockSettings.getHideUnlockAllDecksButton()) {
			unlockAllDecksHb.update();
		}

		if (InputHelper.justClickedLeft) {
			if (startingCardsLabelHb.hovered)
			{
				startingCardsLabelHb.clickStarted = true;
			}
			else if (startingCardsRightHb.hovered)
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
			else if (unlockAllDecksHb.hovered && !DuelistMod.persistentDuelistData.DeckUnlockSettings.getHideUnlockAllDecksButton())
			{
				unlockAllDecksHb.clickStarted = true;
			}
			else if (duelistConfigsHb.hovered) {
				duelistConfigsHb.clickStarted = true;
			}
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			rightClickStartingDeck(startingCardsRightHb, selectScreen);
			return;
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			leftClickStartingDeck(startingCardsLeftHb, selectScreen);
			return;
		}

		if (startingCardsLeftHb.clicked) {
			leftClickStartingDeck(startingCardsLeftHb, selectScreen);
		}

		if (startingCardsRightHb.clicked) {
			rightClickStartingDeck(startingCardsRightHb, selectScreen);
		}
		
		if (challengeModeHb.clicked) {
			challengeModeHb.clicked = false;
			boolean allowChallenge = BonusDeckUnlockHelper.challengeUnlocked(StartingDeck.currentDeck);
			if (allowChallenge) {
				DuelistMod.playingChallenge = !DuelistMod.playingChallenge;
				CharSelectInfo info = ReflectionHacks.getPrivate(deckOption, CharacterOption.class, "charInfo");
				if (info != null) {
					if (!DuelistMod.playingChallenge) {
						Util.setChallengeLevel(0);
						info.relics.remove(ChallengePuzzle.ID);
					}
					else {
						info.relics.add(1, ChallengePuzzle.ID);
						Util.updateCharacterSelectScreenPuzzleDescription();
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
					Util.updateCharacterSelectScreenPuzzleDescription();
				}
			}
		}

		if (challengeRightHb.clicked)
		{
			challengeRightHb.clicked = false;
			if (DuelistMod.playingChallenge) {
				if (DuelistMod.challengeLevel < 20 && BonusDeckUnlockHelper.challengeLevel(StartingDeck.currentDeck) >= DuelistMod.challengeLevel + 1) {
					DuelistMod.challengeLevel++;
					Util.updateCharacterSelectScreenPuzzleDescription();
				}
			}
		}

		if (unlockAllDecksHb.clicked && !DuelistMod.persistentDuelistData.DeckUnlockSettings.getHideUnlockAllDecksButton()) {
			unlockAllDecksHb.clicked = false;
			DuelistMod.persistentDuelistData.GameplaySettings.setUnlockAllDecks(!DuelistMod.persistentDuelistData.GameplaySettings.getUnlockAllDecks());
			DuelistMod.configSettingsLoader.save();
		}

		if (duelistConfigsHb.clicked) {
			duelistConfigsHb.clicked = false;
			Util.openModSettings(ConfigOpenSource.CHARACTER_SELECT);
		}
	}

	public static CharSelectInfo getInfo() {
		try {
			return ReflectionHacks.getPrivate(deckOption, CharacterOption.class, "charInfo");
		} catch (Exception ex) {
			return null;
		}
	}

	private static boolean showHoverBoxes() {
		return !DuelistMod.openedModSettings;
	}

	public static void Render(CharacterSelectScreen selectScreen, SpriteBatch sb)
	{
		if (deckOption == null || DuelistMod.seedPanelOpen) return;

		StartingDeck info = StartingDeck.currentDeck;
		boolean allowChallenge = BonusDeckUnlockHelper.challengeUnlocked(StartingDeck.currentDeck);
		String description = info.generateSelectScreenHeader();
		selectScreen.confirmButton.isDisabled = info.isLocked();
		if (info.isPermanentlyLocked() && !selectScreen.confirmButton.isDisabled) {
			selectScreen.confirmButton.isDisabled = true;
		}
		if (!info.isLocked()) {
			FontHelper.renderFont(sb, FontHelper.cardTitleFont, description, startingCardsSelectedHb.x, startingCardsSelectedHb.cY + (20 * Settings.scale) + 10, Settings.GREEN_TEXT_COLOR);
		} else {
			FontHelper.renderFont(sb, FontHelper.cardTitleFont, description, startingCardsSelectedHb.x, startingCardsSelectedHb.cY + (20 * Settings.scale) + 10, Settings.RED_TEXT_COLOR);
		}

		FontHelper.renderFont(sb, FontHelper.cardTitleFont, "Starting Deck: ", startingCardsLabelHb.x, startingCardsLabelHb.cY, Settings.CREAM_COLOR);
		FontHelper.renderFont(sb, FontHelper.cardTitleFont, info.getDeckName(), startingCardsSelectedHb.x, startingCardsSelectedHb.cY, Settings.CREAM_COLOR);

		if (!startingCardsLeftHb.hovered) { sb.setColor(Color.LIGHT_GRAY); }
		else { sb.setColor(Color.WHITE); }
		sb.draw(ImageMaster.CF_LEFT_ARROW, startingCardsLeftHb.cX - 24.0F, startingCardsLeftHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);

		if (!startingCardsRightHb.hovered) { sb.setColor(Color.LIGHT_GRAY); }
		else { sb.setColor(Color.WHITE); }
		sb.draw(ImageMaster.CF_RIGHT_ARROW, startingCardsRightHb.cX - 24.0F, startingCardsRightHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);

		if (startingCardsSelectedHb.hovered)
		{
			if (info.getDeckHoverDescription() != null && showHoverBoxes()) { TipHelper.renderGenericTip(InputHelper.mX - 140.0f * Settings.scale, InputHelper.mY + 250.0f * Settings.scale, "Unlock Requirements", info.getDeckHoverDescription()); }
		}

		// Unlock All Decks toggle
		if (!DuelistMod.persistentDuelistData.DeckUnlockSettings.getHideUnlockAllDecksButton()) {
			sb.setColor(Color.WHITE);
			sb.draw(ImageMaster.OPTION_TOGGLE, unlockAllDecksHb.cX + 22.0F, unlockAllDecksHb.cY - 35.0f, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
			if (DuelistMod.persistentDuelistData.GameplaySettings.getUnlockAllDecks()) {
				sb.draw(ImageMaster.OPTION_TOGGLE_ON, unlockAllDecksHb.cX + 22.0F, unlockAllDecksHb.cY - 35.0f, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
			}
			if (unlockAllDecksHb.hovered) {
				FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, "Unlock All Decks", unlockAllDecksHb.x, unlockAllDecksHb.cY, Settings.GREEN_TEXT_COLOR);
				if (showHoverBoxes()) {
					TipHelper.renderGenericTip(InputHelper.mX - 140.0f * Settings.scale, InputHelper.mY + 340.0f * Settings.scale, "Unlock All Decks", "Temporarily unlock all decks. NL Does not reset your progress!");
				}
			} else {
				FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, "Unlock All Decks", unlockAllDecksHb.x, unlockAllDecksHb.cY, Settings.CREAM_COLOR);
			}
		}

		// Leaderboard Scores
		DecimalFormat formatter = new DecimalFormat("#,###");

		// True Score
		FontHelper.renderFont(sb, FontHelper.cardTitleFont, "Duelist Leaderboard Score: " + formatter.format(DuelistMod.trueDuelistScore), trueScoreLabelHb.x, trueScoreLabelHb.cY, trueScoreLabelHb.hovered ? Settings.GREEN_TEXT_COLOR : Settings.CREAM_COLOR);
		if (trueScoreLabelHb.hovered && showHoverBoxes()) {
			TipHelper.renderGenericTip(InputHelper.mX - 140.0f * Settings.scale, InputHelper.mY + 340.0f * Settings.scale, "Leaderboard Score", "Determines your position on the score leaderboard, which can be found on the duelist metrics site.");
		}

		// Deck Score
		FontHelper.renderFont(sb, FontHelper.cardTitleFont, "Deck Leaderboard Score: " + formatter.format(StartingDeck.currentDeck.getDeckScore()), deckScoreLabelHb.x, deckScoreLabelHb.cY, deckScoreLabelHb.hovered ? Settings.GREEN_TEXT_COLOR : Settings.CREAM_COLOR);
		if (deckScoreLabelHb.hovered && showHoverBoxes()) {
			TipHelper.renderGenericTip(InputHelper.mX - 140.0f * Settings.scale, InputHelper.mY + 340.0f * Settings.scale, "Leaderboard Score", "Determines your position on the score leaderboard, which can be found on the duelist metrics site.");
		}

		// Duelist Configuration Settings
		FontHelper.renderFont(sb, FontHelper.cardTitleFont, "Duelist Configuration Settings", duelistConfigsHb.x, duelistConfigsHb.cY, duelistConfigsHb.hovered ? Settings.GREEN_TEXT_COLOR : Settings.CREAM_COLOR);
		if (duelistConfigsHb.hovered && showHoverBoxes()) {
			TipHelper.renderGenericTip(InputHelper.mX - 140.0f * Settings.scale, InputHelper.mY + 340.0f * Settings.scale, "Configurations Menu", "Modify DuelistMod-specific configuration settings to make the game play the way you want it to!");
		}
	
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
			if (challengeLevelHb.hovered && Util.getChallengeLevel() > -1 && showHoverBoxes())
			{
				 TipHelper.renderGenericTip(InputHelper.mX - 140.0f * Settings.scale, InputHelper.mY + 340.0f * Settings.scale, "Challenge #b" + DuelistMod.challengeLevel, Util.getChallengeDifficultyDesc());
			}
			
			// Challenge Mode toggle
	        sb.setColor(Color.WHITE);
	        sb.draw(ImageMaster.OPTION_TOGGLE, challengeModeHb.cX + 22.0F, challengeModeHb.cY - 38.0f, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
	        if (DuelistMod.playingChallenge) { sb.draw(ImageMaster.OPTION_TOGGLE_ON, challengeModeHb.cX + 22.0F, challengeModeHb.cY - 38.0f, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false); }
	        if (challengeModeHb.hovered) 
	        {
	            FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, "Challenge Mode", challengeModeHb.x, challengeModeHb.cY, Settings.GREEN_TEXT_COLOR);
	            if (showHoverBoxes()) {
					TipHelper.renderGenericTip(InputHelper.mX - 140.0f * Settings.scale, InputHelper.mY + 340.0f * Settings.scale, "Challenge Mode", "Unlock higher Challenge levels by completing Act 3 on Ascension 20 at the highest Challenge level available.");
				}
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
	        if (!DuelistMod.playingChallenge) { sb.draw(ImageMaster.OPTION_TOGGLE, challengeModeHb.cX + 22.0F, challengeModeHb.cY - 38.0f, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false); }
	        if (challengeModeHb.hovered) 
	        {
	            FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, "Challenge Mode", challengeModeHb.x, challengeModeHb.cY, Settings.RED_TEXT_COLOR);
	            if (showHoverBoxes()) {
					TipHelper.renderGenericTip(InputHelper.mX - 140.0f * Settings.scale, InputHelper.mY + 340.0f * Settings.scale, "Challenge Mode", "Unlock Challenge Mode by defeating the Heart at Ascension 20.");
				}
	        }
	        else 
	        {
	        	FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, "Challenge Mode", challengeModeHb.x, challengeModeHb.cY, Settings.RED_TEXT_COLOR);
	        }
		}
		startingCardsLabelHb.render(sb);
		startingCardsLeftHb.render(sb);
		startingCardsRightHb.render(sb);
		challengeModeHb.render(sb);
		challengeLevelHb.render(sb);
		challengeLeftHb.render(sb);
		challengeRightHb.render(sb);
		trueScoreLabelHb.render(sb);
		duelistConfigsHb.render(sb);
		deckScoreLabelHb.render(sb);
		if (!DuelistMod.persistentDuelistData.DeckUnlockSettings.getHideUnlockAllDecksButton()) {
			unlockAllDecksHb.render(sb);
		}
	}

	private static void UpdateSelectedCharacter(CharacterSelectScreen selectScreen)
	{
		deckOption = null;
		for (CharacterOption o : selectScreen.options)
		{
			if (o.selected && o.c.chosenClass == TheDuelistEnum.THE_DUELIST)
			{
				deckOption = o;
				return;
			}
		}
	}

	public static void leftClickStartingDeck(Hitbox leftHb, CharacterSelectScreen selectScreen) {
		leftHb.clicked = false;
		DuelistMod.shouldReplacePool = false;
		DuelistMod.toReplacePoolWith.clear();
		StartingDeck.lastDeck(selectScreen);
		if (DuelistMod.challengeLevel > BonusDeckUnlockHelper.challengeLevel(StartingDeck.currentDeck)) {
			Util.setChallengeLevel(BonusDeckUnlockHelper.challengeLevel(StartingDeck.currentDeck));
		}
	}

	public static void rightClickStartingDeck(Hitbox rightHb, CharacterSelectScreen selectScreen) {
		rightHb.clicked = false;
		DuelistMod.shouldReplacePool = false;
		DuelistMod.toReplacePoolWith.clear();
		StartingDeck.nextDeck(selectScreen);
		if (DuelistMod.challengeLevel > BonusDeckUnlockHelper.challengeLevel(StartingDeck.currentDeck)) {
			Util.setChallengeLevel(BonusDeckUnlockHelper.challengeLevel(StartingDeck.currentDeck));
		}
	}
}
