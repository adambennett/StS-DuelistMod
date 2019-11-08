package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.actions.common.SolderAction;
import duelistmod.variables.Tags;

public class OutriggerExtensionPower extends DuelistPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("OutriggerExtensionPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("OutriggerExtensionPower.png");
	private boolean finished = false;
	
	public OutriggerExtensionPower(final AbstractCreature owner, final AbstractCreature source, int amount) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = amount;
		updateDescription();
	}
	
	@Override
	public void onTribute(DuelistCard tributed, DuelistCard tributing)
	{
		if (!finished && tributed.hasTag(Tags.MACHINE))
		{
			this.addToBot(new SolderAction(AbstractDungeon.player.hand.group, this.amount, true));
			this.flash();
		}
	}
	
	@Override
	public void atEndOfRound() { finished = false; }
	
	@Override
	public void updateDescription() 
	{
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
}
