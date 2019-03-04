package defaultmod.powers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.DefaultMod;
import defaultmod.actions.unique.*;


public class JinzoPower extends AbstractPower 
{
    public AbstractCreature source;
    public static final String POWER_ID = DefaultMod.makeID("JinzoPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DefaultMod.makePath(DefaultMod.JINZO_POWER);
    ArrayList<AbstractCard> modCards = new ArrayList<AbstractCard>();
    
    public JinzoPower(AbstractCreature owner) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;     
        this.img = new Texture(IMG);
        this.isTurnBased = false;
        this.type = PowerType.BUFF;
        this.amount  = 0;
        this.updateDescription();
    }
    
    @Override
    public void onInitialApplication() 
    {
    	AbstractDungeon.actionManager.addToTop(new JinzoAction(1));
    }
    
    @Override
    public void onRemove() 
    {
    	AbstractDungeon.actionManager.addToBottom(new JinzoRemovedPower(1));
    }
    
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) 
    {
    	AbstractDungeon.actionManager.addToTop(new JinzoAction(1));
    }
    
    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) 
    {
    	AbstractDungeon.actionManager.addToTop(new JinzoAction(1));
    }
    
    @Override
    public void onDrawOrDiscard() 
    {
    	AbstractDungeon.actionManager.addToTop(new JinzoAction(1));
    }
  
    @Override
    public void atStartOfTurnPostDraw() 
    {
    	flash();AbstractDungeon.actionManager.addToTop(new JinzoAction(1));
    }
    
    @Override
	public void updateDescription() 
    {
    	this.description = DESCRIPTIONS[0]; 
    }
}
