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
import duelistmod.powers.duelistPowers.ArcanaPower;
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;
import java.util.List;

public class ToonDarkMagicianGirl extends DuelistCard {

	public static final String ID = duelistmod.DuelistMod.makeID("ToonDarkMagicianGirl");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.TOON_DARK_MAGICIAN_GIRL);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 2;

	public ToonDarkMagicianGirl() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.REQUIRES_TOON_WORLD);
		this.tags.add(Tags.TOON);
		this.tags.add(Tags.SPELLCASTER);
		this.originalName = this.name;
		this.summons = this.baseSummons = 2;
		this.isSummon = true;
		this.damage = this.baseDamage = 10;
		this.magicNumber = this.baseMagicNumber = 6;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		duelistUseCard(p, m);
	}

	@Override
	public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
		preDuelistUseCard(owner, targets);
		AnyDuelist duelist = AnyDuelist.from(this);
		boolean allSpellcasters = this.magicNumber > 0  && allSpellcastersSummoned();
		summon();
		if (targets.size() > 0) {
			attack(targets.get(0));
		}
		if (allSpellcasters) {
			duelist.applyPowerToSelf(new ArcanaPower(duelist.creature(), duelist.creature(), this.magicNumber));
		}
		postDuelistUseCard(owner, targets);
	}

	@Override
	public void triggerOnGlowCheck() {
		super.triggerOnGlowCheck();
		if (allSpellcastersSummoned()) {
			this.glowColor = Color.GOLD;
		}
	}

	private boolean allSpellcastersSummoned() {
		AnyDuelist duelist = AnyDuelist.from(this);
		boolean allSpellcasters = true;
		if (duelist.hasPower(SummonPower.POWER_ID)) {
			SummonPower power = (SummonPower) duelist.getPower(SummonPower.POWER_ID);
			if (power.getCardsSummoned().stream().anyMatch((card) -> !card.hasTag(Tags.SPELLCASTER) && card.hasTag(Tags.MONSTER))) {
				allSpellcasters = false;
			}
		}
		return allSpellcasters;
	}

	@Override
	public AbstractCard makeCopy() {
		return new ToonDarkMagicianGirl();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(2);
			this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
			this.initializeDescription();
		}
	}

}
