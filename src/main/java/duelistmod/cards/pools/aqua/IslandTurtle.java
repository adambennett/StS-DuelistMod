package duelistmod.cards.pools.aqua;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.variables.*;

public class IslandTurtle extends DuelistCard 
{
	// TEXT DECLARATION

	public static final String ID = DuelistMod.makeID("IslandTurtle");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.ISLAND_TURTLE);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 1;
	// /STAT DECLARATION/

	public IslandTurtle() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);		
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.PHARAOH_SERVANT);
		this.tags.add(Tags.AQUA);
		this.tags.add(Tags.AQUA_DECK);
		this.aquaDeckCopies = 2;
		this.summons = this.baseSummons = 1;
		this.magicNumber = this.baseMagicNumber = 1;
		this.baseBlock = this.block = 7;
		this.originalName = this.name;
		this.isSummon = true;
		this.setupStartingCopies();
		
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		incMaxSummons(this.magicNumber);
		summon(p, this.summons, this);
		block(this.block);
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new IslandTurtle();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(1);
			this.upgradeBlock(2);
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}

	@Override
	public void onTribute(DuelistCard tributingCard) 
	{
		aquaSynTrib(tributingCard);
	}


	@Override
	public void onResummon(int summons) {
		// TODO Auto-generated method stub

	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var) 
	{
		AbstractPlayer p = AbstractDungeon.player;
		summon(p, this.summons, this);
		block(this.block);
	}

	@Override
	public void summonThis(int summons, DuelistCard c, int var, AbstractMonster m) {
		AbstractPlayer p = AbstractDungeon.player;
		summon(p, this.summons, this);
		block(this.block);

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
