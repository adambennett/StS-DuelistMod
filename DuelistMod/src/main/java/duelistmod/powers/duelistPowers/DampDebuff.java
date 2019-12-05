package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;
import duelistmod.actions.unique.DampTakeDamageAction;

public class DampDebuff extends DuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("DampDebuff");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
	
	public DampDebuff(AbstractCreature owner, AbstractCreature source, int startAmt) 
	{ 
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = startAmt;
        if (this.amount >= 999) { this.amount = 999; }
		updateDescription(); 
	}

	@Override
	public void updateDescription()
	{
		if (this.owner == null || this.owner.isPlayer) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }
		else { this.description = DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3]; }
	}
	
	@Override
	public void onOverflow(int overflowsTriggered)
	{
		if (overflowsTriggered > 0) 
		{ 
			this.flashWithoutSound(); 
			for (int i = 0; i < overflowsTriggered; i++)
			{
				this.addToBot(new DampTakeDamageAction(this.owner, this.source, this.amount, AbstractGameAction.AttackEffect.FIRE));
			}
		}
	}
}
