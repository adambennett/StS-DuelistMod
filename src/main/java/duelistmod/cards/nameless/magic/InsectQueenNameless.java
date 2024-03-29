package duelistmod.cards.nameless.magic;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.interfaces.NamelessTombCard;
import duelistmod.cards.pools.insects.InsectQueen;
import duelistmod.patches.*;
import duelistmod.variables.*;

public class InsectQueenNameless extends DuelistCard implements NamelessTombCard
{
	// TEXT DECLARATION
	public static final String ID = duelistmod.DuelistMod.makeID("Nameless:Magic:InsectQueen");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.INSECT_QUEEN);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPECIAL;
	private static final int COST = 2;
	// /STAT DECLARATION/

	public InsectQueenNameless() 
	{
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.magicNumber = this.baseMagicNumber = 2;
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.FORCE_TRIB_FOR_RESUMMONS);
		this.tags.add(Tags.INSECT);
		this.tags.add(Tags.X_COST);
		this.tags.add(Tags.GOOD_TRIB);
		this.misc = 0;
		this.originalName = this.name;
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		// Tribute all
		int playerSummons = xCostTribute();

		// Apply poison to all enemies
		poisonAllEnemies(p, playerSummons * 5);
		
		// If unupgraded, reduce max summons by 1.
		decMaxSummons(p, this.magicNumber);

	}

	@Override
	public DuelistCard getStandardVersion() { return new InsectQueen(); }

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new InsectQueenNameless();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();			
			this.upgradeMagicNumber(-1);
			this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
			this.initializeDescription();
		}
	}















}
