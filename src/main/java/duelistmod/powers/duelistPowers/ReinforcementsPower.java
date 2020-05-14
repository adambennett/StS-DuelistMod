package duelistmod.powers.duelistPowers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.variables.*;



public class ReinforcementsPower extends DuelistPower 
{
    public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("ReinforcementsPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.REINFORCEMENTS_POWER);
    
    public ReinforcementsPower(final AbstractCreature owner, final AbstractCreature source) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.updateDescription();
    }
    
    @Override
    public void atEndOfTurn(final boolean isPlayer) 
    {
    	if (isPlayer)
    	{
    		DuelistCard.removePower(this, this.owner);
    	}
    }    
    
    // Specifically does not use this because of tribute monster ordering
    // Logic is inside DuelistMod.java onCardUse subscriber and inside DuelistCard.java tribute func
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) { }
    
    @Override
    public void onEnemyUseCard(AbstractCard card)
    {
    	if (card instanceof DuelistCard && card.hasTag(Tags.MONSTER))
    	{
    		DuelistCard dC = (DuelistCard)card;
    		if (dC.tributes < 1) { DuelistCard.summon(AbstractDungeon.player, 1, (DuelistCard)card); }
    	}  	
    }
  
    @Override
	public void updateDescription() 
    {
    	this.description = DESCRIPTIONS[0];
    }
}
