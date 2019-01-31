package defaultmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
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

public class JamPower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = defaultmod.DefaultMod.makeID("JamPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DefaultMod.makePath(DefaultMod.JAM_POWER);
    private static int TURN_DMG = 5;
    private static int SUMMONS = 1;

    public JamPower(AbstractCreature owner, int newAmount, int summons) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = newAmount;
        this.updateDescription();
        this.img = new Texture(IMG);
        this.isTurnBased = false;
        SUMMONS = summons;
    }


    // At the end of the turn, remove gained Strength.
    @Override
    public void atStartOfTurn() 
    {
    	// If owner still has power
    	if (this.owner.hasPower(JamPower.POWER_ID))
    	{
    		// Summon 1 if possible
			int temp = 0;
			if (!this.owner.hasPower(SummonPower.POWER_ID)) 
			{
				AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner, this.owner, new SummonPower(this.owner, SUMMONS), SUMMONS));
			}
			else
			{
				temp = (this.owner.getPower(SummonPower.POWER_ID).amount);
				if (!(temp > 5 - this.amount)) 
				{
					AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner, this.owner, new SummonPower(this.owner, SUMMONS), SUMMONS));
				}
				else
				{
					AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this.owner, this.owner, new SummonPower(this.owner, 5 - temp), 5 - temp));
				}
			}
			
			// Deal 5 damage to a random enemy for each copy of Jam Breeding Machine that has been played
			for (int i = 0; i < this.amount; i++)
			{
				AbstractMonster targetMonster = AbstractDungeon.getRandomMonster();
				AbstractDungeon.actionManager.addToBottom(new DamageAction(targetMonster, new DamageInfo(this.owner, TURN_DMG, DamageInfo.DamageType.NORMAL),AbstractGameAction.AttackEffect.FIRE));
			}
		}
    }
    
    public void updateDescription() 
    {
    	if (this.amount == 1)
    	{
    		this.description = DESCRIPTIONS[0] + SUMMONS + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
    	}
    	else
    	{
    		this.description = DESCRIPTIONS[0] + SUMMONS + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[3];
    	}
    }
}
