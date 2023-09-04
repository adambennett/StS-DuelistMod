package duelistmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class FallenAngelRoses extends DuelistCard 
{
    // TEXT DECLARATION 

    public static final String ID = DuelistMod.makeID("FallenAngelRoses");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("FallenAngelRoses.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION 	
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;
    // /STAT DECLARATION/
    
    public FallenAngelRoses() 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.tags.add(Tags.MONSTER);
    	this.tags.add(Tags.PLANT);
    	 this.tags.add(Tags.ROSE); 
		this.originalName = this.name;
		this.magicNumber = this.baseMagicNumber = 3;	// # of Random Enemies
		this.secondMagic = this.baseSecondMagic = 3;	// Temp HP gained per enemy
		this.tributes = this.baseTributes = 3;
		this.damage = this.baseDamage = 18;
    }
    
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute();
        attackMultipleRandom(this.magicNumber, AttackEffect.SLASH_DIAGONAL, (attacked) -> gainTempHP(attacked * this.secondMagic));
    }


    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new FallenAngelRoses();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
            this.upgradeSecondMagic(3);
            this.upgradeDamage(7);
            this.upgradeBaseCost(1);
            this.upgradeTributes(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
    














}
