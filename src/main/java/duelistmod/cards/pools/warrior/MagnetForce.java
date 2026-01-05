package duelistmod.cards.pools.warrior;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.warrior.*;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;

public class MagnetForce extends DuelistCard {

    public static final String ID = DuelistMod.makeID("MagnetForce");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("MagnetForce.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = 1;

    public MagnetForce() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 8; // per Magnet
        this.isMultiDamage = true;
        this.baseTributes = this.tributes = 1;
        this.tags.add(Tags.TRAP);
        this.tags.add(Tags.MAGNET);
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
        tribute();
        List<String> toRemoveFrom = new ArrayList<>();
        int magnets = 0;
        if (duelist.hasPower(AlphaMagnetPower.POWER_ID)) {
            AbstractPower pow = duelist.getPower(AlphaMagnetPower.POWER_ID);
            magnets += pow.amount;
            toRemoveFrom.add(pow.ID);
        }
        if (duelist.hasPower(BetaMagnetPower.POWER_ID)) {
            AbstractPower pow = duelist.getPower(BetaMagnetPower.POWER_ID);
            magnets += pow.amount;
            toRemoveFrom.add(pow.ID);
        }
        if (duelist.hasPower(GammaMagnetPower.POWER_ID)) {
            AbstractPower pow = duelist.getPower(GammaMagnetPower.POWER_ID);
            magnets += pow.amount;
            toRemoveFrom.add(pow.ID);
        }
        if (duelist.hasPower(DeltaMagnetPower.POWER_ID)) {
            AbstractPower pow = duelist.getPower(DeltaMagnetPower.POWER_ID);
            magnets += pow.amount;
            toRemoveFrom.add(pow.ID);
        }
        if (duelist.hasPower(EpsilonMagnetPower.POWER_ID)) {
            AbstractPower pow = duelist.getPower(EpsilonMagnetPower.POWER_ID);
            magnets += pow.amount;
            toRemoveFrom.add(pow.ID);
        }
        int total = magnets * this.damage;

        if (total > 0) {
            attack(targets.get(0), this.baseAFX, total);
        }
        if (!toRemoveFrom.isEmpty()) {
            int roll = AbstractDungeon.cardRandomRng.random(toRemoveFrom.size() - 1);
            this.addToBot(new RemoveSpecificPowerAction(owner, owner, toRemoveFrom.get(roll)));
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
        return new MagnetForce();
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
