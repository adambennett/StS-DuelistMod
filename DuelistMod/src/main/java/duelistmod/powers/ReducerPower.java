package duelistmod.powers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.actions.unique.ReduceRandomCardsAction;
import duelistmod.interfaces.DuelistCard;


public class ReducerPower extends AbstractPower 
{
    public AbstractCreature source;
    public static final String POWER_ID = DuelistMod.makeID("ReducerPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.REDUCER_POWER);
    ArrayList<AbstractCard> modCards = new ArrayList<AbstractCard>();
    
    public ReducerPower(AbstractCreature owner, int cards) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;     
        this.img = new Texture(IMG);
        this.isTurnBased = false;
        this.type = PowerType.BUFF;
        this.amount  = cards;
        this.updateDescription();
    }
  
    @Override
    public void atStartOfTurnPostDraw() 
    {
    	flash();
    	AbstractDungeon.actionManager.addToBottom(new ReduceRandomCardsAction(this.amount, 1));
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	updateDescription();
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	if (this.amount > 0) { DuelistCard.reducePower(this, this.owner, 1); }
    	else { DuelistCard.removePower(this, this.owner); }
    	updateDescription();
	}
    
    @Override
	public void updateDescription() 
    {
    	if (this.amount < 2) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }
    	else if (this.amount > 5) { this.amount = 5; this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]; }
    	else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]; }
    }
}
