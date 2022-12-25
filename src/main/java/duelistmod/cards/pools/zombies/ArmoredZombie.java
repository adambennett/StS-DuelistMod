package duelistmod.cards.pools.zombies;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.*;
import duelistmod.powers.SummonPower;
import duelistmod.variables.*;

public class ArmoredZombie extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("ArmoredZombie");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.ARMORED_ZOMBIE);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public ArmoredZombie() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.summons = this.baseSummons = 1;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.METAL_RAIDERS);       
        this.tags.add(Tags.ZOMBIE);       
        this.tags.add(Tags.ZOMBIE_DECK);
		this.tags.add(Tags.ASCENDED_TWO_DECK);
        this.a2DeckCopies = 1;
        this.zombieDeckCopies = 3;        
        this.originalName = this.name;
        this.block = this.baseBlock = 6;
        this.setupStartingCopies();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	block();
    }
 
    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new ArmoredZombie();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!this.upgraded) 
        {
            this.upgradeName();
            this.upgradeSummons(1);
            this.upgradeBlock(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
    
    @Override
    public void onResummonThisCard()
    {
    	
    }

	@Override
	public void onResummon(int summons)
	{
		
	}
	


	@Override
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		AbstractPlayer p = AbstractDungeon.player;
		summon(p, summons, this);
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) 
	{
		AbstractPlayer p = AbstractDungeon.player;
		summon(p, summons, this);
		
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTribute(DuelistCard tributingCard) {
		// TODO Auto-generated method stub
		
	}
}
