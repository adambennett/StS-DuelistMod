package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;

public class DarkMimicLv1 extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("DarkMimicLv1");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("DarkMimicLv1.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 0;
    // /STAT DECLARATION/

    public DarkMimicLv1() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.FIEND);
        this.misc = 0;
        this.originalName = this.name;
        this.isSummon = true;
        this.summons = this.baseSummons = 1;
        this.magicNumber = this.baseMagicNumber = 1;
        
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon(p, this.summons, this);
    	if (!upgraded) { draw(1); }
    	else { draw(2); }
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new DarkMimicLv1();
    }
    
    

    // Upgraded stats.
    @Override
    public void upgrade() 
    {        
    	if (!upgraded)
    	{
	    	this.upgradeName();
	        this.rawDescription = UPGRADE_DESCRIPTION;
	        this.cardsToPreview.upgrade();
	        this.initializeDescription();       
    	}
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		fiendSynTrib(tributingCard);
	}



	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}
	


	@Override
	public void summonThis(int summons, DuelistCard c, int var) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		// TODO Auto-generated method stub
		
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
