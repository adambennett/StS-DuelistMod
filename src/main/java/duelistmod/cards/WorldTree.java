package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.powers.duelistPowers.WorldTreePower;
import duelistmod.variables.Tags;

public class WorldTree extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("WorldTree");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("World_Tree.png");
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

    public WorldTree() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseMagicNumber = this.magicNumber = 3;
        this.secondMagic = this.baseSecondMagic = 24;
        this.tags.add(Tags.SPELL);  
		this.tags.add(Tags.ARCANE);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	if (!p.hasPower(WorldTreePower.POWER_ID)) { applyPowerToSelf(new WorldTreePower(p, p, this.magicNumber, this.secondMagic)); }
    	else
    	{  
    		WorldTreePower pow = (WorldTreePower)p.getPower(WorldTreePower.POWER_ID);
    		pow.amount2 += this.secondMagic;
    		pow.updateDescription();
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new WorldTree();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (canUpgrade()) 
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	this.upgradeMagicNumber(1); this.upgradeSecondMagic(8);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
    
    @Override
    public boolean canUpgrade()
    {
    	if (this.magicNumber < 5) { return true; }
    	else { return false; }
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
