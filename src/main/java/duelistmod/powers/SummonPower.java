package duelistmod.powers;

import java.util.*;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.FrozenEye;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.BigEye;
import duelistmod.cards.other.tokens.*;
import duelistmod.helpers.*;
import duelistmod.powers.duelistPowers.CanyonPower;
import duelistmod.relics.MillenniumKey;
import duelistmod.variables.*;

public class SummonPower extends AbstractPower
{
	public static final String POWER_ID = DuelistMod.makeID("SummonPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePath(Strings.SUMMON_POWER);

	public int MAX_SUMMONS = DuelistMod.defaultMaxSummons;
	public ArrayList<String> summonList = new ArrayList<String>();
	public ArrayList<String> coloredSummonList = new ArrayList<String>();
	public ArrayList<DuelistCard> actualCardSummonList = new ArrayList<DuelistCard>();
	private BigEye be; 
	//private FlameTiger ft;

	// Constructor for summon() in DuelistCard
	public SummonPower(AbstractCreature owner, int newAmount, String newSummon, String desc, DuelistCard c) 
	{
		// Debug logs
		if (summonList.size() > 0 && DuelistMod.debug) { System.out.println("SummonPower() -- SUMMONS LIST SIZE WAS > 0");}
		else if (DuelistMod.debug) {  System.out.println("SummonPower() -- SUMMONS LIST SIZE WAS < 0"); }
		
		// Set power fields
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = newAmount;
		this.img = new Texture(IMG);
		this.description = desc;
		this.canGoNegative = false;
		this.type = PowerType.BUFF;
		this.be = new BigEye();
		//this.ft = new FlameTiger();

		// Check the last max summon value in case the player lost the summon power somehow during battle after changing their max summons
		if (DuelistMod.lastMaxSummons != MAX_SUMMONS) { MAX_SUMMONS = DuelistMod.lastMaxSummons; }
		
		// Force max summons of 5 when player has Millennium Key
		if (AbstractDungeon.player.hasRelic(MillenniumKey.ID)) { MAX_SUMMONS = 5; }
		
		// Add the new summon(s) to the lists
		for (int i = 0; i < newAmount; i++) {if (i < MAX_SUMMONS) { summonList.add(newSummon); actualCardSummonList.add((DuelistCard) c.makeStatEquivalentCopy()); }}

		// Update the description properly
		updateCount(this.amount);
		updateStringColors();
		updateDescription();
	}

	
	// Constructor for powerSummon() in DuelistCard
	public SummonPower(AbstractCreature owner, int newAmount, String newSummon, String desc) 
	{
		// Debug logs
		if (summonList.size() > 0 && DuelistMod.debug) { System.out.println("SummonPower() -- SUMMONS LIST SIZE WAS > 0");}
		else if (DuelistMod.debug) {  System.out.println("SummonPower() -- SUMMONS LIST SIZE WAS < 0"); }
		
		// Set power fields
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = newAmount;
		this.img = new Texture(IMG);
		this.description = desc;
		this.canGoNegative = false;
		this.type = PowerType.BUFF;
		this.be = new BigEye();
		//this.ft = new FlameTiger();
		
		// Check the last max summon value in case the player lost the summon power somehow during battle after changing their max summons
		if (DuelistMod.lastMaxSummons != MAX_SUMMONS) { MAX_SUMMONS = DuelistMod.lastMaxSummons; }
				
		// Force max summons of 5 when player has Millennium Key
		if (AbstractDungeon.player.hasRelic(MillenniumKey.ID)) { MAX_SUMMONS = 5; }
		
		for (int i = 0; i < newAmount; i++) { if (i < MAX_SUMMONS) { summonList.add(newSummon);  }}
		for (int i = 0; i < newAmount; i++) {if (i < MAX_SUMMONS) { actualCardSummonList.add((DuelistCard) DuelistMod.summonMap.get(newSummon).makeStatEquivalentCopy()); }}
		updateCount(this.amount);
		updateStringColors();
		updateDescription();
	}
	
	@Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
		// Rock-type blocking effect
		if (AbstractDungeon.player.hasPower(CanyonPower.POWER_ID))
		{
			for (DuelistCard c : actualCardSummonList) { if (c.hasTag(Tags.ROCK)) { DuelistCard.staticBlock(DuelistMod.rockBlock + AbstractDungeon.player.getPower(CanyonPower.POWER_ID).amount); }}
		}
		else
		{
			for (DuelistCard c : actualCardSummonList) { if (c.hasTag(Tags.ROCK)) { DuelistCard.staticBlock(DuelistMod.rockBlock); }}
		}
		
		if (MAX_SUMMONS > DuelistMod.highestMaxSummonsObtained) { DuelistMod.highestMaxSummonsObtained = MAX_SUMMONS; }
		
		ArrayList<DuelistCard> newList = new ArrayList<>();
		ArrayList<String> strings = new ArrayList<>();
		for (DuelistCard c : actualCardSummonList)
		{
			if (!c.hasTag(Tags.SPIRIT))
			{
				newList.add(c);
				strings.add(c.originalName);
			}
		}
		
		this.amount = newList.size();
		this.actualCardSummonList = newList;
		this.summonList = strings;
		updateCount(this.amount);
		updateStringColors();
		updateDescription();
	}
	
