package duelistmod.potions;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.powers.duelistPowers.BurningDebuff;
import duelistmod.variables.Colors;

public class BurningPotion extends DuelistPotion {


    public static final String POTION_ID = DuelistMod.makeID("BurningPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public BurningPotion() {
    	super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.SPHERE, PotionEffect.OSCILLATE, Colors.DARK_RED_BROWN, Colors.BLACK, Colors.BLACK);
        
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
    public void use(AbstractCreature target) 
    {
    	DuelistCard.applyPower(new BurningDebuff(target, AbstractDungeon.player, this.potency), target);
    	//DuelistCard.burnAllEnemies(this.potency);
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new BurningPotion();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
    	int pot = 10;
    	return pot;
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
      this.potency += 2;
      this.tips.clear();
      this.tips.add(new PowerTip(this.name, this.description));
      this.tips.add(new PowerTip("Burning", DESCRIPTIONS[2]));
    }
}
