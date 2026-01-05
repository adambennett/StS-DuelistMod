package duelistmod.cards.pools.oldWarrior;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.GuardedDuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.warrior.*;
import duelistmod.variables.Tags;

import java.util.List;

public class ElectromagneticShield extends GuardedDuelistCard {

    public static final String ID = DuelistMod.makeID("ElectromagneticShield");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ElectromagneticShield.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;

    public ElectromagneticShield() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseMagicNumber = this.magicNumber = 1;    // plated armor gain per magnet
        this.setBaseGuardedCheck(12);
        this.setGuardedCheck(12);
        this.baseSecondMagic = this.secondMagic = 1;      // dex gain on guarded
        this.baseTributes = this.tributes = 1;
        this.tags.add(Tags.SPELL);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        tribute();
        AnyDuelist duelist = AnyDuelist.from(this);
        int magnets = 0;
        if (duelist.hasPower(AlphaMagnetPower.POWER_ID)) magnets++;
        if (duelist.hasPower(BetaMagnetPower.POWER_ID)) magnets++;
        if (duelist.hasPower(GammaMagnetPower.POWER_ID)) magnets++;
        if (duelist.hasPower(DeltaMagnetPower.POWER_ID)) magnets++;
        if (duelist.hasPower(EpsilonMagnetPower.POWER_ID)) magnets++;
        int total = magnets * this.magicNumber;
        if (total > 0) {
            duelist.applyPowerToSelf(new PlatedArmorPower(owner, total));
        }

        if (isGuardedActive(this, this.getGuardedCheck())) {
            triggerGuarded(duelist, targets);
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public void onGuardedTriggered(AnyDuelist duelist, List<AbstractCreature> targets) {
        if (this.secondMagic > 0) {
            duelist.applyPowerToSelf(new DexterityPower(duelist.creature(), this.secondMagic));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ElectromagneticShield();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            if (this.timesUpgraded > 0) {
                this.upgradeName(NAME + "+" + this.timesUpgraded);
            } else {
                this.upgradeName(NAME + "+");
            }
            this.upgradeMagicNumber(1);
            this.upgradeGuardedCheck(-2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
