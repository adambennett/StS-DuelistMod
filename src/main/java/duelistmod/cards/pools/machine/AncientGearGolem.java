package duelistmod.cards.pools.machine;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tokens.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class AncientGearGolem extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = DuelistMod.makeID("AncientGearGolem");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makeCardPath("AncientGearGolem.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 1;
	// /STAT DECLARATION/

	public AncientGearGolem() 
	{
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.GOOD_TRIB);
		this.tags.add(Tags.MACHINE);
		this.tags.add(Tags.ANCIENT_FOR_PIXIE);
		this.tags.add(Tags.ANCIENT_FOR_MACHINE);
		this.originalName = this.name;
		this.tributes = this.baseTributes = 3;
		this.isSummon = true;
		this.magicNumber = this.baseMagicNumber = 5;
		this.secondMagic = this.baseSecondMagic = 3;
		this.baseAFX = AttackEffect.FIRE;
		this.cardsToPreview = new Token();
	}


	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		tribute();
		applyPowerToSelf(new MetallicizePower(p, this.magicNumber));
		for (int i = 0; i < this.secondMagic; i++)
		{
			DuelistCard tok = DuelistCardLibrary.getTokenInCombat(new Token());
			addCardToHand(tok);
		}		
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() 
	{
		return new AncientGearGolem();
	}

	// Upgraded stats.
	@Override
	public void upgrade() 
	{
		if (!this.upgraded) 
		{
			this.upgradeName();
			this.upgradeBaseCost(0);
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
	



	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		machineSynTrib(tributingCard);
	}


	@Override
	public void onResummon(int summons)
	{

	}


	@Override
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		
	}


	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		
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
