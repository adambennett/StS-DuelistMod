package duelistmod.cards.pools.beast;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.unique.ThousandNeedlesAction;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.BeastDrawCount;
import duelistmod.variables.Tags;

import java.util.List;

public class ThousandNeedles extends DuelistCard {
    public static final String ID = DuelistMod.makeID("ThousandNeedles");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ThousandNeedles.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    private static final int lowDmg = 1;
    private static final int highDmg = 5;

    public ThousandNeedles() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.BEAST);
    	this.misc = 0;
    	this.originalName = this.name;
    	this.summons = this.baseSummons = 1;
        this.baseSecondMagic = this.secondMagic = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        //AnyDuelist duelist = AnyDuelist.from(this);
        summon();
        int sumOfBeasts = BeastDrawCount.countBeasts(this);
        this.addToBot(new ThousandNeedlesAction(this, sumOfBeasts, lowDmg, highDmg));
        /*for (int i = 0; i < sumOfBeasts; i++) {
            AbstractCreature target = duelist.getEnemy() != null ? AbstractDungeon.player : duelist.player() ? AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng) : null;
            if (target != null) {
                int dmgRoll = AbstractDungeon.cardRandomRng.random(lowDmg, highDmg);
                attack(target, this.baseAFX, dmgRoll);
            }
        }*/
        postDuelistUseCard(owner, targets);
    }

    @Override
    public AbstractCard makeCopy() {
    	return new ThousandNeedles();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
