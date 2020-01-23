package duelistmod.characters;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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
import com.megacrit.cardcrawl.relics.Courier;
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
	public static CardGroup resummonPile = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
	public static CardGroup cardPool = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
	public static DuelistSoulGroup duelistSouls = new DuelistSoulGroup(null);
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
		if (Util.deckIs("Spellcaster Deck") && Util.getChallengeLevel() > 3) { return 2; }
		else { return 3; }
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
		ArrayList<AbstractCard> holiday = Util.holidayCardRandomizedList();
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
				GlobalPoolHelper.setGlobalDeckFlags(StarterDeckSetup.getCurrentDeck().getSimpleName());				
				PoolHelpers.newFillColored(StarterDeckSetup.getCurrentDeck().getSimpleName());
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
		
		// Filling in colorless pool with Basic set, if fill type is 0/3/5/6
		// Doing some extra stuff with the Courier to prevent game crashes due to stupid shop implementation
		if (!this.hasRelic(Courier.ID))
		{
			int ind = DuelistMod.setIndex;
			if (ind == 0 || ind == 3 || ind == 5 || ind == 6)
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
			String lastCardPool = "";
			for (AbstractCard c : cardPool.group) { lastCardPool += c.cardID + "~"; DuelistMod.dungeonCardPool.put(c.cardID, c.name); }
			
			Util.log("Saving full string of card pool... string=" + lastCardPool);
			try {
				SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
				config.setString("fullCardPool", lastCardPool);
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
		boolean challenge = false;
		
		// Always get Millennium Puzzle
		retVal.add(MillenniumPuzzle.ID);
		
		// Challenge Puzzle if Challenge Mode enabled
		if (DuelistMod.playingChallenge || DuelistMod.getChallengeDiffIndex() > -1) {
			challenge = true; 
			if (!DuelistMod.playingChallenge)
			{
				DuelistMod.challengeLevel = (DuelistMod.getChallengeDiffIndex() * 5) - 5;
				DuelistMod.playingChallenge = true;
			}
		}	
		
		if (challenge) { retVal.add(ChallengePuzzle.ID); }
		
		// Always add Card Pool relic (for viewing card pool, also handles boosters on victory if card rewards are enabled)
		retVal.add(CardPoolRelic.ID);
		
		// Add 2 other similar relics - one for Basic pool, one for Booster Pack pool
		retVal.add(CardPoolBasicRelic.ID);
		if (DuelistMod.allowBoosters || DuelistMod.alwaysBoosters) {
			retVal.add(BoosterPackPoolRelic.ID);
		}
		
		// If not playing Challenge Mode or Exodia Deck, allow player to customize card pool
		boolean exodiaDeck = StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Exodia Deck");
		if (!exodiaDeck)
		{
			if (DuelistMod.allowCardPoolRelics)
			{
				retVal.add(CardPoolAddRelic.ID);
				retVal.add(CardPoolMinusRelic.ID);
				retVal.add(CardPoolSaveRelic.ID);
				retVal.add(CardPoolOptionsRelic.ID);
			}
		}
		return retVal;
	}

	// Character Select screen effect
	@Override
	public void doCharSelectScreenSelectEffect() 
	{
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
		List<CutscenePanel> panels = new ArrayList<CutscenePanel>();
		panels.add(new CutscenePanel(DuelistMod.makePath("cutscenes/duelist1.png"), "ATTACK_HEAVY"));
		panels.add(new CutscenePanel(DuelistMod.makePath("cutscenes/duelist2.png")));
		panels.add(new CutscenePanel(DuelistMod.makePath("cutscenes/duelist3.png")));
		return panels;
	}

	// Used to load images in the character select screen
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

	@Override
	public String getPortraitImageName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
    public void useCard(final AbstractCard c, final AbstractMonster monster, final int energyOnUse) 
	{
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
	
	public static String getDuelist()
	{
		boolean isDragonDeck = false;
		//boolean isToonDeck = false;
		String deck = StarterDeckSetup.getCurrentDeck().getSimpleName();
		if (deck.equals("Dragon Deck")) { isDragonDeck = true; }
		//if (deck.equals("Toon Deck")) { isToonDeck = true; }
		//if (isToonDeck) { return "Pegasus"; }
		if (isDragonDeck || DuelistMod.playAsKaiba) { return "Kaiba"; }
		else { return "Yugi"; }
	}

	@Override
	public void releaseCard() {
		super.releaseCard();
		for (final AbstractOrb o : this.orbs) 
		{
			if (o instanceof DuelistOrb)
			{
				((DuelistOrb)o).hideInvertValues();
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
