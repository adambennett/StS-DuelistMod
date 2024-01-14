package duelistmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.cards.other.tokens.*;
import duelistmod.patches.*;
import duelistmod.variables.*;

public class HaneHane extends DuelistCard 
{
    // TEXT DECLARATION

    public static final String ID = duelistmod.DuelistMod.makeID("HaneHane");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makePath(Strings.HANE_HANE);
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
    private static final int SUMMONS = 1;
    private static boolean resummoned = false;
    // /STAT DECLARATION/

    public HaneHane() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = 4;
        this.beastDeckCopies = 1;
        this.summons = this.baseSummons = SUMMONS;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.LEGEND_BLUE_EYES);
        this.tags.add(Tags.GOOD_TRIB);
        this.tags.add(Tags.BEAST);
        this.tags.add(Tags.BEAST_DECK);
        this.originalName = this.name;
    }
    
    public HaneHane(boolean resummon) {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = 5;
        this.summons = this.baseSummons = SUMMONS;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.LEGEND_BLUE_EYES);
        this.tags.add(Tags.GOOD_TRIB);
        this.originalName = this.name;
        resummoned = resummon;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon(p, this.summons, this);
    	block(this.block);
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new HaneHane();
    }
    


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeSummons(1);
            //this.upgradeBaseCost(0);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
    
    @Override
    public void customOnTribute(DuelistCard tc)
    {
        super.customOnTribute(tc);
    	DuelistCard tok = DuelistCardLibrary.getTokenInCombat(new HaneToken());
		summon(player(), 1, tok);
	    block(this.block);
    }




}
