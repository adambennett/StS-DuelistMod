package duelistmod.powers.enemyPowers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.interfaces.*;
import duelistmod.variables.Strings;


@SuppressWarnings("unused")
public class ResistNatureEnemyPower extends AbstractPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("ResistNatureEnemyPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("ResistNatureEnemyPower.png");
	public int p = 0;
	
	public ResistNatureEnemyPower(final AbstractCreature owner, final AbstractCreature source, int amount) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = amount;
		if (this.amount > 10) { this.amount = 10; }
		updateDescription();
	}
	
	public float calc()
	{
		return this.amount;
	}
	
	@Override
	public void updateDescription() 
	{
		if (this.amount > 10) { this.amount = 10; }
		if (this.amount < 0)  { this.amount = 0;  }
		this.description = DESCRIPTIONS[0] + (this.amount * 10) + DESCRIPTIONS[1];
	}
}
