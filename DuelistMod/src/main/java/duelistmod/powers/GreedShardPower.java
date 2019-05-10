package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.interfaces.DuelistCard;

// Passive no-effect power, just lets Toon Monsters check for playability

public class GreedShardPower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("GreedShardPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.GREED_SHARD_POWER);
    public CardTags tag = Tags.AQUA;
    private String typeString;

    public GreedShardPower(final AbstractCreature owner, final AbstractCreature source, int newAmount, CardTags tag) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = newAmount;
        this.tag = tag;
        this.updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() 
    {
    	DuelistCard.drawTag(this.amount, this.tag);
    	updateDescription();
    }

    @Override
	public void updateDescription() 
    {
    	// Format tag
    	this.typeString = tag.toString().toLowerCase();
		String temp = this.typeString.substring(0, 1).toUpperCase();
		this.typeString = temp + this.typeString.substring(1);
		
    	if (this.amount > 1) { this.description = DESCRIPTIONS[0] + this.amount + " #y" + this.typeString + DESCRIPTIONS[2]; }
    	else { this.description = DESCRIPTIONS[0] + this.amount + " #y" + this.typeString + DESCRIPTIONS[1]; }
    }
}
