package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.DexterityPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.variables.Strings;

// 

public class SwordsConcealPower extends DuelistPower
{
    public AbstractCreature source;

    public static final String POWER_ID = duelistmod.DuelistMod.makeID("SwordsConcealPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.SWORDS_CONCEAL_POWER);
    
    private boolean isPlayed = false;
    
    public SwordsConcealPower(final AbstractCreature owner, final AbstractCreature source, int newAmount, boolean playedCard) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = newAmount;
        isPlayed = playedCard;
        this.updateDescription();
    }
 
    @Override
    public void onDrawOrDiscard() 
    {
    	updateDescription();
    }
    
    @Override
    public void onEnemyUseCard(AbstractCard card)
    {
    	onPlayCard(card, null);
    }
    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	DuelistCard.staticBlock(this.amount, true);
    	updateDescription();
    }
    
    @Override
    public void onDexChange()
    {
    	updateDescription();
    }
    
    @Override
    public void atStartOfTurn() 
    {
    	updateDescription();
    }
    
    public void onEvokeOrb(AbstractOrb orb) 
    {
    	updateDescription();
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	if (!isPlayed)
    	{
    		if (this.amount > 0) 
    		{ 
    			this.amount--; 
    			if (this.amount < 1)
    			{
    				DuelistCard.removePower(this, AbstractDungeon.player);
    			}
    		}
    	}
    	updateDescription();
	}
    

    @Override
	public void updateDescription() 
    {
    	if (this.owner.hasPower(DexterityPower.POWER_ID)) { this.description = DESCRIPTIONS[0] + (this.amount + this.owner.getPower(DexterityPower.POWER_ID).amount) + DESCRIPTIONS[1]; }
    	else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }
    }
}
