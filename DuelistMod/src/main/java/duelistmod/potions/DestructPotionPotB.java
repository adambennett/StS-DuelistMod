package duelistmod.potions;

import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;

public class DestructPotionPotB extends AbstractPotion {


    public static final String POTION_ID = DuelistMod.makeID("DestructPotionPotB");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public DestructPotionPotB() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.GHOST, PotionColor.SMOKE);
        
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
    	int tributeCount = DuelistCard.powerTribute(AbstractDungeon.player, 0, true);
    	if (tributeCount > 0) 
    	{
    		int rarityRoll = AbstractDungeon.potionRng.random(1, 60);
    		rarityRoll += tributeCount * 4;
    		if (rarityRoll > 100) { rarityRoll = 100; }
    			
    		if (rarityRoll < 55)
    		{
    			int secondRoll = AbstractDungeon.potionRng.random(1, 6);
    			if (secondRoll < 4)
    			{
    				AbstractRelic rand = AbstractDungeon.returnRandomScreenlessRelic(RelicTier.COMMON);
    				while (AbstractDungeon.player.hasRelic(rand.relicId)) { rand = AbstractDungeon.returnRandomScreenlessRelic(RelicTier.COMMON); }
    				AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), rand);
    			}
    			else if (secondRoll < 6) 
    			{
    				AbstractRelic rand = AbstractDungeon.returnRandomScreenlessRelic(RelicTier.UNCOMMON);
    				while (AbstractDungeon.player.hasRelic(rand.relicId)) { rand = AbstractDungeon.returnRandomScreenlessRelic(RelicTier.UNCOMMON); }
    				AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), rand);
    			}
    			else
    			{
    				AbstractRelic rand = AbstractDungeon.returnRandomScreenlessRelic(RelicTier.RARE);
    				while (AbstractDungeon.player.hasRelic(rand.relicId)) { rand = AbstractDungeon.returnRandomScreenlessRelic(RelicTier.RARE); }
    				AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), rand);
    			}
    			
    		}
    		else if (rarityRoll > 55 && rarityRoll < 75)
    		{
    			int secondRoll = AbstractDungeon.potionRng.random(1, 4);
    			if (secondRoll == 1)
    			{
    				AbstractRelic rand = AbstractDungeon.returnRandomScreenlessRelic(RelicTier.COMMON);
    				while (AbstractDungeon.player.hasRelic(rand.relicId)) { rand = AbstractDungeon.returnRandomScreenlessRelic(RelicTier.COMMON); }
    				AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), rand);
    			}
    			else if (secondRoll < 4) 
    			{
    				AbstractRelic rand = AbstractDungeon.returnRandomScreenlessRelic(RelicTier.UNCOMMON);
    				while (AbstractDungeon.player.hasRelic(rand.relicId)) { rand = AbstractDungeon.returnRandomScreenlessRelic(RelicTier.UNCOMMON); }
    				AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), rand);
    			}
    			else
    			{
    				AbstractRelic rand = AbstractDungeon.returnRandomScreenlessRelic(RelicTier.RARE);
    				while (AbstractDungeon.player.hasRelic(rand.relicId)) { rand = AbstractDungeon.returnRandomScreenlessRelic(RelicTier.RARE); }
    				AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), rand);
    			}
    			
    		}
    		else if (rarityRoll > 75 && rarityRoll < 97)
    		{
    			int secondRoll = AbstractDungeon.potionRng.random(1, 3);
    			if (secondRoll < 3)
    			{
    				AbstractRelic rand = AbstractDungeon.returnRandomScreenlessRelic(RelicTier.UNCOMMON);
    				while (AbstractDungeon.player.hasRelic(rand.relicId)) { rand = AbstractDungeon.returnRandomScreenlessRelic(RelicTier.UNCOMMON); }
    				AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), rand);
    			}
    			else 
    			{
    				AbstractRelic rand = AbstractDungeon.returnRandomScreenlessRelic(RelicTier.RARE);
    				while (AbstractDungeon.player.hasRelic(rand.relicId)) { rand = AbstractDungeon.returnRandomScreenlessRelic(RelicTier.RARE); }
    				AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), rand);
    			}
    		}
    		else
    		{
    			int secondRoll = AbstractDungeon.potionRng.random(1, 5);
    			if (secondRoll == 5)
    			{
    				AbstractRelic rand = AbstractDungeon.returnRandomScreenlessRelic(RelicTier.BOSS);
        			while (AbstractDungeon.player.hasRelic(rand.relicId)) { rand = AbstractDungeon.returnRandomScreenlessRelic(RelicTier.BOSS); }
        			AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), rand);
    			}
    			else
    			{
    				AbstractRelic rand = AbstractDungeon.returnRandomScreenlessRelic(RelicTier.RARE);
        			while (AbstractDungeon.player.hasRelic(rand.relicId)) { rand = AbstractDungeon.returnRandomScreenlessRelic(RelicTier.RARE); }
        			AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), rand);
    			}
    			
    		}
    	}
    	else
    	{
    		AbstractRelic rand = new Circlet();
			AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2), rand);
    	}
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new DestructPotionPotB();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
    	int pot = 1;
    	if (AbstractDungeon.player == null) { return pot; }
        return AbstractDungeon.player.hasRelic("SacredBark") ? pot*2 : pot;
    }
    
    @Override
    public void initializeData() {
        this.potency = this.getPotency();
        this.description =  DESCRIPTIONS[0];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }
    
    public void upgradePotion()
    {
      this.tips.clear();
      this.tips.add(new PowerTip(this.name, this.description));
    }
}
