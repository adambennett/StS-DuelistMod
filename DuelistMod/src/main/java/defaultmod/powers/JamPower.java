package defaultmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
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
    public static final String IMG = DefaultMod.makePath(DefaultMod.SUMMON_POWER);
    private static final int TURN_DMG = 5;

    public JamPower(final AbstractCreature owner, final AbstractCreature source, final int amount) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;

    }

    // At the end of the turn, remove gained Strength.
    @Override
    public void atStartOfTurn() 
    {
    	int temp = 0;
    	AbstractMonster targetMonster = AbstractDungeon.getRandomMonster();
    	if (!this.owner.hasPower(SummonPower.POWER_ID)) 
    	{
    		AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(this.owner, this.owner, new SummonPower(this.owner, this.amount), this.amount));
    	}
    	else
    	{
    		temp = (this.owner.getPower(SummonPower.POWER_ID).amount);
    		if (!(temp > 5 - this.amount)) 
    		{
    			AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(this.owner, this.owner, new SummonPower(this.owner, this.amount), this.amount));
    		}
    	}
    	AbstractDungeon.actionManager.addToBottom(new DamageAction(targetMonster, new DamageInfo(this.owner, TURN_DMG, DamageInfo.DamageType.NORMAL),AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }
    
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
