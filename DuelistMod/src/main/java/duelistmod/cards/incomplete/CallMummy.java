package duelistmod.cards.incomplete;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.actions.common.*;
import duelistmod.interfaces.DuelistCard;
import duelistmod.patches.*;

public class CallMummy extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("CallMummy");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("CallMummy.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public CallMummy() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.SPELL);
        this.misc = 0;
        this.originalName = this.name;
        this.baseMagicNumber = this.magicNumber = 1;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	ArrayList<DuelistCard> handCards = new ArrayList<DuelistCard>();
		for (AbstractCard a : p.hand.group) { if (a.hasTag(Tags.MONSTER) && !a.hasTag(Tags.EXEMPT)) { handCards.add((DuelistCard) a.makeStatEquivalentCopy()); }}
		if (handCards.size() > 0)
		{
			if (handCards.size() < this.magicNumber) { AbstractDungeon.actionManager.addToTop(new CardSelectScreenResummonAction(handCards, handCards.size(), false, false)); }
			else { AbstractDungeon.actionManager.addToTop(new CardSelectScreenResummonAction(handCards, this.magicNumber, false, false)); }    		
		}    	
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() 
    {
        return new CallMummy();
    }

    @Override
    public void upgrade() {
        if (!upgraded) 
        {
        	// Name
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	if (DuelistMod.hasUpgradeBuffRelic) { this.exhaust = false; this.upgradeBaseCost(1); }
        	else { this.exhaust = false; }
        	this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
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