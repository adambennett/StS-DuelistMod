package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;

// Passive no-effect power, just lets Toon Monsters check for playability

public class CyberEltaninPower extends DuelistPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("CyberEltaninPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");

    public CyberEltaninPower(int dmgMod, int turns) 
    {
    	this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = AbstractDungeon.player;
        this.amount = dmgMod;
        this.amount2 = turns;
        this.updateDescription();
    }
    
    @Override
    public void atEndOfTurn(boolean isPlayer)
    {
    	if (this.amount2 > 0) { this.amount2--; updateDescription(); }
    	if (this.amount2 < 1) { DuelistCard.removePower(this, this.owner); }
    }
    
    @Override
	public void updateDescription() 
    {
    	if (this.amount < 0) { this.amount = 0; }
        if (this.amount2 == 1) { this.description = DESCRIPTIONS[0] + (this.amount * 10) + DESCRIPTIONS[1] + this.amount2 + DESCRIPTIONS[2]; }
        else { this.description = DESCRIPTIONS[0] + (this.amount * 10) + DESCRIPTIONS[1] + this.amount2 + DESCRIPTIONS[3]; }
    }
}
