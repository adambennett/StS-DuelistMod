package duelistmod.cards.pools.beast;

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
import duelistmod.powers.duelistPowers.RushRecklesslyPower;
import duelistmod.variables.Tags;

import java.util.List;

public class RushRecklessly extends DuelistCard {
    public static final String ID = DuelistMod.makeID("RushRecklessly");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = DuelistMod.makeCardPath("RushRecklessly.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DUELIST_SPELLS;
    private static final int COST = 1;

    public RushRecklessly() {
    	super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    	this.tags.add(Tags.SPELL);
    	this.misc = 0;
    	this.originalName = this.name;
    	this.tributes = this.baseTributes = 1;
        this.baseMagicNumber = this.magicNumber = 2;    // turns
        this.baseSecondMagic = this.secondMagic = 15;   // percent boost
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        duelistUseCard(p, m);
    }

    @Override
    public void duelistUseCard(AbstractCreature owner, List<AbstractCreature> targets) {
        tribute();
        AnyDuelist duelist = AnyDuelist.from(this);
        duelist.applyPowerToSelf(new RushRecklesslyPower(duelist.creature(), duelist.creature(), this.secondMagic, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
    	return new RushRecklessly();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.upgradeSecondMagic(5);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.fixUpgradeDesc();
            this.initializeDescription();
        }
    }
}
