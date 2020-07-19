package duelistmod.patches;

import basemod.devcommands.unlock.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import duelistmod.DuelistMod;
import duelistmod.characters.*;
import duelistmod.helpers.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

//Copied from The Animator, then modified
public class CharacterSelectScreenPatch
{
	protected static final Logger logger = LogManager.getLogger(CharacterSelectScreenPatch.class.getName());

	public static final UIStrings UIStrings = CardCrawlGame.languagePack.getUIString("theDuelist:ConfigMenuText");

	public static Hitbox startingCardsLabelHb;
	public static Hitbox startingCardsSelectedHb;
	public static Hitbox startingCardsLeftHb;
	public static Hitbox startingCardsRightHb;

	public static Hitbox challengeModeHb;
	public static Hitbox challengeLevelHb;
	public static Hitbox challengeLeftHb;
	public static Hitbox challengeRightHb;

	public static CharacterOption deckOption;
	public static CharacterOption challengeOption;

	public static float POS_Y_DECK;
	public static float POS_X_DECK;
	public static float POS_Y_CHALLENGE;
	public static float POS_X_CHALLENGE;

	private static int lastChecked = -100;

	public static void Initialize(CharacterSelectScreen selectScreen)
	{
		float deckLeftTextWidth = FontHelper.getSmartWidth(FontHelper.cardTitleFont, "Starting Deck: ", 9999.0F, 0.0F); 
		float deckRightTextWidth = FontHelper.getSmartWidth(FontHelper.cardTitleFont, "################", 9999.0F, 0.0F); 
		float challengeLeftTextWidth = FontHelper.getSmartWidth(FontHelper.cardTitleFont, "Challenge Mode", 9999.0F, 0.0F); 
		float challengeRightTextWidth = FontHelper.getSmartWidth(FontHelper.cardTitleFont, "  Level 20  ", 9999.0F, 0.0F); 

		POS_X_DECK = 180f * Settings.scale;
		POS_X_CHALLENGE = 1200f * Settings.scale;
		POS_Y_DECK = ((float) Settings.HEIGHT / 3.25F);
		POS_Y_CHALLENGE = ((float) Settings.HEIGHT / 3.25F);

		startingCardsLabelHb = new Hitbox(deckLeftTextWidth, 50.0F * Settings.scale);
		startingCardsSelectedHb = new Hitbox(deckRightTextWidth, 50f * Settings.scale);
		startingCardsLeftHb = new Hitbox(70.0F * Settings.scale, 50.0F * Settings.scale);
		startingCardsRightHb = new Hitbox(70.0F * Settings.scale, 50.0F * Settings.scale);

		challengeModeHb = new Hitbox(challengeLeftTextWidth, 50.0F * Settings.scale);
		challengeLevelHb = new Hitbox(challengeRightTextWidth, 50f * Settings.scale);
		challengeLeftHb = new Hitbox(70.0F * Settings.scale, 50.0F * Settings.scale);
		challengeRightHb = new Hitbox(70.0F * Settings.scale, 50.0F * Settings.scale);

		startingCardsLabelHb.move(POS_X_DECK + (deckLeftTextWidth / 2f), POS_Y_DECK);
		startingCardsLeftHb.move(startingCardsLabelHb.x + startingCardsLabelHb.width + (20 * Settings.scale), POS_Y_DECK - (10 * Settings.scale));
		startingCardsSelectedHb.move(startingCardsLeftHb.x + startingCardsLeftHb.width + (deckRightTextWidth / 2f), POS_Y_DECK);
		startingCardsRightHb.move(startingCardsSelectedHb.x + startingCardsSelectedHb.width + (10 * Settings.scale), POS_Y_DECK - (10 * Settings.scale));

		challengeModeHb.move(POS_X_CHALLENGE + (challengeLeftTextWidth / 2f), POS_Y_CHALLENGE);
		challengeLeftHb.move(challengeModeHb.x + challengeModeHb.width + (20 * Settings.scale), POS_Y_CHALLENGE - (10 * Settings.scale));
		challengeLevelHb.move(challengeLeftHb.x + challengeLeftHb.width + (challengeRightTextWidth / 2f), POS_Y_CHALLENGE);
		challengeRightHb.move(challengeLevelHb.x + challengeLevelHb.width + (10 * Settings.scale), POS_Y_CHALLENGE - (10 * Settings.scale));

		deckOption = null;
	}

