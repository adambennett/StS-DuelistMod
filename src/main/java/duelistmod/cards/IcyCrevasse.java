package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.*;
import com.megacrit.cardcrawl.powers.FocusPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class IcyCrevasse extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("IcyCrevasse");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.ICY_CREVASSE);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    //private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public IcyCrevasse() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.TRAP);
        this.tags.add(Tags.ALL);
        this.tags.add(Tags.ORIGINAL_ORB_DECK);
    	this.startingOPODeckCopies = 1;
        this.misc = 0;
		this.originalName = this.name;
		this.baseMagicNumber = this.magicNumber = 2;		// Focus
		this.baseSecondMagic = this.secondMagic = 2;		// Max summon reduction
		this.baseThirdMagic = this.thirdMagic = 1;			// Frost Channeled
		this.showEvokeValue = true;
		this.showEvokeOrbCount = 2;
		this.setupStartingCopies();
    }
    
    @Override
    public void update()
    {
		super.update();
    	this.showEvokeOrbCount = this.thirdMagic;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	if (canDecMaxSummons(this.secondMagic))
    	{
    		applyPowerToSelf(new FocusPower(p, this.magicNumber));
	    	decMaxSummons(p, this.secondMagic);
            channel(new Frost(), this.thirdMagic);
    	}
    }

    public String failedCardSpecificCanUse(final AbstractPlayer p, final AbstractMonster m) { return "Cannot reduce Max Summons further"; }

    public boolean cardSpecificCanUse(final AbstractPlayer p, final AbstractMonster m) {
        return canDecMaxSummons(this.secondMagic);
    }


    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new IcyCrevasse();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeSecondMagic(-1);
            this.rawDescription = UPGRADE_DESCRIPTION;
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
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		 
		
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) 
	{
		
	}
}
