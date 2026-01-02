package duelistmod.cards.pools.warrior;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.RetainForTurnsPower;
import duelistmod.variables.Tags;

public class OneForOne extends DuelistCard {

    public static final String ID = DuelistMod.makeID("OneForOne");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("OneForOne.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;

    public OneForOne() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.magicNumber = this.baseMagicNumber = 3;
        this.secondMagic = this.baseSecondMagic = 2;
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.ARCANE);
        this.tags.add(Tags.EXODIA_DECK);
        this.exodiaDeckCopies = 2;
        this.exhaust = true;
        this.setupStartingCopies();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyPowerToSelf(new RetainForTurnsPower(p, this.magicNumber, this.secondMagic));
    }

    @Override
    public AbstractCard makeCopy() {
        return new OneForOne();
    }

    @Override
    public void upgrade() {
        if (canUpgrade()) {
            if (this.timesUpgraded > 0) {
                this.upgradeName(NAME + "+" + this.timesUpgraded);
            } else {
                this.upgradeName(NAME + "+");
            }
            this.upgradeSecondMagic(1);
            exodiaDeckCardUpgradeDesc(UPGRADE_DESCRIPTION);
        }
    }

    @Override
    public boolean canUpgrade() {
        return this.timesUpgraded < 5;
    }

}
