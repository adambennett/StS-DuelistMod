package defaultmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import defaultmod.DefaultMod;
import defaultmod.patches.AbstractCardEnum;
import defaultmod.powers.AlphaMagPower;
import defaultmod.powers.BetaMagPower;
import defaultmod.powers.GammaMagPower;
import defaultmod.powers.PotGenerosityPower;
import defaultmod.powers.SummonPower;

public class ValkMagnet extends CustomCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * In order to understand how image paths work, go to defaultmod/DefaultMod.java, Line ~140 (Image path section).
     *
     * Strike Deal 7(9) damage.
     */

    // TEXT DECLARATION

    public static final String ID = defaultmod.DefaultMod.makeID("ValkMagnet");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // Yes, you totally can use "defaultModResources/images/cards/Attack.png" instead and that would work.
    // It might be easier to use that while testing.
    // Using makePath is good practice once you get the hand of things, as it prevents you from
    // having to change *every single card/file/path* if the image path changes due to updates or your personal preference.

    public static final String IMG = DefaultMod.makePath(DefaultMod.VALK_MAGNET);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/

    
    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 2;
    private static final int DAMAGE = 20;
    private static final int SUMMONS = 3;

    // /STAT DECLARATION/

    public ValkMagnet() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	// Summon
    	if (!p.hasPower(SummonPower.POWER_ID)) 
    	{
    		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new SummonPower(p, SUMMONS), SUMMONS));
    	}
    	else
    	{
    		int temp = (p.getPower(SummonPower.POWER_ID).amount);
    		if (!(temp > 5 - SUMMONS)) 
    		{
    			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new SummonPower(p, SUMMONS), SUMMONS));
    		}
    		else
    		{
    			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new SummonPower(p, 5 - temp), 5 - temp));
    		}
    	}
    	
    	// Check for Pot of Generosity
    	if (p.hasPower(PotGenerosityPower.POWER_ID)) 
    	{
    		AbstractDungeon.actionManager.addToTop(new GainEnergyAction(SUMMONS));
    	}
    	
    	// Deal damage to target enemy
    	AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
   
    	// Remove magnets
    	if (!this.upgraded)
    	{
	    	AbstractDungeon.actionManager.addToTop(new ReducePowerAction(p, p, AlphaMagPower.POWER_ID, 1));
	    	AbstractDungeon.actionManager.addToTop(new ReducePowerAction(p, p, BetaMagPower.POWER_ID, 1));
	    	AbstractDungeon.actionManager.addToTop(new ReducePowerAction(p, p, GammaMagPower.POWER_ID, 1));
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new ValkMagnet();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName(); 
            this.upgradeBaseCost(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    // If player doesn't have all three magnets, can't play card
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
    	if (p.energy.energy >= COST)
    	{
	    	if (p.hasPower(AlphaMagPower.POWER_ID)) 
	    	{
	    		if (p.hasPower(BetaMagPower.POWER_ID)) 
		    	{
	    			if (p.hasPower(GammaMagPower.POWER_ID)) 
	    	    	{
	    	    		return true;
	    	    	}
		    	}
	    	}
    	}
    	
    	return false;
    }
}