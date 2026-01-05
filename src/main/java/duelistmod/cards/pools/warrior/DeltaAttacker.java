package duelistmod.cards.pools.warrior;

import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.GuardedDuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.variables.Tags;

import java.util.List;

public class DeltaAttacker extends GuardedDuelistCard {

    public static final String ID = DuelistMod.makeID("DeltaAttacker");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("DeltaAttacker.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 3;

    public DeltaAttacker() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 3;
        this.baseBlock = this.block = 3;
        this.setBaseGuardedCheck(10);
        this.setGuardedCheck(10);
        this.isMultiDamage = true;
        this.tags.add(Tags.SPELL);
        this.originalName = this.name;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        AnyDuelist duelist = AnyDuelist.from(this);

        int summons = 0;
        if (duelist.hasPower(SummonPower.POWER_ID)) {
            SummonPower pow = (SummonPower) duelist.getPower(SummonPower.POWER_ID);
            summons = pow.getNumberOfTypeSummoned(Tags.WARRIOR);
        }
        int repeats = 1 + Math.max(0, summons);
        boolean guarded = isGuardedActive(this, this.getGuardedCheck());

        for (int i = 0; i < repeats; i++) {
            if (duelist.player()) {
                this.addToBot(new DamageAllEnemiesAction(owner, this.multiDamage, DamageInfo.DamageType.NORMAL, this.baseAFX));
            } else if (duelist.getEnemy() != null) {
                attack(AbstractDungeon.player);
            }
            if (guarded) {
                triggerGuarded(duelist, targets);
            }
        }

        postDuelistUseCard(owner, targets);
    }

    @Override
    public void onGuardedTriggered(AnyDuelist duelist, List<AbstractCreature> targets) {
        duelist.block(this.block);
    }

    @Override
    public AbstractCard makeCopy() {
        return new DeltaAttacker();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeGuardedCheck(-4);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
