package duelistmod.cards.incomplete;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;

public class RoseArcher extends DuelistCard 
{
    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("RoseArcher");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("RoseArcher.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 3;
    private boolean shouldUpgradeCost = false;
    private boolean doneUpgrading = false;
    // /STAT DECLARATION/

    public RoseArcher() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.ROSE);
		this.tags.add(Tags.ARCANE);
        this.summons = this.baseSummons = 2;
        this.magicNumber = this.baseMagicNumber = 6;	// Constricted
        this.secondMagic = this.baseSecondMagic = 3;	// Dexterity
        this.thirdMagic = this.baseThirdMagic = 2;		// # of Random Targets
        this.originalName = this.name;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	applyPowerToSelf(new DexterityPower(p, this.secondMagic));
    	constrictMultipleRandom(this.magicNumber, this.thirdMagic);
    	
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new RoseArcher();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (canUpgrade()) 
        {
            this.upgradeName();        
            if (shouldUpgradeCost) { this.upgradeBaseCost(2); this.doneUpgrading = true; }
            else
            {
            	  this.upgradeMagicNumber(2);		// Constricted
                  this.upgradeSecondMagic(1);		// Dexterity
                  this.upgradeThirdMagic(1);		// # of Random Targets
                  this.shouldUpgradeCost = true;
            }
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
    
    @Override
    public boolean canUpgrade()
    {
    	if (!doneUpgrading) { return true; }
    	else { return false; }
    }






	






}
