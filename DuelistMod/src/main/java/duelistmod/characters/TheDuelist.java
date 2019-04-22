package duelistmod.characters;

import java.util.*;

import org.apache.logging.log4j.*;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import duelistmod.*;
import duelistmod.Colors;
import duelistmod.cards.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.relics.MillenniumPuzzle;


public class TheDuelist extends CustomPlayer {
	public static final Logger logger = LogManager.getLogger(DuelistMod.class.getName());

	// =============== BASE STATS =================
	public static final int ENERGY_PER_TURN = DuelistMod.energyPerTurn;
	public static final int STARTING_HP = DuelistMod.startHP;
	public static final int MAX_HP = DuelistMod.maxHP;
	public static final int STARTING_GOLD = DuelistMod.startGold;
	public static final int CARD_DRAW = DuelistMod.cardDraw;
	public static final int ORB_SLOTS = DuelistMod.orbSlots;
	public static final int numberOfArchetypes = 17;
	public static CardGroup theDuelistArchetypeSelectionCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
	private static final CharacterStrings charStrings;
	public static final String NAME;
	public static final String[] DESCRIPTIONS;
	// =============== /BASE STATS/ =================
	
	static 
	{
        charStrings = CardCrawlGame.languagePack.getCharacterString("Duelist");
        NAME = charStrings.NAMES[0];
        DESCRIPTIONS = charStrings.TEXT;
    }

	// =============== TEXTURES OF BIG ENERGY ORB ===============
	public static final String[] orbTextures = 
		{	
				"duelistModResources/images/char/defaultCharacter/orb/layer1.png",
				"duelistModResources/images/char/defaultCharacter/orb/layer2.png",
				"duelistModResources/images/char/defaultCharacter/orb/layer3.png",
				//"duelistModResources/images/char/defaultCharacter/orb/layer4.png",
				//"duelistModResources/images/char/defaultCharacter/orb/layer5.png",
				"duelistModResources/images/char/defaultCharacter/orb/layer4B.png",
				"duelistModResources/images/char/defaultCharacter/orb/layer5B.png",
				"duelistModResources/images/char/defaultCharacter/orb/layer6.png",
				"duelistModResources/images/char/defaultCharacter/orb/layer1d.png",
				"duelistModResources/images/char/defaultCharacter/orb/layer2d.png",
				"duelistModResources/images/char/defaultCharacter/orb/layer3d.png",
				"duelistModResources/images/char/defaultCharacter/orb/layer4d.png",
				"duelistModResources/images/char/defaultCharacter/orb/layer5d.png", 
		};
	// =============== /TEXTURES OF BIG ENERGY ORB/ ===============


	// =============== CHARACTER CLASS START =================

	public TheDuelist(String name, PlayerClass setClass) 
	{
		super(name, setClass, orbTextures,
				//"duelistModResources/images/char/defaultCharacter/orb/vfx.png", null,
				"duelistModResources/images/char/defaultCharacter/orb/vfxB.png", null,
				new SpriterAnimation(DuelistMod.characterModel));
						//"duelistModResources/images/char/duelistCharacterUpdate/YugiB.scml"));
						//"duelistModResources/images/char/duelistCharacter/theDuelistAnimation.scml"));
						//"duelistModResources/images/char/defaultCharacter/Spriter/theDefaultAnimation.scml"));

		// =============== TEXTURES, ENERGY, LOADOUT =================  

		initializeClass(null, // required call to load textures and setup energy/loadout
				DuelistMod.makePath(Strings.THE_DEFAULT_SHOULDER_1), // campfire pose
				DuelistMod.makePath(Strings.THE_DEFAULT_SHOULDER_2), // another campfire pose
				DuelistMod.makePath(Strings.THE_DEFAULT_CORPSE), // dead corpse
				getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager

		// =============== /TEXTURES, ENERGY, LOADOUT/ =================


		// =============== ANIMATIONS =================  

		//this.loadAnimation(
		//		DefaultMod.makePath(DefaultMod.THE_DEFAULT_SKELETON_ATLAS),
		//		DefaultMod.makePath(DefaultMod.THE_DEFAULT_SKELETON_JSON),
		//		1.0f);
		//AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
		//e.setTime(e.getEndTime() * MathUtils.random());

		// =============== /ANIMATIONS/ =================


		// =============== TEXT BUBBLE LOCATION =================

		this.dialogX = (this.drawX + 0.0F * Settings.scale); // set location for text bubbles
		this.dialogY = (this.drawY + 220.0F * Settings.scale); // you can just copy these values

		// =============== /TEXT BUBBLE LOCATION/ =================

	}

	// =============== /CHARACTER CLASS END/ =================


	// Starting description and loadout
	@Override
	public CharSelectInfo getLoadout() {
		return new CharSelectInfo(NAME,
				DESCRIPTIONS[0],
				STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
				getStartingDeck(), false);
	}

	// Starting Deck
	@Override
	public ArrayList<String> getStartingDeck() {
		ArrayList<String> retVal = new ArrayList<>();

		logger.info("Begin loading starter Deck Strings");

		// Standard Deck (10 cards)
		retVal.add(SevenColoredFish.ID);
		retVal.add(SevenColoredFish.ID);
		retVal.add(GiantSoldier.ID);
		retVal.add(GiantSoldier.ID);
		retVal.add(CastleWalls.ID);
		retVal.add(CastleWalls.ID);
		retVal.add(ScrapFactory.ID);
		retVal.add(Ookazi.ID);
		retVal.add(Ookazi.ID);
		retVal.add(SummonedSkull.ID);

		return retVal;
	}

	// Starting Relics	
	@Override
	public ArrayList<String> getStartingRelics() 
	{
		ArrayList<String> retVal = new ArrayList<>();
		retVal.add(MillenniumPuzzle.ID);
		UnlockTracker.markRelicAsSeen(MillenniumPuzzle.ID);
		return retVal;
	}

	// Character Select screen effect
	@Override
	public void doCharSelectScreenSelectEffect() {
		CardCrawlGame.sound.playA("ATTACK_DAGGER_1", 1.25f); // Sound Effect
		CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, false); // Screen Effect
	}

