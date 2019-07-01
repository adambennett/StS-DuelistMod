package duelistmod.cards.incomplete;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.BaseMod;
import duelistmod.*;
import duelistmod.interfaces.DuelistCard;
import duelistmod.patches.AbstractCardEnum;

public class HauntedShrine extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("HauntedShrine");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("HauntedShrine.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public HauntedShrine() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.tags.add(Tags.TRAP);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	
    	// exhaust -> draw
    	// draw -> discard
    	// discard -> exhaust
    	// hand -> random pile
    	// randomly from any pile -> hand, copy them and make them cost 0 this turn
    	ArrayList<AbstractCard> draw = new ArrayList<AbstractCard>();
    	ArrayList<AbstractCard> discard = new ArrayList<AbstractCard>();
    	ArrayList<AbstractCard> hand = new ArrayList<AbstractCard>();
    	ArrayList<AbstractCard> exhaust = new ArrayList<AbstractCard>();
    	
    	// init pile arrays with copies of cards from each pile, then clear each pile
    	for (AbstractCard c : p.exhaustPile.group) { exhaust.add(c.makeStatEquivalentCopy()); }
    	for (AbstractCard c : p.drawPile.group) { draw.add(c.makeStatEquivalentCopy()); }
    	for (AbstractCard c : p.discardPile.group) { discard.add(c.makeStatEquivalentCopy()); }
    	for (AbstractCard c : p.hand.group) { hand.add(c.makeStatEquivalentCopy()); }
    	p.drawPile.clear(); p.exhaustPile.clear(); p.discardPile.clear(); p.hand.clear();
    	
    	// now add copies of the cards from each pile to the new pile 
    	for (AbstractCard c : exhaust) { p.drawPile.group.add(c.makeStatEquivalentCopy()); }
    	for (AbstractCard c : draw) { p.discardPile.group.add(c.makeStatEquivalentCopy()); }
    	for (AbstractCard c : discard) { p.exhaustPile.group.add(c.makeStatEquivalentCopy()); }
    	
    	// Roll 1-3 for each card from your hand to place into a random other pile (draw, discard, exhaust)
    	for (AbstractCard c : hand) 
    	{ 
    		int pileRoll = AbstractDungeon.cardRandomRng.random(1, 3);
    		if (pileRoll == 1) 
    		{ 
    			p.discardPile.group.add(c.makeStatEquivalentCopy());
    			if (DuelistMod.debug) { DuelistMod.logger.info("Sent " + c.originalName + " to discard pile from your hand"); }
    		}
    		else if (pileRoll == 2) 
    		{ 
    			p.drawPile.group.add(c.makeStatEquivalentCopy());
    			if (DuelistMod.debug) { DuelistMod.logger.info("Sent " + c.originalName + " to draw pile from your hand"); }
    		}    		
    		else if (pileRoll == 3) 
    		{ 
    			p.exhaustPile.group.add(c.makeStatEquivalentCopy()); 
    			if (DuelistMod.debug) { DuelistMod.logger.info("Sent " + c.originalName + " to exhaust from your hand"); }
    		}    	
    	}
    	
    	// Fill the players hand with cards from random piles
    	if (!(draw.size() < 0 && discard.size() < 0 && exhaust.size() < 0))
    	{
    		for (int i = 0; i < BaseMod.MAX_HAND_SIZE; i++)
        	{
        		boolean sentACard = false;
        		while (!sentACard && (draw.size() > 0 || discard.size() > 0 || exhaust.size() > 0))
        		{
        			int pileRoll = AbstractDungeon.cardRandomRng.random(1, 3);
            		if (pileRoll == 1 && draw.size() > 0) 
            		{ 
            			AbstractCard c = draw.get(AbstractDungeon.cardRandomRng.random(draw.size() - 1)).makeStatEquivalentCopy(); 
            			c.modifyCostForTurn(-c.cost);
            			c.isCostModifiedForTurn = true;
            			p.hand.group.add(c);
            			if (DuelistMod.debug) { DuelistMod.logger.info("Sent " + c.originalName + " to hand from your old draw pile"); }
            			sentACard = true;
            			//addCardToHand(c);
            		}
            		else if (pileRoll == 2 && discard.size() > 0) 
            		{ 
            			AbstractCard c = discard.get(AbstractDungeon.cardRandomRng.random(discard.size() - 1)).makeStatEquivalentCopy(); 
            			c.modifyCostForTurn(-c.cost);
            			c.isCostModifiedForTurn = true;
            			p.hand.group.add(c);
            			if (DuelistMod.debug) { DuelistMod.logger.info("Sent " + c.originalName + " to hand from your old discard pile"); }
            			sentACard = true;
            			//addCardToHand(c);
            		}    		
            		else if (pileRoll == 3 && exhaust.size() > 0) 
            		{ 
            			AbstractCard c = exhaust.get(AbstractDungeon.cardRandomRng.random(exhaust.size() - 1)).makeStatEquivalentCopy(); 
            			c.modifyCostForTurn(-c.cost);
            			c.isCostModifiedForTurn = true;
            			p.hand.group.add(c); 
            			if (DuelistMod.debug) { DuelistMod.logger.info("Sent " + c.originalName + " to hand from your old exhaust pile"); }
            			sentACard = true;
            			//addCardToHand(c);
            		}  
        		}
        	}
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new HauntedShrine();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (!upgraded) 
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	this.upgradeBaseCost(0);
            this.rawDescription = UPGRADE_DESCRIPTION;
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