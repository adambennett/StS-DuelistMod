package duelistmod.cards.pools.warrior;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.actions.unique.MagnetEnergyGainAction;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.AlphaMagPower;
import duelistmod.variables.Tags;

import java.util.List;

public class AlphaElectro extends AlphaMagnet {

    public static final String ID = DuelistMod.makeID("AlphaElectro");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("AlphaElectro.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final AttackEffect AFX = AttackEffect.SLASH_HORIZONTAL;
    private static final int COST = 1;

    public AlphaElectro() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, TARGET);
        this.baseDamage = this.damage = 14;
        this.summons = this.baseSummons = 1;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.MAGNET);
        this.tags.add(Tags.ROCK);
        this.originalName = this.name;
        this.isSummon = true;
        this.enemyIntent = AbstractMonster.Intent.ATTACK;
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        AnyDuelist duelist = AnyDuelist.from(this);

        summon();

        if (targets.size() > 0) {
            attack(targets.get(0), AFX, this.damage);
        }

        AlphaMagPower pow;
        if (duelist.hasPower(AlphaMagPower.POWER_ID)) {
            pow = (AlphaMagPower) duelist.getPower(AlphaMagPower.POWER_ID);
        } else {
            pow = new AlphaMagPower(owner, owner);
        }
        pow.electrify(2);

        this.addToBot(new MagnetEnergyGainAction(owner, CardType.SKILL));

        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
        return new AlphaElectro();
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

}
