package defaultmod.powers;

import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;

import defaultmod.DefaultMod;
import defaultmod.patches.DuelistCard;

/* 	
 * Lose 10 strength at the end of turn and
 * tribute 1 monster. Then, place this card on top of your draw pile. 
 * 
 * 
 */

public class MagicCylinderPower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = defaultmod.DefaultMod.makeID("MagicCylinderPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DefaultMod.makePath(DefaultMod.MAGIC_CYLINDER_POWER);
    
    public boolean upgraded = false;
    public int MIN_TURNS = 1;
    public int MAX_TURNS = 3;

    public MagicCylinderPower(AbstractCreature owner, int newAmount, boolean upgrade) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = newAmount;
        this.img = new Texture(IMG);
        this.isTurnBased = false;
        this.type = PowerType.BUFF;
        if (upgrade) { upgraded = true; }
        this.updateDescription();
    }
    

    @Override
    public int onLoseHp(int damageAmount)
    {
    	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ReflectionPower(AbstractDungeon.player, 5), 5));
    	if (upgraded) 
    	{
    		int randomTurnNum = ThreadLocalRandom.current().nextInt(MIN_TURNS, MAX_TURNS);
    		DuelistCard.applyRandomBuffPlayer(AbstractDungeon.player, randomTurnNum, true);
    	}
    	
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
