package duelistmod.cards.pools.dragons;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.common.CardSelectScreenResummonAction;
import duelistmod.cards.other.tempCards.*;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class CyberDragonNachster extends DuelistCard {
    public static final String ID = DuelistMod.makeID("CyberDragonNachster");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("CyberDragonNachster.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;
    private static final int COST = 2;

    public CyberDragonNachster() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.summons = this.baseSummons				= 1;		// summons
        							// for any summon or tribute card
        this.baseMagicNumber = this.magicNumber 	= 12;		// DragonScales gain
        this.baseSecondMagic = this.secondMagic 	= 2;		// Artifact gain
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.DRAGON);
        this.tags.add(Tags.MACHINE);
        this.tags.add(Tags.CYBER);
        this.misc = 0;
        this.originalName = this.name;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	summon();
    	ArrayList<AbstractCard> choices = new ArrayList<>();
    	choices.add(new PuzzleDragonScales(this.magicNumber));
    	choices.add(new PuzzleDragonArtifacts(this.secondMagic));
    	this.addToBot(new CardSelectScreenResummonAction(choices, 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new CyberDragonNachster();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            this.upgradeSecondMagic(1);
            this.upgradeMagicNumber(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription(); 
        }
    }

}
