package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.interfaces.DuelistCard;
import duelistmod.patches.AbstractCardEnum;

public class MagicalStone extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("MagicalStone");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.MAGICAL_STONE);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public MagicalStone() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.magicNumber = this.baseMagicNumber = 2;
        this.originalName = this.name;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
       drawTag(this.magicNumber, Tags.SPELL);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new MagicalStone();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (canUpgrade()) 
        {
        	if (this.timesUpgraded > 1) 
        	{ 
        		this.upgradeName(NAME + "+" + this.timesUpgraded); 
        		this.upgradeMagicNumber(1); 
        	}
        	else if (this.timesUpgraded == 1)
        	{
        		this.upgradeName(NAME + "+" + this.timesUpgraded); 
        		this.upgradeBaseCost(0);
        	}
	    	else
	    	{ 
	    		this.upgradeName(NAME + "+"); 
	    		this.exhaust = false;
	    	}
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    @Override
    public boolean canUpgrade()
    {
    	return true;
    }

	@Override
	public void onTribute(DuelistCard tributingCard) {
		// TODO Auto-generated method stub
		
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