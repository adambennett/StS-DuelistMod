package duelistmod.cards.pools.increment;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.orbs.Lava;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.List;
import java.util.stream.Collectors;

public class FiveStarTwilight extends DuelistCard {
	public static final String ID = DuelistMod.makeID("FiveStarTwilight");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makeCardPath("FiveStarTwilight.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.ALL;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
	private static final int COST = 5;

	public FiveStarTwilight() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.damage = this.baseDamage = 3;
		this.tags.add(Tags.SPELL);
		this.tags.add(Tags.X_COST);
		this.originalName = this.name;
		this.enemyIntent = AbstractMonster.Intent.ATTACK;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		duelistUseCard(p, m);
	}

	@Override
	public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
		AnyDuelist duelist = AnyDuelist.from(this);
		int x = xCostTribute();
		if (x > 0) {
			incMaxSummons(x);
			duelist.block(x * 2);
		}
		duelist.channel(new Lava());
		for (int i = 0; i < x; i++) {
			List<AbstractMonster> livingMons = AbstractDungeon.getMonsters().monsters.stream().filter(mon -> mon != null && !mon.isDying && !mon.isDead && !mon.isDeadOrEscaped() && !mon.isEscaping && !mon.halfDead).collect(Collectors.toList());
			if (!livingMons.isEmpty()) {
				attack(livingMons.get(AbstractDungeon.cardRandomRng.random(livingMons.size() - 1)));
			}
		}
	}

	@Override
	public int incrementGeneratedIfPlayed() {
		return this.magicNumber;
	}

	@Override
	public AbstractCard makeCopy() {
		return new FiveStarTwilight();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(2);
			this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
			this.initializeDescription();
		}
	}
}
