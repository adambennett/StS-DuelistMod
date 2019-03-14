package defaultmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.*;
import defaultmod.powers.TimeWizardPower;

public class TimeWizard extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DefaultMod.makeID("TimeWizard");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.TIME_WIZARD);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public TimeWizard() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(DefaultMod.MONSTER);
        this.tags.add(DefaultMod.METAL_RAIDERS);
        this.tags.add(DefaultMod.FULL);
        this.tags.add(DefaultMod.GENERATION_DECK);
		this.startingGenDeckCopies = 1;
		this.originalName = this.name;
		this.summons = 1;
		this.isSummon = true;
		this.setupStartingCopies();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
       summon(p, this.summons, this);
       if (!p.hasPower(TimeWizardPower.POWER_ID)) { applyPowerToSelf(new TimeWizardPower(p, p, 1)); }
       else 
       {
    	   TimeWizardPower timePower = (TimeWizardPower) p.getPower(TimeWizardPower.POWER_ID);
    	   timePower.amount++;
       }
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new TimeWizard();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.tags.add(DefaultMod.GOOD_TRIB);
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
		summon(player(), this.summons, this);
		applyPowerToSelf(new TimeWizardPower(player(), player(), 1));
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) 
	{
		summon(player(), this.summons, this);
		applyPowerToSelf(new TimeWizardPower(player(), player(), 1));
	}

	@Override
	public String getID() {
		return ID;
	}
}