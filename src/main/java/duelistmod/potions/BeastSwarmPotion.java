package duelistmod.potions;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.abstracts.DuelistPotion;
import duelistmod.helpers.Util;
import duelistmod.variables.Colors;
import duelistmod.variables.Tags;

public class BeastSwarmPotion extends DuelistPotion {


    public static final String POTION_ID = DuelistMod.makeID("BeastSwarmPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public BeastSwarmPotion() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
    	super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.SPIKY, PotionEffect.NONE, Colors.GRAY, Colors.RED);
        
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
    public boolean canSpawn() {
		boolean superCheck = super.canSpawn();
		if (!superCheck) return false;
        return Util.deckIs("Beast Deck");
    }

    @Override
    public boolean canUse() {
        return AbstractDungeon.getCurrRoom().phase.equals(AbstractRoom.RoomPhase.COMBAT);
    }


    @Override
    public void use(AbstractCreature target) {
    	for (int i = 0; i < this.potency; i++) {
			AbstractMonster m = AbstractDungeon.getRandomMonster();
			if (m != null) {
                DuelistCard.resummon(DuelistCard.returnTrulyRandomFromSet(Tags.BEAST), m);
            }
		}
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new BeastSwarmPotion();
    }

    @Override
    public int getPotency(final int potency) {
        return 2;
    }
    
    @Override
    public void initializeData() {
        this.potency = this.getPotency();
        this.description =  DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }
    
    public void upgradePotion() {
      this.potency += 1;
      this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];   
      this.tips.clear();
      this.tips.add(new PowerTip(this.name, this.description));
    }
}
