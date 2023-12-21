package duelistmod.cards;

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
import duelistmod.orbs.Earth;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;

import java.util.List;

public class OjamaGreen extends DuelistCard {
	public static final String ID = DuelistMod.makeID("OjamaGreen");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.OJAMA_GREEN);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
	private static final int COST = 2;

    public OjamaGreen() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.tags.add(Tags.MONSTER);
		this.tags.add(Tags.OJAMA);
		this.tags.add(Tags.BEAST);
		this.tags.add(Tags.DARK_CRISIS);
		this.tags.add(Tags.REDUCED);
		this.showEvokeValue = true;
		this.showEvokeOrbCount = 1;
		this.exhaust = true;
		this.originalName = this.name;
		this.summons = this.baseSummons = 1;
		this.isSummon = true;
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
		int MIN_TURNS_ROLL = 1;
		int MAX_TURNS_ROLL = 5;
		int randomTurnNum = AbstractDungeon.cardRandomRng.random(MIN_TURNS_ROLL, MAX_TURNS_ROLL);
		if (duelist.player()) {
			applyRandomBuffPlayer(duelist.getPlayer(), randomTurnNum, false);
		} else if (duelist.getEnemy() != null) {
			applyRandomBuffToEnemyDuelist(duelist, randomTurnNum);
		}
		duelist.channel(new Earth());
		postDuelistUseCard(owner, targets);
	}

	@Override
	public AbstractCard makeCopy() {
		return new OjamaGreen();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBaseCost(1);
			this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
			this.initializeDescription();
		}
	}
}
