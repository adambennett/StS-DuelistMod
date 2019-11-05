package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.pools.machine.CatapultTurtle;
import duelistmod.variables.Strings;

/* 	
 * Lose 10 strength at the end of turn and
 * tribute 1 monster. Then, place this card on top of your draw pile. 
 * 
 * 
 */

public class CatapultPower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = duelistmod.DuelistMod.makeID("CatapultPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.CATAPULT_TURTLE_POWER);

    public CatapultPower(AbstractCreature owner, AbstractCreature source, int amount) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;   
        this.source = source;
        this.amount = amount;
        this.img = new Texture(IMG);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.updateDescription();
    }
    
    @Override
    public void atStartOfTurn() 
    {
		if (this.owner.hasPower(SummonPower.POWER_ID) && this.amount > 0)
		{
			// Get # of summons and tribute all
			int playerSummons = CatapultTurtle.powerTribute(AbstractDungeon.player, 0, true);
			if (playerSummons > 0) { DuelistCard.attackAllEnemiesThorns(this.amount * playerSummons); }
        }   	
    }
    
    @Override
	public void updateDescription() 
    {
    	this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
