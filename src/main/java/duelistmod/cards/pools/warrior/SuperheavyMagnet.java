package duelistmod.cards.pools.warrior;

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
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;

import java.util.ArrayList;
import java.util.List;

public class SuperheavyMagnet extends DuelistCard {

    public static final String ID = DuelistMod.makeID("SuperheavyMagnet");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.SUPERHEAVY_MAGNET);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public SuperheavyMagnet() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.summons = this.baseSummons = 1;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.SUPERHEAVY);
        this.tags.add(Tags.MAGNET);
        this.originalName = this.name;
        this.isSummon = true;
        this.exhaust = true;
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
        List<AbstractPower> magnetPowers = new ArrayList<>();
        int existingMagnets = 0;
        boolean hasAlpha = false;
        boolean hasBeta = false;
        boolean hasGamma = false;
        boolean hasDelta = false;
        boolean hasEpsilon = false;
        for (AbstractPower power : duelist.powers()) {
            if (power instanceof AlphaMagnetPower) {
                existingMagnets++;
                hasAlpha = true;
            }
            if (power instanceof BetaMagnetPower) {
                existingMagnets++;
                hasBeta = true;
            }
            if (power instanceof GammaMagnetPower) {
                existingMagnets++;
                hasGamma = true;
            }
            if (power instanceof DeltaMagnetPower) {
                existingMagnets++;
                hasDelta = true;
            }
            if (power instanceof EpsilonMagnetPower) {
                existingMagnets++;
                hasEpsilon = true;
            }
            if (hasAlpha && hasBeta && hasGamma && hasDelta && hasEpsilon) break;
        }
        if (!hasAlpha) magnetPowers.add(new AlphaMagnetPower(duelist.creature()));
        if (!hasBeta) magnetPowers.add(new BetaMagnetPower(duelist.creature()));
        if (!hasGamma) magnetPowers.add(new GammaMagnetPower(duelist.creature()));
        if (this.upgraded) {
            if (!hasDelta) magnetPowers.add(new DeltaMagnetPower(duelist.creature()));
            if (!hasEpsilon) magnetPowers.add(new EpsilonMagnetPower(duelist.creature()));
        }

        if (!magnetPowers.isEmpty()) {
            AbstractPower power = magnetPowers.get(AbstractDungeon.cardRandomRng.random(magnetPowers.size() - 1));
            duelist.applyPowerToSelf(power);
            existingMagnets++;
        }
        if (existingMagnets > 0) {
            duelist.drawTag(existingMagnets, Tags.WARRIOR);
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
        return new SuperheavyMagnet();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeSummons(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
