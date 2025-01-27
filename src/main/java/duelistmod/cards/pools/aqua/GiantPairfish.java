package duelistmod.cards.pools.aqua;

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

public class GiantPairfish extends DuelistCard {

    public static final String ID = DuelistMod.makeID("GiantPairfish");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GiantPairfish.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;

    public GiantPairfish() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 70;
        this.tributes = this.baseTributes = 18;
        this.baseMagicNumber = this.magicNumber = 1;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.WYRM);
        this.tags.add(Tags.AQUA);
        this.tags.add(Tags.GIANT);
        this.tags.add(Tags.EXEMPT);
        this.originalName = this.name;
    }

    @Override
    public void onOverflowWhileInHand() {
        this.modifyGiantTributes(-this.magicNumber);
    }

    @Override
    public void onOverflowWhileInDraw() {
        this.modifyGiantTributes(-this.magicNumber);
    }

    @Override
    public void onOverflowWhileInDiscard() {
        this.modifyGiantTributes(-this.magicNumber);
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
    public AbstractCard makeCopy() {
        return new GiantPairfish();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(15);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
