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
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.List;

public class ThreeThousandNeedles extends DuelistCard {
    public static final String ID = DuelistMod.makeID("ThreeThousandNeedles");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ThreeThousandNeedles.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 3;

    private static final int lowDmg = 3;
    private static final int highDmg = 8;

    public ThreeThousandNeedles() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.BEAST);
    	this.misc = 0;
    	this.originalName = this.name;
    	this.tributes = this.baseTributes = 3;
        this.baseMagicNumber = this.magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        AnyDuelist duelist = AnyDuelist.from(this);
        List<Integer> beastDrawsByTurn = duelist.player() ? DuelistMod.beastsDrawnByTurn : duelist.getEnemy() != null ? DuelistMod.enemyBeastsDrawnByTurn : null;

        tribute();
        int sumOfBeasts = 0;
        if (beastDrawsByTurn != null) {
            int counter = 0;
            for (int i = beastDrawsByTurn.size() - 1; i > 0 && counter < this.magicNumber; i--, counter++) {
                sumOfBeasts += beastDrawsByTurn.get(i);
            }
        }
        for (int i = 0; i < sumOfBeasts; i++) {
            AbstractCreature target = duelist.getEnemy() != null ? AbstractDungeon.player : duelist.player() ? AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng) : null;
            if (target != null) {
                int dmgRoll = AbstractDungeon.cardRandomRng.random(lowDmg, highDmg);
                attack(target, this.baseAFX, dmgRoll);
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
    	return new ThreeThousandNeedles();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
