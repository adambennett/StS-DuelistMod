package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.helpers.Util;
import duelistmod.patches.*;
import duelistmod.powers.SummonPower;
import duelistmod.variables.*;

import java.util.List;

public class GiantSoldier extends DuelistCard 
{
    // TEXT DECLARATION

    public static final String ID = duelistmod.DuelistMod.makeID("GiantSoldier");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.GIANT_SOLDIER);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    private static final int SUMMONS = 1;
    // /STAT DECLARATION/

    public GiantSoldier() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = 4;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.ROCK);
        this.tags.add(Tags.LEGEND_BLUE_EYES);
        this.tags.add(Tags.STANDARD_DECK);
        this.tags.add(Tags.ORIGINAL_DECK);   
        this.tags.add(Tags.ORIGINAL_HEAL_DECK);
        this.startingOPHDeckCopies = 2;
        this.startingOriginalDeckCopies = 2;
        this.standardDeckCopies = 2;
        this.originalName = this.name;
        this.summons = this.baseSummons = SUMMONS;
        this.isSummon = true;
        this.setupStartingCopies();
        this.enemyIntent = AbstractMonster.Intent.DEFEND;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> target) {
        summon();
        block();
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new GiantSoldier();
    }

    // Upgraded stats.
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
