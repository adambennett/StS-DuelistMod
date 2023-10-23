package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class ClearKuriboh extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("ClearKuriboh");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ClearKuriboh.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public ClearKuriboh() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseDamage = this.damage = 7;
        this.isMultiDamage = true;
        this.summons = this.baseSummons = 1;
        this.isSummon = true;
        this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.ARCANE);
        this.tags.add(Tags.KURIBOH);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	this.applyPowers();
    	attackAllEnemies();
    	if (DuelistMod.lastTagSummoned != Tags.ALL)
    	{
	    	DuelistCard randMon = (DuelistCard) returnTrulyRandomFromSet(DuelistMod.lastTagSummoned, false);
	    	addCardToHand(randMon);
    	}
    }

    
    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (canUpgrade()) 
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	this.upgradeBaseCost(this.cost - 1);
        	if (DuelistMod.hasUpgradeBuffRelic) { this.upgradeDamage(3); }
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
    
    @Override
    public boolean canUpgrade()
    {
    	if (this.cost > 0) { return true; }
    	else { return false; }
    }


	






	
	@Override
    public AbstractCard makeCopy() { return new ClearKuriboh(); }
	
}
