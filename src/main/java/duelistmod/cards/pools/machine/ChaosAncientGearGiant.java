package duelistmod.cards.pools.machine;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;
import java.util.List;

public class ChaosAncientGearGiant extends DuelistCard {

    public static final String ID = DuelistMod.makeID("ChaosAncientGearGiant");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ChaosAncientGearGiant.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;

    public ChaosAncientGearGiant() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseDamage = this.damage = 50;
        this.tributes = this.baseTributes = 12;
        this.baseMagicNumber = this.magicNumber = 1;
        this.misc = 0;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.GIANT);
        this.tags.add(Tags.EXEMPT);
        this.tags.add(Tags.MACHINE);
        this.tags.add(Tags.ANCIENT_FOR_MACHINE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        tribute();
        if (targets.size() > 0) {
            attack(targets.get(0));
        }
        this.resetGiantTributes();
        postDuelistUseCard(owner, targets);
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (c.hasTag(Tags.MACHINE)) {
            this.modifyGiantTributes(-this.magicNumber);
        }
    }

    @Override
    public void onEnemyUseCardWhileInHand(AbstractCard c) {
        if (c.hasTag(Tags.MACHINE)) {
            this.modifyGiantTributes(-this.magicNumber);
        }
    }

    @Override
    public void onEnemyUseCardWhileInDiscard(AbstractCard c) {
        if (c.hasTag(Tags.MACHINE)) {
            this.modifyGiantTributes(-this.magicNumber);
        }
    }

    @Override
    public void onEnemyUseCardWhileInDraw(AbstractCard c) {
        if (c.hasTag(Tags.MACHINE)) {
            this.modifyGiantTributes(-this.magicNumber);
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(10);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ChaosAncientGearGiant();
    }

}
