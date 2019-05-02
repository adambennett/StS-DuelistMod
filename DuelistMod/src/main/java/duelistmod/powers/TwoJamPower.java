package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.cards.JamBreeding;


public class TwoJamPower extends TwoAmountPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("TwoJamPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.JAM_POWER);
    public int turnDmg = 3;

    public TwoJamPower(AbstractCreature owner, int newAmount, int summons) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = newAmount;
        this.amount2 = summons;
        this.img = new Texture(IMG);
        this.isTurnBased = false;
        this.type = PowerType.BUFF;
        this.updateDescription();
    }
    
    public TwoJamPower(AbstractCreature owner, int newAmount, int summons, int dmg) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = newAmount;
        this.amount2 = summons;
        this.img = new Texture(IMG);
        this.isTurnBased = false;
        this.type = PowerType.BUFF;
        this.turnDmg = dmg;
        this.updateDescription();
    }


    // At the end of the turn, remove gained Strength.
    @Override
    public void atStartOfTurn() 
    {
    	// If owner still has power
    	if (this.owner.hasPower(TwoJamPower.POWER_ID))
    	{
    		JamBreeding.powerSummon(AbstractDungeon.player, this.amount2, "Jam Token", false);
			
			// Deal 5 damage to a random enemy for each copy of Jam Breeding Machine that has been played
			for (int i = 0; i < this.amount; i++)
			{
				AbstractMonster targetMonster = AbstractDungeon.getRandomMonster();
				AbstractDungeon.actionManager.addToBottom(new DamageAction(targetMonster, new DamageInfo(this.owner, turnDmg, DamageInfo.DamageType.THORNS),AbstractGameAction.AttackEffect.FIRE));
			}
		}
    }
    
    @Override
	public void updateDescription() 
    {
    	if (this.amount == 1)
    	{
    		this.description = DESCRIPTIONS[0] + this.amount2 + DESCRIPTIONS[1] + this.turnDmg + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3];
    	}
    	else
    	{
    		this.description = DESCRIPTIONS[0] + this.amount2 + DESCRIPTIONS[1] + this.turnDmg + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[4];
    	}
    }
}
