package duelistmod.cards.pools.zombies;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.*;
import duelistmod.variables.Tags;

public class Relinkuriboh extends DuelistCard 
{
	// TEXT DECLARATION

	public static final String ID = DuelistMod.makeID("Relinkuriboh");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makeCardPath("Relinkuriboh.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 1;
	// /STAT DECLARATION/

	public Relinkuriboh() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.FIEND);
		this.tags.add(Tags.KURIBOH);
		this.tags.add(Tags.INCREMENT_DECK);
		this.incrementDeckCopies = 1;
		this.magicNumber = this.baseMagicNumber = 2;
		this.damage = this.baseDamage = 11;
		this.originalName = this.name;
		this.tributes = this.baseTributes = 2;
		this.isSummon = true;
		this.setupStartingCopies();
		
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		tribute();
		incMaxSummons(p, this.magicNumber);		
		attack(m);
	}

	// Which card to return when making a copy of this card.
	@Override
	public AbstractCard makeCopy() {
		return new Relinkuriboh();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeTributes(-1);
			this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
			this.initializeDescription();
		}
	}





	




	
	




}
