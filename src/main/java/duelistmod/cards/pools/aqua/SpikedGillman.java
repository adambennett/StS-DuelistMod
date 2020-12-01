package duelistmod.cards.pools.aqua;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;

public class SpikedGillman extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("SpikedGillman");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("SpikedGillman.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public SpikedGillman() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.summons = this.baseSummons = 1;
        this.baseDamage = this.damage = 4;
        this.baseMagicNumber = this.magicNumber = 2;
        this.isMultiDamage = true;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.AQUA);
    }
    
    @Override
    public void onResummonWhileSummoned(DuelistCard resummoned) 
    {
    	applyPowerToSelf(new ThornsPower(player(), this.magicNumber));
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	normalMultidmg();
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new SpikedGillman();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!upgraded) 
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	this.upgradeDamage(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		aquaSynTrib(tributingCard);
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
