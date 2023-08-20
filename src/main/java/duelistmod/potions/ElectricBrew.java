package duelistmod.potions;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPotion;
import duelistmod.variables.Colors;
import duelistmod.variables.Tags;

public class ElectricBrew extends DuelistPotion {


    public static final String POTION_ID = DuelistMod.makeID("ElectricBrew");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public ElectricBrew() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
    	super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.FAIRY, PotionEffect.RAINBOW, Colors.WHITE, null, null);
        
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
    public float modifyMagicNumber(float tmp, AbstractCard c)
    {
    	if (!c.hasTag(Tags.ALLOYED))
    	{
    		return c.hasTag(Tags.BAD_MAGIC) ? tmp - this.potency : tmp + this.potency;
    	}
    	return tmp;
    }
    
    @Override
    public void onEndOfBattle()
    {
    	AbstractDungeon.player.decreaseMaxHealth(3);
    	this.flash();
    }

    @Override
    public void use(AbstractCreature target)
    {
    	AbstractDungeon.player.increaseMaxHp(2, true);
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new ElectricBrew();
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
        this.description =  DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }
    
    public void upgradePotion()
    {
      this.potency += 2;
      this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];   
      this.tips.clear();
      this.tips.add(new PowerTip(this.name, this.description));
    }
}
