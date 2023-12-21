package duelistmod.cards.nameless.magic;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.interfaces.NamelessTombCard;
import duelistmod.cards.incomplete.DarkworldThorns;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class DarkworldThornsNameless extends DuelistCard implements NamelessTombCard
{
    // TEXT DECLARATION

    public static final String ID = DuelistMod.makeID("Nameless:Magic:DarkworldThorns");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("DarkworldThorns.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPECIAL;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public DarkworldThornsNameless() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.PLANT);       
        this.tags.add(Tags.PLANT_DECK);
        this.plantDeckCopies = 1;
        this.summons = this.baseSummons = 2;			
        this.baseBlock = this.block = 4;
        this.magicNumber = this.baseMagicNumber = 1 + DuelistMod.namelessTombMagicMod;
        this.originalName = this.name;
        this.exhaust = true;
        this.setupStartingCopies();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	block();    
    	applyPowerToSelf(new ThornsPower(p, this.magicNumber));
    }

    @Override
    public DuelistCard getStandardVersion() { return new DarkworldThorns(); }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new DarkworldThornsNameless();
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
