package duelistmod.cards.other.tokens;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.*;
import duelistmod.helpers.RandomActionHelper;
import duelistmod.interfaces.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class GlitchToken extends TokenCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("GlitchToken");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GreenGadget.png");
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

    public GlitchToken() 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.TOKEN);
    	this.tags.add(Tags.MACHINE);
    	this.purgeOnUse = true;
    	this.baseMagicNumber = this.magicNumber = 1;
    	this.summons = this.baseSummons = 1;
    }
    public GlitchToken(String tokenName) 
    { 
    	super(ID, tokenName, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.TOKEN);
    	this.tags.add(Tags.MACHINE);
    	this.purgeOnUse = true;
    	this.baseMagicNumber = this.magicNumber = 1;
    	this.summons = this.baseSummons = 1;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	if (this.magicNumber > 0) { RandomActionHelper.triggerRandomAction(this.magicNumber, false, true, true); }
    }
    @Override public AbstractCard makeCopy() { return new GlitchToken(); }

    
    
	
	
	

	@Override public void upgrade() 
	{
		if (canUpgrade()) {
			if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
			this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
	}
	


}
