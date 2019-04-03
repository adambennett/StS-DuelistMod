package duelistmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.cards.MirrorForce;

/* 	
 * Lose 10 strength at the end of turn and
 * tribute 1 monster. Then, place this card on top of your draw pile. 
 * 
 * 
 */

public class MirrorForcePower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = duelistmod.DuelistMod.makeID("MirrorForcePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.MIRROR_FORCE_POWER);
    private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
    public static boolean upgraded = false;
    public int MULT = 1;
    public int PLAYER_BLOCK = 0;

    public MirrorForcePower(AbstractCreature owner, int newAmount, boolean upgrade) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = newAmount;       
        this.img = new Texture(IMG);
        this.isTurnBased = false;
        this.type = PowerType.BUFF;
        if (upgrade) { MULT = 2; upgraded = true; }
       // PLAYER_BLOCK = AbstractDungeon.player.currentBlock;
        this.updateDescription();
    }
    
    @Override
    public void onDrawOrDiscard() 
    {
    	//PLAYER_BLOCK = AbstractDungeon.player.currentBlock;
    }
    
    @Override
    public void atStartOfTurn() 
    {
    	//PLAYER_BLOCK = AbstractDungeon.player.currentBlock;
    }
    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	//PLAYER_BLOCK = AbstractDungeon.player.currentBlock;
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	//PLAYER_BLOCK = AbstractDungeon.player.currentBlock;
	}
   
    @Override
    public int onAttacked(DamageInfo info, int damageAmount)
    {
    	if (info.owner instanceof AbstractMonster)
    	{
    		//PLAYER_BLOCK = AbstractDungeon.player.currentBlock;
        	if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner && this.amount > 0) 
        	{
    	    	int[] damageAmounts = new int[] {damageAmount, damageAmount, damageAmount, damageAmount, damageAmount, damageAmount, damageAmount, damageAmount, damageAmount, damageAmount};
    	    	for (int i = 0; i < damageAmounts.length; i++) { damageAmounts[i] = (damageAmount + PLAYER_BLOCK) * MULT; }
    	    	if (DuelistMod.debug)
    	    	{
    	    		System.out.println("theDuelist:MirrorForcePower:onAttacked() ---> PlayerBlock: " + PLAYER_BLOCK + " :: damageAmount: " + damageAmount + " :: MULT: " + MULT + " :: damangeAmounts[0]: " + damageAmounts[0]);
    	    	}
    	    	MirrorForce.attackAll(AFX, damageAmounts, DamageType.THORNS);
    	    	AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, this, 1));
    	    	if (this.amount < 1)
    	    	{
    	    		upgraded = false;
    	    		MULT = 1;
    	    	}
        	}
    	}
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
