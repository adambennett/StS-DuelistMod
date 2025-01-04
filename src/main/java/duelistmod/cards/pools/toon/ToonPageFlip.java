package duelistmod.cards.pools.toon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.enums.ToonPageFlipUpgradeStates;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;
import java.util.List;

import static duelistmod.enums.ToonPageFlipUpgradeStates.*;

public class ToonPageFlip extends DuelistCard {

    public static final String ID = DuelistMod.makeID("ToonPageFlip");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ToonPageFlip.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 2;
    private ToonPageFlipUpgradeStates currentToonPageFlipUpgradeStates = MONSTER__DRAW_PILE__RANDOM_ENEMY;

    public ToonPageFlip() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.tags.add(Tags.SPELL);
        this.tags.add(Tags.ARCANE);
        this.tags.add(Tags.EXEMPT);
        this.baseMagicNumber = this.magicNumber = 2;    // Number of Toons to choose
        this.baseSecondMagic = this.secondMagic = 1;    // Random number selected for Resummon
    }

    @Override
    public void update() {
        super.update();
        this.target = getStateFlags().calculated;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        StateFlags flags = getStateFlags();
        // TODO: Implement various effects
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
        return new ToonPageFlip();
    }

    @Override
    public void upgrade() {
        if (this.canUpgrade()) {
            if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
            else { this.upgradeName(NAME + "+"); }
            this.setCurrentUpgradeState(ToonPageFlipUpgradeStates.next(this.currentToonPageFlipUpgradeStates));
            this.rawDescription = EXTENDED_DESCRIPTION[this.currentToonPageFlipUpgradeStates.ordinal() - 1];
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

    @Override
    public boolean canUpgrade() {
        return ToonPageFlipUpgradeStates.next(this.currentToonPageFlipUpgradeStates) != null;
    }

    public void setCurrentUpgradeState(ToonPageFlipUpgradeStates currentToonPageFlipUpgradeStates) {
        this.currentToonPageFlipUpgradeStates = currentToonPageFlipUpgradeStates;
    }

    private StateFlags getStateFlags() {
        boolean isOnlyDrawPile = this.currentToonPageFlipUpgradeStates == MONSTER__DRAW_PILE__RANDOM_ENEMY;

        boolean isOnlyMonsters = this.currentToonPageFlipUpgradeStates == MONSTER__DRAW_PILE__RANDOM_ENEMY ||
                                 this.currentToonPageFlipUpgradeStates == MONSTER__BOTH_PILE__RANDOM_ENEMY;

        boolean isRandomEnemy = this.currentToonPageFlipUpgradeStates == MONSTER__DRAW_PILE__RANDOM_ENEMY ||
                                this.currentToonPageFlipUpgradeStates == MONSTER__BOTH_PILE__RANDOM_ENEMY ||
                                this.currentToonPageFlipUpgradeStates == CARD__BOTH_PILE__RANDOM_ENEMY;

        boolean isChosenEnemy = this.currentToonPageFlipUpgradeStates == CARD__BOTH_PILE__CHOOSE_ENEMY;

        boolean isAllEnemies = this.currentToonPageFlipUpgradeStates == CARD__BOTH_PILE__ALL_ENEMY;

        return new StateFlags(isAllEnemies, isRandomEnemy, isChosenEnemy, isOnlyDrawPile, isOnlyMonsters);
    }

    private static class StateFlags {

        private final boolean isAllEnemies;
        private final boolean isRandomEnemy;
        private final boolean isChooseEnemy;
        private final boolean isOnlyDrawPile;
        private final boolean isOnlyMonsters;
        private final CardTarget calculated;

        public StateFlags(boolean isAllEnemies, boolean isRandomEnemy, boolean isChooseEnemy, boolean isOnlyDrawPile, boolean isOnlyMonsters) {
            this.isAllEnemies = isAllEnemies;
            this.isRandomEnemy = isRandomEnemy;
            this.isChooseEnemy = isChooseEnemy;
            this.isOnlyDrawPile = isOnlyDrawPile;
            this.isOnlyMonsters = isOnlyMonsters;
            this.calculated = isRandomEnemy || isAllEnemies
                    ? CardTarget.ALL_ENEMY
                    : CardTarget.ENEMY;
        }

        public boolean isAllEnemies() {
            return isAllEnemies;
        }

        public boolean isRandomEnemy() {
            return isRandomEnemy;
        }

        public boolean isChooseEnemy() {
            return isChooseEnemy;
        }

        public boolean isOnlyDrawPile() {
            return isOnlyDrawPile;
        }

        public boolean isOnlyMonsters() {
            return isOnlyMonsters;
        }

        public CardTarget getCalculated() {
            return calculated;
        }
    }
}
