package duelistmod.cards;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.*;

import duelistmod.*;
import duelistmod.interfaces.DuelistCard;
import duelistmod.patches.*;
import duelistmod.powers.*;

public class SangaThunder extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = duelistmod.DuelistMod.makeID("SangaThunder");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.SANGA_THUNDER);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
    private static final int COST = 1;
    private static final int DAMAGE = 16;
    //private static final int UPGRADE_PLUS_DMG = 3;
    // /STAT DECLARATION/

    public SangaThunder() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.baseDamage = this.damage = DAMAGE;
    	this.tags.add(Tags.MONSTER);
    	this.tags.add(Tags.GUARDIAN);
    	this.tags.add(Tags.METAL_RAIDERS);
    	if (Loader.isModLoaded("conspire")) { this.tags.add(Tags.GOOD_TRIB); }
    	this.tags.add(Tags.ORIGINAL_ORB_DECK);
    	this.startingOPODeckCopies = 1;
    	this.misc = 0;
		this.originalName = this.name;
		this.tributes = this.baseTributes = 3;
		this.isSummon = true;
		this.summons = this.baseSummons = 1;
		this.setupStartingCopies();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	tribute(p, this.tributes, false, this);
    	summon(p, this.summons, this);
    	attack(m, AFX, this.damage);
    	AbstractOrb orb = new Lightning();
    	channel(orb);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new SangaThunder();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            //this.upgradeDamage(UPGRADE_PLUS_DMG);
            this.upgradeTributes(-1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    // Checking for tributes and if challenge is enabled, monster zones
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
    	// Check for monster zones challenge
    	if (Utilities.isCustomModActive("theDuelist:SummonersChallenge") || DuelistMod.challengeMode)
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

	@Override
	public void onTribute(DuelistCard tributingCard) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		AbstractMonster m = AbstractDungeon.getRandomMonster();
		summon(player(), this.summons, this);
    	attack(m, AFX, this.damage);
    	AbstractOrb orb = new Lightning();
    	channel(orb);
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) 
	{
		summon(player(), this.summons, this);
    	attack(m, AFX, this.damage);
    	AbstractOrb orb = new Lightning();
    	channel(orb);
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