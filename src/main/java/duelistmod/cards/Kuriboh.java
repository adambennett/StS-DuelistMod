package duelistmod.cards;

import java.util.List;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistCardLibrary;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tokens.KuribohToken;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;

public class Kuriboh extends DuelistCard {

	public static final String ID = DuelistMod.makeID("Kuriboh");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.KURIBOH);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 1;

	public Kuriboh() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.magicNumber = this.baseMagicNumber = 1;
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.FIEND);
		this.tags.add(Tags.METAL_RAIDERS);
		this.tags.add(Tags.KURIBOH);
		this.originalName = this.name;
		this.summons = this.baseSummons = 1;
		this.isSummon = true;
		this.exhaust = true;
		this.cardsToPreview = new KuribohToken();
		this.enemyIntent = AbstractMonster.Intent.BUFF;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		duelistUseCard(p, m);
	}

	@Override
	public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
		if (upgraded) {
			incMaxSummons(owner, this.magicNumber);
		}
		DuelistCard tok = DuelistCardLibrary.getTokenInCombat(new KuribohToken());
		summon(owner, this.summons, tok);
	}

	@Override
	public int addToMaxSummonsDuringSummonZoneChecks() {
		return this.upgraded ? this.magicNumber : 0;
	}

	@Override
	public int incrementGeneratedIfPlayed() { return this.upgraded ? this.magicNumber : 0; }

	@Override
	public AbstractCard makeCopy() {
		return new Kuriboh();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
			this.initializeDescription();
		}
	}
}
