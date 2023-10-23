package duelistmod.cards.pools.beast;

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

public class KinkaByo extends DuelistCard {
    public static final String ID = DuelistMod.makeID("KinkaByo");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("KinkaByo.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public KinkaByo() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.baseBlock = this.block = 6;
    	this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.BEAST);
        this.tags.add(Tags.SPIRIT);
    	this.misc = 0;
    	this.originalName = this.name;
    	this.summons = this.baseSummons = 3;
        this.baseMagicNumber = this.magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        summon();
        block();
        if (this.magicNumber > 0) {
            incMaxSummons(this.magicNumber);
        }
    }

    @Override
    public AbstractCard makeCopy() {
    	return new KinkaByo();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