	@Override
	public void onVictory()
	{
	
	}
	
	@Override
	public void onDeath()
	{

	}
	
	@Override
	public void atStartOfTurn() 
	{
		updateCount(this.amount);
		updateStringColors();
		updateDescription();
	}
	
	public boolean hasExplosiveTokens()
	{
		int tokens = 0;
		for (DuelistCard c : actualCardSummonList)
		{
			if (c.hasTag(Tags.EXPLODING_TOKEN) || c.hasTag(Tags.SUPER_EXPLODING_TOKEN))
			{
				tokens++;
			}
		}
		return tokens > 0;
	}
	
	public boolean isEveryMonsterCheck(CardTags tag, boolean tokensAreChecked)
	{
		if (getNumberOfTypeSummoned(tag) == this.amount && this.amount > 0)
		{
			return true;
		}
		
		else if (getNumberOfTypeSummoned(tag) - getNumberOfTokens() == this.amount && this.amount > 0)
		{
			if (DuelistMod.debug) { DuelistMod.logger.info("Found something other than " + DuelistMod.typeCardMap_NAME.get(tag) + "s, but they were just tokens"); }
			return !tokensAreChecked;
		}
		
		else
		{
			if (DuelistMod.debug) { DuelistMod.logger.info("Found something other than " + DuelistMod.typeCardMap_NAME.get(tag) + "s."); }
			return false;
		}
	}
	
	public int getNumberOfTokens() 
	{
		int tokens = 0;
		for (DuelistCard c : actualCardSummonList)
		{
			if (c.hasTag(Tags.TOKEN))
			{
				tokens++;
			}
		}
		return tokens;
	}
	
	public int getNumberOfTypeSummoned(CardTags type)
	{
		int numberFound = 0;
		if (actualCardSummonList.size() > 0)
		{
			for (DuelistCard s : actualCardSummonList)
			{
				if (s.hasTag(type))
				{
					numberFound++;
				}
			}
		}
		return numberFound;
	}

	public int getNumberOfTypeSummonedForTributes(CardTags type, int tributes) {
		int numberFound = 0;
		int tribCounter = tributes;
		if (actualCardSummonList.size() > 0) {
			for (int i = actualCardSummonList.size() - 1; i > -1; i--) {
				if (tribCounter <= 0) {
					break;
				}
				if (actualCardSummonList.get(i).hasTag(type)) {
					numberFound++;
				}
				tribCounter--;
			}
		}
		return numberFound;
	}
	
	public int getNumberOfTypeSummonedFromEither(CardTags type, CardTags typeB)
	{
		int numberFound = 0;
		if (actualCardSummonList.size() > 0)
		{
			for (DuelistCard s : actualCardSummonList)
			{
				if (s.hasTag(type) || s.hasTag(typeB))
				{
					numberFound++;
				}
			}
		}
		return numberFound;
	}
	
	public boolean isOnlyTypeSummoned(CardTags type)
	{
		boolean foundOnlyType = true;
		if (actualCardSummonList.size() > 0)
		{
			for (DuelistCard s : actualCardSummonList)
			{
				if (DuelistMod.debug) { System.out.println("theDuelist:SummonPower:isOnlyTypeSummoned() ---> ref string: " + s); }
				if (s != null)
				{
					if (!s.hasTag(type))
					{
						foundOnlyType = false;
					}
				}
				else
				{
					if (DuelistMod.debug) 
					{ 
						System.out.println("theDuelist:SummonPower:isOnlyTypeSummoned() ---> caught null pointer. printing entire summon map... brace yourself");
						//System.out.println("theDuelist:SummonPower:isOnlyTypeSummoned() ---> the game is going to crash when I'm done printing");
						Set<Entry<String, DuelistCard>> mapSet = DuelistMod.summonMap.entrySet();
						for (Entry<String, DuelistCard> e : mapSet)
						{
							System.out.println("summonMap entry: KEY[" + e.getKey() +  "] : VALUE[" + e.getValue() + "] : " + e.toString());
						}
					}
					
					// this will crash the game on purpose
					/*if (!ref.hasTag(type))
					{
						foundOnlyType = false;
					}*/
				}
			}
			
			if (foundOnlyType) 
			{
				if (DuelistMod.debug) { System.out.println("theDuelist:SummonPower:isOnlyTypeSummoned() ---> found only " + type.name() + "s."); }
				return true;
			}
			else 
			{ 
				if (DuelistMod.debug) { System.out.println("theDuelist:SummonPower:isOnlyTypeSummoned() ---> found something other than " + type.name() + "s."); }
				return false; 
			}
		}
		else
		{
			return false;
		}
	}
	
