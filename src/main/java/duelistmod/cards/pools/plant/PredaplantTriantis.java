package duelistmod.cards.pools.plant;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.interfaces.RevengeCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.List;

public class PredaplantTriantis extends DuelistCard implements RevengeCard {
    public static final String ID = DuelistMod.makeID("PredaplantTriantis");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("PredaplantTriantis.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final AbstractGameAction.AttackEffect AFX = AbstractGameAction.AttackEffect.POISON;
    private static final int COST = 1;

    public PredaplantTriantis() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.baseDamage = this.damage = 3;
    	this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.PREDAPLANT);
        this.tags.add(Tags.ALL);
        this.tags.add(Tags.PLANT);
        this.tags.add(Tags.GOOD_TRIB);
    	this.misc = 0;
    	this.originalName = this.name;
    	this.baseTributes = this.tributes = 3;
        this.baseMagicNumber = this.magicNumber = 4;    // Poison
        this.baseSecondMagic = this.secondMagic = 2;    // Weak
    	this.setupStartingCopies();
    }

    @Override
    public boolean isRevengeActive(DuelistCard card) {
        return RevengeCard.super.isRevengeActive(card);
    }

    @Override
    public void triggerRevenge(AnyDuelist duelist) {
        if (this.magicNumber > 0) {
            poisonAllEnemies(duelist.creature(), this.magicNumber);
        }
        if (this.secondMagic > 0) {
            weakAllEnemies(this.secondMagic);
        }
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
            for (int i = 0; i < 5; i++) {
                attack(targets.get(0), AFX, this.damage);
            }
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
    	return new PredaplantTriantis();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
