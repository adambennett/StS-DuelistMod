package duelistmod.potions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.variables.Tags;

public class BigTributeBottle extends AbstractPotion {


    public static final String POTION_ID = DuelistMod.makeID("BigTributeBottle");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public BigTributeBottle() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.BOLT, PotionColor.SMOKE);
        
        // Potency is the damage/magic number equivalent of potions.
        this.potency = this.getPotency();
        
        // Initialize the Description
        this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        
       // Do you throw this potion at an enemy or do you just consume it.
        this.isThrown = false;
        
        // Initialize the on-hover name + description
        this.tips.add(new PowerTip(this.name, this.description));
        
    }

    @Override
    public void use(AbstractCreature target) 
    {
    	target = AbstractDungeon.player;
    	AbstractPlayer p = AbstractDungeon.player;
    	for (AbstractCard c : p.hand.group)
    	{
    		if (c.hasTag(Tags.MONSTER))
    		{
    			DuelistCard dC = (DuelistCard)c;
    			if (dC.tributes > 0)
    			{
    				dC.changeTributesInBattle(-this.potency, true);
    			}
    		}
    	}
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new BigTributeBottle();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
    	int pot = 2;
    	if (AbstractDungeon.player == null) { return pot; }
        return AbstractDungeon.player.hasRelic("SacredBark") ? pot*2 : pot;
    }
    
    public void upgradePotion()
    {
      this.potency += 1;
      this.tips.clear();
      this.tips.add(new PowerTip(this.name, this.description));
    }
}
