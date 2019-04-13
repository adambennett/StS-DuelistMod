package duelistmod.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import duelistmod.characters.*;
import duelistmod.patches.TheDuelistEnum;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//Copied from The Animator, then modified
public class CharacterSelectScreenPatch
{
    protected static final Logger logger = LogManager.getLogger(CharacterSelectScreenPatch.class.getName());

    public static final UIStrings UIStrings = CardCrawlGame.languagePack.getUIString("theDuelist:ConfigMenuText");

    public static Hitbox startingCardsLabelHb;
    public static Hitbox startingCardsSelectedHb;
    public static Hitbox startingCardsLeftHb;
    public static Hitbox startingCardsRightHb;

    public static CharacterOption selectedOption;

    public static float POS_Y;
    public static float POS_X;

    public static void Initialize(CharacterSelectScreen selectScreen)
    {
        //float leftTextWidth = FontHelper.getSmartWidth(FontHelper.cardTitleFont_N, UIStrings.TEXT[0], 9999.0F, 0.0F); // Ascension
        //float rightTextWidth = FontHelper.getSmartWidth(FontHelper.cardTitleFont_N, UIStrings.TEXT[1], 9999.0F, 0.0F); // Level 22

        float leftTextWidth = FontHelper.getSmartWidth(FontHelper.cardTitleFont_N, "Starting Deck: ", 9999.0F, 0.0F); // Ascension
        float rightTextWidth = FontHelper.getSmartWidth(FontHelper.cardTitleFont_N, "###############", 9999.0F, 0.0F); // Level 22

        POS_X = 180f * Settings.scale;
        //POS_Y = ((float) Settings.HEIGHT / 2.0F) + (20 * Settings.scale);
        POS_Y = ((float) Settings.HEIGHT / 3.25F);
        //POS_Y = ((float) Settings.HEIGHT / 2.0F) - (40 * Settings.scale);

        startingCardsLabelHb = new Hitbox(leftTextWidth, 50.0F * Settings.scale);
        startingCardsSelectedHb = new Hitbox(rightTextWidth, 50f * Settings.scale);
        startingCardsLeftHb = new Hitbox(70.0F * Settings.scale, 50.0F * Settings.scale);
        startingCardsRightHb = new Hitbox(70.0F * Settings.scale, 50.0F * Settings.scale);

        startingCardsLabelHb.move(POS_X + (leftTextWidth / 2f), POS_Y);
        startingCardsLeftHb.move(startingCardsLabelHb.x + startingCardsLabelHb.width + (20 * Settings.scale), POS_Y - (10 * Settings.scale));
        startingCardsSelectedHb.move(startingCardsLeftHb.x + startingCardsLeftHb.width + (rightTextWidth / 2f), POS_Y);
        startingCardsRightHb.move(startingCardsSelectedHb.x + startingCardsSelectedHb.width + (10 * Settings.scale), POS_Y - (10 * Settings.scale));

        selectedOption = null;
    }

    public static void Update(CharacterSelectScreen selectScreen)
    {
        UpdateSelectedCharacter(selectScreen);
        if (selectedOption == null)
        {
            return;
        }

        startingCardsLabelHb.update();
        startingCardsRightHb.update();
        startingCardsLeftHb.update();

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
        }

        if (startingCardsLeftHb.clicked)
        {
            startingCardsLeftHb.clicked = false;
            DuelistCharacterSelect.PreviousLoadout();
            int newIndex = DuelistCharacterSelect.getIndex();
            DuelistCharacterSelect.GetSelectedLoadout().setIndex(newIndex);
            RefreshLoadout(selectScreen, selectedOption);
        }

        if (startingCardsRightHb.clicked)
        {
            startingCardsRightHb.clicked = false;
            DuelistCharacterSelect.NextLoadout();
            int newIndex = DuelistCharacterSelect.getIndex();
            DuelistCharacterSelect.GetSelectedLoadout().setIndex(newIndex);
            RefreshLoadout(selectScreen, selectedOption);
        }
    }

    public static void Render(CharacterSelectScreen selectScreen, SpriteBatch sb)
    {
        if (selectedOption == null)
        {
            return;
        }

        DuelistCustomLoadout info = DuelistCharacterSelect.GetSelectedLoadout();
        String description = info.GetDescription();
        selectScreen.confirmButton.isDisabled = info.Locked;
        if (description != null && !info.Locked)
        {
            float originalScale = FontHelper.cardTitleFont_small_N.getData().scaleX;
            FontHelper.cardTitleFont_small_N.getData().setScale(Settings.scale * 0.8f);
            FontHelper.renderFont(sb, FontHelper.cardTitleFont_small_N, description, startingCardsSelectedHb.x, startingCardsSelectedHb.cY + (20 * Settings.scale), Settings.GREEN_TEXT_COLOR);
            FontHelper.cardTitleFont_small_N.getData().setScale(Settings.scale * originalScale);
        }
        else if (description != null && info.Locked)
        {
        	 float originalScale = FontHelper.cardTitleFont_small_N.getData().scaleX;
             FontHelper.cardTitleFont_small_N.getData().setScale(Settings.scale * 0.8f);
             FontHelper.renderFont(sb, FontHelper.cardTitleFont_small_N, description, startingCardsSelectedHb.x, startingCardsSelectedHb.cY + (20 * Settings.scale), Settings.RED_TEXT_COLOR);
             FontHelper.cardTitleFont_small_N.getData().setScale(Settings.scale * originalScale);
        }

       // FontHelper.renderFont(sb, FontHelper.cardTitleFont_N, UIStrings.TEXT[0], startingCardsLabelHb.x, startingCardsLabelHb.cY, Settings.GOLD_COLOR);
        FontHelper.renderFont(sb, FontHelper.cardTitleFont_N, "Starting Deck: ", startingCardsLabelHb.x, startingCardsLabelHb.cY, Settings.GOLD_COLOR);
       
        FontHelper.renderFont(sb, FontHelper.cardTitleFont_N, info.Name, startingCardsSelectedHb.x, startingCardsSelectedHb.cY, Settings.CREAM_COLOR);//.BLUE_TEXT_COLOR);

        if (!startingCardsLeftHb.hovered)
        {
            sb.setColor(Color.LIGHT_GRAY);
        }
        else
        {
            sb.setColor(Color.WHITE);
        }
        sb.draw(ImageMaster.CF_LEFT_ARROW, startingCardsLeftHb.cX - 24.0F, startingCardsLeftHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);

        if (!startingCardsRightHb.hovered)
        {
            sb.setColor(Color.LIGHT_GRAY);
        }
        else
        {
            sb.setColor(Color.WHITE);
        }
        sb.draw(ImageMaster.CF_RIGHT_ARROW, startingCardsRightHb.cX - 24.0F, startingCardsRightHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);

        startingCardsLabelHb.render(sb);
        startingCardsLeftHb.render(sb);
        startingCardsRightHb.render(sb);
    }

    private static void UpdateSelectedCharacter(CharacterSelectScreen selectScreen)
    {
        CharacterOption current = selectedOption;
        selectedOption = null;
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

                    selectedOption = o;
                }

                return;
            }
        }
    }

    private static void RefreshLoadout(CharacterSelectScreen selectScreen, CharacterOption option)
    {
    	DuelistCharacterSelect.refreshCharacterDecks();
        int currentTotalScore = UnlockTracker.getCurrentScoreTotal(TheDuelistEnum.THE_DUELIST);
        DuelistCharacterSelect.GetSelectedLoadout().Refresh(currentTotalScore, selectScreen, option);
    }
}
