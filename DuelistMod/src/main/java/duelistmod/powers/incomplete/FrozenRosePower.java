package duelistmod.powers.incomplete;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.unique.YamiFormAction;
import duelistmod.cards.YamiForm;


@SuppressWarnings("unused")
public class FrozenRosePower extends AbstractPower implements NonStackablePower
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("FrozenRosePower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("FrozenRosePower.png");
	private boolean finished = false;
	
	public FrozenRosePower(final AbstractCreature owner, final AbstractCreature source) 
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.isTurnBased = false;
		this.img = new Texture(IMG);
		this.source = source;
		updateDescription();
	}
	
	@Override
	public void updateDescription() 
	{
		this.description = DESCRIPTIONS[0];
	}
	
	@Override
	public void atStartOfTurnPostDraw()
	{
		AbstractDungeon.player.draw(2);
		DuelistCard.discard(1, false);
		DuelistCard.removePower(this, this.owner);
	}
}
