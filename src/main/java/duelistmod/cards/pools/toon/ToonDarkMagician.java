package duelistmod.cards.pools.toon;

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

public class ToonDarkMagician extends DuelistCard {

	public static final String ID = duelistmod.DuelistMod.makeID("ToonDarkMagician");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.TOON_DARK_MAGICIAN);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 2;

	public ToonDarkMagician() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.baseDamage = this.damage = 14;
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.REQUIRES_TOON_WORLD);
		this.tags.add(Tags.TOON);
		this.tags.add(Tags.SPELLCASTER);
		this.tags.add(Tags.FULL);
		this.misc = 0;
		this.originalName = this.name;
		this.tributes = this.baseTributes = 2;
		this.magicNumber = this.baseMagicNumber = 2;	// Arcana gain
		this.baseSecondMagic = this.secondMagic = 1;	// Summons check for draw card effect
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		duelistUseCard(p, m);
	}

	@Override
	public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
		preDuelistUseCard(owner, targets);
		tribute();
		if (targets.size() > 0) {
			attack(targets.get(0));
		}
		AnyDuelist duelist = AnyDuelist.from(this);
		duelist.applyPowerToSelf(new ArcanaPower(duelist.creature(), duelist.creature(), this.magicNumber));
		if (duelist.hasPower(SummonPower.POWER_ID) && duelist.getPower(SummonPower.POWER_ID).amount >= this.secondMagic) {
			duelist.draw(1);
		}
		postDuelistUseCard(owner, targets);
	}

	@Override
	public AbstractCard makeCopy() {
		return new ToonDarkMagician();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(4);
			this.upgradeMagicNumber(1);
			this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
			this.initializeDescription();
		}
	}

}
