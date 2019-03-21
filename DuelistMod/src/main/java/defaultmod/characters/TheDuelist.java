package defaultmod.characters;

import java.util.ArrayList;

import org.apache.logging.log4j.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import defaultmod.DefaultMod;
import defaultmod.cards.*;
import defaultmod.patches.AbstractCardEnum;
import defaultmod.relics.MillenniumPuzzle;


public class TheDuelist extends CustomPlayer {
	public static final Logger logger = LogManager.getLogger(DefaultMod.class.getName());

	// =============== BASE STATS =================
	public static final int ENERGY_PER_TURN = DefaultMod.energyPerTurn;
	public static final int STARTING_HP = DefaultMod.startHP;
	public static final int MAX_HP = DefaultMod.maxHP;
	public static final int STARTING_GOLD = DefaultMod.startGold;
	public static final int CARD_DRAW = DefaultMod.cardDraw;
	public static final int ORB_SLOTS = DefaultMod.orbSlots;
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
				"defaultModResources/images/char/defaultCharacter/orb/layer1.png",
				"defaultModResources/images/char/defaultCharacter/orb/layer2.png",
				"defaultModResources/images/char/defaultCharacter/orb/layer3.png",
				//"defaultModResources/images/char/defaultCharacter/orb/layer4.png",
				//"defaultModResources/images/char/defaultCharacter/orb/layer5.png",
				"defaultModResources/images/char/defaultCharacter/orb/layer4B.png",
				"defaultModResources/images/char/defaultCharacter/orb/layer5B.png",
				"defaultModResources/images/char/defaultCharacter/orb/layer6.png",
				"defaultModResources/images/char/defaultCharacter/orb/layer1d.png",
				"defaultModResources/images/char/defaultCharacter/orb/layer2d.png",
				"defaultModResources/images/char/defaultCharacter/orb/layer3d.png",
				"defaultModResources/images/char/defaultCharacter/orb/layer4d.png",
				"defaultModResources/images/char/defaultCharacter/orb/layer5d.png", 
		};
	// =============== /TEXTURES OF BIG ENERGY ORB/ ===============


	// =============== CHARACTER CLASS START =================

	public TheDuelist(String name, PlayerClass setClass) 
	{
		super(name, setClass, orbTextures,
				//"defaultModResources/images/char/defaultCharacter/orb/vfx.png", null,
				"defaultModResources/images/char/defaultCharacter/orb/vfxB.png", null,
				new SpriterAnimation(
						"defaultModResources/images/char/duelistCharacterUpdate/YugiB.scml"));
						//"defaultModResources/images/char/duelistCharacter/theDuelistAnimation.scml"));
						//"defaultModResources/images/char/defaultCharacter/Spriter/theDefaultAnimation.scml"));

		// =============== TEXTURES, ENERGY, LOADOUT =================  

		initializeClass(null, // required call to load textures and setup energy/loadout
				DefaultMod.makePath(DefaultMod.THE_DEFAULT_SHOULDER_1), // campfire pose
				DefaultMod.makePath(DefaultMod.THE_DEFAULT_SHOULDER_2), // another campfire pose
				DefaultMod.makePath(DefaultMod.THE_DEFAULT_CORPSE), // dead corpse
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
	
		
		//  Toon Deck -> REPLACE RANDOM SOLDIER WITH NEW CARD x1 : Toon block card
		
		/*
		 * ORB DECK v2 (12 cards)
		 * 
		 * Machine King x1
		 * Snow Dragon x2
		 * 7-Colored Fish x2
		 * Legendary Fisherman x1
		 * Relinquished x1 
		 * Sanga Thunder x1
		 * Prevent Rat x2
		 * Fiend Megacyber x1
		 * Icy Crevasse x1
		 * 
		 * 
		 */
		
		/*
		 * RESUMMON DECK (10 cards)
		 * 
		 * Armored Zombie x2
		 * Pumprincess x1
		 * Shallow Grave x1
		 * Judge Man x1
		 * Double Coston x2
		 * King Yami x2
		 * Terra Terrible x1
		 * 
		 * 
		 */
		
		
		/*
		 * GENERATION DECK (12 cards)
		 * 
		 * Ojama Yellow x1
		 * Monster Egg x2
		 * Random Soldier x2   						 REPLACE WITH NEW CARD x2 : Gain some block for cheap, with a random generation effect
		 * Red Medicine x1
		 * Hinotoma x1
		 * Card Destruction x1
		 * Book Secret x1
		 * Wiretap x1
		 * Mini-Polymerization x1
		 * Time Wizard x1
		 * 
		 */
		
		/*
		 * OJAMA DECK (12 cards)
		 * 
		 * Ojama Yellow x2
		 * Ojama Green x2
		 * Ojama Black x2
		 * Ojama Knight x1
		 * Ojama King x1
		 * Ojamagic x1
		 * Ojamuscle x1
		 * Random Soldier x2						 REPLACE WITH NEW CARD x2 : Gain some block for cheap, with a random Ojama-type effect
		 * 
		 * 
		 * 
		 */
		
		/*
		 * HEAL DECK (9 cards)
		 * 
		 * Bad Reaction x1
		 * Darklord Marie x2
		 * Curse of Dragon x1
		 * Injection Fairy x1
		 * Gemini Elf x1
		 * Giant Soldier x2
		 * Guardian Angel x1
		 * 
		 * 
		 * 
		 */
		
		/*
		 * INCREMENT DECK (14 cards)
		 * 
		 * Fissure x2 
		 * Spirit Harp x2
		 * Hammer Shot x1
		 * Celtic Guardian x2
		 * Golden Apples x2
		 * Sphere Kuriboh x1
		 * ?? x2 : Increment 1. Block 8. [1 mana]
		 * ?? x2 : Summon 1. Increment 1. Deal 4 damage. [1 mana]
		 * ?? x1 : Increment (1-3). Exhaust. [0 mana]
		 * 
		 */
		
		/*
		 * EXODIA DECK (60 cards)
		 * 
		 * Graceful Charity x1
		 * Exodia RL x1
		 * Exodia LL x1
		 * Exodia RA x1
		 * Exodia LA x1
		 * Exodia Head x1
		 * Dark Magician x2
		 * Celtic Guardian x2
		 * Time Wizard x2
		 * R. Eyes Black Dragon x1
		 * Blackland Fire Dragon x1
		 * Baby Dragon x2
		 * Dark Magician Girl x2
		 * Change of Heart x2
		 * Swords of Revealing Light x1
		 * Pot Greed x1
		 * Pot Forbidden x1
		 * Card Destruction x1
		 * Castle Walls x4
		 * Giant Soldier x4
		 * Summoned Skull x2
		 * Insect Knight x1
		 * Terraforming x1
		 * Seven Colored x4
		 * Scrap Factory x1
		 * Cheerful Coffin x2
		 * The Creator x1
		 * Fissure x2
		 * Smashing Ground x1
		 * Hammer Shot x2
		 * Spirit Harp x2
		 * Mirror Force x1
		 * Trap Hole x1
		 * Ultimate Offering x1
		 * Magic Cylinder x1
		 * Giant Trunade x2
		 * Hane Hane x1
		 * Polymerization x1
		 * Trihorned Dragon x1
		 * 
		 */
		
		
		/*
		 * SUPERHEAVY DECK (12 cards)
		 * 
		 * Superheavy Scales x4 
		 * Superheavy Brawler x1
		 * Superheavy Magnet x1
		 * A. Magnet x1
		 * B. Magnet x1
		 * ?? x4 : Superheavy that just deals damage normally and summons 1
		 * 
		 */
		
		/*
		 * AQUA DECK (10 cards)
		 * 
		 * Island Turtle x2
		 * Seven Colored Fish x3
		 * Legendary Fisherman x1
		 * Revival Jam x1
		 * Oh Fish x1
		 * Umi x1
		 * ?? x1 : Aqua that tributes 2 and deals damage + block, more if you have all aquas
		 * 
		 */
		
		/*
		 * MACHINE DECK (12 cards)
		 * 
		 * Cannon Soldier x1
		 * Machine King x1
		 * Time Wizard x1
		 * Cyber Dragon x1
		 * Jam Breeding x1
		 * Machine Factory x1
		 * Metal Dragon x1
		 * Scrap Factory x2
		 * ?? x3 : Summon 1. NL Gain !B! Block. (6 block, 1 mana, machine)
		 * 
		 * 
		 */
		
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
		CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
				false); // Screen Effect
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
		if (DefaultMod.challengeMode)
		{
			return 40;
		}
		else
		{
			return 5;
		}
	}

	// Should return the card color enum to be associated with your character.
	@Override
	public AbstractCard.CardColor getCardColor() {
		return AbstractCardEnum.DUELIST_MONSTERS;
	}

	// Should return a color object to be used to color the trail of moving cards
	@Override
	public Color getCardTrailColor() {
		return DefaultMod.DEFAULT_GRAY;
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
		return DefaultMod.DEFAULT_PURPLE;
	}

	// Should return a Color object to be used as screen tint effect when your
	// character attacks the heart.
	@Override
	public Color getSlashAttackColor() {
		return DefaultMod.DEFAULT_PURPLE;
	}

	// Should return an AttackEffect array of any size greater than 0. These effects
	// will be played in sequence as your character's finishing combo on the heart.
	// Attack effects are the same as used in DamageAction and the like.
	@Override
	public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
		return new AbstractGameAction.AttackEffect[] {
				AbstractGameAction.AttackEffect.BLUNT_HEAVY };
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

	/*
	@Override
	public CardGroup getArchetypeSelectionCardsPool() 
	{
		return theDuelistArchetypeSelectionCards;
	}

	@Override
	public int numberOfDefaultArchetypes() {
		return numberOfArchetypes;
	}
	*/

}
