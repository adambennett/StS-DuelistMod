package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.*;
import duelistmod.powers.*;
import duelistmod.variables.*;

public class SwordsRevealing extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = DuelistMod.makeID("SwordsRevealing");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.SWORDS_REVEALING);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
	private static final int COST = 3;
	// /STAT DECLARATION/

	public SwordsRevealing() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.tags.add(Tags.SPELL);
		this.tags.add(Tags.LEGEND_BLUE_EYES);
		this.tags.add(Tags.NEVER_GENERATE);
		this.originalName = this.name;
		this.magicNumber = this.baseMagicNumber = 2;
		this.exhaust = true;
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		tribute(p, 0, true, this);
		applyPowerToSelf(new SwordsRevealPower(p, p, this.magicNumber + 1));
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new SwordsRevealing();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(1);
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}

	@Override
	public void onTribute(DuelistCard tributingCard) {
		// TODO Auto-generated method stub

	}

	public String failedCardSpecificCanUse(final AbstractPlayer p, final AbstractMonster m) { return "Requires 3+ tributes"; }

	public boolean cardSpecificCanUse(final AbstractPlayer p, final AbstractMonster m) {
		boolean mausoActive = (p.hasPower(EmperorPower.POWER_ID) && (!((EmperorPower)p.getPower(EmperorPower.POWER_ID)).flag));
		boolean atLeastOneTribute = (p.hasPower(SummonPower.POWER_ID) && (p.getPower(SummonPower.POWER_ID).amount) > 2);
		return mausoActive || atLeastOneTribute;
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
