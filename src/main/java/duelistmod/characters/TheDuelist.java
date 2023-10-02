package duelistmod.characters;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import basemod.animations.AbstractAnimation;
import basemod.animations.SpineAnimation;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;
import com.evacipated.cardcrawl.mod.stslib.relics.OnLoseTempHpRelic;
import com.evacipated.cardcrawl.mod.stslib.vfx.combat.TempDamageNumberEffect;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.combat.BlockedWordEffect;
import com.megacrit.cardcrawl.vfx.combat.DamageImpactLineEffect;
import com.megacrit.cardcrawl.vfx.combat.HbBlockBrokenEffect;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;
import duelistmod.cards.curses.CurseRoyal;
import duelistmod.dto.AnyDuelist;
import duelistmod.enums.CardPoolType;
import duelistmod.enums.DeathType;
import duelistmod.enums.StartingDeck;
import duelistmod.potions.MillenniumElixir;
import duelistmod.powers.SummonPower;
import duelistmod.ui.gameOver.DuelistDeathScreen;
import org.apache.logging.log4j.*;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.blue.GeneticAlgorithm;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import basemod.BaseMod;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.actions.utility.DuelistUseCardAction;
import duelistmod.cards.*;
import duelistmod.cards.incomplete.CircleFireKings;
import duelistmod.cards.other.tempCards.CancelCard;
import duelistmod.cards.pools.aqua.SevenColoredFish;
import duelistmod.cards.pools.dragons.*;
import duelistmod.cards.pools.insects.Taotie;
import duelistmod.cards.pools.machine.ScrapFactory;
import duelistmod.helpers.*;
import duelistmod.helpers.poolhelpers.GlobalPoolHelper;
import duelistmod.orbs.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.relics.*;
import duelistmod.variables.*;
import duelistmod.variables.Colors;

import static com.esotericsoftware.spine.AnimationState.*;


public class TheDuelist extends CustomPlayer {
	public static final Logger logger = LogManager.getLogger(DuelistMod.class.getName());

	// =============== BASE STATS =================
	public static final int ENERGY_PER_TURN = DuelistMod.energyPerTurn;
	public static final int STARTING_HP = DuelistMod.startHP;
	public static final int MAX_HP = DuelistMod.maxHP;
	public static final int STARTING_GOLD = DuelistMod.startGold;
	public static final int CARD_DRAW = DuelistMod.cardDraw;
	public static CardGroup resummonPile = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
	public static CardGroup cardPool = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
	public static DuelistSoulGroup duelistSouls = new DuelistSoulGroup(null);
	private static final CharacterStrings charStrings;
	public static final String NAME;
	public static final String[] DESCRIPTIONS;
	public static TrackEntry currentAnimation;
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
		super(name, setClass, orbTextures,"duelistModResources/images/char/defaultCharacter/orb/vfxB.png", null, getPlayerModel());

		// =============== TEXTURES, ENERGY, LOADOUT =================

		initializeClass(null, // required call to load textures and setup energy/loadout
				DuelistMod.makePath(Strings.THE_DEFAULT_SHOULDER_1), // campfire pose
				DuelistMod.makePath(Strings.THE_DEFAULT_SHOULDER_2), // another campfire pose
				DuelistMod.makePath(Strings.THE_DEFAULT_CORPSE), // dead corpse
				getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager

		// =============== /TEXTURES, ENERGY, LOADOUT/ =================


		// =============== ANIMATIONS =================

		if (DuelistMod.selectedCharacterModelAnimationName != null) {
			currentAnimation = this.state.setAnimation(0, DuelistMod.selectedCharacterModelAnimationName, true);
			currentAnimation.setTimeScale(DuelistMod.persistentDuelistData.VisualSettings.getAnimationSpeed());
		} else {
			currentAnimation = null;
		}

		// =============== /ANIMATIONS/ =================


		// =============== TEXT BUBBLE LOCATION =================

		this.dialogX = (this.drawX + 0.0F * Settings.scale); // set location for text bubbles
		this.dialogY = (this.drawY + 220.0F * Settings.scale); // you can just copy these values

		// =============== /TEXT BUBBLE LOCATION/ =================

	}

