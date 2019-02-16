package defaultmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.DefaultMod;
import defaultmod.patches.DuelistCard;


public class PotGenerosityPower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("PotGenerosityPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DefaultMod.makePath(DefaultMod.POT_GENEROSITY_POWER);
    
    private static int MANA = 1;

    public PotGenerosityPower(final AbstractCreature owner, final AbstractCreature source, int newAmount) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = newAmount;
        this.updateDescription();
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	if (this.amount > 0) { this.amount--; if (this.amount < 1) { DuelistCard.removePower(this, AbstractDungeon.player); } }
    	else { DuelistCard.removePower(this, AbstractDungeon.player); }
	}

    @Override
	public void updateDescription() {
        this.description = DESCRIPTIONS[0] + MANA + DESCRIPTIONS[1];
    }
}
