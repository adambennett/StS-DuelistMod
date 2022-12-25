package duelistmod.cards.pools.insects;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.RandomizedHandAction;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class SpiderWeb extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("SpiderWeb");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("SpiderWeb.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public SpiderWeb() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseBlock = this.block = 6;
        this.baseMagicNumber = this.magicNumber = 3;
        this.misc = 0;
        this.tags.add(Tags.SPELL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	block();
    	for (int i = 0; i < this.magicNumber; i++)
    	{
    		this.addToBot(new RandomizedHandAction((DuelistCard)returnTrulyRandomFromSet(Tags.SPIDER), true));
    	}
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!upgraded) 
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	this.upgradeBlock(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
			
	}

	@Override
	public void onResummon(int summons) 
	{
		
		
	}

	@Override
	public String getID() { return ID; }
	
	@Override
    public AbstractCard makeCopy() { return new SpiderWeb(); }
	public void summonThis(int summons, DuelistCard c, int var) {}
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {}
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {}
}
