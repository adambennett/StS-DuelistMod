package duelistmod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class AltarTribute extends DuelistCard 
{
	// TEXT DECLARATION

	public static final String ID = DuelistMod.makeID("AltarTribute");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.ALTAR_TRIBUTE);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
	private static final int COST = 2;
	// /STAT DECLARATION/

	public AltarTribute() 
	{
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.tags.add(Tags.TRAP);
		this.tags.add(Tags.NEVER_GENERATE);
		this.misc = 0;
		this.tributes = this.baseTributes = 2;
		this.secondMagic = this.baseSecondMagic = 0;
		this.originalName = this.name;
		this.exhaust = true;
		this.setupStartingCopies();
	}
	
	@Override
	public void update()
	{
		super.update();
		if (AbstractDungeon.player != null && AbstractDungeon.getCurrRoom().phase.equals(RoomPhase.COMBAT))
		{
			int dmg = 0;
			if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID))
			{				
				SummonPower pow = (SummonPower) AbstractDungeon.player.getPower(SummonPower.POWER_ID);
				if (pow.actualCardSummonList.size() >= this.tributes)
				{
					int endIndex = pow.actualCardSummonList.size() - 1;
					for (int i = endIndex; i > endIndex - this.tributes; i--)
					{
						if (pow.actualCardSummonList.get(i).block > 0)
						{
							dmg += pow.actualCardSummonList.get(i).block;
						}
					}
					
					if (dmg > 0)
					{
						this.secondMagic = this.baseSecondMagic = dmg;
					}
				}	
				else
				{
					this.secondMagic = this.baseSecondMagic =  0;
				}
			}
			else
			{
				this.secondMagic = this.baseSecondMagic =  0;
			}
		}
		else
		{
			this.secondMagic = this.baseSecondMagic =  0;
		}
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		int healTotal = 0;
		ArrayList<DuelistCard> tributeList = tribute(p, this.tributes, false, this);
		if (tributeList.size() > 0)
		{
			for (DuelistCard c : tributeList)
			{
				//if (c.baseDamage > 0) 	{ healTotal += c.baseDamage; 	}
				if (c.baseBlock  > 0) 	{ healTotal += c.baseBlock; 	}
			}
		}
		
		if (healTotal > 0) { heal(p, healTotal); }
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new AltarTribute();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
            if (DuelistMod.hasUpgradeBuffRelic) {  this.upgradeBaseCost(0); }
            else { this.upgradeBaseCost(1); }
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
	public void onTribute(DuelistCard tributingCard) {
		// TODO Auto-generated method stub
		
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