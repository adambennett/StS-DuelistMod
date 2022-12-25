package duelistmod.cards.pools.warrior;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Lightning;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class BrilliantSpark extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("BrilliantSpark");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("BrilliantSpark.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = -2;
    // /STAT DECLARATION/

    public BrilliantSpark() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.tags.add(Tags.TRAP);
        this.tags.add(Tags.BAD_MAGIC);
        this.baseMagicNumber = this.magicNumber = 3;
        this.secondMagic = this.baseSecondMagic = 1;
        this.selfRetain = true;
    }
    
    @Override
    public void onRetained() {
        if (AbstractDungeon.player.drawPile.size() >= this.magicNumber) {
            channel(new Lightning(), this.secondMagic);
            for (int i = 0; i < this.magicNumber; i++)
            {
                AbstractCard rand = AbstractDungeon.player.drawPile.getRandomCard(true);
                AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(rand, AbstractDungeon.player.drawPile));
            }
        }
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
		
    }
    
    @Override public boolean canUse(AbstractPlayer p, AbstractMonster m) { return false; }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new BrilliantSpark();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!upgraded)
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	this.upgradeSecondMagic(1);
        	this.upgradeMagicNumber(-1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
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
