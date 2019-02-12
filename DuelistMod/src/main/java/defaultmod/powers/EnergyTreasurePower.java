package defaultmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.DefaultMod;
import defaultmod.patches.DuelistCard;


public class EnergyTreasurePower extends AbstractPower 
{
    public AbstractCreature source;
    public static final String POWER_ID = defaultmod.DefaultMod.makeID("EnergyTreasurePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DefaultMod.makePath(DefaultMod.ENERGY_TREASURE_POWER);

    public EnergyTreasurePower(final AbstractCreature owner, int newAmount) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.updateDescription();
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.amount = newAmount;
    }

    @Override
	public void updateDescription() {
        this.description = DESCRIPTIONS[0] + (50 + this.amount) + DESCRIPTIONS[1];
    }
    
    @Override
    public void atEnergyGain() 
    {
    	DuelistCard.gainGold(this.amount + 50, AbstractDungeon.player, true);
    }
}
