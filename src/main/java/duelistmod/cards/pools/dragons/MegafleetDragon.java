package duelistmod.cards.pools.dragons;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class MegafleetDragon extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("MegafleetDragon");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("MegafleetDragon.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;
    // /STAT DECLARATION/

    public MegafleetDragon() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tributes = this.baseTributes 			= 2;		// tributes
        this.specialCanUseLogic = true;							// for any summon or tribute card
        this.useTributeCanUse   = true;							// for tribute cards      
        this.baseMagicNumber = this.magicNumber 	= 12;		// 
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.DRAGON);
        this.tags.add(Tags.MACHINE);
        this.misc = 0;
        this.originalName = this.name;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute();
    	burnAllEnemies(this.magicNumber);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new MegafleetDragon();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            this.upgradeMagicNumber(3);
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
		return ID;
	}

	@Override
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
   
}
