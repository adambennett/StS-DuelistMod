package duelistmod.potions;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.variables.Colors;

public class VampireVial extends DuelistPotion {


    public static final String POTION_ID = DuelistMod.makeID("VampireVial");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public VampireVial() {
    	super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.SPHERE, PotionEffect.OSCILLATE, Colors.GRAY, Colors.BLACK, Colors.BLACK);
        
        // Potency is the damage/magic number equivalent of potions.
        this.potency = this.getPotency();
        
        // Initialize the Description
        this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        
       // Do you throw this potion at an enemy or do you just consume it.
        this.isThrown = true;
        this.targetRequired = true;
        
        // Initialize the on-hover name + description
        //this.tips.add(new PowerTip(this.name, this.description));
        
    }
    
    @Override
    public boolean canSpawn()
    {
    	return true;
    }

    @Override
    public void use(AbstractCreature target) 
    {
    	if (target instanceof AbstractMonster)
    	{
    		DuelistCard.siphon((AbstractMonster)target, this.potency);
    	}   
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new VampireVial();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
    	int pot = 8;
    	return pot;
    }
    
    @Override
    public void initializeData() {
        this.potency = this.getPotency();
        this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }
    
    public void upgradePotion()
    {
      this.potency += 2;
      this.tips.clear();
      this.tips.add(new PowerTip(this.name, this.description));
    }
}
