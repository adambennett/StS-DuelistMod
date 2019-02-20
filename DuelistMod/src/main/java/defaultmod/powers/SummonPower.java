package defaultmod.powers;

import java.util.*;

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
	public HashMap<String, DuelistCard> summonMap = new HashMap<String, DuelistCard>();

	// Constructor for DuelistCard (primary summon methods)
	public SummonPower(AbstractCreature owner) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = 0;
		this.img = new Texture(IMG);
		this.canGoNegative = false;
		this.type = PowerType.BUFF;
		updateCount(this.amount);
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
		summonList.add(newSummon);
		summonMap.put("Puzzle Token", null);
		updateCount(this.amount);
		updateDescription();
	}

	@Override
	public void atStartOfTurn() 
	{
		updateCount(this.amount);
		updateDescription();
	}


	@Override
	public void updateDescription() 
	{
		if (this.amount > 0)
		{
			String summonsString = "";
			for (String s : summonList) { summonsString += s + ", "; }
			int endingIndex = summonsString.lastIndexOf(",");
	        String finalSummonsString = summonsString.substring(0, endingIndex) + ".";
			this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + MAX_SUMMONS + DESCRIPTIONS[2] + finalSummonsString;
		}
		else
		{
			summonList = new ArrayList<String>();
			this.description = DESCRIPTIONS[0] + "0" + DESCRIPTIONS[1] + MAX_SUMMONS + DESCRIPTIONS[2] + "None.";
		} 
	}

	public void updateCount(int amount)
	{
		if (this.amount > MAX_SUMMONS) { this.amount = MAX_SUMMONS; }
		if (this.amount < 0) { this.amount = 0; }
	}
	

}