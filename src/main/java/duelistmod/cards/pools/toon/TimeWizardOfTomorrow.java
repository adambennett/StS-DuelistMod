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
import duelistmod.powers.duelistPowers.TimeWizardOfTomorrowPower;
import duelistmod.variables.Tags;
import java.util.List;

public class TimeWizardOfTomorrow extends DuelistCard {

    public static final String ID = DuelistMod.makeID("TimeWizardOfTomorrow");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("TimeWizardOfTomorrow.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    private int enemyDamage = 8;
    private int selfDamage = 8;

    public TimeWizardOfTomorrow() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.summons = this.baseSummons = 3;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.SPELLCASTER);
        this.tags.add(Tags.FULL);
		this.originalName = this.name;
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
        if (!duelist.hasPower(TimeWizardOfTomorrowPower.POWER_ID)) {
            duelist.applyPowerToSelf(new TimeWizardOfTomorrowPower(duelist.creature(), duelist.creature(), this.selfDamage, this.enemyDamage));
        } else {
            TimeWizardOfTomorrowPower power = (TimeWizardOfTomorrowPower) duelist.getPower(TimeWizardOfTomorrowPower.POWER_ID);
            if (power.getSelfDamage() > this.selfDamage || power.getEnemyDamage() < this.enemyDamage) {
                power.setSelfDamage(this.selfDamage);
                power.setEnemyDamage(this.enemyDamage);
                if (power.getEnemyDamage() != power.getSelfDamage()) {
                    power.amount = power.getEnemyDamage();
                    power.amount2 = power.getSelfDamage();
                }
                power.updateDescription();
            }
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
        return new TimeWizardOfTomorrow();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.enemyDamage = 10;
            this.selfDamage = 4;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard card = super.makeStatEquivalentCopy();
        if (card instanceof TimeWizardOfTomorrow) {
            TimeWizardOfTomorrow token = (TimeWizardOfTomorrow) card;
            token.enemyDamage = this.enemyDamage;
            token.selfDamage = this.selfDamage;
            return token;
        }
        return card;
    }

}
