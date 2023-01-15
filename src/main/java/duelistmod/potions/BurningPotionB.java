package duelistmod.potions;

import basemod.IUIElement;
import basemod.ModLabel;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.variables.Colors;

import java.util.ArrayList;

public class BurningPotionB extends DuelistPotion {


    public static final String POTION_ID = DuelistMod.makeID("BurningPotionB");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public BurningPotionB() {
    	super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.SPHERE, PotionEffect.OSCILLATE, Colors.ORANGE, Colors.BLACK, Colors.BLACK);
        
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
    public void use(AbstractCreature target) 
    {
    	//DuelistCard.applyPower(new BurningDebuff(target, AbstractDungeon.player, this.potency), target);
    	DuelistCard.burnAllEnemies(this.potency);
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new BurningPotionB();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 7;
    }
    
    @Override
    public void initializeData() {
        this.potency = this.getPotency();
        this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip("Burning", DESCRIPTIONS[2]));
    }
    
    public void upgradePotion()
    {
      this.potency += 3;
      this.tips.clear();
      this.tips.add(new PowerTip(this.name, this.description));
      this.tips.add(new PowerTip("Burning", DESCRIPTIONS[2]));
    }
}
