package duelistmod.cards.pools.pharaoh;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

public class GhostrickDollBasic extends DuelistCard {

    public static final String ID = DuelistMod.makeID("GhostrickDollBasic");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("GhostrickDoll.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_MONSTERS;

    public GhostrickDollBasic() {
        super(ID, NAME, IMG,  1, UPGRADE_DESCRIPTION, TYPE, COLOR, CardRarity.BASIC, TARGET);
        this.tags.add(Tags.MONSTER);
        this.tags.add(Tags.SPELLCASTER);
        this.tags.add(Tags.GHOSTRICK);
        this.tags.add(Tags.PHARAOH_THREE_DECK);
        this.p3DeckCopies = 1;
        this.misc = 0;
        this.originalName = this.name;
        this.baseSummons = this.summons = 1;
        this.specialCanUseLogic = true;
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 1;
        this.setupStartingCopies();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	summon();
    	evokeMult(2);
    }

    @Override
    public AbstractCard makeCopy() {
        return new GhostrickDollBasic();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
            this.upgradeBaseCost(0);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription(); 
        }
    }
}
