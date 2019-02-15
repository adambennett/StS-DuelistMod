package defaultmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.*;
import defaultmod.powers.SummonPower;

public class SmallLabyrinthWall extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = defaultmod.DefaultMod.makeID("SmallLabyrinthWall");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DefaultMod.makePath(DefaultMod.LABYRINTH_WALL);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final int COST = 1;
    private static final int BLOCK = 15;
    private static final int UPGRADE_PLUS_BLK = 10;
    private static final int TRIBUTES = 1;
    private boolean secondUpgrade = false;
    // /STAT DECLARATION/

    public SmallLabyrinthWall() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = BLOCK;
        this.tags.add(DefaultMod.MONSTER);
        this.misc = 0;
        this.tributes = 1;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute(p, TRIBUTES, false, this);
    	block(this.block);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new SmallLabyrinthWall();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!this.upgraded) 
        {
            this.upgradeName("Labyrinth Wall");
            this.upgradeBlock(UPGRADE_PLUS_BLK);
            this.tributes = 2;
            this.upgradeBaseCost(2);
            //this.rarity = CardRarity.UNCOMMON;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }

        else if (this.upgraded && !this.secondUpgrade)
        {
        	this.name = "Labyrinth Wall+";
        	this.upgradeBlock(7);
            this.initializeDescription();
            this.secondUpgrade = true;
            this.upgraded = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    // If player doesn't have enough summons, can't play card
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
    	// Check super canUse()
    	boolean canUse = super.canUse(p, m); 
    	if (!canUse) { return false; }
    	
  		// Pumpking & Princess
  		else if (this.misc == 52) { return true; }
    	
    	// Check for # of summons >= tributes
    	else { if (p.hasPower(SummonPower.POWER_ID)) { int temp = (p.getPower(SummonPower.POWER_ID).amount); if (temp >= TRIBUTES) { return true; } } }
    	
    	// Player doesn't have something required at this point
    	this.cantUseMessage = "Not enough Summons";
    	return false;
    }

    public boolean canUpgrade()
    {
    	if (!secondUpgrade) { return true; }
    	else { return false; }
    }

}