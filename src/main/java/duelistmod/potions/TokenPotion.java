package duelistmod.potions;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import duelistmod.DuelistCardLibrary;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistPotion;
import duelistmod.cards.other.tokens.Token;
import duelistmod.variables.Colors;

public class TokenPotion extends DuelistPotion {


    public static final String POTION_ID = DuelistMod.makeID("TokenPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public TokenPotion() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
    	super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.BOLT, PotionEffect.NONE, Colors.WHITE, Colors.RED, Colors.BLACK);

        // Potency is the damage/magic number equivalent of potions.
        this.potency = this.getPotency();
        
        // Initialize the Description
        this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        
       // Do you throw this potion at an enemy or do you just consume it.
        this.isThrown = false;
        
        // Initialize the on-hover name + description
       // this.tips.add(new PowerTip(this.name, this.description));
        
    }

    

    @Override
    public void use(AbstractCreature target) {
    	for (int i = 0; i < this.potency; i++) {
			AbstractMonster m = AbstractDungeon.getRandomMonster();
			if (m != null) {
				DuelistCard tok = DuelistCardLibrary.getTokenInCombat(new Token());
                DuelistCard.resummon(tok, m);
			}
		}
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new TokenPotion();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
    	int pot = 2;
    	return pot;
    }
    
    @Override
    public void initializeData() {
        this.potency = this.getPotency();
        this.description =  DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip("Special Summon", DuelistMod.specialSummonKeywordDescription));
    }
    
    public void upgradePotion()
    {
      this.potency += 1;
      this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];   
      this.tips.clear();
      this.tips.add(new PowerTip(this.name, this.description));
      this.tips.add(new PowerTip("Special Summon", DuelistMod.specialSummonKeywordDescription));
    }
}
