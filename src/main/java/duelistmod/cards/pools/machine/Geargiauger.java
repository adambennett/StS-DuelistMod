package duelistmod.cards.pools.machine;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.*;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.CardSelectScreenIntoHandAction;
import duelistmod.cards.other.tokens.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class Geargiauger extends DuelistCard 
{
    // TEXT DECLARATION
    public static final String ID = DuelistMod.makeID("Geargiauger");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("Geargiauger.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 1;
    // /STAT DECLARATION/

    public Geargiauger() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 9;
        this.summons = this.baseSummons = 1;
        this.specialCanUseLogic = true;
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.MACHINE);
        this.misc = 0;
        this.originalName = this.name;
        this.cardsToPreview = new ExplosiveToken();
        this.magicNumber = this.baseMagicNumber = 2;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
    	summon();
    	attack(m);
    	ArrayList<AbstractCard> powerWalls = new ArrayList<AbstractCard>();
    	if (upgraded) { for (int i = 0; i < this.magicNumber; i++) { DuelistCard tok = DuelistCardLibrary.getTokenInCombat(new SuperExplodingToken()); powerWalls.add(tok); }}
    	else { for (int i = 0; i < this.magicNumber; i++) { DuelistCard tok = DuelistCardLibrary.getTokenInCombat(new ExplosiveToken()); powerWalls.add(tok); }}
    	this.addToBot(new CardSelectScreenIntoHandAction(powerWalls, this.magicNumber, true, false, false));
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new Geargiauger();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            this.upgradeDamage(2);
            this.cardsToPreview = new SuperExplodingToken();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription(); 
        }
    }
    




	










   
}
