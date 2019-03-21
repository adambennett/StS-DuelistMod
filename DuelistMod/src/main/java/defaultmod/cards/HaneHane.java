package defaultmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.*;

public class HaneHane extends DuelistCard 
{
    // TEXT DECLARATION

    public static final String ID = defaultmod.DefaultMod.makeID("HaneHane");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.HANE_HANE);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    private static final int BLOCK = 3;
    private static final int SUMMONS = 1;
    private static boolean resummoned = false;
    // /STAT DECLARATION/

    public HaneHane() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = BLOCK;
        this.magicNumber = this.baseMagicNumber = SUMMONS;
        this.tags.add(DefaultMod.MONSTER);
        this.tags.add(DefaultMod.LEGEND_BLUE_EYES);
        this.tags.add(DefaultMod.GOOD_TRIB);
        this.tags.add(DefaultMod.TOON_DECK);
        this.tags.add(DefaultMod.EXODIA_DECK);
        this.startingExodiaDeckCopies = 1;
        this.startingDeckCopies = 1;
        this.originalName = this.name;
        this.setupStartingCopies();
    }
    
    public HaneHane(boolean resummon) {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = BLOCK;
        this.magicNumber = this.baseMagicNumber = SUMMONS;
        this.tags.add(DefaultMod.MONSTER);
        this.tags.add(DefaultMod.LEGEND_BLUE_EYES);
        this.tags.add(DefaultMod.GOOD_TRIB);
        this.tags.add(DefaultMod.TOON_DECK);
        this.startingDeckCopies = 1;
        this.originalName = this.name;
        this.setupStartingCopies();
        resummoned = resummon;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon(p, this.magicNumber, this);
    	block(this.block);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new HaneHane();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeMagicNumber(1);
            this.upgradeBaseCost(0);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		if (!resummoned) 
		{ 
			summon(player(), 1, new Token("Hane Token"));
	    	block(this.block);
		}
	}

	@Override
	public void onResummon(int summons) 
	{
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) 
	{
	
		
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