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
import duelistmod.patches.DuelistCard;

// Passive no-effect power, just lets Toon Monsters check for playability

public class CallGravePower extends AbstractPower 
{
    public AbstractCreature source;

    public static final String POWER_ID = DuelistMod.makeID("CallGravePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = DuelistMod.makePath(Strings.CALL_GRAVE_POWER);

    public CallGravePower(final AbstractCreature owner, final AbstractCreature source, int newAmount) 
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;        
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
        this.amount = newAmount;
        this.updateDescription();
    }
    
    @Override
    public void onDrawOrDiscard() 
    {
    	
    }
    
    @Override
    public void atStartOfTurn() 
    {
    	if (DuelistMod.monstersThisCombat.size() > 0)
    	{
    		//this.flash();
    		System.out.println("theDuelist:CallGrave:atEndOfTurn() ---> monstersInCombat size was > 0");
    		ArrayList<DuelistCard> allowedResummons = new ArrayList<DuelistCard>();
    		ArrayList<DuelistCard> actualResummons = new ArrayList<DuelistCard>();
    		int loopMax = this.amount + 3;
    		int loopCount = 0;
    		for (DuelistCard c : DuelistMod.monstersThisCombat)
    		{
    			if (c.hasTag(Tags.MONSTER) && !c.hasTag(Tags.EXEMPT))
    			{
    				allowedResummons.add((DuelistCard) c.makeCopy());
    				System.out.println("theDuelist:CallGrave:atEndOfTurn() ---> added " + c.originalName + " to allowedResummons");
    			}
    		}
	    	for (int i = 0; i < this.amount; i++)
	    	{
	    		DuelistCard randomMon = allowedResummons.get(AbstractDungeon.cardRandomRng.random(allowedResummons.size() - 1));
	    		while (actualResummons.contains(randomMon) && loopCount < loopMax)
	    		{
	    			randomMon = allowedResummons.get(AbstractDungeon.cardRandomRng.random(allowedResummons.size() - 1));
	    			loopCount++;
	    		}
	    		actualResummons.add(randomMon);
	    		System.out.println("theDuelist:CallGrave:atEndOfTurn() ---> added " + randomMon.originalName + " to actualResummons");
	    	}
	    	
	    	for (DuelistCard c : actualResummons)
	    	{
	    		DuelistCard.fullResummon(c, false, AbstractDungeon.getRandomMonster(), false);
	    		System.out.println("theDuelist:CallGrave:atEndOfTurn() ---> called resummon on: " + c.originalName);
	    	}
    	}
    	else
    	{
    		System.out.println("theDuelist:CallGrave:atEndOfTurn() ---> monstersInCombat size was NOT > 0!!!");
    	}
    }
    
    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) 
    {
    	
    }
    
    @Override
	public void atEndOfTurn(final boolean isPlayer) 
	{
    	
	}

    @Override
	public void updateDescription() 
    {
    	if (this.amount < 2) { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]; }
    	else { this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]; }
        
    }
}
