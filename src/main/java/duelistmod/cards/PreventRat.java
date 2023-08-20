package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.powers.SummonPower;
import duelistmod.variables.*;

import java.util.List;

public class PreventRat extends DuelistCard 
{
    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("PreventRat");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.PREVENT_RAT);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public PreventRat() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = 7;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.BEAST);
        this.tags.add(Tags.METAL_RAIDERS);
        this.tags.add(Tags.ORB_DECK);
        this.tags.add(Tags.ORIGINAL_ORB_DECK);
        this.tags.add(Tags.IS_OVERFLOW);
        this.tags.add(Tags.BEAST_DECK);
    	this.startingOPODeckCopies = 2;
        this.beastDeckCopies = 2;
        this.orbDeckCopies = 2;
        this.summons = this.baseSummons = 1;
        this.magicNumber = this.baseMagicNumber = 2;	// overflows
        this.secondMagic = this.baseSecondMagic = 4;	// block on overflow
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
        summon();
        block();
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
