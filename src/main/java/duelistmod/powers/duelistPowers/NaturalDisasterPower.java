package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import com.megacrit.cardcrawl.powers.AbstractPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.dto.AnyDuelist;
import duelistmod.helpers.Util;

public class NaturalDisasterPower extends DuelistPower
{	
	public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("NaturalDisasterPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("NaturalDisasterPower.png");
	
	public NaturalDisasterPower(AbstractCreature owner, AbstractCreature source, int turns) {
		this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = turns;
		updateDescription(); 
	}
	
	@Override
	public void onGainVines()
	{
		AnyDuelist duelist = AnyDuelist.from(this);
		AbstractPower pow = Util.leavesPower(this.amount, true, duelist);
		if (pow instanceof VinesPower) {
			VinesPower vp = (VinesPower)pow;
			vp.naturalDisaster = true;
			DuelistCard.applyPowerToSelf(vp);
			return;
		}
		DuelistCard.applyPowerToSelf(pow);
	}

	@Override
	public void updateDescription()
	{
		if (this.amount < 0) { this.amount = 0; }
		if (this.amount == 1) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }
		else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]; }
		
	}

}
