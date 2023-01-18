package duelistmod.cards.other.tokens;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.characters.TheDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class VendreadToken extends TokenCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("VendreadToken");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("AvendreadSavior.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = 0;
    // /STAT DECLARATION/

    public VendreadToken() 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.tags.add(Tags.TOKEN); 
    	this.tags.add(Tags.ZOMBIE); 
    	this.tags.add(Tags.VENDREAD); 
    	this.purgeOnUse = true;
    	this.baseSummons = this.summons = 1;
    	this.baseMagicNumber = this.magicNumber = 1;
    	this.baseSecondMagic = this.secondMagic = 4;
    }
    
    public VendreadToken(String tokenName) 
    { 
    	super(ID, tokenName, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.TOKEN); 
    	this.tags.add(Tags.ZOMBIE); 
    	this.tags.add(Tags.VENDREAD); 
    	this.purgeOnUse = true;
    	this.baseSummons = this.summons = 1;
    	this.baseMagicNumber = this.magicNumber = 1;
    	this.baseSecondMagic = this.secondMagic = 4;
    }
    
    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon(); 
    	if (roulette()) { 
    		float amt = TheDuelist.resummonPile.size() / this.secondMagic;
    		int strGain = (int) (this.magicNumber * amt);
    		applyPowerToSelf(new StrengthPower(p, strGain));
    	}
    }
    @Override public AbstractCard makeCopy() { return new VendreadToken(); }
	

	@Override public void upgrade() 
	{
		if (canUpgrade()) {
			if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
			this.upgradeSecondMagic(-2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
	}
	



}
