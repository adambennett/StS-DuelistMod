package defaultmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.actions.common.*;
import defaultmod.patches.*;

public class OjamaYellow extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = defaultmod.DefaultMod.makeID("OjamaYellow");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DefaultMod.makePath(DefaultMod.OJAMA_YELLOW);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 1;
	private static final int SUMMONS = 1;
	private static final int CARDS = 2;
	// /STAT DECLARATION/

	public OjamaYellow() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.baseMagicNumber = this.magicNumber = CARDS;
		this.tags.add(DefaultMod.MONSTER);
		this.tags.add(DefaultMod.OJAMA);
		this.tags.add(DefaultMod.INVASION_CHAOS);
		this.tags.add(DefaultMod.OJAMA_DECK);
		this.startingOjamaDeckCopies = 2;
		this.originalName = this.name;
		this.exhaust = true;
		this.summons = this.baseSummons = SUMMONS;
		this.isSummon = true;
		this.setupStartingCopies();
	}


	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		summon(p, this.summons, this);
		// Add random cards to hand
		for (int i = 0; i < this.magicNumber; i++)
		{
			DuelistCard randomMonster = (DuelistCard) returnTrulyRandomFromSet(DefaultMod.MONSTER);
			if (upgraded) { AbstractDungeon.actionManager.addToTop(new RandomizedAction(randomMonster, this.upgraded, true, false, true, true, true, true, true, 1, 4, 0, 2, 0, 2)); }
			else { AbstractDungeon.actionManager.addToTop(new RandomizedAction(randomMonster, this.upgraded, true, false, true, true, true, false, false, 1, 4, 0, 1, 0, 1)); }
		}
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new OjamaYellow();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			//this.upgradeMagicNumber(1);
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
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