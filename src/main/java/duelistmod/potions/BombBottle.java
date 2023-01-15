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
import duelistmod.abstracts.*;
import duelistmod.helpers.Util;
import duelistmod.variables.Colors;

public class BombBottle extends DuelistPotion {


    public static final String POTION_ID = DuelistMod.makeID("BombBottle");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
    private int dynamicPot;
    private static final int base = 2;

    public BombBottle() {
    	super(NAME, POTION_ID, PotionRarity.COMMON, PotionSize.SPHERE, PotionEffect.OSCILLATE, Colors.GRAY, Colors.DARK_PURPLE, Colors.BLACK);
        this.dynamicPot = this.getPotency();
        this.description = DESCRIPTIONS[0] + 2 + DESCRIPTIONS[1];
        this.isThrown = true;
        this.targetRequired = true;
    }

    @Override
    public boolean canSpawn() {
        return super.canSpawn() && Util.deckIs("Machine Deck");
    }

    @Override
    public void onDetonate() {
    	incPot();
    }

    @Override
    public void onEndOfBattle() {
        resetPot();
    }

    @Override
    public void use(AbstractCreature target) 
    {
    	int damage = this.dynamicPot;
    	int maxS = DuelistCard.getMaxSummons(AbstractDungeon.player);
    	for (int i = 0; i < maxS; i++) {
	    	final DamageInfo info = new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.THORNS);
	        info.applyEnemyPowersOnly(target);
	        this.addToBot(new DamageAction(target, info, AbstractGameAction.AttackEffect.FIRE));
    	}
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new BombBottle();
    }
    
    private void incPot()
    {
    	if (this.dynamicPot < 20) {
	    	this.dynamicPot++;
	    	initializeData();
	    	flash();
    	}
    }

    private void resetPot() {
        this.dynamicPot = 2;
        initializeData();
        flash();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
    	int pot = dynamicPot;
    	if (pot < base) {
            pot = base;
        }
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
