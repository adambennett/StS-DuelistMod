package duelistmod.cards.pools.fiend;

import com.megacrit.cardcrawl.actions.defect.NewThunderStrikeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.actions.unique.FiresOfDoomsdayAction;
import duelistmod.dto.AnyDuelist;
import duelistmod.orbs.FireOrb;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;

import java.util.List;

public class FiresOfDoomsday extends DuelistCard {
    public static final String ID = DuelistMod.makeID("FiresOfDoomsday");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("FiresOfDoomsday.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 3;

    public FiresOfDoomsday() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.tags.add(Tags.SPELL);
    	this.misc = 0;
    	this.originalName = this.name;
        this.baseDamage = 7;
        this.baseSecondMagic = this.secondMagic = 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        this.baseSecondMagic = 0;
        AnyDuelist duelist = AnyDuelist.from(this);
        this.baseSecondMagic = this.secondMagic = (int) duelist.orbsChanneledThisCombat().stream().filter(o -> o instanceof FireOrb).count();
        for (int i = 0; i < this.secondMagic; i++) {
            this.addToBot(new FiresOfDoomsdayAction(this));
        }
        postDuelistUseCard(owner, targets);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        AnyDuelist duelist = AnyDuelist.from(this);
        this.baseSecondMagic = 0;
        this.secondMagic = 0;
        this.baseSecondMagic = (int) duelist.orbsChanneledThisCombat().stream().filter(o -> o instanceof FireOrb).count();
        if (this.baseSecondMagic > 0) {
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            this.initializeDescription();
        }
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        if (this.baseSecondMagic > 0) {
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        }
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
    	return new FiresOfDoomsday();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
