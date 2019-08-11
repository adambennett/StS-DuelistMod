package duelistmod.powers.enemyPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistMonster;
import duelistmod.monsters.SetoKaiba;


public class EnemyBoosterDragonPower extends AbstractPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("EnemyBoosterDragonPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("BoosterDragonPower.png");

	
	public EnemyBoosterDragonPower(final AbstractCreature owner, final AbstractCreature source, int amount) 
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
	
	public void trigger(SetoKaiba seto)
	{
		if (this.amount > 0) { AbstractDungeon.actionManager.addToBottom(new GainBlockAction(seto, seto, this.amount)); }
	}
	
	@Override
	public void updateDescription() 
	{
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}

	public void trigger(DuelistMonster duelistMonster) 
	{
		if (this.amount > 0) { AbstractDungeon.actionManager.addToBottom(new GainBlockAction(duelistMonster, duelistMonster, this.amount)); }
	}
}
