package duelistmod.cards.pools.toon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import duelistmod.DuelistMod;
import duelistmod.abstracts.DuelistCard;
import duelistmod.dto.AnyDuelist;
import duelistmod.patches.AbstractCardEnum;
import duelistmod.variables.Tags;
import java.util.List;

public class Metalmorph extends DuelistCard {

    public static final String ID = DuelistMod.makeID("Metalmorph");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("Metalmorph.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_TRAPS;
    private static final int COST = 2;

    public Metalmorph() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.tags.add(Tags.TRAP);
        this.tags.add(Tags.MACHINE);
        this.tags.add(Tags.IS_OVERFLOW);
        this.baseTributes = this.tributes = 4;
        this.baseMagicNumber = this.magicNumber = 4;    // Overflows
        this.baseSecondMagic = this.secondMagic = 8;    // Plated Armor gain on play
        this.baseThirdMagic = this.thirdMagic = 2;      // Plated Armor gain on Overflow
    	this.misc = 0;
    	this.originalName = this.name;
        this.selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        preDuelistUseCard(owner, targets);
        AnyDuelist duelist = AnyDuelist.from(this);
        tribute();
        duelist.applyPowerToSelf(new PlatedArmorPower(duelist.creature(), this.secondMagic));
        postDuelistUseCard(owner, targets);
    }

    @Override
    public void triggerOverflowEffect() {
        super.triggerOverflowEffect();
        AnyDuelist duelist = AnyDuelist.from(this);
        duelist.applyPowerToSelf(new PlatedArmorPower(duelist.creature(), this.thirdMagic));
    }


    @Override
    public AbstractCard makeCopy() {
    	return new Metalmorph();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeTributes(-1);
            this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }

}
