package duelistmod.cards.pools.warrior;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.warrior.EpsilonMagnetPower;
import duelistmod.variables.Tags;

public class EpsilonMagnet extends DeltaMagnet {

    public static final String ID = DuelistMod.makeID("EpsilonMagnet");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("EpsilonMagnet.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;

    public EpsilonMagnet() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.summons = this.baseSummons = 2;
        this.baseBlock = this.block = 3;
        this.baseMagicNumber = this.magicNumber = 4;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.MAGNET);
        this.tags.add(Tags.ROCK);
        this.tags.add(Tags.NEVER_GENERATE);
        this.originalName = this.name;
        this.isSummon = true;
        this.enemyIntent = AbstractMonster.Intent.DEFEND;
    }

    @Override
    public void gainMagnetPower(AnyDuelist duelist) {
        if (!duelist.hasPower(EpsilonMagnetPower.POWER_ID)) {
            duelist.applyPowerToSelf(new EpsilonMagnetPower(duelist.creature()), duelist.creature());
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EpsilonMagnet();
    }

    @Override
    public void upgrade() {}

    @Override
    public boolean canUpgrade() {
        return false;
    }

}

