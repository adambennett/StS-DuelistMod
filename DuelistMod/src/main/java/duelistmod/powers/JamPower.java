package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.cards.JamBreeding;

/* 	
 * Lose 10 strength at the end of turn and
 * tribute 1 monster. Then, place this card on top of your draw pile. 
 * 
 * 
 */

public class JamPower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = duelistmod.DuelistMod.makeID("JamPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.JAM_POWER);
    private static int TURN_DMG = 3;
    private static int SUMMONS = 1;

    public JamPower(AbstractCreature owner, int newAmount, int summons) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = newAmount;
        this.img = new Texture(IMG);
        this.isTurnBased = false;
        this.type = PowerType.BUFF;
        SUMMONS = summons;
        this.updateDescription();
    }


    // At the end of the turn, remove gained Strength.
    @Override
    public void atStartOfTurn() 
    {
    	// If owner still has power
    	if (this.owner.hasPower(JamPower.POWER_ID))
    	{
    		JamBreeding.powerSummon(AbstractDungeon.player, SUMMONS, "Jam Token", false);
			
			// Deal 5 damage to a random enemy for each copy of Jam Breeding Machine that has been played
			for (int i = 0; i < this.amount; i++)
			{
				AbstractMonster targetMonster = AbstractDungeon.getRandomMonster();
				AbstractDungeon.actionManager.addToBottom(new DamageAction(targetMonster, new DamageInfo(this.owner, TURN_DMG, DamageInfo.DamageType.THORNS),AbstractGameAction.AttackEffect.FIRE));
			}
		}
    }
    
    @Override
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
