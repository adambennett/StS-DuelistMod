package duelistmod.cards.incomplete;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.OverflowDecrementMagicAction;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class WanderingKing extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("WanderingKing");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("WanderingKing.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 3;
    // /STAT DECLARATION/

    public WanderingKing() 
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseDamage = this.damage = 18;
        this.tributes = this.baseTributes = 1;
        this.magicNumber = this.baseMagicNumber = 4;
        this.secondMagic = this.baseSecondMagic = 6;
        this.misc = 0;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.FIEND);
        this.tags.add(Tags.BAD_MAGIC);
        this.tags.add(Tags.IS_OVERFLOW);
    }
    
    @Override
    public void triggerOverflowEffect()
    {
    	if (getSummons(player()) >= this.tributes)
    	{
            tribute();
            globalOverflow();
    	}
    }
    
    @Override
    public void triggerOnEndOfPlayerTurn() 
    {
    	// If overflows remaining
        if (checkMagicNum() > 0) 
        {
        	if (getSummons(player()) >= this.tributes)
        	{
	        	// Remove 1 overflow
	            AbstractDungeon.actionManager.addToTop(new OverflowDecrementMagicAction(this, -1));
	            
	            // Tribute
	            tribute();
	            
	            // Check Splash Orbs
	            globalOverflow();
        	}
        }
        super.triggerOnEndOfPlayerTurn();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	attackAllEnemies(this.damage);
    	tribute();    	
    	for (AbstractMonster mon : AbstractDungeon.getCurrRoom().monsters.monsters)
    	{
    		if (!mon.isDead && !mon.isDying && !mon.isDeadOrEscaped() && !mon.halfDead)
    		{
    			int roll = AbstractDungeon.cardRandomRng.random(1, 4);
    			if (roll == 1)
    			{
    				applyPower(new VulnerablePower(mon, this.secondMagic, false), mon);
    			}
    		}
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
        	this.upgradeMagicNumber(-1);
        	this.upgradeSecondMagic(3);
        	this.upgradeDamage(4);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
			
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
	public void onResummon(int summons) 
	{
		
		
	}

	@Override
	public String getID() { return ID; }
	
	@Override
    public AbstractCard makeCopy() { return new WanderingKing(); }
	public void summonThis(int summons, DuelistCard c, int var) {}
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {}
	public void optionSelected(AbstractPlayer arg0, AbstractMonster arg1, int arg2) {}
}