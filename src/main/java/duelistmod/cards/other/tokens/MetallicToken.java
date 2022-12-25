package duelistmod.cards.other.tokens;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class MetallicToken extends TokenCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("MetallicToken");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("MetallicToken.png");
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

    public MetallicToken() 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.TOKEN);
    	this.purgeOnUse = true;
    	this.summons = this.baseSummons = 1;
    	this.baseMagicNumber = this.magicNumber = 1;    	
    }
    public MetallicToken(String tokenName) 
    { 
    	super(ID, tokenName, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.TOKEN);
    	this.purgeOnUse = true;
    	this.summons = this.baseSummons = 1;
    	this.baseMagicNumber = this.magicNumber = 1;    	
    	
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	if (roulette() && this.magicNumber > 0) { applyPowerToSelf(new MetallicizePower(p, this.magicNumber)); }
    }
    @Override public AbstractCard makeCopy() { return new MetallicToken(); }

    
    
	@Override public void onTribute(DuelistCard tributingCard) 
	{
		
	}
	
	@Override public void onResummon(int summons) 
	{ 
		
	}
	
	@Override public void summonThis(int summons, DuelistCard c, int var) {  }
	@Override public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) { }
	
	@Override public void upgrade() 
	{
		if (canUpgrade()) {
			if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
			this.upgradeMagicNumber(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
	}
	
	@Override
	public String getID() {
		return ID;
	}
	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}
