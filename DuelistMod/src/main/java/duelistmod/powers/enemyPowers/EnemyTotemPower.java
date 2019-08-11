package duelistmod.powers.enemyPowers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.abstracts.*;
import duelistmod.interfaces.*;
import duelistmod.monsters.SetoKaiba;
import duelistmod.variables.Strings;


@SuppressWarnings("unused")
public class EnemyTotemPower extends AbstractPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("EnemyTotemPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("TotemDragonPower.png");
	private SetoKaiba seto;
	private DuelistMonster mon;
	private boolean isSeto = true;
	
	public EnemyTotemPower(final AbstractCreature owner, final AbstractCreature source, int amount, SetoKaiba enemy) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		this.amount = amount;
		this.seto = enemy;
		updateDescription();
	}
	
	public EnemyTotemPower(DuelistMonster owner, DuelistMonster source2, int amount, DuelistMonster duelistMonster) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source2;
		this.amount = amount;
		this.mon = duelistMonster;
		this.isSeto = false;
		updateDescription();
	}

	@Override
	public void updateDescription() 
	{
		if (this.amount != 1) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];}
		else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }
	}

	@Override
	public void atEndOfTurn(boolean isPlayer) 
	{
		if (this.amount > 0 && isSeto) { this.seto.summon("Dragon Token", this.amount); }
		else if (this.amount > 0) { this.mon.summon("Dragon Token", this.amount); }
		updateDescription();
	}
}
