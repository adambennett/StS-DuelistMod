package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class ToonGoblinAttack extends DuelistCard 
{

    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("ToonGoblin");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.TOON_GOBLIN_ATTACK);
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

    public ToonGoblinAttack() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = 30;
        this.summons = this.baseSummons = 1;
        this.tributes = this.baseTributes = 2;
        this.magicNumber = this.baseMagicNumber = 4;
        this.toon = true;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.TOON);
		this.originalName = this.name;
        this.isSummon = true;
        this.misc = 0;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute();
    	summon();
    	block();
    	applyPower(new VulnerablePower(m, this.magicNumber, false), m);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new ToonGoblinAttack();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    // Checking for tributes and if challenge is enabled, monster zones, and finally also make sure to have Toon World. Jesus.. must be a better way to do this
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
    	// Check for monster zones challenge
    	if (Util.isCustomModActive("theDuelist:SummonersChallenge") || DuelistMod.challengeMode)
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
		    				// Pumpking & Princess
		            		if (this.misc == 52) { return true; }
		            		
		    				// Toon World
		    		    	if (p.hasPower(ToonWorldPower.POWER_ID) || p.hasPower(ToonKingdomPower.POWER_ID)) { return true; }
		    		    	this.cantUseMessage = DuelistMod.toonWorldString; return false; 
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
		    			// Pumpking & Princess
	            		if (this.misc == 52) { return true; }
	            		
	    				// Toon World
	    		    	if (p.hasPower(ToonWorldPower.POWER_ID) || p.hasPower(ToonKingdomPower.POWER_ID)) { return true; }
	    		    	this.cantUseMessage = DuelistMod.toonWorldString; return false; 
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
				    				// Pumpking & Princess
				            		if (this.misc == 52) { return true; }
				            		
				    				// Toon World
				    		    	if (p.hasPower(ToonWorldPower.POWER_ID) || p.hasPower(ToonKingdomPower.POWER_ID)) { return true; }
				    		    	this.cantUseMessage = DuelistMod.toonWorldString; return false; 
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
			    				// Pumpking & Princess
			            		if (this.misc == 52) { return true; }
			            		
			    				// Toon World
			    		    	if (p.hasPower(ToonWorldPower.POWER_ID) || p.hasPower(ToonKingdomPower.POWER_ID)) { return true; }
			    		    	this.cantUseMessage = DuelistMod.toonWorldString; return false; 
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
					// Pumpking & Princess
            		if (this.misc == 52) { return true; }
            		
    				// Toon World
    		    	if (p.hasPower(ToonWorldPower.POWER_ID) || p.hasPower(ToonKingdomPower.POWER_ID)) { return true; }
    		    	this.cantUseMessage = DuelistMod.toonWorldString; return false; 
				}
				
				else
				{
					if (p.hasPower(SummonPower.POWER_ID)) 
					{ 
						int temp = (p.getPower(SummonPower.POWER_ID).amount); 
						if (temp >= this.tributes) 
						{
							// Pumpking & Princess
		            		if (this.misc == 52) { return true; }
		            		
		    				// Toon World
		    		    	if (p.hasPower(ToonWorldPower.POWER_ID) || p.hasPower(ToonKingdomPower.POWER_ID)) { return true; }
		    		    	this.cantUseMessage = DuelistMod.toonWorldString; return false; 
						} 
					}
				}
			}
	    	
	    	// Check for # of summons >= tributes
	    	else 
	    	{ 
	    		if (p.hasPower(SummonPower.POWER_ID))
	    		{ 
	    			int temp = (p.getPower(SummonPower.POWER_ID).amount); 
	    			if (temp >= this.tributes) 
	    			{ 
	    				// Pumpking & Princess
	            		if (this.misc == 52) { return true; }
	            		
	    				// Toon World
	    		    	if (p.hasPower(ToonWorldPower.POWER_ID) || p.hasPower(ToonKingdomPower.POWER_ID)) { return true; }
	    		    	this.cantUseMessage = DuelistMod.toonWorldString; return false; 
	    			} 
	    		} 
	    	}
	    	
	    	// Player doesn't have something required at this point
	    	this.cantUseMessage = this.tribString;
	    	return false;
    	}
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		toonSynTrib(tributingCard);
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