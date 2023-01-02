package duelistmod.potions;

import java.util.ArrayList;

import basemod.IUIElement;
import basemod.ModLabel;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import duelistmod.*;
import duelistmod.abstracts.*;
import duelistmod.actions.common.CardSelectScreenResummonAction;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.variables.Colors;

public class BigOrbBottle extends OrbPotion {


    public static final String POTION_ID = DuelistMod.makeID("BigOrbBottle");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public BigOrbBottle() {
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
        
    }

    

    @Override
    public void use(AbstractCreature target) 
    {
    	target = AbstractDungeon.player;
    	ArrayList<AbstractCard> tempOrbCards = new ArrayList<>();
    	ArrayList<String> added = new ArrayList<String>();
    	int iterations = this.potency * 3;
    	ArrayList<AbstractCard> orbsToChooseFrom = DuelistCardLibrary.orbCardsForGeneration();
    	ArrayList<AbstractCard> fullOrbs = new ArrayList<>();
    	for (AbstractCard c : orbsToChooseFrom) { if (c instanceof DuelistCard) { fullOrbs.add((DuelistCard) c); }}
    	if (iterations > orbsToChooseFrom.size()) { AbstractDungeon.actionManager.addToTop(new CardSelectScreenResummonAction(fullOrbs, this.potency, false, false, false, false)); }
    	else
    	{
	    	for (int i = 0; i < this.potency * 3; i++)
	    	{
	    		AbstractCard orb = orbsToChooseFrom.get(AbstractDungeon.cardRandomRng.random(orbsToChooseFrom.size() - 1));
	    		while (added.contains(orb.name)) { orb = orbsToChooseFrom.get(AbstractDungeon.cardRandomRng.random(orbsToChooseFrom.size() - 1)); }
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
    	int pot = 1;
    	return pot;
    }
    
    @Override
    public void initializeData() {
        this.potency = this.getPotency();
        this.description = DESCRIPTIONS[0];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
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
