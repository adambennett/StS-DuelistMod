package defaultmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.DefaultMod;

/* 	
 * Lose 10 strength at the end of turn and
 * tribute 1 monster. Then, place this card on top of your draw pile. 
 * 
 * 
 */

public class CannonPower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = defaultmod.DefaultMod.makeID("CannonPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DefaultMod.makePath(DefaultMod.CANNON_SOLDIER_POWER);
    private static int TURN_DMG = 5;
    private static int TRIBUTES = 1;

    public CannonPower(AbstractCreature owner, int newAmount, int tributes) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = newAmount;
        this.updateDescription();
        this.img = new Texture(IMG);
        this.isTurnBased = false;
        TRIBUTES = tributes;
    }


    // At the end of the turn, remove gained Strength.
    @Override
    public void atStartOfTurn() 
    {
    	// Only do this if player does not also have Catapult Turtle - in that case they would want the 10 damage to all over 5 a random enemy
	    if (!this.owner.hasPower(CatapultPower.POWER_ID))
	    {
    		// If owner still has power
	    	if (this.owner.hasPower(CannonPower.POWER_ID))
	    	{
	    		for (int i = 0; i < this.amount; i++)
				{
	    			if (this.owner.hasPower(SummonPower.POWER_ID))
	    			{
			    		// Tribute Summon for each copy of Cannon Soldier
			        	AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, SummonPower.POWER_ID, TRIBUTES));
			        	
			        	// Check for Obelisk after tribute
			        	if (this.owner.hasPower(ObeliskPower.POWER_ID))
			        	{
			        		int[] temp = new int[] {6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6};
			    			for (int z : temp) { z = z * TRIBUTES; }
			        		AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this.owner, temp, DamageType.THORNS, AbstractGameAction.AttackEffect.SMASH)); 
			        	}
						
						// Deal 5 damage to a random enemy for each copy of Cannon Soldier
						AbstractMonster targetMonster = AbstractDungeon.getRandomMonster();
						AbstractDungeon.actionManager.addToBottom(new DamageAction(targetMonster, new DamageInfo(this.owner, TURN_DMG, DamageInfo.DamageType.NORMAL),AbstractGameAction.AttackEffect.FIRE));
	    			}
	    		}
			}
	    }
    }
    
    public void updateDescription() 
    {
    	if (this.amount == 1)
    	{
    		this.description = DESCRIPTIONS[0] + TRIBUTES + DESCRIPTIONS[1] + TURN_DMG + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3];
    	}
    	else
    	{
    		this.description = DESCRIPTIONS[0] + TRIBUTES * this.amount + DESCRIPTIONS[1] + TURN_DMG + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[4] + this.amount * TURN_DMG + DESCRIPTIONS[5];
    	}
    }
}
