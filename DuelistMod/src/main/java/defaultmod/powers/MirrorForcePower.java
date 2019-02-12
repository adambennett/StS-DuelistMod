package defaultmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.DefaultMod;
import defaultmod.cards.MirrorForce;

/* 	
 * Lose 10 strength at the end of turn and
 * tribute 1 monster. Then, place this card on top of your draw pile. 
 * 
 * 
 */

public class MirrorForcePower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = defaultmod.DefaultMod.makeID("MirrorForcePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DefaultMod.makePath(DefaultMod.MIRROR_FORCE_POWER);
    private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
    public boolean upgraded = false;
    public int MULT = 1;

    public MirrorForcePower(AbstractCreature owner, int newAmount, boolean upgrade) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = newAmount;
        this.updateDescription();
        this.img = new Texture(IMG);
        this.isTurnBased = false;
        this.type = PowerType.BUFF;
        if (upgrade) { MULT = 2; }
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount)
    {
    	int[] damageAmounts = new int[] {damageAmount, damageAmount, damageAmount, damageAmount, damageAmount, damageAmount, damageAmount, damageAmount, damageAmount, damageAmount};
    	for (int i = 0; i < damageAmounts.length; i++) { damageAmounts[i] = damageAmount * MULT; }
    	MirrorForce.attackAll(AFX, damageAmounts, DamageType.THORNS);
    	AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, this, this.amount));
    	return damageAmount;
    }
    
    @Override
	public void updateDescription() 
    {
    	if (upgraded)
    	{
    		this.description = DESCRIPTIONS[0] + DESCRIPTIONS[2];
    	}
    	else
    	{
    		this.description = DESCRIPTIONS[0] + DESCRIPTIONS[1];
    	}
    }
}