	private static AbstractAnimation getPlayerModel() {
		String animBasePath = "duelistModResources/images/char/duelistCharacter/Spine/";
		String basePath;
		switch (DuelistMod.selectedCharacterModel) {
			case ANIM_YUGI:
				DuelistMod.selectedCharacterModelAnimationName = "animation";
				basePath = animBasePath+"yugi/";
				return new SpineAnimation(
						basePath+"nyoxide.atlas",
						basePath+"nyoxide.json",
						8.5f);
			case ANIM_KAIBA:
				DuelistMod.selectedCharacterModelAnimationName = "idle";
				basePath = animBasePath+"kaiba/";
				return new SpineAnimation(
						basePath+"nyoxide_seto akiba.atlas",
						basePath+"nyoxide_seto akiba.json",
						9.5f);
			case STATIC_KAIBA:
				DuelistMod.characterModel = DuelistMod.kaibaPlayerModel;
				break;
			case STATIC_YUGI_OLD:
				DuelistMod.characterModel = DuelistMod.oldYugiChar;
				break;
			case STATIC_YUGI_NEW:
				DuelistMod.characterModel = DuelistMod.yugiChar;
				break;
		}
		DuelistMod.selectedCharacterModelAnimationName = null;
		return new SpriterAnimation(DuelistMod.characterModel);
	}

	public static void setAnimationSpeed(Float speed) {
		if (currentAnimation == null) return;

		if (speed == null) {
			currentAnimation.setTimeScale(DuelistMod.persistentDuelistData.VisualSettings.getAnimationSpeed());
			return;
		}
		currentAnimation.setTimeScale(speed);
	}

	@Override
	public void applyEndOfTurnTriggers() {
		super.applyEndOfTurnTriggers();
		setAnimationSpeed(null);
		DuelistMod.unblockedDamageTakenLastTurn = DuelistMod.unblockedDamageTakenThisTurn;
		DuelistMod.unblockedDamageTakenThisTurn = false;

		DuelistMod.beastsDrawnByTurn.add(DuelistMod.beastsDrawnThisTurn);
		DuelistMod.enemyBeastsDrawnByTurn.add(DuelistMod.enemyBeastsDrawnThisTurn);
		DuelistMod.beastsDrawnThisTurn = 0;
		DuelistMod.enemyBeastsDrawnThisTurn = 0;
		DuelistMod.uniqueBeastsPlayedThisTurn.clear();
	}

	// =============== /CHARACTER CLASS END/ =================

