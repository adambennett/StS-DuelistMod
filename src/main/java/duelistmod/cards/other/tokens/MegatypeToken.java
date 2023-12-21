package duelistmod.cards.other.tokens;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.*;
import duelistmod.actions.common.RandomizedHandAction;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.*;

public class MegatypeToken extends TokenCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("MegatypeToken");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("RainbowLife.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public MegatypeToken() 
    { 
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.TOKEN);
    	this.purgeOnUse = true;
    	this.magicNumber = this.baseMagicNumber = 1;
    	this.summons = this.baseSummons = 1;
    	this.makeMegatyped();
    	
    }
    public MegatypeToken(String tokenName) 
    { 
    	super(ID, tokenName, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET); 
    	this.tags.add(Tags.TOKEN);
    	this.purgeOnUse = true;
    	this.magicNumber = this.baseMagicNumber = 1;
    	this.summons = this.baseSummons = 1;
    	this.makeMegatyped();
    	
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	for (int i = 0; i < this.magicNumber; i++)
    	{
	    	CardTags randomTypeSelection = DuelistMod.monsterTypes.get(AbstractDungeon.cardRandomRng.random(DuelistMod.monsterTypes.size() - 1));
	    	DuelistCard randTypeMon = (DuelistCard) DuelistCard.returnTrulyRandomFromSets(randomTypeSelection, Tags.MONSTER);
	    	AbstractDungeon.actionManager.addToTop(new RandomizedHandAction(randTypeMon, true));  
    	}
    }
    @Override public AbstractCard makeCopy() { return new MegatypeToken(); }

    
    
	

	@Override public void upgrade() 
	{
		if (canUpgrade()) {
			if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
			this.upgradeBaseCost(0);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
	}
	


}
