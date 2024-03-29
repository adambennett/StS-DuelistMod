package duelistmod.cards.other.tempCards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class UniZombieINC extends UniZombCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("UniZombieINC");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("UniZombie.png");
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

    public UniZombieINC(int blk) 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.baseMagicNumber = this.magicNumber = blk;
    	this.tags.add(Tags.ALLOYED);    	
    }
    
    @Override
    public void activate(AbstractMonster target)
    {
    	incMaxSummons(this.magicNumber);
    }
    
    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	
    }
    @Override public AbstractCard makeCopy() { return new UniZombieINC(this.baseMagicNumber); }

    
    
	
	@Override public void upgrade() 
	{
		if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
	}
	


}
