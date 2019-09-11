package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Strings;


public class CannonPower extends TwoAmountPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("CannonPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.CANNON_SOLDIER_POWER);

    public CannonPower(AbstractCreature owner, int damage, int tributes) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = damage;
        this.amount2 = tributes;
        this.img = new Texture(IMG);
        this.isTurnBased = false;
        this.type = PowerType.BUFF;
        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() 
    {
    	int tribs = DuelistCard.powerTribute(AbstractDungeon.player, 1, false);
    	if (tribs > 0)
    	{
    		for (int i = 0; i < tribs; i++)
    		{
    			AbstractMonster targetMonster = AbstractDungeon.getRandomMonster();
        		AbstractDungeon.actionManager.addToTop(new DamageAction(targetMonster, new DamageInfo(this.owner, this.amount2, DamageType.THORNS),AbstractGameAction.AttackEffect.FIRE));
    		}
    	}
    }
    
    @Override
	public void updateDescription() 
    {
    	if (this.amount == 1)
    	{
    		this.description = DESCRIPTIONS[0] + this.amount2 + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    	}
    	else
    	{
    		this.description = DESCRIPTIONS[0] + this.amount2 + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[3];
    	}
    }
}
