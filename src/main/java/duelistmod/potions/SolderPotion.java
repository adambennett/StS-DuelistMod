package duelistmod.potions;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPotion;
import duelistmod.actions.common.SolderAction;
import duelistmod.helpers.Util;
import duelistmod.variables.Colors;

public class SolderPotion extends DuelistPotion {


    public static final String POTION_ID = DuelistMod.makeID("SolderPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public SolderPotion() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
    	super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.BOLT, PotionEffect.OSCILLATE, Colors.YELLOW, Colors.DARK_PURPLE, Colors.YELLOW);
        
        // Potency is the damage/magic number equivalent of potions.
        this.potency = this.getPotency();
        
        // Initialize the Description
        this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        
       // Do you throw this potion at an enemy or do you just consume it.
        this.isThrown = false;
        
        // Initialize the on-hover name + description
        //this.tips.add(new PowerTip(this.name, this.description));
        
    }

    
    
    @Override
    public boolean canSpawn() {
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
        return Util.deckIs("Machine Deck");
    }

    @Override
    public void use(AbstractCreature target) 
    {
    	this.addToBot(new SolderAction(this.potency));
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new SolderPotion();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 4;
    }
    
    @Override
    public void initializeData() {
        this.potency = this.getPotency();
        this.description =  DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));      
        this.tips.add(new PowerTip("Solder", DESCRIPTIONS[2]));
    }
    
    public void upgradePotion()
    {
      this.potency += 1;
      this.tips.clear();
      this.tips.add(new PowerTip(this.name, this.description));      
      this.tips.add(new PowerTip("Solder", DESCRIPTIONS[2]));
    }
}
