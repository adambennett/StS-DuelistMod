package duelistmod.potions;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.helpers.Util;
import duelistmod.variables.Colors;

public class Bonebrew extends DuelistPotion {


    public static final String POTION_ID = DuelistMod.makeID("Bonebrew");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public Bonebrew() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
    	super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.FAIRY, PotionEffect.RAINBOW, Colors.WHITE, null, null);
        
        // Potency is the damage/magic number equivalent of potions.
        this.potency = this.getPotency();
        
        // Initialize the Description
        this.description = DESCRIPTIONS[0];
        
       // Do you throw this potion at an enemy or do you just consume it.
        this.isThrown = false;
        
        // Initialize the on-hover name + description
        //this.tips.add(new PowerTip(this.name, this.description));
        
    }
    
    @Override
    public boolean canSpawn()
    {
    	if (Util.deckIs("Zombie Deck")) { return true; }
    	return false;
    }
    
    @Override
    public void onResummon(DuelistCard res)
    {
    	if (DuelistMod.currentZombieSouls > 0) { DuelistCard.staticBlock(DuelistMod.currentZombieSouls); }
    }
 
    @Override
    public void use(AbstractCreature target) 
    {
    	
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new Bonebrew();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
    	int pot = 1;
    	return pot;
    }
    
    @Override
    public void initializeData() {
        this.potency = this.getPotency();
        this.description =  DESCRIPTIONS[0];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }
    
    public void upgradePotion()
    {
      this.potency += 2;
      this.description = DESCRIPTIONS[0];   
      this.tips.clear();
      this.tips.add(new PowerTip(this.name, this.description));
    }
}
