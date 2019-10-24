package duelistmod.potions;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.DebuffHelper;

public class DebuffArmorPotion extends AbstractPotion {


    public static final String POTION_ID = DuelistMod.makeID("DebuffArmorPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
    
    public DebuffArmorPotion() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.BOLT, PotionColor.ENERGY);
        
        // Potency is the damage/magic number equivalent of potions.
        this.potency = this.getPotency();
        
        // Initialize the Description
        this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1] + 2 + DESCRIPTIONS[2];
        
       // Do you throw this potion at an enemy or do you just consume it.
        this.isThrown = false;
        
        // Initialize the on-hover name + description
        //this.tips.add(new PowerTip(this.name, this.description));
        
    }

    @Override
    public void use(AbstractCreature target) 
    {
    	target = AbstractDungeon.player;
    	DuelistCard.staticBlock(this.potency);
    	DuelistCard.applyPowerToSelf(DebuffHelper.getRandomPlayerDebuff(AbstractDungeon.player, 2));
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new DebuffArmorPotion();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
    	int pot = 18;
    	return pot;
    }
    
    @Override
    public void initializeData() {
        this.potency = this.getPotency();
        this.description =  DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1] + 2 + DESCRIPTIONS[2];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }
    
    public void upgradePotion()
    {
      this.potency += 6;
      this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1] + 2 + DESCRIPTIONS[2];
      this.tips.clear();
      this.tips.add(new PowerTip(this.name, this.description));
    }
}
