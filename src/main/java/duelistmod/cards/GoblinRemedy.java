package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.*;
import duelistmod.powers.*;
import duelistmod.powers.duelistPowers.GoblinRemedyPower;
import duelistmod.variables.*;

public class GoblinRemedy extends DuelistCard 
{
    // TEXT DECLARATION 
    public static final String ID = DuelistMod.makeID("GoblinSecret");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.GOBLIN_SECRET);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION 	
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public GoblinRemedy() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.HEAL_DECK);
        this.tags.add(Tags.LEGEND_BLUE_EYES);
        this.healDeckCopies = 1;
        this.magicNumber = this.baseMagicNumber = 1;
		this.originalName = this.name;
		this.tributes = this.baseTributes = 2;
		this.setupStartingCopies();
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute(p, this.tributes, false, this);
    	if (p.hasPower(GoblinRemedyPower.POWER_ID))
    	{
    		p.getPower(GoblinRemedyPower.POWER_ID).amount += this.magicNumber;
    		p.getPower(GoblinRemedyPower.POWER_ID).updateDescription();
    	}
    	else
    	{
    		applyPowerToSelf(new GoblinRemedyPower(p, p, this.magicNumber));
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new GoblinRemedy();
    }

    //Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!this.upgraded) 
        {
            if (DuelistMod.hasUpgradeBuffRelic)
            {
            	this.upgradeName();
                this.upgradeMagicNumber(1);
                this.upgradeTributes(-1);
                this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
                this.initializeDescription();
            }
            else
            {
            	this.upgradeName();
                this.upgradeMagicNumber(1);
                this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
                this.initializeDescription();
            }
        }
    }
    



















}
