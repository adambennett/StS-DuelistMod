package duelistmod.cards.pools.zombies;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class VampireGenesis extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("VampireGenesis");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("Vampire_Genesis.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public VampireGenesis() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.tributes = this.baseTributes = 1;
        this.misc = 0;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.ZOMBIE);
        this.tags.add(Tags.VAMPIRE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute();
    	int tokens = 0;
    	for (AbstractCard c : p.hand.group) { if (c.hasTag(Tags.VAMPIRE) && !c.uuid.equals(this.uuid)) { tokens++; }}
    	if (tokens > 0)
    	{
	    	// Find monster of that many tributes, or the next highest available if there are none of that tribute cost
	    	int highestTrib = 0;																// Keeps track of highest tribute cost that exists that is less than the number of aquas summoned
	    	ArrayList<DuelistCard> possibleTributeMonsters = new ArrayList<DuelistCard>();		// All tribute monsters that match either the number of aquas summoned or the highest trib value after the first iteration
	    	ArrayList<DuelistCard> allTributeMonsters = new ArrayList<DuelistCard>();			// Every tribute monster that exists, for last ditch effort to just resummon completely randomly if the other logic fails somehow
	    	
	    	// Loop through duelist cards, figure out if there are any monsters with tribute cost = aquas summoned
	    	// Simultaneously check to see what the next highest tribute cost that exists for any monster is (so if we are looking for ex: 7 tribute cost and there are no 7 tribute monsters, we look until we discover that there are 5 tribute monsters, so we save 5 as the next highest
	    	for (DuelistCard c : DuelistMod.myCards)
	    	{
	    		// Only look at monsters
	    		if (c.hasTag(Tags.MONSTER) && !c.hasTag(Tags.NOT_ADDED) && !c.hasTag(Tags.EXEMPT))
	    		{
	    			// Only look at monsters with tribute costs
	    			if (c.isTributeCard())
	    			{
	    				// If tribute cost = aquas summoned, save to list
	    				if (c.baseTributes == tokens) { possibleTributeMonsters.add((DuelistCard) c.makeStatEquivalentCopy()); }
	    				
	    				// Otherwise, check to see if we want to update the next highest tributes value
	    				else if (c.baseTributes < tokens && c.baseTributes > highestTrib) { highestTrib = c.baseTributes; } 
	    				
	    				// Add all monsters with tribute cost to second list
	    				allTributeMonsters.add((DuelistCard) c.makeStatEquivalentCopy());
	    			}
	    		}
	    	}
	    	
	    	// Now that we have checked for all tribute monsters with tribute cost = aquas summoned, lets see if there are any
	    	if (possibleTributeMonsters.size() > 0)
	    	{
	    		DuelistCard randomChoice = possibleTributeMonsters.get(AbstractDungeon.cardRandomRng.random(possibleTributeMonsters.size() - 1));
	    		DuelistCard.resummon(randomChoice, m, false, this.upgraded);
	    	}
	    	
	    	// If not, loop through again, and save all tribute monsters with the previously found tribute cost (that we know exists for at least 1 monster)
	    	else
	    	{
	    		for (DuelistCard c : DuelistMod.myCards)
	    		{
	    			if (c.hasTag(Tags.MONSTER))
	        		{
	        			if (c.isTributeCard())
	        			{
	        				if (c.baseTributes == highestTrib)
	        				{
	        					possibleTributeMonsters.add((DuelistCard) c.makeStatEquivalentCopy());
	        				}
	        			}
	        		}
	    		}
	    		
	    		// Just make sure we actually did find monsters, incase something goes wrong? idk
	    		if (possibleTributeMonsters.size() > 0)
	    		{
	    			DuelistCard randomChoice = possibleTributeMonsters.get(AbstractDungeon.cardRandomRng.random(possibleTributeMonsters.size() - 1));
		    		DuelistCard.resummon(randomChoice, m, false, this.upgraded);
	    		}
	    		
	    		// If something DOES happen, just resummon a completely random tribute monster and send a debug message
	    		else
	    		{
	    			DuelistCard randomChoice = allTributeMonsters.get(AbstractDungeon.cardRandomRng.random(allTributeMonsters.size() - 1));
		    		DuelistCard.resummon(randomChoice, m, false, this.upgraded);
	    			if (DuelistMod.debug) { DuelistMod.logger.info("Big Wave Small Wave generated a card in the most dumb way possible, in case you were wondering why you got THAT monster specifically. Yeah, I hope you never see this message, because if you do, that means the logic for this card's code is messed up or something catastrophic happened..."); }
	    		}
	    	}
	    	// END Find monster
    	}
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
		zombieSynTrib(tributingCard);
	}


	@Override
	public void onResummon(int summons) 
	{
		
		
	}

	@Override
	public String getID() { return ID; }
	
	@Override
    public AbstractCard makeCopy() { return new VampireGenesis(); }
	public void summonThis(int summons, DuelistCard c, int var) {}
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {}
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {}
}
