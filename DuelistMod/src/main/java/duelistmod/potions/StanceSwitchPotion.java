package duelistmod.potions;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.SacredBark;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;

public class StanceSwitchPotion extends OrbPotion {


    public static final String POTION_ID = DuelistMod.makeID("StanceSwitchPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
    
    private int hpLimit = 100;
 
    public StanceSwitchPotion() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.FAIRY, PotionColor.SMOKE);
        
        // Potency is the damage/magic number equivalent of potions.
        this.potency = this.getPotency();
        
        // Initialize the Description
        this.description = returnDesc();
        
       // Do you throw this potion at an enemy or do you just consume it.
        this.isThrown = false;
        
        // Initialize the on-hover name + description
        this.tips.add(new PowerTip(this.name, this.description));
        
    }

    @Override
    public void use(AbstractCreature target) 
    {
    	target = AbstractDungeon.player;
    }
  
    @Override
    public void onChangeStance()
    {
    	if (AbstractDungeon.player.hasRelic(SacredBark.ID))
	    {	    	
    		if (this.hpLimit >= this.potency * 2)
    		{
		    	DuelistCard.gainTempHP(this.potency * 2);
		    	DuelistCard.damageSelfNotHP(this.potency);
		    	this.hpLimit -= this.potency * 2;
		    	if (this.hpLimit < 0) { this.hpLimit = 0; }
		    	updateDesc();
    		}
    		else if (this.hpLimit > 0)
    		{
    			DuelistCard.gainTempHP(this.hpLimit);
		    	DuelistCard.damageSelfNotHP(this.potency);
		    	this.hpLimit = 0;
		    	updateDesc();
    		}
	    }
	    else
	    {
	    	if (this.hpLimit >= this.potency)
	    	{
		    	DuelistCard.gainTempHP(this.potency);
		    	DuelistCard.damageSelfNotHP(this.potency / 2);
		    	this.hpLimit -= this.potency;
		    	if (this.hpLimit < 0) { this.hpLimit = 0; }
		    	updateDesc();
	    	}
	    	else if (this.hpLimit > 0)
	    	{
	    		DuelistCard.gainTempHP(this.hpLimit);
		    	DuelistCard.damageSelfNotHP(this.potency / 2);
		    	this.hpLimit = 0;
		    	updateDesc();
	    	}
	    }
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new StanceSwitchPotion();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
    	return 2;
    }
    
    private String returnDesc()
    {
    	return DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1] + this.potency/2 + DESCRIPTIONS[2] + this.hpLimit + DESCRIPTIONS[3];
    }
    
    public void updateDesc()
    {
    	this.tips.clear();
    	this.description = returnDesc();
        this.tips.add(new PowerTip(this.name, this.description));
    }
    
    public void upgradePotion()
    {
      this.potency = 4;
      updateDesc();
    }
}
