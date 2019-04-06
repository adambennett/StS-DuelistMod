package duelistmod.powers;

import java.util.*;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.cards.Token;
import duelistmod.patches.DuelistCard;
import duelistmod.relics.*;

public class SummonPower extends AbstractPower
{
	public static final String POWER_ID = duelistmod.DuelistMod.makeID("SummonPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePath(Strings.SUMMON_POWER);

	public int MAX_SUMMONS = 5;
	public int test = 5;
	public ArrayList<String> summonList = new ArrayList<String>();
	public ArrayList<String> coloredSummonList = new ArrayList<String>();

	// Constructor for summon() in DuelistCard
	public SummonPower(AbstractCreature owner, int newAmount, String newSummon, String desc, DuelistCard c) {
		// Set power fields
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = newAmount;
		this.img = new Texture(IMG);
		this.description = desc;
		this.canGoNegative = false;
		this.type = PowerType.BUFF;
		
		// Change Max Summons if player has either of the two relics that effect it
		if (AbstractDungeon.player.hasRelic(MillenniumKey.ID)) { MAX_SUMMONS = 4; }
		else if (AbstractDungeon.player.hasRelic(MillenniumRing.ID)) { MAX_SUMMONS = 8; }
		
		// Check the last max summon value in case the player lost the summon power somehow during battle after changing their max summons
		if (DuelistMod.lastMaxSummons != MAX_SUMMONS) { MAX_SUMMONS = DuelistMod.lastMaxSummons; }
		
		// Add the new summon(s) to the list
		for (int i = 0; i < newAmount; i++) {if (i < MAX_SUMMONS) { summonList.add(newSummon); }}

		// Update the description properly
		updateCount(this.amount);
		updateStringColors();
		updateDescription();
	}

	
	// Constructor for powerSummon() in DuelistCard
	public SummonPower(AbstractCreature owner, int newAmount, String newSummon, String desc) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = newAmount;
		this.img = new Texture(IMG);
		this.description = desc;
		this.canGoNegative = false;
		this.type = PowerType.BUFF;
		if (AbstractDungeon.player.hasRelic(MillenniumKey.ID)) { MAX_SUMMONS = 4; }
		else if (AbstractDungeon.player.hasRelic(MillenniumRing.ID)) { MAX_SUMMONS = 8; }
		if (DuelistMod.lastMaxSummons != MAX_SUMMONS) { MAX_SUMMONS = DuelistMod.lastMaxSummons; }
		for (int i = 0; i < newAmount; i++) { if (i < MAX_SUMMONS) { summonList.add(newSummon);  }}
		updateCount(this.amount);
		updateStringColors();
		updateDescription();
	}
	
	@Override
	public void onVictory()
	{
		DuelistMod.lastMaxSummons = 5;
	}
	
	@Override
	public void onDeath()
	{
		DuelistMod.lastMaxSummons = 5;
	}
	
	@Override
	public void atStartOfTurn() 
	{
		updateCount(this.amount);
		updateStringColors();
		updateDescription();
	}
	
	public int getNumberOfTypeSummoned(CardTags type)
	{
		int numberFound = 0;
		if (summonList.size() > 0)
		{
			for (String s : summonList)
			{
				DuelistCard ref = DuelistMod.summonMap.get(s);
				if (ref.hasTag(type))
				{
					numberFound++;
				}
			}
		}
		return numberFound;
	}
	
	public int getNumberOfTypeSummonedFromEither(CardTags type, CardTags typeB)
	{
		int numberFound = 0;
		if (summonList.size() > 0)
		{
			for (String s : summonList)
			{
				DuelistCard ref = DuelistMod.summonMap.get(s);
				if (ref.hasTag(type) || ref.hasTag(typeB))
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
		if (summonList.size() > 0)
		{
			for (String s : summonList)
			{
				DuelistCard ref = DuelistMod.summonMap.get(s);
				if (DuelistMod.debug) { System.out.println("theDuelist:SummonPower:isOnlyTypeSummoned() ---> ref string: " + s); }
				if (ref != null)
				{
					if (!ref.hasTag(type))
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
		if (summonList.size() > 0)
		{
			for (String s : summonList)
			{
				DuelistCard ref = DuelistMod.summonMap.get(s);
				if (ref.hasTag(type))
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
		if (summonList.size() > 0)
		{
			if (summonList.contains(cardName))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean isMonsterSummoned(String name)
	{
		if (summonList.size() > 0)
		{
			if (summonList.contains(name))
			{
				return true;
			}
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
		goodTags.add(Tags.NATURIA);
		goodTags.add(Tags.TOON);
		coloredSummonList = new ArrayList<String>();
		for (String s : summonList)
		{
			DuelistCard ref = DuelistMod.summonMap.get(s);
			if (ref == null) { ref = new Token(); }
			String coloredString = "";
			if ((ref.hasTag(Tags.GOOD_TRIB) && !(ref instanceof Token)) || (StarterDeckSetup.hasTags(ref, goodTags) && !(ref instanceof Token))) 
			{
				coloredString = "#b" + s;
				coloredString = coloredString.replaceAll("\\s", " #b"); 
				coloredSummonList.add(coloredString);
			}
			else if (ref.hasTag(Tags.BAD_TRIB) && !(ref instanceof Token))
			{
				coloredString = "[#FF5252]" + s;
				coloredString = coloredString.replaceAll("\\s", " [#FF5252]");
				coloredSummonList.add(coloredString);
			}
			else if (ref.hasTag(Tags.TOKEN))
			{
				coloredString = "[#C0B0C0]" + s;
				coloredString = coloredString.replaceAll("\\s", " [#C0B0C0]");
				coloredSummonList.add(coloredString);
			}
			else
			{
				coloredString = s;
				coloredSummonList.add(coloredString);
			}
		}
	}


	@Override
	public void updateDescription() 
	{
		if (this.amount > 0)
		{
			if (this.amount != summonList.size()) { this.amount = summonList.size(); System.out.println("A bad thing happened! Your summons list had a different size than the amount of this power! If you are seeing this message, please go comment on workshop and tell me what happened!"); }
			String summonsString = "";
			for (String s : coloredSummonList) 
			{ 
				summonsString += s + ", "; 
				if (DuelistMod.debug) { System.out.println("theDuelist:SummonPower:updateDescription() ---> string in coloredSummonList: " + s + " :: Summons = " + this.amount); }
			}
			if (DuelistMod.debug) { System.out.println("theDuelist:SummonPower:updateDescription() ---> done looping over colored summons list!"); }
			int endingIndex = summonsString.lastIndexOf(",");
	        String finalSummonsString = summonsString.substring(0, endingIndex) + ".";
			this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + MAX_SUMMONS + DESCRIPTIONS[2] + finalSummonsString;
		}
		else
		{
			summonList = new ArrayList<String>();
			this.description = DESCRIPTIONS[0] + "0" + DESCRIPTIONS[1] + MAX_SUMMONS + DESCRIPTIONS[2] + "#bNone.";
		} 
	}

	public void updateCount(int amount)
	{
		if (this.amount > MAX_SUMMONS) { this.amount = MAX_SUMMONS; }
		if (this.amount < 0) { this.amount = 0; }
	}
}