package defaultmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import defaultmod.DefaultMod;
import defaultmod.patches.*;

public class BlastJuggler extends DuelistCard 
{
	// TEXT DECLARATION
	public static final String ID = defaultmod.DefaultMod.makeID("BlastJuggler");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DefaultMod.makePath(DefaultMod.BLAST_JUGGLER);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
	private static final int COST = 1;
	private static final int SUMMONS = 1;
	private static int MIN_TURNS_ROLL = 3;
	private static int MAX_TURNS_ROLL = 7;
	private static int DEBUFFS = 3;
	// /STAT DECLARATION/

	public BlastJuggler() 
	{
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.tags.add(DefaultMod.MONSTER);
		this.tags.add(DefaultMod.METAL_RAIDERS);
		this.originalName = this.name;
		this.summons = SUMMONS;
		this.isSummon = true;
	}


	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		summon(p, SUMMONS, this);
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() 
	{
		return new BlastJuggler();
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

		// For each debuff to apply, apply a random debuff with a new random turn number
		for (int i = 0; i < DEBUFFS; i++)
		{
			AbstractMonster targetMonster = AbstractDungeon.getRandomMonster();
			int randomTurnNum = AbstractDungeon.cardRandomRng.random(MIN_TURNS_ROLL, MAX_TURNS_ROLL);
			applyPower(getRandomDebuff(AbstractDungeon.player, targetMonster, randomTurnNum), targetMonster);
		}
	}


	@Override
	public void onSummon(int summons)
	{

	}


	@Override
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		AbstractPlayer p = AbstractDungeon.player;
		summon(p, summons, this);
	}


	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		AbstractPlayer p = AbstractDungeon.player;
		summon(p, summons, this);
	}
}