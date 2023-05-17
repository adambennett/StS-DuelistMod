package duelistmod.cards.incomplete;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.List;

public class AmuletAmbition extends DuelistCard {

    public static final String ID = DuelistMod.makeID("AmuletAmbition");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("AmuletAmbition.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 2;

    public AmuletAmbition() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.originalName = this.name;
        this.block = this.baseBlock = 9;				// block
        this.magicNumber = this.baseMagicNumber = 2;	// increment
        this.secondMagic = this.baseSecondMagic = 1;	// random offensive orbs
		this.showEvokeValue = true;
		this.showEvokeOrbCount = 1;
        this.tags.add(Tags.SPELL);
		this.tags.add(Tags.ARCANE);
        this.enemyIntent = AbstractMonster.Intent.DEFEND;
    }
    
	@Override
	public void update() {
		super.update();
		this.showEvokeOrbCount = this.secondMagic;
	}

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	duelistUseCard(p, m);
    }


    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        block();
        incMaxSummons(this.magicNumber);
        for (int i = 0; i < this.secondMagic; i++) {
            DuelistCard.channelRandomOffensive(AnyDuelist.from(this));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new AmuletAmbition();
    }

    @Override
    public void upgrade() {
        if (canUpgrade()) {
        	if (this.timesUpgraded > 0) { this.upgradeName(NAME + "+" + this.timesUpgraded); }
	    	else { this.upgradeName(NAME + "+"); }
        	if (this.timesUpgraded%2==0) { this.upgradeMagicNumber(2); }
        	else if (this.timesUpgraded%3==0) { this.upgradeSecondMagic(1); }
        	else { this.upgradeBlock(2); }
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
    
    @Override
    public boolean canUpgrade() {
        return this.timesUpgraded < 5;
    }

}
