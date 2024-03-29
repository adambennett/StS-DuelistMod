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
import duelistmod.powers.duelistPowers.KuriphotonPower;
import duelistmod.variables.Tags;

import java.util.List;

public class Kuriphoton extends DuelistCard {
	public static final String ID = DuelistMod.makeID("Kuriphoton");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makeCardPath("Kuriphoton.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 3;

	public Kuriphoton() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.magicNumber = this.baseMagicNumber = 1;
		this.damage = this.baseDamage = 16;
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.KURIBOH);
		this.tags.add(Tags.FIEND);
		this.originalName = this.name;
		this.tributes = this.baseTributes = 3;
		this.enemyIntent = AbstractMonster.Intent.ATTACK;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		duelistUseCard(p, m);
	}

	@Override
	public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
		tribute();
		AnyDuelist duelist = AnyDuelist.from(this);
		if (targets.size() > 0) {
			attack(targets.get(0));
		}
		duelist.applyPowerToSelf(new KuriphotonPower(duelist.creature(), duelist.creature(), this.magicNumber));
		postDuelistUseCard(owner, targets);
	}

	@Override
	public int incrementGeneratedIfPlayed() {
		return this.magicNumber;
	}

	@Override
	public AbstractCard makeCopy() {
		return new Kuriphoton();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(4);
			this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
			this.initializeDescription();
		}
	}
}
