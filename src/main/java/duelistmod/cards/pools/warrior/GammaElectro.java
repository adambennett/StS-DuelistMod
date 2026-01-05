package duelistmod.cards.pools.warrior;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.actions.unique.MagnetEnergyGainAction;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.warrior.GammaMagnetPower;
import duelistmod.variables.Tags;

import java.util.List;

public class GammaElectro extends GammaMagnet {

    public static final String ID = DuelistMod.makeID("GammaElectro");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GammaElectro.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public GammaElectro() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.summons = this.baseSummons = 1;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.MAGNET);
        this.tags.add(Tags.ROCK);
        this.originalName = this.name;
        this.isSummon = true;
        this.baseMagicNumber = this.magicNumber = 3; // monsters to draw
        this.enemyIntent = AbstractMonster.Intent.DEFEND;
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        summon();
        AnyDuelist duelist = AnyDuelist.from(this);
        duelist.drawTag(this.magicNumber, Tags.MONSTER);
        this.addToBot(new MagnetEnergyGainAction(owner, CardType.POWER));
        GammaMagnetPower pow = duelist.hasPower(GammaMagnetPower.POWER_ID) ? (GammaMagnetPower) duelist.getPower(GammaMagnetPower.POWER_ID) : new GammaMagnetPower(owner, owner);
        pow.electrify(1, 2);
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
        return new GammaElectro();
    }

    @Override
    public void upgrade() {}

    @Override
    public boolean canUpgrade() {
        return false;
    }
}
