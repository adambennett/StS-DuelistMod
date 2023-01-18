package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.BlackPendantPower;
import duelistmod.variables.Tags;

public class BlackPendant extends DuelistCard 
{
	/* 	
	 * Gain X strength this turn. 
	 * the end of the turn, Tribute X and 
	 * place this card on top of your draw pile. 
	 */
    // TEXT DECLARATION 
    public static final String ID = DuelistMod.makeID("BlackPendant");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("BlackPendant.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION 	
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;
    // /STAT DECLARATION/


    public BlackPendant() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 4;	// Overflows
        this.secondMagic = this.baseSecondMagic = 4;	// Strength
        this.thirdMagic = this.baseThirdMagic = 3;		// HP Loss
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.BAD_MAGIC);
        this.tags.add(Tags.IS_OVERFLOW);
        this.originalName = this.name;
    }
    
    @Override
    public void triggerOverflowEffect()
    {
    	super.triggerOverflowEffect();
    	damageSelf(this.thirdMagic);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	applyPowerToSelf(new StrengthPower(p, this.secondMagic));
    	applyPowerToSelf(new LoseStrengthPower(p, this.secondMagic));
    	if (!p.hasPower(BlackPendantPower.POWER_ID)) { applyPowerToSelf(new BlackPendantPower(p, p, this)); }
    	else
    	{
    		BlackPendantPower pow = (BlackPendantPower) p.getPower(BlackPendantPower.POWER_ID);
    		if (!pow.allPendants.contains(this)) { pow.allPendants.add(this); pow.amount = pow.allPendants.size(); pow.updateDescription(); }
    	}
    }


    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new BlackPendant();
    }
    
    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();            
            this.upgradeMagicNumber(-2);
            this.upgradeSecondMagic(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }


	















}
