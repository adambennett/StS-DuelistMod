package duelistmod.potions;

import basemod.IUIElement;
import basemod.ModLabel;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.AbstractPotion.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.helpers.Util;
import duelistmod.orbs.Gadget;
import duelistmod.variables.Colors;

import java.util.ArrayList;

public class GadgetBottle extends OrbPotion {


    public static final String POTION_ID = DuelistMod.makeID("GadgetBottle");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public GadgetBottle() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
    	super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.BOTTLE, PotionEffect.OSCILLATE, Colors.ORANGE, Colors.DARK_PURPLE, Colors.BLUE);

        
        // Potency is the damage/magic number equivalent of potions.
        this.potency = this.getPotency();
        
        // Initialize the Description
        this.description = DESCRIPTIONS[0];
        
       // Do you throw this potion at an enemy or do you just consume it.
        this.isThrown = false;
        
        // Initialize the on-hover name + description
        //this.tips.add(new PowerTip(this.name, this.description));
        //this.tips.add(new PowerTip("Gadget", DESCRIPTIONS[3]));
        
    }

    
    
    @Override
    public boolean canSpawn()
    {
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
    	if (Util.deckIs("Machine Deck")) { return true; }
    	return false;
    }

    @Override
    public void use(AbstractCreature target) 
    {
    	target = AbstractDungeon.player;
    	for (int i = 0; i < this.potency; i++)
    	{
	       AbstractOrb air = new Gadget();
	       DuelistCard.channel(air);
    	}
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new GadgetBottle();
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
        if (this.potency > 1) { this.description = DESCRIPTIONS[1] + this.potency + DESCRIPTIONS[2]; }
        else { this.description = DESCRIPTIONS[0]; }
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip("Gadget", DESCRIPTIONS[3]));
        this.tips.add(new PowerTip("Invert", DESCRIPTIONS[4]));
    }
    
    public void upgradePotion()
    {
      this.potency += 1;
      if (this.potency > 1)
      {
    	  this.description = DESCRIPTIONS[1] + this.potency + DESCRIPTIONS[2];
      }
      this.tips.clear();
      this.tips.add(new PowerTip(this.name, this.description));
      this.tips.add(new PowerTip("Gadget", DESCRIPTIONS[3]));
    }
}
