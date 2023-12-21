package duelistmod.cards.pools.aqua;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import com.megacrit.cardcrawl.vfx.cardManip.*;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class Monokeros extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("Monokeros");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("Monokeros.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public Monokeros() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseDamage = this.damage = 11;
        this.isMultiDamage = true;
        this.baseBlock = this.block = 8;
        this.summons = this.baseSummons = 2;
        this.magicNumber = this.baseMagicNumber = 2;
        this.isSummon = true;   
        this.misc = 0;
        this.tributes = this.baseTributes = 2;
        this.specialCanUseLogic = true;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.AQUA);
        this.tags.add(Tags.TIDAL);
        this.tags.add(Tags.IS_OVERFLOW);
    }
    
    @Override
    public void statBuffOnTidal()
    {
    	this.upgradeDamage(1);
    	this.upgradeBlock(2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	block(this.block);
    }
    
    @Override
    public void triggerOverflowEffect()
    {
    	super.triggerOverflowEffect();
    	if (getSummons(player()) >= this.tributes)
        {
    		tribute();
            damageAllEnemiesThornsNormal(this.damage); 
        }
    }

    @Override
    public boolean canUse(final AbstractPlayer p, final AbstractMonster m) {
        int save = this.baseTributes;
        int saveInner = this.tributes;
        this.baseTributes = this.tributes = 0;
        boolean result = super.canUse(p, m);
        this.baseTributes = save;
        this.tributes = saveInner;
        return result;
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!upgraded) 
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	this.upgradeMagicNumber(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }



	



	
	@Override
    public AbstractCard makeCopy() { return new Monokeros(); }
	
}
