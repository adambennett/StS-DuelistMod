package duelistmod.cards;

import java.util.ArrayList;

import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.actions.common.*;
import duelistmod.interfaces.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;

public class Tierra extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = DuelistMod.makeID("Tierra");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makeCardPath("Tierra.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 0;
	// /STAT DECLARATION/

	public Tierra() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.tributes = this.baseTributes = 2;
		this.magicNumber = this.baseMagicNumber = 2;
		this.damage = this.baseDamage = 30;
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.FIEND);
		this.misc = 0;
		this.originalName = this.name;
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		tribute();
		attack(m);
		ArrayList<DuelistCard> handTribs = new ArrayList<DuelistCard>();
		for (AbstractCard c : p.hand.group)
		{
			if (c instanceof DuelistCard && c.hasTag(Tags.MONSTER) && !c.uuid.equals(this.uuid))
			{
				DuelistCard dC = (DuelistCard)c;
				if (dC.tributes > 0)
				{
					handTribs.add(dC);
				}
			}
		}
		
		for (AbstractCard c : p.drawPile.group)
		{
			if (c instanceof DuelistCard && c.hasTag(Tags.MONSTER) && !c.uuid.equals(this.uuid))
			{
				DuelistCard dC = (DuelistCard)c;
				if (dC.tributes > 0)
				{
					handTribs.add(dC);
				}
			}
		}
		
		for (AbstractCard c : p.discardPile.group)
		{
			if (c instanceof DuelistCard && c.hasTag(Tags.MONSTER) && !c.uuid.equals(this.uuid))
			{
				DuelistCard dC = (DuelistCard)c;
				if (dC.tributes > 0)
				{
					handTribs.add(dC);
				}
			}
		}
		
		for (AbstractCard c : p.exhaustPile.group)
		{
			if (c instanceof DuelistCard && c.hasTag(Tags.MONSTER) && !c.uuid.equals(this.uuid))
			{
				DuelistCard dC = (DuelistCard)c;
				if (dC.tributes > 0)
				{
					handTribs.add(dC);
				}
			}
		}
		
		if (handTribs.size() > 0)
		{
			for (DuelistCard pick : handTribs)
			{
				AbstractDungeon.actionManager.addToTop(new ModifyTributeAction(pick, this.magicNumber, true));
			}
		}
	}
	
	@Override
    public void atTurnStart() 
    {
    	
    }

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new Tierra();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(-1);
			this.upgradeDamage(5);
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
	public void onTribute(DuelistCard tributingCard)
	{
		if (tributingCard.hasTag(Tags.FIEND))
		{
			AbstractDungeon.actionManager.addToBottom(new FetchAction(AbstractDungeon.player.discardPile, 1));
		}
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