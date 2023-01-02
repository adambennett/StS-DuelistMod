package duelistmod.potions;

import basemod.IUIElement;
import basemod.ModLabel;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.helpers.Util;
import duelistmod.powers.duelistPowers.GreasedDebuff;
import duelistmod.variables.Colors;

import java.util.ArrayList;

public class GreasePot extends DuelistPotion {


    public static final String POTION_ID = DuelistMod.makeID("GreasePot");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public GreasePot() {
    	super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.SPHERE, PotionEffect.OSCILLATE, Colors.GRAY, Colors.BLACK, Colors.BLACK);
        
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
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
    	if (Util.deckIs("Machine Deck")) { return true; }
    	return false;
    }

    @Override
    public void use(AbstractCreature target) 
    {
    	DuelistCard.applyPower(new GreasedDebuff(target, AbstractDungeon.player, this.potency), target);
    	//DuelistCard.greaseAllEnemies(this.potency);
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new GreasePot();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
    	int pot = 6;
    	return pot;
    }
    
    @Override
    public void initializeData() {
        this.potency = this.getPotency();
        this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip("Grease", DESCRIPTIONS[2]));
    }
    
    public void upgradePotion()
    {
      this.potency += 2;
      this.tips.clear();
      this.tips.add(new PowerTip(this.name, this.description));
      this.tips.add(new PowerTip("Grease", DESCRIPTIONS[2]));
    }
}
