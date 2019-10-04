package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPower;
import duelistmod.variables.Strings;

// Passive no-effect power, just lets Toon Monsters check for playability

public class VioletCrystalPower extends DuelistPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = duelistmod.DuelistMod.makeID("VioletCrystalPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.VIOLET_POWER);

    public VioletCrystalPower(int dmgMod, int constrictedBuff) 
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
        this.amount2 = constrictedBuff;
        this.updateDescription();
    }
    
   
    @Override
	public void updateDescription() 
    {
        this.description = DESCRIPTIONS[0] + (this.amount * 10) + DESCRIPTIONS[1] + (this.amount * 10) + DESCRIPTIONS[2] + this.amount2 + DESCRIPTIONS[3];
    }
}
