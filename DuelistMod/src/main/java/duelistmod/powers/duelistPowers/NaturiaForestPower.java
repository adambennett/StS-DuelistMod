package duelistmod.powers.duelistPowers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.actions.unique.*;


public class NaturiaForestPower extends DuelistPower 
{
    public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("NaturiaForestPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    //public static final String IMG = DuelistMod.makePowerPath("NaturiaForestPower.png");
    public static final String IMG = DuelistMod.makePowerPath("PlaceholderPower.png");
    ArrayList<AbstractCard> modCards = new ArrayList<AbstractCard>();
    
    public NaturiaForestPower(int turns) 
    {
    	this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.canGoNegative = false;
        this.img = new Texture(IMG);
        this.source = AbstractDungeon.player;
        this.amount = turns;
		updateDescription(); 
    }
    
    @Override
    public void onInitialApplication() 
    {
    	AbstractDungeon.actionManager.addToTop(new NaturiaForestAction(1));
    }
    
    @Override
    public void atEndOfTurn(boolean isPlayer)
    {
    	this.amount--;
    	updateDescription();
    }
    
    @Override
    public void onRemove() 
    {
    	AbstractDungeon.actionManager.addToBottom(new NaturiaForestRemovedAction(1));
    }
    
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) 
    {
    	AbstractDungeon.actionManager.addToTop(new NaturiaForestAction(1));
    }
    
    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) 
    {
    	AbstractDungeon.actionManager.addToTop(new NaturiaForestAction(1));
    }
    
    @Override
    public void onDrawOrDiscard() 
    {
    	AbstractDungeon.actionManager.addToTop(new NaturiaForestAction(1));
    }
  
    @Override
    public void atStartOfTurnPostDraw() 
    {
    	flash();AbstractDungeon.actionManager.addToTop(new NaturiaForestAction(1));
    }
    
    @Override
	public void updateDescription() 
    {
    	if (this.amount == 1) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }
    	else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]; }
    	if (this.amount < 1) { DuelistCard.removePower(this, this.owner); }
    }
}
