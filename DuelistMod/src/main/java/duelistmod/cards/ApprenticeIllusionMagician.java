package duelistmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;

import duelistmod.*;
import duelistmod.interfaces.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;

public class ApprenticeIllusionMagician extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = DuelistMod.makeID("ApprenticeIllusionMagician");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makeCardPath("ApprenticeIllusionMagician.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
	private static final int COST = 1;
	private boolean upgradeAvail = true;
	// /STAT DECLARATION/

	public ApprenticeIllusionMagician() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.baseDamage = this.damage = 11;
		this.tributes = this.baseTributes = 3;
		this.magicNumber = this.baseMagicNumber = 1;
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.ORB_DECK);
		this.tags.add(Tags.SPELLCASTER);
		this.orbDeckCopies = 1;
		this.misc = 0;
		this.originalName = this.name;
		this.setupStartingCopies();
		this.exhaust = true;
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		tribute(p, this.tributes, false, this);
		attack(m, AFX, this.damage);
		applyPowerToSelf(new FocusPower(p, this.magicNumber));
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new ApprenticeIllusionMagician();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (canUpgrade()) 
		{
			// Name
			if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
			
			// Upgrade Effects
			if (DuelistMod.hasUpgradeBuffRelic)
			{
				if      (timesUpgraded == 1) 	{ this.upgradeDamage(5);							}
				else if (timesUpgraded == 2) 	{ this.upgradeMagicNumber(1); 						}
				else if (timesUpgraded == 3) 	{ this.upgradeDamage(6); 							}
				else if (timesUpgraded == 4) 	{ this.upgradeMagicNumber(1); 						}
				else if (timesUpgraded == 5) 	{ this.exhaust = false;								}
			}
			
			else
			{
				if      (timesUpgraded == 1) 	{ this.upgradeDamage(3);							}
				else if (timesUpgraded == 2) 	{ this.upgradeMagicNumber(1); 						}
				else if (timesUpgraded == 3) 	{ this.upgradeDamage(4); 							}
				else if (timesUpgraded == 4) 	{ this.upgradeTributes(-1);							}
				else if (timesUpgraded == 5) 	{ this.exhaust = false; 							}
			}

			// Description
        	if (timesUpgraded == 5) { this.rawDescription = EXTENDED_DESCRIPTION[0]; }
        	else { this.rawDescription = UPGRADE_DESCRIPTION; }
			this.initializeDescription();
			
			if (timesUpgraded == 5)
			{
				this.upgradeAvail = false;
			}
		}
	}
	
	@Override
	public boolean canUpgrade()
	{
		if (upgradeAvail) { return true; }
		else { return false; }
	}

	// If player doesn't have enough summons, can't play card
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
    	// Check super canUse()
    	boolean canUse = super.canUse(p, m); 
    	if (!canUse) { return false; }
    	
    	// If upgraded, don't need tributes
    	//else if (this.tributes < 1) { return true; }
    	
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
		spellcasterSynTrib(tributingCard);
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