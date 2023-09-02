package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.dto.AnyDuelist;
import duelistmod.orbs.*;

public class AutorokketDragonPower extends DuelistPower {
	public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("AutorokketDragonPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("AutorokketDragonPower.png");

	public AutorokketDragonPower(AbstractCreature owner, AbstractCreature source, int turns) {
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
	public void onChannel(AbstractOrb o) {
		if (o instanceof Lava || o instanceof FireOrb || o instanceof DuelistHellfire || o instanceof Blaze) {
			DuelistCard.burnAllEnemies(this.amount, AnyDuelist.from(this));
		}
	}

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
}
