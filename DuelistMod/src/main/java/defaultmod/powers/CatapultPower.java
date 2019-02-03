package defaultmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.DefaultMod;

/* 	
 * Lose 10 strength at the end of turn and
 * tribute 1 monster. Then, place this card on top of your draw pile. 
 * 
 * 
 */

public class CatapultPower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = defaultmod.DefaultMod.makeID("CatapultPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DefaultMod.makePath(DefaultMod.CATAPULT_TURTLE_POWER);
    private static int TURN_DMG = 3;

    public CatapultPower(AbstractCreature owner) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.updateDescription();
        this.img = new Texture(IMG);
        this.isTurnBased = false;
    }


    // At the end of the turn, remove gained Strength.
    @Override
    public void atStartOfTurn() 
    {
    	// If owner still has power
    	if (this.owner.hasPower(CatapultPower.POWER_ID))
    	{
    		if (this.owner.hasPower(SummonPower.POWER_ID))
    		{
    			// Get # of summons
    			int playerSummons = this.owner.getPower(SummonPower.POWER_ID).amount;
    			
    			if (playerSummons > 0)
    			{
		    		// Tribute all summons
		        	AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, SummonPower.POWER_ID, playerSummons));
		        	
		        	// Check for Obelisk after tribute
		        	if (this.owner.hasPower(ObeliskPower.POWER_ID))
		        	{
		        		int[] obeliskDmg = new int[] {6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6};
		    			for (int z : obeliskDmg) { z = z * playerSummons; }
		        		AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(this.owner, obeliskDmg, DamageType.THORNS, AbstractGameAction.AttackEffect.SMASH)); 
		        	}
					
					// Deal 10 damage to all enemies for each Tribute
		        	int[] catapultDmg = new int[] {TURN_DMG, TURN_DMG, TURN_DMG, TURN_DMG, TURN_DMG, TURN_DMG, TURN_DMG, TURN_DMG, TURN_DMG, TURN_DMG, TURN_DMG, TURN_DMG};
		        	for (int i = 0; i < playerSummons; i++)
		        	{
		        		AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(this.owner, catapultDmg, DamageType.THORNS, AbstractGameAction.AttackEffect.SMASH));
		        	}
    			}
	        }
    	}
    }
    
    public void updateDescription() 
    {
    	this.description = DESCRIPTIONS[0] + TURN_DMG + DESCRIPTIONS[1];
    }
}
