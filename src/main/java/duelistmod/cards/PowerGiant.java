package duelistmod.cards;

import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;
import java.util.List;

public class PowerGiant extends DuelistCard {

    public static final String ID = DuelistMod.makeID("PowerGiant");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("PowerGiant.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public PowerGiant() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseDamage = this.damage = 20;
        this.secondMagic = this.baseSecondMagic = 20;
        this.baseMagicNumber = this.magicNumber = 5;
        this.baseSummons = this.summons = 2;
        this.tributes = this.baseTributes = 6;
        this.misc = 0;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.GIANT);
        this.tags.add(Tags.ROCK);
        this.tags.add(Tags.EXEMPT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        tribute();
        summon();
        if (targets.size() > 0) {
            attack(targets.get(0));
        }
        if (this.damage != this.secondMagic) {
            this.addToTop(new ModifyDamageAction(this.uuid, -this.damage + this.secondMagic));
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public void onSummonWhileInHand(DuelistCard c, int amt) {
        if (amt > 0) {
            damageInc(amt);
        }
    }

    @Override
    public void onSummonWhileInDiscard(DuelistCard c, int amt) {
        if (amt > 0) {
            damageInc(amt);
        }
    }

    @Override
    public void onSummonWhileInDraw(DuelistCard c, int amt) {
        if (amt > 0) {
            damageInc(amt);
        }
    }

    public void damageInc(int summs) {
        AbstractDungeon.actionManager.addToTop(new ModifyDamageAction(this.uuid, this.magicNumber * summs));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            if (this.timesUpgraded > 0) {
                this.upgradeName(NAME + "+" + this.timesUpgraded);
            } else {
                this.upgradeName(NAME + "+");
            }
            this.upgradeDamage(15);
            this.upgradeSecondMagic(15);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new PowerGiant();
    }

}
