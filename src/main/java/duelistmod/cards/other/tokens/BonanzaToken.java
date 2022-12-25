package duelistmod.cards.other.tokens;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.*;

public class BonanzaToken extends TokenCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("BonanzaToken");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.RED_MEDICINE);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/a

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = 0;
    // /STAT DECLARATION/

    public BonanzaToken() 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.TOKEN);
    	this.purgeOnUse = true;
    	this.baseMagicNumber = this.magicNumber = 2;
    	this.summons = this.baseSummons = 1;
    }
    public BonanzaToken(String tokenName) 
    { 
    	super(ID, tokenName, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.TOKEN);  
    	this.purgeOnUse = true;
    	this.baseMagicNumber = this.magicNumber = 2;
    	this.summons = this.baseSummons = 1;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	if (this.magicNumber > 0) { DuelistCard.applyRandomBuffPlayer(p, this.magicNumber, false); }
    }
    @Override public AbstractCard makeCopy() { return new BonanzaToken(); }

    
    
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
