package defaultmod.powers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.DefaultMod;
import defaultmod.cards.Token;
import defaultmod.patches.DuelistCard;
import defaultmod.relics.*;

public class SummonPower extends AbstractPower
{
	public static final String POWER_ID = defaultmod.DefaultMod.makeID("SummonPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DefaultMod.makePath(DefaultMod.SUMMON_POWER);

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
		if (AbstractDungeon.player.hasRelic(MillenniumKey.ID)) { MAX_SUMMONS = 3; }
		else if (AbstractDungeon.player.hasRelic(MillenniumRing.ID)) { MAX_SUMMONS = 10; }
		
		// Add the new summon(s) to the list
		for (int i = 0; i < newAmount; i++) {if (i < MAX_SUMMONS) { summonList.add(newSummon); }}
		
		// Check the last max summon value in case the player lost the summon power somehow during battle after changing their max summons
		if (DefaultMod.lastMaxSummons != MAX_SUMMONS) { MAX_SUMMONS = DefaultMod.lastMaxSummons; }
		
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
		if (AbstractDungeon.player.hasRelic(MillenniumKey.ID)) { MAX_SUMMONS = 3; }
		else if (AbstractDungeon.player.hasRelic(MillenniumRing.ID)) { MAX_SUMMONS = 10; }
		for (int i = 0; i < newAmount; i++) { if (i < MAX_SUMMONS) { summonList.add(newSummon);  }}
		if (DefaultMod.lastMaxSummons != MAX_SUMMONS) { MAX_SUMMONS = DefaultMod.lastMaxSummons; }
		updateCount(this.amount);
		updateStringColors();
		updateDescription();
	}
	
	@Override
	public void onVictory()
	{
		DefaultMod.lastMaxSummons = 5;
	}
	
	@Override
	public void onDeath()
	{
		DefaultMod.lastMaxSummons = 5;
	}
	
	@Override
	public void atStartOfTurn() 
	{
		updateCount(this.amount);
		updateStringColors();
		updateDescription();
	}
	
	public void updateStringColors()
	{
		coloredSummonList = new ArrayList<String>();
		for (String s : summonList)
		{
			DuelistCard ref = DefaultMod.summonMap.get(s);
			if (ref == null) { ref = new Token(); }
			String coloredString = "";
			if (ref.hasTag(DefaultMod.GOOD_TRIB) && !ref.hasTag(DefaultMod.TOKEN)) 
			{
				coloredString = "#b" + s;
				coloredString = coloredString.replaceAll("\\s", " #b"); 
				coloredSummonList.add(coloredString);
			}
			else if (ref.hasTag(DefaultMod.BAD_TRIB) && !ref.hasTag(DefaultMod.TOKEN))
			{
				coloredString = "[#FF5252]" + s;
				coloredString = coloredString.replaceAll("\\s", " [#FF5252]");
				coloredSummonList.add(coloredString);
			}
			else if (ref.hasTag(DefaultMod.TOKEN))
			{
				coloredString = "[#180055]" + s;
				coloredString = coloredString.replaceAll("\\s", " [#180055]");
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
			String summonsString = "";
			for (String s : coloredSummonList) { summonsString += s + ", "; System.out.println("theDuelist:SummonPower:updateDescription() ---> string in coloredSummonList: " + s + " :: Summons = " + this.amount); }
			System.out.println("theDuelist:SummonPower:updateDescription() ---> done looping over colored summons list!");
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