	public static void Update(CharacterSelectScreen selectScreen)
	{
		UpdateSelectedCharacter(selectScreen);
		if (deckOption == null)
		{
			return;
		}

		startingCardsLabelHb.update();
		startingCardsRightHb.update();
		startingCardsLeftHb.update();
		startingCardsSelectedHb.update();

		challengeModeHb.update();
		challengeLevelHb.update();
		challengeRightHb.update();
		challengeLeftHb.update();

		int deckIndex = DuelistCharacterSelect.getIndex();
		if (deckIndex != lastChecked && (deckIndex == 1 || deckIndex == 3 || deckIndex == 4))
		{ 
			DuelistMod.resetDuelistWithDeck(deckIndex);
			//DuelistMod.getEnemyDuelistModel(deckIndex);
			Util.log("Resetting duelist character model! DeckCode=" + deckIndex);
		}
		lastChecked = deckIndex;

		if (InputHelper.justClickedLeft)
		{
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
		}

		if (startingCardsLeftHb.clicked)
		{

			startingCardsLeftHb.clicked = false;
			DuelistMod.shouldReplacePool = false;
			DuelistMod.toReplacePoolWith.clear();
			DuelistCharacterSelect.PreviousLoadout();
			int newIndex = DuelistCharacterSelect.getIndex();
			DuelistCharacterSelect.GetSelectedLoadout().setIndex(newIndex);
			RefreshLoadout(selectScreen, deckOption);
			DuelistMod.resetDuelistWithDeck(newIndex);
			Util.log("Resetting duelist character model! DeckCode=" + newIndex);
			DuelistCustomLoadout info = DuelistCharacterSelect.GetSelectedLoadout();
			if (DuelistMod.challengeLevel > BonusDeckUnlockHelper.challengeLevel(info.Name)) 
			{ 
				DuelistMod.challengeLevel = BonusDeckUnlockHelper.challengeLevel(info.Name); 
				try 
				{
					SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
					config.setInt("currentChallengeLevel", DuelistMod.challengeLevel);
					config.save();
				} catch (Exception e) { e.printStackTrace(); }
			}
		}

		if (startingCardsRightHb.clicked)
		{
			
			startingCardsRightHb.clicked = false;
			DuelistMod.shouldReplacePool = false;
			DuelistMod.toReplacePoolWith.clear();
			DuelistCharacterSelect.NextLoadout();
			int newIndex = DuelistCharacterSelect.getIndex();
			DuelistCharacterSelect.GetSelectedLoadout().setIndex(newIndex);
			RefreshLoadout(selectScreen, deckOption);
			DuelistMod.resetDuelistWithDeck(newIndex);
			Util.log("Resetting duelist character model! DeckCode=" + newIndex);
			DuelistCustomLoadout info = DuelistCharacterSelect.GetSelectedLoadout();
			if (DuelistMod.challengeLevel > BonusDeckUnlockHelper.challengeLevel(info.Name)) 
			{ 
				DuelistMod.challengeLevel = BonusDeckUnlockHelper.challengeLevel(info.Name); 
			}
		}
		
		if (challengeModeHb.clicked)
		{
			challengeModeHb.clicked = false;
			DuelistCustomLoadout info = DuelistCharacterSelect.GetSelectedLoadout();
			boolean allowChallenge = BonusDeckUnlockHelper.challengeUnlocked(info.Name);
			if (allowChallenge)
			{
				DuelistMod.playingChallenge = !DuelistMod.playingChallenge;
				if (!DuelistMod.playingChallenge) { DuelistMod.challengeLevel = 0; }
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
				}
			}
		}

