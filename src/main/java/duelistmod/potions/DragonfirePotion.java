package duelistmod.potions;

import basemod.IUIElement;
import basemod.ModLabel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.dto.DuelistConfigurationData;
import duelistmod.helpers.Util;
import duelistmod.powers.duelistPowers.Dragonscales;
import duelistmod.variables.Colors;

import java.util.ArrayList;

public class DragonfirePotion extends DuelistPotion {


    public static final String POTION_ID = DuelistMod.makeID("DragonfirePotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public DragonfirePotion() {
    	super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.SPHERE, PotionEffect.OSCILLATE, Colors.RED, Colors.DARK_PURPLE, Colors.DARK_RED_BROWN);
        
        // Potency is the damage/magic number equivalent of potions.
        this.potency = this.getPotency();
        
        // Initialize the Description
        this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        
       // Do you throw this potion at an enemy or do you just consume it.
        this.isThrown = true;
        this.targetRequired = true;
        
        // Initialize the on-hover name + description
        //this.tips.add(new PowerTip(this.name, this.description));
        
    }

    @Override
    public DuelistConfigurationData getConfigurations() {
        ArrayList<IUIElement> settingElements = new ArrayList<>();
        RESET_Y();
        LINEBREAK();
        LINEBREAK();
        LINEBREAK();
        LINEBREAK();
        settingElements.add(new ModLabel("Configurations for " + this.name + " not setup yet.", (DuelistMod.xLabPos), (DuelistMod.yPos),DuelistMod.settingsPanel,(me)->{}));
        return new DuelistConfigurationData(this.name, settingElements);
    }
    
    @Override
    public boolean canSpawn()
    {
    	if (Util.deckIs("Dragon Deck")) { return true; }
    	return false;
    }

    @Override
    public void use(AbstractCreature target) 
    {
    	int damage = this.potency;
    	int maxS = DuelistCard.getMaxSummons(AbstractDungeon.player);
    	if (AbstractDungeon.player.hasPower(Dragonscales.POWER_ID)) { Dragonscales pow = (Dragonscales)AbstractDungeon.player.getPower(Dragonscales.POWER_ID); damage += pow.getInc(); }
    	for (int i = 0; i < maxS; i++)
    	{
	    	final DamageInfo info = new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.THORNS);
	        info.applyEnemyPowersOnly(target);
	        this.addToBot(new DamageAction(target, info, AbstractGameAction.AttackEffect.FIRE));
    	}
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new DragonfirePotion();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
    	int pot = 4;
    	return pot;
    }
    
    @Override
    public void initializeData() {
        this.potency = this.getPotency();
        this.description = DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }
    
    public void upgradePotion()
    {
      this.potency += 2;
      this.tips.clear();
      this.tips.add(new PowerTip(this.name, this.description));
    }
}
