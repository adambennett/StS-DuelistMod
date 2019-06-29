package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import duelistmod.*;
import duelistmod.interfaces.DuelistCard;


@SuppressWarnings("unused")
public class PlantTypePower extends AbstractPower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("PlantTypePower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePath(Strings.PLACEHOLDER_POWER);
	private boolean finished = false;
	
	public PlantTypePower(final AbstractCreature owner, final AbstractCreature source, int amount) 
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
	public void atEndOfTurn(boolean isPlayer) 
	{
		if (isPlayer)
		{
			int roll = AbstractDungeon.cardRandomRng.random(1, 10);
			int rollCheck = AbstractDungeon.cardRandomRng.random(1, 3);
			if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID))
			{
				SummonPower instance = (SummonPower) AbstractDungeon.player.getPower(SummonPower.POWER_ID);
				if (instance.isEveryMonsterCheck(Tags.PLANT, false))
				{
					rollCheck += 4;
				}
			}
			if (roll < rollCheck)
			{
				for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters)
				{
					if (!monster.isDead && !monster.isDying)
					{
						DuelistCard.applyPower(new ConstrictedPower(monster, AbstractDungeon.player, this.amount), monster);
					}
				}
			}
		}
	}
}
