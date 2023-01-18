package duelistmod.cards.pools.naturia;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class LuminousMoss extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("LuminousMoss");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("LuminousMoss.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public LuminousMoss() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseMagicNumber = this.magicNumber = 1;
        this.misc = 0;
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.ARCANE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	int cards = 0;
    	for (AbstractCard c : p.hand.group)
    	{
    		if (c.hasTag(Tags.NATURIA))
    		{
    			this.addToBot(new ExhaustSpecificCardAction(c, p.hand));
    			cards++;
    		}
    	}
    	for (AbstractCard c : p.discardPile.group)
    	{
    		if (c.hasTag(Tags.NATURIA))
    		{
    			this.addToBot(new ExhaustSpecificCardAction(c, p.discardPile));
    			cards++;
    		}
    	}
    	
    	if (cards > 0)
    	{
    		gainTempHP(cards * this.magicNumber);
    		for (int i = 0; i < cards; i++)
    		{
    			AbstractCard randNat = returnTrulyRandomFromOnlyFirstSet(Tags.NATURIA, Tags.MEGATYPED);
    			this.addToBot(new MakeTempCardInDrawPileAction(randNat, 1, true, true));
    		}
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
        	this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
    
    @Override
    public boolean canUpgrade()
    {
    	if (this.magicNumber < 6) { return true; }
    	else { return false; }
    }






	
	@Override
    public AbstractCard makeCopy() { return new LuminousMoss(); }
	
}
