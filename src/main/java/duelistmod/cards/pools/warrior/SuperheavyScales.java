package duelistmod.cards.pools.warrior;

import com.megacrit.cardcrawl.actions.utility.ExhaustToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.AbstractStance;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.variables.*;

public class SuperheavyScales extends DuelistCard 
{
	// TEXT DECLARATION

	public static final String ID = DuelistMod.makeID("SuperheavyScales");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.SUPERHEAVY_SCALES);
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
	private static final int BLOCK = 5;
	private static final int SUMMONS = 1;
	// /STAT DECLARATION/

	public SuperheavyScales() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.baseBlock = this.block = BLOCK;
		this.magicNumber = this.baseMagicNumber = 2;
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.SUPERHEAVY);
		this.tags.add(Tags.GOOD_TRIB);
		this.originalName = this.name;
		this.summons = this.baseSummons = SUMMONS;
		this.isSummon = true;
		this.exhaust = true;
	}
	
    @Override
    public void triggerExhaustedCardsOnStanceChange(final AbstractStance newStance) {
        this.addToBot(new ExhaustToHandAction(this));
    }

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		summon(p,  this.summons, this);
		for (int i = 0; i < this.magicNumber; i++) { block(this.block); }
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new SuperheavyScales();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBaseCost(0);
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		superSynTrib(tributingCard);
	}

	@Override
	public void onResummon(int summons) 
	{
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
