package duelistmod.cards.pools.increment;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.List;

public class PerformapalKuribohble extends DuelistCard {
	public static final String ID = DuelistMod.makeID("PerformapalKuribohble");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makeCardPath("PerformapalKuribohble.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 3;

	public PerformapalKuribohble() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.magicNumber = this.baseMagicNumber = 5;
		this.baseSecondMagic = this.secondMagic = 150;
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.KURIBOH);
		this.tags.add(Tags.FIEND);
		this.tags.add(Tags.NEVER_GENERATE);
		this.tags.add(Tags.EXEMPT);
		this.originalName = this.name;
		this.baseSummons = this.summons = 1;
		this.makeFleeting();
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		duelistUseCard(p, m);
	}

	@Override
	public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
		summon();
		AnyDuelist duelist = AnyDuelist.from(this);
		incMaxSummons(this.magicNumber, duelist);
		if (duelist.player()) {
			DuelistCard.gainGold(this.secondMagic, duelist.creature(), true);
		}
		postDuelistUseCard(owner, targets);
	}

	@Override
	public int incrementGeneratedIfPlayed() {
		return this.magicNumber;
	}

	@Override
	public AbstractCard makeCopy() {
		return new PerformapalKuribohble();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeSecondMagic(50);
			this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
			this.initializeDescription();
		}
	}
}
