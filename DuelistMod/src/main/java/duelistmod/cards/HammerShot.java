package duelistmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.*;

public class HammerShot extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("HammerShot");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.HAMMER_SHOT);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public HammerShot() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.baseMagicNumber = this.magicNumber = 2;
    	this.damage = this.baseDamage = 0;
    	this.isMultiDamage = true;
    	this.isMultiDamage = true;
    	this.tags.add(Tags.SPELL);
    	this.tags.add(Tags.ALL);
    	this.tags.add(Tags.EXODIA_DECK);
    	this.exodiaDeckCopies = 2;
    	this.originalName = this.name;
    	this.setupStartingCopies();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	this.damage = this.baseDamage;
        if (this.damage > 0) 
        {
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SMASH));
        }
    }
    
    @Override
    public void applyPowers() 
    {
        super.applyPowers();
	    this.baseDamage = this.magicNumber * getMaxSummons(AbstractDungeon.player);
        this.damage = this.baseDamage;
        this.initializeDescription();
    }
    
    @Override
    public void calculateCardDamage(AbstractMonster mo) 
    {
        super.calculateCardDamage(mo);
        this.baseDamage = this.magicNumber * getMaxSummons(AbstractDungeon.player);
        this.damage = this.baseDamage;
        this.initializeDescription();
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
    	return new HammerShot();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

	@Override
	public void onTribute(DuelistCard tributingCard) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		// TODO Auto-generated method stub
		
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