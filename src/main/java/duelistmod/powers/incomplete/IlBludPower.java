package duelistmod.powers.incomplete;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.interfaces.*;
import duelistmod.variables.Strings;


@SuppressWarnings("unused")
public class IlBludPower extends AbstractPower implements IncrementDiscardSubscriber
{
	public AbstractCreature source;
	public static final String POWER_ID = DuelistMod.makeID("IlBludPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public static final String IMG = DuelistMod.makePowerPath("IlBludPower.png");
	private boolean finished = false;
	
	public IlBludPower(final AbstractCreature owner, final AbstractCreature source, int amount) 
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
	public void receiveIncrementDiscard() 
	{
		if (AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) { return; }
		DuelistCard.damageAllEnemiesThornsNormal(this.amount);
	}
	
	@Override
    public void onInitialApplication() {
        DuelistMod.subscribe(this);
    }

    @Override
    public void onRemove() {
        DuelistMod.unsubscribe(this);
    }

    @Override
    public void onVictory() {
        DuelistMod.unsubscribe(this);
    }

    @Override
    public void onDeath() {
        DuelistMod.unsubscribe(this);
    }
}
