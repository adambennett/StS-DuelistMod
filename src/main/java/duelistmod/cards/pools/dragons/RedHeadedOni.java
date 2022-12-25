package duelistmod.cards.pools.dragons;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.RandomizedHandAction;
import duelistmod.helpers.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class RedHeadedOni extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("RedHeadedOni");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("RedHeadedOni.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public RedHeadedOni() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.summons = this.baseSummons = 1;
        this.baseMagicNumber = this.magicNumber = 1;
        this.secondMagic = this.baseSecondMagic = 1;
        this.isSummon = true;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.ZOMBIE);
        this.tags.add(Tags.DRAGON);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	for (int i = 0; i < this.magicNumber; i++) 
    	{ 
    		AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(BaseGameHelper.getRedCard(), this.secondMagic)); 
    	}
    }


    // Upgraded stats.
    @Override
    public void upgrade() 
    {
    	if (!upgraded) 
    	{
    		if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
    		else { this.upgradeName(NAME + "+"); }
    		this.upgradeSecondMagic(-1);
    		this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
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
	public String getID() { return ID; }
	
	@Override
    public AbstractCard makeCopy() { return new RedHeadedOni(); }
	public void summonThis(int summons, DuelistCard c, int var) {}
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {}
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {}
}
