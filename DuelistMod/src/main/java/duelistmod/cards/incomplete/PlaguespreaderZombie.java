package duelistmod.cards.incomplete;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import duelistmod.*;
import duelistmod.actions.unique.PlayRandomFromDiscardAction;
import duelistmod.interfaces.*;
import duelistmod.orbs.Shadow;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;

public class PlaguespreaderZombie extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("PlaguespreaderZombie");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("PlaguespreaderZombie.png");
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

    public PlaguespreaderZombie() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseDamage = this.damage = 1;
        this.baseBlock = this.block = 1;
        this.baseMagicNumber = this.magicNumber = 1;
        this.summons = this.baseSummons = 1;
        this.isSummon = true;
        this.tributes = this.baseTributes = 1;
        this.misc = 0;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.ZOMBIE);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	// Tribute
    	tribute();
    	
    	// Summon
    	summon();
    	
    	// Block
    	block();
    	
    	// Attack
    	attack(m);
    	
    	// Lose HP
    	damageSelf(this.magicNumber);
    	
    	// Draw zombies
    	drawTag(this.magicNumber, Tags.ZOMBIE);
    	
    	// Channel shadow
    	for (int i = 0; i < this.magicNumber; i++)
    	{
	    	AbstractOrb shadow = new Shadow();
	    	channel(shadow);
	    	
	    	// Random buff
	    	applyRandomBuffPlayer(p, AbstractDungeon.cardRandomRng.random(1, 3), false);
	    	
	    	// Random debuff
	    	AbstractPower debuff = RandomEffectsHelper.getRandomDebuff(p, m, AbstractDungeon.cardRandomRng.random(1, 4));
	    	applyPower(debuff, m);
    	}
    	
    	// Resummon random monster from discard
    	AbstractDungeon.actionManager.addToBottom(new PlayRandomFromDiscardAction(this.magicNumber, false, m, this.uuid));
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new PlaguespreaderZombie();
    }

    // Upgraded stats.
    @Override
    public void upgrade() 
    {
        if (canUpgrade()) 
        {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	if (DuelistMod.hasUpgradeBuffRelic)
        	{
        		this.upgradeSummons(3);
        		this.upgradeDamage(5);
        		this.upgradeBlock(5);
        		this.upgradeMagicNumber(1);        		
        	}
        	else
        	{
        		this.upgradeSummons(1);
        		this.upgradeDamage(2);
        		this.upgradeBlock(3);
        	}
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
    
    @Override
    public boolean canUpgrade()
    {
    	return true;
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		zombieSynTrib(tributingCard);		
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