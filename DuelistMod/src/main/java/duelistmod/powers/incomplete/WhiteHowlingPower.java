package duelistmod.powers.incomplete;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.orbs.WhiteOrb;


public class WhiteHowlingPower extends AbstractPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("WhiteHowlingPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("WhiteHowlingPower.png");
	
	public WhiteHowlingPower(final AbstractCreature owner, final AbstractCreature source, int amount) 
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
	public void updateDescription() 
	{
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}

	@Override
	public void atStartOfTurn() 
	{
		AbstractPlayer p = AbstractDungeon.player;
		boolean hasWhiteOrbs = false;
		for (AbstractOrb o : p.orbs) { if (o instanceof WhiteOrb) { hasWhiteOrbs = true; break; }}
		if (!hasWhiteOrbs) { DuelistCard.channel(new WhiteOrb(), this.amount); }
	}
}
