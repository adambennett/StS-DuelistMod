package duelistmod.cards.pools.warrior;

import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.GuardedMagnetCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.warrior.*;
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;

public class Valkyrion extends GuardedMagnetCard {

    public static final String ID = DuelistMod.makeID("Valkyrion");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.VALK_MAGNET);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public Valkyrion() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.APEX);
        this.tags.add(Tags.ROCK);
        this.tags.add(Tags.WARRIOR);
        this.tags.add(Tags.MAGNET);
        this.baseDamage = this.damage = 6;
        this.baseTributes = this.tributes = 3;
        this.setBaseGuardedCheck(20);
        this.setGuardedCheck(20);
        this.isMultiDamage = true;
        this.originalName = this.name;
    }

    public Valkyrion(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET) {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
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

        this.addToBot(new RemoveSpecificPowerAction(duelist.creature(), duelist.creature(), duelist.getPower(AlphaMagnetPower.POWER_ID)));
        this.addToBot(new RemoveSpecificPowerAction(duelist.creature(), duelist.creature(), duelist.getPower(BetaMagnetPower.POWER_ID)));
        this.addToBot(new RemoveSpecificPowerAction(duelist.creature(), duelist.creature(), duelist.getPower(GammaMagnetPower.POWER_ID)));
        this.addToBot(new RemoveSpecificPowerAction(duelist.creature(), duelist.creature(), duelist.getPower(DeltaMagnetPower.POWER_ID)));
        this.addToBot(new RemoveSpecificPowerAction(duelist.creature(), duelist.creature(), duelist.getPower(EpsilonMagnetPower.POWER_ID)));

        for (int i = 0; i < magnets; i++) {
            if (duelist.player()) {
                this.addToBot(new DamageAllEnemiesAction(owner, this.multiDamage, DamageInfo.DamageType.NORMAL, this.baseAFX));
            } else if (duelist.getEnemy() != null && targets != null && !targets.isEmpty()) {
                attack(targets.get(0));
            }
        }

        if (isGuardedActive(this, this.getGuardedCheck())) {
            triggerGuarded(duelist, targets);
        }

        postDuelistUseCard(owner, targets);
    }

    @Override
    public void onGuardedTriggered(AnyDuelist duelist, List<AbstractCreature> targets) {
        List<AbstractPower> magnetPowers = new ArrayList<>();
        magnetPowers.add(new AlphaMagnetPower(duelist.creature()));
        magnetPowers.add(new BetaMagnetPower(duelist.creature()));
        magnetPowers.add(new GammaMagnetPower(duelist.creature()));
        magnetPowers.add(new DeltaMagnetPower(duelist.creature()));
        magnetPowers.add(new EpsilonMagnetPower(duelist.creature()));
        AbstractPower power = magnetPowers.get(AbstractDungeon.cardRandomRng.random(magnetPowers.size() - 1));
        duelist.applyPowerToSelf(power);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Valkyrion();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.timesUpgraded++;
            this.upgraded = true;
            transformIntoElectro(new Berserkion());
        } else {
            this.timesUpgraded++;
            transformIntoElectro(new ImperionSuperconductiveBattlebot());
        }
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

    @Override
    public boolean copyUpgradeStateOnTransform() {
        return false;
    }
}
