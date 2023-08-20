package duelistmod.potions;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistPotion;
import duelistmod.variables.Colors;

public class DeckJuice extends DuelistPotion {


    public static final String POTION_ID = DuelistMod.makeID("DeckJuice");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public DeckJuice() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
    	super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.CARD, PotionEffect.RAINBOW, Colors.WHITE, null, null);
        
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
    public void use(AbstractCreature target) {
    	DuelistMod.drawExtraCardsAtTurnStartThisBattle += this.potency;
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new DeckJuice();
    }

    @Override
    public int getPotency(final int potency) {
        return 1;
    }
    
    @Override
    public void initializeData() {
        this.potency = this.getPotency();
        int index = this.potency == 1 ? 1 : 2;
        this.description =  DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[index];
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
