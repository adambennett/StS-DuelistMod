package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.actions.common.CardSelectScreenIntoHandAction;
import duelistmod.interfaces.DuelistCard;
import duelistmod.patches.*;

public class Cloning extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("Cloning");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.CLONING);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public Cloning() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.TRAP);
        this.tags.add(Tags.ALL);
        this.misc = 0;
        this.originalName = this.name;
        this.baseMagicNumber = this.magicNumber = 1;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	ArrayList<AbstractCard> handCards = new ArrayList<AbstractCard>();
    	if (upgraded)
    	{
    		for (AbstractCard a : p.hand.group) { if (a.hasTag(Tags.MONSTER)) { handCards.add(a.makeStatEquivalentCopy()); }}
    		if (handCards.size() > 0)
    		{
    			if (handCards.size() < this.magicNumber) { AbstractDungeon.actionManager.addToTop(new CardSelectScreenIntoHandAction(false, true, handCards.size(), handCards)); }
    			else { AbstractDungeon.actionManager.addToTop(new CardSelectScreenIntoHandAction(false, true, this.magicNumber, handCards)); }    		
    		}
    	}
    	else
    	{
    		for (AbstractCard a : p.hand.group) { if (a.hasTag(Tags.MONSTER)) { handCards.add(a); }}
    		if (handCards.size() > 0)
        	{			
        		AbstractCard cardCopy = returnRandomFromArrayAbstract(handCards).makeStatEquivalentCopy();
    			if (cardCopy != null)
    			{
    				if (cardCopy.upgraded) { cardCopy.upgrade(); }
    				cardCopy.applyPowers();
    				addCardToHand(cardCopy);
    			}			
        	}
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() 
    {
        return new Cloning();
    }

    @Override
    public void upgrade() {
        if (canUpgrade()) 
        {
        	// Name
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            
        	// Choose number of cards upgrade after initial upgrade from copy randomly
        	if (timesUpgraded != 1 && this.baseMagicNumber < 5) { this.upgradeMagicNumber(1); }
           
        	// Description
        	if (timesUpgraded == 1) { this.rawDescription = UPGRADE_DESCRIPTION; }
        	else { this.rawDescription = EXTENDED_DESCRIPTION[0]; }
            this.initializeDescription();
        }        
        else { this.upgraded = true; }
    }
    
    @Override
    public boolean canUpgrade()
    {
    	if (this.baseMagicNumber < 5)
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
	
	}



	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		// TODO Auto-generated method stub
		
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