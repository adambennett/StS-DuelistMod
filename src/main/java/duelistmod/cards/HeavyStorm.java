package duelistmod.cards;

import java.util.ArrayList;
import java.util.List;
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
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;

public class HeavyStorm extends DuelistCard {

    public static final String ID = DuelistMod.makeID("HeavyStorm");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.HEAVY_STORM);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;

    public HeavyStorm() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.ALL);
        this.tags.add(Tags.METAL_RAIDERS);
        this.originalName = this.name;
        this.baseDamage = this.damage = 4;
    }

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		duelistUseCard(p, m);
	}

	@Override
	public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
		preDuelistUseCard(owner, targets);
		int totalSummons = getSummonsToRemove();
		if (targets.size() > 0 && totalSummons > 0) {
			AnyDuelist duelist = AnyDuelist.from(this);
			for (int i = 0; i < totalSummons; i++) {
				AbstractCreature mon = duelist.player() ? AbstractDungeon.getRandomMonster() : duelist.getEnemy() != null ? AbstractDungeon.player : null;
				if (mon != null) {
					attack(mon);
				}
			}
		}
		postDuelistUseCard(owner, targets);
	}

	private int getSummonsToRemove() {
		AnyDuelist duelist = AnyDuelist.from(this);
		int totalSummons = 0;
		if (duelist.player()) {
			if (duelist.hasPower(SummonPower.POWER_ID)) {
				SummonPower summonsInstance = (SummonPower) duelist.getPower(SummonPower.POWER_ID);
				totalSummons += summonsInstance.amount;
                summonsInstance.setCardsSummoned(new ArrayList<>());
            }
			for (AbstractMonster mon : AbstractDungeon.getCurrRoom().monsters.monsters) {
				if (mon.hasPower(SummonPower.POWER_ID)) {
					SummonPower enemyInstance = (SummonPower) mon.getPower(SummonPower.POWER_ID);
					totalSummons += enemyInstance.amount;
                    enemyInstance.setCardsSummoned(new ArrayList<>());
                }
			}
		}
		if (duelist.getEnemy() != null) {
			if (duelist.hasPower(SummonPower.POWER_ID)) {
				SummonPower summonsInstance = (SummonPower) duelist.getPower(SummonPower.POWER_ID);
				totalSummons += summonsInstance.amount;
                summonsInstance.setCardsSummoned(new ArrayList<>());
            }
			if (AbstractDungeon.player.hasPower(SummonPower.POWER_ID)) {
				SummonPower enemyInstance = (SummonPower)AbstractDungeon.player.getPower(SummonPower.POWER_ID);
				totalSummons += enemyInstance.amount;
                enemyInstance.setCardsSummoned(new ArrayList<>());
            }
		}
		return totalSummons;
	}

    @Override
    public AbstractCard makeCopy() {
        return new HeavyStorm();
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
