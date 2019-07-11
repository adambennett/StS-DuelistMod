package duelistmod.potions;

import java.util.ArrayList;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.CardSelectScreenResummonAction;

public class BigOrbBottle extends AbstractPotion {


    public static final String POTION_ID = DuelistMod.makeID("BigOrbBottle");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public BigOrbBottle() {
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
        
    }

    @Override
    public void use(AbstractCreature target) 
    {
    	target = AbstractDungeon.player;
    	ArrayList<DuelistCard> tempOrbCards = new ArrayList<DuelistCard>();
    	ArrayList<String> added = new ArrayList<String>();
    	int iterations = this.potency * 3;
    	if (iterations > DuelistMod.orbCards.size()) { AbstractDungeon.actionManager.addToTop(new CardSelectScreenResummonAction(DuelistMod.orbCards, this.potency, false, false, false, false)); }
    	else
    	{
	    	for (int i = 0; i < this.potency * 3; i++)
	    	{
	    		DuelistCard orb = DuelistMod.orbCards.get(AbstractDungeon.cardRandomRng.random(DuelistMod.orbCards.size() - 1));
	    		while (added.contains(orb.name)) { orb = DuelistMod.orbCards.get(AbstractDungeon.cardRandomRng.random(DuelistMod.orbCards.size() - 1)); }
	    		added.add(orb.name); tempOrbCards.add((DuelistCard) orb.makeStatEquivalentCopy());
	    	}
	    	
	    	if (tempOrbCards.size() > 0)
	    	{
	    		AbstractDungeon.actionManager.addToTop(new CardSelectScreenResummonAction(tempOrbCards, this.potency, false, false, false, false));
	    	}	    	
    	}
    	
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new BigOrbBottle();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 1;
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
    }
}
