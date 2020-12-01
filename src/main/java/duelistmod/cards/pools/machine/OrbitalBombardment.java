package duelistmod.cards.pools.machine;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tokens.ExplosiveToken;
import duelistmod.helpers.Util;
import duelistmod.orbs.Alien;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;

public class OrbitalBombardment extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("OrbitalBombardment");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("OrbitalBombardment.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public OrbitalBombardment() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.damage = this.baseDamage = 12;
        this.baseSummons = this.summons = 2;
		this.showEvokeValue = true;
		this.showEvokeOrbCount = 1;
        this.tags.add(Tags.TRAP);
        this.cardsToPreview = new ExplosiveToken();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {   
    	DuelistCard tok = DuelistCardLibrary.getTokenInCombat(new ExplosiveToken());
    	summon(p, this.summons, tok);
    	channel(new Alien());
    	attackAll(this.damage);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new OrbitalBombardment();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!upgraded) 
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	this.upgradeBaseCost(1);
        	this.upgradeDamage(2);
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
