package duelistmod.powers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tokens.Token;
import duelistmod.helpers.BaseGameHelper;

public class OniPower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("OniPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePowerPath("OniPower.png");
    
    public String currentCardColor = "";

    public OniPower(final AbstractCreature owner, final AbstractCreature source, int amt, String cardColor) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = amt;
        this.currentCardColor = cardColor;
        this.updateDescription();
    }
    
    public OniPower(final AbstractCreature owner, final AbstractCreature source, int amt)
    {
    	this(owner, source, amt, "");
    }

    private AbstractCard generateCardFromColor(String color)
    {
    	if (!color.equals(""))
    	{
    		switch (color)
    		{
	    		case "Red":
	    			return BaseGameHelper.getRedCard();	    			
	    		case "Blue":
	    			return BaseGameHelper.getBlueCard();	    			
	    		case "Green":
	    			return BaseGameHelper.getGreenCard();
	    		case "Purple":
	    			return BaseGameHelper.getPurpleCard();
	    		default:
	    			return new Token();
    		}
    	}
    	else
    	{
    		return new Token();
    	}
    }

    private void getNewColor()
    {
    	this.currentCardColor = "";

    	ArrayList<String> colors = new ArrayList<String>();
    	colors.add("Red");
    	colors.add("Blue");
    	colors.add("Green");
    	colors.add("Purple");  
    	this.currentCardColor = colors.get(AbstractDungeon.cardRandomRng.random(colors.size() - 1));
    }
    
    @Override
    public void atStartOfTurn() 
    {
    	for (int i = 0; i < this.amount; i++)
    	{
    		AbstractCard ref = generateCardFromColor(this.currentCardColor);
    		ref.setCostForTurn(-ref.cost);
    		ref.isCostModifiedForTurn = true;
    		DuelistCard.addCardToHand(ref);
    		this.flash();
    	}
    }
    
    @Override
	public void updateDescription() 
    {
    	if (this.currentCardColor.equals("")) { getNewColor(); }
    	if (this.amount == 1) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.currentCardColor + DESCRIPTIONS[2]; }
    	else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.currentCardColor + DESCRIPTIONS[3]; }
    }
}
