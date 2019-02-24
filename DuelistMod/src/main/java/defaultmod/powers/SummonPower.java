package defaultmod.powers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.DefaultMod;
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
	public boolean firstOfBattle = true;

	// Constructor for DuelistCard (primary summon methods)
	public SummonPower(AbstractCreature owner) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = 0;
		this.img = new Texture(IMG);
		this.canGoNegative = false;
		this.type = PowerType.BUFF;
		if (DefaultMod.lastMaxSummons != MAX_SUMMONS) { MAX_SUMMONS = DefaultMod.lastMaxSummons; }
		updateCount(this.amount);
		updateStringColors();
		updateDescription();
	}
	
	public SummonPower(AbstractCreature owner, int newAmount, String newSummon, String desc, DuelistCard c) {
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
		for (int i = 0; i < newAmount; i++) {if (i < MAX_SUMMONS) { summonList.add(newSummon); }}
		if (DefaultMod.lastMaxSummons != MAX_SUMMONS) { MAX_SUMMONS = DefaultMod.lastMaxSummons; }
		updateCount(this.amount);
		updateStringColors();
		updateDescription();
	}

	
	// Constructor for Starter Relic
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
		for (int i = 0; i < newAmount; i++) { if (i < MAX_SUMMONS) { summonList.add(newSummon); }}
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
	public void atStartOfTurn() 
	{
		updateCount(this.amount);
		updateStringColors();
		updateDescription();
	}
	
	public void updateStringColors()
	{
		ArrayList<String> coloredSummonList = new ArrayList<String>();
		for (String s : summonList)
		{
			DuelistCard ref = DefaultMod.summonMap.get(s);
			if (ref.hasTag(DefaultMod.GOOD_TRIB)) { s = "#b" + s; s.replaceAll("\\s", " #b"); coloredSummonList.add(s); }
			else if (ref.hasTag(DefaultMod.BAD_TRIB)) { s = "[#FF5252]" + s; s.replaceAll("\\s", " [#FF5252]"); coloredSummonList.add(s);  }
			else { coloredSummonList.add(s); }
			summonList = coloredSummonList;
		}
	}


	@Override
	public void updateDescription() 
	{
		//updateStringColors();
		if (this.amount > 0)
		{
			String summonsString = "";
			for (String s : summonList) { System.out.println("theDuelist:SummonPower:updateDescription() ---> s: " + s); summonsString += s + ", "; }
			int endingIndex = summonsString.lastIndexOf(",");
	        String finalSummonsString = summonsString.substring(0, endingIndex) + ".";
	        System.out.println("theDuelist:SummonPower:updateDescription() --->  finalSummonsString: " + finalSummonsString);
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