	// Character Select on-button-press sound effect
	@Override
	public String getCustomModeCharacterButtonSoundKey() {
		return "ATTACK_DAGGER_1";
	}

	// Should return how much HP your maximum HP reduces by when starting a run at
	// Ascension 14 or higher. (ironclad loses 5, defect and silent lose 4 hp respectively)
	@Override
	public int getAscensionMaxHPLoss() 
	{
		if (DuelistMod.challengeMode) { return 25; }
		else { return 5;	}
	}

	// Should return the card color enum to be associated with your character.
	@Override
	public AbstractCard.CardColor getCardColor() {
		return AbstractCardEnum.DUELIST;
	}

	// Should return a color object to be used to color the trail of moving cards
	@Override
	public Color getCardTrailColor() {
		return Colors.DEFAULT_GRAY;
	}

	// Should return a BitmapFont object that you can use to customize how your
	// energy is displayed from within the energy orb.
	@Override
	public BitmapFont getEnergyNumFont() {
		return FontHelper.energyNumFontRed;
	}

	// Should return class name as it appears in run history screen.
	@Override
	public String getLocalizedCharacterName() 
	{
		return NAME;
	}

	//Which card should be obtainable from the Match and Keep event?
	@Override
	public AbstractCard getStartCardForEvent() {
		return new SevenColoredFish();
	}

	// The class name as it appears next to your player name in-game
	@Override
	public String getTitle(AbstractPlayer.PlayerClass playerClass) {
		return DESCRIPTIONS[1];
	}

	// Should return a new instance of your character, sending this.name as its name parameter.
	@Override
	public AbstractPlayer newInstance() {
		return new TheDuelist(this.name, this.chosenClass);
	}

	// Should return a Color object to be used to color the miniature card images in run history.
	@Override
	public Color getCardRenderColor() {
		return Colors.DEFAULT_PURPLE;
	}

	// Should return a Color object to be used as screen tint effect when your
	// character attacks the heart.
	@Override
	public Color getSlashAttackColor() {
		return Colors.DEFAULT_PURPLE;
	}

	// Should return an AttackEffect array of any size greater than 0. These effects
	// will be played in sequence as your character's finishing combo on the heart.
	// Attack effects are the same as used in DamageAction and the like.
	@Override
	public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
		return new AbstractGameAction.AttackEffect[] {
				AbstractGameAction.AttackEffect.FIRE };
	}

	// Should return a string containing what text is shown when your character is
	// about to attack the heart. For example, the defect is "NL You charge your
	// core to its maximum..."
	@Override
	public String getSpireHeartText() {
		return DESCRIPTIONS[2];
	}

	// The vampire events refer to the base game characters as "brother", "sister",
	// and "broken one" respectively.This method should return a String containing
	// the full text that will be displayed as the first screen of the vampires event.
	@Override
	public String getVampireText() {
		return DESCRIPTIONS[3];
	}

	@Override
	public List<CutscenePanel> getCutscenePanels() {
		List<CutscenePanel> panels = new ArrayList<CutscenePanel>();
		panels.add(new CutscenePanel(DuelistMod.makePath("cutscenes/duelist1.png"), "ATTACK_HEAVY"));
		panels.add(new CutscenePanel(DuelistMod.makePath("cutscenes/duelist2.png")));
		panels.add(new CutscenePanel(DuelistMod.makePath("cutscenes/duelist3.png")));
		return panels;
	}

	public static Texture GetCharacterPortrait(int id)
	{
	    Texture result;
	    if (!DuelistMod.characterPortraits.containsKey(id))
	    {
	        result = new Texture(DuelistMod.makePath("charSelect/duelist_portrait_" + id + ".png"));
	        DuelistMod.characterPortraits.put(id, result);
	    }
	    else
	    {
	        result = DuelistMod.characterPortraits.get(id);
	    }
	
	    return result;
	}
}
