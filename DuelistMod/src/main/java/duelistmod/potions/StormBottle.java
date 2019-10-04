package duelistmod.potions;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.AbstractPotion.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.orbs.Storm;

public class StormBottle extends OrbPotion {


    public static final String POTION_ID = DuelistMod.makeID("StormBottle");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public StormBottle() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.BOTTLE, PotionColor.SMOKE);
        
        // Potency is the damage/magic number equivalent of potions.
        this.potency = this.getPotency();
        
        // Initialize the Description
        this.description = DESCRIPTIONS[0];
        
       // Do you throw this potion at an enemy or do you just consume it.
        this.isThrown = false;
        
        // Initialize the on-hover name + description
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip("Storm", DESCRIPTIONS[3]));
    }

    @Override
    public void use(AbstractCreature target) 
    {
    	target = AbstractDungeon.player;
    	for (int i = 0; i < this.potency; i++)
    	{
	       AbstractOrb air = new Storm();
	       DuelistCard.channel(air);
    	}
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new StormBottle();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
    	int pot = 1;
    	if (AbstractDungeon.player == null) { return pot; }
        return AbstractDungeon.player.hasRelic("SacredBark") ? pot*2 : pot;
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
      this.tips.add(new PowerTip("Storm", DESCRIPTIONS[3]));
    }
}
