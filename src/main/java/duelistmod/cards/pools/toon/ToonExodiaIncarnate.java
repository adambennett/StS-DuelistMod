package duelistmod.cards.pools.toon;

import com.badlogic.gdx.graphics.Color;
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
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;
import java.util.List;

public class ToonExodiaIncarnate extends DuelistCard {

	public static final String ID = DuelistMod.makeID("ToonExodiaIncarnate");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makeCardPath("ToonExodiaIncarnate.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 0;

	public ToonExodiaIncarnate() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.SPELLCASTER);
		this.tags.add(Tags.TOON);
		this.tags.add(Tags.REQUIRES_TOON_WORLD);
		this.tags.add(Tags.EXODIA);
		this.tags.add(Tags.BAD_MAGIC);
		this.baseSummons = this.summons = 1;
		this.isSummon = true;
		this.baseMagicNumber = this.magicNumber = 4;
		this.baseBlock = this.block = 20;
		this.originalName = this.name;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		duelistUseCard(p, m);
	}

	@Override
	public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
		preDuelistUseCard(owner, targets);
		boolean trigger = isTriggering();
		summon();
		if (trigger) {
			block();
		}
		postDuelistUseCard(owner, targets);
	}

	@Override
	public void triggerOnGlowCheck() {
		super.triggerOnGlowCheck();
		if (isTriggering()) {
			this.glowColor = Color.GOLD;
		}
	}

	private boolean isTriggering() {
		AnyDuelist duelist = AnyDuelist.from(this);
		if (duelist.hasPower(SummonPower.POWER_ID)) {
			SummonPower sp = (SummonPower) duelist.getPower(SummonPower.POWER_ID);
			int spellcasters = sp.getNumberOfTypeSummoned(Tags.SPELLCASTER);
			return spellcasters >= this.magicNumber;
		}
		return false;
	}

	@Override
	public AbstractCard makeCopy() {
		return new ToonExodiaIncarnate();
	}

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
