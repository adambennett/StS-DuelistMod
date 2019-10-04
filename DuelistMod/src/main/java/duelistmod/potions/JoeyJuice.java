package duelistmod.potions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;

public class JoeyJuice extends AbstractPotion {


    public static final String POTION_ID = DuelistMod.makeID("JoeyJuice");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public JoeyJuice() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.SPHERE, PotionColor.FIRE);
        
        // Potency is the damage/magic number equivalent of potions.
        this.potency = this.getPotency();
        
        // Initialize the Description
        this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        
       // Do you throw this potion at an enemy or do you just consume it.
        this.isThrown = true;
        this.targetRequired = true;
        
        // Initialize the on-hover name + description
        this.tips.add(new PowerTip(this.name, this.description));
        
    }

    @Override
    public void use(AbstractCreature target) 
    {
    	int maxS = DuelistCard.getMaxSummons(AbstractDungeon.player);
    	for (int i = 0; i < maxS; i++)
    	{
	    	final DamageInfo info = new DamageInfo(AbstractDungeon.player, this.potency, DamageInfo.DamageType.THORNS);
	        info.applyEnemyPowersOnly(target);
	        this.addToBot(new DamageAction(target, info, AbstractGameAction.AttackEffect.FIRE));
    	}
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new JoeyJuice();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
    	int pot = 4;
    	if (AbstractDungeon.player == null) { return pot; }
        return AbstractDungeon.player.hasRelic("SacredBark") ? pot*2 : pot;
    }
    
    public void upgradePotion()
    {
      this.potency += 2;
      this.tips.clear();
      this.tips.add(new PowerTip(this.name, this.description));
    }
}
