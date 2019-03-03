package defaultmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.DefaultMod;
import defaultmod.interfaces.*;

// 

public class SwordsBurnPower extends AbstractPower
{
    public AbstractCreature source;

    public static final String POWER_ID = defaultmod.DefaultMod.makeID("SwordsBurnPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DefaultMod.makePath(DefaultMod.SWORDS_BURN_POWER);
    public boolean finished = false;
    
    public SwordsBurnPower(final AbstractCreature owner, final AbstractCreature source) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.updateDescription();
    }
 
    @Override
    public void onDrawOrDiscard() 
    {
    	if (this.amount > 0) { this.amount = 0; }
    }
    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	finished = false;
    	if (this.amount > 0) { this.amount = 0; }
    }
    
    @Override
    public void atStartOfTurn() 
    {
    	finished = false;
    	if (this.amount > 0) { this.amount = 0; }
    }
    
    @Override
    public void onEvokeOrb(AbstractOrb orb) 
    {
    	if (!finished) 
    	{ 
    		if (Loader.isModLoaded("conspire") && Loader.isModLoaded("ReplayTheSpireMod")){ RandomOrbHelperDualMod.channelRandomOrb(); }
    		else if (Loader.isModLoaded("conspire") && !Loader.isModLoaded("ReplayTheSpireMod")){ RandomOrbHelperCon.channelRandomOrb(); }
    		else if (Loader.isModLoaded("ReplayTheSpireMod") && !Loader.isModLoaded("conspire")) { RandomOrbHelperRep.channelRandomOrb(); }
    		else { RandomOrbHelper.channelRandomOrb(); }
    	}
    	finished = true;
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	finished = false;
    	if (this.amount > 0) { this.amount = 0; }
	}
    

    @Override
	public void updateDescription() 
    {
    	this.description = DESCRIPTIONS[0]; 
    }
}
