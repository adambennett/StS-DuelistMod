package defaultmod.potions;

import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import defaultmod.patches.DuelistCard;

public class MillenniumElixir extends AbstractPotion {


    public static final String POTION_ID = defaultmod.DefaultMod.makeID("MillenniumElixir");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public MillenniumElixir() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.M, PotionColor.SMOKE);
        
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
        int randomTurnNum = ThreadLocalRandom.current().nextInt(1, 6 + 1);
		int randomTurnNumB = ThreadLocalRandom.current().nextInt(1, 6 + 1);
		DuelistCard.applyRandomBuffPlayer(AbstractDungeon.player, randomTurnNum, false);
		DuelistCard.applyRandomBuffPlayer(AbstractDungeon.player, randomTurnNumB, false);
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new MillenniumElixir();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 2;
    }
    
    public void upgradePotion()
    {
      this.potency += 1;
      this.tips.clear();
      this.tips.add(new PowerTip(this.name, this.description));
    }
}