	@Override
	public void applyStartOfTurnPostDrawRelics() {
		PuzzleHelper.runStartOfBattlePostDrawEffects();
		if (DuelistMod.drawExtraCardsAtTurnStart > 0) {
			AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, DuelistMod.drawExtraCardsAtTurnStart));
		}
		if (DuelistMod.drawExtraCardsAtTurnStartThisBattle > 0) {
			AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, DuelistMod.drawExtraCardsAtTurnStartThisBattle));
		}
		super.applyStartOfTurnPostDrawRelics();
	}

	@Override
	public void applyStartOfTurnPostDrawPowers()
	{
		super.applyStartOfTurnPostDrawPowers();
		if (this.hasOrb())
		{
			for (AbstractOrb o : this.orbs)
			{
				if (o instanceof Black)
				{
					Black b = (Black)o;
					if (b.passiveAmount > 0) { b.triggerPassiveEffect(); }
					if (b.gpcCheck() && b.passiveAmount > 0) { b.triggerPassiveEffect(); }
				}
			}
		}
	}

	@Override
	public void switchedStance() {
		super.switchedStance();
		AbstractPlayer p = AbstractDungeon.player;
		for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  ((DuelistOrb)o).onChangeStance(); }}
		for (AbstractPotion ppopt : this.potions) { if (ppopt instanceof DuelistPotion) { ((DuelistPotion)ppopt).onChangeStance(); }}
	}

	// Starting description and loadout
	@Override
	public CharSelectInfo getLoadout()
	{
		return new CharSelectInfo(NAME,DESCRIPTIONS[0],
				STARTING_HP,
				MAX_HP, getOrbSlots(), STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
				getStartingDeck(), false);
	}

	private int getOrbSlots()
	{
		int amt = 3 + DuelistMod.persistentDuelistData.GameplaySettings.getOrbSlots();
		if (Util.deckIs("Spellcaster Deck") && Util.getChallengeLevel() > 3) { amt--; }
		return amt;
	}

	// Starting Deck
	@Override
	public ArrayList<String> getStartingDeck() {
		ArrayList<String> retVal = new ArrayList<>();

		logger.info("Begin loading starter Deck Strings");

		// The original standard deck from the earliest version of the mod
		// Deprecated, deck is filled in DuelistMod with PostCreateStartingDeckSubscriber
		retVal.add(SevenColoredFish.ID);
		retVal.add(SevenColoredFish.ID);
		retVal.add(GiantSoldier.ID);
		retVal.add(GiantSoldier.ID);
		retVal.add(PowerWall.ID);
		retVal.add(PowerWall.ID);
		retVal.add(ScrapFactory.ID);
		retVal.add(Ookazi.ID);
		retVal.add(Ookazi.ID);
		retVal.add(SummonedSkull.ID);

		return retVal;
	}

	@Override
	public void renderPlayerImage(SpriteBatch sb) {
		switch (animation.type()) {
			case NONE:
				super.renderPlayerImage(sb);
				break;
			case MODEL:
				BaseMod.publishAnimationRender(sb);
				break;
			case SPRITE:
				animation.setFlip(flipHorizontal, flipVertical);
				animation.renderSprite(sb, drawX + animX, drawY + animY);
				break;
		}
	}

	// Card Pool Patch
	@Override
	public ArrayList<AbstractCard> getCardPool(ArrayList<AbstractCard> tmpPool)
	{
		cardPool.group.clear();
		Map<String, String> names = new HashMap<>();
		tmpPool = super.getCardPool(tmpPool);


		// Holiday card handler
		ArrayList<AbstractCard> holiday = DuelistMod.persistentDuelistData.GameplaySettings.getHolidayCards() ? Util.holidayCardRandomizedList() : new ArrayList<>();
		if (holiday.size() > 0)
		{
			DuelistMod.holidayDeckCard = holiday.get(0);
			DuelistMod.addingHolidayCard = true;
			for (int i = 1; i < holiday.size(); i++) { DuelistMod.holidayNonDeckCards.add(holiday.get(i)); }
		}
		// END Holiday card handler


		// Card Pool Removal Relic - replaces pool with the pool, except for the selected cards for removal
		// So we just replace the pool with the list of cards we generated with the relic
		if (DuelistMod.shouldReplacePool && DuelistMod.toReplacePoolWith.size() > 0)
		{
			DuelistMod.coloredCards.clear();
			for (AbstractCard c : DuelistMod.toReplacePoolWith)
			{
				if (!c.rarity.equals(CardRarity.SPECIAL) && !c.rarity.equals(CardRarity.BASIC))
				{
					c.unhover();
					c.stopGlowing();
					if (!names.containsKey(c.originalName))
					{
						tmpPool.add(c);
						cardPool.addToBottom(c);
						DuelistMod.coloredCards.add(c);
						names.put(c.originalName, c.originalName);
					}
					else if (names.containsKey(c.originalName)) { Util.log("Skipped adding " + c.originalName + " to main card pool, since it has already been added"); }
				}
			}

			if (DuelistMod.replacingOnUpdate)
			{
				Util.log("We are replacing the card pool with a card pool loaded from config");
			}
			else
			{
				Util.log("We are replacing the card pool due to Card Pool Relic modifications made by the player");
			}

			//try { if ((DuelistMod.alwaysBoosters || DuelistMod.allowBoosters) && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrMapNode() != null && !DuelistMod.replacingOnUpdate) { BoosterHelper.refreshPool(); }} catch (IllegalArgumentException e) { e.printStackTrace(); }
			if (!DuelistMod.replacingOnUpdate) { DuelistMod.replacedCardPool = true; }
			DuelistMod.shouldReplacePool = false;
			DuelistMod.toReplacePoolWith.clear();
		}

		// Otherwise, we are either:
			// Filling the pool for the first time
			// Adding cards to the pool with the Card Pool Add relic
			// Possibly doing nothing, if the card pools are being reinitialized outside of the above two scenarios and shouldFill is false
		else
		{
			if (DuelistMod.coloredCards.size() == 0)
			{
				Util.log("colored cards was 0! This check detected that.");
				Util.log("filling card pool based on deck: " + StartingDeck.currentDeck.getDeckName());
				//Util.log("filling card pool and deckIndex=" + DuelistMod.deckIndex);
				GlobalPoolHelper.setGlobalDeckFlags(StartingDeck.currentDeck.getDeckName());
				PoolHelpers.newFillColored(StartingDeck.currentDeck.getDeckName());
			}
			else { PoolHelpers.coloredCardsHadCards(); }
			for (AbstractCard c : DuelistMod.coloredCards)
			{
				if (!c.rarity.equals(CardRarity.SPECIAL) && !names.containsKey(c.originalName) && !c.rarity.equals(CardRarity.BASIC))
				{
					tmpPool.add(c);
					cardPool.addToBottom(c);
					names.put(c.originalName, c.originalName);
				}
				else if (names.containsKey(c.originalName)) { Util.log("Skipped adding " + c.originalName + " to main card pool, since it has already been added"); }
			}
		}

		// Filling in colorless pool with Basic set, if fill type allows it
		if (DuelistMod.cardPoolType.includesBasic() && DuelistMod.cardPoolType != CardPoolType.BASIC_ONLY)
		{
			int counter = 1;
			for (AbstractCard c : DuelistMod.duelColorlessCards)
			{
				if (!c.rarity.equals(CardRarity.SPECIAL) && !names.containsKey(c.originalName) && !c.rarity.equals(CardRarity.BASIC))
				{
					Util.log("Basic Set - Colorless Pool: [" + counter + "]: " + c.name);
					if (c.rarity.equals(CardRarity.COMMON))
					{
						Util.log("Found common card in colorless pool! Card:" + c.name);
						//c.rarity = CardRarity.UNCOMMON;
						//c.initializeDescription();
					}
					AbstractDungeon.colorlessCardPool.group.add(c);
					names.put(c.originalName, c.originalName);
					counter++;
				}
				else if (names.containsKey(c.originalName)) { Util.log("Skipped adding " + c.originalName + " to colorless card set, since it was in the main pool already"); }
			}

			if (DuelistMod.holidayNonDeckCards.size() > 0)
			{
				for (AbstractCard c : DuelistMod.holidayNonDeckCards)
				{
					if (!c.rarity.equals(CardRarity.SPECIAL) && !names.containsKey(c.originalName) && !c.rarity.equals(CardRarity.BASIC))
					{
						Util.log("Basic Set - Colorless Pool: [" + counter + "]: " + c.name + " [HOLIDAY CARD]");
						AbstractDungeon.colorlessCardPool.group.add(c);
						names.put(c.originalName, c.originalName);
						counter++;
					}
					else if (names.containsKey(c.originalName)) { Util.log("Skipped adding " + c.originalName + " to colorless card set, since it was in the main pool already"); }
				}
			}
		}


		// Reset the card pool for all the Card Pool relics
		//if (this.hasRelic(MillenniumPuzzle.ID)) { MillenniumPuzzle puz = (MillenniumPuzzle)this.getRelic(MillenniumPuzzle.ID); puz.getDeckDesc(); }
		if (this.hasRelic(CardPoolRelic.ID)) { ((CardPoolRelic)this.getRelic(CardPoolRelic.ID)).refreshPool(); }
		if (this.hasRelic(CardPoolBasicRelic.ID)) { ((CardPoolBasicRelic)this.getRelic(CardPoolBasicRelic.ID)).refreshPool(); }
		if (this.hasRelic(CardPoolAddRelic.ID)) { ((CardPoolAddRelic)this.getRelic(CardPoolAddRelic.ID)).refreshPool(); }
		if (this.hasRelic(CardPoolMinusRelic.ID)) { ((CardPoolMinusRelic)this.getRelic(CardPoolMinusRelic.ID)).refreshPool(); }
		if (this.hasRelic(BoosterPackPoolRelic.ID)) { ((BoosterPackPoolRelic)this.getRelic(BoosterPackPoolRelic.ID)).refreshPool(); }
		DuelistMod.dungeonCardPool.clear();
		Util.log("Duelist card pool size=" + cardPool.size());
		if (DuelistMod.checkedCardPool || DuelistMod.relicReplacement)
		{
			StringBuilder lastCardPool = new StringBuilder();
			for (AbstractCard c : cardPool.group) { lastCardPool.append(c.cardID).append("~"); DuelistMod.dungeonCardPool.put(c.cardID, c.name); }
			Util.log("Saving full string of card pool... string=" + lastCardPool);
			//DuelistMod.setupRunUUID();
			try {
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
				config.setString("fullCardPool", lastCardPool.toString());
				//config.setString(DuelistMod.PROP_RUN_UUID, DuelistMod.runUUID == null ? "" : DuelistMod.runUUID);
				config.save();
			} catch (Exception e) {
				e.printStackTrace();
			}
			DuelistMod.relicReplacement = false;
		}
		return tmpPool;
	}


	// Starting Relics
	@Override
	public ArrayList<String> getStartingRelics()
	{
		ArrayList<String> retVal = new ArrayList<>();

		// Always get Millennium Puzzle
		retVal.add(MillenniumPuzzle.ID);

		// Challenge Puzzle is handled dynamically by select screen
		if (DuelistMod.playingChallenge) {
			retVal.add(ChallengePuzzle.ID);
		}

		// If not playing Exodia Deck, allow player to customize card pool
		// Or if playing Exodia deck with obtain cards turned on
		Util.updateRelicListForSelectScreen(retVal);

		return retVal;
	}

	@Override
	public void onVictory() {
		super.onVictory();
		boolean eliteVictory = AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite;
		boolean boss = AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss;
		BoosterHelper.generateBoosterOnVictory(DuelistMod.lastPackRoll, eliteVictory, boss);
	}

	// Character Select screen effect
	@Override
	public void doCharSelectScreenSelectEffect()
	{
		StartingDeck.loadDeck(DuelistMod.characterSelectScreen);
		//Util.updateCharacterSelectScreenPuzzleDescription();
		int roll = ThreadLocalRandom.current().nextInt(1, 4);
		if (roll == 1) 		{ CardCrawlGame.sound.playV("theDuelist:TimeToDuelB", 0.5F);	}
		else if (roll == 2) { CardCrawlGame.sound.playV("theDuelist:TimeToDuel", 0.5F); 	}
		else if (roll == 3) { CardCrawlGame.sound.playA("ATTACK_DAGGER_1", 1.25f); 			}
		else 				{ CardCrawlGame.sound.playA("ATTACK_DAGGER_1", 1.25f); 			}
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
		return 10;
	}

	// Should return the card color enum to be associated with your character.
	@Override
	public AbstractCard.CardColor getCardColor() {
		return AbstractCardEnum.DUELIST;
	}

	// Should return a color object to be used to color the trail of moving cards
	@Override
	public Color getCardTrailColor() {
		return Colors.CARD_GRAY;
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
		return new GiantSoldier();
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
		return Colors.CARD_PURPLE;
	}

	// Should return a Color object to be used as screen tint effect when your
	// character attacks the heart.
	@Override
	public Color getSlashAttackColor() {
		return Colors.CARD_PURPLE;
	}

	// Should return an AttackEffect array of any size greater than 0. These effects
	// will be played in sequence as your character's finishing combo on the heart.
	// Attack effects are the same as used in DamageAction and the like.
	@Override
	public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
		return new AbstractGameAction.AttackEffect[]
				{
						AbstractGameAction.AttackEffect.FIRE,
						AbstractGameAction.AttackEffect.BLUNT_HEAVY,
						AbstractGameAction.AttackEffect.POISON,
						AbstractGameAction.AttackEffect.BLUNT_LIGHT,
						AbstractGameAction.AttackEffect.FIRE,
						AbstractGameAction.AttackEffect.SLASH_HEAVY
				};
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

	// Fills in the cut up image slideshow during the Heart victory animation sequence
	// I guess the first one plays a sound effect when the image appears on screen?
	// Idk this is copied from someone, Slimebound perhaps
	@Override
	public List<CutscenePanel> getCutscenePanels() {
		List<CutscenePanel> panels = new ArrayList<>();
		panels.add(new CutscenePanel(DuelistMod.makePath("cutscenes/duelist1.png"), "ATTACK_HEAVY"));
		panels.add(new CutscenePanel(DuelistMod.makePath("cutscenes/duelist2.png")));
		panels.add(new CutscenePanel(DuelistMod.makePath("cutscenes/duelist3.png")));
		return panels;
	}

	// Used to load images in the character select screen
	public static Texture GetCharacterPortrait(int id)
	{
	    Texture result = DuelistMod.characterPortraits.getOrDefault(id, null);
		if (result == null) {
			result = new Texture(DuelistMod.makePath("charSelect/duelist_portrait_" + id + ".png"));
			DuelistMod.characterPortraits.put(id, result);
		}
		return result;
	}

	@Override
	public String getPortraitImageName() {
		return null;
	}

	@Override
    public void useCard(final AbstractCard c, final AbstractMonster monster, final int energyOnUse)
	{
		if (c instanceof HiddenCard) {
			c.use(this, monster);
			return;
		}
		if (c.misc == 52 && !(c instanceof GeneticAlgorithm))
		{
			DuelistMod.lastCardResummoned = c;
			if (c.hasTag(Tags.MONSTER) && (DuelistMod.firstMonsterResummonedThisCombat == null || DuelistMod.firstMonsterResummonedThisCombat instanceof CancelCard))
			{
				DuelistMod.firstMonsterResummonedThisCombat = c.makeStatEquivalentCopy();
			}
			if ((DuelistMod.firstCardResummonedThisCombat == null || DuelistMod.firstCardResummonedThisCombat instanceof CancelCard))
			{
				DuelistMod.firstCardResummonedThisCombat = c.makeStatEquivalentCopy();
			}
			this.hand.glowCheck();
		}
		if (c.type == AbstractCard.CardType.ATTACK) {
	        this.useFastAttackAnimation();
	    }
	    c.calculateCardDamage(monster);
	    if (c.cost == -1 && EnergyPanel.totalCount < energyOnUse && !c.ignoreEnergyOnUse) {
	        c.energyOnUse = EnergyPanel.totalCount;
	    }
	    if (c.cost == -1 && c.isInAutoplay) {
	        c.freeToPlayOnce = true;
	    }
	    c.use(this, monster);
	    AbstractDungeon.actionManager.addToBottom(new DuelistUseCardAction(c, monster));
	    if (!c.dontTriggerOnUseCard) {
	        this.hand.triggerOnOtherCardPlayed(c);
	    }
		DuelistCard.handleOnEnemyPlayCardForAllAbstracts(c, AnyDuelist.from(this));
	    this.hand.removeCard(c);
	    this.cardInUse = c;
	    c.target_x = (float)(Settings.WIDTH / 2);
	    c.target_y = (float)(Settings.HEIGHT / 2);
	    if (c.costForTurn > 0 && !c.freeToPlay() && !c.isInAutoplay && (!this.hasPower("Corruption") || c.type != AbstractCard.CardType.SKILL)) {
	        this.energy.use(c.costForTurn);
	    }
	    if (!this.hand.canUseAnyCard() && !this.endTurnQueued) {
	        AbstractDungeon.overlayMenu.endTurnButton.isGlowing = true;
	    }
    }

	@Override
	public void applyStartOfTurnCards()
	{
		super.applyStartOfTurnCards();
		DuelistMod.onTurnStart();
	}

	@Override
	public void loseEnergy(int e) {
		super.loseEnergy(e);
		this.hand.glowCheck();
	}

	@Override
	public void releaseCard() {
		if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null) {
			super.releaseCard();
			for (final AbstractOrb o : this.orbs) {
				if (o instanceof DuelistOrb) {
					((DuelistOrb)o).hideInvertValues();
				}
			}
		}
	}

	@Override
	public void damage(DamageInfo info) {
		int damageAmount = info.output;
		boolean hadBlock = this.currentBlock != 0;
		if (damageAmount < 0) {
			damageAmount = 0;
		}
		if (damageAmount > 1 && this.hasPower("IntangiblePlayer")) {
			damageAmount = 1;
		}
		damageAmount = this.decrementBlock(info, damageAmount);

		boolean hadTempHP = false;
		boolean keepCheckingTempHp = damageAmount > 0;

		for (AbstractDamageModifier mod : DamageModifierManager.getDamageMods(info)) {
			if (mod.ignoresTempHP(this)) {
				keepCheckingTempHp = false;
				break;
			}
		}

		if (keepCheckingTempHp) {
			int temporaryHealth = TempHPField.tempHp.get(this);
			if (temporaryHealth > 0) {
				for (AbstractPower power : this.powers) {
					if (power instanceof OnLoseTempHpPower) {
						damageAmount = ((OnLoseTempHpPower) power).onLoseTempHp(info, damageAmount);
					}
				}
				for (AbstractRelic relic : this.relics) {
					if (relic instanceof OnLoseTempHpRelic) {
						damageAmount = ((OnLoseTempHpRelic) relic).onLoseTempHp(info, damageAmount);
					}
				}

				hadTempHP = true;
				for (int i = 0; i < 18; ++i) {
					AbstractDungeon.effectsQueue.add(new DamageImpactLineEffect(this.hb.cX, this.hb.cY));
				}
				CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
				if (temporaryHealth >= damageAmount) {
					temporaryHealth -= damageAmount;
					AbstractDungeon.effectsQueue.add(new TempDamageNumberEffect(this, this.hb.cX, this.hb.cY, damageAmount));
					damageAmount = 0;
				} else {
					damageAmount -= temporaryHealth;
					AbstractDungeon.effectsQueue.add(new TempDamageNumberEffect(this, this.hb.cX, this.hb.cY, temporaryHealth));
					temporaryHealth = 0;
				}

				TempHPField.tempHp.set(this, temporaryHealth);
			}
			//ReflectionHacks.setPrivateStatic(PlayerDamage.class, "hadTempHp", hadTempHP);
		}


		if (info.owner == this) {
			for (final AbstractRelic r : this.relics) {
				damageAmount = r.onAttackToChangeDamage(info, damageAmount);
			}
		}
		if (info.owner != null) {
			for (final AbstractPower p : info.owner.powers) {
				damageAmount = p.onAttackToChangeDamage(info, damageAmount);
			}
		}
		for (final AbstractRelic r : this.relics) {
			damageAmount = r.onAttackedToChangeDamage(info, damageAmount);
		}
		for (final AbstractPower p : this.powers) {
			damageAmount = p.onAttackedToChangeDamage(info, damageAmount);
		}
		if (info.owner == this) {
			for (final AbstractRelic r : this.relics) {
				r.onAttack(info, damageAmount, this);
			}
		}
		if (info.owner != null) {
			for (final AbstractPower p : info.owner.powers) {
				p.onAttack(info, damageAmount, this);
			}
			for (final AbstractPower p : this.powers) {
				damageAmount = p.onAttacked(info, damageAmount);
			}
			for (final AbstractRelic r : this.relics) {
				damageAmount = r.onAttacked(info, damageAmount);
			}
		}
		for (final AbstractRelic r : this.relics) {
			damageAmount = r.onLoseHpLast(damageAmount);
		}
		this.lastDamageTaken = Math.min(damageAmount, this.currentHealth);
		DuelistMod.unblockedDamageTakenThisTurn = DuelistMod.unblockedDamageTakenThisTurn || this.lastDamageTaken > 0;
		if (damageAmount > 0) {
			for (final AbstractPower p : this.powers) {
				damageAmount = p.onLoseHp(damageAmount);
			}
			for (final AbstractRelic r : this.relics) {
				r.onLoseHp(damageAmount);
			}
			for (final AbstractPower p : this.powers) {
				p.wasHPLost(info, damageAmount);
			}
			for (final AbstractRelic r : this.relics) {
				r.wasHPLost(damageAmount);
			}
			if (info.owner != null) {
				for (final AbstractPower p : info.owner.powers) {
					p.onInflictDamage(info, damageAmount, this);
				}
			}
			if (info.owner != this) {
				this.useStaggerAnimation();
			}
			if (info.type == DamageInfo.DamageType.HP_LOSS) {
				GameActionManager.hpLossThisCombat += damageAmount;
			}
			GameActionManager.damageReceivedThisTurn += damageAmount;
			GameActionManager.damageReceivedThisCombat += damageAmount;
			this.currentHealth -= damageAmount;
			if (damageAmount > 0 && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
				this.updateCardsOnDamage();
				this.updateDuelistCardsOnDamage(damageAmount, info.owner);
				++this.damagedThisCombat;
			}
			AbstractDungeon.effectList.add(new StrikeEffect(this, this.hb.cX, this.hb.cY, damageAmount));
			if (this.currentHealth < 0) {
				this.currentHealth = 0;
			}
			else if (this.currentHealth < this.maxHealth / 4) {
				AbstractDungeon.topLevelEffects.add(new BorderFlashEffect(new Color(1.0f, 0.1f, 0.05f, 0.0f)));
			}
			this.healthBarUpdatedEvent();
			if (this.currentHealth <= this.maxHealth / 2.0f && !this.isBloodied) {
				this.isBloodied = true;
				for (final AbstractRelic r : this.relics) {
					if (r != null) {
						r.onBloodied();
					}
				}
			}
			if (this.currentHealth < 1) {
				if (!this.hasRelic("Mark of the Bloom")) {
					if (this.hasPotion("FairyPotion")) {
						for (final AbstractPotion p2 : this.potions) {
							if (p2.ID.equals("FairyPotion")) {
								p2.flash();
								this.currentHealth = 0;
								p2.use(this);
								AbstractDungeon.topPanel.destroyPotion(p2.slot);
								return;
							}
						}
					}
					else if (this.hasRelic("Lizard Tail") && (this.getRelic("Lizard Tail")).counter == -1) {
						this.currentHealth = 0;
						this.getRelic("Lizard Tail").onTrigger();
						return;
					}
				}
				this.isDead = true;
				DuelistMod.deathScreen = new DuelistDeathScreen(AbstractDungeon.getMonsters(), DeathType.DAMAGE);
				this.currentHealth = 0;
				if (this.currentBlock > 0) {
					this.loseBlock();
					AbstractDungeon.effectList.add(new HbBlockBrokenEffect(this.hb.cX - this.hb.width / 2.0f + AbstractPlayer.BLOCK_ICON_X, this.hb.cY - this.hb.height / 2.0f + AbstractPlayer.BLOCK_ICON_Y));
				}
			}
		}
		else if (this.currentBlock > 0) {
			AbstractDungeon.effectList.add(new BlockedWordEffect(this, this.hb.cX, this.hb.cY, AbstractPlayer.uiStrings.TEXT[0]));
		}
		else if (hadBlock) {
			AbstractDungeon.effectList.add(new BlockedWordEffect(this, this.hb.cX, this.hb.cY, AbstractPlayer.uiStrings.TEXT[0]));
			AbstractDungeon.effectList.add(new HbBlockBrokenEffect(this.hb.cX - this.hb.width / 2.0f + AbstractPlayer.BLOCK_ICON_X, this.hb.cY - this.hb.height / 2.0f + AbstractPlayer.BLOCK_ICON_Y));
		}
		else {
			AbstractDungeon.effectList.add(new StrikeEffect(this, this.hb.cX, this.hb.cY, 0));
		}
	}

	@Override
	public boolean obtainPotion(AbstractPotion potionToObtain) {
		boolean result = super.obtainPotion(potionToObtain);
		boolean hasMillenniumCoin = false;
		MillenniumCoin ref = null;
		for (AbstractRelic relic : this.relics) {
			if (relic instanceof MillenniumCoin) {
				hasMillenniumCoin = true;
				ref = (MillenniumCoin)relic;
				break;
			}
		}
		if (hasMillenniumCoin && potionToObtain instanceof MillenniumElixir) {
			ref.gainGold();
		}
		return result;
	}

	@Override
	public void gainGold(int amount) {
		super.gainGold(amount);
		if (AbstractDungeon.player != null && AbstractDungeon.player.masterDeck != null && AbstractDungeon.player.masterDeck.group != null) {
			ArrayList<CurseRoyal> instances = new ArrayList<>();
			for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
				if (card instanceof CurseRoyal) {
					instances.add((CurseRoyal) card);
				}
			}
			float displayCount = 0.0f;
			for (CurseRoyal curse : instances) {
				curse.loseMaxHp();
				AbstractDungeon.player.masterDeck.removeCard(curse);
				AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(curse, Settings.WIDTH / 3.0f + displayCount, Settings.HEIGHT / 2.0f));
				displayCount += Settings.WIDTH / 6.0f;
			}
		}
	}

	@SpireOverride
	protected void updateCardsOnDamage() {
		if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
			for (final AbstractCard c : this.hand.group) {
				c.tookDamage();
			}
			for (final AbstractCard c : this.discardPile.group) {
				c.tookDamage();
			}
			for (final AbstractCard c : this.drawPile.group) {
				c.tookDamage();
			}
		}
	}

	private void updateDuelistCardsOnDamage(int damageAmount, AbstractCreature damageSource) {
		if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
			for (AbstractCard c : this.hand.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).tookDamageWhileInHand(damageAmount, damageSource); }}
			for (AbstractCard c : this.discardPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).tookDamageWhileInDiscard(damageAmount, damageSource); }}
			for (AbstractCard c : this.drawPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).tookDamageWhileInDraw(damageAmount, damageSource); }}
			for (AbstractCard c : this.exhaustPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).tookDamageWhileExhausted(damageAmount, damageSource); }}
			for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).tookDamageWhileInGraveyard(damageAmount, damageSource); }}
			if (this.hasPower(SummonPower.POWER_ID)) {
				SummonPower pow = (SummonPower)this.getPower(SummonPower.POWER_ID);
				for (DuelistCard c : pow.getCardsSummoned()) { c.tookDamageWhileSummoned(damageAmount, damageSource); }
			}
		}
	}

	@Override
	public void updateInput()
	{
		super.updateInput();
		if (this.hoveredCard instanceof DuelistCard)
		{
			DuelistCard hdc = (DuelistCard)hoveredCard;
			if (hdc instanceof CircleFireKings || hdc instanceof Taotie)
			{
				for (final AbstractOrb o : this.orbs)
	            {
					if (o instanceof Lava || o instanceof FireOrb || o instanceof Blaze || o instanceof DuelistHellfire)
					{
						if (hdc instanceof ExploderDragon || hdc instanceof VolcanicEruption)
						{
							o.showEvokeValue();
						}
					}
	            	if (o instanceof Lava && hdc instanceof CircleFireKings)
	            	{
	            		o.showEvokeValue();
	            	}

	            	if (o instanceof DuelistLight && hdc instanceof Taotie)
	            	{
	            		o.showEvokeValue();
	            	}

	            	if (o instanceof FireOrb && hdc instanceof BlacklandFireDragon)
	            	{
	            		o.showEvokeValue();
	            	}
	            }
			}
			if (hdc.showInvertValue)
			{
				if (hdc.showInvertOrbs == 0)
				{
					for (final AbstractOrb o : this.orbs)
		            {
		            	if (o instanceof DuelistOrb)
		            	{
		            		((DuelistOrb)o).showInvertValue();
		            	}
		            }
				}
				else
				{
					int counter = hdc.showInvertOrbs;
					for (final AbstractOrb o : this.orbs)
		            {
						if (counter > 0)
						{
			            	if (o instanceof DuelistOrb)
			            	{
			            		((DuelistOrb)o).showInvertValue();
			            	}
			            	counter--;
						}
						else { break; }
		            }
				}
			}
		}
	}
}
