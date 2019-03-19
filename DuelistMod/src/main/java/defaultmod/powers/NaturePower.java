package defaultmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.DefaultMod;
import defaultmod.patches.DuelistCard;

// Passive no-effect power, just lets Toon Monsters check for playability

public class NaturePower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("NaturePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DefaultMod.makePath(DefaultMod.NATURE_POWER);

    public NaturePower(final AbstractCreature owner, final AbstractCreature source, int newAmount) 
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
    public void atStartOfTurn() 
    {    	
    	int roll = AbstractDungeon.cardRandomRng.random(1, 3);
    	switch (roll)
    	{
	    	case 1:
	    		DuelistCard.powerSummon(AbstractDungeon.player, this.amount, "Plant Token", false);
	    		if (DefaultMod.debug) { System.out.println("calling power summon from nature power"); }
	    		break;
	    	case 2:
	    		DuelistCard.powerSummon(AbstractDungeon.player, this.amount, "Insect Token", false);
	    		if (DefaultMod.debug) { System.out.println("calling power summon from nature power"); }
	    		break;
	    	case 3:
	    		DuelistCard.powerSummon(AbstractDungeon.player, this.amount, "Predaplant Token", false);
	    		if (DefaultMod.debug) { System.out.println("calling power summon from nature power"); }
	    		break;
	    	default:
	    		DuelistCard.powerSummon(AbstractDungeon.player, this.amount, "Insect Token", false);
	    		if (DefaultMod.debug) { System.out.println("calling power summon from nature power (Default)"); }
	    		break;
    	}    	
    }
    
    @Override
	public void updateDescription() 
    {
        if (this.amount < 2) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }
        else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]; }
    }
}
