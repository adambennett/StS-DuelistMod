package defaultmod.potions;

import java.util.concurrent.ThreadLocalRandom;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;

import defaultmod.patches.DuelistCard;

public class JoeyJuice extends AbstractPotion {


    public static final String POTION_ID = defaultmod.DefaultMod.makeID("JoeyJuice");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public JoeyJuice() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.M, PotionColor.SMOKE);
        
        // Potency is the damage/magic number equivalent of potions.
        this.potency = this.getPotency();
        
        // Initialize the Description
        this.description = DESCRIPTIONS[0];
        
       // Do you throw this potion at an enemy or do you just consume it.
        this.isThrown = true;
        
        // Initialize the on-hover name + description
        this.tips.add(new PowerTip(this.name, this.description));
        
    }

    @Override
    public void use(AbstractCreature target) 
    {
    	if (!target.isPlayer)
    	{
	    	AbstractMonster monster = (AbstractMonster) target;
	        int randomTurnNum = ThreadLocalRandom.current().nextInt(2, 4 + 1);
			int randomTurnNumB = ThreadLocalRandom.current().nextInt(2, 4 + 1);
			//int randomTurnNumC = ThreadLocalRandom.current().nextInt(2, 4 + 1);
	
			// Get random debuffs
			AbstractPower randomDebuff = DuelistCard.getRandomDebuff(AbstractDungeon.player, monster, randomTurnNum + this.potency);
			AbstractPower randomDebuffB = DuelistCard.getRandomDebuff(AbstractDungeon.player, monster, randomTurnNumB + this.potency);
			//AbstractPower randomDebuffC = DuelistCard.getRandomDebuff(AbstractDungeon.player, monster, randomTurnNumC + this.potentcy);
	
			// Apply random debuff(s)
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, randomDebuff, randomTurnNum + this.potency));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, randomDebuffB, randomTurnNumB + this.potency));
			//AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, AbstractDungeon.player, randomDebuffC, randomTurnNumC + this.potency));
    	}
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new JoeyJuice();
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
