package defaultmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.DefaultMod;

// Passive no-effect power, just lets Toon Monsters check for playability

public class GreedShardPower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = defaultmod.DefaultMod.makeID("GreedShardPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    //public static final String IMG = DefaultMod.makePath(DefaultMod.GREED_SHARD_POWER);

    public GreedShardPower(final AbstractCreature owner, final AbstractCreature source) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.updateDescription();
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        //this.img = new Texture(IMG);
        this.source = source;

    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
