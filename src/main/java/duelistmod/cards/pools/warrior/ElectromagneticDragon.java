package duelistmod.cards.pools.warrior;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.warrior.*;
import duelistmod.variables.Tags;

import java.util.List;

public class ElectromagneticDragon extends DuelistCard {

    public static final String ID = DuelistMod.makeID("ElectromagneticDragon");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ElectromagneticDragon.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 3;

    public ElectromagneticDragon() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 12;
        this.baseTributes = this.tributes = 1;
        this.baseBlock = this.block = 12;
        this.baseMagicNumber = this.magicNumber = 8;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.DRAGON);
        this.originalName = this.name;
        this.enemyIntent = AbstractMonster.Intent.ATTACK;
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
        duelist.block(this.block);
        if (targets != null && !targets.isEmpty()) {
            attack(targets.get(0));
        }
        int magnets = 0;
        if (duelist.hasPower(AlphaMagnetPower.POWER_ID)) magnets++;
        if (duelist.hasPower(BetaMagnetPower.POWER_ID)) magnets++;
        if (duelist.hasPower(GammaMagnetPower.POWER_ID)) magnets++;
        if (duelist.hasPower(DeltaMagnetPower.POWER_ID)) magnets++;
        if (duelist.hasPower(EpsilonMagnetPower.POWER_ID)) magnets++;
        int vigorGain = magnets * this.magicNumber;
        if (vigorGain > 0) {
            duelist.applyPowerToSelf(new VigorPower(duelist.creature(), vigorGain));
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
        return new ElectromagneticDragon();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(4);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
