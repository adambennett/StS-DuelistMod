package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.variables.Tags;

public class LeodrakeManePower extends DuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("LeodrakeManePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("LeodrakeManePower.png");
	
	public LeodrakeManePower(int turns) 
	{ 
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = AbstractDungeon.player;
        this.amount = turns;
		updateDescription(); 
	}

	@Override
	public void updateDescription()
	{
		if (this.amount < 0) { this.amount = 0; }
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}

	@Override
	public void onSummon(DuelistCard c, int amt) { if (c.hasTag(Tags.NATURIA)) { this.addToBot(new ScryAction(this.amount)); }}
}
