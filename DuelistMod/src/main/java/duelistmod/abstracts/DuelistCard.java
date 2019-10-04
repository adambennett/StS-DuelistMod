package duelistmod.abstracts;

import java.lang.reflect.Type;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
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
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.cardManip.*;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;

import basemod.BaseMod;
import basemod.abstracts.*;
import basemod.helpers.*;
import duelistmod.DuelistMod;
import duelistmod.actions.common.*;
import duelistmod.cards.*;
import duelistmod.cards.curses.*;
import duelistmod.cards.incomplete.*;
import duelistmod.cards.insects.MirrorLadybug;
import duelistmod.cards.tempCards.*;
import duelistmod.cards.tokens.*;
import duelistmod.characters.FakePlayer;
import duelistmod.helpers.*;
import duelistmod.interfaces.*;
import duelistmod.orbs.*;
import duelistmod.patches.TheDuelistEnum;
import duelistmod.powers.*;
import duelistmod.powers.duelistPowers.*;
import duelistmod.powers.incomplete.*;
import duelistmod.relics.*;
import duelistmod.variables.*;

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
	public boolean upgradedSecondMagic = false;
	public boolean upgradedThirdMagic = false;
	public boolean upgradedTributes = false;
	public boolean upgradedSummons = false;
	public boolean inDuelistBottle = false;
	public boolean loadedTribOrSummonChange = false;
	public boolean fiendDeckDmgMod = false;
	public boolean aquaDeckEffect = false;
	public int secondMagic = 0;
	public int baseSecondMagic = 0;
	public int thirdMagic = 0;
	public int baseThirdMagic = 0;
	public int summons = 0;
	public int tributes = 0;
	public int baseSummons = 0;
	public int baseTributes = 0;
	public int tributesForTurn = 0;
	public int summonsForTurn = 0;
	public int permTribChange = 0;
	public int permSummonChange = 0;
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
	public abstract void onTribute(DuelistCard tributingCard);		/* DEPRECATED - Implement customOnTribute() to run special tributing functions on cards, monster types are handled automatically */
	public abstract void onResummon(int summons);
	public abstract void summonThis(int summons, DuelistCard c, int var);
	public abstract void summonThis(int summons, DuelistCard c, int var, AbstractMonster m);
	// =============== /ABSTRACT METHODS/ =======================================================================================================================================================
	
	// =============== VOID METHODS =========================================================================================================================================================
	public void onTributeWhileInHand(DuelistCard tributedMon, DuelistCard tributingMon) { }
	
	public void onTributeWhileInDiscard(DuelistCard tributedMon, DuelistCard tributingMon) { }
	
	public void onTributeWhileInExhaust(DuelistCard tributedMon, DuelistCard tributingMon) { }
	
	public void onTributeWhileInDraw(DuelistCard tributedMon, DuelistCard tributingMon) { }
	
	public void onSummonWhileInHand(DuelistCard summoned, int amountSummoned) { }
	
	public void onSummonWhileInDiscard(DuelistCard summoned, int amountSummoned) { }
	
	public void onSummonWhileInExhaust(DuelistCard summoned, int amountSummoned) { }
	
	public void onSummonWhileInDraw(DuelistCard summoned, int amountSummoned) { }
	
	public void onIncrementWhileInHand(int amount, int newMaxSummons) { }
	
	public void onIncrementWhileInDraw(int amount, int newMaxSummons) { }
	
	public void onIncrementWhileInDiscard(int amount, int newMaxSummons) { }
	
	public void onIncrementWhileInExhaust(int amount, int newMaxSummons) { }
	
	public void onResummonWhileInHand(DuelistCard resummoned) { }
	
	public void onResummonWhileInDraw(DuelistCard resummoned) { }
	
	public void onResummonWhileInDiscard(DuelistCard resummoned) { }
	
	public void onResummonWhileInExhaust(DuelistCard resummoned) { }
	
	public void onSynergyTributeWhileInHand() { }
	
	public void onSynergyTributeWhileInDiscard() { }
	
	public void onSynergyTributeWhileInExhaust() { }
	
	public void onSynergyTributeWhileInDraw() { }
	// =============== /VOID METHODS/ =======================================================================================================================================================
	
	
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
	public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp)
	{
		super.calculateModifiedCardDamage(player, mo, tmp);
		if (this.hasTag(Tags.DRAGON) && player().hasPower(MountainPower.POWER_ID)) { float dmgMod = (player().getPower(MountainPower.POWER_ID).amount / 10.00f) + 1.0f; tmp = tmp * dmgMod; }
		if (this.hasTag(Tags.SPELLCASTER) && player().hasPower(YamiPower.POWER_ID)) {  float dmgMod = (player().getPower(YamiPower.POWER_ID).amount / 10.00f) + 1.0f; tmp = tmp * dmgMod; }
		if (this.hasTag(Tags.PLANT) && player().hasPower(VioletCrystalPower.POWER_ID)) { float dmgMod = (player().getPower(VioletCrystalPower.POWER_ID).amount / 10.00f) + 1.0f; tmp = tmp * dmgMod; }
		if (this.hasTag(Tags.NATURIA) && player().hasPower(SacredTreePower.POWER_ID)) { float dmgMod = (player().getPower(SacredTreePower.POWER_ID).amount / 10.00f) + 1.0f; tmp = tmp * dmgMod; }
		if (this.hasTag(Tags.AQUA) && player().hasPower(UmiPower.POWER_ID)) { float dmgMod = (player().getPower(UmiPower.POWER_ID).amount / 10.00f) + 1.0f; tmp = tmp * dmgMod; }
		if (this.hasTag(Tags.ZOMBIE) || this.hasTag(Tags.FIEND)) { if (player().hasPower(GatesDarkPower.POWER_ID)) { float dmgMod = (player().getPower(GatesDarkPower.POWER_ID).amount / 10.00f) + 1.0f; tmp = tmp * dmgMod;  }}
		if (this.hasTag(Tags.WARRIOR) && player().hasPower(SogenPower.POWER_ID)) { float dmgMod = (player().getPower(SogenPower.POWER_ID).amount / 10.00f) + 1.0f; tmp = tmp * dmgMod; }
		if (this.hasTag(Tags.BUG) && player().hasPower(BugMatrixPower.POWER_ID)) { float dmgMod = (player().getPower(BugMatrixPower.POWER_ID).amount / 10.00f) + 1.0f; tmp = tmp * dmgMod; }
		if ((this.hasTag(Tags.BUG) || this.hasTag(Tags.SPIDER)) && player().hasPower(ForestPower.POWER_ID)) { float dmgMod = (player().getPower(ForestPower.POWER_ID).amount / 10.00f) + 1.0f; tmp = tmp * dmgMod; }
		if (this.hasTag(Tags.AQUA) && player().hasPower(SpikedGillmanPower.POWER_ID)) { tmp += player().getPower(SpikedGillmanPower.POWER_ID).amount;  }
		if (this.hasTag(Tags.DRAGON) && player().hasPower(TyrantWingPower.POWER_ID)) { tmp += player().getPower(TyrantWingPower.POWER_ID).amount;  }
		if (this.hasTag(DuelistMod.chosenRockSunriseTag) && player().hasPower(RockSunrisePower.POWER_ID)) { tmp = (int) Math.floor(tmp * 1.25); }
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
			dCard.originalMagicNumber = this.originalMagicNumber;
			dCard.inDuelistBottle = this.inDuelistBottle;
			dCard.baseTributes = this.baseTributes;
			dCard.baseSummons = this.baseSummons;
			dCard.isSummonModPerm = this.isSummonModPerm;
			dCard.isTribModPerm = this.isTribModPerm;
			dCard.exhaust = this.exhaust;
			dCard.originalDescription = this.originalDescription;
			ArrayList<CardTags> monsterTags = getAllMonsterTypes(this);
			dCard.tags.addAll(monsterTags);
			dCard.savedTypeMods = this.savedTypeMods;
			//dCard.baseDamage = this.baseDamage;
			if (this.hasTag(Tags.MEGATYPED))
			{
				dCard.tags.add(Tags.MEGATYPED);
			}
		}
		return card;
	}
	
	@Override
	public void resetAttributes()
	{
		super.resetAttributes();
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
		for (String s : this.savedTypeMods) { saveAttributes += s + "~"; }
		return saveAttributes;
	}
	
	@Override
	public void onLoad(String attributeString)
	{
		// If no saved string, just return
		if (attributeString == null) { return; }
		
		// Otherwise, get the saved string and split it into components
		String[] savedStrings = attributeString.split("~");
		ArrayList<String> savedTypes = new ArrayList<String>();
		String[] savedIntegers = new String[4];
		
		// Get the first 4 strings and convert back to int (perm tribute changes, perm summon changes, random pool archetype 1 & 2)
		
		for (int i = 0; i < 4; i++) { savedIntegers[i] = savedStrings[i]; }
		int[] ints = Arrays.stream(savedIntegers).mapToInt(Integer::parseInt).toArray();
		
		// Now look for any saved type modifications
		for (int j = 4; j < savedStrings.length - 1; j++) 
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
			Util.log("Found card from exodia deck!");
			this.makeSoulbound(true);
			this.rawDescription = "Soulbound NL " + this.rawDescription;
			this.initializeDescription();
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
	
	public void customOnTribute(DuelistCard tc)
	{
		
	}

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

	public void postBattleReset()
	{
		if ((this.isTributesModifiedForTurn || this.isTributesModified) && !this.isTribModPerm)
		{
			this.isTributesModifiedForTurn = false;
			this.isTributesModified = false;
			this.tributes = this.baseTributes;
			this.rawDescription = this.originalDescription;
			this.initializeDescription();
		}
		
		if ((this.isSummonsModifiedForTurn || this.isSummonsModified) && !this.isSummonModPerm)
		{
		
			this.isSummonsModifiedForTurn = false;
			this.isSummonsModified = false;
			this.summons = this.baseSummons;
			this.rawDescription = this.originalDescription;
			this.initializeDescription();
		}
		
		if (this.fiendDeckDmgMod && this.damage != this.originalDamage && this.originalDamage != -1)
		{
			this.applyPowers();
			this.aquaDeckEffect = false;
			if (DuelistMod.debug)
			{
				DuelistMod.logger.info("Triggered Fiend deck reset because of increased damage, damage value on card: " + this.damage + ", and old value: " + this.originalDamage + ", card name: " + this.originalName);
			}
		}
		
		else if (this.fiendDeckDmgMod && this.block != this.originalBlock && this.originalBlock != -1)
		{
			this.applyPowers();
			this.aquaDeckEffect = false;
			if (DuelistMod.debug)
			{
				DuelistMod.logger.info("Triggered Aqua deck reset because of increased block, block value on card: " + this.block + ", and old value: " + this.originalBlock + ", card name: " + this.originalName);
			}
		}
		
		if (this.dmgHolder != -1) { this.dmgHolder = -1; }
	}
	
	public void postTurnReset()
	{
		if (this.isTributesModifiedForTurn)
		{
			this.isTributesModifiedForTurn = false;
			this.isTributesModified = false;
			this.tributes = this.baseTributes;
			this.rawDescription = this.originalDescription;
			this.initializeDescription();
		}
		
		if (this.isSummonsModifiedForTurn)
		{		
			this.isSummonsModifiedForTurn = false;
			this.isSummonsModified = false;
			this.summons = this.baseSummons;
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
		if (this.hasTag(Tags.DRAGON) && player().hasPower(TyrantWingPower.POWER_ID)) 
		{  
			TwoAmountPower power = (TwoAmountPower) player().getPower(TyrantWingPower.POWER_ID);			
			power.amount2--;
			power.updateDescription();
		}
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
		if (this.hasTag(Tags.DRAGON) && player().hasPower(TyrantWingPower.POWER_ID)) 
		{  
			TwoAmountPower power = (TwoAmountPower) player().getPower(TyrantWingPower.POWER_ID);
			power.amount2--;
			power.updateDescription();
		}
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(player(), damageAmount, damageTypeForTurn), effect));
	}
	
	public void specialAttack(AbstractMonster m, AttackEffect afx, int dmg)
	{
		if (this.hasTag(Tags.DRAGON) && player().hasPower(TyrantWingPower.POWER_ID)) 
		{  
			TwoAmountPower power = (TwoAmountPower) player().getPower(TyrantWingPower.POWER_ID);
			power.amount2--;
			power.updateDescription();
		}
		AbstractDungeon.actionManager.addToBottom(new DuelistDamageAction(m, new DamageInfo(player(), dmg, damageTypeForTurn), afx));
	}
	
	// Flying Pegasus
	public static void staticAttack(AbstractMonster m, AttackEffect effect, int damageAmount) 
	{
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(player(), damageAmount, DamageType.THORNS), effect));
	}
	
	public void attackFast(AbstractMonster m, AttackEffect effect, int damageAmount)
	{
		if (this.hasTag(Tags.DRAGON) && player().hasPower(TyrantWingPower.POWER_ID)) 
		{  
			TwoAmountPower power = (TwoAmountPower) player().getPower(TyrantWingPower.POWER_ID);
			power.amount2--;
			power.updateDescription();
		}
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(player(), damageAmount, damageTypeForTurn), effect, true));
	}
	
	public int attackMultipleRandom(int amountOfEnemiesToAttack, AttackEffect afx, DamageType dmgType)
	{
		return attackMultipleRandom(this.damage, amountOfEnemiesToAttack, afx, dmgType);
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
	
	public static int attackMultipleRandom(int damage, int amountOfEnemiesToAttack, AttackEffect afx, DamageType dmgType)
	{
		ArrayList<AbstractMonster> allEnemies = new ArrayList<AbstractMonster>();
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
				allEnemies.remove(index);
			}
			
			for (AbstractMonster m : allEnemies)
			{
				Util.log("attackMultipleRandom() -- still remaining in allEnemies: " + m.name);
				AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(player(), damage, dmgType), afx));
			}
			
			if (allEnemies.size() != amountOfEnemiesToAttack) { Util.log("attackMultipleRandom() got different values for allEnemies.size() and amountOfEnemiesToAttack! allEnemies.size()=" + allEnemies.size() + " -- amountOfEnemiesToAttack=" + amountOfEnemiesToAttack); }
			
			return allEnemies.size();
		}
	}

	public static void attackAll(AttackEffect effect, int[] damageAmounts, DamageType dmgForTurn)
	{
		AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(player(), damageAmounts, dmgForTurn, effect));
	}
	
	public void normalMultidmg()
	{
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
	// =============== /ATTACK FUNCTIONS/ =======================================================================================================================================================
	
	
	
	// =============== DEFEND FUNCTIONS =========================================================================================================================================================
	protected void block() 
	{
		block(this.block);
	}

	public void block(int amount) 
	{
		if (this.hasTag(Tags.SPELLCASTER) && player().hasPower(YamiPower.POWER_ID)) { float dmgMod = (player().getPower(YamiPower.POWER_ID).amount / 10.00f) + 1.0f; amount = (int) (amount * dmgMod); }
		if (this.hasTag(Tags.PLANT) && player().hasPower(VioletCrystalPower.POWER_ID)) { float dmgMod = (player().getPower(VioletCrystalPower.POWER_ID).amount / 10.00f) + 1.0f; amount = (int) (amount * dmgMod); }
		if (this.hasTag(Tags.NATURIA) && player().hasPower(SacredTreePower.POWER_ID)) { float dmgMod = (player().getPower(SacredTreePower.POWER_ID).amount / 10.00f) + 1.0f; amount = (int) (amount * dmgMod); }
		if (this.hasTag(Tags.AQUA) && player().hasPower(UmiPower.POWER_ID)) {  float dmgMod = (player().getPower(UmiPower.POWER_ID).amount / 10.00f) + 1.0f; amount = (int) (amount * dmgMod);  }
		if (this.hasTag(Tags.ZOMBIE) || this.hasTag(Tags.FIEND)) { if (player().hasPower(GatesDarkPower.POWER_ID)) {  float dmgMod = (player().getPower(GatesDarkPower.POWER_ID).amount / 10.00f) + 1.0f; amount = (int) (amount * dmgMod);  }}
		if (this.hasTag(Tags.WARRIOR) && player().hasPower(SogenPower.POWER_ID)) {  float dmgMod = (player().getPower(SogenPower.POWER_ID).amount / 10.00f) + 1.0f; amount = (int) (amount * dmgMod);  }
		if ((this.hasTag(Tags.BUG) || this.hasTag(Tags.SPIDER)) && player().hasPower(ForestPower.POWER_ID)) 
		{ 
			TwoAmountPower fore = (TwoAmountPower)player().getPower(ForestPower.POWER_ID);
			int mm = fore.amount2;
			float dmgMod = (mm / 10.00f) + 1.0f; amount = (int) (amount * dmgMod); 
		}
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
	
	public static void applyPower(AbstractPower power, AbstractCreature target) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, player(), power, power.amount));

	}

	public static void applyPowerTop(AbstractPower power, AbstractCreature target) {
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, player(), power, power.amount));

	}

	protected void applyPower(AbstractPower power, AbstractCreature target, int amount) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, player(), power, amount));

	}

	public static void removePower(AbstractPower power, AbstractCreature target) {

		if (target.hasPower(power.ID))
		{
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(target, player(), power, power.amount));
		}
	}
	
	public static void removePowerAction(AbstractCreature target, AbstractPower power)
	{
		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(target, target, power));
	}
	
	public static void removePowerAction(AbstractCreature target, String powerName)
	{
		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(target, target, powerName));
	}

	public static void reducePower(AbstractPower power, AbstractCreature target, int reduction) {

		if (target.hasPower(power.ID))
		{
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(target, player(), power, reduction));
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
		BuffHelper.resetRandomBuffs();

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
		BuffHelper.resetRandomBuffs();

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
	
	public static ArrayList<AbstractPower> getRandomBuffs(AbstractCreature p, int amount, boolean replacement)
	{
		if (!replacement)
		{
			if (amount > DuelistMod.lowNoBuffs - 1) { amount = DuelistMod.lowNoBuffs - 1; }
			BuffHelper.resetRandomBuffs();
			ArrayList<AbstractPower> powerList = new ArrayList<AbstractPower>();
			ArrayList<String> powerNames = new ArrayList<String>();
			// Get randomized buff
			for (int j = 0; j < amount; j++)
			{
				int randomBuffNum = AbstractDungeon.cardRandomRng.random(DuelistMod.randomBuffs.size() - 1);
				AbstractPower randomBuff = DuelistMod.randomBuffs.get(randomBuffNum);
				while(powerNames.contains(randomBuff.name))
				{
					randomBuffNum = AbstractDungeon.cardRandomRng.random(DuelistMod.randomBuffs.size() - 1);
					randomBuff = DuelistMod.randomBuffs.get(randomBuffNum);
				}
				powerList.add(randomBuff);
				powerNames.add(randomBuff.name);
				for (int i = 0; i < DuelistMod.randomBuffs.size(); i++)
				{
					if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:getRandomBuff() ---> buffs[" + i + "]: " + DuelistMod.randomBuffs.get(i).name + " :: amount: " + DuelistMod.randomBuffs.get(i).amount); }
				}
				if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:getRandomBuff() ---> generated random buff: " + randomBuff.name + " :: index was: " + randomBuffNum + " :: turnNum or amount was: " + randomBuff.amount); }	
			}
			return powerList;
		}
		else
		{
			BuffHelper.resetRandomBuffs();
			ArrayList<AbstractPower> powerList = new ArrayList<AbstractPower>();
			// Get randomized buff
			for (int j = 0; j < amount; j++)
			{
				int randomBuffNum = AbstractDungeon.cardRandomRng.random(DuelistMod.randomBuffs.size() - 1);
				AbstractPower randomBuff = DuelistMod.randomBuffs.get(randomBuffNum);
				powerList.add(randomBuff);
				for (int i = 0; i < DuelistMod.randomBuffs.size(); i++)
				{
					if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:getRandomBuff() ---> buffs[" + i + "]: " + DuelistMod.randomBuffs.get(i).name + " :: amount: " + DuelistMod.randomBuffs.get(i).amount); }
				}
				if (DuelistMod.debug) { System.out.println("theDuelist:DuelistCard:getRandomBuff() ---> generated random buff: " + randomBuff.name + " :: index was: " + randomBuffNum + " :: turnNum or amount was: " + randomBuff.amount); }	
			}
			return powerList;
		}
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
	
	public static AbstractPower getRandomBuffPlayer(AbstractPlayer p, int turnNum, boolean smallSet)
	{
		if (smallSet) { return getRandomBuffSmall(p, turnNum); }
		else { return getRandomBuff(p, turnNum); }
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
	public void setColorless()
	{
		this.color = CardColor.COLORLESS;
	}
	
	public static ArrayList<AbstractMonster> getAllMons()
	{
		ArrayList<AbstractMonster> mons = new ArrayList<AbstractMonster>();
    	for (AbstractMonster monst : AbstractDungeon.getCurrRoom().monsters.monsters) { if (!monst.isDead && !monst.isDying && !monst.isDeadOrEscaped() && !monst.halfDead) { mons.add(monst); }}
    	return mons;
	}
	
	// Handle onTribute for custom relics, powers, orbs, stances, cards, potions
	private static void handleOnTributeForAllAbstracts(DuelistCard tributed, DuelistCard tributingCard)
	{
		Util.log("Running onSummon for all abstract objects! Tributed: " + tributed + " for " + tributingCard.name);
		AbstractPlayer p = AbstractDungeon.player;
		for (AbstractRelic r : p.relics) { if (r instanceof DuelistRelic) { ((DuelistRelic)r).onTribute(tributed, tributingCard); }}
		for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  ((DuelistOrb)o).onTribute(tributed, tributingCard); }}
		for (AbstractPower pow : p.powers) { if (pow instanceof DuelistPower) { ((DuelistPower)pow).onTribute(tributed, tributingCard); }}
		for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onTributeWhileInHand(tributed, tributingCard); }}
		for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onTributeWhileInDiscard(tributed, tributingCard); }}
		for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onTributeWhileInDraw(tributed, tributingCard); }}
		for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onTributeWhileInExhaust(tributed, tributingCard); }}
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { ((DuelistPotion) pot).onTribute(tributed, tributingCard); }}
		
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
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { ((DuelistPotion)pot).onSynergyTribute(); }}
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
		}
		else
		{
			for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard && (!(c instanceof MirrorLadybug))) { ((DuelistCard)c).onSummonWhileInHand(summoned, amountSummoned); }}
			for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard && (!(c instanceof MirrorLadybug))) { ((DuelistCard)c).onSummonWhileInDiscard(summoned, amountSummoned); }}
			for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard && (!(c instanceof MirrorLadybug))) { ((DuelistCard)c).onSummonWhileInDraw(summoned, amountSummoned); }}
		}
		for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onSummonWhileInExhaust(summoned, amountSummoned); }}
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { ((DuelistPotion)pot).onSummon(summoned, amountSummoned); }}
	
	}
		
	public boolean hasOtherMonsterTypes(CardTags excludeTag)
	{
		for (CardTags t : DuelistMod.monsterTypes)
		{
			if (this.hasTag(t) && !t.equals(excludeTag)) { return true; }
		}
		return false;
	}
	
	public void chooseHandCardToModifyMagicNumForTurn(int cardsToChoose, int addAmt, boolean canCancel)
	{
		AbstractPlayer p = AbstractDungeon.player;
		ArrayList<DuelistCard> handDuelistCards = new ArrayList<DuelistCard>();
		ArrayList<DuelistCard> secondSet = new ArrayList<DuelistCard>();
    	for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard && !c.uuid.equals(this.uuid)) { handDuelistCards.add((DuelistCard) c); }}
    	for (DuelistCard c : handDuelistCards) { if (!c.isTributesModifiedForTurn) { secondSet.add(c); }}
    	AbstractDungeon.actionManager.addToTop(new CardSelectScreenModifyMagicNumberForTurnAction(secondSet, cardsToChoose, addAmt, canCancel));
	}
	
	public void modifyMagicNumForTurn(ArrayList<DuelistCard> handDuelistCards, int cardsToChoose, int addAmt, boolean canCancel)
	{
		ArrayList<DuelistCard> secondSet = new ArrayList<DuelistCard>();
    	for (DuelistCard c : handDuelistCards) { if (!c.isTributesModifiedForTurn && !c.hasTag(Tags.NO_MAGIC_MOD)) { secondSet.add(c); }}
    	AbstractDungeon.actionManager.addToTop(new CardSelectScreenModifyMagicNumberForTurnAction(secondSet, cardsToChoose, addAmt, canCancel));
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
	
	public void fetch(CardGroup group, boolean top)
	{
		if (top) { AbstractDungeon.actionManager.addToTop(new FetchAction(group)); }
		else { AbstractDungeon.actionManager.addToBottom(new FetchAction(group)); }
	}
	
	public void fetch(int amount, CardGroup group, boolean top)
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
		powerTypeMap.put(Tags.AQUA, new SpikedGillmanPower(AbstractDungeon.player, AbstractDungeon.player, turnAmount));
		powerTypeMap.put(Tags.DRAGON, new StrengthPower(AbstractDungeon.player, turnAmount));
		powerTypeMap.put(Tags.FIEND, new DoomdogPower(AbstractDungeon.player, AbstractDungeon.player, turnAmount));
		powerTypeMap.put(Tags.INSECT, new CocoonPower(AbstractDungeon.player, AbstractDungeon.player, 3));
		powerTypeMap.put(Tags.MACHINE, new ArtifactPower(AbstractDungeon.player, turnAmount));
		powerTypeMap.put(Tags.NATURIA, new VinesPower(turnAmount));
		powerTypeMap.put(Tags.PLANT, new PlantTypePower(AbstractDungeon.player, AbstractDungeon.player, turnAmount));
		powerTypeMap.put(Tags.PREDAPLANT, new ThornsPower(AbstractDungeon.player, turnAmount));
		powerTypeMap.put(Tags.SPELLCASTER, new MagickaPower(AbstractDungeon.player, AbstractDungeon.player, turnAmount));
		powerTypeMap.put(Tags.SUPERHEAVY, new DexterityPower(AbstractDungeon.player, turnAmount));
		powerTypeMap.put(Tags.TOON, new RetainCardPower(AbstractDungeon.player, 1));
		powerTypeMap.put(Tags.WARRIOR, new StrengthPower(AbstractDungeon.player, turnAmount));
		powerTypeMap.put(Tags.ZOMBIE, new TrapHolePower(AbstractDungeon.player, AbstractDungeon.player, 1));
		powerTypeMap.put(Tags.ROCK, new PlatedArmorPower(AbstractDungeon.player, 2));
		
		if (!powerTypeMap.get(type).equals(null)) { return powerTypeMap.get(type); }
		else { return new StrengthPower(AbstractDungeon.player, turnAmount); }
	}
	
	public static SummonPower getSummonPower()
	{
		SummonPower pow = (SummonPower)AbstractDungeon.player.getPower(SummonPower.POWER_ID);
		return pow;
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
		AbstractDungeon.actionManager.addToBottom(new ExhaustAction(AbstractDungeon.player, AbstractDungeon.player, amount, isRandom));
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
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card, false));
		}
	}
	
	public static void addCardToHand(AbstractCard card, int amt)
	{
		for (int i = 0; i < amt; i++) { addCardToHand(card); }
	}
	
	public static void gainTempHP(int amount)
	{
		AbstractDungeon.actionManager.addToTop(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, amount));
	}
	
	public static void getPotion(AbstractPotion pot)
	{
		AbstractDungeon.actionManager.addToTop(new ObtainPotionAction(pot));
	}
	
	// =============== /MISC ACTION FUNCTIONS/ =======================================================================================================================================================

	// =============== SUMMON MONSTER FUNCTIONS =========================================================================================================================================================
	public void summon()
	{
		summon(AbstractDungeon.player, this.summons, this);
	}
	
	public static void summon(AbstractPlayer p, int SUMMONS, DuelistCard c)
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
							if (cardCopy != null && !cardCopy.hasTag(Tags.EXEMPT))
							{
								AbstractMonster m = AbstractDungeon.getRandomMonster();
								fullResummon(cardCopy, c.upgraded, m, false);
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
							if (cardCopy != null && !cardCopy.hasTag(Tags.EXEMPT))
							{
								AbstractMonster m = AbstractDungeon.getRandomMonster();
								fullResummon(cardCopy, c.upgraded, m, false);
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
	}
	
	
	// This function needs to match powerSummon() exactly, except for the first block of code that returns early due to curse/challenges
	// Although it is ok to add extra code on top of powerSummon() inside this function, it just needs to do everything powerSummon() does
	public static void puzzleSummon(AbstractPlayer p, int SUMMONS, String cardName, boolean fromUO)
	{
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
				

				// Check for Slifer
				if (p.hasPower(SliferSkyPower.POWER_ID)) 
				{ 
					SliferSkyPower instance = (SliferSkyPower) p.getPower(SliferSkyPower.POWER_ID);
					applyPowerToSelf(new StrengthPower(p, instance.amount));
					//applyPowerToSelf(new LoseStrengthPower(p, instance.amount));
				} 
				
				
				// Check for Goblin's Secret Remedy
				if (p.hasPower(GoblinRemedyPower.POWER_ID)) { gainTempHP(p.getPower(GoblinRemedyPower.POWER_ID).amount * startSummons);  }
				
				// Check for Blizzard Dragon
				if (p.hasPower(BlizzardDragonPower.POWER_ID) && c.hasTag(Tags.DRAGON)) 
				{ 
					for (int i = 0; i < startSummons; i++) { AbstractOrb frost = new Frost(); channel(frost); }
				}
				// Check for Toon Cannon Soldier
				if (p.hasPower(ToonCannonPower.POWER_ID) && c.hasTag(Tags.TOON))
				{
					ToonCannonPower power = (ToonCannonPower) p.getPower(ToonCannonPower.POWER_ID);
					DuelistCard.damageAllEnemiesThornsPoison(power.amount);
				}
				
				// Check for Seed Cannon
				if (p.hasPower(SeedCannonPower.POWER_ID) && c.hasTag(Tags.PLANT))
				{
					TwoAmountPower seedC = (TwoAmountPower)p.getPower(SeedCannonPower.POWER_ID);
					seedC.amount += startSummons;
					seedC.updateDescription();
				}
				
				// Check for Tripod Fish
				if (p.hasPower(TripodFishPower.POWER_ID) && c.hasTag(Tags.AQUA))
				{
					for (int i = 0; i < p.getPower(TripodFishPower.POWER_ID).amount; i++)
					{						
						DuelistCard randAqExh = (DuelistCard) returnTrulyRandomFromSet(Tags.AQUA);					
						AbstractDungeon.actionManager.addToTop(new RandomizedExhaustPileAction(randAqExh, true));
					}
				}
				
				// Check for Surge orbs
				if (p.hasOrb())
				{
					for (AbstractOrb o : p.orbs)
					{
						if (o instanceof Surge)
						{
							Surge s = (Surge)o;
							s.triggerPassiveEffect();
							Util.log("Triggered Surge without checking potSummons");
						}
					}
				}
				
				int cursedBillGold = cursedBillGoldLoss();
				if (cursedBillGold > 0) { loseGold(cursedBillGold); }
				
				// Check for Power Giants
				for (AbstractCard giantCard : player().hand.group)
				{
					if (giantCard instanceof PowerGiant)
					{
						PowerGiant giant = (PowerGiant)giantCard;
						giant.damageInc();
					}
				}
				
				for (AbstractCard giantCard : player().discardPile.group)
				{
					if (giantCard instanceof PowerGiant)
					{
						PowerGiant giant = (PowerGiant)giantCard;
						giant.damageInc();
					}
				}
				
				for (AbstractCard giantCard : player().drawPile.group)
				{
					if (giantCard instanceof PowerGiant)
					{
						PowerGiant giant = (PowerGiant)giantCard;
						giant.damageInc();
					}
				}
				
				// Check for new summoned types
				ArrayList<CardTags> toRet = getAllMonsterTypes(c);
				if (toRet.size() > 0)
				{
					for (CardTags t : toRet)
					{
						if (!DuelistMod.summonedTypesThisTurn.contains(t))
						{
							DuelistMod.summonedTypesThisTurn.add(t);
							if (player().hasPower(KuribohrnPower.POWER_ID))
							{
								if (DuelistMod.kuribohrnFlipper) 
								{ 
									DuelistCard randZomb = (DuelistCard) returnTrulyRandomFromSet(Tags.ZOMBIE);
									fullResummon(randZomb, false, AbstractDungeon.getRandomMonster(), false);
								}
								DuelistMod.kuribohrnFlipper = !DuelistMod.kuribohrnFlipper;
							}
						}
						DuelistMod.lastTagSummoned = toRet.get(AbstractDungeon.cardRandomRng.random(toRet.size() - 1));
					}
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

				if (potSummons > 0) { for (int i = 0; i < potSummons; i++) { summonsInstance.summonList.add(cardName); summonsInstance.actualCardSummonList.add((DuelistCard) c.makeStatEquivalentCopy()); } }

				// Handle abstract objects
				handleOnSummonForAllAbstracts(c, potSummons);
				

				// Check for Slifer
				if (p.hasPower(SliferSkyPower.POWER_ID) && potSummons > 0) 
				{ 
					SliferSkyPower instance = (SliferSkyPower) p.getPower(SliferSkyPower.POWER_ID);
					applyPowerToSelf(new StrengthPower(p, instance.amount * potSummons));
					//applyPowerToSelf(new LoseStrengthPower(p, instance.amount * potSummons));
				} 
				
				
				// Check for Goblin's Secret Remedy
				if (p.hasPower(GoblinRemedyPower.POWER_ID) && potSummons > 0) { gainTempHP(p.getPower(GoblinRemedyPower.POWER_ID).amount * potSummons);  }

				// Check for Blizzard Dragon
				if (p.hasPower(BlizzardDragonPower.POWER_ID) && c.hasTag(Tags.DRAGON)) 
				{ 
					for (int i = 0; i < potSummons; i++) { AbstractOrb frost = new Frost(); channel(frost); }
				}
				
				// Check for Toon Cannon Soldier
				if (p.hasPower(ToonCannonPower.POWER_ID) && c.hasTag(Tags.TOON) && potSummons > 0)
				{
					ToonCannonPower power = (ToonCannonPower) p.getPower(ToonCannonPower.POWER_ID);
					DuelistCard.damageAllEnemiesThornsPoison(power.amount);
				}
				
				// Check for Seed Cannon
				if (p.hasPower(SeedCannonPower.POWER_ID) && c.hasTag(Tags.PLANT) && potSummons > 0)
				{
					TwoAmountPower seedC = (TwoAmountPower)p.getPower(SeedCannonPower.POWER_ID);
					seedC.amount += potSummons;
					seedC.updateDescription();
				}
				
				// Check for Tripod Fish
				if (p.hasPower(TripodFishPower.POWER_ID) && c.hasTag(Tags.AQUA) && potSummons > 0)
				{
					for (int i = 0; i < p.getPower(TripodFishPower.POWER_ID).amount; i++)
					{						
						DuelistCard randAqExh = (DuelistCard) returnTrulyRandomFromSet(Tags.AQUA);					
						AbstractDungeon.actionManager.addToTop(new RandomizedExhaustPileAction(randAqExh, true));
					}
				}
				
				// Check for Surge orbs
				if (p.hasOrb() && potSummons > 0)
				{
					for (AbstractOrb o : p.orbs)
					{
						if (o instanceof Surge)
						{
							Surge s = (Surge)o;
							for (int i = 0; i < potSummons; i++) { s.triggerPassiveEffect(); }
						}
					}
				}
				
				if (potSummons > 0)
				{
					int cursedBillGold = cursedBillGoldLoss();
					if (cursedBillGold > 0) { loseGold(cursedBillGold); }
				}
				
				// Check for Power Giants
				if (potSummons > 0)
				{
					for (AbstractCard giantCard : player().hand.group)
					{
						if (giantCard instanceof PowerGiant)
						{
							PowerGiant giant = (PowerGiant)giantCard;
							giant.damageInc();
						}
					}
					
					for (AbstractCard giantCard : player().discardPile.group)
					{
						if (giantCard instanceof PowerGiant)
						{
							PowerGiant giant = (PowerGiant)giantCard;
							giant.damageInc();
						}
					}
					
					for (AbstractCard giantCard : player().drawPile.group)
					{
						if (giantCard instanceof PowerGiant)
						{
							PowerGiant giant = (PowerGiant)giantCard;
							giant.damageInc();
						}
					}
				}
				
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
					if (toRet.size() > 0)
					{
						for (CardTags t : toRet)
						{
							if (!DuelistMod.summonedTypesThisTurn.contains(t))
							{
								DuelistMod.summonedTypesThisTurn.add(t);
								if (player().hasPower(KuribohrnPower.POWER_ID))
								{
									if (DuelistMod.kuribohrnFlipper) 
									{ 
										DuelistCard randZomb = (DuelistCard) returnTrulyRandomFromSet(Tags.ZOMBIE);
										fullResummon(randZomb, false, AbstractDungeon.getRandomMonster(), false);
									}
									DuelistMod.kuribohrnFlipper = !DuelistMod.kuribohrnFlipper;
								}
							}
						}
						DuelistMod.lastTagSummoned = toRet.get(AbstractDungeon.cardRandomRng.random(toRet.size() - 1));
					}
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
							if (cardCopy != null && !cardCopy.hasTag(Tags.EXEMPT))
							{
								AbstractMonster m = AbstractDungeon.getRandomMonster();
								fullResummon(cardCopy, c.upgraded, m, false);
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
	
	public int xCostTribute()
	{
		int size = tribute(AbstractDungeon.player, 0, true, this).size();
		if (AbstractDungeon.player.hasRelic(ChemicalX.ID)) { return size * 2; }
		else { return size; }
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
						if (p.hasPower(TributeToonPower.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSets(Tags.MONSTER, Tags.TOON), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPower.POWER_ID), p, 1); }
						if (p.hasPower(TributeToonPowerB.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSet(Tags.TOON), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPowerB.POWER_ID), p, 1); }

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
					if (p.hasPower(TributeToonPower.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSets(Tags.MONSTER, Tags.TOON), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPower.POWER_ID), p, 1); }
					if (p.hasPower(TributeToonPowerB.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSet(Tags.TOON), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPowerB.POWER_ID), p, 1); }

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
					if (p.hasPower(TributeToonPower.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSets(Tags.MONSTER, Tags.TOON), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPower.POWER_ID), p, 1); }
					if (p.hasPower(TributeToonPowerB.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSet(Tags.TOON), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPowerB.POWER_ID), p, 1); }

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
					return tributes;
				}
				else
				{
					empInstance.flag = true;
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
				if (p.hasPower(TributeToonPower.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSets(Tags.MONSTER, Tags.TOON), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPower.POWER_ID), p, 1); }
				if (p.hasPower(TributeToonPowerB.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSet(Tags.TOON), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPowerB.POWER_ID), p, 1); }

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
					if (p.hasPower(TributeToonPower.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSets(Tags.MONSTER, Tags.TOON), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPower.POWER_ID), p, 1); }
					if (p.hasPower(TributeToonPowerB.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSet(Tags.TOON), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPowerB.POWER_ID), p, 1); }

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
					return cardTribList;
				}
				else
				{
					empInstance.flag = true;
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
				if (p.hasPower(TributeToonPower.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSets(Tags.MONSTER, Tags.TOON), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPower.POWER_ID), p, 1); }
				if (p.hasPower(TributeToonPowerB.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSet(Tags.TOON), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPowerB.POWER_ID), p, 1); }

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
				return cardTribList;
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
		if (p.hasPower(TributeToonPower.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSets(Tags.MONSTER, Tags.TOON), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPower.POWER_ID), p, 1); }
		if (p.hasPower(TributeToonPowerB.POWER_ID) && tributes > 0) { AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(returnTrulyRandomFromSet(Tags.TOON), true, true, true, true, false, false, false, false, 1, 3, 0, 0, 0, 0)); reducePower(p.getPower(TributeToonPowerB.POWER_ID), p, 1); }

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
	}
	// =============== /TRIBUTE MONSTER FUNCTIONS/ =======================================================================================================================================================
	
	// =============== TRIBUTE SYNERGY FUNCTIONS =========================================================================================================================================================
	
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
	
	public static void megatypeTrib(DuelistCard tc)
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
		if (DuelistMod.debug) { DuelistMod.logger.info("Ran megatype tribute function, tributing card: " + tc.originalName); }
	}
	
	public static void rockSynTrib(DuelistCard tc)
	{
		if (tc.hasTag(Tags.ROCK))
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
				/*
				ArrayList<DuelistCard> stances = Util.getStanceChoices(true, false, true);
	        	tributingCard.addToBot(new WarriorTribAction(stances));*/
			}
		}
	}
	
	public static void dragonSynTrib(DuelistCard tributingCard)
	{
		if (tributingCard.hasTag(Tags.DRAGON))
		{
			if (!AbstractDungeon.player.hasPower(GravityAxePower.POWER_ID)) 
			{ 
				if (!AbstractDungeon.player.hasPower(MountainPower.POWER_ID)) 
				{ 
					applyPowerToSelf(new StrengthPower(AbstractDungeon.player, DuelistMod.dragonStr)); 
					//applyPowerToSelf(new LoseStrengthPower(AbstractDungeon.player, DuelistMod.dragonStr)); 
				}
				else 
				{ 
					applyPowerToSelf(new StrengthPower(AbstractDungeon.player, DuelistMod.dragonStr + 1)); 
					//applyPowerToSelf(new LoseStrengthPower(AbstractDungeon.player, DuelistMod.dragonStr)); 
				}
			}
			
			if (AbstractDungeon.player.hasRelic(DragonRelicB.ID))
			{
				if (DuelistMod.dragonRelicBFlipper) { drawRare(1, CardRarity.RARE); }
				DuelistMod.dragonRelicBFlipper = !DuelistMod.dragonRelicBFlipper;
			}
			
			if (player().hasPower(TyrantWingPower.POWER_ID))
			{
				TwoAmountPower power = (TwoAmountPower)player().getPower(TyrantWingPower.POWER_ID);
				power.amount2++;
				power.updateDescription();
			}
			
			if (player().hasPower(BoosterDragonPower.POWER_ID))
			{
				AbstractPower pow = player().getPower(BoosterDragonPower.POWER_ID);
				if (pow.amount > 0) { staticBlock(pow.amount); }
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
		if (tributingCard.hasTag(Tags.TOON)) 
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
					}
				}
			}
			AbstractDungeon.actionManager.addToBottom(new FiendFetchAction(p.discardPile, DuelistMod.fiendDraw)); 
		}
	}
	
	public static void aquaSynTrib(DuelistCard tributingCard)
	{
		if (tributingCard.hasTag(Tags.AQUA))
		{
			for (AbstractCard c : player().hand.group)
			{
				if (c instanceof DuelistCard)
				{
					DuelistCard dC = (DuelistCard)c;
					if (dC.baseSummons > 0)
					{
						dC.modifySummonsForTurn(DuelistMod.aquaInc);
					}
					
					if (player().hasRelic(AquaRelicB.ID) && dC.baseTributes > 0)
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
		if (tributingCard.hasTag(Tags.INSECT)) { poisonAllEnemies(player(), DuelistMod.insectPoisonDmg); }
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
					DuelistCard randSpellcaster = (DuelistCard)DuelistCard.returnTrulyRandomFromSets(Tags.MONSTER, Tags.SPELLCASTER);
					AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(randSpellcaster, true));
				}
			}
			
			if (p.hasPower(SpellbookLifePower.POWER_ID))
			{
				gainTempHP(p.getPower(SpellbookLifePower.POWER_ID).amount);
			}
		}
	}
	
	public static void zombieSynTrib(DuelistCard tributingCard)
	{
		
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
			return summonsInstance.MAX_SUMMONS;
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
			if (!(amount > 5 && p.hasRelic(MillenniumKey.ID))) { summonsInstance.MAX_SUMMONS = amount; DuelistMod.lastMaxSummons = amount; }
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
			if (summonsInstance.MAX_SUMMONS == 5 && p.hasRelic(MillenniumKey.ID)) { incremented = false; }
			else if (summonsInstance.MAX_SUMMONS + amount > 5 && p.hasRelic(MillenniumKey.ID)) { summonsInstance.MAX_SUMMONS = 5; DuelistMod.lastMaxSummons = 5; }
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
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { ((DuelistPotion)pot).onIncrement(amount, DuelistMod.lastMaxSummons); }}
		
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
	}
	// =============== /INCREMENT FUNCTIONS/ =======================================================================================================================================================
	
	
	
	// =============== RESUMMON FUNCTIONS =========================================================================================================================================================
	public void checkResummon()
	{
		DuelistMod.resummonsThisRun++;
		AbstractPlayer p = AbstractDungeon.player;
		for (AbstractRelic r : p.relics) { if (r instanceof DuelistRelic) { ((DuelistRelic)r).onResummon(this); }}
		for (AbstractOrb o : p.orbs) { if (o instanceof DuelistOrb) {  ((DuelistOrb)o).onResummon(this); }}
		for (AbstractPower pow : p.powers) { if (pow instanceof DuelistPower) { ((DuelistPower)pow).onResummon(this);}}
		for (AbstractCard c : p.hand.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onResummonWhileInHand(this); }}
		for (AbstractCard c : p.discardPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onResummonWhileInDiscard(this); }}
		for (AbstractCard c : p.drawPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onResummonWhileInDraw(this); }}
		for (AbstractCard c : p.exhaustPile.group) { if (c instanceof DuelistCard) { ((DuelistCard)c).onResummonWhileInExhaust(this); }}
		for (AbstractPotion pot : p.potions) { if (pot instanceof DuelistPotion) { ((DuelistPotion)pot).onResummon(this); }}
		if (this.hasTag(Tags.ZOMBIE)) { block(DuelistMod.zombieResummonBlock); DuelistMod.zombiesResummonedThisCombat++; DuelistMod.zombiesResummonedThisRun++; }
		if (AbstractDungeon.player.hasPower(CardSafePower.POWER_ID)) { drawTag(AbstractDungeon.player.getPower(CardSafePower.POWER_ID).amount, Tags.ZOMBIE); }
	}
	
	public static void fullResummon(DuelistCard cardCopy, boolean upgradeResummon, AbstractMonster target, boolean superFast)
	{
		if (cardCopy.hasTag(Tags.EXEMPT)) { Util.log("You're attemtping to Resummon " + cardCopy.name + ", which is Exempt, so nothing will happen."); }
		// We want to check for Vanity Fiend, Exempt card, or Mortality power - these things prevent Resummons
		// To check for Vanity Fiend, first we check for summons, if the player has summons we look in them for Vanity Fiend plus the power and tag
		if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID))
		{
			SummonPower instance = (SummonPower)AbstractDungeon.player.getPower(SummonPower.POWER_ID);
			if (!instance.isMonsterSummoned(new VanityFiend().originalName) && !cardCopy.hasTag(Tags.EXEMPT) && !AbstractDungeon.player.hasPower(MortalityPower.POWER_ID))
			{
				if (AbstractDungeon.player.hasPower(ResummonBonusPower.POWER_ID))
				{
					int amt = AbstractDungeon.player.getPower(ResummonBonusPower.POWER_ID).amount + 1;
					if (amt > 0 && AbstractDungeon.player.hasOrb()) 
					{
						for (AbstractOrb o : AbstractDungeon.player.orbs)
						{
							if (o instanceof Shadow)
							{
								Shadow shad = (Shadow)o;
								shad.triggerPassiveEffect();
							}
						}
					}
					for (int i = 0; i < amt; i++)
					{
						DuelistCard ref = cardCopy;
						cardCopy = (DuelistCard) cardCopy.makeStatEquivalentCopy();
						if (ref.fiendDeckDmgMod) { Util.log("Resummoned " + ref.name + " and it appears to be roided. DamageOriginal=" + ref.damage + ", DamageNew=" + cardCopy.damage); }
						if (!cardCopy.tags.contains(Tags.TRIBUTE)) { cardCopy.misc = 52; }
						if (upgradeResummon) { cardCopy.upgrade(); }
						cardCopy.freeToPlayOnce = true;
						cardCopy.applyPowers();
						cardCopy.purgeOnUse = true;
						cardCopy.dontTriggerOnUseCard = true;
						if (superFast) { AbstractDungeon.actionManager.addToTop(new QueueCardSuperFastAction(cardCopy, target)); }
						else { AbstractDungeon.actionManager.addToTop(new QueueCardSuperFastAction(cardCopy, target, 1.0F)); }
						if (cardCopy.damage > 0 && AbstractDungeon.player.hasPower(PenNibPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new ResummonModAction(cardCopy, true, false)); Util.log("Resummon cut " + cardCopy.name + "'s damage by half due to Flight or Pen Nib"); }
						cardCopy.onResummon(1);
						cardCopy.checkResummon();
					}
				}
				else
				{
					// Check for Shadow orbs
					if (AbstractDungeon.player.hasOrb()) { for (AbstractOrb o : AbstractDungeon.player.orbs) { if (o instanceof Shadow) { Shadow shad = (Shadow)o; shad.triggerPassiveEffect(); }}}
					
					// Copy card
					DuelistCard ref = cardCopy;
					cardCopy = (DuelistCard) cardCopy.makeStatEquivalentCopy();
					if (ref.fiendDeckDmgMod) { Util.log("Resummoned " + ref.name + " and it appears to be roided. DamageOriginal=" + ref.damage + ", DamageNew=" + cardCopy.damage); }
					
					// Remove tribute cost (unless card requires tributes to function, ex: Insect Queen)
					if (!cardCopy.tags.contains(Tags.TRIBUTE)) { cardCopy.misc = 52; }
					
					// Possible upgrade
					if (upgradeResummon) { cardCopy.upgrade(); }
					
					// Set card variables
					cardCopy.freeToPlayOnce = true;
					cardCopy.applyPowers();
					cardCopy.purgeOnUse = true;
					cardCopy.dontTriggerOnUseCard = true;
					
					// Queue card for Resummon
					if (superFast) { AbstractDungeon.actionManager.addToTop(new QueueCardSuperFastAction(cardCopy, target)); }
					else { AbstractDungeon.actionManager.addToTop(new QueueCardSuperFastAction(cardCopy, target, 1.0F)); }
					
					// Add Pen Nib check in top of queue before card is played, halves damage of any damage dealing cards so that pen nib doesn't appear to proc
					if (cardCopy.damage > 0 && AbstractDungeon.player.hasPower(PenNibPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new ResummonModAction(cardCopy, true, false)); Util.log("Resummon cut " + cardCopy.name + "'s damage by half due to Flight or Pen Nib"); }
					
					// Call Resummon effects from cards
					cardCopy.onResummon(1);
					cardCopy.checkResummon();
				}
				
			}		
			else if (AbstractDungeon.player.hasPower(MortalityPower.POWER_ID))
			{
				MortalityPower pow = (MortalityPower)AbstractDungeon.player.getPower(MortalityPower.POWER_ID);
				pow.triggerOnResummon();
			}
		}
		
		// If the player does not have summons, we skip the check for Vanity Fiend and just look for Mortality and Exempt
		else if (!cardCopy.hasTag(Tags.EXEMPT) && !AbstractDungeon.player.hasPower(MortalityPower.POWER_ID))
		{
			if (AbstractDungeon.player.hasPower(ResummonBonusPower.POWER_ID))
			{
				// Check for Shadow orbs
				int amt = AbstractDungeon.player.getPower(ResummonBonusPower.POWER_ID).amount + 1;
				if (amt > 0 && AbstractDungeon.player.hasOrb()) { for (AbstractOrb o : AbstractDungeon.player.orbs) { if (o instanceof Shadow) { Shadow shad = (Shadow)o; shad.triggerPassiveEffect(); }}}
				for (int i = 0; i < amt; i++)
				{
					DuelistCard ref = cardCopy;
					cardCopy = (DuelistCard) cardCopy.makeStatEquivalentCopy();
					if (ref.fiendDeckDmgMod) { Util.log("Resummoned " + ref.name + " and it appears to be roided. DamageOriginal=" + ref.damage + ", DamageNew=" + cardCopy.damage); }
					if (!cardCopy.tags.contains(Tags.TRIBUTE)) { cardCopy.misc = 52; }
					if (upgradeResummon) { cardCopy.upgrade(); }
					cardCopy.freeToPlayOnce = true;
					cardCopy.applyPowers();
					cardCopy.purgeOnUse = true;
					cardCopy.dontTriggerOnUseCard = true;
					if (superFast) { AbstractDungeon.actionManager.addToTop(new QueueCardSuperFastAction(cardCopy, target)); }
					else { AbstractDungeon.actionManager.addToTop(new QueueCardSuperFastAction(cardCopy, target, 1.0F)); }
					if (cardCopy.damage > 0 && AbstractDungeon.player.hasPower(PenNibPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new ResummonModAction(cardCopy, true, false)); Util.log("Resummon cut " + cardCopy.name + "'s damage by half due to Flight or Pen Nib"); }
					cardCopy.onResummon(1);
					cardCopy.checkResummon();
				}
			}
			else
			{
				// Check for Shadow orbs
				if (AbstractDungeon.player.hasOrb()) { for (AbstractOrb o : AbstractDungeon.player.orbs) { if (o instanceof Shadow) { Shadow shad = (Shadow)o; shad.triggerPassiveEffect(); }}}
				DuelistCard ref = cardCopy;
				cardCopy = (DuelistCard) cardCopy.makeStatEquivalentCopy();
				if (ref.fiendDeckDmgMod) { Util.log("Resummoned " + ref.name + " and it appears to be roided. DamageOriginal=" + ref.damage + ", DamageNew=" + cardCopy.damage); }
				if (!cardCopy.tags.contains(Tags.TRIBUTE)) { cardCopy.misc = 52; }
				if (upgradeResummon) { cardCopy.upgrade(); }
				cardCopy.freeToPlayOnce = true;
				cardCopy.applyPowers();
				cardCopy.purgeOnUse = true;
				cardCopy.dontTriggerOnUseCard = true;
				if (superFast) { AbstractDungeon.actionManager.addToTop(new QueueCardSuperFastAction(cardCopy, target)); }
				else { AbstractDungeon.actionManager.addToTop(new QueueCardSuperFastAction(cardCopy, target, 1.0F)); }
				if (cardCopy.damage > 0 && AbstractDungeon.player.hasPower(PenNibPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new ResummonModAction(cardCopy, true, false)); Util.log("Resummon cut " + cardCopy.name + "'s damage by half due to Flight or Pen Nib"); }
				cardCopy.onResummon(1);
				cardCopy.checkResummon();
			}
		}
		
		// This triggers if all other blocks of code here are skipped but the player does have Mortality debuff
		// We need to decrement the debuff in this case
		else if (AbstractDungeon.player.hasPower(MortalityPower.POWER_ID))
		{
			MortalityPower pow = (MortalityPower)AbstractDungeon.player.getPower(MortalityPower.POWER_ID);
			pow.triggerOnResummon();
		}
		
		if (DuelistMod.mirrorLadybug)
		{
			AbstractDungeon.actionManager.addToBottom(new QueueCardSuperFastAction(new MirrorLadybugFixer(), target)); 
		}
	}
	
	public static void polyResummon(DuelistCard cardCopy, boolean upgradeResummon, AbstractMonster target, boolean superFast)
	{
		if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID))
		{
			SummonPower instance = (SummonPower)AbstractDungeon.player.getPower(SummonPower.POWER_ID);
			if (!instance.isMonsterSummoned(new VanityFiend().originalName) && !AbstractDungeon.player.hasPower(MortalityPower.POWER_ID))
			{
				if (AbstractDungeon.player.hasPower(ResummonBonusPower.POWER_ID))
				{
					int amt = AbstractDungeon.player.getPower(ResummonBonusPower.POWER_ID).amount + 1;
					for (int i = 0; i < amt; i++)
					{
						// Check for Shadow orbs
						if (amt > 0 && AbstractDungeon.player.hasOrb()) { for (AbstractOrb o : AbstractDungeon.player.orbs) { if (o instanceof Shadow) { Shadow shad = (Shadow)o; shad.triggerPassiveEffect(); }}}
						DuelistCard ref = cardCopy;
						cardCopy = (DuelistCard) cardCopy.makeStatEquivalentCopy();
						if (ref.fiendDeckDmgMod) { Util.log("Resummoned " + ref.name + " and it appears to be roided. DamageOriginal=" + ref.damage + ", DamageNew=" + cardCopy.damage); }
						if (!cardCopy.tags.contains(Tags.TRIBUTE)) { cardCopy.misc = 52; }
						if (upgradeResummon) { cardCopy.upgrade(); }
						cardCopy.freeToPlayOnce = true;
						cardCopy.applyPowers();
						cardCopy.purgeOnUse = true;
						cardCopy.dontTriggerOnUseCard = true;
						if (superFast) { AbstractDungeon.actionManager.addToTop(new QueueCardSuperFastAction(cardCopy, target)); }
						else { AbstractDungeon.actionManager.addToTop(new QueueCardSuperFastAction(cardCopy, target, 1.0F)); }
						if (cardCopy.damage > 0 && AbstractDungeon.player.hasPower(PenNibPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new ResummonModAction(cardCopy, true, false)); Util.log("Resummon cut " + cardCopy.name + "'s damage by half due to Flight or Pen Nib"); }
						cardCopy.onResummon(1);
						cardCopy.checkResummon();
					}
				}
				else
				{
					// Check for Shadow orbs
					if (AbstractDungeon.player.hasOrb()) { for (AbstractOrb o : AbstractDungeon.player.orbs) { if (o instanceof Shadow) { Shadow shad = (Shadow)o; shad.triggerPassiveEffect(); }}}
					DuelistCard ref = cardCopy;
					cardCopy = (DuelistCard) cardCopy.makeStatEquivalentCopy();
					if (ref.fiendDeckDmgMod) { Util.log("Resummoned " + ref.name + " and it appears to be roided. DamageOriginal=" + ref.damage + ", DamageNew=" + cardCopy.damage); }
					if (!cardCopy.tags.contains(Tags.TRIBUTE)) { cardCopy.misc = 52; }
					if (upgradeResummon) { cardCopy.upgrade(); }
					cardCopy.freeToPlayOnce = true;
					cardCopy.applyPowers();
					cardCopy.purgeOnUse = true;
					cardCopy.dontTriggerOnUseCard = true;
					if (superFast) { AbstractDungeon.actionManager.addToTop(new QueueCardSuperFastAction(cardCopy, target)); }
					else { AbstractDungeon.actionManager.addToTop(new QueueCardSuperFastAction(cardCopy, target, 1.0F)); }
					if (cardCopy.damage > 0 && AbstractDungeon.player.hasPower(PenNibPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new ResummonModAction(cardCopy, true, false)); Util.log("Resummon cut " + cardCopy.name + "'s damage by half due to Flight or Pen Nib"); }
					cardCopy.onResummon(1);
					cardCopy.checkResummon();
				}
			}	
			else if (AbstractDungeon.player.hasPower(MortalityPower.POWER_ID))
			{
				MortalityPower pow = (MortalityPower)AbstractDungeon.player.getPower(MortalityPower.POWER_ID);
				pow.triggerOnResummon();
			}
		}
		else if (!AbstractDungeon.player.hasPower(MortalityPower.POWER_ID))
		{
			if (AbstractDungeon.player.hasPower(ResummonBonusPower.POWER_ID))
			{
				int amt = AbstractDungeon.player.getPower(ResummonBonusPower.POWER_ID).amount + 1;
				for (int i = 0; i < amt; i++)
				{
					DuelistCard ref = cardCopy;
					cardCopy = (DuelistCard) cardCopy.makeStatEquivalentCopy();
					if (ref.fiendDeckDmgMod) { Util.log("Resummoned " + ref.name + " and it appears to be roided. DamageOriginal=" + ref.damage + ", DamageNew=" + cardCopy.damage); }
					if (!cardCopy.tags.contains(Tags.TRIBUTE)) { cardCopy.misc = 52; }
					if (upgradeResummon) { cardCopy.upgrade(); }
					cardCopy.freeToPlayOnce = true;
					cardCopy.applyPowers();
					cardCopy.purgeOnUse = true;
					cardCopy.dontTriggerOnUseCard = true;
					if (superFast) { AbstractDungeon.actionManager.addToTop(new QueueCardSuperFastAction(cardCopy, target)); }
					else { AbstractDungeon.actionManager.addToTop(new QueueCardSuperFastAction(cardCopy, target, 1.0F)); }
					if (cardCopy.damage > 0 && AbstractDungeon.player.hasPower(PenNibPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new ResummonModAction(cardCopy, true, false)); Util.log("Resummon cut " + cardCopy.name + "'s damage by half due to Flight or Pen Nib"); }
					cardCopy.onResummon(1);
					cardCopy.checkResummon();					
				}
			}
			else
			{
				DuelistCard ref = cardCopy;
				cardCopy = (DuelistCard) cardCopy.makeStatEquivalentCopy();
				if (ref.fiendDeckDmgMod) { Util.log("Resummoned " + ref.name + " and it appears to be roided. DamageOriginal=" + ref.damage + ", DamageNew=" + cardCopy.damage); }
				if (!cardCopy.tags.contains(Tags.TRIBUTE)) { cardCopy.misc = 52; }
				if (upgradeResummon) { cardCopy.upgrade(); }
				cardCopy.freeToPlayOnce = true;
				cardCopy.applyPowers();
				cardCopy.purgeOnUse = true;
				cardCopy.dontTriggerOnUseCard = true;
				if (superFast) { AbstractDungeon.actionManager.addToTop(new QueueCardSuperFastAction(cardCopy, target)); }
				else { AbstractDungeon.actionManager.addToTop(new QueueCardSuperFastAction(cardCopy, target, 1.0F)); }
				if (cardCopy.damage > 0 && AbstractDungeon.player.hasPower(PenNibPower.POWER_ID)) { AbstractDungeon.actionManager.addToTop(new ResummonModAction(cardCopy, true, false)); Util.log("Resummon cut " + cardCopy.name + "'s damage by half due to Flight or Pen Nib"); }
				cardCopy.onResummon(1);
				cardCopy.checkResummon();
			}
		}
		else 
		{
			MortalityPower pow = (MortalityPower)AbstractDungeon.player.getPower(MortalityPower.POWER_ID);
			pow.triggerOnResummon();
		}
	}
	
	public static void playNoResummon(DuelistCard cardCopy, boolean upgradeResummon, AbstractCreature target, boolean superFast)
	{
		if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID))
		{
			SummonPower instance = (SummonPower)AbstractDungeon.player.getPower(SummonPower.POWER_ID);
			if (!instance.isMonsterSummoned(new VanityFiend().originalName) && !cardCopy.hasTag(Tags.EXEMPT))
			{
				cardCopy = (DuelistCard) cardCopy.makeStatEquivalentCopy();
				if (!cardCopy.tags.contains(Tags.TRIBUTE)) { cardCopy.misc = 52; }
				if (upgradeResummon) { cardCopy.upgrade(); }
				cardCopy.freeToPlayOnce = true;
				cardCopy.applyPowers();
				cardCopy.purgeOnUse = true;
				cardCopy.dontTriggerOnUseCard = true;
				if (superFast) { AbstractDungeon.actionManager.addToTop(new QueueCardSuperFastAction(cardCopy, target)); }
				else { AbstractDungeon.actionManager.addToTop(new QueueCardSuperFastAction(cardCopy, target, 1.0F)); }
			}		
		}
		else if (!cardCopy.hasTag(Tags.EXEMPT))
		{
			cardCopy = (DuelistCard) cardCopy.makeStatEquivalentCopy();
			if (!cardCopy.tags.contains(Tags.TRIBUTE)) { cardCopy.misc = 52; }
			if (upgradeResummon) { cardCopy.upgrade(); }
			cardCopy.freeToPlayOnce = true;
			cardCopy.applyPowers();
			cardCopy.purgeOnUse = true;
			cardCopy.dontTriggerOnUseCard = true;
			if (superFast) { AbstractDungeon.actionManager.addToTop(new QueueCardSuperFastAction(cardCopy, target)); }
			else { AbstractDungeon.actionManager.addToTop(new QueueCardSuperFastAction(cardCopy, target, 1.0F)); }
		}
	}
	
	public static void playNoResummon(BuffCard cardCopy, boolean upgradeResummon, AbstractMonster target, boolean superFast)
	{			
		cardCopy.freeToPlayOnce = true;
		cardCopy.applyPowers();
		cardCopy.purgeOnUse = true;
		cardCopy.dontTriggerOnUseCard = true;
		if (superFast) { AbstractDungeon.actionManager.addToTop(new QueueCardSuperFastAction(cardCopy, target)); }
		else { AbstractDungeon.actionManager.addToTop(new QueueCardSuperFastAction(cardCopy, target, 1.0F)); }		
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
			int indexOfTribText = this.rawDescription.indexOf("Summon");
			int modIndex = 21;
			int indexOfNL = indexOfTribText + 21;
			if (this.rawDescription.substring(indexOfNL, indexOfNL + 4).equals(" NL ")) { modIndex += 4; }
			if (indexOfTribText > -1)
			{
				String newDesc = this.rawDescription.substring(0, indexOfTribText) + this.rawDescription.substring(indexOfTribText + modIndex);
				this.rawDescription = newDesc;
				this.originalDescription = newDesc;
				if (DuelistMod.debug) { System.out.println(this.originalName + " made a string: " + newDesc + " this.baseSummons + add : " + this.baseSummons + add); }
			}
		}
		else { this.baseSummons = this.summons += add; }
		this.isSummonsModified = true;
		this.isSummonModPerm = true;
		this.permSummonChange += add;
		this.initializeDescription();
	}
	
	public void modifySummonsForTurn(int add)
	{
		if (this.summons + add <= 0)
		{
			this.summons = 0;
			this.summonsForTurn = 0;
			int indexOfTribText = this.rawDescription.indexOf("Summon");
			int modIndex = 21;
			int indexOfNL = indexOfTribText + 21;
			if (this.rawDescription.substring(indexOfNL, indexOfNL + 4).equals(" NL ")) { modIndex += 4; }
			if (indexOfTribText > -1)
			{
				String newDesc = this.rawDescription.substring(0, indexOfTribText) + this.rawDescription.substring(indexOfTribText + modIndex);
				this.originalDescription = this.rawDescription;
				this.rawDescription = newDesc;
				if (DuelistMod.debug) { System.out.println(this.originalName + " made a string: " + newDesc + " this.summons + add : " + this.summons + add); }
			}
		}
		else { this.originalDescription = this.rawDescription; this.summonsForTurn = this.summons += add; }
		this.isSummonsModifiedForTurn = true;		
		this.isSummonsModified = true;
		this.initializeDescription();
	}
	
	public void modifySummons(int add)
	{
		if (this.summons + add <= 0)
		{
			this.summons = 0;
			int indexOfTribText = this.rawDescription.indexOf("Summon");
			int modIndex = 21;
			int indexOfNL = indexOfTribText + 21;
			if (this.rawDescription.substring(indexOfNL, indexOfNL + 4).equals(" NL ")) { modIndex += 4; }
			if (indexOfTribText > -1)
			{
				this.originalDescription = this.rawDescription;
				String newDesc = this.rawDescription.substring(0, indexOfTribText) + this.rawDescription.substring(indexOfTribText + modIndex);
				this.rawDescription = newDesc;
				if (DuelistMod.debug) { System.out.println(this.originalName + " made a string: " + newDesc + " this.baseSummons + add : " + this.baseSummons + add); }
			}
		}
		else { this.summons += add; }		
		this.isSummonsModified = true;
		this.initializeDescription();
	}
	
	public void setSummons(int set)
	{
		if (set <= 0)
		{
			this.baseSummons = this.summons = 0;
			int indexOfTribText = this.rawDescription.indexOf("Summon");
			int modIndex = 21;
			int indexOfNL = indexOfTribText + 21;
			if (this.rawDescription.substring(indexOfNL, indexOfNL + 4).equals(" NL ")) { modIndex += 4; }
			if (indexOfTribText > -1)
			{
				this.originalDescription = this.rawDescription;
				String newDesc = this.rawDescription.substring(0, indexOfTribText) + this.rawDescription.substring(indexOfTribText + modIndex);
				this.rawDescription = newDesc;
				if (DuelistMod.debug) { System.out.println(this.originalName + " made a string: " + newDesc + " this.baseSummons + add : " + this.summons); }
			}
		}
		else { this.baseSummons = this.summons = set; }		
		this.isSummonsModified = true;
		this.initializeDescription();
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
	
	// =============== TRIBUTE MODIFICATION FUNCTIONS =========================================================================================================================================================
	public void changeTributesInBattle(int addAmount, boolean combat)
	{
		AbstractDungeon.actionManager.addToTop(new ModifyTributeAction(this, addAmount, combat));
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
			int indexOfTribText = this.rawDescription.indexOf("Tribute");
			int modIndex = 22;
			int indexOfNL = indexOfTribText + 22;
			if (this.rawDescription.substring(indexOfNL, indexOfNL + 4).equals(" NL ")) { modIndex += 4; }
			if (indexOfTribText > -1)
			{
				String newDesc = this.rawDescription.substring(0, indexOfTribText) + this.rawDescription.substring(indexOfTribText + modIndex);
				this.rawDescription = newDesc;
				this.originalDescription = newDesc;
				if (DuelistMod.debug) { System.out.println(this.originalName + " made a string: " + newDesc + " this.baseTributes + add : " + this.baseTributes + add); }
			}
		}
		else { this.baseTributes = this.tributes += add; }
		this.isTributesModified = true;
		this.isTribModPerm = true;
		this.permTribChange += add;
		this.initializeDescription();
	}
	
	public void modifyTributesForTurn(int add)
	{
		if (this.tributes + add <= 0)
		{
			this.tributesForTurn = 0;
			this.tributes = 0;
			int indexOfTribText = this.rawDescription.indexOf("Tribute");
			int modIndex = 22;
			int indexOfNL = indexOfTribText + 22;
			
			if (indexOfTribText > -1)
			{
				if (this.rawDescription.substring(indexOfNL, indexOfNL + 4).equals(" NL ")) { modIndex += 4; }
				String newDesc = this.rawDescription.substring(0, indexOfTribText) + this.rawDescription.substring(indexOfTribText + modIndex);
				this.originalDescription = this.rawDescription;
				this.rawDescription = newDesc;
				if (DuelistMod.debug) { System.out.println(this.originalName + " made a string: " + newDesc + " this.tributes + add : " + this.tributes + add); }
			}
		}
		else { this.originalDescription = this.rawDescription; this.tributesForTurn = this.tributes += add; }
		this.isTributesModifiedForTurn = true;
		this.isTributesModified = true;
		this.initializeDescription();
	}

	public void modifyTributes(int add)
	{
		
		if (this.tributes + add <= 0)
		{
			this.tributes = this.baseTributes = 0;
			int indexOfTribText = this.rawDescription.indexOf("Tribute");
			int modIndex = 22;
			int indexOfNL = indexOfTribText + 22;
			if (indexOfTribText > -1)
			{
				if (this.rawDescription.substring(indexOfNL, indexOfNL + 4).equals(" NL ")) { modIndex += 4; }
				this.originalDescription = this.rawDescription;
				String newDesc = this.rawDescription.substring(0, indexOfTribText) + this.rawDescription.substring(indexOfTribText + modIndex);
				this.rawDescription = newDesc;
				if (DuelistMod.debug) { System.out.println(this.originalName + " made a string: " + newDesc + " this.baseTributes + add : " + this.baseTributes + add); }
			}			
		}
		else { this.baseTributes = this.tributes += add; }
		this.isTributesModified = true; 
		this.initializeDescription();
	}
	
	public void setTributes(int set)
	{
		if (set <= 0)
		{
			this.baseTributes = this.tributes = 0;
			int indexOfTribText = this.rawDescription.indexOf("Tribute");
			int modIndex = 22;
			int indexOfNL = indexOfTribText + 22;
			if (this.rawDescription.substring(indexOfNL, indexOfNL + 4).equals(" NL ")) { modIndex += 4; }
			if (indexOfTribText > -1)
			{
				this.originalDescription = this.rawDescription;
				String newDesc = this.rawDescription.substring(0, indexOfTribText) + this.rawDescription.substring(indexOfTribText + modIndex);
				this.rawDescription = newDesc;
				if (DuelistMod.debug) { System.out.println(this.originalName + " made a string: " + newDesc + " this.tributes : " + this.tributes); }
			}
		}
		else { this.baseTributes = this.tributes = set; }
		this.isTributesModified = true; 
		this.initializeDescription();
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
	public static void checkSplash()
	{
		if (AbstractDungeon.player.hasOrb())
		{
			for (AbstractOrb o : AbstractDungeon.player.orbs)
			{
				if (o instanceof Splash)
				{
					Splash ref = (Splash)o;
					ref.triggerPassiveEffect();
					if (ref.gpcCheck()) { ref.triggerPassiveEffect(); }
				}
			}
		}
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
		AbstractDungeon.actionManager.addToTop(new EvokeAllOrbsAction());
	}
	
	public static void evoke(int amount)
	{
		AbstractDungeon.actionManager.addToTop(new EvokeOrbAction(amount));
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
	public static DuelistCard returnRandomTributeFromSets(CardTags tag, CardTags tagB, boolean seeded, boolean fromCardPoolOnly, boolean checkBothTags)
	{
		ArrayList<DuelistCard> tribCards = new ArrayList<DuelistCard>();
		if (!fromCardPoolOnly)
		{
			for (DuelistCard c : DuelistMod.myCards)
			{
				if (c.tributes > 0 && c.hasTag(tag) && c.hasTag(tagB) && !c.hasTag(Tags.NEVER_GENERATE))
				{
					tribCards.add((DuelistCard) c.makeStatEquivalentCopy());
				}
			}
		}
		else
		{
			if (AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST))
			{
				for (AbstractCard c : DuelistMod.coloredCards)
				{
					DuelistCard dC = (DuelistCard)c;
					if (checkBothTags)
					{
						if (dC.tributes > 0 && c.hasTag(tag) && c.hasTag(tagB) && !c.hasTag(Tags.NEVER_GENERATE))
						{
							tribCards.add((DuelistCard) c.makeStatEquivalentCopy());
						}
					}
					else
					{
						if ((dC.tributes > 0 && (c.hasTag(tag) || c.hasTag(tagB))) && !c.hasTag(Tags.NEVER_GENERATE))
						{
							tribCards.add((DuelistCard) c.makeStatEquivalentCopy());
						}
					}
				}
			}
			else
			{
				returnRandomTributeFromSets(tag, tagB, seeded, false, checkBothTags);
			}
		}
		
		if (seeded) { return tribCards.get(AbstractDungeon.cardRandomRng.random(tribCards.size() - 1)); }
		else { return tribCards.get(ThreadLocalRandom.current().nextInt(0, tribCards.size())); }
	}
	
	public static DuelistCard returnRandomTributeFromSet(CardTags tag, boolean seeded, boolean fromCardPoolOnly)
	{
		ArrayList<DuelistCard> tribCards = new ArrayList<DuelistCard>();
		if (!fromCardPoolOnly)
		{
			for (DuelistCard c : DuelistMod.myCards)
			{
				if (c.tributes > 0 && c.hasTag(tag) && !c.hasTag(Tags.NEVER_GENERATE))
				{
					tribCards.add((DuelistCard) c.makeStatEquivalentCopy());
				}
			}
		}
		else
		{
			for (AbstractCard c : DuelistMod.coloredCards)
			{
				DuelistCard dC = (DuelistCard)c;
				if (dC.tributes > 0 && c.hasTag(tag) && !c.hasTag(Tags.NEVER_GENERATE))
				{
					tribCards.add((DuelistCard) c.makeStatEquivalentCopy());
				}
				
			}
		}
		
		if (seeded) { return tribCards.get(AbstractDungeon.cardRandomRng.random(tribCards.size() - 1)); }
		else { return tribCards.get(ThreadLocalRandom.current().nextInt(0, tribCards.size())); }
	}
	
	public static DuelistCard returnRandomTribute(boolean seeded, boolean fromCardPoolOnly)
	{
		ArrayList<DuelistCard> tribCards = new ArrayList<DuelistCard>();
		if (!fromCardPoolOnly)
		{
			for (DuelistCard c : DuelistMod.myCards)
			{
				if (c.tributes > 0 && !c.hasTag(Tags.NEVER_GENERATE))
				{
					tribCards.add((DuelistCard) c.makeStatEquivalentCopy());
				}
			}
		}
		else
		{
			for (AbstractCard c : DuelistMod.coloredCards)
			{
				DuelistCard dC = (DuelistCard)c;
				if (dC.tributes > 0 && !c.hasTag(Tags.NEVER_GENERATE))
				{
					tribCards.add((DuelistCard) c.makeStatEquivalentCopy());
				}
				
			}
		}
		if (seeded) { return tribCards.get(AbstractDungeon.cardRandomRng.random(tribCards.size() - 1)); }
		else { return tribCards.get(ThreadLocalRandom.current().nextInt(0, tribCards.size())); }
	}
	
	public static DuelistCard returnRandomFromArray(ArrayList<DuelistCard> tributeList)
	{
		return tributeList.get(AbstractDungeon.cardRandomRng.random(tributeList.size() - 1));
	}

	public static AbstractCard returnRandomFromArrayAbstract(ArrayList<AbstractCard> tributeList)
	{
		return tributeList.get(AbstractDungeon.cardRandomRng.random(tributeList.size() - 1));
	}
	
	public static AbstractCard returnTrulyRandomFromSetUnseeded(CardTags setToFindFrom) 
	{
		ArrayList<AbstractCard> dragonGroup = new ArrayList<>();
		for (DuelistCard card : DuelistMod.myCards)
		{
			if (card.hasTag(setToFindFrom) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE)) 
			{
				dragonGroup.add(card.makeCopy());
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
			AbstractCard returnable = dragonGroup.get(ThreadLocalRandom.current().nextInt(0, dragonGroup.size()));
			return returnable;
		}
		else
		{
			return new Token();
		}
	}

	public static AbstractCard returnTrulyRandomFromSet(CardTags setToFindFrom) 
	{
		ArrayList<AbstractCard> dragonGroup = new ArrayList<>();
		for (DuelistCard card : DuelistMod.myCards)
		{
			if (card.hasTag(setToFindFrom) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE)) 
			{
				dragonGroup.add(card.makeCopy());
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
	
	public static AbstractCard returnTrulyRandomFromType(CardType type) 
	{
		ArrayList<AbstractCard> dragonGroup = new ArrayList<>();
		for (DuelistCard card : DuelistMod.myCards)
		{
			if (card.type.equals(type) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE)) 
			{
				dragonGroup.add(card.makeCopy());
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
	
	public static AbstractCard returnTrulyRandomFromTypeInCombat(CardType type, boolean basic) 
	{
		ArrayList<AbstractCard> dragonGroup = new ArrayList<>();
		for (AbstractCard card : DuelistMod.coloredCards)
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
			AbstractCard returnable = dragonGroup.get(ThreadLocalRandom.current().nextInt(0, dragonGroup.size()));
			while (returnable.hasTag(Tags.NEVER_GENERATE)) { returnable = dragonGroup.get(ThreadLocalRandom.current().nextInt(0, dragonGroup.size())); }
			return returnable;
		}
		else
		{
			return new Token();
		}
	}
	
	public static AbstractCard returnTrulyRandomFromSet(CardTags setToFindFrom, boolean special) 
	{
		ArrayList<AbstractCard> dragonGroup = new ArrayList<>();
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
	
	public static AbstractCard returnTrulyRandomFromSet(CardTags setToFindFrom, boolean special, boolean allowMegatype) 
	{
		ArrayList<AbstractCard> dragonGroup = new ArrayList<>();
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
			AbstractCard returnable = dragonGroup.get(ThreadLocalRandom.current().nextInt(0, dragonGroup.size()));
			while (returnable.hasTag(Tags.NEVER_GENERATE)) { returnable = dragonGroup.get(ThreadLocalRandom.current().nextInt(0, dragonGroup.size())); }
			return returnable;
		}
		else
		{
			return new Token();
		}
	}

	public static AbstractCard returnTrulyRandomInCombatFromSet(CardTags setToFindFrom) 
	{
		if (AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST))
		{
			if (DuelistMod.coloredCards.size() > 0)
			{
				AbstractCard c = DuelistMod.coloredCards.get(AbstractDungeon.cardRandomRng.random(DuelistMod.coloredCards.size() - 1));
				while (!c.hasTag(setToFindFrom) || c.hasTag(Tags.NEVER_GENERATE)) { c = DuelistMod.coloredCards.get(AbstractDungeon.cardRandomRng.random(DuelistMod.coloredCards.size() - 1)); }
				return c;
			}
			else
			{
				AbstractCard c = DuelistMod.myCards.get(AbstractDungeon.cardRandomRng.random(DuelistMod.myCards.size() - 1));
				while (!c.hasTag(setToFindFrom) || c.hasTag(Tags.NEVER_GENERATE)) { c = DuelistMod.myCards.get(AbstractDungeon.cardRandomRng.random(DuelistMod.myCards.size() - 1)); }
				return c;
			}
		}
		else
		{
			return returnTrulyRandomFromSet(setToFindFrom);
		}
	}
	
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
			while (returnable.hasTag(Tags.NEVER_GENERATE)) { returnable = dragonGroup.get(ThreadLocalRandom.current().nextInt(0, dragonGroup.size())); }
			return returnable;
		}
		else
		{
			return new Token();
		}
	}
	
	public static AbstractCard returnTrulyRandomDuelistPowerForRandomDecks() 
	{
		ArrayList<AbstractCard> dragonGroup = new ArrayList<>();
		for (AbstractCard card : DuelistMod.powersForRandomDecks)
		{
			if (card instanceof DuelistCard && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE)) 
			{
				dragonGroup.add(card.makeCopy());
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
	

	public static AbstractCard returnTrulyRandomDuelistCard() 
	{
		ArrayList<AbstractCard> dragonGroup = new ArrayList<>();
		for (DuelistCard card : DuelistMod.myCards)
		{
			if (card instanceof DuelistCard && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE)) 
			{
				dragonGroup.add(card.makeCopy());
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
	
	public void metronomeAction(AbstractMonster m)
	{
		for (int i = 0; i < this.magicNumber; i++)
    	{
    		DuelistCard randy = returnMetronomeCard(this.upgraded, this);
        	if (!(randy instanceof CancelCard)) { fullResummon(randy, this.upgraded, m, false); }
    	}    
	}
	
	public static DuelistCard returnMetronomeCard(boolean upgraded, DuelistCard metronome) 
	{		
		if (metronome instanceof Metronome)
		{
			ArrayList<DuelistCard> cardsToPullFrom = new ArrayList<DuelistCard>();
			for (DuelistCard c : DuelistMod.myCards) { cardsToPullFrom.add((DuelistCard) c.makeCopy()); }
			for (DuelistCard c : DuelistMod.orbCards) { cardsToPullFrom.add((DuelistCard) c.makeCopy()); }
			if (upgraded) { for (DuelistCard c : DuelistMod.rareCards) { cardsToPullFrom.add((DuelistCard) c.makeCopy()); } }
			DuelistCard c = (DuelistCard) cardsToPullFrom.get(AbstractDungeon.cardRandomRng.random(cardsToPullFrom.size() - 1)).makeCopy();
			while (c.hasTag(Tags.NEVER_GENERATE) || c.hasTag(Tags.EXEMPT) || c.hasTag(Tags.NO_METRONOME)) { c = (DuelistCard) cardsToPullFrom.get(AbstractDungeon.cardRandomRng.random(cardsToPullFrom.size() - 1)).makeCopy(); }
			return (DuelistCard) c.makeCopy();
		}
		else if (metronome instanceof SkillMetronome)
		{
			ArrayList<DuelistCard> cardsToPullFrom = new ArrayList<DuelistCard>();
			for (DuelistCard c : DuelistMod.myCards) { if (c.type.equals(CardType.SKILL) && !c.hasTag(Tags.NEVER_GENERATE) && !c.hasTag(Tags.EXEMPT) && !c.hasTag(Tags.NO_METRONOME) && !c.rarity.equals(CardRarity.SPECIAL) && !c.rarity.equals(CardRarity.BASIC)) { cardsToPullFrom.add((DuelistCard) c.makeCopy()); }}
			for (DuelistCard c : DuelistMod.orbCards) { cardsToPullFrom.add((DuelistCard) c.makeCopy()); }
			DuelistCard c = (DuelistCard) cardsToPullFrom.get(AbstractDungeon.cardRandomRng.random(cardsToPullFrom.size() - 1)).makeCopy();
			return (DuelistCard) c.makeCopy();
		}
		else if (metronome instanceof RareSkillMetronome)
		{
			ArrayList<DuelistCard> cardsToPullFrom = new ArrayList<DuelistCard>();
			for (DuelistCard c : DuelistMod.myCards) { if (c.type.equals(CardType.SKILL) && !c.hasTag(Tags.NEVER_GENERATE) && !c.hasTag(Tags.EXEMPT) && !c.hasTag(Tags.NO_METRONOME) && c.rarity.equals(CardRarity.RARE)) { cardsToPullFrom.add((DuelistCard) c.makeCopy()); }}
			DuelistCard c = (DuelistCard) cardsToPullFrom.get(AbstractDungeon.cardRandomRng.random(cardsToPullFrom.size() - 1)).makeCopy();
			return (DuelistCard) c.makeCopy();
		}
		else if (metronome instanceof AttackMetronome)
		{
			ArrayList<DuelistCard> cardsToPullFrom = new ArrayList<DuelistCard>();
			for (DuelistCard c : DuelistMod.myCards) { if (c.type.equals(CardType.ATTACK) && !c.hasTag(Tags.NEVER_GENERATE) && !c.hasTag(Tags.EXEMPT) && !c.hasTag(Tags.NO_METRONOME) && !c.rarity.equals(CardRarity.SPECIAL) && !c.rarity.equals(CardRarity.BASIC)) { cardsToPullFrom.add((DuelistCard) c.makeCopy()); }}
			DuelistCard c = (DuelistCard) cardsToPullFrom.get(AbstractDungeon.cardRandomRng.random(cardsToPullFrom.size() - 1)).makeCopy();
			return (DuelistCard) c.makeCopy();
		}
		else if (metronome instanceof RareAttackMetronome)
		{
			ArrayList<DuelistCard> cardsToPullFrom = new ArrayList<DuelistCard>();
			for (DuelistCard c : DuelistMod.myCards) { if (c.type.equals(CardType.ATTACK) && !c.hasTag(Tags.NEVER_GENERATE) && !c.hasTag(Tags.EXEMPT) && !c.hasTag(Tags.NO_METRONOME) && c.rarity.equals(CardRarity.RARE)) { cardsToPullFrom.add((DuelistCard) c.makeCopy()); }}
			DuelistCard c = (DuelistCard) cardsToPullFrom.get(AbstractDungeon.cardRandomRng.random(cardsToPullFrom.size() - 1)).makeCopy();
			return (DuelistCard) c.makeCopy();
		}
		else if (metronome instanceof PowerMetronome)
		{
			ArrayList<DuelistCard> cardsToPullFrom = new ArrayList<DuelistCard>();
			for (DuelistCard c : DuelistMod.myCards) { if (c.type.equals(CardType.POWER) && !c.hasTag(Tags.NEVER_GENERATE) && !c.hasTag(Tags.EXEMPT) && !c.hasTag(Tags.NO_METRONOME) && !c.rarity.equals(CardRarity.SPECIAL) && !c.rarity.equals(CardRarity.BASIC)) { cardsToPullFrom.add((DuelistCard) c.makeCopy()); }}
			DuelistCard c = (DuelistCard) cardsToPullFrom.get(AbstractDungeon.cardRandomRng.random(cardsToPullFrom.size() - 1)).makeCopy();
			return (DuelistCard) c.makeCopy();
		}
		else if (metronome instanceof RarePowerMetronome)
		{
			ArrayList<DuelistCard> cardsToPullFrom = new ArrayList<DuelistCard>();
			for (DuelistCard c : DuelistMod.myCards) { if (c.type.equals(CardType.POWER) && !c.hasTag(Tags.NEVER_GENERATE) && !c.hasTag(Tags.EXEMPT) && !c.hasTag(Tags.NO_METRONOME) && c.rarity.equals(CardRarity.RARE)) { cardsToPullFrom.add((DuelistCard) c.makeCopy()); }}
			DuelistCard c = (DuelistCard) cardsToPullFrom.get(AbstractDungeon.cardRandomRng.random(cardsToPullFrom.size() - 1)).makeCopy();
			return (DuelistCard) c.makeCopy();
		}
		else if (metronome instanceof UncommonMetronome)
		{
			ArrayList<DuelistCard> cardsToPullFrom = new ArrayList<DuelistCard>();
			for (DuelistCard c : DuelistMod.myCards) { if (!c.hasTag(Tags.NEVER_GENERATE) && !c.hasTag(Tags.EXEMPT) && !c.hasTag(Tags.NO_METRONOME) && c.rarity.equals(CardRarity.UNCOMMON)) { cardsToPullFrom.add((DuelistCard) c.makeCopy()); }}
			DuelistCard c = (DuelistCard) cardsToPullFrom.get(AbstractDungeon.cardRandomRng.random(cardsToPullFrom.size() - 1)).makeCopy();
			return (DuelistCard) c.makeCopy();
		}
		else if (metronome instanceof UncommonAttackMetronome)
		{
			ArrayList<DuelistCard> cardsToPullFrom = new ArrayList<DuelistCard>();
			for (DuelistCard c : DuelistMod.myCards) { if (c.type.equals(CardType.ATTACK) && !c.hasTag(Tags.NEVER_GENERATE) && !c.hasTag(Tags.EXEMPT) && !c.hasTag(Tags.NO_METRONOME) && !c.rarity.equals(CardRarity.SPECIAL) && !c.rarity.equals(CardRarity.BASIC) && !c.rarity.equals(CardRarity.RARE)) { cardsToPullFrom.add((DuelistCard) c.makeCopy()); }}
			DuelistCard c = (DuelistCard) cardsToPullFrom.get(AbstractDungeon.cardRandomRng.random(cardsToPullFrom.size() - 1)).makeCopy();
			return (DuelistCard) c.makeCopy();
		}
		else if (metronome instanceof TrapMetronome)
		{
			ArrayList<DuelistCard> cardsToPullFrom = new ArrayList<DuelistCard>();
			for (DuelistCard c : DuelistMod.myCards) { if (c.hasTag(Tags.TRAP) && !c.hasTag(Tags.NEVER_GENERATE) && !c.hasTag(Tags.EXEMPT) && !c.hasTag(Tags.NO_METRONOME) && !c.rarity.equals(CardRarity.SPECIAL) && !c.rarity.equals(CardRarity.BASIC)) { cardsToPullFrom.add((DuelistCard) c.makeCopy()); }}
			DuelistCard c = (DuelistCard) cardsToPullFrom.get(AbstractDungeon.cardRandomRng.random(cardsToPullFrom.size() - 1)).makeCopy();
			return (DuelistCard) c.makeCopy();
		}
		else if (metronome instanceof AttackTrapMetronome)
		{
			ArrayList<DuelistCard> cardsToPullFrom = new ArrayList<DuelistCard>();
			for (DuelistCard c : DuelistMod.myCards) { if (c.type.equals(CardType.ATTACK) && c.hasTag(Tags.TRAP) && !c.hasTag(Tags.NEVER_GENERATE) && !c.hasTag(Tags.EXEMPT) && !c.hasTag(Tags.NO_METRONOME) && !c.rarity.equals(CardRarity.SPECIAL) && !c.rarity.equals(CardRarity.BASIC)) { cardsToPullFrom.add((DuelistCard) c.makeCopy()); }}
			DuelistCard c = (DuelistCard) cardsToPullFrom.get(AbstractDungeon.cardRandomRng.random(cardsToPullFrom.size() - 1)).makeCopy();
			return (DuelistCard) c.makeCopy();
		}
		else if (metronome instanceof SpellMetronome)
		{
			ArrayList<DuelistCard> cardsToPullFrom = new ArrayList<DuelistCard>();
			for (DuelistCard c : DuelistMod.myCards) { if (c.hasTag(Tags.SPELL) && !c.hasTag(Tags.NEVER_GENERATE) && !c.hasTag(Tags.EXEMPT) && !c.hasTag(Tags.NO_METRONOME) && !c.rarity.equals(CardRarity.SPECIAL) && !c.rarity.equals(CardRarity.BASIC)) { cardsToPullFrom.add((DuelistCard) c.makeCopy()); }}
			DuelistCard c = (DuelistCard) cardsToPullFrom.get(AbstractDungeon.cardRandomRng.random(cardsToPullFrom.size() - 1)).makeCopy();
			return (DuelistCard) c.makeCopy();
		}
		else if (metronome instanceof BlockSpellMetronome)
		{
			ArrayList<DuelistCard> cardsToPullFrom = new ArrayList<DuelistCard>();
			for (DuelistCard c : DuelistMod.myCards) { if (c.baseBlock > 0 && c.hasTag(Tags.SPELL) && !c.hasTag(Tags.NEVER_GENERATE) && !c.hasTag(Tags.EXEMPT) && !c.hasTag(Tags.NO_METRONOME) && !c.rarity.equals(CardRarity.SPECIAL) && !c.rarity.equals(CardRarity.BASIC)) { cardsToPullFrom.add((DuelistCard) c.makeCopy()); }}
			DuelistCard c = (DuelistCard) cardsToPullFrom.get(AbstractDungeon.cardRandomRng.random(cardsToPullFrom.size() - 1)).makeCopy();
			return (DuelistCard) c.makeCopy();
		}
		else if (metronome instanceof BlockMetronome)
		{
			ArrayList<DuelistCard> cardsToPullFrom = new ArrayList<DuelistCard>();
			for (DuelistCard c : DuelistMod.myCards) { if (c.baseBlock > 0 && !c.hasTag(Tags.NEVER_GENERATE) && !c.hasTag(Tags.EXEMPT) && !c.hasTag(Tags.NO_METRONOME) && !c.rarity.equals(CardRarity.SPECIAL) && !c.rarity.equals(CardRarity.BASIC)) { cardsToPullFrom.add((DuelistCard) c.makeCopy()); }}
			DuelistCard c = (DuelistCard) cardsToPullFrom.get(AbstractDungeon.cardRandomRng.random(cardsToPullFrom.size() - 1)).makeCopy();
			return (DuelistCard) c.makeCopy();
		}
		else if (metronome instanceof RareBlockMetronome)
		{
			ArrayList<DuelistCard> cardsToPullFrom = new ArrayList<DuelistCard>();
			for (DuelistCard c : DuelistMod.myCards) { if (c.rarity.equals(CardRarity.RARE) && c.baseBlock > 0 && !c.hasTag(Tags.NEVER_GENERATE) && !c.hasTag(Tags.EXEMPT) && !c.hasTag(Tags.NO_METRONOME) && !c.rarity.equals(CardRarity.SPECIAL) && !c.rarity.equals(CardRarity.BASIC)) { cardsToPullFrom.add((DuelistCard) c.makeCopy()); }}
			DuelistCard c = (DuelistCard) cardsToPullFrom.get(AbstractDungeon.cardRandomRng.random(cardsToPullFrom.size() - 1)).makeCopy();
			return (DuelistCard) c.makeCopy();
		}
		else if (metronome instanceof OrbMetronome)
		{
			ArrayList<DuelistCard> cardsToPullFrom = new ArrayList<DuelistCard>();
			for (DuelistCard c : DuelistMod.orbCards) { cardsToPullFrom.add((DuelistCard) c.makeCopy()); }
			DuelistCard c = (DuelistCard) cardsToPullFrom.get(AbstractDungeon.cardRandomRng.random(cardsToPullFrom.size() - 1)).makeCopy();
			return (DuelistCard) c.makeCopy();
		}
		else
		{
			return new CancelCard();
		}
	}
	
	public static AbstractCard returnTrulyRandomDuelistCardInCombat() 
	{
		if (AbstractDungeon.player.chosenClass.equals(TheDuelistEnum.THE_DUELIST))
		{
			AbstractCard c = DuelistMod.coloredCards.get(AbstractDungeon.cardRandomRng.random(DuelistMod.coloredCards.size() - 1));
			while (!(c instanceof DuelistCard) || c.hasTag(Tags.NEVER_GENERATE)) { c = DuelistMod.coloredCards.get(AbstractDungeon.cardRandomRng.random(DuelistMod.coloredCards.size() - 1)); }
			return c;
		}
		else
		{
			return returnTrulyRandomDuelistCard();
		}
	}

	public static AbstractCard returnTrulyRandomFromSets(CardTags setToFindFrom, CardTags anotherSetToFindFrom) 
	{
		ArrayList<AbstractCard> dragonGroup = new ArrayList<>();
		for (DuelistCard card : DuelistMod.myCards)
		{
			if (card.hasTag(setToFindFrom) && card.hasTag(anotherSetToFindFrom) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE)) 
			{
				dragonGroup.add(card.makeCopy());
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

	public static AbstractCard returnTrulyRandomFromEitherSet(CardTags setToFindFrom, CardTags anotherSetToFindFrom) 
	{
		ArrayList<AbstractCard> dragonGroup = new ArrayList<>();
		for (DuelistCard card : DuelistMod.myCards)
		{
			if ((card.hasTag(setToFindFrom) || card.hasTag(anotherSetToFindFrom)) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE)) 
			{
				dragonGroup.add(card.makeCopy());
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

	public static AbstractCard returnTrulyRandomFromOnlyFirstSet(CardTags setToFindFrom, CardTags excludeSet) 
	{
		ArrayList<AbstractCard> dragonGroup = new ArrayList<>();
		for (DuelistCard card : DuelistMod.myCards)
		{
			if (card.hasTag(setToFindFrom) && !card.hasTag(excludeSet) && !card.hasTag(Tags.TOKEN) && !card.hasTag(Tags.NEVER_GENERATE)) 
			{
				dragonGroup.add(card.makeCopy());
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
		//boolean useAN = false;
		//if (tagString.equals("Aqua") || tagString.equals("Insect") || tagString.equals("Arcane") || tagString.equals("Ojama")) { useAN = true; }
		if (callingRelic instanceof Monsterbox)
		{			
			res = "Replace ALL monsters in your deck with " + tagString + " monsters.";			
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
	
	public static ArrayList<DuelistCard> generateTypeCardsForRelics(AbstractRelic callingRelic, int magic, boolean limitTypes, int types)
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
				typeCards.add(new DynamicRelicTypeCard(DuelistMod.typeCardMap_ID.get(t), DuelistMod.typeCardMap_NAME.get(t), DuelistMod.typeCardMap_IMG.get(t), generateTypeDescForRelics(t, callingRelic), t, callingRelic, magic));
			}
		}
		else
		{
			for (CardTags t : DuelistMod.monsterTypes)
			{
				typeCards.add(new DynamicRelicTypeCard(DuelistMod.typeCardMap_ID.get(t), DuelistMod.typeCardMap_NAME.get(t), DuelistMod.typeCardMap_IMG.get(t), generateTypeDescForRelics(t, callingRelic), t, callingRelic, magic));
			}
			
			ArrayList<CardTags> extraTags = new ArrayList<CardTags>();
			extraTags.add(Tags.ROSE);		
			extraTags.add(Tags.OJAMA);
			for (CardTags t : extraTags)
			{
				typeCards.add(new DynamicRelicTypeCard(DuelistMod.typeCardMap_ID.get(t), DuelistMod.typeCardMap_NAME.get(t), DuelistMod.typeCardMap_IMG.get(t), generateTypeDescForRelics(t, callingRelic), t, callingRelic, magic));
			}
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
		if (!(this instanceof TributeToken)) { extraTags.add(Tags.MEGATYPED); }
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
		extraTags.add(Tags.MEGATYPED);
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
