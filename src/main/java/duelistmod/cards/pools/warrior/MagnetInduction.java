package duelistmod.cards.pools.warrior;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.warrior.*;
import duelistmod.variables.Tags;

import java.util.List;

public class MagnetInduction extends DuelistCard {

    public static final String ID = DuelistMod.makeID("MagnetInduction");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("MagnetInduction.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 0;

    public MagnetInduction() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 1;
        this.baseTributes = this.tributes = 1;
        this.tags.add(Tags.SPELL);
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
        int magnets = 0;
        if (duelist.hasPower(AlphaMagnetPower.POWER_ID)) magnets += duelist.getPower(AlphaMagnetPower.POWER_ID).amount;
        if (duelist.hasPower(BetaMagnetPower.POWER_ID)) magnets += duelist.getPower(BetaMagnetPower.POWER_ID).amount;
        if (duelist.hasPower(GammaMagnetPower.POWER_ID)) magnets += duelist.getPower(GammaMagnetPower.POWER_ID).amount;
        if (duelist.hasPower(DeltaMagnetPower.POWER_ID)) magnets += duelist.getPower(DeltaMagnetPower.POWER_ID).amount;
        if (duelist.hasPower(EpsilonMagnetPower.POWER_ID)) magnets += duelist.getPower(EpsilonMagnetPower.POWER_ID).amount;
        int total = magnets * this.magicNumber;
        if (total > 0) {
            duelist.applyPowerToSelf(new DexterityPower(owner, total));
        }
        if (duelist.hasPower(AlphaMagnetPower.POWER_ID))
            this.addToBot(new RemoveSpecificPowerAction(owner, owner, AlphaMagnetPower.POWER_ID));
        if (duelist.hasPower(BetaMagnetPower.POWER_ID))
            this.addToBot(new RemoveSpecificPowerAction(owner, owner, BetaMagnetPower.POWER_ID));
        if (duelist.hasPower(GammaMagnetPower.POWER_ID))
            this.addToBot(new RemoveSpecificPowerAction(owner, owner, GammaMagnetPower.POWER_ID));
        if (duelist.hasPower(DeltaMagnetPower.POWER_ID))
            this.addToBot(new RemoveSpecificPowerAction(owner, owner, DeltaMagnetPower.POWER_ID));
        if (duelist.hasPower(EpsilonMagnetPower.POWER_ID))
            this.addToBot(new RemoveSpecificPowerAction(owner, owner, EpsilonMagnetPower.POWER_ID));
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
        return new MagnetInduction();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
