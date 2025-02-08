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

public class GiantRedSeasnake extends DuelistCard {

    private static final CardStrings cardStrings = getCardStrings();
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;

    public GiantRedSeasnake() {
        super(getCARDID(), NAME, getIMG(), COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.AQUA);
        this.tags.add(Tags.GIANT);
        this.tags.add(Tags.EXEMPT);
        this.misc = 0;
        
        
        this.originalName = this.name;
        this.damage = this.baseDamage = 55;
        this.baseTributes = this.tributes = 16;
        this.magicNumber = this.baseMagicNumber = 1;
    }

    @Override
    public void onIncrementWhileInHand(int amount, int newMaxSummons) {
        this.modifyGiantTributes(-this.magicNumber);
    }

    @Override
    public void onIncrementWhileInDraw(int amount, int newMaxSummons) {
        this.modifyGiantTributes(-this.magicNumber);
    }

    @Override
    public void onIncrementWhileInDiscard(int amount, int newMaxSummons) {
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
        return new GiantRedSeasnake();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            if (this.timesUpgraded > 0) {
                this.upgradeName(NAME + "+" + this.timesUpgraded);
            } else {
                this.upgradeName(NAME + "+");
            }
            this.upgradeDamage(10);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

    // AUTOSETUP - ID/IMG - Id, Img name, and class name all must match to use this
    public static String getCARDID() {
        return DuelistMod.makeID(getCurClassName());
    }

    public static CardStrings getCardStrings() {
        return CardCrawlGame.languagePack.getCardStrings(getCARDID());
    }

    public static String getIMG() {
        return DuelistMod.makeCardPath(getCurClassName() + ".png");
    }

    public static String getCurClassName() {
        return (new CurClassNameGetter()).getClassName();
    }

    public static class CurClassNameGetter extends SecurityManager {
        public String getClassName() {
            return getClassContext()[1].getSimpleName();
        }
    }
    // END AUTOSETUP
}
