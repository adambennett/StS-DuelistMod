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
import duelistmod.powers.duelistPowers.SkilledBrownMagicianPower;
import duelistmod.variables.Tags;

import java.util.List;

public class SkilledBrownMagician extends DuelistCard {
	public static final String ID = DuelistMod.makeID("SkilledBrownMagician");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makeCardPath("SkilledBrownMagician.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 2;

	public SkilledBrownMagician() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.magicNumber = this.baseMagicNumber = 2;
		this.damage = this.baseDamage = 12;
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.SPELLCASTER);
		this.originalName = this.name;
		this.summons = this.baseSummons = 1;
		this.enemyIntent = AbstractMonster.Intent.ATTACK;
		this.exhaust = true;
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
		if (targets.size() > 0) {
			attack(targets.get(0));
		}
		duelist.applyPowerToSelf(new SkilledBrownMagicianPower(duelist.creature(), duelist.creature(), this.magicNumber));
		postDuelistUseCard(owner, targets);
	}

	@Override
	public int incrementGeneratedIfPlayed() {
		return this.magicNumber;
	}

	@Override
	public AbstractCard makeCopy() {
		return new SkilledBrownMagician();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(1);
			this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
			this.initializeDescription();
		}
	}
}
