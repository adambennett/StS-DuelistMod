package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.AbstractStance;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;

public class SpiritForcePower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = duelistmod.DuelistMod.makeID("SpiritForcePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("SpiritForcePower.png");

    public SpiritForcePower(final AbstractCreature owner, final AbstractCreature source, int amt) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = amt;
        this.updateDescription();
    }
    
    
    @Override
    public void onChangeStance(final AbstractStance oldStance, final AbstractStance newStance) {
        if (oldStance == null && newStance == null) {
            return;
        }
        String oldStanceName = oldStance != null && oldStance.name != null ? oldStance.name : "Neutral";
        String newStanceName = newStance != null && newStance.name != null ? newStance.name : "Neutral";
        boolean changedStance = !oldStanceName.equals("Neutral") && !newStanceName.equals("Neutral") && !newStanceName.equals(oldStanceName);
        if (changedStance) {
            this.flash();
            DuelistCard.gainTempHP(this.amount);
        }
    }

    @Override
	public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
