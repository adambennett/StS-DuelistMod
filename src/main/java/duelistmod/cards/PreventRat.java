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
import duelistmod.variables.Strings;
import duelistmod.variables.Tags;

import java.util.List;

public class PreventRat extends DuelistCard {

    public static final String ID = DuelistMod.makeID("PreventRat");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.PREVENT_RAT);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;

    public PreventRat() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.BEAST);
        this.tags.add(Tags.METAL_RAIDERS);
        this.tags.add(Tags.ORB_DECK);
        this.tags.add(Tags.ORIGINAL_ORB_DECK);
        this.tags.add(Tags.IS_OVERFLOW);
        this.tags.add(Tags.BEAST_DECK);
        this.baseBlock = this.block = 6;
    	this.startingOPODeckCopies = 2;
        this.beastDeckCopies = 1;
        this.orbDeckCopies = 2;
        this.summons = this.baseSummons = 1;
        this.magicNumber = this.baseMagicNumber = 2;	// overflows
        this.secondMagic = this.baseSecondMagic = 3;	// block on overflow
		this.originalName = this.name;
		this.isSummon = true;
		this.setupStartingCopies();
        this.enemyIntent = AbstractMonster.Intent.DEFEND;
    }
    
    @Override
    public void triggerOverflowEffect() {
    	super.triggerOverflowEffect();
    	block(this.secondMagic);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        summon();
        block();
        postDuelistUseCard(owner, targets);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new PreventRat();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeSecondMagic(1);
            this.upgradeBlock(3);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }



}
