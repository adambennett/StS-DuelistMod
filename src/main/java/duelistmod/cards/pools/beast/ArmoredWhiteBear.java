package duelistmod.cards.pools.beast;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.orbs.WhiteOrb;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.List;

public class ArmoredWhiteBear extends DuelistCard {
    public static final String ID = DuelistMod.makeID("ArmoredWhiteBear");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("ArmoredWhiteBear.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public ArmoredWhiteBear() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.baseBlock = this.block = 8;
        this.baseMagicNumber = this.magicNumber = 1;
    	this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.BEAST);
        this.tags.add(Tags.FERAL);
    	this.misc = 0;
    	this.originalName = this.name;
    	this.summons = this.baseSummons = 1;
    	this.setupStartingCopies();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        summon();
        block();
        AnyDuelist.from(this).channel(new WhiteOrb());
    }

    @Override
    public AbstractCard makeCopy() {
    	return new ArmoredWhiteBear();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeSummons(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
