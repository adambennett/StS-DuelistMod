package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.unique.*;
import duelistmod.helpers.StarterDeckSetup;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class TheCreator extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("TheCreator");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.THE_CREATOR);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public TheCreator() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tributes = this.baseTributes = 5;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.ALL);
        this.tags.add(Tags.CREATOR_DECK);
        this.tags.add(Tags.EXODIA_DECK);
        this.tags.add(Tags.NEVER_GENERATE);
        this.tags.add(Tags.NO_CARD_FOR_RANDOM_DECK_POOLS);
        this.tags.add(Tags.NO_CREATOR);
        this.tags.add(Tags.EXEMPT);
        this.exodiaDeckCopies = 1;
        this.originalName = this.name;
        this.standardDeckCopies = 1;
        this.setupStartingCopies();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	// Tribute
    	tribute();
    	
    	// Exhaust all cards in draw pile
    	if (p.drawPile.group.size() < 500) { AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardListSuperFastAction(p.drawPile.group, p.drawPile, true)); }
    	else { p.exhaustPile.group.addAll(p.drawPile.group);	p.drawPile.group.clear(); }
    
    	// Exhaust all cards in discard pile
    	if (p.discardPile.group.size() < 500) { AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardListSuperFastAction(p.discardPile.group, p.discardPile, true)); }
    	else { p.exhaustPile.group.addAll(p.discardPile.group);	p.discardPile.group.clear(); }
    	
    	// Add a 1 cost for combat, ethereal copy of EVERY Card in pool to draw pile
    	// Hardcoded different pool for Creator Deck - since that pool sucks that sort of ruins the card which is the entire basis of the deck
    	if (StarterDeckSetup.getCurrentDeck().getSimpleName().equals("Creator Deck")) 
    	{  
	    	ArrayList<AbstractCard> creatorDeckUniquePool = new ArrayList<AbstractCard>();
	    	for (AbstractCard c : DuelistMod.myCards)
	    	{
	    		if (!c.hasTag(Tags.NO_CREATOR) && !c.hasTag(Tags.NEVER_GENERATE) && !c.rarity.equals(CardRarity.SPECIAL) && !c.rarity.equals(CardRarity.BASIC))
	    		{
	    			creatorDeckUniquePool.add(c.makeCopy());
	    		}
	    	}
	    	AbstractDungeon.actionManager.addToBottom(new TheCreatorAction(p, p, creatorDeckUniquePool, 1, true, false)); 
    	}
    	else { AbstractDungeon.actionManager.addToBottom(new TheCreatorAction(p, p, DuelistMod.coloredCards, 1, true, false)); }
		
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new TheCreator();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
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
    	
    	// Mausoleum check
    	else if (p.hasPower(EmperorPower.POWER_ID))
		{
			EmperorPower empInstance = (EmperorPower)p.getPower(EmperorPower.POWER_ID);
			if (!empInstance.flag)
			{
				return true;
			}
			else
			{
				if (p.hasPower(SummonPower.POWER_ID)) { int temp = (p.getPower(SummonPower.POWER_ID).amount); if (temp >= this.tributes) { return true; } }
			}
		}
    	
    	// Check for # of summons >= tributes
    	else { if (p.hasPower(SummonPower.POWER_ID)) { int temp = (p.getPower(SummonPower.POWER_ID).amount); if (temp >= this.tributes) { return true; } } }
    	
    	// Player doesn't have something required at this point
    	this.cantUseMessage = this.tribString;
    	return false;
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