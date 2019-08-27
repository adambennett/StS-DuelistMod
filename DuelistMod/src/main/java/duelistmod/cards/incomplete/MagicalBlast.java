package duelistmod.cards.incomplete;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.unique.MagicalBlastAction;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.incomplete.ManaPower;
import duelistmod.variables.Tags;

public class MagicalBlast extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("MagicalBlast");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("MagicalBlast.png");
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

    public MagicalBlast() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseMagicNumber = this.magicNumber = 2;
        this.isMultiDamage = true;
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.NO_MANA_RESET);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	this.damage = this.baseDamage;
        if (this.damage > 0) 
        {
            AbstractDungeon.actionManager.addToTop(new MagicalBlastAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SMASH));
        }
    }
    
    @Override
    public void applyPowers() 
    {
        super.applyPowers();
        if (AbstractDungeon.player.hasPower(ManaPower.POWER_ID))
        {
        	this.baseDamage = this.magicNumber * AbstractDungeon.player.getPower(ManaPower.POWER_ID).amount;
        }
        else
        {
        	this.baseDamage = 0;
        }
	    
        this.damage = this.baseDamage;
        this.initializeDescription();
    }
    
    @Override
    public void calculateCardDamage(AbstractMonster mo) 
    {
        super.calculateCardDamage(mo);
        if (AbstractDungeon.player.hasPower(ManaPower.POWER_ID))
        {
        	this.baseDamage = this.magicNumber * AbstractDungeon.player.getPower(ManaPower.POWER_ID).amount;
        }
        else
        {
        	this.baseDamage = 0;
        }
        this.damage = this.baseDamage;
        this.initializeDescription();
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new MagicalBlast();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!upgraded) 
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }


	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		// TODO Auto-generated method stub
		
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