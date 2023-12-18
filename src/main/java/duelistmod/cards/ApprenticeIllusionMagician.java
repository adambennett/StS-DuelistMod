package duelistmod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
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
import duelistmod.powers.FocusUpPower;
import duelistmod.variables.Tags;

import java.util.List;

public class ApprenticeIllusionMagician extends DuelistCard {
	public static final String ID = DuelistMod.makeID("ApprenticeIllusionMagician");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makeCardPath("ApprenticeIllusionMagician.png");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
	private static final int COST = 2;

	public ApprenticeIllusionMagician() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.baseDamage = this.damage = 12;
		this.tributes = this.baseTributes = 3;
		this.magicNumber = this.baseMagicNumber = 2;
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.ORB_DECK);
		this.tags.add(Tags.SPELLCASTER);
		this.orbDeckCopies = 1;
		this.misc = 0;
		this.originalName = this.name;
		this.setupStartingCopies();
		this.exhaust = true;
		this.enemyIntent = AbstractMonster.Intent.ATTACK_BUFF;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) 
	{
		duelistUseCard(p, m);
	}

	@Override
	public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
		preDuelistUseCard(owner, targets);
		tribute();
		if (targets.size() > 0) {
			attack(targets.get(0), AFX, this.damage);
		}
		AnyDuelist duelist = AnyDuelist.from(this);
		duelist.applyPowerToSelf(new FocusUpPower(duelist.creature(), duelist.creature(),3, this.magicNumber));
		postDuelistUseCard(owner, targets);
	}

	@Override
	public AbstractCard makeCopy() {
		return new ApprenticeIllusionMagician();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeTributes(-1);
			this.upgradeDamage(2);
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.fixUpgradeDesc();
			this.initializeDescription();
		}
	}
}
