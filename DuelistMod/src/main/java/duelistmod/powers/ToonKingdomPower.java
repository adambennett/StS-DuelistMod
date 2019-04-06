package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.patches.DuelistCard;

// Passive no-effect power, just lets Toon Monsters check for playability

public class ToonKingdomPower extends AbstractPower 
{
    public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("ToonKingdomPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.TOON_WORLD_POWER);
    public static int TOON_DMG = 6;
    
    public ToonKingdomPower(final AbstractCreature owner, final AbstractCreature source, int toonDmg) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        TOON_DMG = toonDmg;
        this.amount = TOON_DMG;
        if (DuelistMod.challengeMode)
        {
        	this.amount += 2;
        	TOON_DMG += 2;
        }
        this.updateDescription();
    }
    
    @Override
    public void onDrawOrDiscard() 
    {
    	if (this.amount != TOON_DMG) { this.amount = TOON_DMG; }
    }
    
    @Override
    public void atStartOfTurn() 
    {
    	if (this.amount != TOON_DMG) { this.amount = TOON_DMG; }
    }
    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	if (this.amount != TOON_DMG) { this.amount = TOON_DMG; }
    	if (c.hasTag(Tags.TOON) && !c.originalName.equals("Toon Kingdom") && !c.originalName.equals("Toon World")) 
    	{ 
    		if (TOON_DMG > 0) { DuelistCard.damageSelfNotHP(TOON_DMG); }
    		if (TOON_DMG > 0) { TOON_DMG--; } 
    		//AbstractCard randomToon = (DuelistCard) DuelistCard.returnTrulyRandomFromSet(Tags.TOON);
    		//AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(randomToon, 1, true, false));
    	}
    	
    	this.amount = TOON_DMG;
    	updateDescription();
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	if (TOON_DMG > 0) 
    	{
    		TOON_DMG--;
    		this.amount = TOON_DMG;
    	}
    	if (this.amount != TOON_DMG) { this.amount = TOON_DMG; }
    	updateDescription();
	}

    @Override
	public void updateDescription() 
    {
        if (TOON_DMG < 1) { this.description = DESCRIPTIONS[2]; }
    	else { this.description = DESCRIPTIONS[0] + TOON_DMG + DESCRIPTIONS[1]; }
    }
}
