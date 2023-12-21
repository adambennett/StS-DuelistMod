package duelistmod.cards.pools.aqua;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;

import java.util.List;

public class IslandTurtle extends DuelistCard {
	public static final String ID = DuelistMod.makeID("IslandTurtle");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.ISLAND_TURTLE);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 1;

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
		this.enemyIntent = AbstractMonster.Intent.DEFEND;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		duelistUseCard(p, m);
	}

	@Override
	public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
		incMaxSummons(this.magicNumber);
		summon();
		block();
		postDuelistUseCard(owner, targets);
	}

	@Override
	public int addToMaxSummonsDuringSummonZoneChecks() {
		return this.magicNumber;
	}

	@Override
	public AbstractCard makeCopy() {
		return new IslandTurtle();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(1);
			this.upgradeBlock(2);
			this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
			this.initializeDescription();
		}
	}
}
