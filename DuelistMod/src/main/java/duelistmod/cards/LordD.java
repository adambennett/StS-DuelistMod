package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.orbs.*;
import duelistmod.patches.*;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class LordD extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = DuelistMod.makeID("LordD");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.LORD_D);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 1;
	// /STAT DECLARATION/

	public LordD() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.SPELLCASTER);
		this.tags.add(Tags.ARCANE);
		this.tributes = this.baseTributes = 2;
		this.secondMagic = this.baseSecondMagic = 0;
		this.originalName = this.name;
		this.exhaust = true;
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		tribute(p, this.tributes, false, this);
		AbstractOrb dragon = new DragonOrb();
		AbstractOrb dragonPlus = new DragonPlusOrb();
		if (!upgraded) { channel(dragon); }
		else { channel(dragonPlus); }
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new LordD();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (canUpgrade()) 
		{
			if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
			if (timesUpgraded == 3) { this.upgradeSecondMagic(1); this.upgradeBaseCost(0); }
			if (timesUpgraded == 2) { this.exhaust = false; }
			if (timesUpgraded == 1) { this.rawDescription = UPGRADE_DESCRIPTION; }
        	else { this.rawDescription = EXTENDED_DESCRIPTION[0]; }
            this.initializeDescription();
		}
	}
	
	@Override
	public boolean canUpgrade()
	{
		if (this.secondMagic < 1) { return true; }
		else { return false; }
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