	public boolean typeSummonsMatchMax(CardTags type)
	{
		int numberFound = 0;
		int numberLooking = this.MAX_SUMMONS;
		if (actualCardSummonList.size() > 0)
		{
			for (DuelistCard s : actualCardSummonList)
			{
				if (s.hasTag(type))
				{
					numberFound++;
				}
			}
			
			if (numberFound == numberLooking)
			{
				if (DuelistMod.debug) { System.out.println("theDuelist:SummonPower:typeSummonsMatchMax() ---> found the right amount of " + type.name() + " number found: " + numberFound + " :: number looking for: " + numberLooking); }
				return true;
			}
			else
			{
				if (DuelistMod.debug) { System.out.println("theDuelist:SummonPower:typeSummonsMatchMax() ---> found the wrong amount of " + type.name() + " number found: " + numberFound + " :: number looking for: " + numberLooking); }
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	public boolean isMonsterSummoned(DuelistCard monster)
	{
		String cardName = monster.originalName;
		if (summonList.size() > 0) {
			return summonList.contains(cardName);
		}
		return false;
	}
	
	public boolean isMonsterSummoned(String name)
	{
		if (summonList.size() > 0) {
			return summonList.contains(name);
		}
		return false;
	}
	
	public void updateStringColors()
	{
		ArrayList<CardTags> goodTags = new ArrayList<CardTags>();
		goodTags.add(Tags.AQUA);
		goodTags.add(Tags.FIEND);
		goodTags.add(Tags.DRAGON);
		goodTags.add(Tags.SUPERHEAVY);
		goodTags.add(Tags.MACHINE);
		goodTags.add(Tags.INSECT);
		goodTags.add(Tags.PLANT);
		goodTags.add(Tags.TOON_POOL);
		if (!DuelistMod.warriorTribThisCombat) { goodTags.add(Tags.WARRIOR); }
		coloredSummonList = new ArrayList<String>();
		for (DuelistCard s : actualCardSummonList)
		{
			if (s == null) { s = new Token(); }
			String coloredString = "";
			if (s.hasTag(Tags.MEGATYPED))
			{
				coloredString = "[#BD61FF]" + s.originalName;
				coloredString = coloredString.replaceAll("\\s", " [#BD61FF]");
				coloredSummonList.add(coloredString);
			}
			else if (s.hasTag(Tags.NATURIA))
			{
				coloredString = "[#008000]" + s.originalName;
				coloredString = coloredString.replaceAll("\\s", " [#008000]");
				coloredSummonList.add(coloredString);
			}
			else if (s.hasTag(Tags.BAD_TRIB))
			{
				coloredString = "[#FF5252]" + s.originalName;
				coloredString = coloredString.replaceAll("\\s", " [#FF5252]");
				coloredSummonList.add(coloredString);
			}
			else if (s.hasTag(Tags.TOKEN))
			{
				coloredString = "[#C0B0C0]" + s.originalName;
				coloredString = coloredString.replaceAll("\\s", " [#C0B0C0]");
				coloredSummonList.add(coloredString);
			}
			else if ((s.hasTag(Tags.GOOD_TRIB)) || (StarterDeckSetup.hasTags(s, goodTags))) 
			{
				coloredString = "#b" + s.originalName;
				coloredString = coloredString.replaceAll("\\s", " #b"); 
				coloredSummonList.add(coloredString);
			}
			else
			{
				coloredString = s.originalName;
				coloredSummonList.add(coloredString);
			}
		}
	}


	@Override
	public void updateDescription() {
		if (this.amount > 0) {
			if (this.amount != summonList.size()) {
				this.amount = summonList.size();
				this.description = DESCRIPTIONS[0] + "0" + DESCRIPTIONS[1] + MAX_SUMMONS + DESCRIPTIONS[2] + "#bNone.";
			}
			else {
				StringBuilder summonsString = new StringBuilder();
				for (String s : coloredSummonList) {
					summonsString.append(s).append(", ");
				}
				int endingIndex = summonsString.lastIndexOf(",");
				if (endingIndex > -1) {
					String finalSummonsString = summonsString.substring(0, endingIndex) + ".";
					this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + MAX_SUMMONS + DESCRIPTIONS[2] + finalSummonsString;
				} else {
					this.description = DESCRIPTIONS[0] + "0" + DESCRIPTIONS[1] + MAX_SUMMONS + DESCRIPTIONS[2] + "#bNone.";
				}
			}
		} else {
			summonList = new ArrayList<String>();
			this.description = DESCRIPTIONS[0] + "0" + DESCRIPTIONS[1] + MAX_SUMMONS + DESCRIPTIONS[2] + "#bNone.";
		} 
		
		// Check for Big Eyes & Flame Tigers
		boolean foundBigEye = isMonsterSummoned("Big Eye") || isMonsterSummoned(be.originalName);
		if (!foundBigEye && DuelistMod.gotFrozenEyeFromBigEye) {
			AbstractDungeon.player.loseRelic(FrozenEye.ID);
		}
	}

	public void updateCount(int amount)
	{
		if (this.amount > MAX_SUMMONS) {
			DuelistCard.powerTribute(AbstractDungeon.player, this.amount - MAX_SUMMONS, false);
			this.amount = MAX_SUMMONS; 
		}
		if (this.amount < 0) {
			DuelistCard.powerTribute(AbstractDungeon.player, 0, true);
			this.amount = 0; 
		}
	}
}
