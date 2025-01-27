package duelistmod.cards;

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

public class EarthGiant extends DuelistCard {

    public static final String ID = DuelistMod.makeID("EarthGiant");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("EarthGiant.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public EarthGiant() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.baseBlock = this.block = 50;
        this.tributes = this.baseTributes = 7;
        this.baseMagicNumber = this.magicNumber = 1;
        this.misc = 0;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.GIANT);
        this.tags.add(Tags.ROCK);
        this.tags.add(Tags.EXEMPT);
        this.enemyIntent = AbstractMonster.Intent.DEFEND;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        tribute();
        block();
        this.resetGiantTributes();
        postDuelistUseCard(owner, targets);
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (c.type.equals(CardType.SKILL)) {
            this.modifyGiantTributes(-this.magicNumber);
        }
    }

    @Override
    public void onEnemyUseCardWhileInHand(AbstractCard c) {
        if (c.type.equals(CardType.SKILL)) {
            this.modifyGiantTributes(-this.magicNumber);
        }
    }

    @Override
    public void onEnemyUseCardWhileInDiscard(AbstractCard c) {
        if (c.type.equals(CardType.SKILL)) {
            this.modifyGiantTributes(-this.magicNumber);
        }
    }

    @Override
    public void onEnemyUseCardWhileInDraw(AbstractCard c) {
        if (c.type.equals(CardType.SKILL)) {
            this.modifyGiantTributes(-this.magicNumber);
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeTributes(-1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EarthGiant();
    }

}
