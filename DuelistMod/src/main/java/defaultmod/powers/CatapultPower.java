package defaultmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.DefaultMod;
import defaultmod.cards.CatapultTurtle;

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
    private static int TURN_DMG = 4;

    public CatapultPower(AbstractCreature owner) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.updateDescription();
        this.img = new Texture(IMG);
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
    }
    
    @Override
    public void onDrawOrDiscard() 
    {
    	if (this.amount > 0) { this.amount = 0; }
    }
    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	if (this.amount > 0) { this.amount = 0; }
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	if (this.amount > 0) { this.amount = 0; }
	}

    @Override
    public void atStartOfTurn() 
    {
    	// If owner still has power
    	if (this.owner.hasPower(CatapultPower.POWER_ID))
    	{
    		if (this.owner.hasPower(SummonPower.POWER_ID))
    		{
    			// Get # of summons and tribute all
    			int playerSummons = CatapultTurtle.tribute(AbstractDungeon.player, 0, true);
    			if (playerSummons > 0)
    			{
					// Deal 10 damage to all enemies for each Tribute
		        	int[] catapultDmg = new int[] {TURN_DMG, TURN_DMG, TURN_DMG, TURN_DMG, TURN_DMG, TURN_DMG, TURN_DMG, TURN_DMG, TURN_DMG, TURN_DMG, TURN_DMG, TURN_DMG};
		        	for (int i = 0; i < playerSummons; i++)
		        	{
		        		AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(this.owner, catapultDmg, DamageType.THORNS, AbstractGameAction.AttackEffect.SMASH));
		        	}
    			}
	        }
    	}
    	
    	if (this.amount > 0) { this.amount = 0; }
    }
    
    @Override
	public void updateDescription() 
    {
    	this.description = DESCRIPTIONS[0] + TURN_DMG + DESCRIPTIONS[1];
    }
}
