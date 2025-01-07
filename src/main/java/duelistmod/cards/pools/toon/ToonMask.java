package duelistmod.cards.pools.toon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;
import java.util.ArrayList;
import java.util.List;

public class ToonMask extends DuelistCard {

	public static final String ID = DuelistMod.makeID("ToonMask");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String IMG = DuelistMod.makePath(Strings.TOON_MASK);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
	private static final int COST = 2;
	private static final int MIN_DMG = 8;
	private static final int MAX_DMG = 18;
	private static final int MIN_DMG_U = 10;
	private static final int MAX_DMG_U = 24;

	public ToonMask() {
		super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
		this.isMultiDamage = true;
		this.tags.add(Tags.TOON);
		this.tags.add(Tags.TRAP);
		this.tags.add(Tags.FULL);
		this.misc = 0;
		this.originalName = this.name;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		duelistUseCard(p, m);
	}

	@Override
	public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
		preDuelistUseCard(owner, targets);
		if (targets.size() > 0) {
			AnyDuelist duelist = AnyDuelist.from(this);
			int randomDamage = AbstractDungeon.cardRandomRng.random(this.upgraded ? MIN_DMG_U : MIN_DMG, this.upgraded ? MAX_DMG_U : MAX_DMG);
			if (duelist.player()) {
				ArrayList<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
				for (AbstractMonster g : monsters) {
					if (!g.isDead && !g.isDying && !g.isDeadOrEscaped() && !g.halfDead) {
						this.addToBot(new DamageAction(g, new DamageInfo(duelist.creature(), randomDamage, DamageInfo.DamageType.NORMAL),AbstractGameAction.AttackEffect.FIRE));
					}
				}
			} else if (duelist.getEnemy() != null) {
				this.addToBot(new DamageAction(targets.get(0), new DamageInfo(duelist.creature(), randomDamage, DamageInfo.DamageType.NORMAL),AbstractGameAction.AttackEffect.FIRE));
			}
		}
		postDuelistUseCard(owner, targets);
	}

	@Override
	public AbstractCard makeCopy() {
		return new ToonMask();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
			this.initializeDescription();
		}
	}

}
