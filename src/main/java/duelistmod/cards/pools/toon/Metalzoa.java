package duelistmod.cards.pools.toon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.interfaces.RevengeCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;

public class Metalzoa extends DuelistCard {

	public static final String ID = DuelistMod.makeID("Metalzoa");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makeCardPath("Metalzoa.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 2;

	public Metalzoa() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.FIEND);
		this.tags.add(Tags.MACHINE);
		this.tags.add(Tags.ZOA);
		this.misc = 0;
		this.originalName = this.name;
		this.baseSummons = this.summons = 2;
		this.tributes = this.baseTributes = 1;
		this.baseDamage = this.damage = 15;
		this.isTribute = true;
		this.isSummon = true;
		this.baseMagicNumber = this.magicNumber = 2;	// Plated Armor gain per Zoa tributed
		this.baseSecondMagic = this.secondMagic = 5;	// Metalmorph plated armor gain
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		duelistUseCard(p, m);
	}

	@Override
	public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
		preDuelistUseCard(owner, targets);
		ArrayList<DuelistCard> tributes = tribute();
		summon();
		if (targets.size() > 0) {
			attack(targets.get(0));
		}
		long zoaTributes = tributes.stream().filter(c -> c.hasTag(Tags.ZOA)).count();
		AnyDuelist duelist = AnyDuelist.from(this);
		boolean hasMetalmorph = duelist.hand().stream().anyMatch(c -> c instanceof Metalmorph);
		int platedArmorGain = (int) (zoaTributes * this.magicNumber);
		if (hasMetalmorph) {
			platedArmorGain += this.secondMagic;
		}
		if (platedArmorGain > 0) {
			duelist.applyPowerToSelf(new PlatedArmorPower(duelist.creature(), platedArmorGain));
		}
		postDuelistUseCard(owner, targets);
	}

	@Override
	public AbstractCard makeCopy() {
		return new Metalzoa();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(3);
			this.upgradeMagicNumber(2);
			this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
			this.initializeDescription();
		}
	}

}
