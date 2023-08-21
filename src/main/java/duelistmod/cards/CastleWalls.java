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
import duelistmod.variables.*;

import java.util.List;

public class CastleWalls extends DuelistCard
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("CastleWalls");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.CASTLE_WALLS);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/ 

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = 1;
    private static final int BLOCK = 5;
    // /STAT DECLARATION/

    public CastleWalls() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = BLOCK;
        this.tags.add(Tags.TRAP);
        this.tags.add(CardTags.STARTER_DEFEND);
        this.tags.add(Tags.STANDARD_DECK);
        this.tags.add(Tags.DRAGON_DECK);
        this.tags.add(Tags.SPELLCASTER_DECK);
        this.tags.add(Tags.NATURIA_DECK);
        this.tags.add(Tags.TOON_DECK);
        this.tags.add(Tags.ORB_DECK);
        this.tags.add(Tags.RESUMMON_DECK);
        this.tags.add(Tags.HEAL_DECK);
        this.tags.add(Tags.CREATOR_DECK);
        this.tags.add(Tags.GENERATION_DECK);
        this.tags.add(Tags.MACHINE_DECK);
        this.tags.add(Tags.FIEND_DECK);
        this.tags.add(Tags.AQUA_DECK);
        this.tags.add(Tags.WARRIOR_DECK);
        this.tags.add(Tags.ASCENDED_ONE_DECK);
        this.tags.add(Tags.ASCENDED_TWO_DECK);
        this.tags.add(Tags.ASCENDED_THREE_DECK);
        this.tags.add(Tags.MEGATYPE_DECK);
        this.tags.add(Tags.INCREMENT_DECK);
        this.tags.add(Tags.PLANT_DECK);
        this.tags.add(Tags.INSECT_DECK);
        this.tags.add(Tags.BEAST_DECK);
        this.tags.add(Tags.ZOMBIE_DECK);
        this.zombieDeckCopies = 2;
        this.beastDeckCopies = 2;
        this.insectDeckCopies = 2;
        this.plantDeckCopies = 2;
        this.incrementDeckCopies = 2;
        this.megatypeDeckCopies = 2;
        this.a1DeckCopies = 2;
        this.a2DeckCopies = 2;
        this.a3DeckCopies = 2;
        this.superheavyDeckCopies = 3;
        this.fiendDeckCopies = 2;
        this.machineDeckCopies = 2;
        this.generationDeckCopies = 3;
        this.standardDeckCopies = 2;
        this.dragonDeckCopies = 2;
        this.spellcasterDeckCopies = 2;
        this.natureDeckCopies = 3;
        this.toonDeckCopies = 2;
        this.creatorDeckCopies = 3;
        this.orbDeckCopies = 2;
        this.resummonDeckCopies = 2;
        this.healDeckCopies = 3;
        this.aquaDeckCopies = 2;
        this.originalName = this.name;
        this.setupStartingCopies();
        this.enemyIntent = AbstractMonster.Intent.DEFEND;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        block();
    }

    @Override
    public AbstractCard makeCopy() {
        return new CastleWalls();
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
