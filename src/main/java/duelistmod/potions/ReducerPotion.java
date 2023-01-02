package duelistmod.potions;

import basemod.IUIElement;
import basemod.ModLabel;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.AbstractPotion.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.powers.ReducerPower;
import duelistmod.variables.Colors;

import java.util.ArrayList;

public class ReducerPotion extends DuelistPotion {


    public static final String POTION_ID = DuelistMod.makeID("ReducerPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public ReducerPotion() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
    	super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.BOLT, PotionEffect.NONE, Colors.TEAL, Colors.TEAL, Colors.BLACK);
        
        
        // Potency is the damage/magic number equivalent of potions.
        this.potency = this.getPotency();
        
        // Initialize the Description
        this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1] + 1 + DESCRIPTIONS[2];
        
       // Do you throw this potion at an enemy or do you just consume it.
        this.isThrown = false;
        
        // Initialize the on-hover name + description
        //this.tips.add(new PowerTip(this.name, this.description));
        
    }

    
    
    @Override
    public boolean canUse()
    {
    	int decAmt = 1;
    	if (!AbstractDungeon.getCurrRoom().phase.equals(RoomPhase.COMBAT)) { return false; }
        return DuelistCard.canDecMaxSummons(decAmt);
    }

    @Override
    public void use(AbstractCreature target) 
    {
    	target = AbstractDungeon.player;
    	DuelistCard.applyPowerToSelf(new ReducerPower(AbstractDungeon.player, this.potency + 1));
    	DuelistCard.decMaxSummons(AbstractDungeon.player, 1);
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new ReducerPotion();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
    	int pot = 3;
    	return pot;
    }
    
    @Override
    public void initializeData() {
        this.potency = this.getPotency();
        this.description =  DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1] + 1 + DESCRIPTIONS[2];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }
    
    public void upgradePotion()
    {
      this.potency += 2;
      this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1] + 1 + DESCRIPTIONS[2];
      this.tips.clear();
      this.tips.add(new PowerTip(this.name, this.description));
    }
}
