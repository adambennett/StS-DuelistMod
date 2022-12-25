package duelistmod.cards.pools.warrior;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class SuperheavyMagnet extends DuelistCard 
{
    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("SuperheavyMagnet");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.SUPERHEAVY_MAGNET);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public SuperheavyMagnet() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = 15;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.SUPERHEAVY);
        this.tags.add(Tags.REDUCED);
		this.originalName = this.name;
		this.tributes = this.baseTributes = 3;
		this.baseMagicNumber = this.magicNumber = 3;
		this.secondMagic = this.baseSecondMagic = 2;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	
    	tribute();
    	applyPowerToSelf(new MantraPower(p, this.magicNumber));
    	for (int i = 0; i < this.secondMagic; i++)
    	{
	    	int randomMagnetNum = AbstractDungeon.cardRandomRng.random(0, 2);
	    	switch (randomMagnetNum)
	    	{
	    		case 0: applyPowerToSelf(new AlphaMagPower(p, p)); break;
	    		case 1: applyPowerToSelf(new BetaMagPower(p, p)); break;
	    		case 2: applyPowerToSelf(new GammaMagPower(p, p)); break;
	    		default: applyPowerToSelf(new BetaMagPower(p, p)); break;
	    	}
    	}
    
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new SuperheavyMagnet();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }


	@Override
	public void onTribute(DuelistCard tributingCard) {
		superSynTrib(tributingCard);
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
