package defaultmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.DefaultMod;
import defaultmod.cards.CannonSoldier;


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
        this.type = PowerType.BUFF;
        TRIBUTES = tributes;
    }

    @Override
    public void atStartOfTurn() 
    {
		// If owner still has power
    	if (this.owner.hasPower(CannonPower.POWER_ID))
    	{
    		for (int i = 0; i < this.amount; i++)
			{
    			if (this.owner.hasPower(SummonPower.POWER_ID))
    			{
    				CannonSoldier.tribute(AbstractDungeon.player, TRIBUTES, false);
    				// Deal 5 damage to a random enemy for each copy of Cannon Soldier
					AbstractMonster targetMonster = AbstractDungeon.getRandomMonster();
					AbstractDungeon.actionManager.addToTop(new DamageAction(targetMonster, new DamageInfo(this.owner, TURN_DMG, DamageType.THORNS),AbstractGameAction.AttackEffect.FIRE));
    			}
    		}
		}
    }
    
    @Override
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