		if (challengeRightHb.clicked)
		{
			challengeRightHb.clicked = false;
			if (DuelistMod.playingChallenge)
			{
				DuelistCustomLoadout info = DuelistCharacterSelect.GetSelectedLoadout();
				if (DuelistMod.challengeLevel < 20 && BonusDeckUnlockHelper.challengeLevel(info.Name) >= DuelistMod.challengeLevel + 1) 
				{ 
					DuelistMod.challengeLevel++; 
				}
			}
		}
	}

	public static void Render(CharacterSelectScreen selectScreen, SpriteBatch sb)
	{
		if (deckOption == null)
		{
			return;
		}
		
		DuelistCustomLoadout info = DuelistCharacterSelect.GetSelectedLoadout();
		boolean allowChallenge = BonusDeckUnlockHelper.challengeUnlocked(info.Name);
		String description = info.GetDescription();
		selectScreen.confirmButton.isDisabled = info.Locked;
		if (info.permaLocked && !selectScreen.confirmButton.isDisabled) {
			selectScreen.confirmButton.isDisabled = true;
		}
		if (description != null && !info.Locked)
		{
			FontHelper.renderFont(sb, FontHelper.cardTitleFont_small, description, startingCardsSelectedHb.x, startingCardsSelectedHb.cY + (20 * Settings.scale), Settings.GREEN_TEXT_COLOR);
		}
		else if (description != null)
		{
			FontHelper.renderFont(sb, FontHelper.cardTitleFont_small, description, startingCardsSelectedHb.x, startingCardsSelectedHb.cY + (20 * Settings.scale), Settings.RED_TEXT_COLOR);
		}

		FontHelper.renderFont(sb, FontHelper.cardTitleFont, "Starting Deck: ", startingCardsLabelHb.x, startingCardsLabelHb.cY, Settings.GOLD_COLOR);
		FontHelper.renderFont(sb, FontHelper.cardTitleFont, info.Name, startingCardsSelectedHb.x, startingCardsSelectedHb.cY, Settings.CREAM_COLOR);
		//FontHelper.renderFont(sb, FontHelper.cardTitleFont, "Challenge Mode", challengeModeHb.x, challengeModeHb.cY, Settings.GOLD_COLOR);
		
		if (!startingCardsLeftHb.hovered) { sb.setColor(Color.LIGHT_GRAY); }
		else { sb.setColor(Color.WHITE); }
		sb.draw(ImageMaster.CF_LEFT_ARROW, startingCardsLeftHb.cX - 24.0F, startingCardsLeftHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);

		if (!startingCardsRightHb.hovered) { sb.setColor(Color.LIGHT_GRAY); }
		else { sb.setColor(Color.WHITE); }
		sb.draw(ImageMaster.CF_RIGHT_ARROW, startingCardsRightHb.cX - 24.0F, startingCardsRightHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);

		if (startingCardsSelectedHb.hovered)
		{
			/*if (!info.deckDesc.equals("") && info.longDesc) 
			{ 
				TipHelper.renderGenericTip(InputHelper.mX - 140.0f * Settings.scale, InputHelper.mY + 240.0f * Settings.scale, "Deck Description", info.deckDesc); 
				TipHelper.renderGenericTip(InputHelper.mX - 140.0f * Settings.scale, InputHelper.mY + 340.0f * Settings.scale, "Unlock Requirements", info.unlockReq);
			}
			else if (!info.deckDesc.equals("")) 
			{ 
				TipHelper.renderGenericTip(InputHelper.mX - 140.0f * Settings.scale, InputHelper.mY + 340.0f * Settings.scale, "Deck Description", info.deckDesc);
			}
			else*/
			if (info.longDesc) { TipHelper.renderGenericTip(InputHelper.mX - 140.0f * Settings.scale, InputHelper.mY + 250.0f * Settings.scale, "Unlock Requirements", info.unlockReq); }
		}
	
		if (allowChallenge && DuelistMod.allowChallengeMode)
		{
			Color challengeLevelColor = Settings.BLUE_TEXT_COLOR;
			if (!DuelistMod.playingChallenge) { challengeLevelColor = Settings.RED_TEXT_COLOR; }
			FontHelper.renderFont(sb, FontHelper.cardTitleFont_small, "Level " + DuelistMod.challengeLevel, challengeLevelHb.x, challengeLevelHb.cY, challengeLevelColor);
			if (!challengeLeftHb.hovered || !DuelistMod.playingChallenge) { sb.setColor(Color.LIGHT_GRAY); }
			else { sb.setColor(Color.WHITE); }
			sb.draw(ImageMaster.CF_LEFT_ARROW, challengeLeftHb.cX - 24.0F, challengeLeftHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);

			if (!challengeRightHb.hovered || !DuelistMod.playingChallenge) { sb.setColor(Color.LIGHT_GRAY); }
			else { sb.setColor(Color.WHITE); }
			sb.draw(ImageMaster.CF_RIGHT_ARROW, challengeRightHb.cX - 24.0F, challengeRightHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
	
			// Render tip on hover over Challenge Level
			if (challengeLevelHb.hovered && Util.getChallengeLevel() > -1)
			{
				 TipHelper.renderGenericTip(InputHelper.mX - 140.0f * Settings.scale, InputHelper.mY + 340.0f * Settings.scale, "Challenge #b" + DuelistMod.challengeLevel, Util.getChallengeDifficultyDesc());
			}
			
			// Challenge Mode toggle
	        sb.setColor(Color.WHITE);
	        sb.draw(ImageMaster.OPTION_TOGGLE, challengeModeHb.cX + 20.0F, challengeModeHb.cY - 40.0f, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
	        if (DuelistMod.playingChallenge) { sb.draw(ImageMaster.OPTION_TOGGLE_ON, challengeModeHb.cX + 20.0F, challengeModeHb.cY - 40.0f, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false); } 
	        if (challengeModeHb.hovered) 
	        {
	            FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, "Challenge Mode", challengeModeHb.x, challengeModeHb.cY - 10.0f, Settings.GREEN_TEXT_COLOR);
	            TipHelper.renderGenericTip(InputHelper.mX - 140.0f * Settings.scale, InputHelper.mY + 340.0f * Settings.scale, "Challenge Mode", "Unlock higher Challenge levels by completing Act 3 on Ascension 20 at the highest Challenge level available.");
	        }
	        else 
	        {
	        	FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, "Challenge Mode", challengeModeHb.x, challengeModeHb.cY - 10.0f, Settings.GOLD_COLOR);
	        }
		}
		else 
		{ 
			DuelistMod.playingChallenge = false;
			DuelistMod.challengeLevel = 0;
			Color challengeLevelColor = Settings.RED_TEXT_COLOR;
			FontHelper.renderFont(sb, FontHelper.cardTitleFont_small, "Level " + DuelistMod.challengeLevel, challengeLevelHb.x, challengeLevelHb.cY, challengeLevelColor);
			sb.setColor(Color.LIGHT_GRAY);
			sb.draw(ImageMaster.CF_LEFT_ARROW, challengeLeftHb.cX - 24.0F, challengeLeftHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
			sb.setColor(Color.LIGHT_GRAY);
			sb.draw(ImageMaster.CF_RIGHT_ARROW, challengeRightHb.cX - 24.0F, challengeRightHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);

			// Challenge Mode toggle
	        sb.setColor(Color.WHITE);
	        if (!DuelistMod.playingChallenge) { sb.draw(ImageMaster.OPTION_TOGGLE, challengeModeHb.cX + 20.0F, challengeModeHb.cY - 40.0f, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false); }
	        if (challengeModeHb.hovered) 
	        {
	            FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, "Challenge Mode", challengeModeHb.x, challengeModeHb.cY - 10.0f, Settings.RED_TEXT_COLOR);
	            TipHelper.renderGenericTip(InputHelper.mX - 140.0f * Settings.scale, InputHelper.mY + 340.0f * Settings.scale, "Challenge Mode", "Unlock Challenge Mode by defeating the Heart at Ascension 20.");
	        }
	        else 
	        {
	        	FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, "Challenge Mode", challengeModeHb.x, challengeModeHb.cY - 10.0f, Settings.RED_TEXT_COLOR);
	        }
		}
		startingCardsLabelHb.render(sb);
		startingCardsLeftHb.render(sb);
		startingCardsRightHb.render(sb);
		challengeModeHb.render(sb);
		challengeLevelHb.render(sb);
		challengeLeftHb.render(sb);
		challengeRightHb.render(sb);
	}

	private static void UpdateSelectedCharacter(CharacterSelectScreen selectScreen)
	{
		CharacterOption current = deckOption;
		deckOption = null;
		for (CharacterOption o : selectScreen.options)
		{
			if (o.selected)
			{
				if (o.c.chosenClass == TheDuelistEnum.THE_DUELIST)
				{
					if (current != o)
					{
						RefreshLoadout(selectScreen, o);
					}

					deckOption = o;
				}

				return;
			}
		}
	}

	private static void RefreshLoadout(CharacterSelectScreen selectScreen, CharacterOption option)
	{
		DuelistCharacterSelect.refreshCharacterDecks();
		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig", DuelistMod.duelistDefaults);
			config.load();
			int duelistScore = config.getInt("duelistScore");
			int currentTotalScore = UnlockTracker.unlockProgress.getInteger("THE_DUELISTTotalScore");
			int scoreToSet = currentTotalScore > 0 ? currentTotalScore : duelistScore;
			scoreToSet = Math.max(duelistScore, scoreToSet);
			Util.log("DUELIST SCORE:: " + scoreToSet);
			DuelistCharacterSelect.GetSelectedLoadout().Refresh(scoreToSet, selectScreen, option);
			config.setInt("duelistScore", scoreToSet);
			config.save();
		} catch(IOException ignored) {}
	}
}
