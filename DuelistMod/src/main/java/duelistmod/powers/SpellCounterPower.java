package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;


public class SpellCounterPower extends AbstractPower 
{
    public AbstractCreature source;
    public static final String POWER_ID = duelistmod.DuelistMod.makeID("SpellCounterPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.SPELL_COUNTER_POWER);

    public SpellCounterPower(final AbstractCreature owner, final AbstractCreature source, final int amount) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = amount;
        this.updateDescription();
    }

    @Override
	public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
    
}
