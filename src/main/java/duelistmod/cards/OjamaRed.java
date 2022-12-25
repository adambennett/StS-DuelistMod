package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.orbs.Black;
import duelistmod.patches.*;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class OjamaRed extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = DuelistMod.makeID("OjamaRed");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makeCardPath("OjamaRed.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 2;
	private static int MIN_TURNS_ROLL = 1;
	private static int MAX_TURNS_ROLL = 5;
	// /STAT DECLARATION/

	public OjamaRed() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.OJAMA);
		this.tags.add(Tags.ARCANE);
		this.originalName = this.name;
		this.tributes = this.baseTributes = 4;
		this.magicNumber = this.baseMagicNumber = 2;
		this.showEvokeValue = true;
		this.showEvokeOrbCount = 1;
		this.setupStartingCopies();
	}


	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		// Summon
		tribute(p, this.tributes, false, this);
		for (int i = 0; i < this.magicNumber; i++)
		{
			int randomTurnNum = AbstractDungeon.cardRandomRng.random(MIN_TURNS_ROLL, MAX_TURNS_ROLL);
			applyRandomBuffPlayer(p, randomTurnNum, false);
		}
		AbstractOrb earth = new Black();
		channel(earth);
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new OjamaRed();
	}

	// Upgraded stats.
	@Override
	public void upgrade() 
	{
		if (this.canUpgrade())
		{
			if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
			if (this.timesUpgraded == 1) { this.upgradeBaseCost(1); }
			else if (this.tributes > 0) { this.upgradeTributes(-2); }
			else { this.upgradeMagicNumber(1); }
			this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
			this.initializeDescription();
		}
	}

	@Override
	public boolean canUpgrade()
	{
		if (this.magicNumber <= 9) { return true; }
		else { return false; }
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
