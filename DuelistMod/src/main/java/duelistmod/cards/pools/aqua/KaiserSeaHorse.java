package duelistmod.cards.pools.aqua;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class KaiserSeaHorse extends DuelistCard 
{
    // TEXT DECLARATION
    private static final CardStrings cardStrings = getCardStrings();
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public KaiserSeaHorse() {
        super(getCARDID(), NAME, getIMG(), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.AQUA);
        this.misc = 0;
        this.specialCanUseLogic = true;
        this.useTributeCanUse = true;
        this.originalName = this.name;
        this.baseTributes = this.tributes = 2;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	int tokens = DuelistMod.overflowsThisCombat;
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
	    		DuelistCard.fullResummon(randomChoice, this.upgraded, m, false);
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
	        		DuelistCard.fullResummon(randomChoice, this.upgraded, m, false);
	    		}
	    		
	    		// If something DOES happen, just resummon a completely random tribute monster and send a debug message
	    		else
	    		{
	    			DuelistCard randomChoice = allTributeMonsters.get(AbstractDungeon.cardRandomRng.random(allTributeMonsters.size() - 1));
	        		DuelistCard.fullResummon(randomChoice, this.upgraded, m, false);
	    			if (DuelistMod.debug) { DuelistMod.logger.info("Big Wave Small Wave generated a card in the most dumb way possible, in case you were wondering why you got THAT monster specifically. Yeah, I hope you never see this message, because if you do, that means the logic for this card's code is messed up or something catastrophic happened..."); }
	    		}
	    	}
	    	// END Find monster
    	}
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new KaiserSeaHorse();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            this.upgradeTributes(-1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription(); 
        }
    }
    
    // Tribute canUse()
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
    	if (this.specialCanUseLogic)
    	{
    		if (this.useTributeCanUse)
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
    		else if (this.useBothCanUse)
    		{
    			// Check for monster zones challenge
    	    	if (Util.isCustomModActive("theDuelist:SummonersChallenge") || DuelistMod.challengeLevel20)
    	    	{
    	    		if ((DuelistMod.getChallengeDiffIndex() < 3) && this.misc == 52) { return true; }
    	    		// Check for energy and other normal game checks
    	    		boolean canUse = super.canUse(p, m); 
    	        	if (!canUse) { return false; }
    	        	
    	        	// Mausoleum check
    		    	else if (p.hasPower(EmperorPower.POWER_ID))
    				{
    		    		// If mausoleum is active skip tribute check and just check monster zones for space
    					EmperorPower empInstance = (EmperorPower)p.getPower(EmperorPower.POWER_ID);
    					if (!empInstance.flag)
    					{
    						if (p.hasPower(SummonPower.POWER_ID))
    			    		{
    			    			int sums = DuelistCard.getSummons(p); int max = DuelistCard.getMaxSummons(p);
    			    			if (sums + this.summons <= max) 
    			    			{ 
    			    				return true; 
    			    			}
    			    			else 
    			    			{ 
    			    				if (sums < max) 
    			    				{ 
    			    					if (max - sums > 1) { this.cantUseMessage = "You only have " + (max - sums) + " monster zones"; }
    			    					else { this.cantUseMessage = "You only have " + (max - sums) + " monster zone"; }
    			    					
    			    				}
    			    				else { this.cantUseMessage = "No monster zones remaining"; }
    			    				return false; 
    			    			}
    			    		}
    			    		else
    			    		{
    			    			return true;
    			    		}
    					}
    					
    					// If no mausoleum active, check tributes and then check summons
    					else
    					{
    						if (p.hasPower(SummonPower.POWER_ID))
    			    		{ 
    			    			int sums = DuelistCard.getSummons(p); 
    			    			if (sums >= this.tributes) 
    			    			{ 
    			    				int max = DuelistCard.getMaxSummons(p);
    			    				if (sums - tributes < 0) { return true; }
    			    				else 
    			    				{ 
    			    					sums -= this.tributes;
    					    			if (sums + this.summons <= max) 
    					    			{ 
    					    				return true; 
    					    			}
    					    			else 
    					    			{ 
    					    				if (sums < max) 
    					    				{ 
    					    					if (max - sums > 1) { this.cantUseMessage = "You only have " + (max - sums) + " monster zones"; }
    					    					else { this.cantUseMessage = "You only have " + (max - sums) + " monster zone"; }
    					    					
    					    				}
    					    				else { this.cantUseMessage = "No monster zones remaining"; }
    					    				return false; 
    					    			} 
    				    			}
    									
    			    			} 
    			    		} 
    					}
    				}
    		    	
    		    	// No mausoleum power - so just check for number of tributes and summon slots
    		    	else 
    		    	{ 
    		    		if (p.hasPower(SummonPower.POWER_ID))
    		    		{ 
    		    			int sums = DuelistCard.getSummons(p); 
    		    			if (sums >= this.tributes) 
    		    			{ 
    		    				int max = DuelistCard.getMaxSummons(p);
    		    				if (sums - tributes < 0) { return true; }
    		    				else 
    		    				{ 
    		    					sums -= this.tributes;
    				    			if (sums + this.summons <= max) 
    				    			{ 
    				    				return true; 
    				    			}
    				    			else 
    				    			{ 
    				    				if (sums < max) 
    				    				{ 
    				    					if (max - sums > 1) { this.cantUseMessage = "You only have " + (max - sums) + " monster zones"; }
    				    					else { this.cantUseMessage = "You only have " + (max - sums) + " monster zone"; }
    				    					
    				    				}
    				    				else { this.cantUseMessage = "No monster zones remaining"; }
    				    				return false; 
    				    			} 
    			    			}
    								
    		    			} 
    		    		} 
    		    	}
    		    	
    		    	// Player doesn't have something required at this point
    		    	this.cantUseMessage = this.tribString;
    		    	return false;
    	        	
    	    	}
    	    	
    	    	// Default behavior - no monster zone challenge
    	    	else
    	    	{
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
    		}
    		else
    		{
    			// Check super canUse()
    	    	boolean canUse = super.canUse(p, m); 
    	    	if (!canUse) { return false; }

    	    	if (Util.isCustomModActive("theDuelist:SummonersChallenge") || DuelistMod.challengeLevel20)
    	    	{
    	    		if ((DuelistMod.getChallengeDiffIndex() < 3) && this.misc == 52) { return true; }
    	    		if (p.hasPower(SummonPower.POWER_ID))
    	    		{
    	    			int sums = DuelistCard.getSummons(p); int max = DuelistCard.getMaxSummons(p);
    	    			if (sums + this.summons <= max) 
    	    			{ 
    	    				return true; 
    	    			}
    	    			else 
    	    			{ 
    	    				if (sums < max) 
    	    				{ 
    	    					if (max - sums > 1) { this.cantUseMessage = "You only have " + (max - sums) + " monster zones"; }
    	    					else { this.cantUseMessage = "You only have " + (max - sums) + " monster zone"; }
    	    					
    	    				}
    	    				else { this.cantUseMessage = "No monster zones remaining"; }
    	    				return false; 
    	    			}
    	    		}
    	    		else
    	    		{
    	    			return true;
    	    		}
    	    	}
    	    	
    	    	else
    	    	{
    	    		return true;
    	    	}
    		}
    	}
    	else
    	{
    		return super.canUse(p, m);
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
		return getCARDID();
	}

	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
	// AUTOSETUP - ID/IMG - Id, Img name, and class name all must match to use this
    public static String getCARDID()
    {
    	return DuelistMod.makeID(getCurClassName());
    }
    
	public static CardStrings getCardStrings()
    {
    	return CardCrawlGame.languagePack.getCardStrings(getCARDID());
    }
    
    public static String getIMG()
    {
    	return DuelistMod.makeCardPath(getCurClassName() + ".png");
    }
    
    public static String getCurClassName()
    {
    	return (new CurClassNameGetter()).getClassName();
    }

    public static class CurClassNameGetter extends SecurityManager{
    	public String getClassName(){
    		return getClassContext()[1].getSimpleName();
    	}
    }
    // END AUTOSETUP
}