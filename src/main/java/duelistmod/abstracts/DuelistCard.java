package duelistmod.abstracts;

import java.lang.reflect.Type;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.PatternSyntaxException;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.actions.common.*;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.*;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.cards.blue.GeneticAlgorithm;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.stances.*;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.RainbowCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.*;
import com.megacrit.cardcrawl.vfx.combat.*;

import basemod.BaseMod;
import basemod.abstracts.*;
import basemod.helpers.*;
import duelistmod.*;
import duelistmod.actions.common.*;
import duelistmod.actions.unique.ReviveAction;
import duelistmod.cards.*;
import duelistmod.cards.curses.*;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.other.tempCards.*;
import duelistmod.cards.other.tokens.*;
import duelistmod.cards.pools.aqua.Monokeros;
import duelistmod.cards.pools.insects.MirrorLadybug;
import duelistmod.cards.pools.machine.IronhammerGiant;
import duelistmod.cards.pools.warrior.DarkCrusader;
import duelistmod.characters.*;
import duelistmod.helpers.*;
import duelistmod.helpers.crossover.*;
import duelistmod.interfaces.*;
import duelistmod.orbs.*;
import duelistmod.patches.*;
import duelistmod.powers.*;
import duelistmod.powers.duelistPowers.*;
import duelistmod.powers.incomplete.*;
import duelistmod.relics.*;
import duelistmod.relics.MachineToken;
import duelistmod.rewards.BoosterPack;
import duelistmod.speedster.SpeedsterUtil.UC;
import duelistmod.speedster.actions.BeginSpeedModeAction;
import duelistmod.speedster.mechanics.*;
import duelistmod.stances.*;
import duelistmod.variables.*;
import duelistmod.vfx.ResummonOrbEffect;

public abstract class DuelistCard extends CustomCard implements ModalChoice.Callback, CustomSavable <String>
{
	
	/*
	 * CONTENTS
	 * 
	 * Card Fields						// Fields for Duelist Cards
	 * Static Setup						// Hack together the orb list before the dungeon is loaded, orb list is for orb modal so every card can open random orb choices dynamically
	 * Abstract Methods					// Abstracts for Duelist Cards
	 * Void Methods						// Empty method calls that can be implemented by Duelist cards
	 * Constructors						// Create new Duelist Cards via constructor
	 * Enums							// Duelist Card Enums
	 * Super Override Functions			// Override AbstractCard and CustomCard methods
	 * Duelist Functions				// Special functions for Duelist Cards
	 * Attack Functions					// Functions that run attack and damage actions
	 * Defend Functions					// Functions that run blocking actions
	 * Power Functions					// Functions that apply, remove and modify powers for players & enemies
	 * Misc Action Functions			// Functions that perform various other actions
	 * Stance Functions					// Functions that perform a variety of stance-related actions
	 * Summon Monster Functions			// Functions that run when playing monsters that Summon
	 * Tribute Monster Functions		// Functions that run when playing monsters that Tribute
	 * Tribute Synergy Functions		// Functions that run when tributing monsters for the same type of monster (eg strength gain on dragon for dragon tributes)
	 * Increment Functions				// Functions that run when playing cards that Increment
	 * Resummon Functions				// Functions that run when playing cards that Resummon
	 * Summon Modification Functions	// For modifying the number of summons on cards
	 * Tribute Modification Functions	// For modifying the number of tributes on cards
	 * Card Modal Functions				// For opening and playing random cards from modal choice builder
	 * Orb Modal Functions				// For opening and playing random cards from modal choice builder. Specifically for use with orb cards only
	 * Orb Functions					// For channeling, evoke, invert actions
	 * Random Card Functions			// For generating random Duelist Cards (pulls cards from DefaultMod.myCards to allow card removal options to function with randomization, and other customization of how random-generation of cards is handled)
	 * Type Card Functions				// For generating selections of monster types, and modifying the function of those chosen types (ala Shard of Greed, Winged Kuriboh Lv9, etc.)
	 * Debug Print Functions			// Functions that generate some sort of helpful debug log to print
	 * 
	 */
	
	// =============== CARD FIELDS =========================================================================================================================================================
	public AttackEffect baseAFX = AttackEffect.SLASH_HORIZONTAL;
	public ArrayList<Integer> startCopies = new ArrayList<Integer>();
	public ArrayList<Integer> saveTest = new ArrayList<Integer>();
	public ArrayList<String> savedTypeMods = new ArrayList<String>();
	public ArrayList<AbstractCard> saveTestCard = new ArrayList<AbstractCard>();
	public static ArrayList<DuelistCard> allowedCardChoices = new ArrayList<DuelistCard>();
	private static ArrayList<AbstractOrb> allowedOrbs = new ArrayList<AbstractOrb>();
	private static ArrayList<AbstractOrb> allOrbs = new ArrayList<AbstractOrb>();
	private static Map<String, AbstractOrb> orbMap = new HashMap<String, AbstractOrb>();
	private ModalChoice orbModal;	
	private ModalChoice cardModal;
	private DuelistModalChoice duelistCardModal;
	public static final String UPGRADE_DESCRIPTION = "";
	public String upgradeType;
	public String exodiaName = "None";
	public String originalName;
	public String tribString = DuelistMod.tribString;
	public String originalDescription = "Uh-oh. This card had its summons or tributes modified and somehow lost the original description! Go yell at Nyoxide.";
	public AbstractMonster detonationTarget = null;
	public boolean specialCanUseLogic = false;
	public boolean useTributeCanUse = false;
	public boolean useBothCanUse = false;
	public boolean toon = false;
	public boolean isSummon = false;
	public boolean isTribute = false;
	public boolean isCastle = false;
	public boolean isTributesModified = false;
	public boolean isTributesModifiedForTurn = false;
	public boolean isMagicNumModifiedForTurn = false;
	public boolean isTribModPerm = false;
	public boolean isSummonsModified = false;
	public boolean isSummonsModifiedForTurn = false;
	public boolean isSummonModPerm = false;
	public boolean isTypeAddedPerm = false;
	public boolean isSecondMagicModified = false;
	public boolean isThirdMagicModified = false;
	public boolean isEntombModified = false;
	public boolean isIncModified = false;
	public boolean upgradedSecondMagic = false;
	public boolean upgradedThirdMagic = false;
	public boolean upgradedTributes = false;
	public boolean upgradedSummons = false;
	public boolean upgradedEntomb = false;
	public boolean upgradedIncrement = false;
	public boolean inDuelistBottle = false;
	public boolean loadedTribOrSummonChange = false;
	public boolean fiendDeckDmgMod = false;
	public boolean aquaDeckEffect = false;
	public boolean showInvertValue = false;
	public boolean sendToGraveyard = false;
	public boolean sendToMasterDeck = false;
	public boolean retainPowerAfterUse = false;
	public boolean ignoreSuperCanUse = false;
	public boolean ignoreDuelistCanUse = false;
	public int showInvertOrbs;
	public int secondMagic = 0;
	public int baseSecondMagic = 0;
	public int thirdMagic = 0;
	public int baseThirdMagic = 0;
	public int baseEntomb = 0;
	public int entomb = 0;
	public int incrementMagic = 0;
	public int baseIncrementMagic = 0;
	public int summons = 0;
	public int tributes = 0;
	public int baseSummons = 0;
	public int baseTributes = 0;
	public int tributesForTurn = 0;
	public int summonsForTurn = 0;
	public int extraSummonsForThisTurn = 0;
	public int extraTributesForThisTurn = 0;
	public int moreSummons = 0;
	public int moreTributes = 0;
	public int permTribChange = 0;
	public int permSummonChange = 0;
	public int permCostChange = 999;
	public int poisonAmt;
	public int upgradeDmg;
	public int upgradeBlk;
	public int upgradeSummons;
	public int playCount;
	public int decSummons;
	public int dex;
	public int dmgHolder = -1;
	public int damageA;
	public int damageB;
	public int damageC;
	public int damageD;
	public int originalDamage = -1;
	public int originalBlock = -1;
	public int standardDeckCopies = 1;
	public int dragonDeckCopies = 1;
	public int spellcasterDeckCopies = 1;
	public int natureDeckCopies = 1;
	public int creatorDeckCopies = 1;
	public int toonDeckCopies = 1;
	public int orbDeckCopies = 1;
	public int resummonDeckCopies = 1;
	public int generationDeckCopies = 1;
	public int ojamaDeckCopies = 1;
	public int healDeckCopies = 1;
	public int incrementDeckCopies = 1;
	public int exodiaDeckCopies = 1;
	public int superheavyDeckCopies = 1;
	public int aquaDeckCopies = 1;
	public int machineDeckCopies = 1;
	public int zombieDeckCopies = 1;
	public int fiendDeckCopies = 1;
	public int giantDeckCopies = 1;
	public int insectDeckCopies = 1;
	public int plantDeckCopies = 1;
	public int predaplantDeckCopies = 1;
	public int megatypeDeckCopies = 1;
	public int a1DeckCopies = 1;
	public int a2DeckCopies = 1;
	public int a3DeckCopies = 1;
	public int p1DeckCopies = 1;
	public int p2DeckCopies = 1;
	public int p3DeckCopies = 1;
	public int p4DeckCopies = 1;
	public int p5DeckCopies = 1;
	public int metronomeDeckCopies = 1;
	public int originalMagicNumber = 0;
	public int detonations = 1;
	public int detonationsExtraRandomLow = 0;
	public int detonationsExtraRandomHigh = 0;
	public double dynDmg = 0;

	public int startingOriginalDeckCopies = 1;
	public int startingOPDragDeckCopies = 1;
	public int startingOPSPDeckCopies = 1;
	public int startingOPNDeckCopies = 1;
	public int startingOPRDeckCopies = 1;
	public int startingOPHDeckCopies = 1;
	public int startingOPODeckCopies = 1;
	// =============== /CARD FIELDS/ =======================================================================================================================================================
	
	
	
	// =============== STATIC SETUP =========================================================================================================================================================
	static
    {
        AbstractPlayer realPlayer = AbstractDungeon.player;
        AbstractDungeon.player = new FakePlayer();
        allOrbs.addAll(returnRandomOrbList());
        allowedOrbs.addAll(allOrbs);
        for (AbstractOrb o : allOrbs) { orbMap.put(o.name, o); }
        resetInvertStringMap();
        AbstractDungeon.player = realPlayer;
    }
	// =============== /STATIC SETUP/ =======================================================================================================================================================
	
	
	
	// =============== ABSTRACT METHODS =========================================================================================================================================================
	public abstract String getID();
	
	@Deprecated
	public abstract void onTribute(DuelistCard tributingCard);		/* DEPRECATED - Implement customOnTribute() to run special tributing functions on cards, monster types are handled automatically */
	
	@Deprecated
	public abstract void onResummon(int summons);
	public abstract void summonThis(int summons, DuelistCard c, int var);
	public abstract void summonThis(int summons, DuelistCard c, int var, AbstractMonster m);
	// =============== /ABSTRACT METHODS/ =======================================================================================================================================================
	
	// =============== VOID METHODS =========================================================================================================================================================
	public void onTributeWhileInHand(DuelistCard tributedMon, DuelistCard tributingMon) { }
	
	public void onTributeWhileInDiscard(DuelistCard tributedMon, DuelistCard tributingMon) { }
	
	public void onTributeWhileInExhaust(DuelistCard tributedMon, DuelistCard tributingMon) { }
	
	public void onTributeWhileInDraw(DuelistCard tributedMon, DuelistCard tributingMon) { }
	
	public void onTributeWhileInGraveyard(DuelistCard tributedMon, DuelistCard tributingMon) { }
	
	public void onTributeWhileSummoned(DuelistCard tributedMon, DuelistCard tributingMon) { }
	
	public void onSummonWhileInHand(DuelistCard summoned, int amountSummoned) { }
	
	public void onSummonWhileInDiscard(DuelistCard summoned, int amountSummoned) { }
	
	public void onSummonWhileInExhaust(DuelistCard summoned, int amountSummoned) { }
	
	public void onSummonWhileInDraw(DuelistCard summoned, int amountSummoned) { }
	
	public void onSummonWhileInGraveyard(DuelistCard summoned, int amountSummoned) { }
	
	public void onSummonWhileSummoned(DuelistCard summoned, int amountSummoned) { }
	
	public void onIncrementWhileInHand(int amount, int newMaxSummons) { }
	
	public void onIncrementWhileInDraw(int amount, int newMaxSummons) { }
	
	public void onIncrementWhileInDiscard(int amount, int newMaxSummons) { }
	
	public void onIncrementWhileInExhaust(int amount, int newMaxSummons) { }
	
	public void onIncrementWhileInGraveyard(int amount, int newMaxSummons) { }
	
	public void onIncrementWhileSummoned(int amount, int newMaxSummons) { }
	
	public void onResummonThisCard() { }
	
	public void onResummonWhileInHand(DuelistCard resummoned) { }
	
	public void onResummonWhileInDraw(DuelistCard resummoned) { }
	
	public void onResummonWhileInDiscard(DuelistCard resummoned) { }
	
	public void onResummonWhileInExhaust(DuelistCard resummoned) { }
	
	public void onResummonWhileInGraveyard(DuelistCard resummoned) { }
	
	public void onResummonWhileSummoned(DuelistCard resummoned) { }
	
	public void onResummonWhileInHand(DuelistCard resummoned, boolean actual) { if (actual) { this.onResummonWhileSummoned(resummoned); }}
	
	public void onResummonWhileInDraw(DuelistCard resummoned, boolean actual) { if (actual) { this.onResummonWhileInDraw(resummoned); }}
	
	public void onResummonWhileInDiscard(DuelistCard resummoned, boolean actual) { if (actual) { this.onResummonWhileInDiscard(resummoned); }}
	
	public void onResummonWhileInExhaust(DuelistCard resummoned, boolean actual) { if (actual) { this.onResummonWhileInExhaust(resummoned); }}
	
	public void onResummonWhileInGraveyard(DuelistCard resummoned, boolean actual) { if (actual) { this.onResummonWhileInGraveyard(resummoned); }}
	
	public void onResummonWhileSummoned(DuelistCard resummoned, boolean actual) { if (actual) { this.onResummonWhileSummoned(resummoned); }}
	
	public int modifyResummonAmtWhileInHand(AbstractCard resummoningCard) { return 0; }
	
	public int modifyResummonAmtWhileInDraw(AbstractCard resummoningCard) { return 0; }
	
	public int modifyResummonAmtWhileInDiscard(AbstractCard resummoningCard) { return 0; }
	
	public int modifyResummonAmtWhileInExhaust(AbstractCard resummoningCard) { return 0; }
	
	public int modifyResummonAmtWhileInGraveyard(AbstractCard resummoningCard) { return 0; }
	
	public int modifyResummonAmtWhileSummoned(AbstractCard resummoningCard) { return 0; }
	
	public int modifyReviveCostWhileInHand(ArrayList<AbstractCard> entombedList) { return 0; }
	
	public int modifyReviveCostWhileInDraw(ArrayList<AbstractCard> entombedList) { return 0; }
	
	public int modifyReviveCostWhileInDiscard(ArrayList<AbstractCard> entombedList) { return 0; }
	
	public int modifyReviveCostWhileInExhaust(ArrayList<AbstractCard> entombedList) { return 0; }
	
	public int modifyReviveCostWhileInGraveyard(ArrayList<AbstractCard> entombedList) { return 0; }
	
	public int modifyReviveCostWhileSummoned(ArrayList<AbstractCard> entombedList) { return 0; }
	
	public int modifyReviveListSizeWhileInHand() { return 0; }
	
	public int modifyReviveListSizeWhileInDraw() { return 0; }
	
	public int modifyReviveListSizeWhileInDiscard() { return 0; }
	
	public int modifyReviveListSizeWhileInExhaust() { return 0; }
	
	public int modifyReviveListSizeWhileInGraveyard() { return 0; }
	
	public int modifyReviveListSizeWhileSummoned() { return 0; }
	
	public boolean allowResummonWhileInHand(AbstractCard resummoningCard) { return true; }
	
	public boolean allowResummonWhileInDraw(AbstractCard resummoningCard) { return true; }
	
	public boolean allowResummonWhileInDiscard(AbstractCard resummoningCard) { return true; }
	
	public boolean allowResummonWhileInExhaust(AbstractCard resummoningCard) { return true; }
	
	public boolean allowResummonWhileInGraveyard(AbstractCard resummoningCard) { return true; }
	
	public boolean allowResummonWhileSummoned(AbstractCard resummoningCard) { return true; }
	
	public boolean allowReviveWhileInHand() { return true; }
	
	public boolean allowReviveWhileInDraw() { return true; }
	
	public boolean allowReviveWhileInDiscard() { return true; }
	
	public boolean allowReviveWhileInExhaust() { return true; }
	
	public boolean allowReviveWhileInGraveyard() { return true; }
	
	public boolean allowReviveWhileSummoned() { return true; }
	
	public boolean upgradeResummonWhileInHand(AbstractCard resummoningCard) { return false; }
	
	public boolean upgradeResummonWhileInDraw(AbstractCard resummoningCard) { return false; }
	
	public boolean upgradeResummonWhileInDiscard(AbstractCard resummoningCard) { return false; }
	
	public boolean upgradeResummonWhileInExhaust(AbstractCard resummoningCard) { return false; }
	
	public boolean upgradeResummonWhileInGraveyard(AbstractCard resummoningCard) { return false; }
	
	public boolean upgradeResummonWhileSummoned(AbstractCard resummoningCard) { return false; }

	public boolean modifyCanUseWhileInHand(AbstractPlayer p, AbstractMonster m) { return true; }

	public boolean modifyCanUseWhileInDiscard(AbstractPlayer p, AbstractMonster m) { return true; }

	public boolean modifyCanUseWhileInExhaust(AbstractPlayer p, AbstractMonster m) { return true; }

	public boolean modifyCanUseWhileInDraw(AbstractPlayer p, AbstractMonster m) { return true; }

	public boolean modifyCanUseWhileInGraveyard(AbstractPlayer p, AbstractMonster m) { return true; }

	public boolean modifyCanUseWhileSummoned(AbstractPlayer p, AbstractMonster m) { return true; }

	public String cannotUseMessageWhileInHand(final AbstractPlayer p, final AbstractMonster m) { return "Cannot use due to " + this.name + " in your hand!"; }

	public String cannotUseMessageWhileInDiscard(final AbstractPlayer p, final AbstractMonster m) { return "Cannot use due to " + this.name + " in your discard pile!"; }

	public String cannotUseMessageWhileInExhaust(final AbstractPlayer p, final AbstractMonster m) { return "Cannot use due to " + this.name + " in your exhaust pile!"; }

	public String cannotUseMessageWhileInDraw(final AbstractPlayer p, final AbstractMonster m) { return "Cannot use due to " + this.name + " in your draw pile!"; }

	public String cannotUseMessageWhileInGraveyard(final AbstractPlayer p, final AbstractMonster m) { return "Cannot use due to " + this.name + " in your Graveyard!"; }

	public String cannotUseMessageWhileSummoned(final AbstractPlayer p, final AbstractMonster m) { return "Cannot use because " + this.name + " is summoned!"; }

	public void onSynergyTributeWhileInHand() { }
	
	public void onSynergyTributeWhileInDiscard() { }
	
	public void onSynergyTributeWhileInExhaust() { }
	
	public void onSynergyTributeWhileInDraw() { }
	
	public void onSynergyTributeWhileInGraveyard() { }
	
	public void onSynergyTributeWhileSummoned() { }
	
	public void onLoseArtifact() { }
	
	public void onLoseArtifactDraw() { }
	
	public void onLoseArtifactHand() { }
	
	public void onLoseArtifactDiscard() { }
	
	public void onLoseArtifactExhaust() { }
	
	public void onLoseArtifactSummoned() { }
	
	public void onLoseArtifactGraveyard() { }
	
	public void onObtainTrigger() { }
	
	public void onPostObtainTrigger() { }
	
	public void onPotionGetWhileInMasterDeck() { }
	
	public void onEnemyUseCardWhileInHand(final AbstractCard card) { }
	
	public void onEnemyUseCardWhileInDraw(final AbstractCard card) { }
	
	public void onEnemyUseCardWhileInDiscard(final AbstractCard card) { }
	
	public void onEnemyUseCardWhileInExhaust(final AbstractCard card) { }
	
	public void onEnemyUseCardWhileInDeck(final AbstractCard card) { }
	
	public void onEnemyUseCardWhileSummoned(final AbstractCard card) { }
	
	public void onEnemyUseCardWhileInGraveyard(final AbstractCard card) { }
	
	public void onEnemyUseCard(final AbstractCard card) { }
	
	public void onDetonateWhileInHand() { }
	public void onDetonateWhileInDraw() { }
	public void onDetonateWhileInDiscard() { }
	public void onDetonateWhileInExhaust() { }
	public void onDetonateWhileInDeck() { }
	public void onDetonateWhileInGraveyard() { }
	public void onDetonateWhileSummoned() { }
	public void onDetonate() { }
	
	public void onSolderWhileInHand() { }
	public void onSolderWhileInDraw() { }
	public void onSolderWhileInDiscard() { }
	public void onSolderWhileInExhaust() { }
	public void onSolderWhileInDeck() { }
	public void onSolderWhileInGraveyard() { }
	public void onSolderWhileSummoned() { }
	public void onSolder() { }
	
	public void onPassRouletteWhileInHand() { }
	public void onPassRouletteWhileInDraw() { }
	public void onPassRouletteWhileInDiscard() { }
	public void onPassRouletteWhileInExhaust() { }
	public void onPassRouletteWhileInDeck() { }
	public void onPassRouletteWhileInGraveyard() { }
	public void onPassRouletteWhileSummoned() { }
	public void onPassRoulette() { }
	
	public int modifyShadowDamageWhileInHand() { return 0; }
	public int modifyShadowDamageWhileInDraw() { return 0; }
	public int modifyShadowDamageWhileInDiscard() { return 0; }
	public int modifyShadowDamageWhileInExhaust() { return 0; }
	public int modifyShadowDamageWhileInDeck() { return 0; }
	public int modifyShadowDamageWhileInGraveyard() { return 0; }
	public int modifyShadowDamageWhileSummoned() { return 0; }
	
	public int modifyUndeadDamageWhileInHand() { return 0; }
	public int modifyUndeadDamageWhileInDraw() { return 0; }
	public int modifyUndeadDamageWhileInDiscard() { return 0; }
	public int modifyUndeadDamageWhileInExhaust() { return 0; }
	public int modifyUndeadDamageWhileInDeck() { return 0; }
	public int modifyUndeadDamageWhileInGraveyard() { return 0; }
	public int modifyUndeadDamageWhileSummoned() { return 0; }
	
	public void onSoulChangeWhileInHand(int newSouls, int change) { }
	public void onSoulChangeWhileInDraw(int newSouls, int change) { }
	public void onSoulChangeWhileInDiscard(int newSouls, int change) { }
	public void onSoulChangeWhileInExhaust(int newSouls, int change) { }
	public void onSoulChangeWhileInDeck(int newSouls, int change) { }
	public void onSoulChangeWhileInGraveyard(int newSouls, int change) { }
	public void onSoulChangeWhileSummoned(int newSouls, int change) { }
	public void onSoulChange(int newSouls, int change) { }
	
	public void triggerOverflowEffect() 
	{ 
		DuelistMod.overflowedThisTurn = true;
		DuelistMod.overflowsThisCombat++;
		SummonPower pow = getSummonPow();
		for (AbstractCard c : player().hand.group) { if (c instanceof DuelistCard && c.hasTag(Tags.TIDAL)) { ((DuelistCard)c).statBuffOnTidal(); }}
		for (AbstractCard c : player().discardPile.group) { if (c instanceof DuelistCard && c.hasTag(Tags.TIDAL)) { ((DuelistCard)c).statBuffOnTidal(); }}
		for (AbstractCard c : player().drawPile.group) { if (c instanceof DuelistCard && c.hasTag(Tags.TIDAL)) { ((DuelistCard)c).statBuffOnTidal(); }}
		for (AbstractCard c : player().exhaustPile.group) { if (c instanceof DuelistCard && c.hasTag(Tags.TIDAL)) { ((DuelistCard)c).statBuffOnTidal(); }}
		for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard && c.hasTag(Tags.TIDAL)) { ((DuelistCard)c).statBuffOnTidal(); }}
		if (pow != null) { 
			for (DuelistCard c : pow.actualCardSummonList) 
			{
				if (c.hasTag(Tags.TIDAL))
				{
					(c).statBuffOnTidal(); 
				}
			} 
		}
	}
	
	public void triggerPossessed()
	{
		SummonPower pow = getSummonPow();
		for (AbstractCard c : player().hand.group) { if (c instanceof DuelistCard && c.hasTag(Tags.POSSESSED)) { ((DuelistCard)c).statBuffOnResummon(); }}
		for (AbstractCard c : player().discardPile.group) { if (c instanceof DuelistCard && c.hasTag(Tags.POSSESSED)) { ((DuelistCard)c).statBuffOnResummon(); }}
		for (AbstractCard c : player().drawPile.group) { if (c instanceof DuelistCard && c.hasTag(Tags.POSSESSED)) { ((DuelistCard)c).statBuffOnResummon(); }}
		for (AbstractCard c : player().exhaustPile.group) { if (c instanceof DuelistCard && c.hasTag(Tags.POSSESSED)) { ((DuelistCard)c).statBuffOnResummon(); }}
		for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard && c.hasTag(Tags.POSSESSED)) { ((DuelistCard)c).statBuffOnResummon(); }}
		if (pow != null) { 
			for (DuelistCard c : pow.actualCardSummonList) 
			{
				if (c.hasTag(Tags.POSSESSED))
				{
					(c).statBuffOnResummon(); 
				}
			} 
		}
	}
	
	public void onOverflowWhileInHand() { }
	public void onOverflowWhileInDraw() { }
	public void onOverflowWhileInDiscard() { }
	public void onOverflowWhileInExhaust() { }
	public void onOverflowWhileInGraveyard() { }
	public void onOverflowWhileSummoned() { }
	
	public void onFishWhileInHand(ArrayList<AbstractCard> discarded, ArrayList<AbstractCard> aquasDiscarded) { }
	public void onFishWhileInDraw(ArrayList<AbstractCard> discarded, ArrayList<AbstractCard> aquasDiscarded) { }
	public void onFishWhileInDiscard(ArrayList<AbstractCard> discarded, ArrayList<AbstractCard> aquasDiscarded) { }
	public void onFishWhileInExhaust(ArrayList<AbstractCard> discarded, ArrayList<AbstractCard> aquasDiscarded) { }
	public void onFishWhileInGraveyard(ArrayList<AbstractCard> discarded, ArrayList<AbstractCard> aquasDiscarded) { }
	public void onFishWhileSummoned(ArrayList<AbstractCard> discarded, ArrayList<AbstractCard> aquasDiscarded) { }
	
	public void onCardDrawnWhileSummoned(final AbstractCard card) { }
	public void onCardDrawnWhileInGraveyard(final AbstractCard card) { }
	
	public void onCardPlayedWhileSummoned(final AbstractCard card) { }
	public void onCardPlayedWhileInGraveyard(final AbstractCard card) { }
	
	public void statBuffOnTidal() { }
	public void statBuffOnResummon() { }
	// =============== /VOID METHODS/ =======================================================================================================================================================
	
	public boolean canSpawnInBooster(BoosterPack pack) { return true; }
	
	// =============== CONSTRUCTORS =========================================================================================================================================================
	public DuelistCard(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET)
	{
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.originalName = NAME;
		this.misc = 0;
		this.baseDamage = this.damage = 0;
		this.originalDescription = DESCRIPTION;
		this.savedTypeMods.add("default");
		setupStartingCopies();
		ModalChoiceBuilder builder = new ModalChoiceBuilder().setCallback(this).setColor(COLOR).setType(CardType.SKILL).setTitle("Choose an Orb to Channel");
		for (AbstractOrb orb : allowedOrbs) { if (DuelistMod.orbCardMap.get(orb.name) != null) { builder.addOption(DuelistMod.orbCardMap.get(orb.name)); }}
		orbModal = builder.create();
		
		ModalChoiceBuilder cardBuilder = new ModalChoiceBuilder().setCallback(this).setColor(COLOR).setType(CardType.SKILL).setTitle("Choose a Card to Play");
		for (DuelistCard c : allowedCardChoices) { cardBuilder.addOption(c); }
		cardModal = cardBuilder.create();
		
		DuelistModalChoiceBuilder duelistCardBuilder = new DuelistModalChoiceBuilder().setCallback(this).setColor(COLOR).setType(CardType.SKILL).setTitle("Choose a Card to Play");
		for (DuelistCard c : allowedCardChoices) { duelistCardBuilder.addOption(c); }
		duelistCardModal = duelistCardBuilder.create();
	}

	// =============== /CONSTRUCTORS/ =======================================================================================================================================================
	
	// =============== ENUMS =========================================================================================================================================================

	// =============== /ENUMS/ =======================================================================================================================================================
	
	// =============== SUPER OVERRIDE FUNCTIONS =========================================================================================================================================================

	@Override
	public boolean canUse(final AbstractPlayer p, final AbstractMonster m) {
		boolean outFlag = false;																										// Dynamic flag for output
		boolean superCheck = super.canUse(p, m);																						// Check super.canUse()
		boolean cardChecks = this.cardSpecificCanUse(p, m);																				// Check anything specific implemented by individual cards (intended to be Overidden)
		boolean summonChallenge = (Util.isCustomModActive("theDuelist:SummonersChallenge") || DuelistMod.challengeLevel20);			// Check for if we need to check space in summon zones for tokens (if C20 or special Challenge the Spire challenge)
		boolean goldChallenge = (DuelistMod.getChallengeDiffIndex() < 3);																// Check for Gold level Challenge the Spire challenge
		boolean resummon = summonChallenge ? goldChallenge && this.misc == 52 : this.misc == 52;										// Check for resummons

		// super.canUse() or card is ignoring that AND card is being resummoned or it passes its own checks
		if ((superCheck || this.ignoreSuperCanUse) && (resummon || cardChecks)) {
			
			// True if: resummoning, card is ignoring normal global checks, or tribute/toon/summon/etc checks all pass
			outFlag = resummon || this.ignoreDuelistCanUse || duelistCanUse(p, m, summonChallenge, goldChallenge);
		}
		if (!outFlag && !cardChecks) {
			String failReason = failedCardSpecificCanUse(p, m);
			if (!failReason.equals("")) {
				this.cantUseMessage = failReason;
			}
		}
		return outFlag;
	}

	public String failedCardSpecificCanUse(final AbstractPlayer p, final AbstractMonster m) { return DuelistMod.needSummonsString; }
	
	public boolean cardSpecificCanUse(final AbstractPlayer p, final AbstractMonster m) {
		if (this.hasTag(Tags.X_COST)) {
			boolean mausoActive = (p.hasPower(EmperorPower.POWER_ID) && (!((EmperorPower)p.getPower(EmperorPower.POWER_ID)).flag));
			boolean atLeastOneTribute = (p.hasPower(SummonPower.POWER_ID) && (p.getPower(SummonPower.POWER_ID).amount) > 0);
			return mausoActive || atLeastOneTribute;
		}
		return true;
	}

	public boolean duelistCanUse(final AbstractPlayer p, final AbstractMonster m, boolean summonChallenge, boolean goldChallenge) {
		boolean abstracts = checkModifyCanUseForAbstracts(p, m);
		boolean hasMauso = p.hasPower(EmperorPower.POWER_ID);
		boolean passToonCheck = !this.hasTag(Tags.TOON_WORLD) || ((p.hasPower(ToonWorldPower.POWER_ID) || (p.hasPower(ToonKingdomPower.POWER_ID))));
		Integer currentSummons = (p.hasPower(SummonPower.POWER_ID)) ? p.getPower(SummonPower.POWER_ID).amount : null;
		int netSummons = currentSummons != null ? currentSummons + this.summons - this.tributes : this.summons - this.tributes;
		int maxSummons = DuelistCard.getMaxSummons(p);
		boolean summonZonesCheck = netSummons > -1 && netSummons <= maxSummons;
		boolean hasSummonOrTribCost = this.tributes > 0 || this.summons > 0;

		// Cards without Summon or Tribute
		if (passToonCheck && abstracts && !hasSummonOrTribCost) {
			return true;
		}

		// Summon, Tribute, or Summon/Tribute cards
		if (passToonCheck && abstracts) {
			
			// If checking for space in summon zones
			if (summonChallenge) {
				
				// Not tributing, either because no tribute cost or Emperor's Mausoleum is active
				if (this.tributes < 1 || (hasMauso && (!((EmperorPower)p.getPower(EmperorPower.POWER_ID)).flag))) {
					int finalSummons = currentSummons != null ? currentSummons + this.summons : this.summons;
					int curr = currentSummons != null ? currentSummons : 0;
					if (maxSummons - finalSummons > 1) { this.cantUseMessage = "You only have " + (maxSummons - curr) + " monster zones"; }
					else if (maxSummons - finalSummons == 1) { this.cantUseMessage = "You only have 1 monster zone"; }
					else { this.cantUseMessage = "No monster zones remaining"; }
					return finalSummons <= maxSummons;
				} 
				
				// Player has summons, and enough to pay tribute cost (which must exist here)
				else if (currentSummons != null && currentSummons >= this.tributes) {
					if (!summonZonesCheck) {
						if (maxSummons - netSummons > 1) { this.cantUseMessage = "You only have " + (maxSummons - currentSummons) + " monster zones"; }
						else if (maxSummons - netSummons == 1) { this.cantUseMessage = "You only have 1 monster zone"; }
						else { this.cantUseMessage = "No monster zones remaining"; }
					} 
					return summonZonesCheck;
				}
			} 
			
			// Only checking if tribute is possible, ignoring summon zone spaces
			else {
				boolean tribCheck = this.tributes < 1 || (p.hasPower(SummonPower.POWER_ID) && (p.getPower(SummonPower.POWER_ID).amount) >= this.tributes);
				boolean outFlag = (hasMauso) ? (!((EmperorPower)p.getPower(EmperorPower.POWER_ID)).flag) || tribCheck : tribCheck;
				if (!outFlag) {
					this.cantUseMessage = this.tribString;
				}
				return outFlag;
			}
		}
		
		// Failed either Toon Check or check on a Duelist Object
		this.cantUseMessage = this.tribString;
		if (!passToonCheck) {
			this.cantUseMessage = DuelistMod.toonWorldString;
		} else if (!abstracts) {
			List<String> cannotUseBecauseOf = getAbstractsCantUseMessage(p, m);
			if (cannotUseBecauseOf.size() > 0) {
				this.cantUseMessage = cannotUseBecauseOf.get(0);
			}
		}
		return false;
	}

	public boolean checkModifyCanUseForAbstracts(final AbstractPlayer p, final AbstractMonster m)
	{
		boolean amtInc;
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { amtInc = ((DuelistPotion)pot).modifyCanUse(p, m, this); if (!amtInc) { return false; }}}
		for (AbstractRelic r : p.relics) { if (r instanceof DuelistRelic) { amtInc = ((DuelistRelic)r).modifyCanUse(p, m, this); if (!amtInc) { return false; }}}
		for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  amtInc = ((DuelistOrb)o).modifyCanUse(p, m, this); if (!amtInc) { return false; }}}
		for (AbstractPower pow : p.powers) { if (pow instanceof DuelistPower) { amtInc = ((DuelistPower)pow).modifyCanUse(p, m, this); if (!amtInc) { return false; }}}
		for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).modifyCanUseWhileInHand(p, m); if (!amtInc) { return false; }}}
		for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).modifyCanUseWhileInDiscard(p, m); if (!amtInc) { return false; }}}
		for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).modifyCanUseWhileInDraw(p, m); if (!amtInc) { return false; }}}
		for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).modifyCanUseWhileInExhaust(p, m); if (!amtInc) { return false; }}}
		for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).modifyCanUseWhileInGraveyard(p, m); if (!amtInc) { return false; }}}
		if (player().hasPower(SummonPower.POWER_ID)) {
			SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
			for (DuelistCard c : pow.actualCardSummonList) { amtInc = c.modifyCanUseWhileSummoned(p, m); if (!amtInc) { return false; }}
		}
		return true;
	}

	public List<String> getAbstractsCantUseMessage(final AbstractPlayer p, final AbstractMonster m)
	{
		List<String> out = new ArrayList<>();
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { String msg =((DuelistPotion)pot).cannotUseMessage(p, m, this); out.add(msg); }}
		for (AbstractRelic r : p.relics) { if (r instanceof DuelistRelic) { String msg =((DuelistRelic)r).cannotUseMessage(p, m, this); out.add(msg);}}
		for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  String msg =((DuelistOrb)o).cannotUseMessage(p, m, this); out.add(msg);}}
		for (AbstractPower pow : p.powers) { if (pow instanceof DuelistPower) { String msg =((DuelistPower)pow).cannotUseMessage(p, m, this); out.add(msg);}}
		for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard) { String msg =((DuelistCard)c).cannotUseMessageWhileInHand(p, m); out.add(msg);}}
		for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard) { String msg =((DuelistCard)c).cannotUseMessageWhileInDiscard(p, m); out.add(msg);}}
		for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard) { String msg =((DuelistCard)c).cannotUseMessageWhileInDraw(p, m); out.add(msg);}}
		for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { String msg =((DuelistCard)c).cannotUseMessageWhileInExhaust(p, m); out.add(msg);}}
		for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { String msg =((DuelistCard)c).cannotUseMessageWhileInGraveyard(p, m); out.add(msg);}}
		if (player().hasPower(SummonPower.POWER_ID)) {
			SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
			for (DuelistCard c : pow.actualCardSummonList) { String msg = c.cannotUseMessageWhileSummoned(p, m); out.add(msg);}
		}
		return out;
	}

	protected void addToBot(final AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }
    
    protected void addToTop(final AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }
	
	@Override
	public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp)
	{
		applyPowersToMagicNumber();
		applyPowersToSummons();
		applyPowersToTributes();		
		applyPowersToSecondMagicNumber();
		applyPowersToThirdMagicNumber();
		applyPowersToEntomb();
		tmp = super.calculateModifiedCardDamage(player, mo, tmp);
		if (this.hasTag(Tags.ZOMBIE) && TheDuelist.resummonPile.size() > 0)
		{
			int dmgBoost = 0;
			int dmgPer = 3 + handleModifyUndeadDamage();
			for (AbstractCard c : TheDuelist.resummonPile.group) { if (c.hasTag(Tags.UNDEAD)) { dmgBoost += dmgPer; }}
			if (dmgBoost > 0) { tmp += dmgBoost; }
		}
		if (this.hasTag(Tags.STAMPEDING) && player().hasPower(CyberEltaninPower.POWER_ID)) {  float dmgMod = (player().getPower(CyberEltaninPower.POWER_ID).amount / 10.00f) + 1.0f; tmp = tmp * dmgMod; }
		if (this.hasTag(Tags.DRAGON) && player.hasPower(Dragonscales.POWER_ID)) { tmp += ((Dragonscales)player.getPower(Dragonscales.POWER_ID)).getInc();  }
		if (mo != null)
		{
			if (this.hasTag(Tags.DINOSAUR) && mo.hasPower(WeakPower.POWER_ID)) { int stacks = mo.getPower(WeakPower.POWER_ID).amount; if (stacks > 0) { float mod = ((float)stacks * 0.1f) + 1.0f; tmp = tmp * mod; }}
			if (this.hasTag(Tags.DINOSAUR) && player.hasPower(LostWorldPower.POWER_ID) && mo.hasPower(VulnerablePower.POWER_ID)) { int stacks = mo.getPower(VulnerablePower.POWER_ID).amount; float mod = ((float)stacks * 0.1f) + 1.0f; tmp = tmp * mod; }
			if (this.hasTag(Tags.DRAGON) && player.hasPower(DragonRavinePower.POWER_ID) && mo.hasPower(VulnerablePower.POWER_ID)) { int stacks = mo.getPower(VulnerablePower.POWER_ID).amount; float mod = ((float)stacks * 0.1f) + 1.0f; tmp = tmp * mod; }
			if (this.hasTag(Tags.DRAGON) && player.hasPower(VanDragPower.POWER_ID) && mo.hasPower(WeakPower.POWER_ID)) { int stacks = mo.getPower(WeakPower.POWER_ID).amount; float mod = ((float)stacks * 0.1f) + 1.0f; tmp = tmp * mod; }
		}
		if (this.hasTag(Tags.DRAGON) && player().hasPower(MountainPower.POWER_ID)) { float dmgMod = (player().getPower(MountainPower.POWER_ID).amount / 10.00f) + 1.0f; tmp = tmp * dmgMod; }
		if ((this.hasTag(Tags.DINOSAUR)) && player().hasPower(JurassicImpactPower.POWER_ID)) { float dmgMod = 1.0f - (player().getPower(JurassicImpactPower.POWER_ID).amount / 10.00f); tmp = tmp * dmgMod; }
		if (this.hasTag(Tags.SPELLCASTER) && player().hasPower(YamiPower.POWER_ID)) {  float dmgMod = (player().getPower(YamiPower.POWER_ID).amount / 10.00f) + 1.0f; tmp = tmp * dmgMod; }
		if (this.hasTag(Tags.PLANT) && player().hasPower(VioletCrystalPower.POWER_ID)) { float dmgMod = (player().getPower(VioletCrystalPower.POWER_ID).amount / 10.00f) + 1.0f; tmp = tmp * dmgMod; }
		if (this.hasTag(Tags.NATURIA) && player().hasPower(SacredTreePower.POWER_ID)) { float dmgMod = (player().getPower(SacredTreePower.POWER_ID).amount / 10.00f) + 1.0f; tmp = tmp * dmgMod; }
		if (this.hasTag(Tags.AQUA) && player().hasPower(UmiPower.POWER_ID)) { float dmgMod = (player().getPower(UmiPower.POWER_ID).amount / 10.00f) + 1.0f; tmp = tmp * dmgMod; }
		if (this.hasTag(Tags.ZOMBIE) || this.hasTag(Tags.FIEND)) { if (player().hasPower(GatesDarkPower.POWER_ID)) { float dmgMod = (player().getPower(GatesDarkPower.POWER_ID).amount / 10.00f) + 1.0f; tmp = tmp * dmgMod;  }}
		if (this.hasTag(Tags.ZOMBIE) || this.hasTag(Tags.ROCK) || this.hasTag(Tags.DINOSAUR)) { if (player().hasPower(WastelandPower.POWER_ID)) { float dmgMod = (player().getPower(WastelandPower.POWER_ID).amount / 10.00f) + 1.0f; tmp = tmp * dmgMod;  }}
		if (this.hasTag(Tags.WARRIOR) && player().hasPower(SogenPower.POWER_ID)) { float dmgMod = (player().getPower(SogenPower.POWER_ID).amount / 10.00f) + 1.0f; tmp = tmp * dmgMod; }
		if (this.hasTag(Tags.BUG) && player().hasPower(BugMatrixPower.POWER_ID)) { float dmgMod = (player().getPower(BugMatrixPower.POWER_ID).amount / 10.00f) + 1.0f; tmp = tmp * dmgMod; }
		if ((this.hasTag(Tags.BUG) || this.hasTag(Tags.SPIDER)) && player().hasPower(ForestPower.POWER_ID)) { float dmgMod = (player().getPower(ForestPower.POWER_ID).amount / 10.00f) + 1.0f; tmp = tmp * dmgMod; }
		if (this.hasTag(Tags.AQUA) && player().hasPower(SpikedGillmanPower.POWER_ID)) { tmp += player().getPower(SpikedGillmanPower.POWER_ID).amount;  }
		if (this.hasTag(Tags.WARRIOR) && player().stance.ID.equals("theDuelist:Spectral")) { tmp = tmp * DuelistMod.spectralDamageMult; }
		if (this.hasTag(Tags.DRAGON) && player().hasPower(CyberDragonSiegerPower.POWER_ID)) {  float dmgMod = (player().getPower(CyberDragonSiegerPower.POWER_ID).amount / 10.00f) + 1.0f; tmp = tmp * dmgMod; }
		if (this.hasTag(Tags.MACHINE) && player().hasPower(CyberDragonSiegerPower.POWER_ID)) {  float dmgMod = (player().getPower(CyberDragonSiegerPower.POWER_ID).amount / 10.00f) + 1.0f; tmp = tmp * dmgMod; }
		if (this.hasTag(Tags.VENDREAD) && player().hasPower(VendreadRevolutionPower.POWER_ID)) { tmp = tmp * 2.0f; }
		if (player().hasPower(SolidarityDiscardPower.POWER_ID))
		{
			SolidarityDiscardPower pow = (SolidarityDiscardPower)player().getPower(SolidarityDiscardPower.POWER_ID);
			CardTags modTag = pow.solidarity();
			if (!modTag.equals(Tags.ALL) && this.hasTag(modTag)) 
			{ 
				float dmgMod = (pow.amount/10.00f) + 1.00f; 
				tmp = (tmp * dmgMod); 
			}
		}
		if (player().hasPower(SolidarityExhaustPower.POWER_ID))
		{
			SolidarityExhaustPower pow = (SolidarityExhaustPower)player().getPower(SolidarityExhaustPower.POWER_ID);
			CardTags modTag = pow.solidarity();
			if (!modTag.equals(Tags.ALL) && this.hasTag(modTag)) 
			{ 
				float dmgMod = (pow.amount/10.00f) + 1.00f; 
				tmp = (tmp * dmgMod); 			
			}
		}
		//if (DuelistMod.debug) { DuelistMod.logger.info("Updated damage for " + this.originalName + " based on power effects. New damage should read as: " + tmp);}
		if (this.hasTag(DuelistMod.chosenRockSunriseTag) && player().hasPower(RockSunrisePower.POWER_ID)) { float dmgMod = (player().getPower(RockSunrisePower.POWER_ID).amount / 10.00f) + 1.0f; tmp = tmp * dmgMod; }
		return tmp;
	}
	
	@Override
	protected void applyPowersToBlock() 
	{
		super.applyPowersToBlock();
		float tmp = (float)this.block;		
		for (AbstractPower pow : AbstractDungeon.player.powers) { if (pow instanceof DuelistPower) { tmp = ((DuelistPower)pow).modifyBlock(tmp, this);}}
		for (AbstractOrb o : AbstractDungeon.player.orbs) { if (o instanceof DuelistOrb) { tmp = ((DuelistOrb)o).modifyBlock(tmp, this);}}
		for (AbstractRelic o : AbstractDungeon.player.relics) { if (o instanceof DuelistRelic) { tmp = ((DuelistRelic)o).modifyBlock(tmp, this);}}
		for (AbstractPotion o : AbstractDungeon.player.potions) { if (o instanceof DuelistPotion) { tmp = ((DuelistPotion)o).modifyBlock(tmp, this);}}
		if (AbstractDungeon.player.stance instanceof DuelistStance) { tmp = ((DuelistStance)AbstractDungeon.player.stance).modifyBlock(tmp, this); }
		if (this.baseBlock != MathUtils.floor(tmp)) {  this.isBlockModified = true; }
		if (tmp < 0.0f) { tmp = 0.0f; }
		this.block = MathUtils.floor(tmp);
	}
	
	@Override
	public void applyPowers()
	{
		applyPowersToMagicNumber();
		applyPowersToSummons();
		applyPowersToTributes();
		applyPowersToSecondMagicNumber();
		applyPowersToThirdMagicNumber();
		applyPowersToEntomb();
		super.applyPowers();	
	}

	public void applyPowersToSummons()
	{
		if (this.isSummonsModifiedForTurn)
		{
			if (this.moreSummons == 0) 
			{ 
				this.moreSummons = this.baseSummons + this.extraSummonsForThisTurn; 				
			}
			int tmp = this.moreSummons;
			for (final AbstractPower p : AbstractDungeon.player.powers) 
			{
				if (p instanceof DuelistPower)
				{
					DuelistPower pow = (DuelistPower)p;
					tmp = pow.modifySummons(tmp, this);
				}
			}
			
			for (final AbstractPotion p : AbstractDungeon.player.potions) 
			{
				if (p instanceof DuelistPotion)
				{
					DuelistPotion pow = (DuelistPotion)p;
					tmp = pow.modifySummons(tmp, this);
				}
			}
			
			for (final AbstractOrb p : AbstractDungeon.player.orbs) 
			{
				if (p instanceof DuelistOrb)
				{
					DuelistOrb pow = (DuelistOrb)p;
					tmp = pow.modifySummons(tmp, this);
				}
			}
			
			for (final AbstractRelic p : AbstractDungeon.player.relics) 
			{
				if (p instanceof DuelistRelic)
				{
					DuelistRelic pow = (DuelistRelic)p;
					tmp = pow.modifySummons(tmp, this);
				}
			}
			if (AbstractDungeon.player.stance instanceof DuelistStance)
			{
				DuelistStance stance = (DuelistStance)AbstractDungeon.player.stance;
				tmp = stance.modifySummons(tmp, this);
			}
			if (this.summons != MathUtils.floor(tmp)) 
			{
				this.isSummonsModified = true;
			}
			if (tmp < 0) 
			{
				tmp = 0;
			}		
			this.summonsForTurn = this.summons = MathUtils.floor(tmp);
		}
		else
		{
			this.isSummonsModified = false;
			int tmp = this.baseSummons;
			for (final AbstractPower p : AbstractDungeon.player.powers) 
			{
				if (p instanceof DuelistPower)
				{
					DuelistPower pow = (DuelistPower)p;
					tmp = pow.modifySummons(tmp, this);
				}
			}
			
			for (final AbstractPotion p : AbstractDungeon.player.potions) 
			{
				if (p instanceof DuelistPotion)
				{
					DuelistPotion pow = (DuelistPotion)p;
					tmp = pow.modifySummons(tmp, this);
				}
			}
			
			for (final AbstractOrb p : AbstractDungeon.player.orbs) 
			{
				if (p instanceof DuelistOrb)
				{
					DuelistOrb pow = (DuelistOrb)p;
					tmp = pow.modifySummons(tmp, this);
				}
			}
			
			for (final AbstractRelic p : AbstractDungeon.player.relics) 
			{
				if (p instanceof DuelistRelic)
				{
					DuelistRelic pow = (DuelistRelic)p;
					tmp = pow.modifySummons(tmp, this);
				}
			}
			if (AbstractDungeon.player.stance instanceof DuelistStance)
			{
				DuelistStance stance = (DuelistStance)AbstractDungeon.player.stance;
				tmp = stance.modifySummons(tmp, this);
			}
			if (this.baseSummons != MathUtils.floor(tmp)) 
			{
				this.isSummonsModified = true;
			}
			if (tmp < 0) 
			{
				tmp = 0;
			}		
			this.summons = MathUtils.floor(tmp);
		}
	}
	
	public void applyPowersToTributes()
	{
		if (this.isTributesModifiedForTurn)
		{
			if (this.moreTributes == 0) 
			{ 
				this.moreTributes = this.baseTributes + this.extraTributesForThisTurn; 				
			}
			int tmp = this.moreTributes;
			for (final AbstractPower p : AbstractDungeon.player.powers) 
			{
				if (p instanceof DuelistPower)
				{
					DuelistPower pow = (DuelistPower)p;
					tmp = pow.modifyTributes(tmp, this);
				}
			}
			
			for (final AbstractPotion p : AbstractDungeon.player.potions) 
			{
				if (p instanceof DuelistPotion)
				{
					DuelistPotion pow = (DuelistPotion)p;
					tmp = pow.modifyTributes(tmp, this);
				}
			}
			
			for (final AbstractOrb p : AbstractDungeon.player.orbs) 
			{
				if (p instanceof DuelistOrb)
				{
					DuelistOrb pow = (DuelistOrb)p;
					tmp = pow.modifyTributes(tmp, this);
				}
			}
			
			for (final AbstractRelic p : AbstractDungeon.player.relics) 
			{
				if (p instanceof DuelistRelic)
				{
					DuelistRelic pow = (DuelistRelic)p;
					tmp = pow.modifyTributes(tmp, this);
				}
			}
			if (AbstractDungeon.player.stance instanceof DuelistStance)
			{
				DuelistStance stance = (DuelistStance)AbstractDungeon.player.stance;
				tmp = stance.modifyTributes(tmp, this);
			}
			if (this.tributes != MathUtils.floor(tmp)) 
			{
				this.isTributesModified = true;
			}
			if (tmp < 0) 
			{
				tmp = 0;
			}		
			this.tributesForTurn = this.tributes = MathUtils.floor(tmp);
		}
		else
		{
			this.isTributesModified = false;
			int tmp = this.baseTributes;
			for (final AbstractPower p : AbstractDungeon.player.powers) 
			{
				if (p instanceof DuelistPower)
				{
					DuelistPower pow = (DuelistPower)p;
					tmp = pow.modifyTributes(tmp, this);
				}
			}
			
			for (final AbstractPotion p : AbstractDungeon.player.potions) 
			{
				if (p instanceof DuelistPotion)
				{
					DuelistPotion pow = (DuelistPotion)p;
					tmp = pow.modifyTributes(tmp, this);
				}
			}
			
			for (final AbstractOrb p : AbstractDungeon.player.orbs) 
			{
				if (p instanceof DuelistOrb)
				{
					DuelistOrb pow = (DuelistOrb)p;
					tmp = pow.modifyTributes(tmp, this);
				}
			}
			
			for (final AbstractRelic p : AbstractDungeon.player.relics) 
			{
				if (p instanceof DuelistRelic)
				{
					DuelistRelic pow = (DuelistRelic)p;
					tmp = pow.modifyTributes(tmp, this);
				}
			}
			if (AbstractDungeon.player.stance instanceof DuelistStance)
			{
				DuelistStance stance = (DuelistStance)AbstractDungeon.player.stance;
				tmp = stance.modifyTributes(tmp, this);
			}
			if (this.baseTributes != MathUtils.floor(tmp)) 
			{
				this.isTributesModified = true;
			}
			if (tmp < 0) 
			{
				tmp = 0;
			}		
			this.tributes = MathUtils.floor(tmp);
		}
	}

	public void applyPowersToMagicNumber()
	{
		boolean wasMagicTrueAlready = this.isMagicNumberModified;
		this.isMagicNumberModified = false;
		float tmp = (float)this.baseMagicNumber;
		for (final AbstractPower p : AbstractDungeon.player.powers) 
		{
			if (p instanceof DuelistPower)
			{
				DuelistPower pow = (DuelistPower)p;
				tmp = pow.modifyMagicNumber(tmp, this);
			}
		}
		
		for (final AbstractPotion p : AbstractDungeon.player.potions) 
		{
			if (p instanceof DuelistPotion)
			{
				DuelistPotion pow = (DuelistPotion)p;
				tmp = pow.modifyMagicNumber(tmp, this);
			}
		}
		
		for (final AbstractOrb p : AbstractDungeon.player.orbs) 
		{
			if (p instanceof DuelistOrb)
			{
				DuelistOrb pow = (DuelistOrb)p;
				tmp = pow.modifyMagicNumber(tmp, this);
			}
		}
		
		for (final AbstractRelic p : AbstractDungeon.player.relics) 
		{
			if (p instanceof DuelistRelic)
			{
				DuelistRelic pow = (DuelistRelic)p;
				tmp = pow.modifyMagicNumber(tmp, this);
			}
		}
		if (AbstractDungeon.player.stance instanceof DuelistStance)
		{
			DuelistStance stance = (DuelistStance)AbstractDungeon.player.stance;
			tmp = stance.modifyMagicNumber(tmp, this);
		}
		if (this.magicNumber != MathUtils.floor(tmp) || wasMagicTrueAlready) 
		{
			this.isMagicNumberModified = true;
		}
		if (tmp < 0.0f) 
		{
			tmp = 0.0f;
		}
		this.magicNumber = MathUtils.floor(tmp);
	}
	
	public void applyPowersToEntomb()
	{
		boolean wasMagicTrueAlready = this.isEntombModified;
		this.isEntombModified = false;
		float tmp = (float)this.baseEntomb;
		for (final AbstractPower p : AbstractDungeon.player.powers) 
		{
			if (p instanceof DuelistPower)
			{
				DuelistPower pow = (DuelistPower)p;
				tmp = pow.modifyEntomb(tmp, this);
			}
		}
		
		for (final AbstractPotion p : AbstractDungeon.player.potions) 
		{
			if (p instanceof DuelistPotion)
			{
				DuelistPotion pow = (DuelistPotion)p;
				tmp = pow.modifyEntomb(tmp, this);
			}
		}
		
		for (final AbstractOrb p : AbstractDungeon.player.orbs) 
		{
			if (p instanceof DuelistOrb)
			{
				DuelistOrb pow = (DuelistOrb)p;
				tmp = pow.modifyEntomb(tmp, this);
			}
		}
		
		for (final AbstractRelic p : AbstractDungeon.player.relics) 
		{
			if (p instanceof DuelistRelic)
			{
				DuelistRelic pow = (DuelistRelic)p;
				tmp = pow.modifyEntomb(tmp, this);
			}
		}
		if (AbstractDungeon.player.stance instanceof DuelistStance)
		{
			DuelistStance stance = (DuelistStance)AbstractDungeon.player.stance;
			tmp = stance.modifyEntomb(tmp, this);
		}
		if (this.entomb != MathUtils.floor(tmp) || wasMagicTrueAlready) 
		{
			this.isEntombModified = true;
		}
		if (tmp < 0.0f) 
		{
			tmp = 0.0f;
		}
		this.entomb = MathUtils.floor(tmp);
	}
	
	public void applyPowersToSecondMagicNumber()
	{
		boolean wasMagicTrueAlready = this.isSecondMagicModified;
		this.isSecondMagicModified = false;
		float tmp = (float)this.baseSecondMagic;
		for (final AbstractPower p : AbstractDungeon.player.powers) 
		{
			if (p instanceof DuelistPower)
			{
				DuelistPower pow = (DuelistPower)p;
				tmp = pow.modifySecondMagicNumber(tmp, this);
			}
		}
		
		for (final AbstractPotion p : AbstractDungeon.player.potions) 
		{
			if (p instanceof DuelistPotion)
			{
				DuelistPotion pow = (DuelistPotion)p;
				tmp = pow.modifySecondMagicNumber(tmp, this);
			}
		}
		
		for (final AbstractOrb p : AbstractDungeon.player.orbs) 
		{
			if (p instanceof DuelistOrb)
			{
				DuelistOrb pow = (DuelistOrb)p;
				tmp = pow.modifySecondMagicNumber(tmp, this);
			}
		}
		
		for (final AbstractRelic p : AbstractDungeon.player.relics) 
		{
			if (p instanceof DuelistRelic)
			{
				DuelistRelic pow = (DuelistRelic)p;
				tmp = pow.modifySecondMagicNumber(tmp, this);
			}
		}
		if (AbstractDungeon.player.stance instanceof DuelistStance)
		{
			DuelistStance stance = (DuelistStance)AbstractDungeon.player.stance;
			tmp = stance.modifySecondMagicNumber(tmp, this);
		}
		if (this.secondMagic != MathUtils.floor(tmp) || wasMagicTrueAlready) 
		{
			this.isSecondMagicModified = true;
		}
		if (tmp < 0.0f) 
		{
			tmp = 0.0f;
		}
		this.secondMagic = MathUtils.floor(tmp);
	}
	
	public void applyPowersToThirdMagicNumber()
	{
		boolean wasMagicTrueAlready = this.isThirdMagicModified;
		this.isThirdMagicModified = false;
		float tmp = (float)this.baseThirdMagic;
		for (final AbstractPower p : AbstractDungeon.player.powers) 
		{
			if (p instanceof DuelistPower)
			{
				DuelistPower pow = (DuelistPower)p;
				tmp = pow.modifyThirdMagicNumber(tmp, this);
			}
		}
		
		for (final AbstractPotion p : AbstractDungeon.player.potions) 
		{
			if (p instanceof DuelistPotion)
			{
				DuelistPotion pow = (DuelistPotion)p;
				tmp = pow.modifyThirdMagicNumber(tmp, this);
			}
		}
		
		for (final AbstractOrb p : AbstractDungeon.player.orbs) 
		{
			if (p instanceof DuelistOrb)
			{
				DuelistOrb pow = (DuelistOrb)p;
				tmp = pow.modifyThirdMagicNumber(tmp, this);
			}
		}
		
		for (final AbstractRelic p : AbstractDungeon.player.relics) 
		{
			if (p instanceof DuelistRelic)
			{
				DuelistRelic pow = (DuelistRelic)p;
				tmp = pow.modifyThirdMagicNumber(tmp, this);
			}
		}
		if (AbstractDungeon.player.stance instanceof DuelistStance)
		{
			DuelistStance stance = (DuelistStance)AbstractDungeon.player.stance;
			tmp = stance.modifyThirdMagicNumber(tmp, this);
		}
		if (this.thirdMagic != MathUtils.floor(tmp) || wasMagicTrueAlready) 
		{
			this.isThirdMagicModified = true;
		}
		if (tmp < 0.0f) 
		{
			tmp = 0.0f;
		}
		this.thirdMagic = MathUtils.floor(tmp);
	}
	
    @Override
    public void triggerOnEndOfPlayerTurn() 
    {
    	// If overflows remaining
    	if (checkMagicNum() > 0 && this.hasTag(Tags.IS_OVERFLOW)) 
    	{
    		// Remove 1 overflow
    		this.addToTop(new OverflowDecrementMagicAction(this, -1));

    		// Heal
    		int overflows = 1;
    		triggerOverflowEffect();
    		if (player().hasPower(MakoBlessingPower.POWER_ID)) 
    		{ 
    			int amt = player().getPower(MakoBlessingPower.POWER_ID).amount;
    			for (int i = 0; i < amt; i++) { triggerOverflowEffect(); }
    			overflows += amt;
    		}
    		handleOnOverflowForAllAbstracts(overflows);
    	}
    	super.triggerOnEndOfPlayerTurn();
    }
    
    public void triggerOverflowConditional() 
	{ 
		// If overflows remaining
    	if (checkMagicNum() > 0 && this.hasTag(Tags.IS_OVERFLOW)) 
    	{
    		// Remove 1 overflow
    		this.addToTop(new OverflowDecrementMagicAction(this, -1));

    		// Heal
    		int overflows = 1;
    		triggerOverflowEffect();
    		if (player().hasPower(MakoBlessingPower.POWER_ID)) 
    		{ 
    			int amt = player().getPower(MakoBlessingPower.POWER_ID).amount;
    			for (int i = 0; i < amt; i++) { triggerOverflowEffect(); }
    			overflows += amt;
    		}
    		handleOnOverflowForAllAbstracts(overflows);
    	}
	}
	
	@Override
	public AbstractCard makeStatEquivalentCopy()
	{
		AbstractCard card = super.makeStatEquivalentCopy();
		if (card instanceof DuelistCard)
		{
			DuelistCard dCard = (DuelistCard)card;
			dCard.tributes = this.tributes;
			dCard.summons = this.summons;
			dCard.isTributesModified = this.isTributesModified;
			dCard.isSummonsModified = this.isSummonsModified;
			dCard.isTributesModifiedForTurn = this.isTributesModifiedForTurn;
			dCard.isMagicNumModifiedForTurn = this.isMagicNumModifiedForTurn;
			dCard.isSummonsModifiedForTurn = this.isSummonsModifiedForTurn;
			dCard.extraSummonsForThisTurn = this.extraSummonsForThisTurn;
			dCard.extraTributesForThisTurn = this.extraTributesForThisTurn;
			dCard.moreSummons = this.moreSummons;
			dCard.moreTributes = this.moreTributes;
			dCard.originalMagicNumber = this.originalMagicNumber;
			dCard.inDuelistBottle = this.inDuelistBottle;
			dCard.baseTributes = this.baseTributes;
			dCard.baseSummons = this.baseSummons;
			dCard.isSummonModPerm = this.isSummonModPerm;
			dCard.isTribModPerm = this.isTribModPerm;
			dCard.exhaust = this.exhaust;
			dCard.isEthereal = this.isEthereal;
			dCard.originalDescription = this.originalDescription;
			ArrayList<CardTags> monsterTags = getAllMonsterTypes(this);
			dCard.tags.addAll(monsterTags);
			dCard.savedTypeMods = this.savedTypeMods;
			dCard.permCostChange = this.permCostChange;
			dCard.permSummonChange = this.permSummonChange;
			dCard.permTribChange = this.permTribChange;
			//dCard.baseDamage = this.baseDamage;
			if (this.hasTag(Tags.MEGATYPED)) { dCard.tags.add(Tags.MEGATYPED); }			
			if (this.hasTag(Tags.UNDEAD)) { dCard.tags.add(Tags.UNDEAD); }
		}
		return card;
	}
	
	public void permUpdateCost(int amt)
	{
		this.updateCost(amt);
		this.permCostChange = amt;
	}
	
	@Override
	public void resetAttributes()
	{
		super.resetAttributes();
	}
	
	@Override
	public void onPlayCard(final AbstractCard c, final AbstractMonster m) 
	{
		super.onPlayCard(c, m);
		if (c.hasTag(Tags.DRAGON) && c.uuid.equals(this.uuid))
		{
			if (c.target.equals(CardTarget.ENEMY))
			{
				int burnRoll = AbstractDungeon.cardRandomRng.random(1, 3);
				if (AbstractDungeon.player.hasRelic(DragonBurnRelic.ID)) { burnRoll = 1; }
				if (burnRoll == 1 && m != null && !m.isDead && !m.isDying && !m.isDeadOrEscaped() && !m.halfDead) 
				{ 
					int burningRoll = AbstractDungeon.cardRandomRng.random(1, 4);
					applyPower(new BurningDebuff(m, AbstractDungeon.player, burningRoll), m); 
				}
			}
			else if (c.target.equals(CardTarget.ALL_ENEMY))
			{
				int enemies = getAllMons().size();
				int burnRoll = AbstractDungeon.cardRandomRng.random(1, 3 + enemies);
				if (AbstractDungeon.player.hasRelic(DragonBurnRelic.ID)) { burnRoll = 1; }
				if (burnRoll == 1) 
				{ 
					int burningRoll = AbstractDungeon.cardRandomRng.random(2, 5);
					DuelistCard.burnAllEnemies(burningRoll); 
				}					
			}
		}
		
		if (c.hasTag(Tags.MACHINE) && c.uuid.equals(this.uuid))
		{
			if (c.target.equals(CardTarget.ENEMY))
			{
				int burnRoll = AbstractDungeon.cardRandomRng.random(1, 6);
				if (burnRoll == 1 && m != null && !m.isDead && !m.isDying && !m.isDeadOrEscaped() && !m.halfDead) 
				{ 
					int burningRoll = 1;
					applyPower(new GreasedDebuff(m, AbstractDungeon.player, burningRoll), m); 
				}
			}
			else if (c.target.equals(CardTarget.ALL_ENEMY))
			{
				int enemies = getAllMons().size();
				int burnRoll = AbstractDungeon.cardRandomRng.random(1, 6 + enemies);
				if (AbstractDungeon.player.hasRelic(DragonBurnRelic.ID)) { burnRoll = 1; }
				if (burnRoll == 1) 
				{ 
					int burningRoll = 1;
					DuelistCard.greaseAllEnemies(burningRoll); 
				}					
			}
		}
		
		if (c.hasTag(Tags.AQUA) && c.uuid.equals(this.uuid))
		{
			boolean hasLO = player().hasPower(LegendaryOceanPower.POWER_ID);
			if (c.target.equals(CardTarget.ENEMY))
			{
				int burnRoll = AbstractDungeon.cardRandomRng.random(1, 5);
				int freezeRoll = AbstractDungeon.cardRandomRng.random(1, 10);
				if (m != null && !m.isDead && !m.isDying && !m.isDeadOrEscaped() && !m.halfDead)
				{
					if (burnRoll == 1) 
					{ 
						int burningRoll = AbstractDungeon.cardRandomRng.random(1, 2);
						applyPower(new DampDebuff(m, AbstractDungeon.player, burningRoll), m); 
					}
					
					if (freezeRoll < 5 && hasLO) 
					{ 						
						applyPower(new FrozenDebuff(m, AbstractDungeon.player), m); 
					}
				}
			}
			else if (c.target.equals(CardTarget.ALL_ENEMY))
			{
				int enemies = getAllMons().size();
				int burnRoll = AbstractDungeon.cardRandomRng.random(1, 4 + enemies);	
				int freezeRoll = AbstractDungeon.cardRandomRng.random(1, 9 + enemies);
				if (burnRoll == 1) 
				{ 
					int burningRoll = AbstractDungeon.cardRandomRng.random(1, 4);
					dampAllEnemies(burningRoll); 
				}	
				
				if (freezeRoll < 5 && hasLO)
				{
					freezeAllEnemies();
				}
			}
		}
    }

	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) 
	{
		if (DuelistMod.debug) 
		{
			AbstractCard temp = duelistCardModal.getCard(arg2);
			AbstractCard tempB = cardModal.getCard(arg2);
			AbstractCard tempC = orbModal.getCard(arg2);
			if (DuelistMod.debug)
			{
				if (temp != null && tempB != null && tempC != null) { System.out.println("theDuelist:DuelistCard:optionSelected() ---> can I see the card we picked? the card should be one of these three:: [Duelist Modal]: " + temp.originalName + ", [CardModal]: " + tempB.originalName + ", [OrbModal]: " + tempC.originalName); }
				else { System.out.println("theDuelist:DuelistCard:optionSelected() ---> one of the modal cards was null, so we printed this unhelpful statement. sorry."); }
			}
		}
	}
	
	@Override
    public void triggerOnGlowCheck() 
    {
    	super.triggerOnGlowCheck();
    	if (this.fiendDeckDmgMod) {
    		this.glowColor = Color.RED;
        }
    }
	// =============== /SUPER OVERRIDE FUNCTIONS/ =======================================================================================================================================================
	
	
	
	// =============== DUELIST FUNCTIONS =========================================================================================================================================================
	@Override
	public String onSave()
	{
		String saveAttributes = "";
		saveAttributes += this.permTribChange + "~";
		saveAttributes += this.permSummonChange + "~";
		saveAttributes += DuelistMod.archRoll1 + "~";
		saveAttributes += DuelistMod.archRoll2 + "~";
		saveAttributes += this.permCostChange + "~";
		for (String s : this.savedTypeMods) { saveAttributes += s + "~"; }
		return saveAttributes;
	}
	
	@Override
	public void onLoad(String attributeString)
	{
		// If no saved string, just return
		if (attributeString == null) { return; }
		
		// Otherwise, get the saved string and split it into components
		try
		{
			String[] savedStrings = attributeString.split("~");
			ArrayList<String> savedTypes = new ArrayList<String>();
			String[] savedIntegers = new String[5];
			
			// Get the first 4 strings and convert back to int (perm tribute changes, perm summon changes, random pool archetype 1 & 2)
			
			for (int i = 0; i < 5; i++) { savedIntegers[i] = savedStrings[i]; }
			try
			{
				int[] ints = Arrays.stream(savedIntegers).mapToInt(Integer::parseInt).toArray();
				
				// Now look for any saved type modifications
				for (int j = 5; j < savedStrings.length - 1; j++) 
				{
					savedTypes.add(savedStrings[j]);
				}
				
				// Now apply saved values to the card
				if (ints[0] != 0)
				{
					this.modifyTributesPerm(ints[0]);
				}
				
				if (ints[1] != 0)
				{
					this.modifySummonsPerm(ints[1]);
				}
				
				if (ints[2] > -1)
				{
					DuelistMod.archRoll1 = ints[2];
				}
				
				if (ints[3] > -1)
				{
					DuelistMod.archRoll2 = ints[3];
				}
				
				if (ints[4] > 999)
				{
					this.permUpdateCost(ints[4]);
					this.initializeDescription();
				}
				
				if (!(savedTypes.contains("default"))) 
				{
					this.savedTypeMods = new ArrayList<String>();
					for (String s : savedTypes)
					{
						this.savedTypeMods.add(s);
						if (s.equals("Megatyped")) { this.makeMegatyped(); }
						else { this.tags.add(DuelistMod.typeCardMap_NameToString.get(s)); }
						this.rawDescription = this.rawDescription + " NL " + s;
					}
					this.originalDescription = this.rawDescription;
					this.isTypeAddedPerm = true;
					this.initializeDescription();
				}
		
				if (DuelistMod.debug) 
				{ 
					System.out.println(this.originalName + " loaded this string: [" + attributeString + "]");
					int counter = 0;
					for (int i : ints)
					{
						System.out.println("ints[" + counter + "]: " + i);
						counter++;
					}
				}
				
				if (StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Exodia Deck"))
				{
					this.makeSoulbound(true);
					this.rawDescription = "Soulbound NL " + this.rawDescription;
					this.initializeDescription();
				}
			} 
			catch(NumberFormatException e)
			{
				Util.log("NumberFormatException when loading DuelistCard properties.. this is probably due to an update in the way DuelistCard objects are saved/loaded since the last time you played this save file. You will need to Abadon your Run.");
				e.printStackTrace();
			}
		} catch(PatternSyntaxException e)
		{
			Util.log("PatternSyntaxException when loading DuelistCard properties.. this is probably due to an update in the way DuelistCard objects are saved/loaded since the last time you played this save file. You will need to Abadon your Run.");
			e.printStackTrace();
		}
	}
	
	@Override
	public Type savedType()
	{
		return String.class;
	}
	
	protected static AbstractPlayer player() {
		return AbstractDungeon.player;
	}
	
	public DuelistCard getCard()
	{
		return this;
	}
	
	protected void upgradeName(String newName) 
	{
		this.timesUpgraded += 1;
		this.upgraded = true;
		this.name = newName;
		initializeTitle();
	}
	
	public void setupStartingCopies()
	{
		this.startCopies = new ArrayList<Integer>();
		this.startCopies.add(this.standardDeckCopies);	
		this.startCopies.add(this.dragonDeckCopies); 		
		this.startCopies.add(this.natureDeckCopies); 		
		this.startCopies.add(this.spellcasterDeckCopies); 	
		this.startCopies.add(this.toonDeckCopies); 			
		this.startCopies.add(this.zombieDeckCopies); 		
		this.startCopies.add(this.aquaDeckCopies); 			
		this.startCopies.add(this.fiendDeckCopies); 		
		this.startCopies.add(this.machineDeckCopies); 		
		this.startCopies.add(this.superheavyDeckCopies); 	
		this.startCopies.add(this.insectDeckCopies); 		
		this.startCopies.add(this.plantDeckCopies); 		
		this.startCopies.add(this.predaplantDeckCopies); 	
		this.startCopies.add(this.megatypeDeckCopies); 			
		this.startCopies.add(this.incrementDeckCopies); 		
		this.startCopies.add(this.creatorDeckCopies);		
		this.startCopies.add(this.ojamaDeckCopies);		
		this.startCopies.add(this.exodiaDeckCopies); 	
		this.startCopies.add(this.giantDeckCopies); 
		this.startCopies.add(this.a1DeckCopies); 
		this.startCopies.add(this.a2DeckCopies); 
		this.startCopies.add(this.a3DeckCopies); 
		this.startCopies.add(this.p1DeckCopies); 
		this.startCopies.add(this.p2DeckCopies); 
		this.startCopies.add(this.p3DeckCopies);
		this.startCopies.add(this.p4DeckCopies); 
		this.startCopies.add(this.p5DeckCopies);
		this.startCopies.add(this.metronomeDeckCopies);
	}
	
	public void customOnTribute(DuelistCard tc) { }

	public void startBattleReset()
	{
		if (this.isTribModPerm)
		{
			this.rawDescription = this.originalDescription;
			this.initializeDescription();
		}
		
		if (this.isSummonModPerm)
		{
			this.rawDescription = this.originalDescription;
			this.initializeDescription();
		}
		
		//if (this.isTypeAddedPerm)
		//{
		//	this.rawDescription = this.originalDescription;
		//	this.initializeDescription();
		//}
	}

	public void postTurnReset()
	{
		if (this.isTributesModifiedForTurn)
		{
			this.isTributesModifiedForTurn = false;
			this.isTributesModified = false;
			this.tributes = this.baseTributes;
			this.moreTributes = 0;
			this.extraTributesForThisTurn = 0;
			this.rawDescription = this.originalDescription;
			this.initializeDescription();
		}
		
		if (this.isSummonsModifiedForTurn)
		{		
			this.isSummonsModifiedForTurn = false;
			this.isSummonsModified = false;
			this.summons = this.baseSummons;
			this.moreSummons = 0;
			this.extraSummonsForThisTurn = 0;
			this.rawDescription = this.originalDescription;
			this.initializeDescription();
		}
		
		if (this.isMagicNumModifiedForTurn)
		{
			this.isMagicNumModifiedForTurn = false;
			this.magicNumber = this.baseMagicNumber = this.originalMagicNumber;
			this.initializeDescription();
		}
	}
	
	
	// UNUSED
	public static boolean purgeCard(AbstractCard toPurge) {
		return purgeCard(toPurge.uuid);
	}

	private static boolean purgeCard(UUID targetUUID) {
		for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
			if (c.uuid.equals(targetUUID)) {
				AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, Settings.WIDTH / 2, Settings.HEIGHT / 2));
				AbstractDungeon.player.masterDeck.removeCard(c);
				return true;
			}
		}
		return false;
	}
	
	public void initializeNumberedCard() {
		playCount = 0;
	}

	public void addPlayCount() {
		for (AbstractCard c : GetAllInBattleInstances.get(this.uuid)) 
		{
			if (c instanceof DuelistCard)
			{
				DuelistCard nc = (DuelistCard) c;
				nc.playCount++;
			}
		}
	}

	// Called by Lava orbs when evoked, override for special behavior when the card is in your hand during Lava orb evoke effect
	// return value is added to evoke damage when the orb is checking this card (so not extra damage for each card in hand, but only extra damage from this card)
	public int lavaEvokeEffect() { return 0; }
	
	// Called by White orbs on Spells/Traps that are being upgraded for the passive effect
	// Implement for extra behavior after the upgrade
	public void whiteOrbPassiveTrigger() {}

	// Called by White orbs on all duelist cards that are being upgraded for the evoke effect
	// Implement for extra behavior after the upgrade
	public void whiteOrbEvokeTrigger() {}

	// END UNUSED
	// =============== /DUELIST FUNCTIONS/ =======================================================================================================================================================
	
	
	
	// =============== ATTACK FUNCTIONS =========================================================================================================================================================
	public void attack(AbstractMonster m)
	{
		if (this.hasTag(Tags.DRAGON)) { this.baseAFX = AttackEffect.FIRE; }
		attack(m, this.baseAFX, this.damage);
	}
	
	public void thornAttack(AbstractMonster m)
	{
		
		thornAttack(m, this.baseAFX, this.damage);
	}
	
	public void thornAttack(AbstractMonster m, int dmg)
	{

		thornAttack(m, this.baseAFX, dmg);
	}
	
	public void thornAttack(AbstractMonster m, AttackEffect effect, int damageAmount) 
	{		
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(player(), damageAmount, DamageType.THORNS), effect));
	}
	
	public static void staticThornAttack(AbstractMonster m, AttackEffect effect, int damageAmount)
	{
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(player(), damageAmount, DamageType.THORNS), effect));
	}
	
	public static void vinesAttack(AbstractMonster m, int dmg)
	{
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(player(), dmg, DamageType.THORNS), AttackEffect.POISON));
	}
	
	public void attack(AbstractMonster m, AttackEffect effect, int damageAmount) 
	{
		stampedeHandler(m, damageAmount);
		recklessHandler(m, damageAmount);
		if (this.hasTag(Tags.DRAGON)) { effect = AttackEffect.FIRE; }
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(player(), damageAmount, damageTypeForTurn), effect));
	}
	
	public void specialAttack(AbstractMonster m, AttackEffect afx, int dmg)
	{
		AbstractDungeon.actionManager.addToBottom(new DuelistDamageAction(m, new DamageInfo(player(), dmg, damageTypeForTurn), afx));
	}
	
	// Flying Pegasus
	public static void staticAttack(AbstractMonster m, AttackEffect effect, int damageAmount) 
	{
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(player(), damageAmount, DamageType.THORNS), effect));
	}
	
	public void attackFast(AbstractMonster m, AttackEffect effect, int damageAmount)
	{
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(player(), damageAmount, damageTypeForTurn), effect, true));
	}
	
	public int attackMultipleRandom(int amountOfEnemiesToAttack, AttackEffect afx, DamageType dmgType)
	{
		return attackMultipleRandom(this.damage, amountOfEnemiesToAttack, afx, dmgType);
	}
	
	public int attackMultipleRandom(int amountOfEnemiesToAttack)
	{
		return attackMultipleRandom(this.damage, amountOfEnemiesToAttack, this.baseAFX, DamageType.NORMAL);
	}
	
	public static void constrictMultipleRandom(int constricted, int amountOfEnemies)
	{
		ArrayList<AbstractMonster> allEnemies = new ArrayList<AbstractMonster>();
		for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters)
		{
			if (!m.isDead && !m.isDying && !m.isDeadOrEscaped() && !m.halfDead) { allEnemies.add(m); }
		}
		
		if (amountOfEnemies >= allEnemies.size()) 
		{
			for (AbstractMonster m : allEnemies)
			{
				applyPower(new ConstrictedPower(m, AbstractDungeon.player, constricted), m);
			}
		}
		else
		{
			for (int i = 0; i < allEnemies.size() - amountOfEnemies; i++)
			{
				allEnemies.remove(AbstractDungeon.cardRandomRng.random(allEnemies.size() - 1));
			}
			
			for (AbstractMonster m : allEnemies)
			{
				applyPower(new ConstrictedPower(m, AbstractDungeon.player, constricted), m);
			}
		}
	}
	
	public int attackMultipleRandom(int damage, int amountOfEnemiesToAttack, AttackEffect afx, DamageType dmgType)
	{
		if (this.hasTag(Tags.DRAGON)) { afx = AttackEffect.FIRE; }
		ArrayList<AbstractMonster> allEnemies = new ArrayList<AbstractMonster>();
		ArrayList<AbstractMonster> nonTargeted = new ArrayList<AbstractMonster>();
		for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters)
		{
			if (!m.isDead && !m.isDying && !m.isDeadOrEscaped() && !m.halfDead) { allEnemies.add(m); }
		}
		
		if (amountOfEnemiesToAttack >= allEnemies.size()) 
		{
			for (AbstractMonster m : allEnemies)
			{
				AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(player(), damage, dmgType), afx));
			}
			return allEnemies.size();
		}
		else
		{
			while (allEnemies.size() > amountOfEnemiesToAttack)
			{
				int index = AbstractDungeon.cardRandomRng.random(allEnemies.size() - 1);
				Util.log("attackMultipleRandom() is removing " + allEnemies.get(index).name + " from allEnemies");
				nonTargeted.add(allEnemies.get(index));
				allEnemies.remove(index);
			}
			
			for (AbstractMonster m : allEnemies)
			{
				Util.log("attackMultipleRandom() -- still remaining in allEnemies: " + m.name);
				AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(player(), damage, dmgType), afx));
			}
			
			if (allEnemies.size() != amountOfEnemiesToAttack) { Util.log("attackMultipleRandom() got different values for allEnemies.size() and amountOfEnemiesToAttack! allEnemies.size()=" + allEnemies.size() + " -- amountOfEnemiesToAttack=" + amountOfEnemiesToAttack); }
			if (nonTargeted.size() > 0)
			{
				if (damage == 0) { nonTargeted.addAll(allEnemies); }
				stampedeHandler(nonTargeted);
				recklessHandler(nonTargeted);
			}
			return allEnemies.size();
		}
	}

	public static void attackAll(AttackEffect effect, int[] damageAmounts, DamageType dmgForTurn)
	{
		AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player(), damageAmounts, dmgForTurn, effect));
	}
	
	public void normalMultidmg()
	{
		if (this.hasTag(Tags.DRAGON)) { this.baseAFX = AttackEffect.FIRE; }
		this.addToBot(new DamageAllEnemiesAction(player(), this.multiDamage, this.damageTypeForTurn, this.baseAFX));
	}
	
	public static void attackAll(int damage)
	{
		int[] damageArray = new int[] { damage, damage, damage, damage, damage, damage, damage, damage, damage, damage };
		attackAll(AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, damageArray, DamageType.NORMAL);
	}
	
	public void attackAllEnemies(int deprecated)
	{
		int[] damageArray = new int[] { damage, damage, damage, damage, damage, damage, damage, damage, damage, damage };
		attackAll(AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, damageArray, DamageType.NORMAL);
	}
	
	public void attackAllEnemies(AttackEffect afx)
	{
		int[] damageArray = new int[] { damage, damage, damage, damage, damage, damage, damage, damage, damage, damage };
		attackAll(afx, damageArray, DamageType.NORMAL);
	}

	public static void attackAllEnemiesThorns(int damage)
	{
		int[] damageArray = new int[] { damage, damage, damage, damage, damage, damage, damage, damage, damage, damage };
		attackAll(AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, damageArray, DamageType.THORNS);
	}
	
	public static void exodiaAttack(int damage)
	{
		AbstractPlayer p = AbstractDungeon.player;
		int[] damageArray = new int[] { damage, damage, damage, damage, damage, damage, damage, damage, damage, damage };
		AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
		AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new MindblastEffect(p.dialogX, p.dialogY, p.flipHorizontal), 0.1F));
		attackAll(AbstractGameAction.AttackEffect.NONE, damageArray, DamageType.THORNS);
	}	
	
	public static void attackAllEnemiesFireThorns(int damage)
	{
		int[] damageArray = new int[] { damage, damage, damage, damage, damage, damage, damage, damage, damage, damage };
		attackAll(AbstractGameAction.AttackEffect.FIRE, damageArray, DamageType.THORNS);
	}

	public void damageThroughBlock(AbstractCreature m, AbstractPlayer p, int damage, AttackEffect effect)
	{
		if (this.hasTag(Tags.DRAGON) && player().hasPower(TyrantWingPower.POWER_ID)) 
		{  
			TwoAmountPower power = (TwoAmountPower) player().getPower(TyrantWingPower.POWER_ID);
			power.amount2--;
			power.updateDescription();
		}
		// Record target block and remove all of it
		int targetArmor = m.currentBlock;
		if (targetArmor > 0) { AbstractDungeon.actionManager.addToTop(new RemoveAllBlockAction(m, m)); }

		// Deal direct damage to target HP
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), effect));

		// Restore original target block
		if (targetArmor > 0) { AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m, m, targetArmor)); }
	}

	public void damageThroughBlockAllEnemies(AbstractPlayer p, int damage, AttackEffect effect)
	{
		ArrayList<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
		for (AbstractMonster m : monsters)
		{
			if (!m.isDead && !m.halfDead && !m.isDying && !m.isDeadOrEscaped()) 
			{ 
				damageThroughBlock(m, p, damage, effect); 
			}
		}
	}

	public static void damageAllEnemiesThornsPoison(int damage)
	{
		int[] damageArray = new int[] { damage, damage, damage, damage, damage, damage, damage, damage, damage, damage };
		attackAll(AbstractGameAction.AttackEffect.POISON, damageArray, DamageType.THORNS);
	}
	
	public static void damageAllEnemiesThornsNormal(int damage)
	{
		int[] damageArray = new int[] { damage, damage, damage, damage, damage, damage, damage, damage, damage, damage };
		attackAll(AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, damageArray, DamageType.THORNS);
	}
	
	public static void damageAllEnemiesThornsFire(int damage)
	{
		int[] damageArray = new int[] { damage, damage, damage, damage, damage, damage, damage, damage, damage, damage };
		attackAll(AbstractGameAction.AttackEffect.FIRE, damageArray, DamageType.THORNS);
	}
	
	private void recklessHandler(AbstractMonster m, int dmg)
	{
		boolean reckless = this.hasTag(Tags.RECKLESS);
		if (this.hasTag(Tags.DRAGON) && player().hasPower(TyrantWingPower.POWER_ID)) { reckless = true; }
		if (reckless)
		{
			ArrayList<AbstractMonster> otherMons = new ArrayList<AbstractMonster>();
			for (AbstractMonster mon : AbstractDungeon.getCurrRoom().monsters.monsters)
			{
				if (!mon.equals(m) && !mon.isDead && !mon.isDying && !mon.isDeadOrEscaped() && !mon.halfDead) { otherMons.add(mon); }
				if (dmg == 0) { otherMons.add(m); }
			}
			if (otherMons.size() > 0)
			{
				for (AbstractMonster mons : otherMons)
				{
					if (AbstractDungeon.cardRandomRng.random(1, 3) == 1)
					{
						applyPower(new VulnerablePower(mons, 1, false), mons);
					}
				}
			}
		}
	}
	
	private void recklessHandler(ArrayList<AbstractMonster> otherMons)
	{
		boolean reckless = this.hasTag(Tags.RECKLESS);
		if (this.hasTag(Tags.DRAGON) && player().hasPower(TyrantWingPower.POWER_ID)) { reckless = true; }
		if (reckless)
		{
			if (otherMons.size() > 0)
			{
				for (AbstractMonster mons : otherMons)
				{
					if (AbstractDungeon.cardRandomRng.random(1, 3) == 1)
					{
						applyPower(new VulnerablePower(mons, 1, false), mons);
					}
				}
			}
		}
	}
	
	private void stampedeHandler(AbstractMonster m, int dmg)
	{
		boolean stampede = this.hasTag(Tags.STAMPEDING);
		if (this.hasTag(Tags.DRAGON) && player().hasPower(TyrantWingPower.POWER_ID)) { stampede = true; }
		if (stampede)
		{
			ArrayList<AbstractMonster> otherMons = new ArrayList<AbstractMonster>();
			for (AbstractMonster mon : AbstractDungeon.getCurrRoom().monsters.monsters)
			{
				if (!mon.equals(m) && !mon.isDead && !mon.isDying && !mon.isDeadOrEscaped() && !mon.halfDead) { otherMons.add(mon); }
				if (dmg == 0) { otherMons.add(m); }
			}
			if (otherMons.size() > 0)
			{
				for (AbstractMonster mons : otherMons)
				{
					if (AbstractDungeon.cardRandomRng.random(1, 2) == 1)
					{
						applyPower(new WeakPower(mons, 1, false), mons);
					}
				}
			}
		}
	}
	
	private void stampedeHandler(ArrayList<AbstractMonster> otherMons)
	{
		boolean stampede = this.hasTag(Tags.STAMPEDING);
		if (this.hasTag(Tags.DRAGON) && player().hasPower(TyrantWingPower.POWER_ID)) { stampede = true; }
		if (stampede)
		{
			if (otherMons.size() > 0)
			{
				for (AbstractMonster mons : otherMons)
				{
					if (AbstractDungeon.cardRandomRng.random(1, 2) == 1)
					{
						applyPower(new WeakPower(mons, 1, false), mons);
					}
				}
			}
		}
	}
	// =============== /ATTACK FUNCTIONS/ =======================================================================================================================================================
	
	
	
	// =============== DEFEND FUNCTIONS =========================================================================================================================================================
	protected void block() 
	{
		block(this.block);
	}

	public void block(int amount) 
	{
		AbstractDungeon.actionManager.addToTop(new GainBlockAction(player(), player(), amount));
	}

	public static void staticBlock(int amount) 
	{
		AbstractDungeon.actionManager.addToTop(new GainBlockAction(player(), player(), amount));
	}
	
	public static void staticBlock(int amount, boolean allowDexterity) 
	{
		int extraBlock = 0;
		if (player().hasPower(DexterityPower.POWER_ID) && allowDexterity) { extraBlock = player().getPower(DexterityPower.POWER_ID).amount; }
		AbstractDungeon.actionManager.addToTop(new GainBlockAction(player(), player(), amount + extraBlock));
	}
	
	public static void manaBlock(int amount, MagickaPower pow) 
	{
		AbstractDungeon.actionManager.addToTop(new GainBlockAction(player(), player(), amount));
		pow.flash();
	}
	// =============== /DEFEND FUNCTIONS/ =======================================================================================================================================================
	
	
	
	// =============== POWER FUNCTIONS =========================================================================================================================================================
	protected boolean hasPower(String power) {
		return player().hasPower(power);
	}
	
	public static void applyPower(AbstractPower power, AbstractCreature target) 
	{
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, player(), power, power.amount));
		player().hand.glowCheck();
	}

	public static void applyPowerTop(AbstractPower power, AbstractCreature target) 
	{
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, player(), power, power.amount));
		player().hand.glowCheck();

	}

	protected void applyPower(AbstractPower power, AbstractCreature target, int amount) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, player(), power, amount));
		player().hand.glowCheck();
	}

	public static void removePower(AbstractPower power, AbstractCreature target) {

		if (target.hasPower(power.ID))
		{
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(target, player(), power, power.amount));
			player().hand.glowCheck();
		}
	}
	
	public static void removePowerAction(AbstractCreature target, AbstractPower power)
	{
		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(target, target, power));
		player().hand.glowCheck();
	}
	
	public static void removePowerAction(AbstractCreature target, String powerName)
	{
		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(target, target, powerName));
		player().hand.glowCheck();
	}

	public static void reducePower(AbstractPower power, AbstractCreature target, int reduction) {

		if (target.hasPower(power.ID))
		{
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(target, player(), power, reduction));
			player().hand.glowCheck();
		}		
	}

	public static void applyPowerToSelf(AbstractPower power) {
		applyPower(power, player());
	}

	public static void applyPowerToSelfTop(AbstractPower power) {
		applyPowerTop(power, player());

	}

	// turnNum arg does not work here, random buffs are generated globally now but I don't feel like fixing all the calls to this function
	public static AbstractPower applyRandomBuff(AbstractCreature p, int turnNum)
	{
		BuffHelper.resetRandomBuffs(turnNum);

		// Get randomized buff
		int randomBuffNum = AbstractDungeon.cardRandomRng.random(DuelistMod.randomBuffs.size() - 1);
		AbstractPower randomBuff = DuelistMod.randomBuffs.get(randomBuffNum);
		for (int i = 0; i < DuelistMod.randomBuffs.size(); i++)
		{
			if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:applyRandomBuff() ---> buffs[" + i + "]: " + DuelistMod.randomBuffs.get(i).name + " :: amount: " + DuelistMod.randomBuffs.get(i).amount); }
		}
		if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:applyRandomBuff() ---> generated random buff: " + randomBuff.name + " :: index was: " + randomBuffNum + " :: turnNum or amount was: " + randomBuff.amount); }
		ArrayList<AbstractPower> powers = p.powers;
		//boolean found = false;
		applyPower(randomBuff, p);
		for (AbstractPower a : powers)
		{
			//if (!a.name.equals("Time Wizard")) { a.updateDescription(); }
			a.updateDescription();
		}		
		return randomBuff;
	}

	public static AbstractPower applyRandomBuffSmall(AbstractCreature p, int turnNum)
	{
		// Setup powers array for random buff selection
		AbstractPower str = new StrengthPower(p, turnNum);
		AbstractPower dex = new DexterityPower(p, 1);
		AbstractPower art = new ArtifactPower(p, turnNum);
		AbstractPower plate = new PlatedArmorPower(p, turnNum);
		AbstractPower regen = new RegenPower(p, turnNum);
		AbstractPower energy = new EnergizedPower(p, 1);
		AbstractPower thorns = new ThornsPower(p, turnNum);
		AbstractPower focus = new FocusPower(p, turnNum);
		AbstractPower[] buffs = new AbstractPower[] { str, dex, art, plate, regen, energy, thorns, focus };

		// Get randomized buff
		int randomBuffNum = AbstractDungeon.cardRandomRng.random(buffs.length - 1);
		AbstractPower randomBuff = buffs[randomBuffNum];

		ArrayList<AbstractPower> powers = p.powers;
		//boolean found = false;
		applyPower(randomBuff, p);
		for (AbstractPower a : powers)
		{
			//if (!a.name.equals("Time Wizard")) { a.updateDescription(); }
			a.updateDescription();
		}
		return randomBuff;
	}
	
	public static AbstractPower getRandomBuff(AbstractCreature p, int turnNum)
	{
		BuffHelper.resetRandomBuffs(turnNum);

		// Get randomized buff
		int randomBuffNum = AbstractDungeon.cardRandomRng.random(DuelistMod.randomBuffs.size() - 1);
		AbstractPower randomBuff = DuelistMod.randomBuffs.get(randomBuffNum);
		for (int i = 0; i < DuelistMod.randomBuffs.size(); i++)
		{
			if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:getRandomBuff() ---> buffs[" + i + "]: " + DuelistMod.randomBuffs.get(i).name + " :: amount: " + DuelistMod.randomBuffs.get(i).amount); }
		}
		if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:getRandomBuff() ---> generated random buff: " + randomBuff.name + " :: index was: " + randomBuffNum + " :: turnNum or amount was: " + randomBuff.amount); }	
		return randomBuff;
	}
	
	
	
	public static AbstractPower getRandomBuffSmall(AbstractCreature p, int turnNum)
	{
		// Setup powers array for random buff selection
		AbstractPower str = new StrengthPower(p, turnNum);
		AbstractPower dex = new DexterityPower(p, 1);
		AbstractPower art = new ArtifactPower(p, turnNum);
		AbstractPower plate = new PlatedArmorPower(p, turnNum);
		AbstractPower regen = new RegenPower(p, turnNum);
		AbstractPower energy = new EnergizedPower(p, 1);
		AbstractPower thorns = new ThornsPower(p, turnNum);
		AbstractPower focus = new FocusPower(p, turnNum);
		AbstractPower[] buffs = new AbstractPower[] { str, dex, art, plate, regen, energy, thorns, focus };

		// Get randomized buff
		int randomBuffNum = AbstractDungeon.cardRandomRng.random(buffs.length - 1);
		AbstractPower randomBuff = buffs[randomBuffNum];
		return randomBuff;
	}

	public static AbstractPower applyRandomBuffPlayer(AbstractPlayer p, int turnNum, boolean smallSet)
	{
		if (smallSet) { return applyRandomBuffSmall(p, turnNum); }
		else { return applyRandomBuff(p, turnNum); }
	}

	public static void poisonAllEnemies(AbstractPlayer p, int amount)
	{
		if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) 
		{
			//flash();
			for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) 
			{
				if (!monster.isDead && !monster.isDying && !monster.isDeadOrEscaped() && !monster.halfDead) 
				{
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new PoisonPower(monster, p, amount), amount));
				}
			}
		}

	}
	
	public static void vulnAllEnemies(int amount)
	{
		AbstractPlayer p = AbstractDungeon.player;
		if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) 
		{
			for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) 
			{
				if (!monster.isDead && !monster.isDying && !monster.isDeadOrEscaped() && !monster.halfDead) 
				{
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new VulnerablePower(monster, amount, false), amount));
				}
			}
		}

	}
	
	public static void burnAllEnemies(int amount)
	{
		AbstractPlayer p = AbstractDungeon.player;
		if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) 
		{
			for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) 
			{
				if (!monster.isDead && !monster.isDying && !monster.isDeadOrEscaped() && !monster.halfDead) 
				{
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new BurningDebuff(monster, AbstractDungeon.player, amount), amount));
				}
			}
		}

	}
	
	public static void siphonAllEnemies(int amount)
	{
		if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) 
		{
			for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) 
			{
				if (!monster.isDead && !monster.isDying && !monster.isDeadOrEscaped() && !monster.halfDead) 
				{
					siphon(monster, amount);
				}
			}
		}
	}
	
	public static void resummonOnAllEnemies(AbstractCard toResummon)
	{
		resummonOnAll(toResummon, 1, false, false);
	}
	
	public static void resummonOnAllEnemies(AbstractCard toResummon, boolean upgrade)
	{
		resummonOnAll(toResummon, 1, upgrade, false);
	}

	public static void weakAllEnemies(int amount)
	{
		AbstractPlayer p = AbstractDungeon.player;
		if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) 
		{
			for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) 
			{
				if (!monster.isDead && !monster.isDying && !monster.isDeadOrEscaped() && !monster.halfDead) 
				{
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new WeakPower(monster, amount, false), amount));
				}
			}
		}

	}
	
	public static void slowAllEnemies(int amount)
	{
		AbstractPlayer p = AbstractDungeon.player;
		if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) 
		{
			for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) 
			{
				if (!monster.isDead && !monster.isDying && !monster.isDeadOrEscaped() && !monster.halfDead) 
				{
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new SlowPower(monster, amount), amount));
				}
			}
		}
	}
	
	public static void freezeAllEnemies()
	{
		AbstractPlayer p = AbstractDungeon.player;
		if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) 
		{
			for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) 
			{
				if (!monster.isDead && !monster.isDying && !monster.isDeadOrEscaped() && !monster.halfDead) 
				{
					applyPower(new FrozenDebuff(monster, p), monster);
				}
			}
		}
	}
	
	public static void greaseAllEnemies(int amount)
	{
		AbstractPlayer p = AbstractDungeon.player;
		if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) 
		{
			for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) 
			{
				if (!monster.isDead && !monster.isDying && !monster.isDeadOrEscaped() && !monster.halfDead) 
				{
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new GreasedDebuff(monster, AbstractDungeon.player, amount), amount));
				}
			}
		}
	}
	
	public static void randomlyDebuffAllEnemies(int turns)
	{
		AbstractPlayer p = AbstractDungeon.player;
		if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) 
		{
			for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) 
			{
				if (!monster.isDead && !monster.isDying && !monster.isDeadOrEscaped() && !monster.halfDead) 
				{
					AbstractPower debuff = DebuffHelper.getRandomDebuff(p, monster, turns);
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, debuff));
				}
			}
		}
	}
	
	public static void dampAllEnemies(int amount)
	{
		AbstractPlayer p = AbstractDungeon.player;
		if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) 
		{
			for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) 
			{
				if (!monster.isDead && !monster.isDying && !monster.isDeadOrEscaped() && !monster.halfDead) 
				{
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new DampDebuff(monster, AbstractDungeon.player, amount), amount));
				}
			}
		}
	}
	
	public static void constrictAllEnemies(AbstractPlayer p, int amount)
	{
		if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) 
		{
			//flash();
			for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) 
			{
				if (!monster.isDead && !monster.isDying && !monster.isDeadOrEscaped() && !monster.halfDead) 
				{
					AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new ConstrictedPower(monster, p, amount), amount));
				}
			}
		}

	}

	// =============== /POWER FUNCTIONS/ =======================================================================================================================================================
	
	
	
	// =============== MISC ACTION FUNCTIONS =========================================================================================================================================================
	public SummonPower getSummonPow()
	{
		if (player().hasPower(SummonPower.POWER_ID)) { return (SummonPower) player().getPower(SummonPower.POWER_ID);}
		else { return null; }
	}
	
	public void gainDex(int amt)
	{
		applyPowerToSelf(new DexterityPower(player(), amt));
	}
	
	public void entomb()
	{
		entomb(this.entomb);
	}
	
	public void entomb(int amt)
	{
		this.addToBot(new CardSelectScreenEntombAction(amt));
	}
	
	public void entomb(int amt, ArrayList<AbstractCard> listToPullFrom)
	{
		this.addToBot(new CardSelectScreenEntombAction(listToPullFrom, amt));
	}
	
	public void mutate(int amt)
	{
		if (AbstractDungeon.player.hasRelic(MutatorToken.ID))
		{
			this.addToBot(new NecronizeAction(amt));
		}
		else
		{
			this.addToBot(new MutateAction(amt));
		}	
	}
	
	public void necronize(int amt)
	{
		this.addToBot(new NecronizeAction(amt));
	}
	
	public static void mutateStatic(int amt)
	{
		if (AbstractDungeon.player.hasRelic(MutatorToken.ID))
		{
			AbstractDungeon.actionManager.addToBottom(new NecronizeAction(amt));
		}
		else
		{
			AbstractDungeon.actionManager.addToBottom(new MutateAction(amt));
		}		
	}
	
	public static void fullMutation(ArrayList<AbstractCard> mutateFrom, int amt, int options, boolean allowNonZomb)
	{
		if (AbstractDungeon.player.hasRelic(MutatorToken.ID))
		{
			fullNecronize(mutateFrom, amt, options, allowNonZomb, false, false);
		}
		else
		{
			fullMutation(mutateFrom, amt, options, allowNonZomb, false, false);
		}
	}
	
	public static void fullMutation(ArrayList<AbstractCard> mutateFrom, int amt, int options, boolean allowNonZomb, boolean ghostrick)
	{
		if (AbstractDungeon.player.hasRelic(MutatorToken.ID))
		{
			fullNecronize(mutateFrom, amt, options, allowNonZomb, ghostrick, false);
		}
		else
		{
			fullMutation(mutateFrom, amt, options, allowNonZomb, ghostrick, false);
		}	
	}
	
	public static void fullMutation(ArrayList<AbstractCard> mutateFrom, int amt, int options, boolean allowNonZomb, boolean ghostrick, boolean vampire)
	{
		if (AbstractDungeon.player.hasRelic(MutatorToken.ID))
		{
			if (ghostrick) { 
				AbstractDungeon.actionManager.addToBottom(new NecronizeAction(mutateFrom, amt, options, allowNonZomb, ghostrick));
			}
			else if (vampire){
				AbstractDungeon.actionManager.addToBottom(new NecronizeAction(mutateFrom, amt, options, allowNonZomb, ghostrick, vampire));
			}
			else {
				AbstractDungeon.actionManager.addToBottom(new NecronizeAction(mutateFrom, amt, options, allowNonZomb));
			}
		}
		else
		{
			if (ghostrick) { 
				AbstractDungeon.actionManager.addToBottom(new MutateAction(mutateFrom, amt, options, allowNonZomb, ghostrick));
			}
			else if (vampire){
				AbstractDungeon.actionManager.addToBottom(new MutateAction(mutateFrom, amt, options, allowNonZomb, ghostrick, vampire));
			}
			else {
				AbstractDungeon.actionManager.addToBottom(new MutateAction(mutateFrom, amt, options, allowNonZomb));
			}
		}	
	}
	
	public static void necronizeStatic(int amt)
	{
		AbstractDungeon.actionManager.addToBottom(new NecronizeAction(amt));
	}
	
	public static void fullNecronize(ArrayList<AbstractCard> necronizeFrom, int amt, int options, boolean allowNonZomb)
	{
		fullNecronize(necronizeFrom, amt, options, allowNonZomb, false, false);
	}
	
	public static void fullNecronize(ArrayList<AbstractCard> necronizeFrom, int amt, int options, boolean allowNonZomb, boolean ghostrick)
	{
		fullNecronize(necronizeFrom, amt, options, allowNonZomb, ghostrick, false);
	}
	
	public static void fullNecronize(ArrayList<AbstractCard> necronizeFrom, int amt, int options, boolean allowNonZomb, boolean ghostrick, boolean vampire)
	{
		if (ghostrick) { 
			AbstractDungeon.actionManager.addToBottom(new NecronizeAction(necronizeFrom, amt, options, allowNonZomb, ghostrick));
		}
		else if (vampire){
			AbstractDungeon.actionManager.addToBottom(new NecronizeAction(necronizeFrom, amt, options, allowNonZomb, ghostrick, vampire));
		}
		else {
			AbstractDungeon.actionManager.addToBottom(new NecronizeAction(necronizeFrom, amt, options, allowNonZomb));
		}
		
	}

	public void revive(int amt)
	{
		this.addToBot(new ReviveAction(amt, false));
	}
	
	public void revive(int amt, boolean noSoulCost)
	{
		this.addToBot(new ReviveAction(amt, noSoulCost));
	}

	public static void reviveStatic(int amt)
	{
		AbstractDungeon.actionManager.addToBottom(new ReviveAction(amt, false));
	}

	public static void reviveStatic(int amt, boolean noSoulCost)
	{
		AbstractDungeon.actionManager.addToBottom(new ReviveAction(amt, noSoulCost));
	}
	
	public void modifySouls(int add)
	{
		Util.modifySouls(add);
	}
	
	public void setSouls(int set)
	{
		Util.setSouls(set);
	}
	
	public boolean isTributeCard()
	{
		return isTributeCard(false);
	}
	
	public boolean isSummonCard()
	{
		return isSummonCard(true);
	}
	
	public boolean isTributeCard(boolean allowZeros)
	{
		if (allowZeros)
		{
			if (this.baseTributes > 0 || DuelistMod.tributeCards.containsKey(this.cardID)) { return true; }
		}
		else
		{
			if (this.tributes > 0 && DuelistMod.tributeCards.containsKey(this.cardID)) { return true; }
		}		
		return false;
	}
	
	public boolean isSummonCard(boolean allowZeros)
	{
		if (allowZeros)
		{
			if (this.baseSummons > 0 || DuelistMod.summonCards.containsKey(this.cardID)) { return true; }
		}
		else
		{
			if (this.summons > 0 && DuelistMod.summonCards.containsKey(this.cardID)) { return true; }
		}		
		return false;
	}
	
	public int checkMagicNum()
	{
		return getModifiedMagicForOverflowCheck();
	}
	
	private int getModifiedMagicForOverflowCheck()
	{
		float tmp = (float)this.baseMagicNumber;
		for (final AbstractPower p : AbstractDungeon.player.powers) 
		{
			if (p instanceof DuelistPower)
			{
				DuelistPower pow = (DuelistPower)p;
				tmp = pow.modifyMagicNumber(tmp, this);
			}
		}
		
		for (final AbstractPotion p : AbstractDungeon.player.potions) 
		{
			if (p instanceof DuelistPotion)
			{
				DuelistPotion pow = (DuelistPotion)p;
				tmp = pow.modifyMagicNumber(tmp, this);
			}
		}
		
		for (final AbstractOrb p : AbstractDungeon.player.orbs) 
		{
			if (p instanceof DuelistOrb)
			{
				DuelistOrb pow = (DuelistOrb)p;
				tmp = pow.modifyMagicNumber(tmp, this);
			}
		}
		
		for (final AbstractRelic p : AbstractDungeon.player.relics) 
		{
			if (p instanceof DuelistRelic)
			{
				DuelistRelic pow = (DuelistRelic)p;
				tmp = pow.modifyMagicNumber(tmp, this);
			}
		}
		if (AbstractDungeon.player.stance instanceof DuelistStance)
		{
			DuelistStance stance = (DuelistStance)AbstractDungeon.player.stance;
			tmp = stance.modifyMagicNumber(tmp, this);
		}
		if (tmp < 0.0f) 
		{
			tmp = 0.0f;
		}
		return MathUtils.floor(tmp);
	}
	
	public void setColorless()
	{
		this.color = CardColor.COLORLESS;
	}
	
	public static boolean roulette()
	{
		boolean passed = false;
		int roll = AbstractDungeon.cardRandomRng.random(1,2);
    	if (roll == 1) { passed = true; }
    	if (player().hasPower(RevolvingSwitchyardPower.POWER_ID)) { passed = true; }
    	if (!passed)
    	{
    		if (AbstractDungeon.player.hasRelic(LoadedDice.ID)) 
    		{
    			roll = AbstractDungeon.cardRandomRng.random(1,4);
    	    	if (roll == 1) 
    	    	{
    	    		passed = true; 
    	    		AbstractDungeon.player.getRelic(LoadedDice.ID).flash();
    	    	}
    		}
    		
    		if (!passed && AbstractDungeon.player.hasRelic(TokenfestPendant.ID))
    		{
    			AbstractDungeon.player.getRelic(TokenfestPendant.ID).flash();
    			DuelistCard.addCardToHand(DuelistCardLibrary.getRandomTokenForCombat());
    		}
    	}
    	if (passed) { handleOnPassRouletteForAllAbstracts(); }
    	return passed;
	}

	public void detonate(boolean superExploding, boolean selfDmg, boolean dmgAllEnemies, boolean dmgEnemies, boolean randomDetonations, boolean randomTarg, AbstractMonster target, int detonationsBase, int detonationsExtraLowRoll, int detonationsExtraHighRoll)
	{
		ArrayList<AbstractMonster> livingMons = new ArrayList<>();
		int detonations = detonationsBase;
		if (player().hasRelic(Wirebundle.ID)) 
		{ 
			player().getRelic(Wirebundle.ID).flash();
			detonations += 2; 
		}
		if (player().hasPower(ZONEPower.POWER_ID)) { detonations += player().getPower(ZONEPower.POWER_ID).amount; }
		if (!selfDmg && Util.getChallengeLevel() > 3 && Util.deckIs("Machine Deck")) { if (AbstractDungeon.cardRandomRng.random(1, 10) == 1) { selfDmg = true; }}
		if (randomTarg || target == null) 
		{
			livingMons = getAllMons();
			target = livingMons.get(AbstractDungeon.cardRandomRng.random(livingMons.size() - 1));
		}
		if (randomDetonations) { detonations += AbstractDungeon.cardRandomRng.random(detonationsExtraLowRoll, detonationsExtraHighRoll); }
		if (AbstractDungeon.player.hasRelic(MachineToken.ID) && selfDmg) 
		{ 
			AbstractDungeon.player.getRelic(MachineToken.ID).flash();
			selfDmg = false; 
		}
		if (superExploding)
		{
			for (int i = 0; i < detonations; i++)
			{
				handleOnDetonateForAllAbstracts();
				if (dmgEnemies && !dmgAllEnemies) { if (target == null || target.isDead || target.isDying || target.isDeadOrEscaped() || target.halfDead) { livingMons = getAllMons(); target = livingMons.get(AbstractDungeon.cardRandomRng.random(livingMons.size() - 1)); }}
				int lowDmg = DuelistMod.explosiveDmgLow * DuelistMod.superExplodgeMultLow;
				int highDmg = DuelistMod.explosiveDmgHigh * DuelistMod.superExplodgeMultHigh;
				int damageRollSuper = AbstractDungeon.cardRandomRng.random(lowDmg, highDmg);
				if (selfDmg) { damageSelfNotHP(damageRollSuper); }
				if (dmgEnemies)
				{
					if (dmgAllEnemies) { attackAllEnemiesFireThorns(damageRollSuper); }
					else { AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(player(), damageRollSuper, DamageType.THORNS), AttackEffect.FIRE)); }
				}
			}
		}
		else
		{
			for (int i = 0; i < detonations; i++)
			{
				handleOnDetonateForAllAbstracts();
				if (dmgEnemies && !dmgAllEnemies) { if (target == null || target.isDead || target.isDying || target.isDeadOrEscaped() || target.halfDead) { livingMons = getAllMons(); target = livingMons.get(AbstractDungeon.cardRandomRng.random(livingMons.size() - 1)); }}
				int lowDmg = DuelistMod.explosiveDmgLow;
				int highDmg = DuelistMod.explosiveDmgHigh;
				int damageRoll = AbstractDungeon.cardRandomRng.random(lowDmg, highDmg);
				if (selfDmg) { damageSelfNotHP(damageRoll); }
				if (dmgEnemies)
				{
					if (dmgAllEnemies) { attackAllEnemiesFireThorns(damageRoll); }
					else { AbstractDungeon.actionManager.addToBottom(new DamageAction(target, new DamageInfo(player(), damageRoll, DamageType.THORNS), AttackEffect.FIRE)); }
				}
			}
		}
	}
	
	public static ArrayList<AbstractMonster> getAllMons()
	{
		ArrayList<AbstractMonster> mons = new ArrayList<AbstractMonster>();
    	for (AbstractMonster monst : AbstractDungeon.getCurrRoom().monsters.monsters) { if (!monst.isDead && !monst.isDying && !monst.isDeadOrEscaped() && !monst.halfDead) { mons.add(monst); }}
    	return mons;
	}
	
	public static ArrayList<AbstractMonster> getAllMonsForFireOrb()
	{
		ArrayList<AbstractMonster> mons = new ArrayList<AbstractMonster>();
    	for (AbstractMonster monst : AbstractDungeon.getCurrRoom().monsters.monsters) { if (!monst.isDead && !monst.isDying && !monst.isDeadOrEscaped() && !monst.halfDead && !monst.hasPower(BurningDebuff.POWER_ID)) { mons.add(monst); }}
    	return mons;
	}
	
	public static int handleModifyShadowDamage()
	{
		int amtInc = 0;
		AbstractPlayer p = AbstractDungeon.player;
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { amtInc += ((DuelistPotion)pot).modifyShadowDamage(); }}
		for (AbstractRelic r : p.relics) { if (r instanceof DuelistRelic) { amtInc += ((DuelistRelic)r).modifyShadowDamage(); }}
		for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  amtInc += ((DuelistOrb)o).modifyShadowDamage(); }}
		for (AbstractPower pow : p.powers) { if (pow instanceof DuelistPower) { amtInc += ((DuelistPower)pow).modifyShadowDamage(); }}
		for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyShadowDamageWhileInHand(); }}
		for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyShadowDamageWhileInDiscard(); }}
		for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyShadowDamageWhileInDraw(); }}
		for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyShadowDamageWhileInExhaust(); }}
		for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyShadowDamageWhileInGraveyard(); }}
		if (player().hasPower(SummonPower.POWER_ID)) {
			SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
			for (DuelistCard c : pow.actualCardSummonList) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyShadowDamageWhileSummoned(); }}
		}
		return amtInc;
	}
	
	public static int handleModifyUndeadDamage()
	{
		int amtInc = 0;
		AbstractPlayer p = AbstractDungeon.player;
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { amtInc += ((DuelistPotion)pot).modifyUndeadDamage(); }}
		for (AbstractRelic r : p.relics) { if (r instanceof DuelistRelic) { amtInc += ((DuelistRelic)r).modifyUndeadDamage(); }}
		for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  amtInc += ((DuelistOrb)o).modifyUndeadDamage(); }}
		for (AbstractPower pow : p.powers) { if (pow instanceof DuelistPower) { amtInc += ((DuelistPower)pow).modifyUndeadDamage(); }}
		for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyUndeadDamageWhileInHand(); }}
		for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyUndeadDamageWhileInDiscard(); }}
		for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyUndeadDamageWhileInDraw(); }}
		for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyUndeadDamageWhileInExhaust(); }}
		for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyUndeadDamageWhileInGraveyard(); }}
		if (player().hasPower(SummonPower.POWER_ID)) {
			SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
			for (DuelistCard c : pow.actualCardSummonList) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyUndeadDamageWhileSummoned(); }}
		}
		return amtInc;
	}
	
	// Handle onTribute for custom relics, powers, orbs, stances, cards, potions
	private static void handleOnTributeForAllAbstracts(DuelistCard tributed, DuelistCard tributingCard)
	{
		Util.log("Running onTribute for all abstract objects! Tributed: " + tributed + " for " + tributingCard.name);
		AbstractPlayer p = AbstractDungeon.player;
		for (AbstractRelic r : p.relics) { if (r instanceof DuelistRelic) { ((DuelistRelic)r).onTribute(tributed, tributingCard); }}
		for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  ((DuelistOrb)o).onTribute(tributed, tributingCard); }}
		for (AbstractPower pow : p.powers) { if (pow instanceof DuelistPower) { ((DuelistPower)pow).onTribute(tributed, tributingCard); }}
		for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onTributeWhileInHand(tributed, tributingCard); }}
		for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onTributeWhileInDiscard(tributed, tributingCard); }}
		for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onTributeWhileInDraw(tributed, tributingCard); }}
		for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onTributeWhileInExhaust(tributed, tributingCard); }}
		for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onTributeWhileInGraveyard(tributed, tributingCard); }}
		if (player().hasPower(SummonPower.POWER_ID)) {
			SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
			for (DuelistCard c : pow.actualCardSummonList) { if (c instanceof DuelistCard) { ((DuelistCard)c).onTributeWhileSummoned(tributed, tributingCard); }}
		}
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { ((DuelistPotion) pot).onTribute(tributed, tributingCard); }}
		if (p.stance instanceof DuelistStance) { ((DuelistStance)p.stance).onTribute(tributed, tributingCard); }
	}
	
	private static void handleOnOverflowForAllAbstracts(int overflows)
	{
		Util.log("Running onOverflow for all abstract objects! Overflows: " + overflows);
		AbstractPlayer p = AbstractDungeon.player;
		for (AbstractRelic r : p.relics) { if (r instanceof DuelistRelic) { ((DuelistRelic)r).onOverflow(overflows); }}
		for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  ((DuelistOrb)o).onOverflow(overflows); }}
		for (AbstractPower pow : p.powers) { if (pow instanceof DuelistPower) { ((DuelistPower)pow).onOverflow(overflows); }}
		for (AbstractMonster mon : getAllMons()) {
			for (AbstractPower pow : mon.powers) {
				if (pow instanceof DuelistPower) {
					((DuelistPower)pow).onOverflow(overflows);
				}
			}
		}
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { ((DuelistPotion) pot).onOverflow(overflows); }}
		if (p.stance instanceof DuelistStance) { ((DuelistStance)p.stance).onOverflow(overflows); }
		for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onOverflowWhileInHand(); }}
		for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onOverflowWhileInDiscard(); }}
		for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onOverflowWhileInDraw(); }}
		for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onOverflowWhileInExhaust(); }}
		for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onOverflowWhileInGraveyard(); }}
		if (player().hasPower(SummonPower.POWER_ID)) {
			SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
			for (DuelistCard c : pow.actualCardSummonList) { if (c instanceof DuelistCard) { ((DuelistCard)c).onOverflowWhileSummoned(); }}
		}
	}
	
	public static void handleOnFishForAllAbstracts(ArrayList<AbstractCard> discarded, ArrayList<AbstractCard> aquasDiscarded)
	{
		Util.log("Running onFish for all abstract objects!");
		AbstractPlayer p = AbstractDungeon.player;
		for (AbstractRelic r : p.relics) { if (r instanceof DuelistRelic) { ((DuelistRelic)r).onFish(discarded, aquasDiscarded); }}
		for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  ((DuelistOrb)o).onFish(discarded, aquasDiscarded); }}
		for (AbstractPower pow : p.powers) { if (pow instanceof DuelistPower) { ((DuelistPower)pow).onFish(discarded, aquasDiscarded); }}
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { ((DuelistPotion) pot).onFish(discarded, aquasDiscarded); }}
		if (p.stance instanceof DuelistStance) { ((DuelistStance)p.stance).onFish(discarded, aquasDiscarded); }
		for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onFishWhileInHand(discarded, aquasDiscarded); }}
		for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onFishWhileInDiscard(discarded, aquasDiscarded); }}
		for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onFishWhileInDraw(discarded, aquasDiscarded); }}
		for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onFishWhileInExhaust(discarded, aquasDiscarded); }}
		for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onFishWhileInGraveyard(discarded, aquasDiscarded); }}
		if (player().hasPower(SummonPower.POWER_ID)) {
			SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
			for (DuelistCard c : pow.actualCardSummonList) { if (c instanceof DuelistCard) { ((DuelistCard)c).onFishWhileSummoned(discarded, aquasDiscarded); }}
		}
	}
	
	private static void handleOnSynergyForAllAbstracts()
	{
		Util.log("Running onSynergyTribute for all abstract objects!");
		AbstractPlayer p = AbstractDungeon.player;
		for (AbstractRelic r : p.relics) { if (r instanceof DuelistRelic) { ((DuelistRelic)r).onSynergyTribute(); }}
		for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  ((DuelistOrb)o).onSynergyTribute(); }}
		for (AbstractPower pow : p.powers) { if (pow instanceof DuelistPower) { ((DuelistPower)pow).onSynergyTribute(); }}
		for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onSynergyTributeWhileInHand(); }}
		for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onSynergyTributeWhileInDiscard(); }}
		for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onSynergyTributeWhileInDraw(); }}
		for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onSynergyTributeWhileInExhaust(); }}
		for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onSynergyTributeWhileInGraveyard(); }}
		if (player().hasPower(SummonPower.POWER_ID)) {
			SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
			for (DuelistCard c : pow.actualCardSummonList) { if (c instanceof DuelistCard) { ((DuelistCard)c).onSynergyTributeWhileSummoned(); }}
		}
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { ((DuelistPotion)pot).onSynergyTribute(); }}
		if (p.stance instanceof DuelistStance) { ((DuelistStance)p.stance).onSynergyTribute(); }
	}
	
	public static void handleOnLoseArtifactForAllAbstracts()
	{
		Util.log("Running onLoseArtifact for all abstract objects!");
		AbstractPlayer p = AbstractDungeon.player;
		for (AbstractRelic r : p.relics) { if (r instanceof DuelistRelic) { ((DuelistRelic)r).onLoseArtifact(); }}
		for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  ((DuelistOrb)o).onLoseArtifact(); }}
		for (AbstractPower pow : p.powers) { if (pow instanceof DuelistPower) { ((DuelistPower)pow).onLoseArtifact(); }}
		for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onLoseArtifact(); ((DuelistCard)c).onLoseArtifactHand(); }}
		for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onLoseArtifact(); ((DuelistCard)c).onLoseArtifactDiscard();}}
		for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onLoseArtifact(); ((DuelistCard)c).onLoseArtifactDraw();}}
		for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onLoseArtifact(); ((DuelistCard)c).onLoseArtifactExhaust();}}
		for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onLoseArtifactGraveyard(); }}
		if (player().hasPower(SummonPower.POWER_ID)) {
			SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
			for (DuelistCard c : pow.actualCardSummonList) { if (c instanceof DuelistCard) { ((DuelistCard)c).onLoseArtifactSummoned(); }}
		}
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { ((DuelistPotion)pot).onLoseArtifact(); }}
		if (p.stance instanceof DuelistStance) { ((DuelistStance)p.stance).onLoseArtifact(); }
	}
	
	public static void handleOnSolderForAllAbstracts()
	{
		Util.log("Running onSolder for all abstract objects!");
		AbstractPlayer p = AbstractDungeon.player;
		for (AbstractRelic r : p.relics) { if (r instanceof DuelistRelic) { ((DuelistRelic)r).onSolder(); }}
		for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  ((DuelistOrb)o).onSolder(); }}
		for (AbstractPower pow : p.powers) { if (pow instanceof DuelistPower) { ((DuelistPower)pow).onSolder(); }}
		for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onSolder(); ((DuelistCard)c).onSolderWhileInHand(); }}
		for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onSolder(); ((DuelistCard)c).onSolderWhileInDiscard();}}
		for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onSolder(); ((DuelistCard)c).onSolderWhileInDraw();}}
		for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onSolder(); ((DuelistCard)c).onSolderWhileInExhaust();}}
		for (AbstractCard c : p.masterDeck.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onSolderWhileInDeck();}}
		for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onSolderWhileInGraveyard(); }}
		if (player().hasPower(SummonPower.POWER_ID)) {
			SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
			for (DuelistCard c : pow.actualCardSummonList) { if (c instanceof DuelistCard) { ((DuelistCard)c).onSolderWhileSummoned(); }}
		}
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { ((DuelistPotion)pot).onSolder(); }}
		if (p.stance instanceof DuelistStance) { ((DuelistStance)p.stance).onSolder(); }
	}
	
	public static void handleOnPassRouletteForAllAbstracts()
	{
		Util.log("Running onPassRoulette for all abstract objects!");
		AbstractPlayer p = AbstractDungeon.player;
		for (AbstractRelic r : p.relics) { if (r instanceof DuelistRelic) { ((DuelistRelic)r).onPassRoulette(); }}
		for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  ((DuelistOrb)o).onPassRoulette(); }}
		for (AbstractPower pow : p.powers) { if (pow instanceof DuelistPower) { ((DuelistPower)pow).onPassRoulette(); }}
		for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onPassRoulette(); ((DuelistCard)c).onPassRouletteWhileInHand(); }}
		for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onPassRoulette(); ((DuelistCard)c).onPassRouletteWhileInDiscard();}}
		for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onPassRoulette(); ((DuelistCard)c).onPassRouletteWhileInDraw();}}
		for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onPassRoulette(); ((DuelistCard)c).onPassRouletteWhileInExhaust();}}
		for (AbstractCard c : p.masterDeck.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onPassRouletteWhileInDeck();}}
		for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onPassRouletteWhileInGraveyard(); }}
		if (player().hasPower(SummonPower.POWER_ID)) {
			SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
			for (DuelistCard c : pow.actualCardSummonList) { if (c instanceof DuelistCard) { ((DuelistCard)c).onPassRouletteWhileSummoned(); }}
		}
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { ((DuelistPotion)pot).onPassRoulette(); }}
		if (p.stance instanceof DuelistStance) { ((DuelistStance)p.stance).onPassRoulette(); }
	}
	
	public static void handleOnDetonateForAllAbstracts()
	{
		Util.log("Running onDetonate for all abstract objects!");
		AbstractPlayer p = AbstractDungeon.player;
		for (AbstractRelic r : p.relics) { if (r instanceof DuelistRelic) { ((DuelistRelic)r).onDetonate(); }}
		for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  ((DuelistOrb)o).onDetonate(); }}
		for (AbstractPower pow : p.powers) { if (pow instanceof DuelistPower) { ((DuelistPower)pow).onDetonate(); }}
		for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onDetonate(); ((DuelistCard)c).onDetonateWhileInHand(); }}
		for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onDetonate(); ((DuelistCard)c).onDetonateWhileInDiscard();}}
		for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onDetonate(); ((DuelistCard)c).onDetonateWhileInDraw();}}
		for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onDetonate(); ((DuelistCard)c).onDetonateWhileInExhaust();}}
		for (AbstractCard c : p.masterDeck.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onDetonateWhileInDeck();}}
		for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onDetonateWhileInGraveyard(); }}
		if (player().hasPower(SummonPower.POWER_ID)) {
			SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
			for (DuelistCard c : pow.actualCardSummonList) { if (c instanceof DuelistCard) { ((DuelistCard)c).onDetonateWhileSummoned(); }}
		}
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { ((DuelistPotion)pot).onDetonate(); }}
		if (p.stance instanceof DuelistStance) { ((DuelistStance)p.stance).onDetonate(); }
	}
	
	public static void handleOnEnemyPlayCardForAllAbstracts(AbstractCard card)
	{
		Util.log("Running onEnemyPlayedCard for all abstract objects!");
		AbstractPlayer p = AbstractDungeon.player;
		for (AbstractRelic r : p.relics) { if (r instanceof DuelistRelic) { ((DuelistRelic)r).onEnemyUseCard(card); }}
		for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  ((DuelistOrb)o).onEnemyUseCard(card);  }}
		for (AbstractPower pow : p.powers) { if (pow instanceof DuelistPower) { ((DuelistPower)pow).onEnemyUseCard(card);  }}
		for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onEnemyUseCard(card); ((DuelistCard)c).onEnemyUseCardWhileInHand(card); }}
		for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onEnemyUseCard(card); ((DuelistCard)c).onEnemyUseCardWhileInDiscard(card);}}
		for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onEnemyUseCard(card); ((DuelistCard)c).onEnemyUseCardWhileInDraw(card);}}
		for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onEnemyUseCard(card); ((DuelistCard)c).onEnemyUseCardWhileInExhaust(card);}}
		for (AbstractCard c : p.masterDeck.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onEnemyUseCardWhileInDeck(card);}}
		for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onEnemyUseCardWhileInGraveyard(card); }}
		if (player().hasPower(SummonPower.POWER_ID)) {
			SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
			for (DuelistCard c : pow.actualCardSummonList) { if (c instanceof DuelistCard) { ((DuelistCard)c).onEnemyUseCardWhileSummoned(card); }}
		}
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { ((DuelistPotion)pot).onEnemyUseCard(card);  }}
		if (p.stance instanceof DuelistStance) { ((DuelistStance)p.stance).onEnemyUseCard(card);  }
	}
	
	public static void handleOnSoulChangeForAllAbstracts(int newSouls, int change)
	{
		Util.log("Running onSoulChange for all abstract objects!");
		AbstractPlayer p = AbstractDungeon.player;
		for (AbstractRelic r : p.relics) { if (r instanceof DuelistRelic) { ((DuelistRelic)r).onSoulChange(newSouls, change); }}
		for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  ((DuelistOrb)o).onSoulChange(newSouls, change);  }}
		for (AbstractPower pow : p.powers) { if (pow instanceof DuelistPower) { ((DuelistPower)pow).onSoulChange(newSouls, change);  }}
		for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onSoulChange(newSouls, change); ((DuelistCard)c).onSoulChangeWhileInHand(newSouls, change); }}
		for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onSoulChange(newSouls, change); ((DuelistCard)c).onSoulChangeWhileInDiscard(newSouls, change);}}
		for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onSoulChange(newSouls, change); ((DuelistCard)c).onSoulChangeWhileInDraw(newSouls, change);}}
		for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onSoulChange(newSouls, change); ((DuelistCard)c).onSoulChangeWhileInExhaust(newSouls, change);}}
		for (AbstractCard c : p.masterDeck.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onSoulChangeWhileInDeck(newSouls, change);}}
		for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onSoulChangeWhileInGraveyard(newSouls, change); }}
		if (player().hasPower(SummonPower.POWER_ID)) {
			SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
			for (DuelistCard c : pow.actualCardSummonList) { if (c instanceof DuelistCard) { ((DuelistCard)c).onSoulChangeWhileSummoned(newSouls, change); }}
		}
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { ((DuelistPotion)pot).onSoulChange(newSouls, change);  }}
		if (p.stance instanceof DuelistStance) { ((DuelistStance)p.stance).onSoulChange(newSouls, change);  }
	}
	
	private static void handleOnSummonForAllAbstracts(DuelistCard summoned, int amountSummoned)
	{
		Util.log("Running onSummon for all abstract objects! Summoned: " + amountSummoned + "x " + summoned.name);
		AbstractPlayer p = AbstractDungeon.player;
		for (AbstractRelic r : p.relics) { if (r instanceof DuelistRelic) { ((DuelistRelic)r).onSummon(summoned, amountSummoned); }}
		for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  ((DuelistOrb)o).onSummon(summoned, amountSummoned); }}
		for (AbstractPower pow : p.powers) { if (pow instanceof DuelistPower) { ((DuelistPower)pow).onSummon(summoned, amountSummoned); }}
		if (!DuelistMod.mirrorLadybug)
		{
			for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onSummonWhileInHand(summoned, amountSummoned); }}
			for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onSummonWhileInDiscard(summoned, amountSummoned); }}
			for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onSummonWhileInDraw(summoned, amountSummoned); }}
			for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onSummonWhileInGraveyard(summoned, amountSummoned); }}
			if (player().hasPower(SummonPower.POWER_ID)) {
				SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
				for (DuelistCard c : pow.actualCardSummonList) { if (c instanceof DuelistCard) { ((DuelistCard)c).onSummonWhileSummoned(summoned, amountSummoned); }}
			}
		}
		else
		{
			for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard && (!(c instanceof MirrorLadybug))) { ((DuelistCard)c).onSummonWhileInHand(summoned, amountSummoned); }}
			for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard && (!(c instanceof MirrorLadybug))) { ((DuelistCard)c).onSummonWhileInDiscard(summoned, amountSummoned); }}
			for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard && (!(c instanceof MirrorLadybug))) { ((DuelistCard)c).onSummonWhileInDraw(summoned, amountSummoned); }}
			for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard && (!(c instanceof MirrorLadybug))) { ((DuelistCard)c).onSummonWhileInGraveyard(summoned, amountSummoned); }}
			if (player().hasPower(SummonPower.POWER_ID)) {
				SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
				for (DuelistCard c : pow.actualCardSummonList) { if (c instanceof DuelistCard && (!(c instanceof MirrorLadybug))) { ((DuelistCard)c).onSummonWhileSummoned(summoned, amountSummoned); }}
			}
		}
		for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onSummonWhileInExhaust(summoned, amountSummoned); }}
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { ((DuelistPotion)pot).onSummon(summoned, amountSummoned); }}
		if (p.stance instanceof DuelistStance) { ((DuelistStance)p.stance).onSummon(summoned, amountSummoned); }
	}
		
	public boolean hasOtherMonsterTypes(CardTags excludeTag)
	{
		for (CardTags t : DuelistMod.monsterTypes)
		{
			if (this.hasTag(t) && !t.equals(excludeTag)) { return true; }
		}
		return false;
	}

	public void makeFleeting()
	{
		FleetingField.fleeting.set(this, true);
		this.tags.add(Tags.NO_CREATOR);
		this.tags.add(Tags.NO_CARD_FOR_RANDOM_DECK_POOLS);
	}
	
	public void makeFleeting(boolean set)
	{
		FleetingField.fleeting.set(this, set);
		this.tags.add(Tags.NO_CREATOR);
		this.tags.add(Tags.NO_CARD_FOR_RANDOM_DECK_POOLS);
	}
	
	public void makeGrave()
	{
		GraveField.grave.set(this, true);
	}
	
	public void makeGrave(boolean set)
	{
		GraveField.grave.set(this, set);
	}
	
	public void makeSoulbound(boolean set)
	{
		SoulboundField.soulbound.set(this, set);
	}
	
	public void makeRefund(boolean set, int amt)
	{
		RefundFields.baseRefund.set(this, amt);
		RefundFields.refund.set(this, amt);
	}
	
	public void makeMegatyped()
	{
		if (!this.hasTag(Tags.MEGATYPED))
		{
			for (CardTags t : DuelistMod.monsterTypes) { this.tags.add(t); }
			this.tags.add(Tags.MEGATYPED);
		}
	}
	
	public void lavaZombieEffectHandler()
	{
		if (!AbstractDungeon.player.hasEmptyOrb())
		{
			ArrayList<Lava> lavas = new ArrayList<Lava>();
			for (AbstractOrb o : AbstractDungeon.player.orbs)
			{
				if (o instanceof Lava)
				{
					lavas.add((Lava) o);
				}
			}
			
			if (lavas.size() > 0)
			{
				for (Lava blurp : lavas)
				{
					blurp.zombieTributeTrigger();
					blurp.updateDescription();
					if (DuelistMod.debug) { DuelistMod.logger.info("Lava orb triggered zombie tribute effect. BLurp"); }
				}
			}
		}
	}
	
	
	public void exodiaDeckCardUpgradeDesc(String UPGRADE_DESCRIPTION)
	{
		if (StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Exodia Deck"))
		{
			this.rawDescription = "Soulbound NL " + UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
		else
		{
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}    
	}
	
	public static void fetch(CardGroup group, boolean top)
	{
		if (top) { AbstractDungeon.actionManager.addToTop(new FetchAction(group)); }
		else { AbstractDungeon.actionManager.addToBottom(new FetchAction(group)); }
	}
	
	public static void fetch(int amount, CardGroup group, boolean top)
	{
		if (top) { AbstractDungeon.actionManager.addToTop(new FetchAction(group, amount)); }
		else { AbstractDungeon.actionManager.addToBottom(new FetchAction(group, amount)); }
	}
	
	public AbstractCard makeFullCopy()
	{
		AbstractCard c = super.makeStatEquivalentCopy();
		c.exhaust = this.exhaust;
		return c;
	}
	
	public static ArrayList<CardTags> getAllMonsterTypes(AbstractCard c)
	{
		ArrayList<CardTags> toRet = new ArrayList<CardTags>();
		for (CardTags t : DuelistMod.monsterTypes)
		{
			if (c.hasTag(t)) { toRet.add(t); }
		}
		
		return toRet;
	}
	
	public ArrayList<CardTags> getAllMonsterTypes()
	{
		ArrayList<CardTags> toRet = new ArrayList<CardTags>();
		for (CardTags t : DuelistMod.monsterTypes)
		{
			if (this.hasTag(t)) { toRet.add(t); }
		}
		
		return toRet;
	}
	
	public static int cursedBillGoldLoss()
	{
		int loss = 0;
		for (AbstractCard c : AbstractDungeon.player.drawPile.group)
		{
			if (c instanceof CursedBill)
			{
				loss += c.magicNumber;
			}
		}
		return loss;
	}
	
	public static boolean hasSummoningCurse()
	{
		for (AbstractCard c : player().hand.group)
		{
			if (c instanceof SummoningCurse)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean isPsiCurseActive()
	{
		for (AbstractCard c : player().drawPile.group)
		{
			if (c instanceof PsiCurse)
			{
				return true;
			}
		}
		
		for (AbstractCard c : player().discardPile.group)
		{
			if (c instanceof PsiCurse)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public static AbstractPower getTypeAssociatedBuff(CardTags type, int turnAmount)
	{
		Map<CardTags,AbstractPower> powerTypeMap = new HashMap<CardTags,AbstractPower>();
		powerTypeMap.put(Tags.AQUA, new FishscalesPower(turnAmount));
		powerTypeMap.put(Tags.DRAGON, new Dragonscales(turnAmount));
		powerTypeMap.put(Tags.FIEND, new BloodPower(turnAmount));
		powerTypeMap.put(Tags.INSECT, new CocoonPower(AbstractDungeon.player, AbstractDungeon.player, 3));
		powerTypeMap.put(Tags.MACHINE, new FluxPower(turnAmount));
		powerTypeMap.put(Tags.NATURIA, new VinesPower(turnAmount));
		powerTypeMap.put(Tags.PLANT, new ThornsPower(AbstractDungeon.player, turnAmount));
		powerTypeMap.put(Tags.PREDAPLANT, new ThornsPower(AbstractDungeon.player, turnAmount));
		powerTypeMap.put(Tags.SPELLCASTER, new MagickaPower(AbstractDungeon.player, AbstractDungeon.player, turnAmount));
		powerTypeMap.put(Tags.SUPERHEAVY, new DexterityPower(AbstractDungeon.player, turnAmount));
		powerTypeMap.put(Tags.TOON_POOL, new RetainCardPower(AbstractDungeon.player, 1));
		powerTypeMap.put(Tags.WARRIOR, new VigorPower(AbstractDungeon.player, turnAmount));
		powerTypeMap.put(Tags.ZOMBIE, new TrapHolePower(AbstractDungeon.player, AbstractDungeon.player, 1));
		powerTypeMap.put(Tags.ROCK, new PlatedArmorPower(AbstractDungeon.player, 2));
		if (!powerTypeMap.get(type).equals(null)) { return powerTypeMap.get(type); }
		else { return new ElectricityPower(turnAmount); }
	}
	
	public static SummonPower getSummonPower()
	{
		if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID))
		{
			SummonPower pow = (SummonPower)AbstractDungeon.player.getPower(SummonPower.POWER_ID);
			return pow;
		}
		else
		{
			return null;
		}		
	}
	
	public static int countMonsterTypesInPile(ArrayList<AbstractCard> pile)
	{
		ArrayList<CardTags> types = new ArrayList<CardTags>();
		for (AbstractCard c : pile)
		{
			ArrayList<CardTags> temp = getAllMonsterTypes(c);
			for (CardTags t : temp)
			{
				if (!types.contains(t))
				{
					types.add(t);
				}
			}
		}
		return types.size();
	}
	
	public static AbstractMonster getRandomMonster()
	{
		AbstractMonster m = AbstractDungeon.getRandomMonster();
		return m;
	}

	protected int getXEffect() {
		if (energyOnUse < EnergyPanel.totalCount) {
			energyOnUse = EnergyPanel.totalCount;
		}

		int effect = EnergyPanel.totalCount;
		if (energyOnUse != -1) {
			effect = energyOnUse;
		}
		if (player().hasRelic(ChemicalX.ID)) {
			effect += ChemicalX.BOOST;
			player().getRelic(ChemicalX.ID).flash();
		}
		return effect;
	}
	
	public void fish(int amt)
	{
		this.addToBot(new FishAction(amt));
	}
	
	public void tsunami(int amt)
	{
		this.addToBot(new TsunamiAction(amt));
	}
	
	public void stunEnemy()
	{
		AbstractMonster m = AbstractDungeon.getRandomMonster();
		if (m != null) { stunEnemy(m); }
	}
	
	public static void stunEnemyStatic()
	{
		AbstractMonster m = AbstractDungeon.getRandomMonster();
		if (m != null) { stunEnemyStatic(m); }
	}

	public void stunEnemy(AbstractMonster m)
	{
		this.addToBot(new StunMonsterAction(m, AbstractDungeon.player));
	}
	
	public static void stunEnemyStatic(AbstractMonster m)
	{
		AbstractDungeon.actionManager.addToBottom(new StunMonsterAction(m, AbstractDungeon.player));
	}
	
	public void stunAllEnemies()
	{
		ArrayList<AbstractMonster> mons = getAllMons();
		for (AbstractMonster m : mons) { stunEnemy(m); }
	}
	
	public static void stunAllEnemiesStatic()
	{
		ArrayList<AbstractMonster> mons = getAllMons();
		for (AbstractMonster m : mons) { stunEnemyStatic(m); }
	}
	
	protected void useXEnergy() {
		AbstractDungeon.actionManager.addToTop(new LoseXEnergyAction(player(), freeToPlayOnce));
	}

	public static void damageSelf(int DAMAGE)
	{
		AbstractDungeon.actionManager.addToBottom(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, DAMAGE, AbstractGameAction.AttackEffect.POISON));
	}
	
	public static void damageSelfNotHP(int DAMAGE)
	{
		AbstractDungeon.actionManager.addToBottom(new DamageAction(player(), new DamageInfo(player(), DAMAGE, DamageInfo.DamageType.NORMAL)));
	}

	public static void heal(AbstractPlayer p, int amount)
	{
		AbstractDungeon.actionManager.addToTop(new HealAction(p, p, amount));
	}
	
	public static void siphon(AbstractMonster m, int amt)
	{
		AbstractDungeon.actionManager.addToBottom(new LoseHPAction(m, AbstractDungeon.player, amt, AbstractGameAction.AttackEffect.NONE));
		gainTempHP(amt);
	}

	protected void healMonster(AbstractMonster p, int amount)
	{
		AbstractDungeon.actionManager.addToTop(new HealAction(p, p, amount));
	}

	public static void gainGold(int amount, AbstractCreature owner, boolean rain)
	{
		if (!AbstractDungeon.player.hasRelic(Ectoplasm.ID)) 
		{
			CardCrawlGame.sound.play("GOLD_GAIN");
			AbstractDungeon.actionManager.addToBottom(new ObtainGoldAction(amount, owner, rain));
		}
	}
	
	public static void loseGold(int amount)
	{
		AbstractDungeon.player.loseGold(amount);
	}

	public static void draw(int cards) {
		AbstractDungeon.actionManager.addToTop(new DrawCardAction(player(), cards));
	}
	
	public static void drawTag(int cards, CardTags tag)
	{
		AbstractDungeon.actionManager.addToTop(new DrawFromTagAction(player(), cards, tag));	
	}
	
	public static void drawTag(int cards, CardTags tag, boolean actionManagerBottom)
	{
		if (actionManagerBottom) { AbstractDungeon.actionManager.addToBottom(new DrawFromTagAction(player(), cards, tag));	}
		else { AbstractDungeon.actionManager.addToTop(new DrawFromTagAction(player(), cards, tag));	}
		
	}

	public static void drawTags(int cards, CardTags tag, CardTags tagB, boolean actionManagerBottom)
	{
		if (actionManagerBottom) { AbstractDungeon.actionManager.addToBottom(new DrawFromBothTagsAction(player(), cards, tag, tagB));	}
		else { AbstractDungeon.actionManager.addToTop(new DrawFromBothTagsAction(player(), cards, tag, tagB));	}
		
	}
	
	public static void drawRare(int cards, CardRarity tag)
	{
		AbstractDungeon.actionManager.addToTop(new DrawFromRarityAction(player(), cards, tag));	
	}
	
	public static void drawRare(int cards, CardRarity tag, boolean actionManagerBottom)
	{
		if (actionManagerBottom) { AbstractDungeon.actionManager.addToBottom(new DrawFromRarityAction(player(), cards, tag));		}
		else { AbstractDungeon.actionManager.addToTop(new DrawFromRarityAction(player(), cards, tag)); }
		
	}
	
	public static void drawRandomType(int cards, boolean actionManagerBottom, int seed)
	{
		switch (seed)
    	{
    		case 1: drawTag(cards, Tags.DRAGON, actionManagerBottom); AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Drawing: Dragons", 1.0F, 2.0F)); break;
    		case 2: drawTag(cards, Tags.MONSTER, actionManagerBottom); AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Drawing: Monsters", 1.0F, 2.0F)); break;
    		case 3: drawTag(cards, Tags.SPELL, actionManagerBottom); AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Drawing: Spells", 1.0F, 2.0F)); break;
    		case 4: drawTag(cards, Tags.TRAP, actionManagerBottom); AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Drawing: Traps", 1.0F, 2.0F)); break;
    		case 5: 
    			CardTags randomChoice = DuelistMod.monsterTypes.get(AbstractDungeon.cardRandomRng.random(DuelistMod.monsterTypes.size() - 1));
    			drawTag(cards, randomChoice, actionManagerBottom); 
    			AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Drawing: #y" + DuelistMod.typeCardMap_NAME.get(randomChoice) + "s", 1.0F, 2.0F)); 
    			break;
    		case 6: 
    			CardTags randomChoiceB = DuelistMod.monsterTypes.get(AbstractDungeon.cardRandomRng.random(DuelistMod.monsterTypes.size() - 1));
    			drawTag(cards, randomChoiceB, actionManagerBottom); 
    			AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Drawing: #y" + DuelistMod.typeCardMap_NAME.get(randomChoiceB) + "s", 1.0F, 2.0F)); 
    			break;
    		case 7: drawTag(cards, Tags.SPELL, actionManagerBottom); AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Drawing: Spells", 1.0F, 2.0F)); break;
    		case 8: drawRare(cards, CardRarity.COMMON, actionManagerBottom); AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Drawing: Commons", 1.0F, 2.0F)); break;
    		case 9: drawRare(cards, CardRarity.UNCOMMON, actionManagerBottom); AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Drawing: Uncommons", 1.0F, 2.0F)); break;
    		case 10: drawRare(cards, CardRarity.RARE, actionManagerBottom); AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Drawing: Rares", 1.0F, 2.0F)); break;
    		case 11: drawTag(cards, Tags.MONSTER, actionManagerBottom); AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Drawing: Monsters", 1.0F, 2.0F)); break;
    		case 12: drawTag(cards, Tags.TRAP, actionManagerBottom); AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Drawing: Traps", 1.0F, 2.0F)); break;
    		default: break;
    	}
	}
	
	public static void drawBottom(int cards) {
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(player(), cards));
	}

	public static void discard(int amount, boolean isRandom)
	{
		AbstractDungeon.actionManager.addToBottom(new DiscardAction(player(), player(), amount, isRandom));
	}
	
	public static void exhaust(int amount, boolean isRandom)
	{
		AbstractDungeon.actionManager.addToBottom(new ExhaustAction(amount, isRandom));
	}

	public static void discardTop(int amount, boolean isRandom)
	{
		AbstractDungeon.actionManager.addToTop(new DiscardAction(player(), player(), amount, isRandom, false));
	}

	public static void gainEnergy(int energy) {
		AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(energy));
	}
	
	public static void addCardToHand(AbstractCard card)
	{
		if (AbstractDungeon.player.hand.group.size() < BaseMod.MAX_HAND_SIZE)
		{
			if ((card instanceof TokenCard || card.hasTag(Tags.TOKEN)) && !card.upgraded && player().hasPower(WonderGaragePower.POWER_ID) && card.canUpgrade()) 
			{ 
				card.upgrade(); 
			}
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card, false));
		}
	}
	
	public static void addCardsToHand(ArrayList<AbstractCard> cards)
	{
		for (AbstractCard c : cards) { addCardToHand(c); }
	}
	
	public static void addCardToHand(AbstractCard card, int amt)
	{
		if ((card instanceof TokenCard || card.hasTag(Tags.TOKEN)) && !card.upgraded && player().hasPower(WonderGaragePower.POWER_ID) && card.canUpgrade()) 
		{ 
			card.upgrade(); 
		}
		for (int i = 0; i < amt; i++) { addCardToHand(card); }
	}
	
	public static void gainTempHP(int amount)
	{
		AbstractDungeon.actionManager.addToTop(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, amount));
	}
	
	public static void giveTempHP(int amt, AbstractCreature target)
	{
		AbstractDungeon.actionManager.addToTop(new AddTemporaryHPAction(target, target, amt));
	}
	
	public static void getPotion(AbstractPotion pot)
	{
		AbstractDungeon.actionManager.addToTop(new ObtainPotionAction(pot));
	}
	
	// =============== /MISC ACTION FUNCTIONS/ =======================================================================================================================================================
	 
	// =============== STANCE FUNCTIONS =========================================================================================================================================================
		
	public static void exitStance()
	{
		AbstractDungeon.actionManager.addToBottom(new DuelistChangeStanceAction("Neutral"));
	}
	
	public void changeStanceInst(String stance)
	{
		this.addToBot(new DuelistChangeStanceAction(stance));
	}
	
	public static void changeStance(String name)
	{
		AbstractDungeon.actionManager.addToBottom(new DuelistChangeStanceAction(name));
	}
	
	public static void changeToRandomStance(boolean allowChaotic, boolean allowDivinity, boolean allowBaseGame, boolean allowDuelist)
	{
		if (!allowBaseGame && !allowDuelist) { return; }
		ArrayList<AbstractStance> stances = new ArrayList<AbstractStance>();
		if (allowDuelist)
		{
			stances.add(new Samurai());
			stances.add(new Guarded());
			stances.add(new Spectral());
			stances.add(new Meditative());		
			stances.add(new Forsaken());
			stances.add(new Entrenched());
			stances.add(new Nimble());
			stances.add(new Unstable());
			if (allowChaotic) { stances.add(new Chaotic()); }
		}
		if (allowBaseGame)
		{
			stances.add(new CalmStance());
			stances.add(new WrathStance());
			if (allowDivinity) { stances.add(new DivinityStance()); }
		}

		String newStance = stances.get(AbstractDungeon.cardRandomRng.random(stances.size() - 1)).ID;
		changeStance(newStance);
		Util.log("Random stance we changed to: " + newStance);
	}
	
	public static void changeToRandomStance()
	{
		changeToRandomStance(false, false, true, true);
	}
	
	public static void changeToRandomStance(boolean allowChaotic, boolean allowDivinity)
	{
		changeToRandomStance(allowChaotic, allowDivinity, true, true);
	}
	
	// =============== /STANCE FUNCTIONS/ =======================================================================================================================================================
		
	// =============== SUMMON MONSTER FUNCTIONS =========================================================================================================================================================
	public void summon()
	{
		summon(AbstractDungeon.player, this.summons, this);
	}
	
	public static void summon(AbstractPlayer p, int SUMMONS, DuelistCard c)
	{
		/*if (c.hasTag(Tags.ZOMBIE) && c.misc == 52 && Util.getChallengeLevel() > 3 && Util.deckIs("Zombie Deck")) 
		{
			Util.log("Triggered a Zombie Special Summon from " + c.cardID + " (" + c.name + ")");
			c = new SpiritToken();
		}*/
		if (hasSummoningCurse()) { return; }
		boolean challengeFailure = (Util.isCustomModActive("theDuelist:SummonRandomizer"));
		if (challengeFailure)
		{
			if (Util.isCustomModActive("challengethespire:Bronze Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 3) == 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedSummonActionText, 1.0F, 2.0F));
					return; 
				}
			}
			else if (Util.isCustomModActive("challengethespire:Silver Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 2) == 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedSummonActionText, 1.0F, 2.0F));
					return; 
				}
			}
			else if (Util.isCustomModActive("challengethespire:Gold Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 3) != 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedSummonActionText, 1.0F, 2.0F));
					return; 
				}
			}
			else if (Util.isCustomModActive("challengethespire:Platinum Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 4) != 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedSummonActionText, 1.0F, 2.0F));
					return; 
				}
			}
		}
		
		//int currentDeck = 0;
		//if (StarterDeckSetup.getCurrentDeck().getArchetypeCards().size() > 0) { currentDeck = StarterDeckSetup.getCurrentDeck().getIndex(); }
		Util.log("theDuelist:DuelistCard:summon() ---> called summon()");
		if (!DuelistMod.checkTrap)
		{
			Util.log("theDuelist:DuelistCard:summon() ---> no check trap, SUMMONS: " + SUMMONS);
			// Check to make sure they still have summon power, if they do not give it to them with a stack of 0
			if (!p.hasPower(SummonPower.POWER_ID))
			{
				AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SummonPower(AbstractDungeon.player, SUMMONS, c.originalName, "#b" + SUMMONS + " monsters summoned. Maximum of 5 Summons.", c), SUMMONS));
				int startSummons = SUMMONS;
				// Handle abstract objects
				handleOnSummonForAllAbstracts(c, startSummons);

				// Set last summoned type to random type from this monster
				ArrayList<CardTags> toRet = getAllMonsterTypes(c);
				if (toRet.size() > 0) { DuelistMod.lastTagSummoned = toRet.get(AbstractDungeon.cardRandomRng.random(toRet.size() - 1)); }
		
				if (!c.hasTag(Tags.TOKEN))
				{
					DuelistMod.summonCombatCount += startSummons;
					DuelistMod.summonRunCount += startSummons;
					DuelistMod.summonTurnCount += startSummons;
				}		
			}

			else
			{
				// Setup Pot of Generosity
				int potSummons = 0;
				int startSummons = p.getPower(SummonPower.POWER_ID).amount;
				SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
				int maxSummons = summonsInstance.MAX_SUMMONS;
				if ((startSummons + SUMMONS) > maxSummons) { potSummons = maxSummons - startSummons; }
				else { potSummons = SUMMONS; }

				// Add SUMMONS
				summonsInstance.amount += potSummons;

				if (potSummons > 0) 
				{ 
					for (int i = 0; i < potSummons; i++) 
					{ 
						summonsInstance.summonList.add(c.originalName);
						summonsInstance.actualCardSummonList.add((DuelistCard) c.makeStatEquivalentCopy());
					} 
				}
				
				// Handle abstract objects
				handleOnSummonForAllAbstracts(c, potSummons);

				if (p.hasPower(UltimateOfferingPower.POWER_ID) && potSummons == 0 && SUMMONS != 0)
				{
					Util.log("Triggered Ultimate Offering effect!");
					UltimateOfferingPower pow = (UltimateOfferingPower) p.getPower(UltimateOfferingPower.POWER_ID);
					pow.onTriggerEffect();
				}
				
				// Check for new summoned types
				if (potSummons > 0)
				{
					ArrayList<CardTags> toRet = getAllMonsterTypes(c);
					if (toRet.size() > 0) { DuelistMod.lastTagSummoned = toRet.get(AbstractDungeon.cardRandomRng.random(toRet.size() - 1)); }
				}


				// Update UI
				summonsInstance.updateCount(summonsInstance.amount);
				summonsInstance.updateStringColors();
				summonsInstance.updateDescription();
				if (!c.hasTag(Tags.TOKEN))
				{
					DuelistMod.summonCombatCount += potSummons;
					DuelistMod.summonRunCount += potSummons;
					DuelistMod.summonTurnCount += potSummons;
				}		

				// Check for Trap Hole
				if (p.hasPower(TrapHolePower.POWER_ID) && !DuelistMod.checkTrap)
				{
					for (int i = 0; i < potSummons; i++)
					{
						TrapHolePower power = (TrapHolePower) p.getPower(TrapHolePower.POWER_ID);
						int randomNum = AbstractDungeon.cardRandomRng.random(1, 10);
						if (randomNum <= power.chance || power.chance > 10)
						{
							DuelistMod.checkTrap = true;
							power.flash();
							if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:summon ---> triggered trap hole with roll of: " + randomNum); }
							powerTribute(p, 1, false);
							DuelistCard cardCopy = DuelistCard.newCopyOfMonster(c.originalName);
							if (cardCopy != null && allowResummonsWithExtraChecks(cardCopy))
							{
								AbstractMonster m = AbstractDungeon.getRandomMonster();								
								resummon(cardCopy, m, 1, c.upgraded, false);
								if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:summon ---> trap hole resummoned properly"); }
							}
						}
						else
						{
							if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:summon ---> did not trigger trap hole with roll of: " + randomNum); }
						}
					}
				}

				// Check for Yami
				if (p.hasPower(YamiPower.POWER_ID) && c.hasTag(Tags.SPELLCASTER))
				{
					spellSummon(p, 1, c);
				}

				// Update UI
				if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:summon() ---> updating summons instance"); }
				summonsInstance.updateCount(summonsInstance.amount);
				summonsInstance.updateStringColors();
				summonsInstance.updateDescription();
				if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:summon() ---> summons instance amount: " + summonsInstance.amount); }
			}
		}
		else
		{			
			if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:summon() ---> check trap, SUMMONS: " + SUMMONS); }
			trapHoleSummon(p, SUMMONS, c);		
		}
		player().hand.glowCheck();
		AbstractDungeon.actionManager.addToBottom(new RefreshHandGlowAction(player()));
	}

	public static void spellSummon(AbstractPlayer p, int SUMMONS, DuelistCard c)
	{
		if (hasSummoningCurse()) { return; }
		boolean challengeFailure = (Util.isCustomModActive("theDuelist:SummonRandomizer"));
		if (challengeFailure)
		{
			if (Util.isCustomModActive("challengethespire:Bronze Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 3) == 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedSummonActionText, 1.0F, 2.0F));
					return; 
				}
			}
			else if (Util.isCustomModActive("challengethespire:Silver Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 2) == 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedSummonActionText, 1.0F, 2.0F));
					return; 
				}
			}
			else if (Util.isCustomModActive("challengethespire:Gold Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 3) != 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedSummonActionText, 1.0F, 2.0F));
					return; 
				}
			}
			else if (Util.isCustomModActive("challengethespire:Platinum Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 4) != 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedSummonActionText, 1.0F, 2.0F));
					return; 
				}
			}
		}
		
		//int currentDeck = 0;		
		//if (StarterDeckSetup.getCurrentDeck().getArchetypeCards().size() > 0) { currentDeck = StarterDeckSetup.getCurrentDeck().getIndex(); }
		if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:spellSummon() ---> called spellSummon()"); }
		if (!DuelistMod.checkTrap)
		{
			if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:spellSummon() ---> no check trap, SUMMONS: " + SUMMONS); }
			// Check to make sure they still have summon power, if they do not give it to them with a stack of 0
			if (!p.hasPower(SummonPower.POWER_ID))
			{
				AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SummonPower(AbstractDungeon.player, SUMMONS, c.originalName, "#b" + SUMMONS + " monsters summoned. Maximum of 5 Summons.", c), SUMMONS));
				int startSummons = SUMMONS;
				// Handle abstract objects
				handleOnSummonForAllAbstracts(c, startSummons);

				// Check for new summoned types			
				ArrayList<CardTags> toRet = getAllMonsterTypes(c);
				if (toRet.size() > 0) { DuelistMod.lastTagSummoned = toRet.get(AbstractDungeon.cardRandomRng.random(toRet.size() - 1)); }

				if (!c.hasTag(Tags.TOKEN))
				{
					DuelistMod.summonCombatCount += startSummons;
					DuelistMod.summonRunCount += startSummons;
					DuelistMod.summonTurnCount += startSummons;
				}		
			}

			else
			{
				// Setup Pot of Generosity
				int potSummons = 0;
				int startSummons = p.getPower(SummonPower.POWER_ID).amount;
				SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
				int maxSummons = summonsInstance.MAX_SUMMONS;
				if ((startSummons + SUMMONS) > maxSummons) { potSummons = maxSummons - startSummons; }
				else { potSummons = SUMMONS; }

				// Add SUMMONS
				summonsInstance.amount += potSummons;

				if (potSummons > 0) 
				{ 
					for (int i = 0; i < potSummons; i++) 
					{ 
						summonsInstance.summonList.add(c.originalName);
						summonsInstance.actualCardSummonList.add((DuelistCard) c.makeStatEquivalentCopy());
					} 
				}

				// Handle abstract objects
				handleOnSummonForAllAbstracts(c, potSummons);
			
				// Check for new summoned types
				if (potSummons > 0)
				{
					ArrayList<CardTags> toRet = getAllMonsterTypes(c);
					if (toRet.size() > 0) { DuelistMod.lastTagSummoned = toRet.get(AbstractDungeon.cardRandomRng.random(toRet.size() - 1)); }
				}
				
				// Update UI
				summonsInstance.updateCount(summonsInstance.amount);
				summonsInstance.updateStringColors();
				summonsInstance.updateDescription();
				if (!c.hasTag(Tags.TOKEN))
				{
					DuelistMod.summonCombatCount += potSummons;
					DuelistMod.summonRunCount += potSummons;
					DuelistMod.summonTurnCount += potSummons;
				}		

			}
		}

		else
		{			
			if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:spellSummon() ---> check trap, SUMMONS: " + SUMMONS); }
			trapHoleSummon(p, SUMMONS, c);		
		}
		player().hand.glowCheck();
		AbstractDungeon.actionManager.addToBottom(new RefreshHandGlowAction(player()));
	}


	public static void powerSummon(AbstractPlayer p, int SUMMONS, String cardName, boolean fromUO)
	{
		if (hasSummoningCurse()) { return; }
		boolean challengeFailure = (Util.isCustomModActive("theDuelist:SummonRandomizer"));
		if (challengeFailure)
		{
			if (Util.isCustomModActive("challengethespire:Bronze Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 3) == 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedSummonActionText, 1.0F, 2.0F));
					return; 
				}
			}
			else if (Util.isCustomModActive("challengethespire:Silver Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 2) == 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedSummonActionText, 1.0F, 2.0F));
					return; 
				}
			}
			else if (Util.isCustomModActive("challengethespire:Gold Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 3) != 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedSummonActionText, 1.0F, 2.0F));
					return; 
				}
			}
			else if (Util.isCustomModActive("challengethespire:Platinum Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 4) != 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedSummonActionText, 1.0F, 2.0F));
					return; 
				}
			}
		}
		
		//int currentDeck = 0;		
		//if (StarterDeckSetup.getCurrentDeck().getArchetypeCards().size() > 0) { currentDeck = StarterDeckSetup.getCurrentDeck().getIndex(); }
		if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon() ---> called powerSummon()"); }
		if (!DuelistMod.checkTrap)
		{
			if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon() ---> no check trap, SUMMONS: " + SUMMONS); }
			DuelistCard c = DuelistMod.summonMap.get(cardName);
			if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon() ---> c: " + c.originalName); }
			// Check to make sure they still have summon power, if they do not give it to them with a stack of 0
			if (!p.hasPower(SummonPower.POWER_ID))
			{
				//DuelistCard newSummonCard = (DuelistCard) DefaultMod.summonMap.get(cardName).makeCopy();
				AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SummonPower(AbstractDungeon.player, SUMMONS, cardName, "#b" + SUMMONS + " monsters summoned. Maximum of 5 Summons."), SUMMONS));
				int startSummons = SUMMONS;
				// Handle abstract objects
				handleOnSummonForAllAbstracts(c, startSummons);
				
				// Check for new summoned types
				ArrayList<CardTags> toRet = getAllMonsterTypes(c);
				if (toRet.size() > 0) { DuelistMod.lastTagSummoned = toRet.get(AbstractDungeon.cardRandomRng.random(toRet.size() - 1)); }
				
				if (!c.hasTag(Tags.TOKEN))
				{
					DuelistMod.summonCombatCount += startSummons;
					DuelistMod.summonRunCount += startSummons;
					DuelistMod.summonTurnCount += startSummons;
				}
				
			}
			else
			{
				// Setup Pot of Generosity
				int potSummons = 0;
				int startSummons = p.getPower(SummonPower.POWER_ID).amount;
				SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
				int maxSummons = summonsInstance.MAX_SUMMONS;
				if ((startSummons + SUMMONS) > maxSummons) { potSummons = maxSummons - startSummons; }
				else { potSummons = SUMMONS; }

				// Add SUMMONS
				summonsInstance.amount += potSummons;

				if (potSummons > 0) { for (int i = 0; i < potSummons; i++) { summonsInstance.summonList.add(cardName); summonsInstance.actualCardSummonList.add((DuelistCard) c.makeStatEquivalentCopy());} }

				// Handle abstract objects
				handleOnSummonForAllAbstracts(c, potSummons);
			
				// Check for Ultimate Offering
				if (p.hasPower(UltimateOfferingPower.POWER_ID) && potSummons == 0 && SUMMONS != 0 && !fromUO)
				{
					Util.log("Triggered Ultimate Offering effect!");
					UltimateOfferingPower pow = (UltimateOfferingPower) p.getPower(UltimateOfferingPower.POWER_ID);
					pow.onTriggerEffect();
				}
				
				// Check for new summoned types
				if (potSummons > 0)
				{
					ArrayList<CardTags> toRet = getAllMonsterTypes(c);
					if (toRet.size() > 0) { DuelistMod.lastTagSummoned = toRet.get(AbstractDungeon.cardRandomRng.random(toRet.size() - 1)); }
				}

				// Update UI
				summonsInstance.updateCount(summonsInstance.amount);
				summonsInstance.updateStringColors();
				summonsInstance.updateDescription();
				if (!c.hasTag(Tags.TOKEN))
				{
					DuelistMod.summonCombatCount += potSummons;
					DuelistMod.summonRunCount += potSummons;
					DuelistMod.summonTurnCount += potSummons;
				}		

				// Check for Trap Hole
				if (p.hasPower(TrapHolePower.POWER_ID) && !DuelistMod.checkTrap)
				{
					for (int i = 0; i < potSummons; i++)
					{
						TrapHolePower power = (TrapHolePower) p.getPower(TrapHolePower.POWER_ID);
						int randomNum = AbstractDungeon.cardRandomRng.random(1, 10);
						if (randomNum <= power.chance || power.chance > 10)
						{
							DuelistMod.checkTrap = true;
							power.flash();
							if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon ---> triggered trap hole with roll of: " + randomNum); }
							powerTribute(p, 1, false);
							DuelistCard cardCopy = DuelistCard.newCopyOfMonster(c.originalName);
							if (cardCopy != null && allowResummonsWithExtraChecks(cardCopy))
							{
								AbstractMonster m = AbstractDungeon.getRandomMonster();
								if (m != null) { resummon(cardCopy, m, 1, c.upgraded, false); }
								if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon ---> trap hole resummoned properly"); }
							}
						}
						else
						{
							if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon ---> did not trigger trap hole with roll of: " + randomNum); }
						}
					}
				}

				// Check for Yami
				if (p.hasPower(YamiPower.POWER_ID) && c.hasTag(Tags.SPELLCASTER))
				{
					spellSummon(p, 1, c);
				}

				// Update UI
				if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon() ---> updating summons instance amount"); }
				summonsInstance.updateCount(summonsInstance.amount);
				summonsInstance.updateStringColors();
				summonsInstance.updateDescription();
				if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon() ---> summons instance amount: " + summonsInstance.amount); }
			}
		}

		else
		{
			if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon() ---> check trap, SUMMONS: " + SUMMONS); }
			DuelistCard c = DuelistMod.summonMap.get(cardName);
			if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon() ---> check trap, c: " + c.originalName); }
			trapHoleSummon(p, SUMMONS, c);
		}
		player().hand.glowCheck();
		AbstractDungeon.actionManager.addToBottom(new RefreshHandGlowAction(player()));
	}

	public static void trapHoleSummon(AbstractPlayer p, int SUMMONS, DuelistCard c)
	{		
		if (hasSummoningCurse()) { return; }
		boolean challengeFailure = (Util.isCustomModActive("theDuelist:SummonRandomizer"));
		if (challengeFailure)
		{
			if (Util.isCustomModActive("challengethespire:Bronze Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 3) == 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedSummonActionText, 1.0F, 2.0F));
					return; 
				}
			}
			else if (Util.isCustomModActive("challengethespire:Silver Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 2) == 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedSummonActionText, 1.0F, 2.0F));
					return; 
				}
			}
			else if (Util.isCustomModActive("challengethespire:Gold Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 3) != 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedSummonActionText, 1.0F, 2.0F));
					return; 
				}
			}
			else if (Util.isCustomModActive("challengethespire:Platinum Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 4) != 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedSummonActionText, 1.0F, 2.0F));
					return; 
				}
			}
		}
		
		//int currentDeck = 0;		
		//if (StarterDeckSetup.getCurrentDeck().getArchetypeCards().size() > 0) { currentDeck = StarterDeckSetup.getCurrentDeck().getIndex(); }
		if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:trapHoleSummon() ---> called trapHoleSummon()"); }
		// Check to make sure they still have summon power, if they do not give it to them with a stack of 0
		if (!p.hasPower(SummonPower.POWER_ID))
		{
			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SummonPower(AbstractDungeon.player, SUMMONS, c.originalName, "#b" + SUMMONS + " monsters summoned. Maximum of 5 Summons.", c), SUMMONS));
			int startSummons = SUMMONS;
			// Handle abstract objects
			handleOnSummonForAllAbstracts(c, startSummons);
			
			// Check for new summoned types
			ArrayList<CardTags> toRet = getAllMonsterTypes(c);
			if (toRet.size() > 0) { DuelistMod.lastTagSummoned = toRet.get(AbstractDungeon.cardRandomRng.random(toRet.size() - 1)); }
			
			if (!c.hasTag(Tags.TOKEN))
			{
				DuelistMod.summonCombatCount += startSummons;
				DuelistMod.summonRunCount += startSummons;
				DuelistMod.summonTurnCount += startSummons;
			}		
		}

		else
		{
			// Setup Pot of Generosity
			int potSummons = 0;
			int startSummons = p.getPower(SummonPower.POWER_ID).amount;
			SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
			int maxSummons = summonsInstance.MAX_SUMMONS;
			if ((startSummons + SUMMONS) > maxSummons) { potSummons = maxSummons - startSummons; }
			else { potSummons = SUMMONS; }

			// Add SUMMONS
			summonsInstance.amount += potSummons;

			if (potSummons > 0) 
			{ 
				for (int i = 0; i < potSummons; i++) 
				{ 
					summonsInstance.summonList.add(c.originalName);
					summonsInstance.actualCardSummonList.add((DuelistCard) c.makeStatEquivalentCopy());
				} 
			}
			
			// Handle abstract objects
			handleOnSummonForAllAbstracts(c, potSummons);

			// Check for Ultimate Offering
			if (p.hasPower(UltimateOfferingPower.POWER_ID) && !DuelistMod.checkUO)
			{
				Util.log("theDuelist:DuelistCard:trapHoleSummon() ---> hit Ultimate Offering, SUMMONS: " + SUMMONS + " HARD CODED NOT TO TRIGGER!");
			}
			
			// Check for new summoned types
			if (potSummons > 0)
			{
				ArrayList<CardTags> toRet = getAllMonsterTypes(c);
				if (toRet.size() > 0) { DuelistMod.lastTagSummoned = toRet.get(AbstractDungeon.cardRandomRng.random(toRet.size() - 1)); }
			}
			
			if (DuelistMod.debug)
			{
				int counter = 1;
				for (CardTags t : DuelistMod.summonedTypesThisTurn)
				{
					DuelistMod.logger.info("DuelistMod.summonedTypesThisTurn[" + counter + "]: " + t);
					counter++;
				}
			}


			// Update UI
			summonsInstance.updateCount(summonsInstance.amount);
			summonsInstance.updateStringColors();
			summonsInstance.updateDescription();
			if (!c.hasTag(Tags.TOKEN))
			{
				DuelistMod.summonCombatCount += potSummons;
				DuelistMod.summonRunCount += potSummons;
				DuelistMod.summonTurnCount += potSummons;
			}		
			DuelistMod.checkUO = false;
			DuelistMod.checkTrap = false;
		}
		player().hand.glowCheck();
		AbstractDungeon.actionManager.addToBottom(new RefreshHandGlowAction(player()));
	}

	public static void uoSummon(AbstractPlayer p, int SUMMONS, DuelistCard c)
	{		
		if (hasSummoningCurse()) { return; }
		boolean challengeFailure = (Util.isCustomModActive("theDuelist:SummonRandomizer"));
		if (challengeFailure)
		{
			if (Util.isCustomModActive("challengethespire:Bronze Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 3) == 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedSummonActionText, 1.0F, 2.0F));
					return; 
				}
			}
			else if (Util.isCustomModActive("challengethespire:Silver Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 2) == 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedSummonActionText, 1.0F, 2.0F));
					return; 
				}
			}
			else if (Util.isCustomModActive("challengethespire:Gold Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 3) != 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedSummonActionText, 1.0F, 2.0F));
					return; 
				}
			}
			else if (Util.isCustomModActive("challengethespire:Platinum Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 4) != 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedSummonActionText, 1.0F, 2.0F));
					return; 
				}
			}
		}
		
		//int currentDeck = 0;		
		//if (StarterDeckSetup.getCurrentDeck().getArchetypeCards().size() > 0) { currentDeck = StarterDeckSetup.getCurrentDeck().getIndex(); }
		if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:uoSummon() ---> called uoSummon()"); }
		// Check to make sure they still have summon power, if they do not give it to them with a stack of 0
		if (!p.hasPower(SummonPower.POWER_ID))
		{
			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SummonPower(AbstractDungeon.player, SUMMONS, c.originalName, "#b" + SUMMONS + " monsters summoned. Maximum of 5 Summons.", c), SUMMONS));
			int startSummons = SUMMONS;
			
			// Handle abstract objects
			handleOnSummonForAllAbstracts(c, startSummons);
		}

		else
		{
			// Setup Pot of Generosity
			int potSummons = 0;
			int startSummons = p.getPower(SummonPower.POWER_ID).amount;
			SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
			int maxSummons = summonsInstance.MAX_SUMMONS;
			if ((startSummons + SUMMONS) > maxSummons) { potSummons = maxSummons - startSummons; }
			else { potSummons = SUMMONS; }

			// Add SUMMONS
			summonsInstance.amount += potSummons;

			if (potSummons > 0) 
			{ 
				for (int i = 0; i < potSummons; i++) 
				{ 
					summonsInstance.summonList.add(c.originalName);
					summonsInstance.actualCardSummonList.add((DuelistCard) c.makeStatEquivalentCopy());
				} 
			}
			
			// Handle abstract objects
			handleOnSummonForAllAbstracts(c, potSummons);

			// Update UI
			Util.log("theDuelist:DuelistCard:uoSummon() ---> updating summons instance");
			summonsInstance.updateCount(summonsInstance.amount);
			summonsInstance.updateStringColors();
			summonsInstance.updateDescription();
			Util.log("theDuelist:DuelistCard:uoSummon() ---> summons instance amount: " + summonsInstance.amount);
		}
		player().hand.glowCheck();
		AbstractDungeon.actionManager.addToBottom(new RefreshHandGlowAction(player()));
	}
	
	
	// This function needs to match powerSummon() exactly, except for the first block of code that returns early due to curse/challenges
	// Although it is ok to add extra code on top of powerSummon() inside this function, it just needs to do everything powerSummon() does
	public static void puzzleSummon(AbstractPlayer p, int SUMMONS, String cardName, boolean fromUO)
	{
		if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon() ---> called powerSummon()"); }
		if (!DuelistMod.checkTrap)
		{
			if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon() ---> no check trap, SUMMONS: " + SUMMONS); }
			DuelistCard c = DuelistMod.summonMap.get(cardName);
			if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon() ---> c: " + c.originalName); }
			// Check to make sure they still have summon power, if they do not give it to them with a stack of 0
			if (!p.hasPower(SummonPower.POWER_ID))
			{
				//DuelistCard newSummonCard = (DuelistCard) DefaultMod.summonMap.get(cardName).makeCopy();
				AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new SummonPower(AbstractDungeon.player, SUMMONS, cardName, "#b" + SUMMONS + " monsters summoned. Maximum of 5 Summons."), SUMMONS));
				int startSummons = SUMMONS;
				// Handle abstract objects
				handleOnSummonForAllAbstracts(c, startSummons);
				
				// Check for new summoned types
				ArrayList<CardTags> toRet = getAllMonsterTypes(c);
				if (toRet.size() > 0) { DuelistMod.lastTagSummoned = toRet.get(AbstractDungeon.cardRandomRng.random(toRet.size() - 1)); }
				
				if (!c.hasTag(Tags.TOKEN))
				{
					DuelistMod.summonCombatCount += startSummons;
					DuelistMod.summonRunCount += startSummons;
					DuelistMod.summonTurnCount += startSummons;
				}
				
			}
			else
			{
				// Setup Pot of Generosity
				int potSummons = 0;
				int startSummons = p.getPower(SummonPower.POWER_ID).amount;
				SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
				int maxSummons = summonsInstance.MAX_SUMMONS;
				if ((startSummons + SUMMONS) > maxSummons) { potSummons = maxSummons - startSummons; }
				else { potSummons = SUMMONS; }

				// Add SUMMONS
				summonsInstance.amount += potSummons;

				if (potSummons > 0) { for (int i = 0; i < potSummons; i++) { summonsInstance.summonList.add(cardName); summonsInstance.actualCardSummonList.add((DuelistCard) c.makeStatEquivalentCopy());} }

				// Handle abstract objects
				handleOnSummonForAllAbstracts(c, potSummons);
			
				// Check for Ultimate Offering
				if (p.hasPower(UltimateOfferingPower.POWER_ID) && potSummons == 0 && SUMMONS != 0 && !fromUO)
				{
					Util.log("Triggered Ultimate Offering effect!");
					UltimateOfferingPower pow = (UltimateOfferingPower) p.getPower(UltimateOfferingPower.POWER_ID);
					pow.onTriggerEffect();
				}
				
				// Check for new summoned types
				if (potSummons > 0)
				{
					ArrayList<CardTags> toRet = getAllMonsterTypes(c);
					if (toRet.size() > 0) { DuelistMod.lastTagSummoned = toRet.get(AbstractDungeon.cardRandomRng.random(toRet.size() - 1)); }
				}

				// Update UI
				summonsInstance.updateCount(summonsInstance.amount);
				summonsInstance.updateStringColors();
				summonsInstance.updateDescription();
				if (!c.hasTag(Tags.TOKEN))
				{
					DuelistMod.summonCombatCount += potSummons;
					DuelistMod.summonRunCount += potSummons;
					DuelistMod.summonTurnCount += potSummons;
				}		

				// Check for Trap Hole
				if (p.hasPower(TrapHolePower.POWER_ID) && !DuelistMod.checkTrap)
				{
					for (int i = 0; i < potSummons; i++)
					{
						TrapHolePower power = (TrapHolePower) p.getPower(TrapHolePower.POWER_ID);
						int randomNum = AbstractDungeon.cardRandomRng.random(1, 10);
						if (randomNum <= power.chance || power.chance > 10)
						{
							DuelistMod.checkTrap = true;
							power.flash();
							if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon ---> triggered trap hole with roll of: " + randomNum); }
							powerTribute(p, 1, false);
							DuelistCard cardCopy = DuelistCard.newCopyOfMonster(c.originalName);
							if (cardCopy != null && allowResummonsWithExtraChecks(cardCopy))
							{
								AbstractMonster m = AbstractDungeon.getRandomMonster();
								if (m != null) { resummon(cardCopy, m, 1, c.upgraded, false); }
								if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon ---> trap hole resummoned properly"); }
							}
						}
						else
						{
							if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon ---> did not trigger trap hole with roll of: " + randomNum); }
						}
					}
				}

				// Check for Yami
				if (p.hasPower(YamiPower.POWER_ID) && c.hasTag(Tags.SPELLCASTER))
				{
					spellSummon(p, 1, c);
				}

				// Update UI
				if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon() ---> updating summons instance amount"); }
				summonsInstance.updateCount(summonsInstance.amount);
				summonsInstance.updateStringColors();
				summonsInstance.updateDescription();
				if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon() ---> summons instance amount: " + summonsInstance.amount); }
			}
		}

		else
		{
			if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon() ---> check trap, SUMMONS: " + SUMMONS); }
			DuelistCard c = DuelistMod.summonMap.get(cardName);
			if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:powerSummon() ---> check trap, c: " + c.originalName); }
			trapHoleSummon(p, SUMMONS, c);
		}
		player().hand.glowCheck();
		AbstractDungeon.actionManager.addToBottom(new RefreshHandGlowAction(player()));
	}
	// =============== /SUMMON MONSTER FUNCTIONS/ =======================================================================================================================================================
	
	
	
	// =============== TRIBUTE MONSTER FUNCTIONS =========================================================================================================================================================
	public ArrayList<DuelistCard> tribute()
	{
		return tribute(AbstractDungeon.player, this.tributes, false, this);
	}
	
	public ArrayList<DuelistCard> tribute(boolean tributeAll)
	{
		return tribute(AbstractDungeon.player, 0, tributeAll, this);
	}
	

	public ArrayList<DuelistCard> xCostTributeWithListReturn()
	{
		ArrayList<DuelistCard> size = tribute(AbstractDungeon.player, 0, true, this);
		ArrayList<DuelistCard> returnable = new ArrayList<>();
		if (AbstractDungeon.player.hasRelic(ChemicalX.ID)) 
		{
			for (DuelistCard s : size)
			{
				returnable.add((DuelistCard) s.makeStatEquivalentCopy());
				returnable.add((DuelistCard) s.makeStatEquivalentCopy());
			}
		}
		else
		{
			returnable.addAll(size);
		}
		return returnable;
	}
	
	public int xCostTribute()
	{
		int size = tribute(AbstractDungeon.player, 0, true, this).size();
		if (AbstractDungeon.player.hasRelic(ChemicalX.ID)) { return size * 2; }
		else { return size; }
	}
	
	public int xCostTribute(CardTags tag)
	{
		return xCostTribute(tag, false);
	}
	
	public int xCostTribute(CardTags tag, boolean disallow)
	{
		if (player().hasPower(SummonPower.POWER_ID))
    	{
			int tokens = 0;
	    	SummonPower summonsInstance = (SummonPower) player().getPower(SummonPower.POWER_ID);
	    	ArrayList<DuelistCard> aSummonsList = summonsInstance.actualCardSummonList;
	    	ArrayList<String> newSummonList = new ArrayList<String>();
	    	ArrayList<DuelistCard> aNewSummonList = new ArrayList<DuelistCard>();
	    	ArrayList<DuelistCard> cardsToTribute = new ArrayList<DuelistCard>();
	    	for (DuelistCard s : aSummonsList)
	    	{
	    		if (disallow)
	    		{
	    			if (!s.hasTag(tag))
		    		{
		    			tokens++;
		    			cardsToTribute.add(s);
		    		}
		    		else
		    		{
		    			newSummonList.add(s.originalName);
		    			aNewSummonList.add(s);
		    		}
	    		}
	    		else
	    		{
	    			if (s.hasTag(tag))
		    		{
		    			tokens++;
		    			cardsToTribute.add(s);
		    		}
		    		else
		    		{
		    			newSummonList.add(s.originalName);
		    			aNewSummonList.add(s);
		    		}
	    		}
	    	}
	    	
	    	tributeSpecificCards(cardsToTribute, this, true);
	    	summonsInstance.summonList = newSummonList;
	    	summonsInstance.actualCardSummonList = aNewSummonList;
	    	summonsInstance.amount -= tokens;
	    	summonsInstance.updateStringColors();
	    	summonsInstance.updateDescription();
	    	if (AbstractDungeon.player.hasRelic(ChemicalX.ID)) { return cardsToTribute.size() * 2; }
	    	return cardsToTribute.size();
    	}
		else { return 0; }
	}
	
	public static int xCostTributeStatic(CardTags tag, DuelistCard tc)
	{
		return xCostTributeStatic(tag, false, tc);
	}
	
	public static int xCostTributeStatic(CardTags tag, boolean disallow, DuelistCard tc)
	{
		if (player().hasPower(SummonPower.POWER_ID))
    	{
			int tokens = 0;
	    	SummonPower summonsInstance = (SummonPower) player().getPower(SummonPower.POWER_ID);
	    	ArrayList<DuelistCard> aSummonsList = summonsInstance.actualCardSummonList;
	    	ArrayList<String> newSummonList = new ArrayList<String>();
	    	ArrayList<DuelistCard> aNewSummonList = new ArrayList<DuelistCard>();
	    	ArrayList<DuelistCard> cardsToTribute = new ArrayList<DuelistCard>();
	    	for (DuelistCard s : aSummonsList)
	    	{
	    		if (disallow)
	    		{
	    			if (!s.hasTag(tag))
		    		{
		    			tokens++;
		    			cardsToTribute.add(s);
		    		}
		    		else
		    		{
		    			newSummonList.add(s.originalName);
		    			aNewSummonList.add(s);
		    		}
	    		}
	    		else
	    		{
	    			if (s.hasTag(tag))
		    		{
		    			tokens++;
		    			cardsToTribute.add(s);
		    		}
		    		else
		    		{
		    			newSummonList.add(s.originalName);
		    			aNewSummonList.add(s);
		    		}
	    		}
	    	}
	    	
	    	tributeSpecificCards(cardsToTribute, tc, true);
	    	summonsInstance.summonList = newSummonList;
	    	summonsInstance.actualCardSummonList = aNewSummonList;
	    	summonsInstance.amount -= tokens;
	    	summonsInstance.updateStringColors();
	    	summonsInstance.updateDescription();
	    	if (AbstractDungeon.player.hasRelic(ChemicalX.ID)) { return cardsToTribute.size() * 2; }
	    	return cardsToTribute.size();
    	}
		else { return 0; }
	}
	
	public int xCostTribute(CardRarity tag)
	{
		return xCostTribute(tag, false);
	}
	
	public int xCostTribute(CardRarity tag, boolean disallow)
	{
		if (player().hasPower(SummonPower.POWER_ID))
    	{
			int tokens = 0;
	    	SummonPower summonsInstance = (SummonPower) player().getPower(SummonPower.POWER_ID);
	    	ArrayList<DuelistCard> aSummonsList = summonsInstance.actualCardSummonList;
	    	ArrayList<String> newSummonList = new ArrayList<String>();
	    	ArrayList<DuelistCard> aNewSummonList = new ArrayList<DuelistCard>();
	    	ArrayList<DuelistCard> cardsToTribute = new ArrayList<DuelistCard>();
	    	for (DuelistCard s : aSummonsList)
	    	{
	    		if (disallow)
	    		{
	    			if (!s.rarity.equals(tag))
		    		{
		    			tokens++;
		    			cardsToTribute.add(s);
		    		}
		    		else
		    		{
		    			newSummonList.add(s.originalName);
		    			aNewSummonList.add(s);
		    		}
	    		}
	    		else
	    		{
	    			if (s.rarity.equals(tag))
		    		{
		    			tokens++;
		    			cardsToTribute.add(s);
		    		}
		    		else
		    		{
		    			newSummonList.add(s.originalName);
		    			aNewSummonList.add(s);
		    		}
	    		}
	    	}
	    	
	    	tributeSpecificCards(cardsToTribute, this, true);
	    	summonsInstance.summonList = newSummonList;
	    	summonsInstance.actualCardSummonList = aNewSummonList;
	    	summonsInstance.amount -= tokens;
	    	summonsInstance.updateStringColors();
	    	summonsInstance.updateDescription();
	    	if (AbstractDungeon.player.hasRelic(ChemicalX.ID)) { return cardsToTribute.size() * 2; }
	    	return cardsToTribute.size();
    	}
		else { return 0; }
	}
	
	public int xCostTribute(ArrayList<DuelistCard> cards)
	{
		if (player().hasPower(SummonPower.POWER_ID))
    	{
			int tokens = 0;
	    	SummonPower summonsInstance = (SummonPower) player().getPower(SummonPower.POWER_ID);
	    	ArrayList<DuelistCard> aSummonsList = summonsInstance.actualCardSummonList;
	    	ArrayList<String> newSummonList = new ArrayList<String>();
	    	ArrayList<DuelistCard> aNewSummonList = new ArrayList<DuelistCard>();
	    	ArrayList<DuelistCard> cardsToTribute = new ArrayList<DuelistCard>();
	    	for (DuelistCard s : aSummonsList)
	    	{
	    		boolean trib = false;
	    		for (DuelistCard c : cards) { if (s.getID().equals(c.getID())) { trib = true; break; }}
	    		if (trib)
	    		{
	    			tokens++;
	    			cardsToTribute.add(s);
	    		}
	    		else
	    		{
	    			newSummonList.add(s.originalName);
	    			aNewSummonList.add(s);
	    		}
	    	}
	    	
	    	tributeSpecificCards(cardsToTribute, this, true);
	    	summonsInstance.summonList = newSummonList;
	    	summonsInstance.actualCardSummonList = aNewSummonList;
	    	summonsInstance.amount -= tokens;
	    	summonsInstance.updateStringColors();
	    	summonsInstance.updateDescription();
	    	if (AbstractDungeon.player.hasRelic(ChemicalX.ID)) { return cardsToTribute.size() * 2; }
	    	return cardsToTribute.size();
    	}
		else { return 0; }
	}
	
	public int detonationTribute(boolean all)
	{
		if (all) { return detonationTribute(0, true); }
		else 
		{ 
			int ms = getMaxSummons(AbstractDungeon.player);
			if (ms > 0)
			{
				int roll = AbstractDungeon.cardRandomRng.random(1, ms);
				return detonationTribute(roll, false);
			}
			else
			{
				return detonationTribute(0, false);
			}
		}
	}
	
	public int detonationTribute(int amt)
	{
		return detonationTribute(amt, false);
	}
	
	public int detonationTribute(int amt, boolean all)
	{
		if (player().hasPower(SummonPower.POWER_ID))
    	{
			int tokens = 0;
	    	SummonPower summonsInstance = (SummonPower) player().getPower(SummonPower.POWER_ID);
	    	ArrayList<DuelistCard> aSummonsList = summonsInstance.actualCardSummonList;
	    	ArrayList<String> newSummonList = new ArrayList<String>();
	    	ArrayList<DuelistCard> aNewSummonList = new ArrayList<DuelistCard>();
	    	ArrayList<DuelistCard> cardsToTribute = new ArrayList<DuelistCard>();
	    	for (DuelistCard s : aSummonsList)
	    	{
	    		if (s instanceof ExplosiveToken || s instanceof SuperExplodingToken)
	    		{
	    			if (tokens >= amt && !all) 
	    			{
	    				newSummonList.add(s.originalName);
		    			aNewSummonList.add(s);
	    			}
	    			else 
	    			{
	    				tokens++;
		    			cardsToTribute.add(s);
		    			if (!(Util.getChallengeLevel() > 3 && Util.deckIs("Machine Deck")))
		    			{
			    			DuelistCard casing = new BombCasing();
			    			newSummonList.add(casing.originalName);
			    			aNewSummonList.add(casing);
		    			}
	    			}
	    		}
	    		else
	    		{
	    			newSummonList.add(s.originalName);
	    			aNewSummonList.add(s);
	    		}
	    	}
	    	
	    	tributeSpecificCards(cardsToTribute, this, true);
	    	summonsInstance.summonList = newSummonList;
	    	summonsInstance.actualCardSummonList = aNewSummonList;
	    	summonsInstance.amount -= tokens;
	    	summonsInstance.updateStringColors();
	    	summonsInstance.updateDescription();
	    	if (AbstractDungeon.player.hasRelic(ChemicalX.ID) && (this.hasTag(Tags.X_COST) || this.cost == -1)) { return cardsToTribute.size() * 2; }
	    	return cardsToTribute.size();
    	}
		else { return 0; }
	}
	
	public static int detonationTributeStatic(int amt, boolean all, boolean selfDmg, int detonations, boolean xCost)
	{
		if (player().hasPower(SummonPower.POWER_ID))
    	{
			int tokens = 0;
	    	SummonPower summonsInstance = (SummonPower) player().getPower(SummonPower.POWER_ID);
	    	ArrayList<DuelistCard> aSummonsList = summonsInstance.actualCardSummonList;
	    	ArrayList<String> newSummonList = new ArrayList<String>();
	    	ArrayList<DuelistCard> aNewSummonList = new ArrayList<DuelistCard>();
	    	ArrayList<DuelistCard> cardsToTribute = new ArrayList<DuelistCard>();
	    	for (DuelistCard s : aSummonsList)
	    	{
	    		if (s instanceof ExplosiveToken || s instanceof SuperExplodingToken)
	    		{
	    			if (tokens >= amt && !all) 
	    			{
	    				newSummonList.add(s.originalName);
		    			aNewSummonList.add(s);
	    			}
	    			else 
	    			{
	    				tokens++;
		    			cardsToTribute.add(s);
		    			if (!(Util.getChallengeLevel() > 3 && Util.deckIs("Machine Deck")))
		    			{
			    			DuelistCard casing = new BombCasing();
			    			newSummonList.add(casing.originalName);
			    			aNewSummonList.add(casing);
		    			}
	    			}
	    		}
	    		else
	    		{
	    			newSummonList.add(s.originalName);
	    			aNewSummonList.add(s);
	    		}
	    	}
	    	
	    	if (selfDmg) { tributeSpecificCards(cardsToTribute, new Token(), true); }
	    	else { DuelistCard bl = new BlastToken(); bl.detonations = detonations; tributeSpecificCards(cardsToTribute, bl, true); }
	    	summonsInstance.summonList = newSummonList;
	    	summonsInstance.actualCardSummonList = aNewSummonList;
	    	summonsInstance.amount -= tokens;
	    	summonsInstance.updateStringColors();
	    	summonsInstance.updateDescription();
	    	if (AbstractDungeon.player.hasRelic(ChemicalX.ID) && xCost) { return cardsToTribute.size() * 2; }
	    	return cardsToTribute.size();
    	}
		else { return 0; }
	}
	
	public static ArrayList<DuelistCard> tribute(AbstractPlayer p, int tributes, boolean tributeAll, DuelistCard card)
	{		
		ArrayList<DuelistCard> tributeList = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> cardTribList = new ArrayList<DuelistCard>();
		boolean challengeFailure = (Util.isCustomModActive("theDuelist:TributeRandomizer"));
		if (challengeFailure)
		{
			if (Util.isCustomModActive("challengethespire:Bronze Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 3) == 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedTribActionText, 1.0F, 2.0F));
					return tributeList;
				}
			}
			else if (Util.isCustomModActive("challengethespire:Silver Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 2) == 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedTribActionText, 1.0F, 2.0F));
					return tributeList;
				}
			}
			else if (Util.isCustomModActive("challengethespire:Gold Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 3) != 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedTribActionText, 1.0F, 2.0F));
					return tributeList;
				}
			}
			else if (Util.isCustomModActive("challengethespire:Platinum Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 4) != 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedTribActionText, 1.0F, 2.0F));
					return tributeList;
				}
			}
		}
		
		if (card.misc != 52)
		{
			// If no summons, just skip this so we don't crash
			// This should never be called without summons due to canUse() checking for tributes before use() can be run
			if (!p.hasPower(SummonPower.POWER_ID)) { return tributeList; }
			else
			{
				//	Check for Mausoleum of the Emperor
				if (p.hasPower(EmperorPower.POWER_ID))
				{
					EmperorPower empInstance = (EmperorPower)p.getPower(EmperorPower.POWER_ID);
					if (empInstance.flag)
					{
						SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);

						// Check for Tomb Looter
						// Checking here because Tomb Looter should proc even when you attack with a tribute card that reduces your summons after attacking
						if (p.hasPower(TombLooterPower.POWER_ID) && card.type.equals(CardType.ATTACK))
						{
							if (getSummons(p) == getMaxSummons(p))
							{
								gainGold(p.getPower(TombLooterPower.POWER_ID).amount, p, true);
							}
						}

						if (tributeAll) { tributes = summonsInstance.amount; }
						if (summonsInstance.amount - tributes < 0) { tributes = summonsInstance.amount; summonsInstance.amount = 0; }
						else { summonsInstance.amount -= tributes; }

						// Check for Obelisk after tributing
						if (p.hasPower(ObeliskPower.POWER_ID) && tributes > 0)
						{
							ObeliskPower instance = (ObeliskPower) p.getPower(ObeliskPower.POWER_ID);
							DuelistCard.damageAllEnemiesThornsNormal(instance.DAMAGE * tributes);
						}
						
						// Check for Blaze orbs
						if (p.hasOrb() && tributes > 0)
						{
							for (AbstractOrb o : p.orbs)
							{
								if (o instanceof Blaze)
								{
									Blaze b = (Blaze)o;
									for (int i = 0; i < tributes; i++) { b.triggerPassiveEffect(); }
								}
							}
						}
						
						// Check for Pharaoh's Curse
						if (p.hasPower(TributeSicknessPower.POWER_ID)) { damageSelfNotHP(tributes * p.getPower(TributeSicknessPower.POWER_ID).amount); }

						// Check for Toon Tribute power
						if (p.hasPower(TributeToonPower.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSets(Tags.MONSTER, Tags.TOON_POOL), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPower.POWER_ID), p, 1); }
						if (p.hasPower(TributeToonPowerB.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSet(Tags.TOON_POOL), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPowerB.POWER_ID), p, 1); }

						// Check for Ironhammer Giants in hand/discard/draw
						if (tributes > 0)
						{
							for (AbstractCard c : player().hand.group)
							{
								if (c instanceof IronhammerGiant)
								{
									IronhammerGiant giant = (IronhammerGiant)c;
									giant.costReduce();
								}
							}
							
							for (AbstractCard c : AbstractDungeon.player.discardPile.group)
							{
								if (c instanceof IronhammerGiant)
								{
									IronhammerGiant giant = (IronhammerGiant)c;
									giant.costReduce();
								}
							}
							
							for (AbstractCard c : AbstractDungeon.player.drawPile.group)
							{
								if (c instanceof IronhammerGiant)
								{
									IronhammerGiant giant = (IronhammerGiant)c;
									giant.costReduce();
								}
							}
						
							// Look through summonsList and remove # tributes strings
							for (int i = 0; i < tributes; i++)
							{
								if (summonsInstance.summonList.size() > 0)
								{
									int endIndex = summonsInstance.summonList.size() - 1;
									DuelistCard temp = DuelistMod.summonMap.get(summonsInstance.summonList.get(endIndex));
									handleOnTributeForAllAbstracts(temp, card);								
									if (temp != null) { tributeList.add(temp); }
									//summonsInstance.summonMap.remove(summonsInstance.summonList.get(endIndex));
									summonsInstance.summonList.remove(endIndex);
								}
								
								if (summonsInstance.actualCardSummonList.size() > 0)
								{
									int endIndex = summonsInstance.actualCardSummonList.size() - 1;
									DuelistCard temp = summonsInstance.actualCardSummonList.get(endIndex);
									if (temp != null) { cardTribList.add(temp); }
									//summonsInstance.summonMap.remove(summonsInstance.summonList.get(endIndex));
									summonsInstance.actualCardSummonList.remove(endIndex);
								}
							}							
						}

						summonsInstance.updateCount(summonsInstance.amount);
						summonsInstance.updateStringColors();
						summonsInstance.updateDescription();
						for (DuelistCard c : cardTribList) 
						{
							c.customOnTribute(card);
							c.runTributeSynergyFunctions(card);
							if (c.hasTag(Tags.AQUA))
							{
								// Check for Levia Dragon
								if (p.hasPower(LeviaDragonPower.POWER_ID))
								{
									LeviaDragonPower instance = (LeviaDragonPower) p.getPower(LeviaDragonPower.POWER_ID);
									int damageObelisk = instance.amount;
									int[] temp = new int[] {damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk};
									for (int i : temp) { i = i * tributes; }
									AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(p, temp, DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
								}
							}
							if (!c.hasTag(Tags.TOKEN) && c.hasTag(Tags.MONSTER))
							{
								DuelistMod.tribCombatCount++;
								DuelistMod.tribRunCount++;
								DuelistMod.tribTurnCount++;
							}
							if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:tribute():1 ---> Called " + c.originalName + "'s customOnTribute()"); }
						}
						if (AbstractDungeon.player.hasPower(ReinforcementsPower.POWER_ID)) { DuelistCard.summon(AbstractDungeon.player, 1, card); }
						return cardTribList;
					}
					else
					{
						empInstance.flag = true;
						if (AbstractDungeon.player.hasPower(ReinforcementsPower.POWER_ID)) { DuelistCard.summon(AbstractDungeon.player, 1, card); }
						player().hand.glowCheck();
						AbstractDungeon.actionManager.addToBottom(new RefreshHandGlowAction(player()));
						return tributeList;
					}
				}
				else
				{

					SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);

					// Check for Tomb Looter
					// Checking here because Tomb Looter should proc even when you attack with a tribute card that reduces your summons after attacking
					if (p.hasPower(TombLooterPower.POWER_ID) && card.type.equals(CardType.ATTACK))
					{
						if (getSummons(p) == getMaxSummons(p))
						{
							gainGold(p.getPower(TombLooterPower.POWER_ID).amount, p, true);
						}
					}

					if (tributeAll) { tributes = summonsInstance.amount; }
					if (summonsInstance.amount - tributes < 0) { tributes = summonsInstance.amount; summonsInstance.amount = 0; }
					else { summonsInstance.amount -= tributes; }

					// Check for Obelisk after tributing
					if (p.hasPower(ObeliskPower.POWER_ID) && tributes > 0)
					{
						ObeliskPower instance = (ObeliskPower) p.getPower(ObeliskPower.POWER_ID);
						DuelistCard.damageAllEnemiesThornsNormal(instance.DAMAGE * tributes);
					}
					
					// Check for Blaze orbs
					if (p.hasOrb() && tributes > 0)
					{
						for (AbstractOrb o : p.orbs)
						{
							if (o instanceof Blaze)
							{
								Blaze b = (Blaze)o;
								for (int i = 0; i < tributes; i++) { b.triggerPassiveEffect(); }
							}
						}
					}
					
					// Check for Pharaoh's Curse
					if (p.hasPower(TributeSicknessPower.POWER_ID)) { damageSelfNotHP(tributes * p.getPower(TributeSicknessPower.POWER_ID).amount); }

					// Check for Toon Tribute power
					if (p.hasPower(TributeToonPower.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSets(Tags.MONSTER, Tags.TOON_POOL), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPower.POWER_ID), p, 1); }
					if (p.hasPower(TributeToonPowerB.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSet(Tags.TOON_POOL), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPowerB.POWER_ID), p, 1); }

					// Check for Ironhammer Giants in hand/discard/draw
					if (tributes > 0)
					{
						for (AbstractCard c : player().hand.group)
						{
							if (c instanceof IronhammerGiant)
							{
								IronhammerGiant giant = (IronhammerGiant)c;
								giant.costReduce();
							}
						}
						
						for (AbstractCard c : AbstractDungeon.player.discardPile.group)
						{
							if (c instanceof IronhammerGiant)
							{
								IronhammerGiant giant = (IronhammerGiant)c;
								giant.costReduce();
							}
						}
						
						for (AbstractCard c : AbstractDungeon.player.drawPile.group)
						{
							if (c instanceof IronhammerGiant)
							{
								IronhammerGiant giant = (IronhammerGiant)c;
								giant.costReduce();
							}
						}
					
						// Look through summonsList and remove #tributes strings
						for (int i = 0; i < tributes; i++)
						{
							if (summonsInstance.summonList.size() > 0)
							{								
								int endIndex = summonsInstance.summonList.size() - 1;
								DuelistCard temp = DuelistMod.summonMap.get(summonsInstance.summonList.get(endIndex));
								handleOnTributeForAllAbstracts(temp, card);	
								if (temp != null) { tributeList.add(temp); }
								//summonsInstance.summonMap.remove(summonsInstance.summonList.get(endIndex));
								summonsInstance.summonList.remove(endIndex);								
							}
							
							if (summonsInstance.actualCardSummonList.size() > 0)
							{
								int endIndex = summonsInstance.actualCardSummonList.size() - 1;
								DuelistCard temp = summonsInstance.actualCardSummonList.get(endIndex);
								if (temp != null) { cardTribList.add(temp); }
								//summonsInstance.summonMap.remove(summonsInstance.summonList.get(endIndex));
								summonsInstance.actualCardSummonList.remove(endIndex);
							}
						}
					}


					summonsInstance.updateCount(summonsInstance.amount);
					summonsInstance.updateStringColors();
					summonsInstance.updateDescription();
					for (DuelistCard c : cardTribList) 
					{
						//c.onTribute(card); 
						c.customOnTribute(card);
						c.runTributeSynergyFunctions(card);
						if (c.hasTag(Tags.AQUA))
						{
							// Check for Levia Dragon
							if (p.hasPower(LeviaDragonPower.POWER_ID))
							{
								LeviaDragonPower instance = (LeviaDragonPower) p.getPower(LeviaDragonPower.POWER_ID);
								int damageObelisk = instance.amount;
								int[] temp = new int[] {damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk};
								for (int i : temp) { i = i * tributes; }
								AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(p, temp, DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
							}
						}
						if (!c.hasTag(Tags.TOKEN) && c.hasTag(Tags.MONSTER))
						{
							DuelistMod.tribCombatCount++;
							DuelistMod.tribRunCount++;
							DuelistMod.tribTurnCount++;
						}
						if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:tribute():2 ---> Called " + c.originalName + "'s customOnTribute()"); }
					}
					if (AbstractDungeon.player.hasPower(ReinforcementsPower.POWER_ID)) { DuelistCard.summon(AbstractDungeon.player, 1, card); }
					return cardTribList;
				}
			}
		}
		else
		{
			//card.misc = 0;
			if (AbstractDungeon.player.hasPower(ReinforcementsPower.POWER_ID)) { DuelistCard.summon(AbstractDungeon.player, 1, card); }
			player().hand.glowCheck();
			AbstractDungeon.actionManager.addToBottom(new RefreshHandGlowAction(player()));
			return tributeList;
		}

	}

	public static int powerTribute(AbstractPlayer p, int tributes, boolean tributeAll)
	{
		ArrayList<DuelistCard> cardTribList = new ArrayList<DuelistCard>();
		boolean challengeFailure = (Util.isCustomModActive("theDuelist:TributeRandomizer"));
		if (challengeFailure)
		{
			if (Util.isCustomModActive("challengethespire:Bronze Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 3) == 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedTribActionText, 1.0F, 2.0F));
					return 0;
				}
			}
			else if (Util.isCustomModActive("challengethespire:Silver Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 2) == 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedTribActionText, 1.0F, 2.0F));
					return 0;
				}
			}
			else if (Util.isCustomModActive("challengethespire:Gold Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 3) != 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedTribActionText, 1.0F, 2.0F));
					return 0;
				}
			}
			else if (Util.isCustomModActive("challengethespire:Platinum Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 4) != 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedTribActionText, 1.0F, 2.0F));
					return 0;
				}
			}
		}
		
		ArrayList<DuelistCard> tributeList = new ArrayList<DuelistCard>();
		// If no summons, just skip this so we don't crash
		// This should never be called without summons due to canUse() checking for tributes before use() can be run
		if (!p.hasPower(SummonPower.POWER_ID)) { return 0; }
		else
		{
			//	Check for Mausoleum of the Emperor
			if (p.hasPower(EmperorPower.POWER_ID))
			{
				EmperorPower empInstance = (EmperorPower)p.getPower(EmperorPower.POWER_ID);
				if (empInstance.flag)
				{
					SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
					if (tributeAll) { tributes = summonsInstance.amount; }
					if (summonsInstance.amount - tributes < 0) { tributes = summonsInstance.amount; summonsInstance.amount = 0; }
					else { summonsInstance.amount -= tributes; }

					// Check for Obelisk after tributing
					if (p.hasPower(ObeliskPower.POWER_ID) && tributes > 0)
					{
						ObeliskPower instance = (ObeliskPower) p.getPower(ObeliskPower.POWER_ID);
						DuelistCard.damageAllEnemiesThornsNormal(instance.DAMAGE * tributes);
					}		
					
					// Check for Blaze orbs
					if (p.hasOrb() && tributes > 0)
					{
						for (AbstractOrb o : p.orbs)
						{
							if (o instanceof Blaze)
							{
								Blaze b = (Blaze)o;
								for (int i = 0; i < tributes; i++) { b.triggerPassiveEffect(); }
							}
						}
					}

					// Check for Pharaoh's Curse
					if (p.hasPower(TributeSicknessPower.POWER_ID)) { damageSelfNotHP(tributes * p.getPower(TributeSicknessPower.POWER_ID).amount); }

					// Check for Toon Tribute power
					if (p.hasPower(TributeToonPower.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSets(Tags.MONSTER, Tags.TOON_POOL), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPower.POWER_ID), p, 1); }
					if (p.hasPower(TributeToonPowerB.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSet(Tags.TOON_POOL), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPowerB.POWER_ID), p, 1); }

					// Check for Ironhammer Giants in hand/discard/draw
					if (tributes > 0)
					{
						for (AbstractCard c : player().hand.group)
						{
							if (c instanceof IronhammerGiant)
							{
								IronhammerGiant giant = (IronhammerGiant)c;
								giant.costReduce();
							}
						}
						
						for (AbstractCard c : AbstractDungeon.player.discardPile.group)
						{
							if (c instanceof IronhammerGiant)
							{
								IronhammerGiant giant = (IronhammerGiant)c;
								giant.costReduce();
							}
						}
						
						for (AbstractCard c : AbstractDungeon.player.drawPile.group)
						{
							if (c instanceof IronhammerGiant)
							{
								IronhammerGiant giant = (IronhammerGiant)c;
								giant.costReduce();
							}
						}
					}
					
					// Look through summonsList and remove #tributes strings					
					if (tributes > 0) 
					{
						for (int i = 0; i < tributes; i++)
						{
							if (summonsInstance.summonList.size() > 0)
							{
								int endIndex = summonsInstance.summonList.size() - 1;
								DuelistCard temp = DuelistMod.summonMap.get(summonsInstance.summonList.get(endIndex));
								handleOnTributeForAllAbstracts(temp, new Token());	
								if (temp != null) { tributeList.add(temp); }
								//summonsInstance.summonMap.remove(summonsInstance.summonList.get(endIndex));
								summonsInstance.summonList.remove(endIndex);
							}
							
							if (summonsInstance.actualCardSummonList.size() > 0)
							{
								int endIndex = summonsInstance.actualCardSummonList.size() - 1;
								DuelistCard temp = summonsInstance.actualCardSummonList.get(endIndex);
								if (temp != null) { cardTribList.add(temp); }
								//summonsInstance.summonMap.remove(summonsInstance.summonList.get(endIndex));
								summonsInstance.actualCardSummonList.remove(endIndex);
							}
						}
					}


					summonsInstance.updateCount(summonsInstance.amount);
					summonsInstance.updateStringColors();
					summonsInstance.updateDescription();
					for (DuelistCard c : cardTribList)
					{ 
						//c.onTribute(new Token());
						c.customOnTribute(new Token());
						c.runTributeSynergyFunctions(new Token());
						if (c.hasTag(Tags.AQUA))
						{
							// Check for Levia Dragon
							if (p.hasPower(LeviaDragonPower.POWER_ID))
							{
								LeviaDragonPower instance = (LeviaDragonPower) p.getPower(LeviaDragonPower.POWER_ID);
								int damageObelisk = instance.amount;
								int[] temp = new int[] {damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk};
								for (int i : temp) { i = i * tributes; }
								AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(p, temp, DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
							}
						}
						if (!c.hasTag(Tags.TOKEN) && c.hasTag(Tags.MONSTER))
						{
							DuelistMod.tribCombatCount++;
							DuelistMod.tribRunCount++;
							DuelistMod.tribTurnCount++;
						}
						if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:powerTribute():1 ---> Called " + c.originalName + "'s customOnTribute()"); }
					}
					player().hand.glowCheck();
					AbstractDungeon.actionManager.addToBottom(new RefreshHandGlowAction(player()));
					return tributes;
				}
				else
				{
					empInstance.flag = true;
					player().hand.glowCheck();
					AbstractDungeon.actionManager.addToBottom(new RefreshHandGlowAction(player()));
					return 0;
				}
			}
			else
			{

				SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
				if (tributeAll) { tributes = summonsInstance.amount; }
				if (summonsInstance.amount - tributes < 0) { tributes = summonsInstance.amount; summonsInstance.amount = 0; }
				else { summonsInstance.amount -= tributes; }

				// Check for Obelisk after tributing
				if (p.hasPower(ObeliskPower.POWER_ID) && tributes > 0)
				{
					ObeliskPower instance = (ObeliskPower) p.getPower(ObeliskPower.POWER_ID);
					DuelistCard.damageAllEnemiesThornsNormal(instance.DAMAGE * tributes);
				}
				
				// Check for Blaze orbs
				if (p.hasOrb() && tributes > 0)
				{
					for (AbstractOrb o : p.orbs)
					{
						if (o instanceof Blaze)
						{
							Blaze b = (Blaze)o;
							for (int i = 0; i < tributes; i++) { b.triggerPassiveEffect(); }
						}
					}
				}

				// Check for Pharaoh's Curse
				if (p.hasPower(TributeSicknessPower.POWER_ID)) { damageSelfNotHP(tributes * p.getPower(TributeSicknessPower.POWER_ID).amount); }

				// Check for Toon Tribute power
				if (p.hasPower(TributeToonPower.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSets(Tags.MONSTER, Tags.TOON_POOL), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPower.POWER_ID), p, 1); }
				if (p.hasPower(TributeToonPowerB.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSet(Tags.TOON_POOL), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPowerB.POWER_ID), p, 1); }

				// Check for Ironhammer Giants in hand/discard/draw
				if (tributes > 0)
				{
					for (AbstractCard c : player().hand.group)
					{
						if (c instanceof IronhammerGiant)
						{
							IronhammerGiant giant = (IronhammerGiant)c;
							giant.costReduce();
						}
					}
					
					for (AbstractCard c : AbstractDungeon.player.discardPile.group)
					{
						if (c instanceof IronhammerGiant)
						{
							IronhammerGiant giant = (IronhammerGiant)c;
							giant.costReduce();
						}
					}
					
					for (AbstractCard c : AbstractDungeon.player.drawPile.group)
					{
						if (c instanceof IronhammerGiant)
						{
							IronhammerGiant giant = (IronhammerGiant)c;
							giant.costReduce();
						}
					}
				}
				
				// Look through summonsList and remove #tributes strings
				if (tributes > 0) 
				{
					for (int i = 0; i < tributes; i++)
					{
						if (summonsInstance.summonList.size() > 0)
						{
							int endIndex = summonsInstance.summonList.size() - 1;
							DuelistCard temp = DuelistMod.summonMap.get(summonsInstance.summonList.get(endIndex));
							handleOnTributeForAllAbstracts(temp, new Token());	
							if (temp != null) { tributeList.add(temp); }
							//summonsInstance.summonMap.remove(summonsInstance.summonList.get(endIndex));
							summonsInstance.summonList.remove(endIndex);
						}
						
						if (summonsInstance.actualCardSummonList.size() > 0)
						{
							int endIndex = summonsInstance.actualCardSummonList.size() - 1;
							DuelistCard temp = summonsInstance.actualCardSummonList.get(endIndex);
							if (temp != null) { cardTribList.add(temp); }
							//summonsInstance.summonMap.remove(summonsInstance.summonList.get(endIndex));
							summonsInstance.actualCardSummonList.remove(endIndex);
						}
					}
				}


				summonsInstance.updateCount(summonsInstance.amount);
				summonsInstance.updateStringColors();
				summonsInstance.updateDescription();
				for (DuelistCard c : cardTribList) 
				{
					//c.onTribute(new Token()); 	
					c.customOnTribute(new Token());
					c.runTributeSynergyFunctions(new Token());
					if (c.hasTag(Tags.AQUA))
					{
						// Check for Levia Dragon
						if (p.hasPower(LeviaDragonPower.POWER_ID))
						{
							LeviaDragonPower instance = (LeviaDragonPower) p.getPower(LeviaDragonPower.POWER_ID);
							int damageObelisk = instance.amount;
							int[] temp = new int[] {damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk};
							for (int i : temp) { i = i * tributes; }
							AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(p, temp, DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
						}
					}
					if (!c.hasTag(Tags.TOKEN) && c.hasTag(Tags.MONSTER))
					{
						DuelistMod.tribCombatCount++;
						DuelistMod.tribRunCount++;
						DuelistMod.tribTurnCount++;
					}
					if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:powerTribute():2 ---> Called " + c.originalName + "'s customOnTribute()"); }
				}
				player().hand.glowCheck();
				AbstractDungeon.actionManager.addToBottom(new RefreshHandGlowAction(player()));
				return tributes;
			}
		}
	}
	
	public static ArrayList<DuelistCard> listReturnPowerTribute(AbstractPlayer p, int tributes, boolean tributeAll)
	{
		ArrayList<DuelistCard> cardTribList = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> tributeList = new ArrayList<DuelistCard>();
		boolean challengeFailure = (Util.isCustomModActive("theDuelist:TributeRandomizer"));
		if (challengeFailure)
		{
			if (Util.isCustomModActive("challengethespire:Bronze Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 3) == 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedTribActionText, 1.0F, 2.0F));
					return tributeList;
				}
			}
			else if (Util.isCustomModActive("challengethespire:Silver Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 2) == 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedTribActionText, 1.0F, 2.0F));
					return tributeList;
				}
			}
			else if (Util.isCustomModActive("challengethespire:Gold Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 3) != 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedTribActionText, 1.0F, 2.0F));
					return tributeList;
				}
			}
			else if (Util.isCustomModActive("challengethespire:Platinum Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 4) != 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedTribActionText, 1.0F, 2.0F));
					return tributeList;
				}
			}
		}
		
		
		// If no summons, just skip this so we don't crash
		// This should never be called without summons due to canUse() checking for tributes before use() can be run
		if (!p.hasPower(SummonPower.POWER_ID)) { return tributeList; }
		else
		{
			//	Check for Mausoleum of the Emperor
			if (p.hasPower(EmperorPower.POWER_ID))
			{
				EmperorPower empInstance = (EmperorPower)p.getPower(EmperorPower.POWER_ID);
				if (empInstance.flag)
				{
					SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
					if (tributeAll) { tributes = summonsInstance.amount; }
					if (summonsInstance.amount - tributes < 0) { tributes = summonsInstance.amount; summonsInstance.amount = 0; }
					else { summonsInstance.amount -= tributes; }

					// Check for Obelisk after tributing
					if (p.hasPower(ObeliskPower.POWER_ID) && tributes > 0)
					{
						ObeliskPower instance = (ObeliskPower) p.getPower(ObeliskPower.POWER_ID);
						DuelistCard.damageAllEnemiesThornsNormal(instance.DAMAGE * tributes);
					}
					
					// Check for Blaze orbs
					if (p.hasOrb() && tributes > 0)
					{
						for (AbstractOrb o : p.orbs)
						{
							if (o instanceof Blaze)
							{
								Blaze b = (Blaze)o;
								for (int i = 0; i < tributes; i++) { b.triggerPassiveEffect(); }
							}
						}
					}

					// Check for Pharaoh's Curse
					if (p.hasPower(TributeSicknessPower.POWER_ID)) { damageSelfNotHP(tributes * p.getPower(TributeSicknessPower.POWER_ID).amount); }

					// Check for Toon Tribute power
					if (p.hasPower(TributeToonPower.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSets(Tags.MONSTER, Tags.TOON_POOL), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPower.POWER_ID), p, 1); }
					if (p.hasPower(TributeToonPowerB.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSet(Tags.TOON_POOL), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPowerB.POWER_ID), p, 1); }

					// Check for Ironhammer Giants in hand/discard/draw
					if (tributes > 0)
					{
						for (AbstractCard c : player().hand.group)
						{
							if (c instanceof IronhammerGiant)
							{
								IronhammerGiant giant = (IronhammerGiant)c;
								giant.costReduce();
							}
						}
						
						for (AbstractCard c : AbstractDungeon.player.discardPile.group)
						{
							if (c instanceof IronhammerGiant)
							{
								IronhammerGiant giant = (IronhammerGiant)c;
								giant.costReduce();
							}
						}
						
						for (AbstractCard c : AbstractDungeon.player.drawPile.group)
						{
							if (c instanceof IronhammerGiant)
							{
								IronhammerGiant giant = (IronhammerGiant)c;
								giant.costReduce();
							}
						}
					}
					
					// Look through summonsList and remove #tributes strings					
					if (tributes > 0) 
					{
						for (int i = 0; i < tributes; i++)
						{
							if (summonsInstance.summonList.size() > 0)
							{
								int endIndex = summonsInstance.summonList.size() - 1;
								DuelistCard temp = DuelistMod.summonMap.get(summonsInstance.summonList.get(endIndex));
								handleOnTributeForAllAbstracts(temp, new Token());	
								if (temp != null) { tributeList.add(temp); }
								//summonsInstance.summonMap.remove(summonsInstance.summonList.get(endIndex));
								summonsInstance.summonList.remove(endIndex);
							}
							
							if (summonsInstance.actualCardSummonList.size() > 0)
							{
								int endIndex = summonsInstance.actualCardSummonList.size() - 1;
								DuelistCard temp = summonsInstance.actualCardSummonList.get(endIndex);
								if (temp != null) { cardTribList.add(temp); }
								//summonsInstance.summonMap.remove(summonsInstance.summonList.get(endIndex));
								summonsInstance.actualCardSummonList.remove(endIndex);
							}
						}
					}


					summonsInstance.updateCount(summonsInstance.amount);
					summonsInstance.updateStringColors();
					summonsInstance.updateDescription();
					for (DuelistCard c : cardTribList)
					{ 
						//c.onTribute(new Token());
						c.customOnTribute(new Token());
						c.runTributeSynergyFunctions(new Token());
						if (c.hasTag(Tags.AQUA))
						{
							// Check for Levia Dragon
							if (p.hasPower(LeviaDragonPower.POWER_ID))
							{
								LeviaDragonPower instance = (LeviaDragonPower) p.getPower(LeviaDragonPower.POWER_ID);
								int damageObelisk = instance.amount;
								int[] temp = new int[] {damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk};
								for (int i : temp) { i = i * tributes; }
								AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(p, temp, DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
							}
						}
						
						if (!c.hasTag(Tags.TOKEN) && c.hasTag(Tags.MONSTER))
						{
							DuelistMod.tribCombatCount++;
							DuelistMod.tribRunCount++;
							DuelistMod.tribTurnCount++;
						}
						if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:powerTribute():1 ---> Called " + c.originalName + "'s customOnTribute()"); }
					}
					player().hand.glowCheck();
					AbstractDungeon.actionManager.addToBottom(new RefreshHandGlowAction(player()));
					return cardTribList;
				}
				else
				{
					empInstance.flag = true;
					player().hand.glowCheck();
					AbstractDungeon.actionManager.addToBottom(new RefreshHandGlowAction(player()));
					return tributeList;
				}
			}
			else
			{

				SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
				if (tributeAll) { tributes = summonsInstance.amount; }
				if (summonsInstance.amount - tributes < 0) { tributes = summonsInstance.amount; summonsInstance.amount = 0; }
				else { summonsInstance.amount -= tributes; }

				// Check for Obelisk after tributing
				if (p.hasPower(ObeliskPower.POWER_ID) && tributes > 0)
				{
					ObeliskPower instance = (ObeliskPower) p.getPower(ObeliskPower.POWER_ID);
					DuelistCard.damageAllEnemiesThornsNormal(instance.DAMAGE * tributes);
				}
				
				// Check for Blaze orbs
				if (p.hasOrb() && tributes > 0)
				{
					for (AbstractOrb o : p.orbs)
					{
						if (o instanceof Blaze)
						{
							Blaze b = (Blaze)o;
							for (int i = 0; i < tributes; i++) { b.triggerPassiveEffect(); }
						}
					}
				}

				// Check for Pharaoh's Curse
				if (p.hasPower(TributeSicknessPower.POWER_ID)) { damageSelfNotHP(tributes * p.getPower(TributeSicknessPower.POWER_ID).amount); }

				// Check for Toon Tribute power
				if (p.hasPower(TributeToonPower.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSets(Tags.MONSTER, Tags.TOON_POOL), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPower.POWER_ID), p, 1); }
				if (p.hasPower(TributeToonPowerB.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSet(Tags.TOON_POOL), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPowerB.POWER_ID), p, 1); }

				// Check for Ironhammer Giants in hand/discard/draw
				if (tributes > 0)
				{
					for (AbstractCard c : player().hand.group)
					{
						if (c instanceof IronhammerGiant)
						{
							IronhammerGiant giant = (IronhammerGiant)c;
							giant.costReduce();
						}
					}
					
					for (AbstractCard c : AbstractDungeon.player.discardPile.group)
					{
						if (c instanceof IronhammerGiant)
						{
							IronhammerGiant giant = (IronhammerGiant)c;
							giant.costReduce();
						}
					}
					
					for (AbstractCard c : AbstractDungeon.player.drawPile.group)
					{
						if (c instanceof IronhammerGiant)
						{
							IronhammerGiant giant = (IronhammerGiant)c;
							giant.costReduce();
						}
					}
				}
				
				// Look through summonsList and remove #tributes strings
				if (tributes > 0) 
				{
					for (int i = 0; i < tributes; i++)
					{
						if (summonsInstance.summonList.size() > 0)
						{
							int endIndex = summonsInstance.summonList.size() - 1;
							DuelistCard temp = DuelistMod.summonMap.get(summonsInstance.summonList.get(endIndex));
							handleOnTributeForAllAbstracts(temp, new Token());	
							if (temp != null) { tributeList.add(temp); }
							//summonsInstance.summonMap.remove(summonsInstance.summonList.get(endIndex));
							summonsInstance.summonList.remove(endIndex);
						}
						
						if (summonsInstance.actualCardSummonList.size() > 0)
						{
							int endIndex = summonsInstance.actualCardSummonList.size() - 1;
							DuelistCard temp = summonsInstance.actualCardSummonList.get(endIndex);
							if (temp != null) { cardTribList.add(temp); }
							//summonsInstance.summonMap.remove(summonsInstance.summonList.get(endIndex));
							summonsInstance.actualCardSummonList.remove(endIndex);
						}
					}
				}


				summonsInstance.updateCount(summonsInstance.amount);
				summonsInstance.updateStringColors();
				summonsInstance.updateDescription();
				for (DuelistCard c : cardTribList) 
				{
					//c.onTribute(new Token()); 
					c.customOnTribute(new Token());
					c.runTributeSynergyFunctions(new Token());
					if (c.hasTag(Tags.AQUA))
					{
						// Check for Levia Dragon
						if (p.hasPower(LeviaDragonPower.POWER_ID))
						{
							LeviaDragonPower instance = (LeviaDragonPower) p.getPower(LeviaDragonPower.POWER_ID);
							int damageObelisk = instance.amount;
							int[] temp = new int[] {damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk};
							for (int i : temp) { i = i * tributes; }
							AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(p, temp, DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
						}
					}
					if (!c.hasTag(Tags.TOKEN) && c.hasTag(Tags.MONSTER))
					{
						DuelistMod.tribCombatCount++;
						DuelistMod.tribRunCount++;
						DuelistMod.tribTurnCount++;
					}
					if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:powerTribute():2 ---> Called " + c.originalName + "'s customOnTribute()"); }
				}
				player().hand.glowCheck();
				AbstractDungeon.actionManager.addToBottom(new RefreshHandGlowAction(player()));
				return cardTribList;
			}
		}
	}

	public static void tributeSpecificCards(ArrayList<DuelistCard> cardsToTribute, DuelistCard tributingCard, boolean callOnTribute)
	{
		boolean challengeFailure = (Util.isCustomModActive("theDuelist:TributeRandomizer"));
		if (challengeFailure)
		{
			if (Util.isCustomModActive("challengethespire:Bronze Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 3) == 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedTribActionText, 1.0F, 2.0F));
					return;
				}
			}
			else if (Util.isCustomModActive("challengethespire:Silver Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 2) == 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedTribActionText, 1.0F, 2.0F));
					return;
				}
			}
			else if (Util.isCustomModActive("challengethespire:Gold Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 3) != 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedTribActionText, 1.0F, 2.0F));
					return;
				}
			}
			else if (Util.isCustomModActive("challengethespire:Platinum Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 4) != 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedTribActionText, 1.0F, 2.0F));
					return;
				}
			}
		}
		AbstractPlayer p = AbstractDungeon.player;
		int tributes = cardsToTribute.size();
		// Check for Obelisk after tributing
		if (p.hasPower(ObeliskPower.POWER_ID) && tributes > 0)
		{
			ObeliskPower instance = (ObeliskPower) p.getPower(ObeliskPower.POWER_ID);
			DuelistCard.damageAllEnemiesThornsNormal(instance.DAMAGE * tributes);
		}

		// Check for Blaze orbs
		if (p.hasOrb() && tributes > 0)
		{
			for (AbstractOrb o : p.orbs)
			{
				if (o instanceof Blaze)
				{
					Blaze b = (Blaze)o;
					for (int i = 0; i < tributes; i++) { b.triggerPassiveEffect(); }
				}
			}
		}

		// Check for Pharaoh's Curse
		if (p.hasPower(TributeSicknessPower.POWER_ID)) { damageSelfNotHP(tributes * p.getPower(TributeSicknessPower.POWER_ID).amount); }

		// Check for Toon Tribute power
		if (p.hasPower(TributeToonPower.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSets(Tags.MONSTER, Tags.TOON_POOL), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPower.POWER_ID), p, 1); }
		if (p.hasPower(TributeToonPowerB.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSet(Tags.TOON_POOL), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPowerB.POWER_ID), p, 1); }

		// Check for Ironhammer Giants in hand/discard/draw
		if (tributes > 0)
		{
			for (AbstractCard c : player().hand.group)
			{
				if (c instanceof IronhammerGiant)
				{
					IronhammerGiant giant = (IronhammerGiant)c;
					giant.costReduce();
				}
			}

			for (AbstractCard c : AbstractDungeon.player.discardPile.group)
			{
				if (c instanceof IronhammerGiant)
				{
					IronhammerGiant giant = (IronhammerGiant)c;
					giant.costReduce();
				}
			}

			for (AbstractCard c : AbstractDungeon.player.drawPile.group)
			{
				if (c instanceof IronhammerGiant)
				{
					IronhammerGiant giant = (IronhammerGiant)c;
					giant.costReduce();
				}
			}
		}
		for (DuelistCard temp : cardsToTribute)
		{
			handleOnTributeForAllAbstracts(temp, tributingCard);
			if (callOnTribute)
			{
				temp.customOnTribute(tributingCard);
				temp.runTributeSynergyFunctions(tributingCard);
				if (!temp.hasTag(Tags.TOKEN) && temp.hasTag(Tags.MONSTER))
				{
					DuelistMod.tribCombatCount++;
					DuelistMod.tribRunCount++;
					DuelistMod.tribTurnCount++;
				}
			}
		}
	}

	public static void tributeChecker(AbstractPlayer p, int tributes, DuelistCard tributingCard, boolean callOnTribute)
	{
		boolean challengeFailure = (Util.isCustomModActive("theDuelist:TributeRandomizer"));
		if (challengeFailure)
		{
			if (Util.isCustomModActive("challengethespire:Bronze Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 3) == 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedTribActionText, 1.0F, 2.0F));
					return;
				}
			}
			else if (Util.isCustomModActive("challengethespire:Silver Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 2) == 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedTribActionText, 1.0F, 2.0F));
					return;
				}
			}
			else if (Util.isCustomModActive("challengethespire:Gold Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 3) != 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedTribActionText, 1.0F, 2.0F));
					return;
				}
			}
			else if (Util.isCustomModActive("challengethespire:Platinum Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 4) != 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedTribActionText, 1.0F, 2.0F));
					return;
				}
			}
		}
		
		ArrayList<DuelistCard> tributeList = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> cardTribList = new ArrayList<DuelistCard>();
		
		// Check for Obelisk after tributing
		if (p.hasPower(ObeliskPower.POWER_ID) && tributes > 0)
		{
			ObeliskPower instance = (ObeliskPower) p.getPower(ObeliskPower.POWER_ID);
			DuelistCard.damageAllEnemiesThornsNormal(instance.DAMAGE * tributes);
		}
		
		// Check for Blaze orbs
		if (p.hasOrb() && tributes > 0)
		{
			for (AbstractOrb o : p.orbs)
			{
				if (o instanceof Blaze)
				{
					Blaze b = (Blaze)o;
					for (int i = 0; i < tributes; i++) { b.triggerPassiveEffect(); }
				}
			}
		}

		// Check for Pharaoh's Curse
		if (p.hasPower(TributeSicknessPower.POWER_ID)) { damageSelfNotHP(tributes * p.getPower(TributeSicknessPower.POWER_ID).amount); }

		// Check for Toon Tribute power
		if (p.hasPower(TributeToonPower.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSets(Tags.MONSTER, Tags.TOON_POOL), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPower.POWER_ID), p, 1); }
		if (p.hasPower(TributeToonPowerB.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSet(Tags.TOON_POOL), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPowerB.POWER_ID), p, 1); }

		// Check for Ironhammer Giants in hand/discard/draw
		if (tributes > 0)
		{
			for (AbstractCard c : player().hand.group)
			{
				if (c instanceof IronhammerGiant)
				{
					IronhammerGiant giant = (IronhammerGiant)c;
					giant.costReduce();
				}
			}
			
			for (AbstractCard c : AbstractDungeon.player.discardPile.group)
			{
				if (c instanceof IronhammerGiant)
				{
					IronhammerGiant giant = (IronhammerGiant)c;
					giant.costReduce();
				}
			}
			
			for (AbstractCard c : AbstractDungeon.player.drawPile.group)
			{
				if (c instanceof IronhammerGiant)
				{
					IronhammerGiant giant = (IronhammerGiant)c;
					giant.costReduce();
				}
			}
		}
		
		if (p.hasPower(SummonPower.POWER_ID))
		{
			SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
			if (tributes > 0) 
			{
				for (int i = 0; i < tributes; i++)
				{
					if (summonsInstance.summonList.size() > 0)
					{
						int endIndex = summonsInstance.summonList.size() - 1;
						DuelistCard temp = DuelistMod.summonMap.get(summonsInstance.summonList.get(endIndex));
						handleOnTributeForAllAbstracts(temp, new Token());	
						if (temp != null) { tributeList.add(temp); }
					}
					
					if (summonsInstance.actualCardSummonList.size() > 0)
					{
						int endIndex = summonsInstance.actualCardSummonList.size() - 1;
						DuelistCard temp = summonsInstance.actualCardSummonList.get(endIndex);
						if (temp != null) { cardTribList.add(temp); }
					}
				}
			}
			
			if (callOnTribute)
			{
				for (DuelistCard c : cardTribList) 
				{
					//c.onTribute(tributingCard);
					c.customOnTribute(tributingCard);
					c.runTributeSynergyFunctions(tributingCard);
					if (c.hasTag(Tags.AQUA))
					{
						// Check for Levia Dragon
						if (p.hasPower(LeviaDragonPower.POWER_ID))
						{
							LeviaDragonPower instance = (LeviaDragonPower) p.getPower(LeviaDragonPower.POWER_ID);
							int damageObelisk = instance.amount;
							int[] temp = new int[] {damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk, damageObelisk};
							for (int i : temp) { i = i * tributes; }
							AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(p, temp, DamageType.THORNS, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
						}
					}
					if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:tributeChecker() ---> Called " + c.originalName + "'s customOnTribute()"); }
				}
			}
			
			for (DuelistCard c : cardTribList)
			{
				if (!c.hasTag(Tags.TOKEN) && c.hasTag(Tags.MONSTER))
				{
					DuelistMod.tribCombatCount++;
					DuelistMod.tribRunCount++;
					DuelistMod.tribTurnCount++;
				}
			}
		}
		player().hand.glowCheck();
		AbstractDungeon.actionManager.addToBottom(new RefreshHandGlowAction(player()));
	}
	// =============== /TRIBUTE MONSTER FUNCTIONS/ =======================================================================================================================================================
	
	// =============== TRIBUTE SYNERGY FUNCTIONS =========================================================================================================================================================
	
	public static void runRandomTributeSynergy()
	{
		RandomSynergyInterface aqua = () -> { aquaSynTrib(new RainbowMagician()); AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Aqua synergy!", 1.0F, 2.0F)); };
		RandomSynergyInterface dragon = () -> { dragonSynTrib(new RainbowMagician()); AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Dragon synergy!", 1.0F, 2.0F)); };
		//RandomSynergyInterface fiend = () -> { fiendSynTrib(new RainbowMagician()); AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Fiend synergy!", 1.0F, 2.0F)); };
		RandomSynergyInterface machine = () -> { machineSynTrib(new RainbowMagician()); AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Machine synergy!", 1.0F, 2.0F)); };
		RandomSynergyInterface insect = () -> { insectSynTrib(new RainbowMagician()); AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Insect synergy!", 1.0F, 2.0F)); };
		RandomSynergyInterface plant = () -> { plantSynTrib(new RainbowMagician()); AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Plant synergy!", 1.0F, 2.0F)); };
		RandomSynergyInterface preda = () -> { predaplantSynTrib(new RainbowMagician()); AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Predaplant synergy!", 1.0F, 2.0F)); };
		//RandomSynergyInterface warrior = () -> { warriorSynTrib(new RainbowMagician()); AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Warrior synergy!", 1.0F, 2.0F)); };
		RandomSynergyInterface naturia = () -> { naturiaSynTrib(new RainbowMagician()); AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Naturia synergy!", 1.0F, 2.0F)); };
		RandomSynergyInterface toon = () -> { toonSynTrib(new RainbowMagician()); AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Toon synergy!", 1.0F, 2.0F)); };
		RandomSynergyInterface wyrm = () -> { wyrmSynTrib(new RainbowMagician()); AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Wyrm synergy!", 1.0F, 2.0F)); };
		ArrayList<RandomSynergyInterface> list = new ArrayList<>();
		list.add(aqua);
		list.add(dragon);
		//list.add(fiend);
		list.add(machine);
		list.add(insect);
		list.add(plant);
		list.add(preda);
		//list.add(warrior);
		list.add(naturia);
		list.add(toon);
		list.add(wyrm);
		list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1)).getSyn();
	}
	
	
	// This function is called anytime a tribute happens
	// It automatically determines if a tribute synergy effect needs to trigger, and then triggers the appropriate one(s)
	// Also it checks for global effects that trigger whenever ANY synergy tribute occurs
	public void runTributeSynergyFunctions(DuelistCard tc)
	{
		if (!AbstractDungeon.player.hasPower(DepoweredPower.POWER_ID))
		{
			// Special function to handle megatyped monsters, plus single check of global synergy effects
			if (this.hasTag(Tags.MEGATYPED))
			{
				megatypeTrib(tc);
				synergyTributeOneTimeChecks(tc, this);
			}
			
			// For any non-megatyped monster tributes, just loop through the monster types that the tributed card has to see if any match the tributing card
			else
			{
				ArrayList<CardTags> cardTypes = getAllMonsterTypes(this);				
				for (CardTags t : cardTypes)
				{
					// Map determines which function to run based on card tag currently iterating over
					// If tributed card has any given type from the map, we run the function that checks the tributing card for matching type
					// The functions that check matching type on the tributing cards also handle triggering the appropriate synergy effects
					switch (DuelistMod.monsterTypeTributeSynergyFunctionMap.get(t))
					{
						case 0: 
							aquaSynTrib(tc); 						
							if (DuelistMod.debug) { DuelistMod.logger.info("ran aqua syn trib automatically from tributing " + this.originalName + " for " + tc.originalName); }
							break;
						case 1: 
							dragonSynTrib(tc);						
							if (DuelistMod.debug) { DuelistMod.logger.info("ran dragon syn trib automatically from tributing " + this.originalName + " for " + tc.originalName); }
							break;
						case 2: 
							fiendSynTrib(tc); 
							if (DuelistMod.debug) { DuelistMod.logger.info("ran fiend syn trib automatically from tributing " + this.originalName + " for " + tc.originalName); }
							break;
						case 3: 
							insectSynTrib(tc); 
							if (DuelistMod.debug) { DuelistMod.logger.info("ran insect syn trib automatically from tributing " + this.originalName + " for " + tc.originalName); }
							break;
						case 4: 
							machineSynTrib(tc); 
							if (DuelistMod.debug) { DuelistMod.logger.info("ran machine syn trib automatically from tributing " + this.originalName + " for " + tc.originalName); }
							break;
						case 5: 
							naturiaSynTrib(tc);
							if (DuelistMod.debug) { DuelistMod.logger.info("ran naturia syn trib automatically from tributing " + this.originalName + " for " + tc.originalName); }
							break;
						case 6: 
							plantSynTrib(tc); 
							if (DuelistMod.debug) { DuelistMod.logger.info("ran plant syn trib automatically from tributing " + this.originalName + " for " + tc.originalName); }
							break;
						case 7: 
							predaplantSynTrib(tc); 
							if (DuelistMod.debug) { DuelistMod.logger.info("ran predaplant syn trib automatically from tributing " + this.originalName + " for " + tc.originalName); }
							break;
						case 8: 
							spellcasterSynTrib(tc); 
							if (DuelistMod.debug) { DuelistMod.logger.info("ran spellcaster syn trib automatically from tributing " + this.originalName + " for " + tc.originalName); }
							break;
						case 9: 
							superSynTrib(tc); 
							if (DuelistMod.debug) { DuelistMod.logger.info("ran superheavy syn trib automatically from tributing " + this.originalName + " for " + tc.originalName); }
							break;
						case 10: 
							toonSynTrib(tc);
							if (DuelistMod.debug) { DuelistMod.logger.info("ran toon syn trib automatically from tributing " + this.originalName + " for " + tc.originalName); }
							break;
						case 11: 
							zombieSynTrib(tc); 
							//lavaZombieEffectHandler();	// increase lava orbs evoke amounts by their passive amounts, this happens every time we tribute any zombie
							if (DuelistMod.debug) { DuelistMod.logger.info("ran zombie syn trib automatically from tributing " + this.originalName + " for " + tc.originalName); }
							break;
						case 12:
							warriorSynTrib(tc);
							Util.log("ran warrior syn trib automatically from tributing " + this.originalName + " for " + tc.originalName);
						case 13:
							rockSynTrib(tc);
							Util.log("ran rock syn trib automatically from tributing " + this.originalName + " for " + tc.originalName);
						case 14:
							wyrmSynTrib(tc);
							Util.log("ran wyrm syn trib automatically from tributing " + this.originalName + " for " + tc.originalName);
						case 15:
							dinoSynTrib(tc);
							Util.log("ran dinosaur syn trib automatically from tributing " + this.originalName + " for " + tc.originalName);
						default: break;
					}
				}
				
				// And finally for non-megatyped cards we still need to run one-time checks for global type-agnostic synergy effects
				synergyTributeOneTimeChecks(tc, this);
			}
		}		
	}
	
	// things to check for only one time when a synergy tribute happens
	// only runs once for megatyped situations and other weirdness that may occur with type modifications
	public static void synergyTributeOneTimeChecks(DuelistCard tributingCard, DuelistCard tributedCard)
	{
		ArrayList<CardTags> tributingCardMonsterTypes = getAllMonsterTypes(tributingCard);
		ArrayList<CardTags> tributedCardMonsterTypes = getAllMonsterTypes(tributedCard);
		boolean oneMatchingType = false;
		for (CardTags t : tributingCardMonsterTypes)
		{
			if (tributedCardMonsterTypes.contains(t))
			{
				oneMatchingType = true;
				break;
			}
		}

		// Successful synergy tribute for at least one type that matches between the two cards involved in this tribute
		// Call any effects that trigger on any given synergy tribute in this block
		if (oneMatchingType)
		{
			if (AbstractDungeon.player.hasRelic(MillenniumScale.ID)) { gainTempHP(3); }
			handleOnSynergyForAllAbstracts();
			DuelistMod.synergyTributesRan++;
		}
	}
	
	public void megatypeTrib(DuelistCard tc)
	{
		if (tc.hasTag(Tags.MEGATYPED) && DuelistMod.quicktimeEventsAllowed)
		{
			if(Settings.isDebug) {
				 UC.doMegatype();
	        } else {
	            //UC.atb(new BeginSpeedModeAction(new SpeedClickEnemyTime(3.0f, mon -> UC.doDmg(mon, damage, DamageInfo.DamageType.NORMAL, UC.getSpeedyAttackEffect(), true))));
	            Runnable myRunnable = () -> {
	                System.out.println("Ran");
	                UC.doVfx(new RainbowCardEffect());
	                UC.doMegatype();
	            };
	            UC.atb(new BeginSpeedModeAction(new SpeedClickButtonTime(5.0f, myRunnable, new BasicButtonGenerator(1f, true)), 1));
	            //UC.atb(new BeginSpeedModeAction(new SpeedHoverZoneTime(10.0f, myRunnable, true, 10)));
	        }
		}
		else
		{
			dragonSynTrib(tc);
			machineSynTrib(tc);
			toonSynTrib(tc);
			fiendSynTrib(tc);
			aquaSynTrib(tc);
			naturiaSynTrib(tc);
			megatypePlantHandler(tc);
			insectSynTrib(tc);
			superSynTrib(tc);
			spellcasterSynTrib(tc);
			zombieSynTrib(tc);
			warriorSynTrib(tc);
			rockSynTrib(tc);
			wyrmSynTrib(tc);
			if (tc.hasTag(Tags.MEGATYPED)) { AbstractDungeon.actionManager.addToTop(new VFXAction(new RainbowCardEffect())); }
		}
		Util.log("Ran megatype tribute function, tributing card: " + tc.originalName);
	}
	
	public static void rockSynTrib(DuelistCard tc)
	{
		if (tc.hasTag(Tags.ROCK))
		{
			
		}
	}
	
	public static void dinoSynTrib(DuelistCard tc)
	{
		if (tc.hasTag(Tags.DINOSAUR))
		{
			
		}
	}
	
	public static void wyrmSynTrib(DuelistCard tc)
	{
		if (tc.hasTag(Tags.WYRM))
		{
			if (!DuelistMod.wyrmTribThisCombat)
			{
				ArrayList<AbstractMonster> mons = new ArrayList<AbstractMonster>();
				for (AbstractMonster mon : AbstractDungeon.getCurrRoom().monsters.monsters)
				{
					if (!mon.isDead && !mon.isDying && !mon.isDeadOrEscaped() && !mon.halfDead && mon.hasPower(PoisonPower.POWER_ID))
					{
						mons.add(mon);
					}
				}
				
				if (mons.size() > 0)
				{
					AbstractMonster rand = mons.get(AbstractDungeon.cardRandomRng.random(mons.size() - 1));
					applyPower(new PoisonPower(rand, player(), rand.getPower(PoisonPower.POWER_ID).amount), rand);
				}
				DuelistMod.wyrmTribThisCombat = true;
			}
		}
	}
	
	public static void warriorSynTrib(DuelistCard tributingCard)
	{
		if (tributingCard.hasTag(Tags.WARRIOR))
		{			
			if (!DuelistMod.warriorTribThisCombat)
			{
				Util.log("Warrior for Warrior tribute code executing, choosing any stance (except Divinity)");
				DuelistMod.warriorTribEffectsTriggeredThisCombat++;
				if (DuelistMod.warriorTribEffectsTriggeredThisCombat >= DuelistMod.warriorTribEffectsPerCombat) { 
					DuelistMod.warriorTribThisCombat = true;
				}
				ArrayList<DuelistCard> stances = Util.getStanceChoices(true, false, true);
	        	tributingCard.addToBot(new WarriorTribAction(stances));
			}
			
			if (AbstractDungeon.player.hasPower(FightingSpiritPower.POWER_ID))
			{
				FightingSpiritPower pow = (FightingSpiritPower)AbstractDungeon.player.getPower(FightingSpiritPower.POWER_ID);
				pow.onTrib();
				pow.flash();
			}
		}
	}
	
	public static void dragonSynTrib(DuelistCard tributingCard)
	{
		if (tributingCard.hasTag(Tags.DRAGON))
		{
			boolean triggerAllowed = true;
			if (Util.getChallengeLevel() > 3 && Util.deckIs("Dragon Deck")) { if (AbstractDungeon.cardRandomRng.random(1, 2) == 2) { triggerAllowed = false; }}
			if (triggerAllowed)
			{
				if (AbstractDungeon.player.hasPower(MountainPower.POWER_ID)) 
				{
					TwoAmountPower pow = (TwoAmountPower)AbstractDungeon.player.getPower(MountainPower.POWER_ID);
					DuelistCard.applyPowerToSelf(new Dragonscales(DuelistMod.dragonStr + pow.amount2));
				}
				else { DuelistCard.applyPowerToSelf(new Dragonscales(DuelistMod.dragonStr)); }
			
				if (AbstractDungeon.player.hasRelic(DragonRelicB.ID))
				{
					if (DuelistMod.dragonRelicBFlipper) { drawRare(1, CardRarity.RARE); }
					DuelistMod.dragonRelicBFlipper = !DuelistMod.dragonRelicBFlipper;
				}
					
				if (player().hasRelic(DragonRelicC.ID))
				{
					AbstractRelic relic = player().getRelic(DragonRelicC.ID);
					int roll = AbstractDungeon.cardRandomRng.random(1, 5);
					if (roll == 1)
					{
						DuelistCard.gainEnergy(1);
						relic.flash();
					}
				}
			}
		}
	}
	
	public static void machineSynTrib(DuelistCard tributingCard)
	{
		if (tributingCard.hasTag(Tags.MACHINE))
		{
			if (!DuelistMod.machineArtifactFlipper) { applyPowerToSelf(new ArtifactPower(player(), DuelistMod.machineArt)); }
			DuelistMod.machineArtifactFlipper = !DuelistMod.machineArtifactFlipper;
		}
	}
	
	public static void toonSynTrib(DuelistCard tributingCard)
	{
		if (tributingCard.hasTag(Tags.TOON_POOL))
		{ 
			for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
			{
				if (!m.isDead && !m.isDying && !m.halfDead && !m.isDeadOrEscaped() && !m.isEscaping && m.currentHealth > 0)
				{
					applyPower(new VulnerablePower(m, DuelistMod.toonVuln, false), m);
				}
			}
		}
	}
	
	public static void fiendSynTrib(DuelistCard tributingCard)
	{
		if (tributingCard.hasTag(Tags.FIEND))
		{
			AbstractPlayer p = AbstractDungeon.player;
			if (p.hasPower(DoomdogPower.POWER_ID)) 
			{ 
				int dmgAmount = p.getPower(DoomdogPower.POWER_ID).amount; 
				damageAllEnemiesThornsNormal(dmgAmount); 
			}
			if (p.hasPower(RedMirrorPower.POWER_ID)) 
			{ 
				for (AbstractCard c : p.discardPile.group) 
				{ 
					if (c.cost > 0)	
					{
						c.setCostForTurn(-p.getPower(RedMirrorPower.POWER_ID).amount);	
						c.isCostModifiedForTurn = true;	
						AbstractDungeon.player.hand.glowCheck();
					}
				}
			}
			if (Util.getChallengeLevel() > 3 && Util.deckIs("Fiend Deck")) { for (AbstractCard c : p.discardPile.group) { if (c.costForTurn > -1) { c.setCostForTurn(c.costForTurn + 1); AbstractDungeon.player.hand.glowCheck();}}}
			AbstractDungeon.actionManager.addToBottom(new FiendFetchAction(p.discardPile, DuelistMod.fiendDraw)); 
		}
	}
	
	public static void aquaSynTrib(DuelistCard tributingCard)
	{
		if (tributingCard.hasTag(Tags.AQUA))
		{
			if (Util.getChallengeLevel() > 3 && Util.deckIs("Aqua Deck")) { if (AbstractDungeon.cardRandomRng.random(1, 2) == 1) { return; }}
			for (AbstractCard c : player().hand.group)
			{
				if (c instanceof DuelistCard && !c.uuid.equals(tributingCard.uuid))
				{
					DuelistCard dC = (DuelistCard)c;
					if (dC.isSummonCard())
					{
						dC.modifySummonsForTurn(DuelistMod.aquaInc);
					}
					
					if (player().hasRelic(AquaRelicB.ID) && dC.isTributeCard(true))
					{
						dC.modifyTributesForTurn(-DuelistMod.aquaInc);
					}
				}
			}
		}
	}
	
	public static void naturiaSynTrib(DuelistCard tributingCard)
	{
		if (tributingCard.hasTag(Tags.NATURIA))
		{
			DuelistCard.applyPowerToSelf(new LeavesPower(1));
		}
	}
	
	public static void megatypePlantHandler(DuelistCard tc)
	{		
		if (tc.hasTag(Tags.PREDAPLANT))
		{
			predaplantSynTrib(tc);
		}
		else if (tc.hasTag(Tags.PLANT))
		{
			plantSynTrib(tc);
		}		
	}
	
	public static void plantSynTrib(DuelistCard tributingCard)
	{
		if (player().hasPower(VioletCrystalPower.POWER_ID) && tributingCard.hasTag(Tags.PLANT)) 
		{ 
			TwoAmountPower pow = (TwoAmountPower)player().getPower(VioletCrystalPower.POWER_ID);
			int buff = pow.amount2;
			constrictAllEnemies(player(), DuelistMod.plantConstricted + buff); 
		}
		else if (tributingCard.hasTag(Tags.PLANT)) { constrictAllEnemies(player(), DuelistMod.plantConstricted); }
	}
	
	public static void predaplantSynTrib(DuelistCard tributingCard)
	{
		plantSynTrib(tributingCard);
		if (tributingCard.hasTag(Tags.PREDAPLANT)) { applyPowerToSelf(new ThornsPower(player(), DuelistMod.predaplantThorns)); }
	}
	
	public static void insectSynTrib(DuelistCard tributingCard)
	{
		if (tributingCard.hasTag(Tags.INSECT)) 
		{ 
			if (Util.getChallengeLevel() > 3 && Util.deckIs("Insect Deck"))
			{
				AbstractMonster rand = AbstractDungeon.getRandomMonster();
				if (rand != null) { applyPower(new PoisonPower(rand, AbstractDungeon.player, DuelistMod.insectPoisonDmg), rand); }
			}
			else
			{
				poisonAllEnemies(player(), DuelistMod.insectPoisonDmg); 
			}			
		}
	}
	
	public static void superSynTrib(DuelistCard tributingCard)
	{
		if (tributingCard.hasTag(Tags.SUPERHEAVY))
		{
			applyPowerToSelf(new DexterityPower(AbstractDungeon.player, DuelistMod.superheavyDex));
		}
	}
	
	public static void spellcasterSynTrib(DuelistCard tributingCard)
	{
		if (tributingCard.hasTag(Tags.SPELLCASTER))
		{
			AbstractPlayer p = AbstractDungeon.player;
			if (p.hasPower(SpellbookKnowledgePower.POWER_ID))
			{
				applyPowerToSelf(new FocusPower(p, p.getPower(SpellbookKnowledgePower.POWER_ID).amount));
			}
			
			if (p.hasPower(SpellbookMiraclePower.POWER_ID))
			{
				invert(1);
			}
			
			if (p.hasPower(SpellbookPowerPower.POWER_ID))
			{
				for (int i = 0; i < p.getPower(SpellbookPowerPower.POWER_ID).amount; i++)
				{
					AbstractCard randSpellcaster = DuelistCard.returnTrulyRandomFromSets(Tags.MONSTER, Tags.SPELLCASTER);
					AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(randSpellcaster, true));
				}
			}
			
			if (p.hasPower(SpellbookLifePower.POWER_ID))
			{
				gainTempHP(p.getPower(SpellbookLifePower.POWER_ID).amount);
			}
		}
	}
	
	
	// This is never actually called, but it appears in some deprecated functions
	// Remove this if you want to delete the calling references everywhere else
	public void zombieSynTrib(DuelistCard tributingCard)
	{
		if (tributingCard.hasTag(Tags.ZOMBIE))
		{
			Util.modifySouls(1);
		}
	}
	// =============== /TRIBUTE SYNERGY FUNCTIONS/ =======================================================================================================================================================
	
	
	// =============== INCREMENT FUNCTIONS =========================================================================================================================================================
	public static int getSummons(AbstractPlayer p)
	{
		if (!p.hasPower(SummonPower.POWER_ID)) { return 0; }
		else
		{
			SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
			return summonsInstance.amount;
		}
	}

	public static int getMaxSummons(AbstractPlayer p)
	{
		if (!p.hasPower(SummonPower.POWER_ID)) { return 0; }
		else
		{
			SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
			int ret = summonsInstance.MAX_SUMMONS;
			if (ret > -1) { return ret; }
			else { return 0; } 
		}
	}
	
	public static void setMaxSummons(int amount)
	{
		setMaxSummons(AbstractDungeon.player, amount);
	}
	
	public static void incMaxSummons(int amount)
	{
		incMaxSummons(AbstractDungeon.player, amount);
	}
	
	public static void decMaxSummons(int amount)
	{
		decMaxSummons(AbstractDungeon.player, amount);
	}

	public static void setMaxSummons(AbstractPlayer p, int amount)
	{
		if (p.hasPower(SummonPower.POWER_ID))
		{
			SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
			if (!(amount > 5 && p.hasRelic(MillenniumKey.ID))) 
			{ 
				summonsInstance.MAX_SUMMONS = amount; 
				DuelistMod.lastMaxSummons = amount; 
			}
			else if (amount > 5 && p.hasRelic(MillenniumKey.ID))
			{
				p.getRelic(MillenniumKey.ID).flash(); 
			}
			summonsInstance.updateCount(summonsInstance.amount);
			summonsInstance.updateStringColors();
			summonsInstance.updateDescription();
		}
		else if (!(amount > 5 && p.hasRelic(MillenniumKey.ID)))
		{
			 DuelistMod.lastMaxSummons = amount; 
		}
		
		if (DuelistMod.lastMaxSummons > DuelistMod.highestMaxSummonsObtained) { DuelistMod.highestMaxSummonsObtained = DuelistMod.lastMaxSummons; }

		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
			config.setInt(DuelistMod.PROP_MAX_SUMMONS, DuelistMod.lastMaxSummons);
			config.save();
			if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:setMaxSummons() ---> ran try block, lastMaxSummons: " + DuelistMod.lastMaxSummons); }
		} catch (Exception e) {
			e.printStackTrace();
		}
		player().hand.glowCheck();
	}
	
	public static void incMaxSummons(AbstractPlayer p, int amount)
	{
		if (amount < 0) { amount = 0; Util.log("Attempted to increment with a negative value. Setting amount to 0. Use decMaxSummons() to reduce max summons."); }
		boolean curseFailure = isPsiCurseActive();
		boolean challengeFailure = (Util.isCustomModActive("theDuelist:MaxSummonChallenge"));
		if (challengeFailure)
		{
			if (Util.isCustomModActive("challengethespire:Bronze Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 3) == 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedIncActionText, 1.0F, 2.0F));
					return; 
				}
			}
			else if (Util.isCustomModActive("challengethespire:Silver Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 2) == 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedIncActionText, 1.0F, 2.0F));
					return; 
				}
			}
			else if (Util.isCustomModActive("challengethespire:Gold Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 3) != 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedIncActionText, 1.0F, 2.0F));
					return; 
				}
			}
			else if (Util.isCustomModActive("challengethespire:Platinum Difficulty"))
			{
				if (AbstractDungeon.cardRandomRng.random(1, 4) != 1) 
				{ 
					AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedIncActionText, 1.0F, 2.0F));
					return; 
				}
			}
		}
		if (curseFailure) { if (AbstractDungeon.cardRandomRng.random(1, 2) == 1) { AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Strings.configFailedIncActionText, 1.0F, 2.0F)); return; }}
		Util.log("Incrementing Max Summons by: " + amount);
		boolean incremented = true;
		if (p.hasPower(SummonPower.POWER_ID))
		{
			SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
			if (summonsInstance.MAX_SUMMONS == 5 && p.hasRelic(MillenniumKey.ID)) { incremented = false; p.getRelic(MillenniumKey.ID).flash(); }
			else if (summonsInstance.MAX_SUMMONS + amount > 5 && p.hasRelic(MillenniumKey.ID)) { summonsInstance.MAX_SUMMONS = 5; DuelistMod.lastMaxSummons = 5; p.getRelic(MillenniumKey.ID).flash(); }
			else { summonsInstance.MAX_SUMMONS += amount; DuelistMod.lastMaxSummons += amount; }			
			summonsInstance.updateCount(summonsInstance.amount);
			summonsInstance.updateStringColors();
			summonsInstance.updateDescription();
		}
		
		else if (DuelistMod.lastMaxSummons + amount < 6 || !p.hasRelic(MillenniumKey.ID))
		{
			DuelistMod.lastMaxSummons += amount;
		}
		
		else
		{
			incremented = false;
		}
		
		for (AbstractRelic r : p.relics) { if (r instanceof DuelistRelic) { ((DuelistRelic)r).onIncrement(amount, DuelistMod.lastMaxSummons); }}
		for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  ((DuelistOrb)o).onIncrement(amount, DuelistMod.lastMaxSummons); }}
		for (AbstractPower pow : p.powers) { if (pow instanceof DuelistPower) { ((DuelistPower)pow).onIncrement(amount, DuelistMod.lastMaxSummons);}}
		for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onIncrementWhileInHand(amount, DuelistMod.lastMaxSummons);}}
		for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onIncrementWhileInDiscard(amount, DuelistMod.lastMaxSummons);}}
		for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onIncrementWhileInDraw(amount, DuelistMod.lastMaxSummons);}}
		for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onIncrementWhileInExhaust(amount, DuelistMod.lastMaxSummons);}}
		for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onIncrementWhileInGraveyard(amount, DuelistMod.lastMaxSummons); }}
		if (player().hasPower(SummonPower.POWER_ID)) {
			SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
			for (DuelistCard c : pow.actualCardSummonList) { if (c instanceof DuelistCard) { ((DuelistCard)c).onIncrementWhileSummoned(amount, DuelistMod.lastMaxSummons); }}
		}
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { ((DuelistPotion)pot).onIncrement(amount, DuelistMod.lastMaxSummons); }}
		if (p.stance instanceof DuelistStance) { ((DuelistStance)p.stance).onIncrement(amount, DuelistMod.lastMaxSummons); }
		
		if (incremented && Util.getChallengeLevel() > 3 && Util.deckIs("Increment Deck")) { damageSelfNotHP(1); }
		
		if (p.hasOrb() && incremented)
		{
			for (AbstractOrb o : p.orbs)
			{
				if (o instanceof Lava)
				{
					Lava la = (Lava)o;
					la.triggerPassiveEffect();
					if (la.gpcCheck()) { la.triggerPassiveEffect(); }
				}
			}
		}
		
		if (p.hasRelic(MillenniumSymbol.ID) && incremented && Util.deckIs("Increment Deck"))
		{
			int maxSumms = getMaxSummons(p);
			if (maxSumms > 0)
			{
				if (Util.getChallengeLevel() > 0 && Util.getChallengeLevel() < 7) { gainTempHP((int) (maxSumms / 2.0f)); }
				else if (Util.getChallengeLevel() < 0) { gainTempHP(maxSumms); }
			}
		}
		
		if (p.hasPower(SphereKuribohPower.POWER_ID) && incremented)
		{
			gainTempHP(p.getPower(SphereKuribohPower.POWER_ID).amount);
		}
		
		if (p.hasPower(WonderWandPower.POWER_ID) && incremented)
		{
			applyPowerToSelf(new MagickaPower(p, p, p.getPower(WonderWandPower.POWER_ID).amount));
		}
		
		if (p.hasPower(InariFirePower.POWER_ID) && incremented)
		{
			for (int i = 0; i < p.getPower(InariFirePower.POWER_ID).amount; i++)
			{
				AbstractOrb fire = new FireOrb();
				channel(fire);
			}
		}
		
		if (p.hasPower(DoomDonutsPower.POWER_ID) && incremented)
		{
			int newCost = p.getPower(DoomDonutsPower.POWER_ID).amount;
			AbstractPower doomPow = p.getPower(DoomDonutsPower.POWER_ID);
			ArrayList<AbstractCard> eligible = new ArrayList<AbstractCard>();
			for (AbstractCard c : p.discardPile.group)
			{
				if (c.cost != newCost && c.cost > -1)
				{
					eligible.add(c);
				}
			}
			if (eligible.size() > 0)
			{
				AbstractCard c = eligible.get(AbstractDungeon.cardRandomRng.random(eligible.size() - 1));
				if (c.cost != 0) {  c.modifyCostForCombat(-c.cost + newCost); c.isCostModified = true; }
				else if (newCost != 0) { c.modifyCostForCombat(newCost); c.isCostModified = true;}
				Util.log("Doom Donuts modifed the cost of " + c.name);
				doomPow.flash();
				AbstractDungeon.player.hand.glowCheck();
			}
		}
		
		if (DuelistMod.lastMaxSummons > DuelistMod.highestMaxSummonsObtained) { DuelistMod.highestMaxSummonsObtained = DuelistMod.lastMaxSummons; }

		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
			config.setInt(DuelistMod.PROP_MAX_SUMMONS, DuelistMod.lastMaxSummons);
			config.save();
			if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:incMaxSummons() ---> ran try block, lastMaxSummons: " + DuelistMod.lastMaxSummons); }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		player().hand.glowCheck();
	}
	
	public static boolean canDecMaxSummons(int amount)
	{
		AbstractPlayer p = AbstractDungeon.player;
		if (p.hasPower(SummonPower.POWER_ID))
		{
			SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
			if (summonsInstance.MAX_SUMMONS - amount > 0) { return true; }			
		}
		
		return false;
	}

	public static void decMaxSummons(AbstractPlayer p, int amount)
	{
		if (amount < 0) { amount = 0; Util.log("Attempted to decrement with a negative value. Setting amount to 0. Use incMaxSummons() to increase max summons."); }
		if (p.hasPower(SummonPower.POWER_ID))
		{
			SummonPower summonsInstance = (SummonPower)p.getPower(SummonPower.POWER_ID);
			if (summonsInstance.MAX_SUMMONS - amount < 1) { summonsInstance.MAX_SUMMONS = 1; DuelistMod.lastMaxSummons = 1; }
			else { summonsInstance.MAX_SUMMONS -= amount; DuelistMod.lastMaxSummons -= amount; }
			summonsInstance.updateCount(summonsInstance.amount);
			summonsInstance.updateStringColors();
			summonsInstance.updateDescription();
		}
		
		else if (DuelistMod.lastMaxSummons - amount > 0)
		{
			DuelistMod.lastMaxSummons -= amount;
		}

		try {
			SpireConfig config = new SpireConfig("TheDuelist", "DuelistConfig",DuelistMod.duelistDefaults);
			config.setInt(DuelistMod.PROP_MAX_SUMMONS, DuelistMod.lastMaxSummons);
			config.save();
			if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:decMaxSummons() ---> ran try block, lastMaxSummons: " + DuelistMod.lastMaxSummons); }
		} catch (Exception e) {
			e.printStackTrace();
		}
		player().hand.glowCheck();
	}
	// =============== /INCREMENT FUNCTIONS/ =======================================================================================================================================================
	
	
	
	// =============== RESUMMON FUNCTIONS =========================================================================================================================================================
	public static boolean checkForUpgradeResummon(AbstractCard resummonedCard)
	{
		boolean amtInc = true;
		AbstractPlayer p = AbstractDungeon.player;
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { amtInc = ((DuelistPotion)pot).upgradeResummon(resummonedCard); if (amtInc) { return true; }}}
		for (AbstractRelic r : p.relics) { if (r instanceof DuelistRelic) { amtInc = ((DuelistRelic)r).upgradeResummon(resummonedCard); if (amtInc) { return true; }}}
		for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  amtInc = ((DuelistOrb)o).upgradeResummon(resummonedCard); if (amtInc) { return true; }}}
		for (AbstractPower pow : p.powers) { if (pow instanceof DuelistPower) { amtInc = ((DuelistPower)pow).upgradeResummon(resummonedCard); if (amtInc) { return true; }}}
		for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).upgradeResummonWhileInHand(resummonedCard); if (amtInc) { return true; }}}
		for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).upgradeResummonWhileInDiscard(resummonedCard); if (amtInc) { return true; }}}
		for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).upgradeResummonWhileInDraw(resummonedCard); if (amtInc) { return true; }}}
		for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).upgradeResummonWhileInExhaust(resummonedCard); if (amtInc) { return true; }}}
		for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).upgradeResummonWhileInGraveyard(resummonedCard); if (amtInc) { return true; }}}
		if (player().hasPower(SummonPower.POWER_ID)) {
			SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
			for (DuelistCard c : pow.actualCardSummonList) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).upgradeResummonWhileSummoned(resummonedCard); if (amtInc) { return true; }}}
		}
		return amtInc;
	}
	
	public static boolean allowResummons(AbstractCard resummonedCard)
	{
		if (resummonedCard.type.equals(CardType.CURSE) || resummonedCard.type.equals(CardType.STATUS)) { return false; }
		boolean amtInc = true;
		AbstractPlayer p = AbstractDungeon.player;
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { amtInc = ((DuelistPotion)pot).allowResummon(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractRelic r : p.relics) { if (r instanceof DuelistRelic) { amtInc = ((DuelistRelic)r).allowResummon(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  amtInc = ((DuelistOrb)o).allowResummon(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractPower pow : p.powers) { if (pow instanceof DuelistPower) { amtInc = ((DuelistPower)pow).allowResummon(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).allowResummonWhileInHand(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).allowResummonWhileInDiscard(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).allowResummonWhileInDraw(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).allowResummonWhileInExhaust(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).allowResummonWhileInGraveyard(resummonedCard); if (!amtInc) { return false; }}}
		if (player().hasPower(SummonPower.POWER_ID)) {
			SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
			for (DuelistCard c : pow.actualCardSummonList) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).allowResummonWhileSummoned(resummonedCard); if (!amtInc) { return false; }}}
		}
		return amtInc;
	}

	public static boolean allowResummonsRevive(AbstractCard resummonedCard)
	{
		if (resummonedCard.type.equals(CardType.CURSE) || resummonedCard.type.equals(CardType.STATUS)) { return false; }
		boolean amtInc;
		AbstractPlayer p = AbstractDungeon.player;
		if (resummonedCard.hasTag(Tags.EXEMPT)) { return false; }
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { amtInc = ((DuelistPotion)pot).allowResummon(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractRelic r : p.relics) { if (r instanceof DuelistRelic) { amtInc = ((DuelistRelic)r).allowResummon(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  amtInc = ((DuelistOrb)o).allowResummon(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractPower pow : p.powers) { if (pow instanceof DuelistPower) { amtInc = ((DuelistPower)pow).allowResummon(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).allowResummonWhileInHand(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).allowResummonWhileInDiscard(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).allowResummonWhileInDraw(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).allowResummonWhileInExhaust(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).allowResummonWhileInGraveyard(resummonedCard); if (!amtInc) { return false; }}}
		if (player().hasPower(SummonPower.POWER_ID)) {
			SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
			for (DuelistCard c : pow.actualCardSummonList) { amtInc = c.allowResummonWhileSummoned(resummonedCard); if (!amtInc) { return false; }}
		}
		return true;
	}
	
	public static boolean allowResummonsWithExtraChecks(AbstractCard resummonedCard)
	{
		if (resummonedCard.type.equals(CardType.CURSE) || resummonedCard.type.equals(CardType.STATUS)) { return false; }
		boolean amtInc;
		AbstractPlayer p = AbstractDungeon.player;
		if (resummonedCard.hasTag(Tags.EXEMPT)) { return false; }
		if (resummonedCard.hasTag(Tags.ZOMBIE)) { int loss = 1; if (DuelistMod.bookEclipseThisCombat) { loss = 2; }if (!Util.checkSouls(loss)) { return false; }}; 
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { amtInc = ((DuelistPotion)pot).allowResummon(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractRelic r : p.relics) { if (r instanceof DuelistRelic) { amtInc = ((DuelistRelic)r).allowResummon(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  amtInc = ((DuelistOrb)o).allowResummon(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractPower pow : p.powers) { if (pow instanceof DuelistPower) { amtInc = ((DuelistPower)pow).allowResummon(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).allowResummonWhileInHand(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).allowResummonWhileInDiscard(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).allowResummonWhileInDraw(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).allowResummonWhileInExhaust(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).allowResummonWhileInGraveyard(resummonedCard); if (!amtInc) { return false; }}}
		if (player().hasPower(SummonPower.POWER_ID)) {
			SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
			for (DuelistCard c : pow.actualCardSummonList) { amtInc = (c).allowResummonWhileSummoned(resummonedCard); if (!amtInc) { return false; }}
		}
		return true;
	}
	
	public static boolean allowResummonsSkipOnlyExempt(AbstractCard resummonedCard)
	{
		if (resummonedCard.type.equals(CardType.CURSE) || resummonedCard.type.equals(CardType.STATUS)) { return false; }
		boolean amtInc = true;
		AbstractPlayer p = AbstractDungeon.player;
		if (resummonedCard.hasTag(Tags.ZOMBIE)) { int loss = 1; if (DuelistMod.bookEclipseThisCombat) { loss = 2; }if (!Util.checkSouls(loss)) { amtInc = false; return amtInc; }}; 
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { amtInc = ((DuelistPotion)pot).allowResummon(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractRelic r : p.relics) { if (r instanceof DuelistRelic) { amtInc = ((DuelistRelic)r).allowResummon(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  amtInc = ((DuelistOrb)o).allowResummon(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractPower pow : p.powers) { if (pow instanceof DuelistPower) { amtInc = ((DuelistPower)pow).allowResummon(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).allowResummonWhileInHand(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).allowResummonWhileInDiscard(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).allowResummonWhileInDraw(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).allowResummonWhileInExhaust(resummonedCard); if (!amtInc) { return false; }}}
		for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).allowResummonWhileInGraveyard(resummonedCard); if (!amtInc) { return false; }}}
		if (player().hasPower(SummonPower.POWER_ID)) {
			SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
			for (DuelistCard c : pow.actualCardSummonList) { if (c instanceof DuelistCard) { amtInc = ((DuelistCard)c).allowResummonWhileSummoned(resummonedCard); if (!amtInc) { return false; }}}
		}
		return amtInc;
	}
	
	public static int checkResummonInc(AbstractCard resummonedCard)
	{
		int amtInc = 0;
		AbstractPlayer p = AbstractDungeon.player;
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { amtInc += ((DuelistPotion)pot).modifyResummonAmt(resummonedCard); }}
		for (AbstractRelic r : p.relics) { if (r instanceof DuelistRelic) { amtInc += ((DuelistRelic)r).modifyResummonAmt(resummonedCard); }}
		for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  amtInc += ((DuelistOrb)o).modifyResummonAmt(resummonedCard); }}
		for (AbstractPower pow : p.powers) { if (pow instanceof DuelistPower) { amtInc += ((DuelistPower)pow).modifyResummonAmt(resummonedCard); }}
		for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyResummonAmtWhileInHand(resummonedCard); }}
		for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyResummonAmtWhileInDiscard(resummonedCard); }}
		for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyResummonAmtWhileInDraw(resummonedCard); }}
		for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyResummonAmtWhileInExhaust(resummonedCard); }}
		for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyResummonAmtWhileInGraveyard(resummonedCard); }}
		if (player().hasPower(SummonPower.POWER_ID)) {
			SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
			for (DuelistCard c : pow.actualCardSummonList) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyResummonAmtWhileSummoned(resummonedCard); }}
		}
		return amtInc;
	}
	
	public static int getCurrentReviveCost()
	{
		int amtInc = 3;
		AbstractPlayer p = AbstractDungeon.player;
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { amtInc += ((DuelistPotion)pot).modifyReviveCost(DuelistMod.entombedCardsCombat); }}
		for (AbstractRelic r : p.relics) { if (r instanceof DuelistRelic) { amtInc += ((DuelistRelic)r).modifyReviveCost(DuelistMod.entombedCardsCombat); }}
		for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  amtInc += ((DuelistOrb)o).modifyReviveCost(DuelistMod.entombedCardsCombat); }}
		for (AbstractPower pow : p.powers) { if (pow instanceof DuelistPower) { amtInc += ((DuelistPower)pow).modifyReviveCost(DuelistMod.entombedCardsCombat); }}
		for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyReviveCostWhileInHand(DuelistMod.entombedCardsCombat); }}
		for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyReviveCostWhileInDiscard(DuelistMod.entombedCardsCombat); }}
		for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyReviveCostWhileInDraw(DuelistMod.entombedCardsCombat); }}
		for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyReviveCostWhileInExhaust(DuelistMod.entombedCardsCombat); }}
		for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyReviveCostWhileInGraveyard(DuelistMod.entombedCardsCombat); }}
		if (player().hasPower(SummonPower.POWER_ID)) {
			SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
			for (DuelistCard c : pow.actualCardSummonList) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyReviveCostWhileSummoned(DuelistMod.entombedCardsCombat); }}
		}
		return amtInc;
	}
	
	public static int getCurrentReviveListSize()
	{
		int amtInc = 5;
		if (Util.getChallengeLevel() > 3 && Util.deckIs("Zombie Deck")) { amtInc = 3; }
		AbstractPlayer p = AbstractDungeon.player;
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { amtInc += ((DuelistPotion)pot).modifyReviveListSize(); }}
		for (AbstractRelic r : p.relics) { if (r instanceof DuelistRelic) { amtInc += ((DuelistRelic)r).modifyReviveListSize(); }}
		for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  amtInc += ((DuelistOrb)o).modifyReviveListSize(); }}
		for (AbstractPower pow : p.powers) { if (pow instanceof DuelistPower) { amtInc += ((DuelistPower)pow).modifyReviveListSize(); }}
		for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyReviveListSizeWhileInHand(); }}
		for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyReviveListSizeWhileInDiscard(); }}
		for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyReviveListSizeWhileInDraw(); }}
		for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyReviveListSizeWhileInExhaust(); }}
		for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyReviveListSizeWhileInGraveyard(); }}
		if (player().hasPower(SummonPower.POWER_ID)) {
			SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
			for (DuelistCard c : pow.actualCardSummonList) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyReviveListSizeWhileSummoned(); }}
		}
		return amtInc;
	}
	
	public static int getCurrentMaxReviveListSize()
	{
		int amtInc = 5;
		AbstractPlayer p = AbstractDungeon.player;
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { amtInc += ((DuelistPotion)pot).modifyReviveCost(DuelistMod.entombedCardsCombat); }}
		for (AbstractRelic r : p.relics) { if (r instanceof DuelistRelic) { amtInc += ((DuelistRelic)r).modifyReviveCost(DuelistMod.entombedCardsCombat); }}
		for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  amtInc += ((DuelistOrb)o).modifyReviveCost(DuelistMod.entombedCardsCombat); }}
		for (AbstractPower pow : p.powers) { if (pow instanceof DuelistPower) { amtInc += ((DuelistPower)pow).modifyReviveCost(DuelistMod.entombedCardsCombat); }}
		for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyReviveCostWhileInHand(DuelistMod.entombedCardsCombat); }}
		for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyReviveCostWhileInDiscard(DuelistMod.entombedCardsCombat); }}
		for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyReviveCostWhileInDraw(DuelistMod.entombedCardsCombat); }}
		for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyReviveCostWhileInExhaust(DuelistMod.entombedCardsCombat); }}
		for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyReviveCostWhileInGraveyard(DuelistMod.entombedCardsCombat); }}
		if (player().hasPower(SummonPower.POWER_ID)) {
			SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
			for (DuelistCard c : pow.actualCardSummonList) { if (c instanceof DuelistCard) { amtInc += ((DuelistCard)c).modifyReviveCostWhileSummoned(DuelistMod.entombedCardsCombat); }}
		}
		return amtInc;
	}
	
	public void checkResummon(boolean actuallyResummoned)
	{
		AbstractPlayer p = AbstractDungeon.player;
		if (actuallyResummoned) { triggerPossessed(); }
		for (AbstractRelic r : p.relics) { if (r instanceof DuelistRelic) { ((DuelistRelic)r).onResummon(this, actuallyResummoned); }}
		for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  ((DuelistOrb)o).onResummon(this, actuallyResummoned); }}
		for (AbstractPower pow : p.powers) { if (pow instanceof DuelistPower) { ((DuelistPower)pow).onResummon(this, actuallyResummoned);}}
		for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onResummonWhileInHand(this, actuallyResummoned); }}
		for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onResummonWhileInDiscard(this, actuallyResummoned); }}
		for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onResummonWhileInDraw(this, actuallyResummoned); }}
		for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onResummonWhileInExhaust(this, actuallyResummoned); }}
		for (AbstractCard c : TheDuelist.resummonPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onResummonWhileInGraveyard(this, actuallyResummoned); }}
		if (player().hasPower(SummonPower.POWER_ID)) {
			SummonPower pow = (SummonPower)player().getPower(SummonPower.POWER_ID);
			for (DuelistCard c : pow.actualCardSummonList) { if (c instanceof DuelistCard) { ((DuelistCard)c).onResummonWhileSummoned(this, actuallyResummoned); }}
		}
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { ((DuelistPotion)pot).onResummon(this, actuallyResummoned); }}
		if (p.stance instanceof DuelistStance) { ((DuelistStance)p.stance).onResummon(this, actuallyResummoned); }
		if (this.hasTag(Tags.ZOMBIE) && actuallyResummoned) { DuelistMod.zombiesResummonedThisCombat++; DuelistMod.zombiesResummonedThisRun++; }
		if (AbstractDungeon.player.hasPower(CardSafePower.POWER_ID) && actuallyResummoned) { drawTag(AbstractDungeon.player.getPower(CardSafePower.POWER_ID).amount, Tags.ZOMBIE); }
		if (actuallyResummoned) { DuelistMod.resummonsThisRun++; }
	}
	
	public static void addToGraveyard(AbstractCard card)
	{
		TheDuelist.resummonPile.addToBottom(card.makeStatEquivalentCopy());
		if (DuelistMod.firstCardInGraveThisCombat == null || DuelistMod.firstCardInGraveThisCombat instanceof CancelCard)
		{
			DuelistMod.firstCardInGraveThisCombat = card.makeStatEquivalentCopy();
		}
	}
	
	public static void runRevive(AbstractCard cardToResummon, boolean noSoulLoss)
	{
		resummon(true, noSoulLoss, cardToResummon, null, 1, false, false);
	}
	
	public static void runRevive(AbstractCard cardToResummon, int amtToResummon, boolean noSoulLoss)
	{
		resummon(true, noSoulLoss, cardToResummon, null, amtToResummon, false, false);
	}
	
	public static void runRevive(AbstractCard cardToResummon, AbstractMonster m, boolean noSoulLoss)
	{
		resummon(true, noSoulLoss, cardToResummon, m, 1, false, false);
	}
	
	public static void runRevive(AbstractCard cardToResummon, AbstractMonster m, boolean allowExempt, boolean noSoulLoss)
	{
		resummon(true, noSoulLoss, cardToResummon, m, 1, false, allowExempt);
	}
	
	public static void runRevive(AbstractCard cardToResummon, AbstractMonster m, boolean allowExempt, boolean upgrade, boolean noSoulLoss)
	{
		resummon(true, noSoulLoss, cardToResummon, m, 1, upgrade, allowExempt);
	}
	
	public static void runRevive(AbstractCard cardToResummon, AbstractMonster target, int amtToResummon, boolean noSoulLoss)
	{
		resummon(true, noSoulLoss, cardToResummon, target, amtToResummon, false, false);
	}
	
	public static void runRevive(AbstractCard cardToResummon, AbstractMonster target, int amtToResummon, boolean allowExempt, boolean noSoulLoss)
	{
		resummon(true, noSoulLoss, cardToResummon, target, amtToResummon, false, allowExempt);
	}
	
	public static void runRevive(AbstractCard cardToResummon, AbstractMonster target, int amtToResummon, boolean upgradeResummonedCard, boolean allowExempt, boolean noSoulLoss)
	{
		resummon(true, noSoulLoss, cardToResummon, target, amtToResummon, upgradeResummonedCard, allowExempt);
	}
	
	public static void resummon(AbstractCard cardToResummon)
	{
		resummon(false, false, cardToResummon, null, 1, false, false);
	}
	
	public static void resummon(AbstractCard cardToResummon, int amtToResummon)
	{
		resummon(false, false, cardToResummon, null, amtToResummon, false, false);
	}
	
	public static void resummon(AbstractCard cardToResummon, AbstractMonster m)
	{
		resummon(false, false, cardToResummon, m, 1, false, false);
	}
	
	public static void resummon(AbstractCard cardToResummon, AbstractMonster m, boolean allowExempt)
	{
		resummon(false, false, cardToResummon, m, 1, false, allowExempt);
	}
	
	public static void resummon(AbstractCard cardToResummon, AbstractMonster m, boolean allowExempt, boolean upgrade)
	{
		resummon(false, false, cardToResummon, m, 1, upgrade, allowExempt);
	}
	
	public static void resummon(AbstractCard cardToResummon, AbstractMonster target, int amtToResummon)
	{
		resummon(false, false, cardToResummon, target, amtToResummon, false, false);
	}
	
	public static void resummon(AbstractCard cardToResummon, AbstractMonster target, int amtToResummon, boolean allowExempt)
	{
		resummon(false, false, cardToResummon, target, amtToResummon, false, allowExempt);
	}
	
	public static void resummon(AbstractCard cardToResummon, AbstractMonster target, int amtToResummon, boolean upgradeResummonedCard, boolean allowExempt)
	{
		resummon(false, false, cardToResummon, target, amtToResummon, upgradeResummonedCard, allowExempt);
	}
	
	// 0- Pre-Resummon Actions (hidden card)
		// Custom abstracts implement new handler
	// 1- Resummon Check (hidden card)
		// Check if next card is resummonable and if we charge souls
		// Check if upgrade next card
			// Continue: take next step
			// Exit:     move to next card in resummon queue and repeat this step
	// 2- Resummon (Actual card)
		// VFX/SFX
		// Resummon the card on the target enemy
		// Take next step
	
	// 3- Mid-Resummon Actions (hidden card)
		// Send last Resummoned card to Graveyard
		// Check onResummon() effects	
	
	// If resummon queue is not empty: return to step 1
	// Else: proceed
	
	// 4- Post-Resummon Actions (hidden card)
		// Custom abstracts implement new handler

	
	public static void resummon(boolean revive, boolean noSoulLoss, AbstractCard cardToResummon, AbstractMonster target, int amtToResummon, boolean upgradeResummonedCard, boolean allowExempt)
	{
		boolean allowResummon = allowResummons(cardToResummon);
		if (allowResummon)
		{
			if (!cardToResummon.hasTag(Tags.EXEMPT) || allowExempt)
			{
				if (!noSoulLoss)
				{
					if (revive)
					{
						int soulLoss = getCurrentReviveCost() * amtToResummon;
						while (!Util.checkSouls(soulLoss) && amtToResummon > 0) 
						{
							amtToResummon--;
							soulLoss = getCurrentReviveCost() * amtToResummon;
						}
						if (Util.checkSouls(soulLoss)) { Util.modifySouls(-soulLoss); }
						else { 
							AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Not enough #rSouls", 1.0F, 2.0F)); 
							return; 
						}
					}
					
					else if (cardToResummon.hasTag(Tags.ZOMBIE))
					{
						int soulLoss = 1;
						if (DuelistMod.bookEclipseThisCombat) { soulLoss = 2; }
						soulLoss *= amtToResummon;
						while (!Util.checkSouls(soulLoss) && amtToResummon > 0) 
						{
							amtToResummon--;
							soulLoss = 1;
							if (DuelistMod.bookEclipseThisCombat) { soulLoss = 2; }
							soulLoss *= amtToResummon;
						}
						if (Util.checkSouls(soulLoss)) { Util.modifySouls(-soulLoss); }
						else { 
							AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Not enough #rSouls", 1.0F, 2.0F)); 
							return; 
						}
					}
				}
				if (!upgradeResummonedCard) { upgradeResummonedCard = checkForUpgradeResummon(cardToResummon); }
				amtToResummon += checkResummonInc(cardToResummon);				
				for (int i = 0; i < amtToResummon; i++)
				{
					if (!cardToResummon.color.equals(AbstractCardEnum.DUELIST))
					{
						int orbs = 1;
						if (cardToResummon.cost > 0) { orbs += cardToResummon.cost * 2; }
						if (cardToResummon instanceof DuelistCard) 
						{ 
							if (((DuelistCard)cardToResummon).tributes > 0) { orbs += ((DuelistCard)cardToResummon).tributes * 3; }
							if (((DuelistCard)cardToResummon).summons > 0) { orbs += ((DuelistCard)cardToResummon).summons; }	
						}
						AbstractDungeon.actionManager.addToBottom(new VFXAction(player(), new VerticalAuraEffect(Color.RED, player().hb.cX, player().hb.cY), 0.15f));
						try { for (int k = 0; k < orbs; k++) { AbstractDungeon.actionManager.addToBottom(new VFXAction(new ResummonOrbEffect(player().hb.cX, player().hb.cY, target))); }} catch (NullPointerException ignored) {}
						CardCrawlGame.sound.playV("theDuelist:ResummonWhoosh", 10.0f);
					}
					final AbstractCard tmp = cardToResummon.makeSameInstanceOf();
		            AbstractDungeon.player.limbo.addToBottom(tmp);
		            tmp.current_x = cardToResummon.current_x;
		            tmp.current_y = cardToResummon.current_y;
		            tmp.target_x = Settings.WIDTH / 2.0f - 300.0f * Settings.scale;
		            tmp.target_y = Settings.HEIGHT / 2.0f;
		            if (!cardToResummon.tags.contains(Tags.FORCE_TRIB_FOR_RESUMMONS) && !(cardToResummon instanceof GeneticAlgorithm)) { tmp.misc = 52; }
		            if (upgradeResummonedCard) { tmp.upgrade(); }
		            if (target != null) { tmp.calculateCardDamage(target); }
		            else
		            { 
		            	target = AbstractDungeon.getRandomMonster();
		            	if (target != null) { tmp.calculateCardDamage(target); }
		            }
		            tmp.purgeOnUse = true;
		            tmp.dontTriggerOnUseCard = true;
		            if (!tmp.color.equals(AbstractCardEnum.DUELIST) || (tmp.hasTag(Tags.TOKEN) && !(tmp instanceof AbstractBuffCard))) {
		            	DuelistCard.addToGraveyard(tmp);
		            }
		            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, target, cardToResummon.energyOnUse, true, true), false);
		            //if (cardToResummon.damage > 0 && AbstractDungeon.player.hasPower(PenNibPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new ResummonModAction(ref, true, false)); Util.log("Resummon cut " + ref.name + "'s damage by half due to Flight or Pen Nib"); }
					if (tmp instanceof DuelistCard && !tmp.color.equals(AbstractCardEnum.DUELIST)) { ((DuelistCard)tmp).onResummonThisCard(); ((DuelistCard)tmp).checkResummon(true); }
				}
				
				if (DuelistMod.mirrorLadybug)
				{
					AbstractDungeon.actionManager.addToBottom(new QueueCardDuelistAction(new MirrorLadybugFixer(), target)); 
				}
			}
		}
		else
		{
			if (cardToResummon instanceof DuelistCard) { ((DuelistCard)cardToResummon).checkResummon(false); }
		}
	}
	
	public static void reviveOnAll(boolean noSoulLoss, AbstractCard cardToResummon, int amtToResummon, boolean upgradeResummonedCard, boolean allowExempt)
	{
		resummonOnAll(true, noSoulLoss, cardToResummon, amtToResummon, upgradeResummonedCard, allowExempt);
	}
	
	public static void resummonOnAll(AbstractCard cardToResummon, int amtToResummon, boolean upgradeResummonedCard, boolean allowExempt)
	{
		resummonOnAll(false, false, cardToResummon, amtToResummon, upgradeResummonedCard, allowExempt);
	}
	
	public static void resummonOnAll(boolean revive, boolean noSoulLoss, AbstractCard cardToResummon, int amtToResummon, boolean upgradeResummonedCard, boolean allowExempt)
	{
		boolean allowResummon = allowResummons(cardToResummon);		
		if (allowResummon)
		{
			ArrayList<CardQueueItem> queue = new ArrayList<>();
			ArrayList<AbstractMonster> mons = getAllMons();
			for (AbstractMonster target : mons)
			{
				Util.log("Resummoning " + cardToResummon.cardID + " on " + target.name);
				if (!cardToResummon.hasTag(Tags.EXEMPT) || allowExempt)
				{
					if (!noSoulLoss)
					{
						if (revive)
						{
							int soulLoss = getCurrentReviveCost() * amtToResummon;
							while (!Util.checkSouls(soulLoss) && amtToResummon > 0) 
							{
								amtToResummon--;
								soulLoss = getCurrentReviveCost() * amtToResummon;
							}
							if (Util.checkSouls(soulLoss)) { Util.modifySouls(-soulLoss); }
							else { 
								AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Not enough #rSouls", 1.0F, 2.0F)); 
								return; 
							}
						}
						
						else if (cardToResummon.hasTag(Tags.ZOMBIE))
						{
							int soulLoss = 1;
							if (DuelistMod.bookEclipseThisCombat) { soulLoss = 2; }
							soulLoss *= amtToResummon;
							while (!Util.checkSouls(soulLoss) && amtToResummon > 0) 
							{
								amtToResummon--;
								soulLoss = 1;
								if (DuelistMod.bookEclipseThisCombat) { soulLoss = 2; }
								soulLoss *= amtToResummon;
							}
							if (Util.checkSouls(soulLoss)) { Util.modifySouls(-soulLoss); }
							else { 
								AbstractDungeon.actionManager.addToBottom(new TalkAction(true, "Not enough #rSouls", 1.0F, 2.0F)); 
								return; 
							}
						}
					}
					if (!upgradeResummonedCard) { upgradeResummonedCard = checkForUpgradeResummon(cardToResummon); }
					amtToResummon += checkResummonInc(cardToResummon);				
					for (int i = 0; i < amtToResummon; i++)
					{
						if (!cardToResummon.color.equals(AbstractCardEnum.DUELIST))
						{
							int orbs = 1;
							if (cardToResummon.cost > 0) { orbs += cardToResummon.cost * 2; }
							if (cardToResummon instanceof DuelistCard) 
							{ 
								if (((DuelistCard)cardToResummon).tributes > 0) { orbs += ((DuelistCard)cardToResummon).tributes * 3; }
								if (((DuelistCard)cardToResummon).summons > 0) { orbs += ((DuelistCard)cardToResummon).summons; }	
							}
							AbstractDungeon.actionManager.addToBottom(new VFXAction(player(), new VerticalAuraEffect(Color.RED, player().hb.cX, player().hb.cY), 0.15f));
							try { for (int k = 0; k < orbs; k++) { AbstractDungeon.actionManager.addToBottom(new VFXAction(new ResummonOrbEffect(player().hb.cX, player().hb.cY, target))); }} catch (NullPointerException ignored) {}
							CardCrawlGame.sound.playV("theDuelist:ResummonWhoosh", 10.0f);
						}
						final AbstractCard tmp = cardToResummon.makeSameInstanceOf();
			            AbstractDungeon.player.limbo.addToBottom(tmp);
			            tmp.current_x = cardToResummon.current_x;
			            tmp.current_y = cardToResummon.current_y;
			            tmp.target_x = Settings.WIDTH / 2.0f - 300.0f * Settings.scale;
			            tmp.target_y = Settings.HEIGHT / 2.0f;
			            if (!cardToResummon.tags.contains(Tags.FORCE_TRIB_FOR_RESUMMONS) && !(cardToResummon instanceof GeneticAlgorithm)) { tmp.misc = 52; }
			            if (upgradeResummonedCard) { tmp.upgrade(); }
			            if (target != null) { tmp.calculateCardDamage(target); }
			            else
			            { 
			            	target = AbstractDungeon.getRandomMonster();
			            	if (target != null) { tmp.calculateCardDamage(target); }
			            }
			            tmp.purgeOnUse = true;
			            tmp.dontTriggerOnUseCard = true;			        
			            queue.add(new CardQueueItem(tmp, target, cardToResummon.energyOnUse, true, true));
			            //if (cardToResummon.damage > 0 && AbstractDungeon.player.hasPower(PenNibPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new ResummonModAction(ref, true, false)); Util.log("Resummon cut " + ref.name + "'s damage by half due to Flight or Pen Nib"); }
						if (tmp instanceof DuelistCard && !tmp.color.equals(AbstractCardEnum.DUELIST)) { ((DuelistCard)tmp).onResummonThisCard(); ((DuelistCard)tmp).checkResummon(true); }
					}
					
					if (DuelistMod.mirrorLadybug)
					{
						AbstractDungeon.actionManager.addToBottom(new QueueCardDuelistAction(new MirrorLadybugFixer(), target)); 
					}
				}
			}
			
			boolean addedToGrave = false;
			for (CardQueueItem q : queue) 
			{ 
				if (!addedToGrave) 
				{ 
		            if (!q.card.color.equals(AbstractCardEnum.DUELIST) || (q.card.hasTag(Tags.TOKEN) && !(q.card instanceof AbstractBuffCard))) {
		            	DuelistCard.addToGraveyard(q.card); addedToGrave = true; 
		            }
				} 
				Util.log("Found " + q.card.cardID + " in resummonOnAllQueue"); 
				AbstractDungeon.actionManager.addCardQueueItem(q, false);  
			}
		}
		else
		{
			if (cardToResummon instanceof DuelistCard) { ((DuelistCard)cardToResummon).checkResummon(false); }
		}
	}
	
	@Deprecated
	public static void fullResummon(DuelistCard cardCopy, boolean upgrade, AbstractMonster targ, boolean unused)
	{
		resummon(cardCopy, targ, 1, upgrade, false);
	}
	
	public static void playNoResummon(int copies, DuelistCard cardCopy, boolean upgradeResummon, AbstractCreature target, boolean superFast)
	{
		if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID))
		{
			SummonPower instance = (SummonPower)AbstractDungeon.player.getPower(SummonPower.POWER_ID);
			if (!instance.isMonsterSummoned(new VanityFiend().originalName) && !cardCopy.hasTag(Tags.EXEMPT))
			{
				for (int i = 0; i < copies; i++)
				{
					cardCopy = (DuelistCard) cardCopy.makeStatEquivalentCopy();
					if (!cardCopy.tags.contains(Tags.FORCE_TRIB_FOR_RESUMMONS)) { cardCopy.misc = 52; }
					if (upgradeResummon) { cardCopy.upgrade(); }
					cardCopy.freeToPlayOnce = true;
					cardCopy.applyPowers();
					cardCopy.purgeOnUse = true;
					cardCopy.dontTriggerOnUseCard = true;
					if (superFast) { AbstractDungeon.actionManager.addToTop(new QueueCardDuelistAction(cardCopy, target)); }
					else { AbstractDungeon.actionManager.addToTop(new QueueCardDuelistAction(cardCopy, target, 1.0F)); }
				}
			}		
		}
		else if (!cardCopy.hasTag(Tags.EXEMPT))
		{
			for (int i = 0; i < copies; i++)
			{
				cardCopy = (DuelistCard) cardCopy.makeStatEquivalentCopy();
				if (!cardCopy.tags.contains(Tags.FORCE_TRIB_FOR_RESUMMONS)) { cardCopy.misc = 52; }
				if (upgradeResummon) { cardCopy.upgrade(); }
				cardCopy.freeToPlayOnce = true;
				cardCopy.applyPowers();
				cardCopy.purgeOnUse = true;
				cardCopy.dontTriggerOnUseCard = true;
				if (superFast) { AbstractDungeon.actionManager.addToTop(new QueueCardDuelistAction(cardCopy, target)); }
				else { AbstractDungeon.actionManager.addToTop(new QueueCardDuelistAction(cardCopy, target, 1.0F)); }
			}
		}
	}

	public static void playNoResummon(DuelistCard cardCopy, boolean upgradeResummon, AbstractCreature target, boolean superFast)
	{
		playNoResummon(1, cardCopy, upgradeResummon, target, superFast);
	}
	
	public static void playNoResummon(BuffCard cardCopy, boolean upgradeResummon, AbstractMonster target, boolean superFast)
	{			
		cardCopy.freeToPlayOnce = true;
		cardCopy.applyPowers();
		cardCopy.purgeOnUse = true;
		cardCopy.dontTriggerOnUseCard = true;
		if (superFast) { AbstractDungeon.actionManager.addToTop(new QueueCardDuelistAction(cardCopy, target)); }
		else { AbstractDungeon.actionManager.addToTop(new QueueCardDuelistAction(cardCopy, target, 1.0F)); }		
	}

	public static void playNoResummonBuffCard(int copies, BuffCard cardCopy, boolean upgradeResummon, AbstractCreature target, boolean superFast)
	{
		if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID))
		{
			SummonPower instance = (SummonPower)AbstractDungeon.player.getPower(SummonPower.POWER_ID);
			if (!instance.isMonsterSummoned(new VanityFiend().originalName) && !cardCopy.hasTag(Tags.EXEMPT))
			{
				for (int i = 0; i < copies; i++)
				{
					cardCopy = cardCopy.makeStatEquivalentCopy();
					Util.log("Buff card resummon description: " + cardCopy.rawDescription);
					if (!cardCopy.tags.contains(Tags.FORCE_TRIB_FOR_RESUMMONS)) { cardCopy.misc = 52; }
					if (upgradeResummon) { cardCopy.upgrade(); }
					cardCopy.freeToPlayOnce = true;
					cardCopy.applyPowers();
					cardCopy.purgeOnUse = true;
					cardCopy.dontTriggerOnUseCard = true;
					if (superFast) { AbstractDungeon.actionManager.addToTop(new QueueCardDuelistAction(cardCopy, target)); }
					else { AbstractDungeon.actionManager.addToTop(new QueueCardDuelistAction(cardCopy, target, 1.0F)); }
				}
			}
		}
		else if (!cardCopy.hasTag(Tags.EXEMPT))
		{
			for (int i = 0; i < copies; i++)
			{
				cardCopy = cardCopy.makeStatEquivalentCopy();
				if (!cardCopy.tags.contains(Tags.FORCE_TRIB_FOR_RESUMMONS)) { cardCopy.misc = 52; }
				if (upgradeResummon) { cardCopy.upgrade(); }
				cardCopy.freeToPlayOnce = true;
				cardCopy.applyPowers();
				cardCopy.purgeOnUse = true;
				cardCopy.dontTriggerOnUseCard = true;
				if (superFast) { AbstractDungeon.actionManager.addToTop(new QueueCardDuelistAction(cardCopy, target)); }
				else { AbstractDungeon.actionManager.addToTop(new QueueCardDuelistAction(cardCopy, target, 1.0F)); }
			}
		}
	}
	// =============== /RESUMMON FUNCTIONS/ =======================================================================================================================================================
	
	
	
	// =============== SUMMON MODIFICATION FUNCTIONS =========================================================================================================================================================
	public void changeSummonsInBattle(int addAmount, boolean combat) { AbstractDungeon.actionManager.addToTop(new ModifySummonAction(this, addAmount, combat)); }
	
	public void upgradeSummons(int add)
	{
		this.summons = this.baseSummons += add;
		this.upgradedSummons = true;
	}
	
	public void modifySummonsPerm(int add)
	{
		if (this.summons + add <= 0)
		{
			this.baseSummons = this.summons = 0;
			this.originalDescription = this.rawDescription;
			/*int indexOfTribText = this.rawDescription.indexOf("Summon");
			int modIndex = 21;
			int indexOfNL = indexOfTribText + 21;
			if (this.rawDescription.substring(indexOfNL, indexOfNL + 4).equals(" NL ")) { modIndex += 4; }
			if (indexOfTribText > -1)
			{
				String newDesc = this.rawDescription.substring(0, indexOfTribText) + this.rawDescription.substring(indexOfTribText + modIndex);
				this.rawDescription = newDesc;
				this.originalDescription = newDesc;
				if (DuelistMod.debug) { System.out.println(this.originalName + " made a string: " + newDesc + " this.baseSummons + add : " + this.baseSummons + add); }
			}*/
		}
		else { this.baseSummons = this.summons += add; }
		this.isSummonsModified = true;
		this.isSummonModPerm = true;
		this.permSummonChange += add;
		this.initializeDescription();
		player().hand.glowCheck();
	}
	
	public void modifySummonsForTurn(int add)
	{
		if (this.summons + add <= 0)
		{
			this.summons = 0;
			this.summonsForTurn = 0;
			this.originalDescription = this.rawDescription;
			/*int indexOfTribText = this.rawDescription.indexOf("Summon");
			int modIndex = 21;
			int indexOfNL = indexOfTribText + 21;
			if (this.rawDescription.substring(indexOfNL, indexOfNL + 4).equals(" NL ")) { modIndex += 4; }
			if (indexOfTribText > -1)
			{
				String newDesc = this.rawDescription.substring(0, indexOfTribText) + this.rawDescription.substring(indexOfTribText + modIndex);
				this.originalDescription = this.rawDescription;
				this.rawDescription = newDesc;
				if (DuelistMod.debug) { System.out.println(this.originalName + " made a string: " + newDesc + " this.summons + add : " + this.summons + add); }
			}*/
		}
		else { this.originalDescription = this.rawDescription; this.summonsForTurn = this.summons += add; }
		this.isSummonsModifiedForTurn = true;		
		this.isSummonsModified = true;
		this.moreSummons = 0;
		this.extraSummonsForThisTurn += add;
		this.initializeDescription();
		player().hand.glowCheck();
	}
	
	public void modifySummons(int add)
	{
		if (this.summons + add <= 0)
		{
			this.summons = 0;
			this.originalDescription = this.rawDescription;
			/*int indexOfTribText = this.rawDescription.indexOf("Summon");
			int modIndex = 21;
			int indexOfNL = indexOfTribText + 21;
			if (this.rawDescription.substring(indexOfNL, indexOfNL + 4).equals(" NL ")) { modIndex += 4; }
			if (indexOfTribText > -1)
			{
				this.originalDescription = this.rawDescription;
				String newDesc = this.rawDescription.substring(0, indexOfTribText) + this.rawDescription.substring(indexOfTribText + modIndex);
				this.rawDescription = newDesc;
				if (DuelistMod.debug) { System.out.println(this.originalName + " made a string: " + newDesc + " this.baseSummons + add : " + this.baseSummons + add); }
			}*/
		}
		else { this.summons += add; }		
		this.isSummonsModified = true;
		this.initializeDescription();
		player().hand.glowCheck();
	}
	
	public void setSummons(int set)
	{
		if (set <= 0)
		{
			this.baseSummons = this.summons = 0;
			this.originalDescription = this.rawDescription;
			/*int indexOfTribText = this.rawDescription.indexOf("Summon");
			int modIndex = 21;
			int indexOfNL = indexOfTribText + 21;
			if (this.rawDescription.substring(indexOfNL, indexOfNL + 4).equals(" NL ")) { modIndex += 4; }
			if (indexOfTribText > -1)
			{
				this.originalDescription = this.rawDescription;
				String newDesc = this.rawDescription.substring(0, indexOfTribText) + this.rawDescription.substring(indexOfTribText + modIndex);
				this.rawDescription = newDesc;
				if (DuelistMod.debug) { System.out.println(this.originalName + " made a string: " + newDesc + " this.baseSummons + add : " + this.summons); }
			}*/
		}
		else { this.baseSummons = this.summons = set; }		
		this.isSummonsModified = true;
		this.initializeDescription();
		player().hand.glowCheck();
	}
	
	public static void addMonsterToHandModSummons(String name, int add, boolean combat)
	{
    	DuelistCard newCopy = (DuelistCard) DuelistCard.newCopyOfMonster(name).makeStatEquivalentCopy();
    	if (newCopy.summons > 0)
    	{
    		AbstractDungeon.actionManager.addToTop(new ModifySummonAction(newCopy, add, combat));
    		AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(newCopy));
    	}
	}
	
	public static void addMonsterToDeckModSummons(String name, int add, boolean combat)
	{
    	DuelistCard newCopy = (DuelistCard) DuelistCard.newCopyOfMonster(name).makeStatEquivalentCopy();
    	if (newCopy.summons > 0)
    	{
    		AbstractDungeon.actionManager.addToTop(new ModifySummonAction(newCopy, add, combat));
    		AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(newCopy, true, false));
    	}
	}
	
	public static void obtainMonsterModSummons(String name, int add)
	{
    	DuelistCard newCopy = (DuelistCard) DuelistCard.newCopyOfMonster(name).makeStatEquivalentCopy();
    	if (newCopy.summons > 0)
    	{
    		AbstractDungeon.actionManager.addToTop(new ModifySummonPermAction(newCopy, add));
    		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(newCopy, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
    	}
	}
	// =============== /SUMMON MODIFICATION FUNCTIONS/ =======================================================================================================================================================
	
	public void upgradeSecondMagic(int add)
	{
		this.secondMagic = this.baseSecondMagic += add;
		this.upgradedSecondMagic = true;
	}
	
	public void upgradeThirdMagic(int add)
	{
		this.thirdMagic = this.baseThirdMagic += add;
		this.upgradedThirdMagic = true;
	}
	
	public void upgradeEntomb(int add)
	{
		this.entomb = this.baseEntomb += add;
		this.upgradedEntomb = true;
	}
	
	public void upgradeIncrement(int add)
	{
		this.incrementMagic = this.baseIncrementMagic += add;
		this.upgradedIncrement = true;
	}
	
	// =============== TRIBUTE MODIFICATION FUNCTIONS =========================================================================================================================================================
	public void changeTributesInBattle(int addAmount, boolean combat)
	{
		AbstractDungeon.actionManager.addToTop(new ModifyTributeAction(this, addAmount, combat));
		player().hand.glowCheck();
	}
	
	public void upgradeTributes(int add)
	{
		this.tributes = this.baseTributes += add;
		this.upgradedTributes = true;
	}
	
	public void modifyTributesPerm(int add)
	{
		if (this.tributes + add <= 0)
		{
			this.baseTributes = this.tributes = 0;
			this.originalDescription = this.rawDescription; 
			/*int indexOfTribText = this.rawDescription.indexOf("Tribute");
			int modIndex = 22;
			int indexOfNL = indexOfTribText + 22;
			if (this.rawDescription.substring(indexOfNL, indexOfNL + 4).equals(" NL ")) { modIndex += 4; }
			if (indexOfTribText > -1)
			{
				String newDesc = this.rawDescription.substring(0, indexOfTribText) + this.rawDescription.substring(indexOfTribText + modIndex);
				this.rawDescription = newDesc;
				this.originalDescription = newDesc;
				if (DuelistMod.debug) { System.out.println(this.originalName + " made a string: " + newDesc + " this.baseTributes + add : " + this.baseTributes + add); }
			}*/
		}
		else { this.baseTributes = this.tributes += add; }
		this.isTributesModified = true;
		this.isTribModPerm = true;
		this.permTribChange += add;
		this.initializeDescription();
		player().hand.glowCheck();
	}
	
	public void modifyTributesForTurn(int add)
	{
		if (this.tributes + add <= 0)
		{
			this.tributesForTurn = 0;
			this.tributes = 0;
			this.originalDescription = this.rawDescription; 
			/*int indexOfTribText = this.rawDescription.indexOf("Tribute");
			int modIndex = 22;
			int indexOfNL = indexOfTribText + 22;
			
			if (indexOfTribText > -1)
			{
				if (this.rawDescription.substring(indexOfNL, indexOfNL + 4).equals(" NL ")) { modIndex += 4; }
				String newDesc = this.rawDescription.substring(0, indexOfTribText) + this.rawDescription.substring(indexOfTribText + modIndex);
				this.originalDescription = this.rawDescription;
				this.rawDescription = newDesc;
				if (DuelistMod.debug) { System.out.println(this.originalName + " made a string: " + newDesc + " this.tributes + add : " + this.tributes + add); }
			}*/
		}
		else { this.originalDescription = this.rawDescription; this.tributesForTurn = this.tributes += add; }
		this.isTributesModifiedForTurn = true;
		this.isTributesModified = true;
		this.moreTributes = 0;
		this.extraTributesForThisTurn += add;
		this.initializeDescription();
		player().hand.glowCheck();
	}

	public void modifyTributes(int add)
	{
		
		if (this.tributes + add <= 0)
		{
			this.tributes = this.baseTributes = 0;
			this.originalDescription = this.rawDescription; 
			/*int indexOfTribText = this.rawDescription.indexOf("Tribute");
			int modIndex = 22;
			int indexOfNL = indexOfTribText + 22;
			if (indexOfTribText > -1)
			{
				if (this.rawDescription.substring(indexOfNL, indexOfNL + 4).equals(" NL ")) { modIndex += 4; }
				this.originalDescription = this.rawDescription;
				String newDesc = this.rawDescription.substring(0, indexOfTribText) + this.rawDescription.substring(indexOfTribText + modIndex);
				this.rawDescription = newDesc;
				if (DuelistMod.debug) { System.out.println(this.originalName + " made a string: " + newDesc + " this.baseTributes + add : " + this.baseTributes + add); }
			}*/	
		}
		else { this.baseTributes = this.tributes += add; }
		this.isTributesModified = true; 
		this.initializeDescription();
		player().hand.glowCheck();
	}
	
	public void setTributes(int set)
	{
		if (set <= 0)
		{
			this.baseTributes = this.tributes = 0;
			this.originalDescription = this.rawDescription; 
			/*int indexOfTribText = this.rawDescription.indexOf("Tribute");
			int modIndex = 22;
			int indexOfNL = indexOfTribText + 22;
			if (this.rawDescription.substring(indexOfNL, indexOfNL + 4).equals(" NL ")) { modIndex += 4; }
			if (indexOfTribText > -1)
			{
				this.originalDescription = this.rawDescription;
				String newDesc = this.rawDescription.substring(0, indexOfTribText) + this.rawDescription.substring(indexOfTribText + modIndex);
				this.rawDescription = newDesc;
				if (DuelistMod.debug) { System.out.println(this.originalName + " made a string: " + newDesc + " this.tributes : " + this.tributes); }
			}*/
		}
		else { this.baseTributes = this.tributes = set; }
		this.isTributesModified = true; 
		this.initializeDescription();
		player().hand.glowCheck();
	}
	
	public static void addMonsterToHandModTributes(String name, int add, boolean combat)
	{
    	DuelistCard newCopy = (DuelistCard) DuelistCard.newCopyOfMonster(name).makeStatEquivalentCopy();
    	if (newCopy.tributes > 0)
    	{
    		AbstractDungeon.actionManager.addToTop(new ModifyTributeAction(newCopy, add, combat));
    		AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(newCopy));
    	}
	}
	
	public static void addMonsterToDeckModTributes(String name, int add, boolean combat)
	{
    	DuelistCard newCopy = (DuelistCard) DuelistCard.newCopyOfMonster(name).makeStatEquivalentCopy();
    	if (newCopy.tributes > 0)
    	{
    		AbstractDungeon.actionManager.addToTop(new ModifyTributeAction(newCopy, add, combat));
    		AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(newCopy, true, false));
    	}
	}
	
	public static void obtainMonsterModTributes(String name, int add)
	{
    	DuelistCard newCopy = (DuelistCard) DuelistCard.newCopyOfMonster(name).makeStatEquivalentCopy();
    	if (newCopy.tributes > 0)
    	{
    		AbstractDungeon.actionManager.addToTop(new ModifyTributePermAction(newCopy, add));
    		AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(newCopy, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
    	}
	}
	// =============== /TRIBUTE MODIFICATION FUNCTIONS/ =======================================================================================================================================================
	
	
	
	// =============== CARD MODAL FUNCTIONS =========================================================================================================================================================

	// =============== /CARD MODAL FUNCTIONS/ =======================================================================================================================================================

	
	// =============== ORB MODAL FUNCTIONS =========================================================================================================================================================

	// =============== /ORB MODAL FUNCTIONS/ =======================================================================================================================================================
	
	
	
	// =============== ORB FUNCTIONS =========================================================================================================================================================
	public void evokeAllFlameBased()
	{
		AbstractPlayer p = AbstractDungeon.player;
		ArrayList<AbstractOrb> flames = new ArrayList<AbstractOrb>();
    	for (AbstractOrb o : p.orbs)
    	{
    		if (o instanceof Lava || o instanceof FireOrb || o instanceof Blaze || o instanceof DuelistHellfire)
			{
    			flames.add(o);
			}
    	}
    	
    	for (AbstractOrb o : flames)
    	{
    		this.addToBot(new EvokeSpecificOrbAction(o, 1));
    	}
	}
	
	public static int fillOrbSlotsWithOrb(AbstractOrb orb)
	{
		int lightning = 0;
		int channeled = 0;
		AbstractPlayer p = AbstractDungeon.player;
    	lightning = p.maxOrbs - p.filledOrbCount();
    	if (lightning > 0) { for (int i = 0; i < lightning; i++) { channel(orb); channeled++; }}
    	return channeled;
	}

	public static void channel(AbstractOrb orb)
	{
		AbstractDungeon.actionManager.addToTop(new ChannelAction(orb));
	}
	
	public static void channel(AbstractOrb orb, int amount)
	{
		for (int i = 0; i < amount; i++) 
		{ 
			AbstractOrb copy = orb.makeCopy();
			AbstractDungeon.actionManager.addToTop(new ChannelAction(copy));
		}
	}

	public static void channelBottom(AbstractOrb orb)
	{
		AbstractDungeon.actionManager.addToBottom(new ChannelAction(orb));
	}
	
	public static void zombieLavaChannel()
	{
		AbstractOrb lava = new Shadow(AbstractDungeon.player.hasRelic(ZombieRelic.ID));
		channel(lava);
	}
	
	public static boolean checkForWater()
	{
		if (Loader.isModLoaded("conspire") && Loader.isModLoaded("ReplayTheSpireMod")){ return RandomOrbHelperDualMod.checkWater(); }
		else if (Loader.isModLoaded("conspire") && !Loader.isModLoaded("ReplayTheSpireMod")){ return RandomOrbHelperCon.checkWater(); }
		else if (Loader.isModLoaded("ReplayTheSpireMod") && !Loader.isModLoaded("conspire")) { return  RandomOrbHelperRep.checkWater(); }
		else { return RandomOrbHelper.checkWater(); }
	}
	
	public static void channelWater()
	{
		if (Loader.isModLoaded("conspire") && Loader.isModLoaded("ReplayTheSpireMod")){ RandomOrbHelperDualMod.channelWater(); }
		else if (Loader.isModLoaded("conspire") && !Loader.isModLoaded("ReplayTheSpireMod")){ RandomOrbHelperCon.channelWater(); }
		else if (Loader.isModLoaded("ReplayTheSpireMod") && !Loader.isModLoaded("conspire")) { RandomOrbHelperRep.channelWater(); }
		else { RandomOrbHelper.channelWater(); }
	}

	public static void channelRandom()
	{
		if (Loader.isModLoaded("conspire") && Loader.isModLoaded("ReplayTheSpireMod")){ RandomOrbHelperDualMod.channelRandomOrb(); }
		else if (Loader.isModLoaded("conspire") && !Loader.isModLoaded("ReplayTheSpireMod")){ RandomOrbHelperCon.channelRandomOrb(); }
		else if (Loader.isModLoaded("ReplayTheSpireMod") && !Loader.isModLoaded("conspire")) { RandomOrbHelperRep.channelRandomOrb(); }
		else { RandomOrbHelper.channelRandomOrb(); }
	}
	
	public static void channelRandomNoGlassOrGate()
	{
		if (Loader.isModLoaded("conspire") && Loader.isModLoaded("ReplayTheSpireMod")){ RandomOrbHelperDualMod.channelRandomOrbNoGlassOrGate(); }
		else if (Loader.isModLoaded("conspire") && !Loader.isModLoaded("ReplayTheSpireMod")){ RandomOrbHelperCon.channelRandomOrbNoGlassOrGate(); }
		else if (Loader.isModLoaded("ReplayTheSpireMod") && !Loader.isModLoaded("conspire")) { RandomOrbHelperRep.channelRandomOrbNoGlassOrGate(); }
		else { RandomOrbHelper.channelRandomOrbNoGlassOrGate(); }
	}
	
	public static void spellcasterPuzzleChannel()
	{
		AbstractOrb copy;
		if (Loader.isModLoaded("conspire") && Loader.isModLoaded("ReplayTheSpireMod")){ copy = RandomOrbHelperDualMod.spellcasterPuzzleChannel(); }
		else if (Loader.isModLoaded("conspire") && !Loader.isModLoaded("ReplayTheSpireMod")){ copy = RandomOrbHelperCon.spellcasterPuzzleChannel(); }
		else if (Loader.isModLoaded("ReplayTheSpireMod") && !Loader.isModLoaded("conspire")) { copy = RandomOrbHelperRep.spellcasterPuzzleChannel(); }
		else { copy = RandomOrbHelper.spellcasterPuzzleChannel(); }
		
		if (AbstractDungeon.player.hasRelic(MillenniumSymbol.ID))
		{
			if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss || AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite)
			{
				RandomOrbHelper.triggerSecondSpellcasterOrb(copy);
			}
		}
	}
	
	public static void channelRandomOffensive()
	{
		if (Loader.isModLoaded("conspire") && Loader.isModLoaded("ReplayTheSpireMod")){ RandomOrbHelperDualMod.channelRandomOffense(); }
		else if (Loader.isModLoaded("conspire") && !Loader.isModLoaded("ReplayTheSpireMod")){ RandomOrbHelperCon.channelRandomOffense(); }
		else if (Loader.isModLoaded("ReplayTheSpireMod") && !Loader.isModLoaded("conspire")) { RandomOrbHelperRep.channelRandomOffense(); }
		else { RandomOrbHelper.channelRandomOffense(); }
	}
	
	public static void channelRandomDefensive()
	{
		if (Loader.isModLoaded("conspire") && Loader.isModLoaded("ReplayTheSpireMod")){ RandomOrbHelperDualMod.channelRandomDefense(); }
		else if (Loader.isModLoaded("conspire") && !Loader.isModLoaded("ReplayTheSpireMod")){ RandomOrbHelperCon.channelRandomDefense(); }
		else if (Loader.isModLoaded("ReplayTheSpireMod") && !Loader.isModLoaded("conspire")) { RandomOrbHelperRep.channelRandomDefense(); }
		else { RandomOrbHelper.channelRandomDefense(); }
	}
	
	public static ArrayList<AbstractOrb> returnRandomOrbList()
	{
		ArrayList<AbstractOrb> returnOrbs = new ArrayList<AbstractOrb>();
		if (Loader.isModLoaded("conspire") && Loader.isModLoaded("ReplayTheSpireMod")){ returnOrbs.addAll(RandomOrbHelperDualMod.returnOrbList()); }
		else if (Loader.isModLoaded("conspire") && !Loader.isModLoaded("ReplayTheSpireMod")){ returnOrbs.addAll(RandomOrbHelperCon.returnOrbList()); }
		else if (Loader.isModLoaded("ReplayTheSpireMod") && !Loader.isModLoaded("conspire")) { returnOrbs.addAll(RandomOrbHelperRep.returnOrbList()); }
		else { returnOrbs.addAll(RandomOrbHelper.returnOrbList()); }
		return returnOrbs;
	}
	
	public static void resetInvertStringMap()
	{
		if (Loader.isModLoaded("conspire") && Loader.isModLoaded("ReplayTheSpireMod")){ RandomOrbHelperDualMod.resetOrbStringMap(); }
		else if (Loader.isModLoaded("conspire") && !Loader.isModLoaded("ReplayTheSpireMod")){ RandomOrbHelperCon.resetOrbStringMap(); }
		else if (Loader.isModLoaded("ReplayTheSpireMod") && !Loader.isModLoaded("conspire")) { RandomOrbHelperRep.resetOrbStringMap(); }
		else { RandomOrbHelper.resetOrbStringMap(); }
	}

	public static void evokeAll()
	{
		AbstractDungeon.actionManager.addToBottom(new EvokeAllOrbsAction());
	}
	
	public static void evoke(int amount)
	{
		AbstractDungeon.actionManager.addToBottom(new EvokeOrbAction(amount));
	}
	
	public static void evokeMult(int amount)
	{
		for (int i = 0; i < amount; i++)
		{
			AbstractDungeon.player.evokeWithoutLosingOrb();
		}
		AbstractDungeon.actionManager.addToTop(new RemoveNextOrbAction());
	}

	
	public static void evokeMult(int amount, AbstractOrb orb)
	{
		for (int i = 0; i < amount; i++)
		{
			orb.onEvoke();
		}
	}

	public static void invertAll(int amount)
	{
		System.out.println("(A) orb slots::::: " + AbstractDungeon.player.maxOrbs);
		if (AbstractDungeon.player.orbs.size() > 0 && AbstractDungeon.player.hasOrb())
		{
			int numberOfInverts;
			if (AbstractDungeon.player.hasRelic(InversionRelic.ID)) { amount++; }
			if (AbstractDungeon.player.hasRelic(InversionEvokeRelic.ID)) { numberOfInverts = 2; }
			else { numberOfInverts = 1; }		
			resetInvertStringMap();
			for (int i = 0; i < numberOfInverts; i++)
			{				
				int invertedOrbs = 0;
				//ArrayList<AbstractOrb> baseOrbs = new ArrayList<AbstractOrb>();
				ArrayList<String> invertOrbNames = new ArrayList<String>();
				int loopCount = AbstractDungeon.player.filledOrbCount();
				for (int j = 0; j < loopCount; j++)
				{
					//baseOrbs.add(AbstractDungeon.player.orbs.get(j));
					invertOrbNames.add(AbstractDungeon.player.orbs.get(j).name);
					evokeMult(amount, AbstractDungeon.player.orbs.get(j));
					//evokeMult(amount);
					System.out.println("Orb we added to baseOrbs: " + AbstractDungeon.player.orbs.get(j).makeCopy());
					invertedOrbs++;
				}
				AbstractDungeon.actionManager.addToTop(new RemoveAllOrbsAction());
				System.out.println("(B) orb slots::::: " + AbstractDungeon.player.maxOrbs);
				for (int j = 0; j < invertedOrbs; j++)
				{
					if (DuelistMod.invertableOrbNames.contains(invertOrbNames.get(j)))
					{
						AbstractDungeon.actionManager.addToBottom(new ChannelAction(DuelistMod.invertStringMap.get(invertOrbNames.get(j)).makeCopy()));
						if (DuelistMod.debug) { System.out.println("Orb we inverted to channel: " + invertOrbNames.get(j)); }
					}
					else
					{
						if (DuelistMod.debug) { System.out.println("Skipped inverting " + invertOrbNames.get(j) + " because we did not find an entry in the allowed invertable orbs names list"); }
					}
				}
				
			}
		}
	}
	
	public static void invertAllWithoutRemoving(int amount)
	{
		if (AbstractDungeon.player.orbs.size() > 0 && AbstractDungeon.player.hasOrb())
		{
			int numberOfInverts;
			if (AbstractDungeon.player.hasRelic(InversionRelic.ID)) { amount++; }
			if (AbstractDungeon.player.hasRelic(InversionEvokeRelic.ID)) { numberOfInverts = 2; }
			else { numberOfInverts = 1; }			
			for (int i = 0; i < numberOfInverts; i++)
			{
				resetInvertStringMap();
				int invertedOrbs = 0;
				ArrayList<String> baseOrbs = new ArrayList<String>();
				int loopCount = AbstractDungeon.player.filledOrbCount();
				for (int j = 0; j < loopCount; j++)
				{
					evokeMult(amount, AbstractDungeon.player.orbs.get(j));
					invertedOrbs++;
					baseOrbs.add(AbstractDungeon.player.orbs.get(j).name);
				}
		
				for (int j = 0; j < invertedOrbs; j++)
				{
					if (DuelistMod.invertableOrbNames.contains(baseOrbs.get(j)))
					{
						AbstractDungeon.actionManager.addToBottom(new ChannelAction(DuelistMod.invertStringMap.get(baseOrbs.get(j)).makeCopy()));
						if (DuelistMod.debug) { System.out.println("Orb we inverted to channel: " + baseOrbs.get(j)); }
					}
					else
					{
						if (DuelistMod.debug) { System.out.println("Skipped inverting " + baseOrbs.get(j) + " because we did not find an entry in the allowed invertable orbs names list"); }
					}
				}
			}
		}
	}
	
	public static void invertAllMult(int amount, int numberOfInverts)
	{
		for (int i = 0; i < numberOfInverts; i++) {	invertAll(amount); }
	}
	
	public static void invertAllWithoutRemovingMult(int amount, int numberOfInverts)
	{
		for (int i = 0; i < numberOfInverts; i++) { invertAllWithoutRemoving(amount); }
	}

	public static void invert(int amount)
	{
		if (AbstractDungeon.player.orbs.size() > 0 && AbstractDungeon.player.hasOrb())
		{
			int numberOfInverts;
			if (AbstractDungeon.player.hasRelic(InversionRelic.ID)) { amount++; }
			if (AbstractDungeon.player.hasRelic(InversionEvokeRelic.ID)) { numberOfInverts = 2; }
			else { numberOfInverts = 1; }		
			resetInvertStringMap();
			AbstractOrb o = AbstractDungeon.player.orbs.get(0);
			String orbToInvert = o.name;
			evokeMult(amount * numberOfInverts, AbstractDungeon.player.orbs.get(0));
			AbstractDungeon.actionManager.addToTop(new RemoveNextOrbAction());
			for (int i = 0; i < numberOfInverts; i++)
			{
				if (DuelistMod.invertableOrbNames.contains(orbToInvert))
				{
					AbstractDungeon.actionManager.addToBottom(new ChannelAction(DuelistMod.invertStringMap.get(orbToInvert).makeCopy()));
					if (DuelistMod.debug) { System.out.println("Orb we inverted to channel: " + orbToInvert); }
				}
				else
				{
					Util.log("Skipped inverting " + orbToInvert + " because we did not find an entry in the allowed invertable orbs names list");
					if (DuelistMod.debug)
					{ 
						int counter = 0;
						for (String s : DuelistMod.invertableOrbNames)
						{
							Util.log("Invert Names[" + counter + "]: " + s + " ||| vs. ||| " + orbToInvert);
						}
					}
				}
			}
		}
	}
	
	public static void invertIceQueen(int amount, int frosts)
	{
		if (AbstractDungeon.player.orbs.size() > 0 && AbstractDungeon.player.hasOrb())
		{
			int numberOfInverts;
			if (AbstractDungeon.player.hasRelic(InversionRelic.ID)) { amount++; }
			if (AbstractDungeon.player.hasRelic(InversionEvokeRelic.ID)) { numberOfInverts = 2; }
			else { numberOfInverts = 1; }		
			resetInvertStringMap();
			AbstractOrb o = AbstractDungeon.player.orbs.get(0);
			String orbToInvert = o.name;
			evokeMult(amount * numberOfInverts, AbstractDungeon.player.orbs.get(0));
			AbstractDungeon.actionManager.addToTop(new RemoveNextOrbAction());
			for (int i = 0; i < frosts; i++)
			{
				AbstractDungeon.actionManager.addToBottom(new ChannelAction(new Frost()));
			}
			for (int i = 0; i < numberOfInverts; i++)
			{
				if (DuelistMod.invertableOrbNames.contains(orbToInvert))
				{
					AbstractDungeon.actionManager.addToBottom(new ChannelAction(DuelistMod.invertStringMap.get(orbToInvert).makeCopy()));
					if (DuelistMod.debug) { System.out.println("Orb we inverted to channel: " + orbToInvert); }
				}
				else
				{
					if (DuelistMod.debug) { System.out.println("Skipped inverting " + orbToInvert + " because we did not find an entry in the allowed invertable orbs names list"); }
				}
			}
		}
	}
	
	public static void invertQueenDragon(int amount, int frosts)
	{
		if (AbstractDungeon.player.orbs.size() > 0 && AbstractDungeon.player.hasOrb())
		{
			int numberOfInverts;
			if (AbstractDungeon.player.hasRelic(InversionRelic.ID)) { amount++; }
			if (AbstractDungeon.player.hasRelic(InversionEvokeRelic.ID)) { numberOfInverts = 2; }
			else { numberOfInverts = 1; }		
			resetInvertStringMap();
			AbstractOrb o = AbstractDungeon.player.orbs.get(0);
			String orbToInvert = o.name;
			evokeMult(amount * numberOfInverts, AbstractDungeon.player.orbs.get(0));
			AbstractDungeon.actionManager.addToTop(new RemoveNextOrbAction());
			for (int i = 0; i < frosts; i++)
			{
				AbstractDungeon.actionManager.addToBottom(new ChannelAction(new FireOrb()));
			}
			for (int i = 0; i < numberOfInverts; i++)
			{
				if (DuelistMod.invertableOrbNames.contains(orbToInvert))
				{
					AbstractDungeon.actionManager.addToBottom(new ChannelAction(DuelistMod.invertStringMap.get(orbToInvert).makeCopy()));
					if (DuelistMod.debug) { System.out.println("Orb we inverted to channel: " + orbToInvert); }
				}
				else
				{
					if (DuelistMod.debug) { System.out.println("Skipped inverting " + orbToInvert + " because we did not find an entry in the allowed invertable orbs names list"); }
				}
			}
		}
	}
	
	public static void invertQueenRoses(int amount, int frosts)
	{
		if (AbstractDungeon.player.orbs.size() > 0 && AbstractDungeon.player.hasOrb())
		{
			int numberOfInverts;
			if (AbstractDungeon.player.hasRelic(InversionRelic.ID)) { amount++; }
			if (AbstractDungeon.player.hasRelic(InversionEvokeRelic.ID)) { numberOfInverts = 2; }
			else { numberOfInverts = 1; }		
			resetInvertStringMap();
			AbstractOrb o = AbstractDungeon.player.orbs.get(0);
			String orbToInvert = o.name;
			evokeMult(amount * numberOfInverts, AbstractDungeon.player.orbs.get(0));
			AbstractDungeon.actionManager.addToTop(new RemoveNextOrbAction());
			for (int i = 0; i < frosts; i++)
			{
				AbstractDungeon.actionManager.addToBottom(new ChannelAction(new Mud()));
			}
			for (int i = 0; i < numberOfInverts; i++)
			{
				if (DuelistMod.invertableOrbNames.contains(orbToInvert))
				{
					AbstractDungeon.actionManager.addToBottom(new ChannelAction(DuelistMod.invertStringMap.get(orbToInvert).makeCopy()));
					if (DuelistMod.debug) { System.out.println("Orb we inverted to channel: " + orbToInvert); }
				}
				else
				{
					if (DuelistMod.debug) { System.out.println("Skipped inverting " + orbToInvert + " because we did not find an entry in the allowed invertable orbs names list"); }
				}
			}
		}
	}

	public static void invertMult(int amount, int numberOfInverts)
	{
		for (int i = 0; i < numberOfInverts; i++) { invert(amount); }
	}
	
	public static void removeOrbs(int amount)
	{
		for (int i = 0; i < amount; i++)
		{
			AbstractDungeon.actionManager.addToTop(new RemoveNextOrbAction());
		}
	}
	// =============== /ORB FUNCTIONS/ =======================================================================================================================================================
	
	
	
	// =============== RANDOM CARD FUNCTIONS =========================================================================================================================================================
	public static AbstractCard completelyRandomCard()
	{
		if (DuelistMod.totallyRandomCardList.size() > 0)
		{
			return DuelistMod.totallyRandomCardList.get(AbstractDungeon.cardRandomRng.random(DuelistMod.totallyRandomCardList.size() - 1));
		}
		else
		{
			Util.log("DuelistCard.completelyRandomCard() is generating the entire pool manually because the pre-prepared list is not generated properly...");
			ArrayList<AbstractCard> cardList = new ArrayList<>();
			Map<String, String> cardMap = new HashMap<>();
			for (AbstractCard c : DuelistMod.myCards)
			{
				if (cardMap.containsKey(c.cardID)) 
				{
					cardList.add(c.makeCopy());
					cardMap.put(c.cardID, c.cardID);
				}
			}
			for (AbstractCard c : TheDuelist.cardPool.group)
			{
				if (cardMap.containsKey(c.cardID)) 
				{
					cardList.add(c.makeCopy());
					cardMap.put(c.cardID, c.cardID);
				}
			}
			
			for (AbstractCard c : AbstractDungeon.colorlessCardPool.group)
			{
				if (cardMap.containsKey(c.cardID)) 
				{
					cardList.add(c.makeCopy());
					cardMap.put(c.cardID, c.cardID);
				}
			}
			
			for (AbstractCard c : BaseGameHelper.getAllBaseGameCards())
			{
				if (cardMap.containsKey(c.cardID)) 
				{
					cardList.add(c.makeCopy());
					cardMap.put(c.cardID, c.cardID);
				}
			}
	
			if (DuelistMod.isInfiniteSpire)
			{
				for (AbstractCard c : InfiniteSpireHelper.getAllBlackCards())
				{
					if (cardMap.containsKey(c.cardID)) 
					{
						cardList.add(c.makeCopy());
						cardMap.put(c.cardID, c.cardID);
					}
				}
			}
	
			if (DuelistMod.isAnimator)
			{
				try 
				{ 
					for (AbstractCard c : AnimatorHelper.getAllCards()) 
					{ 
						if (cardMap.containsKey(c.cardID)) 
						{
							cardList.add(c.makeCopy());
							cardMap.put(c.cardID, c.cardID);
						}
					}
				}
				catch (IllegalAccessException e) { Util.log("Illegal access exception while attempting to read Animator cards into map"); }
			}
	
			if (DuelistMod.isClockwork)
			{
				for (AbstractCard c : ClockworkHelper.getAllCards())
				{
					if (cardMap.containsKey(c.cardID)) 
					{
						cardList.add(c.makeCopy());
						cardMap.put(c.cardID, c.cardID);
					}
				}
			}
	
			if (DuelistMod.isConspire)
			{
				for (AbstractCard c : ConspireHelper.getAllCards())
				{
					if (cardMap.containsKey(c.cardID)) 
					{
						cardList.add(c.makeCopy());
						cardMap.put(c.cardID, c.cardID);
					}
				}
			}
	
			if (DuelistMod.isDisciple)
			{
				for (AbstractCard c : DiscipleHelper.getAllCards())
				{
					if (cardMap.containsKey(c.cardID)) 
					{
						cardList.add(c.makeCopy());
						cardMap.put(c.cardID, c.cardID);
					}
				}
			}
	
			if (DuelistMod.isGatherer)
			{
				for (AbstractCard c : GathererHelper.getAllCards())
				{
					if (cardMap.containsKey(c.cardID)) 
					{
						cardList.add(c.makeCopy());
						cardMap.put(c.cardID, c.cardID);
					}
				}
			}
	
			if (DuelistMod.isHubris)
			{
				for (AbstractCard c : HubrisHelper.getAllCards())
				{
					if (cardMap.containsKey(c.cardID)) 
					{
						cardList.add(c.makeCopy());
						cardMap.put(c.cardID, c.cardID);
					}
				}
			}
	
			if (DuelistMod.isReplay)
			{
				for (AbstractCard c : ReplayHelper.getAllCards())
				{
					if (cardMap.containsKey(c.cardID)) 
					{
						cardList.add(c.makeCopy());
						cardMap.put(c.cardID, c.cardID);
					}
				}
			}
			
			DuelistMod.totallyRandomCardList.clear();
			DuelistMod.totallyRandomCardList.addAll(cardList);			
			AbstractCard returnable = cardList.get(AbstractDungeon.cardRandomRng.random(cardList.size() - 1));
			return returnable;
		}
	}
	
	public static DuelistCard returnRandomFromArray(ArrayList<DuelistCard> tributeList)
	{
		return tributeList.get(AbstractDungeon.cardRandomRng.random(tributeList.size() - 1));
	}

	public static AbstractCard returnRandomFromArrayAbstract(ArrayList<AbstractCard> tributeList)
	{
		return tributeList.get(AbstractDungeon.cardRandomRng.random(tributeList.size() - 1));
	}
	
	public static AbstractCard returnRandomMetronome()
	{
		ArrayList<AbstractCard> dragonGroup = new ArrayList<>();
		for (AbstractCard card : DuelistMod.metronomes)
		{
			if (card.hasTag(Tags.METRONOME)) 
			{
				dragonGroup.add(card.makeCopy());
			}
		}
		if (dragonGroup.size() > 0)
		{
			AbstractCard returnable = dragonGroup.get(AbstractDungeon.cardRandomRng.random(dragonGroup.size() - 1));
			return returnable;
		}
		else
		{
			return new Token();
		}
	}
	
	public static ArrayList<DuelistCard> invigorationFinder(int amtNeeded)
	{
		ArrayList<DuelistCard> insects = new ArrayList<>();
		for (AbstractCard c : TheDuelist.cardPool.group)
		{
			if (c.hasTag(Tags.INSECT) && !c.hasTag(Tags.NEVER_GENERATE) && allowResummonsWithExtraChecks(c))
			{
				insects.add((DuelistCard) c.makeCopy());
			}
		}
		
		if (insects.size() < amtNeeded)
		{
			for (AbstractCard c : DuelistMod.myCards)
			{
				if (c.hasTag(Tags.INSECT) && !c.hasTag(Tags.NEVER_GENERATE) && allowResummonsWithExtraChecks(c))
				{
					insects.add((DuelistCard) c.makeCopy());
				}
			}
			
			if (insects.size() > amtNeeded)
			{
				while (insects.size() > amtNeeded)
				{
					insects.remove(AbstractDungeon.cardRandomRng.random(insects.size() - 1));
				}
				
				return insects;
			}
			else
			{
				return insects;
			}
		}
		else if (insects.size() > amtNeeded)
		{
			while (insects.size() > amtNeeded)
			{
				insects.remove(AbstractDungeon.cardRandomRng.random(insects.size() - 1));
			}
			
			return insects;
		}
		else { return insects; }
	}
	
	public static ArrayList<AbstractCard> giantFinder(int amtNeeded)
	{
		ArrayList<AbstractCard> insects = new ArrayList<>();
		for (AbstractCard c : TheDuelist.cardPool.group)
		{
			if (c.hasTag(Tags.GIANT) && !c.hasTag(Tags.NEVER_GENERATE))
			{
				insects.add(c.makeCopy());
			}
		}
		
		if (insects.size() < amtNeeded)
		{
			for (AbstractCard c : DuelistMod.myCards)
			{
				if (c.hasTag(Tags.GIANT) && !c.hasTag(Tags.NEVER_GENERATE))
				{
					insects.add(c.makeCopy());
				}
			}
			
			if (insects.size() > amtNeeded)
			{
				while (insects.size() > amtNeeded)
				{
					insects.remove(AbstractDungeon.cardRandomRng.random(insects.size() - 1));
				}
				
				return insects;
			}
			else
			{
				return insects;
			}
		}
		else if (insects.size() > amtNeeded)
		{
			while (insects.size() > amtNeeded)
			{
				insects.remove(AbstractDungeon.cardRandomRng.random(insects.size() - 1));
			}
			
			return insects;
		}
		else { return insects; }
	}
	
	public static AbstractCard findWaterHazCard()
	{
		if (DuelistMod.waterHazardCards.size() > 0)
		{
			return DuelistMod.waterHazardCards.get(AbstractDungeon.cardRandomRng.random(DuelistMod.waterHazardCards.size() - 1));
		}
		else
		{
			return new Monokeros();
		}
	}
	
	public static ArrayList<AbstractCard> findAllOfTypeForResummon(CardTags tag, CardTags tagsB, CardRarity rare, int amtNeeded)
	{
		ArrayList<AbstractCard> insects = new ArrayList<>();
		for (AbstractCard c : TheDuelist.cardPool.group)
		{
			if (c.rarity.equals(rare) && (c.hasTag(tag) || tag == null) && (c.hasTag(tagsB) || tagsB == null) && !c.hasTag(Tags.NEVER_GENERATE) && allowResummonsWithExtraChecks(c))
			{
				insects.add(c.makeCopy());
			}
		}
		
		if (insects.size() < amtNeeded)
		{
			for (AbstractCard c : DuelistMod.myCards)
			{
				if (c.rarity.equals(rare) && (c.hasTag(tag) || tag == null) && (c.hasTag(tagsB) || tagsB == null) && !c.hasTag(Tags.NEVER_GENERATE) && allowResummonsWithExtraChecks(c))
				{
					insects.add(c.makeCopy());
				}
			}
			
			if (insects.size() > amtNeeded)
			{
				while (insects.size() > amtNeeded)
				{
					insects.remove(AbstractDungeon.cardRandomRng.random(insects.size() - 1));
				}
				
				return insects;
			}
			else
			{
				return insects;
			}
		}
		else if (insects.size() > amtNeeded)
		{
			while (insects.size() > amtNeeded)
			{
				insects.remove(AbstractDungeon.cardRandomRng.random(insects.size() - 1));
			}
			
			return insects;
		}
		else { return insects; }
	}
	
	public static ArrayList<AbstractCard> findAllOfTypeForResummon(CardTags tag, int amtNeeded)
	{
		return findAllOfTypeForResummon(tag, null, amtNeeded);
	}
	
	public static ArrayList<AbstractCard> findAllOfTypeForResummonWithDuplicates(CardTags tag, int amtNeeded)
	{
		ArrayList<AbstractCard> insects = new ArrayList<>();
		ArrayList<AbstractCard> toRet = new ArrayList<>();
		for (AbstractCard c : TheDuelist.cardPool.group)
		{
			if (c.hasTag(tag) && !c.hasTag(Tags.NEVER_GENERATE) && allowResummonsWithExtraChecks(c))
			{
				insects.add(c.makeCopy());
			}
		}
		
		if (insects.size() > 0)
		{
			while (toRet.size() < amtNeeded)
			{
				AbstractCard rand = insects.get(AbstractDungeon.cardRandomRng.random(insects.size() - 1));
				toRet.add(rand.makeCopy());
			}
			return toRet;
		}
		else
		{
			for (AbstractCard c : DuelistMod.myCards)
			{
				if (c.hasTag(tag) && !c.hasTag(Tags.NEVER_GENERATE) && allowResummonsWithExtraChecks(c))
				{
					insects.add(c.makeCopy());
				}
			}
			
			if (insects.size() > 0)
			{
				while (toRet.size() < amtNeeded)
				{
					AbstractCard rand = insects.get(AbstractDungeon.cardRandomRng.random(insects.size() - 1));
					toRet.add(rand.makeCopy());
				}
			}
			
			return toRet;
		}
	}
	
	public static ArrayList<AbstractCard> findAllOfTypeForResummonSpire(CardRarity rare, int amtNeeded, boolean colorless)
	{
		ArrayList<AbstractCard> insects = new ArrayList<>();
		for (AbstractCard c : BaseGameHelper.getAllBaseGameCards(colorless))
		{
			if ((c.rarity.equals(rare) || rare == null) && !c.hasTag(Tags.NEVER_GENERATE) && allowResummonsWithExtraChecks(c))
			{
				insects.add(c.makeCopy());
			}
		}
		
		if (insects.size() > amtNeeded)
		{
			while (insects.size() > amtNeeded)
			{
				insects.remove(AbstractDungeon.cardRandomRng.random(insects.size() - 1));
			}
		}
		
		return insects;
	}

	public static ArrayList<AbstractCard> findAllOfTypeForResummonMetronome(CardTags tag, CardTags tagsB, CardRarity rare, int amtNeeded)
	{
		ArrayList<AbstractCard> insects = new ArrayList<>();
		ArrayList<AbstractCard> excludedList = new ArrayList<>();
		for (AbstractCard c : TheDuelist.cardPool.group)
		{
			if (c.rarity.equals(rare) && (c.hasTag(tag) || tag == null) && (c.hasTag(tagsB) || tagsB == null) && !c.hasTag(Tags.NEVER_GENERATE) && allowResummonsWithExtraChecks(c))
			{
				boolean excluded = false;
				if (!(DuelistMod.toonBtnBool && c.hasTag(Tags.TOON_POOL))) {
					if (!(DuelistMod.ojamaBtnBool && c.hasTag(Tags.OJAMA))) {
						if (!(DuelistMod.exodiaBtnBool && c.hasTag(Tags.EXODIA))) {
							insects.add(c.makeCopy());
							excluded = true;
						}
					}

				}

				if (excluded) {
					excludedList.add(c.makeCopy());
				}
			}
		}

		if (insects.size() < amtNeeded)
		{
			for (AbstractCard c : DuelistMod.myCards)
			{
				if (c.rarity.equals(rare) && (c.hasTag(tag) || tag == null) && (c.hasTag(tagsB) || tagsB == null) && !c.hasTag(Tags.NEVER_GENERATE) && allowResummonsWithExtraChecks(c))
				{
					boolean excluded = false;
					if (!(DuelistMod.toonBtnBool && c.hasTag(Tags.TOON_POOL))) {
						if (!(DuelistMod.ojamaBtnBool && c.hasTag(Tags.OJAMA))) {
							if (!(DuelistMod.exodiaBtnBool && c.hasTag(Tags.EXODIA))) {
								insects.add(c.makeCopy());
								excluded = true;
							}
						}

					}

					if (excluded) {
						excludedList.add(c.makeCopy());
					}
				}
			}

			if (insects.size() > amtNeeded)
			{
				while (insects.size() > amtNeeded)
				{
					insects.remove(AbstractDungeon.cardRandomRng.random(insects.size() - 1));
				}

				return insects;
			}
			else if (insects.size() < 1 && excludedList.size() > 0) {
				return excludedList;
			}
			else
			{
				return insects;
			}
		}
		else if (insects.size() > amtNeeded)
		{
			while (insects.size() > amtNeeded)
			{
				insects.remove(AbstractDungeon.cardRandomRng.random(insects.size() - 1));
			}

			return insects;
		}
		else if (insects.size() < 1 && excludedList.size() > 0) { return excludedList; }
		else { return insects; }
	}

	public static ArrayList<AbstractCard> findAllOfTypeForResummonMetronome(CardTags tag, int amtNeeded)
	{
		return findAllOfTypeForResummonMetronome(tag, null, amtNeeded);
	}

	public static ArrayList<AbstractCard> findAllOfTypeForResummonMetronome(CardTags tag, CardTags tagsB, int amtNeeded)
	{
		ArrayList<AbstractCard> insects = new ArrayList<>();
		ArrayList<AbstractCard> excludedList = new ArrayList<>();
		for (AbstractCard c : TheDuelist.cardPool.group)
		{
			if ((c.hasTag(tag) || tag == null) && (c.hasTag(tagsB) || tagsB == null) && !c.hasTag(Tags.NEVER_GENERATE) && allowResummonsWithExtraChecks(c))
			{
				boolean excluded = false;
				if (!(DuelistMod.toonBtnBool && c.hasTag(Tags.TOON_POOL))) {
					if (!(DuelistMod.ojamaBtnBool && c.hasTag(Tags.OJAMA))) {
						if (!(DuelistMod.exodiaBtnBool && c.hasTag(Tags.EXODIA))) {
							insects.add(c.makeCopy());
							excluded = true;
						}
					}

				}

				if (excluded) {
					excludedList.add(c.makeCopy());
				}
			}
		}

		if (insects.size() < amtNeeded)
		{
			for (AbstractCard c : DuelistMod.myCards)
			{
				if ((c.hasTag(tag) || tag == null) && (c.hasTag(tagsB) || tagsB == null) && !c.hasTag(Tags.NEVER_GENERATE) && allowResummonsWithExtraChecks(c))
				{
					boolean excluded = false;
					if (!(DuelistMod.toonBtnBool && c.hasTag(Tags.TOON_POOL))) {
						if (!(DuelistMod.ojamaBtnBool && c.hasTag(Tags.OJAMA))) {
							if (!(DuelistMod.exodiaBtnBool && c.hasTag(Tags.EXODIA))) {
								insects.add(c.makeCopy());
								excluded = true;
							}
						}

					}

					if (excluded) {
						excludedList.add(c.makeCopy());
					}
				}
			}

			if (insects.size() > amtNeeded)
			{
				while (insects.size() > amtNeeded)
				{
					insects.remove(AbstractDungeon.cardRandomRng.random(insects.size() - 1));
				}

				return insects;
			}
			else if (insects.size() < 1 && excludedList.size() > 0)
			{
				return excludedList;
			}

			else {
				return insects;
			}
		}
		else if (insects.size() > amtNeeded)
		{
			while (insects.size() > amtNeeded)
			{
				insects.remove(AbstractDungeon.cardRandomRng.random(insects.size() - 1));
			}

			return insects;
		}
		else if (insects.size() < 1 && excludedList.size() > 0) { return excludedList; }
		else { return insects; }
	}
	
	public static ArrayList<AbstractCard> findAllOfTypeForResummon(CardTags tag, CardTags tagsB, int amtNeeded)
	{
		ArrayList<AbstractCard> insects = new ArrayList<>();
		for (AbstractCard c : TheDuelist.cardPool.group)
		{
			if ((c.hasTag(tag) || tag == null) && (c.hasTag(tagsB) || tagsB == null) && !c.hasTag(Tags.NEVER_GENERATE) && allowResummonsWithExtraChecks(c))
			{
				insects.add(c.makeCopy());
			}
		}
		
		if (insects.size() < amtNeeded)
		{
			for (AbstractCard c : DuelistMod.myCards)
			{
				if ((c.hasTag(tag) || tag == null) && (c.hasTag(tagsB) || tagsB == null) && !c.hasTag(Tags.NEVER_GENERATE) && allowResummonsWithExtraChecks(c))
				{
					insects.add(c.makeCopy());
				}
			}
			
			if (insects.size() > amtNeeded)
			{
				while (insects.size() > amtNeeded)
				{
					insects.remove(AbstractDungeon.cardRandomRng.random(insects.size() - 1));
				}
				
				return insects;
			}
			else
			{
				return insects;
			}
		}
		else if (insects.size() > amtNeeded)
		{
			while (insects.size() > amtNeeded)
			{
				insects.remove(AbstractDungeon.cardRandomRng.random(insects.size() - 1));
			}
			
			return insects;
		}
		else { return insects; }
	}

	public static ArrayList<AbstractCard> findAllOfCardTypeForResummon(CardType tag, int amtNeeded)
	{
		ArrayList<AbstractCard> insects = new ArrayList<>();
		for (AbstractCard c : TheDuelist.cardPool.group)
		{
			if (c.type.equals(tag) && !c.hasTag(Tags.NEVER_GENERATE) && allowResummonsWithExtraChecks(c))
			{
				insects.add(c.makeCopy());
			}
		}
		
		if (insects.size() < amtNeeded)
		{
			for (AbstractCard c : DuelistMod.myCards)
			{
				if (c.type.equals(tag) && !c.hasTag(Tags.NEVER_GENERATE) && allowResummonsWithExtraChecks(c))
				{
					insects.add(c.makeCopy());
				}
			}
			
			if (insects.size() > amtNeeded)
			{
				while (insects.size() > amtNeeded)
				{
					insects.remove(AbstractDungeon.cardRandomRng.random(insects.size() - 1));
				}
				
				return insects;
			}
			else
			{
				return insects;
			}
		}
		else if (insects.size() > amtNeeded)
		{
			while (insects.size() > amtNeeded)
			{
				insects.remove(AbstractDungeon.cardRandomRng.random(insects.size() - 1));
			}
			
			return insects;
		}
		else { return insects; }
	}
	
	public static ArrayList<AbstractCard> findAllOfCardTypeForResummonWithBlock(CardType tag, int amtNeeded)
	{
		ArrayList<AbstractCard> insects = new ArrayList<>();
		for (AbstractCard c : TheDuelist.cardPool.group)
		{
			if (c.type.equals(tag) && !c.hasTag(Tags.NEVER_GENERATE) && allowResummonsWithExtraChecks(c) && c.baseBlock > 0)
			{
				insects.add(c.makeCopy());
			}
		}
		
		if (insects.size() < amtNeeded)
		{
			for (AbstractCard c : DuelistMod.myCards)
			{
				if (c.type.equals(tag) && !c.hasTag(Tags.NEVER_GENERATE) && allowResummonsWithExtraChecks(c) && c.baseBlock > 0)
				{
					insects.add(c.makeCopy());
				}
			}
			
			if (insects.size() > amtNeeded)
			{
				while (insects.size() > amtNeeded)
				{
					insects.remove(AbstractDungeon.cardRandomRng.random(insects.size() - 1));
				}
				
				return insects;
			}
			else
			{
				return insects;
			}
		}
		else if (insects.size() > amtNeeded)
		{
			while (insects.size() > amtNeeded)
			{
				insects.remove(AbstractDungeon.cardRandomRng.random(insects.size() - 1));
			}
			
			return insects;
		}
		else { return insects; }
	}
	
	public static ArrayList<AbstractCard> findAllOfType(CardTags tag, int amtNeeded)
	{
		ArrayList<AbstractCard> insects = new ArrayList<>();
		for (AbstractCard c : TheDuelist.cardPool.group)
		{
			if (c.hasTag(tag) && !c.hasTag(Tags.NEVER_GENERATE))
			{
				insects.add(c.makeCopy());
			}
		}
		
		if (insects.size() < amtNeeded)
		{
			for (AbstractCard c : DuelistMod.myCards)
			{
				if (c.hasTag(tag) && !c.hasTag(Tags.NEVER_GENERATE))
				{
					insects.add(c.makeCopy());
				}
			}
			
			if (insects.size() > amtNeeded)
			{
				while (insects.size() > amtNeeded)
				{
					insects.remove(AbstractDungeon.cardRandomRng.random(insects.size() - 1));
				}
				
				return insects;
			}
			else
			{
				return insects;
			}
		}
		else if (insects.size() > amtNeeded)
		{
			while (insects.size() > amtNeeded)
			{
				insects.remove(AbstractDungeon.cardRandomRng.random(insects.size() - 1));
			}
			
			return insects;
		}
		else { return insects; }
	}
	
	public static ArrayList<AbstractCard> findAllOfType(CardType tag, int amtNeeded)
	{
		ArrayList<AbstractCard> insects = new ArrayList<>();
		for (AbstractCard c : TheDuelist.cardPool.group)
		{
			if (c.type.equals(tag) && !c.hasTag(Tags.NEVER_GENERATE))
			{
				insects.add(c.makeCopy());
			}
		}
		
		if (insects.size() < amtNeeded)
		{
			for (AbstractCard c : DuelistMod.myCards)
			{
				if (c.type.equals(tag) && !c.hasTag(Tags.NEVER_GENERATE))
				{
					insects.add(c.makeCopy());
				}
			}
			
			if (insects.size() > amtNeeded)
			{
				while (insects.size() > amtNeeded)
				{
					insects.remove(AbstractDungeon.cardRandomRng.random(insects.size() - 1));
				}
				
				return insects;
			}
			else
			{
				return insects;
			}
		}
		else if (insects.size() > amtNeeded)
		{
			while (insects.size() > amtNeeded)
			{
				insects.remove(AbstractDungeon.cardRandomRng.random(insects.size() - 1));
			}
			
			return insects;
		}
		else { return insects; }
	}
	
	public static ArrayList<AbstractCard> hundredMachines(CardRarity rarity, int amtNeeded)
	{
		ArrayList<AbstractCard> insects = new ArrayList<>();
		for (AbstractCard c : TheDuelist.cardPool.group)
		{
			if (c.hasTag(Tags.MACHINE) && !c.hasTag(Tags.NEVER_GENERATE) && c.rarity.equals(rarity))
			{
				insects.add(c.makeCopy());
			}
		}
		
		if (insects.size() < amtNeeded)
		{
			for (AbstractCard c : DuelistMod.myCards)
			{
				if (c.hasTag(Tags.MACHINE) && !c.hasTag(Tags.NEVER_GENERATE) && c.rarity.equals(rarity))
				{
					insects.add(c.makeCopy());
				}
			}
			
			if (insects.size() > amtNeeded)
			{
				while (insects.size() > amtNeeded)
				{
					insects.remove(AbstractDungeon.cardRandomRng.random(insects.size() - 1));
				}
				
				return insects;
			}
			else
			{
				return insects;
			}
		}
		else if (insects.size() > amtNeeded)
		{
			while (insects.size() > amtNeeded)
			{
				insects.remove(AbstractDungeon.cardRandomRng.random(insects.size() - 1));
			}
			
			return insects;
		}
		else { return insects; }
	}

	// Good function - written 11-12
	public static AbstractCard returnTrulyRandomFromSet(CardTags setToFindFrom) 
	{
		return returnTrulyRandomFromSet(setToFindFrom, true);
	}
	
	// Good function - written 11-12
	public static AbstractCard returnTrulyRandomFromType(CardType type, boolean basic) 
	{
		ArrayList<AbstractCard> dragonGroup = new ArrayList<>();
		for (AbstractCard card : TheDuelist.cardPool.group)
		{
			if (card.type.equals(type) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE)) 
			{
				dragonGroup.add(card.makeCopy());
			}
		}
		if (basic)
		{
			for (AbstractCard card : DuelistMod.duelColorlessCards)
			{
				if (card.type.equals(type) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE)) 
				{
					dragonGroup.add(card.makeCopy());
				}
			}
		}
		if (dragonGroup.size() > 0)
		{
			AbstractCard returnable = dragonGroup.get(AbstractDungeon.cardRandomRng.random(dragonGroup.size() - 1));
			return returnable;
		}
		else
		{
			for (DuelistCard card : DuelistMod.myCards)
			{
				if (card.type.equals(type) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE)) 
				{
					dragonGroup.add(card.makeCopy());
				}
			}
			if (dragonGroup.size() > 0)
			{
				AbstractCard returnable = dragonGroup.get(AbstractDungeon.cardRandomRng.random(dragonGroup.size() - 1));
				return returnable;
			}
			else
			{
				return new Token();
			}
		}
	}
	
	// Good function - written 11-12
	public static AbstractCard returnTrulyRandomFromTypeInCombat(CardType type, boolean basic) 
	{
		ArrayList<AbstractCard> dragonGroup = new ArrayList<>();
		for (AbstractCard card : TheDuelist.cardPool.group)
		{
			if (card.type.equals(type) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE)) 
			{
				dragonGroup.add(card.makeCopy());
			}
		}
		
		if (basic)
		{
			for (AbstractCard card : DuelistMod.duelColorlessCards)
			{
				if (card.type.equals(type) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE)) 
				{
					dragonGroup.add(card.makeCopy());
				}
			}
		}
		
		if (dragonGroup.size() > 0)
		{
			AbstractCard returnable = dragonGroup.get(AbstractDungeon.cardRandomRng.random(dragonGroup.size() - 1));
			return returnable;
		}
		else
		{
			for (AbstractCard card : DuelistMod.myCards)
			{
				if (card.type.equals(type) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE)) 
				{
					dragonGroup.add(card.makeCopy());
				}
			}
			if (dragonGroup.size() > 0)
			{
				AbstractCard returnable = dragonGroup.get(AbstractDungeon.cardRandomRng.random(dragonGroup.size() - 1));
				return returnable;
			}
			else { return new Token(); }
		}
	}
	
	// Good function - written 11-12
	public static AbstractCard returnTrulyRandomFromSet(CardTags setToFindFrom, boolean special) 
	{
		ArrayList<AbstractCard> dragonGroup = new ArrayList<>();
		for (AbstractCard c : TheDuelist.cardPool.group)
		{
			if (c.hasTag(setToFindFrom) && !c.hasTag(Tags.TOKEN) && !c.hasTag(Tags.NEVER_GENERATE)) { dragonGroup.add(c.makeCopy()); }
		}
		for (AbstractCard c : DuelistMod.duelColorlessCards)
		{
			if (c.hasTag(setToFindFrom) && !c.hasTag(Tags.TOKEN) && !c.hasTag(Tags.NEVER_GENERATE)) { dragonGroup.add(c.makeCopy()); }
		}
		if (dragonGroup.size() > 0)
		{
			AbstractCard returnable = dragonGroup.get(AbstractDungeon.cardRandomRng.random(dragonGroup.size() - 1));
			return returnable;
		}
		else
		{
			for (DuelistCard card : DuelistMod.myCards)
			{
				if (card.hasTag(setToFindFrom) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE)) 
				{
					if (special || !card.rarity.equals(CardRarity.SPECIAL))
					{
						dragonGroup.add(card.makeCopy());
					}				
				}
			}
			if (dragonGroup.size() > 0)
			{
				AbstractCard returnable = dragonGroup.get(ThreadLocalRandom.current().nextInt(0, dragonGroup.size()));
				while (returnable.hasTag(Tags.NEVER_GENERATE)) { returnable = dragonGroup.get(ThreadLocalRandom.current().nextInt(0, dragonGroup.size())); }
				return returnable;
			}
			else
			{
				return new Token();
			}
		}
	}
	
	// Monsterbox
	public static AbstractCard returnTrulyRandomFromSet(CardTags setToFindFrom, boolean special, boolean allowMegatype) 
	{
		ArrayList<AbstractCard> dragonGroup = new ArrayList<>();
		for (AbstractCard card : TheDuelist.cardPool.group)
		{
			if (card.hasTag(setToFindFrom) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE)) 
			{
				if (special || !card.rarity.equals(CardRarity.SPECIAL))
				{
					if (allowMegatype || !card.hasTag(Tags.MEGATYPED))
					{
						dragonGroup.add(card.makeCopy());
					}					
				}				
			}
		}
		for (AbstractCard card : DuelistMod.duelColorlessCards)
		{
			if (card.hasTag(setToFindFrom) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE)) 
			{
				if (special || !card.rarity.equals(CardRarity.SPECIAL))
				{
					if (allowMegatype || !card.hasTag(Tags.MEGATYPED))
					{
						dragonGroup.add(card.makeCopy());
					}					
				}				
			}
		}
		if (dragonGroup.size() > 0)
		{
			AbstractCard returnable = dragonGroup.get(AbstractDungeon.cardRandomRng.random(dragonGroup.size() - 1));
			return returnable;
		}
		else
		{
			for (DuelistCard card : DuelistMod.myCards)
			{
				if (card.hasTag(setToFindFrom) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE)) 
				{
					if (special || !card.rarity.equals(CardRarity.SPECIAL))
					{
						if (allowMegatype || !card.hasTag(Tags.MEGATYPED))
						{
							dragonGroup.add(card.makeCopy());
						}					
					}				
				}
			}
			if (dragonGroup.size() > 0)
			{
				AbstractCard returnable = dragonGroup.get(AbstractDungeon.cardRandomRng.random(dragonGroup.size() - 1));
				return returnable;
			}
			else
			{
				return new Token();
			}
		}
	}
	
	// Why slot machine do this
	public static AbstractCard slotMachineCard()
	{
		ArrayList<AbstractCard> arcane = new ArrayList<AbstractCard>();
		for (AbstractCard c : TheDuelist.cardPool.group)
		{
			if (c.hasTag(Tags.ARCANE) && !c.hasTag(Tags.NEVER_GENERATE)) {
				arcane.add(c.makeStatEquivalentCopy());
				Util.log("Slot Machine is adding " + c.name + " to pool of possible cards [cardPool]");
			}
		}
		
		if (arcane.size() > 0)
		{
			Util.log("Slot Machine found an Arcane card from the card pool. ArcaneSet size=" + arcane.size());
			int index = AbstractDungeon.cardRandomRng.random(arcane.size() - 1);
			Util.log("Index of random card=" + index + ", and card: " + arcane.get(index).name);
			return arcane.get(index).makeStatEquivalentCopy();
		}
		else
		{
			for (AbstractCard c : DuelistMod.coloredCards)
			{
				if (c.hasTag(Tags.ARCANE) && !c.hasTag(Tags.NEVER_GENERATE)) {
					arcane.add(c.makeStatEquivalentCopy());
					Util.log("Slot Machine is adding " + c.name + " to pool of possible cards [coloredCards]");
				}
			}
			
			if (arcane.size() > 0)
			{
				Util.log("Slot Machine found an Arcane card from coloredCards");
				return arcane.get(AbstractDungeon.cardRandomRng.random(arcane.size() - 1)).makeStatEquivalentCopy();
			}
			else
			{
				for (AbstractCard c : DuelistMod.myCards)
				{
					if (c.hasTag(Tags.ARCANE) && !c.hasTag(Tags.NEVER_GENERATE)) {
						arcane.add(c.makeStatEquivalentCopy());
						Util.log("Slot Machine is adding " + c.name + " to pool of possible cards [myCards]");
					}
				}
				
				if (arcane.size() > 0)
				{
					Util.log("Slot Machine found an Arcane card from myCards");
					return arcane.get(AbstractDungeon.cardRandomRng.random(arcane.size() - 1)).makeStatEquivalentCopy();
				}
				else
				{
					return new Token();
				}
			}
		}
	}

	// Good function - written 11-12-19
	public static AbstractCard returnTrulyRandomInCombatFromSet(CardTags setToFindFrom, boolean basic) 
	{
		if (AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST))
		{
			ArrayList<AbstractCard> choices = new ArrayList<>();
			for (AbstractCard c : TheDuelist.cardPool.group) { if (c.hasTag(setToFindFrom) && !c.hasTag(Tags.NEVER_GENERATE)) { choices.add(c.makeStatEquivalentCopy()); }}
			if (basic && DuelistMod.duelColorlessCards.size() > 0) { for (AbstractCard c : DuelistMod.duelColorlessCards) { if (c.hasTag(setToFindFrom) && !c.hasTag(Tags.NEVER_GENERATE)) { choices.add(c.makeStatEquivalentCopy()); }}}
			if (choices.size() > 0)
			{
				AbstractCard c = choices.get(AbstractDungeon.cardRandomRng.random(choices.size() - 1));
				return c;
			}
			else 
			{
				choices = new ArrayList<>();
				for (AbstractCard c : DuelistMod.myCards) { if (c.hasTag(setToFindFrom) && !c.hasTag(Tags.NEVER_GENERATE)) { choices.add(c.makeStatEquivalentCopy()); }}
				if (basic && DuelistMod.duelColorlessCards.size() > 0) { for (AbstractCard c : DuelistMod.duelColorlessCards) { if (c.hasTag(setToFindFrom) && !c.hasTag(Tags.NEVER_GENERATE)) { choices.add(c.makeStatEquivalentCopy()); }}}
				if (choices.size() > 0)
				{
					AbstractCard c = choices.get(AbstractDungeon.cardRandomRng.random(choices.size() - 1));
					return c;
				}
				else
				{
					return new Token();
				}
			}
		}
		else
		{
			return returnTrulyRandomFromSet(setToFindFrom);
		}
	}
	
	
	// Good function - written 11-12-19
	public static AbstractCard returnTrulyRandomInCombatFromType(CardType setToFindFrom, boolean basic) 
	{
		if (AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST))
		{
			ArrayList<AbstractCard> choices = new ArrayList<>();
			for (AbstractCard c : TheDuelist.cardPool.group) { if (c.type.equals(setToFindFrom) && !c.hasTag(Tags.NEVER_GENERATE)) { choices.add(c.makeStatEquivalentCopy()); }}
			if (basic && DuelistMod.duelColorlessCards.size() > 0) { for (AbstractCard c : DuelistMod.duelColorlessCards) { if (c.type.equals(setToFindFrom) && !c.hasTag(Tags.NEVER_GENERATE)) { choices.add(c.makeStatEquivalentCopy()); }}}
			if (choices.size() > 0)
			{
				AbstractCard c = choices.get(AbstractDungeon.cardRandomRng.random(choices.size() - 1));
				return c;
			}
			else 
			{
				choices = new ArrayList<>();
				for (AbstractCard c : DuelistMod.myCards) { if (c.type.equals(setToFindFrom) && !c.hasTag(Tags.NEVER_GENERATE)) { choices.add(c.makeStatEquivalentCopy()); }}
				if (basic && DuelistMod.duelColorlessCards.size() > 0) { for (AbstractCard c : DuelistMod.duelColorlessCards) { if (c.type.equals(setToFindFrom) && !c.hasTag(Tags.NEVER_GENERATE)) { choices.add(c.makeStatEquivalentCopy()); }}}
				if (choices.size() > 0)
				{
					AbstractCard c = choices.get(AbstractDungeon.cardRandomRng.random(choices.size() - 1));
					return c;
				}
				else
				{
					return new Token();
				}
			}
		}
		else
		{
			return returnTrulyRandomFromType(setToFindFrom, basic);
		}
	}
	
	// Leave unseeded - checked 11-12
	public static AbstractCard returnTrulyRandomDuelistCardForRandomDecks() 
	{
		ArrayList<AbstractCard> dragonGroup = new ArrayList<>();
		for (AbstractCard card : DuelistMod.cardsForRandomDecks)
		{
			if (card instanceof DuelistCard && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE)) 
			{
				dragonGroup.add(card.makeCopy());
			}
		}
		if (dragonGroup.size() > 0)
		{
			AbstractCard returnable = dragonGroup.get(ThreadLocalRandom.current().nextInt(0, dragonGroup.size()));
			return returnable;
		}
		else
		{
			return new Token();
		}
	}

	public static AbstractCard returnTrulyRandomDuelistCard() 
	{
		return returnTrulyRandomDuelistCard(false, false);
	}

	public static AbstractCard returnTrulyRandomDuelistCard(boolean allowSpecial, boolean allowBasic) 
	{
		ArrayList<AbstractCard> tmp = returnTrulyRandomDuelistCard(allowSpecial, allowBasic, 1);
		if (tmp.size() > 0) { return tmp.get(AbstractDungeon.cardRandomRng.random(tmp.size() - 1)); }
		else
		{
			return new CancelCard();
		}
	}
	
	public static ArrayList<AbstractCard> returnTrulyRandomDuelistCard(boolean allowSpecial, boolean allowBasic, int amtNeeded) 
	{
		ArrayList<AbstractCard> dragonGroup = new ArrayList<>();
		for (DuelistCard card : DuelistMod.myCards)
		{
			if (card instanceof DuelistCard && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE)) 
			{
				if (allowSpecial || !card.rarity.equals(CardRarity.SPECIAL))
				{
					if (allowBasic || !card.rarity.equals(CardRarity.BASIC)) 
					{
						dragonGroup.add(card.makeCopy());
					}
				}
			}
		}
		
		while (dragonGroup.size() > amtNeeded)
		{
			dragonGroup.remove(AbstractDungeon.cardRandomRng.random(dragonGroup.size() - 1));
		}
		
		return dragonGroup;
	}
	
	public void metronomeAction()
	{
		AbstractMonster m = AbstractDungeon.getRandomMonster();
		if (m != null) {
			metronomeAction(m, 1, false);
		}		
	}
	
	public void metronomeAction(AbstractMonster m)
	{
		metronomeAction(m, 1, false);			
	}

	public void metronomeAction(int copies)
	{
		AbstractMonster m = AbstractDungeon.getRandomMonster();
		if (m != null) {
			metronomeAction(m, copies, false);
		}		
	}

	public void metronomeAction(int copies, boolean allowExempt)
	{
		AbstractMonster m = AbstractDungeon.getRandomMonster();
		if (m != null) {
			metronomeAction(m, copies, allowExempt);
		}		
	}
	
	public void metronomeAction(AbstractMonster m, int copies)
	{
		metronomeAction(m, copies, false);
	}

	public void metronomeAction(AbstractMonster m, boolean allowExempt)
	{
		metronomeAction(m, 1, allowExempt);
	}

	public void metronomeAction(AbstractMonster m, int copies, boolean allowExempt)
	{
		if (this instanceof MetronomeCard)
		{
			for (AbstractCard c : getMetronomeResummon((MetronomeCard)this))
			{				
				if (this.target.equals(CardTarget.ALL_ENEMY)) {
					if (!(c instanceof CancelCard)) 
					{ 
						resummonOnAll(c, copies, this.upgraded, allowExempt); 
						for (int i = 0; i < copies; i++) { 
							DuelistMod.metronomeResummonsThisCombat.add(c.makeStatEquivalentCopy());
						}
					}
				}
				else {
					if (!(c instanceof CancelCard)) 
					{ 
						resummon(c, m, copies, this.upgraded, allowExempt); 
						for (int i = 0; i < copies; i++) { 
							DuelistMod.metronomeResummonsThisCombat.add(c.makeStatEquivalentCopy());
						}
					}
				}				
			}
		}
	}
	
	public static ArrayList<AbstractCard> getMetronomeResummon(MetronomeCard card)
	{
		if (card.returnsMultiple)
		{
			return card.returnCards();
		}
		else
		{
			ArrayList<AbstractCard> list = new ArrayList<>();
			for (int i = 0; i < card.magicNumber; i++)
			{
				AbstractCard res = card.returnCard();
				if (!(res instanceof CancelCard)) { list.add(res); }
			}
			return list;
		}
	}
	
	public static AbstractCard returnTrulyRandomDuelistCardInCombat() 
	{
		if (AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST))
		{
			AbstractCard c = TheDuelist.cardPool.group.get(AbstractDungeon.cardRandomRng.random(TheDuelist.cardPool.group.size() - 1));
			while (!(c instanceof DuelistCard) || c.hasTag(Tags.NEVER_GENERATE)) { c = TheDuelist.cardPool.group.get(AbstractDungeon.cardRandomRng.random(TheDuelist.cardPool.group.size() - 1)); }
			return c;
		}
		else
		{
			return returnTrulyRandomDuelistCard();
		}
	}

	public static AbstractCard returnTrulyRandomFromSetsFilterMegatype(CardTags setToFindFrom, CardTags anotherSetToFindFrom, boolean allowMegatype) {
		if (!allowMegatype) {
			ArrayList<AbstractCard> dragonGroup = new ArrayList<>();
			for (AbstractCard card : TheDuelist.cardPool.group)
			{
				if (card.hasTag(setToFindFrom) && card.hasTag(anotherSetToFindFrom) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE) && !card.hasTag(Tags.MEGATYPED))
				{
					dragonGroup.add(card.makeCopy());
				}
			}
			for (AbstractCard card : DuelistMod.duelColorlessCards)
			{
				if (card.hasTag(setToFindFrom) && card.hasTag(anotherSetToFindFrom) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE) && !card.hasTag(Tags.MEGATYPED))
				{
					dragonGroup.add(card.makeCopy());
				}
			}
			if (dragonGroup.size() > 0)
			{
				AbstractCard returnable = dragonGroup.get(AbstractDungeon.cardRandomRng.random(dragonGroup.size() - 1));
				return returnable;
			}
			else
			{
				for (DuelistCard card : DuelistMod.myCards)
				{
					if (card.hasTag(setToFindFrom) && card.hasTag(anotherSetToFindFrom) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE) && !card.hasTag(Tags.MEGATYPED))
					{
						dragonGroup.add(card.makeCopy());
					}
				}
				if (dragonGroup.size() > 0)
				{
					AbstractCard returnable = dragonGroup.get(AbstractDungeon.cardRandomRng.random(dragonGroup.size() - 1));
					return returnable;
				}
				else
				{
					return new Token();
				}
			}
		} else {
			return returnTrulyRandomFromSets(setToFindFrom, anotherSetToFindFrom);
		}
	}

	// Good function - written 11-12
	public static AbstractCard returnTrulyRandomFromSets(CardTags setToFindFrom, CardTags anotherSetToFindFrom) 
	{
		ArrayList<AbstractCard> dragonGroup = new ArrayList<>();
		for (AbstractCard card : TheDuelist.cardPool.group)
		{
			if (card.hasTag(setToFindFrom) && card.hasTag(anotherSetToFindFrom) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE)) 
			{
				dragonGroup.add(card.makeCopy());
			}
		}
		for (AbstractCard card : DuelistMod.duelColorlessCards)
		{
			if (card.hasTag(setToFindFrom) && card.hasTag(anotherSetToFindFrom) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE)) 
			{
				dragonGroup.add(card.makeCopy());
			}
		}
		if (dragonGroup.size() > 0)
		{
			AbstractCard returnable = dragonGroup.get(AbstractDungeon.cardRandomRng.random(dragonGroup.size() - 1));
			return returnable;
		}
		else
		{
			for (DuelistCard card : DuelistMod.myCards)
			{
				if (card.hasTag(setToFindFrom) && card.hasTag(anotherSetToFindFrom) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE)) 
				{
					dragonGroup.add(card.makeCopy());
				}
			}
			if (dragonGroup.size() > 0)
			{
				AbstractCard returnable = dragonGroup.get(AbstractDungeon.cardRandomRng.random(dragonGroup.size() - 1));
				return returnable;
			}
			else
			{
				return new Token();
			}
		}
	}
	
	// Good function - written 11-12
	public AbstractCard cyberDragonCoreRandom() 
	{
		ArrayList<AbstractCard> dragonGroup = new ArrayList<>();
		for (AbstractCard card : TheDuelist.cardPool.group)
		{
			if (card.hasTag(Tags.MACHINE) && card.hasTag(Tags.DRAGON) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE) && !card.rarity.equals(CardRarity.SPECIAL) && !card.color.equals(AbstractCardEnum.DUELIST_SPECIAL)) 
			{
				dragonGroup.add(card.makeCopy());
			}
		}
		for (AbstractCard card : DuelistMod.duelColorlessCards)
		{
			if (card.hasTag(Tags.MACHINE) && card.hasTag(Tags.DRAGON) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE) && !card.rarity.equals(CardRarity.SPECIAL) && !card.color.equals(AbstractCardEnum.DUELIST_SPECIAL)) 
			{
				dragonGroup.add(card.makeCopy());
			}
		}
		if (dragonGroup.size() > 0)
		{
			AbstractCard returnable = dragonGroup.get(AbstractDungeon.cardRandomRng.random(dragonGroup.size() - 1));
			return returnable;
		}
		else
		{
			for (DuelistCard card : DuelistMod.myCards)
			{
				if (card.hasTag(Tags.MACHINE) && card.hasTag(Tags.DRAGON) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE) && !card.rarity.equals(CardRarity.SPECIAL) && !card.color.equals(AbstractCardEnum.DUELIST_SPECIAL)) 
				{
					dragonGroup.add(card.makeCopy());
				}
			}
			if (dragonGroup.size() > 0)
			{
				AbstractCard returnable = dragonGroup.get(AbstractDungeon.cardRandomRng.random(dragonGroup.size() - 1));
				return returnable;
			}
			else
			{
				return new Token();
			}
		}		
	}

	// Good function - written 11-12
	public static AbstractCard returnTrulyRandomFromEitherSet(CardTags setToFindFrom, CardTags anotherSetToFindFrom) 
	{
		ArrayList<AbstractCard> dragonGroup = new ArrayList<>();
		for (AbstractCard card : TheDuelist.cardPool.group)
		{
			if ((card.hasTag(setToFindFrom) || card.hasTag(anotherSetToFindFrom)) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE)) 
			{
				dragonGroup.add(card.makeCopy());
			}
		}
		for (AbstractCard card : DuelistMod.duelColorlessCards)
		{
			if ((card.hasTag(setToFindFrom) || card.hasTag(anotherSetToFindFrom)) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE)) 
			{
				dragonGroup.add(card.makeCopy());
			}
		}
		if (dragonGroup.size() > 0)
		{
			AbstractCard returnable = dragonGroup.get(AbstractDungeon.cardRandomRng.random(dragonGroup.size() - 1));
			return returnable;
		}
		else
		{
			for (DuelistCard card : DuelistMod.myCards)
			{
				if ((card.hasTag(setToFindFrom) || card.hasTag(anotherSetToFindFrom)) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE)) 
				{
					dragonGroup.add(card.makeCopy());
				}
			}
			if (dragonGroup.size() > 0)
			{
				AbstractCard returnable = dragonGroup.get(AbstractDungeon.cardRandomRng.random(dragonGroup.size() - 1));
				return returnable;
			}
			else
			{
				return new Token();
			}
		}
	}

	// Good function - written 11-12
	public static AbstractCard returnTrulyRandomFromOnlyFirstSet(CardTags setToFindFrom, CardTags excludeSet) 
	{
		ArrayList<AbstractCard> dragonGroup = new ArrayList<>();
		for (AbstractCard card : TheDuelist.cardPool.group)
		{
			if (card.hasTag(setToFindFrom) && !card.hasTag(excludeSet) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE) && !card.rarity.equals(CardRarity.SPECIAL) && !card.color.equals(AbstractCardEnum.DUELIST_SPECIAL)) 
			{
				dragonGroup.add(card.makeCopy());
			}
		}
		for (AbstractCard card : DuelistMod.duelColorlessCards)
		{
			if (card.hasTag(setToFindFrom) && !card.hasTag(excludeSet) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE) && !card.rarity.equals(CardRarity.SPECIAL) && !card.color.equals(AbstractCardEnum.DUELIST_SPECIAL)) 
			{
				dragonGroup.add(card.makeCopy());
			}
		}
		if (dragonGroup.size() > 0)
		{
			AbstractCard returnable = dragonGroup.get(AbstractDungeon.cardRandomRng.random(dragonGroup.size() - 1));
			return returnable;
		}
		else
		{
			for (DuelistCard card : DuelistMod.myCards)
			{
				if (card.hasTag(setToFindFrom) && !card.hasTag(excludeSet) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE) && !card.rarity.equals(CardRarity.SPECIAL) && !card.color.equals(AbstractCardEnum.DUELIST_SPECIAL)) 
				{
					dragonGroup.add(card.makeCopy());
				}
			}
			if (dragonGroup.size() > 0)
			{
				AbstractCard returnable = dragonGroup.get(AbstractDungeon.cardRandomRng.random(dragonGroup.size() - 1));
				return returnable;
			}
			else
			{
				return new Token();
			}
		}		
	}

	public static DuelistCard newCopyOfMonster(String name)
	{
		DuelistCard find = DuelistMod.summonMap.get(name);
		if (find != null) { return (DuelistCard) find.makeCopy(); }
		else { return new Token(); }
	}

	// =============== /RANDOM CARD FUNCTIONS/ =======================================================================================================================================================

	// =============== TYPE CARD FUNCTIONS =========================================================================================================================================================
	public static String generateTypeDescForRelics(CardTags tag, AbstractRelic callingRelic)
	{
		String res = "";
		String tagString = tag.toString().toLowerCase();
		String temp = tagString.substring(0, 1).toUpperCase();
		tagString = temp + tagString.substring(1);
		boolean useAN = false;
		if (tagString.equals("Aqua") || tagString.equals("Insect") || tagString.equals("Arcane") || tagString.equals("Ojama")) { useAN = true; }
		if (callingRelic instanceof Monsterbox)
		{			
			res = "Replace ALL monsters in your deck with " + tagString + " monsters.";			
		}
		
		if (callingRelic instanceof MillenniumPrayerbook)
		{
			if (useAN) { res = "At the start of each turn, add an " + tagString + " card to your hand."; }
			else { res = "At the start of each turn, add a " + tagString + " card to your hand."; }
		}
		
		return res;
	}
	
	public static String generateTypeDescForRelics(CardType tag, AbstractRelic callingRelic)
	{
		String res = "";
		String tagString = "";
		if (tag.equals(CardType.ATTACK)) { tagString = "an Attack "; }
		else if (tag.equals(CardType.SKILL)) { tagString = "a Skill "; }
		else { tagString = "a Power "; } 

		if (callingRelic instanceof MillenniumPrayerbook)
		{
			res = "At the start of each turn, add " + tagString + " card to your hand.";	
		}
		
		return res;
	}
	
	public static String generateTypeCardDescForRelic(int magic, CardTags tag, DuelistCard card)
	{
		String res = "";
		String tagString = tag.toString().toLowerCase();
		String temp = tagString.substring(0, 1).toUpperCase();
		tagString = temp + tagString.substring(1);
		boolean useAN = false;
		
		if (tagString.equals("Aqua") || tagString.equals("Insect") || tagString.equals("Arcane") || tagString.equals("Ojama")) { useAN = true; }
		if (card instanceof DarkCrusader)
		{
			if (magic != 1) { res = "Summon " + magic + " " + tagString + " Tokens"; }
			else { res = "Summon " + magic + " " + tagString + " Token"; }
		}
		
		if (card instanceof ShardGreed)
		{
			if (useAN)
			{
				if (magic < 2) { res = "At the start of turn, draw an " + tagString + " card."; }
				else { res = "At the start of turn, draw " + magic + " " + tagString + " cards."; }
			}
			else
			{
				if (magic < 2) { res = "At the start of turn, draw a " + tagString + " card."; }
				else { res = "At the start of turn, draw " + magic + " " + tagString + " cards."; }
			}			
		}
		
		if (card instanceof RockSunrise)
		{
			res = tagString + " cards deal an additional 25% damage for the rest of combat.";
		}
		
		if (card instanceof RainbowJar)
		{
			res = Strings.configRainbowJarA + tagString + Strings.configRainbowJarB;
		}
		
		if (card instanceof WingedKuriboh9 || card instanceof WingedKuriboh10)
		{
			if (magic < 2) { res = Strings.configWingedTextA + magic + " " + tagString + Strings.configGreedShardB; }
			else { res = Strings.configWingedTextA + magic + " " + tagString + Strings.configWingedTextB; }
		}
		
		if (card instanceof YamiForm)
		{
			 res = Strings.configYamiFormA + tagString + Strings.configYamiFormB;
		}
		
		if (card instanceof RainbowGravity)
		{
			res = Strings.configRainbow + tagString + Strings.configRainbowB;
		}
		
		if (card instanceof TributeToken)
		{
			res = "Tribute a monster with a monster of " + tagString + " type.";
		}
		
		if (card instanceof SummonToken)
		{
			res = "Summon a random " + tagString + " monster.";
		}
		
		return res;
	}
	
	public String generateDynamicTypeCardDesc(int magic, CardTags tag)
	{
		String res = "";
		String tagString = tag.toString().toLowerCase();
		String temp = tagString.substring(0, 1).toUpperCase();
		tagString = temp + tagString.substring(1);
		boolean useAN = false;
		
		if (tagString.equals("Aqua") || tagString.equals("Insect") || tagString.equals("Arcane") || tagString.equals("Ojama")) { useAN = true; }
		if (this instanceof DarkCrusader)
		{
			if (magic != 1) { res = "Summon " + magic + " " + tagString + " Tokens"; }
			else { res = "Summon " + magic + " " + tagString + " Token"; }
		}
		
		if (this instanceof ShardGreed)
		{
			if (useAN)
			{
				if (magic < 2) { res = "At the start of turn, draw an " + tagString + " card."; }
				else { res = "At the start of turn, draw " + magic + " " + tagString + " cards."; }
			}
			else
			{
				if (magic < 2) { res = "At the start of turn, draw a " + tagString + " card."; }
				else { res = "At the start of turn, draw " + magic + " " + tagString + " cards."; }
			}			
		}
		
		if (this instanceof RockSunrise)
		{
			res = tagString + " cards deal an additional 25% damage for the rest of combat.";
		}
		
		if (this instanceof RainbowJar)
		{
			res = Strings.configRainbowJarA + tagString + Strings.configRainbowJarB;
		}
		
		if (this instanceof WingedKuriboh9 || this instanceof WingedKuriboh10)
		{
			if (magic < 2) { res = Strings.configWingedTextA + magic + " " + tagString + Strings.configGreedShardB; }
			else { res = Strings.configWingedTextA + magic + " " + tagString + Strings.configWingedTextB; }
		}
		
		if (this instanceof YamiForm)
		{
			 res = Strings.configYamiFormA + tagString + Strings.configYamiFormB;
		}
		
		if (this instanceof RainbowGravity)
		{
			res = Strings.configRainbow + tagString + Strings.configRainbowB;
		}
		
		if (this instanceof TributeToken)
		{
			res = "Tribute a monster with a monster of " + tagString + " type.";
		}
		
		if (this instanceof SummonToken)
		{
			res = "Summon a random " + tagString + " monster.";
		}
		
		return res;
	}
	
	public static String generateDynamicTypeCardDesc(int magic, CardTags tag, DuelistCard callingCard, int randomTypes)
	{
		String res = "";
		String tagString = tag.toString().toLowerCase();
		String temp = tagString.substring(0, 1).toUpperCase();
		tagString = temp + tagString.substring(1);
		boolean useAN = false;
		
		if (tagString.equals("Aqua") || tagString.equals("Insect") || tagString.equals("Arcane")) { useAN = true; }
		
		if (callingCard instanceof ShardGreed)
		{
			if (useAN)
			{
				if (magic < 2) { res = "At the start of turn, draw an " + tagString + " card."; }
				else { res = "At the start of turn, draw " + magic + " " + tagString + " cards."; }
			}
			else
			{
				if (magic < 2) { res = "At the start of turn, draw a " + tagString + " card."; }
				else { res = "At the start of turn, draw " + magic + " " + tagString + " cards."; }
			}			
		}
		
		if (callingCard instanceof RainbowJar)
		{
			res = Strings.configRainbowJarA + tagString + Strings.configRainbowJarB;
		}
		
		if (callingCard instanceof WingedKuriboh9 || callingCard instanceof WingedKuriboh10)
		{
			if (magic < 2) { res = Strings.configWingedTextA + magic + " " + tagString + Strings.configGreedShardB; }
			else { res = Strings.configWingedTextA + magic + " " + tagString + Strings.configWingedTextB; }
		}
		
		if (callingCard instanceof YamiForm)
		{
			 res = Strings.configYamiFormA + tagString + Strings.configYamiFormB;
		}
		
		return res;
	}
	
	public ArrayList<DuelistCard> generateTypeCards(int magic)
	{
		return generateTypeCards(magic, false);
	}
	
	public static ArrayList<DuelistCard> generateTypeCardsForMonsterbox(AbstractRelic callingRelic, int magic, boolean limitTypes, int types)
	{
		ArrayList<DuelistCard> typeCards = new ArrayList<DuelistCard>();
		ArrayList<CardTags> randomThreeTags = new ArrayList<CardTags>();
		ArrayList<CardTags> allTags = new ArrayList<CardTags>();	
		if (limitTypes)
		{
			if (types > DuelistMod.monsterTypes.size() + 2) { randomThreeTags.addAll(DuelistMod.monsterTypes); randomThreeTags.add(Tags.ROSE); randomThreeTags.add(Tags.OJAMA); }
			else 
			{
				allTags.addAll(DuelistMod.monsterTypes);
				allTags.add(Tags.ROSE);
				allTags.add(Tags.OJAMA);
				while (randomThreeTags.size() < types)
				{
					int ind = AbstractDungeon.cardRandomRng.random(allTags.size() - 1);
					CardTags rand = allTags.get(ind);
					randomThreeTags.add(rand);
					allTags.remove(ind);
				}
			}
			
			for (CardTags t : randomThreeTags)
			{
				typeCards.add(new DynamicRelicTagCard(DuelistMod.typeCardMap_ID.get(t), DuelistMod.typeCardMap_NAME.get(t), DuelistMod.typeCardMap_IMG.get(t), generateTypeDescForRelics(t, callingRelic), t, callingRelic, magic));
			}
		}
		else
		{
			for (CardTags t : DuelistMod.monsterTypes)
			{
				typeCards.add(new DynamicRelicTagCard(DuelistMod.typeCardMap_ID.get(t), DuelistMod.typeCardMap_NAME.get(t), DuelistMod.typeCardMap_IMG.get(t), generateTypeDescForRelics(t, callingRelic), t, callingRelic, magic));
			}
			
			ArrayList<CardTags> extraTags = new ArrayList<CardTags>();
			extraTags.add(Tags.ROSE);		
			extraTags.add(Tags.OJAMA);
			for (CardTags t : extraTags)
			{
				typeCards.add(new DynamicRelicTagCard(DuelistMod.typeCardMap_ID.get(t), DuelistMod.typeCardMap_NAME.get(t), DuelistMod.typeCardMap_IMG.get(t), generateTypeDescForRelics(t, callingRelic), t, callingRelic, magic));
			}
		}		
		return typeCards;
	}
	
	public static ArrayList<DuelistCard> generateTypeCardsForPrayerbook(AbstractRelic callingRelic, ArrayList<CardTags> tags, ArrayList<CardType> types, int magic)
	{
		ArrayList<DuelistCard> typeCards = new ArrayList<DuelistCard>();
		for (CardTags t : tags) 
		{ 
			DuelistCard tagCard = new DynamicRelicTagCard(DuelistMod.typeCardMap_ID.get(t), DuelistMod.typeCardMap_NAME.get(t), DuelistMod.typeCardMap_IMG.get(t), generateTypeDescForRelics(t, callingRelic), t, callingRelic, magic);
			typeCards.add(tagCard); 
		}
		
		for (CardType t : types)
		{
			String type = "";
			String imgPath = "";
			if (t.equals(CardType.ATTACK)) 
			{ 
				type = "Attack"; 
				imgPath = DuelistMod.makeCardPath("MillenniumPrayerbookCardAttack.png");
				
			}
			else if (t.equals(CardType.SKILL)) 
			{ 
				type = "Skill"; 
				imgPath = DuelistMod.makeCardPath("MillenniumPrayerbookCard.png");
			}
			else 
			{ 
				type = "Power"; 
				imgPath = DuelistMod.makeCardPath("MillenniumSpellbook.png");
			}
			DuelistCard tagCard = new DynamicRelicTypeCard("theDuelist:MillenniumPrayerbook" + type, type, imgPath, generateTypeDescForRelics(t, callingRelic), t, callingRelic, magic);
			typeCards.add(tagCard); 
		}
		return typeCards;
	}
	
	public static ArrayList<DuelistCard> generateTypeForRelic(int magic, boolean customDesc, DuelistCard typeFunctionCardToRun)
	{
		ArrayList<DuelistCard> typeCards = new ArrayList<DuelistCard>();
		for (CardTags t : DuelistMod.monsterTypes)
		{
			if (customDesc) { typeCards.add(new DynamicTypeCard(DuelistMod.typeCardMap_ID.get(t), DuelistMod.typeCardMap_NAME.get(t), DuelistMod.typeCardMap_IMG.get(t), generateTypeCardDescForRelic(magic, t, typeFunctionCardToRun), t, typeFunctionCardToRun, magic)); }
			else { typeCards.add(new DynamicTypeCard(DuelistMod.typeCardMap_ID.get(t), DuelistMod.typeCardMap_NAME.get(t), DuelistMod.typeCardMap_IMG.get(t), DuelistMod.typeCardMap_DESC.get(t), t, typeFunctionCardToRun, magic)); }
		}
		
		ArrayList<CardTags> extraTags = new ArrayList<CardTags>();
		extraTags.add(Tags.ROSE);		
		boolean isTok = typeFunctionCardToRun instanceof TributeToken || typeFunctionCardToRun instanceof SummonToken;
		if (!isTok) { extraTags.add(Tags.MEGATYPED); }
		extraTags.add(Tags.OJAMA);	
		extraTags.add(Tags.GIANT);
		extraTags.add(Tags.MAGNET);
		for (CardTags t : extraTags)
		{
			if (customDesc) { typeCards.add(new DynamicTypeCard(DuelistMod.typeCardMap_ID.get(t), DuelistMod.typeCardMap_NAME.get(t), DuelistMod.typeCardMap_IMG.get(t), generateTypeCardDescForRelic(magic, t, typeFunctionCardToRun), t, typeFunctionCardToRun, magic)); }
			else { typeCards.add(new DynamicTypeCard(DuelistMod.typeCardMap_ID.get(t), DuelistMod.typeCardMap_NAME.get(t), DuelistMod.typeCardMap_IMG.get(t), DuelistMod.typeCardMap_DESC.get(t), t, typeFunctionCardToRun, magic)); }
			
		}
		return typeCards;
	}
	
	public ArrayList<DuelistCard> generateTypeCards(int magic, boolean customDesc)
	{
		ArrayList<DuelistCard> typeCards = new ArrayList<DuelistCard>();
		for (CardTags t : DuelistMod.monsterTypes)
		{
			if (customDesc) { typeCards.add(new DynamicTypeCard(DuelistMod.typeCardMap_ID.get(t), DuelistMod.typeCardMap_NAME.get(t), DuelistMod.typeCardMap_IMG.get(t), generateDynamicTypeCardDesc(magic, t), t, this, magic)); }
			else { typeCards.add(new DynamicTypeCard(DuelistMod.typeCardMap_ID.get(t), DuelistMod.typeCardMap_NAME.get(t), DuelistMod.typeCardMap_IMG.get(t), DuelistMod.typeCardMap_DESC.get(t), t, this, magic)); }
		}
		
		ArrayList<CardTags> extraTags = new ArrayList<CardTags>();
		extraTags.add(Tags.ROSE);		
		boolean isTok = this instanceof TributeToken || this instanceof SummonToken;
		if (!isTok) { extraTags.add(Tags.MEGATYPED); }
		extraTags.add(Tags.OJAMA);	
		extraTags.add(Tags.GIANT);
		extraTags.add(Tags.MAGNET);
		for (CardTags t : extraTags)
		{
			if (customDesc) { typeCards.add(new DynamicTypeCard(DuelistMod.typeCardMap_ID.get(t), DuelistMod.typeCardMap_NAME.get(t), DuelistMod.typeCardMap_IMG.get(t), generateDynamicTypeCardDesc(magic, t), t, this, magic)); }
			else { typeCards.add(new DynamicTypeCard(DuelistMod.typeCardMap_ID.get(t), DuelistMod.typeCardMap_NAME.get(t), DuelistMod.typeCardMap_IMG.get(t), DuelistMod.typeCardMap_DESC.get(t), t, this, magic)); }
			
		}
		return typeCards;
	}
	
	public ArrayList<DuelistCard> generateTypeCardsCustomTypes(int magic, boolean customDesc, ArrayList<CardTags> types)
	{
		ArrayList<DuelistCard> typeCards = new ArrayList<DuelistCard>();
		for (CardTags t : types)
		{
			if (customDesc) { typeCards.add(new DynamicTypeCard(DuelistMod.typeCardMap_ID.get(t), DuelistMod.typeCardMap_NAME.get(t), DuelistMod.typeCardMap_IMG.get(t), generateDynamicTypeCardDesc(magic, t), t, this, magic)); }
			else { typeCards.add(new DynamicTypeCard(DuelistMod.typeCardMap_ID.get(t), DuelistMod.typeCardMap_NAME.get(t), DuelistMod.typeCardMap_IMG.get(t), DuelistMod.typeCardMap_DESC.get(t), t, this, magic)); }
		}
		return typeCards;
	}
	
	public ArrayList<DuelistCard> generateTypeCardsShard(int magic, boolean customDesc)
	{
		ArrayList<DuelistCard> typeCards = new ArrayList<DuelistCard>();
		ArrayList<CardTags> extraTags = new ArrayList<CardTags>();
		extraTags.add(Tags.ROSE);
		extraTags.add(Tags.ARCANE);
		if (!(this instanceof ShardGreed)) { extraTags.add(Tags.MEGATYPED); }
		extraTags.add(Tags.OJAMA);
		//extraTags.add(Tags.MONSTER);
		extraTags.add(Tags.SPELL);
		extraTags.add(Tags.TRAP);
		extraTags.add(Tags.GIANT);
		extraTags.add(Tags.MAGNET);
		
		for (CardTags t : extraTags)
		{
			if (customDesc) { typeCards.add(new DynamicTypeCard(DuelistMod.typeCardMap_ID.get(t), DuelistMod.typeCardMap_NAME.get(t), DuelistMod.typeCardMap_IMG.get(t), generateDynamicTypeCardDesc(magic, t), t, this, magic)); }
			else { typeCards.add(new DynamicTypeCard(DuelistMod.typeCardMap_ID.get(t), DuelistMod.typeCardMap_NAME.get(t), DuelistMod.typeCardMap_IMG.get(t), DuelistMod.typeCardMap_DESC.get(t), t, this, magic)); }
			
		}
		for (CardTags t : DuelistMod.monsterTypes)
		{
			if (customDesc) { typeCards.add(new DynamicTypeCard(DuelistMod.typeCardMap_ID.get(t), DuelistMod.typeCardMap_NAME.get(t), DuelistMod.typeCardMap_IMG.get(t), generateDynamicTypeCardDesc(magic, t), t, this, magic)); }
			else { typeCards.add(new DynamicTypeCard(DuelistMod.typeCardMap_ID.get(t), DuelistMod.typeCardMap_NAME.get(t), DuelistMod.typeCardMap_IMG.get(t), DuelistMod.typeCardMap_DESC.get(t), t, this, magic)); }
			
		}
		
		
		return typeCards;
	}
	
	public static ArrayList<DuelistCard> generateTypeCards(int magic, boolean customDesc, DuelistCard callingCard)
	{
		ArrayList<DuelistCard> typeCards = new ArrayList<DuelistCard>();
		ArrayList<CardTags> types = new ArrayList<CardTags>();
		types.add(Tags.ROSE);
		types.add(Tags.MEGATYPED);
		types.add(Tags.OJAMA);
		types.add(Tags.GIANT);
		types.add(Tags.MAGNET);
		types.addAll(DuelistMod.monsterTypes);
		for (CardTags t : types)
		{
			if (customDesc) { typeCards.add(new DynamicTypeCard(DuelistMod.typeCardMap_ID.get(t), DuelistMod.typeCardMap_NAME.get(t), DuelistMod.typeCardMap_IMG.get(t), generateDynamicTypeCardDesc(magic, t, callingCard, DuelistMod.monsterTypes.size()), t, callingCard, magic)); }
			else { typeCards.add(new DynamicTypeCard(DuelistMod.typeCardMap_ID.get(t), DuelistMod.typeCardMap_NAME.get(t), DuelistMod.typeCardMap_IMG.get(t), DuelistMod.typeCardMap_DESC.get(t), t, callingCard, magic)); }
			
		}
		return typeCards;
	}
	
	public static ArrayList<DuelistCard> generateTypeCards(int magic, boolean customDesc, DuelistCard callingCard, int numberOfRandomTypes, boolean seeded)
	{
		ArrayList<DuelistCard> typeCards = new ArrayList<DuelistCard>();
		ArrayList<CardTags> types = new ArrayList<CardTags>();
		types.add(Tags.ROSE);
		types.add(Tags.MEGATYPED);
		types.add(Tags.OJAMA);
		types.add(Tags.GIANT);
		types.add(Tags.MAGNET);
		int maxS = DuelistMod.monsterTypes.size() + types.size();
		if (numberOfRandomTypes > maxS) { numberOfRandomTypes = maxS; }
		if (numberOfRandomTypes == maxS) { return generateTypeCards(magic, customDesc, callingCard); }
		else 
		{
			types.addAll(DuelistMod.monsterTypes);
			for (int i = 0; i < maxS - numberOfRandomTypes; i++)
			{
				if (seeded)
				{
					types.remove(AbstractDungeon.cardRandomRng.random(types.size() - 1));
				}
				else
				{
					types.remove(ThreadLocalRandom.current().nextInt(0, types.size()));
				}
			}
		}
		
		for (CardTags t : types)
		{
			if (customDesc) { typeCards.add(new DynamicTypeCard(DuelistMod.typeCardMap_ID.get(t), DuelistMod.typeCardMap_NAME.get(t), DuelistMod.typeCardMap_IMG.get(t), generateDynamicTypeCardDesc(magic, t, callingCard, numberOfRandomTypes), t, callingCard, magic)); }
			else { typeCards.add(new DynamicTypeCard(DuelistMod.typeCardMap_ID.get(t), DuelistMod.typeCardMap_NAME.get(t), DuelistMod.typeCardMap_IMG.get(t), DuelistMod.typeCardMap_DESC.get(t), t, callingCard, magic)); }
		}
		return typeCards;
	}
	// =============== /TYPE CARD FUNCTIONS/ =========================================================================================================================================================

	// =============== DEBUG PRINT FUNCTIONS =========================================================================================================================================================
	public static void printSetDetails(CardTags[] setsToFindFrom) 
	{
		// Map that holds set info for printing at end
		Map<CardTags, Integer> tagMap = new HashMap<CardTags, Integer>();
		Map<CardTags, ArrayList<DuelistCard>> tagSet = new HashMap<CardTags, ArrayList<DuelistCard>>();
		for (CardTags t : setsToFindFrom) { tagMap.put(t, 0); tagSet.put(t, new ArrayList<DuelistCard>()); }

		// Check all cards in library
		for (DuelistCard potentialMatchCard : DuelistMod.myCards)
		{
			// See if check card is missing any match tags
			for (CardTags t : setsToFindFrom) 
			{
				if (potentialMatchCard.hasTag(t))
				{
					tagMap.put(t, tagMap.get(t) + 1);
					tagSet.get(t).add(potentialMatchCard);
				}

			}
		}

		Set<Entry<CardTags, Integer>> set = tagMap.entrySet();
		for (Entry<CardTags, Integer> t : set)
		{
			System.out.println("theDuelist:DuelistCard:printSetDetails() --- > START OF SET: " + t.getKey() + " --- " + t.getValue());
			for (DuelistCard c : tagSet.get(t.getKey()))
			{
				System.out.println(c.name);
			}
			System.out.println("theDuelist:DuelistCard:printSetDetails() --- > END OF SET: " + t.getKey());
		} 

	}
	// =============== /DEBUG PRINT FUNCTIONS/ =======================================================================================================================================================
}
