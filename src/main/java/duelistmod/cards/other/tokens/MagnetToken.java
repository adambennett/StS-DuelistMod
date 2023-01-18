package duelistmod.cards.other.tokens;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class MagnetToken extends TokenCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("MagnetToken");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.SUPERHEAVY_MAGNET);
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

    public MagnetToken() 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.TOKEN);
    	this.purgeOnUse = true;
    	this.baseSummons = this.summons = 1;
    }
    public MagnetToken(String tokenName) 
    { 
    	super(ID, tokenName, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.TOKEN); 
    	this.purgeOnUse = true;
    	this.baseSummons = this.summons = 1;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	if (upgraded)
    	{    		
    		int randomMagnetNum = AbstractDungeon.cardRandomRng.random(0, 2);
        	switch (randomMagnetNum)
        	{
        		case 0: applyPowerToSelf(new AlphaMagPower(p, p)); break;
        		case 1: applyPowerToSelf(new BetaMagPower(p, p)); break;
        		case 2: applyPowerToSelf(new GammaMagPower(p, p)); break;
        		default: applyPowerToSelf(new BetaMagPower(p, p)); break;
        	}        	
    	}
    	else
    	{
    		if (roulette()) { 
        		int randomMagnetNum = AbstractDungeon.cardRandomRng.random(0, 2);
            	switch (randomMagnetNum)
            	{
            		case 0: applyPowerToSelf(new AlphaMagPower(p, p)); break;
            		case 1: applyPowerToSelf(new BetaMagPower(p, p)); break;
            		case 2: applyPowerToSelf(new GammaMagPower(p, p)); break;
            		default: applyPowerToSelf(new BetaMagPower(p, p)); break;
            	}
        	}
    	}
    }
    @Override public AbstractCard makeCopy() { return new MagnetToken(); }

    
    
	

	@Override public void upgrade() 
	{
		if (canUpgrade()) {
			if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
	}
	


}
