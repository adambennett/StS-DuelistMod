package duelistmod.cards.pools.insects;

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

public class KarakuriSpider extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("KarakuriSpider");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("KarakuriSpider.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public KarakuriSpider() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 1;
        this.secondMagic = this.baseSecondMagic = 2;
        this.summons = this.baseSummons = 3;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.SPIDER);
        this.tags.add(Tags.MACHINE);
        this.originalName = this.name;
        this.isSummon = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	for (int i = 0; i < this.secondMagic; i++) { incMaxSummons(this.magicNumber); }
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new KarakuriSpider();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeSecondMagic(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		
	}


	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
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
